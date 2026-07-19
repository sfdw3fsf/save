import { getSettingVarietiesBreedNameApi, settingVarietiesBreedNameKeys } from '@/store/setting/varieties/setting-varieties.api';
import { settingVarietiesBreedNameRespToOptions } from '@/store/setting/varieties/setting-varieties.service';
import { keepPreviousData, useQuery } from '@tanstack/react-query';

export function useSettingVarietiesBreed() {
  const { data: breedNameOptions = [] } = useQuery({
    queryKey: settingVarietiesBreedNameKeys.lists(),
    queryFn: () => getSettingVarietiesBreedNameApi(),
    select: (response) => {
      return settingVarietiesBreedNameRespToOptions(response.data.data);
    },
    placeholderData: keepPreviousData,
  });

  return { breedNameOptions };
}
