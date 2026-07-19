import ToastUIReactCalendar from '@toast-ui/react-calendar';
import dayjs from 'dayjs';
import { useCallback, useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';

export const useCalendar = (calendarRef: React.RefObject<ToastUIReactCalendar>) => {
  const { t: tCommon } = useTranslation('common');
  const [currentMonth, setCurrentMonth] = useState<number>(new Date().getMonth() + 1);
  const [currentYear, setCurrentYear] = useState<number>(new Date().getFullYear());
  const [selectedDateRangeText, setSelectedDateRangeText] = useState('');
  const [calendarDateRange, setCalendarDateRange] = useState<any[]>();

  const getCalInstance = useCallback(() => calendarRef.current?.getInstance?.(), [calendarRef]);

  const updateRenderRangeText = useCallback(() => {
    const calInstance = getCalInstance();
    if (!calInstance) {
      setSelectedDateRangeText('');
      return;
    }
    const startDateRange = dayjs(calInstance.getDateRangeStart().d.d);
    const endDateRange = dayjs(calInstance.getDateRangeEnd().d.d);

    const calDate = calInstance.getDate();
    const year = calDate.getFullYear();
    const month = calDate.getMonth() + 1;
    setCurrentMonth(month);
    setCurrentYear(year);
    setSelectedDateRangeText(`${year}${tCommon('date.year')} ${month}${tCommon('date.month')}`);
    setCalendarDateRange([startDateRange, endDateRange]);
  }, [getCalInstance]);

  useEffect(() => {
    updateRenderRangeText();
  }, [updateRenderRangeText]);

  const handleClickNextButton = () => {
    const calendarInstance = getCalInstance();
    if (calendarInstance) {
      calendarInstance.next();
      updateRenderRangeText();
    }
  };

  const handleClickPreButton = () => {
    const calendarInstance = getCalInstance();
    if (calendarInstance) {
      calendarInstance.prev();
      updateRenderRangeText();
    }
  };

  const handleClickTodayButton = () => {
    const calendarInstance = getCalInstance();
    if (calendarInstance) {
      calendarInstance.today();
      updateRenderRangeText();
    }
  };

  return {
    currentMonth,
    currentYear,
    selectedDateRangeText,
    handleClickNextButton,
    handleClickPreButton,
    handleClickTodayButton,
    calendarDateRange,
  };
};
