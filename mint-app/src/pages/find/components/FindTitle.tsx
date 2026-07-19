import { useBreakpoint } from '@/hooks/useBreakpoint';
import { twMerge } from 'tailwind-merge';

export type FindTitleProps = {
  title: string;
  subtitle: string;
  description?: string;
  className?: string;
};

const FindTitle = ({ className, description, title, subtitle }: FindTitleProps): JSX.Element => {
  const { hasMinWidthMd: isWeb } = useBreakpoint('md');

  return (
    <div className={twMerge('flex w-full flex-col text-left', isWeb ? 'gap-5' : 'gap-3', className)}>
      <div className='flex w-full flex-col'>
        <h3 className={twMerge('font-medium text-slate-800', isWeb ? 'text-display-sm' : 'text-lg')}>{title}</h3>
      </div>
      <div className='flex w-full flex-col'>
        <div className='text-sm font-medium text-slate-800'>{subtitle}</div>
        <div className='text-sm font-normal text-slate-500'>{description}</div>
      </div>
    </div>
  );
};

export default FindTitle;
