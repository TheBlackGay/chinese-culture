'use client';

import React from 'react';
import { Card, Row, Col, Statistic } from 'antd';
import {
  FileTextOutlined,
  FolderOutlined,
  TagsOutlined,
  EyeOutlined,
} from '@ant-design/icons';
import Link from 'next/link';

export default function ContentPage() {
  const statistics = [
    {
      title: '文章数量',
      value: 93,
      icon: <FileTextOutlined />,
      color: '#1677ff',
      link: '/content/articles',
    },
    {
      title: '分类数量',
      value: 12,
      icon: <FolderOutlined />,
      color: '#52c41a',
      link: '/content/categories',
    },
    {
      title: '标签数量',
      value: 45,
      icon: <TagsOutlined />,
      color: '#faad14',
      link: '/content/tags',
    },
    {
      title: '总浏览量',
      value: 12580,
      icon: <EyeOutlined />,
      color: '#ff4d4f',
      link: '/content/articles',
    },
  ];

  return (
    <div>
      <Row gutter={[16, 16]}>
        {statistics.map((stat) => (
          <Col key={stat.title} xs={24} sm={12} md={6}>
            <Link href={stat.link}>
              <Card hoverable>
                <Statistic
                  title={stat.title}
                  value={stat.value}
                  prefix={React.cloneElement(stat.icon, {
                    style: { color: stat.color },
                  })}
                  valueStyle={{ color: stat.color }}
                />
              </Card>
            </Link>
          </Col>
        ))}
      </Row>

      <Row gutter={[16, 16]} className="mt-6">
        <Col xs={24} md={12}>
          <Card title="最近更新">
            <p>暂无数据</p>
          </Card>
        </Col>
        <Col xs={24} md={12}>
          <Card title="热门文章">
            <p>暂无数据</p>
          </Card>
        </Col>
      </Row>
    </div>
  );
} 