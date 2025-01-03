/**
 * @file 通用 hooks
 */

import { useState, useEffect, useCallback, useRef } from 'react';
import type { DependencyList } from 'react';
import { debounce, throttle } from '@/utils/common';

/**
 * 异步数据加载 hook
 * @param asyncFn 异步函数
 * @param deps 依赖数组
 */
export function useAsync<T>(
  asyncFn: () => Promise<T>,
  deps: DependencyList = []
) {
  const [data, setData] = useState<T | null>(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<Error | null>(null);

  useEffect(() => {
    setLoading(true);
    setError(null);
    asyncFn()
      .then(result => {
        setData(result);
        setLoading(false);
      })
      .catch(err => {
        setError(err);
        setLoading(false);
      });
  }, deps);

  return { data, loading, error };
}

/**
 * 防抖 hook
 * @param fn 要执行的函数
 * @param delay 延迟时间
 * @param deps 依赖数组
 */
export function useDebounce<T extends (...args: any[]) => any>(
  fn: T,
  delay: number,
  deps: DependencyList = []
): T {
  const { current } = useRef({ fn, timer: null });
  useEffect(() => {
    current.fn = fn;
  }, [fn]);

  return useCallback(
    debounce((...args) => current.fn(...args), delay),
    deps
  ) as T;
}

/**
 * 节流 hook
 * @param fn 要执行的函数
 * @param delay 延迟时间
 * @param deps 依赖数组
 */
export function useThrottle<T extends (...args: any[]) => any>(
  fn: T,
  delay: number,
  deps: DependencyList = []
): T {
  const { current } = useRef({ fn, timer: null });
  useEffect(() => {
    current.fn = fn;
  }, [fn]);

  return useCallback(
    throttle((...args) => current.fn(...args), delay),
    deps
  ) as T;
}

/**
 * 上一个值的 hook
 * @param value 当前值
 */
export function usePrevious<T>(value: T): T | undefined {
  const ref = useRef<T>();
  useEffect(() => {
    ref.current = value;
  });
  return ref.current;
}

/**
 * 组件挂载状态 hook
 */
export function useIsMounted(): () => boolean {
  const isMounted = useRef(false);
  useEffect(() => {
    isMounted.current = true;
    return () => {
      isMounted.current = false;
    };
  }, []);
  return useCallback(() => isMounted.current, []);
}

/**
 * 强制更新 hook
 */
export function useForceUpdate(): () => void {
  const [, setState] = useState({});
  return useCallback(() => setState({}), []);
}

/**
 * 本地存储 hook
 * @param key 存储键
 * @param initialValue 初始值
 */
export function useLocalStorage<T>(key: string, initialValue: T) {
  const [storedValue, setStoredValue] = useState<T>(() => {
    try {
      const item = window.localStorage.getItem(key);
      return item ? JSON.parse(item) : initialValue;
    } catch (error) {
      console.error(error);
      return initialValue;
    }
  });

  const setValue = (value: T | ((val: T) => T)) => {
    try {
      const valueToStore = value instanceof Function ? value(storedValue) : value;
      setStoredValue(valueToStore);
      window.localStorage.setItem(key, JSON.stringify(valueToStore));
    } catch (error) {
      console.error(error);
    }
  };

  return [storedValue, setValue] as const;
}

/**
 * 会话存储 hook
 * @param key 存储键
 * @param initialValue 初始值
 */
export function useSessionStorage<T>(key: string, initialValue: T) {
  const [storedValue, setStoredValue] = useState<T>(() => {
    try {
      const item = window.sessionStorage.getItem(key);
      return item ? JSON.parse(item) : initialValue;
    } catch (error) {
      console.error(error);
      return initialValue;
    }
  });

  const setValue = (value: T | ((val: T) => T)) => {
    try {
      const valueToStore = value instanceof Function ? value(storedValue) : value;
      setStoredValue(valueToStore);
      window.sessionStorage.setItem(key, JSON.stringify(valueToStore));
    } catch (error) {
      console.error(error);
    }
  };

  return [storedValue, setValue] as const;
}

/**
 * 媒体查询 hook
 * @param query 媒体查询字符串
 */
export function useMediaQuery(query: string): boolean {
  const [matches, setMatches] = useState(false);

  useEffect(() => {
    const mediaQuery = window.matchMedia(query);
    setMatches(mediaQuery.matches);

    const handler = (event: MediaQueryListEvent) => {
      setMatches(event.matches);
    };

    mediaQuery.addEventListener('change', handler);
    return () => mediaQuery.removeEventListener('change', handler);
  }, [query]);

  return matches;
}

/**
 * 网络状态 hook
 */
export function useNetwork() {
  const [isOnline, setIsOnline] = useState(true);
  const [networkType, setNetworkType] = useState<string>('');

  useEffect(() => {
    const updateNetwork = () => {
      setIsOnline(navigator.onLine);
      setNetworkType((navigator as any).connection?.effectiveType || '');
    };

    updateNetwork();
    window.addEventListener('online', updateNetwork);
    window.addEventListener('offline', updateNetwork);
    (navigator as any).connection?.addEventListener('change', updateNetwork);

    return () => {
      window.removeEventListener('online', updateNetwork);
      window.removeEventListener('offline', updateNetwork);
      (navigator as any).connection?.removeEventListener('change', updateNetwork);
    };
  }, []);

  return { isOnline, networkType };
}

/**
 * 页面可见性 hook
 */
export function usePageVisibility() {
  const [isVisible, setIsVisible] = useState(!document.hidden);

  useEffect(() => {
    const handleVisibilityChange = () => {
      setIsVisible(!document.hidden);
    };

    document.addEventListener('visibilitychange', handleVisibilityChange);
    return () => {
      document.removeEventListener('visibilitychange', handleVisibilityChange);
    };
  }, []);

  return isVisible;
}

/**
 * 剪贴板 hook
 */
export function useClipboard() {
  const [copied, setCopied] = useState(false);

  const copy = useCallback(async (text: string) => {
    try {
      await navigator.clipboard.writeText(text);
      setCopied(true);
      setTimeout(() => setCopied(false), 2000);
      return true;
    } catch (error) {
      console.error('Failed to copy:', error);
      return false;
    }
  }, []);

  return { copied, copy };
} 