import React, { useState } from 'react';
import { PageContainer } from '@ant-design/pro-components';
import { Card, DatePicker, Button, Radio, Space, message } from 'antd';
import type { Dayjs } from 'dayjs';
import { calculateZiWei } from '@/services/ziwei';
import ZiWeiChart from '@/components/ZiWeiChart';
import styles from './index.less';

const ZiweiPage: React.FC = () => {
  const [selectedDateTime, setSelectedDateTime] = useState<Dayjs | null>(null);
  const [gender, setGender] = useState<'male' | 'female'>('male');
  const [ziWeiResult, setZiWeiResult] = useState<any>(null);

  const handleCalculate = () => {
    if (selectedDateTime) {
      const result = calculateZiWei(
        selectedDateTime.year(),
        selectedDateTime.month() + 1,
        selectedDateTime.date(),
        selectedDateTime.hour(),
        gender
      );
      setZiWeiResult(result);
    }
  };

  return (
    <PageContainer>
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

          {ziWeiResult && (
            <ZiWeiChart 
              data={ziWeiResult} 
              onTimeChange={async (params) => {
                try {
                  // 调用紫微斗数运限计算接口
                  const horoscope = await calculateZiWei(
                    selectedDateTime!.year(),
                    selectedDateTime!.month() + 1,
                    selectedDateTime!.date(),
                    selectedDateTime!.hour(),
                    gender,
                    params
                  );
                  setZiWeiResult(horoscope);
                } catch (error) {
                  console.error('计算运限失败:', error);
                  message.error('计算运限失败');
                }
              }}
            />
          )}
        </Space>
      </Card>
    </PageContainer>
  );
};

export default ZiweiPage; 