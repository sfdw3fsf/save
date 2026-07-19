import { CustomTextInput } from '@/components/common/CustomTextInput';
import DragHandle from '@/components/common/table/DragHandle';
import EditableHeader from '@/components/common/table/EditableHeader';
import { useMenuPermission } from '@/hook/auth/useMenuPermission';
import { SettingVarietiesDTO } from '@/store/setting/varieties/dto/setting-varieties.dto';
import { SettingVarietiesUpdateREQ } from '@/store/setting/varieties/request/setting-varieties.request';
import { InputEnum } from '@/utils/enums/input.enum';
import { MenuKeys } from '@/utils/enums/side-menu-type';
import { handleBlurUpdate } from '@/utils/utils/inline-update.util';
import { TableColumnsType } from 'antd';
import { useTranslation } from 'react-i18next';

type VarietiesDataColumnsProps = {
  onUpdateSettingVarietyBreed: (id: number, value: SettingVarietiesUpdateREQ) => void;
};

export const VarietiesDetailColumns = ({
  onUpdateSettingVarietyBreed,
}: VarietiesDataColumnsProps): TableColumnsType<SettingVarietiesDTO> => {
  const { t: tSetting } = useTranslation('setting');
  const { t: tCommon } = useTranslation('common');
  const { canEdit } = useMenuPermission(MenuKeys.SETTING);

  return [
    {
      title: <EditableHeader title={tSetting('varieties.table.breed')} />,
      dataIndex: 'name',
      render: (text: string, record: SettingVarietiesDTO) => (
        <CustomTextInput
          readOnly={!canEdit}
          type={InputEnum.TEXT}
          defaultValue={text}
          onBlur={handleBlurUpdate<SettingVarietiesUpdateREQ>(record.id, 'name', text, onUpdateSettingVarietyBreed)}
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
