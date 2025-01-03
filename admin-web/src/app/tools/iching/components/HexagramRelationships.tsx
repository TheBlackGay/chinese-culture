'use client';

import React from 'react';
import { Card, Space, Typography } from 'antd';
import styled from 'styled-components';
import { Hexagram } from '../types';
import HexagramDisplay from './HexagramDisplay';
import { getHexagramRelationships } from '../utils/hexagram';
import { BASE_HEXAGRAMS } from '../data/base';

const { Title, Text } = Typography;

const RelationshipContainer = styled.div`
  margin: 20px 0;
`;

const RelationshipCard = styled(Card)`
  margin: 16px 0;
  transition: all 0.3s ease;
  &:hover {
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  }
`;

const RelationshipRow = styled.div`
  display: flex;
  align-items: center;
  gap: 24px;
  padding: 16px;
  border-radius: 8px;
  background: #f5f5f5;
  margin: 8px 0;
  transition: all 0.3s ease;

  &:hover {
    background: #f0f0f0;
    transform: translateX(4px);
  }
`;

const RelationshipInfo = styled.div`
  flex: 1;
`;

const HexagramInfo = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 8px;
`;

interface HexagramRelationshipsProps {
  hexagram: Hexagram;
}

const HexagramRelationships: React.FC<HexagramRelationshipsProps> = ({
  hexagram,
}) => {
  const relationships = getHexagramRelationships(hexagram.key);
  const relatedHexagrams = {
    opposite: BASE_HEXAGRAMS[relationships.opposite],
    inverse: BASE_HEXAGRAMS[relationships.inverse],
    mutual: BASE_HEXAGRAMS[relationships.mutual],
    nuclear: BASE_HEXAGRAMS[relationships.nuclear],
  };

  const relationshipDescriptions = {
    opposite: '对宫卦是阴阳完全相反的卦。代表事物的对立面，可以帮助我们从相反的角度理解事物。',
    inverse: '综卦是将上下卦互换位置得到的卦。表示事物的另一种可能性或发展方向。',
    mutual: '互卦是由第二、三、四爻和第三、四、五爻组成的卦。反映事物的内在联系和变化趋势。',
    nuclear: '核卦是去掉初爻和上爻后的六爻。表示事物的核心本质和基本趋势。',
  };

  return (
    <RelationshipContainer>
      <Title level={4}>卦象关系</Title>
      <Space direction="vertical" style={{ width: '100%' }}>
        {Object.entries(relatedHexagrams).map(([type, relatedHexagram]) => (
          <RelationshipCard key={type}>
            <Title level={5}>
              {type === 'opposite'
                ? '对宫卦'
                : type === 'inverse'
                ? '综卦'
                : type === 'mutual'
                ? '互卦'
                : '核卦'}
            </Title>
            <RelationshipRow>
              <HexagramInfo>
                <HexagramDisplay
                  hexagram={hexagram.key}
                  size="small"
                  showTrigramNames={false}
                  animated={false}
                />
                <Text strong>{hexagram.name}卦</Text>
              </HexagramInfo>
              <RelationshipInfo>
                <Text>{relationshipDescriptions[type as keyof typeof relationshipDescriptions]}</Text>
              </RelationshipInfo>
              <HexagramInfo>
                <HexagramDisplay
                  hexagram={relatedHexagram.key}
                  size="small"
                  showTrigramNames={false}
                  animated={false}
                />
                <Text strong>{relatedHexagram.name}卦</Text>
              </HexagramInfo>
            </RelationshipRow>
          </RelationshipCard>
        ))}
      </Space>
    </RelationshipContainer>
  );
};

export default HexagramRelationships; 