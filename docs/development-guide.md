# 开发规范指南

## 目录
- [技术栈规范](#技术栈规范)
- [开发环境规范](#开发环境规范)
- [代码风格规范](#代码风格规范)
- [项目结构规范](#项目结构规范)
- [Git 工作流规范](#git-工作流规范)
- [文档规范](#文档规范)
- [安全规范](#安全规范)
- [性能规范](#性能规范)

## 技术栈规范

### 前端技术栈
- 框架：Next.js 14.x
- 语言：TypeScript 5.x
- UI 组件库：Ant Design 5.x
- 状态管理：Redux Toolkit
- 样式：SCSS + Tailwind CSS
- HTTP 客户端：Axios
- 工具库：
  - 日期处理：dayjs
  - 加密：crypto-js
  - 类型检查：TypeScript

### 依赖版本管理
- 使用 `package.json` 锁定主要依赖版本
- 使用 `package-lock.json` 锁定所有依赖版本
- 定期更新依赖，修复安全漏洞
- 新增依赖需要团队评审

## 开发环境规范

### 编辑器配置
- 使用 VS Code 作为主要 IDE
- 必要的 VS Code 插件：
  - ESLint
  - Prettier
  - TypeScript Vue Plugin
  - Tailwind CSS IntelliSense
  - GitLens

### 环境变量
- 开发环境：`.env.development`
- 生产环境：`.env.production`
- 测试环境：`.env.test`
- 禁止在代码中硬编码环境相关的配置

## 代码风格规范

### 通用规范
- 使用 ESLint + Prettier 进行代码格式化
- 使用 EditorConfig 统一编辑器配置
- 文件末尾保留一个空行
- 使用 2 个空格进行缩进
- 行尾不留空格
- 最大行宽：100 字符

### 命名规范
1. 文件命名：
   - 组件文件：PascalCase（如：`UserProfile.tsx`）
   - 工具文件：camelCase（如：`dateUtils.ts`）
   - 样式文件：kebab-case（如：`user-profile.scss`）
   - 类型文件：PascalCase（如：`UserTypes.ts`）
   - 测试文件：原文件名.test.ts（如：`utils.test.ts`）

2. 变量命名：
   - 普通变量：camelCase
   - 常量：UPPER_SNAKE_CASE
   - 私有变量：_camelCase
   - 类型/接口：PascalCase
   - 枚举：PascalCase

3. 函数命名：
   - 普通函数：camelCase
   - 组件函数：PascalCase
   - 事件处理函数：handleXxx
   - 获取数据函数：getXxx/fetchXxx
   - 更新数据函数：updateXxx
   - 删除数据函数：deleteXxx/removeXxx
   - 检查函数：isXxx/hasXxx/shouldXxx

### TypeScript 规范
- 启用严格模式（`strict: true`）
- 明确声明函数参数和返回值类型
- 避免使用 `any` 类型
- 使用 interface 代替 type（除非必须使用 type）
- 使用泛型增强代码复用性
- 导出类型定义时使用 `export type`

### React 组件规范
1. 组件结构：
   ```typescript
   import React from 'react';
   import type { FC } from 'react';
   import styles from './index.module.scss';

   interface Props {
     // props 类型定义
   }

   const ComponentName: FC<Props> = ({ prop1, prop2 }) => {
     // hooks 声明

     // 事件处理函数

     // 渲染函数或组件

     return (
       // JSX
     );
   };

   export default ComponentName;
   ```

2. 组件原则：
   - 单一职责
   - 组件尽量保持纯函数
   - 适当抽取复用逻辑到 hooks
   - 避免过深的组件嵌套
   - Props 必须定义类型
   - 必要的组件注释

### 样式规范
1. 样式文件组织：
   - 全局样式：`styles/globals.scss`
   - 变量：`styles/_variables.scss`
   - 混入：`styles/_mixins.scss`
   - 组件样式：`components/ComponentName/style.module.scss`

2. 样式命名：
   - 使用 BEM 命名规范
   - 使用语义化的类名
   - 避免过深的选择器嵌套（最多 3 层）

### 注释规范
1. 文件注释：
   ```typescript
   /**
    * @file 文件描述
    * @description 详细描述
    * @author 作者
    */
   ```

2. 函数注释：
   ```typescript
   /**
    * 函数描述
    * @param {string} param1 参数1描述
    * @param {number} param2 参数2描述
    * @returns {boolean} 返回值描述
    */
   ```

3. 组件注释：
   ```typescript
   /**
    * 组件描述
    * @component
    * @example
    * ```tsx
    * <ComponentName prop1="value1" prop2={value2} />
    * ```
    */
   ```

## 项目结构规范

```
src/
├── app/                # Next.js 应用目录
├── components/         # 公共组件
│   ├── common/        # 基础组件
│   └── business/      # 业务组件
├── hooks/             # 自定义 hooks
├── layouts/           # 布局组件
├── services/          # API 服务
├── store/             # 状态管理
│   ├── slices/        # Redux slices
│   └── index.ts       # Store 配置
├── styles/            # 全局样式
├── types/             # 类型定义
├── utils/             # 工具函数
└── constants/         # 常量定义
```

## Git 工作流规范

### 分支管理
- main：主分支，用于生产环境
- develop：开发分支
- feature/*：功能分支
- hotfix/*：紧急修复分支
- release/*：发布分支

### 提交规范
使用 Angular 提交规范：
- feat: 新功能
- fix: 修复
- docs: 文档更新
- style: 代码格式（不影响代码运行的变动）
- refactor: 重构（既不是新增功能，也不是修改 bug 的代码变动）
- perf: 性能优化
- test: 增加测试
- chore: 构建过程或辅助工具的变动
- revert: 回滚

提交示例：
```bash
feat(user): add user login function
fix(auth): fix token expired issue
docs(api): update API documentation
```

### Code Review 规范
- 提交 PR 前完成自测
- 每个 PR 至少需要一个审核人
- 代码审核重点关注：
  - 代码质量和规范
  - 业务逻辑正确性
  - 性能问题
  - 安全问题

## 安全规范

1. 身份认证：
   - 使用 JWT 进行身份验证
   - Token 存储在 localStorage，注意 XSS 防护
   - 敏感操作需要二次验证

2. 数据安全：
   - 敏感数据传输使用 HTTPS
   - 密码等敏感数据使用加密存储
   - 防止 SQL 注入和 XSS 攻击
   - 实现 CSRF 防护

3. 错误处理：
   - 生产环境不暴露具体错误信息
   - 统一的错误处理机制
   - 错误日志记录和监控

## 性能规范

1. 加载优化：
   - 路由懒加载
   - 组件按需加载
   - 图片懒加载
   - 合理的缓存策略

2. 渲染优化：
   - 避免不必要的重渲染
   - 使用 React.memo 优化函数组件
   - 使用 useMemo 和 useCallback 缓存值和函数
   - 合理使用 Context

3. 打包优化：
   - 代码分割
   - Tree Shaking
   - 压缩资源
   - 合理的 chunk 分割

## 测试规范

1. 单元测试：
   - 使用 Jest + React Testing Library
   - 重要的工具函数必须有单测
   - 核心组件必须有单测
   - 测试覆盖率要求：80%以上

2. E2E 测试：
   - 使用 Cypress
   - 覆盖核心业务流程
   - CI/CD 中必须包含 E2E 测试

3. 测试原则：
   - 测试代码同样需要 Code Review
   - 测试用例必须有清晰的描述
   - 一个测试用例只测试一个功能点
   - 避免测试实现细节 