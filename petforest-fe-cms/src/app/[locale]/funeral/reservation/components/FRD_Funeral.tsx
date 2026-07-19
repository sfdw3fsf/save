import CustomButton from '@/components/common/CustomButton';
import CustomSelect from '@/components/common/CustomSelect';
import CustomFormInput from '@/components/common/form/CustomFormField';
import { useReservationTime } from '@/hook/reservation-time/useReservationTime';
import { useSettingProgress } from '@/hook/setting/useSettingProgress';
import { useSettingWaitingRoom } from '@/hook/setting/useSettingWaitingRoom';
import { ReservationDetailDTO } from '@/store/funeral/reservation/dto/reservation.dto';
import { createReservationMessageApi } from '@/store/funeral/reservation/reservation.api';
import { ICONS } from '@/theme';
import {
  DETAIL_FUNERAL_RESERVATION_OPTIONS,
  DETAIL_FUNERAL_STATUS_OPTIONS,
  DETAIL_VISTOR_NUMBER_OPTIONS,
} from '@/utils/enums/funeral/reservation-mngt.enums';
import { InputEnum } from '@/utils/enums/input.enum';
import { helperOption } from '@/utils/helpers/common.helper';
import { handleError } from '@/utils/helpers/notification-error.helper';
import { handleSuccess } from '@/utils/helpers/notification-success.helper';
import { useMutation } from '@tanstack/react-query';
import Image from 'next/image';
import { useFormContext, useWatch } from 'react-hook-form';
import { useTranslation } from 'react-i18next';
import FRD_FuneralServiceSelection from './FRD_FuneralServiceSelection';
import { FRD_TitleSectionFormTitle } from './FRD_TitleSectionFormTitle';

export const FRD_Funeral = () => {
  const { t: tReservation } = useTranslation('funeral_reservation');
  const { t: tEnum } = useTranslation('enum');
  const { control, setValue, watch } = useFormContext<ReservationDetailDTO>();
  const progressId = useWatch({ control: control, name: 'funeralInfo.progress' });
  const reservationStatus = useWatch({ control: control, name: 'funeralInfo.reservation' });
  const situationStatus = useWatch({ control: control, name: 'funeralInfo.status' });
  const { reservationTimeOptions } = useReservationTime();
  const { waitingRoomOptions } = useSettingWaitingRoom({});
  const { progressOptions, subProgressOptions } = useSettingProgress({ progressId });
  const RESERVATION_OPTIONS = helperOption('reservation.reservation', DETAIL_FUNERAL_RESERVATION_OPTIONS, tEnum);
  const STATUS_OPTIONS = helperOption('reservation.status', DETAIL_FUNERAL_STATUS_OPTIONS, tEnum);
  const NUMBER_PEOPLE_OPTIONS = helperOption('reservation.visitor_number', DETAIL_VISTOR_NUMBER_OPTIONS, tEnum);

  const { mutate: mutateCreateMessage } = useMutation({
    mutationFn: createReservationMessageApi,
    onSuccess: () => {
      handleSuccess('message');
    },
    onError: handleError,
  });

  const onCreateMessage = () => {
    mutateCreateMessage([watch('id')]);
  };

  return (
    <div>
      <div className='flex gap-[18px] flex-col'>
        <FRD_TitleSectionFormTitle title={tReservation('detail.title.funeral')} />
        <div className='inline-flex'>
          <CustomButton
            onClick={onCreateMessage}
            type='ghost'
            icon={<Image src={ICONS.NOTI} alt='noti' />}
            className='!bg-primary-50 !h-[38px]'>
            {tReservation('detail.send_noti')}
          </CustomButton>
        </div>
      </div>
      <div className='flex gap-4 flex-col mb-4'>
        <div className='grid grid-cols-3 gap-y-4 gap-x-[10px] lg:gap-x-6 mt-4'>
          <CustomFormInput
            control={control}
            name={'funeralInfo.funeralDate'}
            type={InputEnum.DATE_PICKER}
            label={tReservation('detail.funeral_date')}
          />
          <CustomFormInput
            control={control}
            name={'funeralInfo.time'}
            type={InputEnum.SELECT}
            label={tReservation('detail.funeral_time')}
            optionItems={reservationTimeOptions}
          />
          <CustomFormInput
            label={tReservation('detail.visitor_number.title')}
            control={control}
            name={'funeralInfo.numberOfVisitors'}
            type={InputEnum.SELECT}
            optionItems={NUMBER_PEOPLE_OPTIONS}
          />
        </div>
        <FRD_FuneralServiceSelection />
        <div className='grid grid-cols-2 gap-y-4 gap-x-[10px] lg:gap-x-6 mb-4 mt-2'>
          <CustomFormInput
            control={control}
            name={'funeralInfo.reservation'}
            type={InputEnum.SELECT}
            label={tReservation('detail.reservation.title')}
            optionItems={RESERVATION_OPTIONS}
          />
          <CustomFormInput
            control={control}
            name={'funeralInfo.status'}
            type={InputEnum.SELECT}
            label={tReservation('detail.status.title')}
            optionItems={STATUS_OPTIONS}
            disabled={reservationStatus !== DETAIL_FUNERAL_RESERVATION_OPTIONS.CONFIRMED}
          />
        </div>
        <div className='grid grid-cols-3 gap-y-4 gap-x-[10px] lg:gap-x-6'>
          <CustomFormInput
            control={control}
            name={'funeralInfo.progress'}
            type={InputEnum.OTHER}
            label={tReservation('detail.progress.title')}
            renderInput={({ value, onChange }: { value: number; onChange: (value: number) => void }) => (
              <CustomSelect
                value={value}
                options={progressOptions}
                disabled={situationStatus !== DETAIL_FUNERAL_STATUS_OPTIONS.INPROGRESS}
                onChange={(selectedValue) => {
                  onChange(selectedValue);
                  setValue('funeralInfo.subProgress', null);
                }}
                placeholder={tReservation('detail.progress.placeholder')}
              />
            )}
          />
          <CustomFormInput
            control={control}
            name={'funeralInfo.subProgress'}
            type={InputEnum.SELECT}
            label={tReservation('detail.sub_progress.title')}
            disabled={situationStatus !== DETAIL_FUNERAL_STATUS_OPTIONS.INPROGRESS}
            optionItems={subProgressOptions}
            placeholder={tReservation('detail.sub_progress.placeholder')}
          />
          <CustomFormInput
            control={control}
            name={'funeralInfo.waitingRoom'}
            type={InputEnum.SELECT}
            label={tReservation('detail.waiting_room.title')}
            placeholder={tReservation('detail.placeholder')}
            disabled={situationStatus !== DETAIL_FUNERAL_STATUS_OPTIONS.INPROGRESS}
            optionItems={waitingRoomOptions}
          />
        </div>

        <CustomFormInput
          control={control}
          name={'funeralInfo.progressStatusNote'}
          type={InputEnum.TEXT}
          label={tReservation('detail.progress_status_notes')}
        />
        <CustomFormInput
          control={control}
          name={'funeralInfo.request'}
          type={InputEnum.TEXT_AREA}
          label={tReservation('detail.request')}
          rows={3}
        />
      </div>
    </div>
  );
};
