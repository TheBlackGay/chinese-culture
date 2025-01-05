import { defineConfig } from '@umijs/max';

export default defineConfig({
  antd: {
    dark: true,
    configProvider: {
      theme: {
        token: {
          colorBgContainer: '#141414',
          colorBgElevated: '#1f1f1f',
          colorBgLayout: '#000000',
          colorBgBase: '#000000',
        },
      },
    },
  },
  access: {},
  model: {},
  initialState: {},
  request: {},
  layout: {
    title: '中国传统文化管理系统',
  },
  routes: [
    {
      path: '/',
      redirect: '/home',
    },
    {
      path: '/home',
      name: '首页',
      icon: 'home',
      component: './Home',
    },
    {
      path: '/cultural-tools',
      name: '文化工具',
      icon: 'tool',
      routes: [
        {
          path: '/cultural-tools/bazi',
          name: '八字计算',
          component: './CulturalTools/Bazi',
        },
      ],
    },
  ],
  npmClient: 'pnpm',
});

