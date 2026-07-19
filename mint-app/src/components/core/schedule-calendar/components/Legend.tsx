import { useTranslation } from 'react-i18next';

type LegendProps = {
  isShowGreyLegend?: boolean;
  isShowGreenLegend?: boolean;
};

const Legend = ({ isShowGreenLegend = true, isShowGreyLegend = true }: LegendProps): JSX.Element => {
  const { t: tCommon } = useTranslation('common');
  if (isShowGreenLegend || isShowGreyLegend) {
    return (
      <div className='flex items-center justify-center gap-4 md:w-fit w-full'>
        {isShowGreyLegend && (
          <div className='flex gap-2 items-center'>
            <div className='w-[8px] h-[8px] rounded-full bg-comp-icon-tertiary' />
            <div className='text-comp-text-secondary text-sm'>{tCommon('calendar.desired_time')}</div>
          </div>
        )}

        {isShowGreenLegend && (
          <div className='flex gap-2 items-center'>
            <div className='w-[8px] h-[8px] rounded-full bg-comp-icon-brand' />
            <div className='text-comp-text-secondary text-sm'>{tCommon('calendar.schedule_legend')}</div>
          </div>
        )}
      </div>
    );
  }
  return <></>;
};

export default Legend;
