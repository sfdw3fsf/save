import FormField from '@/components/core/form/form-field/FormField';
import { FindIdByPhoneNumberInput, findIdByPhoneNumberSchema } from '@/utils/schemas/find.schema';
import { yupResolver } from '@hookform/resolvers/yup';
import { useEffect } from 'react';
import { useForm } from 'react-hook-form';
import { useTranslation } from 'react-i18next';
import FindContainer from '../../find/components/FindContainer';

const FORM_ID = 'find-id-by-phone-number-form';

type FindIdByPhoneNumberFormProps = {
  onSubmit: (data: FindIdByPhoneNumberInput) => void;
};

const FindIdByPhoneNumberForm = ({ onSubmit }: FindIdByPhoneNumberFormProps): JSX.Element => {
  const { t: tCommon } = useTranslation('common');
  const { t: tForm } = useTranslation('form');

  const { control, handleSubmit, reset } = useForm<FindIdByPhoneNumberInput>({
    resolver: yupResolver<FindIdByPhoneNumberInput>(findIdByPhoneNumberSchema),
    defaultValues: findIdByPhoneNumberSchema.getDefault(),
  });

  useEffect(() => {
    reset();
  }, [reset]);

  return (
    <FindContainer
      title={{
        title: tCommon('find_id_password.find_id'),
        subtitle: tCommon('find_id_password.by_phone_number'),
        description: tCommon('find_id_password.you_can_find_your_id_by'),
      }}
      form={{
        id: FORM_ID,
        submitLabel: tCommon('find_id_password.find_id'),
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
    </FindContainer>
  );
};

export default FindIdByPhoneNumberForm;
