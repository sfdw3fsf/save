import { ROUTES } from '@/router/routes.constant';
import { Navigate } from 'react-router-dom';

const ChangePasswordNavigation = (): JSX.Element => {
  return <Navigate to={ROUTES.ACCOUNT.CHANGE_PASSWORD} replace />;
};

export default ChangePasswordNavigation;
