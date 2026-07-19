import XIcon from '@/components/icons/XIcon';
import { cloneElement, ReactElement, ReactNode, useMemo } from 'react';
import { twMerge } from 'tailwind-merge';
import { theme } from './badge.theme';

type BadgeProps = {
  show?: boolean;
  label: ReactNode;
  color?: 'green' | 'orange' | 'blue' | 'red' | 'neutral';
  type?: 'text' | 'status';
  shape?: 'square-round' | 'round';
  icon?: ReactElement;
  className?: HTMLDivElement['className'];
  onClose?: () => void;
};

const Badge = ({
  show = true,
  label,
  color = 'green',
  shape = 'square-round',
  type = 'text',
  icon,
  className,
  onClose,
}: BadgeProps) => {
  const showCloseButton = useMemo<boolean>(() => !!onClose, [onClose]);

  if (!show) return <></>;

  return (
    <div
      className={twMerge(
        'flex w-fit truncate items-center justify-center py-0.5 px-2 gap-1 border',
        icon && 'pl-1 pr-1,5',
        showCloseButton && 'pl-2 pr-1',
        icon && showCloseButton && 'px-1',
        theme.shape[shape],
        theme.color[type][color],
        className,
      )}>
      {!!icon && (
        <div className='flex size-[13px] shrink-0 items-center justify-center p-[1.5px]'>
          {cloneElement(icon, { className: twMerge('size-full', theme.icon[color]) })}
        </div>
      )}

      <div className='text-xs font-medium'>{label}</div>

      {showCloseButton && (
        <button
          type='button'
          form='_notexist'
          className='size-[14px] shrink-0 flex items-center justify-center'
          onClick={onClose}>
          <XIcon className='size-full text-inherit' />
        </button>
      )}
    </div>
  );
};

export default Badge;
