'use client';

import React from 'react';
import { Card, Statistic } from 'antd';

interface StatisticCardProps {
  title: string;
  value: number;
  prefix: React.ReactNode;
}

export default function StatisticCard({ title, value, prefix }: StatisticCardProps) {
  return (
    <Card>
      <Statistic
        title={title}
        value={value}
        prefix={prefix}
      />
    </Card>
  );
} 