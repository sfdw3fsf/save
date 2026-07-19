import { CategoryInfo } from '@/components/common/CountInfoCard';
import { ListData } from '@/components/common/ListData';
import CustomText from '@/components/common/Text';
import useResize from '@/hook/useResize';
import { Card, Divider } from 'antd';
import { CDPM_SummaryCountInfoItem } from './CDPM_SummaryCountInfoItem';

export type CountInfoCardChildrenValue = {
  label?: string;
  value: CategoryInfo[];
};

type CDPM_CountInfoCardProps = {
  title: string;
  value: number | string;
  childrenValue: CountInfoCardChildrenValue[];
  className?: string;
  numberOfColumns?: number;
  numberOfColumnsResponsive?: number;
};
const CDPM_SummaryCountInfoCard = ({
  title,
  value,
  childrenValue,
  className,
  numberOfColumns = 4,
  numberOfColumnsResponsive = 2,
}: CDPM_CountInfoCardProps) => {
  const { isResponsive } = useResize();
  const numberOfColumnList = isResponsive ? numberOfColumnsResponsive : numberOfColumns;

  return (
    <Card className={`p-3 inline-block  w-auto ${className}`}>
      <div className='flex items-center'>
        <CustomText variant='label-1-normal' className='mr-1.5'>
          {title}
        </CustomText>
        <CustomText variant='heading-2' className='!text-primary-800'>
          {value}
        </CustomText>
      </div>
      <div className='flex flex-col lg:flex-row'>
        {childrenValue.map((children, index) => (
          <div className='mt-[8px] lg:mt-[12px] flex' key={children.label}>
            <div className='flex flex-col w-full'>
              <CustomText variant='label-1-normal' className='!font-medium !text-neutral-60'>
                {children.label}
              </CustomText>
              <ListData
                data={children.value}
                keyExtractor={(item: CategoryInfo) => item.title}
                renderItem={(item: CategoryInfo) => <CDPM_SummaryCountInfoItem title={item.title} value={item.value} />}
                styleList='mt-1 flex-1 flex-grow w-full !justify-between'
                styleContainer='flex flex-wrap'
                numberOfColumns={numberOfColumnList}
                noWrap={true}
                gap={16}
              />
            </div>
            {index !== childrenValue.length - 1 && (
              <Divider type='vertical' className='!mx-4 !h-[78px] !text-neutral-30 !hidden lg:!block' />
            )}
          </div>
        ))}
      </div>
    </Card>
  );
};

export default CDPM_SummaryCountInfoCard;
