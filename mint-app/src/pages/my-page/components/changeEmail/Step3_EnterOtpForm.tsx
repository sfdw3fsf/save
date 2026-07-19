import Button from '@/components/core/button/Button';
import Divider from '@/components/core/divider/Divider';
import FormField from '@/components/core/form/form-field/FormField';
import { SuccessToast } from '@/components/core/success-notification/SuccessNotification';
import { userRequestUpdateEmailAPI } from '@/data/user/user.api';
import { QUERY_KEYS } from '@/utils/constants/data.constant';
import { EXCEPTION_CODE } from '@/utils/enums/data/exception-code.enum';
import { getErrorMessage, getExceptionCode } from '@/utils/helpers/data.helper';
import { useMutation, useQueryClient } from '@tanstack/react-query';
import { AxiosError } from 'axios';
import { enqueueSnackbar } from 'notistack';
import { SubmitHandler, useForm } from 'react-hook-form';
import { Trans, useTranslation } from 'react-i18next';
type Step3_EnterOtpFormProps = {
  onNext: (step: number) => void;
  newEmail: string;
  onClose: () => void;
  setCloseEmail?: any;
  queryKey?: string;
  email?: string;
};

type OtpFormValues = {
  otp: string;
};

const Step3_EnterOtpForm = ({
  onNext,
  newEmail,
  onClose,
  setCloseEmail,
  queryKey,
  email,
}: Step3_EnterOtpFormProps): JSX.Element => {
  const { control, handleSubmit, setError } = useForm<OtpFormValues>({
    defaultValues: {
      otp: '',
    },
  });
  const queryClient = useQueryClient();
  console.log(email);
  const {
    mutate: userRequestUpdateEmail,
    isPending,
    error,
  } = useMutation({
    mutationFn: (request: any) => userRequestUpdateEmailAPI(request),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: [QUERY_KEYS.USER_DETAILS] });

      setCloseEmail(false);
      SuccessToast({
        label: tCommon('my-page.success_email_label'),
        content: tCommon('my-page.success_email_content'),
      });
      onNext(1);
    },
    onError: (error: AxiosError) => {
      const exceptionCode = getExceptionCode(error);

      switch (exceptionCode) {
        case EXCEPTION_CODE['MINT.INT.EX.006']: {
          setError('otp', { type: 'verify', message: 'verify.otp' });
          return;
        }
        default: {
          const message = getErrorMessage(error);
          enqueueSnackbar({ variant: 'error', message });
        }
      }
    },
  });
  const onSubmit: SubmitHandler<OtpFormValues> = (data) => {
    userRequestUpdateEmail({
      newEmail: newEmail,
      otp: data.otp,
    });
  };
  const { t: tCommon } = useTranslation();
  const FORM_ID = 'OtpFormValues';
  return (
    <div>
      <div className='flex w-full flex-col items-center justify-center gap-6 pb-9 max-md:pb-[52px]'>
        <div className='flex flex-col w-full gap-5 items-center justify-center'>
          <div className='flex flex-col w-full gap-3 text-center'>
            <h3 className='text-display-xs font-medium text-slate-800'>
              {tCommon('find_id_password.enter_authentication_number')}
            </h3>

            {!!email && (
              <div>
                <Trans
                  i18nKey='find_id_password.verification_code_has_been_sent'
                  ns='common'
                  values={{ data: email }}
                  components={{ span: <span className='text-md font-bold text-primary-500' /> }}
                />
              </div>
            )}
          </div>

          <Divider />

          <form id={FORM_ID} className='w-full' onSubmit={handleSubmit(onSubmit)}>
            <FormField
              type='otp'
              className='w-full'
              componentProps={{
                control: control,
                name: 'otp',
                required: true,
                numInputs: 6,
                isInputNum: true,
                truncate: true,
              }}
            />
          </form>
        </div>
      </div>

      <div className='flex w-full flex-col items-center justify-center gap-6'>
        <Button isLoading={isPending} form={FORM_ID} className='w-full' variant='primary'>
          {tCommon('find_id_password.verify_authentication_number')}
        </Button>

        <div className='flex flex-col w-full items-center justify-center gap-1 text-center'>
          <span className='text-sm font-normal text-slate-500'>
            {tCommon('find_id_password.didnt_receive_the_verification_number')}
          </span>
          <Button sizing='sm' variant='linkColor'>
            {tCommon('find_id_password.resend')}
          </Button>
        </div>
      </div>
    </div>
  );
};

export default Step3_EnterOtpForm;
