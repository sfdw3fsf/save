// src/types/TabTypes.ts
export interface TabItemProps {
  rightIcon?: React.ReactNode;
  label: string;
  children: React.ReactNode;
}

export interface TabListProps {
  activeTabIndex?: number; // Optional, defaults to 0 if not provided
  children: React.ReactElement<TabItemProps> | React.ReactElement<TabItemProps>[];
}
