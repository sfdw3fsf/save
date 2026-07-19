import AuthNamespace from '../../public/locales/en/auth.json';
import CollaboratingDeliveryPromotionalMaterialNameSpace from '../../public/locales/en/collaborating_delivery_promotional_material.json';
import CollaboratingPartnerHospitalNameSpace from '../../public/locales/en/collaborating_partner_hospital.json';
import CollaboratingSalesNameSpace from '../../public/locales/en/collaborating_sales.json';
import CollaboratingSettlementNameSpace from '../../public/locales/en/collaborating_settlement.json';
import CommonNameSpace from '../../public/locales/en/common.json';
import DashboardNameSpace from '../../public/locales/en/dashboard.json';
import EnumNameSpace from '../../public/locales/en/enum.json';
import FacilityNameSpace from '../../public/locales/en/facility.json';
import FormNameSpace from '../../public/locales/en/form.json';
import LucetteNameSpace from '../../public/locales/en/funeral_lucette.json';
import NotiHistoryNameSpace from '../../public/locales/en/funeral_noti_history.json';
import PNTSettingNameSpace from '../../public/locales/en/funeral_pnt_setting.json';
import ReservationNameSpace from '../../public/locales/en/funeral_reservation.json';
import StockNameSpace from '../../public/locales/en/funeral_stock.json';
import HomeNameSpace from '../../public/locales/en/home.json';
import ManagerNameSpace from '../../public/locales/en/manager.json';
import ManagerBranchNameSpace from '../../public/locales/en/manager_branch.json';
import ManagerUserNameSpace from '../../public/locales/en/manager_user.json';
import MaterialNameSpace from '../../public/locales/en/material.json';
import EducationCenterSuppliesNameSpace from '../../public/locales/en/material_education_center_supplies.json';
import ExpendablesSuppliesNameSpace from '../../public/locales/en/material_expendables_supplies.json';
import FuneralSuppliesNameSpace from '../../public/locales/en/material_funeral_supplies.json';
import LucetteSuppliesNameSpace from '../../public/locales/en/material_lucette_supplies.json';
import OtherFuneralSuppliesNameSpace from '../../public/locales/en/material_other_funeral_supplies.json';
import MenuNameSpace from '../../public/locales/en/menu.json';
import MessageNameSpace from '../../public/locales/en/message.json';
import OssuaryNameSpace from '../../public/locales/en/ossuary.json';
import PostNameSpace from '../../public/locales/en/post.json';
import SettingNameSpace from '../../public/locales/en/setting.json';
import SettlementNameSpace from '../../public/locales/en/settlement.json';
import StatisticNameSpace from '../../public/locales/en/statistic.json';

export const i18nResources = {
  common: CommonNameSpace,
  form: FormNameSpace,
  funeral_reservation: ReservationNameSpace,
  funeral_pnt_setting: PNTSettingNameSpace,
  funeral_lucette: LucetteNameSpace,
  funeral_stock: StockNameSpace,
  funeral_noti_history: NotiHistoryNameSpace,
  menu: MenuNameSpace,
  material: MaterialNameSpace,
  material_funeral_supplies: FuneralSuppliesNameSpace,
  material_lucette_supplies: LucetteSuppliesNameSpace,
  material_other_funeral_supplies: OtherFuneralSuppliesNameSpace,
  material_education_center_supplies: EducationCenterSuppliesNameSpace,
  material_expendables_supplies: ExpendablesSuppliesNameSpace,
  facility: FacilityNameSpace,
  post: PostNameSpace,
  setting: SettingNameSpace,
  settlement: SettlementNameSpace,
  enum: EnumNameSpace,
  ossuary: OssuaryNameSpace,
  auth: AuthNamespace,
  manager: ManagerNameSpace,
  manager_user: ManagerUserNameSpace,
  manager_branch: ManagerBranchNameSpace,
  home: HomeNameSpace,
  collaborating_sales: CollaboratingSalesNameSpace,
  collaborating_delivery_promotional_material: CollaboratingDeliveryPromotionalMaterialNameSpace,
  collaborating_partner_hospital: CollaboratingPartnerHospitalNameSpace,
  collaborating_settlement: CollaboratingSettlementNameSpace,
  statistic: StatisticNameSpace,
  dashboard: DashboardNameSpace,
  message: MessageNameSpace,
};

export type I18NResource = typeof i18nResources;
