'use client';

import React from 'react';
import { Card, Row, Col, Statistic } from 'antd';
import {
  UserOutlined,
  ReadOutlined,
  LikeOutlined,
  CommentOutlined,
} from '@ant-design/icons';

export default function DashboardPage() {
  return (
    <div>
      <h1 className="mb-6">仪表盘</h1>
      
      <Row gutter={[16, 16]}>
        <Col xs={24} sm={12} md={6}>
          <Card>
            <Statistic
              title="总用户数"
              value={1128}
              prefix={<UserOutlined />}
              valueStyle={{ color: '#1677ff' }}
            />
          </Card>
        </Col>
        <Col xs={24} sm={12} md={6}>
          <Card>
            <Statistic
              title="文章数量"
              value={93}
              prefix={<ReadOutlined />}
              valueStyle={{ color: '#52c41a' }}
            />
          </Card>
        </Col>
        <Col xs={24} sm={12} md={6}>
          <Card>
            <Statistic
              title="总点赞数"
              value={2802}
              prefix={<LikeOutlined />}
              valueStyle={{ color: '#faad14' }}
            />
          </Card>
        </Col>
        <Col xs={24} sm={12} md={6}>
          <Card>
            <Statistic
              title="总评论数"
              value={1257}
              prefix={<CommentOutlined />}
              valueStyle={{ color: '#ff4d4f' }}
            />
          </Card>
        </Col>
      </Row>

      <Row gutter={[16, 16]} className="mt-6">
        <Col xs={24} md={12}>
          <Card title="最近活动">
            <p>暂无数据</p>
          </Card>
        </Col>
        <Col xs={24} md={12}>
          <Card title="系统公告">
            <p>暂无数据</p>
          </Card>
        </Col>
      </Row>
    </div>
  );
} 