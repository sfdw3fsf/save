'use client';
import CustomText from '@/components/common/Text';
import { ReservationDetailDTO } from '@/store/funeral/reservation/dto/reservation.dto';
import { Checkbox, Collapse } from 'antd';
import { CollapseProps } from 'antd/lib';
import { useEffect, useState } from 'react';
import { useFormContext } from 'react-hook-form';
import { useTranslation } from 'react-i18next';
import './custom-collapse.scss';
import { FRD_TransportService } from './FRD_TransportService';

const FRD_FuneralServiceSelection = () => {
  const { getValues, setValue } = useFormContext<ReservationDetailDTO>();
  const funeralTransport = getValues('funeralInfo.isActiveTransport');
  const { t: tReservation } = useTranslation('funeral_reservation');
  const [activeCheckbox, setActiveCheckbox] = useState<boolean>(funeralTransport);

  const handleChangeCheckbox = (value: boolean) => {
    setActiveCheckbox(value);
    setValue('funeralInfo.isActiveTransport', value);
  };

  const renderCustomHeader = (title: string) => (
    <div
      className={`inline-flex items-center overflow-hidden gap-3 ${activeCheckbox ? 'bg-primary-850' : 'bg-neutral-20'} rounded-lg h-[38px] px-4`}>
      <CustomText variant='label-1-normal' className={`whitespace-nowrap ${activeCheckbox && '!text-white'}`}>
        {title}
      </CustomText>
      <Checkbox
        checked={activeCheckbox}
        style={
          {
            '--border-color': 'white',
          } as any
        }
        className='custom-checkbox'
        onChange={(e) => handleChangeCheckbox(e.target.checked)}
      />
    </div>
  );

  const items: CollapseProps['items'] = [
    {
      key: 1,
      showArrow: false,
      label: renderCustomHeader(tReservation('detail.transport.title')),
      children: <FRD_TransportService />,
    },
  ];

  useEffect(() => setActiveCheckbox(funeralTransport), [funeralTransport]);

  return <Collapse activeKey={activeCheckbox ? [1] : []} ghost items={items}></Collapse>;
};

export default FRD_FuneralServiceSelection;
