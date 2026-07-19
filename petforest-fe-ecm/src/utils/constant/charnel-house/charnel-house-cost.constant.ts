type CharnelHouseCostType = {
  notes: string[];
  tables: {
    title: string;
    costs: {
      content: string;
      price: string;
    }[];
  }[];
};

export const CHARNEL_HOUSE_COST_ONE: CharnelHouseCostType = {
  notes: [
    '모든 봉안당은 1년 약정이며 하우징이 포함된 가격 입니다.',
    '약정 기간 만료 후 1년 단위로 재계약이 가능합니다. (연 재계약 비용 22만 원)',
    '봉안당 이용 보호자에게는 정기 행사가 진행될 시 별도 안내해 드립니다.',
    '방문은 오전 9시부터 오후 6시까지 연중무휴 가능하며, 이외 시간 방문을 희망하는 경우 문의 해주십시오.',
    '자택 또는 다른 곳에 안치 중인 봉안함도 펫포레스트로 이함하신 후 납골당 안치가 가능합니다.',
  ],
  tables: [
    {
      title: '프리미엄 | 창측',
      costs: [
        { content: '1단', price: '500,000' },
        { content: '2단, 6단', price: '700,000' },
        { content: '3단, 4단, 5단', price: '900,000' },
      ],
    },
    {
      title: '럭셔리 | 실내측',
      costs: [
        { content: '1단', price: '400,000' },
        { content: '2단, 6단', price: '500,000' },
        { content: '3단, 4단, 5단', price: '600,000' },
      ],
    },
  ],
};

export const CHARNEL_HOUSE_COST_TWO: CharnelHouseCostType = {
  notes: [
    '약정 기간 만료 후 1년 단위로 재계약이 가능합니다. (연 재계약 비용 22만 원)',
    '봉안당 이용 보호자에게는 정기 행사가 진행될 시 별도 안내해 드립니다.',
    '방문은 오전 9시부터 오후 6시까지 연중무휴 가능하며, 이외 시간 방문을 희망하는 경우 문의 해주십시오.',
    '자택 또는 다른 곳에 안치 중인 봉안함도 펫포레스트로 이함하신 후 납골당 안치가 가능합니다.',
  ],
  tables: [
    {
      title: '프리미엄',
      costs: [
        { content: '7단', price: '300,000' },
        { content: '6단', price: '400,000' },
        { content: '1단, 5단', price: '500,000' },
        { content: '2단, 3단, 4단', price: '700,000' },
      ],
    },
  ],
};
