import { twJoin, twMerge } from 'tailwind-merge';
import { ButtonSizing, ButtonVariant } from '../button/Button';
import { theme } from '../button/button.theme';

type SpinnerProps = {
  className?: string;
  sizing?: ButtonSizing;
  variant?: ButtonVariant;
  raw?: boolean;
};

const Spinner = ({ className, sizing = 'md', variant = 'secondaryGray', raw = false }: SpinnerProps): JSX.Element => {
  return (
    <span
      className={twMerge(
        raw ? 'border-comp-border-brand-strong size-20' : twJoin(theme.base.icon[sizing], theme.variant[variant].border),
        'spinner border-2 border-b-transparent',
        className,
      )}></span>
  );
};

export default Spinner;
