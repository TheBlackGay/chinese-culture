/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    './src/pages/**/*.{js,ts,jsx,tsx,mdx}',
    './src/components/**/*.{js,ts,jsx,tsx,mdx}',
    './src/app/**/*.{js,ts,jsx,tsx,mdx}',
  ],
  important: true, // 确保 Tailwind 样式优先级高于 Ant Design
  theme: {
    extend: {
      colors: {
        primary: 'var(--primary-color)',
        success: 'var(--success-color)',
        warning: 'var(--warning-color)',
        error: 'var(--error-color)',
        info: 'var(--info-color)',
      },
      backgroundColor: {
        base: 'var(--bg-color)',
        component: 'var(--component-bg)',
        hover: 'var(--hover-bg)',
      },
      textColor: {
        base: 'var(--text-color)',
        secondary: 'var(--text-color-secondary)',
        disabled: 'var(--disabled-color)',
      },
      borderColor: {
        base: 'var(--border-color)',
      },
      spacing: {
        xs: 'var(--spacing-xs)',
        sm: 'var(--spacing-sm)',
        md: 'var(--spacing-md)',
        lg: 'var(--spacing-lg)',
        xl: 'var(--spacing-xl)',
      },
      fontSize: {
        sm: 'var(--font-size-sm)',
        base: 'var(--font-size-base)',
        lg: 'var(--font-size-lg)',
      },
    },
  },
  plugins: [],
  // 禁用 Tailwind 的预设样式
  corePlugins: {
    preflight: false,
  },
}; 