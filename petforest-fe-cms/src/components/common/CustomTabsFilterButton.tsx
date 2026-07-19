import { OptionItems } from '@/utils/types/option.type';
import clsx from 'clsx';
import { isNil } from 'lodash';
import { useEffect, useState } from 'react';
import CustomButton from './CustomButton';
import CustomText from './Text';

type CustomTabsFilterButtonProps = {
  optionTabs: OptionItems<number | string>;
  defaultValue?: string | number;
  onChangeTabs?: (value: string | number) => void;
  classname?: string;
};

export const CustomTabsFilterButton = ({
  optionTabs,
  defaultValue,
  onChangeTabs = () => {},
  classname,
}: CustomTabsFilterButtonProps) => {
  const [activeTabs, setActiveTabs] = useState<number | string>();

  const handleChangeTabs = (id: number | string) => {
    onChangeTabs(id);
    setActiveTabs(id);
  };

  useEffect(() => {
    const isValidTab = !isNil(defaultValue) && optionTabs.some((tab) => tab.value === defaultValue);
    if (isValidTab) {
      setActiveTabs(defaultValue);
      return;
    }
    setActiveTabs(optionTabs[0].value);
  }, [defaultValue, optionTabs]);

  return (
    <div className={`flex gap-2 justify-start overflow-x-auto lg:justify-center hide-scrollbar ${classname}`}>
      {optionTabs.map((item) => (
        <CustomButton
          onClick={() => handleChangeTabs(item.value)}
          type='text'
          className={clsx(
            `${activeTabs === item.value ? '!bg-neutral-90' : '!border-[1px] !border-neutral-40 '} !px-[15px] !py-1 !rounded-full`,
          )}
          key={item.value}>
          <CustomText
            className={clsx(`${activeTabs === item.value ? '!text-white' : '!text-neutral-60 '}`)}
            variant='label-1-normal'>
            {item.label}
          </CustomText>
        </CustomButton>
      ))}
    </div>
  );
};
