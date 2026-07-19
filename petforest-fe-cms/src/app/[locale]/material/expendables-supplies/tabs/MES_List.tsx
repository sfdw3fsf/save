import ModalConfirm from '@/components/common/ConfirmModal';
import DeleteButton from '@/components/common/DeleteButton';
import CustomModal from '@/components/common/modals/CustomModal';
import CustomTable from '@/components/common/table/CustomTable';
import TableFilter from '@/components/common/table/TableFilter';
import { useMenuPermission } from '@/hook/auth/useMenuPermission';
import { useArrangeOrdinal } from '@/hook/useArrangeOrdinal';
import useDownloadExcel from '@/hook/useDownloadExcel';
import { useModalConfirm } from '@/hook/useModalConfirm';
import { usePaging } from '@/hook/usePaging';
import { useSelectTable } from '@/hook/useSelectTable';
import { MaterialDTO } from '@/store/material/dto/material.dto';
import { MaterialDetailInput } from '@/store/material/input/material.input';
import {
  deleteMaterialApi,
  getMaterialListApi,
  materialExpendablesKeys,
  updateMaterialApi,
  updateMaterialStatusApi,
} from '@/store/material/material.api';
import {
  materialDetailInputToRequest,
  materialIntialValueForm,
  materialListParamsToFilter,
  materialRespToDto,
} from '@/store/material/material.service';
import {
  MaterialListFilter,
  MaterialListREQ,
  MaterialUpdateREQ,
  MaterialUpdateStatusREQ,
} from '@/store/material/request/material.request';
import { EXCEL_FILE_NAME } from '@/utils/constants/excel-name.constant';
import { EXCEL_URL } from '@/utils/constants/excel-url.constant';
import { ACTIVE_STATE } from '@/utils/enums/common.enum';
import { MATERIAL_TYPE } from '@/utils/enums/material/material.enum';
import { MenuKeys } from '@/utils/enums/side-menu-type';
import { handleError } from '@/utils/helpers/notification-error.helper';
import { handleSuccess } from '@/utils/helpers/notification-success.helper';
import { parseArrayToStringRequest } from '@/utils/parser/array-request.parser';
import { initialPagingState, PagingState } from '@/utils/types/paging.type';
import { keepPreviousData, useMutation, useQuery, useQueryClient } from '@tanstack/react-query';
import { useSearchParams } from 'next/navigation';
import { useMemo, useRef, useState } from 'react';
import { FormProvider, useForm } from 'react-hook-form';
import { useTranslation } from 'react-i18next';
import MaterialTableDetailFilter from '../../components/MaterialTableDetailFilter';
import MES_DetailModal from '../components/MES_DetailModal';
import { MES_Columns } from '../data-columns/MES_Columns';

const MES_List = () => {
  const { t: tMaterial } = useTranslation('material');

  const searchParams = useSearchParams();
  const queryClient = useQueryClient();
  const { mutateDownloadExcel } = useDownloadExcel<MaterialListREQ>({
    fileName: EXCEL_FILE_NAME.MATERIAL_EXPENDABLES_FUNERAL,
    url: EXCEL_URL.MATERIAL,
  });
  const { isOpen, setIsOpen, openModal, closeModal } = useModalConfirm();
  const { selectedRowKeys, setSelectedRowKeys, handleSelect, handleSelectAll } = useSelectTable<MaterialDTO>();
  const { arrangeOrdinal } = useArrangeOrdinal({
    url: `materials`,
    queryKey: materialExpendablesKeys.lists(),
    type: { materialType: MATERIAL_TYPE.CONSUMABLE },
  });

  const methods = useForm<MaterialDetailInput>({
    defaultValues: materialIntialValueForm,
  });
  const { canEdit } = useMenuPermission(MenuKeys.MATERIAL_EXPENDABLES_SUPPLIES);

  const materialId = useRef<number>(0);

  const [isOpenDetail, setIsOpenDetail] = useState<boolean>(false);
  const [isHidingFooter, setIsHiddingFooter] = useState<boolean>(true);

  const { handleSubmit } = methods;

  const { initialPaging, initialFilter } = useMemo(() => {
    const initialFilter = materialListParamsToFilter(searchParams);
    const initialPaging: PagingState = {
      pageSize: +(searchParams.get('pageSize') || initialPagingState.pageSize),
      pageNumber: +(searchParams.get('pageNumber') || initialPagingState.pageNumber),
    };
    return { initialPaging, initialFilter };
  }, []);

  const { filter, handlePageChange, handleFilterChange, handleTableChange } = usePaging<MaterialListFilter>({
    initialPaging,
    initialFilter,
    debounceTime: 500,
  });

  const { data: materialExpendablesListData } = useQuery({
    queryKey: materialExpendablesKeys.list(filter),
    queryFn: () => getMaterialListApi({ ...filter, materialType: MATERIAL_TYPE.CONSUMABLE }),
    select: (response) => {
      return { total: response.data.data.length, data: response.data.data.map(materialRespToDto) };
    },
    placeholderData: keepPreviousData,
  });

  const { mutate: mutateUpdateMaterial } = useMutation({
    mutationFn: ({ id, body }: { id: number; body: MaterialUpdateREQ }) => updateMaterialApi(id, body),
    onSuccess: () => {
      handleSuccess('update');
      queryClient.invalidateQueries({ queryKey: materialExpendablesKeys.lists() });
      materialId.current && queryClient.invalidateQueries({ queryKey: materialExpendablesKeys.detail(materialId.current) });
    },
    onError: handleError,
  });

  const { mutate: mutateUpdateMaterialStatus } = useMutation({
    mutationFn: ({ id, body }: { id: number; body: MaterialUpdateStatusREQ }) => updateMaterialStatusApi(id, body),
    onSuccess: () => {
      handleSuccess('update');
      queryClient.invalidateQueries({ queryKey: materialExpendablesKeys.lists() });
    },
    onError: handleError,
  });

  const { mutate: mutateDeleteMaterial, isPending: isDeleting } = useMutation({
    mutationFn: (ids: string) => deleteMaterialApi(ids),
    onSuccess: () => {
      closeModal();
      handleSuccess('delete');
      setSelectedRowKeys([]);
      queryClient.invalidateQueries({ queryKey: materialExpendablesKeys.lists() });
    },
    onError: handleError,
  });

  const materialName = useMemo(() => {
    if (materialId.current === 0) return '';
    return materialExpendablesListData?.data.find((item) => item.id === materialId.current)?.name || '';
  }, [materialId.current, materialExpendablesListData?.data]);

  const onDetail = (id: number) => {
    materialId.current = id;
    setIsOpenDetail(true);
  };

  const onUpdateMaterial = (id: number, values: MaterialUpdateREQ) => {
    mutateUpdateMaterial({ id, body: values });
  };

  const onUpdateMaterialStatus = (id: number, values: MaterialUpdateStatusREQ) => {
    mutateUpdateMaterialStatus({ id, body: { ...values, materialType: MATERIAL_TYPE.FUNERAL } });
  };

  const onDeleteMaterial = () => {
    mutateDeleteMaterial(parseArrayToStringRequest(selectedRowKeys));
  };

  const onUpdateProductInformation = (values: MaterialDetailInput) => {
    const body = materialDetailInputToRequest(values);
    mutateUpdateMaterial({ id: materialId.current, body });
  };

  const onDownloadExcel = () => {
    mutateDownloadExcel({
      filter: { ...filter, materialType: MATERIAL_TYPE.CONSUMABLE },
      ids: parseArrayToStringRequest(selectedRowKeys, false),
    });
  };

  return (
    <>
      <div className='mt-6'>
        <TableFilter total={materialExpendablesListData?.total}>
          <MaterialTableDetailFilter onFilterChange={handleFilterChange} onDownloadExcel={onDownloadExcel} />
        </TableFilter>
      </div>
      <div className='mt-4' id='fixedTable'>
        <CustomTable<MaterialDTO>
          columns={MES_Columns({ onDetail, onUpdateMaterial, onUpdateMaterialStatus })}
          data={materialExpendablesListData?.data || []}
          rowClassName={(record) => (record.status === ACTIVE_STATE.INACTIVE ? 'disabled-row-20 disabled-text' : '')}
          showPaging={false}
          showSelect
          extra={
            canEdit && (
              <DeleteButton
                onClick={() => {
                  openModal(selectedRowKeys as number[]);
                }}
              />
            )
          }
          handleReoder={arrangeOrdinal}
          onPageNumberChange={handlePageChange}
          onChangeTable={handleTableChange}
          onSelect={handleSelect}
          onSelectAll={handleSelectAll}
        />
      </div>

      <FormProvider {...methods}>
        <CustomModal
          isOpen={isOpenDetail}
          onClose={() => {
            methods.reset(materialIntialValueForm);
            setIsOpenDetail(false);
          }}
          onOk={handleSubmit(onUpdateProductInformation)}
          isHideFooter={isHidingFooter}
          title={materialName}>
          <MES_DetailModal id={materialId.current} setIsHiddingFooter={setIsHiddingFooter} />
        </CustomModal>
      </FormProvider>

      <ModalConfirm isOpen={isOpen} setIsOpen={setIsOpen} onConfirm={onDeleteMaterial} isLoading={isDeleting} />
    </>
  );
};

export default MES_List;
