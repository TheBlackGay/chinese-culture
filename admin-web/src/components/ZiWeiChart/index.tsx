import React, { useState } from 'react';
import { Card, Button } from 'antd';
import type { Star, Palace, ZiWeiResult } from '@/types/iztro';
import './index.less';

interface ZiWeiChartProps {
  data: ZiWeiResult;
}

const ZiWeiChart: React.FC<ZiWeiChartProps> = ({ data }) => {
  const [chartRotation, setChartRotation] = useState(0);

  // 处理旋转
  const handleRotate = () => {
    setChartRotation((prev) => (prev + 90) % 360);
  };

  // 渲染星耀
  const renderStar = (star: Star) => {
    const starClass = `star star-${star.type === '主星' ? 'major' : star.type === '辅星' ? 'minor' : 'other'}`;
    return (
      <span key={star.name} className={starClass} title={star.description}>
        {star.name}
      </span>
    );
  };

  // 渲染宫位
  const renderPalace = (palace: Palace, index: number) => {
    return (
      <div 
        key={index}
        className={`palace p${index + 1}`} 
      >
        <div className="palace-content" style={{ transform: `rotate(${-chartRotation}deg)` }}>
          <div className="palace-header">
            <span className="palace-name">{palace.type}</span>
            <span className="palace-stems">
              {palace.heavenlyStem}{palace.earthlyBranch}
            </span>
          </div>
          <div className="palace-body">
            <div className="palace-stars">
              {palace.stars?.map(renderStar)}
            </div>
            {palace.transformations && palace.transformations.length > 0 && (
              <div className="transformations">
                {palace.transformations.join('、')}
              </div>
            )}
          </div>
        </div>
      </div>
    );
  };

  // 渲染中宫信息
  const renderCenterInfo = () => {
    const { centerInfo } = data;
    return (
      <div className="center-info">
        <h3>中宫</h3>
        <p>阳历：{centerInfo.birthTime}</p>
        <p>农历：{centerInfo.lunarBirthDay}</p>
        <p>命主：{centerInfo.fate}</p>
        <p>身主：{centerInfo.bodyFate}</p>
        <p>五行局：{centerInfo.fiveElements}</p>
        <p>起运：{centerInfo.startAge}</p>
        <p>流年：{centerInfo.direction}</p>
      </div>
    );
  };

  return (
    <Card className="chart-container">
      <div className="chart-header">
        <Button onClick={handleRotate}>旋转命盘</Button>
      </div>
      <div className="chart-wrapper">
        <div 
          className="chart-body" 
          style={{ transform: `rotate(${chartRotation}deg)` }}
        >
          {data.palaces?.map((palace, index) => renderPalace(palace, index))}
        </div>
        {renderCenterInfo()}
      </div>
    </Card>
  );
};

export default ZiWeiChart; 