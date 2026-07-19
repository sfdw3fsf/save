import { AUTH_SOCIAL_PROVIDER } from '@/utils/enums/data/auth-social-provider.enum';

export type UserAuthProvider = {
  provider: AUTH_SOCIAL_PROVIDER;
  isVerified: boolean;
};
