import React from 'react';
import { Card } from 'antd';
import styles from './index.less';

interface Star {
  name: string;
  status?: string;  // 星耀状态（庙、旺、平等）
  color?: 'blue' | 'green' | 'red' | 'yellow' | 'purple' | 'cyan';
}

interface Palace {
  name: string;  // 宫位名称
  position?: string;  // 宫位方位（临官、帝旺等）
  flowYear?: string;  // 流年
  smallLimit?: string;  // 小限
  range?: string;  // 宫位区间
  attributes?: string[];  // 宫位属性（病符、指背等）
  heavenlyStem: string;  // 天干
  earthlyBranch: string;  // 地支
  majorStars?: Star[];  // 主星
  minorStars?: Star[];  // 辅星
  otherStars?: Star[];  // 杂耀
}

interface ZiWeiResult {
  name: string;
  gender: '男' | '女';
  solarDate: string;
  lunarDate: string;
  soul: string;
  body: string;
  fiveElementsClass: string;
  palaces: Palace[];
  centerInfo?: {
    birthTime: string;
    clockTime: string;
    lunarBirthDay: string;
    fate: string;
    bodyFate: string;
    fiveElements: string;
    startAge: string;
    direction: string;
  };
}

interface ZiWeiChartProps {
  data: ZiWeiResult;
}

const ZiWeiChart: React.FC<ZiWeiChartProps> = ({ data }) => {
  if (!data || !data.palaces) return null;

  const renderStar = (star: Star) => (
    <span key={star.name} className={`${styles.star} ${star.color ? styles[star.color] : ''}`}>
      {star.name}
      {star.status && <span className={styles.starStatus}>{star.status}</span>}
    </span>
  );

  const renderPalace = (palace: Palace, index: number) => (
    <div key={index} className={`${styles.palace} ${styles['p' + (index + 1)]}`}>
      {/* 顶部区域 - 星耀 */}
      <div className={styles.starsArea}>
        <div className={styles.starList}>
          {palace.majorStars?.map(renderStar)}
          {palace.minorStars?.map(renderStar)}
          {palace.otherStars?.map(renderStar)}
        </div>
      </div>

      {/* 中间区域 - 流年小限 */}
      <div className={styles.yearInfo}>
        <div className={styles.flowYear}>流年: {palace.flowYear || '-'}</div>
        <div className={styles.smallLimit}>小限: {palace.smallLimit || '-'}</div>
        {palace.range && <div className={styles.range}>{palace.range}</div>}
      </div>

      {/* 底部区域 - 宫位信息 */}
      <div className={styles.palaceInfo}>
        {/* 宫位属性 */}
        {palace.attributes && (
          <div className={styles.attributes}>
            {palace.attributes.map((attr, idx) => (
              <span key={idx} className={styles.attribute}>{attr}</span>
            ))}
          </div>
        )}
        
        {/* 天干地支 */}
        <div className={styles.stemBranch}>
          <span className={styles.stem}>{palace.heavenlyStem}</span>
          <span className={styles.branch}>{palace.earthlyBranch}</span>
        </div>

        {/* 宫位名称和方位 */}
        <div className={styles.nameArea}>
          <span className={styles.palaceName}>{palace.name}</span>
          {palace.position && (
            <span className={styles.position}>{palace.position}</span>
          )}
        </div>
      </div>
    </div>
  );

  return (
    <Card className={styles.resultCard} bordered={false}>
      <div className={styles.ziWeiContent}>
        <div className={styles.palaceGrid}>
          {/* 12宫位 */}
          {data.palaces.map((palace, index) => renderPalace(palace, index))}

          {/* 中宫信息 */}
          {data.centerInfo && (
            <div className={`${styles.palace} ${styles.center}`}>
              <div>姓名：{data.name}</div>
              <div>性别：<span className={styles.gender}>{data.gender}</span></div>
              <div>阳历：{data.centerInfo.birthTime}</div>
              <div>钟表：{data.centerInfo.clockTime}</div>
              <div>农历：{data.centerInfo.lunarBirthDay}</div>
              <div>命主：{data.centerInfo.fate}</div>
              <div>身主：{data.centerInfo.bodyFate}</div>
              <div>五行局：{data.centerInfo.fiveElements}</div>
              <div>起运：{data.centerInfo.startAge}</div>
              <div>流向：{data.centerInfo.direction}</div>
            </div>
          )}
        </div>
      </div>
    </Card>
  );
};

export default ZiWeiChart; 