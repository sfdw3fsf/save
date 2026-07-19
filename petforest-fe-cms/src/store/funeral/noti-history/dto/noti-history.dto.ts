import { NOTI_HISTORY_CATEGORY } from '@/utils/enums/funeral/reservation-mngt.enums';

export type NotiHistoryListDTO = {
  key: number;
  id: number;
  messageKey: string;
  contact: string;
  category: NOTI_HISTORY_CATEGORY;
  createdAt?: string;
  petName?: string;
  guardianName?: string;
  guardianContact?: string;
};

export type NotiHistoryDetailDTO = {
  id: number;
  content?: string;
  messageKey: string;
  key: string;
  contact?: string;
};
