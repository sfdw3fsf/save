import { ReactElement, ReactNode, useMemo } from 'react';
import { FieldValues, useController } from 'react-hook-form';
import { twMerge } from 'tailwind-merge';
import FormAvatarInput, { FormAvatarInputProps } from '../form-avatar-input/FormAvatarInput';
import FormDatalist, { FormDatalistProps } from '../form-datalist/FormDatalist';
import FormDateRangePicker, { FormDateRangePickerProps } from '../form-date-range-picker/FormDateRangePicker';
import FormDropdown, { FormDropdownProps } from '../form-dropdown/FormDropdown';
import FormFileInput, { FormFileInputProps } from '../form-file-input/FormFileInput';
import FormInput, { FormInputProps } from '../form-input/FormInput';
import FormLabel, { FormLabelProps } from '../form-label/FormLabel';
import FormMessage, { FormMessageTranslateKey } from '../form-message/FormMessage';
import FormOtpInput, { FormOtpInputProps } from '../form-opt-input/FormOtpInput';
import FormOptionGroup, { FormOptionGroupProps } from '../form-option-group/FormOptionGroup';
import FormOption, { FormOptionProps } from '../form-option/FormOption';
import FormTextArea, { FormTextAreaProps } from '../form-text-area/FormTextArea';

type FormComponentProps<T extends FieldValues> =
  | { type: 'input'; componentProps: FormInputProps<T> }
  | { type: 'otp'; componentProps: FormOtpInputProps<T> }
  | { type: 'option'; componentProps: FormOptionProps<T> }
  | { type: 'option-group'; componentProps: FormOptionGroupProps<T> }
  | { type: 'file'; componentProps: FormFileInputProps<T> }
  | { type: 'text-area'; componentProps: FormTextAreaProps<T> }
  | { type: 'drop-down'; componentProps: FormDropdownProps<T> }
  | { type: 'date-range-picker'; componentProps: FormDateRangePickerProps<T> }
  | { type: 'avatar'; componentProps: FormAvatarInputProps<T> }
  | { type: 'datalist'; componentProps: FormDatalistProps<T> };

type FormFieldProps<T extends FieldValues> = FormComponentProps<T> &
  Pick<FormLabelProps, 'label' | 'info' | 'hideRequiredIndicator'> & {
    className?: string;
    helperText?: string;
    successMessage?: string;
    rightNode?: ReactNode;
    hideErrorMessage?: boolean;
    expandComponent?: ReactElement;
    messageClassName?: string;
  };

const FormField = <T extends FieldValues>({
  label,
  className,
  helperText,
  successMessage,
  type,
  rightNode,
  info,
  hideErrorMessage = false,
  hideRequiredIndicator,
  expandComponent,
  messageClassName,
  componentProps: { control, name, showSuccessMessage = false, required = false, ...rest },
}: FormFieldProps<T>): JSX.Element => {
  const {
    fieldState: { error: fieldError },
  } = useController({ control, name });

  const isSuccess = useMemo<boolean>(() => !fieldError && showSuccessMessage, [fieldError, showSuccessMessage]);

  const gap = useMemo(() => {
    switch (type) {
      case 'option':
      case 'option-group':
      case 'file':
      case 'avatar':
        return 'gap-3';
      default:
        return 'gap-1';
    }
  }, [type]);

  const mt = useMemo(() => {
    switch (type) {
      case 'option':
      case 'option-group':
      case 'file':
      case 'avatar':
        return 'mt-3';
      case 'otp':
        return 'mt-2';
      default:
        return 'mt-1';
    }
  }, [type]);

  const component = useMemo(() => {
    const componentProps = { name, control, showSuccessMessage, ...rest };
    switch (type) {
      case 'input':
        return <FormInput {...(componentProps as FormInputProps<T>)} />;
      case 'otp':
        return <FormOtpInput {...(componentProps as FormOtpInputProps<T>)} />;
      case 'option':
        return <FormOption {...(componentProps as FormOptionProps<T>)} />;
      case 'option-group':
        return <FormOptionGroup {...(componentProps as FormOptionGroupProps<T>)} />;
      case 'file':
        return <FormFileInput {...(componentProps as FormFileInputProps<T>)} />;
      case 'text-area':
        return <FormTextArea {...(componentProps as FormTextAreaProps<T>)} />;
      case 'drop-down':
        return <FormDropdown {...(componentProps as FormDropdownProps<T>)} />;
      case 'date-range-picker':
        return <FormDateRangePicker {...(componentProps as FormDateRangePickerProps<T>)} />;
      case 'avatar':
        return <FormAvatarInput {...(componentProps as FormAvatarInputProps<T>)} />;
      case 'datalist':
        return <FormDatalist {...(componentProps as FormDatalistProps<T>)} />;
      default:
        return <></>;
    }
  }, [control, name, rest, showSuccessMessage, type]);

  return (
    <div className={twMerge('flex w-full flex-col items-start justify-start', gap, className)}>
      <FormLabel
        name={type === 'option-group' ? `${name}.0` : name}
        required={required}
        label={label}
        info={info}
        className={gap}
        hideRequiredIndicator={hideRequiredIndicator}
      />

      <div className='w-full'>
        <div className='flex w-full items-center justify-start gap-3'>
          {component}
          {!!rightNode && <div>{rightNode}</div>}
        </div>

        {!hideErrorMessage && (
          <FormMessage
            className={twMerge(mt, messageClassName)}
            variant={(!!fieldError && 'error') || (isSuccess && 'success') || undefined}
            translateKey={fieldError?.message as FormMessageTranslateKey}
            message={!fieldError ? (isSuccess && successMessage) || helperText : undefined}
          />
        )}

        {!!expandComponent && expandComponent}
      </div>
    </div>
  );
};

export default FormField;
