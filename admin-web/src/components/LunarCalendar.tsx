import React from 'react';
import { Calendar, Badge, Card, Typography } from 'antd';
import type { Dayjs } from 'dayjs';
import { Solar } from 'lunar-typescript';
import styled from 'styled-components';

const { Text } = Typography;

const StyledCard = styled(Card)`
  .ant-card-body {
    padding: 0;
  }
`;

const DateCell = styled.div`
  padding: 4px;
  min-height: 80px;
`;

const LunarDate = styled(Text)`
  display: block;
  text-align: center;
  margin-bottom: 4px;
  font-size: 12px;
`;

const FestivalBadge = styled(Badge)`
  display: block;
  margin-bottom: 2px;
  .ant-badge-status-text {
    font-size: 12px;
  }
`;

const DayInfo = styled.div<{ type: 'yi' | 'ji' }>`
  margin-top: 2px;
  font-size: 12px;
  color: ${props => props.type === 'yi' ? '#52c41a' : '#ff4d4f'};
`;

const LunarCalendar: React.FC = () => {
  const dateCellRender = (date: Dayjs) => {
    try {
      // 转换为农历
      const solar = Solar.fromDate(date.toDate());
      const lunar = solar.getLunar();
      
      // 获取节日信息
      const festivals = [
        ...lunar.getFestivals(), // 农历节日
        ...solar.getFestivals(), // 公历节日
        lunar.getJieQi(), // 节气
      ].filter(Boolean);

      // 获取宜忌
      const dayYi = lunar.getDayYi(); // 宜
      const dayJi = lunar.getDayJi(); // 忌

      return (
        <DateCell>
          <LunarDate type="secondary">
            {lunar.getDayInChinese()}
          </LunarDate>
          
          {festivals.map((festival, index) => (
            <FestivalBadge
              key={index}
              status="success"
              text={festival}
            />
          ))}
          
          {dayYi.length > 0 && (
            <DayInfo type="yi">
              宜: {dayYi.slice(0, 2).join('、')}
            </DayInfo>
          )}
          {dayJi.length > 0 && (
            <DayInfo type="ji">
              忌: {dayJi.slice(0, 2).join('、')}
            </DayInfo>
          )}
        </DateCell>
      );
    } catch (error) {
      console.error('Error rendering date cell:', error);
      return null;
    }
  };

  return (
    <StyledCard 
      title="农历日历" 
      className="lunar-calendar-card"
    >
      <Calendar
        fullscreen={false}
        cellRender={dateCellRender}
      />
    </StyledCard>
  );
};

export default LunarCalendar; 