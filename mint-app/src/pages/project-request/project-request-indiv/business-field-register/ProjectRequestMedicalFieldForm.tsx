import Alert from '@/components/core/alert/Alert';
import FormField from '@/components/core/form/form-field/FormField';
import FormLabel from '@/components/core/form/form-label/FormLabel';
import { FieldValues, Path, useWatch } from 'react-hook-form';
import { useTranslation } from 'react-i18next';
import { twMerge } from 'tailwind-merge';
import { ProjectRequestBusinessFormProps } from './ProjectRequestBusinessModal';

const ProjectRequestMedicalFieldForm = <T extends FieldValues>({
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

  const selectedMedicalField = useWatch({
    control,
    name: baseData?.map((item) => `medicalField${item.id}` as Path<T>),
  });

  return (
    <form id={id} className={twMerge('flex w-full flex-col gap-6 justify-start items-start', className)} onSubmit={onSubmit}>
      <div className='flex w-full flex-col gap-6 justify-start items-start'>
        <FormLabel label={tForm('label.specialty_area')} required />

        <div className='w-full gap-x-6 gap-y-4'>
          {baseData.map((item, index) => (
            <div key={index}>
              <FormField
                type='option-group'
                // TEMPORARY => NOT REASONABLE
                // TODOS: Fix it
                // label={item.name}
                className='my-2'
                label={''}
                hideRequiredIndicator
                hideErrorMessage
                componentProps={{
                  control,
                  name: `medicalField${item.id}` as Path<T>,
                  required: true,
                  type: 'checkbox',
                  replace: true,
                  className: 'w-full',
                  // TEMPORARY => NOT REASONABLE
                  customOptionClassName: 'w-[45%]',
                  //customGroupClassName: `flex-wrap w-full ${baseData[index].fields.length > 2 ? 'flex-row' : 'flex-col'}`,
                  customGroupClassName: `flex-wrap w-full ${baseData[index].fields.length > 2 ? 'flex-row' : 'flex-col'}`,
                  values: item.fields.map((i) => ({
                    label: i.id < 0 ? tCommon('project_request.etc') : language === 'ko' ? i.name : i.nameInEnglish,
                    checkedValue: i.id,
                  })),
                }}
              />
              {/* If value < 0 => etc value => show text input for type etc field */}
              <div className='w-full flex flex-row flex-nowrap '>
                {baseData[index].fields.length % 2 === 0 && <div className='w-[48%] flex' />}

                <div className='w-[48%] flex'>
                  {selectedMedicalField[index]?.includes(-item.id) && (
                    <FormField
                      type='input'
                      hideRequiredIndicator
                      componentProps={{
                        control,
                        name: `medicalFieldEtc${item.id}` as Path<T>,
                        required: true,
                        placeholder: tForm('placeholder.medical_field_etc'),
                      }}
                      className='mt-2 w-full'
                    />
                  )}
                </div>
              </div>
            </div>
          ))}
        </div>
      </div>

      {showErrorMessage && (
        <Alert className='w-full' title={tCommon('project_request.select')} open={showErrorMessage}>
          {tForm('required.medical_field')}
        </Alert>
      )}
    </form>
  );
};

export default ProjectRequestMedicalFieldForm;
