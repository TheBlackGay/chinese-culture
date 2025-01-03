'use client';

import { useState, useEffect } from 'react';
import { Layout, Menu, Button, Dropdown, Avatar, Breadcrumb, theme } from 'antd';
import {
  MenuFoldOutlined,
  MenuUnfoldOutlined,
  DashboardOutlined,
  FileTextOutlined,
  SettingOutlined,
  UserOutlined,
  LogoutOutlined,
  ToolOutlined,
  BellOutlined
} from '@ant-design/icons';
import { usePathname, useRouter } from 'next/navigation';
import Image from 'next/image';

const { Header, Sider, Content } = Layout;
const { useToken } = theme;

interface BasicLayoutProps {
  children: React.ReactNode;
}

export default function BasicLayout({ children }: BasicLayoutProps) {
  const [collapsed, setCollapsed] = useState(false);
  const [breadcrumbs, setBreadcrumbs] = useState<{ label: string; path: string }[]>([]);
  const pathname = usePathname();
  const router = useRouter();
  const { token } = useToken();

  const menuItems = [
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
      icon: <ToolOutlined />,
      label: '文化工具',
      children: [
        {
          key: '/tools/iching',
          label: '周易卦象',
        },
        {
          key: '/tools/bazi',
          label: '生辰八字',
        },
        {
          key: '/tools/lucky-days',
          label: '黄道吉日',
        },
        {
          key: '/tools/five-elements',
          label: '五行属性',
        },
        {
          key: '/tools/zodiac',
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

  const userMenuItems = [
    {
      key: 'profile',
      icon: <UserOutlined />,
      label: '个人信息',
      type: 'item'
    },
    {
      key: 'divider',
      type: 'divider'
    },
    {
      key: 'logout',
      icon: <LogoutOutlined />,
      label: '退出登录',
      type: 'item',
      danger: true
    },
  ];

  useEffect(() => {
    // 根据当前路径更新面包屑
    const generateBreadcrumbs = () => {
      const paths = pathname.split('/').filter(Boolean);
      const items = paths.map((path, index) => {
        const url = `/${paths.slice(0, index + 1).join('/')}`;
        const menuItem = menuItems.find(item => item.key === url) || 
                        menuItems.flatMap(item => item.children || []).find(child => child?.key === url);
        return {
          label: menuItem?.label || path,
          path: url,
        };
      });
      setBreadcrumbs([{ label: '首页', path: '/dashboard' }, ...items]);
    };
    generateBreadcrumbs();
  }, [pathname]);

  const handleMenuClick = ({ key }: { key: string }) => {
    router.push(key);
  };

  const handleUserMenuClick = ({ key }: { key: string }) => {
    if (key === 'logout') {
      // 处理退出登录
      router.push('/login');
    } else if (key === 'profile') {
      router.push('/profile');
    }
  };

  return (
    <Layout style={{ minHeight: '100vh' }}>
      <Sider 
        trigger={null} 
        collapsible 
        collapsed={collapsed}
        style={{
          boxShadow: '2px 0 8px 0 rgba(29,35,41,.05)',
          background: token.colorBgContainer,
        }}
        width={260}
      >
        <div className="flex items-center justify-center p-4 h-16 border-b border-[#f0f0f0]">
          {!collapsed && (
            <div className="text-lg font-semibold text-[#1890ff] flex items-center">
              <Image src="/logo.png" alt="Logo" width={32} height={32} className="mr-2" />
              中国文化管理
            </div>
          )}
          {collapsed && <Image src="/logo.png" alt="Logo" width={32} height={32} />}
        </div>
        <Menu
          mode="inline"
          selectedKeys={[pathname]}
          defaultOpenKeys={['/content', '/tools']}
          items={menuItems}
          onClick={handleMenuClick}
          style={{ 
            border: 'none',
            height: 'calc(100vh - 64px)',
            overflowY: 'auto',
          }}
        />
      </Sider>
      <Layout>
        <Header 
          style={{ 
            padding: '0 24px',
            background: token.colorBgContainer,
            boxShadow: '0 1px 4px rgba(0,21,41,.08)',
            height: '64px',
            position: 'sticky',
            top: 0,
            zIndex: 100,
          }}
        >
          <div className="flex justify-between items-center h-full">
            <div className="flex items-center">
              <Button
                type="text"
                icon={collapsed ? <MenuUnfoldOutlined /> : <MenuFoldOutlined />}
                onClick={() => setCollapsed(!collapsed)}
                style={{
                  fontSize: '16px',
                  width: 64,
                  height: 64,
                }}
              />
              <Breadcrumb items={breadcrumbs.map(item => ({ title: item.label }))} />
            </div>
            <div className="flex items-center gap-4">
              <Button 
                type="text" 
                icon={<BellOutlined />}
                className="flex items-center justify-center"
                style={{ width: 40, height: 40 }}
              />
              <Dropdown
                menu={{
                  items: userMenuItems,
                  onClick: handleUserMenuClick,
                }}
                placement="bottomRight"
              >
                <div className="flex items-center cursor-pointer">
                  <Avatar 
                    icon={<UserOutlined />} 
                    style={{ backgroundColor: token.colorPrimary }}
                  />
                  <span className="ml-2">管理员</span>
                </div>
              </Dropdown>
            </div>
          </div>
        </Header>
        <Content 
          style={{ 
            margin: '24px',
            padding: '24px',
            background: token.colorBgContainer,
            borderRadius: token.borderRadius,
            minHeight: 280,
          }}
        >
          {children}
        </Content>
      </Layout>
    </Layout>
  );
} 