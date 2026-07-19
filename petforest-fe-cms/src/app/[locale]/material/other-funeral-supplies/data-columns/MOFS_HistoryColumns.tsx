import { MaterialInventoryDTO } from '@/store/material/dto/material.dto';
import { parseDate } from '@/utils/parser/date-time.parser';
import { TableColumnsType } from 'antd';
import { useTranslation } from 'react-i18next';

export const MOFS_HistoryColumns = (): TableColumnsType<MaterialInventoryDTO> => {
  const { t: tOtherSupplies } = useTranslation('material_other_funeral_supplies');
  const { t: tCommon } = useTranslation('common');

  return [
    {
      title: tOtherSupplies('history.table.date'),
      dataIndex: 'registerDate',
      sorter: true,
      width: 100,
      render: (value) => parseDate(value),
      onCell: (record: MaterialInventoryDTO) => ({
        className: record.reservation ? 'disabled-column' : '',
      }),
    },
    {
      title: tOtherSupplies('history.table.quantity'),
      dataIndex: 'quantity',
      sorter: true,
      width: 100,
      onCell: (record: MaterialInventoryDTO) => ({
        className: record.reservation ? 'disabled-column' : '',
      }),
    },
    {
      title: tOtherSupplies('history.table.type'),
      dataIndex: 'type',
      sorter: true,
      width: 100,
      onCell: (record: MaterialInventoryDTO) => ({
        className: record.reservation ? 'disabled-column' : '',
      }),
    },
    {
      title: tOtherSupplies('history.table.name'),
      dataIndex: 'name',
      width: 100,
      sorter: true,
      onCell: (record: MaterialInventoryDTO) => ({
        className: record.reservation ? 'disabled-column' : '',
      }),
    },
    {
      title: tOtherSupplies('history.table.current_situation'),
      dataIndex: 'currentStock',
      sorter: true,
      width: 100,
    },
    {
      title: tOtherSupplies('history.table.carryover'),
      dataIndex: 'carryoverStock',
      sorter: true,
      width: 100,
    },
    {
      title: tCommon('table.memo'),
      dataIndex: 'note',
      width: 100,
      sorter: true,
    },
  ];
};

export default MOFS_HistoryColumns;
