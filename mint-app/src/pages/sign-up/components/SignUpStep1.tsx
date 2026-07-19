import Button from '@/components/core/button/Button';
import FormField from '@/components/core/form/form-field/FormField';
import { confirmEmailOtpAPI, verifyEmailAPI } from '@/data/account/account.api';
import { ConfirmEmailOtpREQ } from '@/data/account/request/confirm-email-otp.request';
import { VerifyEmailREQ } from '@/data/account/request/verify-email.request';
import useCountdown from '@/hooks/useCountdown';
import SignUpContainer, { SignUpContainerProps } from '@/pages/sign-up/components/SignUpContainer';
import { useGlobalStore } from '@/store/global.store';
import { RESEND_OTP_SECONDS } from '@/utils/constants/data.constant';
import { EXCEPTION_CODE } from '@/utils/enums/data/exception-code.enum';
import { USER_ROLE } from '@/utils/enums/user/user-role.enum';
import { getErrorMessage, getExceptionCode } from '@/utils/helpers/data.helper';
import { SignUpStep1Input, signUpStep1Schema } from '@/utils/schemas/sign-up.schema';
import { yupResolver } from '@hookform/resolvers/yup';
import { useMutation } from '@tanstack/react-query';
import { AxiosError } from 'axios';
import { enqueueSnackbar } from 'notistack';
import { useEffect, useState } from 'react';
import { useForm } from 'react-hook-form';
import { useTranslation } from 'react-i18next';

const FORM_ID = 'sign-up-info-form';

type SignUpStep1Props = Pick<SignUpContainerProps, 'titleProps' | 'onBack'> & {
  userRole: USER_ROLE;
  onNext: () => void;
};

const SignUpStep1 = ({ onNext, onBack, titleProps, userRole }: SignUpStep1Props): JSX.Element => {
  const { t: tCommon } = useTranslation('common');
  const { t: tForm } = useTranslation('form');

  const { setSignUpData } = useGlobalStore();

  const { startCountdown, stopCountdown, totalSeconds, formatTime, isCounting } = useCountdown(RESEND_OTP_SECONDS);
  const [otpIsVerified, setOtpIsVerified] = useState<boolean>(false);
  const [showOtpInput, setShowOtpInput] = useState(false);

  const { control, handleSubmit, watch, setError } = useForm<SignUpStep1Input>({
    resolver: yupResolver<SignUpStep1Input>(signUpStep1Schema),
    defaultValues: signUpStep1Schema.getDefault(),
    context: { isOptionalOtp: !showOtpInput },
  });

  const { mutate: requestOtpApi, isPending: isVerifyingEmail } = useMutation({
    mutationFn: (request: VerifyEmailREQ) => verifyEmailAPI(request),
    onSuccess: () => {
      stopCountdown();
      setOtpIsVerified(false);
      startCountdown();
    },
    onError: (error: AxiosError) => {
      const exceptionCode = getExceptionCode(error);

      switch (exceptionCode) {
        case EXCEPTION_CODE['MINT.INT.EX.009']: {
          setError('otp', { type: 'verify', message: 'verify.otp_expired' });
          return;
        }
        case EXCEPTION_CODE['MINT.INT.EX.019']: {
          setError('email', { type: 'verify', message: 'verify.duplicated_email' });
          return;
        }
        case EXCEPTION_CODE.VALIDATION_VIOLATION_EXCEPTION: {
          setError('phoneNumber', { type: 'verify', message: 'verify.invalid_phone' });
          return;
        }
        case EXCEPTION_CODE['MINT.INT.EX.020']: {
          setError('phoneNumber', { type: 'verify', message: 'verify.duplicated_phone' });
          return;
        }
        default: {
          const message = getErrorMessage(error);
          enqueueSnackbar({ variant: 'error', message });
        }
      }
    },
  });

  const { mutate: confirmOtpApi, isPending: isConfirmingEmailOtp } = useMutation({
    mutationFn: (request: ConfirmEmailOtpREQ) => confirmEmailOtpAPI(request),
    onSuccess: () => {
      stopCountdown();
      setOtpIsVerified(true);
    },
    onError: (error: AxiosError) => {
      const exceptionCode = getExceptionCode(error);

      if (exceptionCode === EXCEPTION_CODE['MINT.INT.EX.006']) {
        setError('otp', { type: 'verify', message: 'verify.otp' });
        return;
      }

      const message = getErrorMessage(error);
      enqueueSnackbar({ variant: 'error', message });
    },
  });

  const requestOtp = handleSubmit(async ({ email, phoneNumber }: SignUpStep1Input) => {
    const request: VerifyEmailREQ = { email, phoneNumber };
    requestOtpApi(request);
  });

  const confirmOtp = handleSubmit(async ({ email, phoneNumber, otp }) => {
    const request: ConfirmEmailOtpREQ = { email, phoneNumber, otp };
    confirmOtpApi(request);
  });

  useEffect(() => {
    const subscription = watch((_, { name }) => {
      if (otpIsVerified && (name === 'email' || name === 'phoneNumber')) {
        stopCountdown();
        setOtpIsVerified(false);
      }
    });
    return () => subscription.unsubscribe();
  }, [otpIsVerified, watch]);

  useEffect(() => {
    if (isCounting) setShowOtpInput(true);
  }, [isCounting]);

  return (
    <SignUpContainer form={FORM_ID} onBack={onBack} titleProps={titleProps}>
      <form
        id={FORM_ID}
        className='flex w-full flex-col gap-6'
        onSubmit={(e) => {
          e.preventDefault();
          if (otpIsVerified) {
            const { email, phoneNumber } = watch();
            setSignUpData({ email, phoneNumber, userRole });
            onNext();
            return;
          }
          if (!showOtpInput) {
            requestOtp();
            return;
          }
          confirmOtp();
        }}>
        <FormField
          type='input'
          label={tForm('label.email')}
          successMessage={tForm('success.email')}
          componentProps={{
            name: 'email',
            control,
            required: true,
            placeholder: tForm('placeholder.email'),
            showSuccessMessage: otpIsVerified || (showOtpInput && isCounting),
            disabled: isCounting || otpIsVerified,
          }}
          rightNode={
            <Button
              labelStyle='md:text-md'
              form={!showOtpInput ? FORM_ID : undefined}
              onClick={requestOtp}
              isLoading={isVerifyingEmail}
              disabled={isCounting || otpIsVerified}
              className='w-[132px]'
              sizing='xl'>
              {isCounting ? formatTime(totalSeconds) : tCommon('sign_up.request_verification_code')}
            </Button>
          }
        />

        {showOtpInput && (
          <FormField
            className='-mt-3'
            type='input'
            componentProps={{
              name: 'otp',
              control,
              required: true,
              placeholder: tForm('placeholder.otp'),
              disabled: otpIsVerified,
            }}
            rightNode={
              <Button
                form={isCounting ? FORM_ID : undefined}
                onClick={() => confirmOtp()}
                isLoading={isConfirmingEmailOtp}
                disabled={otpIsVerified}
                className='w-[132px]'
                sizing='xl'>
                {tCommon('sign_up.confirm')}
              </Button>
            }
          />
        )}

        <FormField
          type='input'
          label={tForm('label.phone_number')}
          successMessage={tForm('success.phoneNumber')}
          componentProps={{
            name: 'phoneNumber',
            control,
            required: true,
            placeholder: tForm('placeholder.phone_number'),
            showSuccessMessage: otpIsVerified || (showOtpInput && isCounting),
            disabled: isCounting || otpIsVerified,
          }}
        />
      </form>
    </SignUpContainer>
  );
};

export default SignUpStep1;
