import CustomRangePicker from '@/components/common/CustomRangePicker';
import { useBranch } from '@/hook/branch/useBranch';
import { StatisticBasicREQ } from '@/store/statistic/request/statistic.request';
import { Checkbox } from 'antd';
import dayjs from 'dayjs';

type StatisticFilterProps = {
  filter: StatisticBasicREQ;
  onFilterChange: (filter: StatisticBasicREQ) => void;
};

const StatisticFilter = ({ filter, onFilterChange }: StatisticFilterProps) => {
  const { branchOptions } = useBranch();

  return (
    <div className='flex justify-between mt-[31px] items-center'>
      <div className='flex gap-6 '>
        {branchOptions.map((branch) => (
          <Checkbox
            key={branch.value}
            checked={filter.branchIds.includes(branch.value)}
            onClick={() => {
              if (filter.branchIds.includes(branch.value)) {
                onFilterChange({
                  ...filter,
                  branchIds: filter.branchIds.filter((id) => id !== branch.value),
                });
              } else {
                onFilterChange({
                  ...filter,
                  branchIds: [...filter.branchIds, branch.value],
                });
              }
            }}>
            {branch.label}
          </Checkbox>
        ))}
      </div>

      <CustomRangePicker
        classNameLabel='flex-1 min-w-[150px] lg:flex-none'
        defaultValue={[dayjs().startOf('month').valueOf(), dayjs().valueOf()]}
        onChange={(values) => {
          values[0] &&
            values[1] &&
            onFilterChange({
              ...filter,
              startDate: values[0],
              endDate: values[1],
            });
        }}
      />
    </div>
  );
};

export default StatisticFilter;
