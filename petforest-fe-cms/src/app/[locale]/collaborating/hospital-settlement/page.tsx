'use client';
import CustomButton from '@/components/common/CustomButton';
import { CustomTabsFilterButton } from '@/components/common/CustomTabsFilterButton';
import PageTitle from '@/components/common/page/PageTitle';
import CustomTable from '@/components/common/table/CustomTable';
import TableFilter from '@/components/common/table/TableFilter';
import TotalCard from '@/components/common/TotalCard';
import { useBranch } from '@/hook/branch/useBranch';
import { useHospitalSettlementMethod } from '@/hook/setting/useHospitalSettlementMethod';
import { usePaging } from '@/hook/usePaging';
import {
  getHospitalSettlementListApi,
  getHospitalSettlementMethodSummaryApi,
  hospitalSettlementKeys,
  hospitalSettlementMethodSummaryKeys,
  sendEmailHospitalSettlementApi,
  updateBulkSettlementDateApi,
} from '@/store/collaborating-hospital/collaborating-hospital.api';
import {
  hospitalSettlementMethodSummaryRespToDto,
  hospitalSettlementParamsToFilter,
  hospitalSettlementRespToDto,
} from '@/store/collaborating-hospital/collaborating-hospital.service';
import { HospitalSettlementDTO } from '@/store/collaborating-hospital/dto/collaborating-hospital.dto';
import {
  HospitalSettlementBulkREQ,
  HospitalSettlementListFilter,
  HospitalSettlementListREQ,
  HospitalSettlementSendEmailREQ,
} from '@/store/collaborating-hospital/request/collaborating-hospital.request';
import { ICONS } from '@/theme';
import { ButtonTypeEnum } from '@/utils/enums/button-type.enum';
import { DOWNLOAD_FUNERAL_STATEMENT } from '@/utils/types/collaborating/hospital-settlement.type';
import { OptionItems } from '@/utils/types/option.type';
import { initialPagingState, PagingState } from '@/utils/types/paging.type';
import { VerticalAlignBottomOutlined } from '@ant-design/icons';
import { keepPreviousData, useMutation, useQuery, useQueryClient } from '@tanstack/react-query';
import Image from 'next/image';

import CenteredModal from '@/components/common/CenteredModal';
import { recipientRequirePopup } from '@/components/popup/RecipientRequirePopup';
import useDownloadExcel from '@/hook/useDownloadExcel';
import { useSelectTable } from '@/hook/useSelectTable';
import { EXCEL_FILE_NAME } from '@/utils/constants/excel-name.constant';
import { EXCEL_URL } from '@/utils/constants/excel-url.constant';
import { getCurrentMonthRange } from '@/utils/helpers/date-time.helper';
import { handleError } from '@/utils/helpers/notification-error.helper';
import { handleSuccess } from '@/utils/helpers/notification-success.helper';
import { parseArrayToStringRequest } from '@/utils/parser/array-request.parser';
import dayjs, { Dayjs } from 'dayjs';
import { useSearchParams } from 'next/navigation';
import { useEffect, useMemo, useState } from 'react';
import { FormProvider, useForm } from 'react-hook-form';
import { useTranslation } from 'react-i18next';
import { CHS_ChangeDateModal } from './components/CHS_ChangeDateModal';
import { CHS_DetailModal } from './components/CHS_DetailModal';
import CHS_TableFilter from './components/CHS_TableFilter';
import { CHS_Columns } from './data-columns/CHS_Columns';

const HospitalSettlementList = () => {
  const { t: tCommon } = useTranslation('common');
  const { t: tSettlement } = useTranslation('collaborating_settlement');
  const searchParams = useSearchParams();
  const queryClient = useQueryClient();
  const { settlementMethodOptions } = useHospitalSettlementMethod();
  const [startDate, endDate] = getCurrentMonthRange();
  const [bulkChangeDate, setBulkChangeDate] = useState<Dayjs | null>(dayjs().startOf('day'));
  const [selectedMethod, setSelectedMethod] = useState<string>();

  const initialValues: DOWNLOAD_FUNERAL_STATEMENT = {
    hospitalId: undefined,
    funeralDate: [startDate, endDate],
  };

  const { mutateDownloadExcel } = useDownloadExcel<HospitalSettlementListREQ>({
    fileName: EXCEL_FILE_NAME.HOSPITAL_SETTLEMENT,
    url: EXCEL_URL.HOSPITAL_SETTLEMENT,
  });

  const { selectedRowKeys, setSelectedRowKeys, handleSelect, handleSelectAll } = useSelectTable<HospitalSettlementDTO>();

  const [isOpenSendEmail, setIsOpenSendEmail] = useState<boolean>(false);
  const [isOpenChangeDate, setIsOpenChangeDate] = useState<boolean>(false);
  const { branchOptions } = useBranch();
  const methods = useForm<DOWNLOAD_FUNERAL_STATEMENT>({
    defaultValues: initialValues,
  });
  const { handleSubmit, setValue } = methods;

  const branchTabs: OptionItems<number | string> = useMemo(() => {
    if (!branchOptions) return [];
    return [{ label: tCommon('entire'), value: 'All' }, ...branchOptions];
  }, [branchOptions]);

  const { initialPaging, initialFilter } = useMemo(() => {
    const initialFilter = hospitalSettlementParamsToFilter(searchParams);
    const initialPaging: PagingState = {
      pageSize: +(searchParams.get('pageSize') || initialPagingState.pageSize),
      pageNumber: +(searchParams.get('pageNumber') || initialPagingState.pageNumber),
    };
    return { initialPaging, initialFilter };
  }, []);

  const { filter, handlePageChange, handleFilterChange, handleTableChange } = usePaging<HospitalSettlementListFilter>({
    initialPaging,
    initialFilter,
    debounceTime: 500,
  });

  const filterObject = JSON.parse(filter.filter || '{}');
  const startDateFilter = filterObject['reservation.funeralInformation.funeralDate.gte'];
  const endDateFilter = filterObject['reservation.funeralInformation.funeralDate.lte'];

  const { data: hospitalSettlementListData } = useQuery({
    queryKey: hospitalSettlementKeys.list(filter),
    queryFn: () => getHospitalSettlementListApi(filter),
    select: (response) => {
      return { ...response.data, data: response.data.data.filter(Boolean).map(hospitalSettlementRespToDto) };
    },
    placeholderData: keepPreviousData,
  });

  const { data: hospitalSettlementMethodSummary } = useQuery({
    queryKey: [hospitalSettlementMethodSummaryKeys.lists(), startDateFilter, endDateFilter],
    queryFn: () =>
      getHospitalSettlementMethodSummaryApi({
        startDate: startDateFilter?.toString(),
        endDate: endDateFilter?.toString(),
      }),
    select: (response) => {
      return response.data.data && hospitalSettlementMethodSummaryRespToDto(response.data.data, tCommon);
    },
    placeholderData: keepPreviousData,
  });

  const { mutate: mutateSendEmail, isPending: isSending } = useMutation({
    mutationFn: ({ id, body }: { id: number; body: HospitalSettlementSendEmailREQ }) => sendEmailHospitalSettlementApi(id, body),
    onSuccess: () => {
      handleSuccess('email');
    },
    onError: handleError,
  });

  const { mutate: mutateUpdateBulk } = useMutation({
    mutationFn: (body: HospitalSettlementBulkREQ) => updateBulkSettlementDateApi(body),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: hospitalSettlementKeys.lists() });
      handleSuccess('update');
      setSelectedRowKeys([]);
      setIsOpenChangeDate(false);
    },
    onError: handleError,
  });

  const onDetail = () => {
    setIsOpenSendEmail(true);
  };

  const onCloseDetail = () => {
    setIsOpenSendEmail(false);
  };
  const onSubmit = (data: DOWNLOAD_FUNERAL_STATEMENT) => {
    const body: HospitalSettlementSendEmailREQ = { startDate: data.funeralDate[0], endDate: data.funeralDate[1] };
    if (data.hospitalId) {
      mutateSendEmail({ id: data.hospitalId, body });
    }
  };

  const handleOpenSummaryTable = () => {
    window.open('/collaborating/hospital-settlement/summary', '_blank', `noopener,noreferrer`);
  };

  const onChangeTab = (value: number | string) => {
    const newTableFilter = JSON.parse(filter?.filter || '');
    newTableFilter['hospital.hospitalBranches.some.branchId.in'] = value === 'All' ? '' : [value];

    handleFilterChange({ filter: JSON.stringify(newTableFilter) });
  };

  const onDownloadExcel = () => {
    mutateDownloadExcel({ filter, ids: parseArrayToStringRequest(selectedRowKeys, false) });
  };

  const onChangeDateSubmit = () => {
    if (!bulkChangeDate) {
      return;
    }
    mutateUpdateBulk({ ids: selectedRowKeys as number[], settlementDate: bulkChangeDate.valueOf() });
  };

  const onChangeDate = (date: Dayjs | null) => {
    setBulkChangeDate(date);
  };

  const onSelectMethod = (value?: string) => {
    setSelectedMethod(value);
    const obj = JSON.parse(filter?.filter ?? '');
    const settlementMethodId = settlementMethodOptions.find((item) => item.label === value)?.value;
    handleFilterChange(
      {
        filter: JSON.stringify({ ...obj, 'hospitalEntitlement.id.in': settlementMethodId ? [settlementMethodId] : undefined }),
      },
      true,
    );
  };

  useEffect(() => {
    const changeValues = filter.filter ? JSON.parse(filter?.filter ?? '') : {};
    const startDate = changeValues['reservation.funeralInformation.funeralDate.gte'];
    const endDate = changeValues['reservation.funeralInformation.funeralDate.lte'];
    if (startDate && endDate) setValue('funeralDate', [startDate, endDate]);
  }, [filter.filter, setValue]);

  return (
    <>
      <PageTitle
        title={tSettlement('title')}
        actions={
          <div className='!hidden gap-2 lg:!inline-flex'>
            <CustomButton
              type={ButtonTypeEnum.GHOST}
              onClick={onDetail}
              icon={<VerticalAlignBottomOutlined className='!text-primary-1000' />}>
              {tSettlement('button.download_funeral_statement_and_send_email')}
            </CustomButton>
            <CustomButton
              type={ButtonTypeEnum.GHOST}
              onClick={() => handleOpenSummaryTable()}
              icon={<Image src={ICONS.LOOK_GREEN} width={20} height={20} alt='summary' />}>
              {tSettlement('button.settlement_summary')}
            </CustomButton>
          </div>
        }
      />

      <CustomTabsFilterButton classname='!justify-start my-4 md:flex' optionTabs={branchTabs} onChangeTabs={onChangeTab} />

      <div className='mt-6 flex overflow-x-auto gap-2.5'>
        {hospitalSettlementMethodSummary?.map((item) => (
          <TotalCard
            key={item.title}
            className='!min-w-[196px] inline-flex whitespace-nowrap'
            numberClassName='mr-1'
            title={item.title}
            value={item.value}
            separator='/'
            isSelected={selectedMethod === item.title}
            onSelect={() => {
              if (item.title === selectedMethod) {
                onSelectMethod(undefined);
              } else {
                onSelectMethod(item.title);
              }
            }}
          />
        ))}
      </div>
      <div className='my-6'>
        <TableFilter total={hospitalSettlementListData?.total}>
          <CHS_TableFilter
            onOpenSummaryTable={handleOpenSummaryTable}
            onFilterChange={handleFilterChange}
            onDownloadExcel={onDownloadExcel}
          />
        </TableFilter>
      </div>

      <div className='mt-4 ' id='fixedTable'>
        <CustomTable<HospitalSettlementDTO>
          key={JSON.stringify({ settlementMethodOptions })}
          columns={CHS_Columns({ settlementMethodOptions })}
          data={hospitalSettlementListData?.data || []}
          showSelect
          showIndex
          paging={{
            total: hospitalSettlementListData?.total,
            pageSize: filter.pageSize,
            pageNumber: filter.pageNumber,
          }}
          onPageNumberChange={handlePageChange}
          onChangeTable={handleTableChange}
          onSelect={handleSelect}
          onSelectAll={handleSelectAll}
          extra={
            <div className='flex flex-col gap-2'>
              <CustomButton
                className='p-4 '
                onClick={() => {
                  if (selectedRowKeys.length === 0) {
                    recipientRequirePopup();
                    return;
                  }
                  setIsOpenChangeDate(true);
                }}
                type={ButtonTypeEnum.GHOST}
                icon={<Image src={ICONS.BULK_CHANGE} width={20} height={20} alt='summary' />}>
                {tSettlement('detail.bulk')}
              </CustomButton>
              <CustomButton
                className='p-4 lg:hidden'
                type={ButtonTypeEnum.GHOST}
                onClick={onDetail}
                icon={<VerticalAlignBottomOutlined className='!text-primary-1000' />}>
                {tSettlement('button.download_funeral_statement_and_send_email')}
              </CustomButton>
            </div>
          }
        />
      </div>

      <CenteredModal
        isOpen={isOpenChangeDate}
        onOk={onChangeDateSubmit}
        onCancel={() => {
          setIsOpenChangeDate(false);
        }}
        title={tSettlement('detail.change_date')}
        confirmLoading={isSending}>
        <CHS_ChangeDateModal onChangeDate={onChangeDate} />
      </CenteredModal>

      <FormProvider {...methods}>
        <form>
          <CenteredModal
            isOpen={isOpenSendEmail}
            onOk={handleSubmit(onSubmit)}
            onCancel={onCloseDetail}
            okText={tSettlement('detail.send_email')}
            title={tSettlement('detail.funeral_statement')}
            confirmLoading={isSending}>
            <CHS_DetailModal />
          </CenteredModal>
        </form>
      </FormProvider>
    </>
  );
};
export default HospitalSettlementList;
