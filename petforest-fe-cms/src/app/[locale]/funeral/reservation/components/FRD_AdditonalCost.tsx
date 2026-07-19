import CustomFormInput from '@/components/common/form/CustomFormField';
import { ReservationDetailDTO } from '@/store/funeral/reservation/dto/reservation.dto';
import { InputEnum } from '@/utils/enums/input.enum';
import { useFormContext } from 'react-hook-form';
import { useTranslation } from 'react-i18next';
import { FRD_TitleSectionFormTitle } from './FRD_TitleSectionFormTitle';

export const FRD_AdditonalCost = () => {
  const { control } = useFormContext<ReservationDetailDTO>();
  const { t: tReservation } = useTranslation('funeral_reservation');

  return (
    <div>
      <FRD_TitleSectionFormTitle title={tReservation('detail.title.additional_cost')} />
      <div className='flex gap-4 flex-col'>
        <div className='grid grid-cols-2 lg:grid-cols-3 gap-y-4 gap-x-[10px] lg:gap-x-6 my-4'>
          <CustomFormInput
            label={tReservation('detail.additional_cost.transport_service')}
            control={control}
            allowNegative={true}
            name={'additionalCost.transportService'}
            type={InputEnum.THOUSAND_NUMBER}
            placeholder={tReservation('detail.number_input')}
          />
          <CustomFormInput
            label={tReservation('detail.additional_cost.overweight')}
            control={control}
            allowNegative={true}
            name={'additionalCost.overweight'}
            type={InputEnum.THOUSAND_NUMBER}
            placeholder={tReservation('detail.number_input')}
          />
          <CustomFormInput
            label={tReservation('detail.additional_cost.columbarium')}
            control={control}
            allowNegative={true}
            name={'additionalCost.columbarium'}
            type={InputEnum.THOUSAND_NUMBER}
            placeholder={tReservation('detail.number_input')}
          />
          <CustomFormInput
            label={tReservation('detail.additional_cost.temporary_storage')}
            control={control}
            allowNegative={true}
            name={'additionalCost.temporaryStorage'}
            type={InputEnum.THOUSAND_NUMBER}
            placeholder={tReservation('detail.number_input')}
          />
          <CustomFormInput
            label={tReservation('detail.additional_cost.carbon_plate')}
            control={control}
            allowNegative={true}
            name={'additionalCost.carbonPlate'}
            type={InputEnum.THOUSAND_NUMBER}
            placeholder={tReservation('detail.number_input')}
          />
          <CustomFormInput
            label={tReservation('detail.additional_cost.miscellaneous')}
            control={control}
            allowNegative={true}
            name={'additionalCost.miscellaneous'}
            type={InputEnum.THOUSAND_NUMBER}
            placeholder={tReservation('detail.number_input')}
          />
        </div>
      </div>
    </div>
  );
};
