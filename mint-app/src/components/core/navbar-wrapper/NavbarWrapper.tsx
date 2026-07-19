import { PARAM_SCROLL_POSITION } from '@/utils/constants/data.constant';
import { NAVBAR_MAPPER } from '@/utils/constants/navbar-mapper.constant';
import { useEffect, useMemo } from 'react';
import { Outlet, useLocation, useSearchParams } from 'react-router-dom';
import Navbar, { NavbarVariant } from '../navbar/Navbar';

export const NavbarWrapper = () => {
  const location = useLocation();

  const [searchParams] = useSearchParams();
  const scrollPosition = searchParams.get(PARAM_SCROLL_POSITION);

  const findVariant = (pathname: string): NavbarVariant | undefined => {
    const navbarVariants = Object.keys(NAVBAR_MAPPER) as NavbarVariant[];
    const variant = navbarVariants.find((variant) => NAVBAR_MAPPER[variant].some((v) => pathname.includes(v)));
    return variant;
  };

  const variant = useMemo<NavbarVariant | undefined>(() => findVariant(location.pathname), [location.pathname]);

  useEffect(() => {
    if (scrollPosition) return;

    if ('scrollRestoration' in window.history) {
      window.history.scrollRestoration = 'manual';
    }

    window.scrollTo({ top: 0, left: 0, behavior: 'smooth' });

    return () => {
      if ('scrollRestoration' in window.history) {
        window.history.scrollRestoration = 'auto';
      }
    };
  }, [location, scrollPosition]);

  return (
    <>
      <Navbar variant={variant} />
      <Outlet />
    </>
  );
};
