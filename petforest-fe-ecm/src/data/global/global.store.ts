import { create } from 'zustand';

type GlobalState = {
  isIntro: boolean;
  setIsIntro: (value: boolean) => void;
};

export const useGlobalStore = create<GlobalState>((set) => ({
  isIntro: false,
  setIsIntro: (value) => set({ isIntro: value }),
}));
