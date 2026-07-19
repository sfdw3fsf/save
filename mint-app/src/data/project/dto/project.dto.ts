import { TIMESLOT_ENUM } from '@/utils/enums/project/meeting-format.enum';
import { PROJECT_STATICS_ENUM } from '@/utils/enums/project/project-list.enum';
import { USER_ROLE } from '@/utils/enums/user/user-role.enum';
import { Meeting } from '../response/project.response';

export type ProjectStatisticsDTO = {
  status: PROJECT_STATICS_ENUM;
  value: number;
};

// This type is used for calendar
export type MeetingScheduleDTO = {
  id: number;
  start?: Date;
  end?: Date;
  url?: string;
  proposalCreator?: USER_ROLE;
  type?: TIMESLOT_ENUM;
};

export type InitialMeeting = {
  meeting: Meeting;
};
