import { ReactNode } from 'react';
import { twMerge } from 'tailwind-merge';
type PhoneAssistantProps = {
  icon?: ReactNode;
  title: string;
  email: string;
  timeType: string;
  time?: string;
  className?: string;
};

export const PhoneAssistant = ({ icon, title, email, timeType, time, className }: PhoneAssistantProps): JSX.Element => {
  return (
    <div
      className={twMerge('flex flex-col p-4 bg-white border gap-5 border-slate-200 rounded-lg w-full max-w-[376px] ', className)}>
      <div className='flex flex-col gap-3'>
        <div className='size-12 bg-primary-50 rounded-full flex items-center justify-center '>{icon}</div>
        <div className='flex flex-col gap-2'>
          <p className='text-lg md:text-2xl font-medium text-slate-800'>{title}</p>
          <p className='text-2xl md:text-3xl font-medium text-primary-500'>{email}</p>
        </div>
      </div>
      <div className='flex flex-col gap-3'>
        <p className='text-sm md:text-base font-normal text-slate-500'>{timeType}</p>
        <p className='text-sm md:text-base font-normal text-slate-500'>{time}</p>
      </div>
    </div>
  );
};
