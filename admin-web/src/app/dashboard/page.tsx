'use client';

import React from 'react';
import dynamic from 'next/dynamic';
import { Row, Col } from 'antd';
import {
  UserOutlined,
  FileTextOutlined,
  TagOutlined,
  CommentOutlined,
} from '@ant-design/icons';

const StatisticCard = dynamic(() => import('@/components/Dashboard/StatisticCard'), {
  ssr: false,
});

const LunarCalendar = dynamic(() => import('@/components/Calendar/LunarCalendar'), {
  ssr: false,
});

export default function DashboardPage() {
  const statistics = [
    {
      title: '用户总数',
      value: 1234,
      prefix: <UserOutlined />,
    },
    {
      title: '文章总数',
      value: 56,
      prefix: <FileTextOutlined />,
    },
    {
      title: '标签总数',
      value: 18,
      prefix: <TagOutlined />,
    },
    {
      title: '评论总数',
      value: 289,
      prefix: <CommentOutlined />,
    },
  ];

  return (
    <div className="p-6">
      <Row gutter={[16, 16]}>
        {statistics.map((stat, index) => (
          <Col key={index} xs={24} sm={12} md={6}>
            <StatisticCard {...stat} />
          </Col>
        ))}
      </Row>

      <Row className="mt-6">
        <Col xs={24}>
          <LunarCalendar />
        </Col>
      </Row>
    </div>
  );
} 