import React, { ReactNode } from 'react';
import { Link } from 'react-router-dom';
type NavbarLinkprops = {
  to: string;
  className?: string;
  children: ReactNode;
};
export default function NavbarLink({ to, children, className }: NavbarLinkprops) {
  return (
    <Link to={to}>
      <span className='text-sm text-gray-600 font-normal'>{children}</span>
    </Link>
  );
}
