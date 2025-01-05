/**
 * @file 主题工具
 */

import { STORAGE_KEYS } from '@/constants';
import Storage from './storage';

export type ThemeMode = 'light' | 'dark';

class ThemeUtil {
  /**
   * 获取当前主题模式
   */
  static getThemeMode(): ThemeMode {
    return Storage.get<ThemeMode>(STORAGE_KEYS.THEME) ?? 'light';
  }

  /**
   * 设置主题模式
   * @param mode 主题模式
   */
  static setThemeMode(mode: ThemeMode): void {
    Storage.set(STORAGE_KEYS.THEME, mode);
    document.documentElement.setAttribute('data-theme', mode);
    
    // 设置 antd 主题
    const root = document.documentElement;
    if (mode === 'dark') {
      root.style.colorScheme = 'dark';
    } else {
      root.style.colorScheme = 'light';
    }
  }

  /**
   * 切换主题模式
   */
  static toggleThemeMode(): ThemeMode {
    const currentMode = this.getThemeMode();
    const newMode = currentMode === 'light' ? 'dark' : 'light';
    this.setThemeMode(newMode);
    return newMode;
  }

  /**
   * 初始化主题
   */
  static initTheme(): void {
    const mode = this.getThemeMode();
    this.setThemeMode(mode);
  }

  /**
   * 监听系统主题变化
   * @param callback 回调函数
   */
  static watchSystemTheme(callback: (mode: ThemeMode) => void): () => void {
    const mediaQuery = window.matchMedia('(prefers-color-scheme: dark)');
    
    const handleChange = (e: MediaQueryListEvent) => {
      const mode: ThemeMode = e.matches ? 'dark' : 'light';
      callback(mode);
    };

    mediaQuery.addEventListener('change', handleChange);
    
    // 返回清理函数
    return () => mediaQuery.removeEventListener('change', handleChange);
  }
}

export default ThemeUtil; 