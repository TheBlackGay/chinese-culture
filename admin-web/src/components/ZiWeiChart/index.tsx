import React, { useState } from 'react';
import { Card, Button } from 'antd';
import type { Star, Palace, ZiWeiResult } from '@/types/iztro';
import './index.less';

interface ZiWeiChartProps {
  data: ZiWeiResult;
}

const ZiWeiChart: React.FC<ZiWeiChartProps> = ({ data }) => {
  const [chartRotation, setChartRotation] = useState(0);

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

  // 渲染星耀
  const renderStar = (star: Star) => {
    // 特殊星耀列表
    const purpleStars = ['煞星', '地空', '地劫'];

    // 判断是否是需要显示为紫色的星耀
    const isPurpleStar = purpleStars.includes(star.name);

    const starClass = isPurpleStar
      ? 'star star-purple'
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
    return (
      <div
        key={index}
        className={`palace p${index + 1}`}
      >
        <div className="palace-content" style={{ transform: `rotate(${-chartRotation}deg)` }}>
          <div className="palace-header">
            <span className="palace-name">{palace.type}</span>
            {/*{palace.isBodyPalace && <span className="palace-mark">身宫</span>}*/}
            {/*{palace.name === data.soul && <span className="palace-mark">命宫</span>}*/}
          </div>
          <div className="palace-body">
            <div className="palace-stars">
              {palace.stars?.map(renderStar)}
            </div>
            {/*{palace.transformations && palace.transformations.length > 0 && (*/}
            {/*  <div className="transformations">*/}
            {/*    {palace.transformations.join('、')}*/}
            {/*  </div>*/}
            {/*)}*/}
            {/* 显示大限信息 */}
            {palace.decadal && (
              <div className="decadal-info">
                {palace.decadal.range[0]}～{palace.decadal.range[1]}
              </div>
            )}
            {/* 显示小限信息 */}
            {palace.ages && palace.ages.length > 0 && (
              <div className="ages-info">
                流年：{palace.ages.join('、')}
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
        </div>
      </div>
    );
  };

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
    <Card className="chart-container">
      <div className="chart-header">
        {/*<Button onClick={handleRotate}>旋转命盘</Button>*/}
        <div>紫微斗数开发中，可能有不准</div>
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
