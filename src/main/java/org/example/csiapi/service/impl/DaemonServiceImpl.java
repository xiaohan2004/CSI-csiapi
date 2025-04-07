package org.example.csiapi.service.impl;

import org.example.csiapi.annotation.RecordLog;
import org.example.csiapi.service.DaemonService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.nio.file.Files;
import java.nio.charset.StandardCharsets;

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

    @RecordLog
    @Override
    public void startCsiDaemon() {
        controlDaemon("csi_daemon.py", "start");
    }

    @RecordLog
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

    @RecordLog
    @Override
    public void startStatusDaemon() {
        controlDaemon("status_daemon.py", "start");
    }

    @RecordLog
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

    @RecordLog
    @Override
    public void uploadModel(MultipartFile file) {
        if (file.isEmpty()) {
            throw new RuntimeException("上传文件为空");
        }
    
        try {
            String fileName = file.getOriginalFilename();
            File modelsDir = new File(MODELS_DIR).getAbsoluteFile();
            File dest = new File(modelsDir, fileName);
            
            if (!modelsDir.exists()) {
                modelsDir.mkdirs();
            }
            
            file.transferTo(dest);
        } catch (IOException e) {
            throw new RuntimeException("模型上传失败: " + e.getMessage());
        }
    }

    @RecordLog
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

    @RecordLog
    @Override
    public void selectModel(String modelName) {
        try {
            // 1. 检查模型文件是否存在
            File modelFile = new File(MODELS_DIR + "/" + modelName);
            if (!modelFile.exists()) {
                throw new RuntimeException("模型不存在");
            }

            // 2. 停止守护进程
            stopStatusDaemon();

            // 3. 修改配置文件
            File configFile = new File(DAEMON_DIR + "/predictor_config.ini");
            List<String> newConfig = Arrays.asList(
                "[predictor]",
                "model_name = ResNet50",
                "model_path = ./saved_models/" + modelName,
                "signal_process_method = mean_filter",
                "feature_type = 振幅"
            );
            
            // 写入新的配置
            Files.write(configFile.toPath(), newConfig, StandardCharsets.UTF_8);

            // 4. 重新启动守护进程
            startStatusDaemon();
            
        } catch (IOException e) {
            throw new RuntimeException("修改配置文件失败: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("切换模型失败: " + e.getMessage());
        }
    }

    @RecordLog
    @Override
    public File downloadModel(String modelName) {
        File modelFile = new File(MODELS_DIR + "/" + modelName);
        if (!modelFile.exists()) {
            throw new RuntimeException("模型不存在");
        }
        return modelFile;
    }

    @Override
    public Map<String, String> getCurrentModel() {
        try {
            File configFile = new File(DAEMON_DIR + "/predictor_config.ini");
            if (!configFile.exists()) {
                throw new RuntimeException("配置文件不存在");
            }

            Map<String, String> config = new HashMap<>();
            List<String> lines = Files.readAllLines(configFile.toPath(), StandardCharsets.UTF_8);
            
            for (String line : lines) {
                line = line.trim();
                if (line.startsWith("[") || line.isEmpty()) {
                    continue;
                }
                String[] parts = line.split("=", 2);
                if (parts.length == 2) {
                    config.put(parts[0].trim(), parts[1].trim());
                }
            }
            
            return config;
        } catch (IOException e) {
            throw new RuntimeException("读取配置文件失败: " + e.getMessage());
        }
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
            // 获取 daemon 目录的绝对路径
            File daemonDir = new File(DAEMON_DIR).getAbsoluteFile();
            
            List<String> command = new ArrayList<>();
            command.add("/root/miniconda3/envs/" + CONDA_ENV + "/bin/python");
            // 只使用脚本名，不使用完整路径
            command.add(daemonScript);
            command.add(action);

            ProcessBuilder pb = new ProcessBuilder(command);
            pb.redirectErrorStream(true);
            
            // 设置工作目录为 daemon 目录
            pb.directory(daemonDir);
            
            Process p = pb.start();
            
            // 读取进程的输出
            StringBuilder output = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    output.append(line).append("\n");
                }
            }
            
            int exitCode = p.waitFor();
            
            if (exitCode != 0) {
                throw new RuntimeException(action + " 操作执行失败:\n" + output.toString());
            }
        } catch (Exception e) {
            throw new RuntimeException("执行 " + action + " 操作失败: " + e.getMessage());
        }
    }
} 