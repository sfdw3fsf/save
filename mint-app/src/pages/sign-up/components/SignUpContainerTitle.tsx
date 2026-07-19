import Stepper from '@/components/core/stepper/Stepper';
import { useBreakpoint } from '@/hooks/useBreakpoint';
import { useMemo } from 'react';
import { twJoin } from 'tailwind-merge';

export type SignUpContainerTitleProps = {
  currentStep: number;
  description: string;
  title: string;
  subtitle: string;
  totalSteps?: number;
};

const theme = {
  cont: { web: 'gap-5', mob: 'gap-3' },
  title: { web: 'text-display-sm', mob: 'text-lg' },
};

const SignUpContainerTitle = ({
  currentStep,
  description,
  title,
  subtitle,
  totalSteps,
}: SignUpContainerTitleProps): JSX.Element => {
  const { hasMinWidthMd } = useBreakpoint('md');
  const deviceType = useMemo(() => (hasMinWidthMd ? 'web' : 'mob'), [hasMinWidthMd]);

  return (
    <div className={twJoin('flex flex-col w-full', theme.cont[deviceType])}>
      <div className='flex w-full items-start justify-start text-left gap-5'>
        <h2 className={twJoin('w-full font-medium text-comp-text-default', theme.title[deviceType])}>{title}</h2>
        <Stepper totalSteps={totalSteps ?? currentStep} currentStep={currentStep} />
      </div>

      <div className='text-sm'>
        <h3 className='text-comp-text-default font-medium'>{subtitle}</h3>
        <p className='text-comp-text-secondary font-normal'>{description}</p>
      </div>
    </div>
  );
};

export default SignUpContainerTitle;
