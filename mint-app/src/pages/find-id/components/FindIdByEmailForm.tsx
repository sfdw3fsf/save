import FormField from '@/components/core/form/form-field/FormField';
import { findIdRequestOtpAPI } from '@/data/account-finder/account-finder.api';
import { FindIdRequestOtpREQ } from '@/data/account-finder/request/find-id.request';
import { EXCEPTION_CODE } from '@/utils/enums/data/exception-code.enum';
import { getErrorMessage, getExceptionCode } from '@/utils/helpers/data.helper';
import { FindIdByEmailInput, findIdByEmailSchema } from '@/utils/schemas/find.schema';
import { yupResolver } from '@hookform/resolvers/yup';
import { useMutation } from '@tanstack/react-query';
import { AxiosError } from 'axios';
import { enqueueSnackbar } from 'notistack';
import { useEffect } from 'react';
import { useForm } from 'react-hook-form';
import { useTranslation } from 'react-i18next';
import FindContainer from '../../find/components/FindContainer';

const FORM_ID = 'find-id-by-email-form';

type FindIdByEmailFormProps = {
  onSubmit: (data: FindIdByEmailInput) => void;
};

const FindIdByEmailForm = ({ onSubmit }: FindIdByEmailFormProps): JSX.Element => {
  const { t: tCommon } = useTranslation('common');
  const { t: tForm } = useTranslation('form');

  const { control, handleSubmit, reset, watch, setError } = useForm<FindIdByEmailInput>({
    resolver: yupResolver<FindIdByEmailInput>(findIdByEmailSchema),
    defaultValues: findIdByEmailSchema.getDefault(),
  });

  const { mutate: requestOtpApi, isPending } = useMutation({
    mutationFn: (request: FindIdRequestOtpREQ) => findIdRequestOtpAPI(request),
    onSuccess: () => {
      onSubmit(watch());
    },
    onError: (error: AxiosError) => {
      const exceptionCode = getExceptionCode(error);

      switch (exceptionCode) {
        case EXCEPTION_CODE['MINT.INT.EX.022']: {
          setError('email', { type: 'verify', message: 'verify.find_id_invalid' });
          setError('name', { type: 'verify', message: 'verify.find_id_invalid' });
          return;
        }
        default: {
          const message = getErrorMessage(error);
          enqueueSnackbar({ variant: 'error', message });
        }
      }
    },
  });

  const handleRequestOtp = async ({ name, email }: FindIdByEmailInput) => {
    const request: FindIdRequestOtpREQ = { name, email };
    requestOtpApi(request);
  };

  useEffect(() => {
    reset();
  }, [reset]);

  return (
    <FindContainer
      title={{
        title: tCommon('find_id_password.find_id'),
        subtitle: tCommon('find_id_password.by_email'),
        description: tCommon('find_id_password.you_can_find_your_id_by'),
      }}
      form={{
        id: FORM_ID,
        submitLabel: tCommon('find_id_password.find_id'),
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
    </FindContainer>
  );
};

export default FindIdByEmailForm;
