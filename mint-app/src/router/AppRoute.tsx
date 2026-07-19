import Loading from '@/components/core/loading/Loading';
import { UserDetailsRESP } from '@/data/user/response/user-details.response';
import { userDetailsAPI } from '@/data/user/user.api';
import { transformUserDetailsResponse } from '@/data/user/user.service';
import useLogout from '@/hooks/useLogout';
import { useAuthStore } from '@/store/auth.store';
import { BaseResponse } from '@/types/response.type';
import { useMutation } from '@tanstack/react-query';
import { useEffect, useMemo } from 'react';
import { Outlet } from 'react-router-dom';

const AppRoute = () => {
  const { accessToken, userInfo, setUserInfo } = useAuthStore();

  const shouldFetch = useMemo<boolean>(() => Boolean(accessToken && !userInfo), [accessToken, userInfo]);

  const { handleLogout, isLoading: isLoggingOut } = useLogout();

  const { mutate, isPending, isError } = useMutation({
    mutationFn: userDetailsAPI,
    onSuccess: ({ data }: BaseResponse<UserDetailsRESP>) => {
      const transformedData = transformUserDetailsResponse(data);
      setUserInfo(transformedData);
    },
  });

  useEffect(() => {
    if (!shouldFetch) return;
    mutate();
  }, [shouldFetch]);

  if (!shouldFetch) {
    return <Outlet />;
  }

  if (isPending || isLoggingOut) {
    return <Loading />;
  }

  if (isError) {
    handleLogout();
  }

  return <Loading />;
};

export default AppRoute;
