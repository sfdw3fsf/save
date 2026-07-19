'use client';
import { EXPENDABLES_SUPPLIES_TABS } from '@/utils/enums/material/expendables-supplies.enum';
import { Tabs, TabsProps } from 'antd';
import { Dispatch, SetStateAction, useMemo, useState } from 'react';
import { useTranslation } from 'react-i18next';
import MES_DetailInventory from '../tabs/MES_DetailInventory';
import MES_DetailProductInformation from '../tabs/MES_DetailProductInformation';

type MES_DetailModalProps = {
  id: number;
  setIsHiddingFooter: Dispatch<SetStateAction<boolean>>;
};

const MES_DetailModal = ({ id, setIsHiddingFooter }: MES_DetailModalProps) => {
  const { t: tMaterial } = useTranslation('material');
  const [segmented, setSegmented] = useState<EXPENDABLES_SUPPLIES_TABS>(EXPENDABLES_SUPPLIES_TABS.REPAIR);

  const funeralSuppliesTabs: TabsProps['items'] = useMemo(() => {
    return [
      {
        key: EXPENDABLES_SUPPLIES_TABS.INVENTORY,
        label: tMaterial('tabs.inventory'),
        children: <MES_DetailInventory id={id} />,
      },
      {
        key: EXPENDABLES_SUPPLIES_TABS.PRODUCT,
        label: tMaterial('tabs.product'),
        children: <MES_DetailProductInformation id={id} />,
      },
    ];
  }, [tMaterial]);

  return (
    <div>
      <div className='border-b-neutral-40 border-b-[1px]'>
        <Tabs
          items={funeralSuppliesTabs}
          defaultActiveKey={EXPENDABLES_SUPPLIES_TABS.REPAIR}
          onChange={(value) => {
            setSegmented(value as EXPENDABLES_SUPPLIES_TABS);
            if (value === EXPENDABLES_SUPPLIES_TABS.PRODUCT) {
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

export default MES_DetailModal;
