import { USER_ROLE } from '@/utils/enums/user/user-role.enum';
import { USER_STATUS } from '@/utils/enums/user/user-status.enum';

export type RegisterDTO = {
  accessToken: string;
  userStatus: USER_STATUS;
  userRole: USER_ROLE;
};
