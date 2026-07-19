'use client';
import AddButton from '@/components/common/AddButton';
import PageTitle from '@/components/common/page/PageTitle';
import { useMenuPermission } from '@/hook/auth/useMenuPermission';
import useRouterHandler from '@/hook/useRouterHandler';
import { createMaterialApi, materialExpendablesKeys } from '@/store/material/material.api';
import { EXPENDABLES_SUPPLIES_TABS } from '@/utils/enums/material/expendables-supplies.enum';
import { MATERIAL_TYPE } from '@/utils/enums/material/material.enum';
import { MenuKeys } from '@/utils/enums/side-menu-type';
import { handleError } from '@/utils/helpers/notification-error.helper';
import { handleSuccess } from '@/utils/helpers/notification-success.helper';
import { useMutation, useQueryClient } from '@tanstack/react-query';
import { Tabs, TabsProps } from 'antd';
import { useSearchParams } from 'next/navigation';
import { useMemo, useState } from 'react';
import { useTranslation } from 'react-i18next';
import MES_History from './tabs/MES_History';
import MES_List from './tabs/MES_List';

const page = () => {
  const { t: tMenu } = useTranslation('menu');
  const { t: tExpendablesSupplies } = useTranslation('material_expendables_supplies');
  const { t: tMaterial } = useTranslation('material');
  const queryClient = useQueryClient();
  const searchParams = useSearchParams();
  const { handleURLPush } = useRouterHandler();
  const { canEdit } = useMenuPermission(MenuKeys.MATERIAL_EXPENDABLES_SUPPLIES);

  const [tab, setTab] = useState<EXPENDABLES_SUPPLIES_TABS>(
    (searchParams.get('tab') as EXPENDABLES_SUPPLIES_TABS) || EXPENDABLES_SUPPLIES_TABS.LIST,
  );

  const { mutate: mutateCreateMaterialExpendables } = useMutation({
    mutationFn: () => createMaterialApi({ materialType: MATERIAL_TYPE.CONSUMABLE }),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: materialExpendablesKeys.lists() });
      handleSuccess('create');
    },
    onError: handleError,
  });

  const expendablesTabs: TabsProps['items'] = useMemo(() => {
    return [
      {
        key: EXPENDABLES_SUPPLIES_TABS.LIST,
        label: tExpendablesSupplies('tabs.list'),
        children: <MES_List />,
      },
      {
        key: EXPENDABLES_SUPPLIES_TABS.INVENTORY,
        label: tMaterial('tabs.inventory'),
        children: <MES_History />,
      },
    ];
  }, [tExpendablesSupplies]);

  const onCreateMaterialExpendables = () => {
    mutateCreateMaterialExpendables();
  };

  const handleChangeTab = (value: EXPENDABLES_SUPPLIES_TABS) => {
    setTab(value);
    handleURLPush('tab', value, false);
  };

  return (
    <div>
      <PageTitle
        title={tMenu('expendables-supplies')}
        actions={tab === EXPENDABLES_SUPPLIES_TABS.LIST && canEdit && <AddButton onClick={onCreateMaterialExpendables} />}
      />

      <div className='mt-4'>
        <Tabs
          destroyInactiveTabPane
          items={expendablesTabs}
          defaultActiveKey={EXPENDABLES_SUPPLIES_TABS.LIST}
          activeKey={tab}
          onChange={(value) => {
            handleChangeTab(value as EXPENDABLES_SUPPLIES_TABS);
          }}
        />
      </div>
    </div>
  );
};

export default page;
