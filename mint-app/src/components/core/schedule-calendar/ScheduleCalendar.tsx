import { useBreakpoint } from '@/hooks/useBreakpoint';
import i18n, { I18nLanguage } from '@/i18n';
import TabBar, { TabBarItem } from '@/pages/project-management/components/TabBar';
import { getEndDateByView, getNextDateByView, getPrevDateByView, getStartDateByView } from '@/utils/helpers/calendar.helper';
import { Divider } from '@mantine/core';
import dayjs from 'dayjs';
import 'dayjs/locale/en';
import 'dayjs/locale/ko';
import { enqueueSnackbar } from 'notistack';
import { useCallback, useEffect, useMemo, useState } from 'react';
import { Calendar, dayjsLocalizer, Event, EventProps, Views } from 'react-big-calendar';
import withDragAndDrop, { EventInteractionArgs } from 'react-big-calendar/lib/addons/dragAndDrop';
import { useTranslation } from 'react-i18next';
import { useSearchParams } from 'react-router-dom';
import { twMerge } from 'tailwind-merge';
import Button from '../button/Button';
import Spinner from '../spinner/Spinner';
import { SuccessToast } from '../success-notification/SuccessNotification';
import DateButtonControl from './components/DateButtonControl';
import Legend from './components/Legend';
import PlaceHolder from './components/PlaceHolder';
import ScheduleCalendarHeader from './components/ScheduleCalendarHeader';
import ScheduleCard from './components/ScheduleCard';
import './index.css';
import ScheduleCalendarItem, { TCalendarVariant } from './ScheduleCalendarItem';
import ScheduleMeetingList, { ScheduleMeetingItem } from './ScheduleMeetingList';

const STEP = 30;
const TIME_SLOTS = 60 / STEP;

const localizer = dayjsLocalizer(dayjs);

const DnDCalendar = withDragAndDrop(Calendar);

export type TScheduleEvent = Event & {
  parentId?: number;
  id?: number;
};
export type ScheduleCalendarView = typeof Views.MONTH | typeof Views.WEEK | typeof Views.DAY;

type ScheduleCalendarProps = {
  className?: string;
  isShowLegend?: boolean;
  events?: TScheduleEvent[];
  bgEvents?: TScheduleEvent[];
  submitText?: string;
  onSubmit?: (events: TScheduleEvent[]) => void;
  onCancel?: () => void;
  titleSection?: React.ReactNode;
  isSplitTimeSlot?: boolean;
  variant?: TCalendarVariant;
  defaultDate?: Date;
  onReset?: () => void;
  loading?: boolean;
  allowEdit?: boolean;
  showViewTabs?: boolean;
  showToday?: boolean;
  customItemRender?: Partial<Record<ScheduleCalendarView, TCalendarVariant>>;
  meetingList?: ScheduleMeetingItem[];
  enableConfirmBtn?: boolean; // in some case, not allow edit but allow submit
  hideAvailableMeetings?: boolean;
  allowPast?: boolean;
  isLoadingEvents?: boolean;
  preventAutoChangeView?: boolean;
  customClassName?: Partial<{ calendarContainer: string; tabItem: string; calendarInfo: string; calendarHeader: string }>;
  havePlaceholder?: boolean;
  isShowGreyLegend?: boolean;
  isShowGreenLegend?: boolean;
  isDivider?: boolean;
  onCloseViewOnly?: () => void;
};

const ScheduleCalendar = ({
  isShowLegend = true,
  events,
  submitText = 'OK',
  onSubmit,
  onCancel,
  titleSection,
  isSplitTimeSlot = false,
  variant,
  bgEvents,
  defaultDate,
  onReset,
  loading = false,
  className,
  allowEdit = true,
  showViewTabs = false,
  showToday = false,
  customItemRender,
  meetingList = [],
  enableConfirmBtn = true,
  hideAvailableMeetings = false,
  allowPast = true,
  isLoadingEvents = false,
  preventAutoChangeView = false,
  customClassName,
  havePlaceholder = true,
  isShowGreyLegend = true,
  isDivider = false,
  onCloseViewOnly,
  isShowGreenLegend = true,
}: ScheduleCalendarProps): JSX.Element => {
  const { t: tCommon } = useTranslation('common');
  const language = i18n.language as I18nLanguage;

  const { hasMinWidthLg: isWeb } = useBreakpoint('lg');

  const [_, setSearchParams] = useSearchParams();

  const [myEvents, setMyEvents] = useState<TScheduleEvent[]>(events || []);
  const [backgroundEvents, setBackgroundEvents] = useState(bgEvents || []);

  // default WEEK
  const [dateRange, setDateRange] = useState<{ start: Date; end: Date }>(() => {
    const start = dayjs(defaultDate).startOf('week').toDate() || dayjs().startOf('week').toDate();
    return { start: start, end: getEndDateByView(start, Views.WEEK) };
  });
  const [selectedView, setSelectedView] = useState<ScheduleCalendarView>(Views.WEEK);
  const [selectedDate, setSelectedDate] = useState(dayjs(defaultDate).toDate() || dayjs().startOf('week').toDate());

  const viewTabs = useMemo<TabBarItem<ScheduleCalendarView>[]>(
    () => [
      { label: tCommon('calendar.monthly'), value: Views.MONTH },
      { label: tCommon('calendar.weekly'), value: Views.WEEK },
      { label: tCommon('calendar.daily'), value: Views.DAY },
    ],
    [tCommon],
  );

  const enableConfirmButton = useMemo(() => {
    if (!enableConfirmBtn) return false;
    if (myEvents?.length === 0) return false;
    return true;
  }, [enableConfirmBtn, myEvents?.length]);

  const components: any = {
    event: (props: EventProps) => {
      const event = props.event as TScheduleEvent;
      const isBackgroundEvent = backgroundEvents?.some((e) => e.id === event.id);
      const customRender = customItemRender?.[selectedView];
      return (
        <ScheduleCalendarItem
          event={event}
          variant={customRender ?? (variant ? variant : isBackgroundEvent ? 'desired' : 'scheduled')}
        />
      );
    },
  };

  // This function is to split long event to many short events -> ensure time range in [1hr,2hrs]
  // For example when user resize from 1PM to 6PM, it will seperate to 1PM->3PM, 3PM-> 5PM, 5PM -> 6PM
  const splitEvent = useCallback(
    (event?: Event, start?: Date, end?: Date, parentId?: number | null) => {
      if (!event || !start || !end) return [];
      if (end.getMinutes() === 59) {
        end.setMinutes(60);
      }

      // Calculate ID for each time slot
      const idList = myEvents?.map((item: any) => item.id) as number[];
      const maxId = Math.max(...idList);
      const newId = maxId === Number.NEGATIVE_INFINITY ? 1 : maxId + 1;

      // If no need to split time
      if (!isSplitTimeSlot) {
        return [{ ...event, id: newId, start, end }];
      }

      // CLIENT NOT ALLOW TO AUTO SPLIT IF TIME IS OVER 2 HOURS
      const startTime = dayjs(start);
      const endTime = dayjs(end);
      const diffInMinutes = endTime.diff(startTime, 'minute');
      if (diffInMinutes > 120) {
        enqueueSnackbar({ variant: 'error', message: tCommon('calendar.error_over_2_hour') });
        return [];
      }

      // Split Event with min 1 hour and max 2 hours per slot
      const minDuration = 1 * 60 * 60 * 1000; // 1 hour in milliseconds
      const maxDuration = 2 * 60 * 60 * 1000; // 2 hours in milliseconds

      let totalDuration = dayjs(end).diff(dayjs(start)); // in milliseconds
      const slots = [];

      while (totalDuration > 0) {
        let slotDuration;

        if (totalDuration <= maxDuration) {
          slotDuration = totalDuration;
        } else {
          const remainingAfterMax = totalDuration - maxDuration;
          if (remainingAfterMax >= minDuration) {
            slotDuration = maxDuration;
          } else {
            slotDuration = totalDuration - minDuration;
          }
        }

        slots.push(slotDuration);
        totalDuration -= slotDuration;
      }

      // Create Slots
      const newEvents = [];
      let slotStart = dayjs(start);
      for (let i = 0; i < slots.length; i++) {
        const slotDuration = slots[i];
        const slotEnd = slotStart.add(slotDuration, 'millisecond');
        newEvents.push({
          ...event,
          parentId: parentId || null,
          id: newId + i,
          start: slotStart.toDate(),
          end: slotEnd.toDate(),
        });
        slotStart = slotEnd;
      }
      return newEvents;
    },
    [isSplitTimeSlot, myEvents],
  );

  // This function is to allow resize only in background event, not outside background event
  // Call this when backgroundEvents.length !== 0
  const backgroundSlot = useCallback(
    (start?: Date, end?: Date) => {
      if (backgroundEvents?.length === 0) return true;
      if (!start || !end) return false;
      const validSlot = backgroundEvents?.find((item) => start >= item.start! && end <= item.end!);
      if (validSlot) return validSlot?.id;
      return false;
    },
    [backgroundEvents],
  );

  // This function is to check overlappped event
  const overlappedSlot = useCallback(
    (start?: Date, end?: Date, id?: number) => {
      if (myEvents?.length === 0) return false;
      if (!start || !end) return false;
      const filterEvents = id ? myEvents?.filter((item) => item.id !== id) : myEvents;
      const validSlot = filterEvents?.every((item) => start >= item.end! || end <= item.start!);
      return !validSlot;
    },
    [myEvents],
  );

  // This function is to prevent create event in the past
  const isFutureEvent = useCallback((date?: Date) => (allowPast ? true : dayjs(date).isAfter(dayjs())), [allowPast]);

  // This function is to check if this event bigger than 1 hour
  const isEventMoreThanOneHour = (start: Date, end: Date) => {
    const startTime = dayjs(start);
    const endTime = dayjs(end);
    const diffInMinutes = endTime.diff(startTime, 'minute');
    return diffInMinutes >= 60;
  };

  // MOVE INSIDE EVENTS
  const moveEvent = useCallback(
    ({ event, start, end, isAllDay: droppedOnAllDaySlot = false }: EventInteractionArgs<TScheduleEvent>) => {
      if (!allowEdit) {
        return;
      }
      const { allDay } = event;
      if (!allDay && droppedOnAllDaySlot) {
        event.allDay = true;
      }
      if (allDay && !droppedOnAllDaySlot) {
        event.allDay = false;
      }
      if (!backgroundSlot(start as Date, end as Date)) {
        enqueueSnackbar({ variant: 'error', message: tCommon('calendar.error_drag_outside') });
        return;
      }

      if (overlappedSlot(start as Date, end as Date, event?.id)) {
        enqueueSnackbar({ variant: 'error', message: tCommon('calendar.overlap_error') });
        return;
      }

      if (!isFutureEvent(start as Date)) {
        enqueueSnackbar({ variant: 'error', message: tCommon('calendar.past_event_error') });
        return;
      }

      setMyEvents((prev: any) => {
        const existing = prev?.find((ev: any) => ev.id === event.id) ?? {};
        const filtered = prev?.filter((ev: any) => ev.id !== event.id);
        return [...filtered, { ...existing, start, end, allDay: event.allDay }];
      });
    },
    [allowEdit, backgroundSlot, isFutureEvent, overlappedSlot, tCommon],
  );

  // RESIZE EVENTS
  const resizeEvent = useCallback(
    ({ event, start, end }: EventInteractionArgs<TScheduleEvent>) => {
      // Check some constraints

      const parentId = backgroundSlot(start as Date, end as Date);

      if (!parentId) {
        enqueueSnackbar({ variant: 'error', message: tCommon('calendar.error_drag_outside') });
        return;
      }

      if (overlappedSlot(event.start, event.end, event?.id)) {
        enqueueSnackbar({ variant: 'error', message: tCommon('calendar.overlap_error') });
        return;
      }

      if (!isFutureEvent(start as Date)) {
        enqueueSnackbar({ variant: 'error', message: tCommon('calendar.past_event_error') });
        return;
      }

      if (!isEventMoreThanOneHour(start as Date, end as Date)) {
        enqueueSnackbar({ variant: 'error', message: tCommon('calendar.more_than_1_hour_error') });
        return;
      }

      const newEvents = splitEvent(event, start as Date, end as Date, typeof parentId === 'number' ? parentId : null);
      setMyEvents((prev: any) => {
        const filtered = prev.filter((ev: any) => ev.id !== event.id);
        return [...filtered, ...newEvents];
      });
    },
    [backgroundSlot, isFutureEvent, overlappedSlot, splitEvent, tCommon],
  );

  // CREATE NEW EVENT
  const newEvent = useCallback(
    (event: TScheduleEvent & { action: string }) => {
      // PREVENT CLICK, JUST ALLOW DRAG ACTION
      console.log('event', event);
      if (event.action === 'click' || event.action === 'doubleClick') {
        return;
      }
      // Check some constraints
      const parentId = backgroundSlot(event.start, event.end);
      if (!parentId) {
        enqueueSnackbar({ variant: 'error', message: tCommon('calendar.error_drag_outside') });
        return;
      }
      if (overlappedSlot(event.start, event.end)) {
        enqueueSnackbar({ variant: 'error', message: tCommon('calendar.overlap_error') });
        return;
      }
      if (!isFutureEvent(event.start)) {
        enqueueSnackbar({ variant: 'error', message: tCommon('calendar.past_event_error') });
        return;
      }
      if (!isEventMoreThanOneHour(event.start as Date, event.end as Date)) {
        enqueueSnackbar({ variant: 'error', message: tCommon('calendar.more_than_1_hour_error') });
        return;
      }

      const newEvents = splitEvent(event, event.start, event.end, typeof parentId === 'number' ? parentId : null);
      setMyEvents((prev: any) => [...prev, ...newEvents]);
    },
    [backgroundSlot, isFutureEvent, overlappedSlot, splitEvent, tCommon],
  );

  // DRAGGABLE OUTSIDE
  // const handleDragStart = useCallback((event: Event) => setDraggedEvent(event), []);

  // const onDropFromOutside = useCallback(
  //   ({ start, end }: any) => {
  //     const event = {
  //       start,
  //       end,
  //       isAllDay: false,
  //     };
  //     setDraggedEvent(null);
  //     newEvent(event as Event);
  //   },
  //   [setDraggedEvent, newEvent],
  // );

  const onNext = (view: ScheduleCalendarView) => {
    if (view === Views.DAY && isWeb) {
      setSelectedDate((prev) => dayjs(prev).add(1, 'd').toDate());
    }
    const start = getNextDateByView(dateRange.start, view);
    setDateRange({ start: start, end: getEndDateByView(start, view) });
  };

  const onBack = (view: ScheduleCalendarView) => {
    if (view === Views.DAY && isWeb) {
      setSelectedDate((prev) => dayjs(prev).subtract(1, 'd').toDate());
    }
    const start = getPrevDateByView(dateRange.start, view);
    setDateRange({ start: start, end: getEndDateByView(start, view) });
  };

  const onDeleteEvent = (id?: number) => {
    if (!id || !allowEdit) return;
    const newEvents = myEvents?.filter((event) => event.id !== id);
    SuccessToast({
      label: tCommon('calendar.delete_success.title'),
      content: tCommon('calendar.delete_success.sub_title'),
    });
    setMyEvents([...newEvents]);
  };

  const onChangeView = useCallback(
    (view: ScheduleCalendarView, current: Date) => {
      setSelectedView(view);
      onChangeDateRange(current, view);
    },
    [setSelectedView],
  );

  const onChangeDateRange = (current: Date, view: ScheduleCalendarView) => {
    const start = getStartDateByView(current, view);
    setDateRange({ start: start, end: getEndDateByView(start, view) });
  };

  // RENDER
  const renderResetSection = useMemo(() => {
    if (!onReset) return null;
    return (
      <div className='flex flex-col w-full h-fit'>
        <Divider className='my-4' />
        <Button
          onClick={() => {
            setMyEvents([]);
            setBackgroundEvents([]);
            onReset();
          }}>
          {tCommon('calendar.new_time_offer')}
        </Button>
      </div>
    );
  }, [onReset, tCommon]);

  const renderEventCard = useMemo(() => {
    return (
      <div className='flex flex-col'>
        <div className='text-slate-800 lg:text-lg text-md mt-2 mb-2'>
          {onCloseViewOnly ? tCommon('calendar.view_only_confirmed_meeting_time') : tCommon('calendar.meeting_time_available')}
        </div>
        <div className='bg-white overflow-x-auto scrollbar-none lg:overflow-x-visible -mx-6 px-6 lg:mx-0 lg:px-0 flex'>
          <ScheduleCard
            scheduleEvents={myEvents}
            onDelete={allowEdit ? onDeleteEvent : undefined}
            bottomSection={renderResetSection}
            className='w-fit p-0'
            childCont='flex-row w-fit lg:flex-col flex'
            placeHolder={onCloseViewOnly ? tCommon('calendar.view_only_please_select_desire_time') : undefined}
          />
        </div>
      </div>
    );
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [myEvents, renderResetSection, tCommon]);

  const renderBackgroundCard = useMemo(() => {
    if (!backgroundEvents?.length) return null;
    return (
      <div className='bg-white overflow-x-auto scrollbar-none -mx-6 px-6 lg:mx-0 lg:px-0'>
        <ScheduleCard
          scheduleEvents={backgroundEvents}
          className='w-fit p-0'
          childCont='flex-row w-fit'
          isPlaceHolder={false}
          title={tCommon('calendar.desired_consultant_date')}
        />
      </div>
    );
  }, [backgroundEvents, tCommon]);

  // EFFECTS
  useEffect(() => {
    dayjs.locale(language);
  }, [language]);

  useEffect(() => {
    setMyEvents(events ?? []);
  }, [events]);

  useEffect(() => {
    setBackgroundEvents(bgEvents ?? []);
  }, [bgEvents]);

  useEffect(() => {
    if (preventAutoChangeView) return;
    onChangeView(isWeb ? Views.WEEK : Views.DAY, selectedDate);
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [isWeb, preventAutoChangeView]);

  useEffect(() => {
    let pStart: number, pEnd: number;

    if (selectedView === Views.DAY) {
      pStart = dayjs(selectedDate).startOf('day').unix();
      pEnd = dayjs(selectedDate).endOf('day').unix();
    } else {
      pStart = dayjs(dateRange.start).unix();
      pEnd = dayjs(dateRange.end).unix();
    }

    setSearchParams(
      {
        start: (pStart * 1000).toString(),
        end: (pEnd * 1000).toString(),
      },
      { replace: true },
    );
  }, [dateRange, selectedDate, selectedView]);

  return (
    <div className='flex flex-col w-full'>
      {isDivider && <Divider className='-mx-100' />}

      <div
        className={twMerge(
          'w-full flex flex-col bg-white overflow-y-auto p-4 lg:w-[calc(100vw-200px)] inset-0 static-scrollbar',
          className,
        )}>
        {!!titleSection && titleSection}
        <div className={twMerge('w-full grid grid-cols-1 gap-2', hideAvailableMeetings ? 'lg:grid-cols-3' : 'lg:grid-cols-4')}>
          {!hideAvailableMeetings && isWeb && (
            <div className='col-span-1 lg:col-span-1 order-0 lg:order-1 flex px-2 lg:px-0'>{renderEventCard}</div>
          )}
          <div className={twMerge('lg:col-span-3 col-span-1 py-2 relative', customClassName?.calendarContainer)}>
            <div className={twMerge('flex flex-col w-full gap-4 h-fit px-2', customClassName?.calendarInfo)}>
              {renderBackgroundCard}
              {!hideAvailableMeetings && !isWeb && renderEventCard}

              {showViewTabs && (
                <TabBar
                  contClassName='gap-8'
                  itemClassName={twMerge('w-[120px] ml-0', customClassName?.tabItem)}
                  tabs={viewTabs}
                  selectedTab={selectedView}
                  onChangeTab={(view) => onChangeView(view, selectedDate)}
                />
              )}

              <ScheduleMeetingList className='-mb-1' isLoading={isLoadingEvents} meetings={meetingList} />

              <DateButtonControl
                startDate={selectedView === Views.DAY && isWeb ? selectedDate : dateRange.start}
                endDate={selectedView === Views.DAY && isWeb ? selectedDate : dateRange.end}
                onNext={() => onNext(selectedView)}
                onBack={() => onBack(selectedView)}
                view={selectedView}
                showToday={showToday && selectedView === Views.DAY}
                onClickToday={() => {
                  const today = dayjs().toDate();
                  setSelectedDate(today);
                  onChangeDateRange(today, selectedView);
                }}
              />
              {isShowLegend && <Legend isShowGreenLegend={isShowGreenLegend} isShowGreyLegend={isShowGreyLegend} />}
              <ScheduleCalendarHeader
                className={customClassName?.calendarHeader}
                startDate={selectedView === Views.DAY && isWeb ? selectedDate : dateRange.start}
                endDate={selectedView === Views.DAY && isWeb ? selectedDate : dateRange.end}
                selectedDate={selectedDate}
                setSelectedDate={setSelectedDate}
                size={isWeb ? 'lg' : 'sm'}
                variant={selectedView === Views.MONTH ? 'weekday' : 'range'}
              />
              <div className={twMerge(selectedView === Views.MONTH ? 'h-[800px]' : 'h-fit', isLoadingEvents && 'hidden')}>
                <DnDCalendar
                  //defaultDate={defaultDate}
                  // defaultView={Views.WEEK}
                  events={myEvents}
                  localizer={localizer}
                  onEventDrop={moveEvent}
                  onEventResize={resizeEvent}
                  components={components}
                  resizable={allowEdit}
                  toolbar={false}
                  view={selectedView}
                  date={selectedView === Views.DAY ? selectedDate : dateRange.start}
                  views={{ month: true, week: true, day: true }}
                  selectable={allowEdit}
                  // drag out side
                  draggableAccessor={() => allowEdit}
                  onSelectSlot={newEvent}
                  // onDropFromOutside={onDropFromOutside}
                  step={STEP}
                  timeslots={TIME_SLOTS}
                  dayLayoutAlgorithm='no-overlap'
                  showMultiDayTimes
                  backgroundEvents={backgroundEvents}
                  onView={(view) => onChangeView(view as ScheduleCalendarView, selectedDate)}
                  onNavigate={(d: Date) => {
                    onChangeView(Views.DAY, d);
                    setSelectedDate(d);
                  }}
                  messages={{
                    showMore(count) {
                      return tCommon('calendar.view_more', { count });
                    },
                  }}
                  formats={{
                    timeGutterFormat: (date, culture, localizer) => localizer!.format(date, 'h:mm', culture), // Custom format without "AM/PM"
                  }}
                />
              </div>
              {isLoadingEvents && <Spinner raw className='mx-auto block size-10' />}
            </div>
            {havePlaceholder && allowEdit && myEvents?.length === 0 && (
              <div className='bottom-70 right-0 left-0 z-50 flex items-center justify-center self-center sticky bottom-0'>
                <PlaceHolder title={tCommon('calendar.please_mark_your_calendar')} />
              </div>
            )}
          </div>
        </div>
      </div>

      {onCloseViewOnly ? (
        <div className='flex flex-col px-6 py-4'>
          <Divider />
          <div className='w-full items-center flex justify-end my-4 gap-2'>
            <Button onClick={() => onCloseViewOnly?.()} labelContStyle='px-[28px]' variant='primary'>
              {tCommon('calendar.cancel_not_editable')}
            </Button>
          </div>
        </div>
      ) : (
        <>
          {(!!onSubmit || !!onCancel) && (
            <div className='flex flex-col px-6 py-4'>
              <Divider />
              <div className='w-full items-center flex justify-end my-4 gap-2'>
                {!!onCancel && (
                  <Button labelContStyle='px-[70.5px]' onClick={() => onCancel?.()}>
                    {tCommon('button.cancel')}
                  </Button>
                )}
                {!!onSubmit && (
                  <Button
                    onClick={() => {
                      onSubmit?.(myEvents);
                    }}
                    labelContStyle='px-[28px]'
                    variant='primary'
                    isLoading={loading}
                    disabled={!enableConfirmButton}>
                    {submitText}
                  </Button>
                )}
              </div>
            </div>
          )}
        </>
      )}
    </div>
  );
};

export default ScheduleCalendar;
