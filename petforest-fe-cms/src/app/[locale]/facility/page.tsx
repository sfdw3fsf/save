'use client';
import AddButton from '@/components/common/AddButton';
import PageTitle from '@/components/common/page/PageTitle';
import useRouterHandler from '@/hook/useRouterHandler';
import { createFacilityApi, facilityKeys } from '@/store/facility/facility.api';
import { facilityCreateToReq } from '@/store/facility/facility.service';
import { FACILITY_TABS } from '@/utils/enums/facility/facility.enum';
import { handleError } from '@/utils/helpers/notification-error.helper';
import { handleSuccess } from '@/utils/helpers/notification-success.helper';
import { useMutation, useQueryClient } from '@tanstack/react-query';
import { Tabs, TabsProps } from 'antd';
import { useSearchParams } from 'next/navigation';
import { useMemo, useState } from 'react';
import { useTranslation } from 'react-i18next';
import FacilityList from './tabs/FacilitiesList';
import FacilityHistory from './tabs/FacilityHistory';

const page = () => {
  const { t: tFacility } = useTranslation('facility');
  const queryClient = useQueryClient();

  const { handleURLPush } = useRouterHandler();
  const searchParams = useSearchParams();

  const [tab, setTab] = useState<FACILITY_TABS>((searchParams.get('tab') as FACILITY_TABS) || FACILITY_TABS.HISTORY);
  const [isOpenDetail, setIsOpenDetail] = useState<boolean>(false);

  const funeralSuppliesTabs: TabsProps['items'] = useMemo(() => {
    return [
      {
        key: FACILITY_TABS.HISTORY,
        label: tFacility('tabs.repair'),
        children: <FacilityHistory isOpenDetail={isOpenDetail} setIsOpenDetail={setIsOpenDetail} />,
      },
      {
        key: FACILITY_TABS.LIST,
        label: tFacility('tabs.facilities'),
        children: <FacilityList />,
      },
    ];
  }, [tFacility, isOpenDetail]);

  const { mutate: mutateCreateFacility } = useMutation({
    mutationFn: () => createFacilityApi(facilityCreateToReq()),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: facilityKeys.lists() });
      handleSuccess('create');
    },
    onError: handleError,
  });

  const onCreate = () => {
    switch (tab) {
      case FACILITY_TABS.LIST:
        mutateCreateFacility();
        break;
      case FACILITY_TABS.HISTORY:
        setIsOpenDetail(true);
        break;
    }
  };

  const handleChangeTab = (value: FACILITY_TABS) => {
    setTab(value);
    handleURLPush('tab', value, false);
  };

  return (
    <div>
      <PageTitle title={tFacility('facility')} actions={<AddButton onClick={onCreate} />} />

      <div className='mt-4'>
        <Tabs
          destroyInactiveTabPane
          items={funeralSuppliesTabs}
          defaultActiveKey={FACILITY_TABS.HISTORY}
          activeKey={tab}
          onChange={(value) => handleChangeTab(value as FACILITY_TABS)}
        />
      </div>
    </div>
  );
};

export default page;
