import FormField from '@/components/core/form/form-field/FormField';
import { FindPasswordByPhoneNumberInput, findPasswordByPhoneNumberSchema } from '@/utils/schemas/find.schema';
import { yupResolver } from '@hookform/resolvers/yup';
import { useEffect } from 'react';
import { useForm } from 'react-hook-form';
import { useTranslation } from 'react-i18next';
import FindContainer from '../../find/components/FindContainer';

const FORM_ID = 'find-password-by-phone-number-form';

type FindPasswordByPhoneNumberFormProps = {
  onSubmit: (data: FindPasswordByPhoneNumberInput) => void;
};

const FindPasswordByPhoneNumberForm = ({ onSubmit }: FindPasswordByPhoneNumberFormProps): JSX.Element => {
  const { t: tCommon } = useTranslation('common');
  const { t: tForm } = useTranslation('form');

  const { control, handleSubmit, reset } = useForm<FindPasswordByPhoneNumberInput>({
    resolver: yupResolver<FindPasswordByPhoneNumberInput>(findPasswordByPhoneNumberSchema),
    defaultValues: findPasswordByPhoneNumberSchema.getDefault(),
  });

  useEffect(() => {
    reset();
  }, [reset]);

  return (
    <FindContainer
      title={{
        title: tCommon('find_id_password.find_password'),
        subtitle: tCommon('find_id_password.by_phone_number'),
        description: tCommon('find_id_password.you_can_find_your_password_by'),
      }}
      form={{
        id: FORM_ID,
        submitLabel: tCommon('find_id_password.find_password'),
        onSubmit: handleSubmit(onSubmit),
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
        label={tForm('label.phone_number')}
        componentProps={{
          name: 'phone',
          control,
          required: true,
          placeholder: tForm('placeholder.phone_number'),
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

export default FindPasswordByPhoneNumberForm;
