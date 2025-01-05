'use client';

import { PageContainer, ProCard } from '@ant-design/pro-components';
import { DatePicker, Form, Radio, Button, Typography } from 'antd';
import type { Dayjs } from 'dayjs';
import dayjs from 'dayjs';

const { Title, Text } = Typography;

const BaZiPage: React.FC = () => {
  const [form] = Form.useForm();

  const onFinish = (values: any) => {
    console.log('计算结果:', values);
    // TODO: 实现八字计算逻辑
  };

  return (
    <PageContainer
      header={{
        title: '生辰八字计算',
        subTitle: '根据阳历或阴历生日计算生辰八字',
      }}
    >
      <div className="space-y-6">
        <ProCard
          title={<span className="text-white/85">生辰八字计算器</span>}
          bordered={false}
          className="bg-[#1f1f1f] hover:bg-[#252525] transition-all"
          headerBordered
        >
          <Form
            form={form}
            layout="vertical"
            onFinish={onFinish}
            initialValues={{
              calendarType: 'solar',
              datetime: dayjs(),
              gender: 'male',
            }}
          >
            <Form.Item
              label={<span className="text-white/85">历法选择</span>}
              name="calendarType"
            >
              <Radio.Group>
                <Radio value="solar">阳历</Radio>
                <Radio value="lunar">阴历</Radio>
              </Radio.Group>
            </Form.Item>

            <Form.Item
              label={<span className="text-white/85">出生日期时间</span>}
              name="datetime"
              rules={[{ required: true, message: '请选择出生日期时间' }]}
            >
              <DatePicker
                showTime
                format="YYYY-MM-DD HH:mm"
                placeholder="选择日期和时间"
                className="w-full"
              />
            </Form.Item>

            <Form.Item
              label={<span className="text-white/85">性别</span>}
              name="gender"
            >
              <Radio.Group>
                <Radio value="male">男</Radio>
                <Radio value="female">女</Radio>
              </Radio.Group>
            </Form.Item>

            <Form.Item>
              <Button type="primary" htmlType="submit" className="w-full">
                开始计算
              </Button>
            </Form.Item>
          </Form>
        </ProCard>

        <ProCard
          title={<span className="text-white/85">计算结果</span>}
          bordered={false}
          className="bg-[#1f1f1f] hover:bg-[#252525] transition-all"
          headerBordered
        >
          <div className="space-y-4">
            <div>
              <Text className="text-white/60">八字：</Text>
              <Text className="text-white/85">待计算</Text>
            </div>
            <div>
              <Text className="text-white/60">五行：</Text>
              <Text className="text-white/85">待计算</Text>
            </div>
            <div>
              <Text className="text-white/60">生肖：</Text>
              <Text className="text-white/85">待计算</Text>
            </div>
          </div>
        </ProCard>
      </div>
    </PageContainer>
  );
};

export default BaZiPage; 