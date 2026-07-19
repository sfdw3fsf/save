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
  materialLucetteKeys,
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
import { LUCETTE_SUPPLIES_DEFAULT_ITEM } from '@/utils/constants/material/lucette-supplies.constant';
import { ACTIVE_STATE } from '@/utils/enums/common.enum';
import { MATERIAL_TYPE } from '@/utils/enums/material/material.enum';
import { MenuKeys } from '@/utils/enums/side-menu-type';
import { handleError } from '@/utils/helpers/notification-error.helper';
import { handleSuccess } from '@/utils/helpers/notification-success.helper';
import { parseArrayToStringRequest } from '@/utils/parser/array-request.parser';
import { initialPagingState, PagingState } from '@/utils/types/paging.type';
import { keepPreviousData, useMutation, useQuery, useQueryClient } from '@tanstack/react-query';
import { usePathname, useRouter, useSearchParams } from 'next/navigation';
import { useMemo, useRef, useState } from 'react';
import { FormProvider, useForm } from 'react-hook-form';
import MaterialTableDetailFilter from '../../components/MaterialTableDetailFilter';
import MLS_DetailModal from '../components/MLS_DetailModal';
import MLS_Columns from '../data-columns/MLS_Columns';

const MLS_List = () => {
  const searchParams = useSearchParams();
  const queryClient = useQueryClient();
  const { isOpen, setIsOpen, openModal, closeModal } = useModalConfirm();
  const { selectedRowKeys, setSelectedRowKeys, handleSelect, handleSelectAll } = useSelectTable<MaterialDTO>();
  const { arrangeOrdinal } = useArrangeOrdinal({
    url: `materials`,
    queryKey: materialLucetteKeys.lists(),
    type: { materialType: MATERIAL_TYPE.LUCETTE },
  });
  const { mutateDownloadExcel } = useDownloadExcel<MaterialListREQ>({
    fileName: EXCEL_FILE_NAME.MATERIAL_LUCETTE,
    url: EXCEL_URL.MATERIAL,
  });

  const methods = useForm<MaterialDetailInput>({
    defaultValues: materialIntialValueForm,
  });
  const pathname = usePathname();
  const router = useRouter();
  const { canEdit } = useMenuPermission(MenuKeys.MATERIAL_LUCETTE_SUPPLIES);

  const materialId = useRef<number>(0);

  const [isOpenDetail, setIsOpenDetail] = useState<boolean>(false);
  const [isHidingFooter, setIsHiddingFooter] = useState<boolean>(true);
  const [previousParams, setPreviousParams] = useState<string | null>(null);

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

  const { data: materialLucetteListData } = useQuery({
    queryKey: materialLucetteKeys.list(filter),
    queryFn: () => getMaterialListApi({ ...filter, materialType: MATERIAL_TYPE.LUCETTE }),
    select: (response) => {
      return { total: response.data.data.length, data: response.data.data.map(materialRespToDto) };
    },
    placeholderData: keepPreviousData,
  });

  const { mutate: mutateUpdateMaterial } = useMutation({
    mutationFn: ({ id, body }: { id: number; body: MaterialUpdateREQ }) => updateMaterialApi(id, body),
    onSuccess: () => {
      handleSuccess('update');
      queryClient.invalidateQueries({ queryKey: materialLucetteKeys.lists() });
      materialId.current && queryClient.invalidateQueries({ queryKey: materialLucetteKeys.detail(materialId.current) });
    },
    onError: handleError,
  });

  const { mutate: mutateUpdateMaterialStatus } = useMutation({
    mutationFn: ({ id, body }: { id: number; body: MaterialUpdateStatusREQ }) => updateMaterialStatusApi(id, body),
    onSuccess: () => {
      handleSuccess('update');
      queryClient.invalidateQueries({ queryKey: materialLucetteKeys.lists() });
    },
    onError: handleError,
  });

  const { mutate: mutateDeleteMaterial, isPending: isDeleting } = useMutation({
    mutationFn: (ids: string) => deleteMaterialApi(ids),
    onSuccess: () => {
      closeModal();
      handleSuccess('delete');
      setSelectedRowKeys([]);
      queryClient.invalidateQueries({ queryKey: materialLucetteKeys.lists() });
    },
    onError: handleError,
  });

  const materialName = useMemo(() => {
    if (materialId.current === 0) return '';
    return materialLucetteListData?.data.find((item) => item.id === materialId.current)?.name || '';
  }, [materialId.current, materialLucetteListData?.data]);

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
      filter: { ...filter, materialType: MATERIAL_TYPE.LUCETTE },
      ids: parseArrayToStringRequest(selectedRowKeys, false),
    });
  };

  return (
    <>
      <div className='mt-6'>
        <TableFilter total={materialLucetteListData?.total}>
          <MaterialTableDetailFilter onFilterChange={handleFilterChange} onDownloadExcel={onDownloadExcel} />
        </TableFilter>
      </div>

      <div className='mt-4' id='fixedTable'>
        <CustomTable<MaterialDTO>
          columns={MLS_Columns({ onDetail, onUpdateMaterial, onUpdateMaterialStatus })}
          data={materialLucetteListData?.data || []}
          showPaging={false}
          showSelect
          rowClassName={(record) => (record.status === ACTIVE_STATE.INACTIVE ? 'disabled-row-20 disabled-text' : '')}
          getCheckboxProps={(record) => {
            return {
              disabled: LUCETTE_SUPPLIES_DEFAULT_ITEM.includes(record.name),
            };
          }}
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
            const restoredUrl = previousParams ? `${pathname}?${previousParams}` : pathname;
            router.push(restoredUrl);
            materialId.current = 0;
            methods.reset(materialIntialValueForm);
            setIsOpenDetail(false);
          }}
          onOk={canEdit ? handleSubmit(onUpdateProductInformation) : undefined}
          isHideFooter={isHidingFooter}
          title={materialName}>
          <MLS_DetailModal id={materialId.current} setIsHiddingFooter={setIsHiddingFooter} />
        </CustomModal>
      </FormProvider>

      <ModalConfirm isOpen={isOpen} setIsOpen={setIsOpen} onConfirm={onDeleteMaterial} isLoading={isDeleting} />
    </>
  );
};

export default MLS_List;
