import CustomDatePicker from '@/components/common/CustomDatePicker';
import CustomFileUploadInput from '@/components/common/CustomFileUploadInput';
import CustomIconButton from '@/components/common/CustomIconButton';
import CustomSelect from '@/components/common/CustomSelect';
import { CustomTextInput } from '@/components/common/CustomTextInput';
import CustomTimePicker from '@/components/common/CustomTimePicker';
import EditableHeader from '@/components/common/table/EditableHeader';
import { useMenuPermission } from '@/hook/auth/useMenuPermission';
import { PopupDTO } from '@/store/post/dto/post.dto';
import { PopupUpdateREQ } from '@/store/post/request/post.request';
import { DATE_FORMAT } from '@/utils/constants/date.constant';
import { InputEnum } from '@/utils/enums/input.enum';
import { POST_VISIBILITY } from '@/utils/enums/post/post.enum';
import { MenuKeys } from '@/utils/enums/side-menu-type';
import { helperFilter, helperOption } from '@/utils/helpers/common.helper';
import { addTimeToDate } from '@/utils/parser/date-time.parser';
import { orUndefined } from '@/utils/parser/io.parser';
import { handleBlurUpdate, handleChangeUpdate } from '@/utils/utils/inline-update.util';
import { useUploadFile } from '@/utils/utils/upload-file-to-s3.util';
import { FileImageOutlined } from '@ant-design/icons';
import { TableColumnsType } from 'antd';
import { UploadFile } from 'antd/lib';
import dayjs, { Dayjs } from 'dayjs';
import { useTranslation } from 'react-i18next';

type PopupDataColumnsProps = {
  onOpenImageModal: (imageId: number | null, popupId: number) => void;
  onUpdatePopup: (id: number, values: PopupUpdateREQ) => void;
  onUploadFile: (id: number, file: UploadFile<any>[]) => void;
};

export const PopupDataColumns = ({
  onOpenImageModal,
  onUpdatePopup,
  onUploadFile,
}: PopupDataColumnsProps): TableColumnsType<PopupDTO> => {
  const { t: tPost } = useTranslation('post');
  const { t: tEnum } = useTranslation('enum');
  const { canEdit } = useMenuPermission(MenuKeys.POST);
  const { uploadFile } = useUploadFile();

  return [
    {
      title: <EditableHeader title={tPost('popup.table.public_status')} />,
      dataIndex: 'visibility',
      width: 160,
      sorter: true,
      filters: helperFilter('post.visibility', POST_VISIBILITY, tEnum),
      render: (visibility: POST_VISIBILITY, record) => (
        <CustomSelect
          disabled={!canEdit}
          className={visibility === POST_VISIBILITY.RELEASE ? 'active-select' : ''}
          variant='borderless'
          options={helperOption('post.visibility', POST_VISIBILITY, tEnum)}
          value={tEnum(`post.visibility.${visibility}` as any)}
          onChange={handleChangeUpdate<PopupUpdateREQ>(record.id, 'visibility', visibility, onUpdatePopup)}
        />
      ),
    },
    {
      title: <EditableHeader title={tPost('popup.table.title')} />,
      dataIndex: 'title',
      sorter: true,
      width: 280,
      render: (text: string, record) => (
        <CustomTextInput
          readOnly={!canEdit}
          type={InputEnum.TEXT}
          defaultValue={text}
          onBlur={handleBlurUpdate<PopupUpdateREQ>(record.id, 'title', text, onUpdatePopup)}
        />
      ),
    },
    {
      title: <EditableHeader title={tPost('popup.table.start_date')} />,
      dataIndex: 'startAt',
      sorter: true,
      width: 150,
      render: (text: string, record) => (
        <CustomDatePicker
          disabled={!canEdit}
          defaultValue={dayjs(text)}
          onChange={(value) => {
            onUpdatePopup(record.id, { startAt: orUndefined(addTimeToDate(value, record.startTimeAt)?.valueOf()) });
          }}
        />
      ),
    },
    {
      title: <EditableHeader title={tPost('popup.table.start_time')} />,
      dataIndex: 'startTimeAt',
      sorter: true,
      width: 150,
      render: (text: Dayjs, record) => (
        <CustomTimePicker
          disabled={!canEdit}
          format={DATE_FORMAT.TIME.PICKER_HOUR_MIN}
          value={text}
          onChange={(value) => {
            onUpdatePopup(record.id, { startAt: orUndefined(value?.valueOf()) });
          }}
        />
      ),
    },
    {
      title: <EditableHeader title={tPost('popup.table.end_date')} />,
      dataIndex: 'endAt',
      sorter: true,
      width: 150,
      render: (text: Dayjs, record) => (
        <CustomDatePicker
          disabled={!canEdit}
          defaultValue={dayjs(text)}
          onChange={(value) => {
            onUpdatePopup(record.id, { endAt: orUndefined(addTimeToDate(value, record.endTimeAt)?.valueOf()) });
          }}
        />
      ),
    },
    {
      title: <EditableHeader title={tPost('popup.table.end_time')} />,
      dataIndex: 'endTimeAt',
      sorter: true,
      width: 150,
      render: (text: Dayjs, record) => (
        <CustomTimePicker
          disabled={!canEdit}
          format={DATE_FORMAT.TIME.PICKER_HOUR_MIN}
          value={text}
          onChange={(value) => {
            onUpdatePopup(record.id, { endAt: orUndefined(value?.valueOf()) });
          }}
        />
      ),
    },
    {
      title: <EditableHeader title={tPost('popup.table.link')} />,
      dataIndex: 'connectionLink',
      sorter: true,
      width: 280,
      render: (text: string, record) => (
        <CustomTextInput
          disabled={!canEdit}
          type={InputEnum.TEXT}
          defaultValue={text}
          onBlur={handleBlurUpdate<PopupUpdateREQ>(record.id, 'connectionLink', text, onUpdatePopup)}
        />
      ),
    },
    canEdit &&
      ({
        title: tPost('popup.table.image'),
        key: 'image',
        width: 80,
        align: 'center',
        className: 'ant-column-center',
        render: (_: any, record: PopupDTO) => (
          <CustomFileUploadInput
            name={'maintenanceImages'}
            onChange={async (files) => {
              onUploadFile(record.id, files);
            }}
            maxCount={1}
            title={false}
            displayFiles={false}
          />
        ),
      } as any),
    {
      title: tPost('popup.table.preview'),
      key: 'preview',
      width: 80,
      align: 'center',
      fixed: 'right',
      render: (_, record) =>
        record.imageId && (
          <CustomIconButton
            onClick={() => {
              onOpenImageModal(record.imageId, record.id);
            }}
            icon={<FileImageOutlined />}
          />
        ),
    },
  ];
};
