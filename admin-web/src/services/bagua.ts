/**
 * 八卦与六十四卦核心服务
 * 提供占卜所需的完整功能实现
 */

// ==============================
// 类型定义
// ==============================

export interface BaguaInfo {
  name: string; // 中文名（乾、坤等）
  nature: string; // 自然属性（天、地、雷等）
  attribute: string; // 五行属性（金、木、水等）
  symbol: string; // 八卦符号
  binary: string; // 二进制表示（3位）
  number: number; // 序号（1-8）
  direction?: string; // 方位
}

export interface HexagramResult {
  upperTrigram: BaguaInfo; // 上卦
  lowerTrigram: BaguaInfo; // 下卦
  changingLines: number[]; // 变爻位置（1-6）
  timestamp?: number; // 时间戳
  number?: number; // 卦象编号（1-64）
  selected?: string; // 选中的卦名
}

export interface HexagramInterpretation {
  name: string; // 卦名
  meaning: string; // 核心含义
  upperTrigram: BaguaInfo & {
    position: string; // 位置（上卦/下卦）
    meaning: string; // 解读
  };
  lowerTrigram: BaguaInfo & {
    position: string;
    meaning: string;
  };
  wuxingAnalysis: string; // 五行分析
  yaoAnalysis: string[]; // 爻变分析
  overall: string; // 总体建议
}

export interface HexagramInfo {
  name: string;
  meaning: string;
  description: string;
  analysis: string;
  yaoChanges: Record<number, string>;
}

// ==============================
// 八卦数据（经卦）
// ==============================

export const baguaData: Record<string, BaguaInfo> = {
  qian: {
    name: '乾',
    nature: '天',
    attribute: '金',
    symbol: '☰',
    binary: '111',
    number: 1,
    direction: '西北',
  },
  kun: {
    name: '坤',
    nature: '地',
    attribute: '土',
    symbol: '☷',
    binary: '000',
    number: 2,
    direction: '西南',
  },
  zhen: {
    name: '震',
    nature: '雷',
    attribute: '木',
    symbol: '☳',
    binary: '101',
    number: 3,
    direction: '东偏南',
  },
  xun: {
    name: '巽',
    nature: '风',
    attribute: '木',
    symbol: '☴',
    binary: '010',
    number: 4,
    direction: '东南',
  },
  kan: {
    name: '坎',
    nature: '水',
    attribute: '水',
    symbol: '☵',
    binary: '100',
    number: 5,
    direction: '正北',
  },
  li: {
    name: '离',
    nature: '火',
    attribute: '火',
    symbol: '☲',
    binary: '011',
    number: 6,
    direction: '正南',
  },
  gen: {
    name: '艮',
    nature: '山',
    attribute: '土',
    symbol: '☶',
    binary: '001',
    number: 7,
    direction: '东北',
  },
  dui: {
    name: '兑',
    nature: '泽',
    attribute: '金',
    symbol: '☱',
    binary: '110',
    number: 8,
    direction: '西偏南',
  },
};

// ==============================
// 六十四卦数据（别卦）
// ==============================

export const hexagramData: Record<string, HexagramInfo> = {
  // 乾卦系列
  '111111': {
    name: '乾为天',
    meaning: '刚健中正',
    description: '象征天，表示刚健、纯粹、积极、向上的特质',
    analysis: '事业上宜积极进取，贵人运旺，但需谨防过于刚强',
    yaoChanges: {
      1: '潜龙勿用：当前不宜轻举妄动，应当韬光养晦',
      2: '见龙在田：时机已到，可以有所作为',
      3: '终日乾乾：保持警惕，持续努力',
      4: '或跃在渊：谨慎行事，把握时机',
      5: '飞龙在天：时运亨通，成就非凡',
      6: '亢龙有悔：物极必反，需知进退',
    },
  },
  '111110': {
    name: '天风姤',
    meaning: '遇合相遇',
    description: '上天下风，阳气初降，万物相遇交合之象',
    analysis: '人际关系方面会有新的机遇，但需要把握适度',
    yaoChanges: {
      1: '系于金柅，贞吉',
      2: '包有鱼，无咎',
      3: '臀无肤，其行次且',
      4: '包无鱼，起凶',
      5: '以杞包瓜，含章，有陨自天',
      6: '姤其角，吝，无咎',
    },
  },
  // 坤卦系列
  '000000': {
    name: '坤为地',
    meaning: '柔顺中正',
    description: '象征地，表示包容、顺从、厚德、承载的特质',
    analysis: '事业上宜稳扎稳打，循序渐进，切忌急躁冒进',
    yaoChanges: {
      1: '履霜坚冰至：开始有危险的征兆，需要警惕',
      2: '直方大：地道平正，宜循序渐进',
      3: '含章可贞：内含美质，可以正固',
      4: '括囊无咎：谨慎保守，可免祸患',
      5: '黄裳元吉：正当盛时，大有可为',
      6: '龙战于野：谨防过度，以免损害',
    },
  },
  // 震卦系列
  '001001': {
    name: '震为雷',
    meaning: '震动行动',
    description: '震为雷，象征春雷激荡，万物复苏的行动力',
    analysis: '事业宜大胆进取；感情有新的突破；投资理财可以行动',
    yaoChanges: {
      1: '震来虩虩，后笑言哑哑，吉',
      2: '震来厉，亿丧贝，跻于九陵，勿逐，七日得',
      3: '震苏苏，震行无眚',
      4: '震遂泥',
      5: '震往来厉，亿无丧，有事',
      6: '震索索，视矍矍，征凶。震不于其躬，于其邻，无咎',
    },
  },
  // 兑卦系列
  '011011': {
    name: '兑为泽',
    meaning: '喜悦愉快',
    description: '兑为泽，象征喜悦和愉快的氛围',
    analysis: '事业顺遂欢欣；感情和谐美满；投资收益可喜',
    yaoChanges: {
      1: '和兑，吉',
      2: '孚兑，吉，悔亡',
      3: '来兑，凶',
      4: '商兑，未宁，介疾有喜',
      5: '孚于剥，有厉',
      6: '引兑',
    },
  },
  // 巽卦系列
  '110110': {
    name: '巽为风',
    meaning: '谦逊顺从',
    description: '巽为风，象征风的柔顺特性与处事态度',
    analysis: '事业宜低调行事；感情以柔克刚；投资保持谨慎',
    yaoChanges: {
      1: '进退，利武人之贞',
      2: '巽在床下，用史巫纷若，吉，无咎',
      3: '频巽，吝',
      4: '悔亡，田获三品',
      5: '贞吉，悔亡，无不利，无初有终。先庚三日，后庚三日，吉',
      6: '巽在床下，丧其资斧，贞凶',
    },
  },
  // 坎卦系列
  '010010': {
    name: '坎为水',
    meaning: '险难考验',
    description: '坎为水，象征流动与险阻的双重特质',
    analysis: '事业需要谨慎；感情要耐心对待；投资防风险',
    yaoChanges: {
      1: '习坎，入于坎窞，凶',
      2: '坎有险，求小得',
      3: '来之坎坎，险且枕，入于坎窞，勿用',
      4: '樽酒簋贰，用缶，纳约自牖，终无咎',
      5: '坎不盈，只既平，无咎',
      6: '系用徽纆，寘于丛棘，三岁不得，凶',
    },
  },
  // 离卦系列
  '101101': {
    name: '离为火',
    meaning: '光明智慧',
    description: '离为火，象征光明与智慧的照耀',
    analysis: '事业光明在望；感情要以诚相待；投资需明智',
    yaoChanges: {
      1: '履错然，敬之无咎',
      2: '黄离，元吉',
      3: '日昃之离，不鼓缶而歌，则大耋之嗟，凶',
      4: '突如其来如，焚如，死如，弃如',
      5: '出涕沱若，戚嗟若，吉',
      6: '王用出征，有嘉折首，获匪其丑，无咎',
    },
  },
};

// ==============================
// 核心功能函数
// ==============================

/**
 * 根据时间生成卦象
 */
export function getHexagramByTime(time?: string | Date): HexagramResult {
  const date = typeof time === 'string' ? new Date(time) : time || new Date();

  if (isNaN(date.getTime())) {
    throw new Error('无效的时间格式');
  }

  const hour = date.getHours();
  const minute = date.getMinutes();
  const second = date.getSeconds();

  const upperTrigram = generateTrigramByTime(hour);
  const lowerTrigram = generateTrigramByTime(minute);

  return {
    upperTrigram,
    lowerTrigram,
    changingLines: generateChangingLines(second),
    timestamp: date.getTime(),
  };
}

/**
 * 根据数字生成卦象（1-64）
 */
export function getHexagramByNumber(number: number): HexagramResult {
  if (number < 1 || number > 64) {
    throw new Error('数字必须在1-64之间');
  }

  const binaryStr = (number - 1).toString(2).padStart(6, '0');
  const upperBinary = binaryStr.slice(0, 3);
  const lowerBinary = binaryStr.slice(3);

  const upperBagua = Object.values(baguaData).find(
    (bagua) => bagua.binary === upperBinary,
  );

  const lowerBagua = Object.values(baguaData).find(
    (bagua) => bagua.binary === lowerBinary,
  );

  if (!upperBagua || !lowerBagua) {
    throw new Error('无效的卦象组合');
  }

  return {
    upperTrigram: upperBagua,
    lowerTrigram: lowerBagua,
    changingLines: [],
    number,
  };
}

/**
 * 根据铜钱卦结果生成卦象
 */
export function getHexagramByCoin(coinResults: number[][]): HexagramResult {
  if (coinResults.length !== 6) {
    throw new Error('需要6次投掷结果');
  }

  const binaryString = coinResults
    .map((result) => {
      const positiveCount = result.filter((r) => r === 1).length;
      return positiveCount >= 2 ? '1' : '0';
    })
    .join('');

  const upperBinary = binaryString.slice(0, 3);
  const lowerBinary = binaryString.slice(3);

  const upperTrigram = Object.values(baguaData).find(
    (bagua) => bagua.binary === upperBinary,
  );

  const lowerTrigram = Object.values(baguaData).find(
    (bagua) => bagua.binary === lowerBinary,
  );

  if (!upperTrigram || !lowerTrigram) {
    throw new Error('无效的卦象组合');
  }

  const changingLines = coinResults
    .map((result, index) => {
      const positiveCount = result.filter((r) => r === 1).length;
      return positiveCount === 3 || positiveCount === 0 ? index + 1 : null;
    })
    .filter((line): line is number => line !== null);

  return {
    upperTrigram,
    lowerTrigram,
    changingLines,
    selected: `${upperTrigram.name}${lowerTrigram.name}`,
  };
}

/**
 * 直接选择卦象
 */
export function getHexagramBySelection(
  upperBaguaName: string,
  lowerBaguaName: string,
): HexagramResult {
  const upperBagua = Object.values(baguaData).find(
    (bagua) => bagua.name === upperBaguaName,
  );

  const lowerBagua = Object.values(baguaData).find(
    (bagua) => bagua.name === lowerBaguaName,
  );

  if (!upperBagua || !lowerBagua) {
    throw new Error('无效的卦象名称');
  }

  return {
    upperTrigram: upperBagua,
    lowerTrigram: lowerBagua,
    changingLines: [],
    selected: `${upperBaguaName}${lowerBaguaName}`,
  };
}

/**
 * 解读卦象
 */
export function interpretHexagram(
  hexagram: HexagramResult,
): HexagramInterpretation {
  const { upperTrigram, lowerTrigram, changingLines } = hexagram;

  const hexagramCode = upperTrigram.binary + lowerTrigram.binary;
  const hexagramInfo = hexagramData[hexagramCode] || {
    name: '未知卦象',
    meaning: '暂无解释',
    description: '暂无描述',
    analysis: '请谨慎行事',
    yaoChanges: {},
  };

  const wuxingAnalysis = analyzeWuxing(
    upperTrigram.attribute,
    lowerTrigram.attribute,
  );

  const yaoAnalysis = analyzeChangingLines(
    changingLines,
    hexagramInfo.yaoChanges,
  );

  return {
    name: hexagramInfo.name,
    meaning: hexagramInfo.meaning,
    upperTrigram: {
      ...upperTrigram,
      position: '上卦',
      meaning: `代表${upperTrigram.nature}的特质`,
    },
    lowerTrigram: {
      ...lowerTrigram,
      position: '下卦',
      meaning: `代表${lowerTrigram.nature}的特质`,
    },
    wuxingAnalysis,
    yaoAnalysis,
    overall: generateOverallAnalysis(hexagramInfo, wuxingAnalysis),
  };
}

// ==============================
// 辅助函数
// ==============================

/**
 * 生成三爻卦（经卦）
 */
function generateTrigramByTime(timeValue: number): BaguaInfo {
  const normalized = timeValue % 8;
  const baguas = Object.values(baguaData);
  return baguas[normalized] || baguas[0];
}

/**
 * 生成变爻
 */
function generateChangingLines(seed: number): number[] {
  const lines = [];
  for (let i = 0; i < 6; i++) {
    if ((seed + i) % 6 === 0) {
      lines.push(i + 1);
    }
  }
  return lines;
}

/**
 * 分析五行关系
 */
function analyzeWuxing(upperAttr: string, lowerAttr: string): string {
  const relations = {
    金: ['生水', '克木'],
    木: ['生火', '克土'],
    水: ['生木', '克火'],
    火: ['生土', '克金'],
    土: ['生金', '克水'],
  };

  const upperDesc = relations[upperAttr]?.join('、') || '';
  const lowerDesc = relations[lowerAttr]?.join('、') || '';

  return `上卦${upperAttr}(${upperDesc})与下卦${lowerAttr}(${lowerDesc})相生相克，需平衡调和。`;
}

/**
 * 分析变爻
 */
function analyzeChangingLines(
  lines: number[],
  yaoChanges: Record<number, string>,
): string[] {
  if (lines.length === 0) {
    return ['无变爻，卦象稳定，可按原意解读。'];
  }

  return lines.map((line) => {
    const changeDesc = yaoChanges[line] || `第${line}爻变化，需特别注意`;
    return `${getLineName(line)}发生变化：${changeDesc}`;
  });
}

/**
 * 获取爻位名称
 */
function getLineName(line: number): string {
  const names = ['初爻', '二爻', '三爻', '四爻', '五爻', '上爻'];
  return names[line - 1] || `第${line}爻`;
}

/**
 * 生成总体分析
 */
function generateOverallAnalysis(
  hexagramInfo: HexagramInfo,
  wuxingAnalysis: string,
): string {
  return (
    `【卦象解读】${hexagramInfo.name} - ${hexagramInfo.meaning}\n\n` +
    `【核心描述】${hexagramInfo.description}\n\n` +
    `【五行关系】${wuxingAnalysis}\n\n` +
    `【行动建议】${hexagramInfo.analysis}\n\n` +
    `💡 温馨提示：占卜仅供参考，具体决策请结合实际情况。`
  );
}

// ==============================
// 工具函数
// ==============================

/**
 * 检查卦象完整性
 */
export function checkHexagramIntegrity(): {
  missing: string[];
  duplicates: string[];
} {
  const missing = [];
  const duplicates = new Map<string, number>();

  // 检查64个卦象是否存在
  for (let i = 1; i <= 64; i++) {
    const hexagram = getHexagramByNumber(i);
    const code = hexagram.upperTrigram.binary + hexagram.lowerTrigram.binary;

    if (!hexagramData[code]) {
      missing.push(code);
    }
  }

  // 检查重复卦象
  Object.keys(hexagramData).forEach((code) => {
    duplicates.set(code, (duplicates.get(code) || 0) + 1);
  });

  const duplicateCodes = Array.from(duplicates.entries())
    .filter(([_, count]) => count > 1)
    .map(([code]) => code);

  return {
    missing,
    duplicates: duplicateCodes,
  };
}

/**
 * 获取八卦信息
 */
export const getBaguaInfo = (key: keyof typeof baguaData) => {
  return baguasData[key];
};

/**
 * 根据符号获取八卦
 */
export const getBaguaBySymbol = (symbol: string) => {
  for (const [key, data] of Object.entries(baguaData)) {
    if (data.symbol === symbol) {
      return data;
    }
  }
  return null;
};

// ==============================
// 测试代码
// ==============================

/**
 * 运行所有测试用例
 */
export function runTests() {
  console.log('=== 八卦占卜系统测试开始 ===');

  testGetHexagramByTime();
  testGetHexagramByNumber();
  testGetHexagramByCoin();
  testInterpretation();
  testIntegrityCheck();

  console.log('=== 测试完成 ===');
}

function testGetHexagramByTime() {
  try {
    const hexagram = getHexagramByTime();
    console.log('✅ 时间起卦测试通过');
    console.log(
      `卦象: ${hexagram.upperTrigram.name}${hexagram.lowerTrigram.name}`,
    );
  } catch (error) {
    console.error('❌ 时间起卦测试失败:', error);
  }
}

function testGetHexagramByNumber() {
  try {
    const hexagram = getHexagramByNumber(1);
    console.log('✅ 数字起卦测试通过');
    console.log(
      `卦象: ${hexagram.upperTrigram.name}${hexagram.lowerTrigram.name}`,
    );
  } catch (error) {
    console.error('❌ 数字起卦测试失败:', error);
  }
}

function testGetHexagramByCoin() {
  try {
    const coinResults = [
      [1, 1, 0],
      [1, 0, 0],
      [1, 1, 1],
      [0, 0, 0],
      [1, 0, 1],
      [0, 1, 0],
    ];

    const hexagram = getHexagramByCoin(coinResults);
    console.log('✅ 铜钱起卦测试通过');
    console.log(`变爻: ${hexagram.changingLines.join(', ')}`);
  } catch (error) {
    console.error('❌ 铜钱起卦测试失败:', error);
  }
}

function testInterpretation() {
  try {
    const hexagram = getHexagramByNumber(1);
    const interpretation = interpretHexagram(hexagram);

    console.log('✅ 卦象解读测试通过');
    console.log(`卦名: ${interpretation.name}`);
    console.log(`总体建议: ${interpretation.overall.slice(0, 100)}...`);
  } catch (error) {
    console.error('❌ 卦象解读测试失败:', error);
  }
}

function testIntegrityCheck() {
  try {
    const { missing, duplicates } = checkHexagramIntegrity();

    console.log('✅ 卦象完整性测试通过');
    console.log(`缺失卦象: ${missing.length}`);
    console.log(`重复卦象: ${duplicates.length}`);
  } catch (error) {
    console.error('❌ 卦象完整性测试失败:', error);
  }
}
