import CustomButton from '@/components/common/CustomButton';
import { CustomTextInput } from '@/components/common/CustomTextInput';
import EditableHeader from '@/components/common/table/EditableHeader';
import { OssuaryDetailUsageHistoryDTO } from '@/store/ossuary/dto/ossuary.dto';
import { InputEnum } from '@/utils/enums/input.enum';
import { DownloadOutlined } from '@ant-design/icons';
import { TableColumnsType } from 'antd';
import { TFunction } from 'i18next';

type OssuaryUsageHistoryColumnsProps = {
  tOssuary: TFunction<'ossuary', undefined>;
  onUpdateOssuaryDetailUsageHistory: (id: number, values: { note: string }) => void;
  onDownloadPdf: (id: number) => void;
};

export const OssuaryUsageHistoryColumns = ({
  tOssuary,
  onUpdateOssuaryDetailUsageHistory,
  onDownloadPdf,
}: OssuaryUsageHistoryColumnsProps): TableColumnsType<OssuaryDetailUsageHistoryDTO> => {
  return [
    {
      title: tOssuary('table.category'),
      dataIndex: 'place',
      width: 100,
      sorter: true,
    },
    {
      title: tOssuary('table.room_number'),
      dataIndex: 'room',
      width: 100,
      sorter: true,
    },
    {
      title: tOssuary('table.entry_date'),
      dataIndex: 'registrationDate',
      width: 150,
      sorter: true,
    },
    {
      title: tOssuary('table.expiration_date'),
      dataIndex: 'expirationDate',
      width: 150,
      sorter: true,
    },
    {
      title: tOssuary('table.extension'),
      width: 200,
      align: 'center',
      render: (_, record) => (
        <CustomButton
          onClick={() => {
            onDownloadPdf(record.id);
          }}
          type='text'
          icon={<DownloadOutlined />}>
          {tOssuary('button.contract')}
        </CustomButton>
      ),
    },
    {
      title: tOssuary('table.remaining_days'),
      dataIndex: 'remainingDays',
      width: 150,
      sorter: true,
      render: (text) => (
        <div className='flex items-center gap-2'>
          <div className='w-[10px] h-[10px] rounded-full bg-blue-500'></div>
          {text}
        </div>
      ),
    },

    {
      title: <EditableHeader title={tOssuary('table.memo')} />,
      dataIndex: 'memo',
      width: 150,
      sorter: true,
      render: (text: string, record) => (
        <CustomTextInput
          type={InputEnum.TEXT}
          defaultValue={text}
          onBlur={(e) => {
            onUpdateOssuaryDetailUsageHistory(record.id, { note: e.target.value });
          }}
        />
      ),
    },
  ];
};
