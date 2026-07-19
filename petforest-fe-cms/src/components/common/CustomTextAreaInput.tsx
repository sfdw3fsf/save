import { Input } from 'antd';
import LabelForm from './form/LabelForm';

const { TextArea } = Input;

export type CustomTextAreaInputProps = {
  placeholder?: string;
  spanCol?: number;
  disabled?: boolean;
  classNameInput?: string;
  className?: string;
  type?: string;
  defaultValue?: string;
  prefix?: React.ReactNode;
  suffix?: React.ReactNode;
  label?: React.ReactNode;
  rows?: number;
  draggable?: boolean;
  value?: string;
  onChange?: (e: React.ChangeEvent<HTMLTextAreaElement>) => void;
  bordered?: boolean;
  labelWidth?: number;
  isResize?: boolean;
};

export const CustomTextAreaInput = ({
  label,
  placeholder = '',
  disabled,
  classNameInput,
  className,
  rows,
  draggable,
  defaultValue,
  value,
  onChange,
  bordered,
  labelWidth,
  isResize = true,
}: CustomTextAreaInputProps) => {
  return (
    <LabelForm label={label} bordered={bordered} labelWidth={labelWidth} className={className}>
      <TextArea
        placeholder={placeholder}
        defaultValue={defaultValue}
        value={value}
        onChange={onChange}
        className={`h-full ${classNameInput}`}
        disabled={disabled}
        rows={rows}
        draggable={draggable}
        style={{ resize: isResize ? undefined : 'none' }}
      />
    </LabelForm>
  );
};
