import Alert from '@/components/core/alert/Alert';
import FormField from '@/components/core/form/form-field/FormField';
import FormLabel from '@/components/core/form/form-label/FormLabel';
import { FieldValues, Path, useWatch } from 'react-hook-form';
import { useTranslation } from 'react-i18next';
import { twMerge } from 'tailwind-merge';
import { ProjectRequestBusinessFormProps } from './ProjectRequestBusinessModal';

const ProjectRequestAdvisoryFieldForm = <T extends FieldValues>({
  id,
  className,
  control,
  baseData,
  showErrorMessage = false,
  onSubmit,
  language,
}: ProjectRequestBusinessFormProps<T>): JSX.Element => {
  const { t: tCommon } = useTranslation('common');
  const { t: tForm } = useTranslation('form');

  const selectedAdvisoryField = useWatch({
    control,
    name: baseData?.map((item) => `advisoryField${item.id}` as Path<T>),
  });

  return (
    <form id={id} className={twMerge('flex w-full flex-col gap-6 justify-start items-start', className)} onSubmit={onSubmit}>
      <div className='flex w-full flex-col gap-6 justify-start items-start'>
        <FormLabel label={tForm('label.main_advisory')} required />

        <div className='grid w-full grid-cols-2 max-md:grid-cols-1 gap-x-6 gap-y-4'>
          {baseData.map((item, index) => (
            <div key={index}>
              <FormField
                type='option-group'
                label={language === 'ko' ? item.name : item.nameInEnglish}
                hideRequiredIndicator
                hideErrorMessage
                componentProps={{
                  control,
                  name: `advisoryField${item.id}` as Path<T>,
                  required: true,
                  type: 'checkbox',
                  replace: true,
                  values: item.fields.map((i) => ({
                    label: i.id < 0 ? tCommon('project_request.etc') : language === 'ko' ? i.name : i.nameInEnglish,
                    checkedValue: i.id,
                  })),
                }}
              />
              {/* If value < 0 => etc value => show text input for type etc field */}
              {selectedAdvisoryField[index]?.includes(-item.id) && (
                <FormField
                  type='input'
                  //hideErrorMessage
                  hideRequiredIndicator
                  componentProps={{
                    control,
                    name: `advisoryFieldEtc${item.id}` as Path<T>,
                    required: true,
                    placeholder: tForm('placeholder.advisory_field_etc'),
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
          {tForm('required.main_advisory')}
        </Alert>
      )}
    </form>
  );
};

export default ProjectRequestAdvisoryFieldForm;
