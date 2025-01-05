/** @type {import('next').NextConfig} */
const nextConfig = {
  reactStrictMode: true,
  sassOptions: {
    additionalData: `@import "@/styles/_variables.scss"; @import "@/styles/_mixins.scss";`,
  },
  images: {
    domains: ['localhost'], // 配置图片域名白名单
  },
  // 配置环境变量
  env: {
    APP_ENV: process.env.APP_ENV || 'development',
  },
  // 配置构建输出
  output: 'standalone',
  // 配置 webpack
  webpack: (config) => {
    // 添加自定义配置
    return config;
  },
};

module.exports = nextConfig; 