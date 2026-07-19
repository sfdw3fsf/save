import BackgroundImage from '@/assets/images/background-image-3.svg';
import ArrowRightIcon from '@/components/icons/ArrowRightIcon';
import LocationIcon from '@/components/icons/LocationIcon';
import MailIcon from '@/components/icons/MailIcon';
import XIcon from '@/components/icons/XIcon';
import { FORM_POSITION } from '@/pages/inquiry/InquiryPage';
import { ROUTES } from '@/router/routes.constant';
import { useAuthStore } from '@/store/auth.store';
import { PARAM_SCROLL_POSITION } from '@/utils/constants/data.constant';
import { USER_ROLE } from '@/utils/enums/user/user-role.enum';
import { ICONS } from '@/utils/theme';
import i18next from 'i18next';
import { memo, startTransition, useMemo, useState } from 'react';
import { Trans, useTranslation } from 'react-i18next';
import { Link, useNavigate } from 'react-router-dom';
import Button from '../button/Button';
import ModalDialog from '../modal-dialog/ModalDialog';

const Footer = () => {
  const { t } = useTranslation('common');
  const [openDialogModal, setOpenDialogModal] = useState(false);
  const [modalFilePath, setModalFilePath] = useState(''); // New state for file path

  const navigate = useNavigate();

  const { userInfo } = useAuthStore();

  const linkProjectManagement = useMemo(() => {
    switch (userInfo?.userRole) {
      case USER_ROLE.MEMBER:
        return ROUTES.PROJECT_MANAGEMENT.INDIVIDUAL;
      case USER_ROLE.EXPERT:
        return ROUTES.PROJECT_MANAGEMENT.EXPERT;
      default:
        return ROUTES.PROJECT_MANAGEMENT.INDIVIDUAL;
    }
  }, [userInfo?.userRole]);

  return (
    <div className='flex flex-col bg-primary-950 text-white md:pt-16 md:px-[118px] p-4 md:pb-12'>
      <div className='relative flex flex-col md:gap-[72px] gap-8'>
        <div
          className={
            'flex items-center justify-between w-full h-fit lg:px-16 lg:pb-[88px] lg:pt-28 p-4 bg-cover bg-no-repeat rounded-[32px]'
          }
          style={{ backgroundImage: `url(${BackgroundImage})` }}>
          <div className='flex flex-col w-full lg:gap-10 gap-5 items-center md:items-start'>
            <p className='whitespace-pre-line xl:text-5xl lg:text-3xl text-2xl font-bold text-slate-950'>{t('footer.main')}</p>
            <Button
              className='w-60 text-primary-1000 font-semibold md:mx-0 mx-auto'
              onClick={() => navigate(`${ROUTES.INQUIRY.self}?${PARAM_SCROLL_POSITION}=${FORM_POSITION}`)}>
              {t('footer.main_button')}
            </Button>
          </div>
          <div>
            <img src={ICONS.LOGO.MD} className='w-44 lg:w-72 xl:w-96 hidden sm:block' />
            <p className='text-slate-800 font-bold text-3xl hidden sm:block'>Connecting professionals, Delivering expertise</p>
          </div>
        </div>
        <div>
          <div className='flex pb-6 flex-col xl:flex-row items-start justify-between lg:gap-[60px] gap-6 md:px-8 '>
            <div>
              <img src={ICONS.LOGO.SM} className='mb-4' />
              <div className='flex flex-col gap-3'>
                <div className='flex gap-2 items-center'>
                  <LocationIcon className='text-white w-5 h-8' />
                  <p
                    className='text-sm break-keep lg:whitespace-nowrap'
                    style={{ fontSize: 'clamp(0.875rem, 0.18vw + 0.6rem, 1.25rem)' }}>
                    {t('footer.address').replace(/ /g, '\u00A0')}
                  </p>
                </div>
                <div className='flex gap-2 items-center'>
                  <MailIcon className='text-white' />
                  <p className='md:text-lg text-sm'>general@mintmd.bio</p>
                </div>
              </div>
            </div>
            <div className='flex items-center justify-between w-1/3 gap-[60px]'>
              <div className='flex flex-col gap-4 w-full'>
                <Link to={ROUTES.PROJECT_REQUEST.INDIVIDUAL.REQUEST} className='text-nowrap md:text-base'>
                  {t('footer.footer_advisory')}
                </Link>
                <Link to={linkProjectManagement} className='text-nowrap md:text-base'>
                  {t('footer.footer_project')}
                </Link>
                <Link to={ROUTES.MY_PAGE.self} className='text-nowrap md:text-base'>
                  {t('footer.my_page')}
                </Link>
                <Link to={ROUTES.INQUIRY.self} className='text-nowrap md:text-base'>
                  {t('footer.about')}
                </Link>
              </div>
            </div>
            <div className='flex flex-col gap-6'>
              <Trans
                i18nKey='footer.title'
                ns='common'
                className='text-3xl'
                components={{
                  span: <span className='md:text-3xl text-xl text-primary-300' />,
                  p: <span className='md:text-3xl text-xl text-white whitespace-pre-line text-nowrap'></span>,
                }}
              />
              <div className='flex flex-col items-start gap-4'>
                <Link to={'https://www.mintmd.bio/'} target='_blank' className='md:text-sm text-xs flex'>
                  {t('footer.title_button')}
                  <ArrowRightIcon className='text-white ml-[6px]' />
                </Link>
                <Link
                  to={`${i18next.language === 'en' ? 'https://mintventures.bio/_ENG/' : 'https://mintventures.bio/'}`}
                  className='md:text-sm text-xs flex'
                  target='_blank'>
                  {t('footer.title_button2')}
                  <ArrowRightIcon className='text-white ml-[6px]' />
                </Link>
              </div>
            </div>
          </div>
          <div className='flex flex-col md:flex-row md:items-center justify-between md:mx-8 pb-6 pt-8 border-t-[1px] gap-4 pr-6'>
            <p className='md:text-sm text-xs'>{t('footer.copy_right')}</p>
            <p className='md:text-sm text-xs'>{t('footer.business_doc')}</p>
            <div className='flex items-center gap-4'>
              <button
                className='md:text-sm text-xs'
                onClick={() => {
                  setModalFilePath(
                    i18next.language == 'en'
                      ? 'Terms & Conditions_Dr. Wiz Client_ENG_v1.htm'
                      : 'Dr. Wiz 고객 약관-converted.html',
                  );
                  startTransition(() => {
                    setOpenDialogModal(true);
                  });
                }}>
                {t('footer.policy')}
              </button>
              <button
                className='md:text-sm text-xs'
                onClick={() => {
                  setModalFilePath(
                    i18next.language == 'en'
                      ? 'Terms & Conditions_Dr. Wiz Expert_ENG_v1.htm'
                      : 'Dr. Wiz Expert 약관_2024 2H_최종-converted.html',
                  );
                  startTransition(() => {
                    setOpenDialogModal(true);
                  });
                }}>
                {t('footer.condition')}
              </button>
              <button
                className='md:text-sm text-xs'
                onClick={() => {
                  setModalFilePath(
                    i18next.language == 'en'
                      ? 'MintMD Privacy Policy_ENG_v1-converted.html'
                      : 'MintMD Privacy Policy v2.1docx-converted.html',
                  );
                  startTransition(() => {
                    setOpenDialogModal(true);
                  });
                }}>
                {t('footer.privacy')}
              </button>
            </div>
          </div>
        </div>
      </div>
      <ModalDialog visible={openDialogModal} onClose={setOpenDialogModal}>
        <div className='p-3 flex-col gap-3 flex'>
          <div className='flex justify-end pr-3 '>
            <button
              onClick={() => {
                setOpenDialogModal(false);
              }}
              className='items-end'>
              <XIcon />
            </button>
          </div>
          <div className='scrollbar-none'>
            <embed
              type='text/html'
              className='w-[calc(43vw+160px)] h-[56vh] max-w-unset'
              src={modalFilePath}
              title='client policy'></embed>
          </div>
        </div>
      </ModalDialog>
    </div>
  );
};

export default memo(Footer);
