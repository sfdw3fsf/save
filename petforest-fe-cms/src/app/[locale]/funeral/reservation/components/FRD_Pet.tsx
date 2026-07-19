import CustomIconButton from '@/components/common/CustomIconButton';
import CustomFormInput from '@/components/common/form/CustomFormField';
import CustomText from '@/components/common/Text';
import { useSettingVarieties } from '@/hook/setting/useSettingVarieties';
import { ReservationDetailDTO } from '@/store/funeral/reservation/dto/reservation.dto';
import { COLORS } from '@/theme';
import { DETAIL_PET_GENDER_OPTIONS, DETAIL_PET_REGISTATION_OPTIONS } from '@/utils/enums/funeral/reservation-mngt.enums';
import { InputEnum } from '@/utils/enums/input.enum';
import { helperOption } from '@/utils/helpers/common.helper';
import { InfoCircleOutlined, PaperClipOutlined } from '@ant-design/icons';

import CustomSelect from '@/components/common/CustomSelect';
import { useFormContext, useWatch } from 'react-hook-form';
import { useTranslation } from 'react-i18next';
import { FRD_TitleSectionFormTitle } from './FRD_TitleSectionFormTitle';

export const FRD_Pet = () => {
  const { control, setValue } = useFormContext<ReservationDetailDTO>();
  const petTypeId = useWatch<ReservationDetailDTO>({
    control,
    name: 'petInfo.type',
  });
  const { t: tEnum } = useTranslation('enum');
  const { t: tReservation } = useTranslation('funeral_reservation');
  const { petTypeOptions, petBreedOptions } = useSettingVarieties({ varietyId: petTypeId });

  const REGISTATION_OPTIONS = helperOption('reservation.registration', DETAIL_PET_REGISTATION_OPTIONS, tEnum);
  const GENDER_OPTIONS = helperOption('reservation.gender', DETAIL_PET_GENDER_OPTIONS, tEnum);

  return (
    <div>
      <FRD_TitleSectionFormTitle title={tReservation('detail.title.pet')} />

      <div className='flex gap-4 flex-col'>
        <div className='grid grid-cols-2 gap-y-4 gap-x-[10px] lg:gap-x-6 mt-3'>
          <CustomFormInput<ReservationDetailDTO>
            control={control}
            name={'petInfo.registration'}
            type={InputEnum.SELECT_AND_INPUT}
            optionItems={REGISTATION_OPTIONS}
            placeholder={tReservation('detail.registration.placeholder')}
            label={tReservation('detail.registration.title')}
          />
          <CustomFormInput<ReservationDetailDTO>
            control={control}
            name={'petInfo.petName'}
            type={InputEnum.TEXT}
            label={tReservation('detail.pet_name')}
          />

          <CustomFormInput<ReservationDetailDTO>
            control={control}
            name={'petInfo.type'}
            type={InputEnum.OTHER}
            label={tReservation('detail.type.title')}
            optionItems={petTypeOptions}
            renderInput={({ value, onChange }: { value: number; onChange: (value: number) => void }) => (
              <CustomSelect
                value={value}
                options={petTypeOptions}
                onChange={(selectedValue) => {
                  onChange(selectedValue);
                  setValue('petInfo.breed', null);
                }}
              />
            )}
          />

          <CustomFormInput<ReservationDetailDTO>
            control={control}
            name={'petInfo.breed'}
            type={InputEnum.SELECT}
            optionItems={petBreedOptions}
            placeholder={tReservation('detail.breed.placeholder')}
            label={tReservation('detail.breed.title')}
          />
        </div>
        <div className='grid grid-cols-3 gap-y-4 gap-x-[10px] lg:gap-x-6'>
          <CustomFormInput<ReservationDetailDTO>
            control={control}
            name={'petInfo.weight'}
            type={InputEnum.THOUSAND_NUMBER}
            label={tReservation('detail.weight')}
            suffix={
              <CustomText variant='label-1-normal' className='!text-neutral-70'>
                kg
              </CustomText>
            }
          />
          <CustomFormInput<ReservationDetailDTO>
            control={control}
            name={'petInfo.gender'}
            type={InputEnum.SELECT}
            label={tReservation('detail.gender.title')}
            optionItems={GENDER_OPTIONS}
          />
          <CustomFormInput<ReservationDetailDTO>
            control={control}
            name={'petInfo.age'}
            type={InputEnum.THOUSAND_NUMBER}
            label={tReservation('detail.age')}
          />
        </div>
        <div className='grid grid-cols-2 gap-y-4 gap-x-[10px] lg:gap-x-6 mt-3'>
          <CustomFormInput<ReservationDetailDTO>
            control={control}
            name={'petInfo.dateBirth'}
            type={InputEnum.DATE_PICKER}
            allowClear={false}
            label={tReservation('detail.date_birth')}
            placeholder={tReservation('detail.placeholder')}
          />

          <CustomFormInput<ReservationDetailDTO>
            control={control}
            name={'petInfo.dateDeath'}
            type={InputEnum.DATE_PICKER}
            allowClear={false}
            label={tReservation('detail.date_death')}
            placeholder={tReservation('detail.placeholder')}
          />
        </div>
        <CustomFormInput<ReservationDetailDTO>
          control={control}
          buttonUpload={<CustomIconButton icon={<PaperClipOutlined className='!text-neutral-70' />} />}
          name={'reservationPhoto.attachments'}
          placeholder={tReservation('detail.photo.placeholder')}
          type={InputEnum.IMAGE}
          bordered={true}
          multiple={true}
          fileType='image'
          label={tReservation('detail.photo.title')}
          classNameLabel='flex gap-1 items-center'
          displayName={false}
          extraTitle={
            <>
              <InfoCircleOutlined style={{ color: COLORS.grey['grey-mist'] }} />
              <CustomText variant='caption-1' className='!text-grey-grey-light'>
                {tReservation('detail.photo.sub_title')}
              </CustomText>
            </>
          }
        />
      </div>
    </div>
  );
};
