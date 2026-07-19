'use client';
import AddButton from '@/components/common/AddButton';
import CustomButton from '@/components/common/CustomButton';
import PageTitle from '@/components/common/page/PageTitle';
import { useMenuPermission } from '@/hook/auth/useMenuPermission';
import useRouterHandler from '@/hook/useRouterHandler';
import { MaterialInventoryMonthlyHistoryDTO } from '@/store/material/dto/material.dto';
import { createMaterialApi, materialFuneralKeys } from '@/store/material/material.api';
import { ICONS } from '@/theme';
import { ButtonTypeEnum } from '@/utils/enums/button-type.enum';
import { FUNERAL_SUPPLIES_TABS } from '@/utils/enums/material/funeral-supplies.enum';
import { MATERIAL_TYPE } from '@/utils/enums/material/material.enum';
import { MenuKeys } from '@/utils/enums/side-menu-type';
import { handleError } from '@/utils/helpers/notification-error.helper';
import { handleSuccess } from '@/utils/helpers/notification-success.helper';
import { FilterREQ } from '@/utils/types/filter.type';
import { useMutation, useQueryClient } from '@tanstack/react-query';
import { Tabs, TabsProps } from 'antd';
import Image from 'next/image';
import { useSearchParams } from 'next/navigation';
import { useMemo, useState } from 'react';
import { useTranslation } from 'react-i18next';
import MaterialMonthlyDetailModal from '../components/Material_MonthlyDetailModal';
import MFS_History from './tabs/MFS_History';
import MFS_HistorySummary from './tabs/MFS_HistorySummary';
import MFS_List from './tabs/MFS_List';

const page = () => {
  const { t: tMenu } = useTranslation('menu');
  const { t: tFuneralSupplies } = useTranslation('material_funeral_supplies');
  const { t: tMaterial } = useTranslation('material');
  const { canEdit } = useMenuPermission(MenuKeys.MATERIAL_FUNERAL_SUPPLIES);

  const queryClient = useQueryClient();
  const searchParams = useSearchParams();
  const { handleURLPush } = useRouterHandler();

  const [isOpenDetail, setIsOpenDetail] = useState<boolean>(false);
  const [tab, setTab] = useState<FUNERAL_SUPPLIES_TABS>(
    (searchParams.get('tab') as FUNERAL_SUPPLIES_TABS) || FUNERAL_SUPPLIES_TABS.LIST,
  );
  const [monthlyHistoryData, setMonthlyHistoryData] = useState<MaterialInventoryMonthlyHistoryDTO[]>([]);

  const { mutate: mutateCreateMaterialFuneral } = useMutation({
    mutationFn: () => createMaterialApi({ materialType: MATERIAL_TYPE.FUNERAL }),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: materialFuneralKeys.lists() });
      handleSuccess('create');
    },
    onError: handleError,
  });

  const onDataChange = (values: MaterialInventoryMonthlyHistoryDTO[]) => {
    setMonthlyHistoryData(values);
  };

  const [filterSummary, setFilterSummary] = useState<FilterREQ>();
  const funeralSuppliesTabs: TabsProps['items'] = useMemo(() => {
    return [
      {
        key: FUNERAL_SUPPLIES_TABS.LIST,
        label: tFuneralSupplies('tabs.list'),
        children: <MFS_List />,
      },
      {
        key: FUNERAL_SUPPLIES_TABS.INVENTORY,
        label: tMaterial('tabs.inventory'),
        children: <MFS_History onDataChange={onDataChange} filterSummary={filterSummary} />,
      },
    ];
  }, [tFuneralSupplies, filterSummary]);

  const onDetail = () => {
    setIsOpenDetail(true);
  };

  const onCloseDetail = () => {
    setIsOpenDetail(false);
  };

  const onCreateMaterialFuneral = () => {
    mutateCreateMaterialFuneral();
  };

  const handleChangeTab = (value: FUNERAL_SUPPLIES_TABS) => {
    setTab(value);
    handleURLPush('tab', value, false);
  };

  return (
    <div>
      <PageTitle
        title={tMenu('funeral-supplies')}
        actions={
          <>
            {tab === FUNERAL_SUPPLIES_TABS.LIST && canEdit && <AddButton onClick={onCreateMaterialFuneral} />}
            {tab === FUNERAL_SUPPLIES_TABS.INVENTORY && (
              <CustomButton
                type={ButtonTypeEnum.GHOST}
                onClick={onDetail}
                icon={<Image src={ICONS.LOOK_GREEN} width={20} height={20} alt='summary' />}>
                {tFuneralSupplies('button.summary')}
              </CustomButton>
            )}
          </>
        }
      />

      <div className='mt-4'>
        <Tabs
          destroyInactiveTabPane
          items={funeralSuppliesTabs}
          defaultActiveKey={FUNERAL_SUPPLIES_TABS.LIST}
          activeKey={tab}
          onChange={(value) => {
            handleChangeTab(value as FUNERAL_SUPPLIES_TABS);
          }}
        />
      </div>

      <MaterialMonthlyDetailModal isOpen={isOpenDetail} onClose={onCloseDetail} title={tFuneralSupplies('summary.title')}>
        <MFS_HistorySummary data={monthlyHistoryData} setFilterSummary={setFilterSummary} />
      </MaterialMonthlyDetailModal>
    </div>
  );
};

export default page;
