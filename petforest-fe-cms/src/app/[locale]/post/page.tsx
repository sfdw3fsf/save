'use client';
import AddButton from '@/components/common/AddButton';
import PageTitle from '@/components/common/page/PageTitle';
import { POST_ENUM_TABS } from '@/utils/enums/post/post.enum';
import { Tabs } from 'antd';
import { TabsProps } from 'antd/lib';
import { useSearchParams } from 'next/navigation';

import { useMenuPermission } from '@/hook/auth/useMenuPermission';
import useRouterHandler from '@/hook/useRouterHandler';
import { createPopupApi, createPostApi, createReviewApi, popupKeys, postKeys, reviewKeys } from '@/store/post/post.api';
import { popupreateToReq, reviewCreateToReq } from '@/store/post/post.service';
import { MenuKeys } from '@/utils/enums/side-menu-type';
import { handleError } from '@/utils/helpers/notification-error.helper';
import { handleSuccess } from '@/utils/helpers/notification-success.helper';
import { useMutation, useQueryClient } from '@tanstack/react-query';
import { useMemo, useState } from 'react';
import { useTranslation } from 'react-i18next';
import Popup from './tabs/Popup';
import Post from './tabs/Post';
import Review from './tabs/Review';

const page = () => {
  const { t: tPost } = useTranslation('post');
  const { t: tMenu } = useTranslation('menu');
  const { canEdit } = useMenuPermission(MenuKeys.POST);

  const searchParams = useSearchParams();
  const { handleURLPush } = useRouterHandler();
  const queryClient = useQueryClient();

  const [tab, setTab] = useState<POST_ENUM_TABS>((searchParams.get('tab') as POST_ENUM_TABS) || POST_ENUM_TABS.POST);

  const funeralSuppliesTabs: TabsProps['items'] = useMemo(() => {
    return [
      {
        key: POST_ENUM_TABS.POST,
        label: tPost('tabs.post'),
        children: <Post />,
      },
      {
        key: POST_ENUM_TABS.REVIEW,
        label: tPost('tabs.review'),
        children: <Review />,
      },
      {
        key: POST_ENUM_TABS.POPUP,
        label: tPost('tabs.popup'),
        children: <Popup />,
      },
    ];
  }, [tPost]);

  const { mutate: mutateCreatePost } = useMutation({
    mutationFn: () => createPostApi(),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: postKeys.lists() });
      handleSuccess('create');
    },
    onError: handleError,
  });

  const { mutate: mutatetCreateReview } = useMutation({
    mutationFn: () => createReviewApi(reviewCreateToReq()),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: reviewKeys.lists() });
      handleSuccess('create');
    },
    onError: handleError,
  });

  const { mutate: mutatetCreatePopup } = useMutation({
    mutationFn: () => createPopupApi(popupreateToReq()),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: popupKeys.lists() });
      handleSuccess('create');
    },
    onError: handleError,
  });

  const onCreate = () => {
    if (tab === POST_ENUM_TABS.POST) {
      mutateCreatePost();
    } else if (tab === POST_ENUM_TABS.REVIEW) {
      mutatetCreateReview();
    } else if (tab === POST_ENUM_TABS.POPUP) {
      mutatetCreatePopup();
    }
  };

  const handleChangeTab = (value: POST_ENUM_TABS) => {
    setTab(value);
    handleURLPush('tab', value, false);
  };

  return (
    <div>
      <PageTitle title={tMenu('post')} actions={canEdit && <AddButton onClick={onCreate} />} />

      <div className='mt-4'>
        <Tabs
          destroyInactiveTabPane
          items={funeralSuppliesTabs}
          defaultActiveKey={POST_ENUM_TABS.POST}
          activeKey={tab}
          onChange={(value) => handleChangeTab(value as POST_ENUM_TABS)}
        />
      </div>
    </div>
  );
};

export default page;
