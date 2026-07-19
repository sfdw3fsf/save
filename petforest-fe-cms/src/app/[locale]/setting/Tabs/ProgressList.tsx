import ModalConfirm from '@/components/common/ConfirmModal';
import DeleteButton from '@/components/common/DeleteButton';
import CustomModal from '@/components/common/modals/CustomModal';
import SearchInput from '@/components/common/SearchInput';
import CustomTable from '@/components/common/table/CustomTable';
import TableFilter from '@/components/common/table/TableFilter';
import { useMenuPermission } from '@/hook/auth/useMenuPermission';
import { useSettingProgress } from '@/hook/setting/useSettingProgress';
import { useArrangeOrdinal } from '@/hook/useArrangeOrdinal';
import { useModalConfirm } from '@/hook/useModalConfirm';
import { usePaging } from '@/hook/usePaging';
import { useSelectTable } from '@/hook/useSelectTable';
import { SettingProgressDTO } from '@/store/setting/progress/dto/setting-progress.dto';
import { SettingProgressListFilter, SettingProgressUpdateREQ } from '@/store/setting/progress/request/setting-progress.request';
import {
  deleteSettingProgressApi,
  settingProgressKeys,
  updateSettingProgressApi,
} from '@/store/setting/progress/setting-progress.api';
import { settingProgressListParamToFilter } from '@/store/setting/progress/setting-progress.service';
import { MenuKeys } from '@/utils/enums/side-menu-type';
import { handleError } from '@/utils/helpers/notification-error.helper';
import { handleSuccess } from '@/utils/helpers/notification-success.helper';
import { parseArrayToStringRequest } from '@/utils/parser/array-request.parser';
import { useMutation, useQueryClient } from '@tanstack/react-query';
import { useSearchParams } from 'next/navigation';
import { useMemo, useRef, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { ProgressDetailModal } from '../components/ProgressDetailModal';
import { ProgressColumns } from '../data-columns/ProgressColumns';

export const ProgressList = () => {
  const { t: tSetting } = useTranslation('setting');
  const searchParams = useSearchParams();
  const queryClient = useQueryClient();
  const { canEdit } = useMenuPermission(MenuKeys.SETTING);

  const { arrangeOrdinal } = useArrangeOrdinal({ url: 'progresses', queryKey: settingProgressKeys.lists() });

  const progressId = useRef<number>(0);
  const { isOpen, setIsOpen, openModal, closeModal } = useModalConfirm();
  const { selectedRowKeys, setSelectedRowKeys, handleSelect, handleSelectAll } = useSelectTable<SettingProgressDTO>();

  const [isOpenDetail, setIsOpenDetail] = useState<boolean>(false);

  const { initialFilter } = useMemo(() => {
    const initialFilter = settingProgressListParamToFilter(searchParams);
    return { initialFilter };
  }, []);

  const { filter, handleFilterChange } = usePaging<SettingProgressListFilter>({
    initialFilter,
    debounceTime: 500,
  });

  const { settingProgressData, settingSubProgressDetailData } = useSettingProgress({ filter, progressId: progressId.current });

  const { mutate: mutateDeleteSettingProgress, isPending: isDeleting } = useMutation({
    mutationFn: (ids: string) => deleteSettingProgressApi(ids),
    onSuccess: () => {
      closeModal();
      handleSuccess('delete');
      setSelectedRowKeys([]);
      queryClient.invalidateQueries({ queryKey: settingProgressKeys.lists() });
    },
    onError: handleError,
  });

  const { mutate: mutateUpdateSettingProgress } = useMutation({
    mutationFn: ({ id, body }: { id: number; body: SettingProgressUpdateREQ }) => updateSettingProgressApi(id, body),
    onSuccess: () => {
      handleSuccess('update');
      queryClient.invalidateQueries({ queryKey: settingProgressKeys.lists() });
    },
    onError: handleError,
  });

  const onDeleteSettingProgress = () => {
    mutateDeleteSettingProgress(parseArrayToStringRequest(selectedRowKeys));
  };

  const onUpdateSettingProgress = (id: number, values: SettingProgressUpdateREQ) => {
    mutateUpdateSettingProgress({ id, body: values });
  };

  const onDetail = (id: number) => {
    progressId.current = id;
    setIsOpenDetail(true);
  };

  const onCloseDetail = () => {
    setIsOpenDetail(false);
    queryClient.invalidateQueries({ queryKey: settingProgressKeys.lists() });
  };

  return (
    <>
      <div className='mt-6'>
        <TableFilter total={settingProgressData?.total}>
          <SearchInput
            defaultValue={searchParams.get('search')?.toString()}
            onChange={(e) => {
              handleFilterChange({ search: e.target.value });
            }}
          />
        </TableFilter>
      </div>
      <div className='mt-4 ' id='fixedTable'>
        <CustomTable<SettingProgressDTO>
          columns={ProgressColumns({ onDetail, onUpdateSettingProgress })}
          data={settingProgressData?.data || []}
          showSelect
          showPaging={false}
          handleReoder={arrangeOrdinal}
          showIndex
          onSelect={handleSelect}
          onSelectAll={handleSelectAll}
          extra={
            canEdit && (
              <DeleteButton
                onClick={() => {
                  openModal(selectedRowKeys as number[]);
                }}
              />
            )
          }
        />
      </div>
      <CustomModal
        isOpen={isOpenDetail}
        isHideFooter={true}
        onClose={onCloseDetail}
        title={tSetting('progress.detail.sub_progress')}>
        <ProgressDetailModal subProgressData={settingSubProgressDetailData?.data || []} progressId={progressId.current || 0} />
      </CustomModal>
      <ModalConfirm isOpen={isOpen} setIsOpen={setIsOpen} onConfirm={onDeleteSettingProgress} isLoading={isDeleting} />
    </>
  );
};
