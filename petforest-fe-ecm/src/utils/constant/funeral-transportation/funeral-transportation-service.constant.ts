export const FUNERAL_TRANSPORTATION_CATEGORIES = ['운구 서비스', '비동행 장례'];

export const TRANSPORTATION_ACCOMPANIED = {
  content:
    '장례식장까지 차량 이동이 어렵거나 운전이 힘드신 보호자님을 위해 펫포레스트에서 운구 서비스를 지원합니다.\n자세한 내용은 상담 전화 1577-0996으로 문의 부탁드립니다.',
  subContent:
    "· 운구서비스는 출발하시는 곳에서 펫포레스트까지 오시는 '편도'만 운영하며, 장례가 끝난 후에는 일반 택시를 불러드립니다.",
  img: '/images/funeral/transportation/transportation-service.png',
};

export const TRANSPORTATION_UNACCOMPANIED = {
  content:
    '펫포레스트만의 비동행 장례 서비스는 사정상 장례식장에 방문하기 어려운 보호자님을 대신해,\n책임 장례지도사가 모든 장례 절차를 도맡아 진행하는 서비스입니다.',
  process: [
    {
      step: '예약',
      content: '상담 전화 1577-0996을 통해 비동행 장례 서비스를 신청합니다.',
      img: '/images/funeral/transportation/transportation-unaccompanied-1.png',
      note: '* 3일 이상 장례가 지연될 경우 임시 안치서비스를 이용할 수 있습니다.',
    },
    {
      step: '요청 장소 방문',
      content: '펫포레스트의 책임 장례지도사가 보호자님이 계신 위치로 출동합니다.',
      img: '/images/funeral/transportation/transportation-unaccompanied-2.png',
    },
    {
      step: '장례식장 이동',
      content: '펫포레스트 장례식장으로 안전하게 운구합니다.',
      img: '/images/funeral/transportation/transportation-unaccompanied-3.png',
    },
    {
      step: '장례 진행',
      content: '책임 장례지도사의 전담 하에 펫포레스트 장례 서비스를 기존과 동일하게 진행합니다.',
      img: '/images/funeral/transportation/transportation-unaccompanied-4.png',
    },
    {
      step: '촬영',
      content: '화장 절차를 포함한 모든 장례 과정을 촬영합니다.',
      img: '/images/funeral/transportation/transportation-unaccompanied-5.png',
    },
    {
      step: '유골함 인도',
      content: '장례 후 보호자님이 요청하신 장소로 유골함을 직접 인도해드립니다.',
      img: '/images/funeral/transportation/transportation-unaccompanied-6.png',
    },
  ],
};

type TransportationCostType = {
  title: string;
  costs: {
    content: string;
    price: string;
  }[];
};

export const TRANSPORTATION_COST_1: TransportationCostType[] = [
  {
    title: '서울 I 운구비용',
    costs: [
      {
        content: '강남,강동,서초,송파',
        price: '60,000',
      },
      {
        content: '관악,광진,금천,동작,성동,용산',
        price: '80,000',
      },
      {
        content: '마포,서대문,성북,동대문,양천,영등포,구로,은평,종로,중구,중랑,강서,강북,노원,도봉',
        price: '100,000',
      },
    ],
  },
  {
    title: '경기 I 운구비용',
    costs: [
      {
        content: '광주(동지역),용인(수지,기흥,모현읍),성남(분당)',
        price: '30,000',
      },
      {
        content: '광주,성남,용인,하남,이천,수원(영통구),화성(동탄)',
        price: '60,000',
      },
      {
        content: '과천,광명,수원,안양,화성,의왕,오산',
        price: '80,000',
      },
      {
        content: '인천,군포,남양주,구리,의정부,부천,시흥,안산,평택,안성,여주,양평,이천',
        price: '100,000',
      },
      {
        content: '김포,고양,가평,포천,연천,파주,동두천',
        price: '120,000',
      },
    ],
  },
];

export const TRANSPORTATION_COST_2: TransportationCostType[] = [
  {
    title: '서울 I 운구비용',
    costs: [
      {
        content: '강서,마포,양천,영등포',
        price: '60,000',
      },
      {
        content: '구로,금천구,관악구,동작구,서대문구,은평,용산,동작',
        price: '80,000',
      },
      {
        content: '서초,강남,송파,강동,광진,성동,중구,종로,성북,동대문,강북,도봉,노원,중랑',
        price: '100,000',
      },
    ],
  },
  {
    title: '경기 I 운구비용',
    costs: [
      {
        content: '김포',
        price: '40,000',
      },
      {
        content: '인천(서구,계양),고양,부천,파주(운정신도시)',
        price: '60,000',
      },
      {
        content: '인천,시흥,광명,파주',
        price: '80,000',
      },
      {
        content: '안산,과천,수원,안양,의왕,군포,남양주,구리,의정부,성남,하남,수원,화성',
        price: '100,000',
      },
      {
        content: '광주,용인,오산,평택,안성,이천,여주,연천,포천,가평,양평',
        price: '120,000',
      },
    ],
  },
];
