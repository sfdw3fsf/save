import { POST_VISIBILITY } from '@/utils/enums/customer-service.enum';

export type PopupRESP = {
  id: number;
  title: string;
  connectionLink: string;
  visibility: POST_VISIBILITY;
  startAt: string;
  endAt: string;
  file: {
    id: number;
    fileName: string;
    key: string;
  };
  isWatched: boolean;
};
