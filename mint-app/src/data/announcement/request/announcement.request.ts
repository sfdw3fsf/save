import { ANNOUNCEMENT_SEARCH_TYPE } from '@/utils/enums/data/announcement-type.enum';

export type AnnouncementREQ = {
  page: number;
  size: number;
  searchType?: ANNOUNCEMENT_SEARCH_TYPE;
  search?: string;
  category?: string;
  direction?: string;
};
