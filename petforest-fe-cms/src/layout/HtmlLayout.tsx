import localFont from 'next/font/local';
import Head from 'next/head';
import { ReactNode } from 'react';

export const pretendard = localFont({
  src: [
    {
      path: '../../public/fonts/pretendard-regular.woff',
      weight: '400',
      style: 'normal',
    },
    {
      path: '../../public/fonts/pretendard-medium.woff',
      weight: '500',
      style: 'normal',
    },
    {
      path: '../../public/fonts/pretendard-semibold.woff',
      weight: '600',
      style: 'normal',
    },
    {
      path: '../../public/fonts/pretendard-bold.woff',
      weight: '700',
      style: 'normal',
    },
  ],
});

export const eastSeaDokdo = localFont({
  src: [
    {
      path: '../../public/fonts/EastSeaDokdo-Regular.ttf',
      style: 'normal',
      weight: '400',
    },
  ],
  variable: '--font-eastSeaDokdo',
});

const HtmlLayout: React.FC<{
  children: ReactNode;
  lang: string;
  dir: string;
}> = ({ children, lang, dir }) => {
  return (
    <html lang={lang} dir={dir} className={`${pretendard.className} ${eastSeaDokdo.variable}`} suppressHydrationWarning>
      <Head>
        <script src='react-sortable-hoc/dist/react-sortable-hoc.umd.js'></script>
      </Head>
      <body suppressHydrationWarning>{children}</body>
    </html>
  );
};

export default HtmlLayout;
