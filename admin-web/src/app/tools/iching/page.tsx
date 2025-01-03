'use client';

import React, { useState } from 'react';
import { Button, Card, Space, Typography, Divider, List } from 'antd';
import { getHexagramKey, getHexagramName, getChangedHexagram, HEXAGRAM_DATA } from './data';

const { Title, Text } = Typography;

export default function IChingPage() {
  const [lines, setLines] = useState<number[]>([]);
  const [result, setResult] = useState<string | null>(null);
  const [changedResult, setChangedResult] = useState<string | null>(null);

  // 投掷三枚铜钱
  const throwCoins = () => {
    // 模拟投掷三枚铜钱
    const coins = Array(3).fill(0).map(() => Math.random() < 0.5 ? 2 : 3);
    const sum = coins.reduce((a, b) => a + b, 0);
    
    // 根据和数确定爻的值
    let value: number;
    switch (sum) {
      case 6: // 老阴
        value = 6;
        break;
      case 7: // 少阳
        value = 7;
        break;
      case 8: // 少阴
        value = 8;
        break;
      case 9: // 老阳
        value = 9;
        break;
      default:
        value = 0;
    }

    // 添加新的爻
    const newLines = [...lines, value];
    setLines(newLines);

    // 如果已经有六爻，计算卦象
    if (newLines.length === 6) {
      const hexagramKey = getHexagramKey(newLines);
      setResult(hexagramKey);
      
      // 计算变卦
      const changedKey = getChangedHexagram(newLines);
      setChangedResult(changedKey);
    }
  };

  // 重新开始
  const reset = () => {
    setLines([]);
    setResult(null);
    setChangedResult(null);
  };

  // 渲染卦象信息
  const renderHexagramInfo = (hexagramKey: string | null) => {
    if (!hexagramKey || !HEXAGRAM_DATA[hexagramKey]) return null;
    
    const hexagram = HEXAGRAM_DATA[hexagramKey];
    return (
      <Card style={{ marginTop: 16 }}>
        <Title level={4}>{hexagram.name}卦</Title>
        <Text>{hexagram.description}</Text>
        <Divider />
        <List
          size="small"
          dataSource={hexagram.yaoTexts}
          renderItem={(text, index) => (
            <List.Item>
              <Text>{text}</Text>
            </List.Item>
          )}
        />
      </Card>
    );
  };

  return (
    <div style={{ padding: 24 }}>
      <Title level={2}>周易卦象</Title>
      <Space direction="vertical" style={{ width: '100%' }}>
        <Card>
          <Space>
            <Button type="primary" onClick={throwCoins} disabled={lines.length === 6}>
              投掷铜钱
            </Button>
            <Button onClick={reset}>重新开始</Button>
          </Space>
          <div style={{ marginTop: 16 }}>
            <Text>已投掷 {lines.length} 次</Text>
            {lines.length > 0 && (
              <div style={{ marginTop: 8 }}>
                {lines.map((line, index) => (
                  <div key={index}>
                    第{index + 1}爻：{line === 6 ? '老阴' : line === 7 ? '少阳' : line === 8 ? '少阴' : '老阳'}
                  </div>
                ))}
              </div>
            )}
          </div>
        </Card>

        {result && (
          <>
            <Title level={3}>本卦</Title>
            {renderHexagramInfo(result)}
          </>
        )}

        {changedResult && result !== changedResult && (
          <>
            <Title level={3}>变卦</Title>
            {renderHexagramInfo(changedResult)}
          </>
        )}
      </Space>
    </div>
  );
} 