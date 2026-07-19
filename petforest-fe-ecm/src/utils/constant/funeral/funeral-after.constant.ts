type FuneralAfterType = {
  img: string;
  title: string;
  subTitle: string;
  contents: string[];
  btnText: string;
  link: string;
};

export const FUNERAL_AFTERS: FuneralAfterType[] = [
  {
    img: '/images/funeral/funeral-after-1.jpg',
    title: '루세떼 제작',
    subTitle:
      '루세떼 장례를 선택하신 경우, 숙련된 루세떼 전문 장례지도사가 특허 받은 기술을 통해 유골분을 루세떼로 제작해 드립니다.',
    contents: [
      '루세떼 제작은 장례 시간 외 3~4시간이 추가로 소요됩니다.',
      '당일 일정이 어려운 경우 상담을 통해 예약 후 진행해드립니다.',
    ],
    btnText: '루세떼 알아보기',
    link: '/charnel-house/lucete',
  },
  {
    img: '/images/funeral/funeral-after-2.jpg',
    title: '봉안당 안치',
    subTitle: '장례가 끝난 유골분 또는 제작이 완료된 루세떼를 펫포레스트 봉안당에 안치 합니다.',
    contents: ['봉안당 선택시 포레스트 하우징과 고급 신주 명패를 제공해 드립니다. (경기 광주점)'],
    btnText: '봉안당 알아보기',
    link: '/charnel-house',
  },
];
