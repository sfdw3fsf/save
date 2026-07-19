'use client';
import { ListData } from '@/components/list-data/ListData';
import '@/components/reservation/DatePicker.css';
import { branchKeys, getBranchListApi } from '@/store/branch/branch.api';
import { branchRespToDto } from '@/store/branch/branch.service';
import { BranchDTO } from '@/store/branch/dto/branch.dto';
import { ReservationTime } from '@/store/reservation/dto/reservation.dto';
import { ReservationTimeFilter } from '@/store/reservation/request/reservation.request';
import { getReservationTimeApi, reservationTimeKeys } from '@/store/reservation/reservation.api';
import { reservationTimeFilterToRequest, reservationTimeRespToDto } from '@/store/reservation/reservation.service';
import { useReservationStore } from '@/store/reservation/reservation.store';
import { mockDataReservationTime } from '@/utils/constant/reservation/reservation-info.constant';
import { parseDate } from '@/utils/constant/utils/date.parser';
import { getDaysBeforeToday, getToday } from '@/utils/constant/utils/date.utils';
import { RESERVATION_TIME_STATUS } from '@/utils/enums/reservation.enum';
import { parseDayValueToTimestamp, parseTimestampToDayValue } from '@/utils/parser/date-time.parser';
import { Modal, ModalBody, ModalContent, ModalHeader, ModalOverlay, useDisclosure } from '@chakra-ui/react';
import { Calendar, CalendarDigit, Day, DayValue } from '@hassanmojab/react-modern-calendar-datepicker';
import { keepPreviousData, useQuery } from '@tanstack/react-query';
import { isEmpty } from 'lodash';
import { useSearchParams } from 'next/navigation';

import { useEffect, useMemo, useState } from 'react';

const myCustomLocale = {
  // months list by order
  months: [
    '1월', // January
    '2월', // February
    '3월', // March
    '4월', // April
    '5월', // May
    '6월', // June
    '7월', // July
    '8월', // August
    '9월', // September
    '10월', // October
    '11월', // November
    '12월', // December
  ],

  // week days by order
  weekDays: [
    {
      name: '일요일', // Sunday
      short: '일', // S
      isWeekend: true,
    },
    {
      name: '월요일', // Monday
      short: '월', // M
    },
    {
      name: '화요일', // Tuesday
      short: '화', // T
    },
    {
      name: '수요일', // Wednesday
      short: '수', // W
    },
    {
      name: '목요일', // Thursday
      short: '목', // T
    },
    {
      name: '금요일', // Friday
      short: '금', // F
    },
    {
      name: '토요일', // Saturday
      short: '토', // S
    },
  ],

  // just play around with this number between 0 and 6
  weekStartingIndex: 0,

  // return a { year: number, month: number, day: number } object
  getToday(gregorainTodayObject: Day) {
    return gregorainTodayObject;
  },

  // return a native JavaScript date here
  toNativeDate(date: Day) {
    return new Date(date.year, date.month - 1, date.day);
  },

  // return a number for date's month length
  getMonthLength(date: Day) {
    return new Date(date.year, date.month, 0).getDate();
  },

  // return a transformed digit to your locale
  transformDigit(digit: CalendarDigit) {
    return digit;
  },

  // texts in the date picker
  nextMonth: '다음 달',
  previousMonth: '이전 달',
  openMonthSelector: '월 선택 열기',
  openYearSelector: '연도 선택 열기',
  closeMonthSelector: '월 선택 닫기',
  closeYearSelector: '연도 선택 닫기',
  defaultPlaceholder: '선택하세요',

  // for input range value
  from: '부터',
  to: '까지',

  // used for input value when multi dates are selected
  digitSeparator: ',',

  // if your provide -2 for example, year will be 2 digited
  yearLetterSkip: 0,

  // is your language rtl or ltr?
  isRtl: false,
};

type FirstReservationFormProps = {
  handleNextStep: () => void;
  selectedDay: number | null;
  setSelectedDay: (selectedDay: number) => void;
  time: string | null;
  setTime: (value: string | null) => void;
};
const FirstReservationForm = ({ handleNextStep, selectedDay, setSelectedDay, time, setTime }: FirstReservationFormProps) => {
  const { isOpen, onOpen, onClose } = useDisclosure();
  const searchParams = useSearchParams();
  const { selectedBranch, setSelectedBranch } = useReservationStore((state) => state);
  const allDisabledDays = getDaysBeforeToday();
  const { initialFilter } = useMemo(() => {
    const initialFilter = {
      date: selectedDay ?? undefined,
      branchId: selectedBranch?.id ?? 0,
    };

    return { initialFilter };
  }, [selectedDay, selectedBranch?.id]);

  const [filter, setFilter] = useState<ReservationTimeFilter>(initialFilter);

  const canMoveToNextStep = useMemo(() => {
    if (selectedDay !== null && time !== null) {
      return true;
    }
    return false;
  }, [selectedDay, time]);

  const isNotSelectTimeAndDate = useMemo(() => {
    if (!time && !selectedDay) return true;
    return false;
  }, [selectedDay, time]);

  const { data: reservationTimeListData } = useQuery({
    queryKey: reservationTimeKeys.list(filter),
    queryFn: () => getReservationTimeApi(reservationTimeFilterToRequest(filter)),
    select: (response) => {
      return response?.data && { ...response.data, data: reservationTimeRespToDto(response.data.data.reservationTimes) };
    },
    placeholderData: keepPreviousData,
  });

  const { data: branchListData } = useQuery({
    queryKey: branchKeys.list(filter),
    queryFn: () => getBranchListApi(),
    select: (response) => {
      return response?.data && response?.data.data.map(branchRespToDto);
    },
    placeholderData: keepPreviousData,
  });

  const handleChangeDate = (dateNumber: DayValue) => {
    const valueChange = parseDayValueToTimestamp(dateNumber);
    if (isEmpty(selectedBranch)) {
      onOpen();
    } else if (valueChange) {
      setTime(null);
      setSelectedDay(valueChange);
      setFilter({ ...filter, date: valueChange });
    }
  };

  const handleChangeTime = (timeNumber: string) => {
    if (selectedBranch === null) {
      onOpen();
    } else {
      setTime(timeNumber);
    }
  };

  const handleChangeBranch = (item: BranchDTO) => {
    setSelectedBranch(item);
  };

  useEffect(() => {
    const index = Number(searchParams.get('activeBranch'));
    const activeBranch = index && branchListData ? branchListData[index - 1] : undefined;
    setSelectedBranch(activeBranch);
  }, [searchParams, branchListData]);

  useEffect(() => setFilter(initialFilter), [initialFilter]);

  return (
    <div className='cont-body'>
      <div>
        <div className='text-grey-main text-[14px] font-semibold text-left mb-[13px] md:mb-[20px]'>지점</div>
        <div className='flex flex-row gap-[9px] justify-between mb-[33px] md:mb-[27px]'>
          {branchListData?.map((item) => (
            <button
              key={item.name}
              className={`btn-choose-branch md:border border-grey-neutral ${selectedBranch?.name === item.name ? 'active' : ''}`}
              onClick={() => handleChangeBranch(item)}>
              {item.name}
            </button>
          ))}
        </div>
        <Calendar
          value={parseTimestampToDayValue(selectedDay)}
          onChange={handleChangeDate}
          disabledDays={allDisabledDays}
          locale={myCustomLocale}
          minimumDate={getToday()}
          colorPrimary='#0D4221'
          shouldHighlightWeekends
        />
        <div className='text-grey-main text-[14px] font-semibold text-left mt-[33px] mb-[13px] md:mt-[25px] md:mb-[20px]'>
          오전
        </div>
        <ListData
          data={reservationTimeListData?.data.AM || mockDataReservationTime.AM}
          keyExtractor={(item: any) => item.time}
          renderItem={(item: ReservationTime) => (
            <button
              className={`btn-pick-time ${(item.status === RESERVATION_TIME_STATUS.CLOSED || item.status === RESERVATION_TIME_STATUS.COMPLETED) && 'time-disable'} ${time?.toString() === item.time && 'active'}`}
              onClick={() => handleChangeTime(item.time)}>
              {item.time}
            </button>
          )}
          numberOfColumns={4}
          noWrap={true}
          styleContainer='flex flex-wrap gap-3 w-full'
        />
        <div className='text-grey-main text-[14px] font-semibold text-left mt-[33px] mb-[13px] md:mt-[25px] md:mb-[20px]'>
          오후
        </div>
        <ListData
          data={reservationTimeListData?.data.PM || mockDataReservationTime.PM}
          keyExtractor={(item: any) => item.time}
          renderItem={(item: ReservationTime) => (
            <button
              className={`btn-pick-time  ${(item.status === RESERVATION_TIME_STATUS.CLOSED || item.status === RESERVATION_TIME_STATUS.COMPLETED) && 'time-disable'} ${time?.toString() === item.time && 'active'}`}
              onClick={() => handleChangeTime(item.time)}>
              {item.time}
            </button>
          )}
          numberOfColumns={4}
          noWrap={true}
          styleContainer='flex flex-wrap gap-[12px] w-full'
        />
        <div className={`w-full rounded-[3px] mt-[30px] border border-grey-neutral}`}>
          {isNotSelectTimeAndDate ? (
            <div className='text-grey-main text-center py-[20px] md:py-[25px] text-[16px] font-medium leading-[14px]'>
              날짜와 시간을 선택해주세요
            </div>
          ) : (
            <div className='gap-5 h-full flex justify-center items-center w-full py-[20px] md:py-[25px]'>
              <div className='inline-flex items-center gap-2.5'>
                {selectedDay === null ? (
                  <span className='font-medium text-[16px] leading-[16px]  text-grey-main'>날짜를 선택해주세요</span>
                ) : (
                  <>
                    <span className='font-medium text-[16px] leading-[16px] text-grey-main'>날짜</span>
                    <span className='text-[16px] font-bold leading-[14px] text-black'>{`${parseTimestampToDayValue(selectedDay)?.year}-${parseDate(parseTimestampToDayValue(selectedDay)?.month || 0)}-${parseDate(parseTimestampToDayValue(selectedDay)?.day || 0)} (월)`}</span>
                  </>
                )}
              </div>
              <div className='inline-flex items-center gap-2.5'>
                {time === null ? (
                  <span className='font-medium text-[14px] leading-[16px] text-grey-main'>시간을 선택해주세요</span>
                ) : (
                  <>
                    <span className='font-medium text-[16px] leading-[16px] text-grey-main'>시간</span>
                    <span className='text-[16px] font-bold  leading-[14px]  text-black'>{time}</span>
                  </>
                )}
              </div>
            </div>
          )}
        </div>
        <div className='mt-[31px] md:mt-[28px]'>
          <button
            className={`btn-choose-branch !border-0 !h-[44px] md:!h-[52px] !text-[14px] md:!text-[16px] ${canMoveToNextStep ? 'active' : '!cursor-not-allowed'}`}
            disabled={!canMoveToNextStep}
            onClick={() => handleNextStep()}>
            다음
          </button>
        </div>
      </div>
      <Modal isOpen={isOpen} onClose={onClose} isCentered>
        <ModalOverlay />
        <ModalContent className=' mx-[20px] md:mx-0'>
          <ModalHeader className='text-center !text-[16px] '>
            <div className='h-[28px]'>지점을 선택해주세요</div>
          </ModalHeader>
          <ModalBody className='text-center text-[14px] text-grey-main'>
            <button onClick={onClose} className='h-[30px]'>
              확인
            </button>
          </ModalBody>
        </ModalContent>
      </Modal>
    </div>
  );
};

export default FirstReservationForm;
