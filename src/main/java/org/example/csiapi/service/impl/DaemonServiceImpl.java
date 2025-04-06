package org.example.csiapi.service.impl;

import org.example.csiapi.service.DaemonService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DaemonServiceImpl implements DaemonService {
    private static final String CONDA_ENV = "25CAAC";
    private static final String DAEMON_DIR = "../daemon";
    private static final String MODELS_DIR = DAEMON_DIR + "/saved_models";
    private static final String LOGS_DIR = DAEMON_DIR + "/tmp";

    @Override
    public Map<String, Object> getCsiStatus() {
        return checkProcessStatus("csi_daemon.py");
    }

    @Override
    public Map<String, Object> getCsiLog(int lines) {
        return readLog("csi_daemon.log", lines);
    }

    @Override
    public void startCsiDaemon() {
        controlDaemon("csi_daemon.py", "start");
    }

    @Override
    public void stopCsiDaemon() {
        controlDaemon("csi_daemon.py", "stop");
    }

    @Override
    public Map<String, Object> getStatusDaemonStatus() {
        return checkProcessStatus("status_daemon.py");
    }

    @Override
    public Map<String, Object> getStatusLog(int lines) {
        return readLog("status_daemon.log", lines);
    }

    @Override
    public void startStatusDaemon() {
        controlDaemon("status_daemon.py", "start");
    }

    @Override
    public void stopStatusDaemon() {
        controlDaemon("status_daemon.py", "stop");
    }

    @Override
    public List<String> listModels() {
        File modelsDir = new File(MODELS_DIR);
        if (!modelsDir.exists()) {
            throw new RuntimeException("模型目录不存在");
        }
        
        return Arrays.stream(modelsDir.listFiles())
            .filter(File::isFile)
            .map(File::getName)
            .collect(Collectors.toList());
    }

    @Override
    public void uploadModel(MultipartFile file) {
        if (file.isEmpty()) {
            throw new RuntimeException("上传文件为空");
        }

        try {
            String fileName = file.getOriginalFilename();
            File dest = new File(MODELS_DIR + "/" + fileName);
            
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();
            }
            
            file.transferTo(dest);
        } catch (IOException e) {
            throw new RuntimeException("模型上传失败: " + e.getMessage());
        }
    }

    @Override
    public void deleteModel(String modelName) {
        File modelFile = new File(MODELS_DIR + "/" + modelName);
        if (!modelFile.exists()) {
            throw new RuntimeException("模型不存在");
        }
        
        if (!modelFile.delete()) {
            throw new RuntimeException("模型删除失败");
        }
    }

    @Override
    public void selectModel(String modelName) {
        File modelFile = new File(MODELS_DIR + "/" + modelName);
        if (!modelFile.exists()) {
            throw new RuntimeException("模型不存在");
        }

        // TODO: 实现具体的模型切换逻辑
    }

    @Override
    public File downloadModel(String modelName) {
        File modelFile = new File(MODELS_DIR + "/" + modelName);
        if (!modelFile.exists()) {
            throw new RuntimeException("模型不存在");
        }
        return modelFile;
    }

    private Map<String, Object> checkProcessStatus(String processName) {
        try {
            ProcessBuilder pb = new ProcessBuilder("ps", "-ef");
            Process p = pb.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            
            boolean isRunning = reader.lines()
                .anyMatch(line -> line.contains(processName) && !line.contains("grep"));
            
            Map<String, Object> status = new HashMap<>();
            status.put("running", isRunning);
            status.put("processName", processName);
            
            return status;
        } catch (IOException e) {
            throw new RuntimeException("获取进程状态失败: " + e.getMessage());
        }
    }

    private Map<String, Object> readLog(String logFileName, int lines) {
        File logFile = new File(LOGS_DIR + "/" + logFileName);
        if (!logFile.exists()) {
            throw new RuntimeException("日志文件不存在");
        }

        try {
            List<String> logLines = new ArrayList<>();
            Process p = Runtime.getRuntime().exec("tail -n " + lines + " " + logFile.getAbsolutePath());
            
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    logLines.add(line);
                }
            }

            Map<String, Object> result = new HashMap<>();
            result.put("fileName", logFileName);
            result.put("lines", logLines);
            
            return result;
        } catch (IOException e) {
            throw new RuntimeException("读取日志失败: " + e.getMessage());
        }
    }

    private void controlDaemon(String daemonScript, String action) {
        try {
            List<String> command = new ArrayList<>();
            command.add("conda");
            command.add("run");
            command.add("-n");
            command.add(CONDA_ENV);
            command.add("python");
            command.add(DAEMON_DIR + "/" + daemonScript);
            command.add(action);

            ProcessBuilder pb = new ProcessBuilder(command);
            Process p = pb.start();
            
            int exitCode = p.waitFor();
            
            if (exitCode != 0) {
                throw new RuntimeException(action + " 操作执行失败");
            }
        } catch (Exception e) {
            throw new RuntimeException("执行 " + action + " 操作失败: " + e.getMessage());
        }
    }
} 