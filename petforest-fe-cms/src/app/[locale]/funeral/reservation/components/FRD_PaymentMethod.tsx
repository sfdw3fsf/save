import CustomFormInput from '@/components/common/form/CustomFormField';
import { ReservationDetailDTO } from '@/store/funeral/reservation/dto/reservation.dto';
import { InputEnum } from '@/utils/enums/input.enum'; // Ensure correct path
import { useEffect, useMemo } from 'react';
import { useFormContext, useWatch } from 'react-hook-form';
import { useTranslation } from 'react-i18next';
import { FRD_TitleSectionFormTitle } from './FRD_TitleSectionFormTitle';

export const FRD_PaymentMethod = () => {
  const { t: tReservation } = useTranslation('funeral_reservation');
  const { control, setValue } = useFormContext<ReservationDetailDTO>();

  const paymentMethod = useWatch({
    control,
    name: 'paymentMethod',
  });

  const additionalCost = useWatch({
    control,
    name: 'additionalCost',
  });

  const totalAmount = useMemo(() => {
    if (!additionalCost || !paymentMethod) return 0;

    const totalAdditionalCost = Object.values(additionalCost).reduce((sum, value) => sum + (value || 0), 0);
    return (
      totalAdditionalCost +
      (paymentMethod.baseAmount ?? 0) +
      (paymentMethod.itemAmount ?? 0) +
      (paymentMethod.additionalDiscountAmount ?? 0)
    );
  }, [additionalCost, paymentMethod]);

  useEffect(() => {
    setValue('paymentMethod.totalAmount', totalAmount);
  }, [totalAmount, setValue]);

  return (
    <div>
      <FRD_TitleSectionFormTitle title={tReservation('detail.title.payment_method')} />
      <div className='flex gap-4 flex-col mb-4 mt-3'>
        <div className='grid grid-cols-2 gap-y-4 gap-x-[10px] lg:gap-x-6'>
          <CustomFormInput
            label={tReservation('detail.payment_method.base_amount')}
            control={control}
            name={'paymentMethod.baseAmount'}
            type={InputEnum.THOUSAND_NUMBER}
            placeholder='0'
            allowNegative={true}
            disabled
            className='disabled-input-show'
          />
          <CustomFormInput
            label={tReservation('detail.payment_method.item_amount')}
            control={control}
            name={'paymentMethod.itemAmount'}
            type={InputEnum.THOUSAND_NUMBER}
            placeholder='0'
            allowNegative={true}
            disabled
            isLimitNumber={false}
            className='disabled-input-show'
          />
          <CustomFormInput
            label={tReservation('detail.payment_method.additional_discount_reason')}
            control={control}
            name={'paymentMethod.additionalDiscountReason'}
            type={InputEnum.TEXT}
            placeholder=''
            allowNegative={true}
          />
          <CustomFormInput
            label={tReservation('detail.payment_method.additional_discount_amount')}
            control={control}
            name={'paymentMethod.additionalDiscountAmount'}
            type={InputEnum.THOUSAND_NUMBER}
            allowNegative={true}
            placeholder={tReservation('detail.number_input')}
            isFocus={false}
          />
          <CustomFormInput
            label={tReservation('detail.payment_method.coupon')}
            control={control}
            name={'paymentMethod.coupon'}
            type={InputEnum.THOUSAND_NUMBER}
            placeholder='0'
            disabled
            allowNegative={true}
            className='disabled-input-show'
          />
          <CustomFormInput
            label={tReservation('detail.payment_method.total_amount')}
            control={control}
            name={'paymentMethod.totalAmount'}
            type={InputEnum.THOUSAND_NUMBER}
            placeholder='0'
            allowNegative={true}
            classNameLabel='!text-error-600'
            disabled
            isLimitNumber={false}
            className='disabled-input-show'
          />
        </div>
      </div>
    </div>
  );
};
