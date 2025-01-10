import React, { useState, useRef, useEffect } from 'react';
import { Card, Button, Tooltip, message } from 'antd';
import type { Star, Palace, ZiWeiResult } from '@/types/iztro';
import { calculateZiWei } from '@/services/ziwei';
import HoroscopeSelector from '../HoroscopeSelector';
import classNames from 'classnames';
import './index.less';

interface ZiWeiChartProps {
  data: ZiWeiResult;
  onTimeChange?: (params: {
    decadal?: number;
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

  // 计算三方四正
  const getThreeAndFourPalaces = (palace: string) => {
    const palaceIndex = data.palaces.findIndex(p => p.type === palace);
    if (palaceIndex === -1) return null;

    return {
      // 三方：本宫前4位和后4位的宫位
      threeWays: [
        data.palaces[(palaceIndex + 4) % 12].type,
        data.palaces[(palaceIndex + 8) % 12].type
      ],
      // 四正：只取对宫（相隔6个宫位）
      fourCorrect: [
        data.palaces[(palaceIndex + 6) % 12].type, // 对宫
      ]
    };
  };

  // 判断宫位是否在三方四正中
  const isInThreeAndFour = (palaceType: string) => {
    if (!selectedPalace) return false;
    const result = getThreeAndFourPalaces(selectedPalace);
    if (!result) return false;
    return [...result.threeWays, ...result.fourCorrect].includes(palaceType);
  };

  // 渲染星耀
  const renderStar = (star: Star) => {
    // 特殊星耀列表
    const purpleStars = ['煞星', '地空', '地劫'];
    const orangeStars = ['文昌', '天钺', '天马', '文曲', '左辅', '右弼', '天魁', '禄存'];

    // 判断是否是需要显示为紫色或橙色的星耀
    const isPurpleStar = purpleStars.includes(star.name);
    const isOrangeStar = orangeStars.includes(star.name);

    const starClass = isPurpleStar
      ? 'star star-purple'
      : isOrangeStar
      ? 'star star-orange'
      : `star star-${star.type === '主星' ? 'major' : star.type === '辅星' ? 'minor' : 'other'}`;

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
      star.transformation && `四化: ${star.transformation}化 (来源: ${star.transformation.source})`,
      star.brightness && `星耀强度: ${star.brightness}`
    ].filter(Boolean).join('\n');

    // 打印星耀数据，用于调试
    console.log('Rendering star:', { star, hasTransformation: !!star.transformation });

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
            title={`来源: ${star.transformation}`}
          >
            {star.transformation}
          </small>
        )}
      </span>
    );
  };

  // 渲染宫位
  const renderPalace = (palace: Palace, index: number) => {
    const isSelected = palace.type === selectedPalace;
    const isRelated = isInThreeAndFour(palace.type);

    // 处理大限星耀
    const decadalStars = palace.decadal?.stars || [];
    const allStars = [...(palace.stars || []), ...decadalStars];

    return (
      <div
        key={index}
        className={classNames(`palace p${index + 1}`, {
          'palace-selected': isSelected,
          'palace-related': isRelated
        })}
        onClick={() => setSelectedPalace(palace.type)}
      >
        <div className="palace-content" style={{ transform: `rotate(${-chartRotation}deg)` }}>
          <div className="palace-header">
            <span className="palace-name">{palace.type}</span>
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
                {palace.decadal.stars && palace.decadal.stars.length > 0 && (
                  <div className="decadal-stars">
                    {palace.decadal.stars.map(star => (
                      <span key={star.name} className="decadal-star">
                        {star.name}
                      </span>
                    ))}
                  </div>
                )}
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

  // 处理旋转
  // const handleRotate = () => {
  //   setChartRotation((prev) => (prev + 90) % 360);
  // };

  // 渲染中宫信息
  const renderCenterInfo = () => {
    return (
      <div className="center-info">
        <div>阳历：{data.solarDate}</div>
        <div>农历：{data.lunarDate}</div>
        <div>时辰：{data.time}</div>
        <div>时辰范围：{data.timeRange}</div>
        <div>星座：{data.sign}</div>
        <div>生肖：{data.zodiac}</div>
        <div>性别：{data.gender}</div>
        <div>命主：{data.soul}</div>
        <div>身主：{data.body}</div>
        <div>五行局：{data.fiveElementsClass}</div>
      </div>
    );
  };

  // 地支时间转换为小时数
  const zhiToHour = (zhi: string): number => {
    const zhiHourMap: { [key: string]: number } = {
      '子时': 0,  // 23:00-00:59
      '丑时': 2,  // 01:00-02:59
      '寅时': 4,  // 03:00-04:59
      '卯时': 6,  // 05:00-06:59
      '辰时': 8,  // 07:00-08:59
      '巳时': 10, // 09:00-10:59
      '午时': 12, // 11:00-12:59
      '未时': 14, // 13:00-14:59
      '申时': 16, // 15:00-16:59
      '酉时': 18, // 17:00-18:59
      '戌时': 20, // 19:00-20:59
      '亥时': 22  // 21:00-22:59
    };
    // 移除可能的"时"字
    const cleanZhi = zhi.replace('时', '');
    return zhiHourMap[`${cleanZhi}时`] ?? 0;
  };

  return (
    <div className="ziwei-chart-container">
      <div className="ziwei-chart">
        <Card className="chart-container">
          <div className="chart-header">
            <div>紫微斗数开发中，可能有不准</div>
          </div>
          <div className="chart-wrapper">
            <div
              ref={chartRef}
              className="chart-body"
              style={{ transform: `rotate(${chartRotation}deg)` }}
            >
              {data.palaces?.map((palace, index) => renderPalace(palace, index))}
            </div>
            {renderConnectionLines()}
            {renderCenterInfo()}
          </div>
        </Card>
      </div>
      {onTimeChange && (
        <div className="horoscope-selector-container">
          <HoroscopeSelector
            startYear={parseInt(data.solarDate.split('-')[0])}
            currentYear={new Date().getFullYear()}
            onTimeChange={async (params) => {
              try {
                // 打印运限参数
                console.log("data.solarDate:", JSON.stringify(data.solarDate));
                console.log("data.time:", JSON.stringify(data.time));
                console.log("data.timeRange:", JSON.stringify(data.timeRange));

                // 将地支时间转换为小时数
                const birthHour = zhiToHour(data.time);
                console.log('转换后的出生时间:', birthHour);

                console.log('运限计算参数:', {
                  params,
                  birthYear: parseInt(data.solarDate.split('-')[0]),
                  birthMonth: parseInt(data.solarDate.split('-')[1]),
                  birthDay: parseInt(data.solarDate.split('-')[2]),
                  birthHour,
                  gender: data.gender === '男' ? 'male' : 'female'
                });

                // 调用紫微斗数运限计算接口
                const horoscope = await calculateZiWei(
                  parseInt(data.solarDate.split('-')[0]),
                  parseInt(data.solarDate.split('-')[1]),
                  parseInt(data.solarDate.split('-')[2]),
                  birthHour,
                  data.gender === '男' ? 'male' : 'female',
                  params
                );

                if (onTimeChange) {
                  onTimeChange(params);
                }
              } catch (error) {
                console.error('计算运限失败:', error);
                message.error('计算运限失败');
              }
            }}
            mingGongData={data.palaces.find(p => p.type === '命宫')}
            palaces={data.palaces}
          />
        </div>
      )}
    </div>
  );
};

export default ZiWeiChart;
