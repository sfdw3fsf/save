import React, { createContext, useContext } from 'react';
import { Control } from 'react-hook-form';

const ControlContext = createContext<Control<any, any> | null>(null);

export const useControl = () => {
  const context = useContext(ControlContext);
  if (!context) {
    throw new Error('useControl must be used within a ControlProvider');
  }
  return context;
};

export const ControlProvider = ({ control, children }: { control: Control<any, any>; children: React.ReactNode }) => {
  return <ControlContext.Provider value={control}>{children}</ControlContext.Provider>;
};
