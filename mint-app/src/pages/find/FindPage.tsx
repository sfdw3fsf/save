import PageContainer from '@/components/core/page-container/PageContainer';
import SelectBox from '@/components/core/select-box/SelectBox';
import IdentificationBadgeIcon from '@/components/icons/IdentificationBadgeIcon';
import KeyIcon from '@/components/icons/KeyIcon';
import { ROUTES } from '@/router/routes.constant';
import { useTranslation } from 'react-i18next';
import { useNavigate } from 'react-router-dom';

const FindPage = (): JSX.Element => {
  const { t: tCommon } = useTranslation('common');

  const navigate = useNavigate();

  return (
    <PageContainer withBackground title={tCommon('title.find_id_password')}>
      <div className='flex w-full max-w-[420px] flex-col items-center justify-center gap-3'>
        <div className='flex w-full flex-col items-center justify-center text-center'>
          <h2 className='text-slate-800 text-display-xs font-medium'>{tCommon('find_id_password.find_id_password')}</h2>
          <div className='text-slate-500 text-md font-normal'>{tCommon('find_id_password.lorem')}</div>
        </div>

        <div className='flex w-full flex-col items-center justify-center gap-3'>
          <SelectBox
            title={tCommon('find_id_password.find_id')}
            icon={<IdentificationBadgeIcon />}
            buttonProps={{
              children: tCommon('find_id_password.shortcut'),
              onClick: () => navigate(ROUTES.FIND.ID),
            }}
            description={tCommon('find_id_password.lorem')}
          />

          <SelectBox
            title={tCommon('find_id_password.find_password')}
            icon={<KeyIcon />}
            buttonProps={{
              children: tCommon('find_id_password.shortcut'),
              onClick: () => navigate(ROUTES.FIND.PASSWORD),
            }}
            description={tCommon('find_id_password.lorem')}
          />
        </div>
      </div>
    </PageContainer>
  );
};

export default FindPage;
