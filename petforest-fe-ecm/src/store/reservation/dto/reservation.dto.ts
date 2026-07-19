import {
  RESERVATION_TIME_SHIFT,
  RESERVATION_TIME_STATUS,
  SERVICE_TRANSPORT_ACCOMPANIED_STATUS,
  VISTOR_NUMBER,
} from '@/utils/enums/reservation.enum';

export type ReservationTime = {
  id: number;
  time: string;
  shift?: RESERVATION_TIME_SHIFT;
  status: RESERVATION_TIME_STATUS;
};

export type ReservationTimeDTO = {
  AM: ReservationTime[];
  PM: ReservationTime[];
};

export type ReservationDTO = {
  reserveInfo: {
    branch: number;
    date?: number;
    time: string;
  };
  petInfo: {
    petName: string;
    petWeight: number;
    petClassification: number;
  };
  guardianInfo: {
    guardianName: string;
    numOfVisitor?: VISTOR_NUMBER;
    phoneNumber: string;
    message: string;
  };
  additionalInfo: {
    isNotSellected?: boolean;
    funeralService: {
      isActive?: boolean;
      accompanny?: SERVICE_TRANSPORT_ACCOMPANIED_STATUS | null;
      address?: string | null;
      detailedAddress?: string | null;
    };
    flowerBouquetService?: boolean;
    lucetteService?: boolean;
  };
};

export type ReservationDetailDTO = {
  branchName: string;
  date: number;
  time: string;
  petName: string;
  petWeight: number;
  petType: string;
  guardianName: string;
  guardianPhone: string;
  numberOfVisitor: VISTOR_NUMBER;
};
