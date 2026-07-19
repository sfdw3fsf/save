import { NAVIGATION_ROUTES, ROUTES } from '@/router/routes.constant';
import { PARAM_ACCESS_TOKEN, PARAM_USER_ROLE } from '@/utils/constants/data.constant';
import { USER_ROLE } from '@/utils/enums/user/user-role.enum';
import { useTranslation } from 'react-i18next';
import { useNavigate } from 'react-router-dom';
import SignUpStep2 from '../sign-up/components/SignUpStep2';

const SignUpExpertStep2Page = (): JSX.Element => {
  const { t: tCommon } = useTranslation('common');

  const navigate = useNavigate();

  return (
    <SignUpStep2
      titleProps={{
        currentStep: 2,
        title: tCommon('sign_up.expert'),
        subtitle: tCommon('sign_up.set_id_and_password'),
        description: tCommon('sign_up.now_please_set_your_id_and_password'),
      }}
      onBack={() => navigate(ROUTES.SIGN_UP.EXPERT.STEP_1)}
      onNext={(token: string) =>
        navigate(
          `${NAVIGATION_ROUTES.SIGN_UP_STEP_3}?${PARAM_ACCESS_TOKEN}=${encodeURIComponent(token)}&${PARAM_USER_ROLE}=${encodeURIComponent(USER_ROLE.EXPERT)}`,
        )
      }
    />
  );
};

export default SignUpExpertStep2Page;
