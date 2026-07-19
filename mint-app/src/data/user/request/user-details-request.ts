export type UserREQ = {
  companyName?: string;
  fullName?: string;
  personInCharge?: string;
  internalEmail?: string;
  departmentName?: string;
  name?: string;
  // avatar
  businessFieldCategoryIds?: number[];
  attachments?: any;
};

export type UserUpdatePasswordREQ = {
  oldPassword: string;
  newPassword: string;
  confirmPassword: string;
};
