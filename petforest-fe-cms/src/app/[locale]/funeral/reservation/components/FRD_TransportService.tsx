import CustomSelect from '@/components/common/CustomSelect';
import CustomFormInput from '@/components/common/form/CustomFormField';
import useDaumnAddress from '@/hook/useDaumnAddress';
import { useUser } from '@/hook/user/useUser';
import { ReservationDetailDTO } from '@/store/funeral/reservation/dto/reservation.dto';
import { ICONS } from '@/theme';
import { DATE_FORMAT } from '@/utils/constants/date.constant';
import {
  DETAIL_SERVICE_NUMBER_ATTENDANCE_OPTIONS,
  DETAIL_SERVICE_TRANSPORT_ACCOMPANIED_STATUS_OPTIONS,
} from '@/utils/enums/funeral/reservation-mngt.enums';
import { InputEnum } from '@/utils/enums/input.enum';
import { helperOption } from '@/utils/helpers/common.helper';
import { Dayjs } from 'dayjs';
import Image from 'next/image';
import { useFormContext } from 'react-hook-form';
import { useTranslation } from 'react-i18next';

export const FRD_TransportService = () => {
  const { t: tReservation } = useTranslation('funeral_reservation');
  const { t: tEnum } = useTranslation('enum');
  const { control, setValue, watch } = useFormContext<ReservationDetailDTO>();
  const accompanimentStatus = watch('funeralInfo.transport.accompanimentStatus');
  const { managerOptions } = useUser();
  const { openPostcode } = useDaumnAddress();
  const TRANSPORT_OPTIONS = helperOption(
    'reservation.accompaniment_status',
    DETAIL_SERVICE_TRANSPORT_ACCOMPANIED_STATUS_OPTIONS,
    tEnum,
  );
  const NUMBER_PEOPLE_OPTIONS = helperOption('reservation.number_people', DETAIL_SERVICE_NUMBER_ATTENDANCE_OPTIONS, tEnum);

  const onOpenPostCode = async () => {
    const data = await openPostcode();
    const address = data.address || '';
    const province = data.sido;
    const district = data.sigungu;
    setValue('funeralInfo.transport.deliveryServiceAddress', `${address}, ${province}, ${district}`);
  };

  const disabledTime: any = (_: Dayjs | null) => ({
    disabledHours: () => [0, 1, 2, 3, 4, 5, 6, 7, 8, 22, 23],
    disabledMinutes: () => [],
    disabledSeconds: () => [],
  });

  return (
    <div className='mb-2 mt-4 flex gap-4 flex-col border-l-[3px] pl-4 border-primary-850'>
      <div className='grid grid-cols-2 gap-[10px] lg:gap-6 '>
        <CustomFormInput
          label={tReservation('detail.transport.accompaniment_status.title')}
          control={control}
          name={'funeralInfo.transport.accompanimentStatus'}
          type={InputEnum.OTHER}
          renderInput={({ value, onChange }: { value: number; onChange: (value: number) => void }) => (
            <CustomSelect
              value={value}
              optionsSelect={TRANSPORT_OPTIONS}
              onChange={(selectedValue) => {
                onChange(selectedValue);
                if (selectedValue === DETAIL_SERVICE_TRANSPORT_ACCOMPANIED_STATUS_OPTIONS.NOT_ACCOMPANY)
                  setValue('funeralInfo.transport.numberPeople', null);
              }}
            />
          )}
        />

        <CustomFormInput
          label={tReservation('detail.transport.number_people.title')}
          control={control}
          name={'funeralInfo.transport.numberPeople'}
          type={InputEnum.SELECT}
          optionItems={NUMBER_PEOPLE_OPTIONS}
          disabled={accompanimentStatus === DETAIL_SERVICE_TRANSPORT_ACCOMPANIED_STATUS_OPTIONS.NOT_ACCOMPANY}
        />

        <CustomFormInput
          control={control}
          name={'funeralInfo.transport.manager'}
          type={InputEnum.SELECT}
          label={tReservation('detail.inCharge')}
          optionItems={managerOptions}
          placeholder={tReservation('detail.select_person_in_charge')}
        />
        <CustomFormInput
          control={control}
          name={'funeralInfo.transport.transportTime'}
          type={InputEnum.TIME_PICKER}
          bordered={true}
          placeholder={tReservation('detail.placeholder')}
          format={DATE_FORMAT.TIME.PICKER_HOUR_MIN}
          label={tReservation('detail.transport_time')}
          disabledTime={disabledTime}
        />
      </div>

      <CustomFormInput
        label={tReservation('detail.transport.delivery_service_address.title')}
        control={control}
        name={'funeralInfo.transport.deliveryServiceAddress'}
        type={InputEnum.TEXT}
        placeholder={tReservation('detail.transport.delivery_service_address.placeholder_search')}
        suffix={<Image src={ICONS.LOCATION} width={20} alt='location' />}
        onClick={onOpenPostCode}
        readOnly
      />
      <CustomFormInput
        control={control}
        name={'funeralInfo.transport.deliveryServiceDetailAddress'}
        type={InputEnum.TEXT}
        placeholder={tReservation('detail.transport.delivery_service_address.placeholder_detail')}
      />
    </div>
  );
};
