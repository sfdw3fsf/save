import CustomText from '@/components/common/Text';
import { useHospitalSettlementMethod } from '@/hook/setting/useHospitalSettlementMethod';
import { hospitalSettlementSummaryToTotalRow } from '@/store/collaborating-hospital/collaborating-hospital.service';
import { HospitalSettlementSummaryDTO } from '@/store/collaborating-hospital/dto/collaborating-hospital.dto';
import { Table } from 'antd';
import { useMemo } from 'react';
import { useTranslation } from 'react-i18next';
import { twMerge } from 'tailwind-merge';

type CHS_SummaryProps = {
  data: readonly HospitalSettlementSummaryDTO[];
};
const CHS_Summary = ({ data }: CHS_SummaryProps) => {
  const { t: tCommon } = useTranslation('common');
  const { settlementMethodOptions } = useHospitalSettlementMethod();

  const summaryData = useMemo(() => {
    return hospitalSettlementSummaryToTotalRow(data as HospitalSettlementSummaryDTO[]);
  }, [data]);

  const bordered = 'border-t-[1px] border-neutral-60';

  return (
    <Table.Summary.Row>
      <Table.Summary.Cell index={0} colSpan={2} className={twMerge('ant-summarize-total', bordered)}>
        <CustomText variant='label-1-normal' className='!text-neutral-80'>
          {tCommon('table.total')}
        </CustomText>
      </Table.Summary.Cell>
      {settlementMethodOptions.map((item, index) => (
        <>
          <Table.Summary.Cell index={index + 1} className={twMerge('ant-column-center', bordered)}>
            {summaryData[item.value] ? summaryData[item.value][0].toLocaleString() : 0}
          </Table.Summary.Cell>
          <Table.Summary.Cell index={index + 2} className={twMerge('ant-column-center', bordered)}>
            {summaryData[item.value] ? summaryData[item.value][1].toLocaleString() : 0}
          </Table.Summary.Cell>
        </>
      ))}
    </Table.Summary.Row>
  );
};

export default CHS_Summary;
