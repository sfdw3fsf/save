export const removeEmptyParams = <T extends object>(data: any) => {
  const newData: Partial<T> = {};

  for (const key in data) {
    if (data[key] !== '' && data[key] !== 'null' && data[key] !== null && data[key] !== undefined && data[key] !== 'All') {
      newData[key as keyof T] = data[key];
    }
  }

  return newData;
};

export const objectReplace = <T extends object>(
  data: any,
  excludedValues: any[] = ['', '[]', '{}'],
  fallback: any = undefined,
) => {
  const newData: Partial<T> = {};

  for (const key in data) {
    if (excludedValues.includes(data[key])) {
      newData[key as keyof T] = fallback;
    } else {
      newData[key as keyof T] = data[key];
    }
  }

  return newData;
};
