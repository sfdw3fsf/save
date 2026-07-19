import { getMenuPermissionApi, menuPermissionKeys } from '@/store/auth/auth.api';
import { menuPermissionRespToDto } from '@/store/auth/auth.service';
import { useAuthStore } from '@/store/auth/auth.store';
import { USER_PERMISSION } from '@/utils/enums/manager/manager.enum';
import { MenuKeys } from '@/utils/enums/side-menu-type';
import { useQuery } from '@tanstack/react-query';

export function useMenuPermission(menu?: MenuKeys, enabled?: boolean) {
  const { selectedBranchId } = useAuthStore((state) => state);
  /* Actions */
  const { data: menuPermissionData } = useQuery({
    queryKey: menuPermissionKeys.list({ selectedBranchId }),
    queryFn: () => getMenuPermissionApi(),
    select: (response) => {
      return response.data.data && menuPermissionRespToDto(response.data.data);
    },

    enabled: enabled,
  });

  const canEdit = menuPermissionData && menu && menuPermissionData[menu] === USER_PERMISSION.MODIFY ? true : false;

  return { menuPermissionData, canEdit };
}
