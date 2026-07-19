import AddButton from '@/components/common/AddButton';
import ModalConfirm from '@/components/common/ConfirmModal';
import DeleteButton from '@/components/common/DeleteButton';
import CustomTable from '@/components/common/table/CustomTable';
import { useMenuPermission } from '@/hook/auth/useMenuPermission';
import { useArrangeOrdinal } from '@/hook/useArrangeOrdinal';
import { useModalConfirm } from '@/hook/useModalConfirm';
import { useSelectTable } from '@/hook/useSelectTable';
import { SettingProgressDTO } from '@/store/setting/progress/dto/setting-progress.dto';
import { SettingProgressUpdateREQ } from '@/store/setting/progress/request/setting-progress.request';
import {
  createSubSettingProgressApi,
  deleteSubSettingProgressApi,
  settingProgressKeys,
  updateSubSettingProgressApi,
} from '@/store/setting/progress/setting-progress.api';
import { MenuKeys } from '@/utils/enums/side-menu-type';
import { handleError } from '@/utils/helpers/notification-error.helper';
import { handleSuccess } from '@/utils/helpers/notification-success.helper';
import { parseArrayToStringRequest } from '@/utils/parser/array-request.parser';
import { useMutation, useQueryClient } from '@tanstack/react-query';
import { ProgressDetailColumns } from '../data-columns/ProgressDetailColumns';

type ProgressDetailModalProps = {
  subProgressData: SettingProgressDTO[];
  progressId: number;
};

export const ProgressDetailModal = ({ subProgressData, progressId }: ProgressDetailModalProps) => {
  const queryClient = useQueryClient();
  const { canEdit } = useMenuPermission(MenuKeys.SETTING);

  const { arrangeOrdinal } = useArrangeOrdinal({
    url: `progresses/${progressId}/sub-progresses`,
    queryKey: settingProgressKeys.detail(progressId),
  });

  const { isOpen, setIsOpen, openModal, closeModal } = useModalConfirm();
  const { selectedRowKeys, setSelectedRowKeys, handleSelect, handleSelectAll } = useSelectTable<SettingProgressDTO>();

  const { mutate: mutateCreateSettingSubProgress } = useMutation({
    mutationFn: () => createSubSettingProgressApi(progressId),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: settingProgressKeys.detail(progressId) });
      handleSuccess('create');
    },
    onError: handleError,
  });

  const { mutate: mutateUpdateSettingSubProgress } = useMutation({
    mutationFn: ({ id, body }: { id: number; body: SettingProgressUpdateREQ }) =>
      updateSubSettingProgressApi(progressId, id, body),
    onSuccess: () => {
      handleSuccess('update');
      queryClient.invalidateQueries({ queryKey: settingProgressKeys.detail(progressId) });
    },
    onError: handleError,
  });

  const { mutate: mutateDeleteUpdateSettingSubProgress, isPending: isDeleting } = useMutation({
    mutationFn: (ids: string) => deleteSubSettingProgressApi(progressId, ids),
    onSuccess: () => {
      closeModal();
      handleSuccess('delete');
      queryClient.invalidateQueries({ queryKey: settingProgressKeys.detail(progressId) });
    },
    onError: handleError,
  });

  const onCreateSettingSubProgress = () => {
    mutateCreateSettingSubProgress();
  };

  const onUpdateSettingSubProgress = (id: number, value: SettingProgressUpdateREQ) => {
    mutateUpdateSettingSubProgress({ id, body: value });
  };

  const onDeleteManagerBranch = () => {
    mutateDeleteUpdateSettingSubProgress(parseArrayToStringRequest(selectedRowKeys));
  };

  return (
    <div className='mt-4'>
      {canEdit && (
        <div className='flex gap-3'>
          <DeleteButton
            onClick={() => {
              openModal(selectedRowKeys as number[]);
            }}
          />
          <AddButton onClick={onCreateSettingSubProgress} />
        </div>
      )}
      <div className='mt-4 '>
        <CustomTable<SettingProgressDTO>
          columns={ProgressDetailColumns({ onUpdateSettingSubProgress })}
          data={subProgressData || []}
          showSelect
          onSelect={handleSelect}
          onSelectAll={handleSelectAll}
          handleReoder={arrangeOrdinal}
          key={subProgressData.join()}
          showPaging={false}
          showIndex
        />
        <ModalConfirm isOpen={isOpen} setIsOpen={setIsOpen} onConfirm={onDeleteManagerBranch} isLoading={isDeleting} />
      </div>
    </div>
  );
};
