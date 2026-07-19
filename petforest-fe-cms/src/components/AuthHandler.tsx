'use client';

import { useMenuPermission } from '@/hook/auth/useMenuPermission';
import { useUserBranches } from '@/hook/auth/useUserBranches';
import { languages } from '@/i18Config';
import PageLayout from '@/layout/PageLayout';
import { useAuthStore } from '@/store/auth/auth.store';
import { authRoutes, fullScreenModeRoutes, MY_ROUTE, openNewTabRoutes, viewModeRoutes } from '@/utils/constants/route.constant';
import { USER_PERMISSION } from '@/utils/enums/manager/manager.enum';
import { deleteCookie, getCookie } from 'cookies-next';
import { usePathname, useRouter } from 'next/navigation';

import { BaseHTTP } from '@/config/http';
import { pathToPermissionKey } from '@/store/auth/auth.service';
import { MenuPermissionDTO } from '@/store/auth/dto/auth-menu-permission.dto';
import { COLORS } from '@/theme';
import { useQueryClient } from '@tanstack/react-query';
import { Layout } from 'antd';
import { useEffect, useMemo, useState } from 'react';
import NoPermission from './NoPermission';

const AuthHandler = ({ children }: any) => {
  const router = useRouter();
  const tabKey = 'tab-key';
  const [isTokenConfigured, setIsTokenConfigured] = useState(false);
  const [isLoading, setIsLoading] = useState(true);

  const { token, selectedBranchId, setSelectedBranchId, isLoggedIn } = useAuthStore((state) => state);
  const { userBranchesData } = useUserBranches(isTokenConfigured && !!token);
  const { menuPermissionData } = useMenuPermission(undefined, isTokenConfigured && !!token);

  const isStay = getCookie('stay');

  const data = useMemo(() => {
    if (isLoggedIn && userBranchesData) return userBranchesData;
    return undefined;
  }, [userBranchesData, isLoggedIn]);

  const pathName = usePathname();
  const queryClient = useQueryClient();

  const currentUrl = pathName;
  const urlSegments = currentUrl.split('/');
  const filteredUrl = languages.includes(urlSegments[1]) ? `/${urlSegments.slice(2).join('/')}` : currentUrl;

  const isAuthRouteCheck = authRoutes.includes(filteredUrl);
  const isViewModeRouteCheck = viewModeRoutes.includes(filteredUrl);
  const isFullScreenModeRoute = fullScreenModeRoutes.includes(filteredUrl);
  const isNewTabRoute = openNewTabRoutes.includes(filteredUrl);

  const userPermission = useMemo(() => {
    const menuKey = pathToPermissionKey(pathName) as keyof MenuPermissionDTO;

    return menuPermissionData && menuPermissionData[menuKey];
  }, [pathName, menuPermissionData]);

  useEffect(() => {
    if (token) {
      BaseHTTP.getInstance().config({
        accessToken: token,
        branchId: selectedBranchId || 0,
      });
      setIsTokenConfigured(true);
    } else {
      setSelectedBranchId(undefined);
      setIsTokenConfigured(false);
    }
  }, [token, selectedBranchId]);

  useEffect(() => {
    if (data && !selectedBranchId) {
      const mainBranch = data.data.find((item) => item.isMainBranch)?.id;
      setSelectedBranchId(mainBranch);
    }
  }, [data, selectedBranchId, token]);

  useEffect(() => {
    const handleTabClose = () => {
      if (isStay !== 'true' && !isViewModeRouteCheck && !isFullScreenModeRoute && !isNewTabRoute) {
        deleteCookie('token');
        deleteCookie('isLogin');
        router.push(MY_ROUTE.AUTH.LOGIN);
      }
    };

    const checkTabStatus = () => {
      if (!sessionStorage.getItem(tabKey)) {
        handleTabClose();
      } else {
        console.log('Tab was reloaded.');
      }
    };

    checkTabStatus();

    const handleBeforeUnload = () => {
      sessionStorage.setItem(tabKey, 'active');
    };

    window.addEventListener('beforeunload', handleBeforeUnload);

    return () => {
      window.removeEventListener('beforeunload', handleBeforeUnload);
    };
  }, [router]);

  useEffect(() => {
    if (userBranchesData && menuPermissionData) {
      setIsLoading(false);
    }
  }, [userBranchesData, menuPermissionData]);

  useEffect(() => {
    if (selectedBranchId) {
      queryClient.resetQueries();
    }
  }, [selectedBranchId]);

  if (isLoading && !isAuthRouteCheck) {
    return null;
  }

  if (!isTokenConfigured && !isAuthRouteCheck) {
    return <NoPermission />;
  }

  if (userPermission && userPermission === USER_PERMISSION.NOT_ALLOW && !isAuthRouteCheck) {
    return <NoPermission />;
  }

  if (isFullScreenModeRoute) {
    return (
      <Layout
        style={{
          backgroundColor: COLORS.white,
          transition: 'margin-left 0.3s ease',
          height: '100vh',
        }}>
        <div className='h-full'>{children}</div>
      </Layout>
    );
  }

  if (isAuthRouteCheck || isViewModeRouteCheck) {
    return (
      <Layout
        style={{
          marginRight: 40,
          marginLeft: 40,
          marginTop: isAuthRouteCheck ? 0 : 60,
          backgroundColor: COLORS.neutral[15],
          transition: 'margin-left 0.3s ease',
          overflow: 'hidden',
        }}>
        {/* <BreadCrumb /> */}
        <div className='bg-white min-h-[calc(100vh-60px)]'>{children}</div>
      </Layout>
    );
  }

  return <PageLayout isViewMode={isViewModeRouteCheck}>{children}</PageLayout>;
};

export default AuthHandler;
