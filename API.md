# CSI API 接口文档

## 基础信息
- 基础URL: `http://localhost:8080/api`
- 所有响应格式均为统一的 JSON 格式：
```json
{
"code": 1, // 1表示成功，0表示失败
"msg": "success", // 提示信息
"data": {} // 具体数据
}
```

## 状态数据接口

### 0. 获取最新状态(给灯用)
- 端点: `/status/light`
- 方法: GET
- 描述: 获取最新的状态信息（按结束时间），给灯用，只返回0或1
- 请求示例: `GET http://localhost:8080/api/status/light`
- 响应示例:
  - 有人
  ```json
  1
  ```
  - 无人
  ```json
  0
  ```

### 1. 分页获取状态数据（降序）
- 端点: `/status/desc`
- 方法: GET
- 参数:
  - page: 页码（默认1）
  - pageSize: 每页大小（默认10）
- 描述: 按时间戳降序获取状态数据
- 请求示例: `GET http://localhost:8080/api/status/desc?page=1&pageSize=2`
- 响应示例:
```json
{
    "code": 1,
    "msg": "success",
    "data": {
        "total": 100,
        "records": [
            {
                "id": 2,
                "deviceId": "device001",
                "startTimestamp": 1648000010000,
                "endTimestamp": 1648000020000,
                "status": 1,
                "confidence": 0.95
            },
            {
                "id": 1,
                "deviceId": "device001",
                "startTimestamp": 1648000000000,
                "endTimestamp": 1648000010000,
                "status": 1,
                "confidence": 0.95
            }
        ]
    }
}
```

### 2. 分页获取状态数据（升序）
- 端点: `/status/asc`
- 方法: GET
- 参数:
  - page: 页码（默认1）
  - pageSize: 每页大小（默认10）
- 描述: 按时间戳升序获取状态数据
- 请求示例: `GET http://localhost:8080/api/status/asc?page=1&pageSize=2`
- 响应示例:
```json
{
    "code": 1,
    "msg": "success",
    "data": {
        "total": 100,
        "records": [
            {
                "id": 1,
                "deviceId": "device001",
                "startTimestamp": 1648000000000,
                "endTimestamp": 1648000010000,
                "status": 1,
                "confidence": 0.95
            },
            {
                "id": 2,
                "deviceId": "device001",
                "startTimestamp": 1648000010000,
                "endTimestamp": 1648000020000,
                "status": 1,
                "confidence": 0.95
            }
        ]
    }
}
```

### 3. 获取最新状态
- 端点: `/status/latest`
- 方法: GET
- 描述: 获取最新的状态信息（按结束时间）
- 请求示例: `GET http://localhost:8080/api/status/latest`
- 响应示例:
```json
{
    "code": 1,
    "msg": "success",
    "data": {
        "id": 1,
        "deviceId": "device001",
        "startTimestamp": 1648000000000,
        "endTimestamp": 1648000010000,
        "status": 1,
        "confidence": 0.95
    }
}
```

### 4. 根据时间戳获取状态
- 端点: `/status/{timestamp}`
- 方法: GET
- 参数: 
  - timestamp: 时间戳（毫秒）
- 描述: 获取指定时间戳的状态数据
- 请求示例: `GET http://localhost:8080/api/status/1648000000000`
- 可能的响应:
  - 成功响应:
```json
{
    "code": 1,
    "msg": "success",
    "data": {
        "id": 1,
        "deviceId": "device001",
        "startTimestamp": 1648000000000,
        "endTimestamp": 1648000010000,
        "status": 1,
        "confidence": 0.95
    }
}
```
  - 离线响应:
```json
{
    "code": 0,
    "msg": "OFFLINE",
    "data": null
}
```
  - 处理中响应:
```json
{
    "code": 0,
    "msg": "PROCESSING",
    "data": null
}
```

### 5. 获取最新状态（按ID）
- 端点: `/status/latestById`
- 方法: GET
- 描述: 获取ID最大的状态记录
- 请求示例: `GET http://localhost:8080/api/status/latestById`
- 响应示例:
```json
{
    "code": 1,
    "msg": "success",
    "data": {
        "id": 100,
        "deviceId": "device001",
        "startTimestamp": 1648000000000,
        "endTimestamp": 1648000010000,
        "status": 1,
        "confidence": 0.95
    }
}
```

### 6. 获取最新状态（按开始时间）
- 端点: `/status/latestByStart`
- 方法: GET
- 描述: 获取开始时间最新的状态记录
- 请求示例: `GET http://localhost:8080/api/status/latestByStart`
- 响应示例:
```json
{
    "code": 1,
    "msg": "success",
    "data": {
        "id": 1,
        "deviceId": "device001",
        "startTimestamp": 1648000100000,
        "endTimestamp": 1648000110000,
        "status": 1,
        "confidence": 0.95
    }
}
```

### 7. 获取最新状态（按结束时间）
- 端点: `/status/latestByEnd`
- 方法: GET
- 描述: 获取结束时间最新的状态记录
- 请求示例: `GET http://localhost:8080/api/status/latestByEnd`
- 响应示例:
```json
{
    "code": 1,
    "msg": "success",
    "data": {
        "id": 1,
        "deviceId": "device001",
        "startTimestamp": 1648000000000,
        "endTimestamp": 1648000110000,
        "status": 1,
        "confidence": 0.95
    }
}
```

### 8. 获取时间段内的状态数据
- 端点: `/status/between`
- 方法: GET
- 参数:
  - startTime: 开始时间戳（毫秒）
  - endTime: 结束时间戳（毫秒）
- 描述: 获取时间段内的所有状态数据
- 请求示例: `GET http://localhost:8080/api/status/between?startTime=1648000000000&endTime=1648000100000`
- 成功响应示例:
```json
{
    "code": 1,
    "msg": "success",
    "data": [
        {
            "id": 1,
            "deviceId": "device001",
            "startTimestamp": 1648000000000,
            "endTimestamp": 1648000010000,
            "status": 1,
            "confidence": 0.95
        },
        {
            "id": 2,
            "deviceId": "device001",
            "startTimestamp": 1648000010000,
            "endTimestamp": 1648000020000,
            "status": 0,
            "confidence": 0.92
        }
    ]
}
```
- 错误响应示例:
```json
{
    "code": 0,
    "msg": "开始时间必须小于结束时间",
    "data": null
}
```

### 9. 获取状态数据总数
- 端点: `/status/count`
- 方法: GET
- 描述: 获取状态数据表中的总记录数
- 请求示例: `GET http://localhost:8080/api/status/count`
- 响应示例:
```json
{
    "code": 1,
    "msg": "success", 
    "data": 500
}
```

### 10. 创建状态数据
- 端点: `/status`
- 方法: POST
- 描述: 创建新的状态数据
- 请求体:
```json
{
    "deviceId": "device001",
    "startTimestamp": 1648000000000,
    "endTimestamp": 1648000010000,
    "status": 1,
    "confidence": 0.95
}
```
- 响应示例:
```json
{
    "code": 1,
    "msg": "success",
    "data": {
        "id": 101,
        "deviceId": "device001",
        "startTimestamp": 1648000000000,
        "endTimestamp": 1648000010000,
        "status": 1,
        "confidence": 0.95
    }
}
```

### 11. 更新状态数据
- 端点: `/status/{id}`
- 方法: PUT
- 参数:
  - id: 状态数据ID（路径参数）
- 描述: 更新指定ID的状态数据
- 请求体:
```json
{
    "deviceId": "device001",
    "startTimestamp": 1648000000000,
    "endTimestamp": 1648000010000,
    "status": 2,
    "confidence": 0.98
}
```
- 响应示例:
```json
{
    "code": 1,
    "msg": "success",
    "data": {
        "id": 101,
        "deviceId": "device001",
        "startTimestamp": 1648000000000,
        "endTimestamp": 1648000010000,
        "status": 2,
        "confidence": 0.98
    }
}
```
- 错误响应示例:
```json
{
    "code": 0,
    "msg": "状态数据不存在",
    "data": null
}
```

### 12. 删除状态数据
- 端点: `/status/{id}`
- 方法: DELETE
- 参数:
  - id: 状态数据ID（路径参数）
- 描述: 删除指定ID的状态数据
- 请求示例: `DELETE http://localhost:8080/api/status/101`
- 响应示例:
```json
{
    "code": 1,
    "msg": "success",
    "data": null
}
```
- 错误响应示例:
```json
{
    "code": 0,
    "msg": "状态数据不存在",
    "data": null
}
```

## 原始数据接口

### 1. 分页获取原始数据（降序）
- 端点: `/rawData/desc`
- 方法: GET
- 参数:
  - page: 页码（默认1）
  - pageSize: 每页大小（默认10）
- 描述: 按时间戳降序获取原始数据
- 请求示例: `GET http://localhost:8080/api/rawData/desc?page=1&pageSize=2`
- 响应示例:
```json
{
    "code": 1,
    "msg": "success",
    "data": {
        "total": 100,
        "records": [
            {
                "id": 2,
                "deviceId": "device001",
                "timestamp": 1648000010000,
                "csiData": "...",
                "processed": 1
            },
            {
                "id": 1,
                "deviceId": "device001",
                "timestamp": 1648000000000,
                "csiData": "...",
                "processed": 1
            }
        ]
    }
}
```

### 2. 分页获取原始数据（升序）
- 端点: `/rawData/asc`
- 方法: GET
- 参数:
  - page: 页码（默认1）
  - pageSize: 每页大小（默认10）
- 描述: 按时间戳升序获取原始数据
- 请求示例: `GET http://localhost:8080/api/rawData/asc?page=1&pageSize=2`
- 响应示例:
```json
{
    "code": 1,
    "msg": "success",
    "data": {
        "total": 100,
        "records": [
            {
                "id": 1,
                "deviceId": "device001",
                "timestamp": 1648000000000,
                "csiData": "...",
                "processed": 1
            },
            {
                "id": 2,
                "deviceId": "device001",
                "timestamp": 1648000010000,
                "csiData": "...",
                "processed": 1
            }
        ]
    }
}
```

### 3. 获取最新原始数据
- 端点: `/rawData/latest`
- 方法: GET
- 描述: 获取最新的一条原始数据
- 请求示例: `GET http://localhost:8080/api/rawData/latest`
- 响应示例:
```json
{
    "code": 1,
    "msg": "success",
    "data": {
        "id": 100,
        "deviceId": "device001",
        "timestamp": 1648000100000,
        "csiData": "...",
        "processed": 1
    }
}
```

### 4. 根据时间戳获取原始数据
- 端点: `/rawData/{timestamp}`
- 方法: GET
- 参数:
  - timestamp: 时间戳（毫秒）
- 描述: 获取指定时间戳附近的原始数据（±350ms范围内最接近的数据）
- 请求示例: `GET http://localhost:8080/api/rawData/1648000000000`
- 成功响应示例:
```json
{
    "code": 1,
    "msg": "success",
    "data": {
        "id": 1,
        "deviceId": "device001",
        "timestamp": 1648000000100,
        "csiData": "...",
        "processed": 1
    }
}
```
- 错误响应示例:
```json
{
    "code": 0,
    "msg": "No data found for this timestamp",
    "data": null
}
```

### 5. 获取时间段内的原始数据
- 端点: `/rawData/between`
- 方法: GET
- 参数:
  - startTime: 开始时间戳（毫秒）
  - endTime: 结束时间戳（毫秒）
- 描述: 获取时间段内的所有原始数据
- 请求示例: `GET http://localhost:8080/api/rawData/between?startTime=1648000000000&endTime=1648000100000`
- 成功响应示例:
```json
{
    "code": 1,
    "msg": "success",
    "data": [
        {
            "id": 1,
            "deviceId": "device001",
            "timestamp": 1648000000000,
            "csiData": "...",
            "processed": 1
        },
        {
            "id": 2,
            "deviceId": "device001",
            "timestamp": 1648000010000,
            "csiData": "...",
            "processed": 1
        }
    ]
}
```
- 错误响应示例:
```json
{
    "code": 0,
    "msg": "开始时间必须小于结束时间",
    "data": null
}
```

### 6. 获取原始数据总数
- 端点: `/rawData/count`
- 方法: GET
- 描述: 获取原始数据表中的总记录数
- 请求示例: `GET http://localhost:8080/api/rawData/count`
- 响应示例:
```json
{
    "code": 1,
    "msg": "success",
    "data": 1000
}
```

### 7. 创建原始数据
- 端点: `/rawData`
- 方法: POST
- 描述: 创建新的原始数据
- 请求体:
```json
{
    "deviceId": "device001",
    "timestamp": 1648000000000,
    "csiData": "...",
    "processed": 0
}
```
- 响应示例:
```json
{
    "code": 1,
    "msg": "success",
    "data": {
        "id": 101,
        "deviceId": "device001",
        "timestamp": 1648000000000,
        "csiData": "...",
        "processed": 0
    }
}
```

### 8. 更新原始数据
- 端点: `/rawData/{id}`
- 方法: PUT
- 参数:
  - id: 原始数据ID（路径参数）
- 描述: 更新指定ID的原始数据
- 请求体:
```json
{
    "deviceId": "device001",
    "timestamp": 1648000000000,
    "csiData": "...",
    "processed": 1
}
```
- 响应示例:
```json
{
    "code": 1,
    "msg": "success",
    "data": {
        "id": 101,
        "deviceId": "device001",
        "timestamp": 1648000000000,
        "csiData": "...",
        "processed": 1
    }
}
```
- 错误响应示例:
```json
{
    "code": 0,
    "msg": "原始数据不存在",
    "data": null
}
```

### 9. 删除原始数据
- 端点: `/rawData/{id}`
- 方法: DELETE
- 参数:
  - id: 原始数据ID（路径参数）
- 描述: 删除指定ID的原始数据
- 请求示例: `DELETE http://localhost:8080/api/rawData/101`
- 响应示例:
```json
{
    "code": 1,
    "msg": "success",
    "data": null
}
```
- 错误响应示例:
```json
{
    "code": 0,
    "msg": "原始数据不存在",
    "data": null
}
```

## 原始数据与状态数据结构说明

### Status 状态数据
```json
{
"id": 1,
"deviceId": "device001",
"startTimestamp": 1648000000000,
"endTimestamp": 1648000010000,
"status": 1,
"confidence": 0.95
}
```

### RawData 原始数据
```json
{
"id": 1,
"deviceId": "device001",
"timestamp": 1648000000000,
"csiData": "...",
"processed": 1
}
```

## 服务器监控接口

### 1. 获取服务器完整信息
- 端点: `/api/server/info`
- 方法: GET
- 描述: 获取服务器的CPU、内存、磁盘、网络和进程信息
- 响应示例:
```json
{
    "code": 1,
    "msg": "success",
    "data": {
        "cpu": {
            "systemCpuLoad": 25.5,
            "processCpuLoad": 10.2,
            "coreCount": 8,
            "model": "Intel(R) Core(TM) i7-10700K"
        },
        "memory": {
            "total": 17179869184,
            "used": 8589934592,
            "free": 8589934592,
            "usageRate": 50.0
        },
        "disks": [
            {
                "name": "sda",
                "totalSpace": 256060514304,
                "freeSpace": 128030257152,
                "readBytes": 1024000,
                "writeBytes": 512000
            }
        ],
        "network": {
            "bytesReceived": 1048576,
            "bytesSent": 524288,
            "packetsReceived": 1000,
            "packetsSent": 500
        },
        "topProcesses": [
            {
                "pid": 1234,
                "name": "java",
                "cpuUsage": 15.5,
                "memoryUsed": 1073741824,
                "user": "root"
            }
        ]
    }
}
```

### 2. 获取CPU信息
- 端点: `/api/server/cpu`
- 方法: GET
- 描述: 获取CPU使用率和基本信息
- 响应示例:
```json
{
    "code": 1,
    "msg": "success",
    "data": {
        "systemCpuLoad": 25.5,
        "processCpuLoad": 10.2,
        "coreCount": 8,
        "model": "Intel(R) Core(TM) i7-10700K"
    }
}
```

### 3. 获取内存信息
- 端点: `/api/server/memory`
- 方法: GET
- 描述: 获取内存使用情况
- 响应示例:
```json
{
    "code": 1,
    "msg": "success",
    "data": {
        "total": 17179869184,
        "used": 8589934592,
        "free": 8589934592,
        "usageRate": 50.0
    }
}
```

### 4. 获取磁盘信息
- 端点: `/api/server/disk`
- 方法: GET
- 描述: 获取磁盘使用情况和I/O信息
- 响应示例:
```json
{
    "code": 1,
    "msg": "success",
    "data": [
        {
            "name": "sda",
            "totalSpace": 256060514304,
            "freeSpace": 128030257152,
            "readBytes": 1024000,
            "writeBytes": 512000
        },
        {
            "name": "sdb",
            "totalSpace": 512060514304,
            "freeSpace": 256030257152,
            "readBytes": 2048000,
            "writeBytes": 1024000
        }
    ]
}
```

### 5. 获取网络信息
- 端点: `/api/server/network`
- 方法: GET
- 描述: 获取网络流量信息
- 响应示例:
```json
{
    "code": 1,
    "msg": "success",
    "data": {
        "bytesReceived": 1048576,
        "bytesSent": 524288,
        "packetsReceived": 1000,
        "packetsSent": 500
    }
}
```

### 6. 获取进程信息
- 端点: `/api/server/processes`
- 方法: GET
- 参数:
  - limit: 返回的进程数量（默认5）
- 描述: 获取资源占用最多的进程信息
- 请求示例: `GET http://localhost:8080/api/server/processes?limit=3`
- 响应示例:
```json
{
    "code": 1,
    "msg": "success",
    "data": [
        {
            "pid": 1234,
            "name": "java",
            "cpuUsage": 15.5,
            "memoryUsed": 1073741824,
            "user": "root"
        },
        {
            "pid": 5678,
            "name": "mysql",
            "cpuUsage": 8.3,
            "memoryUsed": 536870912,
            "user": "mysql"
        },
        {
            "pid": 9012,
            "name": "nginx",
            "cpuUsage": 2.1,
            "memoryUsed": 268435456,
            "user": "nginx"
        }
    ]
}
```

## 服务器监控接口数据结构说明

### CPU信息 (CpuInfo)
- systemCpuLoad: 系统CPU使用率（百分比，0-100）
- processCpuLoad: 当前进程CPU使用率（百分比，0-100）
- coreCount: CPU核心数
- model: CPU型号

### 内存信息 (MemoryInfo)
- total: 总内存大小（字节）
- used: 已使用内存大小（字节）
- free: 可用内存大小（字节）
- usageRate: 内存使用率（百分比，0-100）

### 磁盘信息 (DiskInfo)
- name: 磁盘名称
- totalSpace: 磁盘总容量（字节）
- freeSpace: 磁盘剩余空间（字节）
- readBytes: 磁盘读取字节数
- writeBytes: 磁盘写入字节数

### 网络信息 (NetworkInfo)
- bytesReceived: 接收的总字节数
- bytesSent: 发送的总字节数
- packetsReceived: 接收的数据包数量
- packetsSent: 发送的数据包数量

### 进程信息 (ProcessInfo)
- pid: 进程ID
- name: 进程名称
- cpuUsage: 进程CPU使用率（百分比，0-100）
- memoryUsed: 进程使用的内存大小（字节）
- user: 进程所属用户

## 守护进程管理接口

### 1. CSI守护进程管理

#### 1.1 获取CSI守护进程状态
- 端点: `/api/daemon/csi/status`
- 方法: GET
- 描述: 获取CSI守护进程的运行状态
- 请求示例: `GET http://localhost:8080/api/daemon/csi/status`
- 响应示例:
```json
{
    "code": 1,
    "msg": "success",
    "data": {
        "running": true,
        "processName": "csi_daemon.py"
    }
}
```

#### 1.2 获取CSI守护进程日志
- 端点: `/api/daemon/csi/log`
- 方法: GET
- 参数:
  - lines: 要获取的日志行数（默认100）
- 描述: 获取CSI守护进程的最新日志
- 请求示例: `GET http://localhost:8080/api/daemon/csi/log?lines=50`
- 响应示例:
```json
{
    "code": 1,
    "msg": "success",
    "data": {
        "fileName": "csi_daemon.log",
        "lines": [
            "2024-03-06 10:00:00 INFO: 守护进程启动",
            "2024-03-06 10:00:01 INFO: 加载模型成功",
            // ... 更多日志行
        ]
    }
}
```

#### 1.3 启动CSI守护进程
- 端点: `/api/daemon/csi/start`
- 方法: POST
- 描述: 在conda环境中启动CSI守护进程
- 请求示例: `POST http://localhost:8080/api/daemon/csi/start`
- 响应示例:
```json
{
    "code": 1,
    "msg": "success",
    "data": "启动成功"
}
```

#### 1.4 停止CSI守护进程
- 端点: `/api/daemon/csi/stop`
- 方法: POST
- 描述: 停止CSI守护进程
- 请求示例: `POST http://localhost:8080/api/daemon/csi/stop`
- 响应示例:
```json
{
    "code": 1,
    "msg": "success",
    "data": "停止成功"
}
```

### 2. Status守护进程管理

#### 2.1 获取Status守护进程状态
- 端点: `/api/daemon/status/status`
- 方法: GET
- 描述: 获取Status守护进程的运行状态
- 请求示例: `GET http://localhost:8080/api/daemon/status/status`
- 响应示例:
```json
{
    "code": 1,
    "msg": "success",
    "data": {
        "running": true,
        "processName": "status_daemon.py"
    }
}
```

#### 2.2 获取Status守护进程日志
- 端点: `/api/daemon/status/log`
- 方法: GET
- 参数:
  - lines: 要获取的日志行数（默认100）
- 描述: 获取Status守护进程的最新日志
- 请求示例: `GET http://localhost:8080/api/daemon/status/log?lines=50`
- 响应示例:
```json
{
    "code": 1,
    "msg": "success",
    "data": {
        "fileName": "status_daemon.log",
        "lines": [
            "2024-03-06 10:00:00 INFO: 守护进程启动",
            "2024-03-06 10:00:01 INFO: 状态更新成功",
            // ... 更多日志行
        ]
    }
}
```

#### 2.3 启动Status守护进程
- 端点: `/api/daemon/status/start`
- 方法: POST
- 描述: 在conda环境中启动Status守护进程
- 请求示例: `POST http://localhost:8080/api/daemon/status/start`
- 响应示例:
```json
{
    "code": 1,
    "msg": "success",
    "data": "启动成功"
}
```

#### 2.4 停止Status守护进程
- 端点: `/api/daemon/status/stop`
- 方法: POST
- 描述: 停止Status守护进程
- 请求示例: `POST http://localhost:8080/api/daemon/status/stop`
- 响应示例:
```json
{
    "code": 1,
    "msg": "success",
    "data": "停止成功"
}
```

### 3. 模型管理

#### 3.1 获取模型列表
- 端点: `/api/daemon/model/list`
- 方法: GET
- 描述: 获取所有可用模型的文件名列表
- 请求示例: `GET http://localhost:8080/api/daemon/model/list`
- 响应示例:
```json
{
    "code": 1,
    "msg": "success",
    "data": [
        "model_v1.pth",
        "model_v2.pth",
        "model_v3.pth"
    ]
}
```

#### 3.2 下载模型
- 端点: `/api/daemon/model/download/{modelName}`
- 方法: GET
- 参数:
  - modelName: 模型文件名（路径参数）
- 描述: 下载指定的模型文件
- 请求示例: `GET http://localhost:8080/api/daemon/model/download/model_v1.pth`
- 响应:
  - Content-Type: application/octet-stream
  - Content-Disposition: attachment; filename="model_v1.pth"
  - Body: 模型文件的二进制内容
- 错误响应示例:
```json
{
    "code": 0,
    "msg": "下载模型失败: 模型不存在",
    "data": null
}
```

#### 3.3 上传模型
- 端点: `/api/daemon/model/upload`
- 方法: POST
- 参数:
  - file: 模型文件（multipart/form-data）
- 描述: 上传新的模型文件
- 请求示例: `POST http://localhost:8080/api/daemon/model/upload`
- 请求头:
  - Content-Type: multipart/form-data
- 响应示例:
```json
{
    "code": 1,
    "msg": "success",
    "data": "模型上传成功"
}
```
- 错误响应示例:
```json
{
    "code": 0,
    "msg": "上传文件为空",
    "data": null
}
```

#### 3.4 删除模型
- 端点: `/api/daemon/model/{modelName}`
- 方法: DELETE
- 参数:
  - modelName: 模型文件名（路径参数）
- 描述: 删除指定的模型文件
- 请求示例: `DELETE http://localhost:8080/api/daemon/model/model_v1.pth`
- 响应示例:
```json
{
    "code": 1,
    "msg": "success",
    "data": "模型删除成功"
}
```
- 错误响应示例:
```json
{
    "code": 0,
    "msg": "模型不存在",
    "data": null
}
```

#### 3.5 选择模型
- 端点: `/api/daemon/model/select/{modelName}`
- 方法: POST
- 参数:
  - modelName: 模型文件名（路径参数）
- 描述: 切换到指定的模型
- 请求示例: `POST http://localhost:8080/api/daemon/model/select/model_v1.pth`
- 响应示例:
```json
{
    "code": 1,
    "msg": "success",
    "data": "模型切换成功"
}
```
- 错误响应示例:
```json
{
    "code": 0,
    "msg": "模型不存在",
    "data": null
}
```

#### 3.6 获取当前模型配置
- 端点: `/api/daemon/model/current`
- 方法: GET
- 描述: 获取当前正在使用的模型配置信息
- 请求示例: `GET http://localhost:8080/api/daemon/model/current`
- 响应示例:
```json
{
    "code": 1,
    "msg": "success",
    "data": {
        "model_name": "ResNet50",
        "model_path": "./saved_models/model.pth",
        "signal_process_method": "mean_filter",
        "feature_type": "振幅"
    }
}
```
- 错误响应示例:
```json
{
    "code": 0,
    "msg": "读取配置文件失败: 配置文件不存在",
    "data": null
}
```

### 注意事项
1. 所有守护进程操作都在conda的25CAAC环境中执行
2. 日志文件位于`../daemon/tmp/`目录下
3. 模型文件位于`../daemon/saved_models/`目录下
4. 模型切换功能需要重启相关守护进程才能生效
5. 文件上传大小可能受到服务器配置限制


## 获取所有操作日志
- 端点: `/api/log`
- 方法: GET
- 描述: 获取所有操作日志（最新的250条）
- 请求示例: `GET http://localhost:8080/api/log`
- 响应示例:
```json
{
    "code": 1,
    "msg": "success",
    "data": [
        {
            "id": 1,
            "operation": "User login",
            "timestamp": "2023-10-01T12:00:00Z"
        },
        {
            "id": 2,
            "operation": "Data export",
            "timestamp": "2023-10-01T12:05:00Z"
        }
    ]
}
```
- 错误响应示例:
```json
{
    "code": 0,
    "msg": "获取操作日志失败",
    "data": null
}
```