/**
 * @file API 相关类型定义
 */

// API 响应基础类型
export interface ApiResponse<T = any> {
  code: number;
  message: string;
  data: T;
}

// 分页请求参数
export interface PaginationParams {
  current: number;
  pageSize: number;
}

// 分页响应数据
export interface PaginationResult<T> {
  list: T[];
  total: number;
  current: number;
  pageSize: number;
}

// 登录参数
export interface LoginParams {
  username: string;
  password: string;
  remember?: boolean;
}

// 登录结果
export interface LoginResult {
  token: string;
  user: User;
}

// 用户信息
export interface User {
  id: number;
  username: string;
  nickname: string;
  avatar?: string;
  email?: string;
  phone?: string;
  roles: string[];
  permissions: string[];
  createTime: string;
  updateTime: string;
}

// 文件上传结果
export interface UploadResult {
  url: string;
  name: string;
  size: number;
  type: string;
} 