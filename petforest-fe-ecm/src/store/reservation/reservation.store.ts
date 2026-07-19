import { create } from 'zustand';
import { persist } from 'zustand/middleware';
import { BranchDTO } from '../branch/dto/branch.dto';

type ReservationState = {
  reservationId?: number;
  setReservationId: (reservationId?: number) => void;
  selectedBranch?: BranchDTO;
  setSelectedBranch: (branch?: BranchDTO) => void;
};

export const useReservationStore = create<ReservationState>()(
  persist(
    (set) => ({
      reservationId: undefined,
      setReservationId: (reservationId?: number) => set({ reservationId }),
      selectedBranch: undefined,
      setSelectedBranch: (branch?: BranchDTO) => set({ selectedBranch: branch }),
    }),
    {
      name: 'reservation-store',
      partialize: (state) => ({
        reservationId: state.reservationId,
        selectedBranch: state.selectedBranch,
      }),
    },
  ),
);
