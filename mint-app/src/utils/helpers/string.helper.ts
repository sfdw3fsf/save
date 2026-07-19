export const withFallbackValue = ({
  inputValue,
  defaultValue,
  isNumber,
}: {
  inputValue: string | number | null | undefined;
  defaultValue?: string | number;
  isNumber?: boolean;
}): string | number => {
  if (inputValue === null || inputValue === undefined) {
    return defaultValue || (isNumber ? 0 : '');
  }
  return inputValue;
};

export const withFallbackStr = (name?: string | null) => withFallbackValue({ inputValue: name, defaultValue: '_' });

export const sanitizeForId = (label: string) => {
  return label.toLowerCase().replace(/[^\w\s]|(\s+)/g, (_match: string, group1: string) => (group1 ? '-' : ''));
};

export const getStringFlatMap = (object: any[]): string[] => {
  const flatten = (i: Record<string, any>): string[] => {
    return Object.values(i).flatMap((value) => {
      if (typeof value === 'object' && value !== null) {
        return flatten(value);
      }
      return typeof value === 'string' ? [value] : [];
    });
  };
  return object.flatMap(flatten);
};
