import PermissionAccept from '@/components/hocs/PermissionAccept';
import { ROUTES } from '@/router/routes.constant';
import { useAuthStore } from '@/store/auth.store';
import { USER_ROLE } from '@/utils/enums/user/user-role.enum';
import { USER_STATUS } from '@/utils/enums/user/user-status.enum';
import { useTranslation } from 'react-i18next';
import { Navigate, useNavigate } from 'react-router-dom';
import SignUpExpertStep3 from './components/SignUpExpertStep3';

const SignUpExpertStep3Page = PermissionAccept(
  (): JSX.Element => {
    const { t: tCommon } = useTranslation('common');

    const { userInfo, resetUserInfo } = useAuthStore();

    const navigate = useNavigate();

    if (userInfo?.userStatus !== USER_STATUS.PENDING) {
      return <Navigate to={ROUTES.HOME} replace />;
    }

    return (
      <SignUpExpertStep3
        titleProps={{
          currentStep: 3,
          title: tCommon('sign_up.join_the_experts'),
          subtitle: tCommon('sign_up.input_details'),
          description: tCommon('sign_up.enter_info_below'),
        }}
        onBack={() => navigate(ROUTES.SIGN_UP.EXPERT.STEP_2)}
        onNext={() => {
          navigate(ROUTES.HOME);
          resetUserInfo();
        }}
      />
    );
  },
  [USER_ROLE.EXPERT],
  [],
);

export default SignUpExpertStep3Page;
