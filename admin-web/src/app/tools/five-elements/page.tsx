'use client';

import React, { useState } from 'react';
import { Card, InputNumber, Button, Typography, Descriptions, Alert } from 'antd';
import { Solar } from 'lunar-typescript';

const { Title, Paragraph } = Typography;

interface FiveElementsResult {
  year: number;
  ganZhi: string;
  element: string;
  elementDetail: string;
  zodiac: string;
  description: string;
}

const elementDescriptions = {
  '金': '金代表秋天，象征着收获与成熟，性质坚强刚毅。',
  '木': '木代表春天，象征着生长与发展，性质温和灵活。',
  '水': '水代表冬天，象征着智慧与灵性，性质柔和善变。',
  '火': '火代表夏天，象征着热情与活力，性质温暖明亮。',
  '土': '土代表季节交替，象征着稳重与包容，性质厚实可靠。',
};

export default function FiveElementsPage() {
  const [year, setYear] = useState<number>(new Date().getFullYear());
  const [result, setResult] = useState<FiveElementsResult | null>(null);

  const calculateFiveElements = () => {
    const solar = Solar.fromYmd(year, 1, 1);
    const lunar = solar.getLunar();
    const ganZhi = lunar.getYearInGanZhi();
    const zodiac = lunar.getYearShengXiao();
    
    // 根据天干确定五行属性
    const gan = ganZhi.charAt(0);
    let element = '';
    switch (gan) {
      case '甲':
      case '乙':
        element = '木';
        break;
      case '丙':
      case '丁':
        element = '火';
        break;
      case '戊':
      case '己':
        element = '土';
        break;
      case '庚':
      case '辛':
        element = '金';
        break;
      case '壬':
      case '癸':
        element = '水';
        break;
    }

    setResult({
      year,
      ganZhi,
      element,
      elementDetail: elementDescriptions[element as keyof typeof elementDescriptions],
      zodiac,
      description: `${year}年为${ganZhi}年，属${zodiac}，五行属${element}。`,
    });
  };

  return (
    <div>
      <Title level={2}>年份五行属性</Title>
      <Paragraph>
        五行（金木水火土）是中国传统文化中对自然界基本物质的分类，每一个年份都对应着不同的五行属性。
      </Paragraph>
      
      <Card className="mb-6">
        <div className="flex items-center gap-4">
          <InputNumber
            className="w-32"
            value={year}
            onChange={(value) => setYear(value || new Date().getFullYear())}
            min={1900}
            max={2100}
          />
          <Button type="primary" onClick={calculateFiveElements}>
            查询五行
          </Button>
        </div>
      </Card>

      {result && (
        <Card title="五行分析结果">
          <Descriptions bordered column={1}>
            <Descriptions.Item label="年份">{result.year}年</Descriptions.Item>
            <Descriptions.Item label="干支">{result.ganZhi}</Descriptions.Item>
            <Descriptions.Item label="生肖">{result.zodiac}</Descriptions.Item>
            <Descriptions.Item label="五行">{result.element}</Descriptions.Item>
          </Descriptions>
          
          <Alert
            className="mt-6"
            message="五行属性详解"
            description={result.elementDetail}
            type="info"
            showIcon
          />
        </Card>
      )}
    </div>
  );
} 