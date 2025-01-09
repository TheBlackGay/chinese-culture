import React, { useState, useEffect } from 'react';
import { Table, Button } from 'antd';
import { RightOutlined, LeftOutlined } from '@ant-design/icons';
import type { ColumnsType } from 'antd/es/table';
import './index.less';

interface HoroscopeSelectorProps {
  startYear: number;
  currentYear: number;
  onTimeChange: (params: {
    decadal?: number;
    year?: number;
    month?: number;
    day?: number;
    hour?: number;
  }) => void;
}

interface TimeRange {
  decadal?: number;
  year?: number;
  month?: number;
  day?: number;
  hour?: number;
}

interface PageOffsets {
  decadal: number;
  year: number;
  month: number;
  day: number;
  hour: number;
}

// 数字转中文数字
const numberToChinese = (num: number): string => {
  const chineseNumbers = ['零', '一', '二', '三', '四', '五', '六', '七', '八', '九', '十'];
  if (num <= 10) return chineseNumbers[num];
  if (num < 20) return `十${num > 10 ? chineseNumbers[num - 10] : ''}`;
  const tens = Math.floor(num / 10);
  const ones = num % 10;
  return `${chineseNumbers[tens]}十${ones > 0 ? chineseNumbers[ones] : ''}`;
};

// 获取年份的天干地支
const getYearGanZhi = (year: number): string => {
  const gan = '甲乙丙丁戊己庚辛壬癸';
  const zhi = '子丑寅卯辰巳午未申酉戌亥';
  const ganIndex = (year - 4) % 10;
  const zhiIndex = (year - 4) % 12;
  return `${gan[ganIndex]}${zhi[zhiIndex]}`;
};

// 获取大限的天干地支
const getDecadalGanZhi = (decadalValue: number): string => {
  // TODO: 这里需要根据实际规则计算大限的天干地支
  return '甲戌';
};

const HoroscopeSelector: React.FC<HoroscopeSelectorProps> = ({
  startYear,
  currentYear,
  onTimeChange,
}) => {
  const [selectedTime, setSelectedTime] = useState<TimeRange>({});
  const [pageOffsets, setPageOffsets] = useState<PageOffsets>({
    decadal: 0,
    year: 0,
    month: 0,
    day: 0,
    hour: 0,
  });

  // 计算命宫大限开始年龄
  const getMingGongStartAge = () => {
    return 6; // 这里应该根据实际命宫计算，暂时固定为6岁
  };

  // 生成指定数量的连续数字数组
  const getPageNumbers = (offset: number, length: number = 10) => 
    Array.from({ length }, (_, i) => i + offset);

  // 生成大限数据
  const getDecadalData = (numbers: number[]) => numbers.map(num => {
    const mingGongStartAge = getMingGongStartAge();
    if (num === 0) {
      return {
        key: `decadal-${num}`,
        value: `童限(${numberToChinese(1)}～${numberToChinese(mingGongStartAge - 1)}岁)`,
        type: 'decadal',
        decadalValue: num,
        startAge: 1,
        endAge: mingGongStartAge - 1
      };
    }
    const startAge = mingGongStartAge + (num - 1) * 10;
    const endAge = startAge + 9;
    const ganZhi = getDecadalGanZhi(num);
    return {
      key: `decadal-${num}`,
      value: `${numberToChinese(startAge)}～${numberToChinese(endAge)}岁\n${ganZhi}限`,
      type: 'decadal',
      decadalValue: num,
      startAge,
      endAge
    };
  });

  // 根据年龄获取对应的年份
  const getYearByAge = (age: number) => startYear + age - 1;

  // 获取默认大限数据
  const getDefaultDecadalData = () => {
    const mingGongStartAge = getMingGongStartAge();
    return {
      decadalValue: 1,
      startAge: mingGongStartAge,
      endAge: mingGongStartAge + 9
    };
  };

  // 生成流年数据
  const getYearData = (numbers: number[]) => {
    const decadalData = selectedTime.decadal !== undefined
      ? getDecadalData(getPageNumbers(0))[selectedTime.decadal]
      : getDefaultDecadalData();

    const yearStartAge = decadalData.startAge;
    const yearEndAge = decadalData.endAge;
    const yearStartYear = getYearByAge(yearStartAge);
    const yearEndYear = getYearByAge(yearEndAge);
    const yearRange = Array.from(
      { length: yearEndYear - yearStartYear + 1 },
      (_, i) => yearStartYear + i
    );
    
    return numbers.map(num => {
      const year = yearRange[num + pageOffsets.year];
      if (!year) return null;
      const age = year - startYear + 1;
      const ganZhi = getYearGanZhi(year);
      return {
        key: `year-${num}`,
        value: `${year}年\n${ganZhi}${numberToChinese(age)}岁`,
        type: 'year',
        yearValue: year
      };
    }).filter(Boolean);
  };

  // 生成流月数据
  const getMonthData = (numbers: number[]) => {
    if (!selectedTime.year) {
      return numbers.map(num => ({
        key: `month-${num}`,
        value: `${numberToChinese(num + 1)}月`,
        type: 'month',
        monthValue: num + 1
      }));
    }

    return numbers.map(num => {
      const month = num + pageOffsets.month + 1;
      if (month > 12) return null;
      return {
        key: `month-${num}`,
        value: `${numberToChinese(month)}月`,
        type: 'month',
        monthValue: month
      };
    }).filter(Boolean);
  };

  // 生成流日数据
  const getDayData = (numbers: number[]) => {
    if (!selectedTime.month) {
      return [
        numbers.map(num => ({
          key: `day1-${num}`,
          value: `初${numberToChinese(num + 1)}`,
          type: 'day',
          dayValue: num + 1
        })),
        numbers.map(num => ({
          key: `day2-${num}`,
          value: `十${numberToChinese(num + 1)}`,
          type: 'day',
          dayValue: num + 11
        })),
        numbers.map(num => ({
          key: `day3-${num}`,
          value: `廿${numberToChinese(num + 1)}`,
          type: 'day',
          dayValue: num + 21
        }))
      ];
    }

    const daysInMonth = new Date(selectedTime.year!, selectedTime.month!, 0).getDate();
    const days = Array.from({ length: daysInMonth }, (_, i) => i + 1);
    const offset = pageOffsets.day * 10;
    
    return [
      days.slice(offset, offset + 10).map((day, index) => ({
        key: `day-${day}`,
        value: day <= 10 ? `初${numberToChinese(day)}` : 
               day <= 20 ? `十${numberToChinese(day - 10)}` : 
               `廿${numberToChinese(day - 20)}`,
        type: 'day',
        dayValue: day
      }))
    ];
  };

  // 生成流时数据
  const getHourData = () => {
    const timeNames = ['子', '丑', '寅', '卯', '辰', '巳', '午', '未', '申', '酉', '戌', '亥'];
    return timeNames.map((name, index) => ({
      key: `hour-${index}`,
      value: `${name}时`,
      type: 'hour',
      hourValue: index
    }));
  };

  // 处理翻页
  const handlePageChange = (type: keyof PageOffsets, direction: 'prev' | 'next') => {
    setPageOffsets(prev => ({
      ...prev,
      [type]: Math.max(0, prev[type] + (direction === 'prev' ? -1 : 1))
    }));
  };

  // 处理单元格点击
  const handleCellClick = (record: any) => {
    const newSelectedTime = { ...selectedTime };
    switch (record.type) {
      case 'decadal':
        newSelectedTime.decadal = record.decadalValue;
        // 清除下级选择
        delete newSelectedTime.year;
        delete newSelectedTime.month;
        delete newSelectedTime.day;
        delete newSelectedTime.hour;
        // 重置下级页码
        setPageOffsets(prev => ({
          ...prev,
          year: 0,
          month: 0,
          day: 0,
          hour: 0
        }));
        break;
      case 'year':
        newSelectedTime.year = record.yearValue;
        // 清除下级选择
        delete newSelectedTime.month;
        delete newSelectedTime.day;
        delete newSelectedTime.hour;
        // 重置下级页码
        setPageOffsets(prev => ({
          ...prev,
          month: 0,
          day: 0,
          hour: 0
        }));
        break;
      case 'month':
        newSelectedTime.month = record.monthValue;
        // 清除下级选择
        delete newSelectedTime.day;
        delete newSelectedTime.hour;
        // 重置下级页码
        setPageOffsets(prev => ({
          ...prev,
          day: 0,
          hour: 0
        }));
        break;
      case 'day':
        newSelectedTime.day = record.dayValue;
        // 清除下级选择
        delete newSelectedTime.hour;
        // 重置下级页码
        setPageOffsets(prev => ({
          ...prev,
          hour: 0
        }));
        break;
      case 'hour':
        newSelectedTime.hour = record.hourValue;
        break;
    }
    setSelectedTime(newSelectedTime);
    onTimeChange(newSelectedTime);
  };

  // 检查单元格是否被选中
  const isCellSelected = (record: any) => {
    switch (record.type) {
      case 'decadal':
        return selectedTime.decadal === record.decadalValue;
      case 'year':
        return selectedTime.year === record.yearValue;
      case 'month':
        return selectedTime.month === record.monthValue;
      case 'day':
        return selectedTime.day === record.dayValue;
      case 'hour':
        return selectedTime.hour === record.hourValue;
      default:
        return false;
    }
  };

  // 生成表格数据
  const getTableData = () => {
    const decadalNumbers = getPageNumbers(pageOffsets.decadal);
    const yearNumbers = getPageNumbers(pageOffsets.year);
    const monthNumbers = getPageNumbers(pageOffsets.month);
    const dayNumbers = getPageNumbers(pageOffsets.day);

    return [
      { 
        key: 'decadal',
        label: '大限',
        type: 'decadal',
        data: getDecadalData(decadalNumbers),
        canPrevPage: pageOffsets.decadal > 0,
        canNextPage: true
      },
      { 
        key: 'year',
        label: '流年',
        type: 'year',
        data: getYearData(yearNumbers),
        canPrevPage: pageOffsets.year > 0,
        canNextPage: true
      },
      { 
        key: 'month',
        label: '流月',
        type: 'month',
        data: getMonthData(monthNumbers),
        canPrevPage: pageOffsets.month > 0,
        canNextPage: selectedTime.year ? (pageOffsets.month + 1) * 10 < 12 : true
      },
      ...getDayData(dayNumbers).map((row, index) => ({
        key: `day-${index}`,
        label: '流日',
        type: 'day',
        data: row,
        canPrevPage: pageOffsets.day > 0,
        canNextPage: selectedTime.month ? (pageOffsets.day + 1) * 10 < new Date(selectedTime.year!, selectedTime.month!, 0).getDate() : true
      })),
      { 
        key: 'hour',
        label: '流时',
        type: 'hour',
        data: getHourData(),
        canPrevPage: false,
        canNextPage: false
      }
    ];
  };

  // 生成列配置
  const columns: ColumnsType<any> = [
    {
      title: '',
      dataIndex: 'label',
      key: 'label',
      width: 80,
      fixed: 'left',
      className: 'label-column'
    },
    ...Array(10).fill(0).map((_, num) => ({
      title: '',
      dataIndex: ['data', num, 'value'],
      key: num,
      width: 120,
      render: (value: string, record: any) => {
        const cellData = record.data?.[num];
        return cellData ? (
          <div
            className={`cell-content ${record.key}-cell ${isCellSelected(cellData) ? 'selected' : ''}`}
            onClick={() => handleCellClick(cellData)}
          >
            {value.split('\n').map((line, index) => (
              <div key={index} className={index === 1 ? 'sub-text' : ''}>
                {line}
              </div>
            ))}
          </div>
        ) : null;
      }
    })),
    {
      title: '',
      key: 'action',
      width: 80,
      render: (_, record) => (
        <div className="page-actions">
          <Button
            type="text"
            icon={<LeftOutlined />}
            onClick={() => handlePageChange(record.type, 'prev')}
            disabled={!record.canPrevPage}
          />
          <Button
            type="text"
            icon={<RightOutlined />}
            onClick={() => handlePageChange(record.type, 'next')}
            disabled={!record.canNextPage}
          />
        </div>
      )
    }
  ];

  return (
    <div className="horoscope-selector">
      <Table
        columns={columns}
        dataSource={getTableData()}
        pagination={false}
        size="small"
        scroll={{ x: true }}
        bordered
        showHeader={false}
      />
    </div>
  );
};

export default HoroscopeSelector; 