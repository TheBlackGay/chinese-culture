'use client';

import { ConfigProvider } from 'antd';
import zhCN from 'antd/locale/zh_CN';
import BasicLayout from '@/components/layouts/BasicLayout';
import StyledComponentsRegistry from '@/lib/AntdRegistry';
import '@/styles/globals.css';

const theme = {
  token: {
    colorPrimary: '#1890ff',
    borderRadius: 4,
    colorBgContainer: '#ffffff',
    colorBgLayout: '#f0f2f5',
  },
};

export default function RootLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  return (
    <html lang="zh-CN">
      <body>
        <StyledComponentsRegistry>
          <ConfigProvider theme={theme} locale={zhCN}>
            <BasicLayout>{children}</BasicLayout>
          </ConfigProvider>
        </StyledComponentsRegistry>
      </body>
    </html>
  );
}
