'use client';

import React from 'react';
import { Alert, Button } from 'antd';

interface Props {
  children: React.ReactNode;
}

interface State {
  hasError: boolean;
  error: Error | null;
}

export default class ErrorBoundary extends React.Component<Props, State> {
  constructor(props: Props) {
    super(props);
    this.state = {
      hasError: false,
      error: null,
    };
  }

  static getDerivedStateFromError(error: Error): State {
    return {
      hasError: true,
      error,
    };
  }

  override componentDidCatch(error: Error, errorInfo: React.ErrorInfo) {
    // 这里可以记录错误日志
    console.error('Error:', error);
    console.error('Error Info:', errorInfo);
  }

  override render() {
    if (this.state.hasError) {
      return (
        <div className="flex flex-col items-center justify-center p-8">
          <Alert
            message="页面出错了"
            description={this.state.error?.message || '发生了未知错误'}
            type="error"
            showIcon
            className="mb-4"
          />
          <Button
            type="primary"
            onClick={() => {
              this.setState({ hasError: false, error: null });
              window.location.reload();
            }}
          >
            刷新页面
          </Button>
        </div>
      );
    }

    return this.props.children;
  }
} 