import Stepper from '@/components/core/stepper/Stepper';

type ProjectRequestBusinessModalTitleProps = {
  currentStep: number;
  totalSteps?: number;
  description: string;
  title: string;
};
const ProjectRequestBusinessModalTitle = ({
  totalSteps,
  currentStep,
  title,
  description,
}: ProjectRequestBusinessModalTitleProps): JSX.Element => {
  return (
    <div className='flex w-full flex-row gap-1'>
      <div className='flex flex-col w-full'>
        <div className='text-lg font-bold max-md:text-md text-comp-text-default'>{title}</div>
        <div className='text-sm font-normal text-[#475467] w-full max-w-[60%]'>{description}</div>
      </div>
      <Stepper totalSteps={totalSteps ?? currentStep} currentStep={currentStep} />
    </div>
  );
};

export default ProjectRequestBusinessModalTitle;
