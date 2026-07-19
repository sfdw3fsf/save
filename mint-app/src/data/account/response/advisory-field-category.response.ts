export type AdvisoryFieldCategoryRESP = {
  id: number;
  name: string;
  nameInEnglish: string;
  advisoryFields: { id: number; name: string; nameInEnglish: string; isOtherField: boolean }[];
};
