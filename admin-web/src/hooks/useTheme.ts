import { useEffect, useState } from 'react';
import { useAppDispatch } from '@/store';
import { setTheme } from '@/store/slices/globalSlice';
import ThemeUtil, { ThemeMode } from '@/utils/theme';

export function useTheme() {
  const dispatch = useAppDispatch();
  const [mode, setMode] = useState<ThemeMode>(ThemeUtil.getThemeMode());

  useEffect(() => {
    // 初始化主题
    ThemeUtil.initTheme();
    
    // 监听系统主题变化
    const cleanup = ThemeUtil.watchSystemTheme((newMode) => {
      setMode(newMode);
      dispatch(setTheme(newMode));
      ThemeUtil.setThemeMode(newMode);
    });

    return cleanup;
  }, [dispatch]);

  const toggleTheme = () => {
    const newMode = ThemeUtil.toggleThemeMode();
    setMode(newMode);
    dispatch(setTheme(newMode));
  };

  return {
    mode,
    toggleTheme,
  };
} 