import '@umijs/max/typings';

declare module '*.css';
declare module '*.less';
declare module '*.png';
declare module '*.svg' {
  export function ReactComponent(props: React.SVGProps<SVGSVGElement>): React.ReactElement;
  const url: string;
  export default url;
}

declare module 'iztro' {
  export interface IFunctionalHoroscope {
    solarDate: string;
    lunarDate: string;
    mingGong: number;
    shenGong: number;
    wuxing: string;
    palaces: Array<{
      name: string;
      earthlyBranch: string;
      stars: string[];
      transformations: Record<string, {
        type: '禄' | '权' | '科' | '忌';
        source: string;
      }>;
      starCombinations: string[];
    }>;
  }

  export class Astro {
    static bySolar(year: number, month: number, day: number, hour: number): {
      getHoroscope(): IFunctionalHoroscope;
    };
  }
}
