import ReservationCode from '@/components/table/ReservationCode';
import { MaterialInventoryDTO } from '@/store/material/dto/material.dto';
import { parseDate } from '@/utils/parser/date-time.parser';
import { TableColumnsType } from 'antd';
import { useTranslation } from 'react-i18next';

export const MLS_HistoryColumns = (): TableColumnsType<MaterialInventoryDTO> => {
  const { t: tLucetteSupplies } = useTranslation('material_lucette_supplies');
  const { t: tCommon } = useTranslation('common');

  return [
    {
      title: tLucetteSupplies('history.table.date'),
      dataIndex: 'registerDate',
      sorter: true,
      width: 100,
      render: (value) => parseDate(value),
    },
    {
      title: tLucetteSupplies('history.table.funeral_number'),
      dataIndex: 'reservationNumber',
      sorter: true,
      width: 100,
      render: (text, record) => <ReservationCode code={text} id={record.id} />,
    },
    {
      title: tLucetteSupplies('history.table.quantity'),
      dataIndex: 'quantity',
      sorter: true,
      width: 100,
    },
    {
      title: tLucetteSupplies('history.table.product_name'),
      dataIndex: 'name',
      width: 100,
      sorter: true,
    },
    {
      title: tLucetteSupplies('history.table.current_situation'),
      dataIndex: 'currentStock',
      sorter: true,
      width: 100,
    },
    {
      title: tLucetteSupplies('history.table.carryover'),
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

export default MLS_HistoryColumns;
