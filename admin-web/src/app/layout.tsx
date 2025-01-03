import type { Metadata } from "next";
import { Inter } from "next/font/google";
import "./globals.css";
import AntdProvider from "@/components/providers/AntdProvider";
import LayoutProvider from "@/components/layouts/LayoutProvider";

const inter = Inter({
  subsets: ["latin"],
  display: "swap",
});

export const metadata: Metadata = {
  title: "中国文化管理系统",
  description: "中国文化管理系统 - 传承文明，弘扬文化",
};

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <html lang="zh-CN">
      <body className={`${inter.className} antialiased`}>
        <AntdProvider>
          <LayoutProvider>
            {children}
          </LayoutProvider>
        </AntdProvider>
      </body>
    </html>
  );
}
