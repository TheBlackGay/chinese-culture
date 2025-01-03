'use client';

import React, { useState } from 'react';
import { Card, Button, Typography, Descriptions, Row, Col, Alert } from 'antd';

const { Title, Paragraph, Text } = Typography;

interface Hexagram {
  id: number;
  name: string;
  chinese: string;
  lines: boolean[];  // true 表示阳爻，false 表示阴爻
  meaning: string;
  interpretation: {
    overall: string;
    career: string;
    relationship: string;
    health: string;
  };
}

// 64卦数据（这里只展示部分示例）
const hexagrams: Hexagram[] = [
  {
    id: 1,
    name: 'Qian',
    chinese: '乾',
    lines: [true, true, true, true, true, true],
    meaning: '乾为天',
    interpretation: {
      overall: '大吉大利，象征着强劲的创造力和领导力。',
      career: '事业发展顺利，适合开创新事业或扩大规模。',
      relationship: '人际关系和谐，贵人运旺盛。',
      health: '精力充沛，身体状况良好。',
    },
  },
  {
    id: 2,
    name: 'Kun',
    chinese: '坤',
    lines: [false, false, false, false, false, false],
    meaning: '坤为地',
    interpretation: {
      overall: '包容、顺从，象征着广大的承载力和包容力。',
      career: '稳扎稳打，循序渐进，适合务实发展。',
      relationship: '人缘好，但需要主动一些。',
      health: '平稳，注意休息。',
    },
  },
  // 可以添加更多卦象
];

export default function HexagramPage() {
  const [currentHexagram, setCurrentHexagram] = useState<Hexagram | null>(null);
  const [coins, setCoins] = useState<number[]>([]);

  // 模拟抛硬币
  const throwCoins = () => {
    const results: number[] = [];
    for (let i = 0; i < 6; i++) {
      const coin1 = Math.random() < 0.5 ? 0 : 1;
      const coin2 = Math.random() < 0.5 ? 0 : 1;
      const coin3 = Math.random() < 0.5 ? 0 : 1;
      results.push(coin1 + coin2 + coin3);
    }
    setCoins(results);

    // 根据硬币结果生成卦象
    const lines = results.map(r => r >= 2);
    // 在实际应用中，应该根据卦象查找对应的解释
    // 这里简单返回乾卦或坤卦
    const allYang = lines.every(l => l);
    const allYin = lines.every(l => !l);
    setCurrentHexagram(allYang ? hexagrams[0] : (allYin ? hexagrams[1] : hexagrams[0]));
  };

  const renderLine = (isYang: boolean) => (
    <div className="h-2 my-2 bg-gray-800">
      {isYang ? (
        <div className="w-full h-full bg-black" />
      ) : (
        <div className="flex justify-between">
          <div className="w-[45%] h-full bg-black" />
          <div className="w-[45%] h-full bg-black" />
        </div>
      )}
    </div>
  );

  return (
    <div>
      <Title level={2}>周易卦象解读</Title>
      <Paragraph>
        周易是中国古代最重要的经典之一，通过卦象来预测和解读事物的发展规律。
        您可以通过抛掷铜钱的方式获取卦象，系统会为您解读其中的含义。
      </Paragraph>

      <Card className="mb-6">
        <div className="text-center">
          <Button type="primary" size="large" onClick={throwCoins}>
            抛掷铜钱
          </Button>
          {coins.length > 0 && (
            <div className="mt-4">
              <Text>铜钱结果：{coins.map(c => c + 1).join(', ')}</Text>
            </div>
          )}
        </div>
      </Card>

      {currentHexagram && (
        <Row gutter={[16, 16]}>
          <Col xs={24} md={8}>
            <Card title="卦象">
              <div className="flex justify-center mb-4">
                <div className="w-32">
                  {currentHexagram.lines.map((isYang, index) => (
                    <div key={index}>{renderLine(isYang)}</div>
                  ))}
                </div>
              </div>
              <Descriptions bordered column={1}>
                <Descriptions.Item label="卦名">{currentHexagram.chinese}</Descriptions.Item>
                <Descriptions.Item label="卦意">{currentHexagram.meaning}</Descriptions.Item>
              </Descriptions>
            </Card>
          </Col>
          <Col xs={24} md={16}>
            <Card title="卦象解读">
              <Alert
                message="总体解读"
                description={currentHexagram.interpretation.overall}
                type="info"
                showIcon
                className="mb-4"
              />
              <Descriptions bordered column={1}>
                <Descriptions.Item label="事业">{currentHexagram.interpretation.career}</Descriptions.Item>
                <Descriptions.Item label="人际">{currentHexagram.interpretation.relationship}</Descriptions.Item>
                <Descriptions.Item label="健康">{currentHexagram.interpretation.health}</Descriptions.Item>
              </Descriptions>
            </Card>
          </Col>
        </Row>
      )}
    </div>
  );
} 