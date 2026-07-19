import { UserInfo } from '@/types/user-info.type';
import { ERROR_MESSAGE } from '@/utils/constants/data.constant';
import { USER_ROLE } from '@/utils/enums/user/user-role.enum';
import { USER_STATUS } from '@/utils/enums/user/user-status.enum';
import dayjs from 'dayjs';
import { UserCalendarMeetingDTO } from './dto/user-calendar-meeting.dto';
import { UserCalendarMeetingRESP } from './response/user-calendar-meeting.response';
import { UserDetailsRESP } from './response/user-details.response';

export const transformUserDetailsResponse = (response: UserDetailsRESP): UserInfo => {
  if (!(response.userRole in USER_ROLE)) throw new Error(ERROR_MESSAGE.INVALID_USER_ROLE);

  if (!(response.userStatus in USER_STATUS)) throw new Error(ERROR_MESSAGE.INVALID_USER_STATUS);

  const result: UserInfo = {
    fullName: response.fullName,
    username: response.username,
    email: response.email,
    userId: response.userId,
    phoneNumber: response.phoneNumber,
    userRole: response.userRole as USER_ROLE,
    userStatus: response.userStatus as USER_STATUS,
  };

  return result;
};

export const transformUserCalendarMeetingResponse = (response: UserCalendarMeetingRESP): UserCalendarMeetingDTO[] => {
  return (
    response?.meetings?.map((meeting) => {
      const dto: UserCalendarMeetingDTO = {
        projectId: meeting.projectId,
        title: meeting.projectName,
        end: dayjs(meeting.endTime).toDate(),
        start: dayjs(meeting.startTime).toDate(),
        id: meeting.meetingId,
        url: meeting.url,
      };
      return dto;
    }) ?? []
  );
};
