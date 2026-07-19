import { COLORS } from '@/theme';
import { SALES_CALENDAR_STATUS } from '@/utils/enums/collaborating/sales-mngt.enums';

export const SalesStatusScheduleOptions = [
  { value: SALES_CALENDAR_STATUS.NEW_SALES, color: COLORS.orange.honey },
  { value: SALES_CALENDAR_STATUS.REQUEST, color: COLORS.purple.lavender },
  { value: SALES_CALENDAR_STATUS.TRANSMISSION, color: COLORS.green.seafoam },
];
