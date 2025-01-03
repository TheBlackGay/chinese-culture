'use client';

import { useState, useEffect } from 'react';

interface PreloadOptions {
  key: string;
  fetchFn: () => Promise<any>;
  ttl?: number; // 缓存时间，单位：毫秒
}

const cache = new Map<string, { data: any; timestamp: number }>();

export function usePreloadData<T>({ key, fetchFn, ttl = 5 * 60 * 1000 }: PreloadOptions) {
  const [data, setData] = useState<T | null>(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<Error | null>(null);

  useEffect(() => {
    const loadData = async () => {
      try {
        setLoading(true);
        setError(null);

        // 检查缓存
        const cached = cache.get(key);
        if (cached && Date.now() - cached.timestamp < ttl) {
          setData(cached.data);
          return;
        }

        // 获取新数据
        const newData = await fetchFn();
        
        // 更新缓存
        cache.set(key, {
          data: newData,
          timestamp: Date.now(),
        });

        setData(newData);
      } catch (err) {
        setError(err instanceof Error ? err : new Error('加载数据失败'));
      } finally {
        setLoading(false);
      }
    };

    loadData();
  }, [key, fetchFn, ttl]);

  // 提供手动刷新方法
  const refresh = async () => {
    try {
      setLoading(true);
      setError(null);

      const newData = await fetchFn();
      
      cache.set(key, {
        data: newData,
        timestamp: Date.now(),
      });

      setData(newData);
    } catch (err) {
      setError(err instanceof Error ? err : new Error('刷新数据失败'));
    } finally {
      setLoading(false);
    }
  };

  return { data, loading, error, refresh };
} 