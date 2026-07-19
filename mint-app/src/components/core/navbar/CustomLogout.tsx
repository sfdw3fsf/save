import SignOutIcon from '@/components/icons/SignOutIcon';
import useLogout from '@/hooks/useLogout';
import { useTranslation } from 'react-i18next';
import Button from '../button/Button';

type CustomLogoutProps = {
  className?: string;
};

const CustomLogout = ({ className }: CustomLogoutProps): JSX.Element => {
  const { t: tCommon } = useTranslation('common');
  const { handleLogout, isLoading } = useLogout();

  return (
    <Button labelContStyle='gap-1' isLoading={isLoading} onClick={handleLogout} leftIcon={<SignOutIcon />} className={className}>
      {tCommon('navbar.logout')}
    </Button>
  );
};

export default CustomLogout;
