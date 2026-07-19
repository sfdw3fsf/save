import { create } from 'zustand';

type ExampleState = {
  user: string;
  setUser: (value: string) => void;
};

export const useExampleStore = create<ExampleState>((set) => ({
  user: '',
  setUser: (value) => set({ user: value }),
}));
