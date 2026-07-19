import { POST_CATEGORIES } from '@/utils/enums/customer-service.enum';
import { parseTimestampToDate } from '@/utils/parser/date-time.parser';
import { orNull, orString } from '@/utils/parser/io.parser';
import { AnnouncementDetailDTO, AnnouncementDTO, FaqDTO, Post, ReviewDTO } from './dto/customer-service.dto';
import { AnnouncementDetailRESP, AnnouncementRESP, ReviewRESP } from './response/customer-service.response';

export const postRespToDto = (r: AnnouncementRESP): AnnouncementDTO => {
  return {
    id: r.id,
    title: r.title,
    createdAt: parseTimestampToDate(orNull(r.createdAt)),
    category: r.category,
    thumbnail: r.thumbnail?.key ? process.env.NEXT_PUBLIC_S3_PREFIX + r.thumbnail.key : undefined,
    link: orString(r.link),
    exposure: r.exposure,
  };
};

export const faqRespToDto = (r: AnnouncementRESP[]): FaqDTO => {
  return {
    FREQUENTLY_ASKED: r
      .filter((item) => item.category?.startsWith('FREQUENTLY_ASKED'))
      .map((item) => ({ id: item.id, title: item.title, category: item.category }) as Post),
    FREQUENTLY_ASKED_BASIC_TRAINING: r
      .filter((item) => item.category === POST_CATEGORIES.FREQUENTLY_ASKED_BASIC_TRAINING)
      .map((item) => ({ id: item.id, title: item.title, category: item.category }) as Post),
    FREQUENTLY_ASKED_ETC: r
      .filter((item) => item.category === POST_CATEGORIES.FREQUENTLY_ASKED_ETC)
      .map((item) => ({ id: item.id, title: item.title, category: item.category }) as Post),
    FREQUENTLY_ASKED_LUCETTE: r
      .filter((item) => item.category === POST_CATEGORIES.FREQUENTLY_ASKED_LUCETTE)
      .map((item) => ({ id: item.id, title: item.title, category: item.category }) as Post),
    FREQUENTLY_ASKED_FUNERAL_SERVICE: r
      .filter((item) => item.category === POST_CATEGORIES.FREQUENTLY_ASKED_FUNERAL_SERVICE)
      .map((item) => ({ id: item.id, title: item.title, category: item.category }) as Post),
    FREQUENTLY_ASKED_FUNERAL_MATERIAL: r
      .filter((item) => item.category === POST_CATEGORIES.FREQUENTLY_ASKED_FUNERAL_MATERIAL)
      .map((item) => ({ id: item.id, title: item.title, category: item.category }) as Post),
  };
};

export const announcementDetailRespToDto = (r: AnnouncementDetailRESP): AnnouncementDetailDTO => {
  const preprocessHTML = (html: string) => {
    const parser = new DOMParser();
    const doc = parser.parseFromString(html, 'text/html');
    const images = doc.querySelectorAll('img');
    images.forEach((img) => {
      img.style.width = '100%';
    });

    return doc.body.innerHTML;
  };

  return {
    id: r.id,
    title: r.title,
    content: preprocessHTML(r.content),
    category: r.category,
    createdAt: parseTimestampToDate(orNull(r.createdAt)),
    thumbnail: r.thumbnail ? process.env.NEXT_PUBLIC_S3_PREFIX + r.thumbnail.key : '',
  };
};

export const reviewRespToDto = (r: ReviewRESP[]): ReviewDTO[] => {
  const response = r.filter((item) => item.content && item.reviewerName && item.title);
  const result: ReviewDTO[] = [];
  for (let i = 0; i < response.length; i += 3) {
    result.push({
      id: result.length + 1,
      reviews: response.slice(i, i + 3),
    });
  }

  return result;
};
