import type { Metadata } from "next";
import { Inter } from "next/font/google";
import { ConfigProvider } from "antd";
import zhCN from "antd/locale/zh_CN";
import LayoutProvider from "@/components/layouts/LayoutProvider";
import ClientProvider from "@/components/providers/ClientProvider";
import "./globals.css";

const inter = Inter({
  subsets: ["latin"],
  display: "swap",
});

export const metadata: Metadata = {
  title: "中国文化管理系统",
  description: "中国文化管理系统 - 后台管理",
};

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <html lang="zh-CN">
      <body className={`${inter.className} antialiased`}>
        <ClientProvider>
          <ConfigProvider
            locale={zhCN}
            theme={{
              token: {
                colorPrimary: "#1677ff",
                borderRadius: 4,
              },
            }}
          >
            <LayoutProvider>{children}</LayoutProvider>
          </ConfigProvider>
        </ClientProvider>
      </body>
    </html>
  );
}
