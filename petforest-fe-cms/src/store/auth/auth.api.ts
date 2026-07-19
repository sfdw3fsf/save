import { api } from '@/config/http';
import { BaseListResponse, BaseResponse } from '@/utils/types/response.type';
import { initKeys } from '@/utils/utils/query-key.utils';
import { UserBranch, UserPermission } from '../manager/user/response/manager-user.response';
import { AuthChangePasswordREQ, AuthLoginREQ } from './request/auth-login.request';
import { AuthLoginRESP } from './response/auth-login.response';

export const loginApi = async (body: AuthLoginREQ) => {
  return await api.post<BaseResponse<AuthLoginRESP>>(`auth/login`, body);
};

export const logoutApi = async () => {
  return await api.delete(`auth/cookies`);
};

export const userBranchesKeys = initKeys('user-branches');
export const userBranchesHeaderKeys = initKeys('user-branches-header');
export const getUserBranchesApi = async () => {
  return api.get<BaseListResponse<UserBranch[]>>('auth/user-branches');
};

export const menuPermissionKeys = initKeys('menu-permission');
export const getMenuPermissionApi = async () => {
  return api.get<BaseListResponse<UserPermission[]>>('auth/user-permissions');
};

export const changePasswordApi = async (body: AuthChangePasswordREQ) => {
  return await api.patch(`auth/password`, body);
};
