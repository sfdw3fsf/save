import { create } from 'zustand';

type FuneralReservation = {
  selectedReservationKeys: number[];
  setSelectedReservationKeys: (keys: number[]) => void;
};

export const useReservationStore = create<FuneralReservation>((set) => ({
  selectedReservationKeys: [],
  setSelectedReservationKeys: (selectedReservationKeys) => set({ selectedReservationKeys }),
}));
