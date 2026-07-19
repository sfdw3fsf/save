import { MeetingScheduleDTO } from '@/data/project/dto/project.dto';
import { transformInitialMeetingsResponse, transformMeetingsResponse } from '@/data/project/project.service';
import { ProjectDetailRESP, ProposalTimeSlotRESP } from '@/data/project/response/project.response';
import { UserInfo } from '@/types/user-info.type';
import { TIMESLOT_ENUM } from '@/utils/enums/project/meeting-format.enum';
import { USER_ROLE } from '@/utils/enums/user/user-role.enum';
import { MEETING_STATUS_ENUM } from '../enums/meeting-status.enums';

type Props = {
  projectDetail?: ProjectDetailRESP;
  proposalMeetings?: ProposalTimeSlotRESP;
  userInfo?: UserInfo | null;
};
export const extractMeetings = ({ projectDetail, proposalMeetings, userInfo }: Props) => {
  const meetingStatus: MEETING_STATUS_ENUM =
    projectDetail?.proposalTimeSlotsFinalMeetings && projectDetail?.proposalTimeSlotsFinalMeetings?.length !== 0
      ? MEETING_STATUS_ENUM.FINAL
      : proposalMeetings?.meetings && proposalMeetings.meetings.length !== 0
        ? MEETING_STATUS_ENUM.PROPASAL
        : MEETING_STATUS_ENUM.INITIAL;

  const initialMeetings: MeetingScheduleDTO[] =
    projectDetail?.meetings?.length !== 0 ? transformInitialMeetingsResponse(projectDetail?.meetings || []) : [];

  // const meetings: MeetingScheduleDTO[] =
  //   meetingStatus === MEETING_STATUS_ENUM.FINAL
  //     ? transformMeetingsResponse(projectDetail?.proposalTimeSlotsFinalMeetings || [])
  //     : meetingStatus === MEETING_STATUS_ENUM.PROPASAL
  //       ? transformMeetingsResponse(proposalMeetings?.meetings || [])
  //       : meetingStatus === MEETING_STATUS_ENUM.INITIAL
  //         ? initialMeetings || []
  //         : [];

  const meetings: MeetingScheduleDTO[] =
    meetingStatus === MEETING_STATUS_ENUM.INITIAL ? initialMeetings : transformMeetingsResponse(proposalMeetings?.meetings || []);

  const editable = projectDetail?.proposal?.proposalCreator !== userInfo?.userRole;

  let backgroundMeetings: MeetingScheduleDTO[] = [];
  let foregroundMeetings: MeetingScheduleDTO[] = [];

  // if (meetingStatus === MEETING_STATUS_ENUM.FINAL) {
  //   backgroundMeetings = meetings?.filter((m) => m.type === TIMESLOT_ENUM.TIME_SLOT) || [];
  //   foregroundMeetings = meetings?.filter((m) => m.type === TIMESLOT_ENUM.FINAL_MEETING) || [];
  // }

  if (meetingStatus === MEETING_STATUS_ENUM.PROPASAL || meetingStatus === MEETING_STATUS_ENUM.FINAL) {
    backgroundMeetings = meetings?.filter((m) => m.type === TIMESLOT_ENUM.TIME_SLOT) || [];
    foregroundMeetings = meetings?.filter((m) => m.type === TIMESLOT_ENUM.MEETING) || [];
  }

  if (meetingStatus === MEETING_STATUS_ENUM.INITIAL) {
    backgroundMeetings = userInfo?.userRole === USER_ROLE.EXPERT ? meetings : [];
    foregroundMeetings = userInfo?.userRole === USER_ROLE.EXPERT ? [] : meetings;
  }

  // const meetingsInCard =
  //   meetingStatus !== MEETING_STATUS_ENUM.FINAL
  //     ? meetings
  //     : meetings?.filter((m) => m.type === TIMESLOT_ENUM.FINAL_MEETING) || [];
  const meetingsInCard = foregroundMeetings?.length === 0 ? backgroundMeetings : foregroundMeetings || [];

  return { meetingStatus, initialMeetings, meetings, backgroundMeetings, foregroundMeetings, editable, meetingsInCard };
};
