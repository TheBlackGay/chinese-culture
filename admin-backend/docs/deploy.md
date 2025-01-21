# 紫微斗数系统部署文档

## 环境要求

### 1. 硬件要求
- CPU: 2核及以上
- 内存: 4GB及以上
- 硬盘: 50GB及以上

### 2. 软件要求
- JDK 1.8+
- MySQL 5.7+
- Redis 6.0+
- Maven 3.6+
- Nginx 1.18+

## 部署步骤

### 1. 数据库配置

1. 创建数据库
```sql
CREATE DATABASE chinese_culture DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
```

2. 执行SQL脚本
```bash
mysql -u root -p chinese_culture < init.sql
```

### 2. Redis配置

1. 修改redis.conf
```conf
# 设置密码
requirepass your_password

# 开启持久化
appendonly yes

# 设置最大内存
maxmemory 2gb

# 设置淘汰策略
maxmemory-policy allkeys-lru
```

2. 启动Redis
```bash
redis-server /etc/redis/redis.conf
```

### 3. 后端服务部署

1. 修改配置文件(application.yml)
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/chinese_culture?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai
    username: your_username
    password: your_password
  
  redis:
    host: localhost
    port: 6379
    password: your_password
    database: 0

server:
  port: 8080
  servlet:
    context-path: /api
```

2. 打包
```bash
mvn clean package -DskipTests
```

3. 启动服务
```bash
nohup java -jar admin-backend.jar --spring.profiles.active=prod > app.log 2>&1 &
```

### 4. Nginx配置

1. 修改nginx.conf
```nginx
http {
    upstream backend {
        server 127.0.0.1:8080;
    }

    server {
        listen 80;
        server_name your_domain.com;

        location /api/ {
            proxy_pass http://backend;
            proxy_set_header Host $host;
            proxy_set_header X-Real-IP $remote_addr;
            proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        }
    }
}
```

2. 重启Nginx
```bash
nginx -s reload
```

## 监控配置

### 1. JVM监控

1. 添加JVM参数
```bash
-Xms2g -Xmx2g -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/path/to/dump
```

2. 配置Prometheus和Grafana监控

### 2. 应用监控

1. 配置Spring Boot Actuator
```yaml
management:
  endpoints:
    web:
      exposure:
        include: health,info,metrics,prometheus
```

2. 配置日志收集(ELK)

## 备份策略

### 1. 数据库备份
```bash
# 创建备份脚本
#!/bin/bash
DATE=$(date +%Y%m%d)
mysqldump -u root -p chinese_culture > backup_$DATE.sql
```

### 2. Redis备份
```bash
# 配置自动备份
dir /path/to/backup
dbfilename dump.rdb
save 900 1
save 300 10
save 60 10000
```

## 常见问题

### 1. 内存溢出
- 检查JVM参数配置
- 分析堆内存快照
- 优化SQL查询

### 2. 连接超时
- 检查数据库连接池配置
- 检查网络连接
- 查看服务器负载

### 3. 性能问题
- 使用jstack分析线程状态
- 检查慢SQL日志
- 优化Redis缓存策略

## 运维命令

### 1. 服务管理
```bash
# 启动服务
./startup.sh

# 停止服务
./shutdown.sh

# 重启服务
./restart.sh

# 查看日志
tail -f app.log
```

### 2. 数据库管理
```bash
# 备份数据库
./backup_db.sh

# 恢复数据库
mysql -u root -p chinese_culture < backup.sql

# 清理历史数据
DELETE FROM t_horoscope WHERE create_time < DATE_SUB(NOW(), INTERVAL 30 DAY);
```

### 3. 缓存管理
```bash
# 清理Redis缓存
redis-cli -a your_password FLUSHDB

# 查看Redis信息
redis-cli -a your_password INFO
```

## 安全配置

### 1. 防火墙配置
```bash
# 开放必要端口
firewall-cmd --permanent --add-port=80/tcp
firewall-cmd --permanent --add-port=443/tcp
firewall-cmd --reload
```

### 2. SSL配置
```nginx
server {
    listen 443 ssl;
    server_name your_domain.com;

    ssl_certificate /path/to/cert.pem;
    ssl_certificate_key /path/to/key.pem;
    
    ssl_protocols TLSv1.2 TLSv1.3;
    ssl_ciphers HIGH:!aNULL:!MD5;
}
```

### 3. 安全加固
- 定期更新系统补丁
- 配置防SQL注入
- 启用HTTPS
- 配置跨域限制
- 实施密码策略

## 性能优化

### 1. JVM优化
- 调整垃圾回收器
- 优化内存分配
- 配置线程池

### 2. 数据库优化
- 添加必要索引
- 优化SQL语句
- 配置数据库参数

### 3. 缓存优化
- 合理设置缓存策略
- 优化缓存粒度
- 配置缓存过期时间 