import { UploadFile } from 'antd';
import { Dayjs } from 'dayjs';

export type FacilityListDTO = {
  key: number;
  id: number;
  companyName: string;
  type: string;
  managerName: string;
  contact: string;
  facilityMaintenances: number;
};

export type FacilityHistoryFormDTO = {
  maintenanceDate: number;
  maintenanceScheduleDate: number;
  title: string;
  companyName: string;
  companyManager: string;
  contact: string;
  email: string;
  detail: string;
  note: string;
  facilityId: number | null;
  managerId: number;
  maintenanceImages: UploadFile<any>[];
};

export type FacilityHistoryListDTO = {
  key: number;
  id: number;
  maintenanceDate: Dayjs | null;
  maintenanceScheduleDate: Dayjs | null;
  title: string;
  'facility.companyName': string;
  facilityId: number;
  facilityType: string;
  type: string;
  managerId: number;
  'manager.fullName': string;
};

export type FacilityDetailListDTO = {
  key: number;
  id: number;
  maintenanceDate: Dayjs | null;
  title: string;
  managerName: string;
  managerId: number;
};

export type FacilityDetailDTO = {
  companyName: string;
  type: string;
  managerName: string;
  contact: string;
  note: string;
  email: string;
};
