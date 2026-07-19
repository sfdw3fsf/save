import CustomHamburgerIcon from '@/components/icons/CustomHamburgerIcon';
import { useBreakpoint } from '@/hooks/useBreakpoint';
import { ROUTES } from '@/router/routes.constant';
import { useAuthStore } from '@/store/auth.store';
import { USER_ROLE } from '@/utils/enums/user/user-role.enum';
import { IMAGES } from '@/utils/theme';
import { useEffect, useMemo, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { Link } from 'react-router-dom';
import { twMerge } from 'tailwind-merge';
import Button from '../button/Button';
import Divider from '../divider/Divider';
import IconButton from '../icon-button/IconButton';
import CustomLogout from './CustomLogout';
import LanguageDropdown from './LanguageDropdown';
import NavbarLink, { NavbarLinkProps } from './NavbarLink';
import NavbarMenu from './NavbarMenu';

export type NavbarVariant = 'with-sign-up-btn' | 'with-login-btn' | 'with-links';

type NavbarProps = {
  variant?: NavbarVariant;
};

const Navbar = ({ variant }: NavbarProps): JSX.Element => {
  const { t: tCommon } = useTranslation('common');

  const { hasMinWidthLg: isWeb } = useBreakpoint('lg');

  const { accessToken, userInfo } = useAuthStore();
  const isLoggedIn = useMemo<boolean>(() => Boolean(accessToken && userInfo), [accessToken, userInfo]);

  const [openMenu, setOpenMenu] = useState<boolean>(false);

  useEffect(() => {
    setOpenMenu((prev) => prev && !isWeb);
  }, [isWeb]);

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

  const authLinks = useMemo<NavbarLinkProps[]>(() => {
    const shared = [
      { children: tCommon('navbar.project_management'), to: linkProjectManagement },
      { children: tCommon('navbar.contact_us'), to: ROUTES.INQUIRY.self },
    ];
    if (userInfo?.userRole !== USER_ROLE.EXPERT) {
      return [{ children: tCommon('navbar.request_consultation'), to: ROUTES.PROJECT_REQUEST.INDIVIDUAL.REQUEST }, ...shared];
    }
    return shared;
  }, [linkProjectManagement, tCommon, userInfo?.userRole]);

  const publicLinks = useMemo<NavbarLinkProps[]>(
    () => [
      { children: tCommon('navbar.news'), to: ROUTES.NEWS.self },
      { children: tCommon('navbar.notices'), to: ROUTES.ANNOUNCEMENT.self },
      { children: tCommon('navbar.faq'), to: ROUTES.FAQ },
    ],
    [tCommon],
  );

  useEffect(() => {
    setOpenMenu(false);
  }, [window.location.href]);

  return (
    <>
      <div
        className={twMerge(
          'w-full h-navbar fixed top-0 left-0 flex items-center justify-center px-20 max-lg:px-4 z-[20]',
          variant === 'with-links' && 'bg-white',
        )}>
        <div className='w-full h-full flex items-center justify-center px-8 max-lg:px-0 gap-10'>
          <div className='flex items-center justify-start'>
            <Link to={ROUTES.HOME}>
              <div className='h-[45px] w-[61.1px] shrink-0'>
                <img src={IMAGES.APP.LOGO} alt='logo' className='w-full h-full'></img>
              </div>
            </Link>
          </div>

          <div className='w-full'>
            {variant === 'with-links' && (
              <div className='w-full flex items-center justify-start text-left gap-8 max-lg:hidden'>
                {authLinks.map(({ children, to }, index) => (
                  <NavbarLink key={`web-${index}`} to={to}>
                    {children}
                  </NavbarLink>
                ))}
              </div>
            )}
          </div>

          <div className={twMerge('flex items-center justify-end gap-3', variant === 'with-links' && 'max-lg:hidden')}>
            {variant === 'with-links' &&
              (isLoggedIn ? (
                <>
                  <Link to={ROUTES.MY_PAGE.self}>
                    <Button>{tCommon('navbar.my_page')}</Button>
                  </Link>
                </>
              ) : (
                <>
                  <Link to={ROUTES.SIGN_UP.self}>
                    <Button>{tCommon('navbar.sign_up')}</Button>
                  </Link>
                  <Link to={ROUTES.LOGIN}>
                    <Button className='w-[140px]' variant='primary'>
                      {tCommon('navbar.login')}
                    </Button>
                  </Link>
                </>
              ))}

            {!isLoggedIn && (
              <>
                {variant === 'with-login-btn' && (
                  <Link to={ROUTES.LOGIN}>
                    <Button>{tCommon('navbar.login')}</Button>
                  </Link>
                )}

                {variant === 'with-sign-up-btn' && (
                  <Link to={ROUTES.SIGN_UP.self}>
                    <Button>{tCommon('navbar.sign_up')}</Button>
                  </Link>
                )}
              </>
            )}

            <LanguageDropdown />

            {isLoggedIn && <CustomLogout />}

            {variant === 'with-links' && (
              <NavbarMenu items={publicLinks.map(({ children, to }) => ({ label: children, value: to }))} />
            )}
          </div>

          {variant === 'with-links' && (
            <div className='flex items-center justify-end lg:hidden'>
              <IconButton
                onClick={() => setOpenMenu((prev) => !prev)}
                variant='greyGhost'
                icon={<CustomHamburgerIcon open={openMenu} />}
                keepIconStyle
              />
            </div>
          )}
        </div>

        {variant === 'with-links' && openMenu && (
          <div className='fixed top-navbar scrollbar-none z-[15] w-screen h-[calc(100vh-72px)] bg-comp-fill-neutral-reverse bg-opacity-20 overflow-y-auto'>
            <div className='flex w-full flex-col bg-white'>
              <div className='flex w-full flex-col py-6 gap-2'>
                {[...authLinks, ...publicLinks].map(({ children, to }, index) => (
                  <NavbarLink className='w-full px-4 py-3' key={`mob-${index}`} to={to} onClick={() => setOpenMenu(false)}>
                    {children}
                  </NavbarLink>
                ))}
              </div>

              <Divider />

              <div className='flex w-full flex-col py-6 px-4 gap-3'>
                <LanguageDropdown className='w-full' align='center' />

                {isLoggedIn ? (
                  <>
                    <Link to={ROUTES.MY_PAGE.self}>
                      <Button className='w-full'>{tCommon('navbar.my_page')}</Button>
                    </Link>
                    <CustomLogout className='w-full' />
                  </>
                ) : (
                  <>
                    <Link to={ROUTES.SIGN_UP.self}>
                      <Button className='w-full'>{tCommon('navbar.sign_up')}</Button>
                    </Link>
                    <Link to={ROUTES.LOGIN}>
                      <Button className='w-full' variant='primary'>
                        {tCommon('navbar.login')}
                      </Button>
                    </Link>
                  </>
                )}
              </div>
            </div>
          </div>
        )}
      </div>
    </>
  );
};

export default Navbar;
