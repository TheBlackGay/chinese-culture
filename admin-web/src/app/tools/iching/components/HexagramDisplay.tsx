'use client';

import React from 'react';
import styled, { keyframes } from 'styled-components';
import { Tooltip } from 'antd';

const appear = keyframes`
  from {
    opacity: 0;
    transform: translateY(10px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
`;

const Container = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  margin: 16px 0;
  animation: ${appear} 0.5s ease-out;
`;

const YaoContainer = styled.div<{ isChanging?: boolean }>`
  position: relative;
  width: 120px;
  height: 20px;
  margin: 4px 0;
  display: flex;
  justify-content: center;
  align-items: center;
  transition: all 0.3s ease;

  ${({ isChanging }) =>
    isChanging &&
    `
    &:after {
      content: '';
      position: absolute;
      right: -24px;
      top: 50%;
      transform: translateY(-50%);
      width: 16px;
      height: 16px;
      background: #ff9800;
      border-radius: 50%;
    }
  `}
`;

const YaoLine = styled.div<{ isYang: boolean; isChanging?: boolean }>`
  width: ${({ isYang }) => (isYang ? '100%' : '45%')};
  height: 8px;
  background-color: ${({ isYang }) => (isYang ? '#000' : '#000')};
  margin: ${({ isYang }) => (isYang ? '0' : '0 4px')};
  transition: all 0.3s ease;
  position: relative;

  ${({ isChanging }) =>
    isChanging &&
    `
    background-color: #ff9800;
    box-shadow: 0 0 8px rgba(255, 152, 0, 0.5);
  `}

  &:hover {
    transform: scale(1.05);
  }
`;

const TrigramName = styled.div`
  font-size: 14px;
  color: #666;
  margin: 8px 0;
`;

interface HexagramDisplayProps {
  hexagram: string;
  changingLines?: number[];
  showTrigramNames?: boolean;
  size?: 'small' | 'medium' | 'large';
  animated?: boolean;
}

const TRIGRAM_NAMES: Record<string, string> = {
  '111': '乾',
  '000': '坤',
  '010': '震',
  '101': '离',
  '011': '兑',
  '100': '艮',
  '001': '坎',
  '110': '巽',
};

const HexagramDisplay: React.FC<HexagramDisplayProps> = ({
  hexagram,
  changingLines = [],
  showTrigramNames = true,
  size = 'medium',
  animated = true,
}) => {
  const upperTrigram = hexagram.slice(0, 3);
  const lowerTrigram = hexagram.slice(3);

  const sizeStyles = {
    small: { width: 80, height: 6 },
    medium: { width: 120, height: 8 },
    large: { width: 160, height: 10 },
  }[size];

  return (
    <Container style={{ animationDuration: animated ? '0.5s' : '0s' }}>
      {showTrigramNames && (
        <TrigramName>{TRIGRAM_NAMES[upperTrigram]}</TrigramName>
      )}
      {hexagram.split('').map((yao, index) => (
        <Tooltip
          key={index}
          title={`第${6 - index}爻：${yao === '1' ? '阳' : '阴'}${
            changingLines.includes(5 - index) ? '（变）' : ''
          }`}
        >
          <YaoContainer isChanging={changingLines.includes(5 - index)}>
            {yao === '1' ? (
              <YaoLine
                isYang
                isChanging={changingLines.includes(5 - index)}
                style={sizeStyles}
              />
            ) : (
              <>
                <YaoLine
                  isYang={false}
                  isChanging={changingLines.includes(5 - index)}
                  style={{ ...sizeStyles, width: sizeStyles.width * 0.45 }}
                />
                <YaoLine
                  isYang={false}
                  isChanging={changingLines.includes(5 - index)}
                  style={{ ...sizeStyles, width: sizeStyles.width * 0.45 }}
                />
              </>
            )}
          </YaoContainer>
        </Tooltip>
      ))}
      {showTrigramNames && (
        <TrigramName>{TRIGRAM_NAMES[lowerTrigram]}</TrigramName>
      )}
    </Container>
  );
};

export default HexagramDisplay; 