import type { Config } from "tailwindcss";

const config: Config = {
  content: [
    "./src/pages/**/*.{js,ts,jsx,tsx,mdx}",
    "./src/components/**/*.{js,ts,jsx,tsx,mdx}",
    "./src/app/**/*.{js,ts,jsx,tsx,mdx}",
  ],
  theme: {
    extend: {
      colors: {
        primary: {
          DEFAULT: '#1677ff',
          dark: '#0958d9',
        },
        success: {
          DEFAULT: '#52c41a',
          dark: '#389e0d',
        },
        warning: {
          DEFAULT: '#faad14',
          dark: '#d48806',
        },
        error: {
          DEFAULT: '#ff4d4f',
          dark: '#f5222d',
        },
      },
      spacing: {
        '72': '18rem',
        '84': '21rem',
        '96': '24rem',
      },
      maxWidth: {
        '8xl': '88rem',
      },
      fontFamily: {
        sans: ['var(--font-inter)'],
      },
    },
  },
  plugins: [],
  corePlugins: {
    preflight: true,
  },
}

export default config;
