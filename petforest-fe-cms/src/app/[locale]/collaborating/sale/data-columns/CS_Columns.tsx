import CustomDatePicker from '@/components/common/CustomDatePicker';
import CustomSelect from '@/components/common/CustomSelect';
import { CustomTextInput } from '@/components/common/CustomTextInput';
import Status from '@/components/common/Status';
import EditableHeader from '@/components/common/table/EditableHeader';
import { HospitalSaleListDTO } from '@/store/collaborating-hospital/dto/collaborating-hospital.dto';
import { HospitalSaleListUpdateREQ } from '@/store/collaborating-hospital/request/collaborating-hospital.request';
import { InputEnum } from '@/utils/enums/input.enum';
import { ENUM_STATUS } from '@/utils/enums/status.enum';
import { helperFilter } from '@/utils/helpers/common.helper';
import { FilterOptionItem } from '@/utils/types/option.type';
import { handleBlurUpdate, handleChangeDateUpdate, handleChangeUpdate } from '@/utils/utils/inline-update.util';
import { TableColumnsType } from 'antd';
import dayjs from 'dayjs';
import { useTranslation } from 'react-i18next';

type CS_ColumnsProps = {
  onListUpdate: (id: number, body: HospitalSaleListUpdateREQ) => void;
  hospitalOptions: FilterOptionItem[];
  managerOptions: FilterOptionItem[];
};

export const CS_Columns = ({
  onListUpdate,
  hospitalOptions,
  managerOptions,
}: CS_ColumnsProps): TableColumnsType<HospitalSaleListDTO> => {
  const { t: tSales } = useTranslation('collaborating_sales');
  const { t: tCommon } = useTranslation('common');
  const { t: tEnum } = useTranslation('enum');

  const statusFilter = helperFilter('status', ENUM_STATUS, tEnum);

  return [
    {
      title: <EditableHeader title={tSales('table.hospital_name')} />,
      dataIndex: 'hospitalName',
      key: 'hospitalName',
      sorter: true,
      width: 150,
      render: (value: string, record: HospitalSaleListDTO) => (
        <CustomSelect
          onChange={handleChangeUpdate<HospitalSaleListUpdateREQ>(record.id, 'hospitalId', record.hospitalId, onListUpdate)}
          variant='borderless'
          options={hospitalOptions}
          defaultValue={value}
          showSearch
          filterOption={(input, option) => ((option?.label as string) ?? '').toLowerCase().includes(input.toLowerCase())}
        />
      ),
    },
    {
      title: tSales('table.region'),
      dataIndex: 'region',
      key: 'region',
      sorter: true,
      width: 150,
      render: (_, record: HospitalSaleListDTO) => [record.provinceName, record.districtName].filter(Boolean).join(', '),
    },
    {
      title: <EditableHeader title={tSales('table.visit_status')} />,
      dataIndex: 'visitStatus',
      key: 'visitStatus',
      filters: statusFilter,
      width: 120,
      sorter: true,
      render: (value: ENUM_STATUS, record: HospitalSaleListDTO) => (
        <CustomSelect
          onChange={handleChangeUpdate<HospitalSaleListUpdateREQ>(record.id, 'visitStatus', record.visitStatus, onListUpdate)}
          variant='borderless'
          optionRender={(option) => option.data.text}
          options={statusFilter.map((option) => ({
            ...option,
            label: <Status isSuccess={option.value === ENUM_STATUS.COMPLETE} status={option.text} />,
          }))}
          defaultValue={value}
        />
      ),
    },
    {
      title: <EditableHeader title={tSales('table.visit_date')} />,
      dataIndex: 'visitedAt',
      key: 'visitedAt',
      width: 120,
      sorter: true,
      render: (value: dayjs.Dayjs, record: HospitalSaleListDTO) => (
        <CustomDatePicker
          defaultValue={value}
          onChange={handleChangeDateUpdate<HospitalSaleListUpdateREQ>(record.id, 'visitedAt', onListUpdate)}
        />
      ),
    },
    {
      title: <EditableHeader title={tSales('table.person_in_charge')} />,
      dataIndex: 'manager',
      key: 'manager',
      sorter: true,
      width: 110,
      render: (value: number, record: HospitalSaleListDTO) => (
        <CustomSelect
          variant='borderless'
          options={managerOptions}
          defaultValue={value}
          onChange={handleChangeUpdate<HospitalSaleListUpdateREQ>(record.id, 'managerId', value, onListUpdate)}
        />
      ),
    },

    {
      title: <EditableHeader title={tCommon('table.memo')} />,
      dataIndex: 'note',
      key: 'note',
      width: 300,
      render: (value: string, record: HospitalSaleListDTO) => (
        <CustomTextInput
          onBlur={handleBlurUpdate<HospitalSaleListUpdateREQ>(record.id, 'note', value, onListUpdate)}
          type={InputEnum.TEXT_AREA}
          defaultValue={value}
        />
      ),
    },
  ];
};
