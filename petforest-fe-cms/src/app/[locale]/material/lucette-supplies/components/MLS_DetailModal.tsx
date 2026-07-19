'use client';
import { LUCETTE_SUPPLIES_TABS } from '@/utils/enums/material/lucette-supplies.enum';
import { Tabs, TabsProps } from 'antd';
import { Dispatch, SetStateAction, useMemo, useState } from 'react';
import { useTranslation } from 'react-i18next';
import MLS_DetailInventory from '../tabs/MLS_DetailInventory';
import MLS_DetailProductInformation from '../tabs/MLS_DetailProductInformation';

type MLS_DetailModalProps = {
  id: number;
  setIsHiddingFooter: Dispatch<SetStateAction<boolean>>;
};

const MLS_DetailModal = ({ id, setIsHiddingFooter }: MLS_DetailModalProps) => {
  const { t: tMaterial } = useTranslation('material');
  const [segmented, setSegmented] = useState<LUCETTE_SUPPLIES_TABS>(LUCETTE_SUPPLIES_TABS.REPAIR);

  const funeralSuppliesTabs: TabsProps['items'] = useMemo(() => {
    return [
      {
        key: LUCETTE_SUPPLIES_TABS.INVENTORY,
        label: tMaterial('tabs.inventory'),
        children: <MLS_DetailInventory id={id} />,
      },
      {
        key: LUCETTE_SUPPLIES_TABS.PRODUCT,
        label: tMaterial('tabs.product'),
        children: <MLS_DetailProductInformation id={id} />,
      },
    ];
  }, [tMaterial]);

  return (
    <div>
      <div className='border-b-neutral-40 border-b-[1px]'>
        <Tabs
          items={funeralSuppliesTabs}
          defaultActiveKey={LUCETTE_SUPPLIES_TABS.REPAIR}
          onChange={(value) => {
            setSegmented(value as LUCETTE_SUPPLIES_TABS);
            if (value === LUCETTE_SUPPLIES_TABS.PRODUCT) {
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

export default MLS_DetailModal;
