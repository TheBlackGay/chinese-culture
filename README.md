# 中国传统文化数字化平台

## 项目介绍
本项目是一个致力于传播中国传统文化的数字化平台，包含后台管理系统和C端H5应用。通过现代化的技术手段，将传统文化（如八字、节气等）以数字化的形式呈现给用户。

## 项目结构
```
chinese-culture/
├── admin-web/          # 后台管理前端（Next.js）
├── admin-server/       # 后台管理服务端（Spring Boot）
├── docs/              # 项目文档
└── README.md          # 项目说明
```

## 技术栈

### 后台管理前端 (admin-web)
- Next.js 14.x
- TypeScript 5.x
- Ant Design Pro 5.x
- Redux Toolkit

### 后台管理服务端 (admin-server)
- Java 17
- Spring Boot 3.x
- MySQL 8.0
- Redis 7.x
- Spring Security

## 开发环境要求
- Node.js 18.x 或以上
- Java Development Kit (JDK) 17
- MySQL 8.0
- Redis 7.x
- Maven 3.8.x
- Git

## 本地开发说明

### 后台管理前端启动
```bash
cd admin-web
npm install
npm run dev
```

### 后台管理服务端启动
```bash
cd admin-server
mvn clean install
mvn spring-boot:run
```

## 部署说明
详细的部署文档请参考 `docs/deployment.md`

## 开发规范
1. Git 分支管理
   - main: 主分支，用于生产环境
   - develop: 开发分支
   - feature/*: 功能分支
   - hotfix/*: 紧急修复分支

2. 代码规范
   - 前端遵循 ESLint + Prettier 规范
   - 后端遵循 Alibaba Java Coding Guidelines

## 项目文档
更多详细文档请查看 `docs` 目录：
- 项目规划文档：`docs/project-plan.md`
- API文档：`docs/api-docs.md`
- 数据库设计：`docs/database-design.md`

## 贡献指南
请阅读 `CONTRIBUTING.md` 了解如何参与项目开发。

## 版权说明
本项目采用 MIT 协议开源，请自由地使用和分享。