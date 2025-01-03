'use client';

import React, { useState } from 'react';
import { Layout, Menu, Button, Dropdown } from 'antd';
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
      key: '/dashboard',
      icon: <DashboardOutlined />,
      label: '仪表盘',
    },
    {
      key: '/content',
      icon: <FileTextOutlined />,
      label: '内容管理',
      children: [
        {
          key: '/content/articles',
          label: '文章管理',
        },
        {
          key: '/content/categories',
          label: '分类管理',
        },
        {
          key: '/content/tags',
          label: '标签管理',
        },
      ],
    },
    {
      key: '/tools',
      icon: <CompassOutlined />,
      label: '文化工具',
      children: [
        {
          key: '/tools/bazi',
          icon: <CalendarOutlined />,
          label: '生辰八字',
        },
        {
          key: '/tools/lucky-days',
          icon: <CalendarOutlined />,
          label: '黄道吉日',
        },
        {
          key: '/tools/five-elements',
          icon: <FireOutlined />,
          label: '五行属性',
        },
        {
          key: '/tools/zodiac',
          icon: <ZhihuOutlined />,
          label: '生肖信息',
        },
      ],
    },
    {
      key: '/settings',
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
    router.push(key);
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
          selectedKeys={[pathname]}
          defaultOpenKeys={['/content', '/tools']}
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
          {children}
        </Content>
      </Layout>
    </Layout>
  );
} 