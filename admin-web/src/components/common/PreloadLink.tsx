'use client';

import React, { useCallback, useState } from 'react';
import { useRouter } from 'next/navigation';
import { Spin } from 'antd';

interface PreloadLinkProps {
  href: string;
  children: React.ReactNode;
  className?: string;
  onClick?: (e: React.MouseEvent) => void;
  preloadData?: () => Promise<any>;
  prefetch?: boolean;
}

export default function PreloadLink({
  href,
  children,
  className,
  onClick,
  preloadData,
  prefetch = true,
}: PreloadLinkProps) {
  const router = useRouter();
  const [isLoading, setIsLoading] = useState(false);

  const handleClick = useCallback(
    async (e: React.MouseEvent) => {
      e.preventDefault();
      if (isLoading) return;

      try {
        setIsLoading(true);
        
        if (preloadData) {
          await preloadData();
        }
        
        if (onClick) {
          onClick(e);
        }
        
        router.push(href);
      } catch (error) {
        console.error('Navigation error:', error);
      } finally {
        setIsLoading(false);
      }
    },
    [href, router, onClick, isLoading, preloadData]
  );

  return (
    <a
      href={href}
      onClick={handleClick}
      className={`${className} ${isLoading ? 'cursor-wait opacity-70' : ''}`}
      onMouseEnter={() => {
        if (prefetch && !isLoading) {
          // 预加载数据
          preloadData?.();
        }
      }}
    >
      {isLoading ? (
        <span className="inline-flex items-center">
          <Spin size="small" className="mr-1" />
          {children}
        </span>
      ) : (
        children
      )}
    </a>
  );
} 