import React, { useState, useEffect } from 'react';
import { PageContainer } from '@ant-design/pro-components';
import { Card, DatePicker, Button, Typography, Table, Tag, Row, Col, Space, Progress, List, Collapse, Tooltip, Tabs, Radio } from 'antd';
import type { Dayjs } from 'dayjs';
import dayjs from 'dayjs';
import { getBaZi, getLunarInfo, getTrueSolarTime } from '@/services/lunar';
import { calculateChengGu } from '@/services/chengGu';
import styles from './index.less';
import { CaretRightOutlined, ExclamationCircleOutlined } from '@ant-design/icons';

const { Text } = Typography;
const { TabPane } = Tabs;

// 五行顺序和颜色映射
const wuXingConfig = [
  { element: '金', color: '#FFD700', description: '金代表着坚强、刚毅，象征着秋天，与肺、大肠相对应。性质坚强、清洁、含蓄。' },
  { element: '木', color: '#4CAF50', description: '木代表着生长、向上，象征着春天，与肝、胆相对应。性质温和、向上、生发。' },
  { element: '水', color: '#2196F3', description: '水代表着智慧、灵活，象征着冬天，与肾、膀胱相对应。性质柔和、灵活、下行。' },
  { element: '火', color: '#FF5722', description: '火代表着温暖、光明，象征着夏天，与心、小肠相对应。性质温暖、上升、明亮。' },
  { element: '土', color: '#FF9800', description: '土代表着包容、中和，象征着季节交替，与脾、胃相对应。性质厚重、包容、中正。' },
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
  gender: 'male' | 'female';
  lunarInfo: any;
  timestamp: number;
}

const BaziPage: React.FC = () => {
  const [selectedDateTime, setSelectedDateTime] = useState<Dayjs | null>(null);
  const [baziResult, setBaziResult] = useState<string[]>([]);
  const [lunarInfo, setLunarInfo] = useState<any>(null);
  const [history, setHistory] = useState<HistoryRecord[]>([]);
  const [activeTab, setActiveTab] = useState<string>('basic');
  const [gender, setGender] = useState<'male' | 'female'>('male');

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
    setGender(record.gender);
    setLunarInfo(record.lunarInfo);
  };

  const handleCalculate = () => {
    if (selectedDateTime) {
      const date = selectedDateTime.toDate();
      const bazi = getBaZi(date, selectedDateTime.hour());
      setBaziResult(bazi);
      
      const lunar = getLunarInfo(date);
      console.log('Lunar info:', lunar);
      
      // 计算称骨结果
      const chengGu = calculateChengGu(
        lunar.yearInGanZhi,
        lunar.month,
        lunar.day,
        lunar.ganZhi.hour.zhi,
        gender
      );
      console.log('ChengGu result:', chengGu);
      
      setLunarInfo({
        ...lunar,
        chengGu
      });

      // 添加到历史记录
      saveHistory({
        id: Date.now().toString(),
        datetime: selectedDateTime.format('YYYY-MM-DD HH:mm'),
        gender,
        lunarInfo: {
          ...lunar,
          chengGu
        },
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

  // 渲染基本信息内容
  const renderBasicInfo = () => (
    <Card className={styles.resultCard} bordered={false}>
      <Row gutter={[16, 16]}>
        {/* 基本信息区域 */}
        <Col span={24}>
          <div className={styles.infoBlock}>
            <Text className={styles.infoTitle}>基本信息</Text>
            <Row gutter={[32, 16]}>
              <Col span={8}>
                <div className={styles.infoItem}>
                  <Text className={styles.infoLabel}>阳历：</Text>
                  <Text>{selectedDateTime?.format('YYYY年MM月DD日 HH:mm')}</Text>
                </div>
              </Col>
              <Col span={8}>
                <div className={styles.infoItem}>
                  <Text className={styles.infoLabel}>农历：</Text>
                  <Text>{lunarInfo.yearInChinese}年 {lunarInfo.monthInChinese}月 {lunarInfo.dayInChinese}</Text>
                </div>
              </Col>
              <Col span={8}>
                <div className={styles.infoItem}>
                  <Text className={styles.infoLabel}>生肖：</Text>
                  <Text>{lunarInfo.zodiac}</Text>
                </div>
              </Col>
              <Col span={8}>
                <div className={styles.infoItem}>
                  <Text className={styles.infoLabel}>星座：</Text>
                  <Text>{lunarInfo.constellation}</Text>
                </div>
              </Col>
              <Col span={8}>
                <div className={styles.infoItem}>
                  <Text className={styles.infoLabel}>节气：</Text>
                  <Text>{lunarInfo.term || '无'}</Text>
                </div>
              </Col>
              <Col span={8}>
                <div className={styles.infoItem}>
                  <Text className={styles.infoLabel}>真太阳时：</Text>
                  <Text>{selectedDateTime ? getTrueSolarTime(selectedDateTime.toDate()) : '--'}</Text>
                </div>
              </Col>
            </Row>
          </div>
        </Col>

        {/* 八字表格区域 */}
        <Col span={24}>
          <Table 
            dataSource={[{ key: 'bazi-row' }]} 
            columns={columns} 
            pagination={false}
            className={styles.baziTable}
          />
        </Col>

        {/* 纳音五行 */}
        <Col span={24}>
          <div className={styles.infoBlock}>
            <Text className={styles.infoTitle}>纳音五行</Text>
            <Row gutter={[16, 16]}>
              <Col span={6}>
                <div className={styles.infoItem}>
                  <Text className={styles.infoLabel}>年柱纳音：</Text>
                  <Text>{lunarInfo.naYin.year}</Text>
                </div>
              </Col>
              <Col span={6}>
                <div className={styles.infoItem}>
                  <Text className={styles.infoLabel}>月柱纳音：</Text>
                  <Text>{lunarInfo.naYin.month}</Text>
                </div>
              </Col>
              <Col span={6}>
                <div className={styles.infoItem}>
                  <Text className={styles.infoLabel}>日柱纳音：</Text>
                  <Text>{lunarInfo.naYin.day}</Text>
                </div>
              </Col>
              <Col span={6}>
                <div className={styles.infoItem}>
                  <Text className={styles.infoLabel}>时柱纳音：</Text>
                  <Text>{lunarInfo.naYin.hour}</Text>
                </div>
              </Col>
            </Row>
          </div>
        </Col>

        {/* 命理信息区域 */}
        <Col span={24}>
          <Row gutter={[32, 16]} className={styles.infoSection}>
            {/* 基本命理 */}
            <Col span={8}>
              <div className={styles.infoBlock}>
                <Text className={styles.infoTitle}>基本命理</Text>
                <div className={styles.infoContent}>
                  {lunarInfo.taiYuan && (
                    <div className={styles.infoItem}>
                      <Text className={styles.infoLabel}>胎元：</Text>
                      <Text>{lunarInfo.taiYuan}</Text>
                    </div>
                  )}
                  {lunarInfo.mingGong && (
                    <div className={styles.infoItem}>
                      <Text className={styles.infoLabel}>命宫：</Text>
                      <Text>{lunarInfo.mingGong}</Text>
                    </div>
                  )}
                  {lunarInfo.shenGong && (
                    <div className={styles.infoItem}>
                      <Text className={styles.infoLabel}>身宫：</Text>
                      <Text>{lunarInfo.shenGong}</Text>
                    </div>
                  )}
                  {lunarInfo.taiXi && (
                    <div className={styles.infoItem}>
                      <Text className={styles.infoLabel}>胎息：</Text>
                      <Text>{lunarInfo.taiXi}</Text>
                    </div>
                  )}
                  {lunarInfo.mingGua && (
                    <div className={styles.infoItem}>
                      <Text className={styles.infoLabel}>命卦：</Text>
                      <Text>{lunarInfo.mingGua}</Text>
                    </div>
                  )}
                  <div className={styles.infoItem}>
                    <Text type="secondary">更多命理信息正在开发中...</Text>
                  </div>
                </div>
              </div>
            </Col>

            {/* 神煞信息 */}
            <Col span={8}>
              <div className={styles.infoBlock}>
                <Text className={styles.infoTitle}>神煞信息</Text>
                <div className={styles.infoContent}>
                  <div className={styles.infoItem}>
                    <Text className={styles.infoLabel}>当前节气：</Text>
                    <Text>{lunarInfo.jieQi.current}</Text>
                  </div>
                  <div className={styles.infoItem}>
                    <Text className={styles.infoLabel}>下一节气：</Text>
                    <Text>{lunarInfo.jieQi.next} ({lunarInfo.jieQi.nextDate})</Text>
                  </div>
                  {lunarInfo.jiShen?.length > 0 && (
                    <div className={styles.infoItem}>
                      <Text className={styles.infoLabel}>吉神：</Text>
                      <div className={styles.tagGroup}>
                        {lunarInfo.jiShen.map((shen: string, index: number) => (
                          <Tag key={index} color="success">{shen}</Tag>
                        ))}
                      </div>
                    </div>
                  )}
                  {lunarInfo.xiongSha?.length > 0 && (
                    <div className={styles.infoItem}>
                      <Text className={styles.infoLabel}>凶煞：</Text>
                      <div className={styles.tagGroup}>
                        {lunarInfo.xiongSha.map((sha: string, index: number) => (
                          <Tag key={index} color="error">{sha}</Tag>
                        ))}
                      </div>
                    </div>
                  )}
                  <div className={styles.infoItem}>
                    <Text type="secondary">更多神煞信息正在开发中...</Text>
                  </div>
                </div>
              </div>
            </Col>

            {/* 五行分布 */}
            <Col span={8}>
              <div className={styles.infoBlock}>
                <Text className={styles.infoTitle}>五行分布</Text>
                <div className={styles.wuxingContent}>
                  {wuXingConfig.map(({ element, color, description }) => (
                    <div key={element} className={styles.wuxingProgressItem}>
                      <div className={styles.wuxingLabel}>
                        <div className={styles.wuxingTitleGroup}>
                          <Tag color={color} className={styles.wuxingTag}>
                            {element}
                          </Tag>
                          <Tooltip title={description} placement="right">
                            <ExclamationCircleOutlined className={styles.wuxingInfo} />
                          </Tooltip>
                        </div>
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

        {/* 十神六亲 */}
        <Col span={24}>
          <div className={styles.infoBlock}>
            <Text className={styles.infoTitle}>十神六亲</Text>
            <Row gutter={[16, 16]}>
              <Col span={12}>
                <div className={styles.infoSubBlock}>
                  <Text className={styles.infoSubTitle}>十神</Text>
                  <div className={styles.infoGrid}>
                    <div className={styles.infoItem}>
                      <Text className={styles.infoLabel}>年柱：</Text>
                      <Text>{lunarInfo.shiShen.year}</Text>
                    </div>
                    <div className={styles.infoItem}>
                      <Text className={styles.infoLabel}>月柱：</Text>
                      <Text>{lunarInfo.shiShen.month}</Text>
                    </div>
                    <div className={styles.infoItem}>
                      <Text className={styles.infoLabel}>日柱：</Text>
                      <Text>{lunarInfo.shiShen.day}</Text>
                    </div>
                    <div className={styles.infoItem}>
                      <Text className={styles.infoLabel}>时柱：</Text>
                      <Text>{lunarInfo.shiShen.hour}</Text>
                    </div>
                  </div>
                </div>
              </Col>
              <Col span={12}>
                <div className={styles.infoSubBlock}>
                  <Text className={styles.infoSubTitle}>六亲</Text>
                  <div className={styles.infoGrid}>
                    <div className={styles.infoItem}>
                      <Text className={styles.infoLabel}>年柱：</Text>
                      <Text>{lunarInfo.liuQin.year}</Text>
                    </div>
                    <div className={styles.infoItem}>
                      <Text className={styles.infoLabel}>月柱：</Text>
                      <Text>{lunarInfo.liuQin.month}</Text>
                    </div>
                    <div className={styles.infoItem}>
                      <Text className={styles.infoLabel}>日柱：</Text>
                      <Text>{lunarInfo.liuQin.day}</Text>
                    </div>
                    <div className={styles.infoItem}>
                      <Text className={styles.infoLabel}>时柱：</Text>
                      <Text>{lunarInfo.liuQin.hour}</Text>
                    </div>
                  </div>
                </div>
              </Col>
            </Row>
          </div>
        </Col>

        {/* 袁天罡称骨 */}
        <Col span={24}>
          <div className={styles.infoBlock}>
            <Text className={styles.infoTitle}>袁天罡称骨</Text>
            <Row gutter={[16, 16]}>
              <Col span={24}>
                <div className={styles.infoItem}>
                  <Text className={styles.infoLabel}>称骨重量：</Text>
                  <Text>{lunarInfo.chengGu?.weight || '--'} 两</Text>
                </div>
                {lunarInfo.chengGu?.description && (
                  <div className={styles.infoItem} style={{ marginTop: 8 }}>
                    <Text>{lunarInfo.chengGu.description}</Text>
                  </div>
                )}
              </Col>
            </Row>
          </div>
        </Col>
      </Row>
    </Card>
  );

  // 渲染基本排盘内容
  const renderBasicChart = () => (
    <Card className={styles.resultCard} bordered={false}>
      <div style={{ textAlign: 'center', padding: '40px 0' }}>
        <Text type="secondary" style={{ fontSize: 16 }}>基本排盘功能开发中...</Text>
      </div>
    </Card>
  );

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
                <Radio.Group 
                  value={gender} 
                  onChange={(e) => setGender(e.target.value)}
                  optionType="button"
                  buttonStyle="solid"
                >
                  <Radio.Button value="male">男命</Radio.Button>
                  <Radio.Button value="female">女命</Radio.Button>
                </Radio.Group>
                <Button type="primary" onClick={handleCalculate}>
                  计算
                </Button>
              </div>

              {lunarInfo && (
                <Tabs 
                  activeKey={activeTab}
                  onChange={setActiveTab}
                  className={styles.contentTabs}
                >
                  <TabPane tab="基本信息" key="basic">
                    {renderBasicInfo()}
                  </TabPane>
                  <TabPane tab="基本排盘" key="chart">
                    {renderBasicChart()}
                  </TabPane>
                </Tabs>
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
                          <Tag color={item.gender === 'male' ? 'blue' : 'pink'} style={{ marginLeft: 8 }}>
                            {item.gender === 'male' ? '男命' : '女命'}
                          </Tag>
                        </div>
                        <div className={styles.historyInfo}>
                          <Tag>{item.lunarInfo.yearInGanZhi}</Tag>
                          <Tag>{item.lunarInfo.monthInGanZhi}</Tag>
                          <Tag>{item.lunarInfo.dayInGanZhi}</Tag>
                          <Tag>{item.lunarInfo.hourInGanZhi}</Tag>
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