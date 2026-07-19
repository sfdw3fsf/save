import Block from '@/components/core/block/Block';
import Button from '@/components/core/button/Button';
import Divider from '@/components/core/divider/Divider';
import FormField from '@/components/core/form/form-field/FormField';
import PencilSimpleLineIcon from '@/components/icons/PencilSimpleLineIcon';
import {
  advisoryFieldCategoriesAPI,
  businessFieldCategoriesAPI,
  medicalFieldCategoriesAPI,
  registerExpertAPI,
} from '@/data/account/account.api';
import {
  transformAdvisoryFieldCategoryResponse,
  transformBusinessFieldCategoryResponse,
  transformMedicalFieldCategoryToFlatItems,
} from '@/data/account/account.service';
import { RegisterExpertREQ } from '@/data/account/request/register-expert.request';
import { AdvisoryFieldCategoryRESP } from '@/data/account/response/advisory-field-category.response';
import { BusinessFieldCategoryRESP } from '@/data/account/response/business-field-category.response';
import { MedicalFieldCategoryRESP } from '@/data/account/response/medical-field-category.response';
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
import { getNameByLanguage } from '@/utils/helpers/form.helper';
import { SignUpExpertStep3Input, signUpExpertStep3Schema } from '@/utils/schemas/sign-up.schema';
import { IMAGES } from '@/utils/theme';
import { yupResolver } from '@hookform/resolvers/yup';
import { useMutation, useQuery } from '@tanstack/react-query';
import { useEffect, useMemo, useRef, useState } from 'react';
import { useForm } from 'react-hook-form';
import { useTranslation } from 'react-i18next';
import { twMerge } from 'tailwind-merge';
import { filterCategory, transformBusinessCombineInput } from '../helpers/sign-up-expert.helper';
import SignUpExpertBusinessModal, { BusinessCombineInput, SignUpExpertBusinessModalRef } from './SignUpExpertBusinessModal';

const FORM_ID = 'sign-up-expert-detail-form';

type SignUpExpertStep3Props = Pick<SignUpContainerProps, 'titleProps' | 'onBack' | 'onNext'>;

const SignUpExpertStep3 = ({ onNext, ...containerProps }: SignUpExpertStep3Props): JSX.Element => {
  const { t: tCommon } = useTranslation('common');
  const { t: tForm } = useTranslation('form');

  const lang = i18n.language;

  // REFS
  const termsAndCondsModalRef = useRef<SignUpTermsAndCondsModalRef | null>(null);
  const businessModalRef = useRef<SignUpExpertBusinessModalRef | null>(null);
  const privaciesModalRef = useRef<SignUpPrivaciesModalRef | null>(null);

  // STATES
  const [businessCombineInput, setBusinessCombineInput] = useState<BusinessCombineInput>();
  const [file, setFile] = useState<File>();
  const [isUploadingFile, setIsUploadingFile] = useState<boolean>(false);

  // FORM
  const {
    control,
    handleSubmit,
    formState: { errors },
    setValue,
    watch,
    setError,
  } = useForm<SignUpExpertStep3Input>({
    resolver: yupResolver<SignUpExpertStep3Input>(signUpExpertStep3Schema),
    defaultValues: signUpExpertStep3Schema.getDefault(),
  });

  // APIS
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

  const { mutate: registerExpertApi, isPending: isRegistering } = useMutation({
    mutationFn: (request: RegisterExpertREQ) => registerExpertAPI(request),
    onSuccess: () => onNext?.(),
    onError,
  });

  const { data: businessFieldCategories = [], isFetching: isFetchingBusiness } = useQuery({
    queryKey: [QUERY_KEYS.BUSINESS_FIELD_CATEGORIES],
    queryFn: businessFieldCategoriesAPI,
    select: ({ data }: BaseResponse<BusinessFieldCategoryRESP[]>) => transformBusinessFieldCategoryResponse(data),
  });

  const { data: advisoryFieldCategories = [], isFetching: isFetchingAdvisory } = useQuery({
    queryKey: [QUERY_KEYS.ADVISORY_FIELD_CATEGORIES],
    queryFn: advisoryFieldCategoriesAPI,
    select: ({ data }: BaseResponse<AdvisoryFieldCategoryRESP[]>) => transformAdvisoryFieldCategoryResponse(data),
  });

  const { data: departmentOptions = [], isFetching: isFetchingMedical } = useQuery({
    queryKey: [QUERY_KEYS.MEDICAL_FIELD_CATEGORIES],
    queryFn: medicalFieldCategoriesAPI,
    select: ({ data }: BaseResponse<MedicalFieldCategoryRESP[]>) => transformMedicalFieldCategoryToFlatItems(data),
  });

  const filteredBusinessField = useMemo(
    () => filterCategory(businessFieldCategories, businessCombineInput?.businessFieldIds),
    [businessCombineInput?.businessFieldIds, businessFieldCategories],
  );

  const filteredAdvisoryField = useMemo(
    () => filterCategory(advisoryFieldCategories, businessCombineInput?.advisoryFieldIds),
    [advisoryFieldCategories, businessCombineInput?.advisoryFieldIds],
  );

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
    queryFn: () => termsAPI({ type: 'SIGNUP_EXPERT' }),
    select: ({ data }: BaseResponse<TermRESP[]>) => data,
  });
  const localeTerms = useMemo<TermDTO[]>(() => transformTermsResponse(i18n.language, terms), [terms, i18n.language]);

  // METHODS
  const handleErrorUploadFile = () => {
    setValue('attachments', undefined);
    setError('attachments', { type: 'verify', message: 'verify.upload_failed' });
    setIsUploadingFile(false);
  };

  const onSubmit = handleSubmit(async (data: SignUpExpertStep3Input) => {
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

    const { businessFieldIds, advisoryFieldIds, otherAdvisoryFields, otherBusinessFields } =
      transformBusinessCombineInput(businessCombineInput);

    const request: RegisterExpertREQ = {
      fullName: data.fullName,
      schoolFrom: data.schoolFrom,
      affiliation: data.affiliation,
      personalHistory: data.personalHistory,
      department: data.department,
      detailedField: data.detailedField,
      contact: data.contact,
      businessFieldIds: businessFieldIds,
      advisoryFieldIds: advisoryFieldIds,
      otherAdvisoryFields: otherAdvisoryFields,
      otherBusinessFields: otherBusinessFields,
      attachments: file && key && fileType ? [{ name: file.name, fileType: fileType, key: key, size: file.size }] : undefined,
    };

    registerExpertApi(request);
  });

  // EFFECTS
  useEffect(() => {
    setValue('selectedBusinessFields', !!businessCombineInput);
  }, [businessCombineInput]);

  useEffect(() => {
    const subscription = watch(({ attachments }, { name }) => {
      if (name === 'attachments') {
        setFile(attachments as File | undefined);
      }
    });
    return () => subscription.unsubscribe();
  }, [watch]);

  console.log('filteredBusinessField', filteredBusinessField);

  return (
    <SignUpContainer
      form={FORM_ID}
      isLoading={isRegistering || isUploadingFile}
      onNext={onNext}
      {...containerProps}
      isLastStep
      className='max-w-[880px] md:gap-10'>
      <form id={FORM_ID} className='flex flex-col gap-8 w-full max-md:gap-6' onSubmit={onSubmit}>
        <div className='flex w-full flex-col gap-8 max-md:gap-6'>
          <div className='grid grid-cols-2 gap-x-6 gap-y-8 max-md:grid-cols-1 max-md:gap-y-6'>
            <FormField
              type='input'
              label={tForm('label.name')}
              componentProps={{
                control,
                name: 'fullName',
                required: true,
                placeholder: tForm('placeholder.name'),
              }}
            />

            <div className='max-md:hidden'></div>

            <FormField
              type='input'
              label={tForm('label.affiliation')}
              componentProps={{
                control,
                name: 'affiliation',
                required: true,
                placeholder: tForm('placeholder.affiliation'),
              }}
            />

            <FormField
              type='input'
              label={tForm('label.career')}
              componentProps={{
                control,
                name: 'personalHistory',
                required: true,
                placeholder: tForm('placeholder.career'),
                rightInputNode: tCommon('component.year'),
              }}
            />

            <FormField
              type='input'
              label={tForm('label.school_of_origin')}
              componentProps={{
                name: 'schoolFrom',
                control,
                required: true,
                placeholder: tForm('placeholder.school_of_origin'),
              }}
            />

            <FormField
              type='input'
              label={tForm('label.contact')}
              componentProps={{
                name: 'contact',
                control,
                required: true,
                placeholder: tForm('placeholder.phone_number'),
              }}
            />

            <FormField
              type='datalist'
              label={tForm('label.specialty')}
              componentProps={{
                name: 'department',
                control,
                required: true,
                options: departmentOptions.map((option) => {
                  const name = getNameByLanguage(lang, option);
                  return { label: name, value: name };
                }),
                isSearchable: true,
              }}
            />

            <FormField
              type='input'
              label={tForm('label.subspecialty')}
              componentProps={{
                name: 'detailedField',
                control,
                required: true,
                placeholder: tForm('placeholder.subspecialty'),
              }}
            />
          </div>

          <div className='w-full'>
            <Block
              className={twMerge(!businessCombineInput && 'max-h-[300px]')}
              isError={!!errors.selectedBusinessFields}
              imgSrc={IMAGES.ILLUSTRATION.BROKEN}
              header={{
                title: tCommon('sign_up.business_field'),
                rightNode: businessCombineInput ? (
                  <Button
                    isLoading={isFetchingAdvisory || isFetchingBusiness}
                    leftIcon={<PencilSimpleLineIcon />}
                    sizing='2sm'
                    className='h-7'
                    onClick={() => businessModalRef.current?.setOpen(true)}>
                    {tCommon('component.edit')}
                  </Button>
                ) : undefined,
              }}
              message={tCommon('sign_up.for_better_matching')}
              buttonProps={{
                children: tCommon('sign_up.select_business_area'),
                onClick: () => businessModalRef.current?.setOpen(true),
              }}>
              {!businessCombineInput ? undefined : (
                <div className='flex w-full flex-col gap-6 text-sm text-left'>
                  {filteredBusinessField.length > 0 && (
                    <div className='flex w-full flex-col gap-3'>
                      <div className='font-medium text-comp-text-brand'>{tCommon('sign_up.business_field_section')}</div>
                      {filteredBusinessField.map(({ label, allFields }, index) =>
                        allFields?.name && allFields?.nameInEnglish ? (
                          <div key={index} className='flex flex-col w-full gap-1'>
                            <div className='text-comp-text-secondary'>{getNameByLanguage(lang, label)}</div>
                            <div className='font-medium text-comp-text-default'>{getNameByLanguage(lang, allFields)}</div>
                          </div>
                        ) : null,
                      )}
                    </div>
                  )}
                  {filteredAdvisoryField.length > 0 && (
                    <div className='flex w-full flex-col gap-3'>
                      <div className='font-medium text-comp-text-brand'>{tCommon('sign_up.advisory_field_section')}</div>
                      {filteredAdvisoryField.map(({ label, allFields }, index) =>
                        allFields?.name && allFields?.nameInEnglish ? (
                          <div key={index} className='flex flex-col w-full gap-1'>
                            <div className='text-comp-text-secondary'>{getNameByLanguage(lang, label)}</div>
                            <div className='font-medium text-comp-text-default'>{getNameByLanguage(lang, allFields)}</div>
                          </div>
                        ) : null,
                      )}
                    </div>
                  )}
                </div>
              )}
            </Block>
          </div>
        </div>

        <div className='grid grid-cols-2 gap-x-6 gap-y-8 max-md:grid-cols-1 max-md:gap-y-6'>
          <FormField
            type='file'
            label={tForm('label.resume')}
            info={
              <>
                <p>{tForm('info.resume')}</p>
                <p>{tCommon('sign_up.resume_increases_your_chances')}</p>
              </>
            }
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
          <div className='max-md:hidden'></div>
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

      <SignUpExpertBusinessModal
        ref={businessModalRef}
        businessFieldCategories={businessFieldCategories}
        advisoryFieldCategories={advisoryFieldCategories}
        onSubmit={setBusinessCombineInput}
        defaultValues={businessCombineInput}
      />

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

export default SignUpExpertStep3;
