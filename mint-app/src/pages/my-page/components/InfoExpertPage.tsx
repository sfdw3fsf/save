import Button from '@/components/core/button/Button';
import FormField from '@/components/core/form/form-field/FormField';
import Spinner from '@/components/core/spinner/Spinner';
import { SuccessToast } from '@/components/core/success-notification/SuccessNotification';
import PermissionAccept from '@/components/hocs/PermissionAccept';
import ListIcon from '@/components/icons/ListIcon';
import PencilSimpleLineIcon from '@/components/icons/PencilSimpleLineIcon';
import PlusIcon from '@/components/icons/PlusIcon';
import { medicalFieldCategoriesAPI } from '@/data/account/account.api';
import { transformMedicalFieldCategoryToFlatItems } from '@/data/account/account.service';
import { MedicalFieldCategoryRESP } from '@/data/account/response/medical-field-category.response';
import { GeneratePresignedUrlREQ } from '@/data/storage/request/generate-presigned-url.request';
import { generatePresignedUrlAPI } from '@/data/storage/storage.api';
import { Experience } from '@/data/user/response/user-details.response';
import { userExpertAPI, userExpertUpdateAPI } from '@/data/user/user.api';
import i18n from '@/i18n';
import { BaseResponse } from '@/types/response.type';
import { S3File } from '@/types/s3-file.type';
import { QUERY_KEYS } from '@/utils/constants/data.constant';
import { USER_ROLE } from '@/utils/enums/user/user-role.enum';
import { getStorageContentType } from '@/utils/helpers/file.helper';
import { getNameByLanguage } from '@/utils/helpers/form.helper';
import { ExpertInformationSchema } from '@/utils/schemas/my-page-schema';
import { yupResolver } from '@hookform/resolvers/yup';
import { useMutation, useQuery } from '@tanstack/react-query';
import { useEffect, useRef, useState } from 'react';
import { useForm } from 'react-hook-form';
import { useTranslation } from 'react-i18next';
import { useNavigate } from 'react-router-dom';
import ChangeEmailModal from './changeEmail/ChangeEmailModal';
import ChangePhoneNumberModal from './changePhone/ChangePhoneNumberModal';
import AddEducationModal from './expertModal/AddEducationModal';
import AddExperienceModal from './expertModal/AddExperienceModal';
import EditBusinessAdvisoryModal from './expertModal/EditBusinessAdvisoryModal';
import EditEducationModal from './expertModal/EditEducationModal';
import EditExperienceModal from './expertModal/EditExperienceModal';
import MyPageContainer from './MyPageContainer';

type ExpertInformationFormValues = {
  name: string;
  schoolFrom: string;
  affiliation: string;
  personalHistory: number;
  department: string;
  internalEmail?: string;
  detailedField: string;
  attachments?: any;
  avatar?: any;
  businessFieldIds?: number[];
  advisoryFieldIds?: number[];
  otherBusinessFields?: otherFields[];
  otherAdvisoryFields?: otherFields[];
  educations?: any;
  experiences?: any;
};
type AddEducationFormValue = {
  id: number | string;
  name: string;
  detail: string;
  entranceMonth?: number;
  entranceYear?: number;
  graduationMonth?: number;
  graduationYear?: number;
};
type otherFields = {
  categoryId: number;
  name: string;
};
type ExpertInformationRequest = {
  name: string;
  schoolFrom: string;
  affiliation: string;
  personalHistory: number;
  department: string;
  avatar?: any;
  attachments?: any;
  educations?: any;
  experiences?: any;
};
const FORM_ID = 'expert-information-form';

const InfoExpertPage = PermissionAccept(
  (): JSX.Element => {
    const { t: tCommon } = useTranslation('common');
    const { t: tForm } = useTranslation('form');
    const { control: control2 } = useForm();
    const [file, setFile] = useState<File>();

    const [avatarFile, setAvatarFile] = useState<File | undefined>();
    const [isUploadingFile, setIsUploadingFile] = useState<boolean>(false);
    const [openChangePhoneNumber, setOpenChangePhoneNumber] = useState<boolean>(false);
    const [openChangeEmail, setOpenChangeEmail] = useState<boolean>(false);
    const [openAddEducation, setOpenAddEducation] = useState<boolean>(false);
    const [openEditEducation, setOpenEditEducation] = useState<boolean>(false);
    const [educations, setEducations] = useState<AddEducationFormValue[]>([]);
    const [educationToEdit, setEducationToEdit] = useState<AddEducationFormValue | null>(null);
    const [isConfirmEducation, setisConfirmEducation] = useState<boolean>(false);
    const [openAddExperience, setOpenAddExperience] = useState<boolean>(false);
    const [experiences, setExperiences] = useState<Experience[]>([]);
    const [openEditExperience, setOpenEditExperience] = useState<boolean>(false);
    const [experienceToEdit, setExperienceToEdit] = useState<Experience | null>(null);
    const [isOpenEditExperienceStatus, setIsOpenEditExperienceStatus] = useState<boolean>(false);
    const [isOpenEditEducationStatus, setIsOpenEditEducationStatus] = useState<boolean>(false);
    const [isConfirmExperience, setIsConfirmExperience] = useState<boolean>(false);
    const [openEditBusiness, setOpenEditBusiness] = useState<boolean>(false);

    const dragItem = useRef<number | null>(null);
    const dragOverItem = useRef<number | null>(null);
    const { data: departmentOptions = [], isFetching: isFetchingMedical } = useQuery({
      queryKey: [QUERY_KEYS.MEDICAL_FIELD_CATEGORIES],
      queryFn: medicalFieldCategoriesAPI,
      select: ({ data }: BaseResponse<MedicalFieldCategoryRESP[]>) => transformMedicalFieldCategoryToFlatItems(data),
    });

    const handleSort = () => {
      const items = [...educations];
      if (dragItem.current !== null && dragOverItem.current !== null) {
        const draggedItem = items[dragItem.current];
        items.splice(dragItem.current, 1);
        items.splice(dragOverItem.current, 0, draggedItem);
        setEducations(items);
      }
    };

    const handleSortExperience = () => {
      const items = [...experiences];
      if (dragItem.current !== null && dragOverItem.current !== null) {
        const draggedItem = items[dragItem.current];
        items.splice(dragItem.current, 1);
        items.splice(dragOverItem.current, 0, draggedItem);
        setExperiences(items);
      }
    };

    const handleAddExperience = (newExperience: Experience) => {
      const isDuplicate = experiences.some((experience) => experience.id === newExperience.id);

      if (isDuplicate) {
        alert('The Experience already exists. Please use another.');
      } else {
        setExperiences((prev) => [...prev, newExperience]);
        setIsOpenEditExperienceStatus(true);
      }
    };

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

    const handleDeleteExperience = (id: string | number) => {
      setExperiences((prev) => prev.filter((experience) => experience.id !== id));
    };

    const handleAddEducation = (newEducation: AddEducationFormValue) => {
      const isDuplicate = educations.some((education) => education.id === newEducation.id);

      if (isDuplicate) {
        alert('The Education already exists. Please use another.');
      } else {
        setEducations((prev) => [...prev, newEducation]);
        setIsOpenEditEducationStatus(true);
      }
    };

    const handleEditEducation = (updatedEducation: AddEducationFormValue) => {
      setEducations((prev) => prev.map((education) => (education.id === updatedEducation.id ? updatedEducation : education)));
      setOpenEditEducation(false);
    };

    const handleDeleteEducation = (id: string | number) => {
      setEducations((prev) => prev.filter((education) => education.id !== id));
    };

    const handleOpenEditEducation = (education: AddEducationFormValue) => {
      setEducationToEdit(education);
      setOpenEditEducation(true);
    };

    const handleOpenEditExperience = (experience: Experience) => {
      setExperienceToEdit(experience);
      setOpenEditExperience(true);
    };

    const handleEditExperience = (updatedExperience: Experience) => {
      setExperiences((prev) =>
        prev.map((experience) => (experience.id === updatedExperience.id ? updatedExperience : experience)),
      );
      setOpenEditExperience(false);
    };

    const navigate = useNavigate();

    // Fetch user details
    const { data: userDetails, isFetching: isFetchingUserDetails } = useQuery({
      queryKey: [QUERY_KEYS.USER_DETAILS],
      queryFn: userExpertAPI,
    });

    useEffect(() => {
      if (userDetails?.data?.educations) {
        setEducations(userDetails.data.educations);
      }
    }, [userDetails]);
    useEffect(() => {
      if (userDetails?.data?.experiences) {
        setExperiences(userDetails.data.experiences);
      }
    }, [userDetails]);

    // User update mutation
    const { mutate: updateUser, isPending } = useMutation({
      mutationFn: (request: ExpertInformationRequest) => userExpertUpdateAPI(request),
      onSuccess: () => {
        SuccessToast({
          label: tCommon('my-page.success_edit_profile_label'),
          content: tCommon('my-page.success_edit_profile_content'),
        });
      },
    });
    const handleErrorUploadFile = () => {
      setValue('attachments', undefined);
      setError('attachments', { type: 'verify', message: 'verify.upload_failed' });
      setIsUploadingFile(false);
    };

    const {
      handleSubmit,
      control,
      reset,
      watch,
      setValue,
      setError,
      formState: { errors },
    } = useForm<ExpertInformationFormValues>({
      resolver: yupResolver(ExpertInformationSchema),
      defaultValues: {
        name: '',
        schoolFrom: '',
        affiliation: '',
        personalHistory: undefined,
        avatar: {},
        detailedField: '',
        department: '',
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
    useEffect(() => {
      const subscription = watch(({ avatar }, { name }) => {
        if (name === 'avatar') {
          setAvatarFile(avatar as File | undefined);
        }
      });
      return () => subscription.unsubscribe();
    }, [watch]);

    useEffect(() => {
      const foundDepartment = departmentOptions.find((dept) => dept.name === userDetails?.data.department);
      const department: string | undefined = i18n.language === 'en' ? foundDepartment?.nameInEnglish : foundDepartment?.name;
      if (userDetails?.data) {
        reset({
          name: userDetails.data.fullName,
          schoolFrom: userDetails.data.school,
          affiliation: userDetails.data.affiliation,
          personalHistory: Number(userDetails.data.yearOfExperience),
          detailedField: userDetails.data.detailedField,
          avatar: userDetails.data.avatar,
          department: department,
          attachments: userDetails.data.expertAttachments?.[0] || undefined,
        });
      }
    }, [userDetails, reset, departmentOptions, i18n.language]);
    const onSubmit = async (value: ExpertInformationRequest) => {
      // let uploadedFiles: any[] = [];
      let avatarKey, avatarFileType;
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

      // Avatar upload logic
      if (avatarFile) {
        setIsUploadingFile(true);

        try {
          avatarFileType = getStorageContentType(avatarFile);
          if (!avatarFileType) {
            throw new Error('Invalid avatar file type');
          }

          const { data: avatarData } = await generatePresignedUrlAPI({
            storageDomain: 'USER',
            storageContentType: avatarFileType,
          } as GeneratePresignedUrlREQ);

          if (!avatarData) {
            throw new Error('Failed to get avatar presigned URL');
          }

          await fetch(avatarData.url, { method: 'PUT', body: avatarFile });
          avatarKey = avatarData.key;
        } catch (error) {
          return;
        } finally {
          setIsUploadingFile(false);
        }
      }

      const newValue = {
        ...value,
        avatar:
          avatarFile && avatarKey && avatarFileType
            ? { key: avatarKey, fileType: avatarFileType, name: avatarFile.name, size: avatarFile.size }
            : null,
        attachments: file && key && fileType ? [{ name: file.name, fileType: fileType, key: key, size: file.size }] : [],
        educations: isConfirmEducation ? educations.map(({ id, ...rest }) => rest) : educations, // Ensure confirmed educations are sent
        experiences: isConfirmExperience ? experiences.map(({ id, ...rest }) => rest) : experiences, // Handle confirmed experience changes
      };

      updateUser(newValue);
    };

    const onCloseChangePhoneNumber = () => {
      setOpenChangePhoneNumber(false);
    };
    const onCloseChangeEmail = () => {
      setOpenChangeEmail(false);
    };
    const onCloseAddEducation = () => {
      setOpenAddEducation(false);
    };

    return (
      <MyPageContainer description={tCommon('my-page.update_your_information_here')} title={tCommon('my-page.expert_info')}>
        <div className='flex  flex-col md:gap-8 gap-6'>
          {isFetchingUserDetails ? (
            <div className='w-full flex justify-center '>
              <Spinner raw className='w-full size-10 text-left my-10' />
            </div>
          ) : (
            <div className='flex flex-col gap-12'>
              <div className='flex flex-col md:gap-8 gap-6'>
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
                        {tCommon('my-page.change_email_modal_button')}
                      </Button>
                    }
                    componentProps={{
                      name: 'email',
                      control: control2,
                      required: true,
                      disabled: true,
                      placeholder: userDetails?.data?.email || 'KimJiwon@nasd',
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
                      name: 'expertId',
                      control: control2,
                      required: true,
                      disabled: true,
                      placeholder: userDetails?.data?.username + '' || 'KimJiwon123',
                    }}
                  />
                  <FormField
                    className='md:flex-row flex-col gap-1'
                    type='avatar'
                    label={tForm('label.avatar_upload')}
                    componentProps={{
                      name: 'avatar',
                      control,
                      mimeTypes: ['image/jpeg', 'image/png'],
                    }}
                  />
                </div>
              </div>

              <div className='flex flex-col gap-6'>
                <h1 className='md:text-xl text-base text-slate-800 font-medium pb-4 border-b border-slate-200'>
                  {tCommon('my-page.expert_info')}
                </h1>
                <form onSubmit={handleSubmit(onSubmit)} id={FORM_ID} className='flex flex-col gap-6 max-w-[694px]'>
                  <FormField
                    className='md:flex-row flex-col'
                    type='input'
                    label={tCommon('expert_matching.name')}
                    componentProps={{
                      name: 'name',
                      control,
                      required: true,
                      placeholder: 'KimJiwon@nasd',
                    }}
                  />
                  <FormField
                    className='md:flex-row flex-col'
                    type='input'
                    label={tForm('label.school_of_origin')}
                    componentProps={{
                      name: 'schoolFrom',
                      control,
                      required: true,
                      placeholder: 'KimJiwon@nasd',
                    }}
                  />
                  <FormField
                    className='md:flex-row flex-col'
                    type='input'
                    label={tForm('label.affiliation')}
                    componentProps={{
                      name: 'affiliation',
                      control,
                      required: true,
                      placeholder: 'KimJiwon@nasd',
                    }}
                  />
                  <FormField
                    className='md:flex-row flex-col'
                    type='input'
                    label={tForm('label.career')}
                    componentProps={{
                      name: 'personalHistory',
                      control,
                      placeholder: '...',
                      rightInputNode: tCommon('component.year'),
                      required: true,
                    }}
                  />

                  <FormField
                    className='md:flex-row flex-col'
                    type='datalist'
                    label={tForm('label.specialty')}
                    componentProps={{
                      name: 'department',
                      control,
                      required: true,
                      defaultValue:
                        i18n.language === 'ko'
                          ? userDetails?.data.department
                          : departmentOptions.find((dept) => dept.name === userDetails?.data.department)?.nameInEnglish,
                      options: departmentOptions.map((option) => {
                        const name = getNameByLanguage(i18n.language, option);
                        return { label: name, value: name };
                      }),
                      isSearchable: true,
                    }}
                  />
                  <FormField
                    className='md:flex-row flex-col'
                    type='input'
                    label={tForm('label.detailed_field')}
                    componentProps={{
                      name: 'detailedField',
                      control,
                      placeholder: '...',
                      required: true,
                    }}
                  />
                </form>

                {/* Business/Advisory Field */}
                <div className='max-w-[694px] w-full flex flex-col gap-1 border border-slate-200 rounded-lg'>
                  <div className='flex flex-row justify-between px-4 py-3  bg-slate-25 items-center'>
                    <h1>{tCommon('sign_up.business_field')}</h1>
                    <Button sizing='2sm' leftIcon={<PencilSimpleLineIcon />} onClick={() => setOpenEditBusiness(true)}>
                      {tCommon('component.edit')}
                    </Button>
                  </div>
                  {/* */}

                  <div className='p-4'>
                    <div className='flex flex-col gap-5'>
                      <div className='gap-3 flex flex-col'>
                        <h3 className='text-primary-500 text-sm font-medium'>{tForm('label.business_field')}</h3>
                        <div className='flex flex-col gap-3'>
                          {userDetails?.data?.businessFieldCategories
                            ?.filter((category) => category.isUserSelected) // Optional: Filter based on your needs
                            .map((category) => (
                              <div key={category.id} className='flex flex-col gap-1'>
                                <p className='text-slate-500 text-sm font-normal'>
                                  {i18n.language === 'en' ? category.nameInEnglish : category.name}
                                </p>{' '}
                                {/* Business Category Name */}
                                {
                                  <p className='font-medium text-sm text-slate-800'>
                                    {category.businessFields
                                      ?.filter((field) => field.isUserSelected)
                                      .map((field) => (i18n.language === 'en' ? field.nameInEnglish : field.name))
                                      .join(', ')}{' '}
                                    {/* Business Fields */}
                                  </p>
                                }
                              </div>
                            ))}
                        </div>
                      </div>
                    </div>

                    <div className=' flex flex-col gap-3 mt-5'>
                      <h3 className='text-primary-500 text-sm font-medium'>{tCommon('sign_up.advisory_field_section')}</h3>
                      <div className='flex flex-col gap-3'>
                        {userDetails?.data?.advisoryFieldCategories
                          ?.filter((category) => category.isUserSelected)
                          .map((category) => (
                            <div key={category.id} className='flex flex-col gap-1'>
                              <p className='text-slate-500 text-sm font-normal'>
                                {i18n.language === 'en' ? category.nameInEnglish : category.name}
                              </p>
                              <p className='font-medium text-sm text-slate-800 '>
                                {category.advisoryFields
                                  .filter((field) => field.isUserSelected)
                                  .map((field: any) => (i18n.language === 'en' ? field.nameInEnglish : field.name))
                                  .join(', ')}{' '}
                              </p>
                            </div>
                          ))}
                      </div>
                    </div>
                  </div>
                </div>

                {/*  */}
                <div className='max-w-[694px] w-fit lg:w-full mb-6'>
                  <FormField
                    className='md:flex-row flex-col '
                    type='file'
                    label={tForm('label.resume')}
                    info={
                      <div>
                        <span>{tCommon('my-page.resume_upload_label_1')}</span>
                        <br />
                        <span>{tCommon('my-page.resume_upload_label_2')}</span>
                      </div>
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
                </div>

                {/* Professional Career Information */}

                <div className='flex flex-col gap-5'>
                  <h1 className='md:text-xl text-base text-slate-800 font-medium pb-4 border-b border-slate-200'>
                    {tCommon('my-page.professional_career_information')}
                  </h1>

                  {/* Education */}
                  <div className='max-w-[694px] w-full flex flex-col  border border-slate-200 rounded-lg'>
                    <div className='flex flex-row justify-between px-4 py-3  bg-slate-25 items-center'>
                      <h1 className='text-slate-800 text-base font-normal'>{tCommon('expert_matching.education')}</h1>
                      <div className='flex flex-row gap-3'>
                        <Button
                          className='md:min-w-[66px] max-h-[28px]'
                          sizing='2sm'
                          // leftIcon={<PlusIcon />}
                          onClick={() => setOpenAddEducation(true)}>
                          <div className='flex gap-1 justify-center items-center'>
                            <PlusIcon className='size-4 text-slate-500' />
                            <span> {tCommon('my-page.addition')}</span>
                          </div>
                        </Button>
                        {!isOpenEditEducationStatus && (
                          <Button
                            className='md:min-w-[66px] max-h-[28px]'
                            sizing='2sm'
                            // leftIcon={<PencilSimpleLineIcon />}
                            onClick={() => setIsOpenEditEducationStatus(true)}>
                            <div className='flex gap-1 justify-center items-center'>
                              <PencilSimpleLineIcon className='size-4 text-slate-500' />
                              <span> {tCommon('component.edit')}</span>
                            </div>{' '}
                          </Button>
                        )}
                        {isOpenEditEducationStatus && (
                          <Button
                            sizing='2sm'
                            className='md:min-w-[66px] max-h-[28px]'
                            variant='primary'
                            onClick={() => {
                              setisConfirmEducation(true), setIsOpenEditEducationStatus(false);
                            }}>
                            {tCommon('my-page.confirm')}
                          </Button>
                        )}
                      </div>
                    </div>

                    <div className='p-4 flex flex-col gap-5'>
                      <div className='gap-5 flex flex-col'>
                        {isOpenEditEducationStatus
                          ? educations?.map((education, index) => (
                              <div
                                key={index}
                                className='flex flex-row justify-between '
                                draggable
                                onDragStart={() => (dragItem.current = index)}
                                onDragEnter={() => (dragOverItem.current = index)}
                                onDragEnd={handleSort}
                                onDragOver={(e) => e.preventDefault()}>
                                <div className='flex flex-col gap-1'>
                                  <p className='font-normal text-slate-500 text-sm'>{`${education.entranceYear}.${education.entranceMonth} - ${education.graduationYear}.${education.graduationMonth}`}</p>
                                  <p className='font-bold text-base text-slate-800'>{education.name}</p>
                                  <p className='text-sm text-slate-500 font-medium'>{education.detail}</p>
                                </div>
                                <div className='flex flex-row gap-8 items-center justify-center h-fit'>
                                  <ListIcon className='w-[12px] h-[9px] ' />
                                  <Button
                                    className='md:min-w-[66px] max-h-[28px]'
                                    sizing='2sm'
                                    // leftIcon={<PencilSimpleLineIcon />}
                                    onClick={() => {
                                      handleOpenEditEducation(education);
                                    }}>
                                    <div className='flex gap-1 justify-center items-center'>
                                      <PencilSimpleLineIcon className='size-4 text-slate-500' />
                                      <span> {tCommon('component.edit')}</span>
                                    </div>
                                  </Button>
                                </div>
                              </div>
                            ))
                          : educations?.map((education, index) => (
                              <div key={index} className='flex flex-row justify-between ' draggable>
                                <div className='flex flex-col gap-1'>
                                  <p className='font-normal text-slate-500 text-sm'>{`${education.entranceYear}.${education.entranceMonth} - ${education.graduationYear}.${education.graduationMonth}`}</p>
                                  <p className='font-bold text-base text-slate-800'> {education.name}</p>
                                  <p className='text-sm text-slate-500 font-medium'> {education.detail}</p>
                                </div>
                                <div className='flex flex-row gap-4'></div>
                              </div>
                            ))}
                      </div>
                    </div>
                  </div>

                  {/* Personal history */}
                  <div className='max-w-[694px]  w-full flex flex-col  border border-slate-200 rounded-lg'>
                    <div className='flex flex-row justify-between px-4 py-3  max-h-[52px] bg-slate-25 items-center'>
                      <h1>{tForm('label.career_my_page')}</h1>
                      <div className='flex flex-row gap-3'>
                        <Button
                          className='md:min-w-[66px] max-h-[28px] '
                          sizing='2sm'
                          // leftIcon={<PlusIcon />}
                          onClick={() => setOpenAddExperience(true)}>
                          <div className='flex gap-1 justify-center items-center'>
                            <PlusIcon className='size-4 text-slate-500' />
                            <span> {tCommon('my-page.addition')}</span>
                          </div>
                        </Button>
                        {!isOpenEditExperienceStatus && (
                          <Button
                            className='md:min-w-[66px] max-h-[28px]'
                            sizing='2sm'
                            // leftIcon={<PencilSimpleLineIcon />}
                            onClick={() => setIsOpenEditExperienceStatus(true)}>
                            <div className='flex gap-1 justify-center items-center'>
                              <PencilSimpleLineIcon className='size-4 text-slate-500' />
                              <span> {tCommon('component.edit')}</span>
                            </div>
                          </Button>
                        )}
                        {isOpenEditExperienceStatus && (
                          <Button
                            className='md:min-w-[66px] max-h-[28px]'
                            sizing='2sm'
                            variant='primary'
                            onClick={() => {
                              setIsConfirmExperience(true);
                              setIsOpenEditExperienceStatus(false);
                            }}>
                            {tCommon('my-page.confirm')}
                          </Button>
                        )}
                      </div>
                    </div>

                    <div className='p-4 flex flex-col gap-5'>
                      <div className='gap-5 flex flex-col'>
                        {isOpenEditExperienceStatus
                          ? experiences?.map((experience, index) => (
                              <div
                                key={index}
                                className='flex flex-row justify-between'
                                draggable
                                onDragStart={() => (dragItem.current = index)}
                                onDragEnter={() => (dragOverItem.current = index)}
                                onDragEnd={handleSortExperience} // New sort handler for experiences
                                onDragOver={(e) => e.preventDefault()}>
                                <div className='flex flex-col gap-1'>
                                  <p className='font-normal text-slate-500 text-sm'>
                                    {' '}
                                    {experience.endYear === new Date().getFullYear() &&
                                    experience.endMonth === new Date().getMonth() + 1
                                      ? `${experience.startYear} - ${tCommon('my-page.present')}`
                                      : `${experience.startYear} - ${experience.endYear}`}
                                  </p>
                                  <p className='font-bold text-base text-slate-800'>{experience.name}</p>
                                  <p className='text-sm text-slate-500 font-medium'>{experience.detail}</p>
                                </div>
                                <div className='flex flex-row gap-8 items-center justify-center h-fit'>
                                  <ListIcon className='w-[12px] h-[9px]' />
                                  <Button
                                    className='md:min-w-[66px] max-h-[28px]'
                                    sizing='2sm'
                                    // leftIcon={<PencilSimpleLineIcon />}
                                    onClick={() => handleOpenEditExperience(experience)}>
                                    <div className='flex gap-1 justify-center items-center'>
                                      <PencilSimpleLineIcon className='size-4 text-slate-500' />
                                      <span> {tCommon('component.edit')}</span>
                                    </div>
                                  </Button>
                                </div>
                              </div>
                            ))
                          : experiences?.map((experience, index) => (
                              <div key={index} className='flex flex-row justify-between'>
                                <div className='flex flex-col gap-1'>
                                  <p className='font-normal text-slate-500 text-sm'>
                                    {' '}
                                    {experience.endYear === new Date().getFullYear() &&
                                    experience.endMonth === new Date().getMonth() + 1
                                      ? `${experience.startYear} - ${tCommon('my-page.present')}`
                                      : `${experience.startYear} - ${experience.endYear}`}
                                  </p>
                                  <p className='font-bold text-base text-slate-800'>{experience.name}</p>
                                  <p className='text-sm text-slate-500 font-medium'>{experience.detail}</p>
                                </div>
                                <div className='flex flex-row gap-4'></div>
                              </div>
                            ))}
                      </div>
                    </div>
                  </div>
                </div>
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
            oldEmail={userDetails?.data.email}
          />
          <AddEducationModal open={openAddEducation} onClose={onCloseAddEducation} handleAddEducation={handleAddEducation} />
          {educationToEdit && (
            <EditEducationModal
              open={openEditEducation}
              onClose={() => setOpenEditEducation(false)}
              handleEditEducation={handleEditEducation}
              initialValues={educationToEdit}
              handleDeleteEducation={handleDeleteEducation}
            />
          )}
          <AddExperienceModal
            open={openAddExperience}
            onClose={() => setOpenAddExperience(false)}
            handleAddExperience={handleAddExperience}
          />
          {experienceToEdit && (
            <EditExperienceModal
              open={openEditExperience}
              onClose={() => setOpenEditExperience(false)}
              handleEditExperience={handleEditExperience}
              initialValues={experienceToEdit}
              handleDeleteExperience={handleDeleteExperience}
            />
          )}
          <EditBusinessAdvisoryModal
            advisories={userDetails?.data.advisoryFieldCategories}
            business={userDetails?.data.businessFieldCategories}
            open={openEditBusiness}
            onClose={() => setOpenEditBusiness(false)}
            userDetail={userDetails?.data}
          />
        </div>
      </MyPageContainer>
    );
  },
  [USER_ROLE.EXPERT],
  [],
);

export default InfoExpertPage;
