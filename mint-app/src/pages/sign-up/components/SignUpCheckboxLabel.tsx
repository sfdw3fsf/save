import Badge from '@/components/core/badge/Badge';
import Button from '@/components/core/button/Button';
import { useTranslation } from 'react-i18next';

type SignUpCheckboxLabelProps = { label: string; onClick: () => void };

const SignUpCheckboxLabel = ({ label, onClick }: SignUpCheckboxLabelProps): JSX.Element => {
  const { t: tCommon } = useTranslation('common');
  return (
    <div className='flex w-full items-center justify-start gap-3 py-1'>
      <Badge label={tCommon('component.required')} color='orange' className='h-[22px]' />
      <div className='flex items-center justify-start gap-2'>
        <span className='text-sm font-medium text-comp-text-default'>{label}</span>
        <Button className='min-h-5' variant='link' sizing='2sm' onClick={onClick}>
          [{tCommon('sign_up.view')}]
        </Button>
      </div>
    </div>
  );
};

export default SignUpCheckboxLabel;
