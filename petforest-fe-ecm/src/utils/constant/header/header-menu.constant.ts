import { MY_ROUTE, ROUTE_GROUP } from '../route.constant';

export type HeaderMenuType = {
  title: string;
  link: string;
  group: string;
  subMenus: Omit<HeaderMenuType, 'subMenus' | 'group'>[];
};

export const HEADER_MENUS: HeaderMenuType[] = [
  {
    title: '장례 서비스',
    link: MY_ROUTE.FUNERAL.self,
    group: ROUTE_GROUP.FUNERAL,
    subMenus: [
      { title: '장례 절차', link: MY_ROUTE.FUNERAL.HOME },
      { title: '장례 비용', link: MY_ROUTE.FUNERAL.EXPENSE },
      { title: '특별한 서비스', link: MY_ROUTE.FUNERAL.SPECIAL },
      { title: '차량 서비스', link: MY_ROUTE.FUNERAL.TRANSPORTATION },
    ],
  },
  {
    title: '기억의 정원',
    link: MY_ROUTE.CHARNEL_HOUSE.HOME,
    group: ROUTE_GROUP.CHARNEL_HOUSE,
    subMenus: [
      { title: '봉안당', link: MY_ROUTE.CHARNEL_HOUSE.HOME },
      { title: '루세떼', link: MY_ROUTE.CHARNEL_HOUSE.LUCETE },
      { title: '장례 용품', link: MY_ROUTE.CHARNEL_HOUSE.SUPPLIES },
    ],
  },
  {
    title: '지점 및 시설 안내',
    link: MY_ROUTE.BRANCH.BRANCH_1,
    group: ROUTE_GROUP.BRANCH,
    subMenus: [
      { title: '경기 광주점', link: MY_ROUTE.BRANCH.BRANCH_1 },
      { title: '김포점', link: MY_ROUTE.BRANCH.BRANCH_2 },
      // { title: '남양주점', link: MY_ROUTE.BRANCH.BRANCH_3 },
      { title: '허브센터', link: MY_ROUTE.BRANCH.HERB_CENTER },
    ],
  },
  {
    title: '고객센터',
    link: MY_ROUTE.CUSTOMER_SERVICE.NOTICE,
    group: ROUTE_GROUP.CUSTOMER_SERVICE,
    subMenus: [
      { title: '공지사항', link: MY_ROUTE.CUSTOMER_SERVICE.NOTICE },
      { title: '자주하는 질문', link: MY_ROUTE.CUSTOMER_SERVICE.FAQ },
    ],
  },
  {
    title: '펫포레스트',
    link: MY_ROUTE.ABOUT.BRAND_STORY,
    group: ROUTE_GROUP.ABOUT,
    subMenus: [
      { title: '브랜드 스토리', link: MY_ROUTE.ABOUT.BRAND_STORY },
      { title: '히스토리', link: MY_ROUTE.ABOUT.HISTORY },
      { title: '언론 보도', link: MY_ROUTE.ABOUT.ARTICLE },
      { title: '저널', link: MY_ROUTE.ABOUT.JOURNAL },
    ],
  },
];
