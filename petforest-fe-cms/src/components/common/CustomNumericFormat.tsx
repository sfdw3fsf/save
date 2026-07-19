import { isEmpty, isNaN } from 'lodash';
import { ReactNode } from 'react';
import { NumericFormat } from 'react-number-format';
import { CustomTextInput } from './CustomTextInput';

type CustomNumericFormatProps = {
  suffix?: ReactNode;
  value?: number;
  onChange?: (...event: any[]) => void;
  placeholder?: string;
  className?: string;
  allowNegative?: boolean;
  onBlur?: (value: number) => void;
  disabled?: boolean;
  readOnly?: boolean;
  defaultValue?: number;
  isLimit?: boolean;
  allowDecimal?: boolean;
  isFocus?: boolean;
};

const CustomNumericFormat = ({
  suffix,
  value,
  onChange,
  placeholder,
  className,
  allowNegative = true,
  onBlur,
  disabled,
  readOnly,
  defaultValue,
  isLimit = true,
  allowDecimal = true,
  isFocus = true,
}: CustomNumericFormatProps) => {
  const handleBlur = (e: React.FocusEvent<HTMLInputElement>) => {
    if (onBlur) {
      const numericValue = isEmpty(e.target.value) ? 0 : parseFloat(e.target.value.replace(/[^0-9.-]+/g, ''));
      onBlur(isNaN(numericValue) ? 0 : numericValue);
    }
  };

  const handleFocus = (e: React.FocusEvent<HTMLInputElement>) => {
    if (e.target.value === '0') {
      e.target.select();
    }
  };

  return (
    <div className='relative'>
      <NumericFormat
        readOnly={readOnly}
        disabled={disabled}
        value={value}
        placeholder={placeholder}
        onValueChange={(values) => onChange?.(values)}
        thousandSeparator
        customInput={CustomTextInput}
        onBlur={handleBlur}
        classNameInput={`${className} !pr-[30px]`}
        defaultValue={defaultValue}
        valueIsNumericString
        allowNegative={allowNegative}
        allowLeadingZeros={false}
        onFocus={isFocus ? handleFocus : undefined}
        decimalScale={allowDecimal ? undefined : 0}
        maxLength={isLimit ? 11 : undefined}
        style={{
          fontSize: 14,
        }}
      />
      <div className='absolute right-3 inset-y-0 flex items-center'>{suffix}</div>
    </div>
  );
};

export default CustomNumericFormat;
