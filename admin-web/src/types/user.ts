/**
 * @file 用户相关类型定义
 */

// 用户基本信息
export interface User {
  id: number;
  username: string;
  email: string;
  nickname?: string;
  avatar?: string;
  role: string;
  permissions: string[];
  status: number;
  lastLoginTime?: string;
  createdAt: string;
  updatedAt: string;
}

// 用户状态
export enum UserStatus {
  DISABLED = 0,
  ENABLED = 1,
  LOCKED = 2,
}

// 用户角色
export enum UserRole {
  ADMIN = 'admin',
  EDITOR = 'editor',
  VIEWER = 'viewer',
}

// 用户权限
export interface Permission {
  id: number;
  code: string;
  name: string;
  description?: string;
}

// 用户设置
export interface UserSettings {
  theme: 'light' | 'dark';
  language: string;
  notifications: boolean;
} 