import Badge from '@/components/core/badge/Badge';
import Button from '@/components/core/button/Button';
import ScheduleCardItem from '@/components/core/schedule-calendar/components/ScheduleCardItem';
import CalendarBlankIcon from '@/components/icons/CalendarBlankIcon';
import EyeIcon from '@/components/icons/EyeIcon';
import VideoCameraIcon from '@/components/icons/VideoCameraIcon';
import { MeetingScheduleDTO } from '@/data/project/dto/project.dto';
import InfoCard from '@/pages/project-request/project-request-indiv/components/InfoCard';
import { USER_ROLE } from '@/utils/enums/user/user-role.enum';
import { Divider } from '@mantine/core';
import { useTranslation } from 'react-i18next';

type ProposalCalendarProps = {
  proposalMeetings: MeetingScheduleDTO[];
  userRole?: USER_ROLE;
  onViewCalendar?: () => void;
  onViewDetail?: (meeting: MeetingScheduleDTO) => void;
  loading?: boolean;
  isBadge?: boolean;
};

const ProposalCalendar = ({
  proposalMeetings,
  userRole,
  loading = false,
  onViewCalendar,
  onViewDetail,
  isBadge = true,
}: ProposalCalendarProps) => {
  const { t: tCommon } = useTranslation('common');

  return (
    <div className='flex flex-col'>
      <div className='text-slate-500 text-md md:text-lg mb-4'>{tCommon('calendar.calendar')}</div>
      <InfoCard title={tCommon('calendar.meeting_calendar')} childrenCont='md:grid-cols-1 grid-cols-1'>
        <div className={'flex flex-col py-2 px-0'}>
          {proposalMeetings?.length !== 0 && (
            <div className='flex flex-col gap-2'>
              {proposalMeetings?.map((meeting, index) => {
                return (
                  <ScheduleCardItem
                    key={index}
                    start={meeting.start}
                    end={meeting.end}
                    rightSection={
                      <div className='flex flex-col items-end w-full gap-4'>
                        <div className='flex gap-1 flex-row justify-end w-full flex-wrap'>
                          {isBadge && meeting?.proposalCreator !== userRole && !meeting.url && (
                            <div>
                              <Badge label={tCommon('calendar.propose_time')} color='orange' />
                            </div>
                          )}
                          <button
                            className='flex gap-1 disabled:cursor-not-allowed'
                            form='_noexist'
                            onClick={() => onViewDetail?.(meeting)}
                            disabled={loading}>
                            <div className='text-slate-500 text-sm text-end font-medium'>{tCommon('calendar.view_schedule')}</div>
                            <EyeIcon className='text-slate-500' />
                          </button>
                        </div>
                        {!meeting?.url ? (
                          <div className='text-slate-500 text-sm text-end'>{tCommon('calendar.link_not_register')}</div>
                        ) : (
                          <div className='w-fit flex'>
                            <Button
                              className='flex rounded-md bg-primary-500 px-4 py-2 items-center gap-2 w-full flex-wrap'
                              labelContStyle='gap-2 w-full'
                              onClick={() => {
                                window.open(meeting?.url);
                              }}>
                              <div className='flex gap-2 w-full'>
                                <div className='text-white text-sm flex-grow whitespace-normal break-words text-left'>
                                  {tCommon('calendar.participate_video_meeting')}
                                </div>
                                <VideoCameraIcon className='text-white flex-shrink-0' />
                              </div>
                            </Button>
                          </div>
                        )}
                      </div>
                    }
                  />
                );
              })}
              <Divider className='mt-4 mb-3' />
            </div>
          )}
          <Button
            leftIcon={<CalendarBlankIcon />}
            labelContStyle='gap-[2px]'
            onClick={() => onViewCalendar?.()}
            isLoading={loading}>
            {tCommon('calendar.view_calendar')}
          </Button>
        </div>
      </InfoCard>
    </div>
  );
};

export default ProposalCalendar;
