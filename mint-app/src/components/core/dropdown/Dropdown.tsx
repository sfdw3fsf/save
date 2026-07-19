import useOutsideClick from '@/hooks/useOutsideClick';
import i18n from '@/i18n';
import { ReactElement, ReactNode, useRef, useState } from 'react';
import { twMerge } from 'tailwind-merge';

export type DropdownItem = {
  label: ReactNode;
  value: any;
  leftIcon?: ReactNode;
  rightIcon?: ReactNode;
  disabled?: boolean;
};

export type DropdownAlign = 'left' | 'center';
export type DropdownProps = {
  children?: ReactNode;
  className?: string;
  items: DropdownItem[];
  onSelect?: (value: any) => void;
  align?: DropdownAlign;
  icons?: { close: ReactElement; open: ReactElement };
  menuContainerClassName?: string;
  menuAlign?: 'left' | 'right';
  menuClassName?: string;
};

const theme = {
  align: {
    left: 'justify-start text-left',
    center: 'justify-center text-center',
  },
  menuAlign: {
    left: 'left-0',
    right: 'right-0',
  },
};

const Dropdown = ({
  children,
  items,
  onSelect,
  className,
  align = 'left',
  icons,
  menuContainerClassName,
  menuAlign = 'left',
  menuClassName,
}: DropdownProps): JSX.Element => {
  const [open, setOpen] = useState<boolean>(false);

  const dropdownRef = useRef<HTMLDivElement | null>(null);

  const toggleOpen = () => {
    setOpen((prev) => !prev);
  };

  const handleSelect = (value: any) => {
    onSelect?.(value);
    toggleOpen();
  };

  useOutsideClick(dropdownRef, () => {
    if (open) setOpen(false);
  });

  if (!children && !icons) return <></>;

  return (
    <div ref={dropdownRef} className={twMerge('relative inline-block w-fit', className)} onMouseLeave={() => setOpen(false)}>
      <div onClickCapture={toggleOpen}>{icons ? (open ? icons.open : icons.close) : children}</div>
      {open && (
        <div className={twMerge('block w-full absolute pt-0.5 z-[10]', theme.menuAlign[menuAlign], menuContainerClassName)}>
          <ul
            className={twMerge(
              'shadow-elevation-lg rounded-lg bg-white overflow-hidden w-full border border-slate-200',
              menuClassName,
            )}>
            {items.map((item, index) => (
              <div key={index} className='w-full'>
                <button
                  role='option'
                  aria-selected={item.value === i18n.language}
                  className={twMerge(
                    'group w-full flex items-center text-sm text-slate-800 disabled:text-soft-400 py-2.5 px-3.5 gap-1 min-h-10 bg-white enabled:hover:bg-slate-50',
                    theme.align[align],
                    item.value === i18n.language && 'bg-slate-50 enabled:hover:bg-slate-100 font-medium',
                  )}
                  onClick={() => handleSelect(item.value)}
                  disabled={item.disabled}>
                  {item.leftIcon}
                  <div className='flex items-center'>{item.label}</div>
                  {item.rightIcon}
                </button>
              </div>
            ))}
          </ul>
        </div>
      )}
    </div>
  );
};

export default Dropdown;
