export type SupplyType = {
  name: string;
  desc: string;
  note?: string;
  images: {
    title: string;
    link: string;
    cost?: {
      title: string;
      price: string;
    };
  }[];
  costs: {
    title: string;
    price: string;
    isBuy?: boolean;
    link?: string;
  }[];
};

export const SUPPLIES: SupplyType[] = [
  {
    name: '한지 유골 주머니',
    desc: '펫포레스트에서 자체 개발한 한지유골주머니입니다. 전주 한지는 스스로 습기와 통풍을 조절하여 유골의 영구 보존을 가능하게 합니다. 또한 매장 시에는 100% 생분해되는 자연 친화적인 특성을 가집니다. 한지 유골 주머니는 펫포레스트의 모든 장례 과정에 포함됩니다.',
    note: '· 한지유골주머니 특허출원 번호 10-2022-0082013',
    images: [
      {
        title: '한지 유골 주머니',
        link: '/images/charnel-house/supplies/ash-bag.jpg',
      },
    ],
    costs: [
      {
        title: '한지 유골 주머니 (소,중/대)',
        price: '30,000원 / 35,000원',
        isBuy: true,
        link: 'https://smartstore.naver.com/petforest_store/products/8489530525?',
      },
    ],
  },
  {
    name: '관',
    desc: '격식 있는 추모 의식을 위한 반려동물 전용 관입니다. 아이의 편안한 안식을 위해 따뜻한 오동나무 소재를 사용했습니다. 이별의 순간, 아이에게 전하고 싶은 말들을 직접 적어주세요.',
    images: [
      {
        title: '고급 오동나무관',
        link: '/images/charnel-house/supplies/coffin-1.jpg',
      },
      {
        title: '최고급 오동나무관',
        link: '/images/charnel-house/supplies/coffin-2.jpg',
      },
    ],
    costs: [
      {
        title: '고급 오동나무관(중/대/특대)',
        price: '200,000원 / 250,000원 / 300,000원',
      },
      {
        title: '최고급 오동나무관',
        price: '300,000원',
      },
    ],
  },
  {
    name: '요람',
    desc: '아이의 몸을 편안하게 뉘어 쉴 수 있도록 천연 버드나무 소재의 요람을 마련했습니다. 목화솜 이불을 깔아 푹신하고 따뜻한 보금자리가 될 수 있도록 준비했습니다.',
    images: [
      {
        title: '요람',
        link: '/images/charnel-house/supplies/cradle.jpg',
      },
    ],
    costs: [
      {
        title: '요람 (중/대)  ',
        price: '250,000원 / 300,000원',
      },
    ],
  },
  {
    name: '수의',
    desc: '반려동물의 장례 또한 사람과 다르지 않아야 한다는 생각으로 만든 전용 수의입니다. 명주, 인견 소재를 사용해 한 땀 한 땀 정성을 다해 제작했습니다.',
    images: [
      {
        title: '고급 수의',
        link: '/images/charnel-house/supplies/shround-1.jpg',
        cost: {
          title: '고급수의 (싸개/착용)',
          price: '150,000원',
        },
      },
      {
        title: '최고급 수의(블루)',
        link: '/images/charnel-house/supplies/shround-2.jpg',
        cost: {
          title: '최고급 수의 (싸개/착용)',
          price: '250,000원',
        },
      },
      {
        title: '최고급 수의(핑크)',
        link: '/images/charnel-house/supplies/shround-3.jpg',
        cost: {
          title: '최고급 수의 (싸개/착용)',
          price: '250,000원',
        },
      },
      {
        title: '대형 고급수의 (망토수의)',
        link: '/images/charnel-house/supplies/shround-4.jpg',
        cost: {
          title: '대형 고급수의 (망토수의)',
          price: '150,000원',
        },
      },
    ],
    costs: [
      {
        title: '고급수의 (싸개/착용)',
        price: '150,000원',
      },
      {
        title: '최고급 수의 (싸개/착용)',
        price: '250,000원',
      },
      {
        title: '대형 고급수의 (망토수의)',
        price: '150,000원',
      },
    ],
  },
  {
    name: '유골함',
    desc: '아이의 유골분을 담을 수 있는 유골함입니다. 가정에서 별도의 관리 없이 아이의 유골을 보존할 수 있도록 자체적으로 습기를 조절할 수 있도록 처리했습니다.',
    note: '· 모든 유골함에는 한지 유골 주머니가 기본으로 포함됩니다.',
    images: [
      {
        title: '목함',
        link: '/images/charnel-house/supplies/urn-1.jpg',
      },
      {
        title: '황토 유골함',
        link: '/images/charnel-house/supplies/urn-2.jpg',
      },
      {
        title: '포레스트 기능성 유골함',
        link: '/images/charnel-house/supplies/urn-3.jpg',
      },
    ],
    costs: [
      {
        title: '목함(소중함/따스함)',
        price: '300,000원 / 500,000원',
        isBuy: true,
        link: 'https://smartstore.naver.com/petforest_store/products/8489194070',
      },
      {
        title: '황토 유골함',
        price: '280,000원',
      },
      {
        title: '포레스트 기능성 유골함',
        price: '200,000원',
        isBuy: true,
        link: 'https://smartstore.naver.com/petforest_store/products/8489487698',
      },
    ],
  },
  {
    name: '하우징',
    desc: '봉안된 유골분을 외부 충격과 습기로부터 보호합니다. 안정감 있는 지붕 형태와 잔잔한 미색의 나뭇결이 편안한 느낌을 불러오도록 제작했습니다.',
    images: [
      {
        title: '하우징',
        link: '/images/charnel-house/supplies/housing.jpg',
      },
    ],
    costs: [
      {
        title: '하우징',
        price: '350,000원',
        isBuy: true,
        link: 'https://smartstore.naver.com/petforest_store/products/8489408971',
      },
    ],
  },
];
