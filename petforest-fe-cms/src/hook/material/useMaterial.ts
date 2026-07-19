import {
  getMaterialGroupTypeListApi,
  getMaterialNamesListApi,
  getMaterialTypeListApi,
  materialGroupTypeKeys,
  materialNamesKeys,
  materialTypeKeys,
} from '@/store/material/material.api';
import { materialTypeDtoToOptions } from '@/store/material/material.service';
import { MATERIAL_TYPE } from '@/utils/enums/material/material.enum';
import { keepPreviousData, useQuery } from '@tanstack/react-query';
import { useMemo } from 'react';

export function useMaterial() {
  const { data: materialTypeData } = useQuery({
    queryKey: materialTypeKeys.lists(),
    queryFn: () => getMaterialTypeListApi({ materialType: MATERIAL_TYPE.FUNERAL }),
    select: (response) => {
      return response.data;
    },
    placeholderData: keepPreviousData,
  });

  const { data: materialNameData } = useQuery({
    queryKey: materialNamesKeys.lists(),
    queryFn: () => getMaterialNamesListApi({ materialType: MATERIAL_TYPE.LUCETTE }),
    select: (response) => {
      return response.data.data;
    },
    placeholderData: keepPreviousData,
  });

  const { data: materialGroupTypeNameData } = useQuery({
    queryKey: materialGroupTypeKeys.lists(),
    queryFn: () => getMaterialGroupTypeListApi({ materialType: MATERIAL_TYPE.FUNERAL }),
    select: (response) => {
      return response.data.data;
    },
    placeholderData: keepPreviousData,
  });

  const materialTypeOptions = useMemo(() => {
    if (!materialTypeData) return [];
    return materialTypeDtoToOptions(materialTypeData.data);
  }, [materialTypeData]);

  return { materialTypeOptions, materialNameData, materialGroupTypeNameData };
}
