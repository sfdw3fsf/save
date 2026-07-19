import { SettingCorrespondentDTO } from '@/store/setting/correspondent/dto/setting-correspondent.dto';

export type MaterialDetailInput = {
  type: string;
  price: number;
  vendorId?: number;
  vendorManagerName: string;
  vendorContact: string;
  vendorDepositAccount: string;
  vendorBusinessRegistration: string;
  note: string;
  initialVendor?: SettingCorrespondentDTO;
};
