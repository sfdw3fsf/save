import { PROJECT_STATICS_ENUM } from '@/utils/enums/project/project-list.enum';
import { InitialMeeting, MeetingScheduleDTO, ProjectStatisticsDTO } from './dto/project.dto';
import { Meeting, ProjectStatisticsRESP } from './response/project.response';

export const transformProjectStaticsResponse = (statistics: ProjectStatisticsRESP): ProjectStatisticsDTO[] =>
  Object.entries(statistics).map(([key, value]) => ({
    status: key as PROJECT_STATICS_ENUM,
    value: value,
  }));

export const transformMeetingsResponse = (meetings: Meeting[] = []): MeetingScheduleDTO[] =>
  meetings?.map((m) => ({
    ...m,
    start: new Date(m.startTime),
    end: new Date(m.endTime),
  }));

export const transformInitialMeetingsResponse = (meetings: InitialMeeting[]): MeetingScheduleDTO[] =>
  meetings?.map((m) => ({
    ...m.meeting,
    start: new Date(m.meeting.startTime),
    end: new Date(m.meeting.endTime),
  }));
