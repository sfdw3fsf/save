import CustomIconButton from '@/components/common/CustomIconButton';
import CustomFormInput from '@/components/common/form/CustomFormField';
import CustomText from '@/components/common/Text';
import { ReservationDetailDTO } from '@/store/funeral/reservation/dto/reservation.dto';
import { ICONS } from '@/theme';
import { DETAIL_PAYMENT_METHODS_OPTIONS } from '@/utils/enums/funeral/reservation-mngt.enums';
import { InputEnum } from '@/utils/enums/input.enum';
import { helperOption } from '@/utils/helpers/common.helper';
import { PlusCircleOutlined } from '@ant-design/icons';
import { useQueryClient } from '@tanstack/react-query';
import { Divider } from 'antd';
import dayjs from 'dayjs';
import Image from 'next/image';
import { useFieldArray, useFormContext } from 'react-hook-form';
import { useTranslation } from 'react-i18next';

const FRD_SettlementDetail = () => {
  const { t: tReservation } = useTranslation('funeral_reservation');
  const { t: tEnum } = useTranslation('enum');
  const queryClient = useQueryClient();
  const { control, watch, setValue } = useFormContext<ReservationDetailDTO>();
  const { fields, prepend, remove } = useFieldArray({
    control,
    name: 'settlementDetail',
  });

  const onAddSupply = () => {
    prepend({
      id: undefined,
      date: dayjs().valueOf(),
      settlementMethod: null,
      amount: 0,
      note: '',
    });
  };

  const PAYMENT_METHOD_OPTIONS = helperOption('reservation.payment_method', DETAIL_PAYMENT_METHODS_OPTIONS, tEnum);

  return (
    <>
      <div className='flex gap-4 flex-col mt-[32px]'>
        <div>
          <div className='grid grid-cols-12 !bg-neutral-15 py-[3px] items-center'>
            <CustomText variant='label-1-normal' className='col-span-10 !font-semibold pl-4 block md:hidden'>
              {tReservation('detail.settlement_detail.select_date')}/{tReservation('detail.settlement_detail.settlement_method')}
            </CustomText>
            <CustomText variant='label-1-normal' className='col-span-2 !font-semibold pl-4 hidden md:block'>
              {tReservation('detail.settlement_detail.select_date')}
            </CustomText>
            <CustomText variant='label-1-normal' className='col-span-2 !font-semibold pl-4 hidden md:block'>
              {tReservation('detail.settlement_detail.settlement_method')}
            </CustomText>
            <CustomText variant='label-1-normal' className='col-span-2 !font-semibold pl-4 hidden md:block'>
              {tReservation('detail.settlement_detail.amount')}
            </CustomText>
            <div className='flex col-span-2 md:col-span-6 items-center'>
              <CustomText variant='label-1-normal' className='!font-semibold pl-4 flex-1 hidden md:block'>
                {tReservation('detail.settlement_detail.remarks')}
              </CustomText>
              <div className='col-span-1 flex justify-end pr-2'>
                <CustomIconButton
                  icon={<PlusCircleOutlined className='!text-primary-1000' style={{ fontSize: '18px' }} />}
                  onClick={onAddSupply}
                />
              </div>
            </div>
          </div>
          {fields.map((field, index) => {
            return (
              <div key={field.id} className='grid grid-cols-12 gap-4 gap-x-[10px] md:gap-x-4 p-2'>
                <div className='col-span-6 md:col-span-2'>
                  <CustomFormInput
                    control={control}
                    name={`settlementDetail[${index}].date` as any}
                    type={InputEnum.DATE_PICKER}
                  />
                </div>
                <div className='col-span-6 md:col-span-2'>
                  <CustomFormInput
                    control={control}
                    name={`settlementDetail[${index}].settlementMethod` as any}
                    type={InputEnum.SELECT}
                    optionItems={PAYMENT_METHOD_OPTIONS}
                    popupMatchSelectWidth={false}
                    placeholder={tReservation('detail.placeholder')}
                  />
                </div>
                <div className='col-span-12 md:col-span-2 flex items-center gap-[10px]'>
                  <CustomText variant='label-1-normal' className='!text-neutral-80 block md:hidden'>
                    {tReservation('detail.settlement_detail.amount')}
                  </CustomText>
                  <CustomFormInput
                    control={control}
                    name={`settlementDetail[${index}].amount` as any}
                    type={InputEnum.THOUSAND_NUMBER}
                    className='flex-1'
                  />
                </div>
                <div className='flex col-span-12 md:col-span-6 gap-[10px] items-center'>
                  <CustomText variant='label-1-normal' className='!text-neutral-80 block md:hidden'>
                    {tReservation('detail.settlement_detail.remarks')}
                  </CustomText>
                  <CustomFormInput
                    className='flex-1'
                    control={control}
                    name={`settlementDetail[${index}].note` as any}
                    type={InputEnum.TEXT}
                  />

                  <CustomIconButton icon={<Image src={ICONS.X} alt='delete' />} onClick={() => remove(index)} />
                </div>
                <Divider className='my-0 md:hidden col-span-12' />
              </div>
            );
          })}
        </div>
      </div>
    </>
  );
};

export default FRD_SettlementDetail;
