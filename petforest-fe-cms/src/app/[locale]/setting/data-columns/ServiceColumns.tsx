import CustomColorPicker from '@/components/common/CustomColorPicker';
import CustomIconButton from '@/components/common/CustomIconButton';
import CustomNumericFormat from '@/components/common/CustomNumericFormat';
import { CustomTextInput } from '@/components/common/CustomTextInput';
import DragHandle from '@/components/common/table/DragHandle';
import EditableHeader from '@/components/common/table/EditableHeader';
import { useMenuPermission } from '@/hook/auth/useMenuPermission';
import { SettingServicesDTO } from '@/store/setting/service/dto/setting-services.dto';
import { SettingServiceUpdateREQ } from '@/store/setting/service/request/setting-services.request';
import { ICONS } from '@/theme';
import { InputEnum } from '@/utils/enums/input.enum';
import { MenuKeys } from '@/utils/enums/side-menu-type';
import { handleBlurUpdate, handleChangeUpdate } from '@/utils/utils/inline-update.util';
import { TableColumnsType } from 'antd';
import Image from 'next/image';
import { useTranslation } from 'react-i18next';

type ServiceColumnsProps = {
  onDetail: (id: number) => void;
  onUpdateSettingService: (id: number, body: SettingServiceUpdateREQ) => void;
};

export const ServiceColumns = ({
  onDetail,
  onUpdateSettingService,
}: ServiceColumnsProps): TableColumnsType<SettingServicesDTO> => {
  const { t: tSetting } = useTranslation('setting');
  const { t: tCommon } = useTranslation('common');
  const { canEdit } = useMenuPermission(MenuKeys.SETTING);

  return [
    {
      title: <EditableHeader title={tSetting('service.table.division')} />,
      dataIndex: 'name',
      width: 128,
      render: (text: string, record: SettingServicesDTO) => (
        <CustomTextInput
          readOnly={!canEdit}
          type={InputEnum.TEXT}
          defaultValue={text}
          onBlur={handleBlurUpdate<SettingServiceUpdateREQ>(record.id, 'name', text, onUpdateSettingService)}
        />
      ),
    },
    {
      title: <EditableHeader title={tSetting('service.table.service')} />,
      dataIndex: 'type',
      width: 128,
      render: (text: string, record: SettingServicesDTO) => (
        <CustomTextInput
          readOnly={!canEdit}
          type={InputEnum.TEXT}
          defaultValue={text}
          onBlur={handleBlurUpdate<SettingServiceUpdateREQ>(record.id, 'type', text, onUpdateSettingService)}
        />
      ),
    },
    {
      title: tSetting('service.table.article'),
      dataIndex: 'serviceDefaultMaterials',
      className: 'whitespace-nowrap',
      width: 792,
    },
    {
      title: <EditableHeader title={tSetting('service.table.amount')} />,
      dataIndex: 'price',
      render: (value: number, record: SettingServicesDTO) => (
        <CustomNumericFormat
          readOnly={!canEdit}
          value={value}
          onBlur={handleChangeUpdate<SettingServiceUpdateREQ>(record.id, 'price', value, onUpdateSettingService)}
        />
      ),
      width: 156,
    },
    {
      title: <EditableHeader title={tSetting('service.table.color')} />,
      dataIndex: 'color',
      render: (value: string, record: SettingServicesDTO) => {
        return (
          <CustomColorPicker
            value={value}
            onChange={handleChangeUpdate<SettingServiceUpdateREQ>(record.id, 'color', value, onUpdateSettingService)}
            disabled={!canEdit}
          />
        );
      },
      width: 136,
    },
    {
      title: tSetting('service.table.item_management'),
      key: 'look',
      className: 'ant-table-view-column',
      width: canEdit ? 80 : undefined,
      align: 'center',
      render: (_: any, record: SettingServicesDTO, index: number) => (
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
        width: 80,
        align: 'center',
        render: () => <DragHandle />,
      } as any),
  ].filter(Boolean);
};
