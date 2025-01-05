/**
 * @file API 接口类型定义
 */

import type { User } from './user';

// 通用响应格式
export interface ApiResponse<T = any> {
  code: number;
  message: string;
  data: T;
}

// 分页请求参数
export interface PaginationQuery {
  page: number;
  pageSize: number;
  sortField?: string;
  sortOrder?: 'ascend' | 'descend';
  [key: string]: any;
}

// 分页响应数据
export interface PaginationResponse<T> {
  list: T[];
  total: number;
  page: number;
  pageSize: number;
}

// 登录请求参数
export interface LoginParams {
  username: string;
  password: string;
  remember?: boolean;
}

// 登录响应数据
export interface LoginResult {
  token: string;
  user: User;
}

// 修改密码参数
export interface ChangePasswordParams {
  oldPassword: string;
  newPassword: string;
  confirmPassword: string;
}

// 用户查询参数
export interface UserQueryParams extends PaginationQuery {
  username?: string;
  email?: string;
  role?: string;
  status?: number;
} 