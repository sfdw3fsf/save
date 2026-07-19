import CustomIconButton from '@/components/common/CustomIconButton';
import { CustomTextInput } from '@/components/common/CustomTextInput';
import EditableHeader from '@/components/common/table/EditableHeader';
import { FacilityListDTO } from '@/store/facility/dto/facility.dto';
import { FacilityListUpdateREQ } from '@/store/facility/request/facility.request';
import { ICONS } from '@/theme';
import { InputEnum } from '@/utils/enums/input.enum';
import { FilterOptions } from '@/utils/types/option.type';
import { handleBlurUpdate } from '@/utils/utils/inline-update.util';
import { TableColumnsType } from 'antd';
import Image from 'next/image';
import { useTranslation } from 'react-i18next';

type FacilityListColumnsProps = {
  onDetail: (id: number) => void;
  companyOptions: FilterOptions<string>;
  onUpdateFacilityList: (id: number, body: FacilityListUpdateREQ) => void;
};

export const FacilityListColumns = ({
  onDetail,
  companyOptions,
  onUpdateFacilityList,
}: FacilityListColumnsProps): TableColumnsType<FacilityListDTO> => {
  const { t: tFacility } = useTranslation('facility');
  const { t: tCommon } = useTranslation('common');

  return [
    {
      title: <EditableHeader title={tCommon('table.facility.company')} />,
      dataIndex: 'companyName',
      width: 100,
      filters: companyOptions,
      render: (text: string, record: FacilityListDTO) => (
        <CustomTextInput
          onBlur={handleBlurUpdate<FacilityListUpdateREQ>(record.id, 'companyName', text, onUpdateFacilityList)}
          type={InputEnum.TEXT}
          defaultValue={text}
        />
      ),
    },
    {
      title: <EditableHeader title={tCommon('table.facility.facility_type')} />,
      dataIndex: 'type',
      sorter: true,
      width: 100,
      render: (text: string, record: FacilityListDTO) => (
        <CustomTextInput
          onBlur={handleBlurUpdate<FacilityListUpdateREQ>(record.id, 'type', text, onUpdateFacilityList)}
          type={InputEnum.TEXT}
          defaultValue={text}
        />
      ),
    },
    {
      title: tFacility('table.repair_count'),
      dataIndex: 'facilityMaintenances',
      sorter: true,
      width: 100,
    },
    {
      title: <EditableHeader title={tFacility('table.company_manager')} />,
      dataIndex: 'managerName',
      sorter: true,
      width: 100,
      render: (text: string, record: FacilityListDTO) => (
        <CustomTextInput
          onBlur={handleBlurUpdate<FacilityListUpdateREQ>(record.id, 'managerName', text, onUpdateFacilityList)}
          type={InputEnum.TEXT}
          defaultValue={text}
        />
      ),
    },
    {
      title: <EditableHeader title={tFacility('table.contact')} />,
      dataIndex: 'contact',
      sorter: true,
      width: 100,
      render: (text: string, record: FacilityListDTO) => (
        <CustomTextInput
          onBlur={handleBlurUpdate<FacilityListUpdateREQ>(record.id, 'contact', text, onUpdateFacilityList)}
          type={InputEnum.TEXT}
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
