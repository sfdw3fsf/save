import CustomIconButton from '@/components/common/CustomIconButton';
import CustomFormInput from '@/components/common/form/CustomFormField';
import CustomText from '@/components/common/Text';
import { useAuthStore } from '@/store/auth/auth.store';
import { ReservationDetailDTO } from '@/store/funeral/reservation/dto/reservation.dto';
import { ICONS } from '@/theme';
import { STOCK_ORDER_CATEGORIES } from '@/utils/constants/funeral/stock-order.constant';
import { DETAIL_SERVICE_NEW_AUDITION_COLOR_OPTIONS } from '@/utils/enums/funeral/reservation-mngt.enums';
import { InputEnum } from '@/utils/enums/input.enum'; // Ensure the path is correct
import { helperGetBranchEnum, helperOption } from '@/utils/helpers/common.helper';
import { PlusCircleOutlined } from '@ant-design/icons';
import Image from 'next/image';
import { useEffect, useRef } from 'react';
import { useFieldArray, useFormContext } from 'react-hook-form';
import { useTranslation } from 'react-i18next';

export const FRD_NewAdditionService = () => {
  const { t: tReservation } = useTranslation('funeral_reservation');
  const { t: tEnum } = useTranslation('enum');
  const { control } = useFormContext<ReservationDetailDTO>();
  const { fields, prepend, remove } = useFieldArray({
    control,
    name: 'serviceInfo.newAdditon.typeMemorialTablet',
  });
  const { selectedBranchId } = useAuthStore();

  const currentBranch = helperGetBranchEnum(selectedBranchId);

  const COLOR_OPTIONS = helperOption('material', DETAIL_SERVICE_NEW_AUDITION_COLOR_OPTIONS, tEnum);
  const AMOUNT_TYPE_OPTIONS = helperOption(
    'detail.new_additon.type_memorial_tablet.option',
    STOCK_ORDER_CATEGORIES[currentBranch as keyof typeof STOCK_ORDER_CATEGORIES] as any,
    tReservation,
  );

  const onAddTypeMemorialTablet = () => {
    prepend({ type: undefined, quantity: 1 });
  };

  const initialized = useRef(false);

  useEffect(() => {
    if (!initialized.current) {
      initialized.current = true;
      if (fields.length === 0) {
        prepend({ type: undefined, quantity: 1 });
      }
    }
  }, []);

  return (
    <div className='pb-6 flex gap-4 flex-col'>
      <CustomFormInput
        label={tReservation('detail.new_additon.color.title')}
        control={control}
        name={'serviceInfo.newAdditon.color'}
        type={InputEnum.SELECT}
        optionItems={COLOR_OPTIONS}
      />
      <div className='flex justify-between items-center'>
        <CustomText variant='label-1-normal' className='!font-semibold'>
          {tReservation('detail.new_additon.type_memorial_tablet.title')}
        </CustomText>
        <div></div>
      </div>
      <div>
        <div className={` !bg-neutral-15 grid grid-cols-9 gap-2 lg:gap-4 items-center lg:p-2`}>
          <div className='col-span-6'>
            <CustomText variant='label-1-normal' className='pl-6 !font-semibold'>
              {tReservation('detail.new_additon.header.type')}
            </CustomText>
          </div>
          <CustomText variant='label-1-normal' className='!font-semibold col-span-2 whitespace-nowrap'>
            {tReservation('detail.new_additon.header.quantity')}
          </CustomText>
          <div className='mr-2 flex justify-end '>
            <CustomIconButton
              icon={<PlusCircleOutlined className='!text-primary-1000 ' style={{ fontSize: '18px' }} />}
              onClick={onAddTypeMemorialTablet}
            />
          </div>
        </div>
        {fields.map((field, index) => (
          <div key={field.id} className='grid grid-cols-9 gap-2 lg:gap-4 p-2'>
            <div className='col-span-6'>
              <CustomFormInput
                control={control}
                name={`serviceInfo.newAdditon.typeMemorialTablet[${index}].type` as any}
                type={InputEnum.SELECT}
                optionItems={AMOUNT_TYPE_OPTIONS}
                placeholder={tReservation('detail.stock.placeholder' as any)}
              />
            </div>

            <div className='col-span-2'>
              <CustomFormInput
                control={control}
                name={`serviceInfo.newAdditon.typeMemorialTablet[${index}].quantity` as any}
                type={InputEnum.THOUSAND_NUMBER}
              />
            </div>

            <div className='flex justify-end'>
              <CustomIconButton icon={<Image src={ICONS.X} alt='delete' />} onClick={() => remove(index)} />
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};
