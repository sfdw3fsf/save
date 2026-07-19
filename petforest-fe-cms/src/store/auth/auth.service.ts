import { MY_ROUTE } from '@/utils/constants/route.constant';
import { USER_BRANCH_TYPE, USER_PERMISSION } from '@/utils/enums/manager/manager.enum';
import { OptionItems } from '@/utils/types/option.type';
import { UserBranch, UserPermission } from '../manager/user/response/manager-user.response';
import { MenuPermissionDTO } from './dto/auth-menu-permission.dto';
import { UserBranchDTO } from './dto/auth-user-branches.dto';

export const initialLoginValues = {
  id: '',
  password: '',
};

export const userBranchesRespToDto = (r: UserBranch): UserBranchDTO => {
  return {
    id: r.branch.id,
    name: r.branch.name,
    isMainBranch: r.type === USER_BRANCH_TYPE.MAIN,
    color: r.branch.color,
  };
};

export const userBranchesDtoToOptions = (d: UserBranchDTO[]): OptionItems<number> => {
  return d.map((item) => ({ label: item.name, value: item.id }));
};

export const menuPermissionRespToDto = (r: UserPermission[]): MenuPermissionDTO => {
  const result: MenuPermissionDTO = {} as MenuPermissionDTO;

  const isParent = (parentPath: string, childPath: string): boolean => {
    return childPath.startsWith(parentPath) && childPath !== parentPath;
  };

  r.forEach((item) => {
    const path = item.menu.path;
    result[path] = item.permission;
  });

  Object.keys(result).forEach((path) => {
    let parentPermission = result[path as keyof MenuPermissionDTO];

    r.forEach((item) => {
      const childPath = item.menu.path;

      if (isParent(path, childPath)) {
        if (item.permission !== USER_PERMISSION.NOT_ALLOW) {
          parentPermission = USER_PERMISSION.MODIFY;
        }
      }
    });

    result[path as keyof MenuPermissionDTO] = parentPermission;
  });

  return result;
};
export const pathToPermissionKey = (currentPathname: string): string | null => {
  for (const [routeGroup, paths] of Object.entries(MY_ROUTE)) {
    if (typeof paths === 'object') {
      for (const [key, path] of Object.entries(paths)) {
        if (currentPathname === path) {
          const permissionKey = key.toUpperCase();
          return permissionKey;
        }
      }
    } else {
      if (currentPathname === paths) {
        const permissionKey = routeGroup.toUpperCase();
        return permissionKey;
      }
    }
  }
  return null;
};
