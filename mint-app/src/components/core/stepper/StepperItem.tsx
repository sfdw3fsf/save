import CheckIcon from '@/components/icons/CheckIcon';
import { twMerge } from 'tailwind-merge';

export type StepperItemState = 'incomplete' | 'complete' | 'current';

type StepperItemProps = {
  state?: StepperItemState;
  size?: 'sm' | 'lg';
  stepNumber: number;
  clickable?: boolean;
  className?: string;
};
const theme = {
  base: 'shrink-0 font-bold flex items-center justify-center rounded-full overflow-hidden',
  size: {
    sm: 'w-6 h-6 text-xs',
    lg: 'w-10 h-10 text-display-xs',
  },
  cont: {
    incomplete: 'bg-white border-[1.5px] border-slate-200 text-comp-icon-tertiary',
    complete: 'bg-primary-500 text-white',
    current: 'bg-primary-500 text-white',
  },
  iconCont: 'shrink-0 flex items-center justify-center',
  iconBase: 'w-full h-full text-inherit',
  icon: {
    sm: 'w-4 h-4',
    lg: 'w-6 h-6',
  },
};

const StepperItem = ({
  state = 'incomplete',
  size = 'sm',
  stepNumber,
  clickable = false,
  className,
}: StepperItemProps): JSX.Element => {
  return (
    <button disabled={!clickable} className={twMerge(theme.base, theme.size[size], theme.cont[state], className)}>
      {state === 'complete' ? (
        <div className={twMerge(theme.iconCont, theme.icon[size])}>
          <CheckIcon className={theme.iconBase} />
        </div>
      ) : (
        stepNumber
      )}
    </button>
  );
};

export default StepperItem;
