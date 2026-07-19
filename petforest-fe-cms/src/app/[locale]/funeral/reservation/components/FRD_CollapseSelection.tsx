'use client';
import CustomText from '@/components/common/Text';
import { ReservationDetailDTO } from '@/store/funeral/reservation/dto/reservation.dto';
import { serviceKeyMapping, ServiceKeyType } from '@/utils/types/reservation.type';
import { Checkbox, Collapse, Divider } from 'antd';
import { CollapseProps } from 'antd/lib';
import { useEffect, useState } from 'react';
import { useFormContext, useWatch } from 'react-hook-form';
import { useTranslation } from 'react-i18next';
import './custom-collapse.scss';
import { FRD_ColumbariumnService } from './FRD_ColumbariumnService';
import { FRD_LucetteService } from './FRD_LucetteService';
import { FRD_NewAdditionService } from './FRD_NewAdditionService';

const FRD_CollapseSelection = () => {
  const { t: tReservation } = useTranslation('funeral_reservation');
  const { control, setValue } = useFormContext<ReservationDetailDTO>();
  const formServiceValue = useWatch<ReservationDetailDTO>({
    control,
    name: 'serviceInfo',
  });
  const [activeKeys, setActiveKeys] = useState<number[]>([]);

  const handleCheckboxChange = (key: number, checked: boolean) => {
    const serviceName = Object.entries(serviceKeyMapping).find(([, value]) => value === key)?.[0] as ServiceKeyType | undefined;

    if (!serviceName) return;

    setValue(`serviceInfo.${serviceName}.isActive`, checked);
    setActiveKeys((prev) => (checked ? [...prev, key] : prev.filter((k) => k !== key)));
  };

  const renderCustomHeader = (title: string, key: number) => (
    <div className='inline-flex items-center overflow-hidden gap-3 w-full pb-4'>
      <CustomText variant='label-1-normal' className='whitespace-nowrap'>
        {title}
      </CustomText>
      <Checkbox checked={activeKeys.includes(key)} onChange={(e) => handleCheckboxChange(key, e.target.checked)} />
      <Divider variant='dashed' className='!text-neutral-50 !border-t-2 !border-dashed !m-0' />
    </div>
  );

  useEffect(() => {
    if (!formServiceValue) return;

    const activeKeys = Object.keys(serviceKeyMapping)
      .filter((key) => {
        const serviceKey = key as ServiceKeyType;
        const serviceValue = (formServiceValue as Record<ServiceKeyType, { isActive?: boolean } | undefined>)[serviceKey];
        return serviceValue?.isActive;
      })
      .map((key) => serviceKeyMapping[key as ServiceKeyType]);

    setActiveKeys(activeKeys);
  }, [formServiceValue]);

  const items: CollapseProps['items'] = [
    {
      key: 1,
      showArrow: false,
      label: renderCustomHeader(tReservation('detail.new_additon.title'), 1),
      children: <FRD_NewAdditionService />,
    },
    {
      key: 2,
      showArrow: false,
      label: renderCustomHeader(tReservation('detail.lucette.title'), 2),
      children: <FRD_LucetteService />,
    },
    {
      key: 3,
      showArrow: false,
      label: renderCustomHeader(tReservation('detail.columbarium.title'), 3),
      children: <FRD_ColumbariumnService />,
    },
  ];

  return <Collapse activeKey={activeKeys} ghost items={items}></Collapse>;
};

export default FRD_CollapseSelection;
