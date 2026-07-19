import { ReactNode } from 'react';
import { twMerge } from 'tailwind-merge';
type InquiryAssistantProps = {
  icon?: ReactNode;
  title: string;
  content: string;
  button: ReactNode;
  timeInfor?: string;
  className?: string;
};

export const InquiryAssistant = ({ icon, title, content, button, timeInfor, className }: InquiryAssistantProps): JSX.Element => {
  return (
    <div
      className={twMerge(
        'flex flex-col p-4 bg-white border gap-5 border-slate-200 rounded-lg w-full max-w-[376px] h-max',
        className,
      )}>
      <div className='flex flex-col gap-3'>
        <div className='size-12 bg-primary-50 rounded-full flex items-center justify-center '>{icon}</div>
        <div className='flex flex-col gap-2'>
          <p className='text-lg md:text-2xl font-medium text-slate-800'>{title}</p>
          <p className='text-2xl md:text-3xl font-medium text-slate-800'>{content}</p>
          <p className='text-base font-normal text-slate-500'>{button}</p>
        </div>
      </div>

      <div className='flex flex-col gap-1.5'>
        <p className='text-sm md:text-base font-normal text-slate-500'>{timeInfor}</p>
      </div>
    </div>
  );
};
