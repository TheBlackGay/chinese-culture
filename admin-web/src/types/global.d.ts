/**
 * @file 全局类型声明
 */

declare global {
  // 环境变量类型
  namespace NodeJS {
    interface ProcessEnv {
      NODE_ENV: 'development' | 'production' | 'test';
      APP_ENV: string;
      NEXT_PUBLIC_API_BASE_URL: string;
      NEXT_PUBLIC_API_TIMEOUT: string;
      NEXT_PUBLIC_AUTH_TOKEN_KEY: string;
      NEXT_PUBLIC_AUTH_USER_KEY: string;
      NEXT_PUBLIC_APP_NAME: string;
      NEXT_PUBLIC_APP_DESCRIPTION: string;
      NEXT_PUBLIC_APP_VERSION: string;
      NEXT_PUBLIC_CDN_URL: string;
      NEXT_PUBLIC_LOG_LEVEL: string;
      NEXT_PUBLIC_CRYPTO_KEY: string;
    }
  }

  // Window 对象扩展
  interface Window {
    __REDUX_DEVTOOLS_EXTENSION__: any;
    __REDUX_DEVTOOLS_EXTENSION_COMPOSE__: any;
  }
}

// 通用类型
export type Nullable<T> = T | null;
export type Optional<T> = T | undefined;
export type Recordable<T = any> = Record<string, T>;
export type ReadonlyRecordable<T = any> = Readonly<Record<string, T>>;
export type DeepPartial<T> = {
  [P in keyof T]?: T[P] extends object ? DeepPartial<T[P]> : T[P];
};
export type TimeoutHandle = ReturnType<typeof setTimeout>;
export type IntervalHandle = ReturnType<typeof setInterval>;

// 样式相关
export type CSSProperties = React.CSSProperties;
export type HTMLAttributes<T = any> = React.HTMLAttributes<T>;

// 事件相关
export type ChangeEvent = React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>;
export type KeyboardEvent = React.KeyboardEvent<HTMLInputElement | HTMLTextAreaElement>;

// 函数相关
export type AnyFunction<T = any> = (...args: any[]) => T;
export type CustomEvent<T = any> = { target: { value: T } };
export type ComponentType = keyof JSX.IntrinsicElements | React.JSXElementConstructor<any>;

export {}; 