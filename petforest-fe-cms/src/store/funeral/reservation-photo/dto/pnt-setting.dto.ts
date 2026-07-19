import { Dayjs } from 'dayjs';
import { ReservationPhotoFile } from './reservation-photo.dto';

export type PntSettingReservation = {
  id: number;
  key: number;
  code?: string;
  funeralDate: Dayjs | null;
  funeralTime: string | null;
  petName?: string;
  petBreed?: {
    id: number;
    name?: string;
  };
  files: ReservationPhotoFile[];
  petImage?: ReservationPhotoFile;
  guardianName?: string;
};

export type PntSettingListDTO = {
  id: number;
  key: number;
  name?: string;
  reservation?: PntSettingReservation;
};

export type PntSettingDetailDTO = {
  id: number;
  key: number;
  name?: string;
  reservation?: {
    id: number;
    key: number;
    code?: string;
    funeralDate: Dayjs | null;
    funeralTime: Dayjs | null;
    files: ReservationPhotoFile[];
    petName?: string;
    petBreed?: {
      id: number;
      name?: string;
    };
    guardianName?: string;
  };
};
