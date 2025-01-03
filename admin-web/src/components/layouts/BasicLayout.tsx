'use client';

import React, { useState, Suspense } from 'react';
import { Layout, Menu, Button, Dropdown, Spin } from 'antd';
import {
  MenuFoldOutlined,
  MenuUnfoldOutlined,
  DashboardOutlined,
  FileTextOutlined,
  SettingOutlined,
  UserOutlined,
  CalendarOutlined,
  CompassOutlined,
  FireOutlined,
  ZhihuOutlined,
  FontSizeOutlined,
  BookOutlined,
  ScheduleOutlined,
  DeploymentUnitOutlined,
  ToolOutlined,
} from '@ant-design/icons';
import { usePathname, useRouter } from 'next/navigation';
import type { MenuProps } from 'antd';

const { Header, Sider, Content } = Layout;

interface BasicLayoutProps {
  children: React.ReactNode;
}

export default function BasicLayout({ children }: BasicLayoutProps) {
  const [collapsed, setCollapsed] = useState(false);
  const router = useRouter();
  const pathname = usePathname();

  const menuItems: MenuProps['items'] = [
    {
      key: 'dashboard',
      icon: <DashboardOutlined />,
      label: '仪表盘',
    },
    {
      key: 'tools',
      icon: <ToolOutlined />,
      label: '文化工具',
      children: [
        {
          key: 'tools/bazi',
          label: '生辰八字',
        },
        {
          key: 'tools/lucky-days',
          label: '黄道吉日',
        },
        {
          key: 'tools/five-elements',
          label: '五行属性',
        },
        {
          key: 'tools/zodiac',
          label: '生肖信息',
        },
        {
          key: 'tools/hexagram',
          label: '周易卦象',
        },
        {
          key: 'tools/festivals',
          label: '传统节日',
        },
      ],
    },
    {
      key: 'content',
      icon: <FileTextOutlined />,
      label: '内容管理',
    },
    {
      key: 'settings',
      icon: <SettingOutlined />,
      label: '系统设置',
    },
  ];

  const userMenuItems: MenuProps['items'] = [
    {
      key: 'profile',
      label: '个人信息',
    },
    {
      key: 'logout',
      label: '退出登录',
    },
  ];

  const handleMenuClick = ({ key }: { key: string }) => {
    const path = key.startsWith('/') ? key : `/${key}`;
    router.push(path);
  };

  const handleUserMenuClick = ({ key }: { key: string }) => {
    if (key === 'logout') {
      // 处理退出登录
      console.log('logout');
    } else if (key === 'profile') {
      router.push('/profile');
    }
  };

  return (
    <Layout style={{ minHeight: '100vh' }}>
      <Sider trigger={null} collapsible collapsed={collapsed}>
        <div className="p-4 text-white text-xl font-bold">
          {collapsed ? '文化' : '中国文化'}
        </div>
        <Menu
          theme="dark"
          mode="inline"
          selectedKeys={[pathname.slice(1)]}
          defaultOpenKeys={['content', 'tools']}
          items={menuItems}
          onClick={handleMenuClick}
        />
      </Sider>
      <Layout>
        <Header className="bg-white px-4 flex justify-between items-center">
          <Button
            type="text"
            icon={collapsed ? <MenuUnfoldOutlined /> : <MenuFoldOutlined />}
            onClick={() => setCollapsed(!collapsed)}
          />
          <Dropdown menu={{ items: userMenuItems, onClick: handleUserMenuClick }}>
            <Button type="text" icon={<UserOutlined />}>
              管理员
            </Button>
          </Dropdown>
        </Header>
        <Content className="m-6 min-h-[280px] bg-white p-6 rounded-lg">
          <Suspense fallback={
            <div className="flex justify-center items-center h-[200px]">
              <Spin size="large" tip="加载中..." />
            </div>
          }>
            {children}
          </Suspense>
        </Content>
      </Layout>
    </Layout>
  );
} 