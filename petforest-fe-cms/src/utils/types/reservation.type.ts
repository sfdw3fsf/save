export const serviceKeyMapping = {
  newAdditon: 1,
  lucette: 2,
  columbarium: 3,
} as const;

export type ServiceKeyType = keyof typeof serviceKeyMapping;
