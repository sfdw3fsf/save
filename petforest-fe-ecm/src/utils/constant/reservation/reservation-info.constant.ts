import { RESERVATION_TIME_STATUS } from '@/utils/enums/reservation.enum';

export const RESERVATION_PRECAUTIONS = [
  '· 펫포레스트의 모든 장례는 예약제로 운영됩니다.',
  '· 장례 절차는 전 과정 참관이 가능합니다.',
  '· 운구 서비스 / 꽃다발 서비스 요청 시 선택사항 체크란에 꼭 체크해 주세요.(별도 비용 발생)',
  '· 운구서비스는 차량 배치 상황 에 따라 시간이 변경될 수 있으며, 예약 접수 후 개별적으로 연락드립니다.',
  '· 예약 접수 후 개별적으로 예약 확정 알림톡을 드립니다.',
];

export const mockDataReservationTime = {
  AM: [
    { id: 1, time: '01:30', status: RESERVATION_TIME_STATUS.COMPLETED },
    { id: 2, time: '02:00', status: RESERVATION_TIME_STATUS.COMPLETED },
    { id: 3, time: '02:30', status: RESERVATION_TIME_STATUS.COMPLETED },
    { id: 4, time: '03:00', status: RESERVATION_TIME_STATUS.COMPLETED },
    { id: 5, time: '03:30', status: RESERVATION_TIME_STATUS.COMPLETED },
    { id: 6, time: '04:00', status: RESERVATION_TIME_STATUS.COMPLETED },
    { id: 7, time: '04:30', status: RESERVATION_TIME_STATUS.COMPLETED },
    { id: 8, time: '05:00', status: RESERVATION_TIME_STATUS.COMPLETED },
    { id: 9, time: '05:30', status: RESERVATION_TIME_STATUS.COMPLETED },
    { id: 10, time: '06:00', status: RESERVATION_TIME_STATUS.COMPLETED },
    { id: 11, time: '06:30', status: RESERVATION_TIME_STATUS.COMPLETED },
    { id: 12, time: '07:00', status: RESERVATION_TIME_STATUS.COMPLETED },
    { id: 13, time: '07:30', status: RESERVATION_TIME_STATUS.COMPLETED },
    { id: 14, time: '08:00', status: RESERVATION_TIME_STATUS.COMPLETED },
    { id: 15, time: '08:30', status: RESERVATION_TIME_STATUS.COMPLETED },
    { id: 16, time: '09:00', status: RESERVATION_TIME_STATUS.COMPLETED },
    { id: 17, time: '09:30', status: RESERVATION_TIME_STATUS.COMPLETED },
    { id: 18, time: '10:00', status: RESERVATION_TIME_STATUS.COMPLETED },
    { id: 19, time: '10:30', status: RESERVATION_TIME_STATUS.COMPLETED },
    { id: 20, time: '11:00', status: RESERVATION_TIME_STATUS.COMPLETED },
    { id: 21, time: '11:30', status: RESERVATION_TIME_STATUS.COMPLETED },
  ],
  PM: [
    { id: 22, time: '12:00', status: RESERVATION_TIME_STATUS.COMPLETED },
    { id: 23, time: '12:30', status: RESERVATION_TIME_STATUS.COMPLETED },
    { id: 24, time: '13:00', status: RESERVATION_TIME_STATUS.COMPLETED },
    { id: 25, time: '13:30', status: RESERVATION_TIME_STATUS.COMPLETED },
    { id: 26, time: '14:00', status: RESERVATION_TIME_STATUS.COMPLETED },
    { id: 27, time: '14:30', status: RESERVATION_TIME_STATUS.COMPLETED },
    { id: 28, time: '15:00', status: RESERVATION_TIME_STATUS.COMPLETED },
    { id: 29, time: '15:30', status: RESERVATION_TIME_STATUS.COMPLETED },
    { id: 30, time: '16:00', status: RESERVATION_TIME_STATUS.COMPLETED },
    { id: 31, time: '16:30', status: RESERVATION_TIME_STATUS.COMPLETED },
    { id: 32, time: '17:00', status: RESERVATION_TIME_STATUS.COMPLETED },
    { id: 33, time: '17:30', status: RESERVATION_TIME_STATUS.COMPLETED },
    { id: 34, time: '18:00', status: RESERVATION_TIME_STATUS.COMPLETED },
    { id: 35, time: '18:30', status: RESERVATION_TIME_STATUS.COMPLETED },
    { id: 36, time: '19:00', status: RESERVATION_TIME_STATUS.COMPLETED },
    { id: 37, time: '19:30', status: RESERVATION_TIME_STATUS.COMPLETED },
    { id: 38, time: '20:00', status: RESERVATION_TIME_STATUS.COMPLETED },
    { id: 39, time: '21:00', status: RESERVATION_TIME_STATUS.COMPLETED },
    { id: 40, time: '22:00', status: RESERVATION_TIME_STATUS.COMPLETED },
    { id: 41, time: '23:00', status: RESERVATION_TIME_STATUS.COMPLETED },
  ],
};

export const priorityOrder = [
  'petInfo.petName',
  'petInfo.petWeight',
  'petInfo.petClassification',
  'guardianInfo.guardianName',
  'guardianInfo.numOfVisitor',
  'guardianInfo.phoneNumber',
  'additionalInfo.funeralService.accompanny',
  'additionalInfo.funeralService.address',
  'additionalInfo.funeralService.detailedAddress',
  'guardianName',
  'phoneNumber',
];

export const exceptForNumberSymbols = ['e', 'E', '+', '-', ','];
