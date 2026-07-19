import { RESERVATION_TIME_SHIFT, RESERVATION_TIME_STATUS, VISTOR_NUMBER } from '@/utils/enums/reservation.enum';

export type ReservationTimeRESP = {
  id: number;
  time: string;
  shift: RESERVATION_TIME_SHIFT;
  status: RESERVATION_TIME_STATUS;
};

export type ReservationRESP = {
  id: number;
};

export type ReservationDetailRESP = {
  id: number;
  code: string;
  branch: {
    id: number;
    name: string;
    naverMap: string;
    kakaoMap: string;
    roadAddress: string;
    streetAddress: string;
  };
  petInformation: {
    petType: {
      id: number;
      name: string;
    };
    weight: number;
    name: string;
  };
  guardianInformation: {
    name: string;
    phoneNumber: string;
  };
  funeralInformation: {
    id: number;
    funeralDate: string;
    funeralTime: string;
    numberOfVisitors: VISTOR_NUMBER;
  };
};

export type ReservationPetTypeRESP = {
  id: number;
  name: string;
  ordinal: number;
  petBreeds: {
    id: number;
    name: string;
    ordinal: number;
  }[];
};
