import CustomDatePicker from '@/components/common/CustomDatePicker';
import CustomSelect from '@/components/common/CustomSelect';
import { CustomTextInput } from '@/components/common/CustomTextInput';
import EditableHeader from '@/components/common/table/EditableHeader';
import ReservationCode from '@/components/table/ReservationCode';
import { FuneralMNGTReservationListDTO } from '@/store/funeral/reservation/dto/reservation.dto';
import { ReservationUpdateFunaralInformationREQ } from '@/store/funeral/reservation/request/reservation.request';
import { DETAIL_FUNERAL_STATUS_OPTIONS } from '@/utils/enums/funeral/reservation-mngt.enums';
import { helperFilter, helperOption } from '@/utils/helpers/common.helper';
import { parseTimestampToDate } from '@/utils/parser/date-time.parser';
import { handleBlurUpdate, handleChangeDateUpdate, handleChangeUpdate } from '@/utils/utils/inline-update.util';
import { TableColumnsType } from 'antd';
import { Dayjs } from 'dayjs';
import { TFunction } from 'i18next';
import { useTranslation } from 'react-i18next';

type HA_ListProps = {
  tHome: TFunction<'home', undefined>;
  onUpdateReservationFuneralInformation: (id: number, value: ReservationUpdateFunaralInformationREQ) => void;
};
export const HA_Columns = ({
  tHome,
  onUpdateReservationFuneralInformation,
}: HA_ListProps): TableColumnsType<FuneralMNGTReservationListDTO> => {
  const { t: tCommon } = useTranslation('common');
  const { t: tEnum } = useTranslation('enum');

  const STATUS_FILTER = helperFilter('reservation.status', DETAIL_FUNERAL_STATUS_OPTIONS, tEnum);
  const STATUS_OPTIONS = helperOption('reservation.status', DETAIL_FUNERAL_STATUS_OPTIONS, tEnum);

  return [
    {
      title: <EditableHeader title={tHome('table.status')} />,
      dataIndex: 'funeralInformation.processStatus',
      width: 80,
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
      title: tHome('table.funeral_date'),
      dataIndex: 'funeralDate',
      sorter: true,
      width: 120,
      render: (value: string) => parseTimestampToDate(value),
    },
    {
      title: tHome('table.funeral_number'),
      dataIndex: 'code',
      width: 120,
      sorter: true,
      render: (value: string, record: FuneralMNGTReservationListDTO) => <ReservationCode code={value} id={record.id} />,
    },
    {
      title: <EditableHeader title={tHome('table.delivery_date')} />,
      dataIndex: 'deliveryDate',
      width: 120,
      sorter: true,
      render: (value: Dayjs, record) => (
        <CustomDatePicker
          defaultValue={value}
          onChange={handleChangeDateUpdate<ReservationUpdateFunaralInformationREQ>(
            record.id,
            'deliveryDate',
            onUpdateReservationFuneralInformation,
          )}
        />
      ),
    },
    {
      title: tHome('table.pet_name'),
      dataIndex: 'petName',
      width: 80,
      sorter: true,
    },
    {
      title: <EditableHeader title={tCommon('table.memo')} />,
      dataIndex: 'progressNote',
      width: 300,
      render: (value: string, record: FuneralMNGTReservationListDTO) => (
        <CustomTextInput
          defaultValue={value}
          onBlur={handleBlurUpdate<ReservationUpdateFunaralInformationREQ>(
            record.id,
            'progressNote',
            value,
            onUpdateReservationFuneralInformation,
          )}
        />
      ),
    },
  ];
};
