'use client';

import React, { useState } from 'react';
import { Card, Form, Input, Button, Typography, Descriptions, Alert, Row, Col } from 'antd';

const { Title, Paragraph, Text } = Typography;

interface NameAnalysisResult {
  tianGe: number;
  renGe: number;
  diGe: number;
  waiGe: number;
  zongGe: number;
  analysis: {
    tianGe: string;
    renGe: string;
    diGe: string;
    waiGe: string;
    zongGe: string;
    overall: string;
  };
}

// 计算汉字的笔画数
const getStrokeCount = (char: string): number => {
  // 这里应该使用一个完整的汉字笔画数据库
  // 这里只是示例数据
  const strokeData: { [key: string]: number } = {
    '王': 4, '李': 7, '张': 11, '刘': 6, '陈': 10, '杨': 7,
    '黄': 12, '赵': 9, '吴': 7, '周': 8, '徐': 10, '孙': 10,
    '马': 10, '朱': 6, '胡': 10, '郭': 11, '何': 7, '高': 10,
    '林': 8, '罗': 8, '郑': 9, '梁': 11, '谢': 12, '宋': 7,
    '唐': 10, '许': 6, '韩': 12, '冯': 9, '邓': 8, '曹': 11,
    '彭': 12, '曾': 12, '萧': 12, '田': 5, '董': 12, '袁': 10,
    '潘': 15, '于': 3, '蒋': 13, '蔡': 14, '余': 7, '杜': 7,
    '叶': 5, '程': 12, '魏': 15, '苏': 9, '丁': 2, '任': 6,
    '卢': 5, '姚': 9, '钟': 9, '姜': 9, '崔': 11, '谭': 12,
    '廖': 13, '范': 8, '汪': 7, '陆': 7, '金': 8, '石': 5,
    '戴': 15, '贾': 11, '韦': 4, '夏': 10, '邱': 7, '方': 4,
    '侯': 9, '邹': 11, '熊': 14, '孟': 8, '秦': 10, '白': 5,
  };
  return strokeData[char] || 0;
};

// 计算五格数理
const calculateWuGe = (familyName: string, givenName: string): NameAnalysisResult => {
  const name = familyName + givenName;
  const strokes = name.split('').map(getStrokeCount);
  
  // 计算五格数理
  const tianGe = familyName.length === 1 ? strokes[0] + 1 : strokes[0] + strokes[1];
  const renGe = strokes.reduce((a, b) => a + b, 0);
  const diGe = givenName.length === 1 ? strokes[strokes.length - 1] + 1 : strokes[strokes.length - 2] + strokes[strokes.length - 1];
  const waiGe = familyName.length === 1 ? strokes[0] + strokes[strokes.length - 1] : strokes[1] + strokes[strokes.length - 1];
  const zongGe = renGe;

  // 简单的吉凶判断（实际应该使用更复杂的算法）
  const getAnalysis = (num: number): string => {
    if ([1, 3, 5, 7, 8, 11, 13, 15, 16, 18, 21, 23, 24, 25, 31, 32, 33, 35, 37, 39, 41, 45, 47, 48, 52, 57, 61, 63, 65, 67, 68, 81].includes(num)) {
      return '吉';
    }
    if ([2, 4, 6, 9, 10, 12, 14, 17, 19, 20, 22, 26, 27, 28, 29, 30, 34, 36, 38, 40, 42, 43, 44, 46, 49, 50, 51, 53, 54, 55, 56, 58, 59, 60, 62, 64, 66, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80].includes(num)) {
      return '凶';
    }
    return '中平';
  };

  return {
    tianGe,
    renGe,
    diGe,
    waiGe,
    zongGe,
    analysis: {
      tianGe: getAnalysis(tianGe),
      renGe: getAnalysis(renGe),
      diGe: getAnalysis(diGe),
      waiGe: getAnalysis(waiGe),
      zongGe: getAnalysis(zongGe),
      overall: [tianGe, renGe, diGe, waiGe, zongGe].map(getAnalysis).filter(a => a === '吉').length >= 3 ? '吉' : '凶',
    },
  };
};

export default function NameAnalysisPage() {
  const [form] = Form.useForm();
  const [result, setResult] = useState<NameAnalysisResult | null>(null);

  const onFinish = (values: any) => {
    const { familyName, givenName } = values;
    const result = calculateWuGe(familyName, givenName);
    setResult(result);
  };

  const getAnalysisColor = (analysis: string) => {
    switch (analysis) {
      case '吉':
        return 'success';
      case '凶':
        return 'error';
      default:
        return 'warning';
    }
  };

  return (
    <div>
      <Title level={2}>姓名五格分析</Title>
      <Paragraph>
        姓名五格是传统文化中对姓名进行数理分析的方法，包括天格、人格、地格、外格和总格五个方面。
      </Paragraph>

      <Card className="mb-6">
        <Form
          form={form}
          layout="inline"
          onFinish={onFinish}
        >
          <Form.Item
            label="姓氏"
            name="familyName"
            rules={[
              { required: true, message: '请输入姓氏' },
              { pattern: /^[\u4e00-\u9fa5]{1,2}$/, message: '请输入1-2个汉字' },
            ]}
          >
            <Input placeholder="请输入姓氏" />
          </Form.Item>
          <Form.Item
            label="名字"
            name="givenName"
            rules={[
              { required: true, message: '请输入名字' },
              { pattern: /^[\u4e00-\u9fa5]{1,2}$/, message: '请输入1-2个汉字' },
            ]}
          >
            <Input placeholder="请输入名字" />
          </Form.Item>
          <Form.Item>
            <Button type="primary" htmlType="submit">
              分析五格
            </Button>
          </Form.Item>
        </Form>
      </Card>

      {result && (
        <Row gutter={[16, 16]}>
          <Col xs={24} md={12}>
            <Card title="数理分析">
              <Descriptions bordered column={1}>
                <Descriptions.Item label="天格">{result.tianGe} ({result.analysis.tianGe})</Descriptions.Item>
                <Descriptions.Item label="人格">{result.renGe} ({result.analysis.renGe})</Descriptions.Item>
                <Descriptions.Item label="地格">{result.diGe} ({result.analysis.diGe})</Descriptions.Item>
                <Descriptions.Item label="外格">{result.waiGe} ({result.analysis.waiGe})</Descriptions.Item>
                <Descriptions.Item label="总格">{result.zongGe} ({result.analysis.zongGe})</Descriptions.Item>
              </Descriptions>
            </Card>
          </Col>
          <Col xs={24} md={12}>
            <Card title="综合分析">
              <Alert
                message={`总体评价：${result.analysis.overall}`}
                type={getAnalysisColor(result.analysis.overall) as any}
                showIcon
                className="mb-4"
              />
              <Paragraph>
                <Text strong>五格释义：</Text>
              </Paragraph>
              <ul className="list-disc pl-6">
                <li>天格：反映先天条件和家庭环境的影响</li>
                <li>人格：反映性格特征和个人发展潜力</li>
                <li>地格：反映后天的生活环境和个人努力</li>
                <li>外格：反映社交能力和与他人的关系</li>
                <li>总格：反映人生总体运势和成就</li>
              </ul>
            </Card>
          </Col>
        </Row>
      )}
    </div>
  );
} 