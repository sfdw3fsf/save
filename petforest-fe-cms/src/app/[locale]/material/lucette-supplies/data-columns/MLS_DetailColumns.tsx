import CustomDatePicker from '@/components/common/CustomDatePicker';
import CustomNumericFormat from '@/components/common/CustomNumericFormat';
import { CustomTextInput } from '@/components/common/CustomTextInput';
import EditableHeader from '@/components/common/table/EditableHeader';
import ReservationCode from '@/components/table/ReservationCode';
import { useMenuPermission } from '@/hook/auth/useMenuPermission';
import { MaterialInventoryDTO } from '@/store/material/dto/material.dto';
import { MaterialInventoryUpdateREQ } from '@/store/material/request/material.request';
import { InputEnum } from '@/utils/enums/input.enum';
import { MenuKeys } from '@/utils/enums/side-menu-type';
import { handleBlurUpdate, handleChangeDateUpdate, handleChangeUpdate } from '@/utils/utils/inline-update.util';
import { TableColumnsType } from 'antd';
import { Dayjs } from 'dayjs';
import { useTranslation } from 'react-i18next';

type MLS_DetailColumnsProps = {
  onUpdateMaterialInventory: (id: number, values: MaterialInventoryUpdateREQ) => void;
};

export const MLS_DetailColumns = ({
  onUpdateMaterialInventory,
}: MLS_DetailColumnsProps): TableColumnsType<MaterialInventoryDTO> => {
  const { t: tMaterial } = useTranslation('material');
  const { t: tCommon } = useTranslation('common');
  const { canEdit } = useMenuPermission(MenuKeys.MATERIAL_LUCETTE_SUPPLIES);

  return [
    {
      title: <EditableHeader title={tMaterial('detail.table.date')} />,
      dataIndex: 'registerDate',
      sorter: true,
      width: 120,
      onCell: (record: MaterialInventoryDTO) => ({
        className: !!record.reservation ? 'disabled-column' : '',
      }),
      render: (text: Dayjs, record) => (
        <CustomDatePicker
          disabled={!!record.reservation || !canEdit}
          value={text}
          onChange={handleChangeDateUpdate<MaterialInventoryUpdateREQ>(record.id, 'registerDate', onUpdateMaterialInventory)}
        />
      ),
    },
    {
      title: tMaterial('detail.table.funeral_number'),
      dataIndex: 'reservation',
      width: 120,
      sorter: true,
      render: (value: string, record: MaterialInventoryDTO) => <ReservationCode code={value} id={record.reservationId} />,
    },
    {
      title: <EditableHeader title={tMaterial('detail.table.quantity')} />,
      dataIndex: 'quantity',
      width: 120,
      onCell: (record: MaterialInventoryDTO) => ({
        className: !!record.reservation ? 'disabled-column' : '',
      }),
      render: (value: number, record) => (
        <CustomNumericFormat
          disabled={!!record.reservation || !canEdit}
          value={value}
          onBlur={handleChangeUpdate<MaterialInventoryUpdateREQ>(record.id, 'quantity', value, onUpdateMaterialInventory)}
        />
      ),
    },
    {
      title: <EditableHeader title={tCommon('table.memo')} />,
      dataIndex: 'note',
      render: (text: string, record) => (
        <CustomTextInput
          readOnly={!canEdit}
          type={InputEnum.TEXT}
          defaultValue={text}
          onBlur={handleBlurUpdate<MaterialInventoryUpdateREQ>(record.id, 'note', text, onUpdateMaterialInventory)}
          className='min-w-[100px]'
        />
      ),
    },
  ];
};
