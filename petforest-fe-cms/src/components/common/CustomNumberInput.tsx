import { Input, InputProps } from 'antd';
import React, { ReactNode } from 'react';

export type CustomNumberInputProps = {
  placeholder?: string;
  spanCol?: number;
  disabled?: boolean;
  classNameInput?: string;
  suffix?: ReactNode;
  bordered?: boolean;
  min?: number;
  max?: number;
  onChange?: (value: number) => void;
  defaultValue?: number;
  value?: number;
} & InputProps;

const CustomNumberInput = ({
  placeholder = '',
  disabled,
  suffix,
  classNameInput,
  min,
  max,
  defaultValue,
  value,
  onChange,
  onBlur,
}: CustomNumberInputProps) => {
  const handleWheel = (e: React.WheelEvent<HTMLInputElement>) => {
    e.currentTarget.blur();
    e.preventDefault();
  };

  return (
    <Input
      placeholder={placeholder}
      className={`h-[39px] ${classNameInput} w-full`}
      disabled={disabled}
      min={min}
      max={max}
      suffix={suffix}
      onWheel={handleWheel}
      defaultValue={defaultValue}
      value={value}
      type='number'
      onChange={(e) => {
        if (onChange) {
          onChange(+e.target.value);
        }
      }}
      onBlur={onBlur}
    />
  );
};

export default CustomNumberInput;
