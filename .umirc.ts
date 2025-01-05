import { defineConfig } from '@umijs/max';
import routes from './config/routes';

export default defineConfig({
  antd: {
    theme: {
      token: {
        colorPrimary: '#00b4ff',
        colorBgContainer: '#1a1a1a',
        colorBgElevated: '#242424',
        colorText: '#ffffff',
        colorTextSecondary: 'rgba(255, 255, 255, 0.65)',
        borderRadius: 8,
        wireframe: false,
      },
    },
  },
  access: {},
  model: {},
  initialState: {},
  request: {},
  layout: {
    title: '中国传统文化管理系统',
    locale: true,
  },
  routes,
  npmClient: 'pnpm',
  tailwindcss: {
    tailwindCssFilePath: './tailwind.css',
  },
});
