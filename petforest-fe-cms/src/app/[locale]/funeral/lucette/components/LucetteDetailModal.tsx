import CustomDatePicker from '@/components/common/CustomDatePicker';
import CustomSelect from '@/components/common/CustomSelect';
import { CustomTextInput } from '@/components/common/CustomTextInput';
import CustomText from '@/components/common/Text';
import useDaumnAddress from '@/hook/useDaumnAddress';
import { ICONS } from '@/theme';
import { ENUM_STATUS } from '@/utils/enums/status.enum';
import { helperOption } from '@/utils/helpers/common.helper';
import { LucetteInfoType } from '@/utils/schemas/funeral/lucette.schema';
import Image from 'next/image';
import { Controller, useFormContext } from 'react-hook-form';
import { useTranslation } from 'react-i18next';

export const LucetteDetailModal = () => {
  const { t: tLucette } = useTranslation('funeral_lucette');
  const { t: tEnum } = useTranslation('enum');

  const { control, setValue } = useFormContext<LucetteInfoType>();
  const { openPostcode } = useDaumnAddress();

  const statusOptions = helperOption('status', ENUM_STATUS, tEnum);

  const onOpenPostCode = async () => {
    const data = await openPostcode();
    const address = data.address || '';
    setValue('funeralAddress', address);
  };

  return (
    <form id='lucette-info-form' className='py-[8px]'>
      <div className='mb-4'>
        <CustomText variant='label-1-normal' className='!font-semibold'>
          {tLucette('lucette.detail.item_title')}
        </CustomText>
        <Controller
          name='jewelry'
          control={control}
          render={({ field: { onChange, value } }) => (
            <CustomTextInput value={value} onChange={onChange} className='!mt-[10px]' placeholder='' />
          )}
        />
      </div>

      <div className='mb-4'>
        <CustomText variant='label-1-normal' className='!font-semibold'>
          {tLucette('lucette.detail.delivery.title')}
        </CustomText>
        <Controller
          name='tycheDeliveryDate'
          control={control}
          render={({ field: { onChange, value } }) => (
            <CustomDatePicker
              placeholder={tLucette('lucette.detail.handover.placeholder')}
              onChange={(date) => onChange(date)}
              value={value}
              className='!mt-[10px]'
              autoFocus={false}
            />
          )}
        />
      </div>

      <div className='mb-4'>
        <CustomText variant='label-1-normal' className='!font-semibold'>
          {tLucette('lucette.detail.handover.title')}
        </CustomText>
        <Controller
          name='petForestIndiaDate'
          control={control}
          render={({ field: { onChange, value } }) => (
            <CustomDatePicker
              placeholder={tLucette('lucette.detail.handover.placeholder')}
              onChange={(date) => onChange(date)}
              value={value}
              className='!mt-[10px]'
              autoFocus={false}
            />
          )}
        />
      </div>

      <div className='mb-4'>
        <CustomText variant='label-1-normal' className='!font-semibold'>
          {tLucette('lucette.detail.delivery_status_title')}
        </CustomText>
        <Controller
          name='deliveryStatus'
          control={control}
          render={({ field: { onChange, value } }) => (
            <CustomSelect onChange={onChange} options={statusOptions} value={value} className='!mt-[10px]' placeholder='' />
          )}
        />
      </div>
      <div className='mb-4'>
        <CustomText variant='label-1-normal' className='!font-semibold'>
          {tLucette('lucette.detail.delivery_address.title')}
        </CustomText>
        <Controller
          name='funeralAddress'
          control={control}
          render={({ field: { onChange, value } }) => (
            <CustomTextInput
              value={value}
              onChange={onChange}
              readOnly
              onClick={onOpenPostCode}
              className='!mt-[10px]'
              suffix={<Image src={ICONS.LOCATION} alt='Look' width={20} height={20} />}
              placeholder={tLucette('lucette.detail.delivery_address.address_funeral_placeholder')}
            />
          )}
        />
        <Controller
          name='detailAddress'
          control={control}
          render={({ field: { onChange, value } }) => (
            <CustomTextInput
              value={value}
              onChange={onChange}
              className='!mt-[10px]'
              placeholder={tLucette('lucette.detail.delivery_address.address_detail_placeholder')}
            />
          )}
        />
      </div>
    </form>
  );
};
