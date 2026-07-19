import CustomIconButton from '@/components/common/CustomIconButton';
import CustomSelect from '@/components/common/CustomSelect';
import { CustomTextInput } from '@/components/common/CustomTextInput';
import DragHandle from '@/components/common/table/DragHandle';
import EditableHeader from '@/components/common/table/EditableHeader';
import { useMenuPermission } from '@/hook/auth/useMenuPermission';
import { MaterialDTO } from '@/store/material/dto/material.dto';
import { MaterialUpdateREQ, MaterialUpdateStatusREQ } from '@/store/material/request/material.request';
import { ICONS } from '@/theme';
import { ACTIVE_STATE } from '@/utils/enums/common.enum';
import { InputEnum } from '@/utils/enums/input.enum';
import { MenuKeys } from '@/utils/enums/side-menu-type';
import { helperFilter, helperOption } from '@/utils/helpers/common.helper';
import { handleBlurUpdate, handleChangeUpdate } from '@/utils/utils/inline-update.util';
import { TableColumnsType } from 'antd';
import clsx from 'clsx';
import Image from 'next/image';
import { useTranslation } from 'react-i18next';

type MES_ColumnsProps = {
  onDetail: (index: number) => void;
  onUpdateMaterial: (id: number, values: MaterialUpdateREQ) => void;
  onUpdateMaterialStatus: (id: number, values: MaterialUpdateStatusREQ) => void;
};

export const MES_Columns = ({
  onDetail,
  onUpdateMaterial,
  onUpdateMaterialStatus,
}: MES_ColumnsProps): TableColumnsType<MaterialDTO> => {
  const { t: tExpendablesSupplies } = useTranslation('material_expendables_supplies');
  const { t: tCommon } = useTranslation('common');
  const { t: tMaterial } = useTranslation('material');
  const { t: tEnum } = useTranslation('enum');
  const { canEdit } = useMenuPermission(MenuKeys.MATERIAL_EXPENDABLES_SUPPLIES);

  const STATE_FILTER = helperOption('active_status', ACTIVE_STATE, tEnum);

  return [
    {
      title: <EditableHeader title={tExpendablesSupplies('table.name')} />,
      dataIndex: 'name',
      sorter: true,
      width: 100,
      render: (text: string, record: any) => (
        <CustomTextInput
          readOnly={!canEdit}
          type={InputEnum.TEXT}
          defaultValue={text}
          onBlur={handleBlurUpdate<MaterialUpdateREQ>(record.id, 'name', text, onUpdateMaterial)}
        />
      ),
    },
    {
      title: tExpendablesSupplies('table.current_situation'),
      dataIndex: 'stockQuantity',
      sorter: true,
      width: 100,
    },
    {
      title: <EditableHeader title={tExpendablesSupplies('table.active')} />,
      dataIndex: 'status',
      width: 150,
      filters: helperFilter('active_status', ACTIVE_STATE, tEnum),
      render: (value: ACTIVE_STATE, record: any) => {
        return (
          <CustomSelect
            disabled={!canEdit}
            variant='borderless'
            classNameSelect={clsx(`${value ? '' : 'disabled-text'} `)}
            options={STATE_FILTER}
            defaultValue={value}
            onChange={handleChangeUpdate<MaterialUpdateStatusREQ>(record.id, 'status', value, onUpdateMaterialStatus)}
          />
        );
      },
    },
    {
      title: tCommon('table.view'),
      className: 'ant-table-view-column',
      key: 'look',
      width: 50,
      align: 'center',
      render: (_: any, record: any) => (
        <CustomIconButton
          onClick={() => {
            onDetail(record.id);
          }}
          icon={<Image src={ICONS.LOOK} alt='Drag' width={24} height={24} />}
        />
      ),
    },
    canEdit &&
      ({
        title: tCommon('table.sort'),
        className: 'ant-table-sort-column',
        key: 'array',
        fixed: 'right',
        width: 50,
        align: 'center',
        render: () => <DragHandle />,
      } as any),
  ].filter(Boolean);
};
