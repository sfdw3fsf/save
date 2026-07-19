declare module '@toast-ui/react-calendar' {
  import { EventObject, ExternalEventTypes, Options, TZDate } from '@toast-ui/calendar';
  import { Component } from 'react';
  type ReactCalendarOptions = Omit<Options, 'defaultView'>;
  type CalendarView = Required<Options>['defaultView'];
  type CalendarExternalEventNames = Extract<keyof ExternalEventTypes, string>;
  type ReactCalendarEventNames = `on${Capitalize<CalendarExternalEventNames>}`;
  type ReactCalendarEventHandler = ExternalEventTypes[CalendarExternalEventNames];
  type ReactCalendarExternalEvents = {
    [events in ReactCalendarEventNames]: ReactCalendarEventHandler;
  };

  export interface ReactEventObject extends Partial<Partial<EventObject>> {
    id: string; // Unique event identifier
    calendarId: string; // Calendar ID for this event
    title: string; // Title of the event
    category: 'milestone' | 'task' | 'allday' | 'time'; // Category of the event
    dueDateClass?: string; // CSS class for due date (optional)
    start: TZDate; // Event start time
    end?: TZDate; // Event end time (optional)
    isReadOnly?: boolean; // Whether the event is read-only (optional)
    state?: number | string;
    location?: string;
    status?: CALENDAR_STATUS_ENUMS;
  }

  interface Props extends ReactCalendarOptions, ReactCalendarExternalEvents {
    height: string;
    events?: Partial<EventObject>[];
    view?: CalendarView;
  }

  export default class ToastUIReactCalendar extends Component<Props> {
    containerElementRef: React.RefObject<HTMLDivElement>;
    calendarInstance: ToastUICalendar | null;
    static defaultProps: {
      height: string;
      view: string;
    };
    componentDidMount(): void;
    shouldComponentUpdate(nextProps: Readonly<Props>): boolean;
    componentWillUnmount(): void;
    setCalendars(calendars?: Options['calendars']): void;
    setEvents(events?: Partial<EventObject>[]): void;
    bindEventHandlers(externalEvents: ReactCalendarExternalEvents): void;
    getInstance(): ToastUICalendar | null;
    getRootElement(): HTMLDivElement | null;
    render(): JSX.Element;
  }
}
