import Icon1 from '@/assets/icons/core-values/icon1.svg';
import Icon2 from '@/assets/icons/core-values/icon2.svg';
import Icon3 from '@/assets/icons/core-values/icon3.svg';
import Icon4 from '@/assets/icons/core-values/icon4.svg';
import { useTranslation } from 'react-i18next';

type CoreValueItemProps = {
  icon: string;
  label: string;
};
const CoreValueItem = ({ icon, label }: CoreValueItemProps) => {
  return (
    <div className='bg-white shadow-md p-4 min-w-[265px] max-w-[270px] items-start flex-col flex h-80 rounded-2xl gap-10'>
      <img src={icon} />
      <p className='text-xl text-slate-800 whitespace-pre-line '>{label}</p>
    </div>
  );
};

const CoreValue = () => {
  const { t: tCommon } = useTranslation('common');
  return (
    <div className='lg:px-8 lg:py-24 py-12 px-3 xl:max-w-[1210px] w-full'>
      <h2 className='text-primary-600 text-sm text-center font-medium '>{tCommon('home.core_values')}</h2>
      <p className='text-slate-800 md:text-4xl text-3xl text-center font-bold md:px-0 px-5 mx-auto mb-16 mt-4 whitespace-pre-line'>
        {tCommon('home.core_values_title')}
      </p>
      <div
        className='flex items-center gap-6 py-6 md:px-0 w-full md:flex-wrap flex-nowrap overflow-x-auto px-1
       md:justify-center'>
        <CoreValueItem icon={Icon1} label={tCommon('home.core_values1')} />
        <CoreValueItem icon={Icon2} label={tCommon('home.core_values2')} />
        <CoreValueItem icon={Icon3} label={tCommon('home.core_values3')} />
        <CoreValueItem icon={Icon4} label={tCommon('home.core_values4')} />
      </div>
    </div>
  );
};

export default CoreValue;
