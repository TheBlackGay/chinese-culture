import { calculateZiWei } from './ziwei';

// 测试用例
async function runTest() {
  console.log('=== 开始测试 ===');
  console.log('测试用例: 1994-12-08 09:05 男');
  
  try {
    const result = calculateZiWei(1994, 12, 8, 9, 'male');
    
    // 检查命宫星耀
    const mingGong = result.palaces[0];  // 命宫应该是第一个宫位
    console.log('\n命宫信息:', {
      name: mingGong.name,
      type: mingGong.type,
      stars: mingGong.stars.map(star => ({
        name: star.name,
        type: star.type,
        brightness: star.brightness
      }))
    });
    
    // 检查是否有紫微和破军
    const hasZiwei = mingGong.stars.some(star => star.name === '紫微');
    const hasPojun = mingGong.stars.some(star => star.name === '破军');
    
    console.log('\n验证结果:');
    console.log('- 命宫是否有紫微:', hasZiwei);
    console.log('- 命宫是否有破军:', hasPojun);
    
    if (!hasZiwei || !hasPojun) {
      console.log('\n警告: 计算结果可能不正确！');
      console.log('期望结果: 紫微破军应该在命宫');
    }
    
    // 输出完整的宫位信息供参考
    console.log('\n所有宫位信息:');
    result.palaces.forEach((palace, index) => {
      console.log(`\n宫位 ${index + 1}:`, {
        name: palace.name,
        type: palace.type,
        stars: palace.stars.map(star => star.name).join(', ')
      });
    });
    
  } catch (error) {
    console.error('测试失败:', error);
  }
  
  console.log('\n=== 测试结束 ===');
}

// 运行测试
runTest(); 