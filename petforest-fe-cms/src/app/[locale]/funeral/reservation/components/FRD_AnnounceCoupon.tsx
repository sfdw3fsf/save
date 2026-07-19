import CopyButton from '@/components/common/CopyButton';
import CustomFormInput from '@/components/common/form/CustomFormField';
import CustomText from '@/components/common/Text';
import { ReservationDetailDTO } from '@/store/funeral/reservation/dto/reservation.dto';
import { InputEnum } from '@/utils/enums/input.enum';
import { handleSuccess } from '@/utils/helpers/notification-success.helper';
import { useFormContext } from 'react-hook-form';
import { useTranslation } from 'react-i18next';
import { FRD_TitleSectionFormTitle } from './FRD_TitleSectionFormTitle';

export const FRD_AnnounceCoupon = () => {
  const { t: tReservation } = useTranslation('funeral_reservation');
  const { control } = useFormContext<ReservationDetailDTO>();
  return (
    <div>
      <FRD_TitleSectionFormTitle title={tReservation('detail.title.announcements_and_coupons')} />
      <div className='flex gap-4 flex-col mt-3'>
        <CustomFormInput
          label={tReservation('detail.announcements_and_coupons.announcement_web_link')}
          control={control}
          type={InputEnum.OTHER}
          name={'announcementsCouponInfo.announcement_web_link'}
          renderInput={({ value }: { value: any }) => (
            <div className='flex gap-4 justify-between whitespace-nowrap items-center'>
              <CustomText ellipsis={true} variant='label-1-normal'>
                {value}
              </CustomText>
              <CopyButton
                className='!min-w-[90px]'
                onClick={() => {
                  if (value) {
                    navigator.clipboard.writeText(value).then(() => {
                      handleSuccess('copy');
                    });
                  }
                }}
              />
            </div>
          )}
        />
        <CustomFormInput
          label={tReservation('detail.announcements_and_coupons.coupon_history')}
          type={InputEnum.OTHER}
          control={control}
          name={'announcementsCouponInfo.coupon_history'}
          renderInput={({ value }: { value: any }) =>
            value === null ? (
              <CustomText variant='label-1-normal' className='!text-neutral-70 mt-[9px]'>
                {tReservation('detail.announcements_and_coupons.no_coupon_history')}
              </CustomText>
            ) : (
              <>
                <div className='grid grid-cols-5 !bg-neutral-15 py-4 border-b'>
                  <div className='col-span-1'>
                    <CustomText variant='label-1-normal' className='!font-semibold pl-6 whitespace-nowrap'>
                      {tReservation('detail.announcements_and_coupons.payment_amount')}
                    </CustomText>
                  </div>
                  <div className='col-span-4'>
                    <CustomText variant='label-1-normal' className='!font-semibold pl-6'>
                      {tReservation('detail.announcements_and_coupons.coupon_contact_sender')}
                    </CustomText>
                  </div>
                </div>
                {value.map((row: any) => (
                  <div className='grid grid-cols-5 h-[48px] border-b' key={row.couponContactSender}>
                    <div className='flex items-center'>
                      <CustomText variant='label-1-normal' className='pl-6 !text-neutral-80'>
                        {row.paymentAmount}
                      </CustomText>
                    </div>
                    <div className='flex items-center col-span-4 '>
                      <CustomText variant='label-1-normal' className='pl-6 !text-neutral-80'>
                        {row.couponContactSender}
                      </CustomText>
                    </div>
                  </div>
                ))}
              </>
            )
          }
        />
      </div>
    </div>
  );
};
