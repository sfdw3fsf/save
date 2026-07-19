import { twMerge } from 'tailwind-merge';

type DividerProps = {
  thickness?: string | number;
  width?: string | number;
  orientation?: 'horizontal' | 'vertical';
  className?: string;
};

const Divider = ({ thickness = '1px', width = '100%', orientation = 'horizontal', className }: DividerProps): JSX.Element => {
  return (
    <div
      className={twMerge('bg-slate-200', orientation === 'horizontal' ? 'w-full h-px' : 'h-full w-px', className)}
      style={{
        height: orientation === 'horizontal' ? (typeof thickness === 'number' ? `${thickness}px` : thickness) : undefined,
        width: orientation === 'horizontal' ? (typeof width === 'number' ? `${width}px` : width) : undefined,
      }}
    />
  );
};

export default Divider;
