import { OtherField } from '@/types/category.type';
import { DateRange } from '@/types/date.type';
import { UploadFile } from '@/types/file.type';
import { ADVISORY_TYPE } from '@/utils/enums/project/advisory.enum';
import { MEETING_FORMAT_ENUM } from '@/utils/enums/project/meeting-format.enum';
import { PROJECT_STATUS } from '@/utils/enums/project/project-list.enum';

export type ProjectsREQ = {
  id?: number;
  name?: string;
  projectStatuses?: PROJECT_STATUS[];
  advisoryType?: ADVISORY_TYPE;
  meetingFormat?: MEETING_FORMAT_ENUM;
  startDate?: number;
  endDate?: number;
  page?: number;
  size?: number;
  direction?: 'ASC' | 'DESC';
};

export type ProjectsIndivREQ = ProjectsREQ;
export type ProjectsExpertREQ = ProjectsREQ;

export type RequestProjectREQ = {
  name: string; // This field need to be confirmed
  businessFieldIds: number[];
  advisoryFieldIds: number[];
  medicalFieldIds: number[];
  advisoryType: ADVISORY_TYPE;
  advisoryTime?: number;
  contentDetail: string;
  meetings: DateRange[];
  meetingFormat: MEETING_FORMAT_ENUM; // This field need to be confirmed
  attachment?: UploadFile;
  otherBusinessFields?: OtherField[];
  otherAdvisoryFields?: OtherField[];
  otherMedicalFields?: OtherField[];
};

export type ProjectDetailREQ = {
  projectId: number;
};

export type AvailableExpertsREQ = {
  fullName?: string;
  page?: number;
  size?: number;
};

export type SelectExpertREQ = {
  expertId: number;
};

export type ProposalsREQ = {
  proposalTimeSlots: DateRange[];
};

export type ProposalMeetingsREQ = {
  proposalMeetings: (DateRange & { proposalTimeSlotId: number })[];
};
