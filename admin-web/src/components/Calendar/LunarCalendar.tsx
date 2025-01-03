'use client';

import React from 'react';
import { Calendar, Badge, Card, Typography } from 'antd';
import type { Dayjs } from 'dayjs';
import { Solar } from 'lunar-typescript';

const { Text } = Typography;

interface CalendarData {
  lunarDay: string;
  lunarMonth: string;
  festivals: string[];
  jieQi?: string;
  suited?: string[];
  avoid?: string[];
}

export default function LunarCalendar() {
  // 获取指定日期的农历信息
  const getCalendarData = (date: Dayjs): CalendarData => {
    try {
      // 使用 lunar-typescript 获取农历信息
      const solar = Solar.fromYmd(date.year(), date.month() + 1, date.date());
      const lunar = solar.getLunar();
      
      return {
        lunarMonth: lunar.getMonthInChinese(),
        lunarDay: lunar.getDayInChinese(),
        festivals: [
          ...lunar.getFestivals(), // 获取农历节日
          ...solar.getFestivals(), // 获取阳历节日
        ],
        jieQi: lunar.getJieQi(), // 获取节气
        suited: lunar.getDayYi(), // 获取宜
        avoid: lunar.getDayJi(), // 获取忌
      };
    } catch (error) {
      console.error('Error getting lunar data:', error);
      return {
        lunarMonth: '',
        lunarDay: '',
        festivals: [],
        suited: [],
        avoid: [],
      };
    }
  };

  // 自定义日期单元格
  const dateCellRender = (date: Dayjs) => {
    const data = getCalendarData(date);
    
    return (
      <div className="lunar-cell">
        <Text className="lunar-day">{data.lunarMonth}月{data.lunarDay}</Text>
        {data.festivals.map((festival, index) => (
          <Badge key={index} status="success" text={festival} />
        ))}
        {data.jieQi && <Badge status="processing" text={data.jieQi} />}
        {data.suited && data.suited.length > 0 && (
          <div className="suited">
            <Text type="success">宜：{data.suited.join('、')}</Text>
          </div>
        )}
        {data.avoid && data.avoid.length > 0 && (
          <div className="avoid">
            <Text type="danger">忌：{data.avoid.join('、')}</Text>
          </div>
        )}
      </div>
    );
  };

  // 自定义月份单元格
  const monthCellRender = (date: Dayjs) => {
    const solar = Solar.fromYmd(date.year(), date.month() + 1, 1);
    const lunar = solar.getLunar();
    return (
      <div className="lunar-month">
        <Text>{lunar.getMonthInChinese()}月</Text>
      </div>
    );
  };

  return (
    <Card title="农历日历" className="lunar-calendar">
      <Calendar
        cellRender={dateCellRender}
        monthCellRender={monthCellRender}
      />
      <style jsx global>{`
        .lunar-calendar .lunar-cell {
          min-height: 80px;
        }
        .lunar-calendar .lunar-day {
          display: block;
          color: #666;
          font-size: 12px;
        }
        .lunar-calendar .ant-badge {
          display: block;
          margin: 2px 0;
          font-size: 12px;
          line-height: 1.2;
        }
        .lunar-calendar .suited,
        .lunar-calendar .avoid {
          font-size: 12px;
          margin-top: 4px;
        }
      `}</style>
    </Card>
  );
} 