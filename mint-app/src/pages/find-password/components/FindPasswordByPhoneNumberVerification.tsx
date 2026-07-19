import { findPwdConfirmOtpAPI, findPwdRequestOtpAPI } from '@/data/account-finder/account-finder.api';
import { FindPwdConfirmOtpREQ, FindPwdRequestOtpREQ } from '@/data/account-finder/request/find-password.request';
import useCountdown from '@/hooks/useCountdown';
import FindVerification, { FindVerificationRef } from '@/pages/find/components/FindVerification';
import { BaseResponse } from '@/types/response.type';
import { RESEND_OTP_SECONDS } from '@/utils/constants/data.constant';
import { EXCEPTION_CODE } from '@/utils/enums/data/exception-code.enum';
import { getErrorMessage, getExceptionCode, onError } from '@/utils/helpers/data.helper';
import { FindPasswordByPhoneNumberInput, FindVerificationInput } from '@/utils/schemas/find.schema';
import { useMutation } from '@tanstack/react-query';
import { AxiosError } from 'axios';
import { enqueueSnackbar } from 'notistack';
import { useEffect, useRef } from 'react';

type FindPasswordByPhoneNumberVerificationProps = {
  formData: FindPasswordByPhoneNumberInput;
  onSuccess: (data: string) => void;
};

const FindPasswordByPhoneNumberVerification = ({
  formData: { phone, name, username },
  onSuccess,
}: FindPasswordByPhoneNumberVerificationProps): JSX.Element => {
  const ref = useRef<FindVerificationRef | null>(null);

  const { startCountdown, stopCountdown, totalSeconds, formatTime, isCounting } = useCountdown(RESEND_OTP_SECONDS);

  const { mutate: confirmOtpApi, isPending: isConfirming } = useMutation({
    mutationFn: (request: FindPwdConfirmOtpREQ) => findPwdConfirmOtpAPI(request),
    onSuccess({ data }: BaseResponse<string>) {
      stopCountdown();
      onSuccess(data);
    },
    onError: (error: AxiosError) => {
      const exceptionCode = getExceptionCode(error);

      if (exceptionCode === EXCEPTION_CODE['MINT.INT.EX.006']) {
        ref.current?.setError('otp', { type: 'verify', message: 'verify.otp' });
        return;
      }

      const message = getErrorMessage(error);
      enqueueSnackbar({ variant: 'error', message });
    },
  });

  const handleConfirmOtp = async ({ otp }: FindVerificationInput) => {
    const request: FindPwdConfirmOtpREQ = { otp, phone };
    confirmOtpApi(request);
  };

  const { mutate: requestOtpApi, isPending: isRequesting } = useMutation({
    mutationFn: (request: FindPwdRequestOtpREQ) => findPwdRequestOtpAPI(request),
    onSuccess: () => {
      handleReset();
    },
    onError,
  });

  const handleRequestOtp = async () => {
    const request: FindPwdRequestOtpREQ = { name, phone, username };
    requestOtpApi(request);
  };

  const handleReset = () => {
    startCountdown();
    ref.current?.handleReset();
  };

  useEffect(() => {
    handleReset();
  }, []);

  return (
    <FindVerification
      ref={ref}
      receiver={phone}
      countdown={isCounting ? formatTime(totalSeconds) : undefined}
      isRequesting={isRequesting}
      onRequestOtp={handleRequestOtp}
      isConfirming={isConfirming}
      onConfirmOtp={handleConfirmOtp}
    />
  );
};

export default FindPasswordByPhoneNumberVerification;
