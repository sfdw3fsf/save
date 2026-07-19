import AddButton from '@/components/common/AddButton';
import ModalConfirm from '@/components/common/ConfirmModal';
import DeleteButton from '@/components/common/DeleteButton';
import CustomTable from '@/components/common/table/CustomTable';
import { useMenuPermission } from '@/hook/auth/useMenuPermission';
import { useArrangeOrdinal } from '@/hook/useArrangeOrdinal';
import { useModalConfirm } from '@/hook/useModalConfirm';
import { useSelectTable } from '@/hook/useSelectTable';
import { SettingVarietiesDTO } from '@/store/setting/varieties/dto/setting-varieties.dto';
import { SettingVarietiesUpdateREQ } from '@/store/setting/varieties/request/setting-varieties.request';
import {
  createSettingVarietiesBreedApi,
  deleteSettingVarietiesBreedApi,
  settingVarietiesKeys,
  updateSettingVarietiesBreedApi,
} from '@/store/setting/varieties/setting-varieties.api';
import { MenuKeys } from '@/utils/enums/side-menu-type';
import { handleError } from '@/utils/helpers/notification-error.helper';
import { handleSuccess } from '@/utils/helpers/notification-success.helper';
import { parseArrayToStringRequest } from '@/utils/parser/array-request.parser';
import { useMutation, useQueryClient } from '@tanstack/react-query';
import { VarietiesDetailColumns } from '../data-columns/VarietyDetailColumns';

type VarietyDetailModalProps = {
  varietyBreeds: SettingVarietiesDTO[];
  varietyId: number;
};

const VarietyDetailModal = ({ varietyBreeds, varietyId }: VarietyDetailModalProps) => {
  const { canEdit } = useMenuPermission(MenuKeys.SETTING);
  const { arrangeOrdinal } = useArrangeOrdinal({
    url: `pet-types/${varietyId}/breeds`,
    queryKey: settingVarietiesKeys.detail(varietyId),
  });
  const queryClient = useQueryClient();

  const { isOpen, setIsOpen, openModal, closeModal } = useModalConfirm();
  const { selectedRowKeys, setSelectedRowKeys, handleSelect, handleSelectAll } = useSelectTable<SettingVarietiesDTO>();

  const { mutate: mutateCreateSettingVarietyBreed } = useMutation({
    mutationFn: () => createSettingVarietiesBreedApi(varietyId),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: settingVarietiesKeys.detail(varietyId) });

      handleSuccess('create');
    },
    onError: handleError,
  });

  const { mutate: mutateUpdateSettingVarietyBreed } = useMutation({
    mutationFn: ({ id, body }: { id: number; body: SettingVarietiesUpdateREQ }) =>
      updateSettingVarietiesBreedApi(varietyId, id, body),
    onSuccess: () => {
      handleSuccess('update');
      queryClient.invalidateQueries({ queryKey: settingVarietiesKeys.detail(varietyId) });
    },
    onError: handleError,
  });

  const { mutate: mutateDeleteSettingVarietyBreed, isPending: isDeleting } = useMutation({
    mutationFn: (ids: string) => deleteSettingVarietiesBreedApi(varietyId, ids),
    onSuccess: () => {
      closeModal();
      handleSuccess('delete');
      setSelectedRowKeys([]);
      queryClient.invalidateQueries({ queryKey: settingVarietiesKeys.detail(varietyId) });
    },
    onError: handleError,
  });

  const onCreateSettingVarietyBreed = () => {
    mutateCreateSettingVarietyBreed();
  };

  const onUpdateSettingVarietyBreed = (id: number, value: SettingVarietiesUpdateREQ) => {
    mutateUpdateSettingVarietyBreed({ id, body: value });
  };

  const onDeleteSettingVarietyBreed = () => {
    mutateDeleteSettingVarietyBreed(parseArrayToStringRequest(selectedRowKeys));
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
          <AddButton onClick={onCreateSettingVarietyBreed} />
        </div>
      )}
      <div className='mt-4 '>
        <CustomTable<SettingVarietiesDTO>
          columns={VarietiesDetailColumns({ onUpdateSettingVarietyBreed })}
          data={varietyBreeds}
          showSelect
          showPaging={false}
          showIndex
          onSelect={handleSelect}
          onSelectAll={handleSelectAll}
          handleReoder={arrangeOrdinal}
        />
      </div>
      <ModalConfirm isOpen={isOpen} setIsOpen={setIsOpen} onConfirm={onDeleteSettingVarietyBreed} isLoading={isDeleting} />
    </div>
  );
};

export default VarietyDetailModal;
