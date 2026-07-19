import { Attachment } from '@/types/attachment.type';
import { Category } from '@/types/category.type';
import { TableContent } from '@/types/table-content.type';

export type NewsDTO = {
  id: number;
  title: string;
  titleContent: string;
  mainContent: string;
  mainImage: NewsMainImageDTO;
  shortDescription: string;
  category: Category;
  tableOfContents: TableContent[];
  attachments: Attachment[];
  createdAt: number;
  updatedAt: number;
};

export type NewsCategoryDTO = {
  id: number;
  name: string;
};

export type NewsMainImageDTO = {
  key: string;
  url: string;
  name: string;
  size: number;
};
