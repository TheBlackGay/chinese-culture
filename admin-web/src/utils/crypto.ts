/**
 * @file 加密工具
 */

import CryptoJS from 'crypto-js';

const SECRET_KEY = process.env.NEXT_PUBLIC_CRYPTO_KEY || 'default-key';

/**
 * AES 加密
 * @param data 待加密数据
 */
export function encrypt(data: string): string {
  return CryptoJS.AES.encrypt(data, SECRET_KEY).toString();
}

/**
 * AES 解密
 * @param ciphertext 密文
 */
export function decrypt(ciphertext: string): string {
  const bytes = CryptoJS.AES.decrypt(ciphertext, SECRET_KEY);
  return bytes.toString(CryptoJS.enc.Utf8);
}

/**
 * MD5 加密
 * @param data 待加密数据
 */
export function md5(data: string): string {
  return CryptoJS.MD5(data).toString();
}

/**
 * Base64 编码
 * @param data 待编码数据
 */
export function encodeBase64(data: string): string {
  return CryptoJS.enc.Base64.stringify(CryptoJS.enc.Utf8.parse(data));
}

/**
 * Base64 解码
 * @param data 待解码数据
 */
export function decodeBase64(data: string): string {
  return CryptoJS.enc.Base64.parse(data).toString(CryptoJS.enc.Utf8);
}

/**
 * SHA256 加密
 * @param data 待加密数据
 */
export function sha256(data: string): string {
  return CryptoJS.SHA256(data).toString();
}

/**
 * 生成随机字符串
 * @param length 长度
 */
export function generateRandomString(length: number): string {
  const chars = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
  let result = '';
  for (let i = 0; i < length; i++) {
    result += chars.charAt(Math.floor(Math.random() * chars.length));
  }
  return result;
} 