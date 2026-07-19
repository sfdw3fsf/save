import Modal from '@/components/core/modal/Modal';
import { useState } from 'react';
import Step1_CurrentPasswordForm from './Step1_CurrentPasswordForm';
import Step2_NewEmailForm from './Step2_NewEmailForm';
import Step3_EnterOtpForm from './Step3_EnterOtpForm';

type ChangePhoneNumberProps = {
  open: boolean;
  onClose: () => void;
  setCloseEmail?: any;
  queryKey?: string;
  oldEmail?: string;
};

const ChangeEmailModal = ({ open, onClose, setCloseEmail, queryKey, oldEmail }: ChangePhoneNumberProps): JSX.Element => {
  const [currentStep, setCurrentStep] = useState<number>(1);
  const [email, setEmail] = useState<string>('');
  const handleClose = () => {
    console.log('handleClose....');
    onClose(), setCurrentStep(1);
  };
  return (
    <Modal
      open={open}
      onClose={handleClose}
      body={
        (currentStep === 1 && <Step1_CurrentPasswordForm onNext={setCurrentStep} onClose={onClose} />) ||
        (currentStep === 2 && <Step2_NewEmailForm onNext={setCurrentStep} setEmail={setEmail} onClose={onClose} />) ||
        (currentStep === 3 && (
          <Step3_EnterOtpForm
            onNext={setCurrentStep}
            email={oldEmail}
            newEmail={email}
            onClose={onClose}
            setCloseEmail={setCloseEmail}
            queryKey={queryKey}
          />
        ))
      }
    />
  );
};
export default ChangeEmailModal;
