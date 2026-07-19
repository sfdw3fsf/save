import Button from '@/components/core/button/Button';
import Card from '@/components/core/card/Card';
import Divider from '@/components/core/divider/Divider';
import DoorOpenIcon from '@/components/icons/DoorOpenIcon';
import KeyIcon from '@/components/icons/KeyIcon';
import { ROUTES } from '@/router/routes.constant';
import { toLocaleDateString } from '@/utils/helpers/datetime.helper';
import { Trans, useTranslation } from 'react-i18next';
import { useNavigate } from 'react-router-dom';

type FindIdConfirmationProps = {
  data: { username: string; joinDate: Date; email?: string; phone?: string; name?: string };
  isByEmail?: boolean;
  onConfirm?: () => void;
};

const FindIdConfirmation = ({ data, isByEmail = false, onConfirm }: FindIdConfirmationProps): JSX.Element => {
  const { t: tCommon } = useTranslation('common');

  const navigate = useNavigate();

  return (
    <Card>
      <div className='flex w-full flex-col items-center justify-center gap-6 pb-[47px] max-md:pb-[15px]'>
        <div className='flex flex-col w-full gap-6 items-center justify-center'>
          <div className='flex flex-col w-full gap-3 text-center'>
            <div>
              <Trans
                i18nKey={
                  isByEmail ? 'find_id_password.this_id_matches_the_email' : 'find_id_password.this_id_matches_the_phone_number'
                }
                ns='common'
                values={{ userName: data.name, userEmail: data.email, userPhoneNumber: data.phone }}
                components={{ span: <span className='text-md font-bold text-primary-500' />, br: <br /> }}
              />
            </div>
          </div>

          <Divider />

          <div className='flex w-full min-h-[149px] flex-col items-center justify-center p-4 gap-2 text-center text-lg font-normal rounded-lg border border-slate-200'>
            <p>
              {tCommon('find_id_password.id')}: {data.username}
            </p>
            <p>
              {tCommon('find_id_password.registration_date')}: {toLocaleDateString(data.joinDate)}
            </p>
          </div>
        </div>
      </div>

      <div className='flex w-full flex-col items-center justify-center gap-6'>
        <Button className='w-full' variant='primary' onClick={onConfirm}>
          {tCommon('find_id_password.confirm')}
        </Button>

        <div className='flex w-full items-center justify-center gap-4'>
          <Button
            className='min-h-5'
            labelContStyle='gap-1'
            keepLeftIconStyle
            leftIcon={<DoorOpenIcon className='h-5 w-5 text-slate-800' />}
            variant='link'
            onClick={() => navigate(ROUTES.LOGIN)}>
            {tCommon('find_id_password.log_in')}
          </Button>
          <Button
            className='min-h-5'
            labelContStyle='gap-1'
            keepLeftIconStyle
            leftIcon={<KeyIcon className='h-5 w-5 text-slate-800' />}
            variant='link'
            onClick={() => navigate(ROUTES.FIND.PASSWORD)}>
            {tCommon('find_id_password.find_password')}
          </Button>
        </div>
      </div>
    </Card>
  );
};

export default FindIdConfirmation;
