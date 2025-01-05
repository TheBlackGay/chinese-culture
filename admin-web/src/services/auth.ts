/**
 * @file 认证服务
 * @description 处理用户认证相关的 API 请求
 */

import HttpClient from '@/utils/request';
import type { LoginParams, LoginResult } from '@/types/api';
import type { User } from '@/types/user';
import { STORAGE_KEYS } from '@/constants';
import Storage from '@/utils/storage';

/**
 * 用户登录
 * @param data 登录参数
 */
export async function login(data: LoginParams): Promise<LoginResult> {
  const result = await HttpClient.post<LoginResult>('/auth/login', data);
  // 保存登录信息
  if (result.token) {
    Storage.set(STORAGE_KEYS.TOKEN, result.token);
    Storage.set(STORAGE_KEYS.USER, result.user);
  }
  return result;
}

/**
 * 用户登出
 */
export async function logout(): Promise<void> {
  try {
    await HttpClient.post('/auth/logout');
  } finally {
    // 清除登录信息
    Storage.remove(STORAGE_KEYS.TOKEN);
    Storage.remove(STORAGE_KEYS.USER);
  }
}

/**
 * 获取当前用户信息
 */
export async function getCurrentUser(): Promise<User> {
  return HttpClient.get<User>('/auth/current-user');
}

/**
 * 修改密码
 * @param data 修改密码参数
 */
export async function changePassword(data: {
  oldPassword: string;
  newPassword: string;
}): Promise<void> {
  await HttpClient.post('/auth/change-password', data);
}

/**
 * 刷新访问令牌
 */
export async function refreshToken(): Promise<{ token: string }> {
  const result = await HttpClient.post<{ token: string }>('/auth/refresh-token');
  if (result.token) {
    Storage.set(STORAGE_KEYS.TOKEN, result.token);
  }
  return result;
}

/**
 * 发送验证码
 * @param email 邮箱地址
 */
export async function sendVerificationCode(email: string): Promise<void> {
  await HttpClient.post('/auth/send-code', { email });
}

/**
 * 重置密码
 * @param data 重置密码参数
 */
export async function resetPassword(data: {
  email: string;
  code: string;
  newPassword: string;
}): Promise<void> {
  await HttpClient.post('/auth/reset-password', data);
} 