import CalendarBlank2Icon from '@/components/icons/CalendarBlank2Icon';
import CalendarBlankIcon from '@/components/icons/CalendarBlankIcon';
import Key2Icon from '@/components/icons/Key2Icon';
import KeyIcon from '@/components/icons/KeyIcon';
import User2Icon from '@/components/icons/User2Icon';
import UserIcon from '@/components/icons/UserIcon';
import { ROUTES } from '@/router/routes.constant';
import { USER_ROLE } from '@/utils/enums/user/user-role.enum';
import { TFunction } from 'i18next';
import { MyPageTabItemProps } from '../components/MyPageTabItem';

export const getMyPageTabItems = (tCommon: TFunction<'common'>, userRole?: USER_ROLE): MyPageTabItemProps[] => {
  switch (userRole) {
    case USER_ROLE.MEMBER:
      return [
        {
          label: tCommon('my-page.calendar'),
          icon: <CalendarBlankIcon />,
          activeIcon: <CalendarBlank2Icon />,
          to: ROUTES.MY_PAGE.INDIVIDUAL.SCHEDULE,
        },
        {
          label: tCommon('my-page.member_info'),
          icon: <UserIcon />,
          activeIcon: <User2Icon />,
          to: ROUTES.MY_PAGE.INDIVIDUAL.INFO,
        },
        {
          label: tCommon('my-page.change_password'),
          icon: <KeyIcon />,
          activeIcon: <Key2Icon />,
          to: ROUTES.MY_PAGE.INDIVIDUAL.PASSWORD,
        },
      ];
    case USER_ROLE.EXPERT:
      return [
        {
          label: tCommon('my-page.calendar'),
          icon: <CalendarBlankIcon />,
          activeIcon: <CalendarBlank2Icon />,
          to: ROUTES.MY_PAGE.EXPERT.SCHEDULE,
        },
        {
          label: tCommon('my-page.expert_info'),
          icon: <UserIcon />,
          activeIcon: <User2Icon />,
          to: ROUTES.MY_PAGE.EXPERT.INFO,
        },
        {
          label: tCommon('my-page.change_password'),
          icon: <KeyIcon />,
          activeIcon: <Key2Icon />,
          to: ROUTES.MY_PAGE.EXPERT.PASSWORD,
        },
      ];
    default:
      return [];
  }
};
