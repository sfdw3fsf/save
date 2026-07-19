import CustomDatePickingCalendar from '@/components/common/CustomDatePickingCalendar';
import CustomFormInput from '@/components/common/form/CustomFormField';
import CustomText from '@/components/common/Text';
import { getHomeAnnouncement, homeAnnouncementKeys } from '@/store/home/home.api';
import { announcementRespToInput } from '@/store/home/home.service';
import { AnnouncementUpdateInput } from '@/store/home/input/home.input';
import { DATE_FORMAT } from '@/utils/constants/date.constant';
import { FIELD_CONTENT } from '@/utils/constants/home/home.constants';
import { InputEnum } from '@/utils/enums/input.enum';
import { FieldContent } from '@/utils/types/home.type';
import { keepPreviousData, useQuery } from '@tanstack/react-query';
import { Divider } from 'antd';
import { Dayjs } from 'dayjs';
import { useEffect } from 'react';
import { useFormContext } from 'react-hook-form';
import { useTranslation } from 'react-i18next';
import { HAD_Content } from './HAD_Content';

export const HA_DetailModal = () => {
  const { t: tHome } = useTranslation('home');
  const { control, watch, reset } = useFormContext<AnnouncementUpdateInput>();

  const dateWatch = watch('date');

  const { data: announcementData } = useQuery({
    queryKey: homeAnnouncementKeys.list(dateWatch),
    queryFn: () => getHomeAnnouncement({ date: dateWatch.startOf('day').valueOf() }),
    select: (response) => {
      return response.data.data && announcementRespToInput(response.data.data, dateWatch);
    },
    placeholderData: keepPreviousData,
  });

  useEffect(() => {
    if (!announcementData) return;
    const currentValues = control._formValues;
    if (JSON.stringify(currentValues) !== JSON.stringify(announcementData)) {
      reset(announcementData);
    }
  }, [announcementData]);

  return (
    <div className='mt-2 mb-4'>
      <CustomFormInput<AnnouncementUpdateInput>
        label={tHome('detail.notice_content')}
        control={control}
        name='content'
        type={InputEnum.TEXT_AREA}
      />
      <Divider className='border-[5px] !text-neutral-20' />
      <div className='flex gap-4 flex-col'>
        <CustomFormInput<AnnouncementUpdateInput>
          label={tHome('detail.schedule_date_selection')}
          control={control}
          className='!gap-4'
          name='date'
          type={InputEnum.OTHER}
          renderInput={({ value, onChange }) => (
            <CustomDatePickingCalendar selectedDate={value} onSelectDate={(value: Dayjs) => onChange(value)} />
          )}
        />
        <CustomFormInput<AnnouncementUpdateInput>
          label={tHome('detail.date')}
          control={control}
          name='date'
          type={InputEnum.OTHER}
          renderInput={({ value }) => (
            <CustomText variant='label-1-normal'>{value?.format(DATE_FORMAT.DATE.HYPHEN) || ''}</CustomText>
          )}
        />

        {FIELD_CONTENT.map((item: FieldContent) => (
          <HAD_Content key={item.label} field={item} />
        ))}
      </div>
    </div>
  );
};
