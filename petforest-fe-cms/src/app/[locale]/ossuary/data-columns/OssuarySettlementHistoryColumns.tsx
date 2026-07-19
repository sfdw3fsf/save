import CustomNumericFormat from '@/components/common/CustomNumericFormat';
import CustomSelect from '@/components/common/CustomSelect';
import { CustomTextInput } from '@/components/common/CustomTextInput';
import EditableHeader from '@/components/common/table/EditableHeader';
import { OssuaryDetailSettlementHistoryDTO } from '@/store/ossuary/dto/ossuary.dto';
import { OssuaryDetailSettlementHistoryUpdateREQ } from '@/store/ossuary/request/ossuary.request';
import { PAYMENT_METHOD } from '@/utils/enums/common.enum';
import { InputEnum } from '@/utils/enums/input.enum';
import { ENUM_STATUS } from '@/utils/enums/status.enum';
import { helperFilter, helperOption } from '@/utils/helpers/common.helper';
import { orUndefined } from '@/utils/parser/io.parser';
import { handleChangeUpdate, handleNumericUpdate } from '@/utils/utils/inline-update.util';
import { TableColumnsType } from 'antd';
import { TFunction } from 'i18next';

type OssuaryUsageHistoryColumnsProps = {
  tOssuary: TFunction<'ossuary', undefined>;
  tEnum: TFunction<'enum', undefined>;
  onUpdateOssuaryDetailSettlementHistory: (id: number, values: OssuaryDetailSettlementHistoryUpdateREQ) => void;
};

export const OssuarySettlementHistoryColumns = ({
  tOssuary,
  tEnum,
  onUpdateOssuaryDetailSettlementHistory,
}: OssuaryUsageHistoryColumnsProps): TableColumnsType<OssuaryDetailSettlementHistoryDTO> => {
  const STATUS_FILTER = helperFilter('status', ENUM_STATUS, tEnum);
  const STATUS_OPTION = helperOption('status', ENUM_STATUS, tEnum);
  const PAYMENT_METHOD_OPTION = helperOption('payment_method', PAYMENT_METHOD, tEnum);

  return [
    {
      title: tOssuary('table.entry_date'),
      dataIndex: 'registrationDate',
      width: 150,
      sorter: true,
    },
    {
      title: tOssuary('table.expiration_date'),
      dataIndex: 'expirationDate',
      width: 150,
      sorter: true,
    },
    {
      title: <EditableHeader title={tOssuary('table.expense')} />,
      dataIndex: 'amount',
      width: 150,
      sorter: true,
      render: (value: number, record) => (
        <CustomNumericFormat
          defaultValue={value}
          onBlur={handleNumericUpdate<OssuaryDetailSettlementHistoryUpdateREQ>(
            record.id,
            'amount',
            value,
            onUpdateOssuaryDetailSettlementHistory,
          )}
        />
      ),
    },
    {
      title: <EditableHeader title={tOssuary('table.settlement_status')} />,
      dataIndex: 'settlementStatus',
      width: 150,
      filters: STATUS_FILTER,
      render: (value: ENUM_STATUS, record) => (
        <CustomSelect
          variant='borderless'
          options={STATUS_OPTION}
          defaultValue={value}
          onChange={handleChangeUpdate<OssuaryDetailSettlementHistoryUpdateREQ>(
            record.id,
            'settlementStatus',
            value,
            onUpdateOssuaryDetailSettlementHistory,
          )}
        />
      ),
    },
    {
      title: <EditableHeader title={tOssuary('table.settlement_method')} />,
      dataIndex: 'settlementMethod',
      width: 150,
      sorter: true,
      render: (value: PAYMENT_METHOD, record: OssuaryDetailSettlementHistoryDTO) => (
        <CustomSelect
          onChange={handleChangeUpdate<OssuaryDetailSettlementHistoryUpdateREQ>(
            record.id,
            'settlementMethod',
            orUndefined(record.settlementMethod),
            onUpdateOssuaryDetailSettlementHistory,
          )}
          variant='borderless'
          options={PAYMENT_METHOD_OPTION}
          defaultValue={value}
        />
      ),
    },
    {
      title: <EditableHeader title={tOssuary('table.memo')} />,
      dataIndex: 'note',
      width: 150,
      sorter: true,
      render: (text: string, record) => (
        <CustomTextInput
          type={InputEnum.TEXT}
          defaultValue={text}
          onBlur={(e) => {
            onUpdateOssuaryDetailSettlementHistory(record.id, { note: e.target.value });
          }}
        />
      ),
    },
  ];
};
