'use client';

import React, { useState } from 'react';
import { Calendar, Badge, Card, Typography } from 'antd';
import type { Dayjs } from 'dayjs';
import type { CalendarMode } from 'antd/es/calendar/generateCalendar';
import { Solar } from 'lunar-typescript';

const { Text } = Typography;

interface CalendarData {
  lunar: string;
  solarTerm?: string;
  festivals: string[];
}

export default function LunarCalendar() {
  const [selectedDate, setSelectedDate] = useState<Dayjs>();

  const getCalendarData = (date: Dayjs): CalendarData => {
    try {
      // 获取阳历日期
      const solar = Solar.fromDate(date.toDate());
      // 获取农历日期
      const lunar = solar.getLunar();
      // 获取节气
      const jieQi = lunar.getJieQi();
      // 获取节日
      const festivals = lunar.getFestivals().map(f => f.toString());

      return {
        lunar: `${lunar.getMonthInChinese()}月${lunar.getDayInChinese()}`,
        solarTerm: jieQi,
        festivals,
      };
    } catch (error) {
      console.error('Error getting calendar data:', error);
      return {
        lunar: '',
        festivals: [],
      };
    }
  };

  const dateCellRender = (date: Dayjs) => {
    try {
      const data = getCalendarData(date);
      return (
        <div className="text-xs">
          <div>{data.lunar}</div>
          {data.solarTerm && (
            <div className="text-green-600">{data.solarTerm}</div>
          )}
          {data.festivals.map((festival, index) => (
            <Badge
              key={index}
              status="error"
              text={<Text type="danger">{festival}</Text>}
            />
          ))}
        </div>
      );
    } catch (error) {
      console.error('Error rendering date cell:', error);
      return null;
    }
  };

  const onSelect = (date: Dayjs) => {
    setSelectedDate(date);
  };

  const onPanelChange = (date: Dayjs, mode: CalendarMode) => {
    console.log(date.format('YYYY-MM-DD'), mode);
  };

  return (
    <Card title="农历日历" className="calendar-card">
      <Calendar
        onSelect={onSelect}
        onPanelChange={onPanelChange}
        cellRender={dateCellRender}
        fullscreen={false}
      />
    </Card>
  );
} 