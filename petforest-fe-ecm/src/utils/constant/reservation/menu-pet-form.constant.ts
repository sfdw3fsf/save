import { SERVICE_TRANSPORT_ACCOMPANIED_STATUS, VISTOR_NUMBER } from '@/utils/enums/reservation.enum';

export const OPTION_GUARDIAN_NUMBER_VISITOR = [
  { value: VISTOR_NUMBER.ONE_PERSON, label: '1명' },
  { value: VISTOR_NUMBER.TWO_PERSON, label: '2명' },
  { value: VISTOR_NUMBER.THREE_PERSON, label: '3명' },
  { value: VISTOR_NUMBER.FOUR_AND_MORE, label: '4명 이상' },
];

export const OPTION_FUNERAL_SERVICE = [
  { value: SERVICE_TRANSPORT_ACCOMPANIED_STATUS.ACCOMPANY, label: '동행' },
  { value: SERVICE_TRANSPORT_ACCOMPANIED_STATUS.NOT_ACCOMPANY, label: '비동행' },
];
