/**
 * @file 验证工具
 */

// 手机号验证
export const isPhone = (value: string): boolean => {
  return /^1[3-9]\d{9}$/.test(value);
};

// 邮箱验证
export const isEmail = (value: string): boolean => {
  return /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/.test(value);
};

// 密码强度验证（至少8位，包含大小写字母和数字）
export const isStrongPassword = (value: string): boolean => {
  return /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,}$/.test(value);
};

// URL验证
export const isUrl = (value: string): boolean => {
  try {
    new URL(value);
    return true;
  } catch {
    return false;
  }
};

// 身份证号验证
export const isIdCard = (value: string): boolean => {
  return /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/.test(value);
};

// 中文姓名验证
export const isChineseName = (value: string): boolean => {
  return /^[\u4e00-\u9fa5]{2,}$/.test(value);
};

// 数字验证
export const isNumber = (value: string): boolean => {
  return !isNaN(Number(value));
};

// 整数验证
export const isInteger = (value: string): boolean => {
  return /^-?\d+$/.test(value);
};

// 正整数验证
export const isPositiveInteger = (value: string): boolean => {
  return /^[1-9]\d*$/.test(value);
};

// 金额验证（两位小数）
export const isMoney = (value: string): boolean => {
  return /^-?\d+(\.\d{1,2})?$/.test(value);
};

// IP地址验证
export const isIp = (value: string): boolean => {
  return /^(\d{1,3}\.){3}\d{1,3}$/.test(value);
};

// 端口号验证
export const isPort = (value: string): boolean => {
  const port = Number(value);
  return port >= 0 && port <= 65535;
};

// MAC地址验证
export const isMac = (value: string): boolean => {
  return /^([0-9A-Fa-f]{2}[:-]){5}([0-9A-Fa-f]{2})$/.test(value);
}; 