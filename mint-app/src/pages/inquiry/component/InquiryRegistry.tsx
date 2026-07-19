import Button from '@/components/core/button/Button';
import Card from '@/components/core/card/Card';
import { DropdownItem } from '@/components/core/dropdown/Dropdown';
import FormField from '@/components/core/form/form-field/FormField';
import { SuccessToast } from '@/components/core/success-notification/SuccessNotification';
import { createInquiry, getInquiryCategory } from '@/data/inquiry/inquiry.api';
import { InquiryCreateREQ, InquiryREQ } from '@/data/inquiry/request/inquiry.request';
import { InquiryCategory } from '@/data/inquiry/response/inquiry.response';
import { BaseResponse } from '@/types/response.type';
import { QUERY_KEYS } from '@/utils/constants/data.constant';
import { yupResolver } from '@hookform/resolvers/yup';
import { useMutation, useQuery, useQueryClient } from '@tanstack/react-query';
import { enqueueSnackbar } from 'notistack';
import { useEffect } from 'react';
import { useForm } from 'react-hook-form';
import { useTranslation } from 'react-i18next';
import * as yup from 'yup';

type InquiryRegistryFormValues = {
  categoryId: number;
  subject: string;
  detail: string;
};

const mapInquiryCategoryToDropdown = (categories: InquiryCategory[]): DropdownItem[] => {
  return categories.map((category) => ({
    label: category.name,
    value: category.id,
  }));
};

export const InquiryRegistry = (): JSX.Element => {
  const { t: tFaq } = useTranslation('faq');
  const queryClient = useQueryClient();
  const inquirySchema = yup.object().shape({
    categoryId: yup.number().required(tFaq('inquirySection.inquiryCategoryRequired')),
    subject: yup.string().required(tFaq('inquirySection.inquirySubjectRequired')),
    detail: yup.string().required(tFaq('inquirySection.inquiryDetailsRequired')),
  });
  const {
    handleSubmit,
    control,
    reset,
    formState: { isSubmitSuccessful },
  } = useForm<InquiryRegistryFormValues>({
    resolver: yupResolver(inquirySchema),
  });

  const { mutate: createInquiryApi, isPending } = useMutation({
    mutationFn: (request: InquiryREQ) => createInquiry(request),

    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: [QUERY_KEYS.INQUIRIES] });
      SuccessToast({
        label: tFaq('inquirySection.inquiry_success_label'),
        content: tFaq('inquirySection.inquiry_success_label'),
      });
    },
    onError: () => {
      enqueueSnackbar({ message: tFaq('inquirySection.inquiry_error_label'), variant: 'error' });
    },
  });

  const { data: InquiryCategory } = useQuery<BaseResponse<InquiryCategory[]>>({
    queryKey: [QUERY_KEYS.INQUIRY_CATEGORIES],
    queryFn: () => getInquiryCategory(),
  });

  const dropdownItems: DropdownItem[] = InquiryCategory?.data ? mapInquiryCategoryToDropdown(InquiryCategory.data) : [];
  const onSubmit = async ({ categoryId, subject, detail }: InquiryCreateREQ) => {
    const request: InquiryCreateREQ = { categoryId, subject, detail };
    createInquiryApi(request);
  };
  useEffect(() => {
    if (isSubmitSuccessful) {
      reset({
        subject: '',
        detail: '',
      });
    }
  }, [isSubmitSuccessful, reset]);
  return (
    <div className='flex justify-center w-full '>
      <Card className='max-w-[544px]'>
        <form id='inquiry-form' className='w-full max-w-[544px] flex flex-col gap-10' onSubmit={handleSubmit(onSubmit)}>
          <div className='flex flex-col gap-6'>
            <div className='flex flex-col '>
              <FormField
                type='drop-down'
                label={tFaq('inquirySection.inquiryCategoryLabel')}
                className=''
                componentProps={{
                  name: 'categoryId',
                  control,
                  items: dropdownItems,
                  required: true,
                  placeholder: tFaq('inquirySection.inquiryCategory'),
                }}
              />
            </div>
            <FormField
              type='input'
              label={tFaq('inquirySection.inquirySubjectLabel')}
              className=''
              componentProps={{
                name: 'subject',
                control,
                required: true,
                placeholder: tFaq('inquirySection.inquirySubject'),
              }}
            />
            <FormField
              label={tFaq('inquirySection.inquiryDetailsLabel')}
              type='text-area'
              componentProps={{
                name: 'detail',
                control,
                sizing: 'lg',
                placeholder: tFaq('inquirySection.inquiryDetails'),
                disabledResize: true,
                required: true,
              }}
            />
          </div>
          <Button isLoading={isPending} form={'inquiry-form'} variant='primary' type='submit'>
            {tFaq('inquirySection.registration')}
          </Button>
        </form>
      </Card>
    </div>
  );
};
