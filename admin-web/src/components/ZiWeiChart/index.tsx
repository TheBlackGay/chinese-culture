import React, { useState, useRef, useEffect } from 'react';
import { Card, Button, Tooltip, message, Form, Select } from 'antd';
import type { Star, Palace, ZiWeiResult } from '@/types/iztro';
import { calculateZiWei } from '@/services/ziwei';
import HoroscopeSelector from '../HoroscopeSelector';
import classNames from 'classnames';
import './index.less';
import dayjs from 'dayjs';

interface ZiWeiChartProps {
  data: ZiWeiResult;
  onTimeChange?: (params: {
    decadal?: any;
    year?: number;
    month?: number;
    day?: number;
    hour?: number;
  }) => void;
}

const ZiWeiChart: React.FC<ZiWeiChartProps> = ({ data, onTimeChange }) => {
  const [chartRotation, setChartRotation] = useState(0);
  const [selectedPalace, setSelectedPalace] = useState<string | null>('命宫');
  const [connectionPoints, setConnectionPoints] = useState<{[key: string]: DOMRect}>({});
  const chartRef = useRef<HTMLDivElement>(null);

  // 数据更新时重新触发命宫选择
  useEffect(() => {
    if (data) {
      // 等待DOM渲染完成后再计算连接点
      setTimeout(() => {
        setSelectedPalace('命宫');
        if (chartRef.current) {
          const points: {[key: string]: DOMRect} = {};
          const palaces = chartRef.current.querySelectorAll('.palace');

          palaces.forEach((palace) => {
            const palaceType = palace.querySelector('.palace-name')?.textContent;
            if (palaceType) {
              points[palaceType] = palace.getBoundingClientRect();
            }
          });

          setConnectionPoints(points);
        }
      }, 0);
    }
  }, [data]);

  // 更新连接点位置
  useEffect(() => {
    if (!chartRef.current || !selectedPalace) return;

    const points: {[key: string]: DOMRect} = {};
    const palaces = chartRef.current.querySelectorAll('.palace');

    palaces.forEach((palace) => {
      const palaceType = palace.querySelector('.palace-name')?.textContent;
      if (palaceType) {
        points[palaceType] = palace.getBoundingClientRect();
      }
    });

    setConnectionPoints(points);
  }, [selectedPalace, chartRotation]);

  // 根据地支获取连接点位置
  const getConnectionPoint = (palace: Palace, rect: DOMRect, chartRect: DOMRect) => {
    const { earthlyBranch } = palace;
    const { left, top, width, height } = rect;

    // 转换成相对于chart的坐标
    const relativeLeft = left - chartRect.left;
    const relativeTop = top - chartRect.top;

    // 根据地支返回连接点坐标
    switch (earthlyBranch) {
      case '子':
      case '丑':
        // 上边框中心
        return {
          x: relativeLeft + width / 2,
          y: relativeTop
        };
      case '寅':
        // 右上角
        return {
          x: relativeLeft + width,
          y: relativeTop
        };
      case '卯':
      case '辰':
        // 右边框中心
        return {
          x: relativeLeft + width,
          y: relativeTop + height / 2
        };
      case '巳':
        // 右下角
        return {
          x: relativeLeft + width,
          y: relativeTop + height
        };
      case '午':
      case '未':
        // 下边框中心
        return {
          x: relativeLeft + width / 2,
          y: relativeTop + height
        };
      case '申':
        // 左下角
        return {
          x: relativeLeft,
          y: relativeTop + height
        };
      case '酉':
      case '戌':
        // 左边框中心
        return {
          x: relativeLeft,
          y: relativeTop + height / 2
        };
      case '亥':
        // 左上角
        return {
          x: relativeLeft,
          y: relativeTop
        };
      default:
        // 默认返回中心点
        return {
          x: relativeLeft + width / 2,
          y: relativeTop + height / 2
        };
    }
  };

  // 渲染连接线
  const renderConnectionLines = () => {
    if (!selectedPalace || !connectionPoints[selectedPalace]) return null;

    const result = getThreeAndFourPalaces(selectedPalace);
    if (!result) return null;

    const chartRect = chartRef.current?.getBoundingClientRect();
    if (!chartRect) return null;

    const selectedPalaceData = data.palaces.find(p => p.type === selectedPalace);
    const selectedRect = connectionPoints[selectedPalace];
    if (!selectedPalaceData) return null;

    const paths: JSX.Element[] = [];

    // 获取选中宫位的连接点
    const fromPoint = getConnectionPoint(selectedPalaceData, selectedRect, chartRect);

    // 绘制到三方宫位的线
    result.threeWays.forEach((palace, index) => {
      const targetPalace = data.palaces.find(p => p.type === palace);
      const targetRect = connectionPoints[palace];
      if (targetPalace && targetRect) {
        const toPoint = getConnectionPoint(targetPalace, targetRect, chartRect);
        paths.push(
          <path
            key={`three-${index}`}
            d={`M ${fromPoint.x} ${fromPoint.y} L ${toPoint.x} ${toPoint.y}`}
            stroke="#ffffff"
            strokeWidth="0.5"
            strokeDasharray="5,5"
            fill="none"
          />
        );
      }
    });

    // 绘制到四正宫位的线
    result.fourCorrect.forEach((palace, index) => {
      const targetPalace = data.palaces.find(p => p.type === palace);
      const targetRect = connectionPoints[palace];
      if (targetPalace && targetRect) {
        const toPoint = getConnectionPoint(targetPalace, targetRect, chartRect);
        paths.push(
          <path
            key={`four-${index}`}
            d={`M ${fromPoint.x} ${fromPoint.y} L ${toPoint.x} ${toPoint.y}`}
            stroke="#ffffff"
            strokeWidth="0.5"
            strokeDasharray="5,5"
            fill="none"
          />
        );
      }
    });

    // 连接三方宫位
    if (result.threeWays.length === 2) {
      const firstPalace = data.palaces.find(p => p.type === result.threeWays[0]);
      const secondPalace = data.palaces.find(p => p.type === result.threeWays[1]);
      const firstRect = connectionPoints[result.threeWays[0]];
      const secondRect = connectionPoints[result.threeWays[1]];

      if (firstPalace && secondPalace && firstRect && secondRect) {
        const point1 = getConnectionPoint(firstPalace, firstRect, chartRect);
        const point2 = getConnectionPoint(secondPalace, secondRect, chartRect);
        paths.push(
          <path
            key="three-connection"
            d={`M ${point1.x} ${point1.y} L ${point2.x} ${point2.y}`}
            stroke="#ffffff"
            strokeWidth="0.5"
            strokeDasharray="5,5"
            fill="none"
          />
        );
      }
    }

    return (
      <div className="connection-lines">
        <svg>
          {paths}
        </svg>
      </div>
    );
  };

  // 判断宫位是否在三方四正中
  const isInThreeAndFour = (palaceType: string) => {
    if (!selectedPalace) return false;
    const result = getThreeAndFourPalaces(selectedPalace);
    if (!result) return false;
    return [...result.threeWays, ...result.fourCorrect].includes(palaceType);
  };

  // 计算三方四正
  const getThreeAndFourPalaces = (palace: string) => {
    // 先查找宫位索引，同时支持使用type或name查找
    const palaceIndex = data.palaces.findIndex(p => 
      (p.type && p.type === palace) || (p.name && p.name === palace)
    );
    
    if (palaceIndex === -1) return null;

    // 获取三方四正宫位的type或name
    const getGongType = (index: number): string => {
      const p = data.palaces[index];
      return p.type || p.name;
    };

    return {
      // 三方：本宫前4位和后4位的宫位
      threeWays: [
        getGongType((palaceIndex + 4) % 12),
        getGongType((palaceIndex + 8) % 12)
      ],
      // 四正：只取对宫（相隔6个宫位）
      fourCorrect: [
        getGongType((palaceIndex + 6) % 12) // 对宫
      ]
    };
  };

  // 渲染星耀
  const renderStar = (star: Star) => {
    // 特殊星耀列表
    const purpleStars = ['煞星', '地空', '地劫'];
    const orangeStars = ['文昌', '天钺', '天马', '文曲', '左辅', '右弼', '天魁', '禄存'];

    // 判断是否是需要显示为紫色或橙色的星耀
    const isPurpleStar = purpleStars.includes(star.name);
    const isOrangeStar = orangeStars.includes(star.name);

    // 确定星耀类型，兼容不同格式的数据源
    let starType = 'other';
    if (star.type) {
      if (star.type === 'major' || star.type === '主星') {
        starType = 'major';
      } else if (star.type === 'soft' || star.type === 'minor' || star.type === '辅星') {
        starType = 'minor';
      }
    }

    const starClass = isPurpleStar
      ? 'star star-purple'
      : isOrangeStar
      ? 'star star-orange'
      : `star star-${starType}`;

    // 获取亮度的中文显示
    const getBrightnessText = (brightness?: string) => {
      if (!brightness) return '';
      const brightnessMap: { [key: string]: string } = {
        '庙': '庙',
        '旺': '旺',
        '得': '得',
        '利': '利',
        '平': '平',
        '不': '不',
        '陷': '陷'
      };
      return brightnessMap[brightness] || '';
    };

    // 构建星耀描述
    const description = [
      star.description,
      star.transformation && `四化: ${star.transformation}化`,
      star.brightness && `星耀强度: ${star.brightness}`
    ].filter(Boolean).join('\n');

    return (
      <span key={star.name} className={starClass} title={description}>
        <span className="star-name">{star.name}</span>
        {star.brightness && (
          <small className="star-brightness">
            {getBrightnessText(star.brightness)}
          </small>
        )}
        {star.transformation && (
          <small
            className={`star-transform transform-${star.transformation}`}
            title={`四化: ${star.transformation}`}
          >
            {star.transformation}
          </small>
        )}
        {star.horoscopeMutagen && (
          <small
            className="star-horoscope-mutagen"
            title={`运限四化: ${star.horoscopeMutagen}`}
          >
            {star.horoscopeMutagen}
          </small>
        )}
      </span>
    );
  };

  // 渲染宫位
  const renderPalace = (palace: Palace, index: number) => {
    const palaceType = palace.type || palace.name;
    const isSelected = palaceType === selectedPalace;
    const isRelated = isInThreeAndFour(palaceType);

    // 确保stars数组存在
    const allStars = palace.stars || [];

    return (
      <div
        key={index}
        className={classNames(`palace p${index + 1}`, {
          'palace-selected': isSelected,
          'palace-related': isRelated
        })}
        onClick={() => setSelectedPalace(palaceType)}
      >
        <div className="palace-content" style={{ transform: `rotate(${-chartRotation}deg)` }}>
          <div className="palace-header">
            <span className="palace-name">{palace.name}</span>
          </div>
          <div className="palace-body">
            <div className="palace-stars">
              {allStars.map(renderStar)}
            </div>
            {palace.decadal && (
              <div className="decadal-info">
                <div className="decadal-range">
                  {palace.decadal.range[0]}～{palace.decadal.range[1]}岁
                </div>
              </div>
            )}
            {palace.ages && palace.ages.length > 0 && (
              <div className="ages-info">
                小限：{palace.ages.join('、')}
              </div>
            )}
            {palace.isBodyPalace && <div className="body-palace-mark">身宫</div>}
            {palace.changsheng12 && (
              <div className="changsheng12" title="长生十二神">
                {palace.changsheng12}
              </div>
            )}
            {palace.boshi12 && (
              <div className="boshi12" title="博士十二神">
                {palace.boshi12}
              </div>
            )}
            {palace.suiqian12 && (
              <div className="suiqian12" title={`岁前十二神: ${palace.suiqian12}`}>
                {palace.suiqian12}
              </div>
            )}
            {palace.jiangqian12 && (
              <div className="jiangqian12" title={`将前十二神: ${palace.jiangqian12}`}>
                {palace.jiangqian12}
              </div>
            )}
          </div>
          <span className="palace-stems">
            {palace.heavenlyStem}{palace.earthlyBranch}
          </span>
          {isSelected && <div className="palace-arrow">→</div>}
        </div>
      </div>
    );
  };

  // 如果没有数据，显示加载中或空状态
  if (!data) {
    return (
      <Card className="chart-container">
        <div style={{ textAlign: 'center', padding: '20px', color: '#fff' }}>
          暂无命盘数据
        </div>
      </Card>
    );
  }

  // 调试输出数据结构
  console.log('命盘数据:', {
    solarDate: data?.solarDate,
    lunarDate: data?.lunarDate,
    gender: data?.gender,
    palacesLength: data?.palaces?.length,
    firstPalace: data?.palaces && data?.palaces.length > 0 ? data.palaces[0] : null
  });

  // 检查数据是否完整
  if (!data || !data.palaces || data.palaces.length === 0) {
    return (
      <Card className="chart-container">
        <div style={{ textAlign: 'center', padding: '20px', color: '#fff' }}>
          命盘数据不完整，请重新计算
        </div>
      </Card>
    );
  }
  
  // 预处理宫位数据，确保每个宫位都有type属性和stars数组
  data.palaces.forEach(palace => {
    // 1. 确保palace.type存在，使用name字段
    if (!palace.type) {
      (palace as any).type = palace.name;
    }
    
    // 2. 如果没有stars数组但有主星/辅星/杂耀数组，合并它们
    if (!palace.stars) {
      const palaceAny = palace as any;
      const allStars: Star[] = [];
      
      // 处理主星
      if (palaceAny.majorStars && Array.isArray(palaceAny.majorStars)) {
        palaceAny.majorStars.forEach((star: any) => {
          allStars.push({
            ...star,
            type: star.type || 'major'
          });
        });
      }
      
      // 处理辅星
      if (palaceAny.minorStars && Array.isArray(palaceAny.minorStars)) {
        palaceAny.minorStars.forEach((star: any) => {
          allStars.push({
            ...star,
            type: star.type || 'soft'
          });
        });
      }
      
      // 处理杂耀
      if (palaceAny.adjectiveStars && Array.isArray(palaceAny.adjectiveStars)) {
        palaceAny.adjectiveStars.forEach((star: any) => {
          allStars.push({
            ...star,
            type: star.type || 'adjective'
          });
        });
      }
      
      palaceAny.stars = allStars;
    }
  });

  // 处理旋转
  // const handleRotate = () => {
  //   setChartRotation((prev) => (prev + 90) % 360);
  // };

  // 渲染中宫信息
  const renderCenterInfo = () => {
    return (
      <div className="center-info">
        <div>阳历：{data.solarDate || '未知'}</div>
        <div>农历：{data.lunarDate || '未知'}</div>
        <div>时辰：{data.time || '未知'}</div>
        <div>时辰范围：{data.timeRange || '未知'}</div>
        <div>星座：{data.sign || '未知'}</div>
        <div>生肖：{data.zodiac || '未知'}</div>
        <div>性别：{data.gender || '未知'}</div>
        <div>命主：{data.soul || '未知'}</div>
        <div>身主：{data.body || '未知'}</div>
        <div>五行局：{data.fiveElementsClass || '未知'}</div>
      </div>
    );
  };

  // 时辰转换成小时
  const zhiToHour = (zhi: string): number => {
    const zhiMap: Record<string, number> = {
      '子': 0, '丑': 2, '寅': 4, '卯': 6, '辰': 8, '巳': 10,
      '午': 12, '未': 14, '申': 16, '酉': 18, '戌': 20, '亥': 22
    };
    return zhiMap[zhi] ?? 0;
  };

  return (
    <Card className="chart-container">
      <div className="debug-note" style={{ position: 'absolute', top: '5px', left: '5px', color: '#ff6b6b', fontSize: '12px' }}>
        紫微斗数开发中，可能有不准
      </div>

      <div className="chart" style={{ transform: `rotate(${chartRotation}deg)` }}>
        {/* 渲染12宫 */}
        {data.palaces?.map((palace, index) => renderPalace(palace, index))}
        
        {/* 渲染连接线 */}
        <div className="center-container">
          {/* 里面的正方形 */}
          <div className="center-square"></div>
          {renderCenterInfo()}
        </div>
      </div>

      {/* 命盘选择器 */}
      <div className="horoscope-selector" style={{ marginTop: '20px' }}>
        <Form layout="inline">
          <Form.Item label="时辰">
            <Select
              style={{ width: 80 }}
              defaultValue={data.time || '子'}
              onChange={(value) => {
                try {
                  if (!data.solarDate) {
                    message.error('没有日期数据，无法重新计算');
                    return;
                  }
                  
                  // 解析当前日期
                  const currentDate = data.solarDate ? dayjs(data.solarDate, 'YYYY-MM-DD') : dayjs();
                  
                  // 合并日期和时辰
                  const hour = zhiToHour(value);
                  const dateTime = currentDate.hour(hour).minute(0).second(0);
                  
                  // 重新计算命盘
                  onTimeChange && onTimeChange({
                    year: dateTime.year(),
                    month: dateTime.month() + 1, // dayjs的月份从0开始
                    day: dateTime.date(),
                    hour: dateTime.hour()
                  });
                } catch (error) {
                  console.error('计算命盘时出错:', error);
                  message.error('计算命盘时出错，请检查日期格式');
                }
              }}
              options={[
                { value: '子', label: '子时 (23:00-01:00)' },
                { value: '丑', label: '丑时 (01:00-03:00)' },
                { value: '寅', label: '寅时 (03:00-05:00)' },
                { value: '卯', label: '卯时 (05:00-07:00)' },
                { value: '辰', label: '辰时 (07:00-09:00)' },
                { value: '巳', label: '巳时 (09:00-11:00)' },
                { value: '午', label: '午时 (11:00-13:00)' },
                { value: '未', label: '未时 (13:00-15:00)' },
                { value: '申', label: '申时 (15:00-17:00)' },
                { value: '酉', label: '酉时 (17:00-19:00)' },
                { value: '戌', label: '戌时 (19:00-21:00)' },
                { value: '亥', label: '亥时 (21:00-23:00)' }
              ]}
            />
          </Form.Item>
        </Form>
      </div>
    </Card>
  );
};

export default ZiWeiChart;
