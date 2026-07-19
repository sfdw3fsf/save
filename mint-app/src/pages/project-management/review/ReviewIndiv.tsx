import Button from '@/components/core/button/Button';
import FormField from '@/components/core/form/form-field/FormField';
import { SuccessToast } from '@/components/core/success-notification/SuccessNotification';
import HeatbeatIcon from '@/components/icons/HeartbeatIcon';
import { createFeedbacksAPI, deleteFeedbacksAPI, editFeedbacksAPI } from '@/data/feedbacks/feedbacks.api';
import { EditFeedbacksREQ, FeedbacksREQ } from '@/data/feedbacks/request/feedbacks.request';
import { FeedbacksRESP } from '@/data/feedbacks/response/feedbacks.response';
import InfoCard from '@/pages/project-request/project-request-indiv/components/InfoCard';
import { onError } from '@/utils/helpers/data.helper';
import { FeedbacksInput, feedbacksSchema } from '@/utils/schemas/project-request.schema';
import { yupResolver } from '@hookform/resolvers/yup';
import { Divider } from '@mantine/core';
import { useMutation } from '@tanstack/react-query';
import { useEffect, useMemo } from 'react';
import { useForm } from 'react-hook-form';
import { useTranslation } from 'react-i18next';

const formID = 'review-form';

type ReviewIndivProps = {
  projectId: number;
  initialValues?: FeedbacksRESP;
  onReload?: () => void;
};

const ReviewIndiv = ({ initialValues, projectId, onReload }: ReviewIndivProps): JSX.Element => {
  const { t: tCommon } = useTranslation('common');
  const { t: tForm } = useTranslation('form');

  // APIS
  const { mutate: createFeedbacksAPi, isPending: isCreating } = useMutation({
    mutationFn: (request: FeedbacksREQ) => createFeedbacksAPI(request),
    onSuccess: () => {
      onReload?.();
      SuccessToast({
        label: tForm('success.feedbacks.title'),
        content: tForm('success.feedbacks.subTitle'),
      });
    },
    onError,
  });

  const { mutate: editFeedbacksAPi, isPending: isEditing } = useMutation({
    mutationFn: (request: EditFeedbacksREQ) => editFeedbacksAPI(request),
    onSuccess: () => {
      onReload?.();
      SuccessToast({
        label: tForm('success.feedbacks.title'),
        content: tForm('success.feedbacks.subTitle'),
      });
    },
    onError,
  });

  const { mutate: deleteFeedbacksAPi, isPending: isDeleting } = useMutation({
    mutationFn: () => deleteFeedbacksAPI(initialValues?.id as number),
    onSuccess: () => {
      onReload?.();
    },
    onError,
  });

  // FORMS
  const { control, handleSubmit, reset } = useForm<FeedbacksInput>({
    resolver: yupResolver<FeedbacksInput>(feedbacksSchema),
  });

  // METHODS
  const onSubmit = handleSubmit(async (data: FeedbacksInput) => {
    if (initialValues?.id) {
      if (!!data.title || !!data.content) {
        editFeedbacksAPi({
          feedbackId: initialValues?.id,
          ...data,
        });
      }
    } else {
      if (!!data.title || !!data.content) {
        createFeedbacksAPi({
          projectId,
          ...data,
        });
      }
    }
  });

  const onDelete = async () => {
    deleteFeedbacksAPi();
  };

  // EFFECTS
  useEffect(() => {
    if (initialValues) {
      reset(initialValues);
    } else {
      reset({ ...feedbacksSchema.getDefault() });
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [initialValues]);

  // RENDER
  const renderButtons = useMemo(() => {
    if (!initialValues) {
      return (
        <Button className='mb-2' form={formID} isLoading={isCreating}>
          {tCommon('review.register_review')}
        </Button>
      );
    } else {
      return (
        <div className='flex w-full gap-2'>
          <Button
            className='w-full'
            rightIcon={<HeatbeatIcon />}
            labelContStyle='gap-1'
            onClick={onDelete}
            isLoading={isDeleting}>
            {tCommon('review.delete')}
          </Button>
          <Button className='w-full' form={formID} rightIcon={<HeatbeatIcon />} labelContStyle='gap-1' isLoading={isEditing}>
            {tCommon('review.edit')}
          </Button>
        </div>
      );
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [initialValues, isCreating, isEditing, isDeleting, tCommon, tForm]);

  return (
    <form id={formID} className='flex flex-col' onSubmit={onSubmit}>
      <div className='text-slate-500 text-md md:text-lg mb-2'>{tCommon('review.review')}</div>
      <InfoCard title={tCommon('review.review_content')} childrenCont={'flex flex-col px-4 my-2 gap-4'}>
        <FormField
          type='input'
          label={tCommon('review.review_title')}
          componentProps={{
            control,
            required: false,
            name: 'title',
            placeholder: tCommon('review.title_placeholder'),
          }}
        />
        <FormField
          type='text-area'
          label={tCommon('review.content')}
          componentProps={{
            control,
            required: false,
            name: 'content',
            placeholder: tCommon('review.content_placeholder'),
          }}
        />
        <Divider />
        {renderButtons}
      </InfoCard>
    </form>
  );
};

export default ReviewIndiv;
