import { api } from '@/config/http';
import { MATERIAL_TYPE } from '@/utils/enums/material/material.enum';
import { BaseListResponse, BaseResponse } from '@/utils/types/response.type';
import { initKeys } from '@/utils/utils/query-key.utils';
import { materialInventoryHistoryListReqMapping, materialInventoryListReqMapping } from './material.service';
import {
  MaterialCreateREQ,
  MaterialInventoryCreateREQ,
  MaterialInventoryHistoryREQ,
  MaterialInventoryListREQ,
  MaterialInventoryMonthlyHistoryREQ,
  MaterialInventoryMonthlyREQ,
  MaterialInventoryUpdateREQ,
  MaterialListNameREQ,
  MaterialListREQ,
  MaterialUpdateREQ,
  MaterialUpdateStatusREQ,
} from './request/material.request';
import {
  MaterialDetailRESP,
  MaterialGroupTypeRESP,
  MaterialInventoryByMonthRESP,
  MaterialInventoryMonthlyHistoryRESP,
  MaterialInventoryMonthlyRESP,
  MaterialInventoryRESP,
  MaterialNameRESP,
  MaterialRESP,
  MaterialTypesRESP,
} from './response/material.response';
import { FilterREQ } from '@/utils/types/filter.type';

export const createMaterialApi = async (body: MaterialCreateREQ) => {
  return await api.post(`materials`, body);
};

export const materialFuneralKeys = initKeys('material-funeral');
export const materialNamesKeys = initKeys('material-other');
export const materialTypeKeys = initKeys('material-type');
export const materialGroupTypeKeys = initKeys('material-group-type');
export const materialLucetteKeys = initKeys('material-lucette');
export const materialOtherKeys = initKeys('material-other');
export const materialExpendablesKeys = initKeys('material-expendables');
export const getMaterialListApi = async (params: MaterialListREQ) => {
  return api.get<BaseListResponse<MaterialRESP[]>>('materials', { params });
};

export const updateMaterialApi = async (id: number, body: MaterialUpdateREQ) => {
  return await api.patch(`materials/${id}`, body);
};

export const updateMaterialStatusApi = async (id: number, body: MaterialUpdateStatusREQ) => {
  return await api.patch(`materials/${id}/status`, body);
};

export const deleteMaterialApi = async (ids: string) => {
  return await api.delete(`materials`, { params: { ids } });
};

export const getMaterialDetailApi = async (id: number) => {
  if (id < 1) return;
  return api.get<BaseResponse<MaterialDetailRESP>>(`materials/${id}`);
};

export const getMaterialNameOptionApi = async (materialType: MATERIAL_TYPE) => {
  return api.get<BaseListResponse<MaterialNameRESP[]>>('materials/names', {
    params: { materialType },
  });
};

//Inventory
export const createMaterialInventoryApi = async (id: number, body: MaterialInventoryCreateREQ) => {
  return await api.post(`materials/${id}/inventories`, body);
};

export const materialFuneralInventoryKeys = initKeys('material-funeral-inventory');
export const materialLucetteInventoryKeys = initKeys('material-lucette-inventory');
export const materialOtherInventoryKeys = initKeys('material-other-inventory');
export const materialExpendablesInventoryKeys = initKeys('material-expendables-inventory');
export const getMaterialInventoryListApi = async (id: number, req: MaterialInventoryListREQ) => {
  const params = materialInventoryListReqMapping(req);
  return api.get<BaseListResponse<MaterialInventoryRESP[]>>(`materials/${id}/inventories`, { params });
};

export const materialFuneralInventoryByMonthKeys = initKeys('material-funeral-inventory-by-month');
export const materialLucetteInventoryByMonthKeys = initKeys('material-lucette-inventory-by-month');
export const materialOtherInventoryByMonthKeys = initKeys('material-other-inventory-by-month');
export const materialExpendablesInventoryByMonthKeys = initKeys('material-expendables-inventory-by-month');
export const getMaterialInventoryByMonthApi = async (id: number) => {
  return api.get<BaseResponse<MaterialInventoryByMonthRESP>>(`materials/${id}/month-histories`);
};

export const updateMaterialInventoryApi = async (id: number, inventoryId: number, body: MaterialInventoryUpdateREQ) => {
  if (inventoryId < 1) return;
  return await api.patch(`materials/${id}/inventories/${inventoryId}`, body);
};

export const deleteMaterialInventoryApi = async (materialId: number, ids: string) => {
  return await api.delete(`materials/${materialId}/inventories`, { params: { ids } });
};

export const materialInventoryMonthlyKeys = initKeys('material-inventory-monthly');
export const getMaterialInventoryMonthlyApi = async (id: number, params: MaterialInventoryMonthlyREQ) => {
  return api.get<BaseListResponse<MaterialInventoryMonthlyRESP[]>>(`materials/${id}/monthly-histories`, { params });
};

export const materialFuneralInventoryHistoryKeys = initKeys('material-funeral-inventory-history');
export const materialLucetteInventoryHistoryKeys = initKeys('material-lucette-inventory-history');
export const materialOtherInventoryHistoryKeys = initKeys('material-other-inventory-history');
export const materialExpendablesInventoryHistoryKeys = initKeys('material-expendables-inventory-history');
export const getMaterialInventoryHistoryListApi = async (req: MaterialInventoryHistoryREQ) => {
  const params = materialInventoryHistoryListReqMapping(req);
  return api.get<BaseListResponse<MaterialInventoryRESP[]>>(`materials/inventories/histories`, { params });
};

export const getMaterialTypeListApi = async (params: { materialType: MATERIAL_TYPE }) => {
  return api.get<BaseListResponse<MaterialTypesRESP[]>>('materials/types', { params });
};

export const getMaterialNamesListApi = async (params: MaterialListNameREQ) => {
  return api.get<BaseListResponse<MaterialNameRESP[]>>('materials/names', { params });
};

export const getMaterialGroupTypeListApi = async (params: { materialType: MATERIAL_TYPE }) => {
  return api.get<BaseListResponse<MaterialGroupTypeRESP>>('materials/group-types', { params });
};

export const materialInventoryMonthlyHistoryKeys = initKeys('material-inventory-monthly-history');
export const getMaterialInventoryMonthlyHistoryApi = async (params: MaterialInventoryMonthlyHistoryREQ & FilterREQ) => {
  return api.get<BaseListResponse<MaterialInventoryMonthlyHistoryRESP[]>>(`materials/monthly-histories`, { params });
};
