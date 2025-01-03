/**
 * @file 通用工具函数
 */

import type { ReactNode } from 'react';
import CryptoJS from 'crypto-js';

/**
 * 判断是否为空值
 * @param value 要判断的值
 */
export function isEmpty(value: unknown): boolean {
  return (
    value === undefined ||
    value === null ||
    value === '' ||
    (Array.isArray(value) && value.length === 0) ||
    (typeof value === 'object' && Object.keys(value).length === 0)
  );
}

/**
 * 深拷贝
 * @param obj 要拷贝的对象
 */
export function deepClone<T>(obj: T): T {
  if (obj === null || typeof obj !== 'object') {
    return obj;
  }

  if (obj instanceof Date) {
    return new Date(obj.getTime()) as unknown as T;
  }

  if (obj instanceof RegExp) {
    return new RegExp(obj) as unknown as T;
  }

  if (Array.isArray(obj)) {
    return obj.map(item => deepClone(item)) as unknown as T;
  }

  const cloned = {} as T;
  Object.keys(obj as object).forEach(key => {
    cloned[key as keyof T] = deepClone((obj as any)[key]);
  });

  return cloned;
}

/**
 * 格式化文件大小
 * @param bytes 字节数
 */
export function formatFileSize(bytes: number): string {
  if (bytes === 0) return '0 B';
  const k = 1024;
  const sizes = ['B', 'KB', 'MB', 'GB', 'TB'];
  const i = Math.floor(Math.log(bytes) / Math.log(k));
  return `${parseFloat((bytes / Math.pow(k, i)).toFixed(2))} ${sizes[i]}`;
}

/**
 * 加密数据
 * @param data 要加密的数据
 */
export function encrypt(data: string): string {
  const key = process.env.NEXT_PUBLIC_CRYPTO_KEY || 'default_key';
  return CryptoJS.AES.encrypt(data, key).toString();
}

/**
 * 解密数据
 * @param encrypted 已加密的数据
 */
export function decrypt(encrypted: string): string {
  const key = process.env.NEXT_PUBLIC_CRYPTO_KEY || 'default_key';
  const bytes = CryptoJS.AES.decrypt(encrypted, key);
  return bytes.toString(CryptoJS.enc.Utf8);
}

/**
 * 防抖函数
 * @param fn 要执行的函数
 * @param delay 延迟时间（毫秒）
 */
export function debounce<T extends (...args: any[]) => any>(
  fn: T,
  delay: number
): (...args: Parameters<T>) => void {
  let timer: NodeJS.Timeout;
  return function (...args: Parameters<T>) {
    clearTimeout(timer);
    timer = setTimeout(() => fn(...args), delay);
  };
}

/**
 * 节流函数
 * @param fn 要执行的函数
 * @param limit 限制时间（毫秒）
 */
export function throttle<T extends (...args: any[]) => any>(
  fn: T,
  limit: number
): (...args: Parameters<T>) => void {
  let inThrottle: boolean;
  return function (...args: Parameters<T>) {
    if (!inThrottle) {
      fn(...args);
      inThrottle = true;
      setTimeout(() => (inThrottle = false), limit);
    }
  };
}

/**
 * 获取树形数据的所有叶子节点
 * @param tree 树形数据
 * @param childrenKey 子节点键名
 */
export function getLeafNodes<T extends Record<string, any>>(
  tree: T[],
  childrenKey = 'children'
): T[] {
  const result: T[] = [];
  const traverse = (nodes: T[]) => {
    nodes.forEach(node => {
      if (!node[childrenKey] || node[childrenKey].length === 0) {
        result.push(node);
      } else {
        traverse(node[childrenKey]);
      }
    });
  };
  traverse(tree);
  return result;
}

/**
 * 将数组转换为树形结构
 * @param array 数组数据
 * @param options 配置项
 */
export function arrayToTree<T extends Record<string, any>>(
  array: T[],
  options: {
    id?: string;
    parentId?: string;
    children?: string;
  } = {}
): T[] {
  const { id = 'id', parentId = 'parentId', children = 'children' } = options;
  const result: T[] = [];
  const map = new Map<string | number, T>();

  // 构建节点映射
  array.forEach(item => {
    map.set(item[id], { ...item, [children]: [] });
  });

  // 构建树形结构
  array.forEach(item => {
    const node = map.get(item[id]);
    if (node) {
      if (item[parentId] && map.has(item[parentId])) {
        const parent = map.get(item[parentId]);
        parent && parent[children].push(node);
      } else {
        result.push(node);
      }
    }
  });

  return result;
}

/**
 * 从对象中排除指定的键
 * @param obj 源对象
 * @param keys 要排除的键
 */
export function omit<T extends object, K extends keyof T>(
  obj: T,
  keys: K[]
): Omit<T, K> {
  const result = { ...obj };
  keys.forEach(key => delete result[key]);
  return result;
}

/**
 * 从对象中选择指定的键
 * @param obj 源对象
 * @param keys 要选择的键
 */
export function pick<T extends object, K extends keyof T>(
  obj: T,
  keys: K[]
): Pick<T, K> {
  const result = {} as Pick<T, K>;
  keys.forEach(key => {
    if (key in obj) {
      result[key] = obj[key];
    }
  });
  return result;
}

/**
 * 检查是否为外部链接
 * @param path 路径
 */
export function isExternalLink(path: string): boolean {
  return /^(https?:|mailto:|tel:)/.test(path);
}

/**
 * 安全地获取嵌套对象的值
 * @param obj 对象
 * @param path 路径
 * @param defaultValue 默认值
 */
export function get(obj: any, path: string, defaultValue?: any): any {
  const travel = (regexp: RegExp) =>
    String.prototype.split
      .call(path, regexp)
      .filter(Boolean)
      .reduce((res, key) => (res !== null && res !== undefined ? res[key] : res), obj);
  const result = travel(/[,[\]]+?/) || travel(/[,[\].]+?/);
  return result === undefined || result === obj ? defaultValue : result;
}

/**
 * 检查是否支持某个 CSS 属性
 * @param property CSS 属性
 */
export function supportCSSProperty(property: string): boolean {
  return typeof document !== 'undefined'
    ? property in document.body.style
    : false;
}

/**
 * 转义 HTML 字符
 * @param str 要转义的字符串
 */
export function escapeHTML(str: string): string {
  const div = document.createElement('div');
  div.textContent = str;
  return div.innerHTML;
}

/**
 * 生成指定范围的随机数
 * @param min 最小值
 * @param max 最大值
 */
export function random(min: number, max: number): number {
  return Math.floor(Math.random() * (max - min + 1) + min);
}

/**
 * 检查是否为移动设备
 */
export function isMobile(): boolean {
  return typeof window !== 'undefined'
    ? /Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(
        window.navigator.userAgent
      )
    : false;
} 