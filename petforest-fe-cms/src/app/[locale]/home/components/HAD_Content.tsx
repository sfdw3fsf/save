import CustomIconButton from '@/components/common/CustomIconButton';
import CustomFormInput from '@/components/common/form/CustomFormField';
import CustomText from '@/components/common/Text';
import { useReservation } from '@/hook/funeral/useReservation';
import { funeralReservationHomeKeys, getReservationListApi } from '@/store/funeral/reservation/reservation.api';
import { reservationRespToDto } from '@/store/funeral/reservation/reservation.service';
import { AnnouncementUpdateInput } from '@/store/home/input/home.input';
import { reservationToOptions } from '@/store/manager/reservation-time/manager-reservation-time.service';
import { ICONS } from '@/theme';
import { ANNOUNCEMENT } from '@/utils/enums/home/home.enums';
import { helperOption } from '@/utils/helpers/common.helper';
import { FieldContent } from '@/utils/types/home.type';
import { PlusCircleOutlined } from '@ant-design/icons';
import { keepPreviousData, useQuery } from '@tanstack/react-query';
import dayjs from 'dayjs';
import { isEmpty, isNil } from 'lodash';
import Image from 'next/image';
import { useCallback, useMemo } from 'react';
import { useFieldArray, useFormContext } from 'react-hook-form';
import { useTranslation } from 'react-i18next';

type HAD_ContentProps = {
  field: FieldContent;
};

const intialDetailValue = {
  [ANNOUNCEMENT.FUNERAL]: [
    {
      detail: '',
      funeralNumber: '',
    },
  ],
  [ANNOUNCEMENT.NOT_ACCOMPANY]: [
    {
      detail: '',
      funeralNumber: '',
    },
  ],
  [ANNOUNCEMENT.ETC]: [
    {
      detail: '',
      date: [dayjs(), dayjs()],
    },
  ],
};

export const HAD_Content = ({ field }: HAD_ContentProps) => {
  const { t: tHome } = useTranslation('home');
  const { t: tEnum } = useTranslation('enum');
  const { t: tCommon } = useTranslation('common');
  const { control } = useFormContext<AnnouncementUpdateInput>();
  const { reservationUnaccompanyOptions } = useReservation();

  const { data: reservationListData } = useQuery({
    queryKey: funeralReservationHomeKeys.lists(),
    queryFn: () => getReservationListApi(),
    select: (response) => {
      return { ...response.data, data: response.data.data.map(reservationRespToDto) };
    },
    placeholderData: keepPreviousData,
  });

  const reservations = useMemo(() => {
    if (!reservationListData) return [];
    return reservationToOptions(reservationListData.data);
  }, [reservationListData]);

  const { fields, append, remove } = useFieldArray({
    control,
    name: `${field.label}` as any,
  });

  const reservationOptions = useCallback(
    (name: 'reservations' | 'unaccompany') => {
      const options = {
        ['reservations']: reservations,
        ['unaccompany']: reservationUnaccompanyOptions,
      };

      return options[name];
    },
    [reservations, reservationUnaccompanyOptions],
  );

  const onAddContent = () => {
    append(intialDetailValue[field.type]);
  };

  const handleOption = (value: any) => {
    if (!isEmpty(value) && !isNil(value)) return helperOption('status', value, tEnum);
    return undefined;
  };

  return (
    <div>
      <div className='flex justify-between items-center mb-2'>
        <CustomText variant='label-1-normal' className='!font-semibold'>
          {tHome(`detail.${field.label}` as any)}
        </CustomText>
        <CustomIconButton icon={<PlusCircleOutlined className='!text-primary-1000 !text-[22px]' />} onClick={onAddContent} />
      </div>
      <div className={`!bg-neutral-15 h-[44px] grid py-3 ${field.containerClassName}`}>
        {field.header.map((headerItem, index) => (
          <div key={index} className={`${field.inputEnums[index].className}`}>
            <CustomText variant='label-1-normal' className={index < field.header.length - 1 ? 'md:pl-6' : 'pl-6'}>
              {tHome(`detail.header.${headerItem}` as any)}
            </CustomText>
          </div>
        ))}
        <CustomText variant='label-1-normal' className='text-center'>
          {tCommon('button.delete')}
        </CustomText>
      </div>
      {fields.map((fieldItem, index) => (
        <div key={fieldItem.id} className={`grid border-b border-neutral-30 py-2 ${field.containerClassName}`}>
          {field.inputEnums.map((inputEnum, inputIndex) => (
            <div key={inputIndex} className={`pl-2 ${inputEnum.className}`}>
              <CustomFormInput
                control={control}
                placeholder=''
                name={`${field.label}.${index}.${inputEnum.name}` as any}
                type={inputEnum.type}
                optionItems={
                  field.label !== 'others' && !inputEnum.options
                    ? reservationOptions(field.label as any)
                    : handleOption(inputEnum?.options)
                }
                allowClear={false}
              />
            </div>
          ))}
          <div className='m-auto'>
            <CustomIconButton icon={<Image src={ICONS.X} alt='delete' />} onClick={() => remove(index)} />
          </div>
        </div>
      ))}
    </div>
  );
};
