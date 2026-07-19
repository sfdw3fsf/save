import useLogout from '@/hooks/useLogout';
import NotFoundPage from '@/pages/not-found/NotFoundPage';
import { ROUTES } from '@/router/routes.constant';
import { useAuthStore } from '@/store/auth.store';
import { USER_ROLE } from '@/utils/enums/user/user-role.enum';
import { USER_STATUS } from '@/utils/enums/user/user-status.enum';
import { ComponentType, useEffect, useMemo } from 'react';
import { useNavigate } from 'react-router-dom';
import Loading from '../core/loading/Loading';

const PermissionAccept = <P extends object>(Component: ComponentType<P>, roles?: USER_ROLE[], statuses?: USER_STATUS[]) => {
  return function AcceptedComponent(props: P) {
    const navigate = useNavigate();

    const { userInfo, accessToken } = useAuthStore();
    const isLoggedIn = useMemo<boolean>(() => !!accessToken, [accessToken]);

    const { handleLogout, isLoading: isLoggingOut } = useLogout();

    const isAcceptedRole = useMemo<boolean>(() => {
      if (!userInfo?.userRole) return !isLoggedIn;
      return roles?.length ? roles.includes(userInfo.userRole) : true;
    }, [userInfo?.userRole, isLoggedIn]);

    const isAcceptedStatus = useMemo<boolean>(() => {
      if (!userInfo?.userStatus) return !isLoggedIn;
      return statuses?.length ? statuses.includes(userInfo.userStatus) : true;
    }, [userInfo?.userStatus, isLoggedIn]);

    useEffect(() => {
      if (userInfo?.userStatus !== USER_STATUS.PENDING) return;
      if (userInfo.userRole === USER_ROLE.EXPERT) {
        navigate(ROUTES.SIGN_UP.EXPERT.STEP_3, { replace: true });
      } else if (userInfo.userRole === USER_ROLE.MEMBER) {
        navigate(ROUTES.SIGN_UP.INDIVIDUAL.STEP_3, { replace: true });
      }
    }, [userInfo]);

    useEffect(() => {
      if (!userInfo) return;
      const { userStatus, userRole } = userInfo;
      if (userRole !== USER_ROLE.EXPERT || userStatus !== USER_STATUS.PENDING_ADMIN_APPROVAL) return;
      handleLogout();
    }, [userInfo]);

    if ((isLoggedIn && userInfo === null) || isLoggingOut) {
      return <Loading />;
    }

    if (!isAcceptedRole || !isAcceptedStatus) {
      return <NotFoundPage />;
    }

    return <Component {...props} />;
  };
};

export default PermissionAccept;
