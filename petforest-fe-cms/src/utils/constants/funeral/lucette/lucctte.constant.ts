import { COLORS } from '@/theme';
import { LUCETTE_CATEGORY } from '@/utils/enums/funeral/lucette-mngt.enums';
import { ENUM_STATUS } from '@/utils/enums/status.enum';

export const LucetteStatusScheduleOptions = [
  { value: ENUM_STATUS.COMPLETE, color: COLORS.blue[500] },
  { value: ENUM_STATUS.INCOMPLETE, color: COLORS.green.main },
];

export const LucetteProcessMap = {
  sameDay: LUCETTE_CATEGORY.ON_THE_DAY,
  schedulePending: LUCETTE_CATEGORY.SCHEDULE_PENDING,
  processPending: LUCETTE_CATEGORY.PROCESS_PENDING,
  standalone: LUCETTE_CATEGORY.EXCLUSIVE,
};
