import { BRANCH_ENUMS } from '@/utils/enums/common.enums';
export type CollaboratingRegion = {
  province: number;
  municipality: number;
};

type HospitalAddress = {
  address: string;
  detailAddress: string;
};

export type PARTNER_HOSPITAL_INFORMATION = {
  director: string;
  settlementMethod: string;
  branch: BRANCH_ENUMS;
  size: string | number;
  parkingAvailability: string;
  hospitalAddress: HospitalAddress;
  businessRegistrationNumber: string | number;
  phoneNumber: number | undefined;
  email: string;
  note: string;
};
