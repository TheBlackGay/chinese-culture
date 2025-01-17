// 年干支权重映射
const yearWeightMap: Record<string, number> = {
  '甲子': 1.2, '甲戌': 1.5, '甲申': 0.5, '甲午': 1.5, '甲辰': 0.8, '甲寅': 1.2,
  '乙丑': 0.9, '乙亥': 0.9, '乙酉': 1.5, '乙未': 0.6, '乙巳': 0.7, '乙卯': 0.8,
  '丙寅': 0.6, '丙子': 1.6, '丙戌': 0.6, '丙申': 0.5, '丙午': 1.3, '丙辰': 0.8,
  '丁卯': 0.7, '丁丑': 0.8, '丁亥': 1.6, '丁酉': 1.4, '丁未': 0.5, '丁巳': 0.6,
  '戊辰': 1.2, '戊寅': 0.8, '戊子': 1.5, '戊戌': 1.4, '戊申': 1.4, '戊午': 1.9,
  '己巳': 0.5, '己卯': 1.9, '己丑': 0.7, '己亥': 0.9, '己酉': 0.5, '己未': 0.6,
  '庚午': 0.9, '庚辰': 1.2, '庚寅': 0.9, '庚子': 0.7, '庚戌': 0.9, '庚申': 0.8,
  '辛未': 0.8, '辛巳': 0.6, '辛卯': 1.2, '辛丑': 0.7, '辛亥': 1.7, '辛酉': 1.6,
  '壬申': 0.7, '壬午': 0.8, '壬辰': 1.0, '壬寅': 0.9, '壬子': 0.5, '壬戌': 1.0,
  '癸酉': 0.8, '癸未': 0.7, '癸巳': 0.7, '癸卯': 1.2, '癸丑': 0.7, '癸亥': 0.6
};

// 农历月份权重映射
const monthWeightMap: Record<number, number> = {
  1: 0.6,  // 正月
  2: 0.7,  // 二月
  3: 1.8,  // 三月
  4: 0.9,  // 四月
  5: 0.5,  // 五月
  6: 1.6,  // 六月
  7: 0.9,  // 七月
  8: 1.5,  // 八月
  9: 1.8,  // 九月
  10: 0.8, // 十月
  11: 0.9, // 十一月
  12: 0.5  // 十二月
};

// 农历日期权重映射
const dayWeightMap: Record<number, number> = {
  1: 0.5,  2: 1.0,  3: 0.8,  4: 1.5,  5: 1.6,  6: 1.5,
  7: 0.8,  8: 1.6,  9: 0.8,  10: 1.6, 11: 0.9, 12: 1.7,
  13: 0.8, 14: 1.7, 15: 1.0, 16: 0.8, 17: 0.9, 18: 1.8,
  19: 0.5, 20: 1.5, 21: 1.0, 22: 0.9, 23: 0.8, 24: 0.9,
  25: 1.5, 26: 1.8, 27: 0.7, 28: 0.8, 29: 1.6, 30: 0.6
};

// 时辰权重映射
const hourWeightMap: Record<string, number> = {
  '子': 1.6, // 23-01时
  '丑': 0.6, // 01-03时
  '寅': 0.7, // 03-05时
  '卯': 1.0, // 05-07时
  '辰': 0.9, // 07-09时
  '巳': 1.6, // 09-11时
  '午': 1.0, // 11-13时
  '未': 0.8, // 13-15时
  '申': 0.8, // 15-17时
  '酉': 0.9, // 17-19时
  '戌': 0.6, // 19-21时
  '亥': 0.6  // 21-23时
};

// 称骨歌诀查询表
const maleChengGuSongs: Record<string, { text: string; explanation: string }> = {
  '2.1': {
    text: '短命非业谓大空，平生灾难事重重；凶祸频临陷逆境，终世困苦事不成。',
    explanation: '此命较为艰难，需要谨慎处世，勤奋努力。'
  },
  '2.2': {
    text: '身寒骨冷苦伶仃，此命推来行乞人；劳劳碌碌无度日，终年打拱过平生。',
    explanation: '此命需要通过勤劳来改善命运。'
  },
  '2.3': {
    text: '此命推来骨格轻，求谋作事事难成；妻儿兄弟应难许，别处他乡作散人。',
    explanation: '宜在外发展，创立自己的事业。'
  },
  '2.4': {
    text: '此命推来福禄无，门庭困苦总难荣；六亲骨肉皆无靠，流浪他乡作老翁。',
    explanation: '早年较为困苦，宜外出发展。'
  },
  '2.5': {
    text: '此命推来祖业微，门庭营度似稀奇；六亲骨肉如冰炭，一世勤劳自把持。',
    explanation: '需要依靠自己的努力，不能依赖他人。'
  },
  '2.6': {
    text: '平生衣禄苦中求，独自营谋事不休；离祖出门宜早计，晚来衣禄自无休。',
    explanation: '早年宜离祖创业，晚年衣禄无忧。'
  },
  '2.7': {
    text: '一生作事少商量，难靠祖宗作主张；独马单枪空做去，早年晚岁总无长。',
    explanation: '独立性强，但需要多听取他人意见。'
  },
  '2.8': {
    text: '一生行事似飘蓬，祖宗产业在梦中；若不过房改名姓，也当移徒二三通。',
    explanation: '宜迁移改变，方能改善命运。'
  },
  '2.9': {
    text: '初年运限未曾亨，纵有功名在后成；须过四旬才可立，移居改姓始为良。',
    explanation: '中年后运势转好，宜改迁立业。'
  },
  '3.0': {
    text: '劳劳碌碌苦中求，东奔西走何日休；若使终身勤与俭，老来稍可免忧愁。',
    explanation: '一生勤劳，晚年可得安稳。'
  },
  '3.1': {
    text: '忙忙碌碌苦中求，何日云开见日头；难得祖基家可立，中年衣食渐无忧。',
    explanation: '中年后运势渐好，衣食无忧。'
  },
  '3.2': {
    text: '初年运蹇事难谋，渐有财源如水流；到得中年衣食旺，那时名利一齐收。',
    explanation: '中年后财运亨通，名利双收。'
  },
  '3.3': {
    text: '早年做事事难成，百年勤劳枉费心；半世自如流水去，后来运到始得金。',
    explanation: '前半生较为困难，后半生运势转好。'
  },
  '3.4': {
    text: '此命福气果如何，僧道门中衣禄多；离祖出家方为妙，朝晚拜佛念弥陀。',
    explanation: '与佛道有缘，宜向善修行。'
  },
  '3.5': {
    text: '生平福量不周全，祖业根基觉少传；营事生涯宜守旧，时来衣食胜从前。',
    explanation: '宜守成，不宜冒进，循序渐进可得安稳。'
  },
  '3.6': {
    text: '不须劳碌过平生，独自成家福不轻；早有福星常照命，任君行去百般成。',
    explanation: '天生福星照命，诸事顺遂。'
  },
  '3.7': {
    text: '此命般般事不成、弟兄少力自孤行；虽然祖业须微有，来得明时去不明。',
    explanation: '独立性强，但需谨慎理财。'
  },
  '3.8': {
    text: '一身骨肉最清高，早入簧门姓氏标；待到年将三十六，蓝衫脱去换红袍。',
    explanation: '中年后有显贵之象。'
  },
  '3.9': {
    text: '此命终身运不通，劳劳作事尽皆空；苦心竭力成家计，到得那时在梦中。',
    explanation: '需要持之以恒，切勿好高骛远。'
  },
  '4.0': {
    text: '平生衣禄是绵长，件件心中自主张；前面风霜多受过，后来必定享安康。',
    explanation: '早年虽苦，晚年必享安康。'
  },
  '4.1': {
    text: '此命推来自不同，为人能干异凡庸；中年还有逍遥福，不比前时运来通。',
    explanation: '中年后运势亨通，生活逍遥。'
  },
  '4.2': {
    text: '得宽怀处且宽怀，何用双眉皱不开；若使中年命运济，那时名利一起来。',
    explanation: '保持乐观，中年后名利双收。'
  },
  '4.3': {
    text: '为人心性最聪明，作事轩昂近贵人；衣禄一生天注定，不须劳碌是丰亨。',
    explanation: '天生聪慧，一生衣禄无忧。'
  },
  '4.4': {
    text: '万事由天莫苦求，须知福碌赖人修；当年财帛难如意，晚景欣然便不优。',
    explanation: '随遇而安，晚年安康。'
  },
  '4.5': {
    text: '名利推求竟若何？前番辛苦后奔波；命中难养男和女，骨肉扶持也不多。',
    explanation: '事业有成，但子女缘分较薄。'
  },
  '4.6': {
    text: '东西南北尽皆通，出姓移居更觉隆；衣禄无穷无数定，中年晚景一般同。',
    explanation: '宜四方发展，衣禄丰足。'
  },
  '4.7': {
    text: '此命推求旺末年，妻荣子贵自怡然；平生原有滔滔福，可卜财源若水泉。',
    explanation: '晚年福旺，财源广进。'
  },
  '4.8': {
    text: '初年运道未曾通，几许蹉跎命亦穷；兄弟六亲无依靠，一生事业晚来整。',
    explanation: '早年困苦，晚年渐好。'
  },
  '4.9': {
    text: '此命推来福不轻，自成自立显门庭；从来富贵人钦敬，使婢差奴过一生。',
    explanation: '自立自强，终成富贵。'
  },
  '5.0': {
    text: '为利为名终日劳，中年福禄也多遭；老来自有财星照，不比前番目下高。',
    explanation: '中年后运势渐旺，晚年更佳。'
  },
  '5.1': {
    text: '一世荣华事事通，不须劳碌自亨通；兄弟叔侄皆如意，家业成时福禄宏。',
    explanation: '天生福旺，家族兴旺。'
  },
  '5.2': {
    text: '一世亨通事事能，不须劳苦自然宁；宗族有光欣喜甚，家产丰盈自称心。',
    explanation: '诸事顺遂，家业兴旺。'
  },
  '5.3': {
    text: '此格推来福泽宏，兴家立业在其中；一生衣食安排定，却是人间一福翁。',
    explanation: '福泽深厚，终成富贵。'
  },
  '5.4': {
    text: '此格详采福泽宏，诗书满腹看功成；丰衣足食多安稳，正是人间有福人。',
    explanation: '学识渊博，生活安稳。'
  },
  '5.5': {
    text: '策马扬鞭争名利，少年作事费筹论；一朝福禄源源至，富贵荣华显六亲。',
    explanation: '少年得志，福禄绵长。'
  },
  '5.6': {
    text: '此格推来礼义通，一身福禄用无穷；甜酸苦辣皆尝过，滚滚财源盈而丰。',
    explanation: '历经磨练，终得成功。'
  },
  '5.7': {
    text: '福禄丰盈万事全，一身荣耀乐天年；名扬威震人争羡，此世逍遥宛似仙。',
    explanation: '福禄双全，荣华富贵。'
  },
  '5.8': {
    text: '平生衣食自然来，名利双全富贵偕；金榜题名登甲第，紫袍玉带走金阶。',
    explanation: '功名显达，富贵双全。'
  },
  '5.9': {
    text: '细推此格秀而清，必定才高学业成；甲第之中应有分，扬鞭走马显威荣。',
    explanation: '才学出众，功名显达。'
  },
  '6.0': {
    text: '一朝金榜快题名，显祖荣宗大器成；衣禄定然无欠缺，田园财帛更丰盈。',
    explanation: '功名显达，家业丰盈。'
  },
  '6.1': {
    text: '不作朝中金榜客，定为世上大财翁；聪明天付经书熟，名显高褂自是荣。',
    explanation: '学识渊博，富贵双全。'
  },
  '6.2': {
    text: '此命生来福不穷，读书必定显亲宗；紫衣玉带为卿相，富贵荣华孰与同。',
    explanation: '功名显达，位居卿相。'
  },
  '6.3': {
    text: '命主为官福禄长，得来富贵实非常；名题雁塔传金榜，大显门庭天下扬。',
    explanation: '官运亨通，名扬天下。'
  },
  '6.4': {
    text: '此格威权不可当，紫袍金带尘高堂；荣华富贵谁能及，万古留名姓氏扬。',
    explanation: '位极人臣，功名永垂。'
  },
  '6.5': {
    text: '细推此命福非轻，富贵荣华孰与争；定国安邦人极品，威声显赫震寰瀛。',
    explanation: '位居显要，定国安邦。'
  },
  '6.6': {
    text: '此格人间一福人，堆金积玉满堂春；从来富贵有天定，金榜题名更显亲。',
    explanation: '富贵天成，功名显达。'
  },
  '6.7': {
    text: '此命生来福自宏，田园家业最高隆；平生衣禄盈丰足，一路荣华万事通。',
    explanation: '家业兴旺，一生顺遂。'
  },
  '6.8': {
    text: '富贵由天莫苦求，万事家计不须谋；十年不比前番事，祖业根基千古留。',
    explanation: '富贵天成，基业永固。'
  },
  '6.9': {
    text: '君是人间福禄星，一生富贵众人钦；总然衣禄由天定，安享荣华过一生。',
    explanation: '福禄双全，众人敬仰。'
  },
  '7.0': {
    text: '此命推来福不轻，何须愁虑苦劳心；荣华富贵已天定，正笏垂绅拜紫宸。',
    explanation: '富贵天定，位极人臣。'
  },
  '7.1': {
    text: '此命生成大不同，公侯卿相在其中；一生自有逍遥福，富贵荣华极品隆。',
    explanation: '位居公卿，福禄无量。'
  },
  '7.2': {
    text: '此命推来天下隆，必定人间一主公；富贵荣华数不尽，定为乾坤一蛟龙。',
    explanation: '位极人臣，富贵无量。'
  }
};

const femaleChengGuSongs: Record<string, { text: string; explanation: string }> = {
  '2.1': {
    text: '生身此命运不通，乌云盖月黑朦胧，莫向故园载花木，可来幽地种青松',
    explanation: '此命较为艰难，需要坚韧不拔的精神。'
  },
  '2.2': {
    text: '女命孤冷独凄身，此身推来路乞人，操心烦恼难度日，一生痛苦度光阴',
    explanation: '命运较为坎坷，需要保重身心。'
  },
  '2.3': {
    text: '女命生来轻薄人，营谋事作难称心，六亲骨肉亦无靠，奔走劳碌困苦门',
    explanation: '事业难成，亲缘较薄。'
  },
  '2.4': {
    text: '女命推来福禄无，治家艰难辛苦多，丈夫儿女不亲爱，奔走他乡作游姑',
    explanation: '家庭关系较为复杂，宜修身养性。'
  },
  '2.5': {
    text: '此命一身八字低，家庭艰辛多苦妻，娘家亲友冷如炭，一生勤劳多忧眉',
    explanation: '家庭生活较为艰辛，需要坚强面对。'
  },
  '2.6': {
    text: '平生依禄但苦求，两次配夫带忧愁，咸酸苦辣他偿过，晚年衣食本无忧',
    explanation: '早年较苦，晚年渐好。'
  },
  '2.7': {
    text: '此格做事单独强，难告夫君作主张，心问口来口问心，晚景衣禄宜自生',
    explanation: '独立性强，宜自主发展。'
  },
  '2.8': {
    text: '女命生来八字轻，为善作事也无因，你把别人当亲生，别人对你假殷情',
    explanation: '人际关系需要谨慎。'
  },
  '2.9': {
    text: '花支艳来硬性身，自奔自力不求人，若问求财方可止，在苦有甜度光阴',
    explanation: '独立自主，终有所成。'
  },
  '3.0': {
    text: '女命推来比郎强，婚姻大事碍无障，中年走过坎坷地，末年渐经行前强',
    explanation: '中年后运势转好。'
  },
  '3.1': {
    text: '早年行运在忙头，劳碌奔波苦勤求，费力劳心把家立，后来晚景名忧愁',
    explanation: '早年辛劳，晚年安稳。'
  },
  '3.2': {
    text: '时逢运来带吉神，从有凶星转灰尘，真变假来假成真，结拜弟妹当亲生',
    explanation: '运势渐好，贵人相助。'
  },
  '3.3': {
    text: '初限命中有变化，中年可比树落花，勤俭持家难度日，晚年成业享荣华',
    explanation: '早年艰辛，晚年富贵。'
  },
  '3.4': {
    text: '矮巴勾枣难捞枝，看破红尘最相宜，谋望求财空费力，婚姻三娶两次离',
    explanation: '婚姻多波折，宜淡泊名利。'
  },
  '3.5': {
    text: '女子走冰怕冰薄，出行交易受残霜，婚姻周郎休此意，官司口舌须相加',
    explanation: '谨慎行事，避免口舌是非。'
  },
  '3.6': {
    text: '忧悉常锁两眉间，家业挂心不等闲，从今以后防口角，任意行移不相关',
    explanation: '注意修身养性，避免争执。'
  },
  '3.7': {
    text: '此命推来费运多，若作摧群受折磨，山路崎岖吊下耳，左插右安心难留',
    explanation: '运势多变，需要坚持。'
  },
  '3.8': {
    text: '凤鸣岐山四方扬，女命逢此大吉昌，走失夫君音信有，晚年衣禄财盈箱',
    explanation: '晚年富贵，家庭和睦。'
  },
  '3.9': {
    text: '女命推来运未能，劳碌奔波一场空，好似俊鸟在笼锁，中年未限凄秋风',
    explanation: '中年前运势较差，需要耐心。'
  },
  '4.0': {
    text: '目前月令运不良，千辛万苦受煎熬，女身受得多苦难，晚年福禄比密甜',
    explanation: '早年艰辛，晚年甜蜜。'
  },
  '4.1': {
    text: '此命推来一般艰，女子为人很非凡，中年逍遥多自在，晚年更比中年超',
    explanation: '中年后运势渐佳。'
  },
  '4.2': {
    text: '杜井破废已多年，今有泉水出来鲜，资生济竭人称美，中运来转喜安然',
    explanation: '运势转好，前景光明。'
  },
  '4.3': {
    text: '推车靠涯道路通，女名求财也无穷，婚姻出配无阻碍，疾病口舌离身躬',
    explanation: '事业顺遂，婚姻和谐。'
  },
  '4.4': {
    text: '夜梦金银醒来空，立志谋业运不通，婚姻难成交易散，夫君趟失未见踪',
    explanation: '需谨慎行事，避免空想。'
  },
  '4.5': {
    text: '女命终身驳杂多，六亲骨肉不相助，命中男女都难养，劳碌辛苦还奔波',
    explanation: '家庭关系复杂，需要坚强。'
  },
  '4.6': {
    text: '孤舟行水离沙滩，离乡出外早过家，是非口舌皆无碍，婚姻合配紫微房',
    explanation: '远行有利，婚姻美满。'
  },
  '4.7': {
    text: '时来运转喜开颜，多年枯木逢春花，枝叶重生多茂盛，凡人见得都赞夸',
    explanation: '运势好转，前程似锦。'
  },
  '4.8': {
    text: '一朵鲜花镜中开，看着极好取不来，劝你休把镜花想，此命推来主可癫',
    explanation: '切忌好高骛远，脚踏实地。'
  },
  '4.9': {
    text: '一生为人福宏名，心兹随君显门庭，容貌美丽惹人爱，银钱富足万事成',
    explanation: '福禄双全，美貌与智慧并存。'
  },
  '5.0': {
    text: '马氏太公不相和，好命逢此忧凝，多恩人无义反为仇，是非平地起风波',
    explanation: '人际关系复杂，需谨慎处世。'
  },
  '5.1': {
    text: '肥羊一群入出场，防虎逢之把口张，适口充饥心欢喜，女命八字大吉昌',
    explanation: '运势昌隆，前程似锦。'
  },
  '5.2': {
    text: '顺风行舟扯起帆，上天又助一顺风，不用费力逍遥去，任意顺行大享通',
    explanation: '诸事顺遂，一帆风顺。'
  },
  '5.3': {
    text: '此命相貌眉目清，文武双全功名成，一生衣禄皆无缺，可算世上积福人',
    explanation: '相貌端庄，福禄双全。'
  },
  '5.4': {
    text: '运开满腹好文章，谋事求财大吉祥，出行交易多得稳，到处享通姓名扬',
    explanation: '事业有成，名利双收。'
  },
  '5.5': {
    text: '发政旅仁志量高，女命求财任他乡，交舍婚姻多有意，无君出外有音耗',
    explanation: '事业发展顺利，婚姻美满。'
  },
  '5.6': {
    text: '明珠辉吐离埃来，女有口有清散开，走失郎君当两归，交易有成永无灾',
    explanation: '运势光明，诸事顺遂。'
  },
  '5.7': {
    text: '游鱼戏水被网惊，踊身变化入龙门，三根杨柳垂金钱，万朵桃花显价能',
    explanation: '逢凶化吉，前程似锦。'
  },
  '5.8': {
    text: '此命推来转悠悠，时运未来莫强求，幸得今日重反点，自有好运在后头',
    explanation: '耐心等待，好运将至。'
  },
  '5.9': {
    text: '雨雪载途活泥泞，交易不安难出生，疾病还拉婚姻慢，谋望求财事难寻',
    explanation: '暂时困难，需要耐心。'
  },
  '6.0': {
    text: '女命八字喜气和，谋事求财吉庆多，口舌渐消疾病少，夫君走别归老窠',
    explanation: '运势和顺，家庭和睦。'
  },
  '6.1': {
    text: '缘木求鱼事多难，虽不得鱼无害反，若是行险弄巧地，事不遂心枉安凡',
    explanation: '谨慎行事，不可冒进。'
  },
  '6.2': {
    text: '指日高升气象新，走失待人有信音，好命遇事遂心好，伺病口舌皆除根',
    explanation: '运势渐好，诸事顺遂。'
  },
  '6.3': {
    text: '五官脱运难抬头，妇命须当把财求，交易少行有人助，一生衣禄不须愁',
    explanation: '贵人相助，衣禄无忧。'
  },
  '6.4': {
    text: '俊鸟曾得出胧中，脱离为难显威风，一朝得意福力至，东南西北任意通',
    explanation: '摆脱困境，前程似锦。'
  },
  '6.5': {
    text: '女命推来福非轻，兹善为事受人敬，天降文王开基业，八百年来富贵门',
    explanation: '福禄深厚，受人尊敬。'
  },
  '6.6': {
    text: '时来运转闺阁楼，贤德淑女君子求，击鼓乐之大吉庆，女命逢此喜悠悠',
    explanation: '贤德淑女，婚姻美满。'
  },
  '6.7': {
    text: '乱丝无头定有头，碰得亲事暂折磨，交易出行无好处，谋事求财心不遂',
    explanation: '暂时困难，需要耐心。'
  },
  '6.8': {
    text: '水底明月不可捞，女命早限运未高，交易出行难获利，未运终得渐见好',
    explanation: '早年不顺，晚年转好。'
  },
  '6.9': {
    text: '太公封祖不非凡，女子求财稳如山，交易合伙多吉庆，疾病口角遗天涯',
    explanation: '事业稳定，诸事顺遂。'
  },
  '7.0': {
    text: '本命推断喜气新，恰遇郎君金遂心，坤身来交正当运，富贵衣禄乐平生',
    explanation: '婚姻美满，富贵安康。'
  },
  '7.1': {
    text: '此命推来宏运，交不须再愁苦劳难，一生身有衣禄福，安享荣华胜班超',
    explanation: '福禄双全，荣华富贵。'
  }
};

// 数字转中文
const numberToChinese = (num: number): string => {
  const chineseNumbers = ['零', '一', '二', '三', '四', '五', '六', '七', '八', '九', '十'];
  
  // 处理整数部分
  const integerPart = Math.floor(num);
  let result = integerPart === 0 ? '' : chineseNumbers[integerPart];
  
  // 处理小数部分（钱）
  const decimalPart = Math.round((num - integerPart) * 10);
  
  if (integerPart > 0) {
    result += '两';
    if (decimalPart > 0) {
      result += chineseNumbers[decimalPart] + '钱';
    }
  } else if (decimalPart > 0) {
    result = chineseNumbers[decimalPart] + '钱';
  }
  
  return result || '零两';
};

// 计算称骨重量
export interface ChengGuResult {
  weight: number;        // 总重量
  weightInChinese: string; // 总重量（中文）
  yearWeight: number;    // 年重
  yearWeightInChinese: string; // 年重（中文）
  monthWeight: number;   // 月重
  monthWeightInChinese: string; // 月重（中文）
  dayWeight: number;     // 日重
  dayWeightInChinese: string; // 日重（中文）
  hourWeight: number;    // 时重
  hourWeightInChinese: string; // 时重（中文）
  description: string;   // 称骨歌诀描述
  explanation: string;   // 解释
}

export function calculateChengGu(
  yearGanZhi: string,    // 年干支
  lunarMonth: number,    // 农历月
  lunarDay: number,      // 农历日
  hourZhi: string,       // 时支
  gender: 'male' | 'female' = 'male', // 性别，默认为男
): ChengGuResult {
  console.log('Input values:', {
    yearGanZhi,
    lunarMonth,
    lunarDay,
    hourZhi,
    gender
  });

  // 获取各部分重量
  const yearWeight = yearWeightMap[yearGanZhi] || 0;
  const monthWeight = monthWeightMap[lunarMonth] || 0;
  const dayWeight = dayWeightMap[lunarDay] || 0;
  const hourWeight = hourWeightMap[hourZhi] || 0;

  console.log('Individual weights:', {
    yearWeight,
    monthWeight,
    dayWeight,
    hourWeight
  });

  // 计算总重量
  const totalWeight = +(yearWeight + monthWeight + dayWeight + hourWeight).toFixed(1);
  console.log('Total weight:', totalWeight);

  // 转换各个部分的重量为中文
  const yearWeightChinese = numberToChinese(yearWeight);
  const monthWeightChinese = numberToChinese(monthWeight);
  const dayWeightChinese = numberToChinese(dayWeight);
  const hourWeightChinese = numberToChinese(hourWeight);
  const totalWeightChinese = numberToChinese(totalWeight);

  // 根据性别选择歌诀表
  const songTable = gender === 'male' ? maleChengGuSongs : femaleChengGuSongs;
  
  // 查找对应的歌诀
  const songKey = totalWeight.toFixed(1);
  console.log('Looking for song with key:', songKey);
  console.log('Available song keys:', Object.keys(songTable));

  const song = songTable[songKey] || {
    text: `暂无对应的称骨歌诀（重量：${songKey}两）`,
    explanation: '请查证重量是否计算正确'
  };

  const result = {
    weight: totalWeight,
    weightInChinese: totalWeightChinese,
    yearWeight,
    yearWeightInChinese: yearWeightChinese,
    monthWeight,
    monthWeightInChinese: monthWeightChinese,
    dayWeight,
    dayWeightInChinese: dayWeightChinese,
    hourWeight,
    hourWeightInChinese: hourWeightChinese,
    description: song.text,
    explanation: song.explanation,
  };

  console.log('Final result:', result);
  return result;
} 