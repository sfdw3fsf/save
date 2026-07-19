import Alert from '@/components/core/alert/Alert';
import FormField from '@/components/core/form/form-field/FormField';
import FormLabel from '@/components/core/form/form-label/FormLabel';
import { FieldValues, Path, useWatch } from 'react-hook-form';
import { useTranslation } from 'react-i18next';
import { twMerge } from 'tailwind-merge';
import { ProjectRequestBusinessFormProps } from './ProjectRequestBusinessModal';

const ProjectRequestBusinessFieldForm = <T extends FieldValues>({
  id,
  className,
  baseData,
  control,
  onSubmit,
  showErrorMessage,
  onExternalChange,
  language,
}: ProjectRequestBusinessFormProps<T>): JSX.Element => {
  const { t: tCommon } = useTranslation('common');
  const { t: tForm } = useTranslation('form');

  const selectedBusinessField = useWatch({
    control,
    name: baseData?.map((item) => `businessField${item.id}` as Path<T>),
  });

  return (
    <form id={id} className={twMerge('flex w-full flex-col gap-6 justify-start items-start', className)} onSubmit={onSubmit}>
      <div className='flex w-full flex-col gap-6 justify-start items-start'>
        <FormLabel label={tForm('label.business_area_consult_on')} required />
        <div className='grid w-full grid-cols-2 max-md:grid-cols-1 gap-x-6 gap-y-4'>
          {baseData?.map((item, index) => (
            <div key={`required${index}`}>
              <FormField
                type='option-group'
                label={language === 'ko' ? item.name : item.nameInEnglish}
                hideRequiredIndicator
                hideErrorMessage
                componentProps={{
                  control,
                  name: `businessField${item.id}` as Path<T>,
                  required: true,
                  type: 'radio',
                  replace: true,
                  values: item.fields.map((i) => ({
                    label: i.id < 0 ? tCommon('project_request.etc') : language === 'ko' ? i.name : i.nameInEnglish,
                    checkedValue: i.id,
                  })),
                  onCustomChange(value) {
                    onExternalChange?.(`businessField${item.id}`, value);
                  },
                }}
              />
              {/* If value < 0 => etc value => show text input for type etc field */}
              {selectedBusinessField[index] < 0 && (
                <FormField
                  type='input'
                  //hideErrorMessage
                  hideRequiredIndicator
                  componentProps={{
                    control,
                    name: `businessFieldEtc${item.id}` as Path<T>,
                    required: true,
                    placeholder: tForm('placeholder.business_field_etc'),
                  }}
                  className='mt-2'
                />
              )}
            </div>
          ))}
        </div>
      </div>

      {showErrorMessage && (
        <Alert className='w-full' title={tCommon('project_request.select')} open={showErrorMessage}>
          {tForm('required.business_field')}
        </Alert>
      )}
    </form>
  );
};

export default ProjectRequestBusinessFieldForm;
