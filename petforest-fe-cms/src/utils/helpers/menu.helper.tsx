import { CustomMenuItemProps } from '@/layout/menu/MenuList';
import { ICONS } from '@/theme';
import Image from 'next/image';
import { MY_ROUTE, ROUTE_GROUP } from '../constants/route.constant';

export const getMenuIcon = (pathSnippets: string[]) => {
  if (pathSnippets.length === 0) return <Image src={ICONS.HOME} alt='Home' />;

  switch (pathSnippets[0]) {
    case ROUTE_GROUP.DASHBOARD:
      return <Image src={ICONS.DASHBOARD} alt='Dashboard' />;
    case ROUTE_GROUP.FUNERAL:
      return <Image src={ICONS.FUNERAL} alt='Funeral' />;
    case ROUTE_GROUP.SETTLEMENT:
      return <Image src={ICONS.SETTLEMENT} alt='Settle' />;
    case ROUTE_GROUP.OSSUARY:
      return <Image src={ICONS.OSSUARY} alt='Charnel' />;
    case ROUTE_GROUP.MATERIAL:
      return <Image src={ICONS.ITEM} alt='Item' />;
    case ROUTE_GROUP.FACILITY:
      return <Image src={ICONS.FACILITY} alt='Facility' />;
    case ROUTE_GROUP.COLLABORATING:
      return <Image src={ICONS.COLLABORATING} alt='Hospital' />;
    case ROUTE_GROUP.POST:
      return <Image src={ICONS.POST} alt='Post' />;
    case ROUTE_GROUP.STATISTIC:
      return <Image src={ICONS.CHART} alt='Chart' />;
    case ROUTE_GROUP.MANAGER:
      return <Image src={ICONS.ADMIN} alt='Admin' />;
    case ROUTE_GROUP.SETTING:
      return <Image src={ICONS.SETTING} alt='Setting' />;
  }
};

export const getMenuKey = (pathSnippets: string) => {
  if (pathSnippets.length === 0) return <Image src={ICONS.HOME} alt='Home' />;
  switch (pathSnippets) {
    case MY_ROUTE.DASHBOARD:
      return 'DASHBOARD';
    case MY_ROUTE.FUNERAL.self:
      return 'FUNERAL';
    case MY_ROUTE.SETTLEMENT.self:
      return 'SETTLEMENT';
    case MY_ROUTE.OSSUARY:
      return 'CHARNEL';
    case MY_ROUTE.MATERIAL.self:
      return 'MATERIAL';
    case MY_ROUTE.FACILITY:
      return 'FACILITY';
    case MY_ROUTE.COLLABORATING.self:
      return 'COLLABORATING';
    case MY_ROUTE.POST:
      return 'POST';
    case MY_ROUTE.STATISTIC.self:
      return 'CHART';
    case MY_ROUTE.MANAGER.self:
      return 'MANAGER';
    case MY_ROUTE.SETTING:
      return 'SETTING';
  }
  return '';
};

export const findMenuItemByPath = (path: string, menuItems: CustomMenuItemProps[]): CustomMenuItemProps | undefined => {
  for (const item of menuItems) {
    if (item.path === path) {
      return item;
    }

    if (item.children) {
      const foundChild = findMenuItemByPath(path, item.children);
      if (foundChild) {
        return foundChild;
      }
    }
  }
  return undefined;
};
