import { Fragment, useMemo } from 'react';
import { twMerge } from 'tailwind-merge';
import StepperDivider from './StepperDivider';
import StepperItem, { StepperItemState } from './StepperItem';

type StepperProps = {
  totalSteps: number;
  currentStep: number;
  className?: HTMLDivElement['className'];
};

const Stepper = ({ totalSteps, currentStep, className }: StepperProps): JSX.Element => {
  const steps = useMemo(
    () =>
      Array.from({ length: totalSteps }, (_, index) => {
        const stepNumber = index + 1;

        let state: StepperItemState;

        if (stepNumber < currentStep) {
          state = 'complete';
        } else if (stepNumber > currentStep) {
          state = 'incomplete';
        } else {
          state = 'current';
        }

        return (
          <Fragment key={index}>
            <StepperItem state={state} stepNumber={stepNumber} />
            {stepNumber !== totalSteps && <StepperDivider color={index + 1 > currentStep ? 'neutral' : 'primary'} />}
          </Fragment>
        );
      }),
    [totalSteps, currentStep],
  );

  return <div className={twMerge('flex w-fit items-center justify-start gap-3', className)}>{steps}</div>;
};

export default Stepper;
