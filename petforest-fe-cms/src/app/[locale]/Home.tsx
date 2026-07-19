'use client';

import CustomButton from '@/components/common/CustomButton';
import { CustomParagraph } from '@/components/common/CustomParagraph';
import CustomModal from '@/components/common/modals/CustomModal';
import PageTitle from '@/components/common/page/PageTitle';
import {
  getHomeAnnouncementScheduleApi,
  homeAnnouncementKeys,
  homeAnnouncementScheduleKeys,
  updateHomeAnnouncementApi,
} from '@/store/home/home.api';
import { announcementInputToReq, announcementScheduleInputToReq, announcementScheduleRespToDto } from '@/store/home/home.service';
import { AnnouncementScheduleInput, AnnouncementUpdateInput } from '@/store/home/input/home.input';
import { AnnouncementUpdateREQ } from '@/store/home/request/home.request';
import { handleError } from '@/utils/helpers/notification-error.helper';
import { handleSuccess } from '@/utils/helpers/notification-success.helper';
import { EditOutlined } from '@ant-design/icons';
import { keepPreviousData, useMutation, useQuery, useQueryClient } from '@tanstack/react-query';
import { Card } from 'antd';
import dayjs, { Dayjs } from 'dayjs';
import { useState } from 'react';
import { FormProvider, useForm } from 'react-hook-form';
import { useTranslation } from 'react-i18next';
import { HA_CollapseTable } from './home/components/HA_CollapseTable';
import { HA_DetailModal } from './home/components/HA_DetailModal';
import HA_Schedule from './home/components/HA_Schedule';

const initialValues: AnnouncementUpdateInput = {
  content: '',
  date: dayjs().startOf('day'),
};

const Home = () => {
  const { t: tHome } = useTranslation('home');

  const queryClient = useQueryClient();

  const [isOpenDetail, setIsOpenDetail] = useState<boolean>(false);
  const [selectedMonthReq, setSelectedMonthReq] = useState<AnnouncementScheduleInput>({
    startDate: dayjs().startOf('month'),
    endDate: dayjs().endOf('month'),
  });
  const [selectedMonth, setSelectedMonth] = useState<AnnouncementScheduleInput>({
    startDate: dayjs().startOf('month'),
    endDate: dayjs().endOf('month'),
  });

  const methods = useForm<AnnouncementUpdateInput>({ defaultValues: initialValues });
  const { handleSubmit } = methods;

  const { mutate: mutateUpdateAnnouncement, isPending: isUpdating } = useMutation({
    mutationFn: ({ id, body }: { id: number; body: AnnouncementUpdateREQ }) => updateHomeAnnouncementApi(id, body),
    onSuccess: () => {
      handleSuccess('update');
      queryClient.invalidateQueries({ queryKey: homeAnnouncementKeys.lists() });
      queryClient.invalidateQueries({ queryKey: homeAnnouncementScheduleKeys.lists() });
    },
    onError: handleError,
  });

  const { data: announcementScheduleData } = useQuery({
    queryKey: homeAnnouncementScheduleKeys.list(selectedMonthReq),
    queryFn: () => getHomeAnnouncementScheduleApi(announcementScheduleInputToReq(selectedMonthReq)),
    select: (response) => {
      return response.data.data && announcementScheduleRespToDto(response.data.data, selectedMonth);
    },
    placeholderData: keepPreviousData,
  });

  const onDetail = () => {
    setIsOpenDetail(true);
  };

  const onCloseDetail = () => {
    setIsOpenDetail(false);
  };

  const onSubmit = (data: AnnouncementUpdateInput) => {
    if (data.id) {
      const body: AnnouncementUpdateREQ = announcementInputToReq(data);
      mutateUpdateAnnouncement({ id: data.id, body });
    }
  };

  const onChangeMonth = ({ startDate, endDate }: AnnouncementScheduleInput) => {
    setSelectedMonth({ startDate, endDate });
  };

  const onFilterChange = (calendarDateRange: [Dayjs, Dayjs]) => {
    setSelectedMonthReq({
      startDate: calendarDateRange[0],
      endDate: calendarDateRange[1],
    });
  };

  return (
    <div>
      <PageTitle
        title={tHome('notice')}
        actions={
          <CustomButton type={'secondary'} icon={<EditOutlined />} onClick={onDetail}>
            {tHome('notice_management')}
          </CustomButton>
        }
      />
      <Card className='!bg-primary-50 !border-none !mt-4 md:!px-6 !px-0 !py-2'>
        <CustomParagraph text={announcementScheduleData?.announcement || ''} />
      </Card>

      <HA_CollapseTable />

      <HA_Schedule
        month={selectedMonthReq}
        onChangeMonth={onChangeMonth}
        data={announcementScheduleData?.schedules || []}
        filter={selectedMonthReq}
        onFilterChange={onFilterChange}
      />

      <FormProvider {...methods}>
        <form>
          <CustomModal
            isLoading={isUpdating}
            isOpen={isOpenDetail}
            onOk={handleSubmit(onSubmit)}
            onCancel={onCloseDetail}
            onClose={onCloseDetail}
            title={tHome('detail.title')}>
            <HA_DetailModal />
          </CustomModal>
        </form>
      </FormProvider>
    </div>
  );
};
export default Home;
