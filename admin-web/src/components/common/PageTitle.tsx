'use client';

import { Typography } from 'antd';
import { ReactNode } from 'react';

const { Title } = Typography;

interface PageTitleProps {
  title: string;
  subtitle?: string;
  extra?: ReactNode;
}

export default function PageTitle({ title, subtitle, extra }: PageTitleProps) {
  return (
    <div className="flex items-center justify-between mb-6">
      <div>
        <Title level={2} className="!mb-0">
          {title}
        </Title>
        {subtitle && (
          <Typography.Text type="secondary" className="mt-1">
            {subtitle}
          </Typography.Text>
        )}
      </div>
      {extra && <div>{extra}</div>}
    </div>
  );
} 