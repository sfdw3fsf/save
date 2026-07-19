import InfoIcon from '@/components/icons/InfoIcon';
import { ReactNode } from 'react';
import { useTranslation } from 'react-i18next';
import { twMerge } from 'tailwind-merge';
import Dot from '../../dot/Dot';

export type FormLabelProps = {
  name?: string;
  label?: string;
  required?: boolean;
  info?: ReactNode;
  className?: HTMLDivElement['className'];
  labelClassName?: HTMLDivElement['className'];
  hideRequiredIndicator?: boolean;
};

const FormLabel = ({
  name,
  label,
  required = false,
  info,
  className,
  labelClassName,
  hideRequiredIndicator = false,
}: FormLabelProps): JSX.Element => {
  const { t: tCommon } = useTranslation('common');

  if (!label) return <></>;

  return (
    <div className={twMerge('flex w-full flex-col items-start justify-start gap-1', className)}>
      <label aria-required={required} htmlFor={name} className='flex w-fit items-center justify-start gap-1 flex-wrap'>
        <span className={twMerge('text-sm font-medium text-slate-800', labelClassName)}>{label}</span>
        {!hideRequiredIndicator &&
          (required ? (
            <Dot />
          ) : (
            <span className='text-sm font-normal text-comp-disabled'>({tCommon('component.optional')})</span>
          ))}
      </label>

      {!!info && (
        <div className='inline-flex w-full gap-1 items-start justify-start text-comp-text-secondary text-sm font-normal'>
          <div className='flex h-5 items-center justify-center'>
            <div className='size-4 shrink-0'>
              <InfoIcon className='size-full text-inherit' />
            </div>
          </div>
          <div>{info}</div>
        </div>
      )}
    </div>
  );
};

export default FormLabel;
