export const parseArrayToStringRequest: any = (i: React.Key[], allowEmpty?: boolean) => {
  if (!allowEmpty && i.length === 0) return;
  return `[${i.join(',')}]`;
};
