'use client';
import ModalConfirm from '@/components/common/ConfirmModal';
import CustomButton from '@/components/common/CustomButton';
import CustomDatePickingCalendar from '@/components/common/CustomDatePickingCalendar';
import EditButton from '@/components/common/EditButton';
import CustomModal from '@/components/common/modals/CustomModal';
import PageTitle from '@/components/common/page/PageTitle';
import CustomText from '@/components/common/Text';
import { useMenuPermission } from '@/hook/auth/useMenuPermission';
import {
  getReservationTimeApi,
  reservationTimeKeys,
  reservationTimeSettingKeys,
  updateReservationTimeAllStatusApi,
  updateReservationTimeSettingApi,
  updateReservationTimeStatusApi,
} from '@/store/manager/reservation-time/manager-reservation-time.api';
import {
  getArrayTimeString,
  reservationTimeAllStatusToRequest,
  reservationTimeFilterToRequest,
  reservationTimeParamsToFilter,
  reservationTimeRespToDto,
} from '@/store/manager/reservation-time/manager-reservation-time.service';
import {
  ReservationTimeFilter,
  ReservationTimeUpdateStatusREQ,
} from '@/store/manager/reservation-time/request/manager-reservation-time.request';
import { RESERVATION_TIME_STATUS } from '@/utils/enums/manager/manager.enum';
import { MenuKeys } from '@/utils/enums/side-menu-type';
import { handleError } from '@/utils/helpers/notification-error.helper';
import { handleSuccess } from '@/utils/helpers/notification-success.helper';
import { parseArrayToStringRequest } from '@/utils/parser/array-request.parser';
import { parseDate } from '@/utils/parser/date-time.parser';
import { InfoCircleOutlined } from '@ant-design/icons';
import { keepPreviousData, useMutation, useQuery, useQueryClient } from '@tanstack/react-query';
import { Divider } from 'antd';
import { isArray } from 'lodash';
import { useSearchParams } from 'next/navigation';
import { useMemo, useRef, useState } from 'react';
import { useTranslation } from 'react-i18next';
import ManagerReservationTimeItem from './components/ManagerReservationTimeItem';
import ManagerReservationTimeSettingModal from './components/ManagerReservationTimeSettingModal';

const page = () => {
  const { t: tManager } = useTranslation('manager');
  const { t: tMessage } = useTranslation('message');
  const { canEdit } = useMenuPermission(MenuKeys.MANAGER_RESERVATION_TIME);

  const searchParams = useSearchParams();
  const queryClient = useQueryClient();

  const { initialFilter } = useMemo(() => {
    const initialFilter = reservationTimeParamsToFilter(searchParams);

    return { initialFilter };
  }, []);

  const timeIdRef = useRef<number>();
  const statusRef = useRef<RESERVATION_TIME_STATUS>();

  const [isOpenTimeSetting, setIsOpenTimeSetting] = useState<boolean>(false);
  const [selectedTimeSettingIds, setSelectedTimeSettingIds] = useState<number[]>([]);
  const [filter, setFilter] = useState<ReservationTimeFilter>(initialFilter);
  const [isOpenAll, setIsOpenAll] = useState<boolean>(false);
  const [isCloseAll, setIsCloseAll] = useState<boolean>(false);
  const [isChangeStatus, setIsChangeStatus] = useState<boolean>(false);
  const [message, setMessage] = useState<string>('');

  const changeStatus = {
    OPENING: RESERVATION_TIME_STATUS.CLOSED,
    CLOSED: RESERVATION_TIME_STATUS.COMPLETED,
    COMPLETED: RESERVATION_TIME_STATUS.OPENING,
  };

  const { data: reservationTimeListData } = useQuery({
    queryKey: reservationTimeKeys.list(filter),
    queryFn: () => getReservationTimeApi(reservationTimeFilterToRequest(filter)),
    select: (response) => {
      return { ...response.data, data: reservationTimeRespToDto(response.data.data.reservationTimes) };
    },
    placeholderData: keepPreviousData,
  });

  const { mutate: mutateUpdateReservationTimeSetting } = useMutation({
    mutationFn: (ids: string) => updateReservationTimeSettingApi(ids),
    onSuccess: () => {
      handleSuccess('update');

      queryClient.invalidateQueries({ queryKey: reservationTimeSettingKeys.all });
      queryClient.invalidateQueries({ queryKey: reservationTimeKeys.all });
    },
    onError: handleError,
  });

  const { mutate: mutateUpdateReservationTimeStatus } = useMutation({
    mutationFn: ({ id, body }: { id: number; body: ReservationTimeUpdateStatusREQ }) => updateReservationTimeStatusApi(id, body),
    onSuccess: () => {
      handleSuccess('update');
      queryClient.invalidateQueries({ queryKey: reservationTimeKeys.lists() });
      setIsChangeStatus(false);
    },
    onError: handleError,
  });
  const { mutate: mutateUpdateReservationTimeAllStatus, isPending: isUpdating } = useMutation({
    mutationFn: (body: ReservationTimeUpdateStatusREQ) => updateReservationTimeAllStatusApi(body),
    onSuccess: () => {
      handleSuccess('update');
      queryClient.invalidateQueries({ queryKey: reservationTimeKeys.lists() });
      setIsOpenAll(false);
      setIsCloseAll(false);
    },
    onError: handleError,
  });

  const onSelectTime = (id: number | number[]) => {
    if (isArray(id)) {
      setSelectedTimeSettingIds(id);
      return;
    }
    if (selectedTimeSettingIds.includes(id)) {
      setSelectedTimeSettingIds((pre) => pre.filter((item) => item !== id));
    } else {
      const newSelectedIds = [...selectedTimeSettingIds, id];
      setSelectedTimeSettingIds(newSelectedIds);
    }
  };

  const onUpdateReservationTimeSetting = () => {
    mutateUpdateReservationTimeSetting(parseArrayToStringRequest(selectedTimeSettingIds));
  };

  const onUpdateReservationTimeStatus = () => {
    if (!timeIdRef.current || !statusRef.current) return;

    const body = {
      date: filter.date.valueOf(),
      status: changeStatus[statusRef.current],
    };

    mutateUpdateReservationTimeStatus({ id: timeIdRef.current, body });
  };

  const onUpdateReservationTimeAllStatus = (status: RESERVATION_TIME_STATUS) => {
    const body = reservationTimeAllStatusToRequest(filter.date, status);
    mutateUpdateReservationTimeAllStatus(body);
  };

  const onOpenChangeStatusConfirmModal = (id: number, time: string, status: RESERVATION_TIME_STATUS) => {
    const date = `[${parseDate(filter.date)} ${time}] `;
    setMessage(date + tMessage(`manager.reservation_time.${changeStatus[status]}`));
    timeIdRef.current = id;
    statusRef.current = status;
    setIsChangeStatus(true);
  };

  return (
    <div>
      <PageTitle
        title={tManager('reservation_time')}
        actions={
          <EditButton
            onClick={() => {
              setIsOpenTimeSetting(true);
            }}
          />
        }
      />

      <div className='mt-8 flex gap-[40px] flex-wrap justify-center lg:justify-start'>
        <CustomDatePickingCalendar
          selectedDate={filter.date}
          onSelectDate={(value) => {
            setFilter({ date: value });
          }}
        />
        <div>
          {canEdit && (
            <div className='flex gap-3 justify-between w-[392px]'>
              <CustomButton
                onClick={() => {
                  if (!reservationTimeListData?.data) return;
                  const times = getArrayTimeString(RESERVATION_TIME_STATUS.CLOSED, reservationTimeListData?.data);
                  const date = `[${parseDate(filter.date)}] `;
                  setMessage(`${date}\n${times}\n${tMessage('manager.reservation_time.open_all')}`);
                  setIsOpenAll(true);
                }}
                type='ghost'
                className='!h-[40px] flex-1'>
                {tManager('open_all')}
              </CustomButton>
              <CustomButton
                onClick={() => {
                  if (!reservationTimeListData?.data) return;
                  const times = getArrayTimeString(RESERVATION_TIME_STATUS.OPENING, reservationTimeListData?.data);

                  const date = `[${parseDate(filter.date)}] `;
                  setMessage(`${date}\n${times}\n${tMessage('manager.reservation_time.close_all')}`);
                  setIsCloseAll(true);
                }}
                type='default'
                className='!h-[40px] flex-1'>
                {tManager('close_all')}
              </CustomButton>
            </div>
          )}

          <div className='mt-8'>
            <CustomText variant='caption-1'> {tManager('morning')}</CustomText>
            <div className='flex flex-wrap gap-3 max-w-[392px] mt-2'>
              {reservationTimeListData?.data.AM.map((item) => (
                <ManagerReservationTimeItem
                  key={item.id}
                  title={item.time}
                  status={item.status}
                  onClick={() => {
                    if (!canEdit) return;
                    onOpenChangeStatusConfirmModal(item.id, item.time, item.status);
                  }}
                />
              ))}
            </div>
          </div>
          <div className='mt-6'>
            <CustomText variant='caption-1'> {tManager('afternoon')}</CustomText>
            <div className='flex flex-wrap gap-3  max-w-[392px] mt-2'>
              {reservationTimeListData?.data.PM.map((item) => (
                <ManagerReservationTimeItem
                  key={item.id}
                  title={item.time}
                  status={item.status}
                  onClick={() => {
                    if (!canEdit) return;
                    onOpenChangeStatusConfirmModal(item.id, item.time, item.status);
                  }}
                />
              ))}
            </div>
          </div>

          <Divider className='!mt-8 !mb-6' />

          <div className='flex gap-[5px]'>
            <InfoCircleOutlined className='text-error-semantic-600' />
            <CustomText variant='caption-1' className='!text-neutral-70'>
              {tManager('click')}
            </CustomText>
          </div>

          <div className='mt-2 flex gap-2 '>
            <div className='!cursor-default border-[1px] flex items-center justify-center rounded-lg border-neutral-40 !h-[40px] !w-[86px]'>
              {tManager('button.open')}
            </div>
            <div className='!cursor-default !h-[40px] flex items-center justify-center rounded-lg !w-[86px] !bg-neutral-30 !text-neutral-60 !border-none'>
              {tManager('button.close')}
            </div>
            <div className='!cursor-default !h-[40px] flex items-center justify-center rounded-lg !w-[86px] !bg-brand-forest-1 !text-primary-900 !border-none'>
              {tManager('button.completed')}
            </div>
          </div>
        </div>
      </div>

      <CustomModal
        isOpen={isOpenTimeSetting}
        onClose={() => {
          setIsOpenTimeSetting(false);
        }}
        onCancel={() => {
          setIsOpenTimeSetting(false);
        }}
        onOk={canEdit ? onUpdateReservationTimeSetting : undefined}
        title={tManager('time_setting')}
        className={'ant-reservation-time-modal'}>
        <ManagerReservationTimeSettingModal onSelectTime={onSelectTime} selectedTimes={selectedTimeSettingIds} />
      </CustomModal>

      <ModalConfirm
        message={message}
        isOpen={isOpenAll}
        setIsOpen={setIsOpenAll}
        onConfirm={() => {
          onUpdateReservationTimeAllStatus(RESERVATION_TIME_STATUS.OPENING);
        }}
        isLoading={isUpdating}
      />

      <ModalConfirm
        message={message}
        isOpen={isCloseAll}
        setIsOpen={setIsCloseAll}
        onConfirm={() => {
          onUpdateReservationTimeAllStatus(RESERVATION_TIME_STATUS.CLOSED);
        }}
        isLoading={isUpdating}
      />

      <ModalConfirm
        message={message}
        isOpen={isChangeStatus}
        setIsOpen={setIsChangeStatus}
        onConfirm={() => {
          onUpdateReservationTimeStatus();
        }}
        isLoading={isUpdating}
      />
    </div>
  );
};

export default page;
