import Stepper from '@/components/core/stepper/Stepper';

type SignUpExpertBusinessModalTitleProps = {
  currentStep: number;
  totalSteps?: number;
  description: string;
  title: string;
};

const SignUpExpertBusinessModalTitle = ({
  totalSteps,
  currentStep,
  title,
  description,
}: SignUpExpertBusinessModalTitleProps): JSX.Element => {
  return (
    <div className='relative flex w-full flex-col gap-1 text-left'>
      <div className='text-lg font-bold max-md:text-md text-comp-text-default'>{title}</div>
      <div className='text-sm font-normal text-[#475467] w-full max-w-[60%]'>{description}</div>
      <Stepper className='absolute top-3 right-5' totalSteps={totalSteps ?? currentStep} currentStep={currentStep} />
    </div>
  );
};

export default SignUpExpertBusinessModalTitle;
