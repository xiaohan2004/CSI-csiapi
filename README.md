# CSI API 服务

## 项目概述
CSI API 是一个基于Spring Boot的RESTful API服务，提供CSI（信道状态信息）数据处理和管理的接口。该服务用于监控设备状态、处理CSI数据并提供相关的状态查询和管理功能。

## 主要功能
- **状态监控**：实时监控设备状态，支持状态的查询和记录
- **数据管理**：管理和处理CSI相关的数据
- **操作日志**：记录系统操作日志，支持操作审计
- **系统配置**：管理系统和模型配置
- **守护程序控制**：控制后台守护进程的运行状态

## 技术栈
- **开发框架**：Spring Boot 3.4.3
- **数据库**：MySQL
- **持久层**：MyBatis
- **API文档**：自定义Markdown文档
- **日志处理**：Spring AOP + 自定义注解
- **JSON处理**：FastJSON
- **系统监控**：OSHI核心库

## 项目结构
```
src/main/java/org/example/csiapi/
├── annotation     # 自定义注解，如操作日志记录注解
├── aop            # 面向切面编程，用于实现日志记录等横切关注点
├── config         # 配置类
├── controller     # 控制器层，处理HTTP请求
├── mapper         # MyBatis映射器接口
├── pojo           # 普通Java对象，包括实体类和DTO
└── service        # 服务层，包含业务逻辑
```

## 快速开始

### 环境要求
- JDK 17+
- Maven 3.6+
- MySQL 8.0+

### 构建与运行
1. **克隆项目**
   ```bash
   git clone <项目仓库URL>
   cd csiapi
   ```

2. **配置数据库**
   
   修改 `src/main/resources/application.properties`或`application.yml`文件中的数据库配置：
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/csiapi
   spring.datasource.username=root
   spring.datasource.password=password
   ```

3. **构建项目**
   ```bash
   mvn clean package
   ```

4. **运行应用**
   ```bash
   java -jar target/csiapi-0.0.1-SNAPSHOT.jar
   ```
   或者使用Spring Boot Maven插件：
   ```bash
   mvn spring-boot:run
   ```

### API文档
详细的API接口文档请参考项目中的`API.md`文件，包含所有接口的详细说明和示例。

## 开发指南

### 添加新接口
1. 在controller包中创建新的控制器类或在现有控制器中添加方法
2. 使用`@RecordLog`注解标记需要记录操作日志的方法（需要添加在实现类方法上，而非接口方法）
3. 在`API.md`中添加新接口的文档

### 日志记录
系统使用AOP实现操作日志的自动记录，通过以下方式工作：
1. 在需要记录日志的方法上添加`@RecordLog`注解
2. 系统自动通过`LogAspect`切面类捕获方法执行信息
3. 记录的日志可通过`/api/log`接口查询

## 注意事项
- 接口注解`@RecordLog`需要添加在实现类的方法上，而不是接口方法上
- 系统配置修改后需要重启服务才能生效
- API响应格式统一为`{code, msg, data}`结构

## 许可证
[许可证类型]

## 贡献指南
1. Fork 项目
2. 创建特性分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 创建Pull Request 