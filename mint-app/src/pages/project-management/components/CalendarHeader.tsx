import InfoFillIcon from '@/components/icons/IconFillIcon';
import { useTranslation } from 'react-i18next';

type CalendarHeaderProps = {
  title?: string;
  subTitle?: string;
  info?: string;
};
const CalendarHeader = ({ title, subTitle, info }: CalendarHeaderProps): JSX.Element => {
  const { t: tCommon } = useTranslation('common');
  return (
    <div className='flex flex-col mx-2 w-fit gap-2'>
      {!!title && <div className='text-xl md:text-display-xs text-slate-800 font-medium'>{title}</div>}
      {!!subTitle && <div className='text-sm text-slate-500'>{subTitle}</div>}
      {!!info && (
        <div className='p-2 border-[1px] rounded-lg border-slate-200 flex gap-2 w-fit'>
          <InfoFillIcon className='text-cerulean-600' />
          <div className='text-sm text-slate-500 whitespace-pre-line'>
            {info ? info : tCommon('calendar.please_select_from_1_to_2')}
          </div>
        </div>
      )}
    </div>
  );
};

export default CalendarHeader;
