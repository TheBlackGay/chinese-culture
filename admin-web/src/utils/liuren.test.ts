const { LiuRen, calculateLiuRen } = require('./liuren');

// 测试用例
const testCases = [
  {
    name: '测试用例1',
    date: new Date('2024-01-20T10:00:00'), // 甲辰日巳时
    shiChen: '巳',
    questionType: 'career'
  },
  {
    name: '测试用例2',
    date: new Date('2024-01-21T14:00:00'), // 乙巳日未时
    shiChen: '未',
    questionType: 'love'
  },
  {
    name: '测试用例3',
    date: new Date('2024-01-22T08:00:00'), // 丙午日辰时
    shiChen: '辰',
    questionType: 'wealth'
  }
];

// 测试函数
function testLiuRen() {
  console.log('开始六壬测试...\n');

  testCases.forEach(testCase => {
    console.log(`=== ${testCase.name} ===`);
    console.log('输入参数：', testCase);

    try {
      // 创建六壬实例
      const liuren = new LiuRen(testCase.date, testCase.shiChen, testCase.questionType);

      // 测试日干支计算
      const dayGanZhi = liuren['getDayGanZhi']();
      console.log('日干支：', dayGanZhi);

      // 测试地盘计算
      const diPan = liuren['diPan'];
      console.log('地盘：', diPan);

      // 测试天盘计算
      const tianPan = liuren['tianPan'];
      console.log('天盘：', tianPan);

      // 测试四课计算
      const siKe = liuren['siKe'];
      console.log('四课：', siKe);

      // 测试三传计算
      const sanChuan = liuren['sanChuan'];
      console.log('三传：', sanChuan);

      // 测试神将计算
      const shenJiang = liuren['calculateShenJiang']();
      console.log('神将：', shenJiang);

      // 测试课体判断
      const keTi = liuren['determineKeTi']();
      console.log('课体：', keTi);

      // 测试格局判断
      const geJu = liuren['determineGeJu']();
      console.log('格局：', geJu);

      // 获取完整结果
      const result = liuren.getResult();
      console.log('完整结果：', result);

      console.log('测试通过！\n');
    } catch (error) {
      console.error('测试失败：', error);
      console.log('\n');
    }
  });
}

// 运行测试
testLiuRen(); 