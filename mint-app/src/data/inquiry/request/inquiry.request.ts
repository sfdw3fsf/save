import { INQUIRY_ANSWER_STATUS, INQUIRY_STATUS } from '@/utils/enums/data/inquiry-status.enum';

export type InquiryREQ = {
  search?: string;
  categoryId?: number;
  status?: INQUIRY_STATUS;
  answerStatus?: INQUIRY_ANSWER_STATUS;
  startDate?: number;
  endDate?: number;
  page?: number;
  size?: number;
  sort?: string;
  direction?: string;
  categoryName?: string;
};
export type InquiryCreateREQ = {
  categoryId: number;
  subject: string;
  detail: string;
  // email: string;
};
