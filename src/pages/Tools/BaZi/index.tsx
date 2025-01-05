'use client';

import { PageContainer, ProCard } from '@ant-design/pro-components';
import {
  Button,
  DatePicker,
  Form,
  message,
  Radio,
  Tag,
  Typography,
} from 'antd';
import dayjs from 'dayjs';
import { Lunar } from 'lunar-typescript';
import { useState } from 'react';

const { Text } = Typography;

interface BaZiResult {
  yearGanZhi: string;
  monthGanZhi: string;
  dayGanZhi: string;
  timeGanZhi: string;
  zodiac: string;
  wuxing: string[];
  wuxingCount: Record<string, number>;
}

// 五行颜色映射
const wuxingColors: Record<string, string> = {
  木: '#4caf50', // 绿色
  火: '#f44336', // 红色
  土: '#795548', // 棕色
  金: '#ffd700', // 金色
  水: '#2196f3', // 蓝色
};

// 五行顺序
const wuxingOrder = ['木', '火', '土', '金', '水'];

const BaZiPage: React.FC = () => {
  const [form] = Form.useForm();
  const [result, setResult] = useState<BaZiResult | null>(null);

  // 计算五行
  const calculateWuXing = (ganZhi: string) => {
    const wuxingMap: { [key: string]: string } = {
      甲: '木',
      乙: '木',
      丙: '火',
      丁: '火',
      戊: '土',
      己: '土',
      庚: '金',
      辛: '金',
      壬: '水',
      癸: '水',
    };
    return wuxingMap[ganZhi[0]] || '';
  };

  // 统计五行数量
  const countWuxing = (wuxingArray: string[]): Record<string, number> => {
    const count: Record<string, number> = {
      木: 0,
      火: 0,
      土: 0,
      金: 0,
      水: 0,
    };
    wuxingArray.forEach((w) => {
      if (w) count[w]++;
    });
    return count;
  };

  const calculateBaZi = (values: any) => {
    console.log('Form values:', values);
    try {
      const { datetime } = values;
      const date = datetime.toDate();
      console.log('Processing date:', date);

      // 使用农历库计算八字
      const lunar = Lunar.fromDate(date);
      console.log('Lunar instance:', lunar);

      // 获取年月日时的干支
      const yearGanZhi = lunar.getYearInGanZhi();
      console.log('Year GanZhi:', yearGanZhi);
      const monthGanZhi = lunar.getMonthInGanZhi();
      console.log('Month GanZhi:', monthGanZhi);
      const dayGanZhi = lunar.getDayInGanZhi();
      console.log('Day GanZhi:', dayGanZhi);
      const timeGanZhi = lunar.getTimeInGanZhi();
      console.log('Time GanZhi:', timeGanZhi);

      // 计算五行
      const wuxing = [
        calculateWuXing(yearGanZhi),
        calculateWuXing(monthGanZhi),
        calculateWuXing(dayGanZhi),
        calculateWuXing(timeGanZhi),
      ];
      console.log('五行:', wuxing);

      // 统计五行数量
      const wuxingCount = countWuxing(wuxing);
      console.log('五行统计:', wuxingCount);

      // 获取生肖
      const zodiac = lunar.getYearShengXiao();
      console.log('生肖:', zodiac);

      const baZiResult: BaZiResult = {
        yearGanZhi,
        monthGanZhi,
        dayGanZhi,
        timeGanZhi,
        zodiac,
        wuxing,
        wuxingCount,
      };

      console.log('最终结果:', baZiResult);
      setResult(baZiResult);
      message.success('八字计算完成！');
    } catch (error) {
      console.error('计算八字时出错：', error);
      message.error('计算八字时出错，请检查输入日期是否正确');
    }
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
            onFinish={calculateBaZi}
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
                showTime={{ format: 'HH:mm' }}
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
              <Text className="text-white/85" style={{ marginLeft: '8px' }}>
                {result
                  ? `${result.yearGanZhi} ${result.monthGanZhi} ${result.dayGanZhi} ${result.timeGanZhi}`
                  : '待计算'}
              </Text>
            </div>
            <div>
              <Text className="text-white/60">五行：</Text>
              <div className="flex flex-wrap gap-2 mt-2">
                {result &&
                  wuxingOrder.map((wuxing) => (
                    <Tag
                      key={wuxing}
                      color={wuxingColors[wuxing]}
                      style={{
                        borderColor: wuxingColors[wuxing],
                        backgroundColor: `${wuxingColors[wuxing]}1A`,
                      }}
                    >
                      {`${wuxing}: ${result.wuxingCount[wuxing]}`}
                    </Tag>
                  ))}
              </div>
            </div>
            <div>
              <Text className="text-white/60">生肖：</Text>
              <Text className="text-white/85" style={{ marginLeft: '8px' }}>
                {result ? result.zodiac : '待计算'}
              </Text>
            </div>
          </div>
        </ProCard>
      </div>
    </PageContainer>
  );
};

export default BaZiPage;
