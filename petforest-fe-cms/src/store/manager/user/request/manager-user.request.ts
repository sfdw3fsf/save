import { ACTIVE_STATE } from '@/utils/enums/common.enum';
import { USER_PERMISSION, USER_POSITION } from '@/utils/enums/manager/manager.enum';
import { FilterREQ } from '@/utils/types/filter.type';
import { PagingREQ } from '@/utils/types/paging.type';
import { Nil } from '@/utils/types/utils.type';

export type ManagerUserCreateREQ = {
  username: string;
  fullName: string;
  phoneNumber: string;
  password: string;
};

export type ManagerUserListFilter = FilterREQ;

export type ManagerUserListREQ = { isDeleted: boolean } & ManagerUserListFilter & PagingREQ;

export type ManagerUserUpdateREQ = {
  username?: string;
  fullName?: string;
  phoneNumber?: string;
  password?: string;
  dateOfBirth?: number;
  position?: Nil<USER_POSITION>;
  status?: ACTIVE_STATE;
};

export type ManagerUserUpdatePermissionREQ = {
  menuId?: number;
  permission?: USER_PERMISSION;
};
