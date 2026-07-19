import PageContainer from '@/components/core/page-container/PageContainer';
import { useAuthStore } from '@/store/auth.store';
import { ReactNode, useMemo } from 'react';
import { useTranslation } from 'react-i18next';
import { twMerge } from 'tailwind-merge';
import { getMyPageTabItems } from '../helper/my-page.helper';
import { MyPageTabItemProps } from './MyPageTabItem';
import MyPageTabList from './MyPageTabList';

type MyPageContainerProps = {
  title?: string;
  children?: ReactNode;
  description?: string;
  className?: string;
};

const MyPageContainer = ({ title, children, description, className }: MyPageContainerProps): JSX.Element => {
  const { t: tCommon } = useTranslation('common');

  const { userInfo } = useAuthStore();

  const tabItems = useMemo<MyPageTabItemProps[]>(
    () => getMyPageTabItems(tCommon, userInfo?.userRole),
    [tCommon, userInfo?.userRole],
  );

  return (
    <PageContainer title={tCommon('my-page.my_page')}>
      <div className='flex w-full gap-4 max-md:flex-col'>
        <MyPageTabList tabItems={tabItems} />

        <div
          className={twMerge(
            'flex flex-col w-full p-8 max-md:p-4 gap-8 max-md:gap-6 rounded-xl border border-comp-border-neutral-soft bg-white',
            className,
          )}>
          {(!!title || !!description) && (
            <div className='flex flex-col w-full gap-1 text-left pb-5 max-md:pb-4'>
              {!!title && <h2 className='text-display-xs font-medium text-comp-text-default max-md:text-xl'>{title}</h2>}
              {!!description && <p className='text-sm font-normal text-comp-text-secondary'>{description}</p>}
            </div>
          )}
          {children}
        </div>
      </div>
    </PageContainer>
  );
};

export default MyPageContainer;
