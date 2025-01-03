'use client';

import React, { useState } from 'react';
import { Card, Select, Typography, Descriptions, Alert, Row, Col } from 'antd';
import { Solar } from 'lunar-typescript';

const { Title, Paragraph } = Typography;

interface ZodiacInfo {
  name: string;
  years: string;
  personality: string;
  fortune: string;
  compatibility: {
    best: string[];
    worst: string[];
  };
}

const zodiacData: Record<string, ZodiacInfo> = {
  '鼠': {
    name: '鼠',
    years: '1924, 1936, 1948, 1960, 1972, 1984, 1996, 2008, 2020',
    personality: '机灵活泼，善于社交，具有领导才能，但有时过于急躁。',
    fortune: '事业上富有创造力，财运佳，但需要注意投资风险。',
    compatibility: {
      best: ['龙', '猴'],
      worst: ['马', '兔'],
    },
  },
  '牛': {
    name: '牛',
    years: '1925, 1937, 1949, 1961, 1973, 1985, 1997, 2009, 2021',
    personality: '勤劳踏实，性格温和，做事认真负责，但有时固执。',
    fortune: '事业稳定发展，财运平稳，感情生活和谐。',
    compatibility: {
      best: ['蛇', '鸡'],
      worst: ['羊', '马'],
    },
  },
  '虎': {
    name: '虎',
    years: '1926, 1938, 1950, 1962, 1974, 1986, 1998, 2010, 2022',
    personality: '勇敢威严，充满正义感，领导能力强，但易冲动。',
    fortune: '事业发展顺利，有贵人相助，感情需要多加耐心。',
    compatibility: {
      best: ['马', '狗'],
      worst: ['猴', '蛇'],
    },
  },
  '兔': {
    name: '兔',
    years: '1927, 1939, 1951, 1963, 1975, 1987, 1999, 2011, 2023',
    personality: '温柔善良，优雅随和，有艺术天赋，但略显优柔寡断。',
    fortune: '人缘好，财运佳，事业发展平稳，感情生活甜蜜。',
    compatibility: {
      best: ['羊', '狗'],
      worst: ['鼠', '龙'],
    },
  },
  '龙': {
    name: '龙',
    years: '1928, 1940, 1952, 1964, 1976, 1988, 2000, 2012, 2024',
    personality: '充满魅力，意志坚强，追求完美，但有时过于理想化。',
    fortune: '事业发展顺利，有贵人相助，财运亨通。',
    compatibility: {
      best: ['鼠', '猴'],
      worst: ['兔', '狗'],
    },
  },
  '蛇': {
    name: '蛇',
    years: '1929, 1941, 1953, 1965, 1977, 1989, 2001, 2013, 2025',
    personality: '智慧优雅，神秘独特，有远见，但有时过于敏感。',
    fortune: '事业稳步上升，财运平稳，感情需要多加沟通。',
    compatibility: {
      best: ['牛', '鸡'],
      worst: ['虎', '猪'],
    },
  },
  '马': {
    name: '马',
    years: '1930, 1942, 1954, 1966, 1978, 1990, 2002, 2014, 2026',
    personality: '活力充沛，热情开朗，追求自由，但易浮躁。',
    fortune: '事业发展机会多，财运波动大，感情生活精彩。',
    compatibility: {
      best: ['虎', '羊'],
      worst: ['鼠', '牛'],
    },
  },
  '羊': {
    name: '羊',
    years: '1931, 1943, 1955, 1967, 1979, 1991, 2003, 2015, 2027',
    personality: '温和善良，富有同情心，有艺术天赋，但略显优柔寡断。',
    fortune: '人缘好，财运稳定，事业需要主动把握机会。',
    compatibility: {
      best: ['兔', '马'],
      worst: ['牛', '狗'],
    },
  },
  '猴': {
    name: '猴',
    years: '1932, 1944, 1956, 1968, 1980, 1992, 2004, 2016, 2028',
    personality: '机智灵活，善于交际，创造力强，但有时过于好动。',
    fortune: '事业发展顺利，财运佳，感情生活丰富。',
    compatibility: {
      best: ['鼠', '龙'],
      worst: ['虎', '猪'],
    },
  },
  '鸡': {
    name: '鸡',
    years: '1933, 1945, 1957, 1969, 1981, 1993, 2005, 2017, 2029',
    personality: '勤劳务实，做事认真，表达能力强，但有时过于挑剔。',
    fortune: '事业稳步发展，财运平稳，感情需要多加包容。',
    compatibility: {
      best: ['牛', '蛇'],
      worst: ['兔', '狗'],
    },
  },
  '狗': {
    name: '狗',
    years: '1934, 1946, 1958, 1970, 1982, 1994, 2006, 2018, 2030',
    personality: '忠诚可靠，正直善良，富有正义感，但有时过于保守。',
    fortune: '事业发展稳定，财运平稳，感情生活和谐。',
    compatibility: {
      best: ['虎', '兔'],
      worst: ['龙', '羊'],
    },
  },
  '猪': {
    name: '猪',
    years: '1935, 1947, 1959, 1971, 1983, 1995, 2007, 2019, 2031',
    personality: '诚实善良，为人厚道，富有同情心，但有时过于天真。',
    fortune: '人缘好，财运稳定，事业发展平稳。',
    compatibility: {
      best: ['羊', '兔'],
      worst: ['蛇', '猴'],
    },
  },
};

export default function ZodiacPage() {
  const [selectedZodiac, setSelectedZodiac] = useState<string>('鼠');
  const zodiacInfo = zodiacData[selectedZodiac];

  const currentYear = new Date().getFullYear();
  const solar = Solar.fromYmd(currentYear, 1, 1);
  const currentZodiac = solar.getLunar().getYearShengXiao();

  return (
    <div>
      <Title level={2}>十二生肖信息</Title>
      <Paragraph>
        十二生肖是中国传统文化中的重要组成部分，每个生肖都有其独特的性格特征和运势特点。
      </Paragraph>

      <Alert
        className="mb-6"
        message={`今年（${currentYear}）是${currentZodiac}年`}
        type="info"
        showIcon
      />
      
      <Card className="mb-6">
        <Select
          className="w-full"
          value={selectedZodiac}
          onChange={setSelectedZodiac}
          options={Object.keys(zodiacData).map(zodiac => ({
            label: zodiac,
            value: zodiac,
          }))}
        />
      </Card>

      {zodiacInfo && (
        <Row gutter={[16, 16]}>
          <Col xs={24} md={12}>
            <Card title="基本信息">
              <Descriptions bordered column={1}>
                <Descriptions.Item label="生肖">{zodiacInfo.name}</Descriptions.Item>
                <Descriptions.Item label="年份">{zodiacInfo.years}</Descriptions.Item>
                <Descriptions.Item label="性格特征">{zodiacInfo.personality}</Descriptions.Item>
              </Descriptions>
            </Card>
          </Col>
          <Col xs={24} md={12}>
            <Card title="运势分析">
              <Descriptions bordered column={1}>
                <Descriptions.Item label="运势概述">{zodiacInfo.fortune}</Descriptions.Item>
                <Descriptions.Item label="最佳配对">
                  {zodiacInfo.compatibility.best.join('、')}
                </Descriptions.Item>
                <Descriptions.Item label="最差配对">
                  {zodiacInfo.compatibility.worst.join('、')}
                </Descriptions.Item>
              </Descriptions>
            </Card>
          </Col>
        </Row>
      )}
    </div>
  );
} 