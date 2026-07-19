import { MenuPermissionDTO } from '@/store/auth/dto/auth-menu-permission.dto';
import { ICONS } from '@/theme';
import { MY_ROUTE } from '@/utils/constants/route.constant';
import { USER_PERMISSION } from '@/utils/enums/manager/manager.enum';
import { MenuKeys } from '@/utils/enums/side-menu-type';
import type { MenuProps } from 'antd';
import { TFunction } from 'i18next';
import { AppRouterInstance } from 'next/dist/shared/lib/app-router-context.shared-runtime';
import Image from 'next/image';

export type CustomMenuItem = {
  router: AppRouterInstance;
  tEnum: TFunction<'enum', undefined>;
  menuPermissionData?: MenuPermissionDTO;
  selectedKey?: MenuKeys;
};

export type CustomMenuItemProps = Required<MenuProps>['items'][number] & {
  path?: string;
  key: string;
  children?: CustomMenuItemProps[];
  icon?: React.ReactNode;
  label: React.ReactNode;
};

const checkedView = (permissions: MenuPermissionDTO | undefined, name: MenuKeys) => {
  if (!permissions) return false;
  if (permissions[name] !== USER_PERMISSION.NOT_ALLOW) return true;
  return false;
};

const checkSelectedKey = (key: MenuKeys, selectedKey?: MenuKeys) => {
  return !!selectedKey?.startsWith(key);
};

export const MenuList = ({ router, tEnum, menuPermissionData, selectedKey }: CustomMenuItem): CustomMenuItemProps[] => [
  checkedView(menuPermissionData, MenuKeys.HOME) &&
    ({
      key: MenuKeys.HOME,
      label: tEnum('menu.HOME'),
      icon: checkSelectedKey(MenuKeys.HOME, selectedKey) ? (
        <Image src={ICONS.HOME_GREEN} alt='Home' />
      ) : (
        <Image src={ICONS.HOME} alt='Home' />
      ),
      onClick: () => router.push('/'),
      path: '/',
    } as any),
  checkedView(menuPermissionData, MenuKeys.DASHBOARD) &&
    ({
      key: MenuKeys.DASHBOARD,
      label: tEnum('menu.DASHBOARD'),
      icon: checkSelectedKey(MenuKeys.DASHBOARD, selectedKey) ? (
        <Image src={ICONS.DASHBOARD_GREEN} alt='Dashboard' />
      ) : (
        <Image src={ICONS.DASHBOARD} alt='Dashboard' />
      ),
      onClick: () => router.push(MY_ROUTE.DASHBOARD),
      path: MY_ROUTE.DASHBOARD,
    } as any),
  checkedView(menuPermissionData, MenuKeys.FUNERAL) &&
    ({
      key: MenuKeys.FUNERAL,
      label: tEnum('menu.FUNERAL'),
      icon: (
        <Image src={ICONS.FUNERAL} alt='Home' className={checkSelectedKey(MenuKeys.FUNERAL, selectedKey) ? 'icon-active' : ''} />
      ),
      children: [
        checkedView(menuPermissionData, MenuKeys.FUNERAL_RESERVATION) && {
          key: MenuKeys.FUNERAL_RESERVATION,
          label: tEnum('menu.FUNERAL_RESERVATION'),
          path: MY_ROUTE.FUNERAL.FUNERAL_RESERVATION,
          onClick: () => router.push(MY_ROUTE.FUNERAL.FUNERAL_RESERVATION),
        },
        checkedView(menuPermissionData, MenuKeys.FUNERAL_PNT_SETTING) && {
          key: MenuKeys.FUNERAL_PNT_SETTING,
          label: tEnum('menu.FUNERAL_PNT_SETTING'),
          path: MY_ROUTE.FUNERAL.FUNERAL_PNT_SETTING,
          onClick: () => router.push(MY_ROUTE.FUNERAL.FUNERAL_PNT_SETTING),
        },
        checkedView(menuPermissionData, MenuKeys.FUNERAL_LUCETTE) && {
          key: MenuKeys.FUNERAL_LUCETTE,
          label: tEnum('menu.FUNERAL_LUCETTE'),
          path: MY_ROUTE.FUNERAL.FUNERAL_LUCETTE,
          onClick: () => router.push(MY_ROUTE.FUNERAL.FUNERAL_LUCETTE),
        },
        checkedView(menuPermissionData, MenuKeys.FUNERAL_STOCK) && {
          key: MenuKeys.FUNERAL_STOCK,
          label: tEnum('menu.FUNERAL_STOCK'),
          path: MY_ROUTE.FUNERAL.FUNERAL_STOCK,
          onClick: () => router.push(MY_ROUTE.FUNERAL.FUNERAL_STOCK),
        },
        checkedView(menuPermissionData, MenuKeys.FUNERAL_NOTI_HISTORY) && {
          key: MenuKeys.FUNERAL_NOTI_HISTORY,
          label: tEnum('menu.FUNERAL_NOTI_HISTORY'),
          path: MY_ROUTE.FUNERAL.FUNERAL_NOTI_HISTORY,
          onClick: () => router.push(MY_ROUTE.FUNERAL.FUNERAL_NOTI_HISTORY),
        },
      ],
    } as any),
  checkedView(menuPermissionData, MenuKeys.SETTLEMENT) &&
    ({
      key: MenuKeys.SETTLEMENT,
      label: tEnum('menu.SETTLEMENT'),
      icon: checkSelectedKey(MenuKeys.SETTLEMENT, selectedKey) ? (
        <Image src={ICONS.SETTLEMENT_GREEN} alt='Settle' />
      ) : (
        <Image src={ICONS.SETTLEMENT} alt='Settle' />
      ),
      children: [
        checkedView(menuPermissionData, MenuKeys.SETTLEMENT_ADJUSTMENT) && {
          key: MenuKeys.SETTLEMENT_ADJUSTMENT,
          label: tEnum('menu.SETTLEMENT_ADJUSTMENT'),
          path: MY_ROUTE.SETTLEMENT.SETTLEMENT_ADJUSTMENT,
          onClick: () => router.push(MY_ROUTE.SETTLEMENT.SETTLEMENT_ADJUSTMENT),
        },
        checkedView(menuPermissionData, MenuKeys.SETTLEMENT_COUPON) && {
          key: MenuKeys.SETTLEMENT_COUPON,
          label: tEnum('menu.SETTLEMENT_COUPON'),
          path: MY_ROUTE.SETTLEMENT.SETTLEMENT_COUPON,
          onClick: () => router.push(MY_ROUTE.SETTLEMENT.SETTLEMENT_COUPON),
        },
      ],
    } as any),
  checkedView(menuPermissionData, MenuKeys.OSSUARY) &&
    ({
      key: MenuKeys.OSSUARY,
      label: tEnum('menu.OSSUARY'),
      icon: checkSelectedKey(MenuKeys.OSSUARY, selectedKey) ? (
        <Image src={ICONS.OSSUARY_GREEN} alt='ossuary' />
      ) : (
        <Image src={ICONS.OSSUARY} alt='ossuary' />
      ),
      onClick: () => router.push(MY_ROUTE.OSSUARY),
      path: MY_ROUTE.OSSUARY,
    } as any),
  checkedView(menuPermissionData, MenuKeys.MATERIAL) &&
    ({
      key: MenuKeys.MATERIAL,
      label: tEnum('menu.MATERIAL'),
      icon: checkSelectedKey(MenuKeys.MATERIAL, selectedKey) ? (
        <Image src={ICONS.ITEM_GREEN} alt='Item' />
      ) : (
        <Image src={ICONS.ITEM} alt='Item' />
      ),
      children: [
        checkedView(menuPermissionData, MenuKeys.MATERIAL_FUNERAL_SUPPLIES) && {
          key: MenuKeys.MATERIAL_FUNERAL_SUPPLIES,
          label: tEnum('menu.MATERIAL_FUNERAL_SUPPLIES'),
          path: MY_ROUTE.MATERIAL.MATERIAL_FUNERAL_SUPPLIES,
          onClick: () => router.push(MY_ROUTE.MATERIAL.MATERIAL_FUNERAL_SUPPLIES),
        },
        checkedView(menuPermissionData, MenuKeys.MATERIAL_LUCETTE_SUPPLIES) && {
          key: MenuKeys.MATERIAL_LUCETTE_SUPPLIES,
          label: tEnum('menu.MATERIAL_LUCETTE_SUPPLIES'),
          path: MY_ROUTE.MATERIAL.MATERIAL_LUCETTE_SUPPLIES,
          onClick: () => router.push(MY_ROUTE.MATERIAL.MATERIAL_LUCETTE_SUPPLIES),
        },
        checkedView(menuPermissionData, MenuKeys.MATERIAL_OTHER_FUNERAL_SUPPLIES) && {
          key: MenuKeys.MATERIAL_OTHER_FUNERAL_SUPPLIES,
          label: tEnum('menu.MATERIAL_OTHER_FUNERAL_SUPPLIES'),
          path: MY_ROUTE.MATERIAL.MATERIAL_OTHER_FUNERAL_SUPPLIES,
          onClick: () => router.push(MY_ROUTE.MATERIAL.MATERIAL_OTHER_FUNERAL_SUPPLIES),
        },
        checkedView(menuPermissionData, MenuKeys.MATERIAL_EXPENDABLES_SUPPLIES) && {
          key: MenuKeys.MATERIAL_EXPENDABLES_SUPPLIES,
          label: tEnum('menu.MATERIAL_EXPENDABLES_SUPPLIES'),
          path: MY_ROUTE.MATERIAL.MATERIAL_EXPENDABLES_SUPPLIES,
          onClick: () => router.push(MY_ROUTE.MATERIAL.MATERIAL_EXPENDABLES_SUPPLIES),
        },
      ],
    } as any),
  checkedView(menuPermissionData, MenuKeys.FACILITY) &&
    ({
      key: MenuKeys.FACILITY,
      label: tEnum('menu.FACILITY'),
      icon: checkSelectedKey(MenuKeys.FACILITY, selectedKey) ? (
        <Image src={ICONS.FACILITY_GREEN} alt='Facility' />
      ) : (
        <Image src={ICONS.FACILITY} alt='Facility' />
      ),
      onClick: () => router.push(MY_ROUTE.FACILITY),
      path: MY_ROUTE.FACILITY,
    } as any),
  checkedView(menuPermissionData, MenuKeys.COLLABORATING) &&
    ({
      key: MenuKeys.COLLABORATING,
      label: tEnum('menu.COLLABORATING'),
      icon: checkSelectedKey(MenuKeys.COLLABORATING, selectedKey) ? (
        <Image src={ICONS.COLLABORATING_GREEN} alt='Hospital' />
      ) : (
        <Image src={ICONS.COLLABORATING} alt='Hospital' />
      ),
      children: [
        checkedView(menuPermissionData, MenuKeys.COLLABORATING_SALES_CALENDAR) && {
          key: MenuKeys.COLLABORATING_SALES_CALENDAR,
          label: tEnum('menu.COLLABORATING_SALES_CALENDAR'),
          path: MY_ROUTE.COLLABORATING.COLLABORATING_SALES_CALENDAR,
          onClick: () => router.push(MY_ROUTE.COLLABORATING.COLLABORATING_SALES_CALENDAR),
        },
        checkedView(menuPermissionData, MenuKeys.COLLABORATING_PARTNER_HOSPITAL) && {
          key: MenuKeys.COLLABORATING_PARTNER_HOSPITAL,
          label: tEnum('menu.COLLABORATING_PARTNER_HOSPITAL'),
          path: MY_ROUTE.COLLABORATING.COLLABORATING_PARTNER_HOSPITAL,
          onClick: () => router.push(MY_ROUTE.COLLABORATING.COLLABORATING_PARTNER_HOSPITAL),
        },
        checkedView(menuPermissionData, MenuKeys.COLLABORATING_SALE_LIST) && {
          key: MenuKeys.COLLABORATING_SALE_LIST,
          label: tEnum('menu.COLLABORATING_SALE_LIST'),
          path: MY_ROUTE.COLLABORATING.COLLABORATING_SALE_LIST,
          onClick: () => router.push(MY_ROUTE.COLLABORATING.COLLABORATING_SALE_LIST),
        },
        checkedView(menuPermissionData, MenuKeys.COLLABORATING_DELIVERY_PROMOTIONAL_MATERIAL) && {
          key: MenuKeys.COLLABORATING_DELIVERY_PROMOTIONAL_MATERIAL,
          label: tEnum('menu.COLLABORATING_DELIVERY_PROMOTIONAL_MATERIAL'),
          path: MY_ROUTE.COLLABORATING.COLLABORATING_DELIVERY_PROMOTIONAL_MATERIAL,
          onClick: () => router.push(MY_ROUTE.COLLABORATING.COLLABORATING_DELIVERY_PROMOTIONAL_MATERIAL),
        },
        checkedView(menuPermissionData, MenuKeys.COLLABORATING_SETTLEMENT) && {
          key: MenuKeys.COLLABORATING_SETTLEMENT,
          label: tEnum('menu.COLLABORATING_SETTLEMENT'),
          path: MY_ROUTE.COLLABORATING.COLLABORATING_SETTLEMENT,
          onClick: () => router.push(MY_ROUTE.COLLABORATING.COLLABORATING_SETTLEMENT),
        },
      ],
    } as any),
  checkedView(menuPermissionData, MenuKeys.POST) && {
    key: MenuKeys.POST,
    label: tEnum('menu.POST'),
    icon: checkSelectedKey(MenuKeys.POST, selectedKey) ? (
      <Image src={ICONS.POST_GREEN} alt='Post' />
    ) : (
      <Image src={ICONS.POST} alt='Post' />
    ),
    path: MY_ROUTE.POST,
    onClick: () => router.push(MY_ROUTE.POST),
  },
  checkedView(menuPermissionData, MenuKeys.STATISTIC) && {
    key: MenuKeys.STATISTIC,
    label: tEnum('menu.STATISTIC'),
    icon: checkSelectedKey(MenuKeys.STATISTIC, selectedKey) ? (
      <Image src={ICONS.CHART_GREEN} alt='Chart' />
    ) : (
      <Image src={ICONS.CHART} alt='Chart' />
    ),
    children: [
      checkedView(menuPermissionData, MenuKeys.STATISTIC_BASIC) && {
        key: MenuKeys.STATISTIC_BASIC,
        label: tEnum('menu.STATISTIC_BASIC'),
        path: MY_ROUTE.STATISTIC.STATISTIC_BASIC,
        onClick: () => router.push(MY_ROUTE.STATISTIC.STATISTIC_BASIC),
      },
      checkedView(menuPermissionData, MenuKeys.STATISTIC_REGION) && {
        key: MenuKeys.STATISTIC_REGION,
        label: tEnum('menu.STATISTIC_REGION'),
        path: MY_ROUTE.STATISTIC.STATISTIC_REGION,
        onClick: () => router.push(MY_ROUTE.STATISTIC.STATISTIC_REGION),
      },
      checkedView(menuPermissionData, MenuKeys.STATISTIC_ARTICLE) && {
        key: MenuKeys.STATISTIC_ARTICLE,
        label: tEnum('menu.STATISTIC_ARTICLE'),
        path: MY_ROUTE.STATISTIC.STATISTIC_ARTICLE,
        onClick: () => router.push(MY_ROUTE.STATISTIC.STATISTIC_ARTICLE),
      },
    ],
  },
  checkedView(menuPermissionData, MenuKeys.MANAGER) && {
    key: MenuKeys.MANAGER,
    label: tEnum('menu.MANAGER'),
    icon: checkSelectedKey(MenuKeys.MANAGER, selectedKey) ? (
      <Image src={ICONS.ADMIN_GREEN} alt='Admin' />
    ) : (
      <Image src={ICONS.ADMIN} alt='Admin' />
    ),
    children: [
      checkedView(menuPermissionData, MenuKeys.MANAGER_RESERVATION_TIME) && {
        key: MenuKeys.MANAGER_RESERVATION_TIME,
        label: tEnum('menu.MANAGER_RESERVATION_TIME'),
        path: MY_ROUTE.MANAGER.MANAGER_RESERVATION_TIME,
        onClick: () => router.push(MY_ROUTE.MANAGER.MANAGER_RESERVATION_TIME),
      },
      checkedView(menuPermissionData, MenuKeys.MANAGER_USER) && {
        key: MenuKeys.MANAGER_USER,
        label: tEnum('menu.MANAGER_USER'),
        path: MY_ROUTE.MANAGER.MANAGER_USER,
        onClick: () => router.push(MY_ROUTE.MANAGER.MANAGER_USER),
      },
      checkedView(menuPermissionData, MenuKeys.MANAGER_BRANCH) && {
        key: MenuKeys.MANAGER_BRANCH,
        label: tEnum('menu.MANAGER_BRANCH'),
        path: MY_ROUTE.MANAGER.MANAGER_BRANCH,
        onClick: () => router.push(MY_ROUTE.MANAGER.MANAGER_BRANCH),
      },
    ],
  },
  checkedView(menuPermissionData, MenuKeys.SETTING) && {
    key: MenuKeys.SETTING,
    label: tEnum('menu.SETTING'),
    icon: checkSelectedKey(MenuKeys.SETTING, selectedKey) ? (
      <Image src={ICONS.SETTING_GREEN} alt='Setting' />
    ) : (
      <Image src={ICONS.SETTING} alt='Setting' />
    ),
    onClick: () => router.push(MY_ROUTE.SETTING),
    path: MY_ROUTE.SETTING,
  },
];
