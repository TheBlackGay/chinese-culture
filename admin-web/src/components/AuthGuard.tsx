import { FC, ReactNode } from 'react';
import { useRouter } from 'next/router';
import { useAppSelector } from '@/store';
import { hasPermission } from '@/utils';

interface AuthGuardProps {
  children: ReactNode;
  permissions?: string[];
  fallback?: ReactNode;
}

const AuthGuard: FC<AuthGuardProps> = ({ children, permissions = [], fallback = null }) => {
  const router = useRouter();
  const { currentUser, permissions: userPermissions } = useAppSelector((state) => state.user);

  // 检查是否登录
  if (!currentUser) {
    if (typeof window !== 'undefined') {
      router.push(`/login?redirect=${encodeURIComponent(router.asPath)}`);
    }
    return null;
  }

  // 检查是否有权限
  if (permissions.length > 0 && !hasPermission(userPermissions, permissions)) {
    return fallback;
  }

  return <>{children}</>;
};

export default AuthGuard; 