import { ENUM_STATUS } from '@/utils/enums/status.enum';
import { FilterREQ } from '@/utils/types/filter.type';
import { PagingREQ } from '@/utils/types/paging.type';
import { Dayjs } from 'dayjs';

export type LucetteListFilter = FilterREQ;

export type LucetteListREQ = LucetteListFilter & PagingREQ;

export type LucetteSummaryREQ = {
  startDate: number | null;
  endDate: number | null;
};

export type LucetteListUpdateREQ = {
  processDate?: number;
  processTime?: string;
  process?: string;
  managerId?: number;
  material?: { materialId: number; quantity: number };
  note?: string;
  boneQuantity?: number;
  jewelry?: string;
  tycheDeliveryDate?: number;
  petForestIndiaDate?: number;
  deliveryStatus?: string;
  address?: string;
  addressDetail?: string;
  status?: ENUM_STATUS;
};

export type LucetteScheduleInput = {
  startDate: Dayjs;
  endDate: Dayjs;
  status?: string[];
};

export type LucetteScheduleREQ = {
  startDate: number;
  endDate: number;
  status?: string;
};
