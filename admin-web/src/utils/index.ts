import dayjs from 'dayjs';
import type { User } from '@/types';

/**
 * 格式化日期
 * @param date 日期
 * @param format 格式
 */
export const formatDate = (date: string | Date, format = 'YYYY-MM-DD HH:mm:ss') => {
  return dayjs(date).format(format);
};

/**
 * 获取本地存储的用户信息
 */
export const getStoredUser = (): User | null => {
  try {
    const userStr = localStorage.getItem(process.env.NEXT_PUBLIC_AUTH_USER_KEY || 'auth_user');
    return userStr ? JSON.parse(userStr) : null;
  } catch (error) {
    return null;
  }
};

/**
 * 获取本地存储的 token
 */
export const getStoredToken = (): string | null => {
  return localStorage.getItem(process.env.NEXT_PUBLIC_AUTH_TOKEN_KEY || 'auth_token');
};

/**
 * 清除登录信息
 */
export const clearLoginInfo = () => {
  localStorage.removeItem(process.env.NEXT_PUBLIC_AUTH_TOKEN_KEY || 'auth_token');
  localStorage.removeItem(process.env.NEXT_PUBLIC_AUTH_USER_KEY || 'auth_user');
};

/**
 * 检查用户是否有权限
 * @param userPermissions 用户权限列表
 * @param requiredPermissions 需要的权限列表
 */
export const hasPermission = (
  userPermissions: string[] = [],
  requiredPermissions: string[] = []
): boolean => {
  if (!requiredPermissions.length) return true;
  return requiredPermissions.some(permission => userPermissions.includes(permission));
};

/**
 * 防抖函数
 * @param fn 需要防抖的函数
 * @param delay 延迟时间
 */
export const debounce = <T extends (...args: any[]) => any>(fn: T, delay: number) => {
  let timer: NodeJS.Timeout;
  return function (this: ThisParameterType<T>, ...args: Parameters<T>) {
    clearTimeout(timer);
    timer = setTimeout(() => fn.apply(this, args), delay);
  };
};

/**
 * 节流函数
 * @param fn 需要节流的函数
 * @param delay 延迟时间
 */
export const throttle = <T extends (...args: any[]) => any>(fn: T, delay: number) => {
  let last = 0;
  return function (this: ThisParameterType<T>, ...args: Parameters<T>) {
    const now = Date.now();
    if (now - last >= delay) {
      fn.apply(this, args);
      last = now;
    }
  };
}; 