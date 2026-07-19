import { getManagerBranchListApi, managerBranchKeys } from '@/store/manager/branch/manager-branch.api';
import { managerBranchDtoToOptions, managerBranchRespToDto } from '@/store/manager/branch/manager-branch.service';
import { ManagerBranchListREQ } from '@/store/manager/branch/request/manager-branch.request';
import { useQuery } from '@tanstack/react-query';
import { useMemo } from 'react';

export function useBranch() {
  /* Actions */
  const { data: managerBranchListData } = useQuery({
    queryKey: managerBranchKeys.lists(),
    queryFn: () => getManagerBranchListApi({ isDeleted: false } as ManagerBranchListREQ),
    select: (response) => {
      return response.data.data.map(managerBranchRespToDto);
    },
  });

  const branchOptions = useMemo(() => {
    if (!managerBranchListData) return [];
    return managerBranchDtoToOptions(managerBranchListData);
  }, [managerBranchListData]);

  return { managerBranchListData, branchOptions };
}
