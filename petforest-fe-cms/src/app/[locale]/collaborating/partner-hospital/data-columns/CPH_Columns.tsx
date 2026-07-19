import CustomIconButton from '@/components/common/CustomIconButton';
import CustomSelect from '@/components/common/CustomSelect';
import { CustomTextInput } from '@/components/common/CustomTextInput';
import EditableHeader from '@/components/common/table/EditableHeader';
import { HospitalDTO } from '@/store/collaborating-hospital/dto/collaborating-hospital.dto';
import { HospitalUpdateREQ } from '@/store/collaborating-hospital/request/collaborating-hospital.request';
import { COLORS, ICONS } from '@/theme';
import { InputEnum } from '@/utils/enums/input.enum';
import { FilterOptions } from '@/utils/types/option.type';
import { handleBlurUpdate, handleChangeUpdate } from '@/utils/utils/inline-update.util';
import { StarFilled } from '@ant-design/icons';
import { TableColumnsType } from 'antd';
import Image from 'next/image';
import { useTranslation } from 'react-i18next';

type PartnerHospitalColumnsProps = {
  onDetail: (id: number) => void;
  branchOptions: FilterOptions;
  settlementMethodOptions: FilterOptions;
  onUpdateHospital: (id: number, values: HospitalUpdateREQ) => void;
};

export const CPH_Columns = ({
  onDetail,
  branchOptions,
  settlementMethodOptions,
  onUpdateHospital,
}: PartnerHospitalColumnsProps): TableColumnsType<HospitalDTO> => {
  const { t: tPartnerHospital } = useTranslation('collaborating_partner_hospital');
  const { t: tCommon } = useTranslation('common');

  return [
    {
      title: <EditableHeader title={tPartnerHospital('table.hospital_name')} />,
      dataIndex: 'name',
      width: 150,
      sorter: true,
      render: (text: string, record) => (
        <div className='flex items-center'>
          {record.isMarked && <StarFilled style={{ color: COLORS.primary[800] }} />}
          <CustomTextInput
            key={text}
            type={InputEnum.TEXT}
            defaultValue={text}
            classNameInput={`${record.isNewHospital && '!text-primary-1000'} !pl-[6px]`}
            onBlur={handleBlurUpdate<HospitalUpdateREQ>(record.id, 'hospitalName', text, onUpdateHospital)}
          />
        </div>
      ),
    },
    {
      title: tPartnerHospital('table.region'),
      dataIndex: 'region',
      sorter: true,
      className: 'whitespace-nowrap',
      width: 70,
    },
    {
      title: <EditableHeader title={tPartnerHospital('table.payment_method')} />,
      dataIndex: 'entitlementMethod',
      width: 80,
      sorter: true,
      render: (value, record) => (
        <CustomSelect
          key={value}
          options={settlementMethodOptions}
          variant='borderless'
          defaultValue={value}
          onChange={handleChangeUpdate<HospitalUpdateREQ>(record.id, 'settlementMethodId', value, onUpdateHospital)}
        />
      ),
    },
    {
      title: <EditableHeader title={tPartnerHospital('table.branch')} />,
      dataIndex: 'hospitalBranches',
      width: 100,
      sorter: true,
      render: (value, record) => (
        <CustomSelect
          key={value}
          classNameSelect='table-select-tag !text-primary-1000 max-w-[350px]'
          height={'auto'}
          options={branchOptions}
          variant='borderless'
          mode='tags'
          defaultValue={value}
          onChange={handleChangeUpdate<HospitalUpdateREQ>(record.id, 'branchIds', value, onUpdateHospital)}
        />
      ),
    },
    {
      title: tPartnerHospital('table.number_of_funeral_connections'),
      dataIndex: 'totalFuneral',
      className: 'ant-column-center',
      sorter: true,
      width: 80,
      render: (value) => (Number(value) === 0 ? '' : value),
    },
    {
      title: tPartnerHospital('table.number_of_sales_visits'),
      dataIndex: 'totalSales',
      className: 'ant-column-center ',
      width: 80,
      sorter: true,
      render: (value) => (Number(value) === 0 ? '' : value),
    },
    {
      title: tPartnerHospital('table.registration_date'),
      dataIndex: 'createdAt',
      className: 'whitespace-nowrap',
      width: 80,
      sorter: true,
    },
    {
      title: tCommon('table.view'),
      render: (_, record, index) => (
        <CustomIconButton
          onClick={() => {
            onDetail(record.id);
          }}
          icon={<Image src={ICONS.LOOK} alt='Drag' width={24} height={24} />}
          className='min-w-[50px]'
        />
      ),
    },
  ];
};
