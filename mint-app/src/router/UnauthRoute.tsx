import { useAuthStore } from '@/store/auth.store';
import { useMemo } from 'react';
import { Navigate, Outlet, useLocation } from 'react-router-dom';
import { CACHE_ROUTES, ROUTES } from './routes.constant';

const UnauthRoute = () => {
  const { accessToken } = useAuthStore();

  const location = useLocation();
  const from = location.state?.from?.pathname;
  const to = useMemo(() => (CACHE_ROUTES.some((i) => i.includes(from)) ? from : ROUTES.HOME), [from]);

  if (accessToken) {
    return <Navigate to={to} replace />;
  }

  return <Outlet />;
};

export default UnauthRoute;
