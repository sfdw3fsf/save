import CustomNumericFormat from '@/components/common/CustomNumericFormat';
import CustomSelect from '@/components/common/CustomSelect';
import DragHandle from '@/components/common/table/DragHandle';
import EditableHeader from '@/components/common/table/EditableHeader';
import { useMenuPermission } from '@/hook/auth/useMenuPermission';
import { ServiceDefaultMaterialsDTO } from '@/store/setting/service/dto/setting-services.dto';
import { SettingServiceUpdateDefaultMaterialREQ } from '@/store/setting/service/request/setting-services.request';
import { MenuKeys } from '@/utils/enums/side-menu-type';
import { FilterOptions } from '@/utils/types/option.type';
import { handleChangeUpdate, handleNumericUpdate } from '@/utils/utils/inline-update.util';
import { TableColumnsType } from 'antd';
import { useTranslation } from 'react-i18next';

type ServiceDetailColumnsProps = {
  materialServiceOptions: FilterOptions;
  onUpdateSettingServiceMaterial: (id: number, value: SettingServiceUpdateDefaultMaterialREQ) => void;
};

export const ServiceDetailColumns = ({
  materialServiceOptions,
  onUpdateSettingServiceMaterial,
}: ServiceDetailColumnsProps): TableColumnsType<ServiceDefaultMaterialsDTO> => {
  const { t: tSetting } = useTranslation('setting');
  const { t: tCommon } = useTranslation('common');
  const { canEdit } = useMenuPermission(MenuKeys.SETTING);

  return [
    {
      title: <EditableHeader title={tSetting('service.detail.table.item')} />,
      dataIndex: 'materialId',
      render: (value: number, record: ServiceDefaultMaterialsDTO) => (
        <CustomSelect
          disabled={!canEdit}
          variant='borderless'
          options={materialServiceOptions}
          onChange={handleChangeUpdate<SettingServiceUpdateDefaultMaterialREQ>(
            record.id,
            'materialId',
            value,
            onUpdateSettingServiceMaterial,
          )}
          defaultValue={value}
        />
      ),
      filters: materialServiceOptions,
    },
    {
      title: <EditableHeader title={tSetting('service.detail.table.quantity')} />,
      dataIndex: 'quantity',
      sorter: true,
      render: (value: number, record: ServiceDefaultMaterialsDTO) => {
        return (
          <CustomNumericFormat
            defaultValue={value}
            onBlur={handleNumericUpdate<SettingServiceUpdateDefaultMaterialREQ>(
              record.id,
              'quantity',
              value,
              onUpdateSettingServiceMaterial,
            )}
          />
        );
      },
    },
    {
      title: tCommon('table.sort'),
      className: 'ant-table-sort-column',
      key: 'array',
      width: 50,
      align: 'center',
      render: () => <DragHandle />,
    },
  ];
};
