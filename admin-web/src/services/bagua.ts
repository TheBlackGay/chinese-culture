/**
 * 八卦与六十四卦核心服务
 * 提供完整的易经占卜功能实现
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
  // 乾卦系列 (1-8)
  '111111': {
    name: '乾为天',
    meaning: '刚健中正',
    description: '象征天，表示刚健、纯粹、积极、向上的特质',
    analysis:
      '事业上宜积极进取，贵人运旺，但需谨防过于刚强。感情要坚定专一，避免浮躁。投资宜把握时机，果断行动。健康方面注意肝火旺盛、失眠多梦。',
    yaoChanges: {
      1: '潜龙勿用：当前不宜轻举妄动，应当韬光养晦，等待时机。',
      2: '见龙在田：时机已到，可以有所作为，但需保持谦逊。',
      3: '终日乾乾：保持警惕，持续努力，不可松懈。',
      4: '或跃在渊：谨慎行事，把握时机，准备突破。',
      5: '飞龙在天：时运亨通，成就非凡，处于人生巅峰。',
      6: '亢龙有悔：物极必反，需知进退，避免骄傲自满。',
    },
  },
  '111110': {
    name: '天风姤',
    meaning: '遇合相遇',
    description: '上天下风，阳气初降，万物相遇交合之象',
    analysis:
      '人际关系方面会有新的机遇，但需要把握适度。感情易有意外之缘，需谨慎选择。投资宜合作，注意合作关系的平衡。健康方面注意呼吸道问题和情绪波动。',
    yaoChanges: {
      1: '系于金柅，贞吉：根基稳固，保持正道可获吉祥。',
      2: '包有鱼，无咎：小有收获，无需担忧。  ',
      3: '臀无肤，其行次且：行动受阻，需耐心等待。',
      4: '包无鱼，起凶：计划落空，可能遭遇小人。',
      5: '以杞包瓜，含章，有陨自天：贵人相助，意外之喜。',
      6: '姤其角，吝，无咎：小有摩擦，总体平安。',
    },
  },
  '111101': {
    name: '天山遁',
    meaning: '退避隐忍',
    description: '天在山上，表示需要适时退避，韬光养晦。',
    analysis:
      '时运不济时，应当明智地选择退守，等待时机。感情需保持距离，避免冲突。投资宜保守，减少风险。健康注意消化系统和情绪压抑。',
    yaoChanges: {
      1: '遁尾，厉，勿用有攸往：退避不及，处境危险，不宜行动。',
      2: '执之用黄牛之革，莫之胜说：坚持原则，可保平安。  ',
      3: '系遁，有疾厉，畜臣妾吉：受困于情，需调整心态。  ',
      4: '好遁，君子吉，小人否：主动退避，君子得利。  ',
      5: '嘉遁，贞吉：美好退避，坚守正道可获吉祥。  ',
      6: '肥遁，无不利：彻底退隐，无所不吉。  ',
    },
  },
  '111011': {
    name: '天火同人',
    meaning: '同心协力',
    description: '上天下火，太阳高照，万物和谐相处。',
    analysis:
      '人际关系和谐，合作共事有利。感情需要真诚沟通，共同成长。投资适合团队合作，共享收益。健康方面精力充沛，身心愉悦。',
    yaoChanges: {
      1: '同人于门，无咎：在门外相遇，没有灾祸。  ',
      2: '同人于宗，吝：局限于小团体，有所遗憾。  ',
      3: '伏戎于莽，升其高陵，三岁不兴：隐藏危机，需谨慎观察。  ',
      4: '乘其墉，弗克攻，吉：登上城墙，不进攻可获吉祥。  ',
      5: '同人先号啕而后笑，大师克相遇：先忧后喜，最终成功。  ',
      6: '同人于郊，无悔：在郊区相遇，没有后悔。  ',
    },
  },
  '111000': {
    name: '天地否',
    meaning: '闭塞不通',
    description: '天地不交，阴阳隔绝，事物处于停滞状态。',
    analysis:
      '事业上暂时受阻，需要耐心等待。感情沟通不畅，需主动交流。投资保持谨慎，避免盲目跟风。健康注意气血不通和情绪低落。',
    yaoChanges: {
      1: '拔茅茹，以其汇，贞吉亨：团结互助，可获吉祥。  ',
      2: '包承，小人吉，大人否亨：小人得势，君子受阻。  ',
      3: '包羞：包容羞辱，需忍耐。  ',
      4: '有命，无咎，畴离祉：得到天命，没有灾祸。  ',
      5: '休否，大人吉，其亡其亡，系于苞桑：停止闭塞，君子得利。  ',
      6: '倾否，先否后喜：局面颠覆，先苦后甜。  ',
    },
  },
  '111001': {
    name: '天雷无妄',
    meaning: '不妄为，顺其自然',
    description: '天雷震动，万物按自然规律生长。',
    analysis:
      '事业顺其自然发展，不可强求。感情需要真诚相待，避免做作。投资遵循市场规律，不投机取巧。健康保持自然作息，身心平衡。',
    yaoChanges: {
      1: '无妄往吉：无所企图，前往吉祥。  ',
      2: '不耕获，不菑畲，则利有攸往：不劳而获，利于前行。  ',
      3: '无妄之灾，或系之牛，行人之得：意外之灾，需谨慎应对。  ',
      4: '可贞无咎：坚守正道，没有灾祸。  ',
      5: '无妄之疾，勿药有喜：意外之病，不治自愈。  ',
      6: '无妄行有眚，无攸利：胡作非为，无所利益。  ',
    },
  },
  '111010': {
    name: '天水讼',
    meaning: '争端诉讼',
    description: '天在上，水在下，阳刚之气下降，表示争执、冲突。',
    analysis:
      '事业易生争议，需明智处理。感情需要沟通化解矛盾。投资谨慎行事，避免利益冲突。健康注意口腔、喉咙问题和情绪纷扰。',
    yaoChanges: {
      1: '不永所事，小有言，终吉：不宜纠缠，小争无妨。  ',
      2: '不克讼归而逋，其邑人三百户无眚：不胜诉讼，退避可免灾。  ',
      3: '食旧德贞厉：依靠过往恩德，坚守正道。  ',
      4: '不克讼复即命渝安贞吉：和解恢复，坚守正道可获吉祥。  ',
      5: '讼元吉：诉讼得胜，大吉。  ',
      6: '或锡之鞶带终朝三褫之：突然得利，又迅速失去。  ',
    },
  },
  '111100': {
    name: '天火同人',
    meaning: '和谐共处',
    description: '天与火相合，光明普照，万物和谐。',
    analysis:
      '人际关系融洽，合作顺利。感情稳定美满，需用心经营。投资收益可观，共享成果。健康方面精力充沛，身体康健。',
    yaoChanges: {
      1: '同人于野亨利涉大川：在野外相遇，利于远行。  ',
      2: '同人于门无咎：在门前相遇，没有灾祸。  ',
      3: '伏戎于莽升其高陵三岁不兴：隐藏危机，需警惕三年。  ',
      4: '乘其墉弗克攻吉：登上城墙，不进攻可获吉祥。  ',
      5: '同人先号啕而后笑大师克相遇：先忧后喜，合作成功。  ',
      6: '同人于郊无悔：在郊区相遇，没有后悔。  ',
    },
  },

  // 坤卦系列 (9-16)
  '000000': {
    name: '坤为地',
    meaning: '柔顺中正',
    description: '象征地，表示包容、顺从、厚德、承载的特质',
    analysis:
      '事业上宜稳扎稳打，循序渐进。感情要包容理解，互相支持。投资稳健发展，不宜激进。健康注意脾胃功能和情绪稳定。',
    yaoChanges: {
      1: '履霜坚冰至：开始有危险的征兆，需要警惕。  ',
      2: '直方大：地道平正，宜循序渐进。  ',
      3: '含章可贞：内含美质，可以正固。  ',
      4: '括囊无咎：谨慎保守，可免祸患。  ',
      5: '黄裳元吉：正当盛时，大有可为。  ',
      6: '龙战于野：谨防过度，以免损害。  ',
    },
  },
  '000001': {
    name: '地雷复',
    meaning: '回复、再生',
    description: '雷在地中，春雷发动，万物复苏的征兆。',
    analysis:
      '事物开始新的循环，充满希望。感情需要重新建立联系。投资看到转机，可适度介入。健康注意春季养生和情绪波动。',
    yaoChanges: {
      1: '不远复无祗悔元吉：迅速恢复，大吉。  ',
      2: '休复吉：休息后恢复，吉祥。  ',
      3: '频复厉无咎：频繁变化，谨慎无灾。  ',
      4: '中行独复：中间道路独自恢复。  ',
      5: '敦复无悔：诚恳恢复，没有后悔。  ',
      6: '迷复凶有灾眚：迷失方向，凶险。  ',
    },
  },
  '000111': {
    name: '地泽临',
    meaning: '亲临、督导',
    description: '大地与泽水相临，象征居高临下地督导和管理。',
    analysis:
      '事业上需要以身作则，发挥领导作用。感情要多关心对方。投资需谨慎监管。健康注意自我监督和调整。',
    yaoChanges: {
      1: '咸临贞吉：真诚督导，吉祥。  ',
      2: '咸临吉无不利：真诚督导，无所不利。  ',
      3: '甘临无攸利既忧之无咎：过分甜言蜜语，需注意。  ',
      4: '至临无咎：到达现场督导，没有灾祸。  ',
      5: '知临大君之宜吉：明智督导，君子得利。  ',
      6: '敦临吉无咎：诚恳督导，吉祥平安。  ',
    },
  },
  '000110': {
    name: '地风升',
    meaning: '上升、发展',
    description: '地风吹动，象征事业向上发展。',
    analysis:
      '事业发展顺利，适宜上进。感情逐渐升温，需要经营。投资稳步增长，可继续持有。健康状况良好，精力充沛。',
    yaoChanges: {
      1: '允升大吉：顺利上升，大吉。  ',
      2: '孚乃利用禴无咎：诚信为本，没有灾祸。  ',
      3: '升虚邑：空城计，需谨慎。  ',
      4: '王用亨于岐山吉无咎：君主祭祀，吉祥平安。  ',
      5: '贞吉升阶：坚守正道，步步高升。  ',
      6: '冥升利于不息之贞：深夜上升，持久坚持。  ',
    },
  },
  '000101': {
    name: '地火明夷',
    meaning: '光明受损',
    description: '火光入地，象征光明受挫、处境不利。',
    analysis:
      '事业需要韬光养晦，等待时机。感情受挫需耐心调解。投资保持谨慎，避免损失。健康注意眼睛和情绪低落。',
    yaoChanges: {
      1: '明夷于飞垂其翼：光明受挫，如鸟垂翼。  ',
      2: '明夷夷于左股用拯马壮吉：左腿受伤，乘马得救。  ',
      3: '明夷于南狩得其大首：南方征伐，获得首领。  ',
      4: '入于左腹获明夷之心：深入内心，获得真实。  ',
      5: '箕子之明夷利贞：箕子坚守，利于正道。  ',
      6: '不明晦初登于天后入于地：光明消失，由盛转衰。  ',
    },
  },
  '000100': {
    name: '地山谦',
    meaning: '谦逊有德',
    description: '大地高山，象征山在地下不露锋芒的谦逊态度。',
    analysis:
      '事业宜保持低调，虚心学习。感情要谦和有礼。投资谨慎为上，避免冒进。健康保持平和心态。',
    yaoChanges: {
      1: '谦谦君子用涉大川吉：谦虚君子，利于远行。  ',
      2: '鸣谦贞吉：谦虚发声，坚守正道。  ',
      3: '劳谦君子有终吉：勤劳谦虚，最终吉祥。  ',
      4: '无不利撝谦：无所不利，保持谦逊。  ',
      5: '不富以其邻利用侵伐无不利：不富有，联合他人。  ',
      6: '鸣谦利用行师征邑国：谦虚用兵，征伐敌国。  ',
    },
  },
  '000010': {
    name: '地水师',
    meaning: '统帅、军队',
    description: '地上行水，象征组织严密的军队和管理。',
    analysis:
      '事业需要统筹规划，团队协作。感情要协调沟通。投资系统布局，全面考虑。健康注意全身调理。',
    yaoChanges: {
      1: '师出以律否臧凶：军队纪律，否则凶险。  ',
      2: '在师中吉无咎王三锡命：军队内部，吉祥。  ',
      3: '师或舆尸凶：军队载尸，凶险。  ',
      4: '师左次无咎：军队后撤，没有灾祸。  ',
      5: '田有禽利执言无咎：狩猎有获，利于陈述。  ',
      6: '大君有命开国承家小人勿用：君主命令，小人不宜。  ',
    },
  },
  '000011': {
    name: '地火明夷',
    meaning: '光明受阻',
    description: '火光入地，象征事业受挫、需要隐忍。',
    analysis:
      '当前处境不利，需耐心等待。感情需沟通化解矛盾。投资保持谨慎，避免损失。健康注意情绪和休息。',
    yaoChanges: {
      1: '明夷于飞垂其翼：光明受挫，行动受限。  ',
      2: '明夷夷于左股用拯马壮吉：腿部受伤，乘马得救。  ',
      3: '明夷于南狩得其大首：南方征伐，获得首领。  ',
      4: '入于左腹获明夷之心：深入内心，获得真实。  ',
      5: '箕子之明夷利贞：坚守正道，利于稳定。  ',
      6: '不明晦初登于天后入于地：由盛转衰，需调整。  ',
    },
  },

  // 震卦系列 (17-24)
  '001001': {
    name: '震为雷',
    meaning: '震动、行动',
    description: '春雷激荡，万物复苏，象征行动力和变革。',
    analysis:
      '事业宜大胆进取，抓住机遇。感情有新突破，需主动沟通。投资可适当增加仓位。健康注意精力分配和情绪管理。',
    yaoChanges: {
      1: '震来虩虩后笑言哑哑吉：雷声吓人，最后欢笑。  ',
      2: '震来厉亿丧贝跻于九陵勿逐七日得：雷声猛烈，失去财物。  ',
      3: '震苏苏震行无眚：雷声小，行动平安。  ',
      4: '震遂泥：雷声大，陷入泥潭。  ',
      5: '震往来厉亿无丧有事：雷声反复，需谨慎。  ',
      6: '震索索视矍矍征凶：雷声惊恐，出行不吉。  ',
    },
  },
  '001011': {
    name: '雷火丰',
    meaning: '丰盛、充实',
    description: '雷电交加，光明充盈，象征事业有成。',
    analysis:
      '事业蒸蒸日上，成果丰硕。感情美满和谐，需用心经营。投资收益可观，合理分配。健康充满活力，注意调节。',
    yaoChanges: {
      1: '遇其配主虽旬无咎往有尚：遇到匹配，十天无咎。  ',
      2: '丰其蔀日中见斗遇其夷主吉：光明受阻，遇见贵人。  ',
      3: '丰其沛日中见沫折其右肱无咎：光明短暂，手臂受伤。  ',
      4: '丰其蔀日中见斗遇其夷主吉：光明再现，遇见贵人。  ',
      5: '来章有庆誉吉：带来荣耀，吉祥。  ',
      6: '丰其屋蔀其家窥其户阒其无人三岁不觌凶：房屋华丽，人去楼空。  ',
    },
  },
  '001010': {
    name: '雷水解',
    meaning: '解脱、化解',
    description: '雷雨降临，解除干旱，象征困境的解决。',
    analysis:
      '困难即将消除，转机已到。感情冰释前嫌，重归于好。投资有望回本，谨慎操作。健康转危为安，注意调养。',
    yaoChanges: {
      1: '无咎：没有灾祸。  ',
      2: '田获三狐得黄矢贞吉：狩猎有获，利于正道。  ',
      3: '负且乘致寇至贞吝：背负重物，招来盗贼。  ',
      4: '解而拇朋至斯孚：解开束缚，朋友到来。  ',
      5: '君子维有解吉有孚于小人：君子解决，小人不信任。  ',
      6: '公用射隼于高墉之上获之无不利：君主射鸟，获得成功。  ',
    },
  },
  '001111': {
    name: '雷天大壮',
    meaning: '强盛壮大',
    description: '春雷响彻天际，象征气势正盛但需防过犹不及。',
    analysis:
      '事业蓬勃发展，势头强劲。感情热情似火，需保持理性。投资收益丰厚，合理规划。健康充满活力，注意节制。',
    yaoChanges: {
      1: '壮于趾征凶有孚：脚趾强壮，行动凶险。  ',
      2: '贞吉：坚守正道，吉祥。  ',
      3: '小人用壮君子用罔贞厉：小人逞强，君子守正。  ',
      4: '贞吉悔亡藩决不羸壮于大舆之輹：坚固，没有危险。  ',
      5: '丧羊于易无悔：丢失羊只，没有后悔。  ',
      6: '羝羊触藩不能退不能遂无攸利艰则吉：公羊撞墙，进退两难。  ',
    },
  },
  '001110': {
    name: '雷风恒',
    meaning: '持久恒常',
    description: '雷电风行，阴阳交合，象征持之以恒的品质。',
    analysis:
      '事业需要长期坚持，不可半途而废。感情要专一持久。投资要有耐心，等待回报。健康需持续调养。',
    yaoChanges: {
      1: '浚恒贞凶无攸利：深挖不休，凶险。  ',
      2: '悔亡：没有后悔。  ',
      3: '不恒其德或承之羞贞吝：不能坚持，招来羞辱。  ',
      4: '田无禽：狩猎没有收获。  ',
      5: '恒其德贞妇人吉夫子凶：坚持德行，女性吉祥。  ',
      6: '振恒凶：摇摆不定，凶险。  ',
    },
  },
  '001101': {
    name: '雷火家人',
    meaning: '家庭和睦',
    description: '风吹火旺，象征家庭和谐、内外一致。',
    analysis:
      '事业需团队合作，内部协调。感情注重家庭关系。投资稳健发展，风险可控。健康关注家人和整体状况。',
    yaoChanges: {
      1: '闲有家悔亡：管理家庭，没有后悔。  ',
      2: '无攸遂在中馈贞吉：不追求外物，内心满足。  ',
      3: '家人嗃嗃悔厉吉：家庭严格，虽严但吉。  ',
      4: '富家大吉：富裕家庭，大吉。  ',
      5: '王假有家勿恤吉：君主管理家庭，吉祥。  ',
      6: '有孚威如终吉：诚信威严，最终吉祥。  ',
    },
  },
  '001100': {
    name: '雷地豫',
    meaning: '愉悦安逸',
    description: '春雷响动，大地欢欣，象征事业顺利、心情愉悦。',
    analysis:
      '事业发展平稳，感情和谐美满。投资略有收益，保持现状。健康心情舒畅，精力充沛。',
    yaoChanges: {
      1: '鸣豫凶：喧闹娱乐，有凶险。  ',
      2: '介于石不终日贞吉：如石头稳固，坚守正道。  ',
      3: '盱豫悔迟有悔：过分期待，后悔。  ',
      4: '由豫大有得勿疑朋盍簪：欢乐之中，获得收获。  ',
      5: '贞疾恒不死：正道疾病，长久不愈。  ',
      6: '冥豫成有渝无咎：沉迷享乐，改变可免灾。  ',
    },
  },
  '001000': {
    name: '雷山小过',
    meaning: '小有过失',
    description: '雷在山上，表示行动谨慎，避免大错。',
    analysis:
      '做事需谨慎小心，小过无妨。感情要包容理解。投资注意细节，避免失误。健康注意小病小痛。',
    yaoChanges: {
      1: '飞鸟以凶：飞鸟受困，有凶险。  ',
      2: '过其祖遇其妣不及其君三岁不兴：超越祖先，无大作为。  ',
      3: '弗过防之得舆只轮往厉：不超越，有车轮。  ',
      4: '无咎弗过遇雨吉：没有过错，遇到雨水吉祥。  ',
      5: '密云不雨自我西郊：乌云密集，无雨自散。  ',
      6: '弗过遇之往厉必戒勿用永贞：不超越，需警惕。  ',
    },
  },

  // 巽卦系列 (25-32)
  '110110': {
    name: '巽为风',
    meaning: '谦逊顺从',
    description: '风的柔顺特性，象征处事态度和沟通方式。',
    analysis:
      '事业宜低调行事，以柔克刚。感情要善于倾听。投资保持谨慎，寻求合作。健康注意呼吸系统和情绪表达。',
    yaoChanges: {
      1: '进退利武人之贞：前进后退，利于军人。  ',
      2: '巽在床下用史巫纷若吉无咎：床下占卜，吉祥平安。  ',
      3: '频巽吝：频繁谦逊，有所遗憾。  ',
      4: '悔亡田获三品：后悔消失，获得丰收。  ',
      5: '贞吉悔亡无不利无初有终：坚守正道，最终成功。  ',
      6: '巽在床下丧其资斧贞凶：床下丢失财物，凶险。  ',
    },
  },
  '110111': {
    name: '风天小畜',
    meaning: '蓄养积累',
    description: '风行天上，涵养待时，象征积蓄力量。',
    analysis:
      '事业需要积累经验，不可急于求成。感情要慢慢培养。投资稳中求进，注重长期收益。健康重在调养。',
    yaoChanges: {
      1: '复自道何其咎吉：回归正道，没有灾祸。  ',
      2: '牵复吉：与人合作，吉祥。  ',
      3: '舆说辐夫妻反目：车辆损坏，夫妻不和。  ',
      4: '有孚血去惕出无咎：诚信为本，没有灾祸。  ',
      5: '有孚挛如富以其邻：诚信相连，富裕邻居。  ',
      6: '既雨既处尚德载妇贞厉月几望君子征凶：降雨停止，需谨慎。  ',
    },
  },
  '110000': {
    name: '风地观',
    meaning: '观察体察',
    description: '风行地上，象征观察万物、了解情况。',
    analysis:
      '事业需要细致观察，把握时机。感情要多了解对方。投资审时度势，避免盲动。健康注意观察和思考。',
    yaoChanges: {
      1: '童观小人无咎君子吝：小孩观察，小人无灾。  ',
      2: '窥观利女贞：偷看，利于女性。  ',
      3: '观我生进退：观察自身，决定去留。  ',
      4: '观国之光利用宾于王：观察国家，可入仕。  ',
      5: '观我生君子无咎：观察自身，君子平安。  ',
      6: '观其生君子无咎：观察他人，君子平安。  ',
    },
  },
  '110001': {
    name: '风山渐',
    meaning: '循序渐进',
    description: '风在山上徐徐吹动，象征逐步推进、稳步发展。',
    analysis:
      '事业需循序渐进，不可操之过急。感情要慢慢培养。投资稳健为主，长期持有。健康重在持续调养。',
    yaoChanges: {
      1: '鸿渐于干小子厉有言无咎：大雁飞翔，小人危险。  ',
      2: '鸿渐于磐饮食衎衎吉：大雁停歇，生活安逸。  ',
      3: '鸿渐于陆夫征不复妇孕不育凶：大雁飞落，婚姻问题。  ',
      4: '鸿渐于木或得其桷无咎：大雁停树，获得支持。  ',
      5: '鸿渐于陵妇三岁不孕终莫之胜吉：大雁高飞，婚姻顺利。  ',
      6: '鸿渐于陆其羽可用为仪吉：大雁归来，可作礼仪。  ',
    },
  },
  '110010': {
    name: '风水涣',
    meaning: '涣散流动',
    description: '风吹水面，四散流动，象征分散与重组。',
    analysis:
      '事业需要多方位发展，避免集中风险。感情需要适当空间。投资应分散配置。健康注意调节和平衡。',
    yaoChanges: {
      1: '用拯马壮吉：骑马拯救，吉祥。  ',
      2: '涣奔其机悔亡：逃散无阻，后悔消失。  ',
      3: '涣其躬无悔：身体分散，没有后悔。  ',
      4: '涣其群元吉涣有丘匪夷所思：群体分散，大吉。  ',
      5: '涣汗其大号涣王居无咎：大声呼喊，君主安居。  ',
      6: '涣其血去逖出无咎：流出血液，远离危险。  ',
    },
  },
  '110011': {
    name: '风泽中孚',
    meaning: '诚信信任',
    description: '风行泽上，万物生长，象征以诚感化他人。',
    analysis:
      '事业以诚信为本，建立信任。感情需要真诚沟通。投资讲究信誉。健康保持乐观积极心态。',
    yaoChanges: {
      1: '虞吉有它不燕：准备充足，有所保留。  ',
      2: '鸣鹤在阴其子和之我有好爵吾与尔靡之：鹤声相和，共享美酒。  ',
      3: '得敌或鼓或罢或泣或歌：遇到敌人，或战或和。  ',
      4: '月几望马匹亡无咎：月底月亮，失去战马。  ',
      5: '有孚挛如无咎：诚信相连，没有灾祸。  ',
      6: '翰音登于天贞凶：声音上天，占卜凶险。  ',
    },
  },
  '110100': {
    name: '风火家人',
    meaning: '家庭和睦',
    description: '风与火相和，象征家庭和谐、内外一致。',
    analysis:
      '事业需团队合作，内部协调。感情注重家庭。投资稳健发展。健康关注家人和整体状况。',
    yaoChanges: {
      1: '闲有家悔亡：家庭管理，没有后悔。  ',
      2: '无攸遂在中馈贞吉：不追求外物，内心满足。  ',
      3: '家人嗃嗃悔厉吉：家庭严格，虽严但吉。  ',
      4: '富家大吉：富裕家庭，吉祥。  ',
      5: '王假有家勿恤吉：君主管理，吉祥。  ',
      6: '有孚威如终吉：诚信威严，最终吉祥。  ',
    },
  },
  '110101': {
    name: '风雷益',
    meaning: '增益、进步',
    description: '风雷相激，象征互相补益、共同进步。',
    analysis:
      '事业互利共赢，合作顺利。感情互相支持。投资共享收益。健康精力充沛。',
    yaoChanges: {
      1: '元吉利贞：大吉，利于正道。  ',
      2: '或益之十朋之龟弗克违元吉：增加财富，没有阻碍。  ',
      3: '益之用凶事无咎：增加困难，没有灾祸。  ',
      4: '中行告公从利用为邑：中间道路，可建城池。  ',
      5: '有孚惠心勿问元吉：诚信为本，大吉。  ',
      6: '莫益之或击之立心勿恒凶：无人增益，可能受击。  ',
    },
  },

  // 坎卦系列 (33-40)
  '010010': {
    name: '坎为水',
    meaning: '险难考验',
    description: '水流不息，象征人生中的困境和挑战。',
    analysis:
      '事业需要谨慎应对风险。感情要相互扶持。投资需防范损失。健康注意安全和情绪波动。',
    yaoChanges: {
      1: '习坎入于坎窞凶：反复困境，处境危险。  ',
      2: '坎有险求小得：面临风险，小有收获。  ',
      3: '来之坎坎险且枕入于坎窞勿用：危险重重，不宜行动。  ',
      4: '樽酒簋贰用缶纳约自牖终无咎：简单宴请，化解矛盾。  ',
      5: '坎不盈只既平无咎：水位适中，没有灾祸。  ',
      6: '系用徽纆寘于丛棘三岁不得凶：被束缚，三年无法解脱。  ',
    },
  },
  '010011': {
    name: '水泽节',
    meaning: '节制适度',
    description: '水量适中，不溢不涸，象征把握分寸。',
    analysis:
      '事业要懂得节制，避免过度。感情需要适度表达。投资量力而行。健康注意规律作息。',
    yaoChanges: {
      1: '不出户庭无咎：待在家中，没有灾祸。  ',
      2: '不出门庭凶：不外出，有凶险。  ',
      3: '不节若则嗟若无咎：不懂节制，会后悔。  ',
      4: '安节亨：懂得节制，顺利通达。  ',
      5: '甘节吉往有尚：享受适度，吉祥。  ',
      6: '苦节贞凶悔亡：过分节制，凶险。  ',
    },
  },
  '010101': {
    name: '水火既济',
    meaning: '完成成就',
    description: '水在火上，象征事情已经完成但需防盛极而衰。',
    analysis:
      '事业已见成效，小成即止。感情趋于圆满。投资收获在望。健康状况良好但需注意保养。',
    yaoChanges: {
      1: '曳其轮濡其尾无咎：拉住车轮，弄湿尾巴。  ',
      2: '妇丧其茀勿逐七日得：妻子丢失头饰，七天找回。  ',
      3: '高宗伐鬼方三年克之小人勿用：讨伐敌国，三年成功。  ',
      4: '繻有衣袽终日戒：需要防备，整日警惕。  ',
      5: '东邻杀牛不如西邻之禴祭实受其福：西边祭祀，获得福气。  ',
      6: '濡其首厉：弄湿头部，危险。  ',
    },
  },
  '010100': {
    name: '水山蹇',
    meaning: '艰难困阻',
    description: '水流被山阻挡，象征行动受阻、困难重重。',
    analysis:
      '事业暂时受阻，需寻求突破。感情有所阻碍，需要沟通。投资保持谨慎。健康注意调理身心。',
    yaoChanges: {
      1: '往蹇来誉：前往困难，返回得誉。  ',
      2: '王臣蹇蹇匪躬之故：君臣艰难，非为自身。  ',
      3: '往蹇来反：前往困难，返回再试。  ',
      4: '往蹇来连：前往困难，连续受阻。  ',
      5: '大蹇朋来：大难之时，朋友相助。  ',
      6: '往蹇来硕吉利见大人：前往困难，最终成功。  ',
    },
  },
  '010110': {
    name: '水地比',
    meaning: '亲近团结',
    description: '水滋润大地，象征人际关系和谐、互相支持。',
    analysis:
      '事业适合合作，建立关系。感情需要增进了解。投资寻求互助。健康注意调养。',
    yaoChanges: {
      1: '有孚比之无咎有孚盈缶终来有他吉：诚信合作，获得帮助。  ',
      2: '比之自内贞吉：内部团结，坚守正道。  ',
      3: '比之匪人：与不适当的人合作。  ',
      4: '外比之贞吉：外部团结，吉祥。  ',
      5: '显比王用三驱失前禽邑人不诫吉：公开团结，君主狩猎。  ',
      6: '比之无首凶：没有首领，凶险。  ',
    },
  },
  '010111': {
    name: '水天需',
    meaning: '等待时机',
    description: '水在天上，云雨将至，象征耐心等待、蓄势待发。',
    analysis:
      '事业需要等待时机，不可操之过急。感情保持耐心。投资稳中求进。健康注意休息和准备。',
    yaoChanges: {
      1: '需于郊利用恒无咎：在郊外等待，利于持久。  ',
      2: '需于沙小有言终吉：在沙滩等待，稍有波折。  ',
      3: '需于泥致寇至：在泥中等待，招来敌人。  ',
      4: '需于血出自穴：在血中等待，从洞穴出。  ',
      5: '酒食贞吉：享受美食，坚守正道。  ',
      6: '入于穴有不速之客三人来敬之终吉：进入洞穴，客人来访。  ',
    },
  },
  '010001': {
    name: '水火未济',
    meaning: '未完成发展',
    description: '火在水上，象征事情尚未完成，需要继续努力。',
    analysis:
      '事业尚需努力，不可松懈。感情有待发展。投资需要时间沉淀。健康注意持续调养。',
    yaoChanges: {
      1: '濡其尾吝：弄湿尾巴，有所遗憾。  ',
      2: '曳其轮贞吉：拉住车轮，坚守正道。  ',
      3: '未济征凶利涉大川：事情未成，利于远行。  ',
      4: '贞吉悔亡震用伐鬼方三年有赏于大国：坚守正道，得胜归来。  ',
      5: '出涕沱若戚嗟若吉：哭泣悲伤，最终吉祥。  ',
      6: '酒食形渥贞凶：饮酒过多，凶险。  ',
    },
  },
  '010000': {
    name: '水地比',
    meaning: '亲近团结',
    description: '水滋润大地，象征和谐共处、互相支持。',
    analysis:
      '事业适合合作发展。感情需要增进理解。投资寻求互助共赢。健康注意整体调养。',
    yaoChanges: {
      1: '有孚比之无咎：诚信为本，没有灾祸。  ',
      2: '比之自内贞吉：内部团结，坚守正道。  ',
      3: '比之匪人：与不适当的人合作。  ',
      4: '外比之贞吉：外部团结，吉祥。  ',
      5: '显比王用三驱失前禽邑人不诫吉：公开团结，君主狩猎。  ',
      6: '比之无首凶：没有首领，凶险。  ',
    },
  },

  // 离卦系列 (41-48)
  '101101': {
    name: '离为火',
    meaning: '光明智慧',
    description: '火光照耀，象征光明、智慧和文明。',
    analysis:
      '事业前景光明，适合文化教育类工作。感情需要真诚相待。投资需谨慎选择项目。健康注意用眼和情绪。',
    yaoChanges: {
      1: '履错然敬之无咎：行走错误，恭敬可免灾。  ',
      2: '黄离元吉：黄色光明，大吉。  ',
      3: '日昃之离不鼓缶而歌则大耋之嗟凶：太阳西斜，唱歌悲伤。  ',
      4: '突如其来如焚如死如弃如：突然到来，如同焚烧。  ',
      5: '出涕沱若戚嗟若吉：哭泣悲伤，最终吉祥。  ',
      6: '王用出征有嘉折首获匪其丑无咎：君主出征，获得胜利。  ',
    },
  },
  '101100': {
    name: '火泽睽',
    meaning: '背离分离',
    description: '火焰向上，兑泽向下，象征意见不合、需要调和。',
    analysis:
      '事业可能有分歧，需沟通协调。感情需要互相理解。投资谨慎决策。健康注意情绪平衡。',
    yaoChanges: {
      1: '观其生小人吉君子否：观察自身，小人得利。  ',
      2: '遇主于巷未失常也：在小巷相遇，没有失去原则。  ',
      3: '见舆曳其牛掣其腿贞厉：车辆被拖，牛腿受阻。  ',
      4: '遇雨既盈暨其朱绂贞吉：遇到雨水，得到红色服饰。  ',
      5: '大臣过誉天子隆礼：大臣受称赞，君主有礼仪。  ',
      6: '公用射隼于高墉之上获之无不利：君主射鸟，获得成功。  ',
    },
  },
  '101110': {
    name: '火风鼎',
    meaning: '革新变革',
    description: '火借风势，象征创新和改革。',
    analysis:
      '事业适合创新变革。感情需要新意。投资可以尝试新项目。健康注意调整和适应。',
    yaoChanges: {
      1: '鼎颠趾利出否得妾以其子无咎：鼎翻脚朝上，得到帮助。  ',
      2: '鼎有实我仇有疾不我能即吉：鼎中有物，敌人有病。  ',
      3: '鼎耳革其行塞雉膏不食方雨亏悔终吉：鼎耳损坏，需要调整。  ',
      4: '鼎折足覆公餗其形渥凶：鼎脚断裂，食物洒落。  ',
      5: '鼎黄耳金铉利贞：黄色鼎耳，利于正道。  ',
      6: '鼎玉铉大吉无不利：玉制鼎钮，大吉。  ',
    },
  },
  '101111': {
    name: '火天大有',
    meaning: '丰盛收获',
    description: '火照天下，光明普照，象征事业成功、财富丰盈。',
    analysis:
      '事业发展顺利，收获颇丰。感情和谐美满。投资收益可观。健康状况良好。',
    yaoChanges: {
      1: '无交害匪咎艰则无咎：没有伤害，困难中平安。  ',
      2: '大车以载有攸往无咎：大车载重，前往平安。  ',
      3: '公用亨于天子小人弗克：君主享乐，小人不能。  ',
      4: '匪其彭无咎：不张扬，没有灾祸。  ',
      5: '厥孚交如威如吉：诚信相交，威严吉祥。  ',
      6: '自天祐之吉无不利：上天保佑，无所不利。  ',
    },
  },
  '101000': {
    name: '火地晋',
    meaning: '进步晋升',
    description: '火光上升，象征事业向上发展、地位提升。',
    analysis:
      '事业发展迅速，宜积极进取。感情逐步深入。投资收益增加。健康状况改善。',
    yaoChanges: {
      1: '晋如摧如贞吉罔孚裕无咎：向上发展，没有阻碍。  ',
      2: '晋如愁如贞吉受兹介福于其王母：晋升困难，获得福气。  ',
      3: '众允悔亡：众人支持，后悔消失。  ',
      4: '晋如硕鼠贞厉：晋升似鼠，危险。  ',
      5: '悔亡失得勿恤往吉无不利：后悔消失，前往吉祥。  ',
      6: '晋其角维用伐邑厉吉无咎贞吝：晋升到顶，吉利。  ',
    },
  },
  '101001': {
    name: '火山旅',
    meaning: '旅行变动',
    description: '火在山上，照亮四方，象征变化和流动性。',
    analysis:
      '事业多变动，需适应环境。感情需要包容理解。投资谨慎为上。健康注意调养。',
    yaoChanges: {
      1: '旅琐琐斯其所取灾：旅途琐事，招来灾难。  ',
      2: '旅即次怀其资得童仆贞：住店有房，得到仆人。  ',
      3: '旅焚其次丧其童仆贞厉：住宿被烧，失去仆人。  ',
      4: '旅于处得其资斧我心不快：住处获得财物，心情不悦。  ',
      5: '射雉一矢亡终以誉命：射中野鸡，获得荣誉。  ',
      6: '鸟焚其巢旅人先笑后号啕丧牛于易凶：鸟巢被烧，先喜后悲。  ',
    },
  },
  '101010': {
    name: '火水未济',
    meaning: '未完成状态',
    description: '火在水上，象征事情尚未完成，需要继续努力。',
    analysis:
      '事业尚需完善，不可松懈。感情有待发展。投资需要时间。健康注意持续努力。',
    yaoChanges: {
      1: '濡其尾吝：弄湿尾巴，有所遗憾。  ',
      2: '曳其轮贞吉：拉住车轮，坚守正道。  ',
      3: '未济征凶利涉大川：事情未成，利于远行。  ',
      4: '贞吉悔亡震用伐鬼方三年有赏于大国：坚守正道，得胜归来。  ',
      5: '出涕沱若戚嗟若吉：哭泣悲伤，最终吉祥。  ',
      6: '酒食形渥贞凶：饮酒过多，凶险。  ',
    },
  },
  '101011': {
    name: '火雷噬嗑',
    meaning: '决断明断',
    description: '雷电交加，象征果断决策、解决问题。',
    analysis:
      '事业需要当机立断。感情应当明确态度。投资把握机会。健康注意节制。',
    yaoChanges: {
      1: '屦校灭趾无咎：刑具伤脚，没有灾祸。  ',
      2: '噬肤灭鼻无咎：咬肉伤鼻，没有灾祸。  ',
      3: '噬腊肉遇毒小吝无咎：咬干肉中毒，稍有遗憾。  ',
      4: '噬乾胏得金矢利艰贞吉：咬硬肉，得到箭头。  ',
      5: '噬乾肉得黄金贞厉无咎：咬干肉，得到黄金。  ',
      6: '何校灭耳凶：刑具伤耳，凶险。  ',
    },
  },

  // 艮卦系列 (49-56)
  '100100': {
    name: '艮为山',
    meaning: '止、静止',
    description: '高山稳重，象征停止和思考。',
    analysis:
      '事业需要适当暂停，总结经验。感情要保持克制。投资宜守不宜进。健康注意休息和调养。',
    yaoChanges: {
      1: '艮其趾无咎利永贞：停止脚部，利于坚守。  ',
      2: '艮其腓不拯其随其心不快：停止腿部，心情不爽。  ',
      3: '艮其限列其夤厉薰心：停止腰部，危险。  ',
      4: '艮其身无咎：停止身体，没有灾祸。  ',
      5: '艮其辅言有序悔亡：停止下巴，言语有序。  ',
      6: '敦艮吉：诚恳静止，吉祥。  ',
    },
  },
  '100101': {
    name: '山火贲',
    meaning: '装饰文明',
    description: '山上有火，象征文明和礼仪。',
    analysis:
      '事业注重形象包装。感情重视仪表。投资讲究外观。健康关注外表和气质。',
    yaoChanges: {
      1: '贲其趾舍车而徒：装饰脚部，放弃车辆。  ',
      2: '贲其须：装饰胡须。  ',
      3: '贲如濡如永贞吉：装饰美丽，长久吉祥。  ',
      4: '贲如皤如白马翰如匪寇婚媾：白色骏马，婚姻相配。  ',
      5: '贲于丘园束帛戋戋吝终吉：装饰山丘，稍有遗憾。  ',
      6: '白贲无咎：白色装饰，没有灾祸。  ',
    },
  },
  '100110': {
    name: '山风蛊',
    meaning: '整顿革新',
    description: '山上大风，象征腐败需要整顿。',
    analysis: '事业需要改革调整。感情要澄清误会。投资需要创新。健康及时调理。',
    yaoChanges: {
      1: '干父之蛊有子考无咎厉终吉：整顿父亲遗留问题。  ',
      2: '干母之蛊不可贞：整顿母亲问题，不宜坚持。  ',
      3: '干父之蛊小有悔无大咎：小有遗憾，没有大灾。  ',
      4: '裕父之蛊往见吝：拖延整顿，有所遗憾。  ',
      5: '干父之蛊用誉：得到赞誉。  ',
      6: '不事王侯高尚其事：不侍奉君主，高洁自守。  ',
    },
  },
  '100111': {
    name: '山天大畜',
    meaning: '蓄养积累',
    description: '高山耸立，积蓄能量，象征厚积薄发。',
    analysis: '事业需要积累经验。感情水到渠成。投资着眼长远。健康重在养生。',
    yaoChanges: {
      1: '有厉利已：有所危险，利于停止。  ',
      2: '舆说輹：车辆脱轴。  ',
      3: '良马逐利艰贞曰闲舆卫利有攸往：好马疾驰，利于行动。  ',
      4: '童牛之牿元吉：小牛角上，大吉。  ',
      5: '豮豕之牙吉：阉猪的牙齿，吉祥。  ',
      6: '何天之衢亨：通达大道，顺利。  ',
    },
  },
  '100000': {
    name: '山地剥',
    meaning: '剥落衰退',
    description: '山上土石剥落，象征事物的衰退和更新。',
    analysis: '事业需要调整策略。感情防备变故。投资保持谨慎。健康注意保养。',
    yaoChanges: {
      1: '剥床以足蔑贞凶：床脚剥落，没有正道。  ',
      2: '剥床以辨蔑贞凶：床板剥落，没有正道。  ',
      3: '剥之无咎：剥落没有灾祸。  ',
      4: '剥床以肤凶：床面剥落，有凶险。  ',
      5: '贯鱼以宫人宠无不利：鱼串成行，得到宠爱。  ',
      6: '硕果不食君子得舆小人剥庐：果实未摘，君子得车。  ',
    },
  },
  '100001': {
    name: '山泽损',
    meaning: '减损取舍',
    description: '山高水下，象征有所损失才有所得。',
    analysis: '事业需要权衡取舍。感情要有付出。投资适当调整。健康注意节制。',
    yaoChanges: {
      1: '已事遄往无咎酌损之：事情完成，迅速前往。  ',
      2: '利贞征凶弗损益之：坚守正道，不宜行动。  ',
      3: '三人行则损一人一人行得其友：三人同行，减一人。  ',
      4: '损其疾使遄有喜无咎：减少疾病，迅速好转。  ',
      5: '或益之十朋之龟弗克违元吉：增加财富，没有阻碍。  ',
      6: '弗损益之无咎贞吉利有攸往得臣无家：不增不减，吉祥。  ',
    },
  },
  '100010': {
    name: '山雷颐',
    meaning: '修养自持',
    description: '雷在山上，象征养晦修身、自我修养。',
    analysis: '事业需要沉淀积累。感情要耐心培养。投资稳健发展。健康注意休养。',
    yaoChanges: {
      1: '舍尔灵龟观我朵颐凶：放弃智慧，看人吃饭。  ',
      2: '拂经于丘颐凶：在山丘上，没有食物。  ',
      3: '拂颐贞凶十年勿用无攸利：不修养生息，十年不利。  ',
      4: '颠颐吉虎视眈眈其欲逐逐无咎：颠倒饮食，吉祥。  ',
      5: '居贞吉不可涉大川：坚守正道，不宜远行。  ',
      6: '由颐厉吉利涉大川：修养自身，利于远行。  ',
    },
  },
  '100011': {
    name: '山风渐',
    meaning: '循序渐进',
    description: '山上有风，徐徐吹动，象征逐步发展。',
    analysis: '事业需稳步前进。感情要慢慢培养。投资宜长期持有。健康重在持续。',
    yaoChanges: {
      1: '鸿渐于干小子厉有言无咎：大雁停驻，小人危险。  ',
      2: '鸿渐于磐饮食衎衎吉：大雁停磐，生活安逸。  ',
      3: '鸿渐于陆夫征不复妇孕不育凶：大雁落地，婚姻问题。  ',
      4: '鸿渐于木或得其桷无咎：大雁停树，获得支持。  ',
      5: '鸿渐于陵妇三岁不孕终莫之胜吉：大雁高飞，婚姻顺利。  ',
      6: '鸿渐于陆其羽可用为仪吉：大雁归来，可作礼仪。  ',
    },
  },

  // 兑卦系列 (57-64)
  '011011': {
    name: '兑为泽',
    meaning: '喜悦愉快',
    description: '泽水清澈，象征喜悦和快乐的心情。',
    analysis: '事业顺利愉快。感情和谐美满。投资收益可喜。健康心情愉悦。',
    yaoChanges: {
      1: '和兑吉：和谐喜悦，吉祥。  ',
      2: '孚兑吉悔亡：诚信喜悦，没有后悔。  ',
      3: '来兑凶：前来喜悦，有凶险。  ',
      4: '商兑未宁介疾有喜：商业喜悦，稍有不安。  ',
      5: '孚于剥有厉：诚信被剥，有危险。  ',
      6: '引兑：引导喜悦。  ',
    },
  },
  '011010': {
    name: '泽水困',
    meaning: '困境阻碍',
    description: '泽中无水，象征处境困难、需要突破。',
    analysis: '事业暂时受阻。感情有所波折。投资需谨慎。健康注意调理。',
    yaoChanges: {
      1: '臀困于株木入于幽谷三岁不见：屁股卡住，三年不见。  ',
      2: '困于酒食朱绂方来利用亨祀征凶无咎：酒肉无忧，适宜祭祀。  ',
      3: '困于石据于蒺藜入其宫不见其妻凶：石头阻碍，没有妻子。  ',
      4: '来徐徐困于金车吝有终：慢慢前行，车辆受阻。  ',
      5: '劓刖困于赤绂乃徐有说利用祭祀：割鼻砍脚，最终解脱。  ',
      6: '困于葛藟于臲卼曰动悔有悔征吉：被困藤蔓，行动后悔。  ',
    },
  },
  '011001': {
    name: '泽山咸',
    meaning: '感应交流',
    description: '泽水浸润山体，象征相互感应和沟通。',
    analysis: '事业贵人相助。感情互相理解。投资讲究默契。健康保持平和。',
    yaoChanges: {
      1: '咸其拇：感应脚趾。  ',
      2: '咸其腓凶居吉：感应小腿，有凶险。  ',
      3: '咸其股执其随往吝：感应大腿，行动受阻。  ',
      4: '贞吉悔亡憧憧往来朋从尔思：坚守正道，朋友相随。  ',
      5: '咸其脢无悔：感应后背，没有后悔。  ',
      6: '咸其辅颊舌：感应脸颊和舌头。  ',
    },
  },
  '011000': {
    name: '泽地萃',
    meaning: '聚集汇聚',
    description: '水聚大地，象征资源集中、人才荟萃。',
    analysis: '事业适合聚集资源。感情可以相聚。投资注重积累。健康调养为主。',
    yaoChanges: {
      1: '有孚不终乃乱乃萃若号一握为笑勿恤往无咎：诚信不足，混乱中相聚。  ',
      2: '引吉无咎孚乃利用禴：引导吉祥，诚信可用。  ',
      3: '萃如嗟如无攸利往无咎小吝：相聚叹息，稍有遗憾。  ',
      4: '大吉无咎：聚集大吉，没有灾祸。  ',
      5: '萃有位无咎匪孚元永贞悔亡：职位稳固，没有后悔。  ',
      6: '赍咨涕洟无咎：悲伤哭泣，没有灾祸。  ',
    },
  },
  '011100': {
    name: '泽天夬',
    meaning: '决断果敢',
    description: '泽在天上，象征水天相接、果断决策。',
    analysis: '事业需要决断。感情要有担当。投资把握时机。健康注意节制。',
    yaoChanges: {
      1: '壮于前趾往不胜为咎：脚趾强壮，前往不利。  ',
      2: '惕号莫夜有戎勿恤：警惕呼喊，夜晚无事。  ',
      3: '壮于頄有凶君子夬夬独行遇雨若濡有愠无咎：额头强壮，有所凶险。  ',
      4: '臀无肤其行次且牵羊悔亡闻言不信：屁股受伤，行走困难。  ',
      5: '苋陆夬夬中行无咎：草木决裂，中间没有灾祸。  ',
      6: '无号终有凶：不呼号，最终凶险。  ',
    },
  },
  '011101': {
    name: '泽风大过',
    meaning: '过度危险',
    description: '泽风交加，力量过大，象征行为过激。',
    analysis: '事业需要节制。感情不可过度。投资要有度。健康防止过劳。',
    yaoChanges: {
      1: '藉用白茅无咎：借助白茅，没有灾祸。  ',
      2: '枯杨生稊老夫得其女妻无不利：枯树发芽，老人娶妻。  ',
      3: '栋桡凶：屋梁弯曲，有凶险。  ',
      4: '栋隆吉有它吝：屋梁高耸，吉祥。  ',
      5: '枯杨生华老妇得其士夫无咎无誉：枯树开花，衰老婚姻。  ',
      6: '过涉灭顶凶无咎：渡河淹没，最终平安。  ',
    },
  },
  '011110': {
    name: '泽雷随',
    meaning: '随顺变化',
    description: '泽中有雷，象征顺应时势、随机应变。',
    analysis: '事业需要灵活应对。感情随和包容。投资适应变化。健康注意调节。',
    yaoChanges: {
      1: '元亨利贞无咎：大吉，没有灾祸。  ',
      2: '屯如邅如乘马班如匪寇婚媾往有庆也：聚集等待，有利婚姻。  ',
      3: '系遁有疾厉畜臣妾吉：束缚退避，小有危险。  ',
      4: '好遁君子吉小人否：主动退避，君子得利。  ',
      5: '嘉遁贞吉：美好退隐，坚守正道。  ',
      6: '肥遁无不利：彻底退避，无所不吉。  ',
    },
  },
  '011111': {
    name: '泽火革',
    meaning: '变革创新',
    description: '泽上生火，象征革命性变化和创新。',
    analysis:
      '事业需要改革。感情要突破常规。投资尝试新领域。健康注意适应变化。',
    yaoChanges: {
      1: '巩用黄牛之革：牢固如牛皮。  ',
      2: '己日乃革之征吉无咎：到己日变革，吉祥。  ',
      3: '征凶贞厉革言三就有孚：行动凶险，需要信任。  ',
      4: '悔亡有孚改命吉：后悔消失，改变命运。  ',
      5: '大人虎变未占有孚：君子变化，没有占卜。  ',
      6: '君子豹变小人革面征凶居贞吉：君子改变，小人表面变化。  ',
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

/**
 * 检查卦象完整性
 */
export function checkHexagramIntegrity() {
  const results = {
    missingHexagrams: [] as string[],
    duplicateHexagrams: [] as string[],
  };

  // 检查缺失卦象
  for (let i = 1; i <= 64; i++) {
    const hexagram = getHexagramByNumber(i);
    const code = hexagram.upperTrigram.binary + hexagram.lowerTrigram.binary;

    if (!hexagramData[code]) {
      results.missingHexagrams.push(code);
    }
  }

  // 检查重复卦象
  const codeCount = new Map<string, number>();
  Object.keys(hexagramData).forEach((code) => {
    codeCount.set(code, (codeCount.get(code) || 0) + 1);
  });

  codeCount.forEach((count, code) => {
    if (count > 1) {
      results.duplicateHexagrams.push(code);
    }
  });

  return results;
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
    const changeDesc = yaoChanges[line] || `第${line}爻发生变化`;
    return `${getLineName(line)}：${changeDesc}`;
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
    `【卦象】${hexagramInfo.name} - ${hexagramInfo.meaning}\n\n` +
    `【核心】${hexagramInfo.description}\n\n` +
    `【五行】${wuxingAnalysis}\n\n` +
    `【建议】${hexagramInfo.analysis}`
  );
}

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
    console.log(
      `总体建议: ${interpretation.overall
        .split('\\n')
        .slice(0, 3)
        .join('\\n')}`,
    );
  } catch (error) {
    console.error('❌ 卦象解读测试失败:', error);
  }
}

function testIntegrityCheck() {
  try {
    const { missingHexagrams, duplicateHexagrams } = checkHexagramIntegrity();

    console.log('✅ 卦象完整性测试通过');
    console.log(`缺失卦象: ${missingHexagrams.length}`);
    console.log(`重复卦象: ${duplicateHexagrams.length}`);
  } catch (error) {
    console.error('❌ 卦象完整性测试失败:', error);
  }
}
