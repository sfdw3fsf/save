import CustomText from '@/components/common/Text';
import { hospitalDeliveryDtoToSummary } from '@/store/collaborating-hospital/collaborating-hospital.service';
import { HospitalDeliverySummaryListDTO } from '@/store/collaborating-hospital/dto/collaborating-hospital.dto';
import { Table } from 'antd';
import { useMemo } from 'react';
import { useTranslation } from 'react-i18next';
import { twMerge } from 'tailwind-merge';

type CDPM_SummaryProps = {
  data: readonly HospitalDeliverySummaryListDTO[];
};
const CDPM_Summary = ({ data }: CDPM_SummaryProps) => {
  const { t: tCommon } = useTranslation('common');

  const summaryData = useMemo(() => {
    return hospitalDeliveryDtoToSummary(data);
  }, [data]);

  const bordered = 'border-t-[1px] border-neutral-60';

  return (
    <Table.Summary.Row>
      <Table.Summary.Cell index={0} colSpan={3} className={twMerge('ant-summarize-total', bordered)}>
        <CustomText variant='label-1-normal' className='!text-neutral-80'>
          {tCommon('table.total')}
        </CustomText>
      </Table.Summary.Cell>
      <Table.Summary.Cell index={1} className={bordered}>
        {summaryData.numOfFunerals.toLocaleString()}
      </Table.Summary.Cell>
      <Table.Summary.Cell index={2} className={twMerge('ant-column-center', bordered)}>
        {summaryData.requestedBrochure.toLocaleString()}
      </Table.Summary.Cell>
      <Table.Summary.Cell index={3} className={twMerge('ant-column-center', bordered)}>
        {summaryData.requestedBigTransportCase.toLocaleString()}
      </Table.Summary.Cell>
      <Table.Summary.Cell index={4} className={twMerge('ant-column-center', bordered)}>
        {summaryData.requestedSmallTransportCase.toLocaleString()}
      </Table.Summary.Cell>
      <Table.Summary.Cell index={5} className={twMerge('ant-column-center', bordered)}>
        {summaryData.deliveredBrochure.toLocaleString()}
      </Table.Summary.Cell>
      <Table.Summary.Cell index={6} className={twMerge('ant-column-center', bordered)}>
        {summaryData.deliveredBigTransportCase.toLocaleString()}
      </Table.Summary.Cell>
      <Table.Summary.Cell index={7} className={twMerge('ant-column-center', bordered)}>
        {summaryData.deliveredSmallTransportCase.toLocaleString()}
      </Table.Summary.Cell>
    </Table.Summary.Row>
  );
};

export default CDPM_Summary;
