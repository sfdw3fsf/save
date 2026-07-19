import XIcon from '@/components/icons/XIcon';
import { MutableRefObject, ReactNode } from 'react';
import { twJoin, twMerge } from 'tailwind-merge';
import Button, { ButtonProps } from '../button/Button';
import Divider from '../divider/Divider';

export type ModalProps = {
  open: boolean;
  onClose: () => void;
  header?: ReactNode;
  body: ReactNode;
  footer?: ReactNode;
  dismissible?: boolean;
  hideCloseButton?: boolean;
  buttons?: ButtonProps[];
  fullHeight?: boolean;
  className?: string;
  bodyRef?: MutableRefObject<HTMLDivElement | null>;
  isDivider?: boolean;
};

const Modal = ({
  open,
  onClose,
  header,
  body,
  footer,
  dismissible = false,
  hideCloseButton = false,
  buttons = [],
  fullHeight = false,
  className,
  bodyRef,
  isDivider = true,
}: ModalProps): JSX.Element => {
  const handleClose = () => {
    onClose();
  };

  if (!open) return <></>;

  return (
    <div
      className={twMerge(
        'fixed inset-0 z-[30] flex items-center justify-center bg-comp-fill-neutral-reverse bg-opacity-20',
        className,
      )}>
      <div
        className={twJoin(
          'absolute flex flex-col w-[94%] max-w-[600px] items-center justify-start bg-white rounded-lg overflow-hidden',
          fullHeight ? 'h-[94%]' : 'h-fit max-h-[94%]',
        )}>
        {!hideCloseButton && (
          <button
            type='button'
            form='_notexist'
            onClick={handleClose}
            className='absolute right-3 top-3 max-md:right-2 max-md:top-2 size-7 max-md:size-4 shrink-0 flex items-center justify-center text-comp-icon-secondary'>
            <XIcon className='size-5 max-md:size-4 shrink-0 text-inherit' />
          </button>
        )}

        {/* header */}
        <div className='flex w-full flex-col gap-3 max-md:gap-2 px-6 max-md:px-4 pt-6 max-md:pt-4 pb-4 max-md:pb-3'>
          {!!header && (
            <>
              {header}
              {isDivider && <Divider />}
            </>
          )}
        </div>

        {/* body */}
        <div ref={bodyRef} className='w-full h-full overflow-y-auto static-scrollbar px-6 max-md:px-4'>
          <div>{body}</div>
        </div>

        {/* footer */}
        <div className='flex w-full flex-col gap-3 max-md:gap-2 px-6 max-md:px-4 py-4'>
          {(!!footer || !!buttons.length) && (
            <>
              <Divider />
              {buttons.length ? (
                <div className='flex w-full items-center justify-end gap-3'>
                  {buttons.map(({ className, ...buttonProps }, index) => (
                    <Button key={index} {...buttonProps} className={twJoin('max-md:w-full md:min-w-24', className)} sizing='sm' />
                  ))}
                </div>
              ) : (
                footer
              )}
            </>
          )}
        </div>
      </div>

      <div
        onClickCapture={() => {
          if (!dismissible) return;
          handleClose();
        }}
        className='w-full h-full'></div>
    </div>
  );
};

export default Modal;
