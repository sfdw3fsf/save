import ModalConfirm from '@/components/common/ConfirmModal';
import CustomTable from '@/components/common/table/CustomTable';
import TotalCard from '@/components/common/TotalCard';
import useDownloadExcel from '@/hook/useDownloadExcel';
import { useModalConfirm } from '@/hook/useModalConfirm';
import { usePaging } from '@/hook/usePaging';
import { useSelectTable } from '@/hook/useSelectTable';
import {
  createHospitalSaleListApi,
  deleteHospitalSaleListApi,
  getHospitalDetailSalesApi,
  hospitalDetailSalesKeys,
} from '@/store/collaborating-hospital/collaborating-hospital.api';
import {
  hospitalDetailSalesRespToDto,
  hospitalSalesListDataDtoToTotal,
} from '@/store/collaborating-hospital/collaborating-hospital.service';
import { HospitalDetailSalesDTO } from '@/store/collaborating-hospital/dto/collaborating-hospital.dto';
import {
  HospitalListFilter,
  HospitalSaleListFilter,
} from '@/store/collaborating-hospital/request/collaborating-hospital.request';
import { EXCEL_FILE_NAME } from '@/utils/constants/excel-name.constant';
import { handleError } from '@/utils/helpers/notification-error.helper';
import { handleSuccess } from '@/utils/helpers/notification-success.helper';
import { parseArrayToStringRequest } from '@/utils/parser/array-request.parser';
import { initialPagingState, PagingState } from '@/utils/types/paging.type';
import { keepPreviousData, useMutation, useQuery, useQueryClient } from '@tanstack/react-query';
import { useMemo } from 'react';
import { useTranslation } from 'react-i18next';
import CPH_TableFilter from '../components/CPH_TableFilter';
import { CPH_SaleActivityColumns } from '../data-columns/CPHD_SaleActivityColumns';

type CPHD_SaleActivitiesProps = {
  id: number;
};
export const CPHD_SaleActivities = ({ id }: CPHD_SaleActivitiesProps) => {
  const { t: tCommon } = useTranslation('common');
  const { t: tPartnerHospital } = useTranslation('collaborating_partner_hospital');
  const queryClient = useQueryClient();
  const { isOpen, setIsOpen, openModal, closeModal } = useModalConfirm();
  const { selectedRowKeys, setSelectedRowKeys, handleSelect, handleSelectAll } = useSelectTable<HospitalDetailSalesDTO>();
  const { mutateDownloadExcel } = useDownloadExcel<HospitalSaleListFilter>({
    fileName: EXCEL_FILE_NAME.HOSPITAL_SALES_MANAGEMENT,
    url: `hospital-sales/hospitals/${id}/exports`,
  });

  const { initialPaging } = useMemo(() => {
    const initialPaging: PagingState = {
      pageSize: initialPagingState.pageSize,
      pageNumber: initialPagingState.pageNumber,
    };
    return { initialPaging };
  }, []);

  const { filter, handlePageChange, handleFilterChange, handleTableChange } = usePaging<HospitalListFilter>({
    initialPaging,
    debounceTime: 500,
    isPushURL: false,
  });

  const { data: hospitalDetailSalesData } = useQuery({
    queryKey: hospitalDetailSalesKeys.list(filter),
    queryFn: () => getHospitalDetailSalesApi(id, filter),
    select: (response) => {
      return { ...response.data, data: response.data.data.map(hospitalDetailSalesRespToDto) };
    },
    placeholderData: keepPreviousData,
  });

  const { mutate: mutateListCreate } = useMutation({
    mutationFn: () => createHospitalSaleListApi(undefined, id),
    onSuccess: () => {
      handleSuccess('create');
      queryClient.invalidateQueries({ queryKey: hospitalDetailSalesKeys.all });
    },
    onError: handleError,
  });

  const { mutate: mutateListDelete, isPending: isDeleting } = useMutation({
    mutationFn: (ids: string) => deleteHospitalSaleListApi(ids),
    onSuccess: () => {
      closeModal();
      setSelectedRowKeys([]);

      handleSuccess('delete');
      queryClient.invalidateQueries({ queryKey: hospitalDetailSalesKeys.all });
    },
    onError: handleError,
  });

  const totalData = useMemo(() => {
    return hospitalSalesListDataDtoToTotal(hospitalDetailSalesData?.data, hospitalDetailSalesData?.total);
  }, [hospitalDetailSalesData]);

  const mockDataCountCard = [
    { title: tPartnerHospital('detail.sales_activities.card.sales_visist'), value: totalData.numerOfSalesVisit },
    { title: tPartnerHospital('detail.sales_activities.card.funeral_visit'), value: totalData.numOfFunerals },
  ];

  const handleListDelete = () => {
    mutateListDelete(parseArrayToStringRequest(selectedRowKeys));
  };

  const onDownloadExcel = () => {
    mutateDownloadExcel({ filter, ids: parseArrayToStringRequest(selectedRowKeys, false) });
  };

  return (
    <div className='mt-6'>
      <div className='grid grid-cols-2 gap-3'>
        {mockDataCountCard.map((item) => (
          <TotalCard key={item.title} className='!w-full' title={item.title} value={item.value} />
        ))}
      </div>

      <div className='flex gap-4 flex-col mt-6'>
        <CPH_TableFilter onFilterChange={handleFilterChange} onDownloadExcel={onDownloadExcel} />
        <div className='text-neutral-80 mt-4'>{`${tCommon('total')} ${hospitalDetailSalesData?.total}${tCommon('number')}`}</div>

        <CustomTable<HospitalDetailSalesDTO>
          columns={CPH_SaleActivityColumns()}
          data={hospitalDetailSalesData?.data || []}
          showPaging
          showSelect
          paging={{
            total: hospitalDetailSalesData?.total,
            pageSize: filter.pageSize,
            pageNumber: filter.pageNumber,
          }}
          onPageNumberChange={handlePageChange}
          onChangeTable={handleTableChange}
          onSelect={handleSelect}
          onSelectAll={handleSelectAll}
        />
      </div>

      <ModalConfirm isOpen={isOpen} setIsOpen={setIsOpen} onConfirm={handleListDelete} isLoading={isDeleting} />
    </div>
  );
};
