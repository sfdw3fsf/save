import ScheduleCalendar from '@/components/core/schedule-calendar/ScheduleCalendar';
import { UserCalendarMeetingREQ } from '@/data/user/request/user-calendar-meeting.request';
import { UserCalendarMeetingRESP } from '@/data/user/response/user-calendar-meeting.response';
import { userCalendarMemberAPI } from '@/data/user/user.api';
import { transformUserCalendarMeetingResponse } from '@/data/user/user.service';
import { BaseResponse } from '@/types/response.type';
import { PARAM_CALENDAR } from '@/utils/constants/data.constant';
import { useQuery } from '@tanstack/react-query';
import dayjs from 'dayjs';
import { useMemo } from 'react';
import { useTranslation } from 'react-i18next';
import { useLocation } from 'react-router-dom';
import MyPageContainer from './MyPageContainer';

const ScheduleIndivPage = (): JSX.Element => {
  const { t: tCommon } = useTranslation('common');

  const location = useLocation();

  const request = useMemo<UserCalendarMeetingREQ | null>(() => {
    const queryParams = new URLSearchParams(location.search);

    const pStart = queryParams.get(PARAM_CALENDAR.START),
      pEnd = queryParams.get(PARAM_CALENDAR.END);

    if (pStart === '0' || pEnd === '0') return null;

    return {
      start: Number(pStart),
      end: Number(pEnd),
    };
  }, [location.search]);

  const {
    data: meetings = [],
    isFetching,
    isPending,
    isLoading,
  } = useQuery({
    queryKey: [request],
    enabled: request !== null,
    queryFn: () => userCalendarMemberAPI(request!),
    select: ({ data }: BaseResponse<UserCalendarMeetingRESP>) => transformUserCalendarMeetingResponse(data),
  });

  return (
    <MyPageContainer
      className='gap-0'
      description={tCommon('my-page.check_out_project_schedule')}
      title={tCommon('my-page.calendar')}>
      <ScheduleCalendar
        className='lg:w-full p-0 -py-2'
        isLoadingEvents={isFetching || isPending || isLoading}
        events={meetings}
        isShowLegend={false}
        showViewTabs
        allowEdit={false}
        defaultDate={dayjs().toDate()}
        showToday
        customItemRender={{ month: 'shortcut' }}
        meetingList={meetings}
        hideAvailableMeetings
        preventAutoChangeView
        customClassName={{ calendarContainer: 'pt-0', tabItem: 'pb-3 pt-0', calendarInfo: 'px-0', calendarHeader: '-my-1' }}
      />
    </MyPageContainer>
  );
};

export default ScheduleIndivPage;
