import initTranslations from '@/i18n';
import HtmlLayout from '@/layout/HtmlLayout';
import AntDesignProvider from '@/provider/AntDesignProvider';
import ReactQueryProvider from '@/provider/ReactQueryProvider';
import TranslationsProvider from '@/provider/TranslationProvider';
import '@/styles/custom-antd.scss';
import '@/styles/form.scss';
import '@/styles/quill-snow.scss';
import { App as AntApp } from 'antd';
import { dir } from 'i18next';
import { ReactNode } from 'react';
import { ToastContainer } from 'react-toastify';

import AuthHandler from '@/components/AuthHandler';
import { i18nNameSpace } from '@/i18n/i18n.namespace';
import 'react-toastify/dist/ReactToastify.css';
import 'slick-carousel/slick/slick-theme.css';
import 'slick-carousel/slick/slick.css';
import './globals.css';

const RootLayout: React.FC<{
  children: ReactNode;
  params: { locale: string };
}> = async ({ children, params: { locale } }) => {
  const { resources } = await initTranslations(locale, i18nNameSpace);

  return (
    <HtmlLayout lang={locale} dir={dir(locale)}>
      <AntDesignProvider>
        <TranslationsProvider locale={locale} namespaces={i18nNameSpace} resources={resources}>
          <AntApp>
            <ReactQueryProvider>
              <AuthHandler>{children}</AuthHandler>
            </ReactQueryProvider>
          </AntApp>
        </TranslationsProvider>
      </AntDesignProvider>
      <ToastContainer />
    </HtmlLayout>
  );
};

export default RootLayout;
