import FormField from '@/components/core/form/form-field/FormField';
import FormLabel from '@/components/core/form/form-label/FormLabel';
import { ProjectRequestStep3Input, projectRequestStep3Schema } from '@/utils/schemas/project-request.schema';
import { yupResolver } from '@hookform/resolvers/yup';
import { useEffect } from 'react';
import { useForm } from 'react-hook-form';
import { useTranslation } from 'react-i18next';
import { ProjectRequestIndivProps } from './ProjectRequestIndivPage';

const ProjectRequestIndivStep3 = ({ formID, onNext, initialValues }: ProjectRequestIndivProps): JSX.Element => {
  const { t: tCommon } = useTranslation('common');
  const { t: tForm } = useTranslation('form');

  // FORMS
  const { control, handleSubmit, reset } = useForm<ProjectRequestStep3Input>({
    resolver: yupResolver<ProjectRequestStep3Input>(projectRequestStep3Schema),
    defaultValues: projectRequestStep3Schema.getDefault(),
  });

  // METHODS
  const onSubmit = handleSubmit(async (data: ProjectRequestStep3Input) => {
    onNext?.({
      ...data,
      attachment: data.attachment,
    });
  });

  // EFFECTS
  useEffect(() => {
    if (initialValues) {
      reset(initialValues as ProjectRequestStep3Input);
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [initialValues]);

  return (
    <div className='flex flex-col'>
      <div className='font-medium text-md'>{tCommon('project_request.step_3.title')}</div>
      <div className='text-slate-500 mt-1 mb-5'>{tCommon('project_request.step_3.detailed_description')}</div>
      <form id={formID} className='flex flex-col gap-4 mb-1' onSubmit={onSubmit}>
        <div className='grid grid-cols-2 gap-x-6 gap-y-8 max-md:grid-cols-1 max-md:gap-y-4'>
          <FormLabel label={tForm('label.advisory_content_detail')} required />
          <FormField
            type='text-area'
            componentProps={{
              control: control,
              name: 'contentDetail',
              placeholder: tForm('placeholder.advisory_content_detail'),
              required: true,
              rows: 6,
            }}
            className='max-md:mb-2'
          />
          <FormLabel label={tForm('label.attach_file')} info={tForm('info.attach_file')} />
          <FormField
            type='file'
            componentProps={{
              control,
              name: 'attachment',
              mimeTypes: [
                'application/pdf',
                'application/vnd.openxmlformats-officedocument.wordprocessingml.document',
                'application/msword',
              ],
              maxSizeInMB: 10,
            }}
            className='max-md:mb-2'
          />
        </div>
      </form>
    </div>
  );
};

export default ProjectRequestIndivStep3;
