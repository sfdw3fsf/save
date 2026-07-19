import Loading from '@/components/core/loading/Loading';
import { ROUTES } from '@/router/routes.constant';
import { PARAM_USER_ROLE } from '@/utils/constants/data.constant';
import { USER_ROLE } from '@/utils/enums/user/user-role.enum';
import { getParamString } from '@/utils/helpers/data.helper';
import { useMemo } from 'react';
import { Navigate, useLocation } from 'react-router-dom';

const SignUpStep3Navigation = (): JSX.Element => {
  const location = useLocation();

  const userRole = useMemo(() => getParamString(location.search, PARAM_USER_ROLE), [location.search]);

  switch (userRole) {
    case USER_ROLE.EXPERT:
      return <Navigate to={ROUTES.SIGN_UP.EXPERT.STEP_3} replace />;
    case USER_ROLE.MEMBER:
      return <Navigate to={ROUTES.SIGN_UP.INDIVIDUAL.STEP_3} replace />;
    default:
      return <Loading />;
  }
};

export default SignUpStep3Navigation;
