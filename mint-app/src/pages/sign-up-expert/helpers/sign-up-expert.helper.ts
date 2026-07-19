import { FieldCategoryDTO } from '@/data/account/dto/field-category.dto';
import { BusinessCombineInput, BusinessInput } from '../components/SignUpExpertBusinessModal';

export const filterCategory = (data: FieldCategoryDTO[], input?: BusinessInput) =>
  input
    ? data.map(({ id, name, nameInEnglish, fields }) => {
        const fieldKey = `field${id}` as keyof BusinessInput;
        const arrayFieldKey = `arrayField${id}` as keyof BusinessInput;
        const advisoryFieldKey = `advisoryField${id}` as keyof BusinessInput;

        const fieldIds = input[fieldKey] ? ([input[fieldKey]].flat() as number[]) : [];
        const arrayFieldIds = (input[arrayFieldKey] as number[]) || [];
        const advisoryFieldIds = (input[advisoryFieldKey] as number[]) || [];

        const fieldsMap = fields
          .filter((i) => fieldIds.includes(i.id) || arrayFieldIds.includes(i.id) || advisoryFieldIds.includes(i.id))
          .sort((_, b) => (fieldIds.includes(b.id) ? 1 : -1));

        return {
          label: {
            name: name,
            nameInEnglish: nameInEnglish,
          },
          allFields: {
            name: fieldsMap.map((item) => item.name).join(' - '),
            nameInEnglish: fieldsMap.map((item) => item.nameInEnglish).join(' - '),
          },
        };
      })
    : [];

export const transformBusinessCombineInput = (
  data?: BusinessCombineInput,
): {
  businessFieldIds: number[];
  advisoryFieldIds: number[];
  otherBusinessFields: { categoryId: number; name: string }[];
  otherAdvisoryFields: { categoryId: number; name: string }[];
} => {
  const getOtherValues = (input?: BusinessInput): { categoryId: number; name: string }[] => {
    if (!input) return [];

    const filteredInput = Object.fromEntries(Object.entries(input).filter(([_, value]) => typeof value === 'string'));

    const values: { categoryId: number; name: string }[] = [];

    Object.keys(filteredInput).forEach((key) => {
      values.push({ categoryId: Number(key.split('_').at(-1)) * -1, name: filteredInput[key] as string });
    });

    return values;
  };

  const flatten = (input?: BusinessInput): number[] => {
    if (!input) return [];
    const filteredInput = Object.fromEntries(Object.entries(input).filter(([_, value]) => typeof value !== 'string')) as Record<
      string,
      number | number[]
    >;
    const ids = Object.values(filteredInput).reduce<number[]>((acc, value) => {
      return acc.concat(Array.isArray(value) ? value : [value]);
    }, []);
    return [...new Set(ids)].filter((i) => i !== null && i !== undefined && i >= 0);
  };

  return {
    businessFieldIds: flatten(data?.businessFieldIds),
    advisoryFieldIds: flatten(data?.advisoryFieldIds),
    otherAdvisoryFields: getOtherValues(data?.advisoryFieldIds),
    otherBusinessFields: getOtherValues(data?.businessFieldIds),
  };
};

export const getAllKeysWithPrefix = (obj: BusinessInput, prefix: string) =>
  Object.entries(obj)
    .filter(([key, value]) => key.startsWith(prefix) && Array.isArray(value))
    .map(([key]) => key);

export const getNumberOfSelectedOptions = (obj: BusinessInput, prefix: string) => {
  return Object.entries(obj)
    .filter(([key]) => key.startsWith(prefix) && Array.isArray(obj[key]))
    .reduce((sum, [_, value]) => sum + (value as number[]).length, 0);
};
