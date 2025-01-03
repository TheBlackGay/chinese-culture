/**
 * @file 高阶组件工具
 */

import React from 'react';
import type { FC, ComponentType } from 'react';
import { Spin } from 'antd';
import type { BaseProps } from '@/types/components';

/**
 * 为组件添加加载状态
 * @param WrappedComponent 被包装的组件
 */
export function withLoading<P extends BaseProps>(
  WrappedComponent: ComponentType<P>
): FC<P & { loading?: boolean }> {
  return function WithLoadingComponent({ loading = false, ...props }: P & { loading?: boolean }) {
    if (loading) {
      return (
        <div className="relative w-full h-full min-h-[200px]">
          <Spin className="absolute top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2" />
        </div>
      );
    }
    return <WrappedComponent {...(props as P)} />;
  };
}

/**
 * 为组件添加错误边界
 * @param WrappedComponent 被包装的组件
 */
export function withErrorBoundary<P extends BaseProps>(
  WrappedComponent: ComponentType<P>
): ComponentType<P> {
  return class ErrorBoundary extends React.Component<P, { hasError: boolean; error: Error | null }> {
    constructor(props: P) {
      super(props);
      this.state = { hasError: false, error: null };
    }

    static getDerivedStateFromError(error: Error) {
      return { hasError: true, error };
    }

    componentDidCatch(error: Error, errorInfo: React.ErrorInfo) {
      // TODO: 集成错误监控系统
      console.error('Component Error:', error, errorInfo);
    }

    render() {
      if (this.state.hasError) {
        return (
          <div className="p-4 text-center">
            <h2 className="text-lg font-bold text-red-500">组件出错了</h2>
            <p className="mt-2 text-gray-600">{this.state.error?.message}</p>
          </div>
        );
      }

      return <WrappedComponent {...this.props} />;
    }
  };
}

/**
 * 为组件添加权限控制
 * @param WrappedComponent 被包装的组件
 * @param permission 所需权限
 */
export function withPermission<P extends BaseProps>(
  WrappedComponent: ComponentType<P>,
  permission: string
): FC<P> {
  return function WithPermissionComponent(props: P) {
    // TODO: 从状态管理中获取用户权限
    const userPermissions: string[] = [];

    if (!userPermissions.includes(permission)) {
      return (
        <div className="p-4 text-center">
          <h2 className="text-lg font-bold text-red-500">无访问权限</h2>
          <p className="mt-2 text-gray-600">您没有访问该组件的权限</p>
        </div>
      );
    }

    return <WrappedComponent {...props} />;
  };
}

/**
 * 为组件添加性能追踪
 * @param WrappedComponent 被包装的组件
 * @param componentName 组件名称
 */
export function withPerformanceTracking<P extends BaseProps>(
  WrappedComponent: ComponentType<P>,
  componentName: string
): FC<P> {
  return function WithPerformanceTrackingComponent(props: P) {
    React.useEffect(() => {
      const startTime = performance.now();

      return () => {
        const endTime = performance.now();
        const duration = endTime - startTime;
        console.log(`[Performance] ${componentName} rendered in ${duration.toFixed(2)}ms`);
      };
    }, []);

    return <WrappedComponent {...props} />;
  };
}

/**
 * 为组件添加主题支持
 * @param WrappedComponent 被包装的组件
 */
export function withTheme<P extends BaseProps>(
  WrappedComponent: ComponentType<P>
): FC<P & { theme?: 'light' | 'dark' }> {
  return function WithThemeComponent({ theme = 'light', ...props }: P & { theme?: 'light' | 'dark' }) {
    return (
      <div data-theme={theme} className={`theme-${theme}`}>
        <WrappedComponent {...(props as P)} />
      </div>
    );
  };
}

/**
 * 为组件添加响应式支持
 * @param WrappedComponent 被包装的组件
 */
export function withResponsive<P extends BaseProps>(
  WrappedComponent: ComponentType<P>
): FC<P> {
  return function WithResponsiveComponent(props: P) {
    const [isMobile, setIsMobile] = React.useState(false);

    React.useEffect(() => {
      const checkMobile = () => {
        setIsMobile(window.innerWidth < 768);
      };

      checkMobile();
      window.addEventListener('resize', checkMobile);

      return () => {
        window.removeEventListener('resize', checkMobile);
      };
    }, []);

    return <WrappedComponent {...props} isMobile={isMobile} />;
  };
} 