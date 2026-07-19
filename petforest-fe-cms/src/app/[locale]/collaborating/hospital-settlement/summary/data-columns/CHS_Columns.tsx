import HospitalName from '@/components/table/HospitalName';
import { HospitalSettlementSummaryDTO } from '@/store/collaborating-hospital/dto/collaborating-hospital.dto';
import { FilterOptions } from '@/utils/types/option.type';
import { TableColumnsType } from 'antd';
import { useMemo } from 'react';
import { useTranslation } from 'react-i18next';
import { NumericFormat } from 'react-number-format';

type CHS_SummaryColumnsProps = {
  settlementMethodOptions: FilterOptions;
};
export const CHS_SummaryColumns = ({
  settlementMethodOptions,
}: CHS_SummaryColumnsProps): TableColumnsType<HospitalSettlementSummaryDTO> => {
  const { t: tSettlement } = useTranslation('collaborating_settlement');

  const settlementMethods = useMemo(() => {
    return settlementMethodOptions
      .map((item) => [
        {
          title: item.label,
          dataIndex: [item.value, '0'],
          colSpan: 2,
          className: 'ant-column-center',
          width: 80,
          render: (value: number) => (
            <NumericFormat value={value} displayType='text' thousandSeparator className='!text-neutral-80' />
          ),
        },
        {
          dataIndex: [item.value, '1'],
          colSpan: 0,
          width: 100,
          className: 'ant-column-center',
          render: (value: number) => (
            <NumericFormat value={value} displayType='text' thousandSeparator className='!text-neutral-80' />
          ),
        },
      ])
      .flat();
  }, [settlementMethodOptions]);

  return [
    {
      title: tSettlement('table.hospital'),
      dataIndex: 'hospital',
      width: 250,
      render: (value: string, record: HospitalSettlementSummaryDTO) => <HospitalName name={value} id={record.hospitalId} />,
    },
    ...settlementMethods,
  ];
};
