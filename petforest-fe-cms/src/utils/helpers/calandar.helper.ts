import { ReactCalendarOptions, ReactEventObject } from '@toast-ui/react-calendar';
import { CALENDAR_STATUS_ENUMS } from '../enums/common.enums';
import { ANNOUNCEMENT } from '../enums/home/home.enums';

export const helperMappingIconEvent = (calendarId: string): string | undefined => {
  const mapping: { [key in ANNOUNCEMENT]: string } = {
    [ANNOUNCEMENT.FUNERAL]: 'icon-funeral',
    [ANNOUNCEMENT.NOT_ACCOMPANY]: 'icon-unaccompanied',
    [ANNOUNCEMENT.ETC]: 'icon-other',
  };

  for (const key in mapping) {
    if (calendarId.startsWith(key)) {
      return mapping[key as ANNOUNCEMENT];
    }
  }

  return undefined;
};

export const helperGetBorderColor = (
  initialCalendars: ReactCalendarOptions['calendars'],
  calendarId: string,
): string | undefined => {
  const calendar = initialCalendars.find((c: ReactCalendarOptions['calendars']) => c.id === calendarId);
  return calendar?.borderColor;
};

export const updateEventIds = (mockData: Partial<ReactEventObject>[], currentMonth: number) => {
  return mockData.map((event) => {
    const eventMonth = new Date(event.start!).getMonth() + 1;

    if (eventMonth !== currentMonth || event.status === CALENDAR_STATUS_ENUMS.INACTIVE) {
      return {
        ...event,
        calendarId: `${event.calendarId}Inactive`,
      };
    }
    return event;
  });
};
