'use client';

import React, { useState } from 'react';
import { Card, Form, DatePicker, TimePicker, Button, Typography, Descriptions } from 'antd';
import { Solar } from 'lunar-typescript';
import dayjs from 'dayjs';

const { Title } = Typography;

interface BaziResult {
  year: string;
  month: string;
  day: string;
  hour: string;
  yearHidden: string[];
  monthHidden: string[];
  dayHidden: string[];
  hourHidden: string[];
}

export default function BaziPage() {
  const [form] = Form.useForm();
  const [result, setResult] = useState<BaziResult | null>(null);

  const onFinish = (values: any) => {
    const { birthDate, birthTime } = values;
    const date = birthDate.toDate();
    const time = birthTime.toDate();
    
    // 合并日期和时间
    date.setHours(time.getHours());
    date.setMinutes(time.getMinutes());
    
    // 使用 lunar-typescript 计算八字
    const solar = Solar.fromDate(date);
    const lunar = solar.getLunar();
    const eightChar = lunar.getEightChar();
    
    setResult({
      year: eightChar.getYear(),
      month: eightChar.getMonth(),
      day: eightChar.getDay(),
      hour: eightChar.getTime(),
      yearHidden: eightChar.getYearHideGan(),
      monthHidden: eightChar.getMonthHideGan(),
      dayHidden: eightChar.getDayHideGan(),
      hourHidden: eightChar.getTimeHideGan(),
    });
  };

  return (
    <div>
      <Title level={2}>生辰八字计算</Title>
      <Card className="mb-6">
        <Form
          form={form}
          layout="vertical"
          onFinish={onFinish}
          initialValues={{
            birthDate: dayjs(),
            birthTime: dayjs(),
          }}
        >
          <Form.Item
            label="出生日期"
            name="birthDate"
            rules={[{ required: true, message: '请选择出生日期' }]}
          >
            <DatePicker className="w-full" />
          </Form.Item>
          <Form.Item
            label="出生时间"
            name="birthTime"
            rules={[{ required: true, message: '请选择出生时间' }]}
          >
            <TimePicker className="w-full" format="HH:mm" />
          </Form.Item>
          <Form.Item>
            <Button type="primary" htmlType="submit">
              计算八字
            </Button>
          </Form.Item>
        </Form>
      </Card>

      {result && (
        <Card title="八字分析结果">
          <Descriptions bordered column={1}>
            <Descriptions.Item label="年柱">{result.year} (藏干: {result.yearHidden.join(', ')})</Descriptions.Item>
            <Descriptions.Item label="月柱">{result.month} (藏干: {result.monthHidden.join(', ')})</Descriptions.Item>
            <Descriptions.Item label="日柱">{result.day} (藏干: {result.dayHidden.join(', ')})</Descriptions.Item>
            <Descriptions.Item label="时柱">{result.hour} (藏干: {result.hourHidden.join(', ')})</Descriptions.Item>
          </Descriptions>
        </Card>
      )}
    </div>
  );
} 