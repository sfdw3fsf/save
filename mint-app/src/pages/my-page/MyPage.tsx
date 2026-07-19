import Loading from '@/components/core/loading/Loading';
import { ROUTES } from '@/router/routes.constant';
import { useAuthStore } from '@/store/auth.store';
import { USER_ROLE } from '@/utils/enums/user/user-role.enum';
import { Navigate } from 'react-router-dom';

const MyPage = (): JSX.Element => {
  const { userInfo } = useAuthStore();

  switch (userInfo?.userRole) {
    case USER_ROLE.MEMBER:
      return <Navigate to={ROUTES.MY_PAGE.INDIVIDUAL.SCHEDULE} replace />;
    case USER_ROLE.EXPERT:
      return <Navigate to={ROUTES.MY_PAGE.EXPERT.SCHEDULE} replace />;
    default:
      return <Loading />;
  }
};

export default MyPage;
