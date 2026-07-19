import CustomIconButton from '@/components/common/CustomIconButton';
import { CustomTextInput } from '@/components/common/CustomTextInput';
import DragHandle from '@/components/common/table/DragHandle';
import EditableHeader from '@/components/common/table/EditableHeader';
import { useMenuPermission } from '@/hook/auth/useMenuPermission';
import { SettingProgressDTO } from '@/store/setting/progress/dto/setting-progress.dto';
import { SettingProgressUpdateREQ } from '@/store/setting/progress/request/setting-progress.request';
import { ICONS } from '@/theme';
import { InputEnum } from '@/utils/enums/input.enum';
import { MenuKeys } from '@/utils/enums/side-menu-type';
import { handleBlurUpdate } from '@/utils/utils/inline-update.util';
import { TableColumnsType } from 'antd';
import Image from 'next/image';
import { useTranslation } from 'react-i18next';

type ProgressColumnsProps = {
  onDetail: (id: number) => void;
  onUpdateSettingProgress: (id: number, value: SettingProgressUpdateREQ) => void;
  canEdit?: boolean;
};

export const ProgressColumns = ({
  onDetail,
  onUpdateSettingProgress,
}: ProgressColumnsProps): TableColumnsType<SettingProgressDTO> => {
  const { t: tSetting } = useTranslation('setting');
  const { t: tCommon } = useTranslation('common');
  const { canEdit } = useMenuPermission(MenuKeys.SETTING);

  return [
    {
      title: <EditableHeader title={tSetting('progress.table.progress')} />,
      dataIndex: 'name',
      width: 250,
      render: (text: string, record: SettingProgressDTO) => (
        <CustomTextInput
          readOnly={!canEdit}
          onBlur={handleBlurUpdate<SettingProgressUpdateREQ>(record.id, 'name', text, onUpdateSettingProgress)}
          type={InputEnum.TEXT}
          defaultValue={text}
          maxLength={12}
        />
      ),
    },
    {
      title: tSetting('progress.table.sub_progress'),
      dataIndex: 'subProgresses',
      width: 250,
    },
    {
      title: tSetting('progress.table.sub_progress_management'),
      key: 'look',
      className: 'ant-table-view-column',
      width: 150,
      align: 'center',
      render: (_: any, record: any, index: number) => (
        <CustomIconButton
          onClick={() => {
            onDetail(record.id);
          }}
          icon={<Image src={ICONS.LOOK} alt='Look' width={24} height={24} />}
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
