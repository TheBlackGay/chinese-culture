// 用户相关类型
export interface User {
  id: number;
  username: string;
  email: string;
  role: string;
  avatar?: string;
  createdAt: string;
  updatedAt: string;
}

// 响应数据类型
export interface ApiResponse<T> {
  code: number;
  message: string;
  data: T;
}

// 分页请求参数
export interface PaginationParams {
  page: number;
  pageSize: number;
  sortField?: string;
  sortOrder?: 'ascend' | 'descend';
}

// 分页响应数据
export interface PaginationData<T> {
  list: T[];
  total: number;
  page: number;
  pageSize: number;
}

// 路由元数据
export interface RouteMetadata {
  title: string;
  icon?: string;
  permission?: string[];
  hideInMenu?: boolean;
} 