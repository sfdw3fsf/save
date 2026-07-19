import { USER_ROLE } from '@/utils/enums/user/user-role.enum';

export type RegisterREQ = {
  email: string;
  phoneNumber: string;
  username: string;
  password: string;
  userRole: USER_ROLE;
};
