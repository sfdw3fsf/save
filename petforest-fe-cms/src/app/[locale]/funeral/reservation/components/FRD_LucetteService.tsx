import CustomButton from '@/components/common/CustomButton';
import CustomIconButton from '@/components/common/CustomIconButton';
import CustomFormInput from '@/components/common/form/CustomFormField';
import CustomText from '@/components/common/Text';
import { createLucetteMessageApi, lucetteKeys } from '@/store/funeral/lucette/lucette.api';
import { ReservationDetailDTO } from '@/store/funeral/reservation/dto/reservation.dto';
import { ICONS } from '@/theme';
import { DATE_FORMAT } from '@/utils/constants/date.constant';
import { LUCETTE_CATEGORY } from '@/utils/enums/funeral/lucette-mngt.enums';
import { DETAIL_SERVICE_LUCETTE_JEWELRY_OPTIONS } from '@/utils/enums/funeral/reservation-mngt.enums';
import { InputEnum } from '@/utils/enums/input.enum';
import { helperOption } from '@/utils/helpers/common.helper';
import { getToday } from '@/utils/helpers/date-time.helper';
import { handleError } from '@/utils/helpers/notification-error.helper';
import { handleSuccess } from '@/utils/helpers/notification-success.helper';
import { MinusOutlined, PlusOutlined } from '@ant-design/icons';
import { useMutation, useQueryClient } from '@tanstack/react-query';
import { Dayjs } from 'dayjs';
import { isNil } from 'lodash';
import Image from 'next/image';
import { useEffect } from 'react';
import { useFieldArray, useFormContext } from 'react-hook-form';
import { useTranslation } from 'react-i18next';
import { NumericFormat } from 'react-number-format';

export const FRD_LucetteService = () => {
  const { t: tReservation } = useTranslation('funeral_reservation');
  const { t: tEnum } = useTranslation('enum');
  const queryClient = useQueryClient();

  const { control, watch, setValue } = useFormContext<ReservationDetailDTO>();
  const { fields } = useFieldArray({
    control,
    name: 'serviceInfo.lucette.materials',
  });

  const categoryOptions = helperOption('lucette.category', LUCETTE_CATEGORY, tEnum);

  const category = watch('serviceInfo.lucette.category');

  const funeralDate = watch('funeralInfo.funeralDate');
  useEffect(() => {
    if (category === LUCETTE_CATEGORY.ON_THE_DAY) {
      setValue('serviceInfo.lucette.processDate', getToday());
    }
  }, [category, setValue]);

  const { mutate: mutateCreateMessage } = useMutation({
    mutationFn: createLucetteMessageApi,
    onSuccess: () => {
      handleSuccess('create');

      queryClient.invalidateQueries({ queryKey: lucetteKeys.lists() });
    },
    onError: handleError,
  });

  const onCreateMessage = () => {
    mutateCreateMessage([watch('id')]);
  };

  const disabledTime: any = (_: Dayjs | null) => ({
    disabledHours: () => [0, 1, 2, 3, 4, 5, 6, 7, 8, 22, 23],
    disabledMinutes: () => [],
    disabledSeconds: () => [],
  });

  return (
    <>
      <div className='mb-4'>
        <CustomButton
          onClick={() => onCreateMessage()}
          type='ghost'
          icon={<Image src={ICONS.NOTI} alt='noti' />}
          className='!bg-primary-50 !h-[38px]'>
          {tReservation('detail.lucette.send_noti')}
        </CustomButton>
      </div>
      <div className='grid grid-cols-3 gap-y-4 gap-x-[10px] lg:gap-x-6 pb-6'>
        {/* <CustomFormInput
          label={tReservation('detail.lucette.category')}
          control={control}
          name={'serviceInfo.lucette.category'}
          placeholder=''
          type={InputEnum.OTHER}
          renderInput={({ value }: { value: number }) => (
            <CustomTextInput
              disabled
              defaultValue={value ? tEnum(`reservation.lucette_category_status.${value}` as any) : ''}></CustomTextInput>
          )}
        /> */}
        <CustomFormInput
          label={tReservation('detail.lucette.category')}
          control={control}
          name={'serviceInfo.lucette.category'}
          type={InputEnum.SELECT}
          placeholder={tReservation('detail.placeholder')}
          optionItems={categoryOptions}
        />
        <CustomFormInput
          label={tReservation('detail.lucette.process_date')}
          control={control}
          name={'serviceInfo.lucette.processDate'}
          type={InputEnum.DATE_PICKER}
          placeholder={tReservation('detail.placeholder')}
          minNumber={Number(funeralDate)}
        />

        <CustomFormInput
          label={tReservation('detail.lucette.process_time')}
          control={control}
          name={'serviceInfo.lucette.processTime'}
          type={InputEnum.TIME_PICKER}
          bordered={true}
          format={DATE_FORMAT.TIME.PICKER_HOUR_MIN}
          placeholder={tReservation('detail.placeholder')}
          disabledTime={disabledTime}
        />

        {fields.map((field, index) => {
          return (
            <div key={field.id}>
              <CustomFormInput
                label={field.name}
                control={control}
                name={`serviceInfo.lucette.materials[${index}].quantity` as any}
                type={InputEnum.OTHER}
                renderInput={({ value, onChange }: { value: number; onChange: (value: number) => void }) => (
                  <div className='flex'>
                    <CustomIconButton
                      className='!border !border-neutral-40 !w-[28px] !h-[28px]'
                      icon={<MinusOutlined />}
                      onClick={() => onChange(Math.max(value - 1, 0))}
                    />
                    <NumericFormat
                      value={value}
                      thousandSeparator
                      displayType='text'
                      className='min-w-0 px-4 my-auto text-center'
                    />
                    <CustomIconButton
                      className='!border !border-neutral-40 !w-[28px] !h-[28px]'
                      icon={<PlusOutlined />}
                      onClick={() => onChange(value + 1)}
                    />
                  </div>
                )}
              />
            </div>
          );
        })}

        <CustomFormInput
          label={tReservation('detail.lucette.jewelry.title')}
          control={control}
          name={'serviceInfo.lucette.status'}
          type={InputEnum.OTHER}
          renderInput={({ value }: { value: DETAIL_SERVICE_LUCETTE_JEWELRY_OPTIONS }) => (
            <CustomText
              variant='label-1-normal'
              className={`label block w-fit ${!isNil(value) ? 'label-success' : 'label-error'}`}>
              {tReservation(`detail.lucette.jewelry.value.${!isNil(value) ? 'applied' : 'not_applied'}`)}
            </CustomText>
          )}
        />
      </div>
    </>
  );
};
