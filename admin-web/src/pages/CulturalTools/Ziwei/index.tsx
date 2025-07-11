import React, { useState } from 'react';
import { PageContainer } from '@ant-design/pro-components';
import { Card, DatePicker, Button, Radio, Space, message, Spin } from 'antd';
import type { Dayjs } from 'dayjs';
import { calculateZiWei } from '@/services/ziwei';
import ZiWeiChart from '@/components/ZiWeiChart';
import './index.less';

// 数据转换函数 - 将API格式转换为组件需要的格式
function processApiData(data: any) {
  if (!data) return null;
  
  // 从response中提取result
  const result = data.status === 'ok' && data.result ? data.result : data;
  
  // 确保宫位数据正确处理
  if (result.palaces && Array.isArray(result.palaces)) {
    result.palaces = result.palaces.map((palace: any) => {
      // 1. 确保palace.type存在
      if (!palace.type) {
        palace.type = palace.name;
      }
      
      // 2. 合并三种星耀为一个stars数组
      if (!palace.stars) {
        const allStars: any[] = [];
        
        // 处理主星
        if (palace.majorStars && Array.isArray(palace.majorStars)) {
          palace.majorStars.forEach((star: any) => {
            allStars.push({
              ...star,
              type: star.type || 'major'
            });
          });
        }
        
        // 处理辅星
        if (palace.minorStars && Array.isArray(palace.minorStars)) {
          palace.minorStars.forEach((star: any) => {
            allStars.push({
              ...star,
              type: star.type || 'soft'
            });
          });
        }
        
        // 处理杂耀
        if (palace.adjectiveStars && Array.isArray(palace.adjectiveStars)) {
          palace.adjectiveStars.forEach((star: any) => {
            allStars.push({
              ...star,
              type: star.type || 'adjective'
            });
          });
        }
        
        palace.stars = allStars;
      }
      
      return palace;
    });
  }
  
  console.log('处理后的数据结构:', {
    solarDate: result.solarDate,
    lunarDate: result.lunarDate,
    gender: result.gender,
    palacesLength: result.palaces?.length,
    firstPalaceStars: result.palaces && result.palaces.length > 0 
      ? `${result.palaces[0].stars?.length}颗星` : '无星'
  });
  
  return result;
}

const ZiweiPage: React.FC = () => {
  const [selectedDateTime, setSelectedDateTime] = useState<Dayjs | null>(null);
  const [gender, setGender] = useState<'male' | 'female'>('male');
  const [ziWeiResult, setZiWeiResult] = useState<any>(null);
  const [loading, setLoading] = useState<boolean>(false);

  // 处理API响应数据
  const processApiResponse = (response: any) => {
    console.log('原始API响应:', response);
    return processApiData(response);
  };

  const handleCalculate = async () => {
    if (selectedDateTime) {
      try {
        setLoading(true);
        const result = await calculateZiWei(
        selectedDateTime.year(),
        selectedDateTime.month() + 1,
        selectedDateTime.date(),
        selectedDateTime.hour(),
        gender
      );
        // 处理API返回的数据格式
        const processedResult = processApiResponse(result);
        setZiWeiResult(processedResult);
      } catch (error) {
        console.error('计算紫微斗数出错:', error);
        message.error('计算紫微斗数失败，请重试');
      } finally {
        setLoading(false);
      }
    } else {
      message.warning('请选择日期和时间');
    }
  };

  // 可以直接使用API返回的JSON数据进行测试
  const handleTestData = () => {
    try {
      console.log('开始解析测试数据...');
      // 解析JSON字符串为对象
      const testJson = `{"status":"ok","message":"计算成功","timestamp":"2025-04-21T01:32:35.933984","result":{"gender":"男","solarDate":"1994-12-8","lunarDate":"一九九四年冬月初六","chineseDate":"甲戌 丙子 戊辰 丁巳","time":"巳时","timeRange":"09:00~11:00","sign":"射手座","zodiac":"狗","earthlyBranchOfSoulPalace":"未","earthlyBranchOfBodyPalace":"巳","soul":"武曲","body":"文昌","fiveElementsClass":"土五局","palaces":[{"index":0,"name":"疾厄","isBodyPalace":false,"isOriginalPalace":false,"heavenlyStem":"丙","earthlyBranch":"寅","majorStars":[{"name":"天同","type":"major","scope":"origin","brightness":"利","mutagen":""},{"name":"天梁","type":"major","scope":"origin","brightness":"庙","mutagen":""},{"name":"禄存","type":"lucun","scope":"origin","brightness":"","mutagen":null}],"minorStars":[{"name":"左辅","type":"soft","scope":"origin","brightness":"","mutagen":""}],"adjectiveStars":[{"name":"龙池","type":"adjective","scope":"origin","brightness":null,"mutagen":null},{"name":"天巫","type":"adjective","scope":"origin","brightness":null,"mutagen":null},{"name":"天使","type":"adjective","scope":"origin","brightness":null,"mutagen":null}],"changsheng12":"病","boshi12":"博士","jiangqian12":"指背","suiqian12":"官符","decadal":{"range":[75,84],"heavenlyStem":"丙","earthlyBranch":"寅"},"ages":[11,23,35,47,59,71,83,95,107,119]},{"index":1,"name":"财帛","isBodyPalace":false,"isOriginalPalace":false,"heavenlyStem":"丁","earthlyBranch":"卯","majorStars":[{"name":"武曲","type":"major","scope":"origin","brightness":"利","mutagen":"科"},{"name":"七杀","type":"major","scope":"origin","brightness":"旺","mutagen":""}],"minorStars":[{"name":"擎羊","type":"tough","scope":"origin","brightness":"陷","mutagen":null}],"adjectiveStars":[{"name":"咸池","type":"flower","scope":"origin","brightness":null,"mutagen":null},{"name":"天寿","type":"adjective","scope":"origin","brightness":null,"mutagen":null},{"name":"月德","type":"adjective","scope":"origin","brightness":null,"mutagen":null}],"changsheng12":"死","boshi12":"力士","jiangqian12":"咸池","suiqian12":"小耗","decadal":{"range":[85,94],"heavenlyStem":"丁","earthlyBranch":"卯"},"ages":[12,24,36,48,60,72,84,96,108,120]},{"index":2,"name":"子女","isBodyPalace":false,"isOriginalPalace":false,"heavenlyStem":"戊","earthlyBranch":"辰","majorStars":[{"name":"太阳","type":"major","scope":"origin","brightness":"旺","mutagen":"忌"}],"minorStars":[{"name":"地劫","type":"tough","scope":"origin","brightness":"","mutagen":null}],"adjectiveStars":[{"name":"天虚","type":"adjective","scope":"origin","brightness":null,"mutagen":null}],"changsheng12":"墓","boshi12":"青龙","jiangqian12":"月煞","suiqian12":"大耗","decadal":{"range":[95,104],"heavenlyStem":"戊","earthlyBranch":"辰"},"ages":[1,13,25,37,49,61,73,85,97,109]},{"index":3,"name":"夫妻","isBodyPalace":true,"isOriginalPalace":false,"heavenlyStem":"己","earthlyBranch":"巳","majorStars":[],"minorStars":[{"name":"文昌","type":"soft","scope":"origin","brightness":"庙","mutagen":""}],"adjectiveStars":[{"name":"红鸾","type":"flower","scope":"origin","brightness":null,"mutagen":null},{"name":"天才","type":"adjective","scope":"origin","brightness":null,"mutagen":null},{"name":"天厨","type":"adjective","scope":"origin","brightness":null,"mutagen":null}],"changsheng12":"绝","boshi12":"小耗","jiangqian12":"亡神","suiqian12":"龙德","decadal":{"range":[105,114],"heavenlyStem":"己","earthlyBranch":"巳"},"ages":[2,14,26,38,50,62,74,86,98,110]},{"index":4,"name":"兄弟","isBodyPalace":false,"isOriginalPalace":false,"heavenlyStem":"庚","earthlyBranch":"午","majorStars":[{"name":"天机","type":"major","scope":"origin","brightness":"庙","mutagen":""}],"minorStars":[{"name":"地空","type":"tough","scope":"origin","brightness":"","mutagen":null},{"name":"火星","type":"tough","scope":"origin","brightness":"庙","mutagen":null}],"adjectiveStars":[{"name":"解神","type":"helper","scope":"origin","brightness":null,"mutagen":null},{"name":"阴煞","type":"adjective","scope":"origin","brightness":null,"mutagen":null}],"changsheng12":"胎","boshi12":"将军","jiangqian12":"将星","suiqian12":"白虎","decadal":{"range":[115,124],"heavenlyStem":"庚","earthlyBranch":"午"},"ages":[3,15,27,39,51,63,75,87,99,111]},{"index":5,"name":"命宫","isBodyPalace":false,"isOriginalPalace":false,"heavenlyStem":"辛","earthlyBranch":"未","majorStars":[{"name":"紫微","type":"major","scope":"origin","brightness":"庙","mutagen":""},{"name":"破军","type":"major","scope":"origin","brightness":"旺","mutagen":"权"}],"minorStars":[{"name":"天钺","type":"soft","scope":"origin","brightness":"","mutagen":null}],"adjectiveStars":[{"name":"三台","type":"adjective","scope":"origin","brightness":null,"mutagen":null},{"name":"八座","type":"adjective","scope":"origin","brightness":null,"mutagen":null},{"name":"封诰","type":"adjective","scope":"origin","brightness":null,"mutagen":null},{"name":"天官","type":"adjective","scope":"origin","brightness":null,"mutagen":null},{"name":"天德","type":"adjective","scope":"origin","brightness":null,"mutagen":null},{"name":"寡宿","type":"adjective","scope":"origin","brightness":null,"mutagen":null},{"name":"天刑","type":"adjective","scope":"origin","brightness":null,"mutagen":null}],"changsheng12":"养","boshi12":"奏书","jiangqian12":"攀鞍","suiqian12":"天德","decadal":{"range":[5,14],"heavenlyStem":"辛","earthlyBranch":"未"},"ages":[4,16,28,40,52,64,76,88,100,112]},{"index":6,"name":"父母","isBodyPalace":false,"isOriginalPalace":false,"heavenlyStem":"壬","earthlyBranch":"申","majorStars":[{"name":"天马","type":"tianma","scope":"origin","brightness":"","mutagen":null}],"minorStars":[{"name":"铃星","type":"tough","scope":"origin","brightness":"陷","mutagen":null}],"adjectiveStars":[{"name":"旬空","type":"adjective","scope":"origin","brightness":null,"mutagen":null},{"name":"截空","type":"adjective","scope":"origin","brightness":null,"mutagen":null},{"name":"天哭","type":"adjective","scope":"origin","brightness":null,"mutagen":null}],"changsheng12":"长生","boshi12":"飞廉","jiangqian12":"岁驿","suiqian12":"吊客","decadal":{"range":[15,24],"heavenlyStem":"壬","earthlyBranch":"申"},"ages":[5,17,29,41,53,65,77,89,101,113]},{"index":7,"name":"福德","isBodyPalace":false,"isOriginalPalace":false,"heavenlyStem":"癸","earthlyBranch":"酉","majorStars":[{"name":"天府","type":"major","scope":"origin","brightness":"旺","mutagen":""}],"minorStars":[{"name":"文曲","type":"soft","scope":"origin","brightness":"庙","mutagen":""}],"adjectiveStars":[{"name":"恩光","type":"adjective","scope":"origin","brightness":null,"mutagen":null},{"name":"天福","type":"adjective","scope":"origin","brightness":null,"mutagen":null},{"name":"空亡","type":"adjective","scope":"origin","brightness":null,"mutagen":null}],"changsheng12":"沐浴","boshi12":"喜神","jiangqian12":"息神","suiqian12":"病符","decadal":{"range":[25,34],"heavenlyStem":"癸","earthlyBranch":"酉"},"ages":[6,18,30,42,54,66,78,90,102,114]},{"index":8,"name":"田宅","isBodyPalace":false,"isOriginalPalace":true,"heavenlyStem":"甲","earthlyBranch":"戌","majorStars":[{"name":"太阴","type":"major","scope":"origin","brightness":"旺","mutagen":""}],"minorStars":[],"adjectiveStars":[{"name":"华盖","type":"adjective","scope":"origin","brightness":null,"mutagen":null},{"name":"天月","type":"adjective","scope":"origin","brightness":null,"mutagen":null}],"changsheng12":"冠带","boshi12":"病符","jiangqian12":"华盖","suiqian12":"岁建","decadal":{"range":[35,44],"heavenlyStem":"甲","earthlyBranch":"戌"},"ages":[7,19,31,43,55,67,79,91,103,115]},{"index":9,"name":"官禄","isBodyPalace":false,"isOriginalPalace":false,"heavenlyStem":"乙","earthlyBranch":"亥","majorStars":[{"name":"廉贞","type":"major","scope":"origin","brightness":"陷","mutagen":"禄"},{"name":"贪狼","type":"major","scope":"origin","brightness":"陷","mutagen":""}],"minorStars":[],"adjectiveStars":[{"name":"天喜","type":"flower","scope":"origin","brightness":null,"mutagen":null},{"name":"天姚","type":"flower","scope":"origin","brightness":null,"mutagen":null},{"name":"台辅","type":"adjective","scope":"origin","brightness":null,"mutagen":null},{"name":"天空","type":"adjective","scope":"origin","brightness":null,"mutagen":null},{"name":"孤辰","type":"adjective","scope":"origin","brightness":null,"mutagen":null}],"changsheng12":"临官","boshi12":"大耗","jiangqian12":"劫煞","suiqian12":"晦气","decadal":{"range":[45,54],"heavenlyStem":"乙","earthlyBranch":"亥"},"ages":[8,20,32,44,56,68,80,92,104,116]},{"index":10,"name":"仆役","isBodyPalace":false,"isOriginalPalace":false,"heavenlyStem":"丙","earthlyBranch":"子","majorStars":[{"name":"巨门","type":"major","scope":"origin","brightness":"旺","mutagen":""}],"minorStars":[{"name":"右弼","type":"soft","scope":"origin","brightness":"","mutagen":""}],"adjectiveStars":[{"name":"凤阁","type":"adjective","scope":"origin","brightness":null,"mutagen":null},{"name":"蜚廉","type":"adjective","scope":"origin","brightness":null,"mutagen":null},{"name":"天伤","type":"adjective","scope":"origin","brightness":null,"mutagen":null}],"changsheng12":"帝旺","boshi12":"伏兵","jiangqian12":"灾煞","suiqian12":"丧门","decadal":{"range":[55,64],"heavenlyStem":"丙","earthlyBranch":"子"},"ages":[9,21,33,45,57,69,81,93,105,117]},{"index":11,"name":"迁移","isBodyPalace":false,"isOriginalPalace":false,"heavenlyStem":"丁","earthlyBranch":"丑","majorStars":[{"name":"天相","type":"major","scope":"origin","brightness":"庙","mutagen":""}],"minorStars":[{"name":"天魁","type":"soft","scope":"origin","brightness":"","mutagen":null},{"name":"陀罗","type":"tough","scope":"origin","brightness":"庙","mutagen":null}],"adjectiveStars":[{"name":"天贵","type":"adjective","scope":"origin","brightness":null,"mutagen":null},{"name":"破碎","type":"adjective","scope":"origin","brightness":null,"mutagen":null}],"changsheng12":"衰","boshi12":"官府","jiangqian12":"天煞","suiqian12":"贯索","decadal":{"range":[65,74],"heavenlyStem":"丁","earthlyBranch":"丑"},"ages":[10,22,34,46,58,70,82,94,106,118]}]},"error":null}`;
      
      const testData = JSON.parse(testJson);
      console.log('JSON解析完成，测试数据结构:', testData);
      
      const processedResult = processApiResponse(testData);
      setZiWeiResult(processedResult);
    } catch (error) {
      console.error('测试数据解析错误:', error);
      message.error('测试数据格式错误');
    }
  };

  return (
    <PageContainer>
      <Card bordered={false}>
        <Space direction="vertical" size="middle" style={{ width: '100%' }}>
          <div className="header">
            <DatePicker 
              showTime
              style={{ width: 200 }} 
              onChange={setSelectedDateTime}
              value={selectedDateTime}
              placeholder="选择日期和时间"
              format="YYYY-MM-DD HH:mm"
            />
            <Radio.Group 
              value={gender} 
              onChange={(e) => setGender(e.target.value)}
              optionType="button"
              buttonStyle="solid"
            >
              <Radio.Button value="male">男命</Radio.Button>
              <Radio.Button value="female">女命</Radio.Button>
            </Radio.Group>
            <Button type="primary" onClick={handleCalculate} loading={loading}>
              计算
            </Button>
            <Button onClick={handleTestData} style={{ marginLeft: 8 }}>
              测试数据
            </Button>
          </div>

          {loading ? (
            <div style={{ textAlign: 'center', padding: '40px 0' }}>
              <Spin tip="正在计算命盘..." />
            </div>
          ) : ziWeiResult ? (
            <ZiWeiChart 
              data={ziWeiResult} 
              onTimeChange={async (params) => {
                try {
                  setLoading(true);
                  // 转换参数格式以匹配服务函数需要的类型
                  const year = params.year;
                  const month = params.month;
                  const day = params.day;
                  const hour = params.hour;
                  
                  // 调用紫微斗数运限计算接口
                  const response = await calculateZiWei(
                    year ?? selectedDateTime!.year(),
                    month ?? (selectedDateTime!.month() + 1),
                    day ?? selectedDateTime!.date(),
                    hour ?? selectedDateTime!.hour(),
                    gender,
                    params.decadal ? { decadal: params.decadal } : undefined
                  );
                  
                  // 处理API返回的数据格式
                  const processedResult = processApiResponse(response);
                  setZiWeiResult(processedResult);
                } catch (error) {
                  console.error('计算运限失败:', error);
                  message.error('计算运限失败');
                } finally {
                  setLoading(false);
                }
              }}
            />
          ) : null}
        </Space>
      </Card>
    </PageContainer>
  );
};

export default ZiweiPage; 