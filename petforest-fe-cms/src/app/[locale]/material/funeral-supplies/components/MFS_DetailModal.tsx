'use client';
import { FUNERAL_SUPPLIES_TABS } from '@/utils/enums/material/funeral-supplies.enum';
import { Tabs, TabsProps } from 'antd';
import { Dispatch, SetStateAction, useMemo, useState } from 'react';
import { useTranslation } from 'react-i18next';
import MFS_DetailInventory from '../tabs/MFS_DetailInventory';
import MFS_DetailProductInformation from '../tabs/MFS_DetailProductInformation';

type MFS_DetailModalProps = {
  id: number;
  setIsHiddingFooter: Dispatch<SetStateAction<boolean>>;
};

const MFS_DetailModal = ({ id, setIsHiddingFooter }: MFS_DetailModalProps) => {
  const { t: tMaterial } = useTranslation('material');
  const [segmented, setSegmented] = useState<FUNERAL_SUPPLIES_TABS>(FUNERAL_SUPPLIES_TABS.REPAIR);

  const funeralSuppliesTabs: TabsProps['items'] = useMemo(() => {
    return [
      {
        key: FUNERAL_SUPPLIES_TABS.INVENTORY,
        label: tMaterial('tabs.inventory'),
        children: <MFS_DetailInventory id={id} />,
      },
      {
        key: FUNERAL_SUPPLIES_TABS.PRODUCT,
        label: tMaterial('tabs.product'),
        children: <MFS_DetailProductInformation id={id} />,
      },
    ];
  }, [tMaterial]);

  return (
    <div>
      <div className='border-b-neutral-40 border-b-[1px]'>
        <Tabs
          items={funeralSuppliesTabs}
          defaultActiveKey={FUNERAL_SUPPLIES_TABS.REPAIR}
          onChange={(value) => {
            setSegmented(value as FUNERAL_SUPPLIES_TABS);
            if (value === FUNERAL_SUPPLIES_TABS.PRODUCT) {
              setIsHiddingFooter(false);
            } else {
              setIsHiddingFooter(true);
            }
          }}
        />
      </div>
    </div>
  );
};

export default MFS_DetailModal;
