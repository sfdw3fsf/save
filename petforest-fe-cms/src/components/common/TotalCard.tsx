import CustomText from '@/components/common/Text';
import { Card } from 'antd';
import { isEmpty } from 'lodash';
import { NumericFormat } from 'react-number-format';

export type ValueTotalCardProps = {
  text: string;
  count: number;
};

type TotalCardProps = {
  title: string;
  value: string | number | ValueTotalCardProps[];
  className?: string;
  numberClassName?: string;
  isRedNumber?: boolean;
  separator?: string;
  separatorClassName?: string;
  textClassName?: string;
  isSelected?: boolean;
  onSelect?: () => void;
};
const TotalCard = ({
  title,
  value,
  className,
  numberClassName,
  isRedNumber,
  textClassName,
  separator,
  separatorClassName,
  isSelected,
  onSelect,
}: TotalCardProps) => {
  const valueItem = typeof value === 'string' || typeof value === 'number' ? [{ text: '', count: value }] : value;

  return (
    <Card
      onClick={onSelect && onSelect}
      className={`p-3 flex  flex-col w-[150px] lg:w-[196px] cursor-pointer ${isSelected ? 'border-[1px] border-primary-900' : ''} ${className}`}>
      <div>
        <CustomText variant='label-1-normal'>{title}</CustomText>
      </div>
      <div className='mt-[6px] flex '>
        {valueItem.map((item, index) => (
          <div key={index}>
            <CustomText
              variant='heading-2'
              className={` ${isRedNumber ? '!text-red-semantic-600' : '!text-primary-800'} ${numberClassName}`}>
              <NumericFormat value={item.count} thousandSeparator displayType='text' />
            </CustomText>
            {!isEmpty(item.text) && (
              <span className={`text-[14px] ${isRedNumber ? '!text-red-semantic-600' : '!text-neutral-60'} ${textClassName}`}>
                {item.text}
              </span>
            )}
            {!isEmpty(separator) && index !== valueItem.length - 1 && (
              <span
                className={`text-[15px] mx-1 ${isRedNumber ? '!text-red-semantic-600' : '!text-neutral-60'} ${separatorClassName}`}>
                {separator}
              </span>
            )}
          </div>
        ))}
      </div>
    </Card>
  );
};

export default TotalCard;
