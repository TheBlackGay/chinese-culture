'use client';

import React, { useState, useEffect } from 'react';
import { Card, Calendar, Typography, Badge, Modal, Descriptions } from 'antd';
import type { Dayjs } from 'dayjs';
import { Solar } from 'lunar-typescript';

const { Title, Paragraph } = Typography;

interface Festival {
  name: string;
  date: string;  // MM-DD 格式
  type: 'solar' | 'lunar';  // 阳历或农历
  description: string;
  customs: string[];
  foods: string[];
}

const festivals: Festival[] = [
  {
    name: '春节',
    date: '01-01',
    type: 'lunar',
    description: '春节是中国最重要的传统节日，象征着新的一年的开始。',
    customs: ['贴春联', '放鞭炮', '守岁', '拜年', '发红包'],
    foods: ['饺子', '年糕', '鱼', '春卷', '汤圆'],
  },
  {
    name: '元宵节',
    date: '01-15',
    type: 'lunar',
    description: '元宵节是春节之后的第一个重要节日，象征着团圆和祥和。',
    customs: ['赏花灯', '猜灯谜', '舞龙舞狮'],
    foods: ['汤圆', '元宵', '芝麻球'],
  },
  {
    name: '清明节',
    date: '04-05',
    type: 'solar',
    description: '清明节是中国传统的祭祖节日，也是春游踏青的好时节。',
    customs: ['扫墓', '踏青', '放风筝'],
    foods: ['青团', '春笋', '清明粑'],
  },
  {
    name: '端午节',
    date: '05-05',
    type: 'lunar',
    description: '端午节是纪念屈原的节日，也是驱邪避灾的重要日子。',
    customs: ['赛龙舟', '挂艾草', '戴香囊'],
    foods: ['粽子', '咸鸭蛋', '雄黄酒'],
  },
  {
    name: '七夕节',
    date: '07-07',
    type: 'lunar',
    description: '七夕节是中国传统的情人节，源于牛郎织女的美丽传说。',
    customs: ['乞巧', '穿针引线', '观星'],
    foods: ['巧果', '七夕面'],
  },
  {
    name: '中秋节',
    date: '08-15',
    type: 'lunar',
    description: '中秋节是中国传统的团圆节日，象征着阖家团圆。',
    customs: ['赏月', '猜灯谜', '赏桂花'],
    foods: ['月饼', '柚子', '螃蟹'],
  },
  {
    name: '重阳节',
    date: '09-09',
    type: 'lunar',
    description: '重阳节是中国传统的敬老节日，也是登高望远的好日子。',
    customs: ['登高', '插茱萸', '赏菊'],
    foods: ['重阳糕', '菊花酒', '栗子'],
  },
  {
    name: '腊八节',
    date: '12-08',
    type: 'lunar',
    description: '腊八节是佛教传统节日，也是冬季重要的民俗节日。',
    customs: ['喝腊八粥', '祭祀祈福'],
    foods: ['腊八粥', '腊八蒜', '腊肉'],
  },
];

export default function FestivalsPage() {
  const [selectedFestival, setSelectedFestival] = useState<Festival | null>(null);
  const [modalVisible, setModalVisible] = useState(false);

  const dateCellRender = (date: Dayjs) => {
    const solar = Solar.fromDate(date.toDate());
    const lunar = solar.getLunar();
    
    // 检查阳历节日
    const solarFestivals = festivals.filter(
      f => f.type === 'solar' && f.date === date.format('MM-DD')
    );
    
    // 检查农历节日
    const lunarFestivals = festivals.filter(
      f => f.type === 'lunar' && f.date === `${lunar.getMonth().toString().padStart(2, '0')}-${lunar.getDay().toString().padStart(2, '0')}`
    );
    
    const allFestivals = [...solarFestivals, ...lunarFestivals];
    
    return (
      <ul className="events">
        {allFestivals.map(festival => (
          <li key={festival.name}>
            <Badge
              status="success"
              text={
                <a
                  onClick={(e) => {
                    e.preventDefault();
                    setSelectedFestival(festival);
                    setModalVisible(true);
                  }}
                >
                  {festival.name}
                </a>
              }
            />
          </li>
        ))}
      </ul>
    );
  };

  return (
    <div>
      <Title level={2}>传统节日查询</Title>
      <Paragraph>
        中国传统节日承载着丰富的文化内涵，是中华民族智慧的结晶。
        您可以通过日历查看各个传统节日的详细信息。
      </Paragraph>

      <Card>
        <Calendar
          cellRender={dateCellRender}
          mode="month"
        />
      </Card>

      <Modal
        title={selectedFestival?.name}
        open={modalVisible}
        onCancel={() => setModalVisible(false)}
        footer={null}
        width={600}
      >
        {selectedFestival && (
          <Descriptions bordered column={1}>
            <Descriptions.Item label="节日类型">
              {selectedFestival.type === 'solar' ? '阳历' : '农历'} {selectedFestival.date}
            </Descriptions.Item>
            <Descriptions.Item label="节日介绍">
              {selectedFestival.description}
            </Descriptions.Item>
            <Descriptions.Item label="传统习俗">
              {selectedFestival.customs.join('、')}
            </Descriptions.Item>
            <Descriptions.Item label="传统美食">
              {selectedFestival.foods.join('、')}
            </Descriptions.Item>
          </Descriptions>
        )}
      </Modal>
    </div>
  );
} 