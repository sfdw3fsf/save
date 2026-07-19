import CustomNumericFormat from '@/components/common/CustomNumericFormat';
import CustomSelect from '@/components/common/CustomSelect';
import { CustomTextInput } from '@/components/common/CustomTextInput';
import EditableHeader from '@/components/common/table/EditableHeader';
import ReservationCode from '@/components/table/ReservationCode';
import { FuneralMNGTReservationListDTO } from '@/store/funeral/reservation/dto/reservation.dto';
import {
  ReservationUpdateFunaralInformationREQ,
  ReservationUpdatePetInformationREQ,
  ReservationUpdateREQ,
} from '@/store/funeral/reservation/request/reservation.request';
import { DETAIL_FUNERAL_RESERVATION_OPTIONS, DETAIL_FUNERAL_STATUS_OPTIONS } from '@/utils/enums/funeral/reservation-mngt.enums';
import { helperFilter, helperOption } from '@/utils/helpers/common.helper';
import { parseTimestampToDate } from '@/utils/parser/date-time.parser';
import { FilterOptions } from '@/utils/types/option.type';
import { handleBlurUpdate, handleChangeUpdate, handleNumericUpdate } from '@/utils/utils/inline-update.util';
import { TableColumnsType } from 'antd';
import { useTranslation } from 'react-i18next';

type ReservationColumnsProps = {
  onDetail: (id: number) => void;
  reservationTimeOptions: FilterOptions<string>;
  petTypeOptions: FilterOptions;
  serviceOptions: FilterOptions;
  onUpdateReservationPetInformation: (id: number, value: ReservationUpdatePetInformationREQ) => void;
  onUpdateReservationFuneralInformation: (id: number, value: ReservationUpdateFunaralInformationREQ) => void;
  onUpdateReservationInformation: (id: number, value: ReservationUpdateREQ) => void;
};

export const ReservationColumns = ({
  onDetail,
  reservationTimeOptions,
  petTypeOptions,
  serviceOptions,
  onUpdateReservationPetInformation,
  onUpdateReservationFuneralInformation,
  onUpdateReservationInformation,
}: ReservationColumnsProps): TableColumnsType<FuneralMNGTReservationListDTO> => {
  const { t: tReservation } = useTranslation('funeral_reservation');
  const { t: tCommon } = useTranslation('common');
  const { t: tEnum } = useTranslation('enum');

  const STATUS_OPTIONS = helperOption('reservation.status', DETAIL_FUNERAL_STATUS_OPTIONS, tEnum);
  const STATUS_FILTER = helperFilter('reservation.status', DETAIL_FUNERAL_STATUS_OPTIONS, tEnum);

  return [
    {
      title: tReservation('table.id'),
      dataIndex: 'code',
      width: 120,
      sorter: true,
      render: (text: string, record: FuneralMNGTReservationListDTO) => {
        const showRedPoint =
          record['funeralInformation.processStatus'] === DETAIL_FUNERAL_STATUS_OPTIONS.RESERVATION &&
          record['funeralInformation.reservationStatus'] !== DETAIL_FUNERAL_RESERVATION_OPTIONS.CONFIRMED &&
          record['funeralInformation.reservationStatus'] !== DETAIL_FUNERAL_RESERVATION_OPTIONS.CANCELLATION;
        const showBluePoint = record.isRegisterPhoto;

        return (
          <div className='flex items-center gap-2 text-id'>
            <ReservationCode code={text} id={record.id} />
            {showRedPoint && <div className={`w-[10px] h-[10px] rounded-full bg-error-500`} />}
            {showBluePoint && <div className={`w-[10px] h-[10px] rounded-full bg-blue-500`} />}
          </div>
        );
      },
    },
    {
      title: tReservation('table.date'),
      dataIndex: 'funeralDate',
      sorter: true,
      width: 90,
      render: (value: string) => (value ? parseTimestampToDate(value) : ''),
    },
    {
      title: <EditableHeader title={tReservation('table.time')} />,
      dataIndex: 'funeralTime',
      width: 90,
      sorter: true,
      render: (value: string, record: FuneralMNGTReservationListDTO) => (
        <CustomSelect
          key={value}
          variant='borderless'
          defaultValue={value}
          options={reservationTimeOptions}
          onChange={handleChangeUpdate<ReservationUpdateFunaralInformationREQ>(
            record.id,
            'funeralTime',
            record.funeralTime,
            onUpdateReservationFuneralInformation,
            { funeralDate: record.funeralDate },
          )}
        />
      ),
    },
    {
      title: <EditableHeader title={tReservation('table.status')} />,
      dataIndex: 'funeralInformation.processStatus',
      width: 90,
      sorter: true,
      filters: STATUS_FILTER,
      render: (value: string, record: FuneralMNGTReservationListDTO) => (
        <CustomSelect
          variant='borderless'
          defaultValue={value}
          options={STATUS_OPTIONS}
          onChange={handleChangeUpdate<ReservationUpdateFunaralInformationREQ>(
            record.id,
            'processStatus',
            record['funeralInformation.processStatus'],
            onUpdateReservationFuneralInformation,
          )}
        />
      ),
    },
    {
      title: <EditableHeader title={tReservation('table.weight')} />,
      dataIndex: 'petWeight',
      sorter: true,
      width: 70,
      render: (value: number, record: FuneralMNGTReservationListDTO) => (
        <CustomNumericFormat
          defaultValue={value}
          onBlur={handleNumericUpdate<ReservationUpdatePetInformationREQ>(
            record.id,
            'weight',
            value,
            onUpdateReservationPetInformation,
          )}
        />
      ),
    },
    {
      title: <EditableHeader title={tReservation('table.pet_name')} />,
      dataIndex: 'petName',
      width: 95,
      render: (value: string, record: FuneralMNGTReservationListDTO) => (
        <CustomTextInput
          key={value}
          defaultValue={value}
          onBlur={handleBlurUpdate<ReservationUpdatePetInformationREQ>(
            record.id,
            'petName',
            value,
            onUpdateReservationPetInformation,
          )}
        />
      ),
    },
    {
      title: <EditableHeader title={tReservation('table.pet_type')} />,
      dataIndex: 'petType',
      width: 100,
      render: (value: number, record: FuneralMNGTReservationListDTO) => (
        <CustomSelect
          key={value}
          onChange={handleChangeUpdate<ReservationUpdatePetInformationREQ>(
            record.id,
            'petTypeId',
            record.petType,
            onUpdateReservationPetInformation,
          )}
          variant='borderless'
          defaultValue={value}
          options={petTypeOptions}
        />
      ),
    },
    {
      title: tReservation('table.progress'),
      dataIndex: 'progress',
      width: 120,
      render: (value: string) => <p className='whitespace-nowrap'>{value}</p>,
    },
    {
      title: <EditableHeader title={tReservation('table.progress_note')} />,
      dataIndex: 'progressNote',
      render: (value: string, record: FuneralMNGTReservationListDTO) => (
        <CustomTextInput
          defaultValue={value}
          className='!min-w-[450px]'
          onBlur={handleBlurUpdate<ReservationUpdateFunaralInformationREQ>(
            record.id,
            'progressNote',
            value,
            onUpdateReservationFuneralInformation,
          )}
        />
      ),
    },
    {
      title: tReservation('table.service'),
      dataIndex: 'funeralService',
      filters: serviceOptions,
      width: 150,
    },
    {
      title: <EditableHeader title={tReservation('table.additional_services')} />,
      dataIndex: 'additionalServices',
      render: (value: string, record: FuneralMNGTReservationListDTO) => (
        <CustomTextInput
          defaultValue={value}
          classNameInput='!text-[14px]'
          className='!min-w-[250px]'
          onBlur={handleBlurUpdate<ReservationUpdateREQ>(record.id, 'reservationNote', value, onUpdateReservationInformation)}
        />
      ),
    },
  ];
};
