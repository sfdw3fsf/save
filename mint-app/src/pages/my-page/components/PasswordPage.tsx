import Button from '@/components/core/button/Button';
import FormField from '@/components/core/form/form-field/FormField';
import { SuccessToast } from '@/components/core/success-notification/SuccessNotification';
import PermissionAccept from '@/components/hocs/PermissionAccept';
import CheckCircleIcon from '@/components/icons/CheckCircleIcon';
import { UserUpdatePasswordREQ } from '@/data/user/request/user-details-request';
import { userUpdatePasswordAPI } from '@/data/user/user.api';
import { PASSWORD_RULES } from '@/utils/constants/form.constant';
import { USER_ROLE } from '@/utils/enums/user/user-role.enum';
import { UpdatePasswordInput, updatePasswordSchema } from '@/utils/schemas/update-password.schema';
import { yupResolver } from '@hookform/resolvers/yup';
import { useMutation } from '@tanstack/react-query';
import { useEffect, useMemo } from 'react';
import { useForm } from 'react-hook-form';
import { useTranslation } from 'react-i18next';
import { useNavigate } from 'react-router-dom';
import { twMerge } from 'tailwind-merge';
import MyPageContainer from './MyPageContainer';

const FORM_ID = 'update-password-form';

const PasswordPage = PermissionAccept(
  (): JSX.Element => {
    const { t: tCommon } = useTranslation('common');
    const navigate = useNavigate();
    const { control, handleSubmit, reset, watch } = useForm<UpdatePasswordInput>({
      resolver: yupResolver<UpdatePasswordInput>(updatePasswordSchema),
      defaultValues: updatePasswordSchema.getDefault(),
    });
    const watchPassword = watch('newPassword');
    const rules = useMemo(() => PASSWORD_RULES(watchPassword, tCommon), [tCommon, watchPassword]);
    const { mutate: updatePasswordAPI, isPending } = useMutation({
      mutationFn: (request: UserUpdatePasswordREQ) => userUpdatePasswordAPI(request),
      onSuccess: () => {
        SuccessToast({
          label: tCommon('my-page.success_change_password_label_toast'),
          content: tCommon('my-page.success_change_passord_content_toast'),
        });
      },
    });
    const handleUpdatePassword = async ({ oldPassword, newPassword, confirmPassword }: UpdatePasswordInput) => {
      const request: UserUpdatePasswordREQ = { oldPassword, newPassword, confirmPassword };
      updatePasswordAPI(request);
    };
    useEffect(() => {
      reset();
    }, [reset]);

    //the problem coms
    return (
      <MyPageContainer description={tCommon('my-page.password_guide')} title={tCommon('my-page.change_password')}>
        <form id={FORM_ID} className='flex flex-col gap-6 max-w-[698px] w-full' onSubmit={handleSubmit(handleUpdatePassword)}>
          <div className='flex flex-col gap-6'>
            <FormField
              type='input'
              className='grid grid-cols-2 max-xl:grid-cols-1'
              label={tCommon('my-page.current_password')}
              componentProps={{
                name: 'oldPassword',
                type: 'password',
                control,
                required: true,
                placeholder: tCommon('my-page-placeholder.current_password'),
              }}
            />
            <FormField
              className='grid grid-cols-2 max-xl:grid-cols-1'
              type='input'
              label={tCommon('my-page.new_password')}
              componentProps={{
                name: 'newPassword',
                type: 'password',
                control,
                required: true,
                placeholder: tCommon('my-page-placeholder.new_password'),
              }}
              expandComponent={
                <div className='flex flex-col gap-1.5 text-xs font-normal w-full mt-3'>
                  <div className='text-comp-text-secondary'>{tCommon('change_password.must_include')}:</div>
                  {rules.map(({ text, isValid }, index) => (
                    <div key={index} className='flex items-center justify-start gap-1 text-comp-icon-tertiary '>
                      <div className='h-4 w-4 shrink-0 flex items-center justify-center max-w-full'>
                        <CheckCircleIcon className={twMerge('text-inherit h-full w-full', isValid && 'text-comp-icon-success')} />
                      </div>
                      <div>{text}</div>
                    </div>
                  ))}
                </div>
              }
            />
          </div>
          <FormField
            className='grid grid-cols-2 max-xl:grid-cols-1'
            type='input'
            label={tCommon('my-page.confirm_new_password')}
            componentProps={{
              name: 'confirmPassword',
              type: 'password',
              control,
              required: true,
              placeholder: tCommon('my-page.confirm_new_password'),
            }}
          />
          <div className='flex flex-row gap-3 mt-2  border-t pt-5 justify-end border-slate-200'>
            <div className='max-w-[200px] w-full flex flex-row gap-3 max-h-[36px]'>
              <Button sizing='2sm' className='px-3 py-2 w-full max-w-[94px] max-h-[36px]' onClick={() => navigate(-1)}>
                {tCommon('button.cancel')}
              </Button>
              <Button
                className='px-3 py-2 w-full  max-w-[94px] max-h-[36px]'
                variant='primary'
                sizing='2sm'
                isLoading={isPending}
                form={FORM_ID}
                type='submit'>
                {tCommon('my-page.confirm')}
              </Button>
            </div>
          </div>
        </form>
      </MyPageContainer>
    );
  },
  [USER_ROLE.EXPERT, USER_ROLE.MEMBER],
  [],
);

export default PasswordPage;
