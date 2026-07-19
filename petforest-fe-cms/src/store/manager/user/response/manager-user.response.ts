import { ACTIVE_STATE } from '@/utils/enums/common.enum';
import { USER_BRANCH_TYPE, USER_PERMISSION, USER_POSITION } from '@/utils/enums/manager/manager.enum';
import { MenuKeys } from '@/utils/enums/side-menu-type';

export type UserBranch = {
  type: USER_BRANCH_TYPE;
  branch: {
    id: number;
    name: string;
    color: string;
  };
};

export type UserPermission = {
  permission: USER_PERMISSION;
  menu: {
    id: number;
    path: MenuKeys;
  };
};

export type ManagerUserRESP = {
  id: number;
  username: string;
  fullName: string;
  phoneNumber: string;
  dateOfBirth: string;
  status: ACTIVE_STATE;
  position: USER_POSITION | null;
  createdAt: string;
  activatedAt: string;
  permissions: UserPermission[];
  userCredential: {
    password: string;
  };
  userBranches: UserBranch[];
};
