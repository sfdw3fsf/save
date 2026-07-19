import { api } from '@/config/http';
import { BaseListResponse } from '@/utils/types/response.type';
import { initKeys } from '@/utils/utils/query-key.utils';
import { BranchRESP } from './response/branch.response';

export const branchKeys = initKeys('branch');
export const getBranchListApi = async () => {
  return api.get<BaseListResponse<BranchRESP[]>>('branches');
};
