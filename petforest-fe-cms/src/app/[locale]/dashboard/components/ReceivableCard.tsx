import CustomText from '@/components/common/Text';
import { helperFormatChartNumericData } from '@/utils/helpers/chart.helper';
import { Card, Flex } from 'antd';
import { useTranslation } from 'react-i18next';

type ReceivableCardProps = {
  total?: number;
};

const ReceivableCard = ({ total }: ReceivableCardProps) => {
  const { t: tDashboard } = useTranslation('dashboard');
  const { t: tCommon } = useTranslation('common');

  return (
    <Card className={`p-6 w-fit min-w-[142px]`}>
      <div className='flex flex-col gap-3'>
        <CustomText variant='label-1-normal'>{tDashboard('receivable')}</CustomText>
        <Flex wrap align='baseline' gap={4}>
          <CustomText variant='title-3' className='!text-error-semantic whitespace-nowrap'>
            {helperFormatChartNumericData(total)}
          </CustomText>
          <CustomText variant='label-1-normal' className='!text-neutral-70'>
            {tCommon('won')}
          </CustomText>
        </Flex>
      </div>
    </Card>
  );
};

export default ReceivableCard;
