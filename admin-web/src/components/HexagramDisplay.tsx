import React from 'react';
import { Card, Typography, Divider, Tag, Space } from 'antd';
import styled from 'styled-components';
import { Hexagram } from '@/app/tools/iching/types';
import { getHexagramLuck } from '@/app/tools/iching/utils';

const { Title, Text, Paragraph } = Typography;

interface HexagramDisplayProps {
  hexagram: Hexagram;
  showDetails?: boolean;
}

const StyledCard = styled(Card)`
  margin-bottom: 24px;
  
  .ant-card-head-title {
    display: flex;
    align-items: center;
    gap: 12px;
  }
`;

const HexagramLine = styled.div<{ isYang: boolean; isChanging: boolean }>`
  width: 100%;
  height: 12px;
  margin: 4px 0;
  display: flex;
  gap: 4px;
  
  ${props => props.isYang
    ? `
      background-color: #1890ff;
      ${props.isChanging ? 'animation: pulse 2s infinite;' : ''}
    `
    : `
      display: flex;
      justify-content: space-between;
      
      &::before,
      &::after {
        content: '';
        width: 45%;
        height: 100%;
        background-color: #1890ff;
        ${props.isChanging ? 'animation: pulse 2s infinite;' : ''}
      }
    `
  }
  
  @keyframes pulse {
    0% { opacity: 1; }
    50% { opacity: 0.5; }
    100% { opacity: 1; }
  }
`;

const AttributeTag = styled(Tag)`
  margin: 4px;
`;

const HexagramDisplay: React.FC<HexagramDisplayProps> = ({ 
  hexagram, 
  showDetails = true 
}) => {
  const luck = getHexagramLuck(hexagram.key);
  const luckColor = {
    '大吉': '#52c41a',
    '中吉': '#1890ff',
    '小吉': '#faad14',
    '凶': '#ff4d4f',
  }[luck];

  return (
    <StyledCard
      title={
        <Space>
          <Title level={4} style={{ margin: 0 }}>{hexagram.name}</Title>
          <Tag color={luckColor}>{luck}</Tag>
        </Space>
      }
    >
      <div style={{ marginBottom: 24 }}>
        {hexagram.lines.map((line, index) => (
          <HexagramLine
            key={index}
            isYang={line.value % 2 === 1}
            isChanging={line.isChanging}
          />
        ))}
      </div>

      <Paragraph>{hexagram.description}</Paragraph>

      {showDetails && (
        <>
          <Divider />
          
          <Space wrap>
            <AttributeTag color="blue">五行: {hexagram.element}</AttributeTag>
            <AttributeTag color="purple">八宫: {hexagram.palace}</AttributeTag>
            <AttributeTag color="cyan">性质: {hexagram.nature}</AttributeTag>
          </Space>

          <Divider />

          <Title level={5}>卦辞解释</Title>
          <Paragraph>{hexagram.meaning.general}</Paragraph>

          <Title level={5}>各方面解释</Title>
          <Space direction="vertical">
            <Text>
              <strong>事业：</strong>
              {hexagram.meaning.career}
            </Text>
            <Text>
              <strong>感情：</strong>
              {hexagram.meaning.love}
            </Text>
            <Text>
              <strong>财运：</strong>
              {hexagram.meaning.wealth}
            </Text>
            <Text>
              <strong>健康：</strong>
              {hexagram.meaning.health}
            </Text>
          </Space>

          {hexagram.relationships && (
            <>
              <Divider />
              <Title level={5}>关系卦</Title>
              <Space wrap>
                <Text>对宫卦：{hexagram.relationships.opposite}</Text>
                <Text>综卦：{hexagram.relationships.inverse}</Text>
                <Text>互卦：{hexagram.relationships.mutual}</Text>
                <Text>内卦：{hexagram.relationships.nuclear}</Text>
              </Space>
            </>
          )}
        </>
      )}
    </StyledCard>
  );
};

export default HexagramDisplay; 