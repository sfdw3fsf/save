import CustomText from '@/components/common/Text';
import { SettlementAdjustmentAdditionalCostListDTO } from '@/store/settlement/dto/settlement.dto';
import { settlementAdjustmentAdditionalCostListSummary } from '@/store/settlement/settlement.service';
import { Table } from 'antd';
import { useMemo } from 'react';
import { useTranslation } from 'react-i18next';
import { NumericFormat } from 'react-number-format';
import { twMerge } from 'tailwind-merge';

type SA_AdditionalTableSummaryProps = {
  data?: readonly SettlementAdjustmentAdditionalCostListDTO[];
};

const SA_AdditionalTableSummary = ({ data }: SA_AdditionalTableSummaryProps) => {
  const { t: tSettlement } = useTranslation('settlement');

  const summary = useMemo(() => settlementAdjustmentAdditionalCostListSummary(data), [data]);

  const render = (amount: number) => {
    return <NumericFormat value={amount} displayType='text' thousandSeparator />;
  };

  const bordered = 'border-t-[1px] border-neutral-60';

  return (
    <Table.Summary.Row>
      <Table.Summary.Cell index={0} colSpan={3} className={twMerge('ant-summarize-total', bordered)}>
        <CustomText variant='label-1-normal' className='!text-neutral-80'>
          {tSettlement('table.total')}
        </CustomText>
      </Table.Summary.Cell>
      <Table.Summary.Cell index={1} className={bordered}>
        {render(summary.transport)}
      </Table.Summary.Cell>
      <Table.Summary.Cell index={2} className={bordered}>
        {render(summary.overweight)}
      </Table.Summary.Cell>
      <Table.Summary.Cell index={3} className={bordered}>
        {render(summary.columbarium)}
      </Table.Summary.Cell>
      <Table.Summary.Cell index={4} className={bordered}>
        {render(summary.temporaryPlacement)}
      </Table.Summary.Cell>
      <Table.Summary.Cell index={5} className={bordered}>
        {render(summary.carbonPlate)}
      </Table.Summary.Cell>
      <Table.Summary.Cell index={6} className={bordered}>
        {render(summary.otherAdditionalCost)}
      </Table.Summary.Cell>
    </Table.Summary.Row>
  );
};

export default SA_AdditionalTableSummary;
