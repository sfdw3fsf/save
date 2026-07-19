import { ReactNode } from 'react';
import { twMerge } from 'tailwind-merge';

type CardProps = {
  children?: ReactNode;
  className?: string;
};

const Card = ({ children, className }: CardProps): JSX.Element => {
  return (
    <div
      className={twMerge(
        'flex w-full max-w-[440px] flex-col items-center justify-center gap-10 rounded-lg shadow-elevation-lg p-8 max-md:p-4 bg-white',
        className,
      )}>
      {children}
    </div>
  );
};

export default Card;
