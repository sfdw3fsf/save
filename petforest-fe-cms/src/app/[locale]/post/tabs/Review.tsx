import ModalConfirm from '@/components/common/ConfirmModal';
import CustomIconButton from '@/components/common/CustomIconButton';
import CustomText from '@/components/common/Text';
import { useMenuPermission } from '@/hook/auth/useMenuPermission';
import { useArrangeOrdinal } from '@/hook/useArrangeOrdinal';
import { useModalConfirm } from '@/hook/useModalConfirm';
import { ReviewDTO } from '@/store/post/dto/post.dto';
import { deleteReviewApi, getReviewListApi, reviewKeys } from '@/store/post/post.api';
import { reviewRespToDto } from '@/store/post/post.service';
import { COLORS, ICONS } from '@/theme';
import { MenuKeys } from '@/utils/enums/side-menu-type';
import { handleError } from '@/utils/helpers/notification-error.helper';
import { handleSuccess } from '@/utils/helpers/notification-success.helper';
import { InfoCircleOutlined } from '@ant-design/icons';
import { keepPreviousData, useMutation, useQuery, useQueryClient } from '@tanstack/react-query';
import { arrayMoveImmutable } from 'array-move';
import Image from 'next/image';
import { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { SortableGrid } from './SortableGrid';

const Review = () => {
  const { t: tPost } = useTranslation('post');
  const queryClient = useQueryClient();
  const { arrangeOrdinal } = useArrangeOrdinal({ url: 'reviews', queryKey: reviewKeys.lists() });
  const { isOpen, setIsOpen, openModal, closeModal } = useModalConfirm();
  const { canEdit } = useMenuPermission(MenuKeys.POST);

  const [data, setData] = useState<ReviewDTO[]>([]);

  const { data: reviewListData } = useQuery({
    queryKey: reviewKeys.lists(),
    queryFn: () => getReviewListApi(),
    select: (response) => {
      return response.data.data.map(reviewRespToDto);
    },
    placeholderData: keepPreviousData,
  });

  const { mutate: mutateDeleteReview, isPending: isDeleting } = useMutation({
    mutationFn: (id: number) => deleteReviewApi(id),
    onSuccess: () => {
      handleSuccess('delete');
      closeModal();
      queryClient.invalidateQueries({ queryKey: reviewKeys.lists() });
    },
    onError: handleError,
  });

  const onSortEnd = ({ oldIndex, newIndex }: { oldIndex: number; newIndex: number }) => {
    setData((pre) => arrayMoveImmutable(pre || [], oldIndex, newIndex));
    reviewListData && arrangeOrdinal(data, oldIndex, newIndex);
  };

  const onDelete = (id: number) => {
    openModal(id);
  };

  const handleDeleteReview = (ids?: number | number[]) => {
    ids && mutateDeleteReview(ids as number);
  };

  useEffect(() => {
    if (!reviewListData) return;
    setData(reviewListData);
  }, [reviewListData]);

  return (
    <div>
      <div className='mt-[34px] h-[24px] flex items-center gap-[6px]'>
        <InfoCircleOutlined className='text-error-semantic-600' style={{ color: COLORS.red.semantic[600] }} />
        <CustomIconButton disabled className='bg-neutral-20 !cursor-default' icon={<Image src={ICONS.TWO_DRAG} alt='Drag' />} />
        <CustomText variant='caption-1' className='text-neutral-70 font-medium'>
          {tPost('review.change_order')}
        </CustomText>
      </div>

      <div className='mt-[40px]'>
        <SortableGrid
          items={data}
          axis='xy'
          shouldUseDragHandle={canEdit}
          onSortEnd={onSortEnd}
          useDragHandle
          onDelete={onDelete}
        />
      </div>

      <ModalConfirm isOpen={isOpen} setIsOpen={setIsOpen} onConfirm={handleDeleteReview} isLoading={isDeleting} />
    </div>
  );
};

export default Review;
