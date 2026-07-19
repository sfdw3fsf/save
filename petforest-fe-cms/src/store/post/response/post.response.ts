import { Nil } from '@/utils/types/utils.type';
import { POST_CATEGORIES, POST_EXPOSURE, POST_VISIBILITY } from './../../../utils/enums/post/post.enum';

//POST
export type PostRESP = {
  id: number;
  title: string;
  visibility: POST_VISIBILITY;
  category: Nil<POST_CATEGORIES>;
  createdAt: Nil<string>;
};

type FileType = {
  id: number;
  key: string;
  fileName: string;
};

export type PostDetailRESP = {
  id: number;
  title: string;
  content: string;
  visibility: POST_VISIBILITY;
  category: POST_CATEGORIES;
  createdAt: string;
  thumbnail: Nil<FileType>;
  attachments: Nil<FileType[]>;
  link: string | null;
  exposure: POST_EXPOSURE;
  exposureQuantity: number;
};

//REVIEW
export type ReviewRESP = {
  id: number;
  title: string;
  content: string;
  reviewerName: string;
  ordinal: number;
};

//POPUP
export type PopupRESP = {
  id: number;
  title: string;
  connectionLink: string;
  visibility: Nil<POST_VISIBILITY>;
  startAt: Nil<string>;
  endAt: Nil<string>;
  imageId: number | null;
};
