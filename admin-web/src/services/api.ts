import axios from 'axios';

const API_BASE_URL = 'http://localhost:8000/api';

/**
 * 从后端获取紫微斗数命盘数据
 * @param solarDate 阳历日期 格式：YYYY-MM-DD
 * @param timeIndex 时辰索引(0-11)
 * @param gender 性别 '男' 或 '女'
 * @param fixLeap 是否修正闰月
 * @param language 语言
 * @returns 紫微斗数星盘数据
 */
export const fetchAstroData = async (
  solarDate: string,
  timeIndex: number,
  gender: '男' | '女',
  fixLeap: boolean = true,
  language: string = 'zh-CN'
) => {
  try {
    const response = await axios.post(`${API_BASE_URL}/astro/by_solar`, {
      solar_date: solarDate,
      time_index: timeIndex,
      gender,
      fix_leap: fixLeap,
      language
    });

    if (response.data.status === 'ok') {
      return response.data.result;
    } else {
      throw new Error(response.data.message || '获取紫微斗数数据失败');
    }
  } catch (error) {
    console.error('获取紫微斗数数据出错:', error);
    throw error;
  }
};

/**
 * 从后端获取紫微斗数运限数据
 * @param solarDate 出生阳历日期
 * @param timeIndex 出生时辰索引
 * @param gender 性别
 * @param horoscopeDate 运限日期
 * @param horoscopeTimeIndex 运限时辰
 * @returns 运限数据
 */
export const fetchHoroscopeData = async (
  solarDate: string,
  timeIndex: number,
  gender: '男' | '女',
  horoscopeDate: string,
  horoscopeTimeIndex: number = 6
) => {
  try {
    const response = await axios.post(`${API_BASE_URL}/astro/horoscope`, {
      solar_date: solarDate,
      time_index: timeIndex,
      gender,
      horoscope_date: horoscopeDate,
      horoscope_time_index: horoscopeTimeIndex
    });

    if (response.data.status === 'ok') {
      return response.data.result;
    } else {
      throw new Error(response.data.message || '获取紫微斗数运限数据失败');
    }
  } catch (error) {
    console.error('获取紫微斗数运限数据出错:', error);
    throw error;
  }
}; 