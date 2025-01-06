import React from 'react';
import { Card } from 'antd';
import styles from './index.less';

interface Star {
  name: string;
  brightness?: string;
  color?: 'blue' | 'green' | 'red' | 'yellow';
}

interface Palace {
  name: string;
  flowYear?: string;
  smallLimit?: string;
  heavenlyStem: string;
  earthlyBranch: string;
  stars?: Star[];
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
      {star.brightness && <span className={styles.starNote}>{star.brightness}</span>}
    </span>
  );

  return (
    <Card className={styles.resultCard} bordered={false}>
      <div className={styles.ziWeiContent}>
        <div className={styles.palaceGrid}>
          {/* 12宫位 */}
          {data.palaces.map((palace, index) => (
            <div key={index} className={`${styles.palace} ${styles['p' + (index + 1)]}`}>
              <div className={styles.palaceName}>{palace.name}</div>
              
              <div className={styles.yearInfo}>
                <div>流年: {palace.flowYear || '-'}</div>
                <div>小限: {palace.smallLimit || '-'}</div>
              </div>

              <div className={styles.stemBranch}>
                <span>{palace.heavenlyStem}</span>
                <span>{palace.earthlyBranch}</span>
              </div>

              <div className={styles.starList}>
                {palace.stars?.map((star, idx) => renderStar(star)) || null}
              </div>
            </div>
          ))}

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