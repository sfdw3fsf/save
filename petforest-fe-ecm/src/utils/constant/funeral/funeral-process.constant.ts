type FuneralProcessType = {
  step: string;
  title: string;
  contents: string[];
  img: string;
};

export const FUNERAL_PROCESSES: FuneralProcessType[] = [
  {
    step: '01. 장례 예약',
    title: '상담 전화 1577-0996 또는 홈페이지를 통해 장례를 예약합니다.',
    contents: [
      '특별한 외상이 없는 경우 1-2일 정도는 자택에서 함께 시간을 보낸 뒤 장례를 진행하셔도 괜찮습니다.',
      '3일 이상 장례가 지연될 경우 펫포레스트 임시 안치 서비스를 이용하실 수 있습니다.',
    ],
    img: '/images/funeral/funeral-process-1.jpg',
  },
  {
    step: '02. 장례식장 방문',
    title: '예약한 일정에 맞춰 장례식장으로 방문합니다.',
    contents: ['차량 이동이 어렵거나 운전이 힘드신 경우 펫포레스트 운구 서비스를 이용하실 수 있습니다.'],
    img: '/images/funeral/funeral-process-2.jpg',
  },
  {
    step: '03. 상담',
    title: '시작부터 마지막까지 장례의 모든 과정을 책임 장례지도사가 함께합니다.',
    contents: ['장례용품 추가를 원하실 경우 상담 시 직접 보고 선택할 수 있습니다.'],
    img: '/images/funeral/funeral-process-3.jpg',
  },
  {
    step: '04. 염습 및 입관',
    title:
      '반려동물의 생전 모습으로 인사 나누실 수 있도록 최소한의 손길로 몸을 깨끗하고 단정하게 염습 후 준비된 관에 입관합니다.',
    contents: ['장례용품을 추가 선택 시 입복 및 입관을 함께 진행합니다.'],
    img: '/images/funeral/funeral-process-4.jpg',
  },
  {
    step: '05. 추모 예식',
    title: '독립된 단독 추모실에서 반려동물과 마지막 인사를 나눕니다.',
    contents: [
      '보호자께서 충분한 인사를 나누실 수 있습니다.',
      '반려동물의 사진을 미리 전달해주시면 슬라이드 영상으로 상영해드립니다.',
      '종교 예식을 진행하실 경우 간단한 종교 물품을 준비해드립니다.',
    ],
    img: '/images/funeral/funeral-process-5.jpg',
  },
  {
    step: '06. 화장 및 수·분골',
    title:
      '개별 화장을 원칙으로 아이의 마지막 모습을 보호자님께서 모두 확인하실 수 있습니다. 화장이 끝난 유골은 수습 후 책임 장례지도사가 수작업으로 수·분골을 진행합니다.',
    contents: ['최신 반려동물 전용 화장 시설을 도입해 유골 손실이 없습니다.'],
    img: '/images/funeral/funeral-process-6.jpg',
  },
  {
    step: '07. 봉안 및 인도',
    title: '유골분이 변질되지 않도록 한지 유골 주머니에 담아 유골함에 봉안하여 보호자님께 안전하게 인도합니다.',
    contents: ['농림축산식품부 허가, 합법 반려동물 장례식장에서 장례를 진행하였음을 증명하는 장례(화장) 증명서가 발급됩니다.'],
    img: '/images/funeral/funeral-process-7.jpg',
  },
];
