export type NotiHistoryListRESP = {
  id: number;
  messageKey: string;
  category: string;
  createdAt: string | null;
  contact: string | null;
  reservation: {
    petInformation: {
      name: string | null;
    } | null;
    guardianInformation: {
      name: string | null;
      phoneNumber: string | null;
    } | null;
  } | null;
};

export type NotiHistoryDetailRESP = {
  id: number;
  content: string | null;
  messageKey: string;
  key: string;
  contact: string | null;
};
