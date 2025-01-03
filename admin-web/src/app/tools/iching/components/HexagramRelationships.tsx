'use client';

import React from 'react';
import styled from 'styled-components';
import { Card, Tabs, Typography, Space } from 'antd';
import { Hexagram } from '../types';
import { HexagramDetail } from './HexagramDetail';

const { Title, Text } = Typography;

const Container = styled.div`
  margin: 20px 0;
`;

const RelationshipCard = styled(Card)`
  margin-bottom: 16px;
  border-radius: 8px;
  background: linear-gradient(145deg, #ffffff 0%, #f8f9fa 100%);
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);

  .ant-card-head {
    border-bottom: 2px solid #f0f0f0;
  }
`;

const Description = styled.div`
  padding: 12px;
  background: #f8f9fa;
  border-radius: 6px;
  margin-bottom: 16px;
  border-left: 4px solid #1890ff;
`;

interface Props {
  hexagram: Hexagram;
  relationships: {
    opposite?: Hexagram;
    complementary?: Hexagram;
    mutual?: Hexagram;
    nuclear?: Hexagram;
  };
}

export const HexagramRelationships: React.FC<Props> = ({ hexagram, relationships }) => {
  const items = [
    {
      key: '1',
      label: '对卦关系',
      children: relationships.opposite ? (
        <>
          <Description>
            <Text>对卦是指上下卦完全相反的卦。对卦关系体现了阴阳的对立统一原理。</Text>
          </Description>
          <HexagramDetail hexagram={relationships.opposite} />
        </>
      ) : (
        <Text>无对应的对卦关系</Text>
      ),
    },
    {
      key: '2',
      label: '综卦关系',
      children: relationships.complementary ? (
        <>
          <Description>
            <Text>综卦是指将原卦上下颠倒而成的卦。综卦关系展示了事物的另一面。</Text>
          </Description>
          <HexagramDetail hexagram={relationships.complementary} />
        </>
      ) : (
        <Text>无对应的综卦关系</Text>
      ),
    },
    {
      key: '3',
      label: '互卦关系',
      children: relationships.mutual ? (
        <>
          <Description>
            <Text>互卦是由原卦第二、三、四爻构成上卦，第三、四、五爻构成下卦而成的卦。</Text>
          </Description>
          <HexagramDetail hexagram={relationships.mutual} />
        </>
      ) : (
        <Text>无对应的互卦关系</Text>
      ),
    },
    {
      key: '4',
      label: '核卦关系',
      children: relationships.nuclear ? (
        <>
          <Description>
            <Text>核卦是由原卦去掉上下两爻后，中间四爻分别作为上下卦而成的卦。</Text>
          </Description>
          <HexagramDetail hexagram={relationships.nuclear} />
        </>
      ) : (
        <Text>无对应的核卦关系</Text>
      ),
    },
  ];

  return (
    <Container>
      <RelationshipCard
        title={
          <Title level={4} style={{ margin: 0 }}>
            {hexagram.name}卦 - 卦象关系
          </Title>
        }
      >
        <Tabs
          defaultActiveKey="1"
          items={items}
          type="card"
          animated={{ inkBar: true, tabPane: true }}
        />
      </RelationshipCard>
    </Container>
  );
}; 