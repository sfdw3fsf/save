import { findIdConfirmOtpAPI, findIdRequestOtpAPI } from '@/data/account-finder/account-finder.api';
import { transformFindIdConfirmOtpResponse } from '@/data/account-finder/account-finder.service';
import { FindIdConfirmOtpDTO } from '@/data/account-finder/dto/find-id.dto';
import { FindIdConfirmOtpREQ, FindIdRequestOtpREQ } from '@/data/account-finder/request/find-id.request';
import { FindIdConfirmOtpRESP } from '@/data/account-finder/response/find-id.response';
import useCountdown from '@/hooks/useCountdown';
import FindVerification, { FindVerificationRef } from '@/pages/find/components/FindVerification';
import { BaseResponse } from '@/types/response.type';
import { RESEND_OTP_SECONDS } from '@/utils/constants/data.constant';
import { EXCEPTION_CODE } from '@/utils/enums/data/exception-code.enum';
import { getErrorMessage, getExceptionCode, onError } from '@/utils/helpers/data.helper';
import { FindIdByEmailInput, FindVerificationInput } from '@/utils/schemas/find.schema';
import { useMutation } from '@tanstack/react-query';
import { AxiosError } from 'axios';
import { enqueueSnackbar } from 'notistack';
import { useEffect, useRef } from 'react';

type FindIdByEmailVerificationProps = {
  formData: FindIdByEmailInput;
  onSuccess: (data: FindIdConfirmOtpDTO) => void;
};

const FindIdByEmailVerification = ({ formData: { email, name }, onSuccess }: FindIdByEmailVerificationProps): JSX.Element => {
  const ref = useRef<FindVerificationRef | null>(null);

  const { startCountdown, stopCountdown, totalSeconds, formatTime, isCounting } = useCountdown(RESEND_OTP_SECONDS);

  const { mutate: confirmOtpApi, isPending: isConfirming } = useMutation({
    mutationFn: (request: FindIdConfirmOtpREQ) => findIdConfirmOtpAPI(request),
    onSuccess({ data }: BaseResponse<FindIdConfirmOtpRESP>) {
      stopCountdown();
      const transformedData = transformFindIdConfirmOtpResponse(data);
      onSuccess(transformedData);
    },
    onError: (error: AxiosError) => {
      const exceptionCode = getExceptionCode(error);

      switch (exceptionCode) {
        case EXCEPTION_CODE['MINT.INT.EX.006']: {
          ref.current?.setError('otp', { type: 'verify', message: 'verify.otp' });
          return;
        }
        case EXCEPTION_CODE['MINT.CACHED_NOT_FOUND_EXCEPTION']: {
          ref.current?.setError('otp', { type: 'verify', message: 'verify.otp_expired' });
          return;
        }
        default: {
          const message = getErrorMessage(error);
          enqueueSnackbar({ variant: 'error', message });
        }
      }
    },
  });

  const handleConfirmOtp = async ({ otp }: FindVerificationInput) => {
    const request: FindIdConfirmOtpREQ = { otp, email };
    confirmOtpApi(request);
  };

  const { mutate: requestOtpApi, isPending: isRequesting } = useMutation({
    mutationFn: (request: FindIdRequestOtpREQ) => findIdRequestOtpAPI(request),
    onSuccess: () => {
      handleReset();
    },
    onError,
  });

  const handleRequestOtp = async () => {
    const request: FindIdRequestOtpREQ = { name, email };
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
      receiver={email}
      countdown={isCounting ? formatTime(totalSeconds) : undefined}
      isRequesting={isRequesting}
      onRequestOtp={handleRequestOtp}
      isConfirming={isConfirming}
      onConfirmOtp={handleConfirmOtp}
    />
  );
};

export default FindIdByEmailVerification;
