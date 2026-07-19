import { UserInfo } from '@/types/user-info.type';

export type LoginDTO = {
  accessToken: string;
  userInfo: UserInfo;
};
