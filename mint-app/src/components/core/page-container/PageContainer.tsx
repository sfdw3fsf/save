import { IMAGES } from '@/utils/theme';
import { ReactNode } from 'react';
import { Helmet, HelmetProvider } from 'react-helmet-async';
import { twJoin, twMerge } from 'tailwind-merge';
import Footer from '../footer/Footer';

type PageContainerProps = {
  title: string;
  subtitle?: string;
  children?: ReactNode;
  withBackground?: boolean;
  className?: string;
  isFullScreen?: boolean;
  hiddenScroll?: boolean;
  showTitle?: boolean;
  withFooter?: boolean;
  hideNavbar?: boolean;
  containerStyle?: string;
  bodyClassName?: string;
};

const PageContainer = ({
  children,
  withBackground = false,
  title,
  subtitle,
  className,
  isFullScreen,
  hiddenScroll,
  hideNavbar = false,
  showTitle = false,
  withFooter = false,
  containerStyle,
  bodyClassName,
}: PageContainerProps): JSX.Element => {
  return (
    <>
      <HelmetProvider>
        <Helmet>
          <title>{title} | Dr. Wiz</title>
        </Helmet>
      </HelmetProvider>

      <div
        className={twMerge(
          'h-full w-full transition-all pt-navbar min-h-[calc(100vh-72px)]',
          hiddenScroll && 'overflow-y-auto scrollbar-none',
          containerStyle,
        )}>
        <div
          className={twMerge(
            'w-full h-full min-h-[calc(100vh-72px)]',
            withBackground && 'bg-top bg-no-repeat bg-[size:72%_auto] max-md:bg-[size:960px_auto]',
          )}
          style={{ backgroundImage: withBackground ? `url(${IMAGES.APP.BACKGROUND_IMAGE})` : undefined }}>
          {showTitle && (
            <div className='flex flex-col w-full items-center justify-center gap-3 py-12 px-20 text-center'>
              {!!subtitle && <p className='text-md font-normal text-comp-text-brand'>{subtitle}</p>}
              <h1 className='text-display-lg font-medium text-comp-text-default'>{title}</h1>
            </div>
          )}

          <div
            className={twMerge(
              !isFullScreen && 'pt-12 pb-24 px-20 max-lg:py-8 max-lg:px-4',
              withBackground && 'pt-14',
              bodyClassName,
            )}>
            <div className={twJoin(!isFullScreen && 'px-8 max-lg:px-0')}>
              <div className={twMerge('w-full flex flex-col items-center', className)}>{children}</div>
            </div>
          </div>

          {withFooter && <Footer />}
        </div>
      </div>
    </>
  );
};

export default PageContainer;
