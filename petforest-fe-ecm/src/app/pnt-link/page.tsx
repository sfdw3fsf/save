'use client';

import { ListData } from '@/components/list-data/ListData';
import { PntSettingListDTO } from '@/store/pnt-link/dto/pnt-link.dto';
import { getPntSettingsListApi, pntSettingKeys } from '@/store/pnt-link/pnt-link.api';
import { pntSettingListRespToDto } from '@/store/pnt-link/pnt-link.service';
import { PNT_ROOM } from '@/utils/constant/pnt/pnt.constant';
import { orString } from '@/utils/parser/io.parser';
import { keepPreviousData, useQuery } from '@tanstack/react-query';
import { useSearchParams } from 'next/navigation';
import { useMemo } from 'react';
import PNTSetting from './components/PNTSetting';

const page = () => {
  const searchParams = useSearchParams();

  const id: string = useMemo(() => {
    return orString(searchParams.get('id'));
  }, [searchParams]);

  const { data: listData = [] } = useQuery({
    queryKey: pntSettingKeys.lists(),
    queryFn: () => getPntSettingsListApi(id),
    select: (response) => {
      return response?.data?.data && response.data.data.map(pntSettingListRespToDto);
    },
    placeholderData: keepPreviousData,
    enabled: !!id,
  });

  return (
    <div className='w-screen h-screen py-8 px-[40px]'>
      <a
        href='/pnt-link/waiting'
        className='bg-[#0D4221] h-[40px]  rounded-lg flex justify-center items-center gap-[6px] mb-[24px]'>
        <img src='/icon/door.svg' alt='door' />
        <div className='text-white font-medium text-[15px] '>대기화면 가기</div>
      </a>
      <ListData<PntSettingListDTO>
        data={listData}
        renderItem={(item: PntSettingListDTO, index) => (
          <PNTSetting
            key={item.id}
            displayName={item.name ? PNT_ROOM[item.name as keyof typeof PNT_ROOM] : ''}
            petName={item.reservation?.petName}
            selectedImage={item.reservation?.files?.[0]?.url}
            guardianName={item.reservation?.guardianName}
            index={index}
            onClick={() => {
              window.open(`/pnt-link/preview?id=${item.id}`, '_blank', `noopener,noreferrer`);
            }}
          />
        )}
        keyExtractor={(item) => item.id}
        numberOfColumns={5}
        styleContainer='flex flex-wrap'
      />
    </div>
  );
};

export default page;
