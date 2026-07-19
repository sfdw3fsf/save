import { twMerge } from 'tailwind-merge';

type TooltipProps = {
  label?: string | React.ReactNode;
  children?: React.ReactNode;
  className?: string;
};
const Tooltip = ({ label, children, className }: TooltipProps): JSX.Element => {
  if (label) {
    return (
      <div className={twMerge('relative inline-block group w-full', className)}>
        {children}
        <div className='z-50 absolute mt-1 left-1/2 transform -translate-x-1/2 flex items-center justify-center'>
          <div className='hidden group-hover:block px-3 py-2 bg-white rounded-lg shadow-lg border border-slate-200 whitespace-nowrap'>
            {label}
          </div>
        </div>
      </div>
    );
  }
  return <>{children}</>;
};

export default Tooltip;
