import { getStringFlatMap } from '@/utils/helpers/string.helper';

const PATHS = {
  HOME: 'home',
  LOGIN: 'login',
  SIGN_UP: 'sign-up',
  FIND: 'find',
  ACCOUNT: 'account',
  ANNOUNCEMENT: 'announcement',
  FAQ: 'faq',
  NEWS: 'news',
  INQUIRY: 'inquiry',
  PROJECT_MANAGEMENT: 'project-management',
  MY_PAGE: 'my-page',
  REDIRECT: (path: string) => `${path}/redirect`,
};

export const ROUTES = {
  self: `/`,
  HOME: `/${PATHS.HOME}`,
  LOGIN: `/${PATHS.LOGIN}`,
  SIGN_UP: {
    self: `/${PATHS.SIGN_UP}`,
    INDIVIDUAL: {
      self: `/${PATHS.SIGN_UP}/individual`,
      STEP_1: `/${PATHS.SIGN_UP}/individual/step-1`,
      STEP_2: `/${PATHS.SIGN_UP}/individual/step-2`,
      STEP_3: `/${PATHS.SIGN_UP}/individual/step-3`,
    },
    EXPERT: {
      self: `/${PATHS.SIGN_UP}/expert`,
      STEP_1: `/${PATHS.SIGN_UP}/expert/step-1`,
      STEP_2: `/${PATHS.SIGN_UP}/expert/step-2`,
      STEP_3: `/${PATHS.SIGN_UP}/expert/step-3`,
    },
  },
  FIND: {
    self: `/${PATHS.FIND}`,
    ID: `/${PATHS.FIND}/id`,
    PASSWORD: `/${PATHS.FIND}/password`,
  },
  ACCOUNT: {
    CHANGE_PASSWORD: `/${PATHS.ACCOUNT}/change-password`,
  },
  ANNOUNCEMENT: {
    self: `/${PATHS.ANNOUNCEMENT}`,
    DETAIL: `/${PATHS.ANNOUNCEMENT}/:id`,
  },
  FAQ: `/${PATHS.FAQ}`,
  NEWS: {
    self: `/${PATHS.NEWS}`,
    DETAIL: `/${PATHS.NEWS}/:id`,
  },
  INQUIRY: {
    self: `/${PATHS.INQUIRY}`,
    DETAIL: `/${PATHS.INQUIRY}/:id`,
  },
  PROJECT_MANAGEMENT: {
    self: `/${PATHS.PROJECT_MANAGEMENT}`,
    INDIVIDUAL: `/${PATHS.PROJECT_MANAGEMENT}/individual`,
    EXPERT: `/${PATHS.PROJECT_MANAGEMENT}/expert`,
    ACCEPT_MATCH: `/${PATHS.PROJECT_MANAGEMENT}/expert/accept-match`,
    DETAIL: {
      INDIVIDUAL: `/${PATHS.PROJECT_MANAGEMENT}/individual/:projectId`,
      EXPERT: `/${PATHS.PROJECT_MANAGEMENT}/expert/:projectId`,
    },
    EXPERT_MATCHING_LIST: `/${PATHS.PROJECT_MANAGEMENT}/individual/:projectId/expert-matching-list`,
  },
  PROJECT_REQUEST: {
    INDIVIDUAL: {
      REQUEST: `/${PATHS.PROJECT_MANAGEMENT}/individual/request`,
      EDIT: `/${PATHS.PROJECT_MANAGEMENT}/individual/edit/:projectId`,
    },
  },
  MY_PAGE: {
    self: `/${PATHS.MY_PAGE}`,
    INDIVIDUAL: {
      self: `/${PATHS.MY_PAGE}/individual`,
      INFO: `/${PATHS.MY_PAGE}/individual/info`,
      PASSWORD: `/${PATHS.MY_PAGE}/individual/password`,
      SCHEDULE: `/${PATHS.MY_PAGE}/individual/schedule`,
    },
    EXPERT: {
      self: `/${PATHS.MY_PAGE}/expert`,
      INFO: `/${PATHS.MY_PAGE}/expert/info`,
      PASSWORD: `/${PATHS.MY_PAGE}/expert/password`,
      SCHEDULE: `/${PATHS.MY_PAGE}/expert/schedule`,
    },
  },
};

export const CACHE_ROUTES = getStringFlatMap([ROUTES.PROJECT_MANAGEMENT, ROUTES.PROJECT_REQUEST, ROUTES.MY_PAGE]);

export const NAVIGATION_ROUTES = {
  SIGN_UP_STEP_3: PATHS.REDIRECT(`/sign-up/step-3`),
  CHANGE_PASSWORD: PATHS.REDIRECT(`/change-password`),
};
