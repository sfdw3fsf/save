import { Input, InputProps } from 'antd';
import { Rule } from 'antd/es/form';
import LabelForm from './form/LabelForm';

export type CustomTextInputProps = {
  placeholder?: string;
  spanCol?: number;
  disabled?: boolean;
  classNameInput?: string;
  isPassword?: boolean;
  className?: string;
  type?: string;
  defaultValue?: string;
  prefix?: React.ReactNode;
  suffix?: React.ReactNode;
  label?: React.ReactNode;
  rules?: Rule[];
  value?: string;
  bordered?: boolean;
  readonly?: boolean;
  size?: 'small' | 'middle' | 'large';
  labelWidth?: number;
  onChange?: (e: React.ChangeEvent<HTMLInputElement>) => void;
  isLabelForm?: boolean;
  classNameLabel?: string;
  isRequired?: boolean;
} & InputProps;

export const CustomTextInput = ({
  label,
  rules,
  placeholder,
  disabled,
  classNameInput,
  className,
  defaultValue,
  prefix,
  suffix,
  isPassword = false,
  type = 'text',
  value,
  labelWidth = 150,
  bordered,
  size,
  onChange,
  readonly,
  isLabelForm = true,
  classNameLabel,
  isRequired: requiredLabel,
  hidden,
  ...rest
}: CustomTextInputProps) => {
  return (
    <div hidden={hidden}>
      <LabelForm
        label={label}
        isRequired={requiredLabel ?? !!rules}
        bordered={bordered}
        labelWidth={labelWidth}
        isLabelForm={isLabelForm}
        className={className}
        classNameLabel={classNameLabel}>
        {isPassword ? (
          <Input.Password
            placeholder={placeholder}
            defaultValue={defaultValue}
            prefix={prefix}
            suffix={suffix}
            type={type}
            size={size}
            className={`h-[39px] text-base !rounded-[8px] ${classNameInput}`}
            disabled={disabled}
            value={value}
            onChange={onChange}
            readOnly={readonly}
          />
        ) : (
          <Input
            placeholder={placeholder}
            defaultValue={defaultValue}
            prefix={prefix}
            suffix={suffix}
            type={type}
            size={size}
            className={`h-[39px] text-base !rounded-[8px] !text-[14px] ${classNameInput}`}
            disabled={disabled}
            value={value}
            onChange={(e) => {
              onChange && onChange(e);
            }}
            readOnly={readonly}
            {...rest}
          />
        )}
      </LabelForm>
    </div>
  );
};
