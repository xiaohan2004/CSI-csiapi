package org.example.csiapi.controller;

import org.example.csiapi.pojo.Result;
import org.example.csiapi.service.DaemonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@RestController
@RequestMapping("/api/daemon")
public class DaemonController {

    @Autowired
    private DaemonService daemonService;
    
    // CSI守护进程相关接口
    @GetMapping("/csi/status")
    public Result getCsiStatus() {
        try {
            return Result.success(daemonService.getCsiStatus());
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/csi/log")
    public Result getCsiLog(@RequestParam(defaultValue = "100") int lines) {
        try {
            return Result.success(daemonService.getCsiLog(lines));
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @PostMapping("/csi/start")
    public Result startCsiDaemon() {
        try {
            daemonService.startCsiDaemon();
            return Result.success("启动成功");
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @PostMapping("/csi/stop")
    public Result stopCsiDaemon() {
        try {
            daemonService.stopCsiDaemon();
            return Result.success("停止成功");
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    // Status守护进程相关接口
    @GetMapping("/status/status")
    public Result getStatusDaemonStatus() {
        try {
            return Result.success(daemonService.getStatusDaemonStatus());
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/status/log")
    public Result getStatusLog(@RequestParam(defaultValue = "100") int lines) {
        try {
            return Result.success(daemonService.getStatusLog(lines));
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @PostMapping("/status/start")
    public Result startStatusDaemon() {
        try {
            daemonService.startStatusDaemon();
            return Result.success("启动成功");
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @PostMapping("/status/stop")
    public Result stopStatusDaemon() {
        try {
            daemonService.stopStatusDaemon();
            return Result.success("停止成功");
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    // 模型管理相关接口
    @GetMapping("/model/list")
    public Result listModels() {
        try {
            return Result.success(daemonService.listModels());
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/model/download/{modelName}")
    public ResponseEntity<Resource> downloadModel(@PathVariable String modelName) {
        try {
            File file = daemonService.downloadModel(modelName);
            Resource resource = new FileSystemResource(file);
            
            return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + modelName + "\"")
                .body(resource);
        } catch (Exception e) {
            throw new RuntimeException("下载模型失败: " + e.getMessage());
        }
    }

    @PostMapping("/model/upload")
    public Result uploadModel(@RequestParam("file") MultipartFile file) {
        try {
            daemonService.uploadModel(file);
            return Result.success("模型上传成功");
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @DeleteMapping("/model/{modelName}")
    public Result deleteModel(@PathVariable String modelName) {
        try {
            daemonService.deleteModel(modelName);
            return Result.success("模型删除成功");
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @PostMapping("/model/select/{modelName}")
    public Result selectModel(@PathVariable String modelName) {
        try {
            daemonService.selectModel(modelName);
            return Result.success("模型切换成功");
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
}
