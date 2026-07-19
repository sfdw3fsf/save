import { getSettingCorrespondentApi, settingCorrespondentKeys } from '@/store/setting/correspondent/setting-correspondent.api';
import {
  correspondentDtoToOptions,
  settingCorrespondentRespToDto,
} from '@/store/setting/correspondent/setting-correspondent.service';
import { useQuery } from '@tanstack/react-query';
import { useMemo } from 'react';

export function useCorrespondent() {
  /* Actions */
  const { data: correspondentListData } = useQuery({
    queryKey: settingCorrespondentKeys.lists(),
    queryFn: () => getSettingCorrespondentApi(),
    select: (response) => {
      return response.data.data.map(settingCorrespondentRespToDto);
    },
  });

  const correspondentOptions = useMemo(() => {
    if (!correspondentListData) return [];
    return correspondentDtoToOptions(correspondentListData);
  }, [correspondentListData]);

  return { correspondentListData, correspondentOptions };
}
