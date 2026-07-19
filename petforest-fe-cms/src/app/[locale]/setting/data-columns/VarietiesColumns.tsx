import CustomIconButton from '@/components/common/CustomIconButton';
import { CustomTextInput } from '@/components/common/CustomTextInput';
import DragHandle from '@/components/common/table/DragHandle';
import EditableHeader from '@/components/common/table/EditableHeader';
import { useMenuPermission } from '@/hook/auth/useMenuPermission';
import { SettingVarietiesDTO } from '@/store/setting/varieties/dto/setting-varieties.dto';
import { SettingVarietiesUpdateREQ } from '@/store/setting/varieties/request/setting-varieties.request';
import { ICONS } from '@/theme';
import { InputEnum } from '@/utils/enums/input.enum';
import { SETTING_PET_TYPE } from '@/utils/enums/setting/setting-mngt.enums';
import { MenuKeys } from '@/utils/enums/side-menu-type';
import { helperFilter } from '@/utils/helpers/common.helper';
import { handleBlurUpdate } from '@/utils/utils/inline-update.util';
import { TableColumnsType } from 'antd';
import Image from 'next/image';
import { useTranslation } from 'react-i18next';

type VarietiesDataColumnsProps = {
  onUpdateSettingVarieties: (id: number, value: SettingVarietiesUpdateREQ) => void;
  onDetail: (id: number) => void;
};

export const VarietiesColumns = ({
  onUpdateSettingVarieties,
  onDetail,
}: VarietiesDataColumnsProps): TableColumnsType<SettingVarietiesDTO> => {
  const { t: tSetting } = useTranslation('setting');
  const { t: tCommon } = useTranslation('common');
  const { canEdit } = useMenuPermission(MenuKeys.SETTING);

  const TYPE_FILTER = helperFilter('varieties.filter', SETTING_PET_TYPE, tSetting);

  return [
    {
      title: <EditableHeader title={tSetting('varieties.table.type')} />,
      dataIndex: 'name',
      render: (text: string, record: SettingVarietiesDTO) => (
        <CustomTextInput
          readOnly={!canEdit}
          type={InputEnum.TEXT}
          defaultValue={text}
          onBlur={handleBlurUpdate<SettingVarietiesUpdateREQ>(record.id, 'name', text, onUpdateSettingVarieties)}
        />
      ),
      filters: TYPE_FILTER,
    },
    {
      title: tSetting('varieties.table.breed'),
      dataIndex: 'breed',
    },
    {
      title: tSetting('varieties.table.management'),
      key: 'look',
      className: 'ant-table-view-column',
      width: canEdit ? 100 : undefined,
      align: 'center',
      render: (_: any, record: SettingVarietiesDTO, index: number) => (
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
