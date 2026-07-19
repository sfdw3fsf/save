import Button from '@/components/core/button/Button';
import FormField from '@/components/core/form/form-field/FormField';
import { FormOptionValuesItem } from '@/components/core/form/form-option-group/FormOptionGroup';
import Spinner from '@/components/core/spinner/Spinner';
import { SuccessToast } from '@/components/core/success-notification/SuccessNotification';
import PermissionAccept from '@/components/hocs/PermissionAccept';
import { businessFieldCategoriesAPI } from '@/data/account/account.api';
import { transformBusinessFieldCategoryResponse } from '@/data/account/account.service';
import { BusinessFieldCategoryRESP } from '@/data/account/response/business-field-category.response';
import { GeneratePresignedUrlREQ } from '@/data/storage/request/generate-presigned-url.request';
import { generatePresignedUrlAPI } from '@/data/storage/storage.api';
import { UserREQ } from '@/data/user/request/user-details-request';
import { userDetailsAPI, userDetailsUpdateAPI } from '@/data/user/user.api';
import i18n from '@/i18n';
import { BaseResponse } from '@/types/response.type';
import { S3File } from '@/types/s3-file.type';
import { UserImage } from '@/types/user-info.type';
import { QUERY_KEYS } from '@/utils/constants/data.constant';
import { USER_ROLE } from '@/utils/enums/user/user-role.enum';
import { getStorageContentType } from '@/utils/helpers/file.helper';
import { MemberInformationSchema } from '@/utils/schemas/my-page-schema';
import { yupResolver } from '@hookform/resolvers/yup';
import { useMutation, useQuery } from '@tanstack/react-query';
import { useEffect, useState } from 'react';
import { useForm } from 'react-hook-form';
import { useTranslation } from 'react-i18next';
import { useNavigate } from 'react-router-dom';
import ChangeEmailModal from './changeEmail/ChangeEmailModal';
import ChangePhoneNumberModal from './changePhone/ChangePhoneNumberModal';
import MyPageContainer from './MyPageContainer';

type MemberInformationFormValues = {
  companyName: string;
  fullName: string;
  personInCharge: string;
  internalEmail: string;
  departmentName?: string;
  businessFieldCategoryIds: number[];
  attachments?: any;
};

type MemberInformationRequest = {
  companyName: string;
  fullName?: string;
  personInCharge?: string;
  internalEmail?: string;
  departmentName?: string;
  avatar?: UserImage;
  businessFieldCategoryIds: number[];
  attachments?: any;
};
const FORM_ID = 'member-information-form';

const MemberInformation = PermissionAccept(
  (): JSX.Element => {
    const { t: tCommon } = useTranslation('common');
    const { t: tForm } = useTranslation('form');
    const { control: control2 } = useForm();

    // const [files, setFiles] = useState<File[]>([]);
    const [file, setFile] = useState<File>();
    const [isUploadingFile, setIsUploadingFile] = useState<boolean>(false);
    const [openChangePhoneNumber, setOpenChangePhoneNumber] = useState<boolean>(false);
    const [openChangeEmail, setOpenChangeEmail] = useState<boolean>(false);
    const navigate = useNavigate();

    // Fetch business field categories
    const { data: businessFieldCategories = [] } = useQuery({
      queryKey: [QUERY_KEYS.BUSINESS_FIELD_CATEGORIES],
      queryFn: businessFieldCategoriesAPI,
      select: ({ data }: BaseResponse<BusinessFieldCategoryRESP[]>) => {
        const dto = transformBusinessFieldCategoryResponse(data);
        console.log(dto);
        const values: FormOptionValuesItem[] = dto.map((value) => ({
          label: i18n.language === 'en' ? value.nameInEnglish : value.name,
          checkedValue: value.id,
        }));
        return values;
      },
    });
    const { refetch: generatePresignedUrlApi } = useQuery({
      queryKey: [QUERY_KEYS.STORAGE_PRESIGNED_URL],
      queryFn: () =>
        generatePresignedUrlAPI({
          storageDomain: 'USER',
          storageContentType: getStorageContentType(file),
        } as GeneratePresignedUrlREQ),
      select: ({ data }: BaseResponse<S3File>) => data,
      enabled: false,
    });
    // Fetch user details
    const { data: userDetails, isFetching: isFetchingUserDetails } = useQuery({
      queryKey: [QUERY_KEYS.USER_DETAILS],
      queryFn: userDetailsAPI,
    });

    //User update mutation
    const { mutate: updateUser, isPending } = useMutation({
      mutationFn: (request: UserREQ) => userDetailsUpdateAPI(request),
      onSuccess: () => {
        SuccessToast({
          label: tCommon('my-page.success_edit_profile_label'),
          content: tCommon('my-page.success_edit_profile_content'),
        });
      },
    });

    const { handleSubmit, control, reset, setValue, watch, setError } = useForm<MemberInformationFormValues>({
      resolver: yupResolver(MemberInformationSchema),
      defaultValues: {
        companyName: '',
        fullName: '',
        personInCharge: '',
        internalEmail: '',
        departmentName: '',
        businessFieldCategoryIds: [],
        attachments: undefined,
      },
    });

    useEffect(() => {
      const subscription = watch(({ attachments }, { name }) => {
        if (name === 'attachments') {
          setFile(attachments as File | undefined);
        }
      });
      return () => subscription.unsubscribe();
    }, [watch]);
    // Populate form with user details
    useEffect(() => {
      if (userDetails?.data) {
        reset({
          companyName: userDetails.data.companyName,
          fullName: userDetails.data.fullName,
          personInCharge: userDetails.data.personInCharge,
          internalEmail: userDetails.data.internalEmail,
          departmentName: userDetails.data.departmentName,
          businessFieldCategoryIds: userDetails.data.businessFieldCategories
            ?.filter((category) => category.isUserSelected)
            .map((category) => category.id),
          attachments: userDetails.data.memberAttachments?.[0] || undefined,
        });
      }
    }, [userDetails, reset]);

    const handleErrorUploadFile = () => {
      setValue('attachments', null);
      setError('attachments', { type: 'verify', message: 'verify.upload_failed' });
      setIsUploadingFile(false);
    };

    const onSubmit = async (value: MemberInformationRequest) => {
      let key, fileType;

      if (file) {
        setIsUploadingFile(true);

        fileType = getStorageContentType(file);

        if (!fileType) {
          handleErrorUploadFile();
          return;
        }

        const { data } = await generatePresignedUrlApi();

        if (!data) {
          handleErrorUploadFile();
          return;
        }

        try {
          await fetch(data.url, { method: 'PUT', body: file });
          key = data.key;
        } catch (error) {
          handleErrorUploadFile();
          return;
        } finally {
          setIsUploadingFile(false);
        }
      }
      // Include the uploaded files in the update request
      updateUser({
        ...value,
        name: value.fullName,
        attachments: file && key && fileType ? [{ name: file.name, fileType: fileType, key: key, size: file.size }] : [],
      });
    };

    const onCloseChangePhoneNumber = () => {
      setOpenChangePhoneNumber(false);
    };
    const onCloseChangeEmail = () => {
      setOpenChangeEmail(false);
    };

    return (
      <MyPageContainer description={tCommon('my-page.update_guide')} title={tCommon('my-page.profile_information')}>
     
        <div className='flex flex-col md:gap-8 gap-6'>
          {isFetchingUserDetails ? (
            <div className='w-full flex justify-center '>
              <Spinner raw className='w-full size-10 text-left my-10' />
            </div>
          ) : (
         
         <div className='flex flex-col gap-12'>
              <div className='flex flex-col md:gap-5 gap-6'>
                <h1 className='md:text-xl text-base text-slate-800 font-medium pb-4 border-b border-slate-200'>
                  {tCommon('my-page.privacy')}
                </h1>
                <div className='flex flex-col gap-6 max-w-[694px]'>
                  <FormField
                    className='md:flex-row flex-col'
                    type='input'
                    label={tForm('label.email')}
                    rightNode={
                      <Button className='w-[122px] h-[48px]' onClick={() => setOpenChangeEmail(true)}>
                        {' '}
                        {tCommon('my-page.change_email_modal_button')}
                      </Button>
                    }
                    componentProps={{
                      name: 'email',
                      control: control2,
                      required: true,
                      disabled: true,
                      placeholder:
                        (userDetails?.data?.email && userDetails.data.email.length > 18
                          ? `${userDetails.data.email.slice(0, 18)}...`
                          : userDetails?.data?.email) || 'KimJiwon@nasd',
                    }}
                  />
                  <FormField
                    className='md:flex-row flex-col'
                    type='input'
                    label={tForm('label.phone_number')}
                    rightNode={
                      <Button className='w-[122px] h-[48px]' onClick={() => setOpenChangePhoneNumber(true)}>
                        {tCommon('my-page.change_phone_number_modal_button')}
                      </Button>
                    }
                    componentProps={{
                      name: 'Phone Number',
                      control: control2,
                      required: true,
                      disabled: true,
                      placeholder: userDetails?.data?.phoneNumber || '010-1234-5678',
                    }}
                  />
                  <FormField
                    className='md:flex-row flex-col'
                    type='input'
                    label={tForm('label.id')}
                    componentProps={{
                      name: 'id',
                      control: control2,
                      required: true,
                      disabled: true,
                      placeholder: userDetails?.data?.username + '' || 'KimJiwon@nasd',
                    }}
                  />
                </div>
           
              </div>
              <div className='flex flex-col gap-5'>
                <h1 className='md:text-xl text-base text-slate-800 font-medium pb-4 border-b border-slate-200'>
                  {tCommon('my-page.member_info')}
                </h1>
                <form onSubmit={handleSubmit(onSubmit)} id={FORM_ID} className='flex flex-col gap-6 max-w-[694px]'>
                  <FormField
                    className='md:flex-row flex-col'
                    type='input'
                    label={tForm('label.company_name')}
                    componentProps={{
                      name: 'companyName',
                      control,
                      required: true,
                      placeholder: 'Company Name',
                    }}
                  />
                  <FormField
                    className='md:flex-row flex-col'
                    type='input'
                    label={tForm('label.person_in_charge_name')}
                    componentProps={{
                      name: 'fullName',
                      control,
                      required: true,
                      placeholder: 'Enter person in charge',
                    }}
                  />
                  <FormField
                    className='md:flex-row flex-col'
                    type='input'
                    label={tForm('label.person_in_charge_position')}
                    componentProps={{
                      name: 'personInCharge',
                      control,
                      required: true,
                      placeholder: 'Enter person in charge',
                    }}
                  />
                  <FormField
                    className='md:flex-row flex-col'
                    type='input'
                    label={tForm('label.contact_email')}
                    componentProps={{
                      name: 'internalEmail',
                      control,
                      required: true,
                      placeholder: 'Enter Email',
                    }}
                  />
                  <FormField
                    className='md:flex-row flex-col gap-1 '
                    type='option-group'
                    label={tForm('label.business_field')}
                    hideErrorMessage
                    componentProps={{
                      control,
                      name: `businessFieldCategoryIds`,
                      required: true,
                      replace: true,
                      type: 'checkbox',
                      values: businessFieldCategories,
                    }}
                  />
                  <FormField
                    className='md:flex-row flex-col'
                    type='input'
                    label={tForm('label.department_my_page')}
                    componentProps={{
                      name: 'departmentName',
                      control,
                      placeholder: '...',
                    }}
                  />
                  <div className='max-w-[694px] w-full  '>
                    <FormField
                      className='md:flex-row flex-col gap-1'
                      type='file'
                      label={tForm('label.attach_certificate')}
                      info={tForm('info.business_license_attachment')}
                      hideRequiredIndicator
                      componentProps={{
                        control,

                        name: 'attachments',

                        mimeTypes: [
                          'application/pdf',
                          'application/msword',
                          'application/vnd.openxmlformats-officedocument.wordprocessingml.document',
                        ],
                        maxSizeInMB: 10,
                      }}
                    />
                  </div>
                </form>
              </div>
            </div>
          )}
          <div className='flex flex-row pt-5 border-t border-slate-200 justify-end w-full gap-3'>
            <Button onClick={() => navigate(-1)} className='w-[94px] h-[36px] px-2 py-3 '>
              {tCommon('button.cancel')}
            </Button>
            <Button
              isLoading={isPending}
              form={FORM_ID}
              className=' w-[94px] h-[36px]  px-2 py-3'
              variant='primary'
              type='submit'>
              {tCommon('my-page.confirm')}
            </Button>
          </div>

          {/* Modal */}
          <ChangePhoneNumberModal open={openChangePhoneNumber} onClose={onCloseChangePhoneNumber} />
          <ChangeEmailModal
            open={openChangeEmail}
            onClose={onCloseChangeEmail}
            setCloseEmail={setOpenChangeEmail}
            queryKey={QUERY_KEYS.USER_DETAILS}
            oldEmail={userDetails?.data.email}
          />
        </div>
      </MyPageContainer>
    );
  },
  [USER_ROLE.MEMBER],
  [],
);

export default MemberInformation;
