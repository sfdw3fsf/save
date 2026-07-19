import CustomText from '@/components/common/Text';
import { ICONS } from '@/theme';
import { CollaboratingRegion } from '@/utils/types/collaborating/partner-hospital.type';
import { CheckOutlined } from '@ant-design/icons';
import type { CascaderProps } from 'antd';
import { Cascader } from 'antd';
import Image from 'next/image';
import { Dispatch, ReactNode, SetStateAction } from 'react';
import CustomIconButton from './CustomIconButton';

export type OptionCascader = {
  value: string | number | null;
  label: string | ReactNode;
  children?: OptionCascader[];
  selected?: boolean;
};

type CustomCascaderProps = {
  selectedRegion: CollaboratingRegion[];
  setSelectedRegion: Dispatch<SetStateAction<CollaboratingRegion[]>>;
  options: OptionCascader[];
  showSelectedList?: boolean;
  onChange: (value: CollaboratingRegion[]) => void;
  onRemove: (value: CollaboratingRegion) => void;
};

const RegionCascader = ({
  selectedRegion,
  setSelectedRegion,
  options,
  showSelectedList = false,
  onChange,
  onRemove,
}: CustomCascaderProps) => {
  const value = selectedRegion.map(({ province, municipality }) => [province, municipality]);
  const selectedMunicipalities = selectedRegion.map(({ municipality }) => municipality);
  const selectedProvinces = selectedRegion.map(({ province }) => province);

  const handleChange: CascaderProps<OptionCascader, 'value', true>['onChange'] = (value) => {
    const hasResetProvince = value.some((item) => item[0] === -1);

    if (hasResetProvince) {
      setSelectedRegion([]);
      onChange([]);
      return;
    }

    const newSelection = value.flatMap((item) => {
      if (item.length > 1) {
        const [province, municipality] = item;
        return {
          province: Number(province),
          municipality: Number(municipality),
        };
      } else {
        const [province] = item;
        const provinceOption = options.find((option) => option.value === province);
        return (
          provinceOption?.children?.map((child) => ({
            province: Number(province),
            municipality: Number(child.value),
          })) || []
        );
      }
    });
    setSelectedRegion(newSelection);
    onChange(newSelection);
  };

  const renderLabel = (label: string, isSelected: boolean) => {
    const isTotalLabel = label.includes('전체');

    if (isTotalLabel) {
      const match = label.match(/(전체)\s*\((\d+)\s*\/\s*(\d+)\)/);

      if (match) {
        const mainText = match[1];
        const numbers = match[2];
        const total = match[3];

        return (
          <div className='flex items-center'>
            <CustomText variant='label-1-normal' className='!font-bold !text-primary-1000'>
              {mainText}
            </CustomText>
            <CustomText variant='label-1-normal' className='ml-1'>
              <span className='!text-primary-1000 !text-sm !font-semibold'>(</span>
              <span className='!text-orange-500 !text-sm !font-semibold'>
                {numbers} / <span className='!text-primary-1000 '>{total}</span>
              </span>
              <span className='!text-primary-1000 !text-sm !font-semibold'>)</span>
            </CustomText>
            {isSelected && <CheckOutlined className='!text-primary-1000 ml-2' />}
          </div>
        );
      }
    }

    return (
      <div className='flex items-center'>
        <CustomText variant='label-1-normal' className={`${isSelected && '!font-semibold !text-primary-1000'}`}>
          {label}
        </CustomText>
        {isSelected && <CheckOutlined className='!text-primary-1000 ml-2' />}
      </div>
    );
  };

  const enhancedOptions = options.map((option) => ({
    ...option,
    label: renderLabel(option.label as string, selectedProvinces.includes(Number(option.value))),
    children: option.children?.map((child) => ({
      ...child,
      label: renderLabel(child.label as string, selectedMunicipalities.includes(Number(child.value))),
    })),
  }));

  const removeRegion = (regionToRemove: CollaboratingRegion) => {
    setSelectedRegion((prevRegions) =>
      prevRegions.filter(
        (region) => region.province !== regionToRemove.province || region.municipality !== regionToRemove.municipality,
      ),
    );
    onRemove(regionToRemove);
  };
  return (
    <>
      <Cascader.Panel className='!h-[190px] w-full' value={value} multiple options={enhancedOptions} onChange={handleChange} />
      {showSelectedList && (
        <div className='mt-4 w-full flex gap-1 overflow-x-auto'>
          {selectedRegion.map((region) => {
            const provinceIndex = options.findIndex((item) => item.value === region.province);
            const provinceLabel = (options[provinceIndex]?.label as string).replace(/\s*\(\d+\)$/, '');

            const municipalityLabel = (
              options[provinceIndex].children?.find((item) => item.value === region.municipality)?.label as string
            ).replace(/\s*\(\d+\)$/, '');
            return (
              <div key={region.municipality} className='h-[24px] flex items-center'>
                <CustomText
                  variant='caption-1'
                  className='!text-neutral-80 whitespace-nowrap'>{`${provinceLabel}>${municipalityLabel}`}</CustomText>
                <CustomIconButton
                  icon={<Image src={ICONS.CLOSE} alt='Remove' width={20} height={20} />}
                  onClick={() => removeRegion(region)}
                />
              </div>
            );
          })}
        </div>
      )}
    </>
  );
};

export default RegionCascader;
