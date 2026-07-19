import CustomSelect from '@/components/common/CustomSelect';
import CustomFormInput from '@/components/common/form/CustomFormField';
import { useSettingPartner } from '@/hook/setting/useSettingSettingPartner';
import { ReservationDetailDTO } from '@/store/funeral/reservation/dto/reservation.dto';
import { getDetailSettingPartnerApi } from '@/store/setting/partner/setting-partner.api';
import { settingPartnerRespToDto } from '@/store/setting/partner/setting-partner.service';
import { InputEnum } from '@/utils/enums/input.enum'; // Ensure correct path
import { useMutation } from '@tanstack/react-query';
import { useFormContext } from 'react-hook-form';
import { useTranslation } from 'react-i18next';
import { FRD_TitleSectionFormTitle } from './FRD_TitleSectionFormTitle';

export const FRD_PartnerCompany = () => {
  const { t: tReservation } = useTranslation('funeral_reservation');
  const { control, setValue } = useFormContext<ReservationDetailDTO>();
  const { partnerOptions } = useSettingPartner({});

  const { mutate: mutateChangeFuneralServiceMaterials } = useMutation({
    mutationFn: (value: number) => getDetailSettingPartnerApi(value),
    onSuccess: (response) => {
      const partnerNote = response?.data?.data ? settingPartnerRespToDto(response?.data?.data).note : '';
      setValue('partnerCompany.remarks', partnerNote, { shouldDirty: true });
    },
  });

  return (
    <div>
      <FRD_TitleSectionFormTitle title={tReservation('detail.title.partner_company')} />
      <div className='grid grid-cols-2 gap-y-4 gap-x-[10px] lg:gap-x-6 my-4'>
        <CustomFormInput
          label={tReservation('detail.partner_company.company_name')}
          control={control}
          name={'partnerCompany.companyName'}
          placeholder={tReservation('detail.placeholder')}
          type={InputEnum.OTHER}
          renderInput={({ value, onChange }: { value: string; onChange: (value: string) => void }) => (
            <CustomSelect
              value={value}
              options={partnerOptions}
              onChange={(selectedValue) => {
                onChange(selectedValue);
                mutateChangeFuneralServiceMaterials(selectedValue);
              }}
              placeholder={tReservation('detail.placeholder')}
            />
          )}
        />
        <CustomFormInput
          label={tReservation('detail.partner_company.remarks')}
          control={control}
          name={'partnerCompany.remarks'}
          type={InputEnum.TEXT}
          placeholder=''
        />
      </div>
    </div>
  );
};
