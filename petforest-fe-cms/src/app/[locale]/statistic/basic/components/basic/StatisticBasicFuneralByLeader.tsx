import CustomButton from '@/components/common/CustomButton';
import CustomTable from '@/components/common/table/CustomTable';
import CustomText from '@/components/common/Text';
import { StatisticBasicManagerDTO } from '@/store/statistic/dto/statistic.dto';
import { DownOutlined, UpOutlined } from '@ant-design/icons';
import { useState } from 'react';
import { useTranslation } from 'react-i18next';
import StatisticCard from '../../../components/StatisticCard';
import { StatisticBasicFuneralColumns } from '../../data-columns/StatisticBasicFuneralColumns';

type StatisticBasicFuneralByLeaderProps = {
  data: StatisticBasicManagerDTO[] | undefined;
};

const StatisticBasicFuneralByLeader = ({ data }: StatisticBasicFuneralByLeaderProps) => {
  const { t: tStatistic } = useTranslation('statistic');
  const { t: tHome } = useTranslation('home');

  const [isExpanded, setIsExpanded] = useState<boolean>(false);

  return (
    <div>
      <StatisticCard
        customTitle={
          <div className='flex items-center gap-4'>
            <CustomText variant='heading-2'>{tStatistic('basic.funeral_leader')}</CustomText>
            <CustomButton
              type='ghost'
              className='!rounded-full'
              onClick={() => {
                setIsExpanded((pre) => !pre);
              }}>
              {isExpanded ? tHome('collapse.collapse') : tHome('collapse.expand')}
              {isExpanded ? <UpOutlined /> : <DownOutlined />}
            </CustomButton>
          </div>
        }
        isDownload={false}>
        {isExpanded && (
          <div className='mt-6'>
            <CustomTable<StatisticBasicManagerDTO>
              columns={StatisticBasicFuneralColumns({ tStatistic })}
              data={data || []}
              showIndex={false}
              showPaging={false}
            />
          </div>
        )}
      </StatisticCard>
    </div>
  );
};

export default StatisticBasicFuneralByLeader;
