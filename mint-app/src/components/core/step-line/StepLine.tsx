import { useMemo } from 'react';
import { twMerge } from 'tailwind-merge';
import { StepperItemState } from '../stepper/StepperItem';
import StepLineHorizontal from './StepLineHorizontal';
import StepLineVertical from './StepLineVertical';
import { theme } from './step-line.theme';

export type Step = {
  title?: string;
  description?: string;
};
type StepOrientation = 'horizontal' | 'vertical';
type StepLineProps = {
  steps: Step[];
  currentStep: number;
  className?: string;
  variant?: StepOrientation;
};

const StepLine = ({ steps, currentStep = 0, variant = 'horizontal', className }: StepLineProps): JSX.Element => {
  const stepNumber = useMemo(() => currentStep - 1, [currentStep]);
  return (
    <div className='flex flex-col w-full gap-1 px-3 pt-4 max-md:p-4'>
      {variant === 'horizontal' && (
        <>
          <div className={theme[variant].title}>{steps[stepNumber].title}</div>
          <div className={theme[variant].description}>{steps[stepNumber].description}</div>
        </>
      )}

      <div className={twMerge('flex items-center w-full', theme[variant].cont, className)}>
        {variant === 'horizontal' ? (
          <>
            {steps?.map((_, index) => {
              const state: StepperItemState = index < stepNumber ? 'complete' : index > stepNumber ? 'incomplete' : 'current';
              return (
                <StepLineHorizontal
                  key={index}
                  index={index}
                  state={state}
                  isLastItem={index === steps?.length - 1}
                  variant='horizontal'
                />
              );
            })}
          </>
        ) : (
          <>
            {steps?.map((step, index) => {
              const state: StepperItemState = index < stepNumber ? 'complete' : index > stepNumber ? 'incomplete' : 'current';
              return (
                <StepLineVertical
                  key={index}
                  index={index}
                  state={state}
                  step={step}
                  isLastItem={index === steps?.length - 1}
                  variant='vertical'
                />
              );
            })}
          </>
        )}
      </div>
    </div>
  );
};
export default StepLine;
