import { ReactNode } from 'react';
import { twMerge } from 'tailwind-merge';
import Button, { ButtonProps } from '../button/Button';

type BlockProps = {
  className?: string;
  header?: { title: string; rightNode?: ReactNode };
  imgSrc?: string;
  message?: string;
  buttonProps?: ButtonProps;
  children?: ReactNode;
  isError?: boolean;
};

const Block = ({ className, header, imgSrc, message, buttonProps, children, isError = false }: BlockProps): JSX.Element => {
  return (
    <div
      className={twMerge(
        'flex w-full flex-col rounded-lg overflow-hidden bg-white border',
        isError ? 'border-comp-border-danger-strong' : 'border-comp-border-neutral-soft',
        className,
      )}>
      {!!header && (
        <div className='flex w-full items-center justify-between gap-1.5 bg-slate-25 px-4 py-3.5 max-md:px-3 max-md:py-4'>
          <div className='flex w-full items-center justify-start text-left'>
            <div className='w-full text-md max-md:text-sm font-normal text-comp-text-default'>{header.title}</div>
          </div>
          <div className='flex w-full items-center justify-end text-right'>{header.rightNode}</div>
        </div>
      )}

      <div className='flex w-full flex-col items-center justify-center text-center p-4 gap-5 max-md:gap-3 max-md:p-7'>
        {children ?? (
          <>
            {!!imgSrc && (
              <div className='size-[120px] shrink-0 '>
                <img src={imgSrc} className='w-full h-full object-cover' />
              </div>
            )}

            {!!message && (
              <div className={twMerge('w-full text-sm font-normal text-comp-text-secondary', isError && 'text-comp-text-danger')}>
                {message}
              </div>
            )}

            {!!buttonProps && (
              <div className='flex w-full items-center justify-center'>
                <Button {...buttonProps} sizing='sm' />
              </div>
            )}
          </>
        )}
      </div>
    </div>
  );
};

export default Block;
