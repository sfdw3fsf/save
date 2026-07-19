export type RegisterMemberREQ = {
  companyName: string;
  fullName: string;
  personInCharge: string;
  internalEmail: string;
  departmentName?: string;
  businessFieldCategoryIds: number[];
  attachments?: {
    key: string;
    fileType: string;
    name: string;
    size: number;
  }[];
};
