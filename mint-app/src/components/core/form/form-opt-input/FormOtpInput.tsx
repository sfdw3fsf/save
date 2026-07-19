import { FieldValues, useController, UseControllerProps } from 'react-hook-form';
import OtpInput from 'react18-input-otp';
import { twJoin, twMerge } from 'tailwind-merge';

export type FormOtpInputProps<T extends FieldValues> = Pick<UseControllerProps<T>, 'name' | 'control'> & {
  numInputs?: number;
  isInputNum?: boolean;
  truncate?: boolean;
  showSuccessMessage?: boolean;
  required?: boolean;
  className?: string;
};

const FormOtpInput = <T extends FieldValues>({
  numInputs = 6,
  isInputNum = true,
  truncate = false,
  name,
  control,
  className,
}: FormOtpInputProps<T>): JSX.Element => {
  const { field, fieldState } = useController({ control, name });

  return (
    <OtpInput
      id={name}
      value={field.value}
      onChange={(value: string) => {
        const isNumeric = /^\d*$/.test(value);
        if (isInputNum && !isNumeric) return;
        field.onChange(value);
      }}
      numInputs={Math.max(1, numInputs)}
      isInputNum={isInputNum}
      shouldAutoFocus
      containerStyle={twMerge('w-full gap-2.5 justify-center', truncate ? 'inline-flex' : 'flex flex-wrap', className)}
      inputProps={{ className: twJoin('otp-input', fieldState.error && 'error') }}
    />
  );
};

export default FormOtpInput;
