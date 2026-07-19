import ModalConfirm from '@/components/common/ConfirmModal';
import DeleteButton from '@/components/common/DeleteButton';
import CustomModal from '@/components/common/modals/CustomModal';
import CustomTable from '@/components/common/table/CustomTable';
import TableFilter from '@/components/common/table/TableFilter';
import { useMenuPermission } from '@/hook/auth/useMenuPermission';
import { useModalConfirm } from '@/hook/useModalConfirm';
import { usePaging } from '@/hook/usePaging';
import { useSelectTable } from '@/hook/useSelectTable';
import { PostDetailDTO, PostDTO } from '@/store/post/dto/post.dto';
import {
  deletePostApi,
  getPostDetailApi,
  getPostListApi,
  postKeys,
  updatePostApi,
  updatePostDetailApi,
} from '@/store/post/post.api';
import {
  initalValues,
  postDetailInputToReq,
  postDetailRespToDto,
  postListFilterFormatRequest,
  postListParamsToFilter,
  postRespToDto,
} from '@/store/post/post.service';
import { PostListFilter, PostUpdateREQ } from '@/store/post/request/post.request';
import { POST_CATEGORIES } from '@/utils/constants/post/post.constant';
import { POST_VISIBILITY } from '@/utils/enums/post/post.enum';
import { MenuKeys } from '@/utils/enums/side-menu-type';
import { handleError } from '@/utils/helpers/notification-error.helper';
import { handleSuccess } from '@/utils/helpers/notification-success.helper';
import { parseArrayToStringRequest } from '@/utils/parser/array-request.parser';
import { initialPagingState, PagingState } from '@/utils/types/paging.type';
import { useUploadFile } from '@/utils/utils/upload-file-to-s3.util';
import { keepPreviousData, useMutation, useQuery, useQueryClient } from '@tanstack/react-query';
import { useSearchParams } from 'next/navigation';
import { useMemo, useRef, useState } from 'react';
import { FormProvider, useForm } from 'react-hook-form';
import { useTranslation } from 'react-i18next';
import PostDetailModal from '../components/PostDetailModal';
import PostTableFilter from '../components/PostTableFilter';
import { PostDataColumns } from '../data-columns/PostDataColumns';

const Post = () => {
  const { t: tMessage } = useTranslation('message');
  const searchParams = useSearchParams();
  const queryClient = useQueryClient();
  const methods = useForm<PostDetailDTO>({
    defaultValues: initalValues,
    // resolver: yupResolver(postSchema) as any,
  });
  const { isOpen, setIsOpen, openModal, closeModal } = useModalConfirm();
  const { selectedRowKeys, setSelectedRowKeys, handleSelect, handleSelectAll } = useSelectTable<PostDTO>();
  const { canEdit } = useMenuPermission(MenuKeys.POST);
  const { uploadFile } = useUploadFile();

  const { initialPaging, initialFilter } = useMemo(() => {
    const initialFilter = postListParamsToFilter(searchParams);
    const initialPaging: PagingState = {
      pageSize: +(searchParams.get('pageSize') || initialPagingState.pageSize),
      pageNumber: +(searchParams.get('pageNumber') || initialPagingState.pageNumber),
    };
    return { initialPaging, initialFilter };
  }, []);

  const { filter, handlePageChange, handleFilterChange, handleTableChange } = usePaging<PostListFilter>({
    initialPaging,
    initialFilter,
    debounceTime: 500,
  });

  const postId = useRef<number>(0);
  const [isOpenDetail, setIsOpenDetail] = useState<boolean>(false);
  const [isOpenExposure, setIsOpenExposure] = useState<boolean>(false);

  const { data: postListData } = useQuery({
    queryKey: postKeys.list(filter),
    queryFn: () => getPostListApi(postListFilterFormatRequest(filter)),
    select: (response) => {
      return { ...response.data, data: response.data.data.map(postRespToDto) };
    },
    placeholderData: keepPreviousData,
  });

  const { mutate: mutateUpdatePost } = useMutation({
    mutationFn: ({ id, body }: { id: number; body: PostUpdateREQ }) => updatePostApi(id, body),
    onSuccess: () => {
      handleSuccess('update');
      queryClient.invalidateQueries({ queryKey: postKeys.lists() });
    },
    onError: handleError,
  });

  const { mutate: mutateDeletePost, isPending: isDeleting } = useMutation({
    mutationFn: (ids: string) => deletePostApi(ids),
    onSuccess: () => {
      closeModal();
      handleSuccess('delete');
      setSelectedRowKeys([]);
      queryClient.invalidateQueries({ queryKey: postKeys.lists() });
    },
    onError: handleError,
  });

  const { data: postDetailData, isFetching } = useQuery({
    queryKey: postKeys.detail(postId.current),
    queryFn: () => (postId.current ? getPostDetailApi(postId.current) : undefined),
    select: (response) => {
      return response?.data.data && postDetailRespToDto(response.data.data);
    },
    placeholderData: keepPreviousData,
  });

  const { mutate: mutateUpdatePostDetail, isPending: isUpdating } = useMutation({
    mutationFn: ({ id, body }: { id: number; body: PostUpdateREQ }) => updatePostDetailApi(id, body),
    onSuccess: () => {
      handleSuccess('update');
      queryClient.invalidateQueries({ queryKey: postKeys.all });
      setIsOpenExposure(false);
    },
    onError: handleError,
  });

  const onDetail = (id: number) => {
    setIsOpenDetail(true);
    postId.current = id;
  };

  const onUpdatePost = (id: number, values: PostUpdateREQ) => {
    mutateUpdatePost({ id, body: values });
  };

  const onDeleteManagerBranch = () => {
    mutateDeletePost(parseArrayToStringRequest(selectedRowKeys));
  };

  const onUpdateDetail = async (values: PostDetailDTO) => {
    if (
      values.categories?.category === POST_CATEGORIES.PRESS_REPORT &&
      values.visibility === POST_VISIBILITY.RELEASE &&
      values.exposureQuantity >= 5
    ) {
      setIsOpenExposure(true);
      return;
    }
    const attachments = values.attachments;
    const thumbnail = values.thumbnail;
    const attachmentIds = attachments && ((await uploadFile({ files: attachments })) as number[]);
    const thumbnailId = thumbnail && ((await uploadFile({ files: thumbnail })) as number[])[0];
    const body = postDetailInputToReq(values, attachmentIds, thumbnailId);
    mutateUpdatePostDetail({ id: values.id, body });
  };

  const onConfirmExposure = async () => {
    const values = methods.getValues();
    const attachments = values.attachments;
    const thumbnail = values.thumbnail;
    const attachmentIds = attachments && ((await uploadFile({ files: attachments })) as number[]);
    const thumbnailId = thumbnail && ((await uploadFile({ files: thumbnail })) as number[])[0];
    const body = postDetailInputToReq(values, attachmentIds, thumbnailId);
    mutateUpdatePostDetail({ id: values.id, body });
  };

  return (
    <div className='mt-4'>
      <TableFilter total={postListData?.total}>
        <PostTableFilter filter={filter} onFilterChange={handleFilterChange} />
      </TableFilter>

      <div className='mt-4 ' id='fixedTable'>
        <CustomTable<PostDTO>
          key={JSON.stringify({ postListData })}
          columns={PostDataColumns({ onDetail, onUpdatePost })}
          data={postListData?.data || []}
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
            total: postListData?.total,
            pageSize: filter.pageSize,
            pageNumber: filter.pageNumber,
          }}
          onPageNumberChange={handlePageChange}
          onChangeTable={handleTableChange}
          onSelect={handleSelect}
          onSelectAll={handleSelectAll}
        />
      </div>

      <FormProvider {...methods}>
        <CustomModal
          isOpen={isOpenDetail}
          isLoading={isUpdating || isFetching}
          onClose={() => {
            setIsOpenDetail(false);
            methods.reset(initalValues);
          }}
          onOk={methods.handleSubmit(onUpdateDetail)}
          title={postDetailData?.title}>
          <PostDetailModal postDetailData={postDetailData} />
        </CustomModal>
      </FormProvider>

      <ModalConfirm isOpen={isOpen} setIsOpen={setIsOpen} onConfirm={onDeleteManagerBranch} isLoading={isDeleting} />
      <ModalConfirm
        message={tMessage('post.exposure')}
        isOpen={isOpenExposure}
        setIsOpen={setIsOpenExposure}
        onConfirm={onConfirmExposure}
      />
    </div>
  );
};

export default Post;
