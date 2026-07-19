'use client';
import AddButton from '@/components/common/AddButton';
import PageTitle from '@/components/common/page/PageTitle';
import { useMenuPermission } from '@/hook/auth/useMenuPermission';
import useRouterHandler from '@/hook/useRouterHandler';
import { createMaterialApi, materialOtherKeys } from '@/store/material/material.api';
import { MATERIAL_TYPE } from '@/utils/enums/material/material.enum';
import { OTHER_FUNERAL_SUPPLIES_TABS } from '@/utils/enums/material/other-funeral-supplies.enum';
import { MenuKeys } from '@/utils/enums/side-menu-type';
import { handleError } from '@/utils/helpers/notification-error.helper';
import { handleSuccess } from '@/utils/helpers/notification-success.helper';
import { useMutation, useQueryClient } from '@tanstack/react-query';
import { Tabs, TabsProps } from 'antd';
import { useSearchParams } from 'next/navigation';
import { useMemo, useState } from 'react';
import { useTranslation } from 'react-i18next';
import MOFS_History from './tabs/MOFS_History';
import MOFS_List from './tabs/MOFS_List';

const page = () => {
  const { t: tMenu } = useTranslation('menu');
  const { t: tOtherSupplies } = useTranslation('material_other_funeral_supplies');
  const { t: tMaterial } = useTranslation('material');

  const queryClient = useQueryClient();
  const searchParams = useSearchParams();
  const { handleURLPush } = useRouterHandler();
  const { canEdit } = useMenuPermission(MenuKeys.MATERIAL_OTHER_FUNERAL_SUPPLIES);

  const [tab, setTab] = useState<OTHER_FUNERAL_SUPPLIES_TABS>(
    (searchParams.get('tab') as OTHER_FUNERAL_SUPPLIES_TABS) || OTHER_FUNERAL_SUPPLIES_TABS.LIST,
  );

  const { mutate: mutateCreateMaterialOther } = useMutation({
    mutationFn: () => createMaterialApi({ materialType: MATERIAL_TYPE.OTHER }),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: materialOtherKeys.lists() });
      handleSuccess('create');
    },
    onError: handleError,
  });

  const otherFuneralTabs: TabsProps['items'] = useMemo(() => {
    return [
      {
        key: OTHER_FUNERAL_SUPPLIES_TABS.LIST,
        label: tOtherSupplies('tabs.list'),
        children: <MOFS_List />,
      },
      {
        key: OTHER_FUNERAL_SUPPLIES_TABS.INVENTORY,
        label: tMaterial('tabs.inventory'),
        children: <MOFS_History />,
      },
    ];
  }, [tOtherSupplies]);

  const onCreateMaterialOther = () => {
    mutateCreateMaterialOther();
  };

  const handleChangeTab = (value: OTHER_FUNERAL_SUPPLIES_TABS) => {
    setTab(value);
    handleURLPush('tab', value, false);
  };

  return (
    <div>
      <PageTitle
        title={tMenu('other-funeral-supplies')}
        actions={tab === OTHER_FUNERAL_SUPPLIES_TABS.LIST && canEdit && <AddButton onClick={onCreateMaterialOther} />}
      />

      <div className='mt-4'>
        <Tabs
          destroyInactiveTabPane
          items={otherFuneralTabs}
          defaultActiveKey={OTHER_FUNERAL_SUPPLIES_TABS.LIST}
          activeKey={tab}
          onChange={(value) => {
            handleChangeTab(value as OTHER_FUNERAL_SUPPLIES_TABS);
          }}
        />
      </div>
    </div>
  );
};

export default page;
