#!/bin/bash

# 创建 buildx 构建器
docker buildx create --name mybuilder --use

# 启动构建器
docker buildx inspect mybuilder --bootstrap

# 构建并推送多架构镜像
docker buildx build \
  --platform linux/amd64,linux/arm64 \
  -t 1047028213/chinese-culture:latest \
  --push \
  . 