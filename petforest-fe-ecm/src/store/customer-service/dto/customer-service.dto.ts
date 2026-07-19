import { POST_CATEGORIES, POST_EXPOSURE } from '@/utils/enums/customer-service.enum';

export type Post = {
  id: number;
  title: string;
  createdAt: string;
  category?: POST_CATEGORIES | null;
  thumbnail?: string;
  link: string;
  exposure?: POST_EXPOSURE;
};

export type AnnouncementDTO = Post;

export type FrequentlyAskedKeys = Extract<keyof typeof POST_CATEGORIES, `FREQUENTLY_ASKED${string}`>;
export type JournalKeys = Extract<keyof typeof POST_CATEGORIES, `JOURNAL${string}`>;

export type FaqDTO = {
  [key in FrequentlyAskedKeys]: Post[];
};

export type AnnouncementDetailDTO = {
  id: number;
  title: string;
  content: string;
  category: POST_CATEGORIES;
  createdAt: string;
  thumbnail: string;
};

export type ReviewDTO = {
  id: number;
  reviews: {
    title: string;
    content: string;
    reviewerName: string;
  }[];
};
