/**
 * @file 环境变量类型定义
 */

declare namespace NodeJS {
  interface ProcessEnv {
    // 环境标识
    NODE_ENV: 'development' | 'production' | 'test';
    APP_ENV: string;

    // API 配置
    NEXT_PUBLIC_API_BASE_URL: string;
    NEXT_PUBLIC_API_TIMEOUT: string;

    // 认证相关
    NEXT_PUBLIC_AUTH_TOKEN_KEY: string;
    NEXT_PUBLIC_AUTH_USER_KEY: string;

    // 应用信息
    NEXT_PUBLIC_APP_NAME: string;
    NEXT_PUBLIC_APP_DESCRIPTION: string;
    NEXT_PUBLIC_APP_VERSION: string;

    // 资源配置
    NEXT_PUBLIC_CDN_URL: string;

    // 其他配置
    NEXT_PUBLIC_LOG_LEVEL: string;
    NEXT_PUBLIC_CRYPTO_KEY: string;
  }
} 