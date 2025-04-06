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

## 数据结构

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