import { ENUM_STATUS } from '@/utils/enums/status.enum';

export type LucetteListRESP = {
  id: number;
  status: string;
  notificationStatus: string;
  boneQuantity: number | null;
  note: string | null;
  processDate: string | null;
  processTime: string | null;
  process: string | null;
  manager: { id: number; fullName: string; position: boolean } | null;
  lucetteOrders:
    | {
        quantity: number;
        lucetteMaterialId: number;
      }[]
    | null;
  reservation: {
    code: string;
    funeralInformation: {
      funeralDate: string | null;
    };
    petInformation: {
      name: string | null;
      weight: number | null;
      petBreed: {
        id: number;
        name: string;
      } | null;
    };
    guardianInformation: {
      name: string | null;
      phoneNumber: string | null;
      address: string | null;
      addressDetail: string | null;
    };
  };
  jewelry: string | null;
};

export type LucetteDetailRESP = {
  id: number;
  jewelry: string | null;
  tycheDeliveryDate: string | null;
  petForestIndiaDate: string | null;
  deliveryStatus: string;
  address: string | null;
  addressDetail: string | null;
};

export type LucetteSummaryRESP = {
  total: number;
  waitingForSchedule: number;
  waitingForProcess: number;
  waitingForExclusive: number;
  waitingForDay: number;
};

export type LucetteScheduleListRESP = {
  date: string;
  records: {
    id: number;
    processTime: string | null;
    processDate: string | null;
    status: ENUM_STATUS | null;
    reservation: {
      petInformation: {
        name: string | null;
        weight: number | null;
      } | null;
      funeralInformation: {
        funeralDate: string | null;
      } | null;
    };
  }[];
};
