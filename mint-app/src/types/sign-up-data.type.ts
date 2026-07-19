import { USER_ROLE } from '@/utils/enums/user/user-role.enum';

export type SignUpData = {
  email: string;
  phoneNumber: string;
  userRole: USER_ROLE;
};
