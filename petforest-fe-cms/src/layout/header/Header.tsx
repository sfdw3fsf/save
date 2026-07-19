import useResize from '@/hook/useResize';
import { getUserBranchesApi, logoutApi, userBranchesHeaderKeys } from '@/store/auth/auth.api';
import { userBranchesDtoToOptions, userBranchesRespToDto } from '@/store/auth/auth.service';
import { useAuthStore } from '@/store/auth/auth.store';
import { COLORS } from '@/theme';
import { MY_ROUTE } from '@/utils/constants/route.constant';
import { handleError } from '@/utils/helpers/notification-error.helper';
import { LeftOutlined, MenuOutlined, RightOutlined } from '@ant-design/icons';
import { useMutation, useQuery, useQueryClient } from '@tanstack/react-query';
import { Button, Layout, Select } from 'antd';
import { deleteCookie } from 'cookies-next';
import { useRouter } from 'next/navigation';
import { useEffect, useMemo, useState } from 'react';

const { Header: AntdHeader } = Layout;

type HeaderProps = {
  collapsed: boolean;
  handleCollapsed: () => void;
};
const Header = ({ collapsed, handleCollapsed }: HeaderProps) => {
  const { isResponsive } = useResize();
  const router = useRouter();
  const logout = useAuthStore((state) => state.logout);
  const queryClient = useQueryClient();
  const [prevBranchColor, setPrevBranchColor] = useState<string>(''); // For better UI, avoid case bg white when waiting fetch data
  const isHidden = useMemo(() => collapsed && isResponsive, [isResponsive, collapsed]);
  const { selectedBranchId, setSelectedBranchId } = useAuthStore((state) => state);

  const { data: userBranchesData, isLoading: isLoadingUserBranch } = useQuery({
    queryKey: userBranchesHeaderKeys.lists(),
    queryFn: () => getUserBranchesApi(),
    select: (response) => {
      return { ...response.data, data: response.data.data.map(userBranchesRespToDto) };
    },
  });

  const { mutate: mutateLogout } = useMutation({
    mutationFn: () => logoutApi(),
    onSuccess: () => {},
    onError: handleError,
  });

  const userBranchOptions = useMemo(() => {
    if (!userBranchesData) return [];
    return userBranchesDtoToOptions(userBranchesData.data);
  }, [userBranchesData]);

  const branchColor = useMemo(() => {
    return userBranchesData?.data.find((item) => item.id === selectedBranchId)?.color;
  }, [selectedBranchId, userBranchesData]);

  useEffect(() => {
    if (branchColor) {
      setPrevBranchColor(branchColor);
    }
  }, [branchColor]);

  const onLogout = () => {
    queryClient.removeQueries();
    deleteCookie('token');
    deleteCookie('id');
    deleteCookie('isLogin');
    deleteCookie('stay');
    logout();

    router.push(MY_ROUTE.AUTH.LOGIN);
    mutateLogout();
  };

  const onChangePassword = () => {
    const windowFeatures = 'width=500,height=500,top=100,left=100,resizable=no,scrollbars=no';
    window.open('/auth/change-password', '_blank', windowFeatures);
  };

  const onGoToHome = () => {
    router.push('/');
  };

  return (
    <AntdHeader className='fixed top-0 z-10 !px-0 !h-[68px] w-full flex justify-between items-center pack-border-bottom-neutral-30'>
      <div className={`${isHidden && 'hidden'} w-full flex items-center justify-between px-[18px] lg:w-[240px]`}>
        <img onClick={onGoToHome} src='/images/Logo.svg' alt='Logo' className='cursor-pointer' />
        <Button className='!rounded-full !gap-0 !w-[34px] !h-[34px] !bg-grey-grey-cloud !border-none' onClick={handleCollapsed}>
          {isResponsive ? (
            <div>
              <MenuOutlined
                style={{
                  color: COLORS.neutral[80],
                  strokeWidth: '30',
                  stroke: COLORS.neutral[80],
                }}
              />

              <RightOutlined
                style={{
                  color: COLORS.neutral[80],
                  strokeWidth: '30',
                  stroke: COLORS.neutral[80],
                }}
              />
            </div>
          ) : (
            <div>
              {!collapsed && (
                <LeftOutlined
                  style={{
                    fontSize: '12px',
                    color: COLORS.neutral[80],
                    strokeWidth: '30',
                    stroke: COLORS.neutral[80],
                  }}
                />
              )}
              <MenuOutlined
                style={{
                  color: COLORS.neutral[80],
                  strokeWidth: '30',
                  stroke: COLORS.neutral[80],
                }}
              />
              {collapsed && (
                <RightOutlined
                  style={{
                    color: COLORS.neutral[80],
                    strokeWidth: '30',
                    stroke: COLORS.neutral[80],
                  }}
                />
              )}
            </div>
          )}
        </Button>
      </div>

      <div className='flex-1 items-center justify-between px-[40px] hidden lg:flex'>
        <div>
          <div style={{ display: 'flex', position: 'relative' }}>
            <div className='absolute z-50 left-[10px] top-[50%] translate-y-[-50%]'>
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
            <Select
              value={selectedBranchId}
              onChange={(value) => {
                setSelectedBranchId(value);
              }}
              options={userBranchOptions}
              className={`w-[138px] custom-branch-select !rounded-lg`}
              style={{ backgroundColor: branchColor ?? prevBranchColor }}
              loading={isLoadingUserBranch}
            />
          </div>
        </div>

        <div className='flex flex-row'>
          <Button onClick={onChangePassword} className='!rounded-[8px] !gap-[8px] flex items-center !text-neutral-100 mr-[12px]'>
            비밀번호 변경
          </Button>
          <Button onClick={onLogout} className='!rounded-[8px] !gap-[8px] flex items-center !text-neutral-100'>
            로그아웃
          </Button>
        </div>
      </div>
    </AntdHeader>
  );
};

export default Header;
