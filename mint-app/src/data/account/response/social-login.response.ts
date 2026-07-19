import { UserDetailsRESP } from '@/data/user/response/user-details.response';

export type SocialLoginRESP = UserDetailsRESP & {
  accessToken: string;
  userAuthProvider: {
    provider: string;
    isVerified: boolean;
  };
};
