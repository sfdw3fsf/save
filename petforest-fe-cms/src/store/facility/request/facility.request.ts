import { FilterREQ } from '@/utils/types/filter.type';
import { PagingREQ } from '@/utils/types/paging.type';

export type FacilityCreateREQ = {
  companyName: string;
  managerName: string;
  type: string;
  contact: string;
};

export type FacilityListFilter = { date?: number } & FilterREQ;

export type FacilityListREQ = FacilityListFilter & PagingREQ;

export type FacilityListUpdateREQ = {
  companyName?: string;
  managerName?: string;
  type?: string;
  contact?: string;
  note?: string;
  email?: string;
};

export type FacilityHistoryCreateUpdateREQ = {
  maintenanceDate?: number;
  maintenanceScheduleDate?: number;
  title?: string;
  detail?: string;
  facilityId?: number;
  managerId?: number;
  maintenanceImageIds?: number[];
};
