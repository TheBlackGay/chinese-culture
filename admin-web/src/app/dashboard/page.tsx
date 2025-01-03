'use client';

import { Card, Row, Col, Statistic } from 'antd';
import {
  UserOutlined,
  FileTextOutlined,
  EyeOutlined,
  LikeOutlined
} from '@ant-design/icons';
import LunarCalendar from '@/components/LunarCalendar';
import styled from 'styled-components';

const StyledCard = styled(Card)`
  &.content-card {
    transition: all 0.3s ease-in-out;
    
    &:hover {
      transform: translateY(-2px);
    }
  }
`;

export default function Dashboard() {
  return (
    <div className="dashboard-container">
      <Row gutter={[24, 24]}>
        <Col xs={24} sm={12} lg={6}>
          <StyledCard hoverable className="content-card">
            <Statistic
              title="总用户数"
              value={1234}
              prefix={<UserOutlined />}
              valueStyle={{ color: '#1890ff' }}
            />
          </StyledCard>
        </Col>
        <Col xs={24} sm={12} lg={6}>
          <StyledCard hoverable className="content-card">
            <Statistic
              title="文章数量"
              value={56}
              prefix={<FileTextOutlined />}
              valueStyle={{ color: '#52c41a' }}
            />
          </StyledCard>
        </Col>
        <Col xs={24} sm={12} lg={6}>
          <StyledCard hoverable className="content-card">
            <Statistic
              title="总访问量"
              value={9876}
              prefix={<EyeOutlined />}
              valueStyle={{ color: '#faad14' }}
            />
          </StyledCard>
        </Col>
        <Col xs={24} sm={12} lg={6}>
          <StyledCard hoverable className="content-card">
            <Statistic
              title="收藏数"
              value={432}
              prefix={<LikeOutlined />}
              valueStyle={{ color: '#eb2f96' }}
            />
          </StyledCard>
        </Col>
      </Row>

      <Row gutter={[24, 24]} className="mt-6">
        <Col xs={24} lg={16}>
          <StyledCard 
            title="最近活动" 
            className="content-card"
            bodyStyle={{ height: '400px' }}
          >
            {/* 这里可以添加图表或活动列表 */}
          </StyledCard>
        </Col>
        <Col xs={24} lg={8}>
          <LunarCalendar />
        </Col>
      </Row>
    </div>
  );
} 