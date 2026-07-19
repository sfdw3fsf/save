import { UserBranch } from '@/store/manager/user/response/manager-user.response';

export const helperMainBranchOptions = (i: UserBranch[]) => {
  return i.map((item) => ({
    label: item.branch.name,
    value: item.branch.id,
  }));
};
