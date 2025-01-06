import React from 'react';
import { Card, Tag } from 'antd';
import styles from './index.less';

interface Star {
  name: string;
  brightness?: string;
  mutagen?: string;
}

interface Palace {
  name: string;
  isBodyPalace: boolean;
  isOriginalPalace: boolean;
  heavenlyStem: string;
  earthlyBranch: string;
  majorStars: Star[];
  minorStars: Star[];
  adjectiveStars: Star[];
}

interface ZiWeiResult {
  solarDate: string;
  lunarDate: string;
  chineseDate: string;
  soul: string;
  body: string;
  fiveElementsClass: string;
  palaces: Palace[];
  horoscope: {
    ages?: {
      current: string;
    };
    years?: {
      current: string;
    };
  };
}

interface ZiWeiChartProps {
  data: ZiWeiResult;
}

const ZiWeiChart: React.FC<ZiWeiChartProps> = ({ data }) => {
  if (!data) return null;

  return (
    <Card className={styles.resultCard} bordered={false}>
      <div className={styles.ziWeiContent}>
        {/* 基本信息 */}
        <div className={styles.basicInfo}>
          <div className={styles.infoRow}>
            <div className={styles.infoItem}>
              <span className={styles.label}>阳历：</span>
              <span className={styles.value}>{data.solarDate}</span>
            </div>
            <div className={styles.infoItem}>
              <span className={styles.label}>农历：</span>
              <span className={styles.value}>{data.lunarDate}</span>
            </div>
            <div className={styles.infoItem}>
              <span className={styles.label}>四柱：</span>
              <span className={styles.value}>{data.chineseDate}</span>
            </div>
          </div>
          <div className={styles.infoRow}>
            <div className={styles.infoItem}>
              <span className={styles.label}>命主：</span>
              <span className={styles.value}>{data.soul}</span>
            </div>
            <div className={styles.infoItem}>
              <span className={styles.label}>身主：</span>
              <span className={styles.value}>{data.body}</span>
            </div>
            <div className={styles.infoItem}>
              <span className={styles.label}>五行局：</span>
              <span className={styles.value}>{data.fiveElementsClass}</span>
            </div>
          </div>
        </div>

        {/* 十二宫位环形布局 */}
        <div className={styles.palaceGrid}>
          {data.palaces.map((palace, index) => (
            <div key={index} className={`${styles.palace} ${styles['p' + (index + 1)]}`}>
              <div className={styles.palaceHeader}>
                <span className={styles.palaceName}>{palace.name}</span>
                <div className={styles.palaceFlags}>
                  {palace.isBodyPalace && <Tag color="blue">身宫</Tag>}
                  {palace.isOriginalPalace && <Tag color="green">本宫</Tag>}
                </div>
              </div>
              
              <div className={styles.stemBranch}>
                <Tag>{palace.heavenlyStem}</Tag>
                <Tag>{palace.earthlyBranch}</Tag>
              </div>

              <div className={styles.starList}>
                {palace.majorStars.length > 0 && (
                  <div className={styles.starGroup}>
                    <div className={styles.starType}>主星</div>
                    <div className={styles.stars}>
                      {palace.majorStars.map((star, idx) => (
                        <Tag key={idx} className="major">{star.name}</Tag>
                      ))}
                    </div>
                  </div>
                )}
                
                {palace.minorStars.length > 0 && (
                  <div className={styles.starGroup}>
                    <div className={styles.starType}>辅星</div>
                    <div className={styles.stars}>
                      {palace.minorStars.map((star, idx) => (
                        <Tag key={idx} className="minor">{star.name}</Tag>
                      ))}
                    </div>
                  </div>
                )}
                
                {palace.adjectiveStars.length > 0 && (
                  <div className={styles.starGroup}>
                    <div className={styles.starType}>杂耀</div>
                    <div className={styles.stars}>
                      {palace.adjectiveStars.map((star, idx) => (
                        <Tag key={idx} className="adjective">{star.name}</Tag>
                      ))}
                    </div>
                  </div>
                )}
              </div>
            </div>
          ))}
        </div>

        {/* 大运流年 */}
        <div className={styles.basicInfo} style={{ marginTop: 24 }}>
          <div className={styles.infoRow}>
            <div className={styles.infoItem}>
              <span className={styles.label}>当前大运：</span>
              <span className={styles.value}>{data.horoscope.ages?.current}</span>
            </div>
            <div className={styles.infoItem}>
              <span className={styles.label}>当前流年：</span>
              <span className={styles.value}>{data.horoscope.years?.current}</span>
            </div>
          </div>
        </div>
      </div>
    </Card>
  );
};

export default ZiWeiChart; 