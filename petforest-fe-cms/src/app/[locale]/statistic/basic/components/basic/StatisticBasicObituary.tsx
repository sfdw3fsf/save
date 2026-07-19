import ColumnChart from '@/components/charts/ColumnChart';
import CustomButton from '@/components/common/CustomButton';
import { COLORS, ICONS } from '@/theme';
import { MY_ROUTE } from '@/utils/constants/route.constant';
import Image from 'next/image';
import { useRouter } from 'next/navigation';
import { useTranslation } from 'react-i18next';
import StatisticCard from '../../../components/StatisticCard';

type StatisticBasicObituaryProps = {
  title: string;
  data: number[];
  categories: (string | string[])[];
  onDownloadExcel?: () => void;
};
const StatisticBasicObituary = ({ title, data, categories, onDownloadExcel }: StatisticBasicObituaryProps) => {
  const { t: tStatistic } = useTranslation('statistic');
  const router = useRouter();

  return (
    <div className='flex-1'>
      <StatisticCard title={title} onDownloadExcel={onDownloadExcel}>
        <div className='mt-6 flex justify-end'>
          <CustomButton
            onClick={() => {
              window.open(MY_ROUTE.SETTLEMENT.SETTLEMENT_COUPON, '_blank', `noopener,noreferrer`);
            }}
            type='text'
            icon={<Image src={ICONS.ARROW_DETAIL_GREEN} width={20} alt='Arrow detail' color={COLORS.primary[1000]} />}
            className='mt-4 self-end !text-primary-1000'>
            {tStatistic('basic.coupon_list')}
          </CustomButton>
        </div>

        <div className='mt-6'>
          <ColumnChart categories={categories} data={data} />
        </div>
      </StatisticCard>
    </div>
  );
};

export default StatisticBasicObituary;
