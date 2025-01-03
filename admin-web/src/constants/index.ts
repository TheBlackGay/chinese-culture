/**
 * @file 系统常量定义
 */

// HTTP 状态码
export const HTTP_STATUS = {
  OK: 200,
  CREATED: 201,
  ACCEPTED: 202,
  NO_CONTENT: 204,
  BAD_REQUEST: 400,
  UNAUTHORIZED: 401,
  FORBIDDEN: 403,
  NOT_FOUND: 404,
  METHOD_NOT_ALLOWED: 405,
  INTERNAL_ERROR: 500,
  SERVICE_UNAVAILABLE: 503,
} as const;

// 业务状态码
export const BUSINESS_CODE = {
  SUCCESS: 0,
  ERROR: -1,
  TOKEN_EXPIRED: 401001,
  NO_PERMISSION: 403001,
  USER_NOT_FOUND: 404001,
  PARAMS_ERROR: 400001,
  SYSTEM_ERROR: 500001,
} as const;

// 本地存储键名
export const STORAGE_KEYS = {
  TOKEN: 'token',
  USER: 'user',
  THEME: 'theme',
  LOCALE: 'locale',
  REMEMBER_ME: 'remember_me',
} as const;

// 主题配置
export const THEME = {
  LIGHT: 'light',
  DARK: 'dark',
  SYSTEM: 'system',
} as const;

// 语言配置
export const LOCALE = {
  ZH_CN: 'zh-CN',
  EN_US: 'en-US',
} as const;

// 分页配置
export const PAGINATION = {
  DEFAULT_CURRENT: 1,
  DEFAULT_PAGE_SIZE: 10,
  PAGE_SIZE_OPTIONS: ['10', '20', '50', '100'],
} as const;

// 日期格式
export const DATE_FORMAT = {
  DATE: 'YYYY-MM-DD',
  TIME: 'HH:mm:ss',
  DATETIME: 'YYYY-MM-DD HH:mm:ss',
  DATETIME_MIN: 'YYYY-MM-DD HH:mm',
} as const;

// 文件相关
export const FILE = {
  // 图片类型
  IMAGE_TYPES: ['.jpg', '.jpeg', '.png', '.gif', '.bmp', '.webp'],
  // 最大图片大小（5MB）
  MAX_IMAGE_SIZE: 5 * 1024 * 1024,
  // 文档类型
  DOC_TYPES: ['.doc', '.docx', '.xls', '.xlsx', '.pdf', '.txt'],
  // 最大文档大小（10MB）
  MAX_DOC_SIZE: 10 * 1024 * 1024,
} as const;

// 正则表达式
export const REGEXP = {
  // 手机号
  PHONE: /^1[3-9]\d{9}$/,
  // 邮箱
  EMAIL: /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/,
  // 密码（至少8位，包含数字和字母）
  PASSWORD: /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$/,
  // URL
  URL: /^(https?:\/\/)?([\da-z.-]+)\.([a-z.]{2,6})([/\w .-]*)*\/?$/,
} as const; 