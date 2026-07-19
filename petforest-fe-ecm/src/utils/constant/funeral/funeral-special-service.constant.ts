export type FuneralSpecialServiceType = {
  category: string;
  content: string;
  note?: string;
  img: string;
};

export const FUNERAL_SPECIAL_SERVICES = [
  {
    category: '한지 유골 주머니',
    content:
      '펫포레스트에서 자체 개발한 한지 유골 주머니입니다. 한지 전문 예술가의 손으로 만든 전주 한지는 스스로 습기와 통풍을 조절하며, 강인하고 질긴 특성으로 유골의 영구 보존을 가능하게 합니다. 매장 시에는 100% 생분해되는 자연 친화적인 특성을 가집니다. 한지 유골 주머니 서비스는 펫포레스트의 모든 장례 과정에 포함됩니다.',
    note: '한지유골주머니 특허출원 번호 10-2022-0082013',
    img: '/images/funeral/funeral-special-service-1.jpg',
  },
  {
    category: '모바일 부고장',
    content:
      '모바일 부고장은 가족 및 친지들에게 반려동물의 부고를 알릴 수 있는 서비스입니다. 부고장을 받은 이는 방명록을 작성할 수 있으며, 조의금을 보낼 수도 있습니다. 받은 조의금은 장례 금액에 자동 합산되어 정산됩니다. 모바일 부고장 서비스는 펫포레스트의 모든 장례 과정에 포함됩니다.',
    img: '/images/funeral/funeral-special-service-2.jpg',
  },
  {
    category: '소동물 장례',
    content:
      '펫포레스트의 소동물 장례 ‘리틀포레스트’는 크기는 작아도 사랑하는 마음은 같다는 일념으로 작은 동물을 조금 더 배려하는 소동물 맞춤 장례입니다. 작고 약한 특성을 고려하여 소동물 전용관과 수의를 제공해 드리며 보다 섬세하고 세심한 장례를 진행합니다.',
    img: '/images/funeral/funeral-special-service-3.jpg',
  },
  {
    category: '꽃다발 서비스',
    content:
      '펫포레스트 꽃다발 서비스는 추모 과정에서 아이에게 꽃장식을 하고자 하시는 보호자님께 장례 당일 꽃 농원에서 수급한 계절 꽃다발을 준비해 드리며, 보호자님께서 직접 꽃장식을 꾸미실 수 있도록 꽃 가위를 제공해드립니다.\n장례 예약 시 함께 신청하지 않은 경우 서비스가 어려울 수 있습니다.',
    note: '꽃다발 비용 6만 원(VAT 포함)',
    img: '/images/funeral/funeral-special-service-4.jpg',
  },
  {
    category: '임시 안치',
    content:
      '반려동물이 무지개 다리를 건넌 후 바로 장례를 진행하기 어려운 경우, 장례식장 내 안치실에서 장례식 당일까지 안전하게 안치해드리는 서비스입니다. 염습 후 임시 운구함을 이용, 전용 냉장 시설에서 안전하게 안치합니다.',
    note: '1일 3만 원(VAT 포함)',
    img: '/images/funeral/funeral-special-service-5.jpg',
  },
];
