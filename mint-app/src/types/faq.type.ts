import { FAQ_CATEGORY } from '@/utils/enums/faq/faq-category.enum';

export type FaqData = {
  id: number;
  question: string;
  answer: string;
  category: FAQ_CATEGORY;
};
