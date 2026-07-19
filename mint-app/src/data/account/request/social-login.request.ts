import { AUTH_SOCIAL_PROVIDER } from '@/utils/enums/data/auth-social-provider.enum';

export type SocialLoginREQ = {
  accessToken: string;
  provider: AUTH_SOCIAL_PROVIDER;
};
