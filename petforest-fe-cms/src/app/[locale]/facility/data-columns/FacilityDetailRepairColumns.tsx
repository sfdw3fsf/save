import CustomDatePicker from '@/components/common/CustomDatePicker';
import CustomIconButton from '@/components/common/CustomIconButton';
import CustomSelect from '@/components/common/CustomSelect';
import { CustomTextInput } from '@/components/common/CustomTextInput';
import EditableHeader from '@/components/common/table/EditableHeader';
import { FacilityDetailListDTO } from '@/store/facility/dto/facility.dto';
import { FacilityHistoryCreateUpdateREQ } from '@/store/facility/request/facility.request';
import { ICONS } from '@/theme';
import { InputEnum } from '@/utils/enums/input.enum';
import { FilterOptions } from '@/utils/types/option.type';
import { handleBlurUpdate, handleChangeDateUpdate, handleChangeUpdate } from '@/utils/utils/inline-update.util';
import { TableColumnsType } from 'antd';
import { Dayjs } from 'dayjs';
import Image from 'next/image';
import { useTranslation } from 'react-i18next';

type FacilityDetailRepairColumnsProps = {
  onDetail: (id: number) => void;
  onUpdateFacilityMaintenances: (id: number, values: FacilityHistoryCreateUpdateREQ) => void;
  managerOptions: FilterOptions;
};
export const FacilityDetailRepairColumns = ({
  onDetail,
  onUpdateFacilityMaintenances,
  managerOptions,
}: FacilityDetailRepairColumnsProps): TableColumnsType<FacilityDetailListDTO> => {
  const { t: tCommon } = useTranslation('common');
  const { t: tFacility } = useTranslation('facility');

  return [
    {
      title: <EditableHeader title={tCommon('table.facility.date')} />,
      dataIndex: 'maintenanceDate',
      sorter: true,
      width: 100,
      render: (date: Dayjs, record: FacilityDetailListDTO) => (
        <CustomDatePicker
          value={date}
          onChange={handleChangeDateUpdate<FacilityHistoryCreateUpdateREQ>(
            record.id,
            'maintenanceDate',
            onUpdateFacilityMaintenances,
          )}
        />
      ),
    },
    {
      title: <EditableHeader title={tFacility('history.title')} />,
      dataIndex: 'title',
      width: 100,
      sorter: true,
      render: (text: string, record: FacilityDetailListDTO) => (
        <CustomTextInput
          type={InputEnum.TEXT}
          defaultValue={text}
          onBlur={handleBlurUpdate<FacilityHistoryCreateUpdateREQ>(record.id, 'title', text, onUpdateFacilityMaintenances)}
        />
      ),
    },
    {
      title: <EditableHeader title={tCommon('table.facility.poc')} />,
      dataIndex: 'managerId',
      width: 100,
      sorter: true,
      render: (text: string, record: FacilityDetailListDTO) => (
        <CustomSelect
          onChange={handleChangeUpdate<FacilityHistoryCreateUpdateREQ>(
            record.id,
            'managerId',
            record.managerId,
            onUpdateFacilityMaintenances,
          )}
          variant='borderless'
          options={managerOptions}
          defaultValue={text}
        />
      ),
    },
    {
      title: tCommon('table.view'),
      className: 'ant-table-view-column',
      key: 'look',
      width: 50,
      align: 'center',
      fixed: 'right',
      render: (_, record, index) => (
        <CustomIconButton
          onClick={() => {
            onDetail(record.id);
          }}
          icon={<Image src={ICONS.LOOK} alt='Drag' width={24} height={24} />}
        />
      ),
    },
  ];
};
