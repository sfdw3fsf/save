import CaretDownIcon from '@/components/icons/CaretDownIcon';
import { useEffect, useMemo, useState } from 'react';
import { FieldValues, useController, UseControllerProps } from 'react-hook-form';
import { useTranslation } from 'react-i18next';
import { components, SingleValue } from 'react-select';
import CreatableSelect from 'react-select/creatable';

type Option = {
  value: string;
  label: string;
};

const CustomOption = (props: any) => {
  const { data } = props;
  return <components.Option {...props}>{data.label}</components.Option>;
};

const CustomDropdownIndicator = (props: any) => {
  return (
    <components.DropdownIndicator {...props}>
      <CaretDownIcon className='text-comp-icon-tertiary' />
    </components.DropdownIndicator>
  );
};

export type FormDatalistProps<T extends FieldValues> = Pick<UseControllerProps<T>, 'name' | 'control'> & {
  showSuccessMessage?: boolean;
  disabled?: boolean;
  placeholder?: string;
  isSearchable?: boolean;
  options: Option[];
  required?: boolean;
  defaultValue?: SingleValue<Option> | string;
};

const FormDatalist = <T extends FieldValues>({
  name,
  control,
  showSuccessMessage = false,
  disabled = false,
  placeholder,
  isSearchable = false,
  options,
  required = false,
  defaultValue,
}: FormDatalistProps<T>): JSX.Element => {
  const {
    field,
    fieldState: { error: fieldError },
  } = useController({ control, name });
  const { t: tCommon } = useTranslation('common');

  const [isFocused, setIsFocused] = useState<boolean>(false);
  const [selectedOption, setSelectedOption] = useState<SingleValue<Option>>(null);

  const isSuccess = useMemo<boolean>(() => !fieldError && showSuccessMessage, [fieldError, showSuccessMessage]);

  const handleChange = (option?: SingleValue<Option> | string) => {
    let newOption: SingleValue<Option> | undefined;

    if (typeof option === 'string') {
      newOption = { label: option, value: option };
    } else {
      newOption = option;
    }

    setSelectedOption(newOption ?? null);
    field.onChange(newOption?.value);
  };

  useEffect(() => {
    handleChange(defaultValue);
  }, [defaultValue]);

  if (options.length <= 0) return <></>;

  return (
    <div className='w-full'>
      <CreatableSelect
        required={required}
        noOptionsMessage={() => tCommon('component.no_option_found')}
        createOptionPosition='last'
        formatCreateLabel={(newOption) => tCommon('component.create_option', { option: newOption })}
        isDisabled={disabled}
        value={selectedOption}
        onChange={(option: SingleValue<Option>) => {
          handleChange(option);
        }}
        isSearchable={isSearchable}
        isClearable={false}
        isMulti={false}
        options={options}
        placeholder={placeholder ? placeholder : tCommon('component.select_or_create')}
        components={{ Option: CustomOption, DropdownIndicator: CustomDropdownIndicator }}
        styles={{
          input: (base) => ({ ...base, margin: 0, padding: 0 }),
          indicatorSeparator: (base) => ({
            ...base,
            display: 'none',
          }),
          menu: (base) => ({
            ...base,
            borderRadius: '8px',
            overflow: 'hidden',
            borderWidth: '1px',
            borderStyle: 'solid',
            borderColor: '#e2e8f0',
            display: 'block',
            boxShadow: '0 4px 6px -2px rgba(16, 24, 40, 0.03), 0 12px 16px -4px rgba(16, 24, 40, 0.08)',
          }),
          singleValue: (base) => ({ ...base, margin: 0 }),
          placeholder: (base) => ({ ...base, color: '#64748b' }),
          control: (base) => ({
            ...base,
            position: 'relative',
            width: '100%',
            height: '50px',
            minHeight: '50px',
            borderRadius: '8px',
            fontSize: '16px',
            lineHeight: '24px',
            color: '#1e293b',
            borderWidth: '1px',
            borderStyle: 'solid',
            borderColor: isSuccess
              ? '#22c55e'
              : disabled
                ? '#e2e8f0'
                : fieldError
                  ? '#f04438'
                  : isFocused && field.value
                    ? '#abe4cf'
                    : isFocused
                      ? '#34987b'
                      : '#e2e8f0',
            backgroundColor: isSuccess && !disabled ? 'white' : disabled ? '#edf2f7' : 'white',
            '&:hover': {
              borderColor: isSuccess
                ? '#22c55e'
                : disabled
                  ? '#e2e8f0'
                  : fieldError
                    ? '#f04438'
                    : isFocused && field.value
                      ? '#abe4cf'
                      : '#34987b',
              backgroundColor: isSuccess && !disabled ? 'white' : disabled ? '#edf2f7' : isFocused ? 'white' : '#f8fafc',
            },
            fontWeight: field.value ? 500 : 400,
            boxShadow: 'none',
          }),
        }}
        onFocus={() => {
          setIsFocused(true);
        }}
        onBlur={() => {
          field.onBlur();
          setIsFocused(false);
        }}
      />
    </div>
  );
};

export default FormDatalist;
