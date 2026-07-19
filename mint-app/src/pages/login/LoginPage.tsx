import Alert from '@/components/core/alert/Alert';
import Button from '@/components/core/button/Button';
import Card from '@/components/core/card/Card';
import Divider from '@/components/core/divider/Divider';
import FormField from '@/components/core/form/form-field/FormField';
import PageContainer from '@/components/core/page-container/PageContainer';
import KeyIcon from '@/components/icons/KeyIcon';
import UserFocusIcon from '@/components/icons/UserFocusIcon';
import { transformSocialLoginResponse } from '@/data/account/account.service';
import { SocialLoginRESP } from '@/data/account/response/social-login.response';
import { loginAPI } from '@/data/auth/auth.api';
import { transformLoginResponse } from '@/data/auth/auth.service';
import { LoginREQ } from '@/data/auth/request/login.request';
import { LoginRESP } from '@/data/auth/response/login.response';
import { ROUTES } from '@/router/routes.constant';
import { useAuthStore } from '@/store/auth.store';
import { BaseResponse } from '@/types/response.type';
import { UserAuthProvider } from '@/types/user-auth-provider.type';
import { UserInfo } from '@/types/user-info.type';
import { EXCEPTION_CODE } from '@/utils/enums/data/exception-code.enum';
import { getErrorMessage, getExceptionCode } from '@/utils/helpers/data.helper';
import { LoginInput, loginSchema } from '@/utils/schemas/login.schema';
import { yupResolver } from '@hookform/resolvers/yup';
import { useMutation } from '@tanstack/react-query';
import { AxiosError } from 'axios';
import { enqueueSnackbar } from 'notistack';
import { useEffect, useState } from 'react';
import { useForm } from 'react-hook-form';
import { useTranslation } from 'react-i18next';
import { useNavigate } from 'react-router-dom';
import CustomGoogleLogin from './components/CustomGoogleLogin';
import CustomKakaoLogin from './components/CustomKakaoLogin';

const FORM_ID = 'login-form';

export type CustomLoginProps = {
  onSuccess: (response: BaseResponse<SocialLoginRESP>) => void;
  onError: (error: AxiosError) => void;
};

const LoginPage = (): JSX.Element => {
  const { t: tCommon } = useTranslation('common');
  const { t: tForm } = useTranslation('form');

  const navigate = useNavigate();

  const [openBannedAlert, setOpenBannedAlert] = useState<boolean>(false);
  const [openMissApprovalAlert, setOpenMissApprovalAlert] = useState<boolean>(false);
  const authStore = useAuthStore();

  const { control, handleSubmit, reset, setError } = useForm<LoginInput>({
    resolver: yupResolver<LoginInput>(loginSchema),
    defaultValues: loginSchema.getDefault(),
  });

  const handleLogin = (accessToken: string, userInfo: UserInfo, userAuthProvider?: UserAuthProvider) => {
    authStore.setAuthStore(accessToken, userInfo, userAuthProvider);
  };

  const handleSocialSuccess = ({ data }: BaseResponse<SocialLoginRESP>) => {
    const { accessToken, userAuthProvider, userInfo } = transformSocialLoginResponse(data);
    handleLogin(accessToken, userInfo, userAuthProvider);
  };

  const { mutate: loginApi, isPending } = useMutation({
    mutationFn: (request: LoginREQ) => loginAPI(request),
    onSuccess: ({ data }: BaseResponse<LoginRESP>) => {
      const { accessToken, userInfo } = transformLoginResponse(data);
      handleLogin(accessToken, userInfo);
    },
    onError: (error: AxiosError) => handleLoginError(error),
  });

  const handleLoginError = (error: AxiosError) => {
    const exceptionCode = getExceptionCode(error);

    switch (exceptionCode) {
      case EXCEPTION_CODE['MINT.INT.EX.022']:
      case EXCEPTION_CODE.BAD_CREDENTIAL_EXCEPTION:
      case EXCEPTION_CODE.VALIDATION_VIOLATION_EXCEPTION: {
        setError('username', { type: 'verify', message: 'verify.login_username' });
        setError('password', { type: 'verify', message: 'verify.login_password' });
        return;
      }
      case EXCEPTION_CODE['MINT.INT.EX.026']:
      case EXCEPTION_CODE.LOCKED_EXCEPTION: {
        setOpenBannedAlert(true);
        return;
      }
      case EXCEPTION_CODE['MINT.INT.EX.042']: {
        setOpenMissApprovalAlert(true);
        return;
      }
      default: {
        const message = getErrorMessage(error);
        enqueueSnackbar({ variant: 'error', message });
      }
    }
  };

  const onSubmit = async ({ username, password, saveId }: LoginInput) => {
    const request: LoginREQ = { username, password, saveId };
    loginApi(request);
  };

  useEffect(() => {
    reset();
  }, [reset]);

  return (
    <PageContainer withBackground title={tCommon('title.login')}>
      <Card>
        <form
          id={FORM_ID}
          className='flex w-full flex-col gap-6'
          onSubmit={(e) => {
            e.preventDefault();
            setOpenBannedAlert(false);
            handleSubmit(onSubmit)(e);
          }}>
          <div className='flex w-full flex-col gap-3 text-left'>
            <h2 className='text-display-xs font-medium'>{tCommon('login.log_in')}</h2>
            <p className='text-slate-500 text-md font-normal'>{tCommon('login.enter_your_details_to_log_in')}</p>
          </div>

          <Divider />

          <div className='flex w-full flex-col gap-3 text-left'>
            <FormField
              type='input'
              label={tForm('label.id')}
              componentProps={{
                name: 'username',
                control,
                required: true,
                placeholder: tForm('placeholder.username'),
              }}
            />
            <FormField
              type='input'
              label={tForm('label.password')}
              componentProps={{
                name: 'password',
                control,
                required: true,
                type: 'password',
                placeholder: tForm('placeholder.login_password'),
              }}
            />
          </div>
          <FormField
            type='option'
            componentProps={{
              control,
              type: 'checkbox',
              name: 'saveId',
              label: tCommon('login.save_id'),
            }}
          />

          <Alert open={openBannedAlert} onClose={() => setOpenBannedAlert(false)} title={tCommon('login.account_locked')}>
            {tCommon('login.your_account_has_been_locked')}
          </Alert>

          <Alert
            open={openMissApprovalAlert}
            onClose={() => setOpenMissApprovalAlert(false)}
            title={tCommon('login.admin_approval_required')}>
            {tCommon('login.contact_admin_approval')}
          </Alert>
        </form>

        <div className='flex w-full flex-col gap-6'>
          <div className='flex w-full flex-col items-center justify-center gap-3'>
            <Button className='w-full' form={FORM_ID} variant='primary' isLoading={isPending}>
              {tCommon('login.log_in')}
            </Button>

            <CustomGoogleLogin onError={handleLoginError} onSuccess={handleSocialSuccess} />

            <CustomKakaoLogin onError={handleLoginError} onSuccess={handleSocialSuccess} />
          </div>

          <div className='flex w-full items-center justify-center gap-4'>
            <Button
              className='min-h-5'
              labelContStyle='gap-1'
              keepLeftIconStyle
              leftIcon={<UserFocusIcon className='h-5 w-5 text-slate-800' />}
              variant='link'
              onClick={() => navigate(ROUTES.FIND.ID)}>
              {tCommon('login.find_id')}
            </Button>
            <Button
              className='min-h-5'
              labelContStyle='gap-1'
              keepLeftIconStyle
              leftIcon={<KeyIcon className='h-5 w-5 text-slate-800' />}
              variant='link'
              onClick={() => navigate(ROUTES.FIND.PASSWORD)}>
              {tCommon('login.find_password')}
            </Button>
          </div>

          <div className='flex items-center justify-center gap-1 text-center'>
            <span className='text-sm font-normal text-slate-500'>{tCommon('login.dont_have_an_account')}</span>
            <Button
              className='max-h-5 overflow-hidden min-h-fit'
              sizing='sm'
              variant='linkColor'
              onClick={() => navigate(ROUTES.SIGN_UP.self)}>
              {tCommon('login.sign_up')}
            </Button>
          </div>
        </div>
      </Card>
    </PageContainer>
  );
};

export default LoginPage;
