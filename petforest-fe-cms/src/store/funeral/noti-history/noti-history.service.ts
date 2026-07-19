import { DATE_FORMAT } from '@/utils/constants/date.constant';
import { NOTI_HISTORY_CATEGORY } from '@/utils/enums/funeral/reservation-mngt.enums';
import { orString, orUndefined, parseStringToNumber } from '@/utils/parser/io.parser';
import dayjs from 'dayjs';
import { NotiHistoryDetailDTO, NotiHistoryListDTO } from './dto/noti-history.dto';
import { NotiHistoryListFilter } from './request/noti-history.request';
import { NotiHistoryDetailRESP, NotiHistoryListRESP } from './response/noti-history.response';

export const notiHistoryListParamsToFilter = (
  searchParams: URLSearchParams,
  defaultFilter?: NotiHistoryListFilter,
): NotiHistoryListFilter => {
  const { start, end } = {
    start: parseStringToNumber(searchParams.get('start')),
    end: parseStringToNumber(searchParams.get('end')),
  };

  let filter;
  if (start && end) {
    filter = JSON.stringify({
      'createdAt.gte': start,
      'createdAt.lte': end,
    });
  }

  return {
    filter: orUndefined(filter || defaultFilter?.filter),
    search: orUndefined(searchParams.get('search') || defaultFilter?.search),
    sortOrder: orUndefined(searchParams.get('sortOrder') || defaultFilter?.sortOrder),
    sortBy: orUndefined(searchParams.get('sortBy') || defaultFilter?.sortBy),
  };
};

export const notiHistoryListRespToDto = (resp: NotiHistoryListRESP): NotiHistoryListDTO => {
  const { reservation, ...rest } = resp;
  const dto: NotiHistoryListDTO = {
    key: rest.id,
    id: rest.id,
    contact: orString(resp.contact),
    category: rest.category as NOTI_HISTORY_CATEGORY,
    messageKey: rest.messageKey,
    createdAt: rest.createdAt ? dayjs(+rest.createdAt).format(DATE_FORMAT.DATE_TIME_SECOND.HYPHEN) : undefined,
    petName: orUndefined(reservation?.petInformation?.name),
    guardianName: orUndefined(reservation?.guardianInformation?.name),
    guardianContact: orUndefined(reservation?.guardianInformation?.phoneNumber),
  };
  return dto;
};

export const notiHistoryDetailRespToDto = (resp: NotiHistoryDetailRESP): NotiHistoryDetailDTO => {
  const dto: NotiHistoryDetailDTO = {
    id: resp.id,
    contact: orUndefined(resp.contact),
    content: orUndefined(resp.content),
    messageKey: resp.messageKey,
    key: resp.key,
  };
  return dto;
};
