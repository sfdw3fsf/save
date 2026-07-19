import Button from '@/components/core/button/Button';
import SocialGoogleIcon from '@/components/icons/SocialGoogleIcon';
import { socialLoginAPI } from '@/data/account/account.api';
import { AUTH_SOCIAL_PROVIDER } from '@/utils/enums/data/auth-social-provider.enum';
import { useGoogleLogin } from '@react-oauth/google';
import { useMutation } from '@tanstack/react-query';
import { enqueueSnackbar } from 'notistack';
import { useTranslation } from 'react-i18next';
import { CustomLoginProps } from '../LoginPage';

const CustomGoogleLogin = ({ onSuccess, onError }: CustomLoginProps): JSX.Element => {
  const { t: tCommon } = useTranslation('common');

  const { mutate: googleLoginApi, isPending } = useMutation({
    mutationFn: (accessToken: string) =>
      socialLoginAPI({
        accessToken,
        provider: AUTH_SOCIAL_PROVIDER.GOOGLE,
      }),
    onSuccess,
    onError,
  });

  const login = useGoogleLogin({
    onSuccess: async (response: any) => {
      const accessToken = response.access_token;
      googleLoginApi(accessToken);
    },
    onError: (error) => {
      console.error(error);
      enqueueSnackbar({ variant: 'error', message: tCommon('default_message.error') });
    },
  });

  return (
    <Button
      onClick={() => login()}
      isLoading={isPending}
      keepLeftIconStyle
      className='w-full max-h-10 px-2.5 pr-4'
      leftIcon={<SocialGoogleIcon className='mr-1.5' />}>
      {tCommon('login.log_in_with_google_account')}
    </Button>
  );
};

export default CustomGoogleLogin;
