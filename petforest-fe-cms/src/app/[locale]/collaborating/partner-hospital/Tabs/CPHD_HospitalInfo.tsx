import CustomFormInput from '@/components/common/form/CustomFormField';
import { useBranch } from '@/hook/branch/useBranch';
import { useHospitalSettlementMethod } from '@/hook/setting/useHospitalSettlementMethod';
import useDaumnAddress from '@/hook/useDaumnAddress';
import { HospitalDetailDTO } from '@/store/collaborating-hospital/dto/collaborating-hospital.dto';
import { ICONS } from '@/theme';
import { InputEnum } from '@/utils/enums/input.enum';
import Image from 'next/image';
import { useFormContext } from 'react-hook-form';
import { useTranslation } from 'react-i18next';

export const CPHD_HospitalInfo = () => {
  const { t: tPartnerHospital } = useTranslation('collaborating_partner_hospital');
  const { control, setValue } = useFormContext<HospitalDetailDTO>();
  const { openPostcode } = useDaumnAddress();

  const { settlementMethodOptions } = useHospitalSettlementMethod();
  const { branchOptions } = useBranch();

  const onOpenPostCode = async () => {
    const data = await openPostcode();
    const address = data.address || '';
    const province = data.sido;
    const district = data.sigungu;
    setValue('address', address);
    setValue('provinceName', province);
    setValue('districtName', district);
  };

  return (
    <div className='flex gap-4 flex-col'>
      <div className='grid grid-cols-1 gap-y-4 gap-x-6 my-4 md:grid-cols-2'>
        <CustomFormInput<HospitalDetailDTO>
          label={tPartnerHospital('detail.hospital_information.director')}
          control={control}
          name='director'
          placeholder=''
          type={InputEnum.TEXT}
        />
        <CustomFormInput<HospitalDetailDTO>
          label={tPartnerHospital('detail.hospital_information.settlement_method')}
          control={control}
          name='entitlementMethod'
          type={InputEnum.SELECT}
          optionItems={settlementMethodOptions}
        />
        <CustomFormInput<HospitalDetailDTO>
          label={tPartnerHospital('detail.hospital_information.connected_branch')}
          control={control}
          name='branchIds'
          type={InputEnum.SELECT_MULTI}
          optionItems={branchOptions}
        />
        <CustomFormInput<HospitalDetailDTO>
          label={tPartnerHospital('detail.hospital_information.hospital_size')}
          control={control}
          name='hospitalSize'
          placeholder=''
          type={InputEnum.TEXT}
        />
      </div>
      <CustomFormInput<HospitalDetailDTO>
        label={tPartnerHospital('detail.hospital_information.parking_availability')}
        control={control}
        name='parkingStatus'
        placeholder=''
        type={InputEnum.TEXT}
      />
      <CustomFormInput<HospitalDetailDTO>
        label={tPartnerHospital('detail.hospital_information.hospital_address.title')}
        control={control}
        name='address'
        suffix={<Image src={ICONS.LOCATION} width={20} alt='location' />}
        placeholder={tPartnerHospital('detail.hospital_information.hospital_address.address_search')}
        type={InputEnum.TEXT}
        onClick={onOpenPostCode}
        readOnly
      />
      <CustomFormInput<HospitalDetailDTO>
        control={control}
        name='detailAddress'
        placeholder={tPartnerHospital('detail.hospital_information.hospital_address.detailed_address')}
        type={InputEnum.TEXT}
      />
      <div className='grid grid-cols-1 gap-y-4 gap-x-6 my-4 md:grid-cols-2'>
        <CustomFormInput<HospitalDetailDTO>
          label={tPartnerHospital('detail.hospital_information.business_registration_number')}
          control={control}
          name='businessRegistrationNumber'
          placeholder=''
          type={InputEnum.TEXT}
        />
        <CustomFormInput<HospitalDetailDTO>
          label={tPartnerHospital('detail.hospital_information.phone_number')}
          control={control}
          name='phoneNumber'
          placeholder=''
          type={InputEnum.TEXT}
        />
      </div>
      <CustomFormInput<HospitalDetailDTO>
        label={tPartnerHospital('detail.hospital_information.email')}
        control={control}
        name='email'
        placeholder=''
        type={InputEnum.TEXT}
      />
      <CustomFormInput<HospitalDetailDTO>
        label={tPartnerHospital('detail.hospital_information.special_notes')}
        control={control}
        name='note'
        placeholder=''
        type={InputEnum.TEXT_AREA}
      />
    </div>
  );
};
