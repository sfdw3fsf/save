export type FlattenKeys<T> = T extends object
  ? {
      [K in keyof T]: K | FlattenKeys<T[K]>;
    }[keyof T]
  : never;

export type Nil<T> = T | null | undefined;

export type WithOptional<T, K extends keyof T> = Omit<T, K> & Partial<Pick<T, K>>;

export type DeleteModalType = {
  isOpen: boolean;
  ids?: number | number[];
};
