import { POST_CATEGORIES } from '../enums/customer-service.enum';

export type JournalInfoBannerType = {
  id: number;
  image?: string;
  category?: POST_CATEGORIES | null;
  text: string;
};
