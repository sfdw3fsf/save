import { ACTIVE_STATE } from '@/utils/enums/common.enum';
import { USER_PERMISSION, USER_POSITION } from '@/utils/enums/manager/manager.enum';
import { MenuKeys } from '@/utils/enums/side-menu-type';
import { Dayjs } from 'dayjs';
import { UserBranch } from '../response/manager-user.response';

export type MenuPermission = {
  [key in MenuKeys]: {
    id: number;
    permission: USER_PERMISSION;
  };
};

export type ManagerUserDTO = {
  key: number;
  id: number;
  username: string;
  fullName: string;
  password: string;
  phoneNumber: string;
  dateOfBirth: Dayjs | null;
  status: ACTIVE_STATE;
  position: USER_POSITION | null;
  createdAt: string;
  activatedAt: string;
  userBranches: UserBranch[];
  branches: number[];
  mainBranch: number;
} & MenuPermission;

export type ManagerUserRetireesDTO = {
  key: number;
  id: number;
  username: string;
  fullName: string;
  password: string;
  phoneNumber: string;
  dateOfBirth: string;
  status: ACTIVE_STATE;
  position: USER_POSITION | null;
  createdAt: string;
  activatedAt: string;
  branches: string;
  mainBranch: string;
} & MenuPermission;
