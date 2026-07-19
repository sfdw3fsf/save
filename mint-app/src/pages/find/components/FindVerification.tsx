import Button from '@/components/core/button/Button';
import Card from '@/components/core/card/Card';
import Divider from '@/components/core/divider/Divider';
import FormField from '@/components/core/form/form-field/FormField';
import { FindVerificationInput, findVerificationSchema } from '@/utils/schemas/find.schema';
import { yupResolver } from '@hookform/resolvers/yup';
import { ForwardedRef, forwardRef, useImperativeHandle } from 'react';
import { useForm, UseFormSetError } from 'react-hook-form';
import { Trans, useTranslation } from 'react-i18next';
import { twMerge } from 'tailwind-merge';

const FORM_ID = 'find-verification-form';

export type FindVerificationRef = {
  setError: UseFormSetError<FindVerificationInput>;
  handleReset: () => void;
};

type FindVerificationProps = {
  receiver?: string;
  isConfirming?: boolean;
  onConfirmOtp: (data: FindVerificationInput) => void;
  isRequesting?: boolean;
  onRequestOtp: () => void;
  countdown?: string;
};

const FindVerification = forwardRef<FindVerificationRef, FindVerificationProps>(
  (
    { receiver, onConfirmOtp, isConfirming, onRequestOtp, isRequesting, countdown }: FindVerificationProps,
    ref: ForwardedRef<FindVerificationRef | null>,
  ): JSX.Element => {
    const { t: tCommon } = useTranslation('common');

    const {
      control,
      handleSubmit,
      reset,
      setError,
      setFocus,
      formState: { errors },
    } = useForm<FindVerificationInput>({
      resolver: yupResolver<FindVerificationInput>(findVerificationSchema),
      defaultValues: findVerificationSchema.getDefault(),
    });

    useImperativeHandle(ref, () => ({
      setError,
      handleReset: () => {
        setFocus('otp.0' as any);
        reset();
      },
    }));

    return (
      <Card>
        <div
          className={twMerge(
            'flex w-full flex-col items-center justify-center gap-6 pb-[88px] max-md:pb-[52px]',
            errors?.otp && 'pb-[58px] max-md:pb-[22px]',
          )}>
          <div className='flex flex-col w-full gap-6 items-center justify-center'>
            <div className='flex flex-col w-full gap-3 text-center'>
              <h3 className='text-display-xs font-medium text-slate-800'>
                {tCommon('find_id_password.enter_authentication_number')}
              </h3>

              {!!receiver && (
                <div>
                  <Trans
                    i18nKey='find_id_password.verification_code_has_been_sent'
                    ns='common'
                    values={{ data: receiver }}
                    components={{ span: <span className='text-md font-bold text-primary-500' /> }}
                  />
                </div>
              )}
            </div>

            <Divider />

            <form id={FORM_ID} className='w-full' onSubmit={handleSubmit(onConfirmOtp)}>
              <FormField
                type='otp'
                className='w-full'
                messageClassName='mt-3'
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
          <Button form={FORM_ID} className='w-full' variant='primary' isLoading={isConfirming}>
            {tCommon('find_id_password.verify_authentication_number')}
          </Button>

          <div className='flex flex-col w-full items-center justify-center gap-1 text-center'>
            <span className='text-sm font-normal text-slate-500'>
              {tCommon('find_id_password.didnt_receive_the_verification_number')}
            </span>
            <Button
              className='min-h-5'
              sizing='sm'
              variant='linkColor'
              disabled={!!countdown}
              onClick={onRequestOtp}
              isLoading={isRequesting}>
              {countdown ?? tCommon('find_id_password.resend')}
            </Button>
          </div>
        </div>
      </Card>
    );
  },
);

FindVerification.displayName = 'FindVerification';

export default FindVerification;
