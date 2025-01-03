'use client';

import { ConfigProvider } from 'antd';
import zhCN from 'antd/locale/zh_CN';
import ErrorBoundary from '@/components/common/ErrorBoundary';
import BasicLayout from './BasicLayout';
import { usePathname } from 'next/navigation';

interface ClientLayoutProps {
  children: React.ReactNode;
}

// 不需要布局的路径列表
const noLayoutPaths = ['/login'];

export default function ClientLayout({ children }: ClientLayoutProps) {
  const pathname = usePathname();
  const needLayout = !noLayoutPaths.includes(pathname);

  return (
    <ErrorBoundary>
      <ConfigProvider
        locale={zhCN}
        theme={{
          token: {
            colorPrimary: '#1677ff',
            borderRadius: 4,
          },
        }}
      >
        {needLayout ? <BasicLayout>{children}</BasicLayout> : children}
      </ConfigProvider>
    </ErrorBoundary>
  );
} 