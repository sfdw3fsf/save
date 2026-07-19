import Button from '@/components/core/button/Button';
import Card from '@/components/core/card/Card';
import SuccessIcon from '@/components/icons/SuccessIcon';
import useLogout from '@/hooks/useLogout';
import { useTranslation } from 'react-i18next';

const ChangePasswordConfirmation = (): JSX.Element => {
  const { t: tCommon } = useTranslation('common');

  const { handleLogout, isLoading } = useLogout();

  return (
    <Card className='md:min-h-[580px] md:justify-between items-center text-center'>
      <div className='flex flex-col gap-10 items-center'>
        <h3 className='text-comp-text-default text-display-xs font-medium'>
          {tCommon('change_password.password_reset_complete')}
        </h3>

        <div className='w-[120px] h-[120px] shrink-0 flex items-center justify-center text-comp-icon-success'>
          <SuccessIcon className='w-full h-full text-inherit' />
        </div>

        <div className='max-md:text-sm text-md font-normal text-comp-text-secondary'>
          {tCommon('change_password.your_password_has_been_successfully_reset')}
        </div>
      </div>

      <div className='flex w-full flex-col gap-6 items-center justify-center'>
        <Button isLoading={isLoading} onClick={handleLogout} className='w-full' variant='primary'>
          {tCommon('change_password.log_in')}
        </Button>
      </div>
    </Card>
  );
};

export default ChangePasswordConfirmation;
