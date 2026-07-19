import { getUserBranchesApi, userBranchesKeys } from '@/store/auth/auth.api';
import { userBranchesDtoToOptions, userBranchesRespToDto } from '@/store/auth/auth.service';
import { useQuery } from '@tanstack/react-query';
import { useMemo } from 'react';

export function useUserBranches(enabled?: boolean) {
  /* Actions */
  const { data: userBranchesData } = useQuery({
    queryKey: userBranchesKeys.lists(),
    queryFn: () => getUserBranchesApi(),
    select: (response) => {
      return { ...response.data, data: response.data.data.map(userBranchesRespToDto) };
    },
    enabled: enabled,
  });

  const userBranchOptions = useMemo(() => {
    if (!userBranchesData) return [];
    return userBranchesDtoToOptions(userBranchesData.data);
  }, [userBranchesData]);

  return { userBranchesData, userBranchOptions };
}
