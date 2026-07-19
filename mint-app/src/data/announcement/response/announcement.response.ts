import { ANNOUNCEMENT_ATTACHMENT_TYPE, ANNOUNCEMENT_STATUS_TYPE } from '@/utils/enums/data/announcement-type.enum';

export type AnnouncementRESP = {
  data: AnnouncementData[];
  pagination: Pagination;
};

export type Attachment = {
  key: string;
  url: string;
  name: string;
  size: number;
  type: ANNOUNCEMENT_ATTACHMENT_TYPE;
};

export type Pagination = {
  pageNo: number;
  pageSize: number;
  totalElements: number;
  totalPages: number;
  last: boolean;
};

export type TableOfContents = {
  title: string;
  anchorValue: string;
};

export type AnnouncementData = {
  id: number;
  title: string;
  titleContent: string;
  mainContent: string;
  category: {
    id: number;
    name: string;
  };
  tableOfContents: TableOfContents[];
  attachments: Attachment[];
  status: ANNOUNCEMENT_STATUS_TYPE;
  createdAt: number;
  updatedAt: number;
};

export type AnnouncementCategory = {
  id: number;
  name: string;
};
