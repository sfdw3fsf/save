import { ListData } from '@/components/common/ListData';
import CustomText from '@/components/common/Text';
import useResize from '@/hook/useResize';
import { Card } from 'antd';
import { CountInfoItem } from './CountInfoItem';

export type CategoryInfo = {
  title: string;
  value: number | string;
  isSelected?: boolean;
  onSelectProcess?: () => void;
};

type LucetteInfoCardProps = {
  title: string;
  value: number;
  childrenValue: CategoryInfo[];
  className?: string;
  numberOfColumns?: number;
  numberOfColumnsResponsive?: number;
};
const CountInfoCard = ({
  title,
  value,
  childrenValue,
  className,
  numberOfColumns = 4,
  numberOfColumnsResponsive = 2,
}: LucetteInfoCardProps) => {
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
      <ListData
        data={childrenValue}
        keyExtractor={(item: CategoryInfo) => item.title}
        renderItem={(item: CategoryInfo) => (
          <CountInfoItem
            title={item.title}
            value={item.value}
            isSelected={item.isSelected}
            onSelectProcess={item.onSelectProcess}
          />
        )}
        styleList='mt-[8px] md:mt-[12px] flex-1 flex-grow w-full !justify-between'
        styleContainer='flex flex-wrap'
        numberOfColumns={numberOfColumnList}
        noWrap={true}
        gap={16}
      />
    </Card>
  );
};

export default CountInfoCard;
