/**
 * @file 基础组件类型定义
 */

import type { ReactNode } from 'react';

// 基础 Props 类型
export interface BaseProps {
  className?: string;
  style?: React.CSSProperties;
  children?: ReactNode;
}

// 带数据的组件 Props 类型
export interface DataComponentProps<T = any> extends BaseProps {
  data?: T;
  loading?: boolean;
  error?: Error | null;
}

// 表单项 Props 类型
export interface FormItemProps extends BaseProps {
  label?: string;
  required?: boolean;
  error?: string;
  help?: string;
}

// 按钮 Props 类型
export interface ButtonProps extends BaseProps {
  type?: 'primary' | 'default' | 'dashed' | 'link' | 'text';
  size?: 'large' | 'middle' | 'small';
  loading?: boolean;
  disabled?: boolean;
  onClick?: (e: React.MouseEvent<HTMLButtonElement>) => void;
}

// 图标 Props 类型
export interface IconProps extends BaseProps {
  name: string;
  size?: number | string;
  color?: string;
  spin?: boolean;
}

// 模态框 Props 类型
export interface ModalProps extends BaseProps {
  visible: boolean;
  title?: string;
  width?: number | string;
  centered?: boolean;
  closable?: boolean;
  maskClosable?: boolean;
  onOk?: () => void;
  onCancel?: () => void;
  footer?: ReactNode | null;
}

// 表格 Props 类型
export interface TableProps<T = any> extends BaseProps {
  dataSource?: T[];
  columns?: TableColumn<T>[];
  loading?: boolean;
  pagination?: TablePagination;
  rowKey?: string | ((record: T) => string);
  onChange?: (pagination: TablePagination, filters: any, sorter: any) => void;
}

export interface TableColumn<T = any> {
  title: string;
  dataIndex: keyof T | string;
  key?: string;
  width?: number | string;
  fixed?: 'left' | 'right';
  align?: 'left' | 'center' | 'right';
  render?: (value: any, record: T, index: number) => ReactNode;
}

export interface TablePagination {
  current: number;
  pageSize: number;
  total: number;
} 