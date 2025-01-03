'use client';

import { Suspense } from 'react';
import BasicLayout from '@/components/layouts/BasicLayout';
import Loading from '@/components/common/Loading';

export default function DashboardLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  return (
    <BasicLayout>
      <Suspense fallback={<Loading />}>
        {children}
      </Suspense>
    </BasicLayout>
  );
} 