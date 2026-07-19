import { FeedbacksRESP } from '@/data/feedbacks/response/feedbacks.response';
import { Attachment } from '@/types/attachment.type';
import { AdvisoryFieldCategories, BusinessFieldCategories, MedicalFieldCategories } from '@/types/category.type';
import { DateRange } from '@/types/date.type';
import { ADVISORY_TYPE } from '@/utils/enums/project/advisory.enum';
import { TIMESLOT_ENUM } from '@/utils/enums/project/meeting-format.enum';
import { PROJECT_STATICS_ENUM, PROJECT_STATUS } from '@/utils/enums/project/project-list.enum';
import { USER_ROLE } from '@/utils/enums/user/user-role.enum';
import { InitialMeeting } from '../dto/project.dto';

export type Education = {
  id: number;
  name: string;
  detail: string;
  entranceYear: number;
  entranceMonth: number;
  graduationMonth: number;
  graduationYear: number;
};

export type Experience = {
  id: number;
  name: string;
  detail: string;
  startMonth: number;
  startYear: number;
  endMonth: number;
  endYear: number;
};

// This type is used for request to BE
export type Meeting = DateRange & {
  id: number;
  url?: string;
  proposalCreator?: USER_ROLE;
  type?: TIMESLOT_ENUM;
};

export type ExpertRESP = {
  expertId: number;
  fullName: string;
  department: string;
  detailedField: string;
  avatar: Attachment;
  businessFieldCategories: BusinessFieldCategories[];
  yearOfExperience: number;
  affiliation: string;
  educations?: Education[];
  experiences?: Experience[];
};

export type MemberRESP = {
  memberId: number;
  companyName?: string;
};

export type ProjectsRESP = {
  id: number;
  name: string;
  advisoryType: ADVISORY_TYPE;
  status: PROJECT_STATUS;
  createdAt?: number;
  nearestMeetingAt?: number;
  haveProposal?: boolean;
  canCreateProposal?: boolean; // Create Grey Color
  canCreateProposalMeetings?: boolean; // Create Green Color
};

export type ProposalRESP = {
  id: number;
  proposalCreator: USER_ROLE;
};

export type ProjectsIndivRESP = ProjectsRESP;
export type ProjectsExpertRESP = ProjectsRESP;

export type ProjectStatisticsRESP = Record<PROJECT_STATICS_ENUM, number>;

export type RequestProjectRESP = {
  projectId: number;
};

export type ProjectDetailRESP = ProjectsRESP & {
  advisoryTime?: number;
  contentDetail: string;
  attachment?: Attachment;
  businessFieldCategories: BusinessFieldCategories[];
  advisoryFieldCategories: AdvisoryFieldCategories[];
  medicalFieldCategories: MedicalFieldCategories[];
  availableExperts?: boolean;
  expertDetail?: ExpertRESP;
  clientDetail?: MemberRESP;
  meetings: InitialMeeting[];
  finalMeetings?: Meeting[];
  feedback?: FeedbacksRESP;
  proposal?: ProposalRESP;
  needCheck?: boolean; // This variables is to check if user have checked when project completed
  proposalTimeSlotsFinalMeetings?: Meeting[];
};

export type ProposalTimeSlotRESP = {
  proposalId: number;
  meetings: Meeting[];
};
