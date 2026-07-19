import CustomText from '@/components/common/Text';
import useRouterHandler from '@/hook/useRouterHandler';
import { COLORS } from '@/theme';
import { CurrentPlaceValue } from '@/utils/types/ossuary.type';
import { Divider } from 'antd';
import clsx from 'clsx';
import { useMemo } from 'react';

type OssuaryItemProps = {
  type?: 'red' | 'blue' | 'orange';
  value: number;
  place?: string;
  id: number;
  currentPlace: CurrentPlaceValue;
  setCurrentPlace?: (value: (prev: CurrentPlaceValue) => CurrentPlaceValue) => void;
};

const OssuaryItem = ({ type, value, place, currentPlace, setCurrentPlace, id }: OssuaryItemProps) => {
  const { handleURLMultiplePush } = useRouterHandler();
  const isSelected = useMemo(() => {
    return currentPlace.place?.toString() === place?.toString();
  }, [place, currentPlace.place]);

  const borderColor = useMemo(() => {
    return isSelected
      ? 'border-black'
      : type === 'red'
        ? 'border-error-200'
        : type === 'blue'
          ? 'border-blue-200'
          : type === 'orange'
            ? 'border-orange-50'
            : 'border-neutral-40';
  }, [type, place, currentPlace.place]);

  const backgroundColor = useMemo(() => {
    return type === 'red'
      ? 'bg-red-semantic-100'
      : type === 'blue'
        ? 'bg-blue-100'
        : type === 'orange'
          ? 'bg-orange-200'
          : 'bg-white';
  }, [type]);

  const textColor = useMemo(() => {
    return type === 'red'
      ? '!text-error-500'
      : type === 'blue'
        ? '!text-blue-500'
        : type === 'orange'
          ? '!text-orange-900'
          : '!text-black';
  }, [type]);

  const dividerColor = useMemo(() => {
    return type === 'red'
      ? COLORS.error[200]
      : type === 'blue'
        ? COLORS.blue[200]
        : type === 'orange'
          ? COLORS.orange[50]
          : undefined;
  }, [type]);

  const handleChangePlace = () => {
    if (setCurrentPlace && place !== undefined) {
      setCurrentPlace((prev: CurrentPlaceValue) => ({ ...prev, place }));
      handleURLMultiplePush({ place: place.toString(), placeId: id.toString() });
    }
  };

  return (
    <div
      className={clsx(
        `cursor-pointer w-[61px] h-[61px] flex flex-col justify-center items-center rounded-lg gap-[2px] px-3  ${isSelected ? 'border-[2px]' : 'border-[1px]'}  ${borderColor} ${backgroundColor}`,
      )}
      onClick={handleChangePlace}>
      <CustomText variant='label-1-normal' className={clsx(`${textColor} !font-medium whitespace-nowrap max-md:text-[11px]`)}>
        {place}
      </CustomText>

      {type && (
        <>
          <Divider style={{ borderColor: dividerColor }} className='!m-0' />

          <CustomText variant='label-1-normal' className={clsx(`${textColor} !font-medium whitespace-nowrap max-md:text-[11px]`)}>
            {value >= 0 ? `+${value}` : `${value}`}
          </CustomText>
        </>
      )}
    </div>
  );
};

export default OssuaryItem;
