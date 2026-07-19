import Button from '@/components/core/button/Button';
import FormField from '@/components/core/form/form-field/FormField';
import { userRequestChangeEmailAPI } from '@/data/user/user.api';
import { EXCEPTION_CODE } from '@/utils/enums/data/exception-code.enum';
import { getErrorMessage, getExceptionCode } from '@/utils/helpers/data.helper';
import { NewEmailSchema } from '@/utils/schemas/my-page-schema';
import { yupResolver } from '@hookform/resolvers/yup';
import { useMutation } from '@tanstack/react-query';
import { AxiosError } from 'axios';
import { enqueueSnackbar } from 'notistack';
import { SubmitHandler, useForm } from 'react-hook-form';
import { useTranslation } from 'react-i18next';

type Step2_NewEmailFormProps = {
  onNext: (step: number) => void;
  setEmail: (email: string) => void;
  onClose: () => void;
};

type NewEmailFormValues = {
  email: string;
};
const FORM_ID = 'new_email_form';
const Step2_NewEmailForm = ({ onNext, setEmail, onClose }: Step2_NewEmailFormProps): JSX.Element => {
  const { t: tCommon } = useTranslation('common');
  const { t: tForm } = useTranslation('form');
  const { control, handleSubmit, setError } = useForm<NewEmailFormValues>({
    resolver: yupResolver(NewEmailSchema),
  });

  const {
    mutate: userRequestChangeEmail,
    isPending,
    error,
  } = useMutation({
    mutationFn: (request: any) => userRequestChangeEmailAPI(request),
    onSuccess: (data, variables) => {
      setEmail(variables.newEmail);
      onNext(3);
    },
    onError: (error: AxiosError) => {
      const exceptionCode = getExceptionCode(error);

      switch (exceptionCode) {
        case EXCEPTION_CODE['MINT.INT.EX.019']: {
          setError('email', { type: 'verify', message: 'verify.duplicated_email' });
          return;
        }
        default: {
          const message = getErrorMessage(error);
          enqueueSnackbar({ variant: 'error', message });
        }
      }
    },
  });
  const handleClose = () => {
    onClose();
    onNext(1);
  };
  const onSubmit: SubmitHandler<NewEmailFormValues> = (data) => {
    userRequestChangeEmail({ newEmail: data.email });
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
          label={tCommon('my-page.new_email')}
          componentProps={{
            name: 'email',
            control,
            required: true,
            placeholder: tForm('placeholder.email'),
          }}
        />

        <div className='flex gap-3 w-full justify-center pt-4 border-t border-slate-200'>
          <Button className=' w-full' type='button' onClick={handleClose}>
            {tCommon('button.cancel')}
          </Button>
          <Button isLoading={isPending} className=' w-full' form={FORM_ID} type='submit' variant='primary'>
            {tCommon('button.save')}
          </Button>
        </div>
      </form>
    </div>
  );
};

export default Step2_NewEmailForm;
