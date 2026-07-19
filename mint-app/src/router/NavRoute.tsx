import Loading from '@/components/core/loading/Loading';
import { useAuthStore } from '@/store/auth.store';
import { PARAM_ACCESS_TOKEN } from '@/utils/constants/data.constant';
import { getParamString } from '@/utils/helpers/data.helper';
import { useEffect, useMemo } from 'react';
import { Outlet, useLocation } from 'react-router-dom';

const NavRoute = () => {
  const location = useLocation();

  const { accessToken, setAccessToken } = useAuthStore();

  const token = useMemo<string | null>(() => getParamString(location.search, PARAM_ACCESS_TOKEN) ?? null, [location.search]);

  useEffect(() => {
    if (accessToken || !token) return;
    setAccessToken(token);
  }, [accessToken, token]);

  if (accessToken) {
    return <Outlet />;
  }

  return <Loading />;
};

export default NavRoute;
