import CustomText from '@/components/common/Text';
import { COLORS } from '@/theme';
import { WEEK_DAYS } from '@/utils/constants/manager/reservation-time.constant';
import { LeftOutlined, RightOutlined } from '@ant-design/icons';
import { Divider } from 'antd';
import clsx from 'clsx';
import dayjs, { Dayjs } from 'dayjs';
import { useState } from 'react';

type CustomDatePickingCalendarProps = {
  selectedDate?: Dayjs | null;
  onSelectDate: (value: Dayjs) => void;
};

const CustomDatePickingCalendar = ({ selectedDate, onSelectDate }: CustomDatePickingCalendarProps) => {
  const [currentDate, setCurrentDate] = useState(dayjs());

  const handlePrevMonth = () => {
    setCurrentDate(currentDate.subtract(1, 'month'));
  };

  const handleNextMonth = () => {
    setCurrentDate(currentDate.add(1, 'month'));
  };

  const getDaysInMonth = (date: dayjs.Dayjs) => {
    return date.daysInMonth();
  };

  const getFirstDayOfMonth = (date: dayjs.Dayjs) => {
    return date.startOf('month').day();
  };

  const renderCalendar = () => {
    const daysInMonth = getDaysInMonth(currentDate);
    const firstDay = getFirstDayOfMonth(currentDate);
    const weeks = [];
    const today = dayjs();

    const days = [];

    for (let i = 0; i < firstDay; i++) {
      const lastMonthDays = getDaysInMonth(currentDate.subtract(1, 'month'));
      days.push(
        <div
          className='w-[40px] h-[40px] flex items-center justify-center text-grey-grey-day'
          key={`prev-${lastMonthDays - firstDay + 1 + i}`}>
          {lastMonthDays - firstDay + 1 + i}
        </div>,
      );
    }

    for (let i = 1; i <= daysInMonth; i++) {
      const currentDay = currentDate.date(i);
      days.push(
        <div
          onClick={() => onSelectDate(currentDay)}
          className={clsx(
            `w-[40px] h-[40px] flex items-center justify-center cursor-pointer rounded-lg ${
              selectedDate && !selectedDate.isSame(currentDay, 'day') && 'hover:bg-neutral-40'
            }`,
            {
              'bg-primary-1100 text-white': selectedDate && selectedDate.isSame(currentDay, 'day'),
              'border-success-900 border-[1px]': today.isSame(currentDay, 'day'),
            },
          )}
          key={i}>
          {i}
        </div>,
      );
    }

    const totalDays = firstDay + daysInMonth;
    for (let i = 0; i < 42 - totalDays; i++) {
      days.push(
        <div className='w-[40px] h-[40px] flex items-center justify-center text-grey-grey-day' key={`next-${i}`}>
          {i + 1}
        </div>,
      );
    }

    for (let i = 0; i < days.length; i += 7) {
      weeks.push(
        <div className='flex gap-3' key={i}>
          {days.slice(i, i + 7)}
        </div>,
      );
    }

    return weeks;
  };

  const renderWeekdays = () => {
    return (
      <div className='flex gap-3'>
        {WEEK_DAYS.map((day, index) => (
          <div key={index} className='w-[40px] h-[40px] flex items-center justify-center '>
            <CustomText variant='label-1-normal'>{day}</CustomText>
          </div>
        ))}
      </div>
    );
  };

  return (
    <div className='max-w-fit max-h-fit border border-neutral-40 rounded-lg '>
      <header className='flex justify-between items-center border-neutral-40 py-3 px-2 rounded-t-lg'>
        <button onClick={handlePrevMonth}>
          <LeftOutlined />
        </button>
        <CustomText variant='body-1-normal' className='!font-medium'>
          {currentDate.format('YYYY년 M월')}
        </CustomText>
        <button onClick={handleNextMonth}>
          <RightOutlined />
        </button>
      </header>
      <Divider className='!m-0' style={{ borderColor: COLORS.neutral[40] }} />
      <div className='flex flex-col px-3 py-2'>
        <div>{renderWeekdays()}</div>
        <div className='gap-3 flex flex-col'>{renderCalendar()}</div>
      </div>
    </div>
  );
};

export default CustomDatePickingCalendar;
