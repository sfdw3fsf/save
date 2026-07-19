import Status from '@/components/common/Status';
import { HospitalDetailSalesDTO } from '@/store/collaborating-hospital/dto/collaborating-hospital.dto';
import { ENUM_STATUS } from '@/utils/enums/status.enum';
import { helperFilter } from '@/utils/helpers/common.helper';
import { TableColumnsType } from 'antd';
import { useTranslation } from 'react-i18next';

export const CPH_SaleActivityColumns = (): TableColumnsType<HospitalDetailSalesDTO> => {
  const { t: tPartnerHospital } = useTranslation('collaborating_partner_hospital');
  const { t: tEnum } = useTranslation('enum');
  const { t: tCommon } = useTranslation('common');

  const STATUS_FILTER = helperFilter('status', ENUM_STATUS, tEnum);

  return [
    {
      title: tPartnerHospital('detail.sales_activities.table.visit_status'),
      dataIndex: 'visitStatus',
      width: 150,
      sorter: true,
      filters: STATUS_FILTER,
      render: (status) => <Status isSuccess={status === ENUM_STATUS.COMPLETE} status={tEnum(`status.${status}` as any)} />,
    },
    {
      title: tPartnerHospital('detail.sales_activities.table.visit_date'),
      dataIndex: 'visitedAt',
      sorter: true,
      width: 150,
    },
    {
      title: tPartnerHospital('detail.sales_activities.table.person_in_charge'),
      dataIndex: 'picName',
      width: 100,
      sorter: true,
    },
    {
      title: tPartnerHospital('detail.sales_activities.table.hospital_person_in_charge'),
      dataIndex: 'hospitalPicName',
      width: 100,
      sorter: true,
    },
    {
      title: tPartnerHospital('detail.sales_activities.table.contact_person'),
      dataIndex: 'hospitalManagerName',
      width: 150,
      sorter: true,
    },
    {
      title: tCommon('table.memo'),
      dataIndex: 'note',
      width: 150,
    },
  ];
};
