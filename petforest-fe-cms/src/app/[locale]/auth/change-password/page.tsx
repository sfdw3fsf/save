'use client';
import CustomButton from '@/components/common/CustomButton';
import CustomFormInput from '@/components/common/form/CustomFormField';
import CustomText from '@/components/common/Text';
import { changePasswordApi } from '@/store/auth/auth.api';
import { AuthChangePasswordREQ } from '@/store/auth/request/auth-login.request';
import { InputEnum } from '@/utils/enums/input.enum';
import { handleError } from '@/utils/helpers/notification-error.helper';
import { handleSuccess } from '@/utils/helpers/notification-success.helper';
import { authChangePasswordSchema } from '@/utils/schemas/auth/auth-change-password.schema';
import { yupResolver } from '@hookform/resolvers/yup';
import { useMutation } from '@tanstack/react-query';
import { useForm } from 'react-hook-form';
import { useTranslation } from 'react-i18next';

const page = () => {
  const { t: tAuth } = useTranslation('auth');

  const { control, handleSubmit, reset, watch } = useForm<AuthChangePasswordREQ>({
    resolver: yupResolver(authChangePasswordSchema),
  });

  const { mutate: mutateChangePassword, isPending: isUpdating } = useMutation({
    mutationFn: (body: AuthChangePasswordREQ) => changePasswordApi(body),
    onSuccess: () => {
      handleSuccess('update');
      reset();
    },
    onError: handleError,
  });

  const onChangePassword = (values: AuthChangePasswordREQ) => {
    mutateChangePassword(values);
  };

  return (
    <div className='flex w-full justify-center items-center flex-col p-8'>
      <CustomText variant='title-2'>{tAuth('change_password.change')}</CustomText>
      <div className='w-full max-w-[400px]'>
        <CustomFormInput
          control={control}
          name='oldPassword'
          type={InputEnum.TEXT}
          label={tAuth('change_password.current')}
          isPassword={true}
          placeholder={tAuth('change_password.place_holder')}
          classNameLabel='mt-[32px] mb-[8px]'
        />
        <CustomFormInput
          control={control}
          name='newPassword'
          type={InputEnum.TEXT}
          label={tAuth('change_password.new')}
          isPassword={true}
          placeholder={tAuth('change_password.place_holder')}
          classNameLabel='mt-3 mb-[8px] !w-full'
        />

        <CustomButton
          loading={isUpdating}
          htmlType='submit'
          type='primary'
          className='!h-[44px] mt-[32px] w-full'
          onClick={handleSubmit(onChangePassword)}>
          {tAuth('change_password.button')}
        </CustomButton>
      </div>
    </div>
  );
};

export default page;
