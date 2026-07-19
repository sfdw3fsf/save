import CustomSelect from '@/components/common/CustomSelect';
import { CustomTextInput } from '@/components/common/CustomTextInput';
import Status from '@/components/common/Status';
import EditableHeader from '@/components/common/table/EditableHeader';
import ReservationCode from '@/components/table/ReservationCode';
import { SettlementCouponListDTO } from '@/store/settlement/dto/settlement.dto';
import { SettlementCouponListUpdateREQ } from '@/store/settlement/request/settlement.request';
import { DATE_FORMAT } from '@/utils/constants/date.constant';
import { COUPON_STATUS } from '@/utils/enums/common.enum';
import { InputEnum } from '@/utils/enums/input.enum';
import { helperFilter } from '@/utils/helpers/common.helper';
import { parseTimestampToDate } from '@/utils/parser/date-time.parser';
import { handleBlurUpdate, handleChangeUpdate } from '@/utils/utils/inline-update.util';
import { TableColumnsType } from 'antd';
import { useTranslation } from 'react-i18next';
import { NumericFormat } from 'react-number-format';

type SettlementCouponColumnsProps = {
  onListUpdate: (id: number, body: SettlementCouponListUpdateREQ) => void;
};

export const SettlementCouponColumns = ({
  onListUpdate,
}: SettlementCouponColumnsProps): TableColumnsType<SettlementCouponListDTO> => {
  const { t: tSettlement } = useTranslation('settlement');
  const { t: tEnum } = useTranslation('enum');

  const couponStatusFilter = helperFilter('coupon_status', COUPON_STATUS, tEnum);

  return [
    {
      title: <EditableHeader title={tSettlement('coupon_table.status')} />,
      dataIndex: 'status',
      width: 140,
      filters: couponStatusFilter,
      render: (value: COUPON_STATUS, record) => (
        <CustomSelect
          variant='borderless'
          defaultValue={value}
          optionRender={(option) => option.data.text}
          options={couponStatusFilter.map((option) => ({
            ...option,
            label: <Status isSuccess={option.value === COUPON_STATUS.PAID} status={option.text} />,
          }))}
          onChange={handleChangeUpdate<SettlementCouponListUpdateREQ>(record.id, 'status', value, onListUpdate)}
        />
      ),
    },
    {
      title: tSettlement('coupon_table.couponNumber'),
      dataIndex: 'code',
      width: 200,
      sorter: true,
    },
    {
      title: tSettlement('coupon_table.funeralNumber'),
      dataIndex: 'reservationCode',
      width: 140,
      sorter: true,
      render: (value: string, record: SettlementCouponListDTO) => <ReservationCode code={value} id={record.reservationId} />,
    },
    {
      title: tSettlement('coupon_table.funeralDate'),
      dataIndex: 'funeralDate',
      width: 140,
      sorter: true,
      render: (value: string) => (value ? parseTimestampToDate(value) : ''),
    },
    {
      title: tSettlement('coupon_table.funeralTime'),
      dataIndex: 'funeralTime',
      width: 80,
      sorter: true,
    },
    {
      title: tSettlement('coupon_table.amount'),
      dataIndex: 'amount',
      width: 140,
      sorter: true,
      render: (value) => <NumericFormat value={value} displayType='text' thousandSeparator />,
    },
    {
      title: tSettlement('coupon_table.buyer'),
      dataIndex: 'purchaser',
      width: 140,
    },
    {
      title: tSettlement('coupon_table.buyerContact'),
      dataIndex: 'purchaserContact',
      width: 140,
    },
    {
      title: tSettlement('coupon_table.guardian'),
      dataIndex: 'guardianName',
      width: 140,
    },
    {
      title: tSettlement('coupon_table.guardianContact'),
      dataIndex: 'guardianPhone',
      width: 140,
    },
    {
      title: tSettlement('coupon_table.purchaseDate'),
      dataIndex: 'purchaseDate',
      width: 200,
      sorter: true,
      render: (value: string) => (value ? parseTimestampToDate(value, DATE_FORMAT.DATE_TIME.HYPHEN) : ''),
    },
    {
      title: tSettlement('coupon_table.noOfTrace'),
      dataIndex: 'paymentCode',
      width: 200,
      sorter: true,
    },
    {
      title: <EditableHeader title={tSettlement('coupon_table.memo')} />,
      dataIndex: 'note',
      width: 200,
      render: (value: string, record) => (
        <CustomTextInput
          onBlur={handleBlurUpdate<SettlementCouponListUpdateREQ>(record.id, 'note', value, onListUpdate)}
          type={InputEnum.TEXT_AREA}
          defaultValue={value}
        />
      ),
    },
  ];
};
