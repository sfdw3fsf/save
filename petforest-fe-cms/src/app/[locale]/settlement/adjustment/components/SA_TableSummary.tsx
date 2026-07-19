import CustomText from '@/components/common/Text';
import useResize from '@/hook/useResize';
import { SettlementAdjustmentListDTO } from '@/store/settlement/dto/settlement.dto';
import { settlementAdjustmentListSummary } from '@/store/settlement/settlement.service';
import { Table } from 'antd';
import { useMemo } from 'react';
import { useTranslation } from 'react-i18next';
import { NumericFormat } from 'react-number-format';
import { twMerge } from 'tailwind-merge';

type SA_TableSummaryProps = {
  data?: readonly SettlementAdjustmentListDTO[];
};

const SA_TableSummary = ({ data }: SA_TableSummaryProps) => {
  const { t: tSettlement } = useTranslation('settlement');
  const { isResponsive } = useResize();

  const summary = useMemo(() => settlementAdjustmentListSummary(data), [data]);

  const render = (amount: number) => {
    return <NumericFormat value={amount} displayType='text' thousandSeparator />;
  };

  const bordered = 'border-t-[1px] border-neutral-60';

  return (
    <Table.Summary.Row>
      <Table.Summary.Cell index={0} colSpan={!isResponsive ? 5 : 2} className={twMerge('ant-summarize-total', bordered)}>
        <CustomText variant='label-1-normal' className='!text-neutral-80'>
          {tSettlement('table.total')}
        </CustomText>
      </Table.Summary.Cell>
      {isResponsive && (
        <>
          <div className={bordered}></div>
          <Table.Summary.Cell index={2} className={bordered}></Table.Summary.Cell>
          <Table.Summary.Cell index={3} className={bordered}></Table.Summary.Cell>
        </>
      )}

      <Table.Summary.Cell index={!isResponsive ? 1 : 4} className={bordered}>
        {render(summary.basicAmount)}
      </Table.Summary.Cell>
      <Table.Summary.Cell index={!isResponsive ? 2 : 5} className={bordered}>
        {render(summary.productAmount)}
      </Table.Summary.Cell>
      <Table.Summary.Cell index={!isResponsive ? 3 : 6} className={bordered}>
        {render(summary.additionalCost.transport)}
      </Table.Summary.Cell>
      <Table.Summary.Cell index={!isResponsive ? 4 : 7} className={bordered}>
        {render(summary.additionalCost.overweight)}
      </Table.Summary.Cell>
      <Table.Summary.Cell index={!isResponsive ? 5 : 8} className={bordered}>
        {render(summary.additionalCost.columbarium)}
      </Table.Summary.Cell>
      <Table.Summary.Cell index={!isResponsive ? 6 : 9} className={bordered}>
        {render(summary.additionalCost.temporaryPlacement)}
      </Table.Summary.Cell>
      <Table.Summary.Cell index={!isResponsive ? 7 : 10} className={bordered}>
        {render(summary.additionalCost.carbonPlate)}
      </Table.Summary.Cell>
      <Table.Summary.Cell index={!isResponsive ? 8 : 11} className={bordered}>
        {render(summary.additionalCost.otherAdditionalCost)}
      </Table.Summary.Cell>
      <Table.Summary.Cell index={!isResponsive ? 9 : 12} className={bordered}></Table.Summary.Cell>
      <Table.Summary.Cell index={!isResponsive ? 10 : 13} className={bordered}>
        {render(summary.discountAmount)}
      </Table.Summary.Cell>
      <Table.Summary.Cell index={!isResponsive ? 11 : 14} className={bordered}>
        {render(summary.usageCoupon)}
      </Table.Summary.Cell>
      <Table.Summary.Cell index={!isResponsive ? 12 : 15} className={bordered}>
        {render(summary.remainingAmount)}
      </Table.Summary.Cell>
      <Table.Summary.Cell index={!isResponsive ? 13 : 16} className={bordered}>
        {render(summary.totalAmount)}
      </Table.Summary.Cell>
      <Table.Summary.Cell index={!isResponsive ? 14 : 17} className={bordered}>
        <CustomText variant='label-1-normal'>
          <span>
            {`• ${tSettlement('payment_method.cash')}: `}
            {render(summary.settlementMethod.cash)}
          </span>
          <br />
          <span>
            {`• ${tSettlement('payment_method.account')}: `}
            {render(summary.settlementMethod.accountAmount)}
          </span>
          <br />
          <span>
            {`• ${tSettlement('payment_method.card')}: `}
            {render(summary.settlementMethod.card)}
          </span>
        </CustomText>
      </Table.Summary.Cell>
      <Table.Summary.Cell index={!isResponsive ? 15 : 18} className={bordered}>
        {render(summary.settlementMethod.prePaid)}
      </Table.Summary.Cell>
      <Table.Summary.Cell index={!isResponsive ? 16 : 19} className={bordered}></Table.Summary.Cell>
      <Table.Summary.Cell index={!isResponsive ? 17 : 20} className={bordered}></Table.Summary.Cell>
    </Table.Summary.Row>
  );
};

export default SA_TableSummary;
