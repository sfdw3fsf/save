import { InputEnum } from '@/utils/enums/input.enum';
import { OptionItems } from '@/utils/types/option.type';
import { InputProps } from 'antd';
import { FormItemLabelProps } from 'antd/es/form/FormItemLabel';
import { TimePickerProps } from 'antd/lib';
import dayjs, { Dayjs } from 'dayjs';
import { isNil } from 'lodash';
import React, { ReactNode } from 'react';
import { Controller, FieldValues, RegisterOptions, useController, UseControllerProps, UseFormSetValue } from 'react-hook-form';
import CustomDatePicker from '../CustomDatePicker';
import CustomFileUploadInput from '../CustomFileUploadInput';
import CustomImageUploadInput from '../CustomImageUploadInput';
import CustomNumericFormat from '../CustomNumericFormat';
import CustomRangePicker from '../CustomRangePicker';
import { CustomReactQuillInput } from '../CustomReactQuillInput';
import CustomSelect from '../CustomSelect';
import CustomSelectInput from '../CustomSelectInput';
import { CustomTextAreaInput } from '../CustomTextAreaInput';
import { CustomTextInput } from '../CustomTextInput';
import CustomTimePicker from '../CustomTimePicker';
import FormMessage, { FormMessageTranslateKey } from './FormMessage';
import LabelForm from './LabelForm';

type FormInputProps<T extends Object> = {
  defaultValue?: any;
  type: InputEnum;
  optionItems?: OptionItems<any>;
  suffix?: ReactNode;
  allowNegative?: boolean;
  rows?: number;
  isResize?: boolean;
  format?: string;
  extraTitle?: ReactNode;
  buttonUpload?: ReactNode;
  isPassword?: boolean;
  isRequired?: boolean;
  disabled?: boolean;
  bordered?: boolean;
  disabledBeforeDate?: boolean;
  showTime?: boolean;
  onSelect?: (value: string) => void;
  onDeselect?: (value: string) => void;
  minNumber?: number;
  allowClear?: boolean;
  rules?: Omit<RegisterOptions<T, any>, 'disabled' | 'setValueAs' | 'valueAsNumber' | 'valueAsDate'> | undefined;
  maxTagCount?: 'responsive';
  maxTagPlaceholder?: (omittedValues: any[]) => ReactNode;
  showSearch?: boolean;
  renderInput?: (props: { value: any; onChange: (value: any) => void }) => React.ReactNode;
  multiple?: boolean;
  fileType?: 'image';
  maxCount?: number;
  displayName?: boolean;
  isLimitNumber?: boolean;
  allowDecimal?: boolean;
  popupMatchSelectWidth?: boolean;
  isFocus?: boolean;
  disabledTime?: (date: Dayjs) => TimePickerProps['disabledTime'];
} & Omit<InputProps, 'value' | 'optionItems'>;

type LabelProps = {
  label?: React.ReactNode;
  labelWidth?: number;
  isLabelForm?: boolean;
  classNameLabel?: string;
  classNameInput?: string;
  requiredLabel?: boolean;
};

type FormFieldProps<T extends FieldValues> = FormItemLabelProps &
  Pick<UseControllerProps<T>, 'name' | 'control'> &
  FormInputProps<T> & {
    className?: string;
    helperText?: string;
    successMessage?: string;
    rightNode?: ReactNode;
    hideErrorMessage?: boolean;
    readOnly?: boolean;
  } & LabelProps & { setValue?: UseFormSetValue<T> };

const CustomFormInput = <T extends FieldValues>({
  name,
  control,
  minNumber,
  defaultValue,
  optionItems,
  isRequired = true,
  allowNegative,
  disabled,
  bordered = false,
  disabledBeforeDate,
  extraTitle,
  showTime,
  onSelect,
  onDeselect,
  allowClear,
  maxTagCount,
  buttonUpload,
  maxTagPlaceholder,
  showSearch = false,
  isPassword = false,
  label,
  className,
  helperText,
  rows = 5,
  isResize = false,
  format,
  successMessage,
  type,
  rules,
  suffix,
  rightNode,
  hideErrorMessage = false,
  requiredLabel,
  labelWidth,
  isLabelForm,
  classNameLabel,
  classNameInput,
  setValue,
  placeholder,
  renderInput,
  multiple = true,
  readOnly,
  fileType,
  maxCount,
  displayName,
  isLimitNumber,
  allowDecimal,
  popupMatchSelectWidth,
  isFocus = true,
  disabledTime,
  ...rest
}: FormFieldProps<T>) => {
  const {
    fieldState: { error },
  } = useController<T>({ control, name });

  return (
    <>
      <LabelForm
        extraLabel={extraTitle}
        label={label}
        isRequired={requiredLabel}
        bordered={bordered}
        labelWidth={labelWidth}
        isLabelForm={isLabelForm}
        className={`${className} gap-[8px]`}
        classNameLabel={`${classNameLabel} font-medium`}>
        <Controller
          name={name as any}
          rules={rules}
          control={control}
          render={({ field: { value, onChange } }) => {
            switch (type) {
              case InputEnum.THOUSAND_NUMBER:
                return (
                  <CustomNumericFormat
                    allowNegative={allowNegative}
                    className={className}
                    placeholder={placeholder}
                    value={value?.toString() || ''}
                    onChange={(e) => {
                      onChange(Number(e.value));
                    }}
                    allowDecimal={allowDecimal}
                    isLimit={isLimitNumber}
                    suffix={suffix}
                    readOnly={readOnly}
                    disabled={disabled}
                    isFocus={isFocus}
                  />
                );
              case InputEnum.IMAGE:
                return (
                  <CustomImageUploadInput
                    name={name}
                    multiple={multiple}
                    disabled={disabled}
                    onChange={onChange}
                    fileType={fileType}
                    value={value}
                    maxCount={maxCount}
                    displayName={displayName}
                  />
                );
              case InputEnum.FILE:
                return (
                  <CustomFileUploadInput
                    name={name}
                    maxCount={maxCount}
                    multiple={multiple}
                    disabled={disabled}
                    onChange={onChange}
                    value={value}
                  />
                );
              case InputEnum.SELECT:
                return (
                  <CustomSelect
                    value={value}
                    disabled={disabled}
                    defaultValue={defaultValue}
                    optionsSelect={optionItems}
                    placeholder={placeholder}
                    classNameSelect={className}
                    onChange={onChange}
                    allowClear={allowClear}
                    popupMatchSelectWidth={popupMatchSelectWidth}
                  />
                );
              case InputEnum.SELECT_SEARCH:
                return (
                  <CustomSelect
                    value={value}
                    disabled={disabled}
                    defaultValue={defaultValue}
                    optionsSelect={optionItems}
                    placeholder={placeholder}
                    classNameSelect={className}
                    onChange={onChange}
                    allowClear={allowClear}
                    showSearch
                    filterOption={(input, option) =>
                      ((option?.label as string) ?? '').toLowerCase().includes(input.toLowerCase())
                    }
                  />
                );
              case InputEnum.SELECT_AND_INPUT:
                return (
                  <CustomSelectInput
                    value={value}
                    defaultValue={defaultValue}
                    options={optionItems}
                    placeholderInput={placeholder}
                    classNameSelect={className}
                    onChange={onChange}
                    allowClear={allowClear}
                  />
                );
              case InputEnum.SELECT_INPUT:
                return (
                  <CustomSelect
                    mode='tags'
                    maxCount={1}
                    defaultValue={defaultValue}
                    optionsSelect={optionItems}
                    onSelect={onSelect}
                    disabled={disabled}
                    allowClear={allowClear}
                    classNameSelect={className}
                    onChange={
                      onChange ??
                      ((value) => {
                        if (!setValue) return;
                        if (value.length !== 0) {
                          if (value?.[0] && !isNaN(+value?.[0])) {
                            setValue(name, [+value[0]] as any);
                          } else if (value[0] === null) {
                            setValue(name, [null] as any);
                          } else {
                            setValue(name, [] as any);
                          }
                        } else {
                          setValue(name, [] as any);
                        }
                      })
                    }
                    placeholder={placeholder}
                  />
                );
              case InputEnum.SELECT_MULTI:
                return (
                  <CustomSelect
                    mode='multiple'
                    isBlue
                    value={value}
                    placeholder={placeholder}
                    defaultValue={defaultValue}
                    showSearch={showSearch}
                    disabled={disabled}
                    onSelect={onSelect}
                    onDeselect={onDeselect}
                    onChange={onChange}
                    classNameSelect={className}
                    allowClear={allowClear}
                    optionsSelect={optionItems}
                    maxTagCount={maxTagCount}
                    maxTagPlaceholder={maxTagPlaceholder}
                  />
                );
              case InputEnum.DATE_PICKER:
                return (
                  <CustomDatePicker
                    name={name}
                    value={!isNil(value) ? dayjs(value) : undefined}
                    bordered={bordered}
                    placeholder={placeholder ? placeholder : 'YYYY-MM-DD.'}
                    onChange={onChange}
                    defaultValue={defaultValue}
                    disabledBeforeDate={disabledBeforeDate}
                    disabled={disabled}
                    showTime={showTime}
                    className={className}
                    allowClear={allowClear}
                    minDate={minNumber ? dayjs(minNumber) : undefined}
                  />
                );
              case InputEnum.TIME_PICKER:
                return (
                  <CustomTimePicker
                    format={format}
                    value={value}
                    disabled={disabled}
                    name={name}
                    bordered={bordered}
                    onChange={onChange}
                    placeholder={placeholder}
                    disabledTime={disabledTime as any}
                    hideDisabledOptions
                    minuteStep={5}
                  />
                );
              case InputEnum.RANGE_PICKER:
                return (
                  <CustomRangePicker
                    name={name}
                    onChange={onChange}
                    disabled={disabled}
                    classNameInput='inputNoStyle'
                    value={value}
                    defaultValue={defaultValue}
                    placeholder={['YYYY-MM-DD', 'YYYY-MM-DD']}
                    className={className}
                    allowClear={allowClear}
                  />
                );
              case InputEnum.HIDE:
                return <CustomTextInput name={name} placeholder={placeholder} size='small' bordered={bordered} hidden />;
              case InputEnum.TEXT_AREA:
                return (
                  <CustomTextAreaInput
                    disabled={disabled || readOnly}
                    value={value}
                    onChange={onChange}
                    placeholder=''
                    rows={rows}
                    isResize={isResize}
                    classNameInput={classNameInput}
                  />
                );

              case InputEnum.TEXT:
                return (
                  <CustomTextInput
                    suffix={suffix}
                    disabled={disabled}
                    isPassword={isPassword}
                    placeholder={placeholder}
                    size='small'
                    bordered={false}
                    value={value}
                    onChange={onChange}
                    readOnly={readOnly}
                    {...rest}
                  />
                );
              case InputEnum.QUILL:
                return <CustomReactQuillInput readOnly={readOnly} value={value} onChange={onChange} />;
              default:
                return <>{renderInput?.({ value, onChange })}</>;
            }
          }}
        />
        {!hideErrorMessage && (
          <FormMessage
            className='mt-1'
            variant={(!!error && 'error') || undefined}
            translateKey={error?.message as FormMessageTranslateKey}
            message={!error ? helperText : undefined}
          />
        )}
      </LabelForm>
    </>
  );
};

export default CustomFormInput;
