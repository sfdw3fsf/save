export type UserCalendarMeetingRESP = {
  meetings: {
    projectId: number;
    projectName: string;
    meetingId: number;
    startTime: number;
    endTime: number;
    url?: string;
  }[];
};
