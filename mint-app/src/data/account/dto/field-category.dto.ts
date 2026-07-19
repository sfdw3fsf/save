export type FieldCategoryDTO = {
  id: number;
  name: string;
  nameInEnglish: string;
  fields: { id: number; name: string; nameInEnglish: string; isOtherField: boolean }[];
};
