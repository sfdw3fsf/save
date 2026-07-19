import PageContainer from '@/components/core/page-container/PageContainer';
import SelectBox from '@/components/core/select-box/SelectBox';
import UserCircleCheckIcon from '@/components/icons/UserCircleCheckIcon';
import UserIcon from '@/components/icons/UserIcon';
import { ROUTES } from '@/router/routes.constant';
import { useTranslation } from 'react-i18next';
import { useNavigate } from 'react-router-dom';

const SignUpPage = (): JSX.Element => {
  const { t: tCommon } = useTranslation('common');

  const navigate = useNavigate();

  return (
    <PageContainer withBackground title={tCommon('title.sign_up')}>
      <div className='flex w-full max-w-[420px] flex-col items-center justify-center gap-3'>
        <div className='flex w-full flex-col items-center justify-center text-center'>
          <h2 className='text-slate-800 text-display-xs font-medium'>{tCommon('sign_up.membership_registration')}</h2>
        </div>

        <div className='flex w-full flex-col items-center justify-center gap-3'>
          <SelectBox
            className='shadow-elevation-lg'
            title={tCommon('sign_up.individual_member')}
            icon={<UserIcon />}
            buttonProps={{
              children: tCommon('sign_up.shortcut'),
              onClick: () => navigate(ROUTES.SIGN_UP.INDIVIDUAL.self),
            }}
            description={tCommon('sign_up.wish_to_request_advice')}
          />

          <SelectBox
            className='shadow-elevation-lg'
            title={tCommon('sign_up.expert_member')}
            icon={<UserCircleCheckIcon />}
            buttonProps={{
              children: tCommon('sign_up.shortcut'),
              onClick: () => navigate(ROUTES.SIGN_UP.EXPERT.self),
            }}
            description={tCommon('sign_up.wish_to_provide_advice')}
          />
        </div>
      </div>
    </PageContainer>
  );
};

export default SignUpPage;
