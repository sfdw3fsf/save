import CustomText from '@/components/common/Text';
import { FuneralMNGTReservationListDTO } from '@/store/funeral/reservation/dto/reservation.dto';
import { DETAIL_FUNERAL_RESERVATION_OPTIONS, DETAIL_FUNERAL_STATUS_OPTIONS } from '@/utils/enums/funeral/reservation-mngt.enums';
import { RightOutlined } from '@ant-design/icons';
import { Divider } from 'antd';
import { useMemo } from 'react';
import { useTranslation } from 'react-i18next';

type ReservationMobileProps = {
  data: FuneralMNGTReservationListDTO;
  onOpenDetail: (id: number) => void;
};
const ReservationMobile = ({ data, onOpenDetail }: ReservationMobileProps) => {
  const { t: tReservation } = useTranslation('funeral_reservation');
  const { t: tEnum } = useTranslation('enum');
  const { t: tCommon } = useTranslation('common');

  const onDetail = () => {
    onOpenDetail(data.id);
  };

  const showRedPoint = useMemo<boolean>(
    () =>
      data['funeralInformation.processStatus'] === DETAIL_FUNERAL_STATUS_OPTIONS.RESERVATION &&
      data['funeralInformation.reservationStatus'] !== DETAIL_FUNERAL_RESERVATION_OPTIONS.CONFIRMED &&
      data['funeralInformation.reservationStatus'] !== DETAIL_FUNERAL_RESERVATION_OPTIONS.CANCELLATION,
    [data['funeralInformation.processStatus'], data['funeralInformation.reservationStatus']],
  );

  const showBluePoint = useMemo<boolean>(() => data.isRegisterPhoto, [data.isRegisterPhoto]);

  return (
    <div>
      <div className='rounded-lg border-[1px] border-neutral-40 w-full mb-3'>
        <div
          className={`px-3 flex items-center justify-between relative ${showRedPoint || showBluePoint ? 'pt-[14px]' : 'pt-2'}`}>
          <CustomText variant='label-1-normal' className='!font-semibold'>
            {data.funeralTime}
          </CustomText>

          <div className='flex gap-1 flex-1 px-3'>
            {(showRedPoint || showBluePoint) && (
              <div className='flex gap-1 items-center justify-center'>
                {showRedPoint && <div className={`w-[10px] h-[10px] rounded-full bg-error-500`} />}
                {showBluePoint && <div className={`w-[10px] h-[10px] rounded-full bg-blue-500`} />}
              </div>
            )}

            {(!!data.petName || !!data.petWeight) && (
              <div className='flex gap-[6px]'>
                {!!data.petName && <CustomText variant='label-1-normal'>{data.petName}</CustomText>}
                {!!data.petWeight && <CustomText variant='label-1-normal'>{data.petWeight + 'kg'}</CustomText>}
              </div>
            )}
          </div>

          <div className='flex gap-1' onClick={onDetail}>
            {!!data.funeralService && (
              <div className='px-2 py-[6px] rounded-lg' style={{ backgroundColor: data.color }}>
                <CustomText variant='label-1-normal'>{data.funeralService}</CustomText>
              </div>
            )}
            <RightOutlined />
          </div>
        </div>

        <Divider className={`my-2 bg-neutral-30`} />

        <div className='pb-2'>
          <div className='flex gap-3 px-2'>
            {data['funeralInformation.processStatus'] && (
              <div className='flex gap-[6px] items-center'>
                <div className='px-[8px] py-[6px] whitespace-nowrap rounded-full bg-green-cream min-w-[41px]'>
                  <CustomText variant='label-1-normal' className='!font-medium'>
                    {tReservation('table.status')}
                  </CustomText>
                </div>
                <CustomText variant='label-1-normal'>
                  {tEnum(`reservation.status.${data['funeralInformation.processStatus']}`)}
                </CustomText>
              </div>
            )}
            {data.progress && (
              <div className='flex gap-[6px] items-center'>
                <div className='px-[8px] py-[6px] whitespace-nowrap rounded-full bg-green-cream min-w-[70px]'>
                  <CustomText variant='label-1-normal' className='!font-medium'>
                    {tReservation('table.progress')}
                  </CustomText>
                </div>
                <CustomText variant='label-1-normal'>{data.progress}</CustomText>
              </div>
            )}
          </div>

          <div className='flex gap-3 px-2 mt-2'>
            {data.progressNote && (
              <div className='flex gap-[6px] items-center'>
                <div className='px-[8px] py-[6px] whitespace-nowrap rounded-full bg-green-cream min-w-[41px]'>
                  <CustomText variant='label-1-normal' className='!font-medium'>
                    {tCommon('table.memo')}
                  </CustomText>
                </div>
                <CustomText variant='label-1-normal' className='max-w-[100px] whitespace-nowrap text-ellipsis overflow-hidden'>
                  {data.progressNote}
                </CustomText>
              </div>
            )}
            {Object.values(data.carService).some((value) => Boolean(value)) && (
              <div className='flex gap-[6px] items-center flex-1 overflow-hidden'>
                <div className='px-[8px] py-[6px] whitespace-nowrap rounded-full bg-orange-secondary min-w-[41px]'>
                  <CustomText variant='label-1-normal' className='!font-medium'>
                    {tReservation('table.car_service' as any)}
                  </CustomText>
                </div>
                <CustomText variant='label-1-normal' className='whitespace-nowrap text-ellipsis overflow-hidden'>
                  {data.carService.guardianAttendanceType &&
                    tEnum(`reservation.accompaniment_status.${data.carService.guardianAttendanceType}` as any)}
                  {data.carService.managerName && '/ ' + data.carService.managerName}
                  {data.carService.vehicleTime && '/ ' + data.carService.vehicleTime}
                </CustomText>
              </div>
            )}
          </div>
        </div>
      </div>
    </div>
  );
};

export default ReservationMobile;
