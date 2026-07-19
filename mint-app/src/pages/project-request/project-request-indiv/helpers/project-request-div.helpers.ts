import {
  transformAdvisoryFieldCategoryResponse,
  transformBusinessFieldCategoryResponse,
  transformMedicalFieldCategoryResponse,
} from '@/data/account/account.service';
import { FieldCategoryDTO } from '@/data/account/dto/field-category.dto';
import { AdvisoryFieldCategoryRESP } from '@/data/account/response/advisory-field-category.response';
import { BusinessFieldCategoryRESP } from '@/data/account/response/business-field-category.response';
import { MedicalFieldCategoryRESP } from '@/data/account/response/medical-field-category.response';
import { ProjectDetailRESP } from '@/data/project/response/project.response';
import { BusinessInput } from '@/pages/sign-up-expert/components/SignUpExpertBusinessModal';
import { AdvisoryFieldCategories, BusinessFieldCategories, MedicalFieldCategories } from '@/types/category.type';

export const filterCategorySecondary = (
  data: FieldCategoryDTO[],
  input?: BusinessInput,
  fieldStr?: string,
  separater?: string,
) =>
  input
    ? data.map(({ id, name, nameInEnglish, fields }) => {
        const fieldKey = `${fieldStr ?? 'fieldKey'}${id}` as keyof BusinessInput;
        const etcFieldKey = `${fieldStr ?? 'fieldKey'}Etc${id}` as keyof BusinessInput;

        const fieldIds = input[fieldKey] ? ([input[fieldKey]].flat() as number[]) : [];

        const fieldsMap = fields.filter((i) => fieldIds.includes(i.id)).sort((_, b) => (fieldIds.includes(b.id) ? 1 : -1));

        return {
          label: {
            name,
            nameInEnglish,
          },
          allFields: {
            name: fieldsMap.map((i) => (i.id >= 0 ? i.name : input[etcFieldKey])).join(separater ?? ' - '),
            nameInEnglish: fieldsMap.map((i) => (i.id >= 0 ? i.nameInEnglish : input[etcFieldKey])).join(separater ?? ' - '),
          },
        };
      })
    : [];

export const extractFields = (input?: Record<string, any>, fieldStr?: string, etcFieldStr?: string) => {
  const result = { field: [] as number[], otherField: [] as { categoryId: number; name: string }[] };

  if (!input || !fieldStr || !etcFieldStr) return result;

  Object.entries(input).forEach(([key, value]) => {
    if (key.startsWith(fieldStr)) {
      const positiveValues = (Array.isArray(value) ? value : [value]).filter((val) => typeof val === 'number' && val > 0);
      result.field.push(...positiveValues);
    }

    if (key.startsWith(etcFieldStr) && typeof value === 'string' && !!value) {
      result.otherField.push({ categoryId: parseInt(key.replace(etcFieldStr, ''), 10), name: value });
    }
  });

  return result;
};

// optimize this code by request BE to response same for 3 fields
export const combineBusinessFields = (businessFieldCategories?: BusinessFieldCategories[]) =>
  businessFieldCategories
    ? businessFieldCategories?.map(({ name, nameInEnglish, businessFields }) => {
        const allFields = businessFields
          ?.sort((a, b) => Number(a.isOtherField) - Number(b.isOtherField)) // Sort with isOtherField false first
          .map((field) => field.name)
          .join(' , ');
        const allFieldsInEnglish = businessFields
          ?.sort((a, b) => Number(a.isOtherField) - Number(b.isOtherField)) // Sort with isOtherField false first
          .map((field) => field.nameInEnglish)
          .join(' , ');
        return { label: { name, nameInEnglish }, allFields: { name: allFields, nameInEnglish: allFieldsInEnglish } };
      })
    : [];

export const combineAdvisoryFields = (advisoryFieldCategories?: AdvisoryFieldCategories[]) =>
  advisoryFieldCategories
    ? advisoryFieldCategories?.map(({ name, nameInEnglish, advisoryFields }) => {
        const allFields = advisoryFields
          ?.sort((a, b) => Number(a.isOtherField) - Number(b.isOtherField)) // Sort with isOtherField false first
          .map((field) => field.name)
          .join(' , ');
        const allFieldsInEnglish = advisoryFields
          ?.sort((a, b) => Number(a.isOtherField) - Number(b.isOtherField)) // Sort with isOtherField false first
          .map((field) => field.nameInEnglish)
          .join(' , ');
        return { label: { name, nameInEnglish }, allFields: { name: allFields, nameInEnglish: allFieldsInEnglish } };
      })
    : [];
export const combineMedicalFields = (medicalFieldCategories?: MedicalFieldCategories[]) =>
  medicalFieldCategories
    ? medicalFieldCategories?.map(({ name, nameInEnglish, medicalFields }) => {
        const allFields = medicalFields
          ?.sort((a, b) => Number(a.isOtherField) - Number(b.isOtherField)) // Sort with isOtherField false first
          .map((field) => field.name)
          .join(' , ');
        const allFieldsInEnglish = medicalFields
          ?.sort((a, b) => Number(a.isOtherField) - Number(b.isOtherField)) // Sort with isOtherField false first
          .map((field) => field.nameInEnglish)
          .join(' , ');
        return { label: { name, nameInEnglish }, allFields: { name: allFields, nameInEnglish: allFieldsInEnglish } };
      })
    : [];
export const transformToObject = (fieldKey: string, fieldKeyEtc: string, data: FieldCategoryDTO[], isArray: boolean = true) => {
  const results: { [key: string]: number[] | number | undefined | string | string[] } = {};
  data.forEach(({ id, fields = [] }) => {
    results[`${fieldKey}${id}`] = isArray
      ? fields?.map((v) => (v.isOtherField ? -id : v.id))
      : fields[0]?.isOtherField
        ? -id
        : fields[0]?.id;

    results[`${fieldKeyEtc}${id}`] = fields?.find((v) => v.isOtherField)?.name;
  });

  return results;
};

export const combineEditData = (projectDetail: ProjectDetailRESP) => {
  const { businessFieldCategories = [], advisoryFieldCategories = [], medicalFieldCategories = [] } = projectDetail;
  const transformedBusinessField = transformBusinessFieldCategoryResponse(businessFieldCategories as BusinessFieldCategoryRESP[]);
  const transformedAdvisoryField = transformAdvisoryFieldCategoryResponse(advisoryFieldCategories as AdvisoryFieldCategoryRESP[]);
  const transformedMedicalField = transformMedicalFieldCategoryResponse(medicalFieldCategories as MedicalFieldCategoryRESP[]);
  const businessFieldIds = transformToObject('businessField', 'businessFieldEtc', transformedBusinessField, false);
  const advisoryFieldIds = transformToObject('advisoryField', 'advisoryFieldEtc', transformedAdvisoryField);
  const medicalFieldIds = transformToObject('medicalField', 'medicalFieldEtc', transformedMedicalField);
  return {
    businessFieldIds,
    advisoryFieldIds,
    medicalFieldIds,
  };
};
