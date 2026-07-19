import PageContainer from '@/components/core/page-container/PageContainer';
import { useState } from 'react';
import { useTranslation } from 'react-i18next';
import ChangePasswordConfirmation from './components/ChangePasswordConfirmation';
import ChangePasswordForm from './components/ChangePasswordForm';

const ChangePasswordPage = (): JSX.Element => {
  const { t: tCommon } = useTranslation('common');

  const [isSuccess, setIsSuccess] = useState<boolean>(false);

  return (
    <PageContainer withBackground title={tCommon('title.change_password')}>
      {isSuccess ? <ChangePasswordConfirmation /> : <ChangePasswordForm onSuccess={() => setIsSuccess(true)} />}
    </PageContainer>
  );
};

export default ChangePasswordPage;
