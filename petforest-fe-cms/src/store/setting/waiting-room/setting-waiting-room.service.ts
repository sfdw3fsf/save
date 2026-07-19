import { orString, orUndefined } from '@/utils/parser/io.parser';
import { SettingWaitingRoomDTO } from './dto/setting-waiting-room.dto';
import { SettingWaitingRoomListFilter } from './request/setting-waiting-room.request';
import { SettingWaitingRoomRESP } from './response/setting-waiting-room.response';

export const settingWaitingRoomRespToDto = (r: SettingWaitingRoomRESP): SettingWaitingRoomDTO => {
  return {
    key: r.id,
    id: r.id,
    name: orString(r.name),
  };
};

export function settingWaitingRoomListParamToFilter(searchParams: URLSearchParams): SettingWaitingRoomListFilter {
  return {
    search: orUndefined(searchParams.get('search')),
    sortOrder: orUndefined(searchParams.get('sortOrder')),
    sortBy: orUndefined(searchParams.get('sortBy')),
  } as SettingWaitingRoomListFilter;
}

export const settingWaitingRoomDtoToOptions = (d: SettingWaitingRoomDTO[]) => {
  return d.map((item) => ({
    text: item.name,
    label: item.name,
    value: item.id,
  }));
};
