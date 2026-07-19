import { FilterREQ } from '@/utils/types/filter.type';
import { PagingREQ } from '@/utils/types/paging.type';

export type ManagerBranchListFilter = FilterREQ;

export type ManagerBranchListREQ = { isDeleted: boolean } & ManagerBranchListFilter & PagingREQ;

export type ManagerBranchUpdateREQ = {
  name?: string;
  color?: string;
  roadAddress?: string;
  streetAddress?: string;
  naverMap?: string;
  kakaoMap?: string;
};
