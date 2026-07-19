import { Dayjs } from 'dayjs';

export type ReservationPhotoFile = {
  id: number;
  key: string;
  fileName?: string;
  usedInMemorialRoom?: boolean;
  url: string;
};

export type ReservationPhotoListDTO = {
  id: number;
  key: number;
  files: ReservationPhotoFile[];
  reservation?: {
    code?: string;
    leaderId: number;
    leaderName?: string;
    funeralDate: Dayjs | null;
    funeralTime: string | null;
    petName?: string;
    petBreed: {
      id: number;
      name?: string;
    };
    guardianName?: string | null;
    guardianPhone?: string | null;
  };
};

export type ReservationPhotoDetailDTO = {
  id: number;
  files: ReservationPhotoFile[];
  reservation?: {
    code?: string;
    leaderId: number;
    leaderName?: string;
    funeralDate: Dayjs | null;
    funeralTime: string | null;
    petName?: string;
    petBreed: {
      id: number;
      name?: string;
    };
    guardianName?: string | null;
    guardianPhone?: string | null;
  };
};
