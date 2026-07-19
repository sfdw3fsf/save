import ReservationCode from '@/components/table/ReservationCode';
import { SettlementAdjustmentAdditionalCostListDTO } from '@/store/settlement/dto/settlement.dto';
import { TableColumnsType } from 'antd';
import { useTranslation } from 'react-i18next';
import { NumericFormat } from 'react-number-format';

export const SA_AdditionalCostColumns = (): TableColumnsType<SettlementAdjustmentAdditionalCostListDTO> => {
  const { t: tSettlement } = useTranslation('settlement');

  return [
    {
      title: tSettlement('table.date'),
      dataIndex: 'funeralDate',
      width: 140,
      fixed: 'left',
      sorter: true,
    },
    {
      title: tSettlement('table.number'),
      dataIndex: 'funeralCode',
      width: 140,
      fixed: 'left',
      sorter: true,
      render: (value: string, record: SettlementAdjustmentAdditionalCostListDTO) => (
        <ReservationCode code={value} id={record.reservationId} />
      ),
    },
    {
      title: tSettlement('table.transport'),
      dataIndex: 'transport',
      width: 140,
      sorter: true,
      render: (value) => (value ? <NumericFormat value={value} displayType='text' thousandSeparator /> : null),
    },
    {
      title: tSettlement('table.overweight_fee'),
      dataIndex: 'overweight',
      width: 140,
      sorter: true,
      render: (value) => (value ? <NumericFormat value={value} displayType='text' thousandSeparator /> : null),
    },
    {
      title: tSettlement('table.columbarium'),
      dataIndex: 'columbarium',
      width: 140,
      sorter: true,
      render: (value) => (value ? <NumericFormat value={value} displayType='text' thousandSeparator /> : null),
    },
    {
      title: tSettlement('table.temporary_storage'),
      dataIndex: 'temporaryPlacement',
      width: 140,
      sorter: true,
      render: (value) => (value ? <NumericFormat value={value} displayType='text' thousandSeparator /> : null),
    },
    {
      title: tSettlement('table.carbon_plate'),
      dataIndex: 'carbonPlate',
      width: 140,
      sorter: true,
      render: (value) => (value ? <NumericFormat value={value} displayType='text' thousandSeparator /> : null),
    },
    {
      title: tSettlement('table.others'),
      dataIndex: 'otherAdditionalCost',
      width: 140,
      sorter: true,
      render: (value) => (value ? <NumericFormat value={value} displayType='text' thousandSeparator /> : null),
    },
  ];
};
