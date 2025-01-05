import React, { useState } from 'react';
import { PageContainer } from '@ant-design/pro-components';
import { Card, DatePicker, Button, Typography, Table, Alert, Tag, Row, Col, Space } from 'antd';
import type { Dayjs } from 'dayjs';
import { getBaZi, getLunarInfo } from '@/services/lunar';
import styles from './index.less';

const { Title, Text } = Typography;

// 五行颜色映射
const wuXingColors: Record<string, string> = {
  '木': '#4CAF50',
  '火': '#FF5722',
  '土': '#FF9800',
  '金': '#FFD700',
  '水': '#2196F3'
};

const BaziPage: React.FC = () => {
  const [selectedDateTime, setSelectedDateTime] = useState<Dayjs | null>(null);
  const [baziResult, setBaziResult] = useState<string[]>([]);
  const [lunarInfo, setLunarInfo] = useState<any>(null);

  const handleCalculate = () => {
    if (selectedDateTime) {
      const date = selectedDateTime.toDate();
      const bazi = getBaZi(date, selectedDateTime.hour());
      setBaziResult(bazi);
      
      const lunar = getLunarInfo(date);
      setLunarInfo(lunar);
    }
  };

  const renderGanZhi = (ganZhi: { gan: string; zhi: string; wuXing: string }) => {
    const [ganWuXing, zhiWuXing] = ganZhi.wuXing.split('');
    return (
      <div className={styles.ganzhiBox}>
        <Tag 
          className={styles.ganzhiTag} 
          color={wuXingColors[ganWuXing]}
        >
          {ganZhi.gan}
        </Tag>
        <Tag 
          className={styles.ganzhiTag} 
          color={wuXingColors[zhiWuXing]}
        >
          {ganZhi.zhi}
        </Tag>
      </div>
    );
  };

  const columns = [
    {
      title: '年柱',
      dataIndex: 'year',
      key: 'year',
      align: 'center' as const,
      render: () => lunarInfo && renderGanZhi(lunarInfo.ganZhi.year),
    },
    {
      title: '月柱',
      dataIndex: 'month',
      key: 'month',
      align: 'center' as const,
      render: () => lunarInfo && renderGanZhi(lunarInfo.ganZhi.month),
    },
    {
      title: '日柱',
      dataIndex: 'day',
      key: 'day',
      align: 'center' as const,
      render: () => lunarInfo && renderGanZhi(lunarInfo.ganZhi.day),
    },
    {
      title: '时柱',
      dataIndex: 'hour',
      key: 'hour',
      align: 'center' as const,
      render: () => lunarInfo && renderGanZhi(lunarInfo.ganZhi.hour),
    },
  ];

  return (
    <PageContainer>
      <Card bordered={false}>
        <Space direction="vertical" size="large" style={{ width: '100%' }}>
          <div className={styles.header}>
            <DatePicker 
              showTime
              style={{ width: 200 }} 
              onChange={setSelectedDateTime}
              placeholder="选择日期和时间"
              format="YYYY-MM-DD HH:mm"
            />
            <Button type="primary" onClick={handleCalculate}>
              计算
            </Button>
          </div>

          {lunarInfo && (
            <>
              <Card className={styles.baziCard} bordered={false}>
                <Table 
                  dataSource={[{}]} 
                  columns={columns} 
                  pagination={false}
                  className={styles.baziTable}
                />
              </Card>
              
              <Card className={styles.wuxingCard} bordered={false} title="五行分布">
                <Row gutter={[24, 24]} justify="space-around" align="middle">
                  {Object.entries(lunarInfo.wuXing).map(([element, count]) => (
                    <Col key={element}>
                      <div className={styles.wuxingItem}>
                        <Tag color={wuXingColors[element]} className={styles.wuxingTag}>
                          {element}
                        </Tag>
                        <span className={styles.wuxingCount}>{count}个</span>
                      </div>
                    </Col>
                  ))}
                </Row>
              </Card>
              
              <Card className={styles.lunarCard} bordered={false}>
                <Row gutter={[32, 16]}>
                  <Col span={8}>
                    <div className={styles.infoItem}>
                      <Text className={styles.infoLabel}>农历</Text>
                      <Text className={styles.infoValue}>
                        {lunarInfo.yearInChinese}年 {lunarInfo.monthInChinese}月 {lunarInfo.dayInChinese}
                      </Text>
                    </div>
                  </Col>
                  <Col span={8}>
                    <div className={styles.infoItem}>
                      <Text className={styles.infoLabel}>生肖</Text>
                      <Text className={styles.infoValue}>{lunarInfo.zodiac}</Text>
                    </div>
                  </Col>
                  {lunarInfo.term && (
                    <Col span={8}>
                      <div className={styles.infoItem}>
                        <Text className={styles.infoLabel}>节气</Text>
                        <Text className={styles.infoValue}>{lunarInfo.term}</Text>
                      </div>
                    </Col>
                  )}
                </Row>
              </Card>
            </>
          )}
        </Space>
      </Card>
    </PageContainer>
  );
};

export default BaziPage; 