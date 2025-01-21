#!/bin/bash

# 设置Java运行环境
export JAVA_HOME=${JAVA_HOME:-/usr/local/java}
export PATH=$JAVA_HOME/bin:$PATH

# 项目根目录
APP_HOME="$(cd "$(dirname "$0")"/../ && pwd)"
# 项目名称
APP_NAME="chinese-culture-admin"
# 配置文件
SPRING_CONFIG="--spring.config.location=$APP_HOME/config/application.yml"
# JVM参数
JAVA_OPTS="-Xms512m -Xmx512m -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=256m"
# 开发环境配置
SPRING_PROFILES="--spring.profiles.active=dev"

# 获取项目进程ID
get_pid() {
    echo $(ps -ef | grep "$APP_NAME" | grep -v grep | awk '{print $2}')
}

# 启动服务
start() {
    pid=$(get_pid)
    if [ -n "$pid" ]; then
        echo "服务已经运行，进程ID: $pid"
        return 0
    fi
    
    echo "启动服务..."
    cd $APP_HOME
    nohup mvn spring-boot:run \
        -Dspring-boot.run.jvmArguments="$JAVA_OPTS" \
        -Dspring-boot.run.arguments="$SPRING_CONFIG $SPRING_PROFILES" \
        > /dev/null 2>&1 &
        
    sleep 2
    pid=$(get_pid)
    if [ -n "$pid" ]; then
        echo "服务启动成功，进程ID: $pid"
    else
        echo "服务启动失败，请检查日志"
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
    sleep 2
    
    pid=$(get_pid)
    if [ -n "$pid" ]; then
        echo "服务未能正常停止，强制终止"
        kill -9 $pid
    fi
    echo "服务已停止"
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
    else
        echo "服务未运行"
    fi
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
    *)
        echo "用法: $0 {start|stop|restart|status}"
        exit 1
        ;;
esac

exit 0 