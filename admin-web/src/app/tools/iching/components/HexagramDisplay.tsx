'use client';

import React from 'react';
import styled, { keyframes } from 'styled-components';
import { Card, Typography, Tooltip } from 'antd';
import { Hexagram } from '../types';

const breathe = keyframes`
  0% { transform: scale(1); }
  50% { transform: scale(1.05); }
  100% { transform: scale(1); }
`;

const flash = keyframes`
  0% { opacity: 1; }
  50% { opacity: 0.6; }
  100% { opacity: 1; }
`;

const Container = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 20px;
  background: linear-gradient(145deg, #f6f8fa 0%, #e9ecef 100%);
  border-radius: 12px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  transition: all 0.3s ease;

  &:hover {
    transform: translateY(-2px);
    box-shadow: 0 6px 16px rgba(0, 0, 0, 0.15);
  }
`;

const HexagramCard = styled(Card)`
  width: 100%;
  margin-bottom: 16px;
  border-radius: 8px;
  overflow: hidden;
  
  .ant-card-head {
    background: linear-gradient(90deg, #1a1a1a 0%, #333333 100%);
    color: white;
  }
`;

const Line = styled.div<{ isYang: boolean }>`
  width: 100px;
  height: 12px;
  background-color: ${props => props.isYang ? '#333' : '#ddd'};
  margin: 4px 0;
  border-radius: 6px;
  position: relative;
  cursor: pointer;
  transition: all 0.3s ease;

  &:hover {
    transform: scaleX(1.1);
    animation: ${breathe} 2s infinite ease-in-out;
  }

  &:before {
    content: '';
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    background: linear-gradient(90deg, rgba(255,255,255,0.1) 0%, rgba(255,255,255,0.2) 100%);
    opacity: 0;
    transition: opacity 0.3s ease;
  }

  &:hover:before {
    opacity: 1;
  }
`;

const ElementBadge = styled.span`
  padding: 4px 8px;
  border-radius: 4px;
  background: #f0f0f0;
  color: #333;
  font-size: 12px;
  margin: 0 4px;
  animation: ${flash} 3s infinite ease-in-out;
`;

interface HexagramDisplayProps {
  hexagram: Hexagram;
  showDetails?: boolean;
}

export const HexagramDisplay: React.FC<HexagramDisplayProps> = ({ hexagram, showDetails = true }) => {
  const lines = hexagram.key.split('').map(bit => bit === '1');

  return (
    <Container>
      <HexagramCard
        title={
          <Typography.Title level={4} style={{ color: 'white', margin: 0 }}>
            {hexagram.sequence}. {hexagram.name}
            <ElementBadge>{hexagram.element}</ElementBadge>
            <ElementBadge>{hexagram.palace}</ElementBadge>
          </Typography.Title>
        }
      >
        <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
          {lines.map((isYang, index) => (
            <Tooltip
              key={index}
              title={hexagram.yaoTexts[5 - index]}
              placement="right"
            >
              <Line isYang={isYang} />
            </Tooltip>
          ))}
        </div>
        {showDetails && (
          <>
            <Typography.Paragraph style={{ marginTop: 16 }}>
              <strong>性质：</strong>{hexagram.nature}
            </Typography.Paragraph>
            <Typography.Paragraph>
              <strong>卦辞：</strong>{hexagram.description}
            </Typography.Paragraph>
            <Typography.Paragraph>
              <strong>象辞：</strong>{hexagram.image}
            </Typography.Paragraph>
          </>
        )}
      </HexagramCard>
    </Container>
  );
}; 