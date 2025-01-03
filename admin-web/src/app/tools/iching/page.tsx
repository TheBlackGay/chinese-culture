'use client';

import React from 'react';
import styled from 'styled-components';
import { Tabs, Typography } from 'antd';
import { HexagramSearch } from './components/HexagramSearch';
import { HexagramCalculator } from './components/HexagramCalculator';

const { Title, Paragraph } = Typography;

const PageContainer = styled.div`
  padding: 24px;
  min-height: 100vh;
  background: linear-gradient(135deg, #f5f7fa 0%, #e4e7eb 100%);
`;

const HeaderContainer = styled.div`
  margin-bottom: 24px;
  text-align: center;
  padding: 32px;
  background: white;
  border-radius: 12px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
`;

const ContentContainer = styled.div`
  background: white;
  padding: 24px;
  border-radius: 12px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
`;

const items = [
  {
    key: '1',
    label: '卦象查询',
    children: <HexagramSearch />,
  },
  {
    key: '2',
    label: '动态演算',
    children: <HexagramCalculator />,
  },
];

export default function IChingPage() {
  return (
    <PageContainer>
      <HeaderContainer>
        <Title level={2}>周易卦象工具</Title>
        <Paragraph>
          探索古老的智慧，寻找生活的启示。这里提供周易六十四卦的详细解释、动态演算和关系分析。
        </Paragraph>
      </HeaderContainer>

      <ContentContainer>
        <Tabs
          defaultActiveKey="1"
          items={items}
          type="card"
          size="large"
          animated={{ inkBar: true, tabPane: true }}
        />
      </ContentContainer>
    </PageContainer>
  );
} 