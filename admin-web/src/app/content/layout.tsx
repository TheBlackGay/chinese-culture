'use client';

import React from 'react';
import { Breadcrumb } from 'antd';
import { usePathname } from 'next/navigation';
import Link from 'next/link';

const breadcrumbNameMap: Record<string, string> = {
  '/content': '内容管理',
  '/content/articles': '文章管理',
  '/content/categories': '分类管理',
  '/content/tags': '标签管理',
};

interface ContentLayoutProps {
  children: React.ReactNode;
}

export default function ContentLayout({ children }: ContentLayoutProps) {
  const pathname = usePathname();
  const pathSnippets = pathname.split('/').filter((i) => i);
  const extraBreadcrumbItems = pathSnippets.map((_, index) => {
    const url = `/${pathSnippets.slice(0, index + 1).join('/')}`;
    return {
      key: url,
      title: <Link href={url}>{breadcrumbNameMap[url]}</Link>,
    };
  });

  const breadcrumbItems = [
    {
      title: <Link href="/">首页</Link>,
      key: 'home',
    },
  ].concat(extraBreadcrumbItems);

  return (
    <div>
      <Breadcrumb items={breadcrumbItems} className="mb-6" />
      {children}
    </div>
  );
} 