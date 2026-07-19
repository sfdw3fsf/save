import { TabItemProps } from '@/types/tab.type';
import React from 'react';

const TabItem: React.FC<TabItemProps> = ({ children }) => {
  return <div>{children}</div>;
};

export default TabItem;
