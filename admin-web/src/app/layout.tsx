'use client';

import React from 'react';
import StyledComponentsRegistry from '@/lib/AntdRegistry';
import BasicLayout from '@/components/layouts/BasicLayout';
import '@/styles/antd.css';
import '@/styles/globals.css';

const RootLayout = ({ children }: { children: React.ReactNode }) => {
  return (
    <html lang="zh-CN">
      <body>
        <StyledComponentsRegistry>
          <BasicLayout>{children}</BasicLayout>
        </StyledComponentsRegistry>
      </body>
    </html>
  );
};

export default RootLayout;
