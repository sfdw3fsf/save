import ReservationCode from '@/components/table/ReservationCode';
import { ReservationPhotoFile, ReservationPhotoListDTO } from '@/store/funeral/reservation-photo/dto/reservation-photo.dto';
import { DATE_FORMAT } from '@/utils/constants/date.constant';
import { FilterOptions } from '@/utils/types/option.type';
import { TableColumnsType } from 'antd';

import { useMemo } from 'react';
import { useTranslation } from 'react-i18next';

type PhotoColumnsProps = {
  managerOptions: FilterOptions;
};

export const PhotoColumns = ({ managerOptions }: PhotoColumnsProps): TableColumnsType<ReservationPhotoListDTO> => {
  const { t: tReservation } = useTranslation('funeral_reservation');

  const columns = useMemo<TableColumnsType<ReservationPhotoListDTO>>(
    () => [
      {
        title: tReservation('photo.table.date'),
        dataIndex: 'funeralDate',
        sorter: true,
        width: 100,
        render: (_, record) => record.reservation?.funeralDate?.format(DATE_FORMAT.DATE.HYPHEN),
      },
      {
        title: tReservation('photo.table.reception_id'),
        dataIndex: 'reservation',
        width: 100,
        sorter: true,
        render: (value, record: ReservationPhotoListDTO) => <ReservationCode code={value.code} id={record.id} />,
      },
      {
        title: tReservation('photo.table.manager'),
        dataIndex: 'reservation.leader.fullName',
        width: 100,
        sorter: true,
        filters: managerOptions.map((i) => ({ ...i, value: i.text })),
        render: (_, record) => record.reservation?.leaderName,
      },
      {
        title: tReservation('photo.table.pet_name'),
        dataIndex: 'petName',
        width: 100,
        sorter: true,
        render: (_, record) => record.reservation?.petName,
      },
      {
        title: tReservation('photo.table.guardian_name'),
        dataIndex: 'guardianName',
        width: 120,
        sorter: true,
        render: (_, record) => record.reservation?.guardianName,
      },
      {
        title: tReservation('photo.table.contact_info'),
        dataIndex: 'guardianContact',
        sorter: true,
        width: 120,
        render: (_, record) => record.reservation?.guardianPhone,
      },
      {
        title: tReservation('photo.table.pet_photo'),
        dataIndex: 'files',
        shouldCellUpdate: () => false,
        render: (files: ReservationPhotoFile[], record: ReservationPhotoListDTO) => (
          <div className='flex gap-1.5 whitespace-nowrap'>
            {files.map((file) => (
              <img loading='lazy' src={file.url} key={file.id} style={{ maxHeight: 80, borderRadius: 8, objectFit: 'contain' }} />
            ))}
          </div>
        ),
      },
    ],
    [managerOptions],
  );

  return columns;
};
