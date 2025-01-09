import React, { useState, useRef, useEffect } from 'react';
import { Card, Button, Tooltip } from 'antd';
import type { Star, Palace, ZiWeiResult } from '@/types/iztro';
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
            stroke="#ff4d4f"
            strokeWidth="1.5"
            markerEnd="url(#arrowhead)"
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
            stroke="#ff4d4f"
            strokeWidth="1.5"
            markerEnd="url(#arrowhead)"
            fill="none"
          />
        );
      }
    });

    return (
      <div className="connection-lines">
        <svg>
          <defs>
            <marker
              id="arrowhead"
              markerWidth="10"
              markerHeight="7"
              refX="9"
              refY="3.5"
              orient="auto"
            >
              <polygon points="0 0, 10 3.5, 0 7" fill="#ff4d4f" />
            </marker>
          </defs>
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
              {palace.stars?.map(renderStar)}
            </div>
            {palace.decadal && (
              <div className="decadal-info">
                {palace.decadal.range[0]}～{palace.decadal.range[1]}
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
            onTimeChange={onTimeChange}
            mingGongData={data.palaces.find(p => p.type === '命宫')}
            palaces={data.palaces}
          />
        </div>
      )}
    </div>
  );
};

export default ZiWeiChart;
