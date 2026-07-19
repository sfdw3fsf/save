import Alert from '@/components/core/alert/Alert';
import FormField from '@/components/core/form/form-field/FormField';
import FormInput from '@/components/core/form/form-input/FormInput';
import FormLabel from '@/components/core/form/form-label/FormLabel';
import i18n from '@/i18n';
import { getNameByLanguage } from '@/utils/helpers/form.helper';
import { FieldValues, Path } from 'react-hook-form';
import { useTranslation } from 'react-i18next';
import { twMerge } from 'tailwind-merge';
import { SignUpExpertBusinessFormProps } from './SignUpExpertBusinessModal';

const SignUpExpertBusinessFieldForm = <T extends FieldValues>({
  id,
  className,
  onSubmit,
  control,
  baseData,
  errorMessage,
}: SignUpExpertBusinessFormProps<T>): JSX.Element => {
  const { t: tForm } = useTranslation('form');

  const lang = i18n.language;

  return (
    <form id={id} className={twMerge('flex w-full flex-col gap-6 justify-start items-start', className)} onSubmit={onSubmit}>
      <div className='flex w-full flex-col gap-6 justify-start items-start'>
        <FormLabel
          label={tForm('label.first_priority_business_area')}
          info={tForm('info.first_priority_business_area')}
          required
        />

        <div className='grid w-full grid-cols-2 max-md:grid-cols-1 gap-x-6 gap-y-4'>
          {baseData.map((item, index) => (
            <div key={`required${index}`}>
              <FormField
                type='option-group'
                label={getNameByLanguage(lang, item)}
                hideRequiredIndicator
                hideErrorMessage
                componentProps={{
                  control,
                  name: `field${item.id}` as Path<T>,
                  required: true,
                  replace: true,
                  type: 'checkbox',
                  values: item.fields.map((i) => ({
                    label: getNameByLanguage(lang, i),
                    checkedValue: i.id,
                    expandComponent: !i.isOtherField ? undefined : (
                      <FormInput
                        placeholder={tForm('placeholder.other_business_field')}
                        name={`field_${item.id}_${i.id}` as Path<T>}
                        control={control}
                      />
                    ),
                  })),
                }}
              />
            </div>
          ))}
        </div>
      </div>

      <div className='flex w-full flex-col gap-6 justify-start items-start'>
        <FormLabel label={tForm('label.other_business_area')} info={tForm('info.other_business_area')} />

        <div className='grid w-full grid-cols-2 max-md:grid-cols-1 gap-x-6 gap-y-4'>
          {baseData.map((item, index) => (
            <div key={`optional${index}`}>
              <FormField
                type='option-group'
                label={getNameByLanguage(lang, item)}
                hideRequiredIndicator
                hideErrorMessage
                componentProps={{
                  control,
                  name: `arrayField${item.id}` as Path<T>,
                  replace: true,
                  type: 'checkbox',
                  values: item.fields.map((i) => ({
                    label: getNameByLanguage(lang, i),
                    checkedValue: i.id,
                    expandComponent: !i.isOtherField ? undefined : (
                      <FormInput
                        placeholder={tForm('placeholder.other_business_field')}
                        name={`arrayField_${item.id}_${i.id}` as Path<T>}
                        control={control}
                      />
                    ),
                  })),
                }}
              />
            </div>
          ))}
        </div>
      </div>

      {!!errorMessage && (
        <Alert className='w-full' title={errorMessage.title} open={!!errorMessage}>
          {errorMessage.detail}
        </Alert>
      )}
    </form>
  );
};

export default SignUpExpertBusinessFieldForm;
