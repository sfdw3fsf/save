export const decodeFilter = (filter: string) => {
  if (!filter) return;

  const result: Record<string, any> = {};
  Object.entries(JSON.parse(filter)).forEach(([key, value]) => {
    const newKey = key.replace(/\.in$/, '');
    result[newKey] = Array.isArray(value) ? value : [value];
  });

  return result;
};
