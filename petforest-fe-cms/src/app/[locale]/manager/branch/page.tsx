'use client';

import { createManagerBranchApi, managerBranchKeys } from '@/store/manager/branch/manager-branch.api';
import { handleError } from '@/utils/helpers/notification-error.helper';

import AddButton from '@/components/common/AddButton';
import PageTitle from '@/components/common/page/PageTitle';
import { useMenuPermission } from '@/hook/auth/useMenuPermission';
import useRouterHandler from '@/hook/useRouterHandler';
import { COLORS } from '@/theme';
import { MANAGER_BRANCH_CONTENT } from '@/utils/enums/manager/manager.enum';
import { MenuKeys } from '@/utils/enums/side-menu-type';
import { handleSuccess } from '@/utils/helpers/notification-success.helper';
import { ArrowLeftOutlined } from '@ant-design/icons';
import { useMutation, useQueryClient } from '@tanstack/react-query';
import { useSearchParams } from 'next/navigation';
import { useMemo } from 'react';
import { useTranslation } from 'react-i18next';
import BranchList from './components/BranchList';
import DeletedBranchList from './components/DeletedBranchList';

const page = () => {
  const { t: tBranch } = useTranslation('manager_branch');

  const { handleURLPush } = useRouterHandler();
  const searchParams = useSearchParams();
  const queryClient = useQueryClient();
  const { canEdit } = useMenuPermission(MenuKeys.MANAGER_BRANCH);

  const isDeletedList = useMemo(
    () => (searchParams.get('content') as MANAGER_BRANCH_CONTENT) === MANAGER_BRANCH_CONTENT.DELETE_LIST,
    [searchParams],
  );

  const { mutate: mutateCreateBranch } = useMutation({
    mutationFn: () => createManagerBranchApi(),
    onSuccess: () => {
      handleSuccess('create');
      queryClient.invalidateQueries({ queryKey: managerBranchKeys.lists() });
    },
    onError: handleError,
  });

  const onCreateBranch = () => {
    mutateCreateBranch();
  };

  return isDeletedList ? (
    <>
      <PageTitle
        title={tBranch('deleted_branch')}
        prefix={
          <div
            className='p-[9px] cursor-pointer'
            onClick={() => handleURLPush('content', MANAGER_BRANCH_CONTENT.BRANCH_LIST, false)}>
            <ArrowLeftOutlined style={{ fontSize: '16px', color: COLORS.neutral[100] }} />
          </div>
        }
      />
      <DeletedBranchList />
    </>
  ) : (
    <>
      <PageTitle title={tBranch('branch')} actions={canEdit && <AddButton onClick={onCreateBranch} />} />
      <BranchList />
    </>
  );
};

export default page;
