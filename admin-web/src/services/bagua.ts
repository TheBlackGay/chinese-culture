// 定义基础接口
interface BaguaInfo {
  name: string;
  nature: string;
  attribute: string;
  symbol: string;
  binary: string;
  number: number;
}

interface HexagramResult {
  upperTrigram: BaguaInfo;
  lowerTrigram: BaguaInfo;
  changingLines: number[];
  timestamp?: number;
  number?: number;
  selected?: string;
}

interface HexagramInterpretation {
  name: string;
  meaning: string;
  upperTrigram: BaguaInfo & { position: string; meaning: string };
  lowerTrigram: BaguaInfo & { position: string; meaning: string };
  wuxingAnalysis: string;
  yaoAnalysis: string[];
  overall: string;
}

interface HexagramInfo {
  name: string;
  meaning: string;
  description: string;
  analysis: string;
  yaoChanges: {
    [key: number]: string;
  };
}

interface LineInfo {
  name: string;
  general: string;
  changing: string;
}

interface WuxingRelation {
  [key: string]: {
    [key: string]: string;
  };
}

// 八卦基本数据
export const baguaData = {
  qian: { name: '乾', nature: '天', attribute: '金', symbol: '☰', binary: '111', number: 1 },
  kun: { name: '坤', nature: '地', attribute: '土', symbol: '☷', binary: '000', number: 2 },
  zhen: { name: '震', nature: '雷', attribute: '木', symbol: '☳', binary: '001', number: 3 },
  xun: { name: '巽', nature: '风', attribute: '木', symbol: '☴', binary: '110', number: 4 },
  kan: { name: '坎', nature: '水', attribute: '水', symbol: '☵', binary: '010', number: 5 },
  li: { name: '离', nature: '火', attribute: '火', symbol: '☲', binary: '101', number: 6 },
  gen: { name: '艮', nature: '山', attribute: '土', symbol: '☶', binary: '100', number: 7 },
  dui: { name: '兑', nature: '泽', attribute: '金', symbol: '☱', binary: '011', number: 8 }
};

// 爻变解释数据
const lineChangeMeanings: Record<number, LineInfo> = {
  1: {
    name: '初爻',
    general: '代表事情的开始阶段',
    changing: '暗示基础发生变化，需要重新规划。'
  },
  2: {
    name: '二爻',
    general: '代表事情的发展阶段',
    changing: '暗示内部条件改变，需要调整策略。'
  },
  3: {
    name: '三爻',
    general: '代表事情的转折点',
    changing: '暗示关键因素改变，需要特别注意。'
  },
  4: {
    name: '四爻',
    general: '代表事情的外在影响',
    changing: '暗示外部环境变化，需要适应调整。'
  },
  5: {
    name: '五爻',
    general: '代表事情的核心位置',
    changing: '暗示核心要素改变，影响重大。'
  },
  6: {
    name: '上爻',
    general: '代表事情的结果',
    changing: '暗示最终结果可能改变，需要重新评估。'
  }
};

// 五行关系数据
const wuxingRelations: WuxingRelation = {
  '金': {
    '金': '比和',
    '木': '克制',
    '水': '生助',
    '火': '被克',
    '土': '被生'
  },
  '木': {
    '金': '被克',
    '木': '比和',
    '水': '被生',
    '火': '生助',
    '土': '克制'
  },
  '水': {
    '金': '被生',
    '木': '生助',
    '水': '比和',
    '火': '克制',
    '土': '被克'
  },
  '火': {
    '金': '生助',
    '木': '被克',
    '水': '被克',
    '火': '比和',
    '土': '被生'
  },
  '土': {
    '金': '生助',
    '木': '被克',
    '水': '生助',
    '火': '被生',
    '土': '比和'
  }
};

// 五行详细说明数据
const wuxingDetails: WuxingRelation = {
  '金': {
    '金': '双金相合，坚固有力，但需防止过于刚硬',
    '木': '金克木，主动有为，但需防止过于强势',
    '水': '金生水，运势顺遂，贵人相助',
    '火': '火克金，阻力较大，需要调整策略',
    '土': '土生金，基础稳固，发展良好'
  },
  '木': {
    '金': '金克木，受制较多，需要避让',
    '木': '双木相合，生机旺盛，但需防止固执',
    '水': '水生木，成长顺利，有利发展',
    '火': '木生火，能量转化，创造力强',
    '土': '木克土，主动有为，但需防止过于激进'
  },
  '水': {
    '金': '金生水，得到支持，发展顺利',
    '木': '水生木，付出较多，需量力而行',
    '水': '双水相合，智慧充沛，但需防止优柔寡断',
    '火': '水克火，主动有为，但需防止过于压制',
    '土': '土克水，受阻较多，需要调整'
  },
  '火': {
    '金': '火克金，主动有为，但需防止过于激进',
    '木': '木生火，得到助力，发展顺利',
    '水': '水克火，受制较多，需要避让',
    '火': '双火相合，热情高涨，但需防止过于急躁',
    '土': '土生火，基础稳固，能量充沛'
  },
  '土': {
    '金': '土生金，付出较多，需量力而行',
    '木': '木克土，受制较多，需要避让',
    '水': '水克土，阻力较大，需要调整',
    '火': '火生土，得到支持，发展顺利',
    '土': '双土相合，稳重踏实，但需防止保守'
  }
};

// 六十四卦数据
const hexagramData: { [key: string]: HexagramInfo } = {
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
      6: '亢龙有悔：物极必反，需知进退'
    }
  },
  '111110': {
    name: '天风姤',
    meaning: '遇合',
    description: '上天下风，阳气初降，万物相遇交合之象。',
    analysis: '人际关系方面会有新的机遇，但需要把握适度。',
    yaoChanges: {
      1: '系于金柅，贞吉',
      2: '包有鱼，无咎',
      3: '臀无肤，其行次且',
      4: '包无鱼，起凶',
      5: '以杞包瓜，含章，有陨自天',
      6: '姤其角，吝，无咎'
    }
  },
  '111101': {
    name: '天山遁',
    meaning: '遁世离俗',
    description: '天在山上，表示需要适时退避，韬光养晦。',
    analysis: '时运不济时，应当明智地选择退守，等待时机。',
    yaoChanges: {
      1: '遁尾，厉，勿用有攸往',
      2: '执之用黄牛之革，莫之胜说',
      3: '系遁，有疾厉，畜臣妾吉',
      4: '好遁，君子吉，小人否',
      5: '嘉遁，贞吉',
      6: '肥遁，无不利'
    }
  },
  '111011': {
    name: '天火同人',
    meaning: '和同',
    description: '天火同光，上下和同，人际关系和谐。',
    analysis: '人际关系和谐，合作共事有利，但需防止过分亲近。',
    yaoChanges: {
      1: '同人于门，无咎',
      2: '同人于宗，吝',
      3: '伏戎于莽，升其高陵，三岁不兴',
      4: '乘其墉，弗克攻，吉',
      5: '同人先号啕而后笑，大师克相遇',
      6: '同人于郊，无悔'
    }
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
      6: '龙战于野：谨防过度，以免损害'
    }
  },
  // 坎卦系列
  '010010': {
    name: '坎为水',
    meaning: '险难、考验',
    description: '坎为水，象征水的流动特性，表示当前局势有险难，需要谨慎渡过。',
    analysis: '事业上需要谨慎；感情要耐心对待；投资理财防范风险；健康方面注意安全。',
    yaoChanges: {
      1: '习坎，入于坎窞，凶',
      2: '坎有险，求小得',
      3: '来之坎坎，险且枕，入于坎窞，勿用',
      4: '樽酒簋贰，用缶，纳约自牖，终无咎',
      5: '坎不盈，只既平，无咎',
      6: '系用徽纆，寘于丛棘，三岁不得，凶'
    }
  },
  '010111': {
    name: '水天需',
    meaning: '等待、需求',
    description: '水天需，象征水汽上升，等待云雨。提示当前要耐心等待，把握时机。',
    analysis: '事业需要等待时机；感情要保持耐心；投资理财稳中求进；健康方面循序渐进。',
    yaoChanges: {
      1: '需于郊，利用恒，无咎',
      2: '需于沙，小有言，终吉',
      3: '需于泥，致寇至',
      4: '需于血，出自穴',
      5: '需于酒食，贞吉',
      6: '入于穴，有不速之客三人来，敬之终吉'
    }
  },
  '010000': {
    name: '水地比',
    meaning: '比邻、亲近',
    description: '水地比，象征水滋润大地。提示当前适合建立关系，团结合作。',
    analysis: '事业宜寻求合作；感情可以增进；投资理财重在互信；健康方面注意调养。',
    yaoChanges: {
      1: '有孚比之，无咎，有孚盈缶，终来有他，吉',
      2: '比之自内，贞吉',
      3: '比之匪人',
      4: '外比之，贞吉',
      5: '显比，王用三驱，失前禽，邑人不诫，吉',
      6: '比之无首，凶'
    }
  },
  '010001': {
    name: '水山蹇',
    meaning: '艰难、困阻',
    description: '水山蹇，水流被山阻，象征行动受阻。提示当前困难重重，需要谨慎前行。',
    analysis: '事业暂时受阻；感情有所阻碍；投资需要谨慎；健康多加注意。',
    yaoChanges: {
      1: '往蹇，来誉',
      2: '王臣蹇蹇，匪躬之故',
      3: '往蹇，来反',
      4: '往蹇，来连',
      5: '大蹇，朋来',
      6: '往蹇，来硕，吉；利见大人'
    }
  },
  '010011': {
    name: '水泽节',
    meaning: '节制、节约',
    description: '水泽节，象征水量适中，不溢不涸。提示当前要适度节制，把握分寸。',
    analysis: '事业要有节制；感情需要节度；投资理财量力而行；健康方面注意规律。',
    yaoChanges: {
      1: '不出户庭，无咎',
      2: '不出门庭，凶',
      3: '不节若，则嗟若，无咎',
      4: '安节，亨',
      5: '甘节，吉，往有尚',
      6: '苦节，贞凶，悔亡'
    }
  },
  // 离卦系列
  '101101': {
    name: '离为火',
    meaning: '光明、智慧',
    description: '离为火，象征光明与智慧，表示当前形势明朗，但需要注意不可过分追求表面光华。',
    analysis: '事业上光明在望；感情要以诚相待；投资理财需明智；健康方面注意用眼。',
    yaoChanges: {
      1: '履错然，敬之无咎',
      2: '黄离，元吉',
      3: '日昃之离，不鼓缶而歌，则大耋之嗟，凶',
      4: '突如其来如，焚如，死如，弃如',
      5: '出涕沱若，戚嗟若，吉',
      6: '王用出征，有嘉折首，获匪其丑，无咎'
    }
  },
  '101111': {
    name: '火天大有',
    meaning: '丰盛、通达',
    description: '火在天上，光明普照，象征丰盛和通达，但也提醒要懂得节制与分享。',
    analysis: '事业发展顺遂；感情和谐美满；投资理财收益可观；健康状况良好。',
    yaoChanges: {
      1: '无交害，匪咎，艰则无咎',
      2: '大车以载，有攸往，无咎',
      3: '公用亨于天子，小人弗克',
      4: '匪其彭，无咎',
      5: '厥孚交如，威如，吉',
      6: '自天祐之，吉无不利'
    }
  },
  '101000': {
    name: '火地晋',
    meaning: '进步、晋升',
    description: '火地晋，火光上升于地，象征光明前进。提示当前形势向上，有利于进取。',
    analysis: '事业上升发展；感情逐步深入；投资收益渐增；健康状况改善。',
    yaoChanges: {
      1: '晋如，摧如，贞吉。罔孚，裕，无咎',
      2: '晋如，愁如，贞吉。受兹介福，于其王母',
      3: '众允，悔亡',
      4: '晋如硕鼠，贞厉',
      5: '悔亡，失得勿恤，往吉，无不利',
      6: '晋其角，维用伐邑，厉吉无咎，贞吝'
    }
  },
  '101001': {
    name: '火山旅',
    meaning: '旅行、动荡',
    description: '火在山上，照耀四方，象征旅行和变动，提醒在变化中要保持谨慎。',
    analysis: '事业多有变动；感情需要包容；投资理财宜谨慎；健康注意调养。',
    yaoChanges: {
      1: '旅琐琐，斯其所取灾',
      2: '旅即次，怀其资，得童仆贞',
      3: '旅焚其次，丧其童仆，贞厉',
      4: '旅于处，得其资斧，我心不快',
      5: '射雉一矢亡，终以誉命',
      6: '鸟焚其巢，旅人先笑后号啕，丧牛于易，凶'
    }
  },
  '101010': {
    name: '火水未济',
    meaning: '未完成、待发展',
    description: '火水相克，象征事物尚未完成，提醒当前局势尚未成熟，需要继续努力。',
    analysis: '事业尚需努力；感情有待发展；投资理财需谨慎；健康注意调理。',
    yaoChanges: {
      1: '濡其尾，吝',
      2: '曳其轮，贞吉',
      3: '未济，征凶，利涉大川',
      4: '贞吉，悔亡，震用伐鬼方，三年有赏于大国',
      5: '贞吉，无悔，君子之光，有孚，吉',
      6: '有孚于饮酒，无咎，濡其首，有孚失是'
    }
  },
  // 巽卦系列
  '110110': {
    name: '巽为风',
    meaning: '谦逊、顺从',
    description: '巽为风，象征风的柔顺特性。提示当前要以谦逊的态度处事，但不可过分屈从。',
    analysis: '事业宜低调行事；感情以柔克刚；投资保持谨慎；健康调养为主。',
    yaoChanges: {
      1: '进退，利武人之贞',
      2: '巽在床下，用史巫纷若，吉，无咎',
      3: '频巽，吝',
      4: '悔亡，田获三品',
      5: '贞吉，悔亡，无不利，无初有终。先庚三日，后庚三日，吉',
      6: '巽在床下，丧其资斧，贞凶'
    }
  },
  '110111': {
    name: '风天小畜',
    meaning: '蓄养、积累',
    description: '风天小畜，象征风行天上，涵养待时。提示当前要注重积累，不可急于求成。',
    analysis: '事业需要积累；感情循序渐进；投资稳中求进；健康重在调养。',
    yaoChanges: {
      1: '复自道，何其咎，吉',
      2: '牵复，吉',
      3: '舆说辐，夫妻反目',
      4: '有孚，血去惕出，无咎',
      5: '有孚挛如，富以其邻',
      6: '既雨既处，尚德载，妇贞厉。月几望，君子征凶'
    }
  },
  '110000': {
    name: '风地观',
    meaning: '观察、体察',
    description: '风地观，象征风行地上，观察万物。提示当前要多加观察，把握时机而动。',
    analysis: '事业需要观察；感情重在了解；投资审时度势；健康注意调理。',
    yaoChanges: {
      1: '童观，小人无咎，君子吝',
      2: '窥观，利女贞',
      3: '观我生，进退',
      4: '观国之光，利用宾于王',
      5: '观我生，君子无咎',
      6: '观其生，君子无咎'
    }
  },
  '110001': {
    name: '风山渐',
    meaning: '渐进、循序',
    description: '风山渐，风在山上徐徐吹动，象征循序渐进。提示当前要稳步前进，不可急躁。',
    analysis: '事业需循序渐进；感情要慢慢培养；投资宜稳健为主；健康重在持续。',
    yaoChanges: {
      1: '鸿渐于干，小子厉，有言，无咎',
      2: '鸿渐于磐，饮食衎衎，吉',
      3: '鸿渐于陆，夫征不复，妇孕不育，凶；利御寇',
      4: '鸿渐于木，或得其桷，无咎',
      5: '鸿渐于陵，妇三岁不孕，终莫之胜，吉',
      6: '鸿渐于陆，其羽可用为仪，吉'
    }
  },
  '110010': {
    name: '风水涣',
    meaning: '散布、分散',
    description: '风水涣，象征风吹水面，四散流动。提示当前要适度分散，不可过于集中。',
    analysis: '事业宜多方位；感情需要空间；投资分散风险；健康注意调节。',
    yaoChanges: {
      1: '用拯马壮，吉',
      2: '涣奔其机，悔亡',
      3: '涣其躬，无悔',
      4: '涣其群，元吉。涣有丘，匪夷所思',
      5: '涣汗其大号，涣王居，无咎',
      6: '涣其血，去逖出，无咎'
    }
  },
  // 艮卦系列
  '100100': {
    name: '山山艮',
    meaning: '止、静止',
    description: '山山艮，双山相叠，象征停止。提示当前要适时止步，静观其变。',
    analysis: '事业需要停顿思考；感情要保持克制；投资理财宜守不宜进；健康注意休养。',
    yaoChanges: {
      1: '艮其趾，无咎，利永贞',
      2: '艮其腓，不拯其随，其心不快',
      3: '艮其限，列其夤，厉薰心',
      4: '艮其身，无咎',
      5: '艮其辅，言有序，悔亡',
      6: '敦艮，吉'
    }
  },
  '100111': {
    name: '山天大畜',
    meaning: '蓄养、积累',
    description: '山天大畜，象征高山耸立，积蓄能量。提示当前要积累实力，为未来发展做准备。',
    analysis: '事业需要积累；感情水到渠成；投资着眼长远；健康重在养生。',
    yaoChanges: {
      1: '有厉，利已',
      2: '舆说輹',
      3: '良马逐，利艰贞。曰闲舆卫，利有攸往',
      4: '童牛之牿，元吉',
      5: '豮豕之牙，吉',
      6: '何天之衢，亨'
    }
  },
  '100000': {
    name: '山地剥',
    meaning: '剥落、衰退',
    description: '山地剥，象征山上土石剥落。提示当前可能面临衰退，但衰退中孕育新机。',
    analysis: '事业需要调整；感情当防变故；投资保持谨慎；健康注意保养。',
    yaoChanges: {
      1: '剥床以足，蔑贞凶',
      2: '剥床以辨，蔑贞凶',
      3: '剥之，无咎',
      4: '剥床以肤，凶',
      5: '贯鱼，以宫人宠，无不利',
      6: '硕果不食，君子得舆，小人剥庐'
    }
  },
  '100001': {
    name: '山风蛊',
    meaning: '振革、整顿',
    description: '山风蛊，象征山上大风，需要整顿。提示当前要振革图新，但要把握分寸。',
    analysis: '事业需要改革；感情应当改进；投资注意创新；健康及时调理。',
    yaoChanges: {
      1: '干父之蛊，有子，考无咎，厉终吉',
      2: '干母之蛊，不可贞',
      3: '干父之蛊，小有悔，无大咎',
      4: '裕父之蛊，往见吝',
      5: '干父之蛊，用誉',
      6: '不事王侯，高尚其事'
    }
  },
  '100010': {
    name: '山水蒙',
    meaning: '蒙昧、启蒙',
    description: '山水蒙，象征山下泉水，蒙昧待启。提示当前需要学习和启发，不可妄自尊大。',
    analysis: '事业需要学习；感情要多沟通；投资需要指导；健康注意保健。',
    yaoChanges: {
      1: '发蒙，利用刑人，用说桎梏，以往吝',
      2: '包蒙吉，纳妇吉，子克家',
      3: '勿用取女，见金夫，不有躬，无攸利',
      4: '困蒙，吝',
      5: '童蒙，吉',
      6: '击蒙，不利为寇，利御寇'
    }
  },
  // 兑卦系列
  '011011': {
    name: '兑为泽',
    meaning: '喜悦、愉快',
    description: '兑为泽，象征喜悦和愉快。提示当前运势喜人，但要防止过分乐观。',
    analysis: '事业顺遂欢欣；感情和谐美满；投资收益可喜；健康心情愉悦。',
    yaoChanges: {
      1: '和兑，吉',
      2: '孚兑，吉，悔亡',
      3: '来兑，凶',
      4: '商兑，未宁，介疾有喜',
      5: '孚于剥，有厉',
      6: '引兑'
    }
  },
  '011111': {
    name: '泽天夬',
    meaning: '决断、果决',
    description: '泽天夬，象征水泽决堤而出。提示当前要果断决策，但不可过于激进。',
    analysis: '事业需要决断；感情要有担当；投资把握时机；健康注意节制。',
    yaoChanges: {
      1: '壮于前趾，往不胜为咎',
      2: '惕号，莫夜有戎，勿恤',
      3: '壮于頄，有凶。君子夬夬，独行遇雨，若濡有愠，无咎',
      4: '臀无肤，其行次且。牵羊悔亡，闻言不信',
      5: '苋陆夬夬，中行无咎',
      6: '无号，终有凶'
    }
  },
  '011000': {
    name: '泽地萃',
    meaning: '聚集、汇聚',
    description: '泽地萃，象征水汇聚大地。提示当前适合聚集资源，团结合作。',
    analysis: '事业宜广结善缘；感情可以相聚；投资注重积累；健康调养为主。',
    yaoChanges: {
      1: '有孚不终，乃乱乃萃，若号，一握为笑，勿恤，往无咎',
      2: '引吉，无咎，孚乃利用禴',
      3: '萃如，嗟如，无攸利，往无咎，小吝',
      4: '大吉，无咎',
      5: '萃有位，无咎。匪孚，元永贞，悔亡',
      6: '赍咨涕洟，无咎'
    }
  },
  '011001': {
    name: '泽山咸',
    meaning: '感应、交流',
    description: '泽山咸，象征泽水浸润山体。提示当前人际关系和谐，适合交流沟通。',
    analysis: '事业贵人相助；感情互相理解；投资讲究默契；健康保持平和。',
    yaoChanges: {
      1: '咸其拇',
      2: '咸其腓，凶，居吉',
      3: '咸其股，执其随，往吝',
      4: '贞吉，悔亡，憧憧往来，朋从尔思',
      5: '咸其脢，无悔',
      6: '咸其辅颊舌'
    }
  },
  '011010': {
    name: '泽水困',
    meaning: '困境、阻碍',
    description: '泽水困，象征泽水干涸。提示当前处境困难，需要耐心等待时机。',
    analysis: '事业暂时受阻；感情有所波折；投资需要谨慎；健康注意调养。',
    yaoChanges: {
      1: '臀困于株木，入于幽谷，三岁不见',
      2: '困于酒食，朱绂方来，利用亨祀，征凶，无咎',
      3: '困于石，据于蒺藜，入于其宫，不见其妻，凶',
      4: '来徐徐，困于金车，吝，有终',
      5: '劓刖，困于赤绂，乃徐有说，利用祭祀',
      6: '困于葛藟，于臲卼，曰动悔。有悔，征吉'
    }
  },
  // 乾卦系列（补充）
  '111000': {
    name: '天地否',
    meaning: '闭塞、不通',
    description: '天地否，象征天地不交，阴阳隔绝。提示当前局势闭塞，需要耐心等待时机。',
    analysis: '事业上暂时受阻；感情上沟通不畅；投资理财需谨慎；健康方面注意调养。',
    yaoChanges: {
      1: '拔茅茹，以其汇，贞吉，亨',
      2: '包承，小人吉，大人否，亨',
      3: '包羞',
      4: '有命，无咎，畴离祉',
      5: '休否，大人吉，其亡其亡，系于苞桑',
      6: '倾否，先否后喜'
    }
  },
  '111001': {
    name: '天雷无妄',
    meaning: '自然、无妄',
    description: '天雷无妄，象征天降雷声，自然运行。提示当前形势自然发展，不可妄为。',
    analysis: '事业顺其自然；感情水到渠成；投资循天时；健康保持规律。',
    yaoChanges: {
      1: '无妄往，吉',
      2: '不耕获，不菑畲，则利有攸往',
      3: '无妄之灾，或系之牛，行人之得，邑人之灾',
      4: '可贞，无咎',
      5: '无妄之疾，勿药有喜',
      6: '无妄，行有眚，无攸利'
    }
  },
  '101011': {
    name: '天泽履',
    meaning: '履行、践行',
    description: '天泽履，象征脚踏实地，稳步前行。提示当前要脚踏实地，循序渐进。',
    analysis: '事业上稳扎稳打；感情要循序渐进；投资理财重在稳健；健康方面保持规律。',
    yaoChanges: {
      1: '素履，往无咎',
      2: '履道坦坦，幽人贞吉',
      3: '眇能视，跛能履，履虎尾，咥人，凶，武人为于大君',
      4: '履虎尾，愬愬，终吉',
      5: '夬履，贞厉',
      6: '视履考祥，其旋元吉'
    }
  },
  // 坤卦系列（补充）
  '000111': {
    name: '地天泰',
    meaning: '通泰、和顺',
    description: '地天泰，象征天地交泰，阴阳调和。提示当前形势大好，但需谨防乐极生悲。',
    analysis: '事业发展顺遂；感情和睦融洽；投资收益可观；健康状况良好。',
    yaoChanges: {
      1: '拔茅茹，以其汇，征吉',
      2: '包荒，用冯河，不遐遗，朋亡，得尚于中行',
      3: '无平不陂，无往不复，艰贞无咎，勿恤其孚，于食有福',
      4: '翩翩，不富以其邻，不戒以孚',
      5: '帝乙归妹，以祉元吉',
      6: '城复于隍，勿用师，自邑告命，贞吝'
    }
  },
  '000100': {
    name: '地雷复',
    meaning: '复归',
    description: '雷在地中，春雷发动，万物复苏，表示重新开始。',
    analysis: '事物开始复苏，充满希望，但需要循序渐进。',
    yaoChanges: {
      1: '不远复，无祗悔，元吉',
      2: '休复，吉',
      3: '频复，厉无咎',
      4: '中行独复',
      5: '敦复，无悔',
      6: '迷复，凶，有灾眚'
    }
  },
  '000001': {
    name: '地山谦',
    meaning: '谦逊、谦虚',
    description: '地山谦，象征山在地下，不露锋芒。提示当前要保持谦虚谨慎的态度。',
    analysis: '事业宜低调行事；感情要谦和有礼；投资理财要谨慎；健康方面保持平和。',
    yaoChanges: {
      1: '谦谦君子，用涉大川，吉',
      2: '鸣谦，贞吉',
      3: '劳谦，君子有终，吉',
      4: '无不利，撝谦',
      5: '不富以其邻，利用侵伐，无不利',
      6: '鸣谦，利用行师，征邑国'
    }
  },
  '000010': {
    name: '地水师',
    meaning: '统率、组织',
    description: '地水师，象征地上行水，组织严密。提示当前要注重团队协作，统筹安排。',
    analysis: '事业需要统筹规划；感情要注重协调；投资要系统布局；健康要全面调理。',
    yaoChanges: {
      1: '师出以律，否臧凶',
      2: '在师中吉，无咎，王三锡命',
      3: '师或舆尸，凶',
      4: '师左次，无咎',
      5: '田有禽，利执言，无咎，长子帅师，弟子舆尸，贞凶',
      6: '大君有命，开国承家，小人勿用'
    }
  },
  '000101': {
    name: '地火明夷',
    meaning: '晦暗、隐伏',
    description: '火光入地，象征光明受损。提示当前处境不利，需要韬光养晦，等待时机。',
    analysis: '事业宜避锋芒；感情需要耐心；投资保持谨慎；健康注意休养。',
    yaoChanges: {
      1: '明夷于飞，垂其翼，君子于行，三日不食，有攸往，主人有言',
      2: '明夷，夷于左股，用拯马壮，吉',
      3: '明夷于南狩，得其大首，不可疾贞',
      4: '入于左腹，获明夷之心，出于门庭',
      5: '箕子之明夷，利贞',
      6: '不明晦，初登于天，后入于地'
    }
  },
  // 震卦系列
  '001001': {
    name: '震为雷',
    meaning: '震动、行动',
    description: '震为雷，象征春雷激荡，万物复苏。提示当前是行动的时机，但需要把握分寸。',
    analysis: '事业宜大胆进取；感情有新的突破；投资理财可以行动；健康注意不要过度。',
    yaoChanges: {
      1: '震来虩虩，后笑言哑哑，吉',
      2: '震来厉，亿丧贝，跻于九陵，勿逐，七日得',
      3: '震苏苏，震行无眚',
      4: '震遂泥',
      5: '震往来厉，亿无丧，有事',
      6: '震索索，视矍矍，征凶。震不于其躬，于其邻，无咎。婚媾有言'
    }
  },
  '001111': {
    name: '雷天大壮',
    meaning: '壮大、强盛',
    description: '雷天大壮，象征春雷响彻天际。提示当前气势正盛，但要防止物极必反。',
    analysis: '事业蒸蒸日上；感情热情似火；投资收益可观；健康充满活力。',
    yaoChanges: {
      1: '壮于趾，征凶，有孚',
      2: '贞吉',
      3: '小人用壮，君子用罔，贞厉。羝羊触藩，羸其角',
      4: '贞吉悔亡，藩决不羸，壮于大舆之輹',
      5: '丧羊于易，无悔',
      6: '羝羊触藩，不能退，不能遂，无攸利，艰则吉'
    }
  },
  '001000': {
    name: '雷地豫',
    meaning: '喜悦、和顺',
    description: '雷地豫，象征春雷响动，大地欢欣。提示当前形势大好，但要戒骄戒躁。',
    analysis: '事业平稳发展；感情和睦融洽；投资稳中有喜；健康心情愉悦。',
    yaoChanges: {
      1: '鸣豫，凶',
      2: '介于石，不终日，贞吉',
      3: '盱豫，悔。迟有悔',
      4: '由豫，大有得。勿疑。朋盍簪',
      5: '贞疾，恒不死',
      6: '冥豫，成有渝，无咎'
    }
  },
  '001010': {
    name: '雷水解',
    meaning: '解除、化解',
    description: '雷水解，象征雷雨降临，解除干旱。提示当前困难即将消除，转机已到。',
    analysis: '事业即将突破；感情可以破冰；投资回报可期；健康转危为安。',
    yaoChanges: {
      1: '无咎',
      2: '田获三狐，得黄矢，贞吉',
      3: '负且乘，致寇至，贞吝',
      4: '解而拇，朋至斯孚',
      5: '君子维有解，吉；有孚于小人',
      6: '公用射隼，于高墉之上，获之，无不利'
    }
  },
  '001011': {
    name: '雷泽归妹',
    meaning: '归依、依附',
    description: '雷泽归妹，象征少女出嫁，依附于夫家。提示当前需要寻求依托，但不可失去独立性。',
    analysis: '事业需要合作；感情宜早定下；投资寻求依托；健康注意调养。',
    yaoChanges: {
      1: '归妹以娣，跛能履，征吉',
      2: '眇能视，利幽人之贞',
      3: '归妹以须，反归以娣',
      4: '归妹愆期，迟归有时',
      5: '帝乙归妹，其君之袂，不如其娣之袂良，月几望，吉',
      6: '女承筐无实，士刲羊无血，无攸利'
    }
  },
  '000110': {
    name: '地风升',
    meaning: '上升、升腾',
    description: '地风升，风从地下升起，象征上升。提示当前形势向上，有利于发展。',
    analysis: '事业上升发展；感情逐步升温；投资收益上涨；健康状况改善。',
    yaoChanges: {
      1: '允升，大吉',
      2: '孚乃利用禴，无咎',
      3: '升虚邑',
      4: '王用亨于岐山，吉无咎',
      5: '贞吉，升阶',
      6: '冥升，利于不息之贞'
    }
  },
  // 添加缺失的卦象
  '110011': {
    name: '风泽中孚',
    meaning: '诚信、信任',
    description: '风行泽上，万物生长，表示以诚信感化他人。提示当前要以诚待人，守信践诺。',
    analysis: '事业以诚为本；感情需要信任；投资讲究诚信；健康保持乐观。',
    yaoChanges: {
      1: '虞吉，有它不燕',
      2: '鸣鹤在阴，其子和之，我有好爵，吾与尔靡之',
      3: '得敌，或鼓或罢，或泣或歌',
      4: '月几望，马匹亡，无咎',
      5: '有孚挛如，无咎',
      6: '翰音登于天，贞凶'
    }
  },
  '011110': {
    name: '泽风大过',
    meaning: '过度、太过',
    description: '泽水浸风，力量过大。提示当前形势有过度之象，需要适可而止。',
    analysis: '事业需要节制；感情不可过度；投资要有节度；健康防止过劳。',
    yaoChanges: {
      1: '藉用白茅，无咎',
      2: '枯杨生稊，老夫得其女妻，无不利',
      3: '栋桡，凶',
      4: '栋隆，吉；有它吝',
      5: '枯杨生华，老妇得其士夫，无咎无誉',
      6: '过涉灭顶，凶，无咎'
    }
  },
  '101100': {
    name: '火雷噬嗑',
    meaning: '决断、明断',
    description: '雷电交加，象征果断决策。提示当前要当机立断，把握时机。',
    analysis: '事业需要魄力；感情应当果断；投资把握机会；健康注意节制。',
    yaoChanges: {
      1: '屦校灭趾，无咎',
      2: '噬肤灭鼻，无咎',
      3: '噬腊肉，遇毒；小吝，无咎',
      4: '噬乾胏，得金矢，利艰贞，吉',
      5: '噬乾肉，得黄金，贞厉，无咎',
      6: '何校灭耳，凶'
    }
  },
  '100011': {
    name: '山泽损',
    meaning: '损益、取舍',
    description: '山泽损，山高水下，表示有所损失才有所得。提示当前要有所取舍。',
    analysis: '事业需要权衡；感情要有付出；投资要有准备；健康注意节制。',
    yaoChanges: {
      1: '已事遄往，无咎，酌损之',
      2: '利贞，征凶，弗损益之',
      3: '三人行，则损一人；一人行，则得其友',
      4: '损其疾，使遄有喜，无咎',
      5: '或益之，十朋之龟弗克违，元吉',
      6: '弗损益之，无咎，贞吉，利有攸往，得臣无家'
    }
  },
  // 添加更多缺失的卦象
  '010101': {
    name: '水火既济',
    meaning: '完成、成就',
    description: '水火既济，阴阳调和。提示当前事物已经达到圆满阶段，但要防止物极必反。',
    analysis: '事业已见成效；感情趋于圆满；投资收获在望；健康状况良好。',
    yaoChanges: {
      1: '曳其轮，濡其尾，无咎',
      2: '妇丧其茀，勿逐，七日得',
      3: '高宗伐鬼方，三年克之，小人勿用',
      4: '繻有衣袽，终日戒',
      5: '东邻杀牛，不如西邻之禴祭，实受其福',
      6: '濡其首，厉'
    }
  },
  '001100': {
    name: '雷火丰',
    meaning: '丰盛、充实',
    description: '雷电交加，光明充盈。提示当前形势大好，但要防止骄傲自满。',
    analysis: '事业蒸蒸日上；感情美满和谐；投资收益可观；健康充满活力。',
    yaoChanges: {
      1: '遇其配主，虽旬无咎，往有尚',
      2: '丰其蔀，日中见斗，往得疑疾，有孚发若，吉',
      3: '丰其沛，日中见沫，折其右肱，无咎',
      4: '丰其蔀，日中见斗，遇其夷主，吉',
      5: '来章，有庆誉，吉',
      6: '丰其屋，蔀其家，窥其户，阒其无人，三岁不觌，凶'
    }
  },
  '111010': {
    name: '天水讼',
    meaning: '争讼、诉讼',
    description: '天在上水在下，阳刚之气下降，表示争讼、冲突。提示当前易生争执，需要明智处理。',
    analysis: '事业有争议；感情需沟通；投资需谨慎；健康防纷扰。',
    yaoChanges: {
      1: '不永所事，小有言，终吉',
      2: '不克讼，归而逋，其邑人三百户，无眚',
      3: '食旧德，贞厉，终吉',
      4: '不克讼，复即命，渝安贞，吉',
      5: '讼，元吉',
      6: '或锡之鞶带，终朝三褫之'
    }
  },
  '111100': {
    name: '天雷无妄',
    meaning: '无妄、自然',
    description: '天上打雷，自然现象，表示无妄之灾或无妄之福。提示当前要顺其自然，不可妄为。',
    analysis: '事业顺其自然；感情水到渠成；投资循天时；健康保持规律。',
    yaoChanges: {
      1: '无妄，往吉',
      2: '不耕获，不菑畲，则利有攸往',
      3: '无妄之灾，或系之牛，行人之得，邑人之灾',
      4: '可贞，无咎',
      5: '无妄之疾，勿药有喜',
      6: '无妄，行有眚，无攸利'
    }
  },
  '001110': {
    name: '雷风恒',
    meaning: '恒久、持续',
    description: '雷电风行，阴阳交合，象征持久不变。提示当前要持之以恒，保持恒心。',
    analysis: '事业需要坚持；感情要保持专一；投资要有恒心；健康持续调养。',
    yaoChanges: {
      1: '浚恒，贞凶，无攸利',
      2: '悔亡',
      3: '不恒其德，或承之羞，贞吝',
      4: '田无禽',
      5: '恒其德，贞，妇人吉，夫子凶',
      6: '振恒，凶'
    }
  },
  '011101': {
    name: '泽火革',
    meaning: '改革、变革',
    description: '泽火革，火烤干泽水，象征变革。提示当前需要改革创新，但要循序渐进。',
    analysis: '事业需要创新改革；感情要有新突破；投资理财需调整；健康注意调养。',
    yaoChanges: {
      1: '巩用黄牛之革',
      2: '己日乃革之，征吉，无咎',
      3: '征凶，贞厉。革言三就，有孚',
      4: '悔亡，有孚改命，吉',
      5: '大人虎变，未占有孚',
      6: '君子豹变，小人革面，征凶，居贞吉'
    }
  },
  '110101': {
    name: '风火家人',
    meaning: '家庭、和睦',
    description: '风吹火旺，象征家庭和睦。提示当前要注重家庭关系，保持内外和谐。',
    analysis: '事业需要团队合作；感情注重家庭和谐；投资理财稳健发展；健康关注家人。',
    yaoChanges: {
      1: '闲有家，悔亡',
      2: '无攸遂，在中馈，贞吉',
      3: '家人嗃嗃，悔厉吉',
      4: '富家，大吉',
      5: '王假有家，勿恤吉',
      6: '有孚威如，终吉'
    }
  },
  '000011': {
    name: '地泽临',
    meaning: '临近、监督',
    description: '地泽临，泽在地上，水往下渗，象征居高临下。提示当前要以谦逊的态度监察和管理。',
    analysis: '事业上要以身作则；感情要以诚相待；投资需要严格监管；健康注意自我监督。',
    yaoChanges: {
      1: '咸临，贞吉',
      2: '咸临，吉无不利',
      3: '甘临，无攸利。既忧之，无咎',
      4: '至临，无咎',
      5: '知临，大君之宜，吉',
      6: '敦临，吉无咎'
    }
  },
  '110100': {
    name: '风山渐',
    meaning: '渐进、循序',
    description: '风山渐，风在山上徐徐吹动，象征循序渐进。提示当前要稳步前进，不可急躁。',
    analysis: '事业需循序渐进；感情要慢慢培养；投资宜稳健为主；健康重在持续。',
    yaoChanges: {
      1: '鸿渐于干，小子厉，有言，无咎',
      2: '鸿渐于磐，饮食衎衎，吉',
      3: '鸿渐于陆，夫征不复，妇孕不育，凶；利御寇',
      4: '鸿渐于木，或得其桷，无咎',
      5: '鸿渐于陵，妇三岁不孕，终莫之胜，吉',
      6: '鸿渐于陆，其羽可用为仪，吉'
    }
  },
  '101110': {
    name: '火风鼎',
    meaning: '革新、变化',
    description: '火风鼎，火借风势，象征革新进取。提示当前适合创新改革，但要把握分寸。',
    analysis: '事业宜创新突破；感情需要新意；投资可以创新；健康注意调养。',
    yaoChanges: {
      1: '鼎颠趾，利出否，得妾以其子，无咎',
      2: '鼎有实，我仇有疾，不我能即，吉',
      3: '鼎耳革，其行塞，雉膏不食，方雨亏悔，终吉',
      4: '鼎折足，覆公餗，其形渥，凶',
      5: '鼎黄耳金铉，利贞',
      6: '鼎玉铉，大吉，无不利'
    }
  },
  '100110': {
    name: '山风蛊',
    meaning: '蛊惑、整顿',
    description: '山风蛊，山下起风，象征腐败需要整顿。提示当前要整顿革新，匡正弊端。',
    analysis: '事业需要整顿；感情要澄清误会；投资需要调整；健康及时调理。',
    yaoChanges: {
      1: '干父之蛊，有子，考无咎，厉终吉',
      2: '干母之蛊，不可贞',
      3: '干父之蛊，小有悔，无大咎',
      4: '裕父之蛊，往见吝',
      5: '干父之蛊，用誉',
      6: '不事王侯，高尚其事'
    }
  },
  '100101': {
    name: '山火贲',
    meaning: '文明、装饰',
    description: '山火贲，火光映山，象征文明装饰。提示当前要注重外在表现，但不可过分。',
    analysis: '事业讲究形象；感情重视仪表；投资注意包装；健康关注外表。',
    yaoChanges: {
      1: '贲其趾，舍车而徒',
      2: '贲其须',
      3: '贲如，濡如，永贞吉',
      4: '贲如，皤如，白马翰如，匪寇婚媾',
      5: '贲于丘园，束帛戋戋，吝，终吉',
      6: '白贲，无咎'
    }
  },
  '010100': {
    name: '水山蹇',
    meaning: '艰难、困阻',
    description: '水山蹇，水流被山阻，象征行动受阻。提示当前困难重重，需要谨慎前行。',
    analysis: '事业暂时受阻；感情有所阻碍；投资需要谨慎；健康多加注意。',
    yaoChanges: {
      1: '往蹇，来誉',
      2: '王臣蹇蹇，匪躬之故',
      3: '往蹇，来反',
      4: '往蹇，来连',
      5: '大蹇，朋来',
      6: '往蹇，来硕，吉；利见大人'
    }
  }
};

// 根据时间起卦
export function getHexagramByTime(time?: string | Date): HexagramResult {
  let date: Date;
  
  if (typeof time === 'string') {
    // 尝试解析输入的时间字符串
    const parsedDate = new Date(time);
    if (isNaN(parsedDate.getTime())) {
      throw new Error('无效的时间格式，请使用 YYYY-MM-DD HH:mm:ss 格式');
    }
    date = parsedDate;
  } else {
    date = time || new Date();
  }
  
  const hour = date.getHours();
  const minute = date.getMinutes();
  const second = date.getSeconds();
  
  // 使用时间各个部分生成卦象
  const upperTrigram = generateTrigramByTime(hour);
  const lowerTrigram = generateTrigramByTime(minute);
  
  return {
    upperTrigram,
    lowerTrigram,
    changingLines: generateChangingLines(second),
    timestamp: date.getTime()
  };
}

// 根据数字起卦（1-50）
export function getHexagramByNumber(number: number): HexagramResult {
  if (number < 1 || number > 50) {
    throw new Error('数字必须在1-50之间');
  }
  
  // 使用数字的不同部分生成上下卦
  const upperNumber = Math.ceil(number / 7) % 8 || 8; // 使用除以7的结果
  const lowerNumber = number % 8 || 8; // 使用除以8的余数
  
  const upperTrigram = generateTrigramByNumber(upperNumber);
  const lowerTrigram = generateTrigramByNumber(lowerNumber);
  
  // 生成变爻
  const changingLines = generateChangingLinesByNumber(number);
  
  return {
    upperTrigram,
    lowerTrigram,
    changingLines,
    number
  };
}

// 直接选择卦象
export function getHexagramBySelection(upperBaguaName: string, lowerBaguaName: string): HexagramResult {
  const upperBagua = Object.values(baguaData).find(bagua => bagua.name === upperBaguaName);
  const lowerBagua = Object.values(baguaData).find(bagua => bagua.name === lowerBaguaName);
  
  if (!upperBagua || !lowerBagua) {
    throw new Error('无效的卦象名称');
  }
  
  return {
    upperTrigram: upperBagua,
    lowerTrigram: lowerBagua,
    changingLines: [],
    selected: `${upperBaguaName}${lowerBaguaName}`
  };
}

// 生成卦象解读
export function interpretHexagram(hexagram: HexagramResult): HexagramInterpretation {
  const { upperTrigram, lowerTrigram, changingLines } = hexagram;
  
  const hexagramCode = upperTrigram.binary + lowerTrigram.binary;
  const hexagramInfo = hexagramData[hexagramCode] || {
    name: '未知卦象',
    meaning: '暂无解释',
    description: '暂无描述',
    analysis: '请谨慎行事',
    yaoChanges: {}
  };
  
  const wuxingAnalysis = analyzeWuxing(upperTrigram.attribute, lowerTrigram.attribute);
  const yaoAnalysis = analyzeChangingLines(changingLines, hexagramInfo.yaoChanges);
  
  return {
    name: hexagramInfo.name,
    meaning: hexagramInfo.meaning,
    upperTrigram: {
      ...upperTrigram,
      position: '上卦',
      meaning: `代表${upperTrigram.nature}的特质`
    },
    lowerTrigram: {
      ...lowerTrigram,
      position: '下卦',
      meaning: `代表${lowerTrigram.nature}的特质`
    },
    wuxingAnalysis,
    yaoAnalysis,
    overall: generateOverallAnalysis(hexagramInfo, wuxingAnalysis)
  };
}

// 辅助函数：根据时间生成卦象
function generateTrigramByTime(timeValue: number): BaguaInfo {
  const normalizedValue = timeValue % 8;
  const baguas = Object.values(baguaData);
  return baguas[normalizedValue];
}

// 辅助函数：根据数字生成卦象
function generateTrigramByNumber(number: number): BaguaInfo {
  const baguas = Object.values(baguaData);
  console.log('生成卦象数字：', number);
  console.log('对应卦象：', baguas[number - 1]);
  return baguas[number - 1];
}

// 辅助函数：生成变爻
function generateChangingLines(seed: number): number[] {
  const result: number[] = [];
  for (let i = 0; i < 6; i++) {
    if ((seed + i) % 6 === 0) {
      result.push(i + 1);
    }
  }
  return result;
}

// 辅助函数：根据数字生成变爻
function generateChangingLinesByNumber(number: number): number[] {
  const lines: number[] = [];
  
  // 使用数字的不同特征生成变爻
  if (number % 2 === 0) {
    lines.push(1); // 初爻
  }
  if (number % 3 === 0) {
    lines.push(3); // 三爻
  }
  if (number % 5 === 0) {
    lines.push(5); // 五爻
  }
  if (number % 7 === 0) {
    lines.push(2); // 二爻
  }
  if (number % 11 === 0) {
    lines.push(4); // 四爻
  }
  if (number % 13 === 0) {
    lines.push(6); // 上爻
  }
  
  return lines.length > 0 ? lines : [(number % 6) + 1];
}

// 辅助函数：分析五行关系
function analyzeWuxing(upperAttribute: string, lowerAttribute: string): string {
  const relation = wuxingRelations[upperAttribute]?.[lowerAttribute] || '关系平稳';
  const detail = wuxingDetails[upperAttribute]?.[lowerAttribute] || '宜守正道';
  return `上卦${upperAttribute}与下卦${lowerAttribute}：${relation}。${detail}。`;
}

// 辅助函数：分析变爻
function analyzeChangingLines(lines: number[], yaoChanges: { [key: number]: string }): string[] {
  if (!lines || lines.length === 0) {
    return ['无变爻，保持现状即可。'];
  }
  return lines.map(line => {
    const lineInfo = lineChangeMeanings[line];
    const yaoText = yaoChanges[line] || '具体情况具体分析';
    if (!lineInfo) return `第${line}爻变化：${yaoText}`;
    return `${lineInfo.name}发生变化：${yaoText}（${lineInfo.changing}）`;
  });
}

// 辅助函数：生成总体分析
function generateOverallAnalysis(hexagramInfo: HexagramInfo, wuxingAnalysis: string): string {
  return `此卦为${hexagramInfo.name}，${hexagramInfo.meaning}。
${hexagramInfo.description}
从五行关系来看，${wuxingAnalysis}
具体分析：${hexagramInfo.analysis}
建议：在行动时要把握时机，明察形势，审时度势。注意在发展过程中保持谦逊谨慎的态度，不可操之过急。`;
}

// 检查所有可能的卦象组合
function checkAllPossibleHexagrams() {
  const baguas = Object.values(baguaData);
  const allCombinations = new Set<string>();
  const missingHexagrams = new Set<string>();
  
  // 生成所有可能的组合
  for (let num = 1; num <= 50; num++) {
    const upperNumber = Math.ceil(num / 7) % 8 || 8;
    const lowerNumber = num % 8 || 8;
    
    const upperTrigram = baguas[upperNumber - 1];
    const lowerTrigram = baguas[lowerNumber - 1];
    
    const hexagramCode = upperTrigram.binary + lowerTrigram.binary;
    allCombinations.add(hexagramCode);
    
    if (!hexagramData[hexagramCode]) {
      missingHexagrams.add(hexagramCode);
      console.log(`数字${num}生成的卦象代码${hexagramCode}（上卦：${upperTrigram.name}，下卦：${lowerTrigram.name}）在数据中缺失`);
    }
  }
  
  console.log('所有可能的组合数量：', allCombinations.size);
  console.log('缺失的卦象数量：', missingHexagrams.size);
  return { allCombinations, missingHexagrams };
}

// 检查卦象的唯一性
function checkHexagramUniqueness() {
  const hexagramCodes = Object.keys(hexagramData);
  const uniqueCodes = new Set(hexagramCodes);
  
  console.log('总卦象数量：', hexagramCodes.length);
  console.log('唯一卦象数量：', uniqueCodes.size);
  
  if (hexagramCodes.length !== uniqueCodes.size) {
    console.log('发现重复的卦象：');
    const codeCount = new Map<string, number>();
    hexagramCodes.forEach(code => {
      codeCount.set(code, (codeCount.get(code) || 0) + 1);
    });
    
    codeCount.forEach((count, code) => {
      if (count > 1) {
        console.log(`卦象代码 ${code} 重复出现 ${count} 次，卦名：${hexagramData[code].name}`);
      }
    });
  }
  
  return hexagramCodes.length === uniqueCodes.size;
}

// 执行检查
console.log('开始检查所有可能的卦象组合...');
const { allCombinations, missingHexagrams } = checkAllPossibleHexagrams(); 

// 执行唯一性检查
console.log('开始检查卦象的唯一性...');
const isUnique = checkHexagramUniqueness();
console.log('卦象唯一性检查结果：', isUnique ? '通过' : '存在重复');

// 测试特定数字的卦象生成
function testNumberHexagram(number: number) {
  const upperNumber = Math.ceil(number / 7) % 8 || 8;
  const lowerNumber = number % 8 || 8;
  
  const baguas = Object.values(baguaData);
  const upperTrigram = baguas[upperNumber - 1];
  const lowerTrigram = baguas[lowerNumber - 1];
  
  const hexagramCode = upperTrigram.binary + lowerTrigram.binary;
  console.log(`数字 ${number} 生成的卦象：`);
  console.log(`上卦：${upperTrigram.name}（${upperTrigram.binary}）`);
  console.log(`下卦：${lowerTrigram.name}（${lowerTrigram.binary}）`);
  console.log(`卦象代码：${hexagramCode}`);
  console.log(`卦象名称：${hexagramData[hexagramCode]?.name || '未知卦象'}`);
  console.log('---');
}

// 测试1-10的数字
console.log('测试数字1-10的卦象生成：');
for (let i = 1; i <= 10; i++) {
  testNumberHexagram(i);
}

// 测试所有数字的卦象生成
function testAllNumbers() {
  const missingHexagrams = new Map<string, { number: number, upperTrigram: BaguaInfo, lowerTrigram: BaguaInfo }>();
  
  console.log('\n测试结果：');
  console.log('数字\t上卦\t下卦\t卦象代码\t卦象名称');
  console.log('----------------------------------------');
  
  for (let num = 1; num <= 50; num++) {
    const upperNumber = Math.ceil(num / 7) % 8 || 8;
    const lowerNumber = num % 8 || 8;
    
    const baguas = Object.values(baguaData);
    const upperTrigram = baguas[upperNumber - 1];
    const lowerTrigram = baguas[lowerNumber - 1];
    
    const hexagramCode = upperTrigram.binary + lowerTrigram.binary;
    const hexagramName = hexagramData[hexagramCode]?.name || '未知卦象';
    
    console.log(`${num}\t${upperTrigram.name}\t${lowerTrigram.name}\t${hexagramCode}\t${hexagramName}`);
    
    if (!hexagramData[hexagramCode]) {
      missingHexagrams.set(hexagramCode, {
        number: num,
        upperTrigram,
        lowerTrigram
      });
    }
  }
  
  if (missingHexagrams.size > 0) {
    console.log('\n缺失的卦象：');
    console.log('数字\t卦象\t\t卦象代码');
    console.log('----------------------------------------');
    missingHexagrams.forEach((info, code) => {
      console.log(`${info.number}\t${info.upperTrigram.name}${info.lowerTrigram.name}\t\t${code}`);
    });
  }
  
  return missingHexagrams;
}

// 执行测试
console.log('开始测试所有数字（1-50）的卦象生成...');
const missingHexagramsFromTest = testAllNumbers();
console.log(`\n共发现 ${missingHexagramsFromTest.size} 个缺失的卦象`);

// 根据铜钱卦结果生成卦象
export function getHexagramByCoin(coinResults: number[][]): HexagramResult {
  if (coinResults.length !== 6) {
    throw new Error('需要6次投掷结果');
  }
  
  // 将铜钱结果转换为二进制字符串
  const binaryString = coinResults.map(result => {
    const positiveCount = result.filter(r => r === 1).length;
    // 二正一反或三正为阳（1），一正二反或三反为阴（0）
    return positiveCount >= 2 ? '1' : '0';
  }).join('');
  
  // 分割上下卦
  const upperBinary = binaryString.slice(0, 3);
  const lowerBinary = binaryString.slice(3);
  
  // 查找对应的卦象
  const upperTrigram = Object.values(baguaData).find(bagua => bagua.binary === upperBinary);
  const lowerTrigram = Object.values(baguaData).find(bagua => bagua.binary === lowerBinary);
  
  if (!upperTrigram || !lowerTrigram) {
    throw new Error('无效的卦象组合');
  }
  
  // 确定变爻
  const changingLines = coinResults.map((result, index) => {
    const positiveCount = result.filter(r => r === 1).length;
    // 三正（老阳）或三反（老阴）为变爻
    return positiveCount === 3 || positiveCount === 0 ? index + 1 : null;
  }).filter((line): line is number => line !== null);
  
  return {
    upperTrigram,
    lowerTrigram,
    changingLines
  };
}