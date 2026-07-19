import CustomDatePicker from '@/components/common/CustomDatePicker';
import CustomIconButton from '@/components/common/CustomIconButton';
import CustomSelect from '@/components/common/CustomSelect';
import { CustomTextInput } from '@/components/common/CustomTextInput';
import EditableHeader from '@/components/common/table/EditableHeader';
import { useUser } from '@/hook/user/useUser';
import { FacilityHistoryListDTO } from '@/store/facility/dto/facility.dto';
import { FacilityHistoryCreateUpdateREQ } from '@/store/facility/request/facility.request';
import { ICONS } from '@/theme';
import { InputEnum } from '@/utils/enums/input.enum';
import { FilterOptions } from '@/utils/types/option.type';
import { handleBlurUpdate, handleChangeDateUpdate, handleChangeUpdate } from '@/utils/utils/inline-update.util';
import { TableColumnsType } from 'antd';
import useBreakpoint from 'antd/lib/grid/hooks/useBreakpoint';
import { Dayjs } from 'dayjs';
import Image from 'next/image';
import { useTranslation } from 'react-i18next';

type FacilityHistoryColumnsProps = {
  onDetail: (id: number) => void;
  companyOptions: FilterOptions<string>;
  managerOptions: FilterOptions;
  onUpdateFacilityHistory: (id: number, values: FacilityHistoryCreateUpdateREQ) => void;
};
export const FacilityHistoryColumns = ({
  onDetail,
  companyOptions,
  managerOptions,
  onUpdateFacilityHistory,
}: FacilityHistoryColumnsProps): TableColumnsType<FacilityHistoryListDTO> => {
  const { t: tCommon } = useTranslation('common');
  const { managerFullnameOptions } = useUser();
  const screens = useBreakpoint();
  return [
    {
      title: <EditableHeader title={tCommon('table.facility.request_date')} />,
      dataIndex: 'maintenanceDate',
      sorter: true,
      width: 110,
      render: (date: Dayjs, record: FacilityHistoryListDTO) => (
        <CustomDatePicker
          defaultValue={date}
          onChange={handleChangeDateUpdate<FacilityHistoryCreateUpdateREQ>(
            record.id,
            'maintenanceDate',
            onUpdateFacilityHistory,
            {
              facilityId: record.facilityId,
            },
          )}
        />
      ),
    },
    {
      title: <EditableHeader title={tCommon('table.facility.completion_date')} />,
      dataIndex: 'maintenanceScheduleDate',
      sorter: true,
      width: 110,
      render: (date: Dayjs, record: FacilityHistoryListDTO) => (
        <CustomDatePicker
          defaultValue={date}
          onChange={handleChangeDateUpdate<FacilityHistoryCreateUpdateREQ>(
            record.id,
            'maintenanceScheduleDate',
            onUpdateFacilityHistory,
            {
              facilityId: record.facilityId,
            },
          )}
        />
      ),
    },
    {
      title: <EditableHeader title={tCommon('table.facility.company')} />,
      dataIndex: 'facility.companyName',
      width: 150,
      filters: companyOptions,
      render: (text: string, record: FacilityHistoryListDTO) => (
        <CustomSelect
          variant='borderless'
          options={companyOptions}
          defaultValue={text}
          onChange={handleChangeUpdate<FacilityHistoryCreateUpdateREQ>(
            record.id,
            'facilityId',
            record.facilityId,
            onUpdateFacilityHistory,
            {
              facilityId: record.facilityId,
            },
          )}
        />
      ),
    },
    {
      title: tCommon('table.facility.facility_type'),
      dataIndex: 'facilityType',
      className: 'whitespace-nowrap',
      sorter: true,
      width: 120,
    },
    {
      title: <EditableHeader title={tCommon('table.facility.title')} />,
      dataIndex: 'title',
      width: screens.md ? undefined : 190,
      render: (text: string, record: FacilityHistoryListDTO) => (
        <CustomTextInput
          onBlur={handleBlurUpdate<FacilityHistoryCreateUpdateREQ>(record.id, 'title', text, onUpdateFacilityHistory, {
            facilityId: record.facilityId,
          })}
          type={InputEnum.TEXT}
          defaultValue={text}
        />
      ),
    },

    {
      title: <EditableHeader title={tCommon('table.facility.poc')} />,
      dataIndex: 'manager.fullName',
      sorter: true,
      width: 130,
      filters: managerFullnameOptions,
      render: (text: string, record: FacilityHistoryListDTO) => (
        <CustomSelect
          variant='borderless'
          options={managerOptions}
          defaultValue={text}
          onChange={handleChangeUpdate<FacilityHistoryCreateUpdateREQ>(
            record.id,
            'managerId',
            record.managerId,
            onUpdateFacilityHistory,
            {
              facilityId: record.facilityId,
            },
          )}
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
