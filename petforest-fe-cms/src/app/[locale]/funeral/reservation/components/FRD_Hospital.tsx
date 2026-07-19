import CustomSelect from '@/components/common/CustomSelect';
import CustomFormInput from '@/components/common/form/CustomFormField';
import { useHospital } from '@/hook/collaborating-hospital/useHospital';
import { useHospitalSettlementMethod } from '@/hook/setting/useHospitalSettlementMethod';
import { getHospitalDetailApi } from '@/store/collaborating-hospital/collaborating-hospital.api';
import { hospitalDetailRespToDto } from '@/store/collaborating-hospital/collaborating-hospital.service';
import { ReservationDetailDTO } from '@/store/funeral/reservation/dto/reservation.dto';
import { InputEnum } from '@/utils/enums/input.enum'; // Ensure correct path
import { useMutation } from '@tanstack/react-query';
import { useFormContext } from 'react-hook-form';
import { useTranslation } from 'react-i18next';
import { FRD_TitleSectionFormTitle } from './FRD_TitleSectionFormTitle';

export const FRD_Hospital = () => {
  const { t: tReservation } = useTranslation('funeral_reservation');
  const { control, setValue } = useFormContext<ReservationDetailDTO>();
  const { settlementMethodOptions } = useHospitalSettlementMethod();
  const { hospitalOptions } = useHospital();

  const { mutate: mutateChangeHospital } = useMutation({
    mutationFn: (hospitalId: number) => getHospitalDetailApi(hospitalId || 0),
    onSuccess: (response) => {
      const hospitalSettlementMethod = response?.data.data
        ? hospitalDetailRespToDto(response?.data.data).entitlementMethod
        : undefined;
      setValue('hospital.settlement_method', hospitalSettlementMethod, { shouldDirty: true });
    },
  });

  return (
    <div>
      <FRD_TitleSectionFormTitle title={tReservation('detail.title.hospital')} />
      <div className='flex gap-0 lg:gap-4 flex-col'>
        <CustomFormInput
          label={tReservation('detail.hospital.hospital_name')}
          control={control}
          name={'hospital.hospital_name'}
          type={InputEnum.OTHER}
          placeholder={tReservation('detail.placeholder')}
          optionItems={hospitalOptions}
          renderInput={({ value, onChange }: { value: number; onChange: (value: number) => void }) => (
            <CustomSelect
              value={value}
              options={hospitalOptions}
              onChange={(selectedValue) => {
                onChange(selectedValue);
                mutateChangeHospital(selectedValue);
              }}
              placeholder={tReservation('detail.placeholder')}
              showSearch
              filterOption={(input, option) => ((option?.label as string) ?? '').toLowerCase().includes(input.toLowerCase())}
            />
          )}
          className='col-span-2 lg:col-span-1'
        />
        <div className='grid grid-cols-2 gap-y-4 gap-x-[10px] lg:gap-x-6 my-4'>
          <CustomFormInput
            label={tReservation('detail.hospital.settlement_method')}
            control={control}
            name={'hospital.settlement_method'}
            type={InputEnum.SELECT}
            placeholder={tReservation('detail.placeholder')}
            optionItems={settlementMethodOptions}
            className='col-span-2 lg:col-span-1'
          />
          <CustomFormInput
            label={tReservation('detail.hospital.amount')}
            control={control}
            name={'hospital.amount'}
            type={InputEnum.THOUSAND_NUMBER}
            placeholder=''
          />
        </div>
      </div>
    </div>
  );
};
