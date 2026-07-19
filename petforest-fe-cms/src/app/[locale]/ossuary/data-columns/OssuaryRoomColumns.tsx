import CustomIconButton from '@/components/common/CustomIconButton';
import { CustomTextInput } from '@/components/common/CustomTextInput';
import EditableHeader from '@/components/common/table/EditableHeader';
import ReservationCode from '@/components/table/ReservationCode';
import { OssuaryPlaceListDTO } from '@/store/ossuary/dto/ossuary.dto';
import { OssuaryUpdateREQ } from '@/store/ossuary/request/ossuary.request';
import { ICONS } from '@/theme';
import { InputEnum } from '@/utils/enums/input.enum';
import { helperMappingPlaceColorBackGround } from '@/utils/helpers/ossuary.helper';
import { handleBlurUpdate } from '@/utils/utils/inline-update.util';
import { TableColumnsType } from 'antd';
import { TFunction } from 'i18next';
import { isNil } from 'lodash';
import Image from 'next/image';

export type OssuaryRoomColumnsProps = {
  onOpenDetail: (id: number) => void;
  onUpdateOssuary: (id: number, body: OssuaryUpdateREQ) => void;
  tOssuary: TFunction<'ossuary', undefined>;
  tCommon: TFunction<'common', undefined>;
};

export const OssuaryRoomColumns = ({
  onOpenDetail,
  tOssuary,
  tCommon,
  onUpdateOssuary,
}: OssuaryRoomColumnsProps): TableColumnsType<OssuaryPlaceListDTO> => {
  return [
    {
      title: tOssuary('table.entry_date'),
      dataIndex: 'registrationDate',
      width: 100,
      sorter: true,
    },
    {
      title: tOssuary('table.expiration_date'),
      dataIndex: 'expirationDate',
      width: 100,
      sorter: true,
    },
    {
      title: tOssuary('table.remaining_days'),
      dataIndex: 'remainingDays',
      width: 90,
      sorter: true,
      render: (value: number) => (
        <div className='flex items-center gap-2'>
          <div
            className={`w-[10px] h-[10px] rounded-full ${!isNil(value) ? helperMappingPlaceColorBackGround(value) : 'hidden'}`}></div>
          {value}
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
      width: 120,
      sorter: true,
    },
    {
      title: tOssuary('table.guardian'),
      dataIndex: 'guardianName',
      width: 120,
      sorter: true,
    },
    {
      title: tOssuary('table.contact'),
      dataIndex: 'contact',
      width: 100,
      sorter: true,
    },
    {
      title: tOssuary('table.emergency_contact'),
      dataIndex: 'emergencyContact',
      width: 120,
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
      width: 80,
      sorter: true,
      render: (value: string, record: OssuaryPlaceListDTO) => <ReservationCode code={value} id={record.reservationId} />,
    },

    {
      title: <EditableHeader title={tOssuary('table.memo')} />,
      dataIndex: 'note',
      width: 150,
      sorter: true,
      render: (text: string, record: OssuaryPlaceListDTO) => (
        <CustomTextInput
          onBlur={handleBlurUpdate<OssuaryUpdateREQ>(record.id, 'note', text, onUpdateOssuary)}
          type={InputEnum.TEXT}
          defaultValue={text}
        />
      ),
    },
    {
      title: tCommon('table.view'),
      className: 'ant-table-view-column',
      key: 'look',
      width: 50,
      align: 'center',
      fixed: 'right',
      render: (_, record, index) => (
        <CustomIconButton
          onClick={() => onOpenDetail(record.id)}
          icon={<Image src={ICONS.LOOK} alt='View' width={24} height={24} />}
        />
      ),
    },
  ];
};
