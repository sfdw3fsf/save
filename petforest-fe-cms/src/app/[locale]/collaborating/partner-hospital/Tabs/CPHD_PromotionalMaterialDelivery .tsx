import AddButton from '@/components/common/AddButton';
import ModalConfirm from '@/components/common/ConfirmModal';
import DeleteButton from '@/components/common/DeleteButton';
import CustomTable from '@/components/common/table/CustomTable';
import TableFilter from '@/components/common/table/TableFilter';
import TotalCard from '@/components/common/TotalCard';
import useDownloadExcel from '@/hook/useDownloadExcel';
import { useModalConfirm } from '@/hook/useModalConfirm';
import { usePaging } from '@/hook/usePaging';
import { useUser } from '@/hook/user/useUser';
import { useSelectTable } from '@/hook/useSelectTable';
import {
  createHospitalDeliveryApi,
  deleteHospitalDeliveryApi,
  getHospitalDeliveryListByHospitalApi,
  hospitalDeliveryKeys,
  updateHospitalDeliveryApi,
} from '@/store/collaborating-hospital/collaborating-hospital.api';
import {
  hospitalDeliveryListDataDtoToTotal,
  hospitalDeliveryRespToDto,
} from '@/store/collaborating-hospital/collaborating-hospital.service';
import { HospitalDeliveryDTO } from '@/store/collaborating-hospital/dto/collaborating-hospital.dto';
import {
  HospitalDeliveryListFilter,
  HospitalDeliveryUpdateREQ,
} from '@/store/collaborating-hospital/request/collaborating-hospital.request';
import { EXCEL_FILE_NAME } from '@/utils/constants/excel-name.constant';
import { handleError } from '@/utils/helpers/notification-error.helper';
import { handleSuccess } from '@/utils/helpers/notification-success.helper';
import { parseArrayToStringRequest } from '@/utils/parser/array-request.parser';
import { initialPagingState } from '@/utils/types/paging.type';
import { keepPreviousData, useMutation, useQuery, useQueryClient } from '@tanstack/react-query';
import { useMemo } from 'react';
import { useTranslation } from 'react-i18next';
import CPH_TableFilter from '../components/CPH_TableFilter';
import { CPHD_PromotionalMaterialDeliveryColumns } from '../data-columns/CPHD_PromotionalMaterialDeliveryColumns';

type CPHD_PromotionalMaterialDeliveryProps = {
  id: number;
};

export const CPHD_PromotionalMaterialDelivery = ({ id }: CPHD_PromotionalMaterialDeliveryProps) => {
  const { t: tPartnerHospital } = useTranslation('collaborating_partner_hospital');
  const { managerOptions } = useUser();
  const queryClient = useQueryClient();
  const { isOpen, setIsOpen, openModal, closeModal } = useModalConfirm();
  const { selectedRowKeys, setSelectedRowKeys, handleSelect, handleSelectAll } = useSelectTable<HospitalDeliveryDTO>();
  const { mutateDownloadExcel } = useDownloadExcel<HospitalDeliveryListFilter>({
    fileName: EXCEL_FILE_NAME.HOSPITAL_PROMOTIONAL_MATERIALS_DETAIL,
    url: `hospital-promo-materials/hospital/${id}/exports`,
  });

  const { filter, handlePageChange, handleFilterChange, handleTableChange } = usePaging<HospitalDeliveryListFilter>({
    initialPaging: { pageSize: initialPagingState.pageSize, pageNumber: initialPagingState.pageNumber },
    debounceTime: 500,
    isPushURL: false,
  });

  const { data: hospitalDeliveryListData } = useQuery({
    queryKey: hospitalDeliveryKeys.list({ id, filter }),
    queryFn: () => getHospitalDeliveryListByHospitalApi(id, filter),
    select: (response) => {
      return { ...response.data, data: response.data.data.map(hospitalDeliveryRespToDto) };
    },
    placeholderData: keepPreviousData,
  });

  const { mutate: mutateCreateHospitalDelivery } = useMutation({
    mutationFn: () => createHospitalDeliveryApi(id),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: hospitalDeliveryKeys.list({ id, filter }) });
      handleSuccess('create');
    },
    onError: handleError,
  });

  const { mutate: mutateUpdateHospitalDelivery } = useMutation({
    mutationFn: ({ id, body }: { id: number; body: HospitalDeliveryUpdateREQ }) => updateHospitalDeliveryApi(id, body),
    onSuccess: () => {
      handleSuccess('update');
      queryClient.invalidateQueries({ queryKey: hospitalDeliveryKeys.list({ id, filter }) });
    },
    onError: handleError,
  });

  const { mutate: mutateDeleteHospitalDelivery, isPending: isDeleting } = useMutation({
    mutationFn: (ids: string) => deleteHospitalDeliveryApi(ids),
    onSuccess: () => {
      closeModal();
      handleSuccess('delete');
      setSelectedRowKeys([]);
      queryClient.invalidateQueries({ queryKey: hospitalDeliveryKeys.list({ id, filter }) });
    },
    onError: handleError,
  });

  const totalData = useMemo(() => {
    return hospitalDeliveryListDataDtoToTotal(hospitalDeliveryListData?.data);
  }, [hospitalDeliveryListData]);

  const mockDataCountCard = useMemo(() => {
    return [
      {
        title: tPartnerHospital('detail.promotional_material_delivery.card.funeral_connection_count'),
        value: totalData.numOfFunerals,
      },
      { title: tPartnerHospital('detail.promotional_material_delivery.card.brochure_count'), value: totalData.deliveredBrochure },
      {
        title: tPartnerHospital('detail.promotional_material_delivery.card.transport_case_count'),
        value: totalData.deliveredTransportCase,
      },
    ];
  }, [totalData]);

  const onCreateHospitalDelivery = () => {
    mutateCreateHospitalDelivery();
  };

  const onUpdateHospitalDelivery = (id: number, values: HospitalDeliveryUpdateREQ) => {
    mutateUpdateHospitalDelivery({ id, body: values });
  };

  const onDeleteHospitalDelivery = () => {
    mutateDeleteHospitalDelivery(parseArrayToStringRequest(selectedRowKeys));
  };

  const onDownloadExcel = () => {
    mutateDownloadExcel({ filter, ids: parseArrayToStringRequest(selectedRowKeys, false) });
  };

  return (
    <div className='mt-6'>
      <div className='grid grid-cols-2 md:grid-cols-3 gap-3'>
        {mockDataCountCard.map((item) => (
          <TotalCard key={item.title} className='!w-full' title={item.title} value={item.value} />
        ))}
      </div>

      <div className='flex gap-4 flex-col mt-6'>
        <div className='flex gap-3'>
          <DeleteButton
            onClick={() => {
              openModal(selectedRowKeys as number[]);
            }}
          />
          <AddButton onClick={onCreateHospitalDelivery} />
        </div>

        <CPH_TableFilter onFilterChange={handleFilterChange} onDownloadExcel={onDownloadExcel} />

        <TableFilter total={hospitalDeliveryListData?.total} />
        <CustomTable<HospitalDeliveryDTO>
          key={JSON.stringify({ managerOptions })}
          columns={CPHD_PromotionalMaterialDeliveryColumns({ managerOptions, onUpdateHospitalDelivery })}
          data={hospitalDeliveryListData?.data || []}
          showPaging
          showSelect
          paging={{
            total: hospitalDeliveryListData?.total,
            pageSize: filter.pageSize,
            pageNumber: filter.pageNumber,
          }}
          onPageNumberChange={handlePageChange}
          onChangeTable={handleTableChange}
          onSelect={handleSelect}
          onSelectAll={handleSelectAll}
        />
      </div>

      <ModalConfirm isOpen={isOpen} setIsOpen={setIsOpen} onConfirm={onDeleteHospitalDelivery} isLoading={isDeleting} />
    </div>
  );
};
