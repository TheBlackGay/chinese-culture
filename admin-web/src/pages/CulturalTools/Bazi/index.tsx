import React, { useState, useEffect } from 'react';
import { PageContainer } from '@ant-design/pro-components';
import { Card, DatePicker, Button, Typography, Table, Tag, Row, Col, Space, Progress, List, Collapse } from 'antd';
import type { Dayjs } from 'dayjs';
import dayjs from 'dayjs';
import { getBaZi, getLunarInfo } from '@/services/lunar';
import styles from './index.less';
import { CaretRightOutlined } from '@ant-design/icons';

const { Text } = Typography;

// 五行顺序和颜色映射
const wuXingConfig = [
  { element: '金', color: '#FFD700' },
  { element: '木', color: '#4CAF50' },
  { element: '水', color: '#2196F3' },
  { element: '火', color: '#FF5722' },
  { element: '土', color: '#FF9800' },
] as const;

// 五行颜色映射对象（为了兼容其他地方的使用）
const wuXingColors: Record<string, string> = Object.fromEntries(
  wuXingConfig.map(({ element, color }) => [element, color])
);

// 五行最大数量
const MAX_WUXING_COUNT = 5;

// 历史记录最大数量
const MAX_HISTORY_COUNT = 50;
const STORAGE_KEY = 'bazi_history';

interface HistoryRecord {
  id: string;
  datetime: string;
  lunarInfo: any;
  timestamp: number;
}

const BaziPage: React.FC = () => {
  const [selectedDateTime, setSelectedDateTime] = useState<Dayjs | null>(null);
  const [baziResult, setBaziResult] = useState<string[]>([]);
  const [lunarInfo, setLunarInfo] = useState<any>(null);
  const [history, setHistory] = useState<HistoryRecord[]>([]);

  // 加载历史记录
  useEffect(() => {
    const savedHistory = localStorage.getItem(STORAGE_KEY);
    if (savedHistory) {
      setHistory(JSON.parse(savedHistory));
    }
  }, []);

  // 保存历史记录
  const saveHistory = (record: HistoryRecord) => {
    // 检查是否已存在相同时间的记录
    const exists = history.some(item => item.datetime === record.datetime);
    if (exists) {
      return; // 如果已存在，直接返回不添加
    }

    // 如果不存在，添加新记录
    const newHistory = [record, ...history].slice(0, MAX_HISTORY_COUNT);
    setHistory(newHistory);
    localStorage.setItem(STORAGE_KEY, JSON.stringify(newHistory));
  };

  // 从历史记录加载数据
  const loadFromHistory = (record: HistoryRecord) => {
    setSelectedDateTime(dayjs(record.datetime));
    setLunarInfo(record.lunarInfo);
  };

  const handleCalculate = () => {
    if (selectedDateTime) {
      const date = selectedDateTime.toDate();
      const bazi = getBaZi(date, selectedDateTime.hour());
      setBaziResult(bazi);
      
      const lunar = getLunarInfo(date);
      setLunarInfo(lunar);

      // 添加到历史记录
      saveHistory({
        id: Date.now().toString(),
        datetime: selectedDateTime.format('YYYY-MM-DD HH:mm'),
        lunarInfo: lunar,
        timestamp: Date.now(),
      });
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
      <div className={styles.container}>
        {/* 主要内容区域 */}
        <div className={styles.mainContent}>
          <Card bordered={false}>
            <Space direction="vertical" size="middle" style={{ width: '100%' }}>
              <div className={styles.header}>
                <DatePicker 
                  showTime
                  style={{ width: 200 }} 
                  onChange={setSelectedDateTime}
                  value={selectedDateTime}
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
                              {wuXingConfig.map(({ element, color }) => (
                                <div key={element} className={styles.wuxingProgressItem}>
                                  <div className={styles.wuxingLabel}>
                                    <Tag color={color} className={styles.wuxingTag}>
                                      {element}
                                    </Tag>
                                    <span className={styles.wuxingCount}>{lunarInfo.wuXing[element]}</span>
                                  </div>
                                  <Progress
                                    type="line"
                                    percent={(lunarInfo.wuXing[element] / MAX_WUXING_COUNT) * 100}
                                    strokeColor={color}
                                    showInfo={false}
                                    className={styles.wuxingProgress}
                                  />
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
        </div>

        {/* 历史记录侧边栏 */}
        <Collapse
          className={styles.sidebarCollapse}
          defaultActiveKey={['history']}
          expandIcon={({ isActive }) => <CaretRightOutlined rotate={isActive ? 90 : 0} />}
          items={[
            {
              key: 'history',
              label: (
                <div className={styles.collapseHeader}>
                  <span>历史记录</span>
                  <Button 
                    type="link" 
                    size="small"
                    onClick={(e) => {
                      e.stopPropagation();
                      setHistory([]);
                      localStorage.removeItem(STORAGE_KEY);
                    }}
                  >
                    清空
                  </Button>
                </div>
              ),
              children: (
                <List
                  dataSource={history}
                  renderItem={(item) => (
                    <List.Item 
                      key={item.id}
                      className={styles.historyItem}
                      onClick={() => loadFromHistory(item)}
                    >
                      <div className={styles.historyContent}>
                        <div className={styles.historyDateTime}>
                          {item.datetime}
                        </div>
                        <div className={styles.historyInfo}>
                          <Tag>{item.lunarInfo.yearInGanZhi}</Tag>
                          <Tag>{item.lunarInfo.monthInGanZhi}</Tag>
                          <Tag>{item.lunarInfo.dayInGanZhi}</Tag>
                        </div>
                      </div>
                    </List.Item>
                  )}
                />
              ),
            }
          ]}
        />
      </div>
    </PageContainer>
  );
};

export default BaziPage; 