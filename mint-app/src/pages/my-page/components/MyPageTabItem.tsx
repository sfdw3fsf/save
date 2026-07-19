import { cloneElement, ReactElement, useMemo } from 'react';
import { Link, useLocation } from 'react-router-dom';
import { twMerge } from 'tailwind-merge';

export type MyPageTabItemProps = {
  label: string;
  activeIcon?: ReactElement;
  icon: ReactElement;
  to: string;
};

const MyPageTabItem = ({ label, activeIcon, icon, to }: MyPageTabItemProps): JSX.Element => {
  const location = useLocation();
  const isActive = useMemo(() => location.pathname === to, [location.pathname, to]);
  return (
    <Link
      title={label}
      className={twMerge(
        'flex w-full gap-1.5 h-9 p-2 text-left max-md:text-center items-center justify-start max-md:justify-center truncate rounded-lg text-sm font-medium',
        isActive ? 'bg-primary-50 text-comp-text-brand-secondary' : 'text-comp-text-secondary hover:bg-gray-100',
      )}
      to={to}>
      <span className='flex items-center justify-center size-5 shrink-0'>
        {cloneElement(isActive ? (activeIcon ?? icon) : icon, {
          className: twMerge('w-full h-full text-inherit', isActive && 'text-comp-icon-brand'),
        })}
      </span>
      <span className='truncate'>{label}</span>
    </Link>
  );
};

export default MyPageTabItem;
