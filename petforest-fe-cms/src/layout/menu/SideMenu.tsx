import CustomSelect from '@/components/common/CustomSelect';
import { useMenuPermission } from '@/hook/auth/useMenuPermission';
import { useBranch } from '@/hook/branch/useBranch';
import useResize from '@/hook/useResize';
import { languages } from '@/i18Config';
import { getUserBranchesApi, logoutApi, userBranchesHeaderKeys } from '@/store/auth/auth.api';
import { userBranchesRespToDto } from '@/store/auth/auth.service';
import { useAuthStore } from '@/store/auth/auth.store';
import { COLORS } from '@/theme';
import { MY_ROUTE } from '@/utils/constants/route.constant';
import { MenuKeys } from '@/utils/enums/side-menu-type';
import { findMenuItemByPath, getMenuKey } from '@/utils/helpers/menu.helper';
import { handleError } from '@/utils/helpers/notification-error.helper';
import Icon, { LeftOutlined, MenuOutlined } from '@ant-design/icons';
import { useMutation, useQuery } from '@tanstack/react-query';
import { Button, Divider, Layout, Menu, MenuProps } from 'antd';
import { deleteCookie } from 'cookies-next';
import { usePathname, useRouter } from 'next/navigation';
import { useEffect, useMemo, useState } from 'react';
import { useTranslation } from 'react-i18next';
import './custom-menu.scss';
import { MenuList } from './MenuList';
import MenuModal from './MenuModal';

const { Sider } = Layout;

type SideMenuProps = {
  collapsed: boolean;
  handleCollapsed: () => void;
};

const SideMenu = ({ collapsed, handleCollapsed }: SideMenuProps) => {
  const { t: tEnum } = useTranslation('enum');
  const { isResponsive } = useResize();
  const router = useRouter();
  const { menuPermissionData } = useMenuPermission();
  const { selectedBranchId, setSelectedBranchId } = useAuthStore((state) => state);
  const { branchOptions } = useBranch();
  const [prevBranchColor, setPrevBranchColor] = useState<string>('');

  const pathname = usePathname()
    .split('/')
    .filter((item: string) => item && !languages.includes(item))
    .slice(0, 2);
  const pathSnippets = `/${pathname.join('/')}`;
  const [current, setCurrent] = useState<string>('');
  const [openKeys, setOpenKeys] = useState<string[]>([getMenuKey(`/${pathname[0]}`).toString()]);

  const { mutate: mutateLogout } = useMutation({
    mutationFn: () => logoutApi(),
    onError: handleError,
  });

  const onClickMenu: MenuProps['onClick'] = (e) => {
    setCurrent(e.key);
    if (isResponsive) handleCollapsed();
  };

  const onOpenChange = (keys: string[]) => {
    if (keys.length === 0) {
      setOpenKeys([]);
    } else {
      setOpenKeys([keys[keys.length - 1]]);
    }
  };

  const onLogout = () => {
    mutateLogout();
    deleteCookie('token');
    deleteCookie('id');
    deleteCookie('isLogin');
    deleteCookie('stay');
    router.push(MY_ROUTE.AUTH.LOGIN);
  };

  const onChangePassword = () => {
    const windowFeatures = 'width=500,height=400,top=100,left=100,resizable=no,scrollbars=no';
    window.open('/auth/change-password', '_blank', windowFeatures);
  };

  const SiderMenu = () => (
    <Menu
      mode='inline'
      onClick={onClickMenu}
      selectedKeys={[current]}
      openKeys={openKeys}
      items={MenuList({ router, tEnum, menuPermissionData, selectedKey: current as MenuKeys })}
      style={{
        overflowY: 'auto',
        border: 'none',
      }}
      className='!pb-[10px] md:!pb-[70px] !p-0'
      onOpenChange={onOpenChange}></Menu>
  );

  const { data: userBranchesData, isLoading: isLoadingUserBranch } = useQuery({
    queryKey: userBranchesHeaderKeys.lists(),
    queryFn: () => getUserBranchesApi(),
    select: (response) => {
      return { ...response.data, data: response.data.data.map(userBranchesRespToDto) };
    },
  });

  const branchColor = useMemo(() => {
    return userBranchesData?.data.find((item) => item.id === selectedBranchId)?.color;
  }, [selectedBranchId, userBranchesData]);

  useEffect(() => {
    if (branchColor) {
      setPrevBranchColor(branchColor);
    }
  }, [branchColor]);

  useEffect(() => {
    const menuItems = MenuList({ router, tEnum, menuPermissionData });
    const foundItem = findMenuItemByPath(pathSnippets, menuItems);
    if (foundItem) {
      setCurrent(foundItem.key);
    }
  }, [pathSnippets, router, tEnum, menuPermissionData]);

  return isResponsive ? (
    <MenuModal
      key={JSON.stringify({ branchOptions, selectedBranchId, branchColor })}
      slideIn='left'
      title={
        <div className={`w-full flex items-center justify-between `}>
          <img src='/images/Logo.svg' alt='Logo' />
          <Button className='!rounded-full !gap-0 !w-[34px] !h-[34px] !bg-grey-grey-cloud !border-none' onClick={handleCollapsed}>
            <LeftOutlined
              style={{
                fontSize: '12px',
                color: COLORS.neutral[80],
                strokeWidth: '30',
                stroke: COLORS.neutral[80],
              }}
            />
            <MenuOutlined
              style={{
                color: COLORS.neutral[80],
                strokeWidth: '30',
                stroke: COLORS.neutral[80],
              }}
            />
          </Button>
        </div>
      }
      isOpen={collapsed}
      onClose={handleCollapsed}
      footer={
        <div className='flex bg-white gap-3 flex-col w-full my-[20px] px-5 absolute bottom-0'>
          <Divider style={{ margin: '0 0 8px 0' }} />
          <div style={{ display: 'flex', position: 'relative' }}>
            <Icon
              component={() => (
                // <img
                //   src='/images/icon/branchIcon.svg'
                //   alt='Logo'
                //   className='absolute z-50 left-[calc(50%-50px)] pr-4 translate-x-[-50%]'
                // />
                <div className='absolute z-50 left-[calc(50%-50px)] pr-4 translate-x-[-50%]'>
                  <svg width='20' height='20' viewBox='0 0 20 20' fill='none' xmlns='http://www.w3.org/2000/svg'>
                    <rect width='20' height='20' rx='10' fill='white' />
                    <path
                      d='M11.6667 15V12C11.6667 11.6889 11.6667 11.5333 11.6061 11.4144C11.5529 11.3099 11.4679 11.2249 11.3633 11.1717C11.2445 11.1111 11.0889 11.1111 10.7778 11.1111H9.22222C8.91108 11.1111 8.75551 11.1111 8.63667 11.1717C8.53214 11.2249 8.44715 11.3099 8.39389 11.4144C8.33333 11.5333 8.33333 11.6889 8.33333 12V15M5 7.22222C5 8.1427 5.74619 8.88889 6.66667 8.88889C7.58714 8.88889 8.33333 8.1427 8.33333 7.22222C8.33333 8.1427 9.07953 8.88889 10 8.88889C10.9205 8.88889 11.6667 8.1427 11.6667 7.22222C11.6667 8.1427 12.4129 8.88889 13.3333 8.88889C14.2538 8.88889 15 8.1427 15 7.22222M6.77778 15H13.2222C13.8445 15 14.1556 15 14.3933 14.8789C14.6024 14.7724 14.7724 14.6024 14.8789 14.3933C15 14.1556 15 13.8445 15 13.2222V6.77778C15 6.1555 15 5.84436 14.8789 5.60668C14.7724 5.39761 14.6024 5.22763 14.3933 5.1211C14.1556 5 13.8445 5 13.2222 5H6.77778C6.1555 5 5.84436 5 5.60668 5.1211C5.39761 5.22763 5.22763 5.39761 5.1211 5.60668C5 5.84436 5 6.1555 5 6.77778V13.2222C5 13.8445 5 14.1556 5.1211 14.3933C5.22763 14.6024 5.39761 14.7724 5.60668 14.8789C5.84436 15 6.1555 15 6.77778 15Z'
                      stroke={branchColor ?? COLORS.primary[1000]}
                      strokeLinecap='round'
                      strokeLinejoin='round'
                    />
                  </svg>
                </div>
              )}
            />
            <CustomSelect
              value={selectedBranchId}
              onChange={(value) => {
                setSelectedBranchId(value);
              }}
              options={branchOptions}
              className='w-full custom-branch-select side-branch-select'
              style={{ backgroundColor: branchColor ?? prevBranchColor }}
              loading={isLoadingUserBranch}
            />
          </div>
          <Button onClick={onChangePassword} className='!rounded-[8px] w-full !gap-[8px] flex items-center !text-neutral-100'>
            비밀번호 변경
          </Button>
          <Button onClick={onLogout} className='!rounded-[8px] w-full !gap-[8px] flex items-center !text-neutral-100'>
            로그아웃
          </Button>
        </div>
      }
      closable={false}>
      {SiderMenu()}
    </MenuModal>
  ) : (
    <Sider
      trigger={null}
      collapsible
      collapsed={collapsed}
      width={240}
      style={{
        position: 'fixed',
        height: '100vh',
        left: 0,
        top: '68px',
        zIndex: 1000,
        overflow: 'auto',
        backgroundColor: COLORS.white,
      }}
      className='hidden lg:block'>
      {SiderMenu()}
    </Sider>
  );
};

export default SideMenu;
