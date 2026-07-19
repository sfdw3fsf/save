import Button from '@/components/core/button/Button';
import FormField from '@/components/core/form/form-field/FormField';
import { userCheckPasswordAPI } from '@/data/user/user.api';
import { EXCEPTION_CODE } from '@/utils/enums/data/exception-code.enum';
import { getErrorMessage, getExceptionCode } from '@/utils/helpers/data.helper';
import { CurrentPasswordSchema } from '@/utils/schemas/my-page-schema';
import { yupResolver } from '@hookform/resolvers/yup';
import { useMutation } from '@tanstack/react-query';
import { AxiosError } from 'axios';
import { enqueueSnackbar } from 'notistack';
import { SubmitHandler, useForm } from 'react-hook-form';
import { useTranslation } from 'react-i18next';

type Step1_CurrentPasswordFormProps = {
  onNext: (step: number) => void;
  onClose: () => void;
};

type CurrentPasswordFormValues = {
  password: string;
};

const FORM_ID = 'current_password';

const Step1_CurrentPasswordForm = ({ onNext, onClose }: Step1_CurrentPasswordFormProps): JSX.Element => {
  const { t: tCommon } = useTranslation('common');
  const { control, handleSubmit, setError } = useForm<CurrentPasswordFormValues>({
    resolver: yupResolver(CurrentPasswordSchema),
  });

  const { mutate: checkCurrentPassword, isPending } = useMutation({
    mutationFn: (request: any) => userCheckPasswordAPI(request),
    onSuccess: () => {
      onNext(2);
    },
    onError: (error: AxiosError) => {
      const exceptionCode = getExceptionCode(error);

      switch (exceptionCode) {
        case EXCEPTION_CODE['BAD_CREDENTIAL_EXCEPTION']: {
          setError('password', { type: 'verify', message: 'verify.login_password' });
          return;
        }
        default: {
          const message = getErrorMessage(error);
          enqueueSnackbar({ variant: 'error', message });
        }
      }
    },
  });
  const onSubmit: SubmitHandler<CurrentPasswordFormValues> = (data) => {
    checkCurrentPassword({ password: data.password, type: 'CHANGE_EMAIL' });
  };

  return (
    <div className='flex flex-col justify-center'>
      <div className='flex flex-col gap-1 justify-center'>
        <h1 className='font-medium text-lg text-center'>{tCommon('my-page.change_email_modal_button')}</h1>
        <p className=' text-sm text-slate-500 text-center'>{tCommon('my-page.change_email_subtitle')}</p>
      </div>
      <form id={FORM_ID} onSubmit={handleSubmit(onSubmit)}>
        <FormField
          className='mt-5 mb-10'
          type='input'
          label={tCommon('my-page.current_password')}
          componentProps={{
            name: 'password',
            type: 'password',
            control,
            required: true,
            placeholder: tCommon('my-page-placeholder.current_password'),
          }}
        />
        <div className='flex gap-3 w-full justify-center pt-4 border-t border-slate-200'>
          <Button className=' w-full' onClick={() => onClose()}>
            {' '}
            {tCommon('button.cancel')}
          </Button>
          <Button className=' w-full' form={FORM_ID} type='submit' variant='primary'>
            {tCommon('find_id_password.confirm')}
          </Button>
        </div>
      </form>
    </div>
  );
};

export default Step1_CurrentPasswordForm;
