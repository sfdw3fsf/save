import CustomCalendar from '@/components/common/calendar/CustomCalendar';
import '@/components/common/calendar/toastui-react-calendar.css';
import CustomButton from '@/components/common/CustomButton';
import CustomIconButton from '@/components/common/CustomIconButton';
import CustomText from '@/components/common/Text';
import { useCalendar } from '@/hook/useCalendar';
import { getLucetteScheduleListApi, lucetteScheduleKeys } from '@/store/funeral/lucette/lucette.api';
import { lucetteScheduleListRespToDto } from '@/store/funeral/lucette/lucette.service';
import { COLORS } from '@/theme';
import { LucetteStatusScheduleOptions } from '@/utils/constants/funeral/lucette/lucctte.constant';
import { ENUM_STATUS } from '@/utils/enums/status.enum';
import { getMonthRange } from '@/utils/helpers/date-time.helper';
import { LeftOutlined, RightOutlined } from '@ant-design/icons';
import { keepPreviousData, useQuery } from '@tanstack/react-query';
import ToastUIReactCalendar from '@toast-ui/react-calendar';
import { Checkbox } from 'antd';
import dayjs from 'dayjs';
import { useMemo, useRef, useState } from 'react';
import { useTranslation } from 'react-i18next';

export const LucetteSchedule = () => {
  const { t: tEnum } = useTranslation('enum');
  const { t: tCommon } = useTranslation('common');
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
  const [statusFilter, setStatusFilter] = useState<ENUM_STATUS[]>(Object.values(ENUM_STATUS));

  const filter = useMemo(() => {
    return {
      startDate: calendarDateRange ? calendarDateRange[0] : dayjs().startOf('month'),
      endDate: calendarDateRange ? calendarDateRange[1] : dayjs().endOf('month'),
      status: statusFilter,
    };
  }, [calendarDateRange, statusFilter]);

  const month = useMemo(() => {
    return getMonthRange(currentMonth);
  }, [currentMonth]);

  const { data: events = [] } = useQuery({
    queryKey: lucetteScheduleKeys.list(filter),
    queryFn: () => getLucetteScheduleListApi(filter),
    select: (response) => {
      if (!response?.data?.data) return;
      return lucetteScheduleListRespToDto(response.data.data, month);
    },
    placeholderData: keepPreviousData,
  });

  const handleCheckboxChange = (status: ENUM_STATUS) => {
    setStatusFilter((prevStatus) => {
      if (prevStatus.includes(status)) {
        return prevStatus.filter((item) => item !== status);
      }
      return [...prevStatus, status];
    });
  };

  return (
    <div>
      <div className='my-6 flex gap-3 justify-between max-md:flex-col'>
        <div className='flex gap-3'>
          <CustomIconButton
            icon={<LeftOutlined style={{ fontSize: '10px', color: COLORS.neutral[70] }} />}
            onClick={handleClickPreButton}
            className='!border !border-neutral-40'
          />
          <CustomText variant='title-3' className='px-2'>
            {selectedDateRangeText}
          </CustomText>
          <CustomIconButton
            icon={<RightOutlined style={{ fontSize: '10px', color: COLORS.neutral[70] }} />}
            onClick={handleClickNextButton}
            className='!border !border-neutral-40'
          />
          <CustomButton type='default' onClick={handleClickTodayButton}>
            {tCommon('date.today')}
          </CustomButton>
        </div>
        <div className='flex items-center justify-end'>
          {LucetteStatusScheduleOptions.map((status) => (
            <Checkbox
              key={status.value}
              checked={statusFilter.includes(status.value)}
              style={
                {
                  '--background-color': status.color,
                  '--border-color': status.color,
                } as React.CSSProperties
              } // Type assertion
              className={`custom-checkbox custom-checkbox-${status.value}`}
              onChange={() => handleCheckboxChange(status.value)}>
              {tEnum(`status.${status.value}`, status.value)}
            </Checkbox>
          ))}
        </div>
      </div>
      <div>
        <CustomCalendar year={currentYear} month={currentMonth} event={events} calendarRef={calendarRef} />
      </div>
    </div>
  );
};
