'use client';
import AddButton from '@/components/common/AddButton';
import PageTitle from '@/components/common/page/PageTitle';
import { useMenuPermission } from '@/hook/auth/useMenuPermission';
import useRouterHandler from '@/hook/useRouterHandler';
import { createSettingCorrespondentApi, settingCorrespondentKeys } from '@/store/setting/correspondent/setting-correspondent.api';
import {
  createSettingHospitalSettlementMethodApi,
  settingHospitalSettlementMethodKeys,
} from '@/store/setting/hospital-settlement-method/setting-hospital-settlement-method.api';
import { createPartnerApi, settingPartnerKeys } from '@/store/setting/partner/setting-partner.api';
import { createSettingProgressApi, settingProgressKeys } from '@/store/setting/progress/setting-progress.api';
import { createSettingServiceApi, settingServiceKeys } from '@/store/setting/service/setting-service.api';
import { createSettingVarietiesApi, settingVarietiesKeys } from '@/store/setting/varieties/setting-varieties.api';
import { createWaitingRoomApi, settingWaitingRoomKeys } from '@/store/setting/waiting-room/setting-waiting-room.api';
import { SETTING_TABS } from '@/utils/enums/setting/setting-mngt.enums';
import { MenuKeys } from '@/utils/enums/side-menu-type';
import { handleError } from '@/utils/helpers/notification-error.helper';
import { handleSuccess } from '@/utils/helpers/notification-success.helper';
import { useMutation, useQueryClient } from '@tanstack/react-query';
import { Tabs, TabsProps } from 'antd';
import { useSearchParams } from 'next/navigation';
import { useState } from 'react';
import { useTranslation } from 'react-i18next';
import { CorrespondentList } from './Tabs/CorrespondentList';
import { HospitalSettleMethodList } from './Tabs/HospitalSettleMethodList';
import { PartnerList } from './Tabs/PartnerList';
import { ProgressList } from './Tabs/ProgressList';
import { ServiceList } from './Tabs/ServiceList';
import { VarietiesList } from './Tabs/VarietiesList';
import { WaitingRoomList } from './Tabs/WaitingRoomList';

export default function Home() {
  const { t: tSetting } = useTranslation('setting');
  const { handleURLPush } = useRouterHandler();
  const searchParams = useSearchParams();
  const queryClient = useQueryClient();
  const { canEdit } = useMenuPermission(MenuKeys.SETTING);

  const [tab, setTab] = useState<SETTING_TABS>((searchParams.get('tab') as SETTING_TABS) || SETTING_TABS.SERVICE);

  const settingTabs: TabsProps['items'] = [
    {
      key: SETTING_TABS.SERVICE,
      label: tSetting('tabs.service'),
      children: <ServiceList />,
    },
    {
      key: SETTING_TABS.VARIETIES,
      label: tSetting('tabs.breed'),
      children: <VarietiesList />,
    },
    {
      key: SETTING_TABS.CORRESPONDENT,
      label: tSetting('tabs.correspondent'),
      children: <CorrespondentList />,
    },
    {
      key: SETTING_TABS.PROGRESS,
      label: tSetting('tabs.progress'),
      children: <ProgressList />,
    },
    {
      key: SETTING_TABS.WAITING_ROOM,
      label: tSetting('tabs.waiting_room'),
      children: <WaitingRoomList />,
    },
    {
      key: SETTING_TABS.PARTNER,
      label: tSetting('tabs.partner_company'),
      children: <PartnerList />,
    },
    {
      key: SETTING_TABS.HOSPITAL_SETTLE_METHOD,
      label: tSetting('tabs.partner_hospital_settlement_method'),
      children: <HospitalSettleMethodList />,
    },
  ];

  const { mutate: mutateCreateSettingVarieties } = useMutation({
    mutationFn: () => createSettingVarietiesApi(),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: settingVarietiesKeys.lists() });
      handleSuccess('create');
    },
    onError: handleError,
  });

  const { mutate: mutateCreateSettingProgress } = useMutation({
    mutationFn: () => createSettingProgressApi(),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: settingProgressKeys.lists() });
      handleSuccess('create');
    },
    onError: handleError,
  });

  const { mutate: mutateCreateSettingService } = useMutation({
    mutationFn: () => createSettingServiceApi(),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: settingServiceKeys.lists() });
    },
    onError: handleError,
  });

  const { mutate: mutateCreateSettingWaitingRoom } = useMutation({
    mutationFn: () => createWaitingRoomApi(),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: settingWaitingRoomKeys.lists() });
      handleSuccess('create');
    },
    onError: handleError,
  });

  const { mutate: mutateCreateSettingHospitalSettlementMethod } = useMutation({
    mutationFn: () => createSettingHospitalSettlementMethodApi(),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: settingHospitalSettlementMethodKeys.lists() });
      handleSuccess('create');
    },
    onError: handleError,
  });

  const { mutate: mutateCreateSettingPartner } = useMutation({
    mutationFn: () => createPartnerApi(),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: settingPartnerKeys.lists() });
      handleSuccess('create');
    },
    onError: handleError,
  });

  const { mutate: mutateCreateSettingCorrespondent } = useMutation({
    mutationFn: () => createSettingCorrespondentApi(),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: settingCorrespondentKeys.lists() });
      handleSuccess('create');
    },
    onError: handleError,
  });

  const onCreate = () => {
    switch (tab) {
      case SETTING_TABS.VARIETIES:
        mutateCreateSettingVarieties();
        break;
      case SETTING_TABS.SERVICE:
        mutateCreateSettingService();
        break;
      case SETTING_TABS.CORRESPONDENT:
        mutateCreateSettingCorrespondent();
        break;
      case SETTING_TABS.WAITING_ROOM:
        mutateCreateSettingWaitingRoom();
        break;
      case SETTING_TABS.PROGRESS:
        mutateCreateSettingProgress();
        break;
      case SETTING_TABS.HOSPITAL_SETTLE_METHOD:
        mutateCreateSettingHospitalSettlementMethod();
        break;
      case SETTING_TABS.PARTNER:
        mutateCreateSettingPartner();
        break;
    }
  };

  const handleChangeTab = (value: SETTING_TABS) => {
    setTab(value);
    handleURLPush('tab', value, false);
  };

  return (
    <div>
      <PageTitle title={tSetting('settings')} actions={canEdit && <AddButton onClick={onCreate} />} />

      <div className='mt-4'>
        <Tabs
          destroyInactiveTabPane
          items={settingTabs}
          activeKey={tab}
          defaultActiveKey={SETTING_TABS.SERVICE}
          onChange={(value) => handleChangeTab(value as SETTING_TABS)}
        />
      </div>
    </div>
  );
}
