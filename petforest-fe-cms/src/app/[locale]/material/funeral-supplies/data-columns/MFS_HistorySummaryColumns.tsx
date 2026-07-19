import { MaterialInventoryMonthlyHistoryDTO } from '@/store/material/dto/material.dto';
import { TableColumnsType } from 'antd';
import { useTranslation } from 'react-i18next';

export const MFS_HistorySummaryColumns = (): TableColumnsType<MaterialInventoryMonthlyHistoryDTO> => {
  const { t: tFuneralSupplies } = useTranslation('material_funeral_supplies');

  return [
    {
      title: tFuneralSupplies('summary.table.type'),
      dataIndex: 'type',
      sorter: true,
      width: 80,
    },
    {
      title: tFuneralSupplies('summary.table.item_name'),
      dataIndex: 'name',
      width: 80,
      sorter: true,
    },
    {
      title: tFuneralSupplies('summary.table.ending_inventory'),
      dataIndex: 'carryoverStock',
      sorter: true,
      width: 80,
    },
    {
      title: tFuneralSupplies('summary.table.amount'),
      dataIndex: 'stockQuantity',
      sorter: true,
      width: 80,
    },
  ];
};
