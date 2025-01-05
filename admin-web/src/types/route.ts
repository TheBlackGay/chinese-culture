/**
 * @file 路由相关类型定义
 */

import { ReactNode } from 'react';

// 路由项配置
export interface RouteItem {
  path: string;
  element?: ReactNode;
  children?: RouteItem[];
  meta?: RouteMeta;
  redirect?: string;
}

// 路由元数据
export interface RouteMeta {
  title: string;
  icon?: ReactNode;
  permission?: string[];
  hideInMenu?: boolean;
  hideInBreadcrumb?: boolean;
  hideChildrenInMenu?: boolean;
  target?: '_blank' | '_self' | '_parent' | '_top';
  keepAlive?: boolean;
}

// 面包屑项
export interface BreadcrumbItem {
  path: string;
  title: string;
  icon?: ReactNode;
}

// 菜单项
export interface MenuItem {
  key: string;
  icon?: ReactNode;
  label: string;
  children?: MenuItem[];
  type?: 'group';
  path?: string;
  target?: string;
  permission?: string[];
  hideInMenu?: boolean;
} 