import ReservationCode from '@/components/table/ReservationCode';
import { FuneralMNGTReservationListDTO } from '@/store/funeral/reservation/dto/reservation.dto';
import { DETAIL_FUNERAL_STATUS_OPTIONS } from '@/utils/enums/funeral/reservation-mngt.enums';
import { helperFilter } from '@/utils/helpers/common.helper';
import { parseTimestampToDate } from '@/utils/parser/date-time.parser';
import { TableColumnsType } from 'antd';
import { useTranslation } from 'react-i18next';

type ReservationColumnsProps = {
  onDetail: (id: number) => void;
};

export const ReservationDeletedColumns = ({
  onDetail,
}: ReservationColumnsProps): TableColumnsType<FuneralMNGTReservationListDTO> => {
  const { t: tReservation } = useTranslation('funeral_reservation');
  const { t: tCommon } = useTranslation('common');
  const { t: tEnum } = useTranslation('enum');

  const STATUS_FILTER = helperFilter('reservation.status', DETAIL_FUNERAL_STATUS_OPTIONS, tEnum);

  return [
    {
      title: tReservation('table.id'),
      dataIndex: 'code',
      width: 120,
      sorter: true,

      render: (value: string, record) => <ReservationCode code={value} id={record.id} />,
    },
    {
      title: tReservation('table.date'),
      dataIndex: 'funeralDate',
      sorter: true,
      width: 100,
      render: (value: string) => (value ? parseTimestampToDate(value) : ''),
    },
    {
      title: tReservation('table.time'),
      dataIndex: 'funeralTime',
      width: 100,
      sorter: true,
    },
    {
      title: tReservation('table.status'),
      dataIndex: 'funeralInformation.processStatus',
      width: 100,
      sorter: true,
      filters: STATUS_FILTER,
      render: (value: string) => tEnum(`reservation.status.${value}` as any),
    },
    {
      title: tReservation('table.weight'),
      dataIndex: 'petWeight',
      sorter: true,
      width: 100,
    },
    {
      title: tReservation('table.pet_name'),
      dataIndex: 'petName',
      width: 100,
    },
    {
      title: tReservation('table.pet_name'),
      dataIndex: 'petType',
      width: 100,
    },
    {
      title: tReservation('table.progress'),
      dataIndex: 'progress',
      width: 100,
    },
    {
      title: tReservation('table.progress_note'),
      dataIndex: 'progressNote',
      width: 100,
    },
    {
      title: tReservation('table.service'),
      dataIndex: 'funeralService',
      filters: [],
      width: 100,
    },
    {
      title: tReservation('table.additional_services'),
      dataIndex: 'additionalService',
      width: 100,
    },
  ];
};
