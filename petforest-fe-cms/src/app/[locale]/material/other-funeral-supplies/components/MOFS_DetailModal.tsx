'use client';
import { OTHER_FUNERAL_SUPPLIES_TABS } from '@/utils/enums/material/other-funeral-supplies.enum';
import { Tabs, TabsProps } from 'antd';
import { Dispatch, SetStateAction, useMemo, useState } from 'react';
import { useTranslation } from 'react-i18next';
import MOFS_DetailInventory from '../tabs/MOFS_DetailInventory';
import MOFS_DetailProductInformation from '../tabs/MOFS_DetailProductInformation';

type MOFS_DetailModalProps = {
  id: number;
  setIsHiddingFooter: Dispatch<SetStateAction<boolean>>;
};

const MOFS_DetailModal = ({ id, setIsHiddingFooter }: MOFS_DetailModalProps) => {
  const { t: tMaterial } = useTranslation('material');
  const [segmented, setSegmented] = useState<OTHER_FUNERAL_SUPPLIES_TABS>(OTHER_FUNERAL_SUPPLIES_TABS.REPAIR);

  const funeralSuppliesTabs: TabsProps['items'] = useMemo(() => {
    return [
      {
        key: OTHER_FUNERAL_SUPPLIES_TABS.INVENTORY,
        label: tMaterial('tabs.inventory'),
        children: <MOFS_DetailInventory id={id} />,
      },
      {
        key: OTHER_FUNERAL_SUPPLIES_TABS.PRODUCT,
        label: tMaterial('tabs.product'),
        children: <MOFS_DetailProductInformation id={id} />,
      },
    ];
  }, [tMaterial]);

  return (
    <div>
      <div className='border-b-neutral-40 border-b-[1px]'>
        <Tabs
          items={funeralSuppliesTabs}
          defaultActiveKey={OTHER_FUNERAL_SUPPLIES_TABS.REPAIR}
          onChange={(value) => {
            setSegmented(value as OTHER_FUNERAL_SUPPLIES_TABS);
            if (value === OTHER_FUNERAL_SUPPLIES_TABS.PRODUCT) {
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

export default MOFS_DetailModal;
