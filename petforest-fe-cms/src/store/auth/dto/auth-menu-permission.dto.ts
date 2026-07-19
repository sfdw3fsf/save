import { USER_PERMISSION } from '@/utils/enums/manager/manager.enum';
import { MenuKeys } from '@/utils/enums/side-menu-type';

export type MenuPermissionDTO = {
  [key in MenuKeys]: USER_PERMISSION;
};
