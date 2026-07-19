import { COLLABORATING_PARTNER_HOSPITAL_DETAIL_TABS } from '@/utils/enums/collaborating/partner-hospital.enum';
import { Tabs } from 'antd';
import { TabsProps } from 'antd/lib';
import { Dispatch, SetStateAction, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { CPHD_HospitalInfo } from '../Tabs/CPHD_HospitalInfo';
import { CPHD_PromotionalMaterialDelivery } from '../Tabs/CPHD_PromotionalMaterialDelivery ';
import { CPHD_SaleActivities } from '../Tabs/CPHD_SaleActivities';
import { CPHD_SettlementDetail } from '../Tabs/CPHD_SettlementDetail';

type CPH_DetailModalProps = {
  setIsHiddingFooter: Dispatch<SetStateAction<boolean>>;
  id: number;
};
export const CPH_DetailModal = ({ id, setIsHiddingFooter }: CPH_DetailModalProps) => {
  const { t: tPartnerHospital } = useTranslation('collaborating_partner_hospital');
  const [tab, setTab] = useState<COLLABORATING_PARTNER_HOSPITAL_DETAIL_TABS>(
    COLLABORATING_PARTNER_HOSPITAL_DETAIL_TABS.HOSPITAL_INFO,
  );

  const partnerHospitalDetailTabs: TabsProps['items'] = [
    {
      key: COLLABORATING_PARTNER_HOSPITAL_DETAIL_TABS.HOSPITAL_INFO,
      label: tPartnerHospital('detail.tabs.hospital_information'),
      children: <CPHD_HospitalInfo />,
    },
    {
      key: COLLABORATING_PARTNER_HOSPITAL_DETAIL_TABS.SETTLEMENT_DETAIL,
      label: tPartnerHospital('detail.tabs.settlement_details'),
      children: <CPHD_SettlementDetail id={id} />,
    },
    {
      key: COLLABORATING_PARTNER_HOSPITAL_DETAIL_TABS.SALE_ACTIVITY,
      label: tPartnerHospital('detail.tabs.sales_activities'),
      children: <CPHD_SaleActivities id={id} />,
    },
    {
      key: COLLABORATING_PARTNER_HOSPITAL_DETAIL_TABS.PROMOTE_MATERIAL_DELIVERY,
      label: tPartnerHospital('detail.tabs.promotional_material_delivery'),
      children: <CPHD_PromotionalMaterialDelivery id={id} />,
    },
  ];

  return (
    <Tabs
      items={partnerHospitalDetailTabs}
      defaultActiveKey={COLLABORATING_PARTNER_HOSPITAL_DETAIL_TABS.HOSPITAL_INFO}
      onChange={(value) => {
        if (value === COLLABORATING_PARTNER_HOSPITAL_DETAIL_TABS.HOSPITAL_INFO) {
          setIsHiddingFooter(false);
        } else {
          setIsHiddingFooter(true);
        }
        setTab(value as COLLABORATING_PARTNER_HOSPITAL_DETAIL_TABS);
      }}
    />
  );
};
