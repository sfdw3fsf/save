'use client';
import AddButton from '@/components/common/AddButton';
import PageTitle from '@/components/common/page/PageTitle';
import { useMenuPermission } from '@/hook/auth/useMenuPermission';
import useRouterHandler from '@/hook/useRouterHandler';
import { createMaterialApi, materialLucetteKeys } from '@/store/material/material.api';
import { LUCETTE_SUPPLIES_TABS } from '@/utils/enums/material/lucette-supplies.enum';
import { MATERIAL_TYPE } from '@/utils/enums/material/material.enum';
import { MenuKeys } from '@/utils/enums/side-menu-type';
import { handleError } from '@/utils/helpers/notification-error.helper';
import { handleSuccess } from '@/utils/helpers/notification-success.helper';
import { useMutation, useQueryClient } from '@tanstack/react-query';
import { Tabs, TabsProps } from 'antd';
import { useSearchParams } from 'next/navigation';
import { useMemo, useState } from 'react';
import { useTranslation } from 'react-i18next';
import MLS_History from './tabs/MLS_History';
import MLS_List from './tabs/MLS_List';

const page = () => {
  const { t: tMenu } = useTranslation('menu');
  const { t: tLucetteSupplies } = useTranslation('material_lucette_supplies');
  const { t: tMaterial } = useTranslation('material');
  const queryClient = useQueryClient();
  const searchParams = useSearchParams();
  const { handleURLPush } = useRouterHandler();
  const { canEdit } = useMenuPermission(MenuKeys.MATERIAL_LUCETTE_SUPPLIES);

  const [tab, setTab] = useState<LUCETTE_SUPPLIES_TABS>(
    (searchParams.get('tab') as LUCETTE_SUPPLIES_TABS) || LUCETTE_SUPPLIES_TABS.LIST,
  );

  const { mutate: mutateCreateMaterialLucette } = useMutation({
    mutationFn: () => createMaterialApi({ materialType: MATERIAL_TYPE.LUCETTE }),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: materialLucetteKeys.lists() });
      handleSuccess('create');
    },
    onError: handleError,
  });

  const lucetteSuppliesTabs: TabsProps['items'] = useMemo(() => {
    return [
      {
        key: LUCETTE_SUPPLIES_TABS.LIST,
        label: tLucetteSupplies('tabs.list'),
        children: <MLS_List />,
      },
      {
        key: LUCETTE_SUPPLIES_TABS.INVENTORY,
        label: tMaterial('tabs.inventory'),
        children: <MLS_History />,
      },
    ];
  }, [tLucetteSupplies]);

  const onCreateMaterialLucette = () => {
    mutateCreateMaterialLucette();
  };

  const handleChangeTab = (value: LUCETTE_SUPPLIES_TABS) => {
    setTab(value);
    handleURLPush('tab', value, false);
  };

  return (
    <div>
      <PageTitle
        title={tMenu('lucette-supplies')}
        actions={tab === LUCETTE_SUPPLIES_TABS.LIST && canEdit && <AddButton onClick={onCreateMaterialLucette} />}
      />

      <div className='mt-4'>
        <Tabs
          destroyInactiveTabPane
          items={lucetteSuppliesTabs}
          defaultActiveKey={LUCETTE_SUPPLIES_TABS.LIST}
          activeKey={tab}
          onChange={(value) => {
            handleChangeTab(value as LUCETTE_SUPPLIES_TABS);
          }}
        />
      </div>
    </div>
  );
};

export default page;
