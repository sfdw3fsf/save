import { ERROR_MESSAGE } from '@/utils/constants/data.constant';
import { convertDateStringToDate } from '@/utils/helpers/datetime.helper';
import { FindIdConfirmOtpDTO } from './dto/find-id.dto';
import { FindIdConfirmOtpRESP } from './response/find-id.response';

export const transformFindIdConfirmOtpResponse = (response: FindIdConfirmOtpRESP): FindIdConfirmOtpDTO => {
  const parsedJoinDate = convertDateStringToDate(response.joinDate);
  if (parsedJoinDate === null) throw new Error(ERROR_MESSAGE.INVALID_DATE);

  const result: FindIdConfirmOtpDTO = { username: response.username, joinDate: parsedJoinDate };

  return result;
};
