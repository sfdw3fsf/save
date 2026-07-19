import Button from '@/components/core/button/Button';
import Card from '@/components/core/card/Card';
import Divider from '@/components/core/divider/Divider';
import FormField from '@/components/core/form/form-field/FormField';
import CheckCircleIcon from '@/components/icons/CheckCircleIcon';
import { resetPasswordAPI } from '@/data/account/account.api';
import { ResetPasswordREQ } from '@/data/account/request/reset-password.request';
import { PASSWORD_RULES } from '@/utils/constants/form.constant';
import { onError } from '@/utils/helpers/data.helper';
import { ChangePasswordInput, changePasswordSchema } from '@/utils/schemas/change-password.schema';
import { yupResolver } from '@hookform/resolvers/yup';
import { useMutation } from '@tanstack/react-query';
import { useEffect, useMemo } from 'react';
import { useForm } from 'react-hook-form';
import { useTranslation } from 'react-i18next';
import { twMerge } from 'tailwind-merge';

const FORM_ID = 'change-password-form';

type ChangePasswordFormProps = {
  onSuccess: () => void;
};

const ChangePasswordForm = ({ onSuccess }: ChangePasswordFormProps): JSX.Element => {
  const { t: tCommon } = useTranslation('common');
  const { t: tForm } = useTranslation('form');

  const { control, handleSubmit, reset, watch } = useForm<ChangePasswordInput>({
    resolver: yupResolver<ChangePasswordInput>(changePasswordSchema),
    defaultValues: changePasswordSchema.getDefault(),
  });

  const watchPassword = watch('newPassword');
  const rules = useMemo(() => PASSWORD_RULES(watchPassword, tCommon), [tCommon, watchPassword]);

  const { mutate: resetPasswordApi, isPending } = useMutation({
    mutationFn: (request: ResetPasswordREQ) => resetPasswordAPI(request),
    onSuccess,
    onError,
  });

  const handleChangePassword = async ({ newPassword }: ChangePasswordInput) => {
    const request: ResetPasswordREQ = { newPassword };
    resetPasswordApi(request);
  };

  useEffect(() => {
    reset();
  }, [reset]);

  return (
    <Card>
      <form id={FORM_ID} className='flex w-full flex-col gap-6' onSubmit={handleSubmit(handleChangePassword)}>
        <div className='flex w-full flex-col gap-3 text-left'>
          <h3 className='text-display-xs font-medium text-slate-800'>{tCommon('change_password.enter_new_password')}</h3>
          <p className='text-md font-normal text-slate-500'>{tCommon('change_password.set_a_new_password')}</p>
        </div>

        <Divider />

        <div className='flex w-full flex-col gap-3 max-md:gap-2'>
          <FormField
            type='input'
            label={tForm('label.password')}
            componentProps={{
              name: 'newPassword',
              control,
              required: true,
              type: 'password',
              placeholder: tForm('placeholder.password'),
            }}
          />

          <div className='flex flex-col gap-1.5 text-xs font-normal text-left'>
            <div className='text-comp-text-secondary mb-0.5'>{tCommon('change_password.must_include')}:</div>
            {rules.map(({ text, isValid }, index) => (
              <div key={index} className='flex items-center justify-start gap-1 text-comp-icon-tertiary'>
                <div className='h-4 w-4 shrink-0 flex items-center justify-center'>
                  <CheckCircleIcon className={twMerge('text-inherit h-full w-full', isValid && 'text-comp-icon-success')} />
                </div>
                <div>{text}</div>
              </div>
            ))}
          </div>
        </div>

        <FormField
          type='input'
          label={tForm('label.match_password')}
          componentProps={{
            name: 'confirmPassword',
            control,
            required: true,
            type: 'password',
            placeholder: tForm('placeholder.match_password'),
          }}
        />
      </form>

      <div className='flex w-full flex-col'>
        <Button isLoading={isPending} form={FORM_ID} className='w-full' variant='primary'>
          {tCommon('change_password.reset_password')}
        </Button>
      </div>
    </Card>
  );
};

export default ChangePasswordForm;
