import { create } from 'zustand';
import { persist } from 'zustand/middleware';

type AuthState = {
  isLoggedIn: boolean;
  setIsLogin: (value: boolean) => void;
  token: string;
  setToken: (token: string) => void;
  selectedBranchId?: number;
  setSelectedBranchId: (branchId?: number) => void;
  //TODO: unser infomation type
  user: any;
  setUser: (value: any) => void;
  logout: () => void;
};

export const useAuthStore = create<AuthState>()(
  persist(
    (set) => ({
      isLoggedIn: false,
      setIsLogin: (value: boolean) => set({ isLoggedIn: value }),
      token: '',
      setToken: (token: string) => set({ token }),
      selectedBranchId: undefined,
      setSelectedBranchId: (branchId?: number) => set({ selectedBranchId: branchId }),
      user: null,
      setUser: (value) => set({ user: value }),
      logout: () => {
        set({
          isLoggedIn: false,
          token: '',
          selectedBranchId: undefined,
          user: null,
        });
      },
    }),
    {
      name: 'auth-store',
      partialize: (state) => ({
        isLoggedIn: state.isLoggedIn,
        token: state.token,
        selectedBranchId: state.selectedBranchId,
      }),
    },
  ),
);
