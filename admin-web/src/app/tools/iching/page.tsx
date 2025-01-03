'use client';

import React from 'react';
import { Tabs, Typography, Card } from 'antd';
import styled from 'styled-components';
import HexagramSearch from './components/HexagramSearch';
import HexagramCalculator from './components/HexagramCalculator';

const { Title, Paragraph } = Typography;

const PageContainer = styled.div`
  padding: 24px;
  max-width: 1400px;
  margin: 0 auto;
  background: #fff;
  min-height: 100vh;
`;

const HeaderContainer = styled.div`
  text-align: center;
  margin-bottom: 24px;
  padding: 24px;
  background: linear-gradient(135deg, #f5f5f5 0%, #ffffff 100%);
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
`;

const ContentContainer = styled(Card)`
  margin-top: 24px;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
`;

const TabContainer = styled.div`
  .ant-tabs-nav {
    margin-bottom: 16px;
    &::before {
      border-bottom: 2px solid #f0f0f0;
    }
  }
  
  .ant-tabs-tab {
    padding: 12px 24px;
    font-size: 16px;
    
    &.ant-tabs-tab-active {
      font-weight: 500;
    }
  }
`;

const items = [
  {
    key: '1',
    label: '卦象查询',
    children: <HexagramSearch />,
  },
  {
    key: '2',
    label: '卦象演算',
    children: <HexagramCalculator />,
  },
];

export default function IChingPage() {
  return (
    <PageContainer>
      <HeaderContainer>
        <Title level={2} style={{ marginBottom: 16 }}>周易卦象</Title>
        <Paragraph style={{ maxWidth: 800, margin: '0 auto', color: '#666' }}>
          周易是中国古代最重要的经典之一，包含六十四卦，每卦由六爻组成。
          通过卦象的变化，可以洞察事物的发展规律和趋势。
        </Paragraph>
      </HeaderContainer>

      <ContentContainer>
        <TabContainer>
          <Tabs
            defaultActiveKey="1"
            items={items}
            size="large"
            animated={{ inkBar: true, tabPane: true }}
          />
        </TabContainer>
      </ContentContainer>
    </PageContainer>
  );
} 