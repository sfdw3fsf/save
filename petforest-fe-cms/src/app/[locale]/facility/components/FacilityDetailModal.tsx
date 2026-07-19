import { FACILITY_TABS } from '@/utils/enums/facility/facility.enum';
import { Tabs } from 'antd';
import { TabsProps } from 'antd/lib';
import { Dispatch, SetStateAction, useEffect, useMemo, useState } from 'react';
import { useTranslation } from 'react-i18next';
import FacilityDetailInformation from '../tabs/FacilityDetailInformation';
import FacilityDetailRepair from '../tabs/FacilityDetailRepair';

type FacilityDetailModalProps = {
  facilityId: number;
  setIsHiddingFooter: Dispatch<SetStateAction<boolean>>;
};

const FacilityDetailModal = ({ facilityId, setIsHiddingFooter }: FacilityDetailModalProps) => {
  const { t: tFacility } = useTranslation('facility');
  const [segmented, setSegmented] = useState<FACILITY_TABS>(FACILITY_TABS.REPAIR);

  const funeralSuppliesTabs: TabsProps['items'] = useMemo(() => {
    return [
      {
        key: FACILITY_TABS.REPAIR,
        label: tFacility('tabs.inventory'),
        children: <FacilityDetailRepair facilityId={facilityId} />,
      },
      {
        key: FACILITY_TABS.FACILITY_INFORMATION,
        label: tFacility('tabs.product'),
        children: <FacilityDetailInformation />,
      },
    ];
  }, [facilityId, tFacility]);

  useEffect(() => {
    if (segmented === FACILITY_TABS.FACILITY_INFORMATION) {
      setIsHiddingFooter(false);
    } else {
      setIsHiddingFooter(true);
    }
  }, [segmented]);

  return (
    <div>
      <div className='border-b-neutral-40 border-b-[1px]'>
        <Tabs
          items={funeralSuppliesTabs}
          defaultActiveKey={FACILITY_TABS.REPAIR}
          onChange={(value) => {
            setSegmented(value as FACILITY_TABS);
          }}
        />
      </div>
    </div>
  );
};

export default FacilityDetailModal;
