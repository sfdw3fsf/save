export type BusinessFieldCategoryRESP = {
  id: number;
  name: string;
  nameInEnglish: string;
  businessFields: { id: number; name: string; nameInEnglish: string; isOtherField: boolean }[];
};
