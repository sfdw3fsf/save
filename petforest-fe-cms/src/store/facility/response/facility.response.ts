import { Nil } from '@/utils/types/utils.type';

export type FacilityListRESP = {
  id: number;
  companyName: string;
  type: string;
  managerName: string;
  contact: string;
  repairQuantities: number;
};

export type FacilityHistoryListRESP = {
  id: number;
  maintenanceDate: string;
  maintenanceScheduleDate: string;
  companyName: Nil<number>;
  title: Nil<string>;
  detail: Nil<string>;
};

type FileType = {
  id: number;
  key: string;
  fileName: string;
};

export type FacilityHistoryMaintanancesListRESP = {
  id: number;
  maintenanceDate: string;
  maintenanceScheduleDate: string;
  companyName: Nil<number>;
  title: Nil<string>;
  detail: Nil<string>;
  facility: {
    id: number;
    companyName: string;
    type: string;
    managerName: string;
    contact: string;
    note: string;
    email: string;
  };
  manager: Nil<{
    id: number;
    fullName: string;
  }>;
  maintenanceImages: Nil<FileType[]>;
};

export type FacilityCompanyNamesRESP = {
  companyName: string;
};

export type FacilityDetailRESP = {
  id: number;
  companyName: Nil<string>;
  type: Nil<string>;
  managerName: Nil<string>;
  contact: Nil<string>;
  note: Nil<string>;
  email: Nil<string>;
};

export type FacilityDetailListRESP = {
  id: number;
  maintenanceDate: string;
  title: Nil<string>;
  facility: {
    id: number;
    companyName: string;
    type: string;
  };
  manager: Nil<{
    id: number;
    fullName: string;
  }>;
  maintenanceImages: Nil<FileType[]>;
};

export type FacilityTypesRESP = {
  id: number;
  type: string;
};
