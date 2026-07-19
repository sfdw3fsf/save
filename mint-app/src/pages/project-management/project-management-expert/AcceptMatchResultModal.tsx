import SuccessFailPopUp, { ContentProps } from '@/components/core/success-fail-pop-up/SuccessFailPopUp';
import { ROUTES } from '@/router/routes.constant';
import { ACCEPT_MATCH_ENUM } from '@/utils/enums/project/accept-match.enum';
import qs from 'query-string';
import { useMemo } from 'react';
import { useTranslation } from 'react-i18next';
import { useLocation, useNavigate } from 'react-router-dom';

// mock URL
const AcceptMatchResultModal = (): JSX.Element => {
  const { t: tCommon } = useTranslation('common');
  const location = useLocation();
  const params = qs.parse(location.search);
  const { type } = params;

  const navigate = useNavigate();

  const contentProps = useMemo<ContentProps>(() => {
    switch (type) {
      case ACCEPT_MATCH_ENUM.ACCEPT:
        return {
          title: tCommon('expert_matching.accept.title'),
          content: tCommon('expert_matching.accept.content'),
        };
      case ACCEPT_MATCH_ENUM.REJECT:
        return {
          title: tCommon('expert_matching.reject.title'),
          content: tCommon('expert_matching.reject.content'),
        };
      case ACCEPT_MATCH_ENUM.ERROR:
        return {
          title: tCommon('expert_matching.accept_error.title'),
          content: tCommon('expert_matching.accept_error.content'),
        };

      default:
        return {};
    }
  }, [tCommon, type]);

  // METHODS
  const onCheck = () => {
    // Navigate to the first route
    navigate(ROUTES.PROJECT_MANAGEMENT.EXPERT);
  };

  const onClose = () => {
    navigate(ROUTES.PROJECT_MANAGEMENT.EXPERT);
  };

  return (
    <SuccessFailPopUp
      open={true}
      variant={type === ACCEPT_MATCH_ENUM.ERROR ? 'fail' : 'success'}
      {...contentProps}
      onCheck={onCheck}
      onClose={onClose}
    />
  );
};

export default AcceptMatchResultModal;
