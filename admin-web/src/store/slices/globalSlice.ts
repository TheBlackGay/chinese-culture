import { createSlice, PayloadAction } from '@reduxjs/toolkit';

interface GlobalState {
  collapsed: boolean; // 侧边栏折叠状态
  theme: 'light' | 'dark'; // 主题模式
  loading: boolean; // 全局加载状态
  notifications: number; // 未读消息数
}

const initialState: GlobalState = {
  collapsed: false,
  theme: 'light',
  loading: false,
  notifications: 0,
};

const globalSlice = createSlice({
  name: 'global',
  initialState,
  reducers: {
    toggleCollapsed: (state) => {
      state.collapsed = !state.collapsed;
    },
    setTheme: (state, action: PayloadAction<'light' | 'dark'>) => {
      state.theme = action.payload;
    },
    setGlobalLoading: (state, action: PayloadAction<boolean>) => {
      state.loading = action.payload;
    },
    setNotifications: (state, action: PayloadAction<number>) => {
      state.notifications = action.payload;
    },
  },
});

export const { toggleCollapsed, setTheme, setGlobalLoading, setNotifications } =
  globalSlice.actions;
export default globalSlice.reducer; 