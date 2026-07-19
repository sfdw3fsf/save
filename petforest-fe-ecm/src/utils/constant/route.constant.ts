export enum ROUTE_GROUP {
  PRIVACY = '/privacy',
  TERMS = '/terms',
  FUNERAL = '/funeral',
  CHARNEL_HOUSE = '/charnel-house',
  BRANCH = '/branch',
  CUSTOMER_SERVICE = '/customer-service',
  ABOUT = '/about',
  RESERVATION = '/reservation',
}

export const MY_ROUTE = {
  PRIVACY: `${ROUTE_GROUP.PRIVACY}`,
  TERMS: `${ROUTE_GROUP.TERMS}`,
  FUNERAL: {
    self: `${ROUTE_GROUP.FUNERAL}`,
    HOME: `${ROUTE_GROUP.FUNERAL}?id=process`,
    EXPENSE: `${ROUTE_GROUP.FUNERAL}?id=expense`,
    SPECIAL: `${ROUTE_GROUP.FUNERAL}?id=special`,
    TRANSPORTATION: `${ROUTE_GROUP.FUNERAL}/transportation`,
  },
  CHARNEL_HOUSE: {
    HOME: `${ROUTE_GROUP.CHARNEL_HOUSE}`,
    LUCETE: `${ROUTE_GROUP.CHARNEL_HOUSE}/lucete`,
    SUPPLIES: `${ROUTE_GROUP.CHARNEL_HOUSE}/supplies`,
  },
  BRANCH: {
    BRANCH_1: `${ROUTE_GROUP.BRANCH}/branch-1`,
    BRANCH_2: `${ROUTE_GROUP.BRANCH}/branch-2`,
    // BRANCH_3: `${ROUTE_GROUP.BRANCH}/branch-3`,
    HERB_CENTER: `${ROUTE_GROUP.BRANCH}/herb-center`,
  },
  CUSTOMER_SERVICE: {
    NOTICE: `${ROUTE_GROUP.CUSTOMER_SERVICE}/notice`,
    FAQ: `${ROUTE_GROUP.CUSTOMER_SERVICE}/faq`,
  },
  ABOUT: {
    BRAND_STORY: `${ROUTE_GROUP.ABOUT}/brand-story`,
    HISTORY: `${ROUTE_GROUP.ABOUT}/history`,
    ARTICLE: `${ROUTE_GROUP.ABOUT}/article`,
    JOURNAL: `${ROUTE_GROUP.ABOUT}/journal`,
  },
  RESERVATION: {
    HOME: `${ROUTE_GROUP.RESERVATION}`,
    CHECK: `${ROUTE_GROUP.RESERVATION}/check`,
    RESERVE: `${ROUTE_GROUP.RESERVATION}/reserve`,
    RESERVE_NAVER: `${ROUTE_GROUP.RESERVATION}/reserve-naver`,
  },
};
