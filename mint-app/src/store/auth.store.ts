import { getLocalAccessToken, removeLocalAccessToken, setLocalAccessToken } from '@/services/local-storage.service';
import { UserAuthProvider } from '@/types/user-auth-provider.type';
import { UserInfo } from '@/types/user-info.type';
import { create } from 'zustand';

type AuthState = {
  accessToken: string | null;
  userInfo: UserInfo | null;
  userAuthProvider: UserAuthProvider | null;

  setAccessToken: (accessToken: string) => void;
  setUserInfo: (userInfo: UserInfo) => void;
  resetUserInfo: () => void;
  setUserAuthProvider: (userAuthProvider: UserAuthProvider) => void;

  setAuthStore: (accessToken: string, userInfo: UserInfo, userAuthProvider?: UserAuthProvider) => void;
  resetAuthStore: () => void;
};

export const useAuthStore = create<AuthState>((set) => ({
  accessToken: getLocalAccessToken(),
  userInfo: null,
  userAuthProvider: null,

  setAccessToken: (accessToken) => {
    setLocalAccessToken(accessToken);
    set({ accessToken });
  },

  setUserInfo: (userInfo) => {
    set({ userInfo });
  },
  resetUserInfo: () => {
    set({ userInfo: null });
  },

  setUserAuthProvider: (userAuthProvider) => {
    set({ userAuthProvider });
  },

  setAuthStore: (accessToken, userInfo, userAuthProvider) => {
    setLocalAccessToken(accessToken);
    set({ accessToken, userInfo });
    if (userAuthProvider) {
      set({ userAuthProvider });
    }
  },

  resetAuthStore: () => {
    removeLocalAccessToken();
    set({ accessToken: null, userInfo: null, userAuthProvider: null });
  },
}));
