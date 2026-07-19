import Button from '@/components/core/button/Button';
import PageContainer from '@/components/core/page-container/PageContainer';
import NotFoundIcon from '@/components/icons/NotFoundIcon';
import { ROUTES } from '@/router/routes.constant';
import { useTranslation } from 'react-i18next';
import { useNavigate } from 'react-router-dom';

const NotFoundPage = (): JSX.Element => {
  const { t: tCommon } = useTranslation('common');
  const navigate = useNavigate();
  return (
    <PageContainer hideNavbar withBackground isFullScreen title={tCommon('title.not_found')}>
      <div className='flex justify-center items-center gap-10 flex-col h-screen -mt-navbar'>
        <div className='flex flex-col items-center  w-full gap-6'>
          <div>
            <NotFoundIcon className='md:w-[167px] md:h-[171px] w-[100px] h-[103px]' />
          </div>
          <div className='flex flex-col items-center gap-6 justify-center'>
            <h1 className='font-medium text-3xl md:text-5xl text-center text-slate-800'> {tCommon('notfound.title')}</h1>
            <p className='text base md:text-xl text-center text-slate-500'> {tCommon('notfound.description')}</p>
          </div>
        </div>
        <div>
          <Button onClick={() => navigate(ROUTES.HOME)} variant='primary' className='py-3 md:px-20 px-28 text-sm md:text-base'>
            {tCommon('notfound.buttonText')}
          </Button>
        </div>
      </div>
    </PageContainer>
  );
};

export default NotFoundPage;
