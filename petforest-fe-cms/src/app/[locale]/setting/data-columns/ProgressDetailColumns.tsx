import { CustomTextInput } from '@/components/common/CustomTextInput';
import DragHandle from '@/components/common/table/DragHandle';
import EditableHeader from '@/components/common/table/EditableHeader';
import { useMenuPermission } from '@/hook/auth/useMenuPermission';
import { SettingProgressDTO } from '@/store/setting/progress/dto/setting-progress.dto';
import { SettingProgressUpdateREQ } from '@/store/setting/progress/request/setting-progress.request';
import { InputEnum } from '@/utils/enums/input.enum';
import { MenuKeys } from '@/utils/enums/side-menu-type';
import { handleBlurUpdate } from '@/utils/utils/inline-update.util';
import { TableColumnsType } from 'antd';
import { useTranslation } from 'react-i18next';

type ProgressDetailColumnsProps = {
  onUpdateSettingSubProgress: (id: number, value: SettingProgressUpdateREQ) => void;
};

export const ProgressDetailColumns = ({
  onUpdateSettingSubProgress,
}: ProgressDetailColumnsProps): TableColumnsType<SettingProgressDTO> => {
  const { t: tSetting } = useTranslation('setting');
  const { t: tCommon } = useTranslation('common');
  const { canEdit } = useMenuPermission(MenuKeys.SETTING);

  return [
    {
      title: <EditableHeader title={tSetting('progress.detail.sub_progress')} />,
      dataIndex: 'name',
      width: 150,
      render: (text: string, record: SettingProgressDTO) => (
        <CustomTextInput
          readOnly={!canEdit}
          type={InputEnum.TEXT}
          defaultValue={text}
          onBlur={handleBlurUpdate<SettingProgressUpdateREQ>(record.id, 'name', text, onUpdateSettingSubProgress)}
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
