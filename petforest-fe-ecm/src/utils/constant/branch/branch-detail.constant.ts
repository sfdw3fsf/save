export type BranchDetailHashtagType = {
  title: string;
  content?: string;
  link?: string;
  image?: string;
};

export type BranchDetailType = {
  name: string;
  link?: string;
  isOpenNewTab?: boolean;
  introImages: {
    title: string;
    link: string;
  }[];
  certificate: string;
  address: string;
  addressLink: string;
  addressLinkKakao?: string;
  openHour?: string;
  phone: string;
  reservationText: string;
  hashTags?: BranchDetailHashtagType[];
};

export const BRANCH_HASH_TAG: BranchDetailHashtagType[] = [
  {
    title: '#꽃다발 서비스',
    content: '추모 과정에서 아이에게 꽂장식을 하고 싶은 보호자님을 위한 서비스입니다.',
    link: '/funeral?activeService=flower',
    image: '/images/branch/flowers.jpeg',
  },
  {
    title: '#임시안치',
    content:
      '반려동물이 무지개 다리를 건넌 후 바로 장례를 진행하기 어려운 경우, 장례식장 내 안치실에서 장례식 당일까지 안전하게 안치해 드리는 서비스입니다.',
    link: '/funeral?activeService=temporary',
    image: '/images/funeral/funeral-special-service-5.jpg',
  },
  {
    title: '#루세떼',
    content:
      "'밝게 빛나라'라는 의미를 가진 추모보석 서비스입니다. 조금 더 가까운 곳에서 반려동물을 기억할 수 있도록 펫포레스트가 함께하겠습니다.",
    link: '/charnel-house/lucete',
    image: '/images/charnel-house/lucete/lucete-top-1.jpg',
  },
  {
    title: '#비동행 장례',
    content:
      '펫포레스트의 비동행 장례 서비스는 사정상 장례식장에 방문하기 어려운 보호자님을 대신해, 책임 장례지도사가 모든 장례 절차를 도맡아 진행하는 서비스입니다.',
    link: '/funeral/transportation?activeService=unaccompanied',
    image: '/images/funeral/transportation/transportation-unaccompanied-3.png',
  },
  {
    title: '#봉안당',
    content: '펫포레스트의 봉안당은 가장 볕이 잘 드는 곳에 있습니다. 보호자님과 반려동물을 위한 소중한 추모의 공간입니다.',
    link: '/charnel-house/',
    image: '/images/charnel-house/gwangju-1.jpg',
  },
  {
    title: '#운구서비스',
    content: '펫포레스트 운구서비스는 장례식장까지 이동이 어렵거나 운전이 힘드신 보호자님을 위한 차량 서비스입니다.',
    link: '/funeral/transportation',
    image: '/images/funeral/transportation/transportation-service.png',
  },
];

export const BRANCH_1_DETAIL: BranchDetailType = {
  name: '경기 광주점',
  link: '/reservation/reserve?activeBranch=1',
  introImages: [
    {
      title: '서울 · 경기광주점 전경',
      link: '/images/branch/branch-1/1.jpg',
    },
    {
      title: '본관 입구',
      link: '/images/branch/branch-1/2.jpg',
    },
    {
      title: '본관 추모실',
      link: '/images/branch/branch-1/3.jpg',
    },
    {
      title: '본관 라운지',
      link: '/images/branch/branch-1/4.jpg',
    },
    {
      title: '봉안당 전경',
      link: '/images/branch/branch-1/5.jpg',
    },
    {
      title: '별관 입구',
      link: '/images/branch/branch-1/6.jpg',
    },
    {
      title: '별관 상담실',
      link: '/images/branch/branch-1/7.jpg',
    },
    {
      title: '별관 라운지',
      link: '/images/branch/branch-1/8.jpg',
    },
  ],
  certificate: '/images/branch/branch-1/certificate.jpg',
  address: '경기도 광주시 오포안로 77',
  addressLink: 'https://map.naver.com/p/entry/place/689184344?placePath=%2Fhome&c=15.00,0,0,0,dh',
  addressLinkKakao:
    'https://m.map.kakao.com/actions/routeView?exEnc=MSMUUM&eyEnc=QOQMOTN&endLoc=%EB%B0%98%EB%A0%A4%EB%8F%99%EB%AC%BC%EC%9E%A5%EB%A1%80%EC%8B%9D%EC%9E%A5%20%ED%8E%AB%ED%8F%AC%EB%A0%88%EC%8A%A4%ED%8A%B8&ids=%2CP2058732006',
  openHour: '오전 9시 ~ 오후 9시',
  phone: '1577-0996 (오전7시 - 오후 11시)',
  reservationText: '광주점 장례예약 >',
};
export const BRANCH_2_DETAIL: BranchDetailType = {
  name: '김포점',
  link: '/reservation/reserve?activeBranch=2',

  introImages: [
    {
      title: '김포점',
      link: '/images/branch/branch-2/1.jfif',
    },
    {
      title: '로비',
      link: '/images/branch/branch-2/2.jpg',
    },
    {
      title: '상담실',
      link: '/images/branch/branch-2/3.jpg',
    },
    {
      title: '추모실',
      link: '/images/branch/branch-2/4.jpg',
    },
    {
      title: '대기실',
      link: '/images/branch/branch-2/5.jpg',
    },
    {
      title: '봉안당 전경',
      link: '/images/branch/branch-2/6.jpg',
    },
    {
      title: '옥상 휴게실 전경',
      link: '/images/branch/branch-2/7.jpg',
    },
    {
      title: '라운지 1',
      link: '/images/branch/branch-2/8.jpg',
    },
    {
      title: '라운지 2',
      link: '/images/branch/branch-2/9.jpg',
    },
  ],
  certificate: '/images/branch/branch-2/certificate.jpg',
  address: '경기도 김포시 통진읍 고정로 308',
  addressLink: 'https://map.naver.com/p/entry/place/13019894?c=19.00,0,0,0,dh',
  addressLinkKakao:
    'https://map.kakao.com/?urlX=412073&urlY=1173830&urlLevel=3&itemId=11935922&q=%ED%8E%AB%ED%8F%AC%EB%A0%88%EC%8A%A4%ED%8A%B8%20%EB%B0%98%EB%A0%A4%EB%8F%99%EB%AC%BC%EC%9E%A5%EB%A1%80%EC%8B%9D%EC%9E%A5%20%EA%B9%80%ED%8F%AC%EC%A0%90&srcid=11935922&map_type=TYPE_MAP',
  openHour: '오전 9시 ~ 오후 9시',
  phone: '1577-0996 (오전7시 - 오후 11시)',
  reservationText: '김포점 장례예약 >',
};
export const BRANCH_3_DETAIL: BranchDetailType = {
  name: '남양주점',
  link: '/reservation/reserve?activeBranch=3',

  introImages: [
    {
      title: '남양주점',
      link: '/images/branch/branch-3/1.jpg',
    },
    {
      title: '로비',
      link: '/images/branch/branch-3/2.jpg',
    },
    {
      title: '상담실',
      link: '/images/branch/branch-3/3.jpg',
    },
    {
      title: '추모실',
      link: '/images/branch/branch-3/4.jpg',
    },
    {
      title: '대기실',
      link: '/images/branch/branch-3/5.jpg',
    },
    {
      title: '봉안당 전경',
      link: '/images/branch/branch-3/6.jpg',
    },
    {
      title: '옥상 휴게실 전경',
      link: '/images/branch/branch-3/7.jpg',
    },
  ],
  certificate: '',
  address: '경기도 남양주시 와부읍 월문리 114-1',
  addressLink: '',
  addressLinkKakao: '',
  openHour: '오전 9시 ~ 오후 9시',
  phone: '1577-0996 (오전7시 - 오후 11시)',
  reservationText: '남양주점 장례예약 >',
};

export const HERB_CENTER_DETAIL: BranchDetailType = {
  name: '펫포레스트 허브센터',
  link: 'https://class.petforest.kr/main/index.jsp',
  isOpenNewTab: true,
  introImages: [
    {
      title: '허브센터 교육장',
      link: '/images/branch/herb-center/1.png',
    },
    {
      title: '허브센터 교육장',
      link: '/images/branch/herb-center/2.png',
    },
    {
      title: '펫포레스트 허브센터',
      link: '/images/branch/herb-center/3.png',
    },
  ],
  address: '경기 성남시 분당구 판교공원로2길 22 7층',
  addressLink:
    'https://map.naver.com/p/directions/-/14147835.6927399,4493517.340942,%EA%B2%BD%EA%B8%B0%20%EC%84%B1%EB%82%A8%EC%8B%9C%20%EB%B6%84%EB%8B%B9%EA%B5%AC%20%ED%8C%90%EA%B5%90%EA%B3%B5%EC%9B%90%EB%A1%9C2%EA%B8%B8%2022,,ADDRESS_POI/-/transit?c=19.00,0,0,0,dh',
  phone: '031-704-5171',
  certificate: '',
  reservationText: '허브센터 더알아보기 >',
  hashTags: [
    {
      title: '#반려동물장례지도사자격증과정',
    },
    {
      title: '#반려동물 장례지도사',
    },
    {
      title: '#자격증과정',
    },
  ],
};
