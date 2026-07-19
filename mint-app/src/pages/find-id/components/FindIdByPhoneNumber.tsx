import { FindIdConfirmOtpDTO } from '@/data/account-finder/dto/find-id.dto';
import FindIdConfirmation from '@/pages/find-id/components/FindIdConfirmation';
import { ROUTES } from '@/router/routes.constant';
import { FindIdByPhoneNumberInput } from '@/utils/schemas/find.schema';
import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import FindIdByPhoneNumberForm from './FindIdByPhoneNumberForm';
import FindIdByPhoneNumberVerification from './FindIdByPhoneNumberVerification';

const FindIdByPhoneNumber = (): JSX.Element => {
  const navigate = useNavigate();

  const [formData, setFormData] = useState<FindIdByPhoneNumberInput>();
  const [verifyData, setVerifyData] = useState<FindIdConfirmOtpDTO>();

  if (verifyData) {
    return (
      <FindIdConfirmation
        data={{ username: verifyData.username, joinDate: verifyData.joinDate, phone: formData?.phone, name: formData?.name }}
        onConfirm={() => navigate(ROUTES.LOGIN)}
      />
    );
  }

  if (formData) {
    return <FindIdByPhoneNumberVerification onSuccess={setVerifyData} formData={formData} />;
  }

  return <FindIdByPhoneNumberForm onSubmit={setFormData} />;
};

export default FindIdByPhoneNumber;
