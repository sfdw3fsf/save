import { UserDetailsRESP } from '@/data/user/response/user-details.response';

export type LoginRESP = UserDetailsRESP & {
  accessToken: string;
};
