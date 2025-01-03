'use client';

import React from 'react';
import { Card, Typography, Tag, Space, Collapse, Tooltip } from 'antd';
import styled from 'styled-components';
import { Hexagram } from '../types';
import HexagramRelationships from './HexagramRelationships';

const { Title, Text, Paragraph } = Typography;
const { Panel } = Collapse;

const DetailContainer = styled.div`
  margin: 20px 0;
`;

const TagContainer = styled.div`
  margin: 8px 0;
  display: flex;
  flex-wrap: wrap;
  gap: 8px;
`;

const YaoContainer = styled.div`
  margin: 16px 0;
  padding: 16px;
  background: #f5f5f5;
  border-radius: 8px;
`;

const YaoText = styled(Text)<{ isChanging?: boolean }>`
  display: block;
  margin: 8px 0;
  padding: 8px;
  background: ${({ isChanging }) => (isChanging ? '#fff3e0' : 'transparent')};
  border-radius: 4px;
  transition: all 0.3s ease;

  &:hover {
    background: ${({ isChanging }) => (isChanging ? '#ffe0b2' : '#f0f0f0')};
  }
`;

interface HexagramDetailProps {
  hexagram: Hexagram;
  isChanged?: boolean;
  changingLines?: number[];
}

const HexagramDetail: React.FC<HexagramDetailProps> = ({
  hexagram,
  isChanged = false,
  changingLines = [],
}) => {
  return (
    <DetailContainer>
      <Card>
        <Title level={3}>
          {hexagram.name}卦 - 第{hexagram.sequence}卦
          {isChanged && <Tag color="orange" style={{ marginLeft: 8 }}>变卦</Tag>}
        </Title>

        <TagContainer>
          <Tooltip title="卦的基本性质">
            <Tag color="blue">性质：{hexagram.nature}</Tag>
          </Tooltip>
          <Tooltip title="对应的五行属性">
            <Tag color="green">五行：{hexagram.element}</Tag>
          </Tooltip>
          <Tooltip title="所属八宫">
            <Tag color="purple">宫位：{hexagram.palace}</Tag>
          </Tooltip>
        </TagContainer>

        <Collapse defaultActiveKey={['1', '2', '3']} expandIconPosition="end">
          <Panel header="卦辞解释" key="1">
            <Space direction="vertical" style={{ width: '100%' }}>
              <Paragraph>
                <Text strong>卦辞：</Text>
                {hexagram.description}
              </Paragraph>
              <Paragraph>
                <Text strong>象辞：</Text>
                {hexagram.image}
              </Paragraph>
            </Space>
          </Panel>

          <Panel header="六爻详解" key="2">
            <YaoContainer>
              {hexagram.yaoTexts.map((text, index) => (
                <YaoText
                  key={index}
                  isChanging={changingLines.includes(index)}
                >
                  <Text strong>第{index + 1}爻：</Text>
                  {text}
                  {changingLines.includes(index) && (
                    <Tag color="orange" style={{ marginLeft: 8 }}>变爻</Tag>
                  )}
                </YaoText>
              ))}
            </YaoContainer>
          </Panel>

          <Panel header="各方面解释" key="3">
            <Space direction="vertical" style={{ width: '100%' }}>
              <Card size="small" title="总体">
                <Text>{hexagram.meaning.general}</Text>
              </Card>
              <Card size="small" title="感情">
                <Text>{hexagram.meaning.love}</Text>
              </Card>
              <Card size="small" title="事业">
                <Text>{hexagram.meaning.career}</Text>
              </Card>
              <Card size="small" title="健康">
                <Text>{hexagram.meaning.health}</Text>
              </Card>
              <Card size="small" title="财运">
                <Text>{hexagram.meaning.wealth}</Text>
              </Card>
            </Space>
          </Panel>

          {!isChanged && (
            <Panel header="卦象关系" key="4">
              <HexagramRelationships hexagram={hexagram} />
            </Panel>
          )}
        </Collapse>
      </Card>
    </DetailContainer>
  );
};

export default HexagramDetail; 