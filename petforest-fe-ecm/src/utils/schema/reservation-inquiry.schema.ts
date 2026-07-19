import * as yup from 'yup';
import { yups } from './yups';

export const reservationInquirySchema = yup.object().shape({
  guardianName: yups.required.string('보호자의 이름이 필요합니다.'),
  phoneNumber: yup
    .string()
    .matches(/^[0-9]{3,4}-?[0-9]{4}$/, '보호자 연락처를 확인해주세요.')
    .required('보호자의 연락처는 필수 항목 입니다.'),
});

export type ReservationInquirySchema = yup.InferType<typeof reservationInquirySchema>;
