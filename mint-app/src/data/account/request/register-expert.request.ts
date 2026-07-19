export type RegisterExpertREQ = {
  fullName: string;
  schoolFrom: string;
  affiliation: string;
  personalHistory: number;
  department: string;
  detailedField?: string;
  contact: string;
  businessFieldIds: number[];
  advisoryFieldIds: number[];
  otherBusinessFields: { categoryId: number; name: string }[];
  otherAdvisoryFields: { categoryId: number; name: string }[];
  attachments?: {
    key: string;
    fileType: string;
    name: string;
    size: number;
  }[];
};
