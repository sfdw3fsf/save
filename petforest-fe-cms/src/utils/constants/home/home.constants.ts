import { ANNOUNCEMENT } from '@/utils/enums/home/home.enums';
import { InputEnum } from '@/utils/enums/input.enum';
import { ENUM_STATUS } from '@/utils/enums/status.enum';
import { FieldContent } from '@/utils/types/home.type';

export const FIELD_CONTENT: FieldContent[] = [
  {
    label: 'reservations',
    containerClassName: 'grid-cols-11',
    header: ['content', 'funeral_number', 'completion_status'],
    inputEnums: [
      { type: InputEnum.TEXT, name: 'detail', className: 'col-span-4' },
      { type: InputEnum.SELECT_SEARCH, name: 'reservationId', className: 'col-span-3' },
      { type: InputEnum.SELECT, options: ENUM_STATUS, name: 'status', className: 'col-span-3' },
    ],
    type: ANNOUNCEMENT.FUNERAL,
  },
  {
    label: 'unaccompany',
    header: ['content', 'funeral_number', 'completion_status'],
    containerClassName: 'grid-cols-11',
    inputEnums: [
      { type: InputEnum.TEXT, name: 'detail', className: 'col-span-4' },
      { type: InputEnum.SELECT_SEARCH, name: 'reservationId', className: 'col-span-3' },
      { type: InputEnum.SELECT, options: ENUM_STATUS, name: 'status', className: 'col-span-3' },
    ],
    type: ANNOUNCEMENT.NOT_ACCOMPANY,
  },
  {
    label: 'others',
    header: ['content', 'period', 'completion_status'],
    containerClassName: 'grid-cols-11',
    inputEnums: [
      { type: InputEnum.TEXT, name: 'detail', className: 'col-span-4' },
      { type: InputEnum.RANGE_PICKER, name: 'date', className: 'col-span-3' },
      { type: InputEnum.SELECT, options: ENUM_STATUS, name: 'status', className: 'col-span-3' },
    ],
    type: ANNOUNCEMENT.ETC,
  },
];
