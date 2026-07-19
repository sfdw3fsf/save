import { useGlobalStore } from '@/data/global/global.store';
import useResize from '@/hook/useResize';
import { HEADER_MENUS, HeaderMenuType } from '@/utils/constant/header/header-menu.constant';
import { HEADER_SOCIALS } from '@/utils/constant/header/header-social.constant';
import { MY_ROUTE } from '@/utils/constant/route.constant';
import Link from 'next/link';
import { useParams, usePathname, useRouter } from 'next/navigation';
import { useEffect, useMemo, useState } from 'react';
import './header.css';
import HeaderNotice from './HeaderNotice';

const Header = () => {
  const pathname = usePathname();
  const params = useParams();
  const router = useRouter();
  const { isIntro } = useGlobalStore();
  const { isResponsive } = useResize();

  const [currentPath, setCurrentPath] = useState('');
  const [visibleSubMenu, setVisibleSubMenu] = useState<number | null>(null);
  const [isAtTop, setIsAtTop] = useState<boolean>(true);

  const firstPath = useMemo(() => `/${pathname.split('/')[1]}`, [pathname]);

  const isInHome = useMemo(() => pathname === '/', [pathname]);

  const isInFuneral = useMemo(() => pathname.startsWith('/funeral') && pathname !== '/funeral/transportation', [pathname]);

  const isBorder = useMemo(() => pathname.startsWith('/reservation') === isAtTop, [pathname, isAtTop]);

  const handleScrollPerFuneralPage = (subMenu: Omit<HeaderMenuType, 'subMenus' | 'group'>) => {
    const funeralSubMenu = subMenu.link.match(/id=([^&]*)/)?.[1];
    if (!isInFuneral) {
      router.push(subMenu.link);
    }
    if (funeralSubMenu) {
      setCurrentPath(subMenu.link);
      const element = document.getElementById(funeralSubMenu);
      const top = isResponsive ? 8 : 80;
      if (element) {
        window.scrollBy({
          top: element.offsetTop - window.scrollY + top,
          behavior: 'smooth',
        });
      }
    } else {
      router.push(subMenu.link);
    }
  };

  useEffect(() => {
    const handleScroll = () => {
      if (window.scrollY > 0) setIsAtTop(false);
      else setIsAtTop(true);
    };

    window.addEventListener('scroll', handleScroll);

    return () => window.removeEventListener('scroll', handleScroll);
  }, []);

  useEffect(() => {
    const currentPath = pathname + window.location.hash;
    setCurrentPath(currentPath);

    const activeMenuIndex = HEADER_MENUS.findIndex((menu) => pathname.includes(menu.group));
    setVisibleSubMenu(activeMenuIndex);
  }, [params, pathname]);

  return (
    <>
      {isInHome && <HeaderNotice />}
      <header className={`header opacity-95 lg:opacity-100 ${isIntro ? '' : 'not-intro'} ${!isInHome ? '!top-0' : ''}`}>
        <div className={`inner flex items-center lmt maxw ${isIntro ? '' : 'not-intro'}`}>
          <a href='/' className='header-logo'>
            <img className='logo-green h-[40px]' src='/images/logo.svg' alt='logo' />
            <img className='logo-white h-[40px]' src='/images/logo-white.svg' alt='logo' />
          </a>
          <Link href={MY_ROUTE.RESERVATION.HOME} className={`reserve-btn`}>
            장례 예약
          </Link>
          <div
            className={`header-menu-container ${isBorder && 'border-b border-light-grey lg:border-none'}  ${isIntro ? '' : 'not-intro'} ${isInHome ? '!top-[84px] lg:!top-[50px]' : ''}`}>
            <ul className='header-menu-list'>
              {HEADER_MENUS.map((menu, index) => (
                <li
                  key={menu.link}
                  className='header-menu-item'
                  onMouseEnter={() => setVisibleSubMenu(index)}
                  onMouseLeave={() => setVisibleSubMenu(null)}>
                  <Link href={menu.link} className={pathname.includes(menu.group) ? 'active' : ''}>
                    {menu.title}
                  </Link>
                  <ul
                    className={`header-sub-menu-list ${isInHome ? 'top-[90px]' : 'top-[60px]'} 
                      ${(firstPath === menu.group && isResponsive) || visibleSubMenu === index ? '!visible' : ''} ${isIntro && '!hidden'}`}>
                    {menu.subMenus.map((subMenu, key) => (
                      <li key={key} className={`header-sub-menu-item ${currentPath === subMenu.link ? 'active' : ''}`}>
                        <button onClick={() => handleScrollPerFuneralPage(subMenu)}>{subMenu.title}</button>
                      </li>
                    ))}
                  </ul>
                </li>
              ))}
              <li className='header-menu-item reserve-item'>
                <Link
                  href={MY_ROUTE.RESERVATION.HOME}
                  className={`btn-header-reservation ${pathname.startsWith(MY_ROUTE.RESERVATION.HOME) ? 'active' : ''}`}>
                  장례 예약
                </Link>
              </li>
            </ul>
          </div>
          <ul className='header-social-list'>
            {HEADER_SOCIALS.map((social, index) => (
              <li key={index}>
                <a href={social.link} target='_blank' rel='noreferrer'>
                  <img className='sns-green max-w-[35px] max-h-[23px]' src={social.img} alt='' />
                  <img className='sns-white max-w-[35px] max-h-[23px]' src={social.imgWhite} alt='' />
                </a>
              </li>
            ))}
          </ul>
        </div>
      </header>
    </>
  );
};

export default Header;
