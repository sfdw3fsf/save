import { cloneElement, ReactElement } from 'react';

export type SelectBoxKeyProps = {
  icon: ReactElement;
};

const SelectBoxKey = ({ icon }: SelectBoxKeyProps): JSX.Element => {
  return (
    <div className='rounded-full border border-slate-200 bg-white flex items-center justify-center h-8 w-8 shrink-0'>
      <div className='h-4 w-4 flex items-center justify-center'>{cloneElement(icon, { className: 'text-slate-500' })}</div>
    </div>
  );
};

export default SelectBoxKey;
