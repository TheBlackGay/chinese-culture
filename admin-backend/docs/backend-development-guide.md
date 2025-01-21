# 后端开发规范文档

## 1. 项目结构
```
admin-backend
├── src/main/java/com/chinese/culture/admin
│   ├── common              # 通用组件
│   │   ├── result         # 统一返回结果
│   │   └── exception      # 全局异常处理
│   ├── config             # 配置类
│   ├── controller         # 控制器层
│   ├── service           # 服务层
│   │   └── impl          # 服务实现
│   ├── model             # 数据模型
│   │   ├── entity       # 实体类
│   │   ├── dto          # 数据传输对象
│   │   └── vo           # 视图对象
│   └── util              # 工具类
└── resources
    └── application.yml    # 配置文件

```

## 2. 编码规范

### 2.1 命名规范
- **包名**：全小写，例如：`com.chinese.culture.admin.controller`
- **类名**：大驼峰命名，例如：`ZiweiController`
- **方法名**：小驼峰命名，例如：`calculateHoroscope`
- **变量名**：小驼峰命名，例如：`birthYear`
- **常量名**：全大写下划线分隔，例如：`MAX_VALUE`

### 2.2 注释规范
- 类注释：使用 Javadoc 格式，包含描述、作者、日期
```java
/**
 * 紫微斗数控制器
 * 
 * @author yourname
 * @date 2024-01-01
 */
```
- 方法注释：使用 Javadoc 格式，包含描述、参数、返回值
```java
/**
 * 计算紫微斗数命盘
 *
 * @param request 请求参数
 * @return 命盘数据
 */
```

### 2.3 异常处理
- 使用自定义的 `BusinessException` 处理业务异常
- 在 service 层抛出异常，在全局异常处理器中统一处理
- 禁止在 catch 中直接打印异常堆栈，应该使用日志框架

### 2.4 返回值规范
- 统一使用 `Result<T>` 包装返回结果
- 成功操作使用 `Result.success()`
- 失败操作使用 `Result.error()`
- 自定义异常状态码使用 `ResultCode` 枚举

### 2.5 接口规范
- 使用 RESTful 风格设计 API
- URL 命名使用小写字母，多个单词用中划线分隔
- 使用 HTTP 方法表示操作类型（GET、POST、PUT、DELETE）
- 请求参数使用 DTO 对象，响应数据使用 VO 对象

### 2.6 Swagger 注解使用
- 控制器类上使用 `@Api(tags = "模块名称")`
- 方法上使用 `@ApiOperation(value = "接口说明")`
- 参数使用 `@ApiModelProperty` 注解

## 3. 统一响应结构
```java
{
    "code": 200,          // 状态码
    "message": "成功",     // 提示信息
    "data": {}           // 响应数据
}
```

## 4. 状态码说明
- 200：操作成功
- 500：操作失败
- 404：参数校验失败
- 401：未授权
- 403：禁止访问
- 1001-1999：业务异常

## 5. 日志规范
- 使用 SLF4J + Logback
- 日志级别：ERROR > WARN > INFO > DEBUG
- 生产环境禁用 DEBUG 级别
- 异常日志需要记录完整堆栈信息

## 6. 参数校验
- 使用 JSR303 注解进行参数校验
- 自定义校验注解放在 common.validator 包下
- 统一处理校验异常，返回详细的错误信息

## 7. 工具类使用
- 优先使用 Java 8+ 提供的工具类
- 字符串处理优先使用 Apache Commons Lang3
- 时间处理使用 Java 8 的 LocalDateTime
- 集合操作优先使用 Java 8 Stream API

## 8. 版本控制
- Git 分支命名规范：
  - 主分支：master
  - 开发分支：develop
  - 功能分支：feature/xxx
  - 修复分支：hotfix/xxx
- 提交信息格式：`type: message`
  - type: feat/fix/docs/style/refactor/test/chore

## 9. 单元测试
- 使用 JUnit 5 编写单元测试
- 测试类命名：`XxxTest`
- 测试方法命名：`test_method_scenario`
- 保持测试用例的独立性和可重复性 

## 10. 技术规范

### 10.1 核心框架
- Spring Boot 2.7.x
  - 使用 `@SpringBootApplication` 注解标注启动类
  - 配置文件优先使用 YAML 格式
  - 使用 `@ConfigurationProperties` 进行配置绑定

### 10.2 API 文档
- Swagger 3.0 (OpenAPI)
  - 使用 `@Tag` 标注控制器分类
  - 使用 `@Operation` 描述接口功能
  - 使用 `@Parameter` 描述请求参数
  - 访问地址：`http://localhost:8080/swagger-ui/index.html`

### 10.3 参数校验
- Spring Validation (JSR-303)
  - 请求类上使用 `@Validated` 开启校验
  - 常用注解：`@NotNull`、`@NotBlank`、`@Size`、`@Range`
  - 自定义校验注解需实现 `ConstraintValidator` 接口

### 10.4 日志框架
- SLF4J + Logback
  - 使用 `@Slf4j` 注解自动注入日志对象
  - 日志配置文件：`logback-spring.xml`
  - 日志输出格式：`%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{50} - %msg%n`

### 10.5 工具库
- Lombok
  - `@Data`: 生成 getter/setter/toString 等
  - `@Builder`: 构建者模式
  - `@Slf4j`: 注入日志对象
  - 避免使用 `@AllArgsConstructor`，优先使用 `@Builder`

- Apache Commons
  - StringUtils: 字符串处理
  - CollectionUtils: 集合操作
  - DateUtils: 日期处理（优先使用 Java 8 时间 API）

### 10.6 紫微斗数组件
- iztro
  - 版本：1.0.x
  - 主要用于命盘计算
  - 使用 `IZiwei` 接口进行命盘操作
  - 异常统一使用 `BusinessException` 包装

### 10.7 农历组件
- lunar
  - 版本：1.0.x
  - 用于农历日期转换
  - 使用 `Lunar` 类进行日期操作
  - 注意处理日期边界情况

### 10.8 线程池
- 使用 `@Async` 进行异步操作
- 自定义线程池配置：
```java
@Configuration
public class ThreadPoolConfig {
    @Bean
    public ThreadPoolTaskExecutor asyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(25);
        executor.setThreadNamePrefix("Async-");
        return executor;
    }
}
```

### 10.9 缓存
- Spring Cache
  - 使用 `@Cacheable` 注解缓存方法结果
  - 使用 `@CacheEvict` 清除缓存
  - 缓存 key 命名规范：`模块:业务:id`

### 10.10 跨域配置
```java
@Configuration
public class CorsConfig {
    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
```

### 10.11 接口限流
- 使用 Spring AOP 实现
- 基于 Redis 的令牌桶算法
- 限流注解：
```java
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RateLimit {
    int limit() default 10;    // 限制次数
    int period() default 1;    // 时间周期（秒）
}
```

### 10.12 响应压缩
- 启用 GZip 压缩：
```yaml
server:
  compression:
    enabled: true
    mime-types: application/json,application/xml,text/html,text/plain
    min-response-size: 2048
``` 