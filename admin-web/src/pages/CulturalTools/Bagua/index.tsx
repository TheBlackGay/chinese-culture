import React, { useState } from 'react';
import { PageContainer } from '@ant-design/pro-components';
import { Card, Button, Typography, Row, Col, Space, Steps, Input, Radio, Modal, Divider, Tag, DatePicker, message } from 'antd';
import { getHexagramByTime, getHexagramByNumber, getHexagramBySelection, interpretHexagram } from '@/services/bagua';
import styles from './index.less';

const { Title, Text, Paragraph } = Typography;
const { TextArea } = Input;

// 八卦基本信息
const baguaInfo = [
  { name: '乾', nature: '天', attribute: '金', symbol: '☰', meaning: '刚健、君主、父亲', direction: '西北' },
  { name: '坤', nature: '地', attribute: '土', symbol: '☷', meaning: '柔顺、母亲、大地', direction: '西南' },
  { name: '震', nature: '雷', attribute: '木', symbol: '☳', meaning: '动、长男、雷电', direction: '东' },
  { name: '巽', nature: '风', attribute: '木', symbol: '☴', meaning: '顺、长女、风', direction: '东南' },
  { name: '坎', nature: '水', attribute: '水', symbol: '☵', meaning: '陷、中男、水', direction: '北' },
  { name: '离', nature: '火', attribute: '火', symbol: '☲', meaning: '丽、中女、火', direction: '南' },
  { name: '艮', nature: '山', attribute: '土', symbol: '☶', meaning: '止、少男、山', direction: '东北' },
  { name: '兑', nature: '泽', attribute: '金', symbol: '☱', meaning: '悦、少女、泽', direction: '西' }
];

// 起卦方式
const diviningMethods = [
  { label: '时间卦', value: 'time', description: '根据当前时间自动生成卦象' },
  { label: '数字卦', value: 'number', description: '选择一个1-50之间的数字生成卦象' },
  { label: '心里卦', value: 'mind', description: '凭直觉选择一个卦象' }
];

const BaguaPage: React.FC = () => {
  const [currentStep, setCurrentStep] = useState(0);
  const [question, setQuestion] = useState('');
  const [method, setMethod] = useState<string>('');
  const [selectedNumber, setSelectedNumber] = useState<number | null>(null);
  const [selectedUpperBagua, setSelectedUpperBagua] = useState<string | null>(null);
  const [selectedLowerBagua, setSelectedLowerBagua] = useState<string | null>(null);
  const [isModalVisible, setIsModalVisible] = useState(false);
  const [hexagramResult, setHexagramResult] = useState<any>(null);
  const [selectedTime, setSelectedTime] = useState<string>('');

  // 步骤内容
  const steps = [
    {
      title: '确定问题',
      content: (
        <div className={styles.stepContent}>
          <Paragraph>
            请描述您想要占卜的问题或事项。问题越具体，解卦越准确。
          </Paragraph>
          <TextArea
            rows={4}
            placeholder="例如：我最近的事业发展如何？"
            value={question}
            onChange={(e) => setQuestion(e.target.value)}
            className={styles.questionInput}
          />
        </div>
      ),
    },
    {
      title: '选择起卦方式',
      content: (
        <div className={styles.stepContent}>
          <Paragraph>
            请选择一种起卦方式，不同的方式会带来不同的体验。
          </Paragraph>
          <Radio.Group onChange={(e) => setMethod(e.target.value)} value={method}>
            <Space direction="vertical">
              {diviningMethods.map((item) => (
                <Radio key={item.value} value={item.value}>
                  <div className={styles.methodItem}>
                    <Text strong>{item.label}</Text>
                    <Text type="secondary">{item.description}</Text>
                  </div>
                </Radio>
              ))}
            </Space>
          </Radio.Group>
        </div>
      ),
    },
    {
      title: '起卦',
      content: (
        <div className={styles.stepContent}>
          {method === 'time' && (
            <div className={styles.stepContent}>
              <DatePicker
                showTime
                placeholder="选择或输入时间"
                onChange={(date, dateString) => setSelectedTime(dateString as string)}
                style={{ width: 200 }}
              />
              <div style={{ marginTop: 8, color: '#666' }}>
                不选择时间则使用当前时间
              </div>
            </div>
          )}
          {method === 'number' && (
            <div>
              <Input
                type="number"
                min={1}
                max={50}
                placeholder="请输入1-50之间的数字"
                onChange={(e) => setSelectedNumber(Number(e.target.value))}
              />
            </div>
          )}
          {method === 'mind' && (
            <div className={styles.stepContent}>
              <div className={styles.baguaSelection}>
                <div className={styles.selectionGroup}>
                  <Title level={5}>选择上卦</Title>
                  <Radio.Group onChange={(e) => setSelectedUpperBagua(e.target.value)} value={selectedUpperBagua}>
                    <Space direction="vertical">
                      {baguaInfo.map((bagua) => (
                        <Radio key={bagua.name} value={bagua.name}>
                          <Space>
                            <span className={styles.baguaSymbol}>{bagua.symbol}</span>
                            <span>{bagua.name}卦（{bagua.nature}）- {bagua.meaning}</span>
                          </Space>
                        </Radio>
                      ))}
                    </Space>
                  </Radio.Group>
                </div>

                <Divider />

                <div className={styles.selectionGroup}>
                  <Title level={5}>选择下卦</Title>
                  <Radio.Group onChange={(e) => setSelectedLowerBagua(e.target.value)} value={selectedLowerBagua}>
                    <Space direction="vertical">
                      {baguaInfo.map((bagua) => (
                        <Radio key={bagua.name} value={bagua.name}>
                          <Space>
                            <span className={styles.baguaSymbol}>{bagua.symbol}</span>
                            <span>{bagua.name}卦（{bagua.nature}）- {bagua.meaning}</span>
                          </Space>
                        </Radio>
                      ))}
                    </Space>
                  </Radio.Group>
                </div>
              </div>
            </div>
          )}
        </div>
      ),
    },
  ];

  // 处理下一步
  const handleNext = () => {
    if (currentStep === 0 && !question) {
      Modal.warning({
        title: '请填写问题',
        content: '需要填写具体的问题才能继续下一步',
      });
      return;
    }
    if (currentStep === 1 && !method) {
      Modal.warning({
        title: '请选择起卦方式',
        content: '需要选择一种起卦方式才能继续下一步',
      });
      return;
    }
    setCurrentStep(currentStep + 1);
  };

  // 处理上一步
  const handlePrev = () => {
    setCurrentStep(currentStep - 1);
  };

  // 处理起卦
  const handleDivination = () => {
    let hexagram;
    try {
      switch (method) {
        case 'time':
          hexagram = selectedTime ? getHexagramByTime(selectedTime) : getHexagramByTime();
          break;
        case 'number':
          if (selectedNumber) {
            hexagram = getHexagramByNumber(selectedNumber);
          }
          break;
        case 'mind':
          if (selectedUpperBagua && selectedLowerBagua) {
            hexagram = getHexagramBySelection(selectedUpperBagua, selectedLowerBagua);
          } else {
            throw new Error('请选择上卦和下卦');
          }
          break;
        default:
          throw new Error('请选择起卦方式');
      }

      if (hexagram) {
        const interpretation = interpretHexagram(hexagram);
        console.log('卦象解读结果：', interpretation);
        setHexagramResult({
          hexagram,
          interpretation,
          question
        });
        setIsModalVisible(true);
      }
    } catch (error) {
      console.error('起卦失败：', error);
      Modal.error({
        title: '起卦失败',
        content: (error as Error).message || '请检查输入是否正确',
      });
    }
  };

  // 渲染卦象结果
  const renderHexagramResult = () => {
    if (!hexagramResult?.interpretation) return null;

    const { interpretation, question } = hexagramResult;
    return (
      <div className={styles.resultContent}>
        <div className={styles.questionSection}>
          <Text strong>所问事项：</Text>
          <Text>{question}</Text>
        </div>
        
        <Divider />
        
        <div className={styles.hexagramSection}>
          <Row gutter={[24, 24]}>
            <Col span={12}>
              <Card title="上卦" bordered={false}>
                <div className={styles.trigramInfo}>
                  <div className={styles.symbol}>{interpretation.upperTrigram.symbol}</div>
                  <div className={styles.details}>
                    <div>{interpretation.upperTrigram.name}（{interpretation.upperTrigram.nature}）</div>
                    <div>五行属性：{interpretation.upperTrigram.attribute}</div>
                    <div>{interpretation.upperTrigram.meaning}</div>
                  </div>
                </div>
              </Card>
            </Col>
            <Col span={12}>
              <Card title="下卦" bordered={false}>
                <div className={styles.trigramInfo}>
                  <div className={styles.symbol}>{interpretation.lowerTrigram.symbol}</div>
                  <div className={styles.details}>
                    <div>{interpretation.lowerTrigram.name}（{interpretation.lowerTrigram.nature}）</div>
                    <div>五行属性：{interpretation.lowerTrigram.attribute}</div>
                    <div>{interpretation.lowerTrigram.meaning}</div>
                  </div>
                </div>
              </Card>
            </Col>
          </Row>
        </div>
        
        <Divider />
        
        <div className={styles.analysisSection}>
          <Title level={4}>卦象解读</Title>
          <div className={styles.analysisItem}>
            <Text strong>卦名：</Text>
            <Text>{interpretation.name}</Text>
          </div>
          <div className={styles.analysisItem}>
            <Text strong>卦意：</Text>
            <Text>{interpretation.meaning}</Text>
          </div>
          <div className={styles.analysisItem}>
            <Text strong>五行关系：</Text>
            <Text>{interpretation.wuxingAnalysis}</Text>
          </div>
          {interpretation.yaoAnalysis && interpretation.yaoAnalysis.length > 0 && (
            <div className={styles.analysisItem}>
              <Text strong>变爻：</Text>
              <div className={styles.lineAnalysis}>
                {interpretation.yaoAnalysis.map((analysis: string, index: number) => (
                  <Tag key={index} color="blue">{analysis}</Tag>
                ))}
              </div>
            </div>
          )}
          <div className={styles.analysisItem}>
            <Text strong>总体解读：</Text>
            <Paragraph>{interpretation.overall}</Paragraph>
          </div>
        </div>
      </div>
    );
  };

  return (
    <PageContainer>
      <Card>
        <Space direction="vertical" size="large" style={{ width: '100%' }}>
          <Title level={4}>八卦占卜</Title>
          <Paragraph>
            八卦占卜是中国传统文化中的一种预测方法，通过演算卦象来预测事物的发展趋势。
            占卜前请保持心境平和，专注于您想要解答的问题。
          </Paragraph>
          
          <Steps current={currentStep} items={steps} />
          
          <div className={styles.stepsContent}>
            {steps[currentStep].content}
          </div>
          
          <div className={styles.stepsAction}>
            {currentStep > 0 && (
              <Button style={{ margin: '0 8px' }} onClick={handlePrev}>
                上一步
              </Button>
            )}
            {currentStep < steps.length - 1 && (
              <Button type="primary" onClick={handleNext}>
                下一步
              </Button>
            )}
            {currentStep === steps.length - 1 && (
              <Button type="primary" onClick={handleDivination}>
                开始占卜
              </Button>
            )}
          </div>
        </Space>
      </Card>

      {/* 结果展示弹窗 */}
      <Modal
        title="卦象解读"
        open={isModalVisible}
        onOk={() => setIsModalVisible(false)}
        onCancel={() => setIsModalVisible(false)}
        width={800}
        footer={[
          <Button key="close" type="primary" onClick={() => setIsModalVisible(false)}>
            关闭
          </Button>
        ]}
      >
        {renderHexagramResult()}
      </Modal>
    </PageContainer>
  );
};

export default BaguaPage; 