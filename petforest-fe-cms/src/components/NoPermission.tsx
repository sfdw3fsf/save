import { logoutApi } from '@/store/auth/auth.api';
import { useAuthStore } from '@/store/auth/auth.store';
import { MY_ROUTE } from '@/utils/constants/route.constant';
import { handleError } from '@/utils/helpers/notification-error.helper';
import { useMutation, useQueryClient } from '@tanstack/react-query';
import { Button } from 'antd';
import { deleteCookie } from 'cookies-next';
import { useRouter } from 'next/navigation';
import { useTranslation } from 'react-i18next';
import CustomText from './common/Text';

const NoPermission = () => {
  const { t: tCommon } = useTranslation('common');
  const queryClient = useQueryClient();
  const logout = useAuthStore((state) => state.logout);
  const router = useRouter();

  const { mutate: mutateLogout } = useMutation({
    mutationFn: () => logoutApi(),
    onSuccess: () => {},
    onError: handleError,
  });

  const onLogout = () => {
    deleteCookie('token');
    deleteCookie('id');
    deleteCookie('isLogin');
    deleteCookie('stay');
    logout();
    queryClient.removeQueries();
    router.push(MY_ROUTE.AUTH.LOGIN);
    mutateLogout();
  };
  return (
    <div className='h-screen w-screen flex items-center justify-center flex-col'>
      <CustomText variant='heading-1' className='w-screen h-screen items-center justify-center flex !text-primary-1000'>
        {tCommon('no_permission')}
      </CustomText>

      <Button onClick={onLogout} className='!rounded-[8px] !gap-[8px] flex items-center !text-neutral-100'>
        로그아웃
      </Button>
    </div>
  );
};

export default NoPermission;
