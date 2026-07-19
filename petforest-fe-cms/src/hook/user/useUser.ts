import { useAuthStore } from '@/store/auth/auth.store';
import { getManagerUserListApi, managerUserKeys } from '@/store/manager/user/manager-user-api';
import {
  managerUserDtoToOptions,
  managerUserFullNameDtoToOptions,
  managerUserRespToDto,
} from '@/store/manager/user/manager-user.service';
import { ManagerUserListREQ } from '@/store/manager/user/request/manager-user.request';
import { USER_POSITION } from '@/utils/enums/manager/manager.enum';
import { keepPreviousData, useQuery } from '@tanstack/react-query';
import { useMemo } from 'react';

export function useUser() {
  const selectedBranchId = useAuthStore((state) => state.selectedBranchId);

  const { data: managerUserListData } = useQuery({
    queryKey: managerUserKeys.lists(),
    queryFn: () =>
      getManagerUserListApi({
        isDeleted: false,
        filter: JSON.stringify({
          'userBranches.some.branchId.in': [selectedBranchId],
          'position.in': ['FUNERAL_MANAGER'],
          'status.in': ['ACTIVE'],
        }),
      } as ManagerUserListREQ),
    select: (response) => {
      return response.data.data.map(managerUserRespToDto);
    },
    placeholderData: keepPreviousData,
  });

  const managerOptions = useMemo(() => {
    if (!managerUserListData) return [];
    return managerUserDtoToOptions(managerUserListData);
  }, [managerUserListData]);

  const managerDirectorOptions = useMemo(() => {
    if (!managerUserListData) return [];
    return managerUserDtoToOptions(managerUserListData.filter((user) => user.position === USER_POSITION.FUNERAL_MANAGER));
  }, [managerUserListData]);

  const managerFullnameOptions = useMemo(() => {
    if (!managerUserListData) return [];
    return managerUserFullNameDtoToOptions(managerUserListData);
  }, [managerUserListData]);

  return { managerUserListData, managerOptions, managerFullnameOptions, managerDirectorOptions };
}
