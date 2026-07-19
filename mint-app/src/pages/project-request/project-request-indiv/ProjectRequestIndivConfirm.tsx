import Button from '@/components/core/button/Button';
import Divider from '@/components/core/divider/Divider';
import FileCard from '@/components/core/file-card/FileCard';
import ArrowLeftIcon from '@/components/icons/ArrowLeftIcon';
import ArrowRightIcon from '@/components/icons/ArrowRightIcon';
import PencilSimpleLineIcon from '@/components/icons/PencilSimpleLineIcon';
import { editProjectAPI, requestProjectAPI } from '@/data/project/project-indiv.api';
import { RequestProjectREQ } from '@/data/project/request/project.request';
import { RequestProjectRESP } from '@/data/project/response/project.response';
import { GeneratePresignedUrlREQ } from '@/data/storage/request/generate-presigned-url.request';
import { generatePresignedUrlAPI } from '@/data/storage/storage.api';
import i18n, { I18nLanguage } from '@/i18n';
import { ROUTES } from '@/router/routes.constant';
import { BaseResponse } from '@/types/response.type';
import { S3File } from '@/types/s3-file.type';
import { QUERY_KEYS } from '@/utils/constants/data.constant';
import { ADVISORY_TYPE } from '@/utils/enums/project/advisory.enum';
import { MEETING_FORMAT_ENUM } from '@/utils/enums/project/meeting-format.enum';
import { onError } from '@/utils/helpers/data.helper';
import { getStorageContentType } from '@/utils/helpers/file.helper';
import { withFallbackStr } from '@/utils/helpers/string.helper';
import { useMutation, useQuery } from '@tanstack/react-query';
import { enqueueSnackbar } from 'notistack';
import { useMemo, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { useNavigate } from 'react-router-dom';
import { twMerge } from 'tailwind-merge';
import { SuccessToast } from '../../../components/core/success-notification/SuccessNotification';
import BasicInfoSection from './components/BasicInfoSection';
import BlockContent from './components/BlockContent';
import InfoCard from './components/InfoCard';
import InfoCardItem from './components/InfoCardItem';
import { combineDates } from './helpers/combine-dates.helpers';
import { extractFields } from './helpers/project-request-div.helpers';
import { useCombineFields } from './hooks/useCombineFields';
import { ProjectRequestData } from './ProjectRequestIndivPage';

type ProjectRequestIndivConfirmProps = {
  projectRequestData: ProjectRequestData | null;
  onEdit: () => void;
  projectId?: number; // If projectId exists => edit, else create
};
const ProjectRequestIndivConfirm = ({ projectRequestData, onEdit, projectId }: ProjectRequestIndivConfirmProps): JSX.Element => {
  const { t: tCommon } = useTranslation('common');
  const { t: tForm } = useTranslation('form');
  const language = i18n.language as I18nLanguage;
  const { projectBusinessCombineInput } = projectRequestData as ProjectRequestData;
  const { filteredBusinessField, filteredAdvisoryField, filteredMedicalField } = useCombineFields(projectBusinessCombineInput);
  const navigate = useNavigate();

  // STATES
  const [isUploadingFile, setIsUploadingFile] = useState(false);
  const consultantDates = useMemo(() => combineDates(projectRequestData?.meetings || []), [projectRequestData?.meetings]);

  // APIS
  const { refetch: generatePresignedUrlApi } = useQuery({
    queryKey: [QUERY_KEYS.STORAGE_PRESIGNED_URL],
    queryFn: () =>
      generatePresignedUrlAPI({
        storageDomain: 'USER',
        storageContentType: getStorageContentType(projectRequestData?.attachment),
      } as GeneratePresignedUrlREQ),
    select: ({ data }: BaseResponse<S3File>) => data,
    enabled: false,
  });

  const { mutate: requestProjectApi, isPending: isRequesting } = useMutation({
    mutationFn: (request: RequestProjectREQ) => requestProjectAPI(request),
    onSuccess: ({ data }) => {
      onSuccess(data);
    },
    onError,
  });

  const { mutate: editProjectApi } = useMutation({
    mutationFn: (request: RequestProjectREQ) => editProjectAPI(projectId!, request),
    onSuccess: () => onSuccess(),
    onError,
  });

  // METHODS
  const onSuccess = (data?: RequestProjectRESP) => {
    navigate(ROUTES.PROJECT_MANAGEMENT.INDIVIDUAL);
    if (!projectId) {
      return SuccessToast({
        label: tForm('success.project_request.title'),
        content: tForm('success.project_request.subTitle'),
        bottomSection: (
          <button
            form='_notexist'
            className='text-sm text-primary-500'
            onClick={() => {
              if (data) {
                navigate(`${ROUTES.PROJECT_MANAGEMENT.INDIVIDUAL}/${data?.projectId}`);
              }
            }}>
            {tForm('success.project_request.view_application')}
          </button>
        ),
      });
    } else {
      return SuccessToast({
        label: tForm('success.project_edit.title'),
        content: tForm('success.project_edit.subTitle'),
      });
    }
  };

  const onSubmit = async () => {
    const { attachment } = projectRequestData as ProjectRequestData;

    // Inculde File
    if (attachment) {
      // Get URL to upload
      setIsUploadingFile(true);
      const fileType = getStorageContentType(attachment);

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
        await fetch(data.url, { method: 'PUT', body: attachment }).then(async () => {
          await handleRequestProject(attachment, data.key, fileType);
        });
      } catch (error) {
        handleErrorUploadFile();
        return;
      } finally {
        setIsUploadingFile(false);
      }
    } else {
      // Not include File
      await handleRequestProject();
    }
  };

  const handleErrorUploadFile = () => {
    setIsUploadingFile(false);
    enqueueSnackbar({ variant: 'error', message: tForm('verify.upload_failed'), autoHideDuration: 3000 });
  };

  const handleRequestProject = async (file?: File, key?: string, fileType?: string) => {
    const businessField = extractFields(projectBusinessCombineInput?.businessFieldIds, 'businessField', 'businessFieldEtc');
    const advisoryField = extractFields(projectBusinessCombineInput?.advisoryFieldIds, 'advisoryField', 'advisoryFieldEtc');
    const medicalField = extractFields(projectBusinessCombineInput?.medicalFieldIds, 'medicalField', 'medicalFieldEtc');
    const { name, advisoryType, contentDetail, advisoryTime, meetings } = projectRequestData as ProjectRequestData;
    const request: RequestProjectREQ = {
      name: name,
      businessFieldIds: businessField?.field || [],
      advisoryFieldIds: advisoryField?.field || [],
      medicalFieldIds: medicalField?.field || [],
      advisoryType: advisoryType as ADVISORY_TYPE,
      meetingFormat: MEETING_FORMAT_ENUM.VIDEO_MEETING,
      advisoryTime: advisoryTime,
      otherBusinessFields: businessField?.otherField || [],
      otherAdvisoryFields: advisoryField?.otherField || [],
      otherMedicalFields: medicalField?.otherField || [],
      meetings:
        meetings?.map((meeting) => ({
          startTime: meeting.start?.getTime() as number,
          endTime: meeting.end?.getTime() as number,
        })) || [],
      contentDetail: contentDetail!,

      attachment: file && key && fileType ? { name: file.name, fileType: fileType, key: key, size: file.size } : undefined,
    };
    if (!projectId) {
      requestProjectApi(request);
    } else {
      editProjectApi(request);
    }
  };

  if (!projectRequestData) return <></>;
  return (
    <>
      <div className='flex flex-col mt-2'>
        <div className='font-medium text-md'>{tCommon('project_request.confirm.title')}</div>
        <div className='text-slate-500 mt-0.5 mb-8'>{tCommon('project_request.confirm.description')}</div>
        <div className='items-start grid md:grid-cols-2 grid-cols-1 gap-x-10 gap-y-4'>
          <div className='flex flex-col col-span-1 md:gap-6 gap-4 md:[&>*:nth-child(2)]:-mt-2'>
            <div className='text-slate-500 text-md md:text-lg'>{tCommon('project_request.basic_information')}</div>
            <BasicInfoSection />
          </div>
          <div className='flex flex-col col-span-1 md:gap-6 gap-4 md:[&>*:nth-child(2)]:-mt-2'>
            <div className='text-slate-500 text-md md:text-lg'>{tCommon('project_request.service_request_info')}</div>
            <InfoCard title={tCommon('project_request.advisory_info')}>
              <div className='flex flex-col w-full gap-5 text-sm text-left md:col-span-2 col-span-1 mb-4'>
                {filteredBusinessField.length > 0 && (
                  <BlockContent title={tForm('label.business_area_consult_on')} content={filteredBusinessField} />
                )}
                {filteredAdvisoryField.length > 0 && (
                  <BlockContent title={tForm('label.main_advisory')} content={filteredAdvisoryField} />
                )}
                {filteredMedicalField.length > 0 && (
                  <BlockContent title={tForm('label.medical_field')} content={filteredMedicalField} />
                )}
              </div>
              <InfoCardItem
                className='md:col-span-2 col-span-1'
                label={tCommon('project_request.project_name')}
                content={projectRequestData.name}
                contentStyle='font-medium text-comp-text-default'
              />
              <InfoCardItem
                className='md:col-span-2 col-span-1'
                label={tCommon('project_request.advisory_type')}
                content={tCommon(`project_request.${projectRequestData.advisoryType}` as any)}
                contentStyle='font-medium text-comp-text-default'
              />
              <InfoCardItem
                label={tCommon('project_request.desired_consultant_date')}
                content={consultantDates}
                contentStyle='font-medium text-comp-text-default'
                contentTooltip={
                  projectRequestData?.meetings && projectRequestData?.meetings?.length > 2 ? consultantDates : undefined
                }
              />
              <InfoCardItem
                label={tCommon('project_request.advisory_time')}
                content={withFallbackStr(projectRequestData.advisoryTime?.toString()) + ' ' + tCommon('project_request.hour')}
                contentStyle='font-medium text-comp-text-default'
              />
            </InfoCard>
            <InfoCard title={tCommon('project_request.advisory_content_detail')}>
              <div className='text-sm text-slate-500 col-span-2'>{projectRequestData.contentDetail}</div>
            </InfoCard>
            {!!projectRequestData.attachment && (
              <InfoCard title={tCommon('project_request.attach_file')} childrenCont='md:grid-cols-1 grid-cols-1'>
                <FileCard file={projectRequestData.attachment} />
              </InfoCard>
            )}
          </div>
        </div>
      </div>
      <div className='flex flex-col gap-4 my-4 w-full'>
        <Divider />
        <div
          className={twMerge(
            'flex md:w-fit w-full justify-end items-center self-end gap-2',
            language === 'en' && 'max-sm:flex-wrap',
          )}>
          <Button
            className='w-full md:px-8'
            leftIcon={<ArrowLeftIcon />}
            onClick={() => navigate(ROUTES.PROJECT_MANAGEMENT.INDIVIDUAL)}>
            {tCommon('project_request.inventory')}
          </Button>
          <Button className='w-full md:px-8' rightIcon={<PencilSimpleLineIcon />} onClick={onEdit}>
            {tCommon('project_request.edit')}
          </Button>
          <Button
            className='w-full md:px-8'
            variant='primary'
            rightIcon={<ArrowRightIcon />}
            onClick={onSubmit}
            isLoading={isUploadingFile || isRequesting}>
            {tCommon('project_request.submit_request')}
          </Button>
        </div>
      </div>
    </>
  );
};

export default ProjectRequestIndivConfirm;
