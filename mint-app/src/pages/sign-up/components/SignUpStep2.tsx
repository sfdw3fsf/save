import Button from '@/components/core/button/Button';
import FormField from '@/components/core/form/form-field/FormField';
import CheckCircleIcon from '@/components/icons/CheckCircleIcon';
import { checkUsernameDuplicateAPI, registerAPI } from '@/data/account/account.api';
import { transformRegisterResponse } from '@/data/account/account.service';
import { CheckUsernameDuplicateREQ } from '@/data/account/request/check-username-duplicate.request';
import { RegisterREQ } from '@/data/account/request/register.request';
import { RegisterRESP } from '@/data/account/response/register.response';
import SignUpContainer, { SignUpContainerProps } from '@/pages/sign-up/components/SignUpContainer';
import { ROUTES } from '@/router/routes.constant';
import { useGlobalStore } from '@/store/global.store';
import { BaseResponse } from '@/types/response.type';
import { PASSWORD_RULES } from '@/utils/constants/form.constant';
import { EXCEPTION_CODE } from '@/utils/enums/data/exception-code.enum';
import { getErrorMessage, getExceptionCode } from '@/utils/helpers/data.helper';
import { SignUpStep2Input, signUpStep2Schema } from '@/utils/schemas/sign-up.schema';
import { yupResolver } from '@hookform/resolvers/yup';
import { useMutation } from '@tanstack/react-query';
import { AxiosError } from 'axios';
import { enqueueSnackbar } from 'notistack';
import { useMemo, useState } from 'react';
import { useForm } from 'react-hook-form';
import { useTranslation } from 'react-i18next';
import { Navigate } from 'react-router-dom';
import { twMerge } from 'tailwind-merge';

const FORM_ID = 'sign-up-auth-form';

type SignUpStep2Props = Pick<SignUpContainerProps, 'titleProps' | 'onBack'> & {
  onNext: (token: string) => void;
};

const SignUpStep2 = ({ onNext, onBack, titleProps }: SignUpStep2Props): JSX.Element => {
  const { t: tCommon } = useTranslation('common');
  const { t: tForm } = useTranslation('form');

  const { signUpData } = useGlobalStore();

  const [isVerifiedId, setIsVerifiedId] = useState<boolean>(false);

  const { control, handleSubmit, watch, setError, trigger } = useForm<SignUpStep2Input>({
    resolver: yupResolver<SignUpStep2Input>(signUpStep2Schema),
    defaultValues: signUpStep2Schema.getDefault(),
  });

  const { mutate: checkUsernameDuplicateApi, isPending: isCheckingUsernameDuplicate } = useMutation({
    mutationFn: (request: CheckUsernameDuplicateREQ) => checkUsernameDuplicateAPI(request),
    onSuccess: () => {
      setIsVerifiedId(true);
    },
    onError: (error: AxiosError) => {
      const exceptionCode = getExceptionCode(error);

      if (exceptionCode === EXCEPTION_CODE['MINT.INT.EX.021']) {
        setError('username', { type: 'verify', message: 'verify.duplicated_username' });
        return;
      }

      const message = getErrorMessage(error);
      enqueueSnackbar({ variant: 'error', message });
    },
  });

  const { mutate: registerApi, isPending: isRegistering } = useMutation({
    mutationFn: (request: RegisterREQ) => registerAPI(request),
    onSuccess: ({ data }: BaseResponse<RegisterRESP>) => {
      const { accessToken } = transformRegisterResponse(data);
      onNext(accessToken);
    },
    onError: (error: AxiosError) => {
      const exceptionCode = getExceptionCode(error);

      if (exceptionCode === EXCEPTION_CODE['MINT.INT.EX.021']) {
        setError('username', { type: 'verify', message: 'verify.duplicated_username' });
        return;
      }

      const message = getErrorMessage(error);
      enqueueSnackbar({ variant: 'error', message });

      if (exceptionCode === EXCEPTION_CODE['MINT.INT.EX.009']) {
        onBack();
        return;
      }
    },
  });

  const checkUsernameDuplicate = async () => {
    const isValid = await trigger('username');
    if (!isValid) return;

    const params: CheckUsernameDuplicateREQ = { username: encodeURIComponent(watch('username')) };
    checkUsernameDuplicateApi(params);
  };

  const handleNext = async ({ username, password }: SignUpStep2Input) => {
    if (!signUpData) return;

    const { email, phoneNumber, userRole } = signUpData;

    const request: RegisterREQ = { email, phoneNumber, username, password, userRole };
    registerApi(request);
  };

  const watchPassword = watch('password');
  const rules = useMemo(() => PASSWORD_RULES(watchPassword, tCommon), [tCommon, watchPassword]);

  if (!signUpData) {
    return <Navigate to={ROUTES.SIGN_UP.self} replace />;
  }

  return (
    <SignUpContainer form={FORM_ID} isLoading={isRegistering} onBack={onBack} titleProps={titleProps}>
      <form
        id={FORM_ID}
        className='flex w-full flex-col gap-6'
        onSubmit={(e) => {
          e.preventDefault();
          handleSubmit(handleNext)();
        }}>
        <FormField
          type='input'
          label={tForm('label.id')}
          successMessage={tForm('success.id')}
          componentProps={{
            name: 'username',
            control,
            required: true,
            placeholder: tForm('placeholder.id'),
            showSuccessMessage: isVerifiedId,
            onChange: () => {
              if (isVerifiedId) setIsVerifiedId(false);
            },
          }}
          rightNode={
            <Button className='py-3 px-2.5' isLoading={isCheckingUsernameDuplicate} onClick={checkUsernameDuplicate} sizing='xl'>
              {tCommon('sign_up.check_for_duplicates')}
            </Button>
          }
        />

        <div className='flex w-full flex-col gap-3 max-md:gap-2'>
          <FormField
            type='input'
            label={tForm('label.password')}
            componentProps={{
              name: 'password',
              control,
              required: true,
              type: 'password',
              placeholder: tForm('placeholder.password'),
            }}
          />

          <div className='flex flex-col gap-1.5 text-xs font-normal text-left'>
            <div className='text-comp-text-secondary'>{tCommon('change_password.must_include')}:</div>
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
    </SignUpContainer>
  );
};

export default SignUpStep2;
