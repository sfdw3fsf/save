import ReservationCode from '@/components/table/ReservationCode';
import { MaterialInventoryDTO } from '@/store/material/dto/material.dto';
import { parseDate } from '@/utils/parser/date-time.parser';
import { TableColumnsType } from 'antd';
import { useTranslation } from 'react-i18next';

export const MFS_HistoryColumns = (): TableColumnsType<MaterialInventoryDTO> => {
  const { t: tFuneralSupplies } = useTranslation('material_funeral_supplies');
  const { t: tCommon } = useTranslation('common');

  return [
    {
      title: tFuneralSupplies('history.table.date'),
      dataIndex: 'registerDate',
      sorter: true,
      width: 100,
      render: (value) => parseDate(value),
    },
    {
      title: tFuneralSupplies('history.table.type'),
      dataIndex: 'type',
      sorter: true,
      width: 100,
    },
    {
      title: tFuneralSupplies('history.table.item_name'),
      dataIndex: 'name',
      width: 100,
      sorter: true,
    },
    {
      title: tFuneralSupplies('history.table.quantity'),
      dataIndex: 'quantity',
      sorter: true,
      width: 100,
    },
    {
      title: tFuneralSupplies('history.table.current_situation'),
      dataIndex: 'currentStock',
      sorter: true,
      width: 100,
    },
    {
      title: tFuneralSupplies('history.table.carryover'),
      dataIndex: 'carryoverStock',
      sorter: false,
      width: 100,
    },
    {
      title: tFuneralSupplies('history.table.funeral_number'),
      dataIndex: 'reservationNumber',
      width: 100,
      sorter: true,
      render: (text, record) => <ReservationCode code={text} id={record.id} />,
    },
    {
      title: tCommon('table.memo'),
      dataIndex: 'note',
      width: 100,
      sorter: true,
    },
  ];
};
