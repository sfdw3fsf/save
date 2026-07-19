import { twJoin } from 'tailwind-merge';

export type DotProps = {
  color?: 'neutral' | 'primary' | 'default';
};

const theme = {
  neutral: 'bg-comp-icon-tertiary',
  primary: 'bg-comp-icon-brand',
  default: 'bg-red-500',
};

const Dot = ({ color = 'default' }: DotProps): JSX.Element => {
  return <span className={twJoin('w-1 h-1 shrink-0 rounded-full overflow-hidden', theme[color])} />;
};

export default Dot;
