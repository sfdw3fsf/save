import { api } from '@/config/http';
import { DELETE_TYPE } from '@/utils/enums/common.enums';
import { BaseListResponse } from '@/utils/types/response.type';
import { initKeys } from '@/utils/utils/query-key.utils';
import {
  ManagerUserCreateREQ,
  ManagerUserListREQ,
  ManagerUserUpdatePermissionREQ,
  ManagerUserUpdateREQ,
} from './request/manager-user.request';
import { ManagerUserRESP } from './response/manager-user.response';

export const createManagerUserApi = async (body: ManagerUserCreateREQ) => {
  return await api.post(`users`, body);
};

export const managerUserKeys = initKeys('manager-user');
export const managerUserRetiressKeys = initKeys('manager-user-retiress');
export const getManagerUserListApi = async (params: ManagerUserListREQ) => {
  return api.get<BaseListResponse<ManagerUserRESP[]>>('users', { params });
};

export const updateManagerUserApi = async (id: number, body: ManagerUserUpdateREQ) => {
  return await api.patch(`users/${id}`, body);
};

export const deleteManagerUserApi = async (ids: string, deleteType: DELETE_TYPE) => {
  return await api.delete(`users`, { params: { ids, deleteType } });
};

export const updateManagerUserPermissionApi = async (id: number, body: ManagerUserUpdatePermissionREQ) => {
  return await api.patch(`users/${id}/permissions`, body);
};

export const updateManagerUserBranchesApi = async (id: number, ids: string) => {
  return await api.patch(`users/${id}/branches`, undefined, { params: { ids } });
};

export const updateManagerUserMainBranchApi = async (id: number, branchId: number) => {
  return await api.patch(`users/${id}/branches/${branchId}`);
};

export const restoreManagerUserApi = async (ids: string) => {
  return await api.patch(`users/restore`, undefined, { params: { ids } });
};
