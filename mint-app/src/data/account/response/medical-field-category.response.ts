export type MedicalFieldCategoryRESP = {
  id: number;
  name: string;
  nameInEnglish: string;
  medicalFields: { id: number; name: string; nameInEnglish: string; isOtherField: boolean }[];
};
