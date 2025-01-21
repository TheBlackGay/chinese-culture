#!/bin/bash

# 设置Java运行环境
export JAVA_HOME=${JAVA_HOME:-/usr/local/java}
export PATH=$JAVA_HOME/bin:$PATH

# 项目根目录
APP_HOME="$(cd "$(dirname "$0")"/../ && pwd)"
# 项目名称
APP_NAME="chinese-culture-admin"
# JAR包路径
JAR_PATH="$APP_HOME/target/$APP_NAME.jar"
# 日志路径
LOG_PATH="$APP_HOME/logs"
# 配置文件
SPRING_CONFIG="--spring.config.location=$APP_HOME/config/application.yml"
# JVM参数
JAVA_OPTS="-Xms1024m -Xmx1024m -XX:MetaspaceSize=256m -XX:MaxMetaspaceSize=512m"
JAVA_OPTS="$JAVA_OPTS -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=$LOG_PATH"
JAVA_OPTS="$JAVA_OPTS -XX:+UseG1GC -XX:MaxGCPauseMillis=200"
# 生产环境配置
SPRING_PROFILES="--spring.profiles.active=prod"

# 获取项目进程ID
get_pid() {
    echo $(ps -ef | grep "$APP_NAME" | grep -v grep | awk '{print $2}')
}

# 创建必要的目录
make_dirs() {
    if [ ! -d "$LOG_PATH" ]; then
        mkdir -p "$LOG_PATH"
    fi
}

# 检查JAR包是否存在
check_jar() {
    if [ ! -f "$JAR_PATH" ]; then
        echo "错误: JAR包不存在 ($JAR_PATH)"
        exit 1
    fi
}

# 启动服务
start() {
    pid=$(get_pid)
    if [ -n "$pid" ]; then
        echo "服务已经运行，进程ID: $pid"
        return 0
    fi
    
    make_dirs
    check_jar
    
    echo "启动服务..."
    nohup java $JAVA_OPTS -jar $JAR_PATH $SPRING_CONFIG $SPRING_PROFILES \
        > "$LOG_PATH/startup.log" 2>&1 &
        
    sleep 5
    pid=$(get_pid)
    if [ -n "$pid" ]; then
        echo "服务启动成功，进程ID: $pid"
        tail -n 50 "$LOG_PATH/startup.log"
    else
        echo "服务启动失败，请检查日志"
        tail -n 50 "$LOG_PATH/startup.log"
        exit 1
    fi
}

# 停止服务
stop() {
    pid=$(get_pid)
    if [ -z "$pid" ]; then
        echo "服务未运行"
        return 0
    fi
    
    echo "停止服务，进程ID: $pid"
    kill -15 $pid
    
    # 等待最多30秒
    for i in {1..30}; do
        sleep 1
        pid=$(get_pid)
        if [ -z "$pid" ]; then
            echo "服务已停止"
            return 0
        fi
    done
    
    # 如果还未停止，强制终止
    pid=$(get_pid)
    if [ -n "$pid" ]; then
        echo "服务未能正常停止，强制终止"
        kill -9 $pid
    fi
}

# 重启服务
restart() {
    stop
    sleep 2
    start
}

# 查看服务状态
status() {
    pid=$(get_pid)
    if [ -n "$pid" ]; then
        echo "服务正在运行，进程ID: $pid"
        # 显示内存使用情况
        echo "内存使用情况:"
        jmap -heap $pid 2>/dev/null | grep -A 5 "Heap Usage:"
    else
        echo "服务未运行"
    fi
}

# 查看日志
logs() {
    if [ ! -f "$LOG_PATH/startup.log" ]; then
        echo "日志文件不存在"
        return 1
    fi
    tail -f "$LOG_PATH/startup.log"
}

# 根据输入参数执行相应操作
case "$1" in
    start)
        start
        ;;
    stop)
        stop
        ;;
    restart)
        restart
        ;;
    status)
        status
        ;;
    logs)
        logs
        ;;
    *)
        echo "用法: $0 {start|stop|restart|status|logs}"
        exit 1
        ;;
esac

exit 0 