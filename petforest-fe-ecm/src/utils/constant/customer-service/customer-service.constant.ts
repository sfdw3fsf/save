import { POST_CATEGORIES } from '@/utils/enums/customer-service.enum';

export const FAQ_FILTERS = Object.keys(POST_CATEGORIES).filter((item) => item.startsWith('FREQUENTLY_ASKED'));
export const JOURNAL_FILTERS = Object.keys(POST_CATEGORIES).filter((item) => item.startsWith('JOURNAL'));

export const FAQ_TEXT = {
  FREQUENTLY_ASKED: '전체',
  FREQUENTLY_ASKED_BASIC_TRAINING: '기초사후수습',
  FREQUENTLY_ASKED_ETC: '기타',
  FREQUENTLY_ASKED_LUCETTE: '루세떼',
  FREQUENTLY_ASKED_FUNERAL_SERVICE: '장례 서비스',
  FREQUENTLY_ASKED_FUNERAL_MATERIAL: '장례 용품',
};

export const JOURNAL_TEXT = {
  JOURNAL: '전체',
  JOURNAL_CULTURAL: '문화',
  JOURNAL_EDUCATION: '교육',
  JOURNAL_FUNERAL: '장례',
  // JOURNAL_LAW: '법률',
  JOURNAL_HEALTH: '반려생활',
  JOURNAL_PETRO: '펫로스',
  JOURNAL_ETC: '기타',
};
