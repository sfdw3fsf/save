import { MaterialInventoryDTO } from '@/store/material/dto/material.dto';
import { parseDate } from '@/utils/parser/date-time.parser';
import { TableColumnsType } from 'antd';
import { useTranslation } from 'react-i18next';

export const MES_HistoryColumns = (): TableColumnsType<MaterialInventoryDTO> => {
  const { t: tExpendables } = useTranslation('material_expendables_supplies');
  const { t: tCommon } = useTranslation('common');

  return [
    {
      title: tExpendables('history.table.date'),
      dataIndex: 'registerDate',
      sorter: true,
      width: 100,
      onCell: (record: MaterialInventoryDTO) => ({
        className: record.reservation ? 'disabled-column' : '',
      }),
      render: (value) => parseDate(value),
    },
    {
      title: tExpendables('history.table.quantity'),
      dataIndex: 'quantity',
      sorter: true,
      width: 100,
      onCell: (record: MaterialInventoryDTO) => ({
        className: record.reservation ? 'disabled-column' : '',
      }),
    },
    {
      title: tExpendables('history.table.name'),
      dataIndex: 'name',
      width: 100,
      sorter: true,
      onCell: (record: MaterialInventoryDTO) => ({
        className: record.reservation ? 'disabled-column' : '',
      }),
    },
    {
      title: tExpendables('history.table.current_situation'),
      dataIndex: 'currentStock',
      sorter: true,
      width: 100,
    },
    {
      title: tExpendables('history.table.carryover'),
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

export default MES_HistoryColumns;
