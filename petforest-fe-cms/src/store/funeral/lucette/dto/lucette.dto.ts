import { LUCETTE_CATEGORY } from '@/utils/enums/funeral/lucette-mngt.enums';
import { ENUM_STATUS } from '@/utils/enums/status.enum';
import { Dayjs } from 'dayjs';

export type LucetteListDTO = {
  key: number;
  id: number;
  status: ENUM_STATUS;
  notificationStatus: ENUM_STATUS;
  boneQuantity?: number;
  note?: string;
  processDate: Dayjs | null;
  processTime: Dayjs | null;
  process?: LUCETTE_CATEGORY;
  managerId?: number;
  managerPosition: boolean;
  managerName?: string;
  lucetteOrder: Record<number, number>;
  reservationCode?: string;
  funeralDate?: string;
  petName?: string;
  petWeight?: number;
  petBreed?: string;
  petBreedId?: number;
  guardianName?: string;
  guardianContact?: string;
  guardianAddress?: string;
  guardianAddressDetail?: string;
  jewelry: string | null;
};

export type LucetteDetailDTO = {
  id: number;
  jewelry: string | null;
  tycheDeliveryDate: Dayjs | null;
  petForestIndiaDate: Dayjs | null;
  deliveryStatus: ENUM_STATUS;
  address: string | null;
  addressDetail: string | null;
};

export type LucetteSummaryDTO = {
  total: number;
  detail: {
    sameDay: number;
    schedulePending: number;
    processPending: number;
    standalone: number;
  };
};

export type LucetteScheduleListDTO = {
  id: string;
  title: string;
  start: string;
  end: string;
  calendarId: string;
  category: 'allday';
  isReadOnly: true;
};
