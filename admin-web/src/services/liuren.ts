import { request } from '@umijs/max';

/** 获取大六壬预测结果 */
export async function getLiurenResult(params: {
  date: string;
  time: string;
  question: string;
}) {
  return request('/api/cultural/liuren/predict', {
    method: 'POST',
    data: params,
  });
}

/** 获取大六壬时辰信息 */
export async function getLiurenTimeInfo() {
  return request('/api/cultural/liuren/timeInfo', {
    method: 'GET',
  });
} 
