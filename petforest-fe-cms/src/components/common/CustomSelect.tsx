import { OptionItems } from '@/utils/types/option.type';
import { Select, SelectProps } from 'antd';
import { ReactNode } from 'react';
import LabelForm from './form/LabelForm';

export type CustomSelectInputProps = {
  label?: React.ReactNode;
  rules?: Array<Record<string, any>>;
  wrapperCol?: number;
  disabled?: boolean;
  classNameSelect?: string;
  height?: string | number;
  classNameForm?: string;
  className?: string;
  optionsSelect?: OptionItems<any>;
  showSearch?: boolean;
  isBlue?: boolean;
  labelWidth?: number;
  isLabelForm?: boolean;
  classNameLabel?: string;
  maxTagCount?: string;
  isRequired?: boolean;
  maxTagPlaceholder?: (omittedValues: any[]) => ReactNode;
} & SelectProps;

const CustomSelect = ({
  label,
  rules,
  disabled,
  classNameSelect,
  className,
  optionsSelect: options,
  showSearch,
  isBlue,
  labelWidth = 150,
  classNameLabel,
  isLabelForm = true,
  maxTagCount,
  maxTagPlaceholder,
  height = 39,
  isRequired,
  ...rest
}: CustomSelectInputProps) => {
  const heightSelect = typeof height === 'number' ? `[${height}px]` : height;

  return (
    <LabelForm
      label={label}
      isRequired={isRequired}
      labelWidth={labelWidth}
      classNameLabel={classNameLabel}
      isLabelForm={isLabelForm}
      className={className}>
      <Select
        className={`!h-${heightSelect} w-full !text-base ${classNameSelect} ${isBlue ? 'select-blue' : ''}`}
        disabled={disabled}
        options={options as any}
        showSearch={showSearch}
        maxTagCount={maxTagCount}
        maxTagPlaceholder={maxTagPlaceholder}
        {...rest}
      />
    </LabelForm>
  );
};

export default CustomSelect;
