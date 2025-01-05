'use client';

import React from 'react';
import { usePathname } from 'next/navigation';
import BasicLayout from './BasicLayout';

// 不需要布局的路由路径
const noLayoutPaths = ['/login', '/register', '/404', '/500'];

interface LayoutProviderProps {
  children: React.ReactNode;
}

const LayoutProvider: React.FC<LayoutProviderProps> = ({ children }) => {
  const pathname = usePathname();

  // 检查当前路径是否需要布局
  const needLayout = !noLayoutPaths.includes(pathname);

  if (!needLayout) {
    return <>{children}</>;
  }

  return <BasicLayout>{children}</BasicLayout>;
};

export default LayoutProvider; 