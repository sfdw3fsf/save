import ColumnChart from '@/components/charts/ColumnChart';
import { StatisticBasicREQ } from '@/store/statistic/request/statistic.request';
import { Checkbox } from 'antd';
import { useTranslation } from 'react-i18next';
import StatisticCard from './StatisticCard';
import StatisticTotal from './StatisticTotal';

type StatisticBasicServicesProps = {
  title: string;
  data: number[];
  categories: (string | string[])[];
  showCheckbox?: boolean;
  unit?: string;
  total?: number | string;
  totalTitle?: string;
  totalUnit?: 'won' | 'cases';
  filter?: StatisticBasicREQ;
  onFilterChange?: (filter: StatisticBasicREQ) => void;
  onDownloadExcel?: () => void;
  checkboxs?: string[];
};

const StatisticServices = ({
  title,
  data,
  categories,
  showCheckbox = false,
  unit,
  total = 0,
  totalTitle,
  totalUnit = 'cases',
  filter,
  onFilterChange,
  onDownloadExcel,
  checkboxs,
}: StatisticBasicServicesProps) => {
  const { t: tEnum } = useTranslation('enum');

  return (
    <div className='flex-1'>
      <StatisticCard title={title} onDownloadExcel={onDownloadExcel}>
        <div className='mt-6 flex items-center'>
          <StatisticTotal title={totalTitle ?? 'Total'} value={total} totalUnit={totalUnit} />
        </div>

        <div className='mt-6'>
          <ColumnChart categories={categories} data={data} unit={unit} />
        </div>

        {showCheckbox && (
          <div className='flex gap-6 flex-wrap'>
            {checkboxs?.map((item) => (
              <Checkbox
                key={item}
                checked={filter?.funeralServiceNames?.includes(item)}
                onClick={() => {
                  if (!onFilterChange || !filter) return;
                  if (filter?.funeralServiceNames?.includes(item)) {
                    onFilterChange({
                      ...filter,
                      funeralServiceNames: filter?.funeralServiceNames?.filter((id) => id !== item),
                    });
                  } else {
                    onFilterChange({
                      ...filter,
                      funeralServiceNames: filter?.funeralServiceNames ? [...(filter?.funeralServiceNames || []), item] : [item],
                    });
                  }
                }}>
                {item}
              </Checkbox>
            ))}
          </div>
        )}
      </StatisticCard>
    </div>
  );
};

export default StatisticServices;
