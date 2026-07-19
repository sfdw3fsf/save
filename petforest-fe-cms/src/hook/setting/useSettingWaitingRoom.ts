import { SettingWaitingRoomListREQ } from '@/store/setting/waiting-room/request/setting-waiting-room.request';
import { getWaitingRoomListApi, settingWaitingRoomKeys } from '@/store/setting/waiting-room/setting-waiting-room.api';
import {
  settingWaitingRoomDtoToOptions,
  settingWaitingRoomRespToDto,
} from '@/store/setting/waiting-room/setting-waiting-room.service';
import { keepPreviousData, useQuery } from '@tanstack/react-query';
import { useMemo } from 'react';

type useSettingWaitingRoomProps = {
  filter?: SettingWaitingRoomListREQ;
};

export function useSettingWaitingRoom({ filter = {} }: useSettingWaitingRoomProps) {
  const { data: settingWaitingRoomData } = useQuery({
    queryKey: settingWaitingRoomKeys.list(filter),
    queryFn: () => getWaitingRoomListApi(filter),
    select: (response) => {
      return { ...response.data, data: response.data.data.map(settingWaitingRoomRespToDto), total: response.data.data.length };
    },
    placeholderData: keepPreviousData,
  });

  const waitingRoomOptions = useMemo(() => {
    if (!settingWaitingRoomData) return [];
    return settingWaitingRoomDtoToOptions(settingWaitingRoomData.data);
  }, [settingWaitingRoomData]);

  return { settingWaitingRoomData, waitingRoomOptions };
}
