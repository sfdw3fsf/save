import CustomButton from '@/components/common/CustomButton';
import { CustomTextInput } from '@/components/common/CustomTextInput';
import Status from '@/components/common/Status';
import EditableHeader from '@/components/common/table/EditableHeader';
import ReservationCode from '@/components/table/ReservationCode';
import useRouterHandler from '@/hook/useRouterHandler';
import { OssuaryListDTO } from '@/store/ossuary/dto/ossuary.dto';
import { OssuaryUpdateREQ } from '@/store/ossuary/request/ossuary.request';
import { InputEnum } from '@/utils/enums/input.enum';
import { PLACE_STATUS } from '@/utils/enums/ossuary/ossuary-room.enum';
import { ENUM_STATUS } from '@/utils/enums/status.enum';
import { helperFilter } from '@/utils/helpers/common.helper';
import { helperMappingPlaceColorBackGround } from '@/utils/helpers/ossuary.helper';
import { parseTimestampToDate } from '@/utils/parser/date-time.parser';
import { handleBlurUpdate } from '@/utils/utils/inline-update.util';
import { ArrowRightOutlined } from '@ant-design/icons';
import { TableColumnsType } from 'antd';
import { isNil } from 'lodash';
import { useTranslation } from 'react-i18next';

type OssuaryColumnsProps = {
  onUpdateOssuary: (id: number, body: OssuaryUpdateREQ) => void;
};

export const OssuaryColumns = ({ onUpdateOssuary }: OssuaryColumnsProps): TableColumnsType<OssuaryListDTO> => {
  const { t: tOssuary } = useTranslation('ossuary');
  const { t: tEnum } = useTranslation('enum');
  const { handleURLMultiplePush } = useRouterHandler();

  const STATUS_FILTER = helperFilter('place_status', PLACE_STATUS, tOssuary);
  const NOTIFICATION_STATUS_FILTER = helperFilter('status', ENUM_STATUS, tEnum);

  return [
    {
      title: tOssuary('table.shortcut'),
      width: 80,
      align: 'center',
      render: (record: OssuaryListDTO) => (
        <div className='w-[32px] h-[32px]'>
          <CustomButton
            type='default'
            icon={
              <ArrowRightOutlined
                onClick={() =>
                  handleURLMultiplePush({ place: record.number.toString(), room: record.room, placeId: record.id.toString() })
                }
              />
            }
          />
        </div>
      ),
    },
    {
      title: tOssuary('table.category'),
      dataIndex: 'room',
      width: 60,
      sorter: true,
    },
    {
      title: tOssuary('table.room_number'),
      dataIndex: 'number',
      width: 60,
      sorter: true,
    },
    {
      title: tOssuary('table.usage_status'),
      dataIndex: 'status',
      width: 120,
      sorter: true,
      filters: STATUS_FILTER,
      render: (status) => (
        <Status
          isSuccess={status === PLACE_STATUS.CHECK_IN}
          status={tOssuary(`${status === PLACE_STATUS.CHECK_IN ? 'place_status.CHECK_IN' : 'place_status.CHECK_OUT'}` as any)}
        />
      ),
    },
    {
      title: tOssuary('table.entry_date'),
      dataIndex: 'registrationDate',
      width: 100,
      sorter: true,
      render: (value: number) => (value ? parseTimestampToDate(value.toString()) : ''),
    },
    {
      title: tOssuary('table.expiration_date'),
      dataIndex: 'expirationDate',
      width: 100,
      sorter: true,
      render: (value: number) => (value ? parseTimestampToDate(value.toString()) : ''),
    },
    {
      title: tOssuary('table.remaining_days'),
      dataIndex: 'remainingDays',
      width: 80,
      sorter: true,
      render: (value: number) => (
        <div className='flex items-center gap-2'>
          {value}
          <div
            className={`w-[10px] h-[10px] rounded-full ${!isNil(value) ? helperMappingPlaceColorBackGround(value) : 'hidden'}`}></div>
        </div>
      ),
    },
    {
      title: tOssuary('table.reception_number'),
      dataIndex: 'receiptNumber',
      width: 120,
      sorter: true,
    },
    {
      title: tOssuary('table.pet_name'),
      dataIndex: 'childName',
      width: 90,
      sorter: true,
    },
    {
      title: tOssuary('table.guardian'),
      dataIndex: 'guardianName',
      width: 90,
      sorter: true,
    },
    {
      title: tOssuary('table.contact'),
      dataIndex: 'contact',
      width: 130,
      sorter: true,
    },
    {
      title: tOssuary('table.emergency_contact'),
      dataIndex: 'emergencyContact',
      width: 150,
      sorter: true,
    },
    {
      title: tOssuary('table.address'),
      dataIndex: 'address',
      className: 'whitespace-nowrap',
      width: 150,
      sorter: true,
    },
    {
      title: tOssuary('table.funeral_number'),
      dataIndex: 'reservationNumber',
      width: 100,
      sorter: true,
      render: (value: string, record: OssuaryListDTO) => <ReservationCode code={value} id={record.reservationId} />,
    },
    {
      title: tOssuary('table.contract_notification'),
      dataIndex: 'contractNotificationStatus',
      width: 150,
      sorter: true,
      filters: NOTIFICATION_STATUS_FILTER,
      render: (status) =>
        status && <Status isSuccess={status === ENUM_STATUS.COMPLETE} status={tEnum(`status.${status}` as any)} />,
    },
    {
      title: tOssuary('table.expiration_notification'),
      dataIndex: 'expirationNotificationStatus',
      width: 150,
      sorter: true,
      filters: NOTIFICATION_STATUS_FILTER,
      render: (status) =>
        status && <Status isSuccess={status === ENUM_STATUS.COMPLETE} status={tEnum(`status.${status}` as any)} />,
    },
    {
      title: tOssuary('table.extension_payment_notification'),
      dataIndex: 'extensionDepositNotificationStatus',
      width: 150,
      sorter: true,
      filters: NOTIFICATION_STATUS_FILTER,
      render: (status) =>
        status && <Status isSuccess={status === ENUM_STATUS.COMPLETE} status={tEnum(`status.${status}` as any)} />,
    },
    {
      title: tOssuary('table.extension_confirmation_notification'),
      dataIndex: 'extensionConfirmationNotificationStatus',
      width: 150,
      sorter: true,
      filters: NOTIFICATION_STATUS_FILTER,
      render: (status) =>
        status && <Status isSuccess={status === ENUM_STATUS.COMPLETE} status={tEnum(`status.${status}` as any)} />,
    },
    {
      title: <EditableHeader title={tOssuary('table.memo')} />,
      dataIndex: 'note',
      sorter: true,
      render: (text: string, record: OssuaryListDTO) => (
        <CustomTextInput
          onBlur={handleBlurUpdate<OssuaryUpdateREQ>(record.id, 'note', text, onUpdateOssuary)}
          type={InputEnum.TEXT}
          defaultValue={text}
          className='min-w-[150px]'
        />
      ),
    },
  ];
};
