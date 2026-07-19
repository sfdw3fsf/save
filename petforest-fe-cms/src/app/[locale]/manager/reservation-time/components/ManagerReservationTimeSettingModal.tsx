import { ListData } from '@/components/common/ListData';
import CustomText from '@/components/common/Text';
import { useMenuPermission } from '@/hook/auth/useMenuPermission';
import {
  getReservationTimeSettingApi,
  reservationTimeSettingKeys,
} from '@/store/manager/reservation-time/manager-reservation-time.api';
import { reservationTimeRespToDto } from '@/store/manager/reservation-time/manager-reservation-time.service';
import { MenuKeys } from '@/utils/enums/side-menu-type';
import { keepPreviousData, useQuery } from '@tanstack/react-query';
import { useEffect } from 'react';
import { useTranslation } from 'react-i18next';
import '../styles/ant-reservation-time-modal.scss';
import ManagerReservationTimeSettingItem from './ManagerReservationTimeSettingItem';

type ManagerReservationTimeSettingModalProps = {
  selectedTimes: number[];
  onSelectTime: (id: number | number[]) => void;
};
const ManagerReservationTimeSettingModal = ({ selectedTimes, onSelectTime }: ManagerReservationTimeSettingModalProps) => {
  const { t: tManager } = useTranslation('manager');
  const { canEdit } = useMenuPermission(MenuKeys.MANAGER_RESERVATION_TIME);

  const { data: reservationTimeSettingListData } = useQuery({
    queryKey: reservationTimeSettingKeys.lists(),
    queryFn: () => getReservationTimeSettingApi(),
    select: (response) => {
      return { ...response.data, data: reservationTimeRespToDto(response.data.data) };
    },
    placeholderData: keepPreviousData,
  });

  useEffect(() => {
    if (!reservationTimeSettingListData?.data) return;
    const selectedAmIds = reservationTimeSettingListData.data.AM.filter((item) => item.usedInReservationTime).map(
      (item) => item.id,
    );
    const selectedPmIds = reservationTimeSettingListData.data.PM.filter((item) => item.usedInReservationTime).map(
      (item) => item.id,
    );
    onSelectTime(selectedAmIds.concat(selectedPmIds));
  }, [reservationTimeSettingListData?.data]);

  return (
    <div>
      <CustomText variant='caption-1'>{tManager('morning')}</CustomText>

      <div className='mt-2 flex flex-wrap justify-between mb-6'>
        <ListData
          data={reservationTimeSettingListData?.data.AM || []}
          renderItem={(item) => (
            <ManagerReservationTimeSettingItem
              title={item.time}
              isActive={selectedTimes.includes(item.id)}
              onClick={() => {
                if (!canEdit) return;
                onSelectTime(item.id);
              }}
            />
          )}
          keyExtractor={(item) => item.id}
          numberOfColumns={4}
          isAlways
          styleContainer='flex flex-wrap'
          gap={12}
        />
      </div>

      <div>
        <CustomText variant='caption-1'>{tManager('afternoon')}</CustomText>
      </div>

      <div className='mt-2 '>
        <ListData
          data={reservationTimeSettingListData?.data.PM || []}
          renderItem={(item) => (
            <ManagerReservationTimeSettingItem
              title={item.time}
              isActive={selectedTimes.includes(item.id)}
              onClick={() => {
                if (!canEdit) return;
                onSelectTime(item.id);
              }}
            />
          )}
          keyExtractor={(item) => item.id}
          numberOfColumns={4}
          isAlways
          styleContainer='flex flex-wrap'
          gap={12}
        />
      </div>
    </div>
  );
};

export default ManagerReservationTimeSettingModal;
