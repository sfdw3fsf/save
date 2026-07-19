import ModalConfirm from '@/components/common/ConfirmModal';
import DeleteButton from '@/components/common/DeleteButton';
import CustomTable from '@/components/common/table/CustomTable';
import TableFilter from '@/components/common/table/TableFilter';
import CustomText from '@/components/common/Text';
import { useMenuPermission } from '@/hook/auth/useMenuPermission';
import { useModalConfirm } from '@/hook/useModalConfirm';
import { usePaging } from '@/hook/usePaging';
import { useSelectTable } from '@/hook/useSelectTable';
import { PopupDTO } from '@/store/post/dto/post.dto';
import { deletePopupApi, getPopupListApi, popupKeys, updateImagePopupApi, updatePopupApi } from '@/store/post/post.api';
import { popupListFilterFormatRequest, popupListParamsToFilter, popupRespToDto } from '@/store/post/post.service';
import { PopupListFilter, PopupUpdateREQ } from '@/store/post/request/post.request';
import { MenuKeys } from '@/utils/enums/side-menu-type';
import { handleError } from '@/utils/helpers/notification-error.helper';
import { handleSuccess } from '@/utils/helpers/notification-success.helper';
import { initialPagingState, PagingState } from '@/utils/types/paging.type';

import { parseArrayToStringRequest } from '@/utils/parser/array-request.parser';
import { orUndefined } from '@/utils/parser/io.parser';
import { useUploadFile } from '@/utils/utils/upload-file-to-s3.util';
import { InfoCircleOutlined } from '@ant-design/icons';
import { keepPreviousData, useMutation, useQuery, useQueryClient } from '@tanstack/react-query';
import { Spin } from 'antd';
import { UploadFile } from 'antd/lib';
import { useSearchParams } from 'next/navigation';
import { useMemo, useRef, useState } from 'react';
import { useTranslation } from 'react-i18next';
import PopupPreview from '../components/PopupPreviewModal';
import PopupTableFilter from '../components/PopupTableFilter';
import { PopupDataColumns } from '../data-columns/PopupDataColumn';

const Popup = () => {
  const { t: tPost } = useTranslation('post');
  const searchParams = useSearchParams();
  const queryClient = useQueryClient();
  const { canEdit } = useMenuPermission(MenuKeys.POST);
  const { uploadFile, isPending } = useUploadFile();

  const imageIdRef = useRef<number>();
  const popupIdRef = useRef<number>();

  const [isOpenImage, setIsOpenImage] = useState<boolean>(false);
  const { isOpen, setIsOpen, openModal, closeModal } = useModalConfirm();

  const { initialPaging, initialFilter } = useMemo(() => {
    const initialFilter = popupListParamsToFilter(searchParams);

    const initialPaging: PagingState = {
      pageSize: +(searchParams.get('pageSize') || initialPagingState.pageSize),
      pageNumber: +(searchParams.get('pageNumber') || initialPagingState.pageNumber),
    };
    return { initialPaging, initialFilter };
  }, []);

  const { selectedRowKeys, setSelectedRowKeys, handleSelect, handleSelectAll } = useSelectTable<PopupDTO>();
  const { filter, handlePageChange, handleFilterChange, handleTableChange } = usePaging<PopupListFilter>({
    initialPaging,
    initialFilter,
    debounceTime: 500,
  });

  const { data: popupListData } = useQuery({
    queryKey: popupKeys.list(filter),
    queryFn: () => getPopupListApi(popupListFilterFormatRequest(filter)),
    select: (response) => {
      return { ...response.data, data: response.data.data.map(popupRespToDto) };
    },
    placeholderData: keepPreviousData,
  });

  const { mutate: mutateUpdatePopup } = useMutation({
    mutationFn: ({ id, body }: { id: number; body: PopupUpdateREQ }) => updatePopupApi(id, body),
    onSuccess: () => {
      closeModal();
      handleSuccess('update');
      queryClient.invalidateQueries({ queryKey: popupKeys.lists() });
    },
    onError: handleError,
  });

  const { mutate: mutateDeletePopup, isPending: isDeleting } = useMutation({
    mutationFn: (ids: string) => deletePopupApi(ids),
    onSuccess: () => {
      closeModal();
      handleSuccess('delete');
      setSelectedRowKeys([]);
      queryClient.invalidateQueries({ queryKey: popupKeys.lists() });
    },
    onError: handleError,
  });

  const { mutate: mutateUpdateImagePopup, isPending: isUpdating } = useMutation({
    mutationFn: ({ id, imageId }: { id: number; imageId: number }) => updateImagePopupApi(id, imageId),
    onSuccess: () => {
      handleSuccess('update');
      queryClient.invalidateQueries({ queryKey: popupKeys.lists() });
    },
    onError: handleError,
  });

  const isLoading = useMemo(() => {
    return isDeleting || isUpdating || isPending;
  }, [isDeleting, isUpdating, isPending]);

  const onOpenImageModal = (imageId: number | null, popupId: number) => {
    imageIdRef.current = orUndefined(imageId);
    popupIdRef.current = orUndefined(popupId);
    setIsOpenImage(true);
  };

  const onUpdatePopup = (id: number, values: PopupUpdateREQ) => {
    mutateUpdatePopup({ id, body: values });
  };

  const onDeletePopup = () => {
    mutateDeletePopup(parseArrayToStringRequest(selectedRowKeys));
  };

  const onUploadFile = async (id: number, files: UploadFile<any>[]) => {
    const imageId = await uploadFile({ files });
    imageId[0] && mutateUpdateImagePopup({ id, imageId: imageId[0] as number });
  };

  return (
    <div className='mt-4'>
      <div className='mt-[34px] h-[24px] flex items-center gap-[6px]'>
        <InfoCircleOutlined className='text-error-semantic-600' />
        <CustomText variant='caption-1' className='text-neutral-70 font-medium'>
          {tPost('popup.image_size')}
        </CustomText>
      </div>

      <div className='mt-4'>
        <TableFilter total={popupListData?.total}>
          <PopupTableFilter filter={filter} onFilterChange={handleFilterChange} />
        </TableFilter>
      </div>

      <div className='mt-4 ' id='fixedTable'>
        <CustomTable<PopupDTO>
          columns={PopupDataColumns({ onOpenImageModal, onUpdatePopup, onUploadFile })}
          data={popupListData?.data || []}
          showPaging
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
          paging={{
            total: popupListData?.total,
            pageSize: filter.pageSize,
            pageNumber: filter.pageNumber,
          }}
          onPageNumberChange={handlePageChange}
          onChangeTable={handleTableChange}
          onSelect={handleSelect}
          onSelectAll={handleSelectAll}
        />
      </div>

      <PopupPreview
        imageId={imageIdRef.current}
        isOpen={isOpenImage}
        onClose={() => {
          setIsOpenImage(false);
        }}
        onSubmit={() => {
          if (!popupIdRef.current) return;
          mutateUpdatePopup({ id: popupIdRef.current, body: { isWatched: true } });
        }}
      />

      <ModalConfirm isOpen={isOpen} setIsOpen={setIsOpen} onConfirm={onDeletePopup} isLoading={isDeleting} />
      <Spin spinning={isLoading} fullscreen />
    </div>
  );
};

export default Popup;
