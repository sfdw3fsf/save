import FormField from '@/components/core/form/form-field/FormField';
import { findPwdRequestOtpAPI } from '@/data/account-finder/account-finder.api';
import { FindPwdRequestOtpREQ } from '@/data/account-finder/request/find-password.request';
import { EXCEPTION_CODE } from '@/utils/enums/data/exception-code.enum';
import { getErrorMessage, getExceptionCode } from '@/utils/helpers/data.helper';
import { FindPasswordByEmailInput, findPasswordByEmailSchema } from '@/utils/schemas/find.schema';
import { yupResolver } from '@hookform/resolvers/yup';
import { useMutation } from '@tanstack/react-query';
import { AxiosError } from 'axios';
import { enqueueSnackbar } from 'notistack';
import { useEffect } from 'react';
import { useForm } from 'react-hook-form';
import { useTranslation } from 'react-i18next';
import FindContainer from '../../find/components/FindContainer';

const FORM_ID = 'find-password-by-email-form';

type FindPasswordByEmailFormProps = {
  onSubmit: (data: FindPasswordByEmailInput) => void;
};

const FindPasswordByEmailForm = ({ onSubmit }: FindPasswordByEmailFormProps): JSX.Element => {
  const { t: tCommon } = useTranslation('common');
  const { t: tForm } = useTranslation('form');

  const { control, handleSubmit, reset, watch, setError } = useForm<FindPasswordByEmailInput>({
    resolver: yupResolver<FindPasswordByEmailInput>(findPasswordByEmailSchema),
    defaultValues: findPasswordByEmailSchema.getDefault(),
  });

  const { mutate: requestOtpApi, isPending } = useMutation({
    mutationFn: (request: FindPwdRequestOtpREQ) => findPwdRequestOtpAPI(request),
    onSuccess: () => {
      onSubmit(watch());
    },
    onError: (error: AxiosError) => {
      const exceptionCode = getExceptionCode(error);

      switch (exceptionCode) {
        case EXCEPTION_CODE['MINT.INT.EX.022']: {
          setError('email', { type: 'verify', message: 'verify.find_password_invalid' });
          setError('name', { type: 'verify', message: 'verify.find_password_invalid' });
          setError('username', { type: 'verify', message: 'verify.find_password_invalid' });
          return;
        }
        default: {
          const message = getErrorMessage(error);
          enqueueSnackbar({ variant: 'error', message });
        }
      }
    },
  });

  const handleRequestOtp = async ({ name, email, username }: FindPasswordByEmailInput) => {
    const request: FindPwdRequestOtpREQ = { name, email, username };
    requestOtpApi(request);
  };

  useEffect(() => {
    reset();
  }, [reset]);

  return (
    <FindContainer
      title={{
        title: tCommon('find_id_password.find_password'),
        subtitle: tCommon('find_id_password.by_email'),
        description: tCommon('find_id_password.you_can_find_your_password_by'),
      }}
      form={{
        id: FORM_ID,
        submitLabel: tCommon('find_id_password.find_password'),
        onSubmit: handleSubmit(handleRequestOtp),
        isLoading: isPending,
      }}>
      <FormField
        type='input'
        label={tForm('label.name')}
        componentProps={{
          name: 'name',
          control,
          required: true,
          placeholder: tForm('placeholder.name'),
        }}
      />
      <FormField
        type='input'
        label={tForm('label.email')}
        componentProps={{
          name: 'email',
          control,
          required: true,
          placeholder: tForm('placeholder.email'),
        }}
      />
      <FormField
        type='input'
        label={tForm('label.id')}
        componentProps={{
          name: 'username',
          control,
          required: true,
          placeholder: tForm('placeholder.id'),
        }}
      />
    </FindContainer>
  );
};

export default FindPasswordByEmailForm;
