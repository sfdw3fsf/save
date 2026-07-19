import { Attachment } from '@/data/announcement/response/announcement.response';
import { PaginationRESP } from '@/types/pagination.type';
import { INQUIRY_ANSWER_STATUS, INQUIRY_STATUS } from '@/utils/enums/data/inquiry-status.enum';

export type InquiryRESP = {
  data: Inquiry[];
  pagination: PaginationRESP;
};

export type InquiryCategory = {
  id: number;
  name: string;
  code: string;
  createdAt: number;
  updatedAt: number;
};

export type Inquiry = {
  id: number;
  category: InquiryCategory;
  status: INQUIRY_STATUS;
  answerStatus: INQUIRY_ANSWER_STATUS;
  subject: string;
  detail: string;
  answer: string | null;
  createdAt: number;
  updatedAt: number;
  attachment: Attachment[];
};
