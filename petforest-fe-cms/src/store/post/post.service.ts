import { DATE_FORMAT } from '@/utils/constants/date.constant';
import { POST_CATEGORIES, POST_VISIBILITY } from '@/utils/enums/post/post.enum';
import { parseTimestampToDate } from '@/utils/parser/date-time.parser';
import { orNull, orString, orUndefined } from '@/utils/parser/io.parser';
import { parsePostCategory } from '@/utils/parser/post.parser';
import { PagingREQ } from '@/utils/types/paging.type';
import { UploadFile } from 'antd';
import dayjs from 'dayjs';
import { PopupDTO, PostDetailDTO, PostDTO, ReviewDTO } from './dto/post.dto';
import {
  PopupCreateREQ,
  PopupListFilter,
  PopupListREQ,
  PostListFilter,
  PostListREQ,
  PostUpdateREQ,
  ReviewCreateREQ,
} from './request/post.request';
import { PopupRESP, PostDetailRESP, PostRESP, ReviewRESP } from './response/post.response';

export const initalValues = {
  visibility: POST_VISIBILITY.RELEASE,
  title: '',
  content: '',
  thumbnail: [],
  attachments: [],
};

//POST
export const postRespToDto = (r: PostRESP): PostDTO => {
  return {
    key: r.id,
    id: r.id,
    title: orString(r.title),
    visibility: orString(r.visibility),
    category: r.category ? parsePostCategory(r.category) : null,
    createdAt: parseTimestampToDate(r.createdAt, DATE_FORMAT.DATE_TIME_SECOND.HYPHEN),
  };
};

export function postListParamsToFilter(searchParams: URLSearchParams): PostListFilter {
  return {
    search: orUndefined(searchParams.get('search')),
    filter: orUndefined(searchParams.get('filter')),
    sortOrder: orUndefined(searchParams.get('sortOrder')),
    sortBy: orUndefined(searchParams.get('sortBy')),
    startDate: orUndefined(searchParams.get('startDate')),
    endDate: orUndefined(searchParams.get('endDate')),
  } as PostListFilter;
}

export const postListFilterFormatRequest = (r: PostListFilter & PagingREQ): PostListREQ => {
  return {
    ...r,
    visibilities: orUndefined(r.visibilities),
    categories: r.categories ? `[${r.categories}]` : undefined,
  };
};

export const postDetailRespToDto = (r: PostDetailRESP): PostDetailDTO => {
  return {
    key: r.id,
    id: r.id,
    title: orString(r.title),
    visibility: orString(r.visibility) as POST_VISIBILITY,
    categories: r.category ? parsePostCategory(r.category) : null,
    createdAt: parseTimestampToDate(r.createdAt, DATE_FORMAT.DATE_TIME_SECOND.HYPHEN),
    content: orString(r.content),
    attachments: r.attachments?.map((item) => ({
      uid: item.id.toString(),
      name: item.fileName,
      status: 'done',
      url: process.env.NEXT_PUBLIC_S3_PREFIX + item.key,
    })),
    thumbnail: r.thumbnail
      ? [
          {
            uid: r.thumbnail.id.toString(),
            name: r.thumbnail.fileName,
            status: 'done',
            url: process.env.NEXT_PUBLIC_S3_PREFIX + r.thumbnail.key,
          } as UploadFile,
        ]
      : undefined,
    link: orString(r.link),
    exposure: r.exposure,
    exposureQuantity: r.exposureQuantity,
  };
};

export const postDetailInputToReq = (i: PostDetailDTO, attachmentIds?: number[], thumbnailId?: number): PostUpdateREQ => {
  return {
    title: i.title,
    content: i.content,
    visibility: i.visibility,
    attachmentFileIds: attachmentIds,
    thumbnailId: thumbnailId,
    category: (i.categories?.subCategory ?? i.categories?.category) as POST_CATEGORIES,
    link: i.link,
    exposure: i.exposure,
  };
};

//REVIEW
export const reviewCreateToReq = (): ReviewCreateREQ => {
  return {
    title: '',
    content: '',
    reviewerName: '',
  };
};

export const reviewRespToDto = (r: ReviewRESP): ReviewDTO => {
  return r;
};

//POPUP
export const popupreateToReq = (): PopupCreateREQ => {
  return {
    title: '',
    connectionLink: '',
  };
};

export function popupListParamsToFilter(searchParams: URLSearchParams): PopupListFilter {
  return {
    search: orUndefined(searchParams.get('search')),
    sortOrder: orUndefined(searchParams.get('sortOrder')),
    sortBy: orUndefined(searchParams.get('sortBy')),
    visibilities: orUndefined(searchParams.get('visibilities')),
    startDate: orUndefined(searchParams.get('startDate')),
    endDate: orUndefined(searchParams.get('endDate')),
  } as PopupListFilter;
}

export const popupListFilterFormatRequest = (r: PopupListFilter & PagingREQ): PopupListREQ => {
  return {
    ...r,
    visibilities: orUndefined(r.visibilities),
  };
};

export const popupRespToDto = (r: PopupRESP): PopupDTO => {
  return {
    key: r.id,
    ...r,
    visibility: r.visibility,
    startAt: orNull(dayjs(Number(r.startAt))),
    startTimeAt: orNull(dayjs(Number(r.startAt))),
    endAt: orNull(dayjs(Number(r.endAt))),
    endTimeAt: orNull(dayjs(Number(r.endAt))),
  };
};
