import PageContainer from '@/components/core/page-container/PageContainer';
import { useTranslation } from 'react-i18next';
import FindPasswordByEmail from './components/FindPasswordByEmail';

const FindPasswordPage = (): JSX.Element => {
  const { t: tCommon } = useTranslation('common');

  // const location = useLocation();

  // const params = queryString.parse(location.search);
  // const isByEmail = useMemo<boolean>(() => params[PARAM_KEY] === PARAM_VALUE_EMAIL, [params]);

  return (
    <PageContainer withBackground title={tCommon('title.find_password')}>
      {/* {isByEmail ? <FindPasswordByEmail /> : <FindPasswordByPhoneNumber />} */}
      <FindPasswordByEmail />
    </PageContainer>
  );
};

export default FindPasswordPage;
