import { CustomTextInput } from '@/components/common/CustomTextInput';
import React, { useState } from 'react';

interface CustomPhoneNumberInputProps {
  value: string;
  onBlur?: (e: React.FocusEvent<HTMLInputElement, Element>) => void;
  readOnly?: boolean;
}

const CustomPhoneNumberInput: React.FC<CustomPhoneNumberInputProps> = ({ value, onBlur, readOnly }) => {
  const [inputValue, setInputValue] = useState(value);

  const formatPhoneNumber = (value: string) => {
    let formattedValue = value.replace(/\D/g, '');

    if (formattedValue.length <= 9) {
      formattedValue = formattedValue.replace(/(\d{2})(\d{3})(\d{4})/, '$1-$2-$3');
    } else if (formattedValue.length === 10) {
      formattedValue = formattedValue.replace(/(\d{2})(\d{4})(\d{4})/, '$1-$2-$3');
    } else if (formattedValue.length === 11) {
      formattedValue = formattedValue.replace(/(\d{3})(\d{4})(\d{4})/, '$1-$2-$3');
    }

    return formattedValue;
  };

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const rawValue = e.target.value;
    setInputValue(formatPhoneNumber(rawValue));
  };

  const handleBlur = (e: React.FocusEvent<HTMLInputElement, Element>) => {
    const formattedValue = formatPhoneNumber(inputValue);
    setInputValue(formattedValue);
    onBlur && onBlur(e);
  };

  return (
    <CustomTextInput
      type='text'
      maxLength={13}
      value={inputValue}
      onChange={handleChange}
      onBlur={handleBlur}
      readOnly={readOnly}
    />
  );
};

export default CustomPhoneNumberInput;
