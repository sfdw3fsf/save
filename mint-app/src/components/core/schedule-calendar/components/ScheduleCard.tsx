import TrashIcon from '@/components/icons/TrashIcon';
import { useTranslation } from 'react-i18next';
import { twMerge } from 'tailwind-merge';
import IconButton from '../../icon-button/IconButton';
import { TScheduleEvent } from '../ScheduleCalendar';
import ScheduleCardItem from './ScheduleCardItem';
type ScheduleCardProps = {
  scheduleEvents?: TScheduleEvent[];
  onDelete?: (id?: number) => void;
  rightSection?: React.ReactNode;
  className?: string;
  childCont?: string;
  isPlaceHolder?: boolean;
  bottomSection?: React.ReactNode;
  title?: string | React.ReactNode;
  placeHolder?: string;
};

const ScheduleCard = ({
  scheduleEvents = [],
  onDelete,
  className,
  isPlaceHolder = true,
  childCont,
  bottomSection,
  title,
  placeHolder,
}: ScheduleCardProps): JSX.Element => {
  const { t: tCommon } = useTranslation('common');

  return (
    <div className={twMerge('flex flex-col p-2', className)}>
      {scheduleEvents?.length !== 0 ? (
        <div className={twMerge('flex flex-col gap-2', childCont)}>
          {scheduleEvents?.map((event, index) => {
            return (
              <ScheduleCardItem
                key={index}
                start={event?.start}
                end={event?.end}
                title={title}
                rightSection={
                  !!onDelete && (
                    <IconButton
                      icon={<TrashIcon className='text-slate-800 size-4' />}
                      keepIconStyle={true}
                      sizing='xs'
                      onClick={() => onDelete?.(event.id)}
                      className='-mt-1'
                    />
                  )
                }
                bottomSection={bottomSection}
              />
            );
          })}
        </div>
      ) : (
        <>
          {isPlaceHolder && (
            <div className='flex flex-col'>
              <div className='border-slate-200 border-[1px] rounded-lg w-full flex flex-col py-4 px-2 items-center'>
                <div className='text-slate-800 font-medium text-sm'>
                  {placeHolder ? placeHolder : tCommon('calendar.please_select_desire_time')}
                </div>
              </div>
              {!!bottomSection && bottomSection}
            </div>
          )}
        </>
      )}
    </div>
  );
};

export default ScheduleCard;
