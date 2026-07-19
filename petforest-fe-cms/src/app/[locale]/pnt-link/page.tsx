'use client';
import { ListData } from '@/components/common/ListData';
import CustomText from '@/components/common/Text';
import { PntSettingListDTO } from '@/store/funeral/reservation-photo/dto/pnt-setting.dto';
import { getPntSettingsListApi, pntSettingKeys } from '@/store/funeral/reservation-photo/reservation-photo.api';
import { pntSettingListRespToDto } from '@/store/funeral/reservation-photo/reservation-photo.service';
import { ICONS } from '@/theme';
import { keepPreviousData, useQuery } from '@tanstack/react-query';
import Image from 'next/image';
import { useTranslation } from 'react-i18next';
import PNTSetting from '../funeral/pnt-setting/components/PNTSetting';

const page = () => {
  const { t: tPNTSetting } = useTranslation('funeral_pnt_setting');

  const {
    data: listData = [],
    isFetching,
    isLoading,
  } = useQuery({
    queryKey: pntSettingKeys.lists(),
    queryFn: () => getPntSettingsListApi(),
    select: (response) => {
      return response?.data?.data && response.data.data.map(pntSettingListRespToDto);
    },
    placeholderData: keepPreviousData,
  });
  return (
    <div>
      <div className='w-full h-[40px] bg-brand-forest rounded-lg flex justify-center items-center gap-[6px] mb-[24px]'>
        <Image src={ICONS.DOOR} width={20} height={20} alt='summary' />
        <CustomText variant='body-2-normal' className='!text-white'>
          {tPNTSetting('go_to_waiting')}
        </CustomText>
      </div>

      <ListData<PntSettingListDTO>
        data={listData}
        renderItem={(item: PntSettingListDTO, index) => (
          <PNTSetting
            key={item.id}
            displayName={item.name}
            petName={item.reservation?.petName}
            selectedImage={item.reservation?.files?.[0]?.url}
            guardianName={item.reservation?.guardianName}
            index={index}
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
