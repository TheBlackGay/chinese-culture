/**
 * @file 本地存储工具
 */

class Storage {
  /**
   * 设置localStorage
   * @param key 键
   * @param value 值
   */
  static set(key: string, value: any): void {
    if (typeof value === 'object') {
      localStorage.setItem(key, JSON.stringify(value));
    } else {
      localStorage.setItem(key, value);
    }
  }

  /**
   * 获取localStorage
   * @param key 键
   * @param defaultValue 默认值
   */
  static get<T = any>(key: string, defaultValue?: T): T | null {
    const value = localStorage.getItem(key);
    if (value === null) return defaultValue ?? null;
    try {
      return JSON.parse(value);
    } catch {
      return value as unknown as T;
    }
  }

  /**
   * 移除localStorage
   * @param key 键
   */
  static remove(key: string): void {
    localStorage.removeItem(key);
  }

  /**
   * 清空localStorage
   */
  static clear(): void {
    localStorage.clear();
  }

  /**
   * 设置sessionStorage
   * @param key 键
   * @param value 值
   */
  static setSession(key: string, value: any): void {
    if (typeof value === 'object') {
      sessionStorage.setItem(key, JSON.stringify(value));
    } else {
      sessionStorage.setItem(key, value);
    }
  }

  /**
   * 获取sessionStorage
   * @param key 键
   * @param defaultValue 默认值
   */
  static getSession<T = any>(key: string, defaultValue?: T): T | null {
    const value = sessionStorage.getItem(key);
    if (value === null) return defaultValue ?? null;
    try {
      return JSON.parse(value);
    } catch {
      return value as unknown as T;
    }
  }

  /**
   * 移除sessionStorage
   * @param key 键
   */
  static removeSession(key: string): void {
    sessionStorage.removeItem(key);
  }

  /**
   * 清空sessionStorage
   */
  static clearSession(): void {
    sessionStorage.clear();
  }
}

export default Storage; 