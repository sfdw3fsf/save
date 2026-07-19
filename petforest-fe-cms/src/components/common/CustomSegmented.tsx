import { Segmented, SegmentedProps } from 'antd';
import { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';

type CustomSegmentedProps<T> = {
  options: T;
  path: string;
} & Omit<SegmentedProps<T>, 'options'>;
const CustomSegmented = <T,>({ value, onChange, options, path }: CustomSegmentedProps<T>) => {
  const { t: tEnum } = useTranslation('enum');

  const segmentedOptions = Object.values(options as any).map((item) => ({ label: tEnum(`${path}.${item}` as any), value: item }));

  const [segmentedValue, setSegmentedValue] = useState<T | undefined>();

  useEffect(() => {
    setSegmentedValue(value);
  }, [value]);

  return (
    <Segmented
      height={200}
      value={segmentedValue}
      onChange={(value) => {
        setSegmentedValue(value);
        onChange && onChange(value);
      }}
      options={segmentedOptions as T[]}
      className='!bg-neutral-20 p-1'
    />
  );
};

export default CustomSegmented;
