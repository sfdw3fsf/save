import CustomButton from '@/components/common/CustomButton';
import CustomFormInput from '@/components/common/form/CustomFormField';
import CustomText from '@/components/common/Text';
import { getMenuPermissionApi, loginApi } from '@/store/auth/auth.api';
import { initialLoginValues, menuPermissionRespToDto } from '@/store/auth/auth.service';
import { useAuthStore } from '@/store/auth/auth.store';
import { MenuPermissionDTO } from '@/store/auth/dto/auth-menu-permission.dto';
import { AuthLoginREQ } from '@/store/auth/request/auth-login.request';
import { MY_ROUTE } from '@/utils/constants/route.constant';
import { InputEnum } from '@/utils/enums/input.enum';
import { USER_PERMISSION } from '@/utils/enums/manager/manager.enum';
import { authLoginSchema } from '@/utils/schemas/auth/auth-login.schema';
import { yupResolver } from '@hookform/resolvers/yup';
import { useMutation } from '@tanstack/react-query';
import { Checkbox } from 'antd';
import { getCookie, setCookie } from 'cookies-next';
import { useRouter } from 'next/navigation';
import { useEffect, useState } from 'react';
import { useForm } from 'react-hook-form';
import { useTranslation } from 'react-i18next';
import { toast } from 'react-toastify';

export const LoginForm = () => {
  const { t: tAuth } = useTranslation('auth');

  const router = useRouter();
  const { setIsLogin, setToken } = useAuthStore((state) => state);

  const { control, handleSubmit, reset, watch } = useForm<AuthLoginREQ>({
    defaultValues: initialLoginValues,
    resolver: yupResolver(authLoginSchema),
  });

  const [isRememberId, setIsRememberId] = useState<boolean>(false);
  const [isStayLogin, setIsStayLogin] = useState<boolean>(false);
  const [permissions, setPermissions] = useState<MenuPermissionDTO>();
  const watchUsername = watch('username');
  const rememberUsername = getCookie('id');

  const { mutate: mutateUserPermission } = useMutation({
    mutationFn: () => getMenuPermissionApi(),
    onSuccess: (response) => {
      const data = menuPermissionRespToDto(response.data.data);
      setPermissions(data);
    },
  });

  const { mutate: mutateLogin, isPending: isLogging } = useMutation({
    mutationFn: (body: AuthLoginREQ) => loginApi(body),
    onSuccess: async (response) => {
      setIsLogin(true);
      setToken(response.data.data.accessToken);
      setCookie('token', response.data.data.accessToken);
      setCookie('isLogin', 'true');

      if (isStayLogin) {
        setCookie('stay', 'true');
      }

      if (isRememberId && watchUsername) {
        setCookie('id', watchUsername);
      }

      await mutateUserPermission();
    },
    onError: () => {
      toast.error('아이디 혹은 비밀번호가 다릅니다.');
    },
  });

  const onSubmit = (data: AuthLoginREQ) => {
    mutateLogin(data);
  };

  useEffect(() => {
    if (!rememberUsername) return;
    setIsRememberId(true);
    reset({ username: rememberUsername.toString() });
  }, [rememberUsername]);

  useEffect(() => {
    if (permissions && permissions.DASHBOARD && permissions.DASHBOARD !== USER_PERMISSION.NOT_ALLOW) {
      router.push(MY_ROUTE.DASHBOARD);
    } else if (permissions) {
      router.push('/');
    }
  }, [permissions, router]);

  return (
    <div className='flex mt-[48px] flex-col w-full items-center lg:mt-0 lg:items-center lg:justify-center'>
      <div className='text-left flex flex-col w-full px-4 max-w-[420px]'>
        <div>
          <img src='/images/Logo.svg' alt='Logo' />
        </div>
        <div className='flex flex-col gap-3 mt-6'>
          <CustomText variant='title-2'>{tAuth('login.welcome')}</CustomText>
          <CustomText variant='body-1-normal' className='!text-neutral-60'>
            {tAuth('login.welcome_back')}
          </CustomText>
        </div>
        <div className='flex flex-col mt-6'>
          <CustomFormInput
            control={control}
            name='username'
            label='ID'
            type={InputEnum.TEXT}
            placeholder={tAuth('login.enter_id')}
          />
          <CustomFormInput
            control={control}
            name='password'
            label={tAuth('login.password')}
            isPassword={true}
            type={InputEnum.TEXT}
            placeholder={tAuth('login.enter_password')}
            classNameLabel='mt-[20px]'
          />
        </div>

        <div className='mt-5 mb-10 grid grid-cols-2 gap-4'>
          <Checkbox
            checked={isRememberId}
            onChange={() => {
              setIsRememberId((pre) => !pre);
            }}>
            {tAuth('login.remember_id')}
          </Checkbox>
          <Checkbox
            checked={isStayLogin}
            onChange={() => {
              setIsStayLogin((pre) => !pre);
            }}>
            {tAuth('login.stay_logged_in')}
          </Checkbox>
        </div>
        <CustomButton loading={isLogging} htmlType='submit' type='primary' className='!h-[44px]' onClick={handleSubmit(onSubmit)}>
          {tAuth('login.button')}
        </CustomButton>
      </div>
    </div>
  );
};
