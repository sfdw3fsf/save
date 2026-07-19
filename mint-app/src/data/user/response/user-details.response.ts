import { Attachment } from '@/types/attachment.type';

export type UserBusinessFieldCategory = {
  id: number;
  name: string;
  nameInEnglish: string;
  isUserSelected: boolean;
};
export type UserDetailsRESP = {
  username: string;
  email: string;
  userId: number;
  phoneNumber: string;
  userRole: string;
  userStatus: string;
  fullName: string;

  // new response
  memberId?: number;
  companyName?: string;
  personInCharge?: string;
  internalEmail?: string;
  departmentName?: string;
  businessFieldCategories?: UserBusinessFieldCategory[];
  //memberAttachments?: File | null;
  memberAttachments?: Attachment[] | null;

  //Expert
};
type AdvisoryField = {
  categoryId: number;
  id: number;
  isOtherFields: boolean;
  isUserSelected: boolean;
  name: string;
};
type BusinessField = {
  categoryId: number;
  id: number;
  isOtherFields: boolean;
  isUserSelected: boolean;
  name: string;
  nameInEnglish?: string;
};
type AdvisoryFieldCategories = {
  id: number;
  name: string;
  nameInEnglish?: string;
  isUserSelected: boolean;
  advisoryFields: AdvisoryField[];
};

type Education = {
  detail: string;
  entranceMonth: number;
  entranceYear: number;
  graduationMonth: number;
  graduationYear: number;
  id: number;
  name: string;
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
export type UserAvatar = {
  key: string;
  name: string;
  type: string;
  size: number;
  url: string;
};
type BusinessFieldCategories = {
  id: number;
  name: string;
  nameInEnglish?: string;
  isUserSelected: boolean;
  businessFields: BusinessField[];
};
export type ExpertDetailsRESP = {
  username: string;
  email: string;
  userId: number;
  phoneNumber: string;
  userRole: string;
  userStatus: string;
  fullName: string;
  avatar: UserAvatar;
  // new response
  // memberId?: number;
  companyName?: string;
  personInCharge?: string;
  internalEmail?: string;
  departmentName?: string;
  businessFieldCategories?: BusinessFieldCategories[];
  expertAttachments?: Attachment[] | null;
  school?: string;
  affiliation?: string;
  yearOfExperience?: number;
  department?: string;
  expertId?: number;

  advisoryFieldCategories?: AdvisoryFieldCategories[];
  educations: Education[];
  experiences: Experience[];
  detailedField: string;
  // advisory
};
