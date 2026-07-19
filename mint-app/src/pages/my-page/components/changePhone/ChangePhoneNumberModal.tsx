import FormField from '@/components/core/form/form-field/FormField';
import Modal from '@/components/core/modal/Modal';
import { SuccessToast } from '@/components/core/success-notification/SuccessNotification';
import { userPhoneUpdateAPI } from '@/data/user/user.api';
import { QUERY_KEYS } from '@/utils/constants/data.constant';
import { EXCEPTION_CODE } from '@/utils/enums/data/exception-code.enum';
import { getErrorMessage, getExceptionCode } from '@/utils/helpers/data.helper';
import { yupResolver } from '@hookform/resolvers/yup';
import { useMutation, useQueryClient } from '@tanstack/react-query';
import { AxiosError } from 'axios';
import { enqueueSnackbar } from 'notistack';
import { useForm } from 'react-hook-form';
import { useTranslation } from 'react-i18next';
import * as yup from 'yup';

type ChangePhoneNumberProps = {
  open: boolean;
  onClose: () => void;
};
type ChangePhoneNumberFormValue = {
  phoneNumber: string;
};
const ChangePhoneNumberModal = ({ open, onClose }: ChangePhoneNumberProps): JSX.Element => {
  const { t: tCommon } = useTranslation('common');
  const { t: tForm } = useTranslation('form');
  const FORM_ID = 'change_phone_number';
  const ChangePhoneNumberSchema = yup.object().shape({
    phoneNumber: yup
      .string()
      .required(tForm('required.advisory_type'))
      .matches(/^[0-9.-]*$/, tForm('verify.invalid_phone')),
  });

  const { control, handleSubmit, reset, setError } = useForm<ChangePhoneNumberFormValue>({
    resolver: yupResolver(ChangePhoneNumberSchema),
  });
  const queryClient = useQueryClient();

  const handleChange = ()=>{
    onClose();
    reset({
      phoneNumber:''
    })
  }
  
  const { mutate: updatePhoneNumber, isPending } = useMutation({
    mutationFn: (request: string) => userPhoneUpdateAPI(request),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: [QUERY_KEYS.USER_DETAILS] });
      SuccessToast({
        label: tCommon('my-page.change_phone_number_success_title'),
        content: tCommon('my-page.change_phone_number_success'),
      });
      onClose();
      reset({
        phoneNumber: '',
      });
    },
    onError: (error: AxiosError) => {
      const exceptionCode = getExceptionCode(error);

      switch (exceptionCode) {
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
  const onSubmit = (value: any) => {
    updatePhoneNumber(value);
  };
  return (
    <Modal
      buttons={[
        { children: tCommon('component.cancel'), onClick: handleChange, className: 'w-full mt-1' },
        {
          children: tCommon('component.save'),
          onClick: onClose,
          variant: 'primary',
          form: FORM_ID,
          className: 'w-full mt-1',
          isLoading: isPending,
        },
      ]}
      open={open}
      onClose={onClose}
      body={
        <form id={FORM_ID} onSubmit={handleSubmit(onSubmit)} className=''>
          <h1 className='font-medium text-lg text-center'>{tCommon('my-page.change_phone_number_title')}</h1>
          <FormField
            className=' flex-col mt-5 md:mb-8 mb-3'
            type='input'
            label={tCommon('my-page.enter_change_phone_number')}
            componentProps={{
              name: 'phoneNumber',
              control,
              required: true,
              placeholder: 'e.g., 010-1234-5678',
            }}
          />
        </form>
      }
    />
  );
};
export default ChangePhoneNumberModal;
