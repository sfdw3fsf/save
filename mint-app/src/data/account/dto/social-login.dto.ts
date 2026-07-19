import { UserAuthProvider } from '@/types/user-auth-provider.type';
import { UserInfo } from '@/types/user-info.type';

export type SocialLoginDTO = {
  accessToken: string;
  userInfo: UserInfo;
  userAuthProvider: UserAuthProvider;
};
