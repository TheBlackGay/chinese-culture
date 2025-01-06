import { baguaData, hexagramData } from './bagua';

// 检查所有可能的卦象组合
function checkAllHexagrams() {
  const baguas = Object.values(baguaData);
  const allPossibleCombinations = new Set<string>();
  const missingHexagrams = new Map<string, { upper: string, lower: string }>();
  const existingHexagrams = new Set<string>();

  // 生成所有可能的组合（8x8=64种组合）
  for (const upperTrigram of baguas) {
    for (const lowerTrigram of baguas) {
      const hexagramCode = upperTrigram.binary + lowerTrigram.binary;
      allPossibleCombinations.add(hexagramCode);

      // 检查是否存在于hexagramData中
      if (!hexagramData[hexagramCode]) {
        missingHexagrams.set(hexagramCode, {
          upper: upperTrigram.name,
          lower: lowerTrigram.name
        });
      } else {
        existingHexagrams.add(hexagramCode);
      }
    }
  }

  // 检查是否有多余的卦象
  const extraHexagrams = new Map<string, string>();
  for (const code of Object.keys(hexagramData)) {
    if (!allPossibleCombinations.has(code)) {
      extraHexagrams.set(code, hexagramData[code].name);
    }
  }

  // 输出统计信息
  console.log('卦象检查结果：');
  console.log('----------------------------------------');
  console.log(`总共可能的组合数：${allPossibleCombinations.size}`);
  console.log(`已实现的卦象数：${existingHexagrams.size}`);
  console.log(`缺失的卦象数：${missingHexagrams.size}`);
  console.log(`多余的卦象数：${extraHexagrams.size}`);

  // 输出缺失的卦象详情
  if (missingHexagrams.size > 0) {
    console.log('\n缺失的卦象列表：');
    console.log('卦象代码\t上卦\t下卦');
    console.log('----------------------------------------');
    missingHexagrams.forEach((info, code) => {
      console.log(`${code}\t${info.upper}\t${info.lower}`);
    });
  }

  // 输出多余的卦象详情
  if (extraHexagrams.size > 0) {
    console.log('\n多余的卦象列表：');
    console.log('卦象代码\t卦象名称');
    console.log('----------------------------------------');
    extraHexagrams.forEach((name, code) => {
      console.log(`${code}\t${name}`);
    });
  }

  return {
    allPossibleCombinations,
    missingHexagrams,
    existingHexagrams,
    extraHexagrams
  };
}

// 检查卦象数据的完整性
function checkHexagramDataIntegrity() {
  const issues = new Map<string, string[]>();

  // 检查每个卦象的数据完整性
  Object.entries(hexagramData).forEach(([code, hexagram]) => {
    const problems = [];

    // 检查必要字段
    if (!hexagram.name) problems.push('缺少卦象名称');
    if (!hexagram.meaning) problems.push('缺少卦象含义');
    if (!hexagram.description) problems.push('缺少卦象描述');
    if (!hexagram.analysis) problems.push('缺少卦象分析');
    
    // 检查爻辞完整性
    if (!hexagram.yaoChanges || Object.keys(hexagram.yaoChanges).length !== 6) {
      problems.push('爻辞不完整（应该有6条）');
    }

    if (problems.length > 0) {
      issues.set(code, problems);
    }
  });

  // 输出检查结果
  if (issues.size > 0) {
    console.log('\n卦象数据完整性问题：');
    console.log('卦象代码\t问题列表');
    console.log('----------------------------------------');
    issues.forEach((problems, code) => {
      console.log(`${code}\t${problems.join(', ')}`);
    });
  } else {
    console.log('\n所有卦象数据完整性检查通过');
  }

  return issues;
}

// 执行测试
console.log('开始进行卦象完整性检查...\n');
const hexagramCheckResult = checkAllHexagrams();
const dataIntegrityIssues = checkHexagramDataIntegrity();

// 导出测试结果
export const testResults = {
  hexagramCheck: hexagramCheckResult,
  dataIntegrity: dataIntegrityIssues
}; 