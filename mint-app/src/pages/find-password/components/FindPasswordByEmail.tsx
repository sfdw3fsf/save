import { NAVIGATION_ROUTES } from '@/router/routes.constant';
import { PARAM_ACCESS_TOKEN } from '@/utils/constants/data.constant';
import { FindPasswordByEmailInput } from '@/utils/schemas/find.schema';
import { useState } from 'react';
import { Navigate } from 'react-router-dom';
import FindPasswordByEmailForm from './FindPasswordByEmailForm';
import FindPasswordByEmailVerification from './FindPasswordByEmailVerification';

const FindPasswordByEmail = (): JSX.Element => {
  const [formData, setFormData] = useState<FindPasswordByEmailInput>();
  const [token, setToken] = useState<string>();

  if (token) {
    return <Navigate to={`${NAVIGATION_ROUTES.CHANGE_PASSWORD}?${PARAM_ACCESS_TOKEN}=${encodeURIComponent(token)}`} />;
  }

  if (formData) {
    return <FindPasswordByEmailVerification onSuccess={setToken} formData={formData} />;
  }

  return <FindPasswordByEmailForm onSubmit={setFormData} />;
};

export default FindPasswordByEmail;
