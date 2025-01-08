const CHANG_SHENG = ['长生', '沐浴', '冠带', '临官', '帝旺', '衰', '病', '死', '墓', '绝', '胎', '养'];

// 天干对应的长生地支起始位置
const CHANG_SHENG_START = {
  '甲': '亥',
  '乙': '午',
  '丙': '寅',
  '丁': '酉',
  '戊': '寅',
  '己': '酉',
  '庚': '巳',
  '辛': '子',
  '壬': '申',
  '癸': '卯'
};

// 地支顺序
const EARTHLY_BRANCHES = ['子', '丑', '寅', '卯', '辰', '巳', '午', '未', '申', '酉', '戌', '亥'];

// 计算长生十二神
function calculateChangSheng(heavenlyStem, earthlyBranch) {
  // 获取该天干的长生起始地支
  const startBranch = CHANG_SHENG_START[heavenlyStem];
  if (!startBranch) return '';

  // 获取起始地支和当前地支的索引
  const startIndex = EARTHLY_BRANCHES.indexOf(startBranch);
  const currentIndex = EARTHLY_BRANCHES.indexOf(earthlyBranch);
  if (startIndex === -1 || currentIndex === -1) return '';

  // 判断天干阴阳
  const isYin = ['乙', '丁', '己', '辛', '癸'].includes(heavenlyStem);

  // 计算步数
  let steps;
  if (isYin) {
    // 阴干逆行：从长生位逆数到目标地支的步数
    steps = (startIndex - currentIndex + 12) % 12;
  } else {
    // 阳干顺行：从长生位顺数到目标地支的步数
    steps = (currentIndex - startIndex + 12) % 12;
  }

  // 调试信息
  console.log('Debug:', {
    heavenlyStem,
    earthlyBranch,
    isYin,
    startBranch,
    startIndex,
    currentIndex,
    steps,
    result: CHANG_SHENG[steps]
  });

  return CHANG_SHENG[steps];
}

// 在计算紫微斗数时调用此函数
function processHoroscope(horoscope) {
  if (!horoscope || !horoscope.palaces) {
    console.error('Invalid horoscope data');
    return horoscope;
  }

  // 处理宫位信息时添加长生十二神
  const processedPalaces = horoscope.palaces.map(palace => ({
    ...palace,
    changsheng12: calculateChangSheng(palace.heavenlyStem, palace.earthlyBranch)
  }));

  return {
    ...horoscope,
    palaces: processedPalaces
  };
}

module.exports = {
  calculateChangSheng,
  processHoroscope
}; 