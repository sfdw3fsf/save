import { transformUserDetailsResponse } from '../user/user.service';
import { LoginDTO } from './dto/login.dto';
import { LoginRESP } from './response/login.response';

export const transformLoginResponse = ({ accessToken, ...userInfo }: LoginRESP): LoginDTO => {
  return {
    accessToken,
    userInfo: transformUserDetailsResponse(userInfo),
  } as LoginDTO;
};
