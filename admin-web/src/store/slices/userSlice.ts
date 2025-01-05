import { createSlice, PayloadAction } from '@reduxjs/toolkit';
import type { User } from '@/types';
import { getStoredUser } from '@/utils';

interface UserState {
  currentUser: User | null;
  permissions: string[];
  loading: boolean;
}

const initialState: UserState = {
  currentUser: getStoredUser(),
  permissions: [],
  loading: false,
};

const userSlice = createSlice({
  name: 'user',
  initialState,
  reducers: {
    setCurrentUser: (state, action: PayloadAction<User | null>) => {
      state.currentUser = action.payload;
      if (action.payload) {
        localStorage.setItem(
          process.env.NEXT_PUBLIC_AUTH_USER_KEY || 'auth_user',
          JSON.stringify(action.payload)
        );
      }
    },
    setPermissions: (state, action: PayloadAction<string[]>) => {
      state.permissions = action.payload;
    },
    setLoading: (state, action: PayloadAction<boolean>) => {
      state.loading = action.payload;
    },
    logout: (state) => {
      state.currentUser = null;
      state.permissions = [];
      localStorage.removeItem(process.env.NEXT_PUBLIC_AUTH_TOKEN_KEY || 'auth_token');
      localStorage.removeItem(process.env.NEXT_PUBLIC_AUTH_USER_KEY || 'auth_user');
    },
  },
});

export const { setCurrentUser, setPermissions, setLoading, logout } = userSlice.actions;
export default userSlice.reducer; 