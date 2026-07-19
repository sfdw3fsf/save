import { CustomTextInput } from '@/components/common/CustomTextInput';
import DragHandle from '@/components/common/table/DragHandle';
import EditableHeader from '@/components/common/table/EditableHeader';
import { useMenuPermission } from '@/hook/auth/useMenuPermission';
import { SettingWaitingRoomDTO } from '@/store/setting/waiting-room/dto/setting-waiting-room.dto';
import { SettingWaitingRoomUpdateREQ } from '@/store/setting/waiting-room/request/setting-waiting-room.request';
import { InputEnum } from '@/utils/enums/input.enum';
import { MenuKeys } from '@/utils/enums/side-menu-type';
import { handleBlurUpdate } from '@/utils/utils/inline-update.util';
import { TableColumnsType } from 'antd';
import { useTranslation } from 'react-i18next';

type WaitingRoomDataColumnsProps = {
  onUpdateSettingWaitingRoom: (id: number, value: SettingWaitingRoomUpdateREQ) => void;
};

export const WaitingRoomColumns = ({
  onUpdateSettingWaitingRoom,
}: WaitingRoomDataColumnsProps): TableColumnsType<SettingWaitingRoomDTO> => {
  const { t: tSetting } = useTranslation('setting');
  const { t: tCommon } = useTranslation('common');
  const { canEdit } = useMenuPermission(MenuKeys.SETTING);

  return [
    {
      title: <EditableHeader title={tSetting('waiting_room.table')} />,
      dataIndex: 'name',
      render: (text: string, record: SettingWaitingRoomDTO) => (
        <CustomTextInput
          readOnly={!canEdit}
          type={InputEnum.TEXT}
          defaultValue={text}
          onBlur={handleBlurUpdate<SettingWaitingRoomUpdateREQ>(record.id, 'name', text, onUpdateSettingWaitingRoom)}
        />
      ),
    },
    canEdit &&
      ({
        title: tCommon('table.sort'),
        className: 'ant-table-sort-column',
        key: 'array',
        width: 50,
        align: 'center',
        render: () => <DragHandle />,
      } as any),
  ].filter(Boolean);
};
