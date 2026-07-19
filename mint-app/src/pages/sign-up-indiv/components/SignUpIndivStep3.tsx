import Alert from '@/components/core/alert/Alert';
import Divider from '@/components/core/divider/Divider';
import FormField from '@/components/core/form/form-field/FormField';
import { FormOptionValuesItem } from '@/components/core/form/form-option-group/FormOptionGroup';
import { businessFieldCategoriesAPI, registerMemberAPI } from '@/data/account/account.api';
import { transformBusinessFieldCategoryResponse } from '@/data/account/account.service';
import { RegisterMemberREQ } from '@/data/account/request/register-member.request';
import { BusinessFieldCategoryRESP } from '@/data/account/response/business-field-category.response';
import { PrivacyDTO } from '@/data/privacy/dto/privacy.dto';
import { privaciesAPI } from '@/data/privacy/privacy.api';
import { transformPrivaciesResponse } from '@/data/privacy/privacy.service';
import { PrivacyRESP } from '@/data/privacy/response/privacy.response';
import { GeneratePresignedUrlREQ } from '@/data/storage/request/generate-presigned-url.request';
import { generatePresignedUrlAPI } from '@/data/storage/storage.api';
import { TermDTO } from '@/data/term/dto/term.dto';
import { TermRESP } from '@/data/term/response/term.response';
import { termsAPI } from '@/data/term/term.api';
import { transformTermsResponse } from '@/data/term/term.service';
import i18n from '@/i18n';
import SignUpCheckboxLabel from '@/pages/sign-up/components/SignUpCheckboxLabel';
import SignUpContainer, { SignUpContainerProps } from '@/pages/sign-up/components/SignUpContainer';
import SignUpPrivaciesModal, { SignUpPrivaciesModalRef } from '@/pages/sign-up/components/SignUpPrivaciesModal';
import SignUpTermsAndCondsModal, { SignUpTermsAndCondsModalRef } from '@/pages/sign-up/components/SignUpTermsAndCondsModal';
import { BaseResponse } from '@/types/response.type';
import { S3File } from '@/types/s3-file.type';
import { QUERY_KEYS } from '@/utils/constants/data.constant';
import { onError } from '@/utils/helpers/data.helper';
import { getStorageContentType } from '@/utils/helpers/file.helper';
import { SignUpIndivStep3Input, signUpIndivStep3Schema } from '@/utils/schemas/sign-up.schema';
import { yupResolver } from '@hookform/resolvers/yup';
import { useMutation, useQuery } from '@tanstack/react-query';
import { useEffect, useMemo, useRef, useState } from 'react';
import { useForm } from 'react-hook-form';
import { useTranslation } from 'react-i18next';

const FORM_ID = 'sign-up-indiv-detail-form';

type SignUpIndivStep3Props = Pick<SignUpContainerProps, 'titleProps' | 'onBack' | 'onNext'>;

const SignUpIndivStep3 = ({ onNext, ...containerProps }: SignUpIndivStep3Props): JSX.Element => {
  const { t: tCommon } = useTranslation('common');
  const { t: tForm } = useTranslation('form');

  // REFS
  const termsAndCondsModalRef = useRef<SignUpTermsAndCondsModalRef | null>(null);
  const privaciesModalRef = useRef<SignUpPrivaciesModalRef | null>(null);

  // STATES
  const [file, setFile] = useState<File>();
  const [isUploadingFile, setIsUploadingFile] = useState<boolean>(false);

  // FORM
  const {
    control,
    handleSubmit,
    formState: { errors },
    watch,
    setValue,
    setError,
  } = useForm<SignUpIndivStep3Input>({
    resolver: yupResolver<SignUpIndivStep3Input>(signUpIndivStep3Schema),
    defaultValues: {
      ...signUpIndivStep3Schema.getDefault(),
      businessFieldCategoryIds: [],
    },
  });

  // APIS
  const { mutate: registerMemberApi, isPending: isRegistering } = useMutation({
    mutationFn: (request: RegisterMemberREQ) => registerMemberAPI(request),
    onSuccess: () => onNext?.(),
    onError,
  });

  const { data: businessFieldCategories = [] } = useQuery({
    queryKey: [QUERY_KEYS.BUSINESS_FIELD_CATEGORIES],
    queryFn: businessFieldCategoriesAPI,
    select: ({ data }: BaseResponse<BusinessFieldCategoryRESP[]>) => {
      const dto = transformBusinessFieldCategoryResponse(data);
      const values: FormOptionValuesItem[] = dto.map((value) => ({
        label: i18n.language === 'ko' ? value.name : value.nameInEnglish,
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

  // APIS: TERMS + PRIVACIES
  const { data: privacies = [] } = useQuery({
    queryKey: [QUERY_KEYS.PRIVACIES],
    queryFn: () => privaciesAPI({ type: 'SIGNUP' }),
    select: ({ data }: BaseResponse<PrivacyRESP[]>) => data,
  });
  const localePrivacies = useMemo<PrivacyDTO[]>(
    () => transformPrivaciesResponse(i18n.language, privacies),
    [privacies, i18n.language],
  );

  const { data: terms = [] } = useQuery({
    queryKey: [QUERY_KEYS.TERMS],
    queryFn: () => termsAPI({ type: 'SIGNUP_MEMBER' }),
    select: ({ data }: BaseResponse<TermRESP[]>) => data,
  });
  const localeTerms = useMemo<TermDTO[]>(() => transformTermsResponse(i18n.language, terms), [terms, i18n.language]);

  // METHODS
  const handleErrorUploadFile = () => {
    setValue('attachments', undefined);
    setError('attachments', { type: 'verify', message: 'verify.upload_failed' });
    setIsUploadingFile(false);
  };

  const onSubmit = handleSubmit(async (input: SignUpIndivStep3Input) => {
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

    const request: RegisterMemberREQ = {
      companyName: input.companyName,
      fullName: input.fullName,
      personInCharge: input.personInCharge,
      internalEmail: input.internalEmail,
      departmentName: input.departmentName,
      businessFieldCategoryIds: input.businessFieldCategoryIds
        .map((field) => {
          if (!field) return null;
          const num = Number(field);
          return isNaN(num) ? null : num;
        })
        .filter((i) => i !== null),
      attachments: file && key && fileType ? [{ name: file.name, fileType: fileType, key: key, size: file.size }] : undefined,
    };

    registerMemberApi(request);
  });

  // EFFECTS
  useEffect(() => {
    const subscription = watch(({ attachments }, { name }) => {
      if (name === 'attachments') {
        setFile(attachments as File | undefined);
      }
    });
    return () => subscription.unsubscribe();
  }, [watch]);

  return (
    <SignUpContainer
      form={FORM_ID}
      isLoading={isRegistering || isUploadingFile}
      {...containerProps}
      isLastStep
      className='max-w-[880px] md:gap-10'>
      <form id={FORM_ID} className='flex flex-col gap-10 w-full max-md:gap-6' onSubmit={onSubmit}>
        <div className='flex w-full flex-col gap-8 max-md:gap-6'>
          <div className='grid grid-cols-2 gap-x-6 gap-y-8 max-md:grid-cols-1 max-md:gap-y-6'>
            <FormField
              type='input'
              label={tForm('label.company_name')}
              componentProps={{
                control,
                name: 'companyName',
                required: true,
                placeholder: tForm('placeholder.company_name'),
              }}
            />
            <FormField
              type='input'
              label={tForm('label.contact_name')}
              componentProps={{
                control,
                name: 'fullName',
                required: true,
                placeholder: tForm('placeholder.name'),
              }}
            />
            <FormField
              type='input'
              label={tForm('label.contact_position')}
              componentProps={{
                control,
                name: 'personInCharge',
                required: true,
                placeholder: tForm('placeholder.position'),
              }}
            />
            <FormField
              type='input'
              label={tForm('label.contact_email')}
              componentProps={{
                control,
                name: 'internalEmail',
                required: true,
                placeholder: tForm('placeholder.email'),
              }}
            />
          </div>

          <div className='flex w-full flex-col gap-6'>
            <FormField
              type='option-group'
              label={tForm('label.business_field')}
              className='grid grid-cols-2 max-md:grid-cols-1 max-md:gap-3 md:gap-x-6'
              hideErrorMessage
              componentProps={{
                control,
                name: 'businessFieldCategoryIds',
                required: true,
                replace: true,
                type: 'checkbox',
                values: businessFieldCategories,
              }}
            />
            <Alert title={tCommon('sign_up.select')} open={!!errors.businessFieldCategoryIds}>
              {tForm('required.business_field')}
            </Alert>
          </div>
        </div>

        <div className='grid grid-cols-2 gap-x-6 gap-y-8 max-md:grid-cols-1 max-md:gap-y-6'>
          <FormField
            type='input'
            label={tForm('label.contact_department')}
            componentProps={{
              control,
              name: 'departmentName',
              placeholder: tForm('placeholder.department'),
            }}
          />

          <FormField
            type='file'
            label={tForm('label.business_license_attachment')}
            info={tForm('info.business_license_attachment')}
            componentProps={{
              control,
              name: 'attachments',
              mimeTypes: ['application/pdf', 'image/jpeg', 'image/png'],
              maxSizeInMB: 5,
            }}
          />
        </div>

        <div className='flex flex-col gap-3 w-full'>
          <div className='text-md font-medium text-comp-text-default'>{tCommon('sign_up.terms_agreement')}</div>

          <Divider />

          <FormField
            type='option-group'
            componentProps={{
              control,
              customGroupClassName: 'gap-1',
              className: 'flex-row-reverse justify-between w-full items-center',
              name: 'isAgreed',
              required: true,
              type: 'checkbox',
              values: [
                {
                  label: (
                    <SignUpCheckboxLabel
                      label={tForm('label.terms_of_service')}
                      onClick={() => termsAndCondsModalRef.current?.setOpen(true)}
                    />
                  ),
                },
                {
                  label: (
                    <SignUpCheckboxLabel
                      label={tForm('label.privacy_agreement')}
                      onClick={() => privaciesModalRef.current?.setOpen(true)}
                    />
                  ),
                },
              ],
            }}
          />
        </div>
      </form>
      <SignUpTermsAndCondsModal
        header={
          <FormField
            type='option-group'
            hideErrorMessage
            componentProps={{
              control,
              name: 'isAgreed',
              required: true,
              type: 'checkbox',
              values: [
                {
                  label: (
                    <span className='text-sm max-md:text-xs font-medium text-comp-text-secondary'>
                      {tCommon('sign_up.consent')}
                    </span>
                  ),
                },
                null,
              ],
            }}
          />
        }
        ref={termsAndCondsModalRef}
        items={localeTerms}
      />

      <SignUpPrivaciesModal
        header={
          <FormField
            type='option-group'
            hideErrorMessage
            componentProps={{
              control,
              name: 'isAgreed',
              required: true,
              type: 'checkbox',
              values: [
                null,
                {
                  label: (
                    <span className='text-sm max-md:text-xs font-medium text-comp-text-secondary'>
                      {tCommon('sign_up.consent')}
                    </span>
                  ),
                },
              ],
            }}
          />
        }
        ref={privaciesModalRef}
        items={localePrivacies}
      />
    </SignUpContainer>
  );
};

export default SignUpIndivStep3;
