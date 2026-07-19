import { twMerge } from 'tailwind-merge';
import Divider from '../divider/Divider';
import StepperItem, { StepperItemState } from '../stepper/StepperItem';
import { theme } from './step-line.theme';

type StepLineHorizontalProps = {
  index: number;
  variant: 'horizontal' | 'vertical';
  isLastItem?: boolean;
  state: StepperItemState;
};
const StepLineHorizontal = ({ index, variant, isLastItem = false, state }: StepLineHorizontalProps) => {
  return (
    <div key={index} className={twMerge(theme[variant].contentCont, !isLastItem ? 'w-full gap-1' : 'w-auto')}>
      <div className='flex items-center'>
        <StepperItem state={state} stepNumber={index + 1} size='sm' />
      </div>
      {!isLastItem && (
        <div className='flex-grow'>
          <Divider
            thickness={2}
            orientation={variant}
            className={twMerge(theme[variant].divider, state === 'complete' && 'bg-comp-icon-brand')}
          />
        </div>
      )}
    </div>
  );
};

export default StepLineHorizontal;
