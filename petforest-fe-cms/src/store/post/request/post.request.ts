import { POST_CATEGORIES, POST_EXPOSURE, POST_MAIN_CATEGORIES, POST_VISIBILITY } from '@/utils/enums/post/post.enum';
import { FilterREQ } from '@/utils/types/filter.type';
import { PagingREQ } from '@/utils/types/paging.type';

//POST
export type PostListFilter = {
  visibilities?: string[];
  categories?: POST_MAIN_CATEGORIES;
} & FilterREQ;

export type PostListREQ = {
  visibilities?: string;
  categories?: string;
} & FilterREQ &
  PagingREQ;

export type PostUpdateREQ = {
  title?: string;
  content?: string;
  visibility?: string;
  category?: POST_CATEGORIES;
  thumbnailId?: number;
  attachmentFileIds?: number[];
  link?: string | null;
  exposure?: POST_EXPOSURE;
};

//REVIEW
export type ReviewCreateREQ = {
  title: string;
  content: string;
  reviewerName: string;
};

export type ReviewUpdateREQ = ReviewCreateREQ;

//POPUP
export type PopupCreateREQ = {
  title: string;
  connectionLink: string;
};

export type PopupListFilter = {
  visibilities?: POST_VISIBILITY;
} & FilterREQ;

export type PopupListREQ = PopupListFilter & PagingREQ;

export type PopupUpdateREQ = {
  title?: string;
  connectionLink?: string;
  startAt?: number;
  endAt?: number;
  visibility?: POST_VISIBILITY;
  isWatched?: boolean;
};
