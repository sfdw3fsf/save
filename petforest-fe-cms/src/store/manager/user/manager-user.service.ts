import { USER_BRANCH_TYPE, USER_PERMISSION } from '@/utils/enums/manager/manager.enum';
import { MenuKeys } from '@/utils/enums/side-menu-type';
import { parseTimestampToDate, parseTimeStampToDayjs } from '@/utils/parser/date-time.parser';
import { orString, orUndefined } from '@/utils/parser/io.parser';
import { ManagerUserDTO, ManagerUserRetireesDTO, MenuPermission } from './dto/manager-user.dto';
import { ManagerUserCreateREQ, ManagerUserListFilter } from './request/manager-user.request';
import { ManagerUserRESP } from './response/manager-user.response';

export const managerUserCreateToReq = (): ManagerUserCreateREQ => {
  return {
    username: '',
    fullName: '',
    phoneNumber: '',
    password: '',
  };
};

export const managerUserListParamToFilter = (searchParams: URLSearchParams): ManagerUserListFilter => {
  return {
    sortOrder: orUndefined(searchParams.get('sortOrder')),
    sortBy: orUndefined(searchParams.get('sortBy')),
  } as ManagerUserListFilter;
};

export const managerUserRespToDto = (r: ManagerUserRESP): ManagerUserDTO => {
  const permissionsByMenuKey: MenuPermission = {} as MenuPermission;

  r.permissions.forEach((permission) => {
    const menuKey = permission.menu.path as MenuKeys;
    permissionsByMenuKey[menuKey] = {
      id: permission.menu.id,
      permission: permission.permission,
    };
  });

  return {
    key: r.id,
    id: r.id,
    username: r.username,
    fullName: r.fullName,
    password: r.userCredential.password,
    phoneNumber: r.phoneNumber,
    dateOfBirth: parseTimeStampToDayjs(r.dateOfBirth),
    status: r.status,
    position: orString(r.position),
    createdAt: parseTimestampToDate(r.createdAt),
    activatedAt: parseTimestampToDate(r.activatedAt),
    userBranches: r.userBranches,
    branches: r.userBranches.map((item) => item.branch.id),
    mainBranch: orString(r.userBranches.find((item) => item.type === USER_BRANCH_TYPE.MAIN)?.branch.id),
    ...permissionsByMenuKey,
  };
};

export const managerUserRetireesRespToDto = (r: ManagerUserRESP): ManagerUserRetireesDTO => {
  const permissionsByMenuKey: MenuPermission = {} as MenuPermission;

  r.permissions.forEach((permission) => {
    const menuKey = permission.menu.path as MenuKeys;
    permissionsByMenuKey[menuKey] = {
      id: permission.menu.id,
      permission: permission.permission,
    };
  });

  return {
    key: r.id,
    id: r.id,
    username: r.username,
    fullName: r.fullName,
    password: r.userCredential.password,
    phoneNumber: r.phoneNumber,
    dateOfBirth: parseTimestampToDate(r.dateOfBirth),
    status: r.status,
    position: orString(r.position),
    createdAt: parseTimestampToDate(r.createdAt),
    activatedAt: parseTimestampToDate(r.activatedAt),
    branches: r.userBranches.map((item) => item.branch.name).join(' / '),
    mainBranch: orString(r.userBranches.find((item) => item.type === USER_BRANCH_TYPE.MAIN)?.branch.name),
    ...permissionsByMenuKey,
  };
};

const generatePermissionFilter = (key: any) => (value: USER_PERMISSION) => ({
  newKey: 'filterPermissions',
  value: value && { path: key, permissions: value },
  type: 'array',
});

export const userFilterKeyMapping = () => {
  return {
    branches: (value: number[]) => ({ 'userBranches.some.branchId.in': value }),
    mainBranch: (value: number[]) => value && { 'userbranches.some.type.in': 'MAIN', 'userBranches.some.branchId.in': value },
    ...Object.fromEntries(Object.keys(MenuKeys).map((key) => [key, generatePermissionFilter(key)])),
  };
};

export const managerUserDtoToOptions = (d: ManagerUserDTO[]) => {
  return d.map((item) => ({
    text: item.fullName,
    value: item.id,
    label: item.fullName,
  }));
};

export const managerUserFullNameDtoToOptions = (d: ManagerUserDTO[]) => {
  return d.map((item) => ({
    text: item.fullName,
    value: item.fullName,
    label: item.fullName,
  }));
};
