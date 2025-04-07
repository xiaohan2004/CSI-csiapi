-- 1. 使用数据库
USE csi;

-- 2. 创建操作日志表
CREATE TABLE operate_log
(
    id            INT AUTO_INCREMENT PRIMARY KEY COMMENT 'ID，自增主键',
    operate_time  DATETIME COMMENT '操作时间，记录操作发生的具体时间',
    class_name    VARCHAR(255) COMMENT '操作类名，记录被操作的类名',
    method_name   VARCHAR(255) COMMENT '操作方法名，记录被调用的方法名',
    method_params TEXT COMMENT '操作方法参数，JSON格式存储方法的参数',
    return_value  TEXT COMMENT '操作方法返回值，JSON格式存储方法的返回值',
    cost_time     BIGINT COMMENT '操作耗时，单位为毫秒'
) COMMENT '操作日志表';