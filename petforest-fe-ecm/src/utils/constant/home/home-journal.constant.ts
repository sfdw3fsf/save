type HomeJournalType = {
  category: string;
  content: string;
  img: string;
  link: string;
};

export const HOME_JOURNALS: HomeJournalType[] = [
  {
    category: '기타',
    content: '반려동물과 살아가기 #5. 이런 것들은 반려동물에게 위험할 지도 몰라요!',
    img: '/images/home/home-journal-1.jfif',
    link: '/journal/journal-detail/1',
  },
  {
    category: '기타',
    content: '반려동물과 살아가기 #4. 반려동물 제품, 단 하나만 추천한다면?  ',
    img: '/images/home/home-journal-2.jfif',
    link: '/journal/journal-detail/2',
  },
  {
    category: '기타',
    content: '반려동물과 살아가기 #3. 반려동물과 봄소풍, 무엇을 준비해야할까요?',
    img: '/images/home/home-journal-3.jfif',
    link: '/journal/journal-detail/3',
  },
  {
    category: '기타',
    content: '반려동물과 살아가기 #2. 반려동물과 식물, 함께 할 수 있을까요?',
    img: '/images/home/home-journal-4.jfif',
    link: '/journal/journal-detail/4',
  },
];
