import { api } from '@/config/http';
import { DELETE_TYPE } from '@/utils/enums/common.enums';
import { BaseListResponse, BaseResponse } from '@/utils/types/response.type';
import { initKeys } from '@/utils/utils/query-key.utils';
import { ManagerBranchListREQ, ManagerBranchUpdateREQ } from './request/manager-branch.request';
import { ManagerBranchRESP } from './response/manager-branch.response';

export const createManagerBranchApi = async () => {
  return await api.post(`branches`);
};

export const managerBranchKeys = initKeys('manager-branch');
export const getManagerBranchListApi = async (params: ManagerBranchListREQ) => {
  return api.get<BaseListResponse<ManagerBranchRESP[]>>('branches', { params });
};

export const updateManagerBranchApi = async (id: number, body: ManagerBranchUpdateREQ) => {
  return await api.patch<BaseResponse<ManagerBranchRESP>>(`branches/${id}`, body);
};

export const deleteManagerBranchApi = async (ids: string, deleteType: DELETE_TYPE) => {
  return await api.delete<BaseResponse<ManagerBranchRESP>>(`branches`, { params: { ids, deleteType } });
};

export const restoreManagerBranchApi = async (ids: string) => {
  return await api.patch<BaseResponse<ManagerBranchRESP>>(`branches/restore`, undefined, { params: { ids } });
};
