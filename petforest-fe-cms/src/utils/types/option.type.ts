export type OptionItem<T> = {
  label: string | React.ReactNode;
  value: T;
  disabled?: boolean;
};

export type OptionItems<T> = OptionItem<T>[];

export type FilterOptionItem<T = number> = {
  text: string;
  value: T;
  label: string | React.ReactNode;
};

export type FilterOptions<T = number> = FilterOptionItem<T>[];
