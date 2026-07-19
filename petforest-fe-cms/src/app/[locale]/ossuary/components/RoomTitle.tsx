import AddButton from '@/components/common/AddButton';
import CustomText from '@/components/common/Text';
import { useOssuary } from '@/hook/ossuary/useOssuary';
import { OssuaryPlaceListDTO } from '@/store/ossuary/dto/ossuary.dto';
import { ossuaryStatusKeys } from '@/store/ossuary/ossuary.api';
import { COLORS } from '@/theme';
import { PLACE_STATUS } from '@/utils/enums/ossuary/ossuary-room.enum';
import { Segmented } from 'antd';
import { isNil } from 'lodash';
import { useSearchParams } from 'next/navigation';
import { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';

type RoomTitleProps = {
  onOpen: (index: null) => void;
  placeId: number;
  status?: PLACE_STATUS;
};
const RoomTitle = ({ onOpen, status, placeId }: RoomTitleProps) => {
  const { t: tOssuary } = useTranslation('ossuary');
  const searchParams = useSearchParams();
  const place = searchParams.get('place');

  const [selectedValue, setSelectedValue] = useState<number>(status === PLACE_STATUS.CHECK_OUT ? 1 : 0);

  const { onUpdateOssuaryStatus } = useOssuary<OssuaryPlaceListDTO>({ queryKey: ossuaryStatusKeys.detail(placeId ?? 0) });

  const options = [
    {
      label: tOssuary('check_in_complete'),
      value: 0,
    },
    {
      label: (
        <span style={{ color: selectedValue === 1 ? COLORS.red.semantic[600] : undefined }}>
          {tOssuary('check_out_complete')}
        </span>
      ),
      value: 1,
    },
  ];

  const handleChangeStatus = (value: number) => {
    setSelectedValue(value);
    onUpdateOssuaryStatus(value === 0 ? PLACE_STATUS.CHECK_IN : PLACE_STATUS.CHECK_OUT, [placeId]);
  };

  useEffect(() => setSelectedValue(status === PLACE_STATUS.CHECK_OUT ? 1 : 0), [status]);

  return (
    <div>
      <div className='flex justify-between  flex-wrap items-start md:items-center'>
        <div className='flex gap-4 items-center'>
          <CustomText variant='headline-1'>{`${searchParams.get('room')}${!isNil(place) ? `-${place}` : ''}`}</CustomText>

          <Segmented
            height={200}
            value={selectedValue}
            onChange={handleChangeStatus}
            options={options}
            className='!bg-neutral-20 p-1 hidden lg:block'></Segmented>
        </div>

        <AddButton onClick={() => onOpen(null)} />
      </div>

      <Segmented
        height={200}
        width={'100%'}
        value={selectedValue}
        onChange={handleChangeStatus}
        options={options}
        block
        className='!bg-neutral-20 p-1 block mt-4 lg:!hidden'></Segmented>
    </div>
  );
};

export default RoomTitle;
