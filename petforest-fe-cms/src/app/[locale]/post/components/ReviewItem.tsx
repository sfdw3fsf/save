import CustomButton from '@/components/common/CustomButton';
import DeleteButton from '@/components/common/DeleteButton';
import CustomFormInput from '@/components/common/form/CustomFormField';
import CustomText from '@/components/common/Text';
import { useMenuPermission } from '@/hook/auth/useMenuPermission';
import { ReviewDTO } from '@/store/post/dto/post.dto';
import { reviewKeys, updateReviewApi } from '@/store/post/post.api';
import { ReviewUpdateREQ } from '@/store/post/request/post.request';
import { ICONS } from '@/theme';
import { InputEnum } from '@/utils/enums/input.enum';
import { MenuKeys } from '@/utils/enums/side-menu-type';
import { handleError } from '@/utils/helpers/notification-error.helper';
import { handleSuccess } from '@/utils/helpers/notification-success.helper';
import { EditOutlined } from '@ant-design/icons';
import { useMutation, useQueryClient } from '@tanstack/react-query';
import { Divider } from 'antd';
import Image from 'next/image';
import { useRef, useState } from 'react';
import { useForm } from 'react-hook-form';
import { useTranslation } from 'react-i18next';
import { Handle } from '../tabs/SortableGrid';

type ReviewItemProps = {
  index: number;
  shouldUseDragHandle?: boolean;
  onDelete: (id: number) => void;
} & Omit<ReviewDTO, 'ordinal'>;

const ReviewItem = ({ index, title, content, reviewerName, shouldUseDragHandle, onDelete, id }: ReviewItemProps) => {
  const { t: tCommon } = useTranslation('common');
  const { t: tPost } = useTranslation('post');
  const queryClient = useQueryClient();
  const { canEdit } = useMenuPermission(MenuKeys.POST);

  const { control, handleSubmit, reset } = useForm<ReviewUpdateREQ>({
    defaultValues: {
      title,
      content,
      reviewerName,
    },
  });

  const reivewIdRef = useRef<number>();

  const [isEdit, setIsEdit] = useState<boolean>(false);

  const { mutate: mutateUpdateReview } = useMutation({
    mutationFn: (data: { id: number; body: ReviewUpdateREQ }) => updateReviewApi(data.id, data.body),
    onSuccess: (data) => {
      reset(data.data.data);
      setIsEdit(false);
      handleSuccess('update');
      queryClient.invalidateQueries({ queryKey: reviewKeys.lists() });
    },
    onError: handleError,
  });

  const onSave = (values: ReviewUpdateREQ) => {
    reivewIdRef.current && mutateUpdateReview({ id: reivewIdRef.current, body: values });
  };

  return !isEdit ? (
    <div className=' bg-white w-full md:w-[calc(50%-24px)] lg:w-[calc(33.333333%-42.666667px)]'>
      <div className='flex justify-between items-center py-[6px]'>
        <span>{index}</span>
        {shouldUseDragHandle && <Handle />}
      </div>

      <Divider className='mt-4' />

      <div className='min-h-[332px]'>
        <div className='flex flex-col gap-4 min-h-[250px] lg:min-h-[340px]'>
          <CustomText variant='title-3' className='!font-semibold min-h-[64px]'>
            {title}
          </CustomText>
          <CustomText variant='headline-1' className='text-neutral-80 !font-medium min-h-[132px]'>
            {content}
          </CustomText>
          <CustomText variant='headline-1' className='text-neutral-80 !font-medium'>
            {reviewerName}
          </CustomText>
        </div>
      </div>

      <Divider />

      {canEdit && (
        <div className='flex justify-end'>
          <div className='flex items-center gap-3'>
            <DeleteButton
              onClick={() => {
                onDelete(id);
              }}
            />
            <CustomButton
              onClick={() => {
                reivewIdRef.current = id;
                setIsEdit(true);
              }}
              type={'default'}
              icon={<EditOutlined />}>
              Edit
            </CustomButton>
          </div>
        </div>
      )}
    </div>
  ) : (
    <div className=' bg-white w-full md:w-[calc(50%-24px)] lg:w-[calc(33.333333%-42.666667px)]'>
      <div className='flex justify-between items-center py-[6px]'>
        <span>{index}</span>
        <Image src={ICONS.TWO_DRAG} alt='Drag' width={24} height={24} className='cursor-move' />
      </div>

      <Divider className='mt-4' />
      <div className='h-[332px]'>
        <div className='flex flex-col gap-4 h-[250px] lg:h-[340px]'>
          <CustomFormInput
            control={control}
            name='title'
            label={tPost('review.title')}
            type={InputEnum.TEXT_AREA}
            classNameInput='!text-title-3 !h-[80px]'
          />
          <CustomFormInput
            control={control}
            name='content'
            label={tPost('review.content')}
            type={InputEnum.TEXT_AREA}
            classNameInput='!text-headline-1 !h-[66px]'
          />
          <CustomFormInput
            control={control}
            name='reviewerName'
            label={tPost('review.reviewer')}
            type={InputEnum.TEXT}
            classNameInput='!text-headline-1'
          />
        </div>
      </div>

      <Divider />

      <div className='flex justify-between lg:justify-end gap-3'>
        <CustomButton
          className='flex-1 lg:flex-none'
          onClick={() => {
            reivewIdRef.current = undefined;
            setIsEdit(false);
          }}
          type={'default'}>
          {tCommon('button.cancel')}
        </CustomButton>
        <CustomButton className='flex-1 lg:flex-none' onClick={handleSubmit(onSave)} type={'primary'}>
          {tCommon('button.save')}
        </CustomButton>
      </div>
    </div>
  );
};

export default ReviewItem;
