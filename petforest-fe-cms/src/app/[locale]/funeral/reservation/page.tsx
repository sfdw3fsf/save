'use client';
import AddButton from '@/components/common/AddButton';
import CustomButton from '@/components/common/CustomButton';
import PageTitle from '@/components/common/page/PageTitle';
import { recipientRequirePopup } from '@/components/popup/RecipientRequirePopup';
import { useReservation } from '@/hook/funeral/useReservation';
import useResize from '@/hook/useResize';
import useRouterHandler from '@/hook/useRouterHandler';
import { sendReservationSurveyApi } from '@/store/funeral/reservation/reservation.api';
import { useReservationStore } from '@/store/funeral/reservation/reservation.store';
import { COLORS, ICONS } from '@/theme';
import { FUNERAL_RESERVATION_CONTENT, FUNERAL_RESERVATION_TABS } from '@/utils/enums/funeral/funeral-mngt.enums';
import { getTodayRange } from '@/utils/helpers/date-time.helper';
import { handleError } from '@/utils/helpers/notification-error.helper';
import { handleSuccess } from '@/utils/helpers/notification-success.helper';
import { ArrowLeftOutlined, CarOutlined } from '@ant-design/icons';
import { useMutation } from '@tanstack/react-query';
import { Tabs, TabsProps } from 'antd';
import Image from 'next/image';
import { useSearchParams } from 'next/navigation';
import { useMemo, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { DeleteList } from './Tabs/DeleteList';
import PhotoList from './Tabs/PhotoList';
import ReservationList from './Tabs/ReservationList';

export default function Home() {
  const { t: tReservation } = useTranslation('funeral_reservation');

  const { isResponsive } = useResize();
  const { onCreateReservation } = useReservation();
  const { handleURLPush } = useRouterHandler();
  const searchParams = useSearchParams();
  const { selectedReservationKeys } = useReservationStore();

  const [startOfDay, endOfDay] = getTodayRange();
  const [createReservationDate, setCreateReservationDate] = useState<number>(endOfDay);
  const [tab, setTab] = useState<FUNERAL_RESERVATION_TABS>(
    (searchParams.get('tab') as FUNERAL_RESERVATION_TABS) || FUNERAL_RESERVATION_TABS.LIST,
  );

  const { mutate: mutateSendReservationSurvey, isPending: isSending } = useMutation({
    mutationFn: (reservationIds: number[]) => sendReservationSurveyApi({ reservationIds }),
    onSuccess: () => {
      handleSuccess('email');
    },
    onError: handleError,
  });

  const isDeletedList = useMemo(
    () => (searchParams.get('content') as FUNERAL_RESERVATION_CONTENT) === FUNERAL_RESERVATION_CONTENT.DELETE_LIST,
    [searchParams],
  );

  const handleChangeTab = (value: FUNERAL_RESERVATION_TABS) => {
    setTab(value);
    handleURLPush('tab', value);
  };

  const handleOpenSummaryTable = () => {
    window.open('/funeral/reservation/non-accompany', '_blank', 'noopener,noreferrer');
  };

  const onSendMessage = () => {
    if (selectedReservationKeys.length === 0) {
      recipientRequirePopup();
      return;
    }
    mutateSendReservationSurvey(selectedReservationKeys);
  };

  const funeraReservationTabs: TabsProps['items'] = [
    {
      key: FUNERAL_RESERVATION_TABS.LIST,
      label: tReservation('reservation'),
      children: (
        <>
          {isResponsive && (
            <div className='mt-6 flex gap-3 flex-wrap'>
              <AddButton onClick={() => onCreateReservation({ date: createReservationDate })} />
              <CustomButton onClick={handleOpenSummaryTable} type='ghost' icon={<CarOutlined />} className='flex-1'>
                {tReservation('button.car')}
              </CustomButton>
              <CustomButton
                onClick={onSendMessage}
                type='ghost'
                icon={<Image src={ICONS.NOTI} width={24} height={24} alt='noti' />}>
                {tReservation('button.survey')}
              </CustomButton>
            </div>
          )}

          <ReservationList setCreateReservationDate={setCreateReservationDate} />
        </>
      ),
    },
    {
      key: FUNERAL_RESERVATION_TABS.PHOTOS,
      label: tReservation('funeral_photos'),
      children: <PhotoList />,
    },
  ];

  return isDeletedList ? (
    <div>
      <PageTitle
        title={tReservation('delete_list')}
        prefix={
          <div
            className='p-[9px] cursor-pointer'
            onClick={() => handleURLPush('content', FUNERAL_RESERVATION_CONTENT.RESERVATION, false)}>
            <ArrowLeftOutlined style={{ fontSize: '16px', color: COLORS.neutral[100] }} />
          </div>
        }
      />
      <DeleteList />
    </div>
  ) : (
    <div>
      <PageTitle
        title={tReservation('funeral_reservation')}
        actions={
          tab === FUNERAL_RESERVATION_TABS.LIST &&
          !isResponsive && (
            <>
              <AddButton onClick={() => onCreateReservation({ date: createReservationDate })} />
              <CustomButton onClick={handleOpenSummaryTable} type='ghost' icon={<CarOutlined />}>
                {tReservation('button.car')}
              </CustomButton>
              <CustomButton
                onClick={onSendMessage}
                type='ghost'
                icon={<Image src={ICONS.NOTI} width={24} height={24} alt='noti' />}>
                {tReservation('button.survey')}
              </CustomButton>
            </>
          )
        }
      />

      <div className='mt-4'>
        <Tabs
          items={funeraReservationTabs}
          defaultActiveKey={FUNERAL_RESERVATION_TABS.LIST}
          activeKey={tab}
          onChange={(value) => handleChangeTab(value as FUNERAL_RESERVATION_TABS)}
        />
      </div>
    </div>
  );
}
