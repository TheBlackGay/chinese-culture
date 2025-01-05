'use client';

import { PageContainer, ProCard } from '@ant-design/pro-components';
import { Card, Row, Col, Statistic, Typography, Space } from 'antd';
import {
  BookOutlined,
  UserOutlined,
  FireOutlined,
  ThunderboltOutlined,
  HistoryOutlined,
  GlobalOutlined,
} from '@ant-design/icons';
import { Line, Pie, Column } from '@ant-design/plots';
import { useState } from 'react';

const { Title, Text } = Typography;

const HomePage: React.FC = () => {
  // 访问趋势数据
  const visitData = [
    { date: '2024-01', value: 3 },
    { date: '2024-02', value: 4 },
    { date: '2024-03', value: 3.5 },
    { date: '2024-04', value: 5 },
    { date: '2024-05', value: 4.9 },
    { date: '2024-06', value: 6 },
    { date: '2024-07', value: 7 },
  ];

  // 内容分布数据
  const contentData = [
    { type: '节气文化', value: 24 },
    { type: '传统习俗', value: 156 },
    { type: '历史故事', value: 89 },
    { type: '民间艺术', value: 45 },
  ];

  // 用户活跃度数据
  const activityData = [
    { time: '00:00', value: 10 },
    { time: '03:00', value: 5 },
    { time: '06:00', value: 15 },
    { time: '09:00', value: 45 },
    { time: '12:00', value: 65 },
    { time: '15:00', value: 90 },
    { time: '18:00', value: 80 },
    { time: '21:00', value: 30 },
  ];

  // 图表通用配置
  const chartCommonConfig = {
    theme: {
      defaultColor: '#00b4ff',
    },
    axis: {
      label: {
        style: {
          fill: 'rgba(255, 255, 255, 0.65)',
        },
      },
      line: {
        style: {
          stroke: 'rgba(255, 255, 255, 0.15)',
        },
      },
      grid: {
        line: {
          style: {
            stroke: 'rgba(255, 255, 255, 0.08)',
          },
        },
      },
    },
  };

  return (
    <PageContainer
      header={{
        title: '传统文化数字驾驶舱',
        ghost: true,
      }}
    >
      <div className="space-y-6">
        {/* 统计卡片 */}
        <Row gutter={[16, 16]}>
          <Col xs={24} sm={12} md={8} lg={4}>
            <ProCard
              bordered={false}
              className="bg-[#1f1f1f] hover:bg-[#252525] transition-all"
            >
              <Statistic
                title={<span className="text-white/60">总访问量</span>}
                value={112893}
                prefix={<GlobalOutlined className="text-[#00b4ff]" />}
                valueStyle={{ color: '#00b4ff' }}
              />
            </ProCard>
          </Col>
          <Col xs={24} sm={12} md={8} lg={4}>
            <ProCard
              bordered={false}
              className="bg-[#1f1f1f] hover:bg-[#252525] transition-all"
            >
              <Statistic
                title={<span className="text-white/60">内容总数</span>}
                value={314}
                prefix={<BookOutlined className="text-[#52c41a]" />}
                valueStyle={{ color: '#52c41a' }}
              />
            </ProCard>
          </Col>
          <Col xs={24} sm={12} md={8} lg={4}>
            <ProCard
              bordered={false}
              className="bg-[#1f1f1f] hover:bg-[#252525] transition-all"
            >
              <Statistic
                title={<span className="text-white/60">活跃用户</span>}
                value={892}
                prefix={<UserOutlined className="text-[#faad14]" />}
                valueStyle={{ color: '#faad14' }}
              />
            </ProCard>
          </Col>
          <Col xs={24} sm={12} md={8} lg={4}>
            <ProCard
              bordered={false}
              className="bg-[#1f1f1f] hover:bg-[#252525] transition-all"
            >
              <Statistic
                title={<span className="text-white/60">今日热度</span>}
                value={1254}
                prefix={<FireOutlined className="text-[#ff4d4f]" />}
                valueStyle={{ color: '#ff4d4f' }}
              />
            </ProCard>
          </Col>
          <Col xs={24} sm={12} md={8} lg={4}>
            <ProCard
              bordered={false}
              className="bg-[#1f1f1f] hover:bg-[#252525] transition-all"
            >
              <Statistic
                title={<span className="text-white/60">系统性能</span>}
                value="93%"
                prefix={<ThunderboltOutlined className="text-[#13c2c2]" />}
                valueStyle={{ color: '#13c2c2' }}
              />
            </ProCard>
          </Col>
          <Col xs={24} sm={12} md={8} lg={4}>
            <ProCard
              bordered={false}
              className="bg-[#1f1f1f] hover:bg-[#252525] transition-all"
            >
              <Statistic
                title={<span className="text-white/60">平均访问时长</span>}
                value="00:12:23"
                prefix={<HistoryOutlined className="text-[#eb2f96]" />}
                valueStyle={{ color: '#eb2f96' }}
              />
            </ProCard>
          </Col>
        </Row>

        {/* 图表区域 */}
        <Row gutter={[16, 16]}>
          <Col xs={24} lg={12}>
            <ProCard
              title={<span className="text-white/85">访问趋势</span>}
              bordered={false}
              className="bg-[#1f1f1f] hover:bg-[#252525] transition-all"
              headerBordered
            >
              <Line
                {...chartCommonConfig}
                data={visitData}
                xField="date"
                yField="value"
                smooth
                point={{
                  size: 5,
                  shape: 'diamond',
                  style: {
                    fill: '#00b4ff',
                    stroke: '#00b4ff',
                    lineWidth: 2,
                  },
                }}
                area={{
                  style: {
                    fill: 'l(270) 0:#00b4ff00 1:#00b4ff',
                  },
                }}
              />
            </ProCard>
          </Col>
          <Col xs={24} lg={12}>
            <ProCard
              title={<span className="text-white/85">用户活跃度</span>}
              bordered={false}
              className="bg-[#1f1f1f] hover:bg-[#252525] transition-all"
              headerBordered
            >
              <Column
                {...chartCommonConfig}
                data={activityData}
                xField="time"
                yField="value"
                color={{
                  type: 'linear',
                  x1: 0,
                  y1: 1,
                  x2: 0,
                  y2: 0,
                  colorStops: [
                    { offset: 0, color: '#00b4ff20' },
                    { offset: 1, color: '#00b4ff' },
                  ],
                }}
              />
            </ProCard>
          </Col>
        </Row>

        <Row gutter={[16, 16]}>
          <Col xs={24} lg={12}>
            <ProCard
              title={<span className="text-white/85">内容分布</span>}
              bordered={false}
              className="bg-[#1f1f1f] hover:bg-[#252525] transition-all"
              headerBordered
            >
              <Pie
                {...chartCommonConfig}
                data={contentData}
                angleField="value"
                colorField="type"
                radius={0.8}
                innerRadius={0.6}
                label={{
                  type: 'outer',
                  style: {
                    fill: 'rgba(255, 255, 255, 0.85)',
                  },
                }}
                statistic={{
                  title: {
                    style: {
                      color: 'rgba(255, 255, 255, 0.85)',
                    },
                    formatter: () => '总计',
                  },
                  content: {
                    style: {
                      color: '#00b4ff',
                    },
                  },
                }}
              />
            </ProCard>
          </Col>
          <Col xs={24} lg={12}>
            <ProCard
              title={<span className="text-white/85">实时动态</span>}
              bordered={false}
              className="bg-[#1f1f1f] hover:bg-[#252525] transition-all"
              headerBordered
            >
              <div className="space-y-4">
                {[1, 2, 3, 4, 5].map((_, index) => (
                  <div
                    key={index}
                    className="flex items-center space-x-4 p-4 rounded-lg bg-[#252525] hover:bg-[#2a2a2a] transition-all"
                  >
                    <div className="w-2 h-2 rounded-full bg-[#00b4ff] animate-pulse" />
                    <div className="flex-1">
                      <div className="text-white/80">用户 Alex 添加了新的节气解读</div>
                      <div className="text-white/40 text-sm">2分钟前</div>
                    </div>
                  </div>
                ))}
              </div>
            </ProCard>
          </Col>
        </Row>
      </div>
    </PageContainer>
  );
};

export default HomePage;
