import React, { useState } from 'react';
import { PageContainer } from '@ant-design/pro-components';
import { Card, DatePicker, Button, Typography, Table, Tag, Row, Col, Space, Divider } from 'antd';
import type { Dayjs } from 'dayjs';
import { getBaZi, getLunarInfo } from '@/services/lunar';
import styles from './index.less';

const { Text } = Typography;

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
        <Space direction="vertical" size="middle" style={{ width: '100%' }}>
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
            <Card className={styles.resultCard} bordered={false}>
              <Row gutter={[16, 16]}>
                {/* 八字表格区域 */}
                <Col span={24}>
                  <Table 
                    dataSource={[{}]} 
                    columns={columns} 
                    pagination={false}
                    className={styles.baziTable}
                  />
                </Col>

                {/* 信息展示区域 */}
                <Col span={24}>
                  <Row gutter={[32, 16]} className={styles.infoSection}>
                    {/* 基本信息 */}
                    <Col span={8}>
                      <div className={styles.infoBlock}>
                        <Text className={styles.infoTitle}>基本信息</Text>
                        <div className={styles.infoContent}>
                          <div className={styles.infoItem}>
                            <Text className={styles.infoLabel}>阳历：</Text>
                            <Text>{selectedDateTime?.format('YYYY年MM月DD日 HH:mm')}</Text>
                          </div>
                          <div className={styles.infoItem}>
                            <Text className={styles.infoLabel}>农历：</Text>
                            <Text>{lunarInfo.yearInChinese}年 {lunarInfo.monthInChinese}月 {lunarInfo.dayInChinese}</Text>
                          </div>
                          <div className={styles.infoItem}>
                            <Text className={styles.infoLabel}>真太阳时：</Text>
                            <Text>{selectedDateTime?.format('HH:mm')}</Text>
                          </div>
                        </div>
                      </div>
                    </Col>

                    {/* 命理信息 */}
                    <Col span={8}>
                      <div className={styles.infoBlock}>
                        <Text className={styles.infoTitle}>命理信息</Text>
                        <div className={styles.infoContent}>
                          <div className={styles.infoItem}>
                            <Text className={styles.infoLabel}>生肖：</Text>
                            <Text>{lunarInfo.zodiac}</Text>
                          </div>
                          <div className={styles.infoItem}>
                            <Text className={styles.infoLabel}>星座：</Text>
                            <Text>{lunarInfo.constellation}</Text>
                          </div>
                          {lunarInfo.term && (
                            <div className={styles.infoItem}>
                              <Text className={styles.infoLabel}>节气：</Text>
                              <Text>{lunarInfo.term}</Text>
                            </div>
                          )}
                        </div>
                      </div>
                    </Col>

                    {/* 五行分布 */}
                    <Col span={8}>
                      <div className={styles.infoBlock}>
                        <Text className={styles.infoTitle}>五行分布</Text>
                        <div className={styles.wuxingContent}>
                          {Object.entries(lunarInfo.wuXing).map(([element, count]) => (
                            <div key={element} className={styles.wuxingItem}>
                              <Tag color={wuXingColors[element]} className={styles.wuxingTag}>
                                {element}
                              </Tag>
                              <Text className={styles.wuxingCount}>{count}个</Text>
                            </div>
                          ))}
                        </div>
                      </div>
                    </Col>
                  </Row>
                </Col>
              </Row>
            </Card>
          )}
        </Space>
      </Card>
    </PageContainer>
  );
};

export default BaziPage; 