import { CustomTextInput } from '@/components/common/CustomTextInput';
import CustomFormInput from '@/components/common/form/CustomFormField';
import { useOssuary } from '@/hook/ossuary/useOssuary';
import { ReservationDetailDTO } from '@/store/funeral/reservation/dto/reservation.dto';
import { InputEnum } from '@/utils/enums/input.enum';
import { PLACE_STATUS } from '@/utils/enums/ossuary/ossuary-room.enum';
import { OptionItem } from '@/utils/types/option.type';
import { useEffect, useState } from 'react';
import { useFormContext } from 'react-hook-form';
import { useTranslation } from 'react-i18next';

export const FRD_ColumbariumnService = () => {
  const { t: tReservation } = useTranslation('funeral_reservation');
  const { control, watch } = useFormContext<ReservationDetailDTO>();
  const roomValue = watch('serviceInfo.columbarium.category');
  const { ossuaryRoomNameOptions, ossuaryPlaceCatergoryOptions } = useOssuary({ roomValue, isCheckout: PLACE_STATUS.CHECK_OUT });
  const selectedRoomNumber = watch('serviceInfo.columbarium.roomNumber');
  const selectedNum = watch('serviceInfo.columbarium.roomName');
  const [initialOptions, setInitialOptions] = useState<OptionItem<any>[]>([]);

  useEffect(() => {
    if (initialOptions.length === 0 && ossuaryPlaceCatergoryOptions.length > 0) {
      if (!!selectedNum) {
        setInitialOptions([{ label: selectedNum, value: selectedRoomNumber }, ...ossuaryPlaceCatergoryOptions]);
      } else {
        setInitialOptions(ossuaryPlaceCatergoryOptions);
      }
    }
  }, [ossuaryPlaceCatergoryOptions]);

  return (
    <div className='grid grid-cols-2 gap-y-4 gap-x-[10px] lg:gap-x-6'>
      <CustomFormInput
        label={tReservation('detail.columbarium.category')}
        control={control}
        name={'serviceInfo.columbarium.category'}
        type={InputEnum.SELECT}
        optionItems={ossuaryRoomNameOptions}
        placeholder={tReservation('detail.placeholder')}
      />

      <CustomFormInput
        label={tReservation('detail.columbarium.room_number')}
        control={control}
        name={'serviceInfo.columbarium.roomNumber'}
        type={InputEnum.SELECT}
        optionItems={initialOptions}
        placeholder={tReservation('detail.placeholder')}
      />

      <CustomFormInput
        label={tReservation('detail.columbarium.date_of_admission')}
        control={control}
        name={'serviceInfo.columbarium.date'}
        type={InputEnum.RANGE_PICKER}
        placeholder={tReservation('detail.placeholder')}
        className='col-span-2 lg:col-span-1'
      />

      {/* <CustomFormInput
        label={tReservation('detail.columbarium.emergency_contact')}
        control={control}
        name={'serviceInfo.columbarium.emergencyContact'}
        type={InputEnum.TEXT}
        placeholder=''
      /> */}
      <CustomFormInput
        label={tReservation('detail.columbarium.emergency_contact')}
        control={control}
        name={'serviceInfo.columbarium.emergencyContact'}
        type={InputEnum.OTHER}
        className='col-span-2 lg:col-span-1'
        renderInput={({ value, onChange }: { value: string; onChange: (value: string) => void }) => {
          const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
            let formattedValue = e.target.value.replace(/\D/g, '');
            if (formattedValue.length <= 9) {
              formattedValue = formattedValue.replace(/(\d{2})(\d{3})(\d{4})/, '$1-$2-$3');
            } else if (formattedValue.length === 10) {
              formattedValue = formattedValue.replace(/(\d{2})(\d{4})(\d{4})/, '$1-$2-$3');
            } else if (formattedValue.length === 11) {
              formattedValue = formattedValue.replace(/(\d{3})(\d{4})(\d{4})/, '$1-$2-$3');
            }

            onChange(formattedValue);
          };

          return <CustomTextInput value={value} onChange={handleChange} maxLength={13} />;
        }}
      />
    </div>
  );
};
