type HomeBranchType = {
  name: string;
  address: string;
  img: string;
  isNew: boolean;
  link: string;
};

export const HOME_BRANCHES: HomeBranchType[] = [
  {
    name: '경기 광주점',
    address: '경기도 광주시 오포안로 77',
    img: '/images/home/gwangju-branch.png',
    link: '/branch/branch-1',
    isNew: false,
  },
  {
    name: '경기 김포점',
    address: '경기도 김포시 통진읍 고정로 308',
    img: '/images/home/gimpo-branch.png',
    link: '/branch/branch-2',
    isNew: true,
  },
  // {
  //   name: '경기 남양주점',
  //   address: '경기도 남양주시 와부읍 월문리 114-1',
  //   img: '/images/branch/branch-3/1.jpg',
  //   link: '/branch/branch-3',
  //   isNew: true,
  // },
  {
    name: '펫포레스트 허브센터',
    address: '경기도 성남시 분당구 판교공원로2길 22, 7층',
    img: '/images/home/herb-center.jpg',
    link: '/branch/herb-center',
    isNew: false,
  },
];
