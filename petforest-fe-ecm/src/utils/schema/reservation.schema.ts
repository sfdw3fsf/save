import * as yup from 'yup';
import { VISTOR_NUMBER } from '../enums/reservation.enum';
import { yups } from './yups';

export const reservationSchema = yup.object().shape({
  reserveInfo: yup
    .object({
      branch: yup.number().required('Branch is required.'),
      date: yup.number().required('Branch is required.'),
      time: yup.string().required('Time is required.'),
    })
    .required(),
  petInfo: yup.object().shape({
    petName: yups.required.string('반려동물의 이름은 필수 항목 입니다.'),
    petWeight: yups.required.number({
      min: {
        value: 1,
        key: '반려동물의 체중은 필수 항목 입니다.',
      },
      requiredKey: '반려동물의 체중은 필수 항목 입니다.',
    }),
    petClassification: yups.required.number({
      min: {
        value: 1,
        key: '반려동물의 구분은 필수 항목 입니다.',
      },
      requiredKey: '반려동물의 구분은 필수 항목 입니다.',
    }),
  }),
  guardianInfo: yup.object().shape({
    guardianName: yups.required.string('보호자의 이름은 필수 항목 입니다.'),
    numOfVisitor: yup.mixed<VISTOR_NUMBER>().oneOf(Object.values(VISTOR_NUMBER)),
    phoneNumber: yup
      .string()
      .matches(/^[0-9]{3,4}-?[0-9]{4}$/, '보호자 연락처를 확인해주세요.')
      .required('보호자의 연락처는 필수 항목 입니다.'),
    message: yup.string().default(''),
  }),
  additionalInfo: yup.object().shape({
    isNotSellected: yup.boolean(),
    flowerBouquetService: yup.boolean(),
    funeralService: yup.object().shape({
      isActive: yup.boolean(),
      accompanny: yups.custom.funeralServiceAccompany('운구 서비스를 위한 정보는 필수 항목입니다.'),
      address: yups.custom.funeralService('운구 서비스를 위한 정보는 필수 항목입니다.'),
      detailedAddress: yups.custom.funeralService('운구 서비스를 위한 정보는 필수 항목입니다.'),
    }),
    lucetteService: yup.boolean(),
  }),
});

export type ReservationSchema = yup.InferType<typeof reservationSchema>;
