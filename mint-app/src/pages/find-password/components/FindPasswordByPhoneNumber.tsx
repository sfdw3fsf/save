import { NAVIGATION_ROUTES } from '@/router/routes.constant';
import { PARAM_ACCESS_TOKEN } from '@/utils/constants/data.constant';
import { FindPasswordByPhoneNumberInput } from '@/utils/schemas/find.schema';
import { useState } from 'react';
import { Navigate } from 'react-router-dom';
import FindPasswordByPhoneNumberForm from './FindPasswordByPhoneNumberForm';
import FindPasswordByPhoneNumberVerification from './FindPasswordByPhoneNumberVerification';

const FindPasswordByPhoneNumber = (): JSX.Element => {
  const [formData, setFormData] = useState<FindPasswordByPhoneNumberInput>();
  const [token, setToken] = useState<string>();

  if (token) {
    return <Navigate to={`${NAVIGATION_ROUTES.CHANGE_PASSWORD}?${PARAM_ACCESS_TOKEN}=${encodeURIComponent(token)}`} />;
  }

  if (formData) {
    return <FindPasswordByPhoneNumberVerification onSuccess={setToken} formData={formData} />;
  }

  return <FindPasswordByPhoneNumberForm onSubmit={setFormData} />;
};

export default FindPasswordByPhoneNumber;
