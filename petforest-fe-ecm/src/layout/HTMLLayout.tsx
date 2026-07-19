'use client';
import '@/i18n';
import CharkaUIProvider from '@/provider/CharkaUIProvider';
import ReactQueryProvider from '@/provider/ReactQueryProvider';
import localFont from 'next/font/local';
import { usePathname } from 'next/navigation';

import PromotionPopup from '@/components/popup/PromotionPopup';
import AntDesignProvider from '@/provider/AntDesignProvider';
import ContactProvider from '@/provider/ContactProvider';
import { FULLSCREEEN_PATHS } from '@/utils/constant/obituary/obituary-path.constant';
import Head from 'next/head';
import { PropsWithChildren, Suspense, useMemo, useState } from 'react';
import Footer from './footer/Footer';
import Header from './header/Header';

export const pretendard = localFont({
  src: [
    {
      path: '../../public/fonts/Pretendard-Light.otf',
      weight: '300',
      style: 'normal',
    },
    {
      path: '../../public/fonts/Pretendard-Regular.woff',
      weight: '400',
      style: 'normal',
    },
    {
      path: '../../public/fonts/Pretendard-Medium.woff',
      weight: '500',
      style: 'normal',
    },
    {
      path: '../../public/fonts/Pretendard-SemiBold.woff',
      weight: '600',
      style: 'normal',
    },
    {
      path: '../../public/fonts/Pretendard-Bold.woff',
      weight: '700',
      style: 'normal',
    },
  ],
});

export const siena = localFont({
  src: [
    {
      path: '../../public/fonts/Fontsmith-FS-Siena.otf',
      style: 'normal',
      weight: '400',
    },
    {
      path: '../../public/fonts/Fontsmith-FS-Siena-Medium.otf',
      weight: '500',
      style: 'normal',
    },
    {
      path: '../../public/fonts/Fontsmith-FS-Siena-Light.otf',
      weight: '300',
      style: 'normal',
    },
  ],
  variable: '--font-siena',
});

const HTMLLayout = ({ children }: PropsWithChildren) => {
  const pathName = usePathname();
  const [isPromotionPopup, setIsPromotionPopup] = useState<boolean>(false);

  const isFullScreen = useMemo(() => {
    return Object.values(FULLSCREEEN_PATHS).some((path) => pathName.startsWith(path));
  }, [pathName]);

  const isDisplayContact = useMemo(() => {
    return !pathName.startsWith('/obituary');
  }, [pathName]);

  return (
    <html lang='en'>
      <Head>
        <meta name='viewport' content='width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no' />
      </Head>
      <body className={` ${siena.variable} ${pretendard.className}`}>
        {isDisplayContact && <ContactProvider />}
        <Suspense>
          <AntDesignProvider>
            <ReactQueryProvider>
              <CharkaUIProvider>
                {!isFullScreen && <Header />}
                {children}
                {!isFullScreen && <Footer />}
                <PromotionPopup
                  isOpen={isPromotionPopup}
                  onClose={() => {
                    setIsPromotionPopup(false);
                  }}
                />
              </CharkaUIProvider>
            </ReactQueryProvider>
          </AntDesignProvider>
        </Suspense>
      </body>
    </html>
  );
};

export default HTMLLayout;
