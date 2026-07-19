'use client';
import { CustomTabsFilterButton } from '@/components/common/CustomTabsFilterButton';
import PageTitle from '@/components/common/page/PageTitle';
import { useOssuary } from '@/hook/ossuary/useOssuary';
import useRouterHandler from '@/hook/useRouterHandler';
import { OssuaryPlaceListDTO } from '@/store/ossuary/dto/ossuary.dto';
import { useSearchParams } from 'next/navigation';
import { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import OssuaryEntire from './components/OssuaryEntire';
import OssuaryRooms from './components/OssuaryRooms';

const page = () => {
  const { t: tOssuary } = useTranslation('ossuary');
  const { t: tCommon } = useTranslation('common');
  const searchParams = useSearchParams();
  const room = searchParams.get('room') || 'entire';
  const [activeRoom, setActiveRoom] = useState<string>(room);
  const { ossuaryRoomNameOptions } = useOssuary<OssuaryPlaceListDTO>({});

  const { handleURLPush } = useRouterHandler();

  const onChangeRoom = (activeRoom: string | number) => {
    setActiveRoom(activeRoom.toString());
    handleURLPush('room', activeRoom.toString(), false);
  };

  useEffect(() => {
    const room = searchParams.get('room');
    if (room) {
      setActiveRoom(room);
    }
  }, [searchParams]);

  return (
    <div>
      <PageTitle title={tOssuary('ossuary') as string} />
      <div className='mt-6 '>
        <CustomTabsFilterButton
          defaultValue={activeRoom}
          onChangeTabs={onChangeRoom}
          optionTabs={[
            { value: 'entire', label: tCommon('entire') },
            ...ossuaryRoomNameOptions.map((room) => ({ value: room.label, label: room.value })),
          ]}
        />
      </div>

      <div className='mt-6'>{activeRoom === 'entire' ? <OssuaryEntire /> : <OssuaryRooms />}</div>
    </div>
  );
};

export default page;
