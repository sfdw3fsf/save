import { CustomTextInput } from '@/components/common/CustomTextInput';
import CustomFormInput from '@/components/common/form/CustomFormField';
import useDaumnAddress from '@/hook/useDaumnAddress';
import { ReservationDetailDTO } from '@/store/funeral/reservation/dto/reservation.dto';
import { ICONS } from '@/theme';
import { DETAIL_GUARDIAN_BOOKING_OPTIONS, DETAIL_GUARDIAN_VISIT_OPTIONS } from '@/utils/enums/funeral/reservation-mngt.enums';
import { InputEnum } from '@/utils/enums/input.enum';
import { helperOption } from '@/utils/helpers/common.helper';
import Image from 'next/image';
import { useFormContext } from 'react-hook-form';
import { useTranslation } from 'react-i18next';
import { FRD_TitleSectionFormTitle } from './FRD_TitleSectionFormTitle';

export const FRD_Guardian = () => {
  const { t: tReservation } = useTranslation('funeral_reservation');
  const { t: tEnum } = useTranslation('enum');
  const { control, setValue } = useFormContext<ReservationDetailDTO>();
  const { openPostcode } = useDaumnAddress();
  const BOOKING_OPTIONS = helperOption('reservation.booking', DETAIL_GUARDIAN_BOOKING_OPTIONS, tEnum);
  const VISIT_OPTIONS = helperOption('reservation.visit', DETAIL_GUARDIAN_VISIT_OPTIONS, tEnum);

  const onOpenPostCode = async () => {
    const data = await openPostcode();
    const address = data.address || '';
    const province = data.sido;
    const district = data.sigungu;
    setValue('guardianInfo.addressGeneral', `${address}, ${province}, ${district}`);
    setValue('guardianInfo.provinceName', province);
    setValue('guardianInfo.districtName', district);
  };

  return (
    <div>
      <FRD_TitleSectionFormTitle title={tReservation('detail.title.guardian')} />
      <div className='flex gap-4 flex-col'>
        <div className='grid grid-cols-2 gap-y-4 gap-x-[10px] lg:gap-x-6 mt-4'>
          <CustomFormInput
            label={tReservation('detail.booking.title')}
            control={control}
            name='guardianInfo.booking'
            type={InputEnum.SELECT}
            optionItems={BOOKING_OPTIONS}
            placeholder={tReservation('detail.placeholder')}
          />
          <CustomFormInput
            label={tReservation('detail.visit.title')}
            control={control}
            name='guardianInfo.visit'
            type={InputEnum.SELECT}
            optionItems={VISIT_OPTIONS}
            placeholder={tReservation('detail.placeholder')}
          />
          <CustomFormInput
            label={tReservation('detail.guardian_name')}
            control={control}
            name='guardianInfo.guardianName'
            type={InputEnum.TEXT}
          />
          <CustomFormInput
            label={tReservation('detail.phone_number')}
            control={control}
            requiredLabel={true}
            classNameLabel='flex gap-1 items-center'
            name='guardianInfo.phoneNumber'
            type={InputEnum.OTHER}
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
        <CustomFormInput
          label={tReservation('detail.address.title')}
          control={control}
          name='guardianInfo.addressGeneral'
          type={InputEnum.TEXT}
          placeholder={tReservation('detail.address.placeholder_search')}
          suffix={<Image src={ICONS.LOCATION} width={20} alt='location' />}
          onClick={onOpenPostCode}
          readOnly
        />
        <CustomFormInput
          control={control}
          name='guardianInfo.addressDetail'
          type={InputEnum.TEXT}
          placeholder={tReservation('detail.address.placeholder_detail')}
        />
        {/* <CustomFormInput label={tReservation('detail.email')} control={control} name='guardianInfo.email' type={InputEnum.TEXT} /> */}
        <CustomFormInput
          label={tReservation('detail.request')}
          control={control}
          name='guardianInfo.request'
          type={InputEnum.TEXT_AREA}
          rows={3}
        />
      </div>
    </div>
  );
};
