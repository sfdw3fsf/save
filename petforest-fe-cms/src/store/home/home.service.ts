import { DATE_FORMAT } from '@/utils/constants/date.constant';
import { ANNOUNCEMENT, ANNOUNCEMENT_INACTIVE } from '@/utils/enums/home/home.enums';
import { ENUM_STATUS } from '@/utils/enums/status.enum';
import { parseTimeStampToDayjs } from '@/utils/parser/date-time.parser';
import { orString } from '@/utils/parser/io.parser';
import dayjs, { Dayjs } from 'dayjs';
import { AnnouncementScheduleDTO } from './dto/home.dto';
import { AnnouncementScheduleInput, AnnouncementUpdateInput } from './input/home.input';
import { AnnouncementScheduleREQ, AnnouncementUpdateREQ } from './request/home.request';
import {
  AnnouncementOthers,
  AnnouncementReservation,
  AnnouncementRESP,
  AnnouncementScheduleRESP,
} from './response/home.response';

export const announcementInputToReq = (i: AnnouncementUpdateInput): AnnouncementUpdateREQ => {
  const reservations =
    i.reservations?.map((item) => ({
      id: item.id,
      reservationId: item.reservationId,
      detail: item.detail,
      type: ANNOUNCEMENT.FUNERAL,
      status: item.status,
    })) || [];
  const unaccompany =
    i.unaccompany?.map((item) => ({
      id: item.id,
      reservationId: item.reservationId,
      detail: item.detail,
      type: ANNOUNCEMENT.NOT_ACCOMPANY,
      status: item.status,
    })) || [];

  const others = i.others?.map((item) => ({
    id: item.id,
    detail: item.detail,
    startDate: item.date[0].valueOf(),
    endDate: item.date[1].valueOf(),
    status: item.status,
  }));
  return { content: i.content, date: i.date?.valueOf(), reservations: reservations?.concat(unaccompany), others };
};

export const announcementRespToInput = (r: AnnouncementRESP, date: Dayjs): AnnouncementUpdateInput => {
  return {
    ...r,
    id: r.id,
    date,
    reservations: r.reservations
      .map((item) => ({
        id: item.id,
        reservationId: item.reservation.id,
        detail: item.detail,
        type: item.type,
        status: item.status,
      }))
      .filter((item) => item.type === ANNOUNCEMENT.FUNERAL),
    unaccompany: r.reservations
      .map((item) => ({
        id: item.id,
        reservationId: item.reservation.id,
        detail: item.detail,
        type: item.type,
        status: item.status,
      }))
      .filter((item) => item.type === ANNOUNCEMENT.NOT_ACCOMPANY),
    others: r.others.map((item) => ({
      id: item.id,
      detail: item.detail,
      date: [parseTimeStampToDayjs(item.startDate) as Dayjs, parseTimeStampToDayjs(item.endDate) as Dayjs],
      status: item.status,
    })),
  };
};

export const announcementScheduleInputToReq = (i: AnnouncementScheduleInput): AnnouncementScheduleREQ => {
  return {
    startDate: i.startDate.valueOf(),
    endDate: i.endDate.endOf('day').valueOf(),
  };
};

export const announcementScheduleRespToDto = (
  r: AnnouncementScheduleRESP,
  selectedMonth: AnnouncementScheduleInput,
): AnnouncementScheduleDTO => {
  return {
    announcement: r.announcement,
    schedules: Object.keys(r.schedules).flatMap((key) => {
      const announcementRecord = r.schedules[key];

      return announcementRecord.records.map((reservation) => {
        const type =
          dayjs(Number(key)).isBefore(selectedMonth.startDate) ||
          dayjs(Number(key)).isAfter(selectedMonth.endDate) ||
          reservation.status === ENUM_STATUS.COMPLETE
            ? ANNOUNCEMENT_INACTIVE[reservation.type]
            : reservation.type;
        return {
          id: reservation.id.toString(),
          calendarId: type,
          title: reservation.detail,
          state: announcementRecord.counts[reservation.type].toString(),
          category: 'allday',
          start: dayjs(Number(key)).format(DATE_FORMAT.DATE_TIME_SECOND.ISO8601),
          end: (reservation as AnnouncementOthers).endDate
            ? dayjs(Number((reservation as AnnouncementOthers).endDate)).format(DATE_FORMAT.DATE_TIME_SECOND.ISO8601)
            : dayjs(Number(key)).format(DATE_FORMAT.DATE_TIME_SECOND.ISO8601),
          isReadOnly: true,
          location: orString((reservation as AnnouncementReservation).reservation?.code),
        };
      });
    }),
  };
};
