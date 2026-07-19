export type Category = {
  id: number;
  name: string;
  nameInEnglish: string;
  isOtherField?: boolean;
};

export type OtherField = {
  categoryId: number;
  name: string;
};

export type BusinessFieldCategories = {
  id: number;
  name: string;
  nameInEnglish: string;
  businessFields: Category[];
};
export type AdvisoryFieldCategories = {
  id: number;
  name: string;
  nameInEnglish: string;
  advisoryFields: Category[];
};
export type MedicalFieldCategories = {
  id: number;
  name: string;
  nameInEnglish: string;
  medicalFields: Category[];
};
