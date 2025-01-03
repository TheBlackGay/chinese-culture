'use client';

import React, { useState, useCallback } from 'react';
import { Button, Card, Space, Typography, Radio, DatePicker, Row, Col, Divider } from 'antd';
import styled from 'styled-components';
import dayjs from 'dayjs';
import { calculateHexagram, calculateManualHexagram, getChangedHexagram } from '../utils/hexagram';
import { BASE_HEXAGRAMS } from '../data/base';
import { Hexagram } from '../types';
import HexagramDisplay from './HexagramDisplay';
import HexagramDetail from './HexagramDetail';

const { Title, Text } = Typography;

const CalculatorContainer = styled.div`
  margin: 20px 0;
  max-width: 1200px;
  width: 100%;
`;

const CoinContainer = styled.div`
  display: flex;
  gap: 16px;
  margin: 16px 0;
`;

const Coin = styled.div<{ value: number }>`
  width: 40px;
  height: 40px;
  border-radius: 50%;
  background-color: ${({ value }) => (value === 2 ? '#FFD700' : '#C0C0C0')};
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  transition: all 0.3s ease;
  &:hover {
    transform: scale(1.1);
  }
`;

const ResultContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 16px;
  margin-top: 24px;
`;

const HexagramRow = styled(Row)`
  background: #fafafa;
  padding: 16px;
  border-radius: 8px;
  margin: 0 !important;
`;

const HexagramCard = styled(Card)`
  height: 100%;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
  
  .ant-card-body {
    padding: 16px;
  }
`;

const HexagramCalculator: React.FC = () => {
  const [calculationType, setCalculationType] = useState<'time' | 'manual'>('time');
  const [selectedDate, setSelectedDate] = useState(dayjs());
  const [currentHexagram, setCurrentHexagram] = useState<Hexagram | null>(null);
  const [changedHexagram, setChangedHexagram] = useState<Hexagram | null>(null);
  const [coins, setCoins] = useState<number[][]>([]);
  const [currentThrow, setCurrentThrow] = useState<number[]>([2, 2, 2]);
  const [changingLines, setChangingLines] = useState<number[]>([]);

  const calculateByTime = useCallback(() => {
    const hexagramKey = calculateHexagram(selectedDate.toDate());
    const hexagram = BASE_HEXAGRAMS[hexagramKey];
    setCurrentHexagram(hexagram);
    setChangedHexagram(null);
    setChangingLines([]);
  }, [selectedDate]);

  const throwCoins = useCallback(() => {
    if (coins.length >= 6) return;

    const sum = currentThrow.reduce((a, b) => a + b, 0);
    const newCoins = [...coins, currentThrow];
    setCoins(newCoins);

    if (newCoins.length === 6) {
      const hexagramKey = calculateManualHexagram(newCoins);
      const changedKey = getChangedHexagram(newCoins);
      
      setCurrentHexagram(BASE_HEXAGRAMS[hexagramKey]);
      
      // 计算变爻
      const newChangingLines = newCoins.map((throwResult, index) => {
        const sum = throwResult.reduce((a, b) => a + b, 0);
        return sum === 6 || sum === 9 ? index : -1;
      }).filter(index => index !== -1);
      
      setChangingLines(newChangingLines);

      if (hexagramKey !== changedKey) {
        setChangedHexagram(BASE_HEXAGRAMS[changedKey]);
      }
    }

    setCurrentThrow([2, 2, 2]);
  }, [coins, currentThrow]);

  const toggleCoin = useCallback((index: number) => {
    setCurrentThrow(prev => {
      const newThrow = [...prev];
      newThrow[index] = newThrow[index] === 2 ? 3 : 2;
      return newThrow;
    });
  }, []);

  const resetManual = useCallback(() => {
    setCoins([]);
    setCurrentThrow([2, 2, 2]);
    setCurrentHexagram(null);
    setChangedHexagram(null);
    setChangingLines([]);
  }, []);

  return (
    <CalculatorContainer>
      <Space direction="vertical" style={{ width: '100%' }}>
        <Card title="卦象计算方式">
          <Radio.Group
            value={calculationType}
            onChange={(e) => {
              setCalculationType(e.target.value);
              setCurrentHexagram(null);
              setChangedHexagram(null);
              resetManual();
            }}
          >
            <Radio.Button value="time">时间起卦</Radio.Button>
            <Radio.Button value="manual">手动起卦</Radio.Button>
          </Radio.Group>

          {calculationType === 'time' && (
            <Space direction="vertical" style={{ width: '100%', marginTop: 16 }}>
              <DatePicker
                showTime
                value={selectedDate}
                onChange={(date) => date && setSelectedDate(date)}
                style={{ width: '100%' }}
              />
              <Button type="primary" onClick={calculateByTime}>
                计算卦象
              </Button>
            </Space>
          )}

          {calculationType === 'manual' && (
            <Space direction="vertical" style={{ width: '100%', marginTop: 16 }}>
              <Text>已投掷 {coins.length} 次，还需投掷 {6 - coins.length} 次</Text>
              <CoinContainer>
                {currentThrow.map((value, index) => (
                  <Coin
                    key={index}
                    value={value}
                    onClick={() => toggleCoin(index)}
                  >
                    {value}
                  </Coin>
                ))}
              </CoinContainer>
              <Space>
                <Button type="primary" onClick={throwCoins} disabled={coins.length >= 6}>
                  投掷铜钱
                </Button>
                <Button onClick={resetManual}>重新开始</Button>
              </Space>
              {coins.length > 0 && (
                <div>
                  {coins.map((throwResult, index) => (
                    <div key={index}>
                      第{index + 1}爻：{throwResult.join(' ')} = {throwResult.reduce((a, b) => a + b, 0)}
                    </div>
                  ))}
                </div>
              )}
            </Space>
          )}
        </Card>

        {currentHexagram && (
          <ResultContainer>
            <HexagramRow gutter={[16, 16]}>
              <Col span={8}>
                <HexagramCard>
                  <div style={{ textAlign: 'center' }}>
                    <Title level={4} style={{ margin: '0 0 16px 0' }}>
                      {currentHexagram.name}卦
                    </Title>
                    <HexagramDisplay
                      hexagram={currentHexagram.key}
                      changingLines={changingLines}
                      size="large"
                      animated
                    />
                  </div>
                </HexagramCard>
              </Col>
              <Col span={16}>
                <HexagramDetail
                  hexagram={currentHexagram}
                  changingLines={changingLines}
                />
              </Col>
            </HexagramRow>

            {changedHexagram && (
              <>
                <Divider>变卦</Divider>
                <HexagramRow gutter={[16, 16]}>
                  <Col span={8}>
                    <HexagramCard>
                      <div style={{ textAlign: 'center' }}>
                        <Title level={4} style={{ margin: '0 0 16px 0' }}>
                          {changedHexagram.name}卦
                        </Title>
                        <HexagramDisplay
                          hexagram={changedHexagram.key}
                          size="large"
                          animated
                        />
                      </div>
                    </HexagramCard>
                  </Col>
                  <Col span={16}>
                    <HexagramDetail
                      hexagram={changedHexagram}
                      isChanged
                    />
                  </Col>
                </HexagramRow>
              </>
            )}
          </ResultContainer>
        )}
      </Space>
    </CalculatorContainer>
  );
};

export default HexagramCalculator; 