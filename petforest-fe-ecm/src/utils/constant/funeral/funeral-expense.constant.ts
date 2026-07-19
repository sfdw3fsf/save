type FuneralExpenseType = {
  title: string;
  price: string;
  content: string;
  note?: string;
  img: string;
};

export const FUNERAL_EXPENSES: FuneralExpenseType[] = [
  {
    title: '스탠다드 장례',
    price: '350,000',
    content: '공통 장례 서비스 + 기본 유골함 + 고급 보자기',
    img: '/images/funeral/funeral-expense-1.jpg',
  },
  {
    title: '포레스트 장례',
    price: '700,000',
    content: '공통 장례 서비스 + *고급 수의 + 고급 오동나무관 + 포레스트 유골함 + 포레스트 액자 + 최고급 보자기 + 발도장 클레이',
    note: '*요람+꽃다발로 대체가능',
    img: '/images/funeral/funeral-expense-2.jpg',
  },
  {
    title: '프리미엄 장례',
    price: '1,400,000',
    content:
      '공통 장례 서비스 + 최고급 수의 + 최고급 오동나무관 + 포레스트 기능성 유골함 + 꽃다발 + 포레스트 하우징 + 포레스트 액자 + 최고급 보자기 + 발도장 클레이',
    img: '/images/funeral/funeral-expense-3.jpg',
  },
  {
    title: '스탠다드 루세떼 장례',
    price: '1,000,000',
    content: '공통 장례 서비스 + 스탠다드 장례 + 루세떼 제작 + 유리병 + 루세떼 보증서',
    img: '/images/funeral/funeral-expense-4.jpg',
  },
  {
    title: '포레스트 루세떼 장례',
    price: '1,350,000',
    content:
      '공통 장례 서비스 + *고급 수의 + 고급 오동나무관 + 포레스트 액자 + 루세떼 제작 + 유리병 + 루세떼 보증서 + 발도장 클레이',
    note: '*요람+꽃다발로 대체가능',
    img: '/images/funeral/funeral-expense-5.jpg',
  },
  {
    title: '프리미엄 루세떼 장례',
    price: '1,750,000',
    content:
      '공통 장례 서비스 + 최고급 수의 + 최고급 오동나무관 + 꽃다발 + 호두나무 루세떼함 + 포레스트 액자 + 루세떼 제작 + 루세떼 보증서 + 발도장 클레이',
    img: '/images/funeral/funeral-expense-6.jpg',
  },
];
