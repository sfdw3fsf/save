import ArrowRightIcon from '@/components/icons/ArrowRightIcon';
import { useBreakpoint } from '@/hooks/useBreakpoint';
import { ReactNode, useMemo } from 'react';
import { twMerge } from 'tailwind-merge';
import Button, { ButtonProps } from '../button/Button';
import SelectBoxKey, { SelectBoxKeyProps } from './SelectBoxKey';

type SelectBoxProps = SelectBoxKeyProps & {
  className?: string;
  title: string;
  buttonProps?: ButtonProps;
  description?: ReactNode;
};

const theme = {
  cont: {
    web: 'gap-3 p-[15px]',
    mob: 'gap-2 p-[11px]',
  },
  title: {
    web: 'text-lg',
    mob: 'text-md',
  },
  description: {
    web: 'text-md',
    mob: 'text-sm',
  },
};

const SelectBox = ({ className, title, buttonProps, description, ...keyProps }: SelectBoxProps): JSX.Element => {
  const { hasMinWidthMd } = useBreakpoint('md');
  const sizing = useMemo(() => (hasMinWidthMd ? 'web' : 'mob'), [hasMinWidthMd]);

  return (
    <div
      className={twMerge(
        'flex w-full flex-col items-center justify-center border border-slate-200 rounded-lg bg-slate-25',
        theme.cont[sizing],
        className,
      )}>
      <div className='flex w-full items-center justify-between gap-3'>
        <div className='flex w-full items-center justify-start gap-3 text-left'>
          <SelectBoxKey {...keyProps} />
          <div className={twMerge('font-bold', theme.title[sizing])}>{title}</div>
        </div>
        <div className='flex items-center justify-end gap-3 text-right'>
          {!!buttonProps && (
            <Button labelContStyle='gap-1' sizing='2sm' variant='secondaryGray' rightIcon={<ArrowRightIcon />} {...buttonProps} />
          )}
        </div>
      </div>
      {!!description && (
        <div
          className={twMerge(
            'flex w-full items-start justify-start gap-2 font-normal text-left text-slate-500',
            theme.description[sizing],
          )}>
          {description}
        </div>
      )}
    </div>
  );
};

export default SelectBox;
