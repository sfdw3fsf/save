import { ROUTES } from '@/router/routes.constant';
import { USER_ROLE } from '@/utils/enums/user/user-role.enum';
import { useTranslation } from 'react-i18next';
import { useNavigate } from 'react-router-dom';
import SignUpStep1 from '../sign-up/components/SignUpStep1';

const SignUpIndivStep1Page = (): JSX.Element => {
  const { t: tCommon } = useTranslation('common');

  const navigate = useNavigate();

  return (
    <SignUpStep1
      userRole={USER_ROLE.MEMBER}
      titleProps={{
        currentStep: 1,
        title: tCommon('sign_up.membership_registration'),
        subtitle: tCommon('sign_up.identity_verification'),
        description: tCommon('sign_up.to_complete_your_registration'),
      }}
      onBack={() => navigate(ROUTES.SIGN_UP.self)}
      onNext={() => navigate(ROUTES.SIGN_UP.INDIVIDUAL.STEP_2)}
    />
  );
};

export default SignUpIndivStep1Page;
