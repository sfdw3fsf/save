import { MaterialInventoryMonthlyDTO } from '@/store/material/dto/material.dto';
import { TableColumnsType } from 'antd';
import { useTranslation } from 'react-i18next';

export const MaterialMonthlyDetailColumns = (): TableColumnsType<MaterialInventoryMonthlyDTO> => {
  const { t: tMaterial } = useTranslation('material');

  return [
    {
      title: tMaterial('detail.monthly_detail.table.year_month'),
      dataIndex: 'recordedAt',
      sorter: true,
    },
    {
      title: tMaterial('detail.monthly_detail.table.carryover_inventory'),
      dataIndex: 'carryoverStock',
    },
    {
      title: tMaterial('detail.monthly_detail.table.end_inventory'),
      dataIndex: 'endingInventory',
    },
    {
      title: tMaterial('detail.monthly_detail.table.amount'),
      dataIndex: 'totalAmount',
    },
  ];
};
