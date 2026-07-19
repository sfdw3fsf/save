import CustomText from '@/components/common/Text';
import HospitalName from '@/components/table/HospitalName';
import ReservationCode from '@/components/table/ReservationCode';
import { HospitalSettlementDTO } from '@/store/collaborating-hospital/dto/collaborating-hospital.dto';
import { FilterOptions } from '@/utils/types/option.type';
import { TableColumnsType } from 'antd';
import { useTranslation } from 'react-i18next';
import { NumericFormat } from 'react-number-format';

type CHS_ColumnsProps = {
  settlementMethodOptions: FilterOptions;
};
export const CHS_Columns = ({ settlementMethodOptions }: CHS_ColumnsProps): TableColumnsType<HospitalSettlementDTO> => {
  const { t: tSettlement } = useTranslation('collaborating_settlement');
  const { t: tCommon } = useTranslation('common');

  return [
    {
      title: tSettlement('table.hospital'),
      dataIndex: 'hospitalName',
      width: 327,
      sorter: true,
      render: (value: string, record: HospitalSettlementDTO) => <HospitalName name={value} id={record.hospitalId} />,
    },
    {
      title: tSettlement('table.funeral_date'),
      dataIndex: 'funeralDate',
      width: 140,
      sorter: true,
    },
    {
      title: tSettlement('table.funeral_number'),
      dataIndex: 'funeralCode',
      width: 134,
      sorter: true,
      render: (value: string, record: HospitalSettlementDTO) => <ReservationCode code={value} id={record.reservationId} />,
    },
    {
      title: tSettlement('table.settlement_date'),
      dataIndex: 'settlementDate',
      width: 140,
      sorter: true,
    },
    {
      title: tSettlement('table.settlement_method'),
      dataIndex: 'hospitalEntitlement.id',
      width: 134,
      sorter: true,
      filters: settlementMethodOptions,
    },
    {
      title: tSettlement('table.amount'),
      dataIndex: 'amount',
      width: 133,
      sorter: true,
      render: (value: number) => (
        <CustomText variant='label-1-normal' className='!text-neutral-80'>
          <NumericFormat value={value} displayType='text' thousandSeparator />
          {tCommon('won')}
        </CustomText>
      ),
    },
    {
      title: tSettlement('table.email_send_latest_date'),
      dataIndex: 'latestEmailDate',
      width: 166,
      sorter: true,
    },
    {
      title: tCommon('table.memo'),
      dataIndex: 'memo',
      width: 327,
      sorter: true,
    },
  ];
};
