import { PAYMENT_METHOD } from '@/utils/enums/common.enum';
import { KAKAO_MESSAGE_TYPE } from '@/utils/enums/common.enums';
import { ENUM_STATUS } from '@/utils/enums/status.enum';
import { FilterREQ } from '@/utils/types/filter.type';
import { PagingREQ } from '@/utils/types/paging.type';

export type OssuaryListFilter = FilterREQ;

export type OssuaryListREQ = OssuaryListFilter & PagingREQ;

export type OssuaryUpdateREQ = {
  registrationDate?: number;
  expirationDate?: number;
  guardianName?: string;
  childName?: string;
  contact?: string;
  address?: string;
  emergencyContact?: string;
  note?: string;
  contractNotificationStatus?: ENUM_STATUS;
  expirationNotificationStatus?: ENUM_STATUS;
  extensionDepositNotificationStatus?: ENUM_STATUS;
  extensionConfirmationNotificationStatus?: ENUM_STATUS;
  columbariumPlaceId?: number;
};

export type OssuaryInformationCreateUpdateREQ = {
  registrationDate?: number;
  expirationDate?: number;
  guardianName?: string;
  childName?: string;
  contact?: string;
  address?: string;
  addressDetail?: string;
  emergencyContact?: string;
  note?: string;
  columbariumRoomId?: number;
  columbariumPlaceId?: number;
};

export type OssuarySummariesREQ = {
  startDate: number;
  endDate: number;
};

export type OssuaryDetailUsageHistoryFilter = FilterREQ;
export type OssuaryDetailUsageHistoryREQ = OssuaryDetailUsageHistoryFilter & PagingREQ;

export type OssuaryDetailSettlementHistoryFilter = FilterREQ;
export type OssuaryDetailSettlementHistoryREQ = OssuaryDetailSettlementHistoryFilter & PagingREQ;

export type OssuaryDetailSettlementHistoryUpdateREQ = {
  note?: string;
  amount?: number;
  settlementMethod?: PAYMENT_METHOD;
  settlementStatus?: ENUM_STATUS;
};

export type OssuaryMessageCreateREQ = {
  type: KAKAO_MESSAGE_TYPE;
  columbariumIds: number[];
};
