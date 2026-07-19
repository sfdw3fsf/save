import { ReactNode } from 'react';

type ValueInfoProps = {
  english: string;
  korean: string;
  context: ReactNode;
};
const ValueInfomationHover = ({ english = '', korean = '', context = '' }: ValueInfoProps) => {
  return (
    <li className='li4'>
      <div className='text-center'>
        <h3 className='md:text-[26px] text-[16.8px] font-bold'>
          <span className='md:text-[18px] text-[15px] text-base'>{english}</span>
          {korean}
        </h3>
      </div>
      <p className='md:text-[18px] text-[15px] break-keep text-center mt-5'>{context}</p>
    </li>
  );
};

export default ValueInfomationHover;
