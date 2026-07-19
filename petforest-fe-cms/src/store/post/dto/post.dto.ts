import { POST_EXPOSURE, POST_VISIBILITY } from '@/utils/enums/post/post.enum';
import { MainCategory, SubCategory } from '@/utils/types/post/post.type';
import { Nil } from '@/utils/types/utils.type';
import { UploadFile } from 'antd/lib';
import { Dayjs } from 'dayjs';

//POST
export type CategoryDTO = Nil<{
  category: Nil<MainCategory>;
  subCategory: Nil<SubCategory>;
}>;

export type PostDTO = {
  key: number;
  id: number;
  title: string;
  visibility: string;
  category: CategoryDTO;
  createdAt: string;
};

export type PostDetailDTO = {
  key: number;
  id: number;
  title: string;
  content: string;
  visibility: POST_VISIBILITY;
  categories: CategoryDTO;
  createdAt: string;
  thumbnail?: UploadFile<any>[];
  attachments?: UploadFile<any>[];
  link: string;
  exposure: POST_EXPOSURE;
  exposureQuantity: number;
};

//REVIEW
export type ReviewDTO = {
  id: number;
  title: string;
  content: string;
  reviewerName: string;
  ordinal: number;
};

//POPUP
export type PopupDTO = {
  key: number;
  id: number;
  title: string;
  connectionLink: string;
  visibility: Nil<POST_VISIBILITY>;
  startAt: Dayjs | null;
  startTimeAt: Dayjs | null;
  endAt: Dayjs | null;
  endTimeAt: Dayjs | null;
  imageId: number | null;
};
