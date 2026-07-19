'use client';
import CustomExtendCalendar from '@/components/common/calendar/CustomExtendCalendar';
import CustomButton from '@/components/common/CustomButton';
import CustomIconButton from '@/components/common/CustomIconButton';
import { CustomTabsFilterButton } from '@/components/common/CustomTabsFilterButton';
import PageTitle from '@/components/common/page/PageTitle';
import CustomText from '@/components/common/Text';
import { useBranch } from '@/hook/branch/useBranch';
import { useCalendar } from '@/hook/useCalendar';
import { getHospitalSaleCalendarApi, hospitalSaleCalendarKeys } from '@/store/collaborating-hospital/collaborating-hospital.api';
import { hospitalSaleCalendarRespToDto } from '@/store/collaborating-hospital/collaborating-hospital.service';
import { HospitalCalendarSaleREQ } from '@/store/collaborating-hospital/request/collaborating-hospital.request';
import { COLORS } from '@/theme';
import { SalesStatusScheduleOptions } from '@/utils/constants/collaborating/sales-calendar.constant';
import { SALES_CALENDAR_STATUS } from '@/utils/enums/collaborating/sales-mngt.enums';
import { SALES_CALENDAR_EVENT_ID, SALES_CALENDAR_EVENT_INACTIVE } from '@/utils/enums/home/calendar.enum';
import { updateEventIds } from '@/utils/helpers/calandar.helper';
import { getMonthRange } from '@/utils/helpers/date-time.helper';
import { OptionItems } from '@/utils/types/option.type';
import { LeftOutlined, RightOutlined } from '@ant-design/icons';
import { keepPreviousData, useQuery } from '@tanstack/react-query';
import ToastUIReactCalendar, { ReactCalendarOptions, ReactEventObject } from '@toast-ui/react-calendar';
import { Checkbox } from 'antd';
import dayjs from 'dayjs';
import { useEffect, useMemo, useRef, useState } from 'react';
import { useTranslation } from 'react-i18next';

const HA_Schedule = () => {
  const { t: tCommon } = useTranslation('common');
  const { t: tSales } = useTranslation('collaborating_sales');
  const { t: tEnum } = useTranslation('enum');
  const calendarRef = useRef<ToastUIReactCalendar | null>(null);
  const {
    currentMonth,
    currentYear,
    selectedDateRangeText,
    handleClickNextButton,
    handleClickPreButton,
    handleClickTodayButton,
    calendarDateRange,
  } = useCalendar(calendarRef);
  const { branchOptions } = useBranch();
  const [events, setEvents] = useState<Partial<ReactEventObject>[]>([]);

  const [startDate, endDate] = getMonthRange(currentMonth);
  const [filter, setFilter] = useState<HospitalCalendarSaleREQ>({
    startDate: dayjs().startOf('month').valueOf(),
    endDate: dayjs().endOf('month').valueOf(),
    saleTypes: Object.values(SALES_CALENDAR_STATUS).map((item) => item),
  });

  const initialCalendars: ReactCalendarOptions['calendars'] = [
    {
      id: SALES_CALENDAR_EVENT_ID.NEW_SALES,
      backgroundColor: COLORS.orange.peach,
      borderColor: COLORS.orange.honey,
      color: COLORS.neutral[100],
      dragBackgroundColor: COLORS.orange.peach,
    },
    {
      id: SALES_CALENDAR_EVENT_ID.REQUEST,
      backgroundColor: COLORS.purple.light,
      borderColor: COLORS.purple.lavender,
      color: COLORS.neutral[100],
      dragBackgroundColor: COLORS.purple.light,
    },
    {
      id: SALES_CALENDAR_EVENT_ID.TRANSMISSION,
      backgroundColor: COLORS.green['mint-cream'],
      borderColor: COLORS.green.seafoam,
      color: COLORS.neutral[100],
      dragBackgroundColor: COLORS.green['mint-cream'],
    },
    {
      id: SALES_CALENDAR_EVENT_INACTIVE.newSales,
      backgroundColor: COLORS.orange.apricot,
      borderColor: COLORS.orange.sunset,
      color: COLORS.neutral[60],
      dragBackgroundColor: COLORS.orange.apricot,
    },
    {
      id: SALES_CALENDAR_EVENT_INACTIVE.request,
      backgroundColor: COLORS.purple.violet,
      borderColor: COLORS.purple.soft,
      color: COLORS.neutral[60],
      dragBackgroundColor: COLORS.green.mint,
    },
    {
      id: SALES_CALENDAR_EVENT_INACTIVE.transmission,
      backgroundColor: COLORS.green['mint-frost'],
      borderColor: COLORS.green['seafoam-haze'],
      color: COLORS.neutral[60],
      dragBackgroundColor: COLORS.blue.hidden,
    },
  ];

  const month = useMemo(() => {
    return getMonthRange(currentMonth, currentYear);
  }, [currentMonth, currentYear]);

  const branchTabs: OptionItems<number | string> = useMemo(() => {
    if (!branchOptions) return [];
    return [{ label: tCommon('entire'), value: 'All' }, ...branchOptions];
  }, [branchOptions]);

  const { data: hospitalCalendarData } = useQuery({
    queryKey: hospitalSaleCalendarKeys.list(filter),
    queryFn: () => getHospitalSaleCalendarApi(filter),
    select: (response) => {
      return response.data.data.map((item, index) => hospitalSaleCalendarRespToDto(item, index, tEnum, month));
    },
    placeholderData: keepPreviousData,
  });

  const handleCheckboxChange = (status: SALES_CALENDAR_STATUS) => {
    setFilter((pre) => {
      if (pre.saleTypes?.includes(status)) {
        return { ...pre, saleTypes: pre.saleTypes?.filter((item) => item !== status) };
      }
      return { ...pre, saleTypes: [...(pre.saleTypes || []), status] } as HospitalCalendarSaleREQ;
    });
  };

  const onChangeTab = (id: number | string) => {
    if (id === 'All') {
      setFilter((pre) => ({ ...pre, branchId: undefined }));
    } else {
      setFilter((pre) => ({ ...pre, branchId: id as number }));
    }
  };

  useEffect(() => {
    if (hospitalCalendarData) {
      const updatedEvents = updateEventIds(hospitalCalendarData, currentMonth);
      setEvents(updatedEvents);
    }
  }, [currentMonth, currentYear, hospitalCalendarData]);

  useEffect(() => {
    if (!hospitalCalendarData) return;
    setEvents(hospitalCalendarData);
  }, [hospitalCalendarData]);

  useEffect(() => {
    if (!calendarDateRange) return;
    if (calendarDateRange[0].isSame(filter.startDate, 'day') && calendarDateRange[1].isSame(filter.endDate, 'day')) {
      return;
    }
    setFilter((pre) => ({ ...pre, startDate: calendarDateRange[0].valueOf(), endDate: calendarDateRange[1].valueOf() }));
  }, [calendarDateRange]);

  return (
    <div>
      <PageTitle title={tSales('calendar.title')} isWrap={true} classNameExtra='contents' />
      <div className='flex justify-end'>
        <CustomTabsFilterButton classname='!justify-start my-4 md:flex' optionTabs={branchTabs} onChangeTabs={onChangeTab} />
      </div>

      <div className='my-6 flex flex-col gap-3 justify-between md:flex-row'>
        <div className='flex gap-1 md:gap-3 items-center'>
          <CustomIconButton
            icon={<LeftOutlined style={{ fontSize: '10px', color: COLORS.neutral[70] }} />}
            onClick={handleClickPreButton}
            className='!border !border-neutral-40 !h-[39px] !w-[39px]'
          />
          <CustomText variant='title-3' className='px-2 hidden md:block'>
            {selectedDateRangeText}
          </CustomText>
          <CustomText variant='headline-1' className='px-2 block md:hidden  !text-neutral-80 !font-extrabold'>
            {selectedDateRangeText}
          </CustomText>
          {/* <CustomRangePicker value={[startDate, endDate]} classNameLabel='block md:hidden flex-1' /> */}
          <CustomIconButton
            icon={<RightOutlined style={{ fontSize: '10px', color: COLORS.neutral[70] }} />}
            onClick={handleClickNextButton}
            className='!border !border-neutral-40 !h-[39px] !w-[39px]'
          />
          <CustomButton type='default' onClick={handleClickTodayButton} className=' md:!block'>
            {tCommon('date.today')}
          </CustomButton>
        </div>
        <div>
          {SalesStatusScheduleOptions.map((status) => (
            <Checkbox
              key={status.value}
              checked={filter.saleTypes?.includes(status.value)}
              style={
                {
                  '--background-color': status.color,
                  '--border-color': status.color,
                } as any
              }
              className={`custom-checkbox custom-checkbox-${status.value}`}
              onChange={() => handleCheckboxChange(status.value)}>
              {tSales(`calendar.status.${status.value}` as any)}
            </Checkbox>
          ))}
        </div>
      </div>
      <div className='border-b-[1px] border-neutral-40'>
        <CustomExtendCalendar calendarEventType={initialCalendars} event={events} calendarRef={calendarRef} tSales={tSales} />
      </div>
    </div>
  );
};

export default HA_Schedule;
