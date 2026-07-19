import CustomButton from '@/components/common/CustomButton';
import CustomNumericFormat from '@/components/common/CustomNumericFormat';
import { CustomTextInput } from '@/components/common/CustomTextInput';
import Status from '@/components/common/Status';
import EditableHeader from '@/components/common/table/EditableHeader';
import CustomText from '@/components/common/Text';
import ReservationCode from '@/components/table/ReservationCode';
import useResize from '@/hook/useResize';
import { SettlementAdjustmentListDTO } from '@/store/settlement/dto/settlement.dto';
import { SettlementAdjustmentListUpdateREQ } from '@/store/settlement/request/settlement.request';
import { COLORS } from '@/theme';
import { InputEnum } from '@/utils/enums/input.enum';
import { ENUM_STATUS } from '@/utils/enums/status.enum';
import { helperFilter } from '@/utils/helpers/common.helper';
import { orUndefined } from '@/utils/parser/io.parser';
import { handleBlurUpdate, handleNumericUpdate } from '@/utils/utils/inline-update.util';
import { PlusOutlined } from '@ant-design/icons';
import { TableColumnsType } from 'antd';
import { useMemo } from 'react';
import { useTranslation } from 'react-i18next';
import { NumericFormat } from 'react-number-format';

type SA_ColumnsProps = {
  onListUpdate: (id: number, body: SettlementAdjustmentListUpdateREQ) => void;
  onRecordSelect?: (record: SettlementAdjustmentListDTO) => void;
};

export const SA_Columns = ({ onListUpdate, onRecordSelect }: SA_ColumnsProps): TableColumnsType<SettlementAdjustmentListDTO> => {
  const { t: tSettlement } = useTranslation('settlement');
  const { t: tEnum } = useTranslation('enum');
  const { t: tCommon } = useTranslation('common');

  const { isResponsive } = useResize();

  const statusFilter = helperFilter('status', ENUM_STATUS, tEnum);

  const columns = useMemo(() => {
    return [
      {
        title: tSettlement('table.status'),
        dataIndex: 'status',
        width: 100,
        fixed: !isResponsive && 'left',
        filters: statusFilter,
        render: (status) =>
          status && <Status isSuccess={status === ENUM_STATUS.COMPLETE} status={tEnum(`status.${status}` as any)} />,
      },
      {
        title: tSettlement('table.date'),
        dataIndex: 'funeralDate',
        width: 140,
        fixed: !isResponsive && 'left',
        sorter: true,
      },
      {
        title: tSettlement('table.number'),
        dataIndex: 'funeralCode',
        width: 140,
        fixed: !isResponsive && 'left',
        sorter: true,
        render: (value: string, record: SettlementAdjustmentListDTO) => <ReservationCode code={value} id={record.funeralId} />,
      },
      {
        title: tSettlement('table.basic_price'),
        dataIndex: 'basicAmount',
        width: 140,
        sorter: true,
        render: (value) => <NumericFormat value={value} displayType='text' thousandSeparator />,
      },
      {
        title: <EditableHeader title={tSettlement('table.product_price')} />,
        dataIndex: 'productAmount',
        width: 140,
        sorter: true,
        render: (value, record: SettlementAdjustmentListDTO) => (
          <CustomNumericFormat
            onBlur={handleNumericUpdate<SettlementAdjustmentListUpdateREQ>(record.id, 'productAmount', value, onListUpdate)}
            defaultValue={value}
          />
        ),
      },
      {
        title: <EditableHeader title={tSettlement('table.additional_cost')} />,
        className: 'ant-table-merged-coulumn',
        children: [
          {
            title: tSettlement('table.transport'),
            dataIndex: 'transport',
            width: 140,
            sorter: true,
            render: (_, record: SettlementAdjustmentListDTO) => {
              const value = orUndefined(record.additionalCost?.transport);
              return (
                <CustomNumericFormat
                  onBlur={handleNumericUpdate<SettlementAdjustmentListUpdateREQ>(record.id, 'transport', value, onListUpdate)}
                  defaultValue={value}
                />
              );
            },
          },
          {
            title: tSettlement('table.overweight_fee'),
            dataIndex: 'overweight',
            width: 140,
            sorter: true,
            render: (_, record: SettlementAdjustmentListDTO) => {
              const value = orUndefined(record.additionalCost?.overweight);
              return (
                <CustomNumericFormat
                  onBlur={handleNumericUpdate<SettlementAdjustmentListUpdateREQ>(record.id, 'overweight', value, onListUpdate)}
                  defaultValue={value}
                />
              );
            },
          },
          {
            title: tSettlement('table.columbarium'),
            dataIndex: 'columbarium',
            width: 140,
            sorter: true,
            render: (_, record: SettlementAdjustmentListDTO) => {
              const value = orUndefined(record.additionalCost?.columbarium);
              return (
                <CustomNumericFormat
                  onBlur={handleNumericUpdate<SettlementAdjustmentListUpdateREQ>(record.id, 'columbarium', value, onListUpdate)}
                  defaultValue={value}
                />
              );
            },
          },
          {
            title: tSettlement('table.temporary_storage'),
            dataIndex: 'temporaryPlacement',
            width: 140,
            sorter: true,
            render: (_, record: SettlementAdjustmentListDTO) => {
              const value = orUndefined(record.additionalCost?.temporaryPlacement);
              return (
                <CustomNumericFormat
                  onBlur={handleNumericUpdate<SettlementAdjustmentListUpdateREQ>(
                    record.id,
                    'temporaryPlacement',
                    value,
                    onListUpdate,
                  )}
                  defaultValue={value}
                />
              );
            },
          },
          {
            title: tSettlement('table.carbon_plate'),
            dataIndex: 'carbonPlate',
            width: 140,
            sorter: true,
            render: (_, record: SettlementAdjustmentListDTO) => {
              const value = orUndefined(record.additionalCost?.carbonPlate);
              return (
                <CustomNumericFormat
                  onBlur={handleNumericUpdate<SettlementAdjustmentListUpdateREQ>(record.id, 'carbonPlate', value, onListUpdate)}
                  defaultValue={value}
                />
              );
            },
          },
          {
            title: tSettlement('table.others'),
            dataIndex: 'otherAdditionalCost',
            width: 140,
            sorter: true,
            render: (_, record: SettlementAdjustmentListDTO) => {
              const value = orUndefined(record.additionalCost?.otherAdditionalCost);
              return (
                <CustomNumericFormat
                  onBlur={handleNumericUpdate<SettlementAdjustmentListUpdateREQ>(
                    record.id,
                    'otherAdditionalCost',
                    value,
                    onListUpdate,
                  )}
                  defaultValue={value}
                />
              );
            },
          },
        ],
      },
      {
        title: <EditableHeader title={tSettlement('table.additional_discount_details')} />,
        dataIndex: 'reasonOfDiscount',
        width: 220,
        sorter: true,
        render: (value: string, record: SettlementAdjustmentListDTO) => (
          <CustomTextInput
            onBlur={handleBlurUpdate<SettlementAdjustmentListUpdateREQ>(record.id, 'reasonOfDiscount', value, onListUpdate)}
            type={InputEnum.TEXT}
            defaultValue={value}
          />
        ),
      },
      {
        title: <EditableHeader title={tSettlement('table.additional_discount_amount')} />,
        dataIndex: 'discountAmount',
        width: 220,
        sorter: true,
        render: (value, record: SettlementAdjustmentListDTO) => (
          <CustomNumericFormat
            onBlur={handleNumericUpdate<SettlementAdjustmentListUpdateREQ>(record.id, 'discountAmount', value, onListUpdate)}
            defaultValue={value}
          />
        ),
      },
      {
        title: tSettlement('table.coupon_used_amount'),
        dataIndex: 'usageCoupon',
        width: 140,
        sorter: true,
        render: (value) => <NumericFormat value={value} displayType='text' thousandSeparator />,
      },
      {
        title: tSettlement('table.outstanding_amount'),
        dataIndex: 'remainingAmount',
        width: 140,
        sorter: true,
        render: (value) => <NumericFormat value={value} displayType='text' thousandSeparator />,
      },
      {
        title: tSettlement('table.total_amount'),
        dataIndex: 'totalAmount',
        width: 140,
        sorter: true,
        render: (value) => <NumericFormat value={value} displayType='text' thousandSeparator />,
      },
      {
        title: <EditableHeader title={tSettlement('table.settlement_method_and_amount')} />,
        className: 'ant-table-merged-coulumn',
        children: [
          {
            title: tSettlement('table.payment_method'),
            dataIndex: 'cash',
            width: 200,
            sorter: true,
            render: (_, record: SettlementAdjustmentListDTO) => {
              const hasReservation = !!record.funeralCode;
              if (
                !record.settlementMethod ||
                (!record.settlementMethod.accountAmount && !record.settlementMethod.card && !record.settlementMethod.cash)
              ) {
                return hasReservation ? null : (
                  <div className='w-full flex justify-center items-center'>
                    <CustomButton
                      onClick={() => onRecordSelect?.(record)}
                      className='!text-primary-1000'
                      type='text'
                      icon={<PlusOutlined style={{ color: COLORS.primary[1000] }} />}>
                      {tCommon('button.add')}
                    </CustomButton>
                  </div>
                );
              }
              const { cash, card, accountAmount } = record.settlementMethod;
              return (
                <CustomText
                  className='cursor-text'
                  onClickCapture={() => {
                    if (hasReservation) return;
                    onRecordSelect?.(record);
                  }}
                  variant='label-1-normal'>
                  <span>
                    {`• ${tSettlement('payment_method.cash')}: `}
                    <NumericFormat value={cash} displayType='text' thousandSeparator />
                  </span>
                  <br />
                  <span>
                    {`• ${tSettlement('payment_method.account')}: `}
                    <NumericFormat value={accountAmount} displayType='text' thousandSeparator />
                  </span>
                  <br />
                  <span>
                    {`• ${tSettlement('payment_method.card')}: `}
                    <NumericFormat value={card} displayType='text' thousandSeparator />
                  </span>
                </CustomText>
              );
            },
          },
          {
            title: tSettlement('table.prepayment_other_companies'),
            dataIndex: 'prePaid',
            width: 140,
            sorter: true,
            render: (_, record: SettlementAdjustmentListDTO) => {
              const value = orUndefined(record.settlementMethod?.prePaid);
              return (
                <CustomNumericFormat
                  onBlur={handleNumericUpdate<SettlementAdjustmentListUpdateREQ>(record.id, 'prePaid', value, onListUpdate)}
                  defaultValue={value}
                />
              );
            },
          },
        ],
      },
      {
        title: <EditableHeader title={tSettlement('table.invoice_issued')} />,
        dataIndex: 'invoiceMemo',
        width: 220,
        sorter: true,
        render: (value: string, record: SettlementAdjustmentListDTO) => (
          <CustomTextInput
            onBlur={handleBlurUpdate<SettlementAdjustmentListUpdateREQ>(record.id, 'invoiceMemo', value, onListUpdate)}
            type={InputEnum.TEXT_AREA}
            defaultValue={value}
          />
        ),
      },
      {
        title: <EditableHeader title={tSettlement('table.notes')} />,
        dataIndex: 'memo',
        width: 220,
        sorter: true,
        render: (value: string, record: SettlementAdjustmentListDTO) => (
          <CustomTextInput
            onBlur={handleBlurUpdate<SettlementAdjustmentListUpdateREQ>(record.id, 'memo', value, onListUpdate)}
            type={InputEnum.TEXT_AREA}
            defaultValue={value}
          />
        ),
      },
    ] as TableColumnsType<SettlementAdjustmentListDTO>;
  }, [isResponsive]);
  return columns;
};
