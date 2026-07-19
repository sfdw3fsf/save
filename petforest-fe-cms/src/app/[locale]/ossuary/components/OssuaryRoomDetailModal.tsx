import { OSSUARY_ROOM } from '@/utils/enums/ossuary/ossuary-room.enum';
import { Tabs, TabsProps } from 'antd';
import { useTranslation } from 'react-i18next';
import OssuarySettlementHistory from '../tabs/OssuarySettlementHistory';
import OssuaryUsageHistory from '../tabs/OssuaryUsageHistory';
import OssuaryUsageInformation from '../tabs/OssuaryUsageInformation';

type OssuaryRoomDetailModalProps = {
  id: number;
  onHideButtons: (value: boolean) => void;
};

const OssuaryRoomDetailModal = ({ id, onHideButtons }: OssuaryRoomDetailModalProps) => {
  const { t: tOssuary } = useTranslation('ossuary');

  const funeralSuppliesTabs: TabsProps['items'] = [
    {
      key: OSSUARY_ROOM.USAGE_INFORMATION,
      label: tOssuary('tabs.usage_information'),
      children: <OssuaryUsageInformation id={id} />,
    },
    {
      key: OSSUARY_ROOM.USAGE_HISTORY,
      label: tOssuary('tabs.usage_history'),
      children: <OssuaryUsageHistory id={id} />,
    },
    {
      key: OSSUARY_ROOM.SETTLEMENT_HISTORY,
      label: tOssuary('tabs.settlement_history'),
      children: <OssuarySettlementHistory id={id} />,
    },
  ];

  return (
    <div>
      <div>
        <Tabs
          items={funeralSuppliesTabs}
          defaultActiveKey={OSSUARY_ROOM.USAGE_INFORMATION}
          onChange={(value) => {
            if (value === OSSUARY_ROOM.USAGE_INFORMATION) {
              onHideButtons(false);
            } else {
              onHideButtons(true);
            }
          }}
        />
      </div>
    </div>
  );
};

export default OssuaryRoomDetailModal;
