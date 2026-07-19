import { logoutAPI } from '@/data/account/account.api';
import { LogoutREQ } from '@/data/account/request/logout.request';
import { useAuthStore } from '@/store/auth.store';
import { PARAM_REDIRECT } from '@/utils/constants/data.constant';
import { useMutation, useQueryClient } from '@tanstack/react-query';
import { useSearchParams } from 'react-router-dom';

const useLogout = () => {
  const queryClient = useQueryClient();
  const [_, setSearchParams] = useSearchParams();
  const { accessToken, resetAuthStore } = useAuthStore();

  const reset = () => {
    resetAuthStore();
    queryClient.removeQueries();
    queryClient.clear();
  };

  const { mutate: logoutApi, isPending: isLoading } = useMutation({
    mutationFn: (request: LogoutREQ) => logoutAPI(request),
    onSuccess: reset,
    onError: reset,
  });

  const handleLogout = async () => {
    if (!accessToken) {
      reset();
      return;
    }
    setSearchParams({ [PARAM_REDIRECT]: '0' });
    logoutApi({ token: accessToken });
  };

  return { handleLogout, isLoading };
};

export default useLogout;
