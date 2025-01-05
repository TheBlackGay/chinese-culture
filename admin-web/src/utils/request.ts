/**
 * @file HTTP 请求工具
 * @description 基于 axios 的请求封装，统一处理请求和响应
 */

import axios, { AxiosError, AxiosInstance, AxiosRequestConfig, AxiosResponse } from 'axios';
import { message } from 'antd';
import type { ApiResponse } from '@/types/api';
import { BUSINESS_CODE, HTTP_STATUS } from '@/constants';
import logger from './logger';
import { getStoredToken } from './storage';

/**
 * 创建 axios 实例
 */
const instance: AxiosInstance = axios.create({
  baseURL: process.env.NEXT_PUBLIC_API_BASE_URL,
  timeout: Number(process.env.NEXT_PUBLIC_API_TIMEOUT) || 10000,
  headers: {
    'Content-Type': 'application/json',
  },
});

/**
 * 请求拦截器
 */
instance.interceptors.request.use(
  (config: AxiosRequestConfig) => {
    // 添加 token
    const token = getStoredToken();
    if (token && config.headers) {
      config.headers.Authorization = `Bearer ${token}`;
    }

    // 记录请求日志
    logger.logRequest(config.method?.toUpperCase() || 'UNKNOWN', config.url || '', config.data);

    return config;
  },
  (error: AxiosError) => {
    logger.error('Request Error:', error);
    return Promise.reject(error);
  }
);

/**
 * 响应拦截器
 */
instance.interceptors.response.use(
  (response: AxiosResponse<ApiResponse>) => {
    const { code, message: msg, data } = response.data;

    // 记录响应日志
    logger.logResponse(
      response.config.method?.toUpperCase() || 'UNKNOWN',
      response.config.url || '',
      response.status,
      response.data
    );

    // 业务成功
    if (code === BUSINESS_CODE.SUCCESS) {
      return data;
    }

    // 特定业务错误处理
    switch (code) {
      case BUSINESS_CODE.TOKEN_EXPIRED:
        // Token 过期，跳转登录页
        window.location.href = '/login';
        break;
      case BUSINESS_CODE.NO_PERMISSION:
        // 无权限，跳转 403 页面
        window.location.href = '/403';
        break;
      default:
        message.error(msg || '请求失败');
    }

    return Promise.reject(new Error(msg || '请求失败'));
  },
  (error: AxiosError<ApiResponse>) => {
    // 记录错误日志
    logger.error('Response Error:', error);

    // HTTP 错误处理
    if (error.response) {
      switch (error.response.status) {
        case HTTP_STATUS.UNAUTHORIZED:
          // 未登录或 token 过期
          window.location.href = '/login';
          break;
        case HTTP_STATUS.FORBIDDEN:
          window.location.href = '/403';
          break;
        case HTTP_STATUS.NOT_FOUND:
          window.location.href = '/404';
          break;
        case HTTP_STATUS.INTERNAL_ERROR:
          message.error('服务器错误，请稍后重试');
          break;
        default:
          message.error(error.response.data?.message || '网络错误');
      }
    } else if (error.request) {
      message.error('网络连接失败，请检查网络');
    } else {
      message.error('请求配置错误');
    }

    return Promise.reject(error);
  }
);

/**
 * 请求工具类
 */
class HttpClient {
  /**
   * GET 请求
   * @param url 请求地址
   * @param config 请求配置
   */
  static async get<T = any>(
    url: string,
    config?: AxiosRequestConfig
  ): Promise<T> {
    return instance.get(url, config);
  }

  /**
   * POST 请求
   * @param url 请求地址
   * @param data 请求数据
   * @param config 请求配置
   */
  static async post<T = any>(
    url: string,
    data?: any,
    config?: AxiosRequestConfig
  ): Promise<T> {
    return instance.post(url, data, config);
  }

  /**
   * PUT 请求
   * @param url 请求地址
   * @param data 请求数据
   * @param config 请求配置
   */
  static async put<T = any>(
    url: string,
    data?: any,
    config?: AxiosRequestConfig
  ): Promise<T> {
    return instance.put(url, data, config);
  }

  /**
   * DELETE 请求
   * @param url 请求地址
   * @param config 请求配置
   */
  static async delete<T = any>(
    url: string,
    config?: AxiosRequestConfig
  ): Promise<T> {
    return instance.delete(url, config);
  }

  /**
   * 上传文件
   * @param url 请求地址
   * @param file 文件
   * @param config 请求配置
   */
  static async upload<T = any>(
    url: string,
    file: File,
    config?: AxiosRequestConfig
  ): Promise<T> {
    const formData = new FormData();
    formData.append('file', file);
    return instance.post(url, formData, {
      ...config,
      headers: {
        'Content-Type': 'multipart/form-data',
      },
    });
  }

  /**
   * 下载文件
   * @param url 请求地址
   * @param filename 文件名
   * @param config 请求配置
   */
  static async download(
    url: string,
    filename?: string,
    config?: AxiosRequestConfig
  ): Promise<void> {
    const response = await instance.get(url, {
      ...config,
      responseType: 'blob',
    });

    const blob = new Blob([response.data]);
    const downloadUrl = window.URL.createObjectURL(blob);
    const link = document.createElement('a');
    link.href = downloadUrl;
    link.download = filename || url.split('/').pop() || 'download';
    document.body.appendChild(link);
    link.click();
    document.body.removeChild(link);
    window.URL.revokeObjectURL(downloadUrl);
  }
}

export default HttpClient; 