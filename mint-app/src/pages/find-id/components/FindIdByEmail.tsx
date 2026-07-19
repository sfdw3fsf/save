import { FindIdConfirmOtpDTO } from '@/data/account-finder/dto/find-id.dto';
import FindIdConfirmation from '@/pages/find-id/components/FindIdConfirmation';
import { ROUTES } from '@/router/routes.constant';
import { FindIdByEmailInput } from '@/utils/schemas/find.schema';
import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import FindIdByEmailForm from './FindIdByEmailForm';
import FindIdByEmailVerification from './FindIdByEmailVerification';

const FindIdByEmail = (): JSX.Element => {
  const navigate = useNavigate();

  const [formData, setFormData] = useState<FindIdByEmailInput>();
  const [verifyData, setVerifyData] = useState<FindIdConfirmOtpDTO>();

  if (verifyData) {
    return (
      <FindIdConfirmation
        isByEmail
        data={{ username: verifyData.username, joinDate: verifyData.joinDate, email: formData?.email, name: formData?.name }}
        onConfirm={() => navigate(ROUTES.LOGIN)}
      />
    );
  }

  if (formData) {
    return <FindIdByEmailVerification onSuccess={setVerifyData} formData={formData} />;
  }

  return <FindIdByEmailForm onSubmit={setFormData} />;
};

export default FindIdByEmail;
