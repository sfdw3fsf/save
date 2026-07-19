type HomeServiceBannerType = {
  title: string;
  content: string;
  btnText: string;
  img: string;
  link: string;
};

export const HOME_SERVICE_BANNERS: HomeServiceBannerType[] = [
  {
    title: '온전한 위로를 위한 펫포레스트의 공간',
    content: '펫포레스트는 보호자와 반려동물의 이별을 추모하기 위한 기억의 숲입니다.',
    btnText: '시설 둘러보기',
    img: '/images/home/home-service-1.jpg',
    link: '/branch/branch-1',
  },
  {
    title: '일대일 책임 장례지도사 제도',
    content: '장례 예식의 모든 과정을 전문 장례지도사가 위로와 존중의 마음으로 책임 장례를\n진행합니다.',
    btnText: '장례 절차 알아보기',
    img: '/images/home/home-service-2.jpg',
    link: '/funeral',
  },
  {
    title: '반려동물을 기억하는 또 다른 방법',
    content: '추모보석은 반려동물의 유골분으로 만든 영원한 기억의 약속 입니다.',
    btnText: '루세떼 알아보기',
    img: '/images/home/home-service-3.jpg',
    link: '/charnel-house/lucete',
  },
  {
    title: '보호자 맞춤형 ‘차량’ 서비스',
    content: '슬픔이 머문 자리마다 펫포레스트가 먼저 달려 가겠습니다.',
    btnText: '차량 서비스 알아보기',
    img: '/images/home/home-service-4.jpg',
    link: '/funeral/transportation',
  },
];
