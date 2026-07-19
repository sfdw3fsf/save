import AddButton from '@/components/common/AddButton';
import ModalConfirm from '@/components/common/ConfirmModal';
import CustomButton from '@/components/common/CustomButton';
import DeleteButton from '@/components/common/DeleteButton';
import TotalCard from '@/components/common/TotalCard';
import CustomTable from '@/components/common/table/CustomTable';
import { useMenuPermission } from '@/hook/auth/useMenuPermission';
import useDownloadExcel from '@/hook/useDownloadExcel';
import { useModalConfirm } from '@/hook/useModalConfirm';
import { usePaging } from '@/hook/usePaging';
import { useSelectTable } from '@/hook/useSelectTable';
import { MaterialInventoryDTO } from '@/store/material/dto/material.dto';
import {
  createMaterialInventoryApi,
  deleteMaterialInventoryApi,
  getMaterialInventoryByMonthApi,
  getMaterialInventoryListApi,
  materialInventoryMonthlyKeys,
  materialOtherInventoryByMonthKeys,
  materialOtherInventoryKeys,
  materialOtherKeys,
  updateMaterialInventoryApi,
} from '@/store/material/material.api';
import { materialInventoryByMonthRespToDto, materialInventoryRespToDto } from '@/store/material/material.service';
import {
  MaterialInventoryListFilter,
  MaterialInventoryListREQ,
  MaterialInventoryUpdateREQ,
} from '@/store/material/request/material.request';
import { ICONS } from '@/theme';
import { EXCEL_FILE_NAME } from '@/utils/constants/excel-name.constant';
import { MATERIAL_TYPE } from '@/utils/enums/material/material.enum';
import { MenuKeys } from '@/utils/enums/side-menu-type';
import { handleError } from '@/utils/helpers/notification-error.helper';
import { handleSuccess } from '@/utils/helpers/notification-success.helper';
import { parseArrayToStringRequest } from '@/utils/parser/array-request.parser';
import { initialPagingState, PagingState } from '@/utils/types/paging.type';
import { keepPreviousData, useMutation, useQuery, useQueryClient } from '@tanstack/react-query';
import { Divider } from 'antd';
import Image from 'next/image';
import { useSearchParams } from 'next/navigation';
import { useMemo, useRef, useState } from 'react';
import { useTranslation } from 'react-i18next';
import MaterialTableDetailFilter from '../../components/MaterialTableDetailFilter';
import MaterialMonthlyDetailModal from '../../components/Material_MonthlyDetailModal';
import MaterialMonthlyDetailModalTable from '../../components/Material_MonthlyDetailModalTable';
import { MOFS_DetailColumns } from '../data-columns/MOFS_DetailColumn';

type MOFS_DetailInventoryProps = {
  id: number;
};

const MOFS_DetailInventory = ({ id }: MOFS_DetailInventoryProps) => {
  const { t: tMaterial } = useTranslation('material');
  const { t: tCommon } = useTranslation('common');
  const searchParams = useSearchParams();
  const queryClient = useQueryClient();
  const { isOpen, setIsOpen, openModal, closeModal } = useModalConfirm();
  const { selectedRowKeys, setSelectedRowKeys, handleSelect, handleSelectAll } = useSelectTable<MaterialInventoryDTO>();
  const { mutateDownloadExcel } = useDownloadExcel<MaterialInventoryListREQ>({
    fileName: EXCEL_FILE_NAME.MATERIAL_OTHER_INVENTORY_DETAILS_SPECIFIC,
    url: `materials/${id}/inventories/exports`,
  });
  const { canEdit } = useMenuPermission(MenuKeys.MATERIAL_OTHER_FUNERAL_SUPPLIES);

  const inventoryId = useRef<number>(0);

  const [isOpenMonthlyDetail, setIsMonthlyDetail] = useState<boolean>(false);

  const { initialPaging } = useMemo(() => {
    const initialPaging: PagingState = {
      pageSize: +(searchParams.get('pageSize') || initialPagingState.pageSize),
      pageNumber: +(searchParams.get('pageNumber') || initialPagingState.pageNumber),
    };
    return { initialPaging };
  }, []);

  const { filter, handlePageChange, handleFilterChange, handleTableChange } = usePaging<MaterialInventoryListFilter>({
    initialPaging,
    debounceTime: 500,
  });

  const { mutate: mutateCreateMaterialInventory } = useMutation({
    mutationFn: () => createMaterialInventoryApi(id, { quantity: 0 }),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: materialOtherInventoryKeys.lists() });
      handleSuccess('create');
    },
    onError: handleError,
  });

  const { data: materialInventoryListData } = useQuery({
    queryKey: materialOtherInventoryKeys.list({ filter, id }),
    queryFn: () => getMaterialInventoryListApi(id, { ...filter, materialType: MATERIAL_TYPE.OTHER }),
    select: (response) => {
      return { ...response.data, data: response.data.data.map(materialInventoryRespToDto) };
    },
    placeholderData: keepPreviousData,
  });

  const { mutate: mutateUpdateMaterialInventory } = useMutation({
    mutationFn: (params: MaterialInventoryUpdateREQ) => updateMaterialInventoryApi(id, inventoryId.current, params),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: materialOtherKeys.lists() });
      queryClient.invalidateQueries({ queryKey: materialOtherInventoryKeys.lists() });
      queryClient.invalidateQueries({ queryKey: materialOtherInventoryByMonthKeys.detail(id) });
      queryClient.invalidateQueries({ queryKey: materialInventoryMonthlyKeys.lists() });
      handleSuccess('update');
    },
    onError: handleError,
  });

  const { data: materialInventoryByMonthData } = useQuery({
    queryKey: materialOtherInventoryByMonthKeys.detail(id),
    queryFn: () => getMaterialInventoryByMonthApi(id),
    select: (response) => {
      return response?.data.data && materialInventoryByMonthRespToDto(response.data.data);
    },
    placeholderData: keepPreviousData,
  });

  const { mutate: mutateDeleteMaterialInventory, isPending: isDeleting } = useMutation({
    mutationFn: (ids: string) => deleteMaterialInventoryApi(id, ids),
    onSuccess: () => {
      closeModal();
      handleSuccess('delete');
      setSelectedRowKeys([]);
      queryClient.invalidateQueries({ queryKey: materialOtherKeys.lists() });
      queryClient.invalidateQueries({ queryKey: materialOtherInventoryKeys.lists() });
      queryClient.invalidateQueries({ queryKey: materialOtherInventoryByMonthKeys.detail(id) });
      queryClient.invalidateQueries({ queryKey: materialInventoryMonthlyKeys.lists() });
    },
    onError: handleError,
  });

  const onCreateMaterialInventory = () => {
    mutateCreateMaterialInventory();
  };

  const onUpdateMaterialInventory = (id: number, values: MaterialInventoryUpdateREQ) => {
    (inventoryId.current = id), mutateUpdateMaterialInventory(values);
  };

  const onDeleteMaterialInventory = () => {
    mutateDeleteMaterialInventory(parseArrayToStringRequest(selectedRowKeys));
  };

  const onDownloadExcel = () => {
    mutateDownloadExcel({
      filter: { ...filter, materialType: MATERIAL_TYPE.OTHER },
      ids: parseArrayToStringRequest(selectedRowKeys, false),
    });
  };

  return (
    <div>
      <div className='flex justify-between w-full gap-3 mt-6 overflow-auto'>
        <TotalCard
          className='flex-1'
          title={tMaterial('detail.current')}
          value={materialInventoryByMonthData?.stockQuantity || 0}
        />
        <TotalCard
          className='flex-1'
          title={tMaterial('detail.carryover')}
          value={materialInventoryByMonthData?.carryoverStock || 0}
        />
      </div>

      <div className='flex justify-end'>
        <CustomButton
          onClick={() => {
            setIsMonthlyDetail(true);
          }}
          type='text'
          icon={<Image src={ICONS.ARROW_DETAIL} width={20} alt='Arrow detail' />}
          className='mt-4'>
          {tMaterial('detail.button.monthly_detail')}
        </CustomButton>
      </div>

      <Divider className='border-[5px] border-neutral-20' />

      {canEdit && (
        <div className='flex gap-3 button-flex'>
          <DeleteButton
            onClick={() => {
              openModal(selectedRowKeys as number[]);
            }}
          />
          <AddButton onClick={onCreateMaterialInventory} />
        </div>
      )}

      <div className='mt-4'>
        <MaterialTableDetailFilter
          materialType={MATERIAL_TYPE.OTHER}
          onFilterChange={handleFilterChange}
          isDetail={true}
          onDownloadExcel={onDownloadExcel}
        />
      </div>

      <div className='text-neutral-80 mt-4'>{`${tCommon('total')} ${materialInventoryListData?.total}${tCommon('number')}`}</div>

      <div className='mb-6 mt-4'>
        <CustomTable<MaterialInventoryDTO>
          columns={MOFS_DetailColumns({ onUpdateMaterialInventory })}
          getCheckboxProps={(record) => {
            return {
              disabled: record.quantity < 0,
            };
          }}
          data={materialInventoryListData?.data || []}
          showSelect
          sticky
          showPaging
          paging={{
            total: materialInventoryListData?.total,
            pageSize: filter.pageSize,
            pageNumber: filter.pageNumber,
          }}
          onPageNumberChange={handlePageChange}
          onChangeTable={handleTableChange}
          onSelect={handleSelect}
          onSelectAll={handleSelectAll}
        />
      </div>

      <MaterialMonthlyDetailModal
        isOpen={isOpenMonthlyDetail}
        onClose={() => {
          setIsMonthlyDetail(false);
        }}
        title={tMaterial('detail.monthly_inventory')}>
        <MaterialMonthlyDetailModalTable id={id} />
      </MaterialMonthlyDetailModal>

      <ModalConfirm isOpen={isOpen} setIsOpen={setIsOpen} onConfirm={onDeleteMaterialInventory} isLoading={isDeleting} />
    </div>
  );
};

export default MOFS_DetailInventory;
