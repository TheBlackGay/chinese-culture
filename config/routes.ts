export default [
  { path: '/', redirect: '/home' },
  { path: '/home', component: './Home' },
  {
    path: '/tools',
    name: '文化工具',
    icon: 'ToolOutlined',
    routes: [
      {
        path: '/tools/bazi',
        name: '生辰八字',
        component: './Tools/BaZi',
      },
    ],
  },
  {
    path: '/content',
    name: '内容管理',
    icon: 'FileTextOutlined',
    routes: [
      {
        path: '/content/articles',
        name: '文章管理',
        component: './Content/Articles',
      },
    ],
  },
  {
    path: '/system',
    name: '系统管理',
    icon: 'SettingOutlined',
    routes: [
      {
        path: '/system/users',
        name: '用户管理',
        component: './System/Users',
      },
      {
        path: '/system/settings',
        name: '系统设置',
        component: './System/Settings',
      },
    ],
  },
]; 