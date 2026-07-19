import Button from '@/components/core/button/Button';
import SocialKakaoIcon from '@/components/icons/SocialKakaoIcon';
import { socialLoginAPI } from '@/data/account/account.api';
import { KAKAO_CLIENT_ID } from '@/utils/constants/data.constant';
import { AUTH_SOCIAL_PROVIDER } from '@/utils/enums/data/auth-social-provider.enum';
import { useMutation } from '@tanstack/react-query';
import { enqueueSnackbar } from 'notistack';
import { useTranslation } from 'react-i18next';
import KakaoLogin from 'react-kakao-login';
import { CustomLoginProps } from '../LoginPage';

const CustomKakaoLogin = ({ onSuccess, onError }: CustomLoginProps): JSX.Element => {
  const { t: tCommon } = useTranslation('common');

  const { mutate: kakaoLoginApi, isPending } = useMutation({
    mutationFn: (accessToken: string) =>
      socialLoginAPI({
        accessToken,
        provider: AUTH_SOCIAL_PROVIDER.KAKAO,
      }),
    onSuccess,
    onError,
  });

  const handleLogin = async (response: any) => {
    const accessToken = response?.response?.access_token;
    if (accessToken) {
      kakaoLoginApi(accessToken);
      return;
    }
    handleError();
  };

  const handleError = () => {
    enqueueSnackbar({ variant: 'error', message: tCommon('default_message.error') });
  };

  return (
    <KakaoLogin
      token={KAKAO_CLIENT_ID}
      useLoginForm
      onSuccess={(response: any) => handleLogin(response)}
      onFail={() => handleError()}
      render={({ onClick }) => (
        <Button
          isLoading={isPending}
          onClick={onClick}
          keepLeftIconStyle
          className='w-full max-h-10 px-2.5 pr-4'
          leftIcon={<SocialKakaoIcon className='mr-1.5' />}>
          {tCommon('login.log_in_with_kakao')}
        </Button>
      )}
    />
  );
};

export default CustomKakaoLogin;
