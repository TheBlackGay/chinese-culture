/**
 * @file 日志工具
 */

type LogLevel = 'debug' | 'info' | 'warn' | 'error';

interface LogOptions {
  module?: string;
  data?: any;
}

class Logger {
  private static instance: Logger;
  private logLevel: LogLevel = 'info';

  private constructor() {
    // 根据环境变量设置日志级别
    this.logLevel = (process.env.NEXT_PUBLIC_LOG_LEVEL as LogLevel) || 'info';
  }

  static getInstance(): Logger {
    if (!Logger.instance) {
      Logger.instance = new Logger();
    }
    return Logger.instance;
  }

  private getTime(): string {
    return new Date().toISOString();
  }

  private shouldLog(level: LogLevel): boolean {
    const levels: LogLevel[] = ['debug', 'info', 'warn', 'error'];
    return levels.indexOf(level) >= levels.indexOf(this.logLevel);
  }

  private formatMessage(level: LogLevel, message: string, options?: LogOptions): string {
    const time = this.getTime();
    const module = options?.module ? `[${options.module}]` : '';
    return `${time} ${level.toUpperCase()} ${module} ${message}`;
  }

  debug(message: string, options?: LogOptions): void {
    if (this.shouldLog('debug')) {
      console.debug(this.formatMessage('debug', message, options), options?.data || '');
    }
  }

  info(message: string, options?: LogOptions): void {
    if (this.shouldLog('info')) {
      console.info(this.formatMessage('info', message, options), options?.data || '');
    }
  }

  warn(message: string, options?: LogOptions): void {
    if (this.shouldLog('warn')) {
      console.warn(this.formatMessage('warn', message, options), options?.data || '');
    }
  }

  error(message: string, error?: Error, options?: LogOptions): void {
    if (this.shouldLog('error')) {
      console.error(
        this.formatMessage('error', message, options),
        error || options?.data || ''
      );
    }
  }

  /**
   * 记录API请求日志
   */
  logRequest(method: string, url: string, data?: any): void {
    this.debug(`${method.toUpperCase()} ${url}`, {
      module: 'API',
      data,
    });
  }

  /**
   * 记录API响应日志
   */
  logResponse(method: string, url: string, status: number, data?: any): void {
    this.debug(`${method.toUpperCase()} ${url} [${status}]`, {
      module: 'API',
      data,
    });
  }

  /**
   * 记录错误日志
   */
  logError(error: Error, context?: string): void {
    this.error(error.message, error, {
      module: context || 'App',
    });
  }
}

export default Logger.getInstance(); 