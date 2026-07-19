import i18n from '@/i18n';
import { useMemo } from 'react';
import { Link } from 'react-router-dom';
import { twJoin, twMerge } from 'tailwind-merge';

export type NavbarLinkProps = {
  children: string;
  to: string;
  className?: string;
  onClick?: () => void;
};

const NavbarLink = ({ children, to, className, onClick }: NavbarLinkProps): JSX.Element => {
  const lang = i18n.language;

  const style = useMemo(() => {
    switch (lang) {
      case 'ko':
        return 'truncate';
      case 'en':
      default:
        return 'break-words';
    }
  }, [lang]);

  return (
    <Link className={twMerge('p-1', className)} to={to} onClick={onClick}>
      <span
        className={twJoin(
          'xl:text-lg text-sm font-normal text-left text-slate-800 hover:text-slate-500 active:text-slate-800',
          style,
        )}>
        {children}
      </span>
    </Link>
  );
};

export default NavbarLink;
