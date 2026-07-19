import { twMerge } from 'tailwind-merge';
import Divider from '../divider/Divider';
import StepperItem, { StepperItemState } from '../stepper/StepperItem';
import { Step } from './StepLine';
import { theme } from './step-line.theme';

type StepLineVerticalProps = {
  index: number;
  variant: 'horizontal' | 'vertical';
  isLastItem?: boolean;
  state: StepperItemState;
  step: Step;
};
const StepLineVertical = ({ index, variant, isLastItem = false, state, step }: StepLineVerticalProps) => {
  return (
    <div key={index} className={twMerge(theme[variant].contentCont)}>
      {/* Stepper item and Divider container */}
      <div className='flex flex-col items-center h-full gap-1 flex-grow pb-1'>
        <StepperItem state={state} stepNumber={index + 1} size='lg' className='text-2xl' />

        {/* Divider between steps */}
        {!isLastItem && (
          <div className='flex-grow flex'>
            <Divider
              orientation={variant}
              className={twMerge(theme[variant].divider, state === 'complete' && 'bg-comp-icon-brand')}
            />
          </div>
        )}
      </div>

      {/* Step Title and Description */}
      <div className='flex flex-col px-0 pt-1.5 pb-8 gap-0.5'>
        <div className={theme[variant].title}>{step.title}</div>
        <div className={theme[variant].description}>{step.description}</div>
      </div>
    </div>
  );
};

export default StepLineVertical;
