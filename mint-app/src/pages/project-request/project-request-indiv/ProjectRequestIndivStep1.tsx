import { useForm } from 'react-hook-form';
import { useTranslation } from 'react-i18next';
import { ProjectRequestIndivProps } from './ProjectRequestIndivPage';
import BasicInfoSection from './components/BasicInfoSection';

const ProjectRequestIndivStep1 = ({ formID, onNext }: ProjectRequestIndivProps): JSX.Element => {
  const { t: tCommon } = useTranslation('common');
  const { handleSubmit } = useForm();

  const onSubmit = handleSubmit(() => {
    onNext?.({});
  });
  return (
    <div className='flex flex-col md:mt-2'>
      <div className='font-medium text-md'>{tCommon('project_request.step_1.title')}</div>
      <div className='text-slate-500 mt-0.5 mb-8 max-md:mb-6'>{tCommon('project_request.step_1.detailed_description')}</div>
      <div className='text-slate-500 mb-4 text-md md:text-lg'>{tCommon('project_request.basic_information')}</div>
      <form id={formID} className='flex flex-col gap-6 max-md:gap-5' onSubmit={onSubmit}>
        <BasicInfoSection />
      </form>
    </div>
  );
};

export default ProjectRequestIndivStep1;
