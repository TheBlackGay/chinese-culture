import React, { useState } from 'react';
import { PageContainer } from '@ant-design/pro-components';
import { Card, Button, Typography, Row, Col, Space, Steps, Input, Radio, Modal, Divider } from 'antd';
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
  const [selectedBagua, setSelectedBagua] = useState<string | null>(null);
  const [isModalVisible, setIsModalVisible] = useState(false);

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
            <Button type="primary" onClick={() => setIsModalVisible(true)}>
              开始起卦
            </Button>
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
              <Button 
                type="primary" 
                style={{ marginTop: 16 }}
                disabled={!selectedNumber || selectedNumber < 1 || selectedNumber > 50}
                onClick={() => setIsModalVisible(true)}
              >
                确认数字
              </Button>
            </div>
          )}
          {method === 'mind' && (
            <Row gutter={[16, 16]}>
              {baguaInfo.map((bagua) => (
                <Col key={bagua.name} xs={24} sm={12} md={8} lg={6}>
                  <Card
                    hoverable
                    onClick={() => setSelectedBagua(bagua.name)}
                    className={styles.baguaCard}
                    style={{ 
                      borderColor: selectedBagua === bagua.name ? '#1890ff' : undefined,
                      cursor: 'pointer'
                    }}
                  >
                    <div className={styles.baguaSymbol}>{bagua.symbol}</div>
                    <div className={styles.baguaContent}>
                      <Title level={5}>{bagua.name}卦 ({bagua.nature})</Title>
                      <div>属性：{bagua.attribute}</div>
                      <div>方位：{bagua.direction}</div>
                      <div>含义：{bagua.meaning}</div>
                    </div>
                  </Card>
                </Col>
              ))}
            </Row>
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
      >
        <div className={styles.resultContent}>
          {/* 这里后续添加卦象解读的具体内容 */}
          <p>卦象解读结果将在这里显示...</p>
        </div>
      </Modal>
    </PageContainer>
  );
};

export default BaguaPage; 