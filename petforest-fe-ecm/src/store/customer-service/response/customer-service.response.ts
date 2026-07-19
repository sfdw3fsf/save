import { POST_CATEGORIES, POST_EXPOSURE, POST_VISIBILITY } from '@/utils/enums/customer-service.enum';
import { Nil } from '@/utils/utils/types/utils.type';

export type AnnouncementRESP = {
  id: number;
  title: string;
  visibility: POST_VISIBILITY;
  category: POST_CATEGORIES | null;
  createdAt: string | null;
  thumbnail: {
    id: number;
    fileName: string;
    key: string;
  } | null;
  link: string | null;
  exposure: POST_EXPOSURE;
};

type FileType = {
  id: number;
  key: string;
  fileName: string;
};

export type AnnouncementDetailRESP = {
  id: number;
  title: string;
  content: string;
  visibility: POST_VISIBILITY;
  category: POST_CATEGORIES;
  createdAt: string;
  thumbnail: Nil<FileType>;
  attachments: Nil<FileType[]>;
};

export type ReviewRESP = {
  id: number;
  title: string;
  content: string;
  reviewerName: string;
  ordinal: number;
};

export type PressReportRESP = {
  id: number;
  title: string;
  content: string;
  reviewerName: string;
  ordinal: number;
};
