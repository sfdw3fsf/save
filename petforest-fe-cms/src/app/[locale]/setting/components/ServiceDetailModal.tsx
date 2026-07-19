import AddButton from '@/components/common/AddButton';
import ModalConfirm from '@/components/common/ConfirmModal';
import DeleteButton from '@/components/common/DeleteButton';
import CustomTable from '@/components/common/table/CustomTable';
import { useMenuPermission } from '@/hook/auth/useMenuPermission';
import { useArrangeOrdinal } from '@/hook/useArrangeOrdinal';
import { useModalConfirm } from '@/hook/useModalConfirm';
import { useSelectTable } from '@/hook/useSelectTable';
import { ServiceDefaultMaterialsDTO } from '@/store/setting/service/dto/setting-services.dto';
import { SettingServiceUpdateDefaultMaterialREQ } from '@/store/setting/service/request/setting-services.request';
import {
  createSettingServiceMaterialApi,
  deleteServiceMaterialListApi,
  getSettingMaterialOptionApi,
  settingServiceKeys,
  updateSettingServiceMaterialApi,
} from '@/store/setting/service/setting-service.api';
import { materialRespToOptions } from '@/store/setting/service/setting-services.service';
import { MenuKeys } from '@/utils/enums/side-menu-type';
import { handleError } from '@/utils/helpers/notification-error.helper';
import { handleSuccess } from '@/utils/helpers/notification-success.helper';
import { parseArrayToStringRequest } from '@/utils/parser/array-request.parser';
import { keepPreviousData, useMutation, useQuery, useQueryClient } from '@tanstack/react-query';
import { isNil } from 'lodash';
import { useMemo } from 'react';
import { ServiceDetailColumns } from '../data-columns/ServiceDetailColumns';

type ServiceDetailModalProps = {
  serviceDefaultMaterial: ServiceDefaultMaterialsDTO[];
  serviceId: number;
};

export const ServiceDetailModal = ({ serviceId, serviceDefaultMaterial }: ServiceDetailModalProps) => {
  const queryClient = useQueryClient();
  const { arrangeOrdinal } = useArrangeOrdinal({
    url: `funeral-services/${serviceId}/service-default-materials`,
    queryKey: settingServiceKeys.detail(serviceId),
  });

  const { canEdit } = useMenuPermission(MenuKeys.SETTING);

  const { isOpen, setIsOpen, openModal, closeModal } = useModalConfirm();
  const { selectedRowKeys, setSelectedRowKeys, handleSelect, handleSelectAll } = useSelectTable<ServiceDefaultMaterialsDTO>();

  const { mutate: mutateCreateSettingServiceMaterial } = useMutation({
    mutationFn: () => createSettingServiceMaterialApi(serviceId),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: settingServiceKeys.detail(serviceId) });
      handleSuccess('create');
    },
    onError: handleError,
  });

  const { mutate: mutateUpdateSettingServiceMaterial } = useMutation({
    mutationFn: ({ id, body }: { id: number; body: SettingServiceUpdateDefaultMaterialREQ }) =>
      updateSettingServiceMaterialApi(serviceId, id, body),
    onSuccess: () => {
      handleSuccess('update');
      queryClient.invalidateQueries({ queryKey: settingServiceKeys.detail(serviceId) });
      queryClient.invalidateQueries({ queryKey: settingServiceKeys.all });
    },
    onError: handleError,
  });

  const { mutate: mutateDeleteSettingServiceMaterial, isPending: isDeleting } = useMutation({
    mutationFn: (ids: string) => deleteServiceMaterialListApi(serviceId, ids),
    onSuccess: () => {
      closeModal();
      handleSuccess('delete');
      setSelectedRowKeys([]);
      queryClient.invalidateQueries({ queryKey: settingServiceKeys.detail(serviceId) });
      queryClient.invalidateQueries({ queryKey: settingServiceKeys.all });
    },
    onError: handleError,
  });

  const { data: materialServiceData } = useQuery({
    queryKey: settingServiceKeys.lists(),
    queryFn: () => getSettingMaterialOptionApi(),
    select: (response) => {
      return response.data.data;
    },
    placeholderData: keepPreviousData,
  });

  const materialServiceOptions = useMemo(() => {
    if (isNil(materialServiceData)) return [];
    return materialRespToOptions(materialServiceData);
  }, [materialServiceData]);

  const onCreateSettingServiceMaterial = () => {
    mutateCreateSettingServiceMaterial();
  };

  const onUpdateSettingServiceMaterial = (id: number, value: SettingServiceUpdateDefaultMaterialREQ) => {
    mutateUpdateSettingServiceMaterial({ id, body: value });
  };

  const onDeleteSettingServiceMaterial = () => {
    mutateDeleteSettingServiceMaterial(parseArrayToStringRequest(selectedRowKeys));
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
          <AddButton onClick={onCreateSettingServiceMaterial} />
        </div>
      )}
      <div className='mt-4 '>
        <CustomTable<ServiceDefaultMaterialsDTO>
          columns={ServiceDetailColumns({ materialServiceOptions, onUpdateSettingServiceMaterial })}
          data={serviceDefaultMaterial}
          showSelect
          showPaging={false}
          showIndex
          onSelect={handleSelect}
          onSelectAll={handleSelectAll}
          handleReoder={arrangeOrdinal}
          key={JSON.stringify({ serviceDefaultMaterial, materialServiceOptions })}
        />
      </div>
      <ModalConfirm isOpen={isOpen} setIsOpen={setIsOpen} onConfirm={onDeleteSettingServiceMaterial} isLoading={isDeleting} />
    </div>
  );
};
