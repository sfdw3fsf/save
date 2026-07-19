import { TFunction } from 'i18next';
import { BRANCH_ENUMS } from '../enums/common.enums';
import { orUndefined } from '../parser/io.parser';

export const helperFilter = (
  path: string,
  enumObj: Record<string, string>,
  tFunc: TFunction<'funeral_reservation' | 'setting' | 'enum' | 'ossuary', undefined>,
  isIncludeEmpty?: boolean,
) => {
  const emptyOption = {
    text: '',
    value: '',
  };

  const options = Object.values(enumObj).map((item) => ({
    text: tFunc(`${path}.${item}` as any),
    value: item,
  }));

  return isIncludeEmpty ? [emptyOption, ...options] : options;
};

export const helperOption = (
  path: string,
  enumObj: Record<string, string>,
  tFunc: TFunction<'setting' | 'home' | 'funeral_reservation' | 'enum' | 'common', undefined>,
  isIncludeAll?: boolean,
  isIncludeEmpty?: boolean,
) => {
  const emptyOption = {
    label: '',
    value: '',
  };

  const options = Object.values(enumObj).map((item) => ({
    label: tFunc(`${path}.${item}` as any),
    value: item,
  }));

  if (isIncludeAll) {
    return [{ label: tFunc(`all`), value: 'All' }, ...options];
  } else if (isIncludeEmpty) {
    return [emptyOption, ...options];
  } else return options;
};

export const helperTab = (
  path: string,
  enumObj: Record<string, string>,
  tFunc: TFunction<'common' | 'enum', undefined>,
): string[] => {
  return Object.values(enumObj).map((item) => tFunc(`${path}.${item}` as any));
};

export const helperDecodeFilter = (
  filters: Record<string, any>,
  filterKeyMapping?: Record<string, (value: any) => any>,
): { newTableFilter: Record<string, any>; newField: Record<string, any> } => {
  const newTableFilter: Record<string, any> = {};
  const newField: Record<string, any> = {};

  Object.entries(filters).forEach(([key, value]) => {
    if (filterKeyMapping && filterKeyMapping[key]) {
      const filterObject = filterKeyMapping[key](orUndefined(value));

      if (filterObject?.type === 'array') {
        if (!newField[filterObject.newKey]) {
          newField[filterObject.newKey] = [];
        }
        if (filterObject.value) {
          newField[filterObject.newKey].push(filterObject.value);
        }
      } else if (filterObject?.newKey) {
        newField[filterObject.newKey] = filterObject.value;
      } else {
        Object.assign(newTableFilter, filterObject);
      }
    } else {
      if (Array.isArray(value)) {
        newTableFilter[`${key}.in`] = value;
      } else {
        newTableFilter[`${key}.in`] = orUndefined(value);
      }
    }
  });

  return { newTableFilter, newField };
};

export const helperGetFilter = <T extends string>(searchParams: URLSearchParams, keys: T[]): Record<T, string[]> => {
  return keys.reduce(
    (filter, key) => {
      const values = searchParams.getAll(key);
      if (values.length > 0) {
        filter[key] = values.flatMap((item) => item.split(','));
      }
      return filter;
    },
    {} as Record<T, string[]>,
  );
};

export const helperGetBranchEnum = <T>(branchId?: number, fallback: T = null as T): BRANCH_ENUMS | T => {
  switch (branchId) {
    case Number(process.env.NEXT_PUBLIC_GYEONGGI_GWANGJU):
      return BRANCH_ENUMS.GYEONGGI_GWANGJU;
    case Number(process.env.NEXT_PUBLIC_GIMPO):
      return BRANCH_ENUMS.GIMPO;
    case Number(process.env.NEXT_PUBLIC_NAMYANGJU):
      return BRANCH_ENUMS.NAMYANGJU;

    default:
      return fallback;
  }
};
