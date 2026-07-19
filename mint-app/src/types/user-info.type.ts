import { USER_ROLE } from '@/utils/enums/user/user-role.enum';
import { USER_STATUS } from '@/utils/enums/user/user-status.enum';

export type UserInfo = {
  username: string;
  email: string;
  userId?: number;
  phoneNumber: string;
  userRole: USER_ROLE;
  userStatus: USER_STATUS;
  fullName?: string;
};

export type UserImage = {
  key: string;
  fileType: 'IMAGE';
  name: string;
  size: number;
};
