import { OptionItems } from '@/utils/types/option.type';
import { Input, Select } from 'antd';
import { SelectProps } from 'antd/lib';

type CustomSelectInputProps<T> = {
  value: T;
  onChange: (value: T) => void;
  classNameSelect?: string;
  classNameInput?: string;
  options?: OptionItems<any>;
  placeholderInput?: string;
} & SelectProps;

const CustomSelectInput = <T extends Record<string, any>>({
  value = {} as T,
  classNameSelect,
  classNameInput,
  onChange,
  options = [],
  placeholderInput,
  ...rest
}: CustomSelectInputProps<T>) => {
  const selectKey =
    (Object.keys(value).find((key) => options.some((option) => option.value === value[key as keyof T])) as keyof T) || 'type';

  const inputKey = Object.keys(value).find((key) => key !== selectKey) as keyof T;

  const handleSelectChange = (newValue: string) => {
    onChange({ ...value, [selectKey]: newValue });
  };

  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    onChange({ ...value, [inputKey]: e.target.value });
  };

  return (
    <div className='flex items-center !h-[38px] custom-select-input'>
      <Select
        value={value[selectKey] as string | undefined}
        className={`!h-[39px] !text-base ${classNameSelect}`}
        options={options}
        style={{ borderTopRightRadius: '0px', borderBottomRightRadius: '0px' }}
        onChange={handleSelectChange}
        {...rest}
      />

      <Input
        value={value[inputKey] as string | undefined}
        placeholder={placeholderInput}
        className={`!h-[39px] flex-1 !text-base ${classNameInput}`}
        onChange={handleInputChange}
      />
    </div>
  );
};

export default CustomSelectInput;
