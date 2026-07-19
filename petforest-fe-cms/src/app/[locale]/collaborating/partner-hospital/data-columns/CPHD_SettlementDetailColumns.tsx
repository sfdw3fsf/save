import CustomDatePicker from '@/components/common/CustomDatePicker';
import CustomNumericFormat from '@/components/common/CustomNumericFormat';
import CustomSelect from '@/components/common/CustomSelect';
import { CustomTextInput } from '@/components/common/CustomTextInput';
import EditableHeader from '@/components/common/table/EditableHeader';
import ReservationCode from '@/components/table/ReservationCode';
import { HospitalDetailSettlementDTO } from '@/store/collaborating-hospital/dto/collaborating-hospital.dto';
import { HospitalDetailSettlementUpdateREQ } from '@/store/collaborating-hospital/request/collaborating-hospital.request';
import { InputEnum } from '@/utils/enums/input.enum';
import { FilterOptions } from '@/utils/types/option.type';
import {
  handleBlurUpdate,
  handleChangeDateUpdate,
  handleChangeUpdate,
  handleNumericUpdate,
} from '@/utils/utils/inline-update.util';
import { TableColumnsType } from 'antd';
import { Dayjs } from 'dayjs';
import { useTranslation } from 'react-i18next';

type CPH_SettlementDetailColumns = {
  id: number;
  settlementMethodOptions: FilterOptions;

  onUpdateHospitalDetailSettlement: (id: number, values: HospitalDetailSettlementUpdateREQ) => void;
};
export const CPH_SettlementDetailColumns = ({
  onUpdateHospitalDetailSettlement,
  settlementMethodOptions,
  id,
}: CPH_SettlementDetailColumns): TableColumnsType<HospitalDetailSettlementDTO> => {
  const { t: tPartnerHospital } = useTranslation('collaborating_partner_hospital');
  const { t: tCommon } = useTranslation('common');

  return [
    {
      title: tPartnerHospital('detail.settlement_details.table.branch'),
      dataIndex: 'branchName',
      className: 'whitespace-nowrap',
      width: 80,
      sorter: true,
      onCell: () => ({ className: 'disabled-column' }),
    },
    {
      title: tPartnerHospital('detail.settlement_details.table.funeral_number'),
      dataIndex: 'funeralCode',
      sorter: true,
      width: 100,
      render: (value: string, record: HospitalDetailSettlementDTO) => <ReservationCode code={value} id={record.reservationId} />,
      onCell: () => ({ className: 'disabled-column' }),
    },
    {
      title: tPartnerHospital('detail.settlement_details.table.funeral_date'),
      dataIndex: 'funeralDate',
      width: 100,
      sorter: true,
      onCell: () => ({ className: 'disabled-column' }),
    },
    {
      title: <EditableHeader title={tPartnerHospital('detail.settlement_details.table.settlement_method')} />,
      dataIndex: 'hospitalEntitlementId',
      width: 110,
      sorter: true,
      render: (value, record) =>
        record.canTakeAction ? (
          <CustomSelect
            options={settlementMethodOptions}
            variant='borderless'
            defaultValue={value}
            onChange={handleChangeUpdate<HospitalDetailSettlementUpdateREQ>(
              record.hospitalSettlementId,
              'hospitalEntitlementId',
              value,
              onUpdateHospitalDetailSettlement,
            )}
          />
        ) : (
          record.hospitalEntitlement?.name
        ),
      onCell: (record) => ({ className: record.canTakeAction ? '' : 'disabled-column' }),
    },
    {
      title: <EditableHeader title={tPartnerHospital('detail.settlement_details.table.amount')} />,
      dataIndex: 'amount',
      width: 100,
      sorter: true,
      render: (value: number, record) =>
        record.canTakeAction ? (
          <CustomNumericFormat
            defaultValue={value}
            onBlur={handleNumericUpdate<HospitalDetailSettlementUpdateREQ>(
              record.hospitalSettlementId,
              'amount',
              value,
              onUpdateHospitalDetailSettlement,
            )}
          />
        ) : (
          value
        ),
      onCell: (record) => ({ className: record.canTakeAction ? '' : 'disabled-column' }),
    },
    {
      title: <EditableHeader title={tPartnerHospital('detail.settlement_details.table.settlement_date')} />,
      dataIndex: 'settlementDate',
      width: 130,
      sorter: true,
      render: (value: Dayjs, record) => (
        <CustomDatePicker
          defaultValue={value}
          onChange={handleChangeDateUpdate<HospitalDetailSettlementUpdateREQ>(
            record.hospitalSettlementId,
            'settlementDate',
            onUpdateHospitalDetailSettlement,
          )}
        />
      ),
    },
    {
      title: <EditableHeader title={tCommon('table.memo')} />,
      dataIndex: 'memo',
      render: (text: string) => (
        <CustomTextInput
          type={InputEnum.TEXT}
          defaultValue={text}
          onBlur={handleBlurUpdate<HospitalDetailSettlementUpdateREQ>(id, 'memo', text, onUpdateHospitalDetailSettlement)}
          className='min-w-[100px]'
        />
      ),
    },
  ];
};
