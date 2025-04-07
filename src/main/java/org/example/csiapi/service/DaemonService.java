package org.example.csiapi.service;

import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.Map;
import java.io.File;

public interface DaemonService {
    // CSI守护进程相关方法
    Map<String, Object> getCsiStatus();
    Map<String, Object> getCsiLog(int lines);
    void startCsiDaemon();
    void stopCsiDaemon();

    // Status守护进程相关方法
    Map<String, Object> getStatusDaemonStatus();
    Map<String, Object> getStatusLog(int lines);
    void startStatusDaemon();
    void stopStatusDaemon();

    // 模型管理相关方法
    List<String> listModels();
    void uploadModel(MultipartFile file);
    void deleteModel(String modelName);
    void selectModel(String modelName);
    File downloadModel(String modelName);
    Map<String, String> getCurrentModel();
} 