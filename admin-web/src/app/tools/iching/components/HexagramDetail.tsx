'use client';

import React from 'react';
import styled from 'styled-components';
import { Card, Collapse, Typography, Divider } from 'antd';
import { Hexagram } from '../types';

const { Panel } = Collapse;
const { Title, Paragraph, Text } = Typography;

const Container = styled.div`
  padding: 20px;
  background: linear-gradient(145deg, #f6f8fa 0%, #e9ecef 100%);
  border-radius: 12px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
`;

const DetailCard = styled(Card)`
  margin-bottom: 16px;
  border-radius: 8px;
  
  .ant-card-head {
    background: linear-gradient(90deg, #1a1a1a 0%, #333333 100%);
    color: white;
  }
`;

const Section = styled.div`
  margin: 16px 0;
`;

const YaoText = styled(Text)`
  display: block;
  margin: 8px 0;
  padding: 8px;
  background: #f9f9f9;
  border-radius: 4px;
  transition: all 0.3s ease;

  &:hover {
    background: #f0f0f0;
    transform: translateX(4px);
  }
`;

const MeaningBox = styled.div`
  padding: 12px;
  background: #f9f9f9;
  border-radius: 6px;
  margin: 8px 0;
  border-left: 4px solid #1890ff;
`;

interface Props {
  hexagram: Hexagram;
}

export const HexagramDetail: React.FC<Props> = ({ hexagram }) => {
  return (
    <Container>
      <DetailCard
        title={
          <Title level={4} style={{ color: 'white', margin: 0 }}>
            {hexagram.sequence}. {hexagram.name} - 详细解释
          </Title>
        }
      >
        <Section>
          <Title level={5}>基本信息</Title>
          <Paragraph>
            <Text strong>性质：</Text> {hexagram.nature}
            <Divider type="vertical" />
            <Text strong>五行：</Text> {hexagram.element}
            <Divider type="vertical" />
            <Text strong>宫位：</Text> {hexagram.palace}
          </Paragraph>
        </Section>

        <Collapse defaultActiveKey={['1']} ghost>
          <Panel header="卦辞解释" key="1">
            <MeaningBox>
              <Paragraph>
                <Text strong>卦辞：</Text> {hexagram.description}
              </Paragraph>
              <Paragraph>
                <Text strong>象辞：</Text> {hexagram.image}
              </Paragraph>
            </MeaningBox>
          </Panel>

          <Panel header="六爻详解" key="2">
            {hexagram.yaoTexts.map((text, index) => (
              <YaoText key={index}>
                <Text strong>第{6 - index}爻：</Text> {text}
              </YaoText>
            ))}
          </Panel>

          <Panel header="各方面解释" key="3">
            <MeaningBox>
              <Paragraph>
                <Text strong>总体：</Text> {hexagram.meaning.general}
              </Paragraph>
            </MeaningBox>
            <MeaningBox>
              <Paragraph>
                <Text strong>感情：</Text> {hexagram.meaning.love}
              </Paragraph>
            </MeaningBox>
            <MeaningBox>
              <Paragraph>
                <Text strong>事业：</Text> {hexagram.meaning.career}
              </Paragraph>
            </MeaningBox>
            <MeaningBox>
              <Paragraph>
                <Text strong>健康：</Text> {hexagram.meaning.health}
              </Paragraph>
            </MeaningBox>
            <MeaningBox>
              <Paragraph>
                <Text strong>财运：</Text> {hexagram.meaning.wealth}
              </Paragraph>
            </MeaningBox>
          </Panel>
        </Collapse>
      </DetailCard>
    </Container>
  );
}; 