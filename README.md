# AI-Respository

## OpenAi CodeX 平台后端

该工程是一个基于 Spring Boot 的标准后台应用，采用经典的 Controller / Service / Mapper 分层模式，ORM 使用 MyBatis，数据库为 MySQL，缓存为 Redis，并提供一个默认欢迎接口。

### 主要特性
- Spring MVC `Controller` 依赖业务 `Service` 接口与实现。
- MyBatis Mapper + XML 与领域对象一一对应，便于扩展更多表。
- Redis 作为欢迎文案的缓存层，数据库不可用时自动回退到内置默认值。
- 开箱即用的 `application.yml`，可通过环境变量覆盖数据库和 Redis 连接信息。

### 模块结构
```
src/main/java/com/codex/platform
├── PlatformApplication.java        # 启动类
├── config
│   └── RedisConfig.java            # RedisTemplate 字符串序列化配置
├── controller
│   └── WelcomeController.java      # 对外控制器
├── domain
│   └── WelcomeMessage.java         # 欢迎文案实体
├── mapper
│   └── WelcomeMessageMapper.java   # MyBatis Mapper 接口
└── service
    ├── WelcomeService.java         # 业务接口
    └── impl
        └── WelcomeServiceImpl.java # 业务实现，调用 Redis + Mapper

src/main/resources
├── application.yml                 # MySQL、Redis、MyBatis 配置
└── mappers
    └── WelcomeMessageMapper.xml    # Mapper XML（welcome_message 表）
```

### 快速开始
1. 准备依赖服务
   - 启动 MySQL，并创建数据库 `codex_platform`：
     ```sql
     CREATE DATABASE codex_platform CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
     USE codex_platform;
     CREATE TABLE welcome_message (
       id BIGINT PRIMARY KEY AUTO_INCREMENT,
       content VARCHAR(255) NOT NULL
     );
     INSERT INTO welcome_message(content) VALUES ('欢迎使用Open Ai CodeX 平台服务，世界的边界在这里开始变成无限。');
     ```
   - 启动 Redis（默认 `localhost:6379`）。

2. 启动应用（需要可访问 Maven 中央仓库以下载依赖）：
   ```bash
   mvn spring-boot:run
   ```

3. 验证接口
   在浏览器或命令行访问 `http://localhost:8080/`，将看到：
   
   `欢迎使用Open Ai CodeX 平台服务，世界的边界在这里开始变成无限。`

### 运行测试
```bash
mvn test
```
若网络环境无法访问 Maven 中央仓库，依赖下载会失败，可在具备外网的环境下执行上述命令。
