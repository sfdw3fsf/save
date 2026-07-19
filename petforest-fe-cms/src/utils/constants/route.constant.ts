export enum ROUTE_GROUP {
  DASHBOARD = 'dashboard',
  FUNERAL = 'funeral',
  SETTLEMENT = 'settlement',
  OSSUARY = 'ossuary',
  MATERIAL = 'material',
  FACILITY = 'facility',
  COLLABORATING = 'collaborating',
  POST = 'post',
  STATISTIC = 'statistic',
  MANAGER = 'manager',
  SETTING = 'setting',
}

export const MY_ROUTE = {
  NO_PERMISSION: '/no-permission',
  AUTH: {
    LOGIN: '/auth/login',
  },
  HOME: `/`,
  DASHBOARD: `/${ROUTE_GROUP.DASHBOARD}`,
  FUNERAL: {
    self: `/${ROUTE_GROUP.FUNERAL}`,
    FUNERAL_RESERVATION: `/${ROUTE_GROUP.FUNERAL}/reservation`,
    FUNERAL_PNT_SETTING: `/${ROUTE_GROUP.FUNERAL}/pnt-setting`,
    FUNERAL_LUCETTE: `/${ROUTE_GROUP.FUNERAL}/lucette`,
    FUNERAL_STOCK: `/${ROUTE_GROUP.FUNERAL}/stock`,
    FUNERAL_NOTI_HISTORY: `/${ROUTE_GROUP.FUNERAL}/notification-history`,
  },
  SETTLEMENT: {
    self: `/${ROUTE_GROUP.SETTLEMENT}`,
    SETTLEMENT_ADJUSTMENT: `/${ROUTE_GROUP.SETTLEMENT}/adjustment`,
    SETTLEMENT_COUPON: `/${ROUTE_GROUP.SETTLEMENT}/coupon`,
  },
  OSSUARY: `/${ROUTE_GROUP.OSSUARY}`,
  MATERIAL: {
    self: `/${ROUTE_GROUP.MATERIAL}`,
    MATERIAL_FUNERAL_SUPPLIES: `/${ROUTE_GROUP.MATERIAL}/funeral-supplies`,
    MATERIAL_LUCETTE_SUPPLIES: `/${ROUTE_GROUP.MATERIAL}/lucette-supplies`,
    MATERIAL_OTHER_FUNERAL_SUPPLIES: `/${ROUTE_GROUP.MATERIAL}/other-funeral-supplies`,
    MATERIAL_EXPENDABLES_SUPPLIES: `/${ROUTE_GROUP.MATERIAL}/expendables-supplies`,
    HISTORY: `/${ROUTE_GROUP.MATERIAL}/history`,
    ARTICLE: `/${ROUTE_GROUP.MATERIAL}/article`,
    JOURNAL: `/${ROUTE_GROUP.MATERIAL}/journal`,
  },
  FACILITY: `/${ROUTE_GROUP.FACILITY}`,
  COLLABORATING: {
    self: `/${ROUTE_GROUP.COLLABORATING}`,
    COLLABORATING_SALES_CALENDAR: `/${ROUTE_GROUP.COLLABORATING}/sales-calendar`,
    COLLABORATING_PARTNER_HOSPITAL: `/${ROUTE_GROUP.COLLABORATING}/partner-hospital`,
    COLLABORATING_SALE_LIST: `/${ROUTE_GROUP.COLLABORATING}/sale`,
    COLLABORATING_DELIVERY_PROMOTIONAL_MATERIAL: `/${ROUTE_GROUP.COLLABORATING}/delivery-promotional-material`,
    COLLABORATING_SETTLEMENT: `/${ROUTE_GROUP.COLLABORATING}/hospital-settlement`,
  },
  POST: `/${ROUTE_GROUP.POST}`,
  STATISTIC: {
    self: `/${ROUTE_GROUP.STATISTIC}`,
    STATISTIC_BASIC: `/${ROUTE_GROUP.STATISTIC}/basic`,
    STATISTIC_REGION: `/${ROUTE_GROUP.STATISTIC}/region`,
    STATISTIC_ARTICLE: `/${ROUTE_GROUP.STATISTIC}/article`,
  },
  MANAGER: {
    self: `/${ROUTE_GROUP.MANAGER}`,
    MANAGER_RESERVATION_TIME: `/${ROUTE_GROUP.MANAGER}/reservation-time`,
    MANAGER_USER: `/${ROUTE_GROUP.MANAGER}/user`,
    MANAGER_BRANCH: `/${ROUTE_GROUP.MANAGER}/branch`,
  },
  SETTING: `/${ROUTE_GROUP.SETTING}`,
  RECEIVE_RESERVATION: '',
};

export const authRoutes = ['/auth/login'];
export const viewModeRoutes = [
  '/settlement/adjustment/additional-cost',
  '/collaborating/delivery-promotional-material/summary',
  '/collaborating/hospital-settlement/summary',
  '/no-permission',
  '/auth/change-password',
  '/funeral/reservation/non-accompany',
];
export const fullScreenModeRoutes = ['/funeral/pnt-setting/preview', '/pnt-link'];
export const openNewTabRoutes = ['/funeral/reservation', '/collaborating/partner-hospital', '/settlement/coupon'];
