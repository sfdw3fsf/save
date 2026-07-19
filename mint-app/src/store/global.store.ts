import { SignUpData } from '@/types/sign-up-data.type';
import { create } from 'zustand';

type GlobalState = {
  signUpData: SignUpData | null;
  setSignUpData: (signUpData: SignUpData) => void;
  resetSignUpData: () => void;
};

export const useGlobalStore = create<GlobalState>((set) => ({
  signUpData: null,
  setSignUpData: (signUpData: SignUpData) => {
    set({ signUpData });
  },
  resetSignUpData: () => {
    set({ signUpData: null });
  },
}));
