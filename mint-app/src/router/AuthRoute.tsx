import { useAuthStore } from '@/store/auth.store';
import { PARAM_REDIRECT } from '@/utils/constants/data.constant';
import { useMemo } from 'react';
import { Navigate, Outlet, useLocation, useSearchParams } from 'react-router-dom';
import { ROUTES } from './routes.constant';

const AuthRoute = () => {
  const { accessToken } = useAuthStore();
  const location = useLocation();

  const [searchParams] = useSearchParams();
  const shouldRedirect = useMemo(() => searchParams.get(PARAM_REDIRECT) !== '0', [searchParams]);

  if (!accessToken) {
    return <Navigate to={ROUTES.LOGIN} state={{ from: shouldRedirect ? location : null }} />;
  }

  return <Outlet />;
};

export default AuthRoute;
