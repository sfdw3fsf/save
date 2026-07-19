import CustomIconButton from '@/components/common/CustomIconButton';
import { NotiHistoryListDTO } from '@/store/funeral/noti-history/dto/noti-history.dto';
import { ICONS } from '@/theme';
import { NOTI_HISTORY_CATEGORY } from '@/utils/enums/funeral/reservation-mngt.enums';
import { helperFilter } from '@/utils/helpers/common.helper';
import { TableColumnsType } from 'antd';
import Image from 'next/image';
import { useTranslation } from 'react-i18next';

type NotiHistoryColumnsProps = {
  onRecordSelect: (id: number) => void;
};

export const NotiHistoryColumns = ({ onRecordSelect }: NotiHistoryColumnsProps): TableColumnsType<NotiHistoryListDTO> => {
  const { t: tNotiHistory } = useTranslation('funeral_noti_history');
  const { t: tCommon } = useTranslation('common');
  const { t: tEnum } = useTranslation('enum');

  const categoryFilter = helperFilter('reservation.noti_history_category', NOTI_HISTORY_CATEGORY, tEnum);

  return [
    {
      title: tNotiHistory('table.category'),
      dataIndex: 'category',
      width: 80,
      filters: categoryFilter,
      render: (value) => tEnum(`reservation.noti_history_category.${value}`, value),
    },
    {
      title: tNotiHistory('table.transmission'),
      dataIndex: 'createdAt',
      sorter: true,
      width: 100,
    },
    {
      title: tNotiHistory('table.pet_name'),
      dataIndex: 'petName',
      width: 80,
    },
    {
      title: tNotiHistory('table.guardian_name'),
      dataIndex: 'guardianName',
      width: 80,
    },
    {
      title: tNotiHistory('table.contact_number'),
      dataIndex: 'contact',
      sorter: true,
      width: 100,
    },
    // {
    //   title: tNotiHistory('table.copy_key'),
    //   key: 'copy',
    //   width: 80,
    //   align: 'center',
    //   render: (_, record) => <CopyButton isIcon text={record.messageKey} />,
    // },
    {
      title: tNotiHistory('table.message_key'),
      dataIndex: 'messageKey',
      sorter: true,
      width: 200,
    },
    {
      title: tCommon('table.view'),
      className: 'ant-table-view-column',
      key: 'look',
      fixed: 'right',
      width: 50,
      align: 'center',
      render: (_, record) => (
        <CustomIconButton
          onClick={() => onRecordSelect(record.id)}
          isNoBorder={true}
          icon={<Image src={ICONS.LOOK} alt='Look' width={24} height={24} />}
        />
      ),
    },
  ];
};
