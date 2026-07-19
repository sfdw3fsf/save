import Loading from '@/components/core/loading/Loading';
import { UserDetailsRESP } from '@/data/user/response/user-details.response';
import { userDetailsAPI } from '@/data/user/user.api';
import { transformUserDetailsResponse } from '@/data/user/user.service';
import useLogout from '@/hooks/useLogout';
import { useAuthStore } from '@/store/auth.store';
import { BaseResponse } from '@/types/response.type';
import { useMutation } from '@tanstack/react-query';
import { useEffect } from 'react';
import { Outlet } from 'react-router-dom';

const PublicRoute = () => {
  const { accessToken, setUserInfo } = useAuthStore();
  const { handleLogout, isLoading } = useLogout();

  const {
    mutate: getUserDetailsApi,
    isPending: isGettingUserInfo,
    isError: hadErrorWhenGettingUserInfo,
  } = useMutation({
    mutationFn: userDetailsAPI,
    onSuccess: ({ data }: BaseResponse<UserDetailsRESP>) => {
      const transformedData = transformUserDetailsResponse(data);
      setUserInfo(transformedData);
    },
  });

  useEffect(() => {
    if (accessToken) getUserDetailsApi();
  }, [accessToken]);

  if (isGettingUserInfo || isLoading) {
    return <Loading />;
  }

  if (hadErrorWhenGettingUserInfo) {
    handleLogout();
  }

  return <Outlet />;
};

export default PublicRoute;
