'use client';

import React, { useState } from 'react';
import { Card, DatePicker, Typography, Table, Tag } from 'antd';
import { Solar } from 'lunar-typescript';
import type { Dayjs } from 'dayjs';
import dayjs from 'dayjs';

const { Title } = Typography;
const { RangePicker } = DatePicker;

interface LuckyDay {
  date: string;
  lunar: string;
  yearGanZhi: string;
  monthGanZhi: string;
  dayGanZhi: string;
  zodiac: string;
  weekDay: string;
  jieQi: string;
  suited: string[];
  avoided: string[];
}

export default function LuckyDaysPage() {
  const [luckyDays, setLuckyDays] = useState<LuckyDay[]>([]);

  const columns = [
    {
      title: '日期',
      dataIndex: 'date',
      key: 'date',
      width: 120,
    },
    {
      title: '农历',
      dataIndex: 'lunar',
      key: 'lunar',
      width: 120,
    },
    {
      title: '干支',
      dataIndex: 'dayGanZhi',
      key: 'dayGanZhi',
      width: 100,
    },
    {
      title: '生肖',
      dataIndex: 'zodiac',
      key: 'zodiac',
      width: 80,
    },
    {
      title: '节气',
      dataIndex: 'jieQi',
      key: 'jieQi',
      width: 100,
      render: (text: string) => text ? <Tag color="green">{text}</Tag> : '-',
    },
    {
      title: '宜',
      dataIndex: 'suited',
      key: 'suited',
      render: (tags: string[]) => (
        <>
          {tags.map(tag => (
            <Tag color="blue" key={tag}>
              {tag}
            </Tag>
          ))}
        </>
      ),
    },
    {
      title: '忌',
      dataIndex: 'avoided',
      key: 'avoided',
      render: (tags: string[]) => (
        <>
          {tags.map(tag => (
            <Tag color="red" key={tag}>
              {tag}
            </Tag>
          ))}
        </>
      ),
    },
  ];

  const onRangeChange = (dates: [Dayjs, Dayjs] | null) => {
    if (!dates) {
      setLuckyDays([]);
      return;
    }

    const [start, end] = dates;
    const days: LuckyDay[] = [];
    let current = start;

    while (current.isBefore(end) || current.isSame(end, 'day')) {
      const solar = Solar.fromDate(current.toDate());
      const lunar = solar.getLunar();

      days.push({
        date: current.format('YYYY-MM-DD'),
        lunar: `${lunar.getMonthInChinese()}月${lunar.getDayInChinese()}`,
        yearGanZhi: lunar.getYearInGanZhi(),
        monthGanZhi: lunar.getMonthInGanZhi(),
        dayGanZhi: lunar.getDayInGanZhi(),
        zodiac: lunar.getYearShengXiao(),
        weekDay: '星期' + '日一二三四五六'.charAt(current.day()),
        jieQi: lunar.getJieQi() || '',
        suited: lunar.getDayYi(),
        avoided: lunar.getDayJi(),
      });

      current = current.add(1, 'day');
    }

    setLuckyDays(days);
  };

  return (
    <div>
      <Title level={2}>黄道吉日查询</Title>
      <Card className="mb-6">
        <RangePicker
          className="w-full"
          onChange={(dates) => onRangeChange(dates as [Dayjs, Dayjs])}
          defaultValue={[dayjs(), dayjs().add(7, 'day')]}
        />
      </Card>

      <Card>
        <Table
          columns={columns}
          dataSource={luckyDays}
          rowKey="date"
          scroll={{ x: 1200 }}
          pagination={{
            defaultPageSize: 7,
            showSizeChanger: true,
            showQuickJumper: true,
          }}
        />
      </Card>
    </div>
  );
} 