import React, { useState, useEffect } from 'react';
import { Card, Typography, Steps, Button, message, DatePicker, Select, Space, Alert, Input, Radio, Descriptions, Tag, Divider, Spin } from 'antd';
import { PageContainer } from '@ant-design/pro-components';
import dayjs from 'dayjs';
import { Lunar } from 'lunar-typescript';
import { calculateLiuRen } from '@/utils/liuren';
import { SAN_CHUAN_DESCRIPTION, GE_JU_DESCRIPTION } from '@/utils/liuren';
import styles from './index.less';

const { Title, Paragraph, Text } = Typography;
const { TextArea } = Input;

// 定义结果类型
interface LiurenResult {
  tianPan: string[];
  diPan: string[];
  siKe: {
    tianKe: string;
    diKe: string;
    renKe: string;
    diZhi: string;
  };
  sanChuan: {
    type: string;
    chu: string;
    zhong: string;
    mo: string;
  };
  geJu: string[];
  shenJiang: string;
  shenJiangAttribute: {
    nature: '吉' | '凶' | '中';
    description: string;
  };
  keTi: {
    type: string;
    description: string;
  };
  analysis: string;
}

// 定义时辰数据
const SHICHEN_DATA = [
  { name: '子时', timeRange: '23:00-1:00', value: '子' },
  { name: '丑时', timeRange: '1:00-3:00', value: '丑' },
  { name: '寅时', timeRange: '3:00-5:00', value: '寅' },
  { name: '卯时', timeRange: '5:00-7:00', value: '卯' },
  { name: '辰时', timeRange: '7:00-9:00', value: '辰' },
  { name: '巳时', timeRange: '9:00-11:00', value: '巳' },
  { name: '午时', timeRange: '11:00-13:00', value: '午' },
  { name: '未时', timeRange: '13:00-15:00', value: '未' },
  { name: '申时', timeRange: '15:00-17:00', value: '申' },
  { name: '酉时', timeRange: '17:00-19:00', value: '酉' },
  { name: '戌时', timeRange: '19:00-21:00', value: '戌' },
  { name: '亥时', timeRange: '21:00-23:00', value: '亥' },
];

// 定义问题类型
const QUESTION_TYPES = [
  { label: '事业学业', value: 'career', description: '与工作、学习、考试相关的问题' },
  { label: '感情婚姻', value: 'love', description: '与恋爱、婚姻、家庭相关的问题' },
  { label: '财运福祉', value: 'wealth', description: '与财运、投资、收益相关的问题' },
  { label: '健康平安', value: 'health', description: '与身体健康、平安相关的问题' },
  { label: '行动决策', value: 'action', description: '与行动、抉择、时机相关的问题' },
  { label: '其他事项', value: 'other', description: '其他类型的问题' },
];

// 定义天干地支数据
const TIAN_GAN = ['甲', '乙', '丙', '丁', '戊', '己', '庚', '辛', '壬', '癸'];
const DI_ZHI = ['子', '丑', '寅', '卯', '辰', '巳', '午', '未', '申', '酉', '戌', '亥'];

const LiurenPage: React.FC = () => {
  const [current, setCurrent] = useState(0);
  const [selectedDate, setSelectedDate] = useState<dayjs.Dayjs | null>(null);
  const [selectedTime, setSelectedTime] = useState<string>('');
  const [loading, setLoading] = useState(false);
  const [question, setQuestion] = useState('');
  const [questionType, setQuestionType] = useState('');
  const [result, setResult] = useState<LiurenResult | null>(null);

  // 获取当前时辰
  const getCurrentShichen = (date: dayjs.Dayjs) => {
    const hour = date.hour();
    let shichenIndex = Math.floor(((hour + 1) % 24) / 2);
    if (shichenIndex === 12) shichenIndex = 0;
    return SHICHEN_DATA[shichenIndex].value;
  };

  // 处理日期选择
  const handleDateSelect = (date: dayjs.Dayjs | null) => {
    setSelectedDate(date);
    if (date) {
      const shichen = getCurrentShichen(date);
      setSelectedTime(shichen);
    }
  };

  // 处理占卜计算
  const handleCalculate = async () => {
    if (selectedDate && selectedTime && questionType && question) {
      setLoading(true);
      try {
        const result = calculateLiuRen(
          selectedDate.toDate(),
          selectedTime,
          questionType
        );
        if (!result) {
          throw new Error('计算结果为空');
        }
        setResult(result);
        setCurrent(2);
      } catch (error) {
        console.error('计算出错：', error);
        message.error({
          content: '计算出错，请检查输入并重试',
          duration: 3
        });
        // 重置结果
        setResult(null);
      } finally {
        setLoading(false);
      }
    } else {
      message.warning('请完整填写所有必要信息');
    }
  };

  // 添加重置方法
  const handleReset = () => {
    setSelectedDate(null);
    setSelectedTime('');
    setQuestion('');
    setQuestionType('');
    setResult(null);
    setCurrent(0);
  };

  // 渲染不同步骤的内容
  const renderStepContent = () => {
    switch (current) {
      case 0:
        return (
          <div className={styles.timeSelection}>
            <Alert
              message="请选择占卜时间"
              description="大六壬占卜需要准确的日期和时辰，请认真选择。系统会根据选择的时间自动判断时辰，您也可以手动调整。"
              type="info"
              showIcon
              style={{ marginBottom: 24 }}
            />
            <Space direction="vertical" size="large" style={{ width: '100%' }}>
              <div>
                <div className={styles.label}>选择日期和时间：</div>
                <DatePicker
                  value={selectedDate}
                  onChange={handleDateSelect}
                  style={{ width: '100%' }}
                  showTime
                  format="YYYY-MM-DD HH:mm:ss"
                  placeholder="请选择日期和时间"
                />
              </div>
              <div>
                <div className={styles.label}>当前时辰：</div>
                <Select
                  value={selectedTime}
                  onChange={(value) => setSelectedTime(value)}
                  options={SHICHEN_DATA.map(item => ({
                    label: `${item.name}（${item.timeRange}）`,
                    value: item.value
                  }))}
                  style={{ width: '100%' }}
                  placeholder="请选择时辰"
                />
              </div>
            </Space>
          </div>
        );
      case 1:
        return (
          <div className={styles.questionSection}>
            <Alert
              message="请设置占卜问题"
              description="大六壬占卜需要明确的问题，请选择问题类型并详细描述您的问题。问题越具体，得到的指引越准确。"
              type="info"
              showIcon
              style={{ marginBottom: 24 }}
            />
            <Space direction="vertical" size="large" style={{ width: '100%' }}>
              <div>
                <div className={styles.label}>选择问题类型：</div>
                <Radio.Group
                  value={questionType}
                  onChange={(e) => setQuestionType(e.target.value)}
                  className={styles.questionTypes}
                >
                  <Space direction="vertical">
                    {QUESTION_TYPES.map(type => (
                      <Radio key={type.value} value={type.value}>
                        <Space direction="vertical" size={0}>
                          <Text>{type.label}</Text>
                          <Text type="secondary" className={styles.typeDescription}>
                            {type.description}
                          </Text>
                        </Space>
                      </Radio>
                    ))}
                  </Space>
                </Radio.Group>
              </div>
              <div>
                <div className={styles.label}>描述您的问题：</div>
                <TextArea
                  value={question}
                  onChange={(e) => setQuestion(e.target.value)}
                  placeholder="请详细描述您的问题，例如：我目前正在考虑是否要换工作，想知道近期的职业发展方向..."
                  autoSize={{ minRows: 4, maxRows: 6 }}
                  maxLength={500}
                  showCount
                />
              </div>
              <Alert
                message="问题设置建议"
                type="warning"
                showIcon
                description={
                  <ul className={styles.tips}>
                    <li>问题要具体明确，避免模糊或过于宽泛的描述</li>
                    <li>一次只问一个问题，避免多个问题混杂</li>
                    <li>避免简单的是否类问题，建议询问方向和建议</li>
                    <li>保持诚心和敬畏之心，切勿戏谑或试探</li>
                  </ul>
                }
              />
            </Space>
          </div>
        );
      case 2:
        if (!result) {
          return (
            <div className={styles.resultSection}>
              <Card className={styles.resultCard}>
                <div style={{ textAlign: 'center', padding: '24px' }}>
                  {loading ? (
                    <Spin tip="正在推算..." />
                  ) : (
                    <div>
                      <Alert
                        message="计算出错"
                        description="抱歉，计算过程中出现错误，请重试。"
                        type="error"
                        showIcon
                        style={{ marginBottom: 16 }}
                      />
                      <Button type="primary" onClick={handleCalculate}>
                        重新计算
                      </Button>
                    </div>
                  )}
                </div>
              </Card>
            </div>
          );
        }
        return (
          <div className={styles.resultSection}>
            <Card className={styles.resultCard} title="天盘地盘">
              <div className={styles.panLayout}>
                <div className={styles.tianPan} data-title="天盘">
                  {result.tianPan.map((gan, index) => (
                    <div key={`tian-${index}`} className={styles.cell}>
                      {gan}
                    </div>
                  ))}
                </div>
                <div className={styles.diPan} data-title="地盘">
                  {result.diPan.map((zhi, index) => (
                    <div key={`di-${index}`} className={styles.cell}>
                      {zhi}
                    </div>
                  ))}
                </div>
              </div>
            </Card>

            <Card className={styles.resultCard} title="四课">
              <div className={styles.siKeLayout}>
                <div className={styles.siKeItem}>
                  <span className={styles.label}>天课：</span>
                  <span className={styles.value}>{result.siKe.tianKe}</span>
                </div>
                <div className={styles.siKeItem}>
                  <span className={styles.label}>地课：</span>
                  <span className={styles.value}>{result.siKe.diKe}</span>
                </div>
                <div className={styles.siKeItem}>
                  <span className={styles.label}>人课：</span>
                  <span className={styles.value}>{result.siKe.renKe}</span>
                </div>
                <div className={styles.siKeItem}>
                  <span className={styles.label}>时支：</span>
                  <span className={styles.value}>{result.siKe.diZhi}</span>
                </div>
              </div>
            </Card>

            <Card className={styles.resultCard} title="三传">
              <div className={styles.sanChuanLayout}>
                <div className={styles.sanChuanType}>
                  <Tag color="blue">{result.sanChuan.type}</Tag>
                </div>
                <div className={styles.sanChuanDetail}>
                  <div className={styles.sanChuanItem}>
                    <span className={styles.label}>初传</span>
                    <span className={styles.value}>{result.sanChuan.chu}</span>
                  </div>
                  <div className={styles.sanChuanItem}>
                    <span className={styles.label}>中传</span>
                    <span className={styles.value}>{result.sanChuan.zhong}</span>
                  </div>
                  <div className={styles.sanChuanItem}>
                    <span className={styles.label}>末传</span>
                    <span className={styles.value}>{result.sanChuan.mo}</span>
                  </div>
                </div>
                <div className={styles.sanChuanDesc}>
                  {SAN_CHUAN_DESCRIPTION[result.sanChuan.type]}
                </div>
              </div>
            </Card>

            <Card className={styles.resultCard} title="格局">
              <div className={styles.geJuLayout}>
                {result.geJu.map((ju, index) => (
                  <div key={index} className={styles.geJuItem}>
                    <Tag color={getGeJuColor(ju)}>{ju}</Tag>
                    <div className={styles.geJuDesc}>{GE_JU_DESCRIPTION[ju]}</div>
                  </div>
                ))}
              </div>
            </Card>

            <Card className={styles.resultCard} title="综合分析">
              <div className={styles.analysisLayout}>
                <div className={styles.basicInfo}>
                  <div className={styles.infoItem}>
                    <span className={styles.label}>神将：</span>
                    <Tag color={getShenJiangColor(result.shenJiang)}>
                      {result.shenJiang}
                    </Tag>
                  </div>
                  <div className={styles.infoItem}>
                    <span className={styles.label}>性质：</span>
                    <Tag color={getNatureColor(result.shenJiangAttribute.nature)}>
                      {result.shenJiangAttribute.nature}
                    </Tag>
                  </div>
                  <div className={styles.infoItem}>
                    <span className={styles.label}>课体：</span>
                    <Tag color="blue">{result.keTi.type}</Tag>
                  </div>
                </div>
                <div className={styles.analysis}>
                  {result.analysis.split('\n').map((line, index) => (
                    <Typography.Paragraph key={index}>
                      {line}
                    </Typography.Paragraph>
                  ))}
                </div>
              </div>
            </Card>
          </div>
        );
      default:
        return null;
    }
  };

  // 检查当前步骤是否可以继续
  const canProceed = () => {
    switch (current) {
      case 0:
        return selectedDate && selectedTime;
      case 1:
        return questionType && question.trim().length >= 10;
      case 2:
        return true;
      default:
        return false;
    }
  };

  // 获取格局标签颜色
  const getGeJuColor = (geJu: string) => {
    const colorMap: Record<string, string> = {
      '天德': 'gold',
      '天乙': 'magenta',
      '天荣': 'red',
      '地德': 'green',
      '地乙': 'cyan',
      '地荣': 'blue',
      '人德': 'purple',
      '人乙': 'geekblue',
      '人荣': 'volcano',
      '空亡': 'default',
      '金舆': 'orange',
      '月德': 'lime',
      '天马': 'processing'
    };
    return colorMap[geJu] || 'default';
  };

  // 获取神将标签颜色
  const getShenJiangColor = (shenJiang: string) => {
    const colorMap: Record<string, string> = {
      '贵人': 'gold',
      '腾蛇': 'magenta',
      '朱雀': 'red',
      '六合': 'green',
      '勾陈': 'cyan',
      '青龙': 'blue',
      '天空': 'purple',
      '白虎': 'geekblue',
      '太常': 'volcano',
      '玄武': 'default',
      '太阴': 'lime',
      '天后': 'processing'
    };
    return colorMap[shenJiang] || 'default';
  };

  // 获取性质标签颜色
  const getNatureColor = (nature: '吉' | '凶' | '中') => {
    const colorMap: Record<string, string> = {
      '吉': 'success',
      '凶': 'error',
      '中': 'warning'
    };
    return colorMap[nature] || 'default';
  };

  return (
    <PageContainer>
      <Card className={styles.liurenCard}>
        <Title level={2}>大六壬占卜</Title>
        <Paragraph>
          大六壬是中国传统文化中的一种重要占卜方法，具有操作严谨、预测准确的特点。通过选择特定的时间，配合您的问题，可以得到相应的指导和启示。
        </Paragraph>
        
        <Steps
          current={current}
          items={[
            {
              title: '选择时间',
              description: '选择占卜时间',
            },
            {
              title: '设置问题',
              description: '明确你想要占卜的问题',
            },
            {
              title: '获取结果',
              description: '解读占卜结果',
            },
          ]}
        />

        <div className={styles.stepContent}>
          {renderStepContent()}
        </div>

        <div className={styles.stepsAction}>
          {current > 0 && (
            <Button 
              style={{ margin: '0 8px' }} 
              onClick={() => setCurrent(current - 1)}
            >
              上一步
            </Button>
          )}
          {current === 2 ? (
            <Space>
              <Button onClick={handleReset}>
                重新开始
              </Button>
              <Button type="primary" onClick={handleCalculate}>
                重新计算
              </Button>
            </Space>
          ) : (
            <Button 
              type="primary"
              disabled={!canProceed()}
              loading={loading}
              onClick={() => {
                if (current === 1) {
                  handleCalculate();
                } else {
                  setCurrent(current + 1);
                }
              }}
            >
              {current === 1 ? '开始计算' : '下一步'}
            </Button>
          )}
        </div>
      </Card>
    </PageContainer>
  );
};

export default LiurenPage; 