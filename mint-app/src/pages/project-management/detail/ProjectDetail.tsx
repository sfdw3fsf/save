import Button from '@/components/core/button/Button';
import Divider from '@/components/core/divider/Divider';
import FileCard from '@/components/core/file-card/FileCard';
import PageContainer from '@/components/core/page-container/PageContainer';
import ArrowLeftIcon from '@/components/icons/ArrowLeftIcon';
import { ProjectDetailRESP } from '@/data/project/response/project.response';
import BlockContent from '@/pages/project-request/project-request-indiv/components/BlockContent';
import InfoCard from '@/pages/project-request/project-request-indiv/components/InfoCard';
import InfoCardItem from '@/pages/project-request/project-request-indiv/components/InfoCardItem';
import {
  combineAdvisoryFields,
  combineBusinessFields,
  combineMedicalFields,
} from '@/pages/project-request/project-request-indiv/helpers/project-request-div.helpers';
import { fDate } from '@/utils/helpers/datetime.helper';
import { withFallbackStr } from '@/utils/helpers/string.helper';
import { useMemo } from 'react';
import { useTranslation } from 'react-i18next';
import ProjectStatusBadge from '../components/ProjectStatusBadge';

type ProjectDetailProps = {
  detail?: ProjectDetailRESP;
  rightSection?: React.ReactNode;
  bottomSection?: React.ReactNode;
  leftSection?: React.ReactNode;
  topSection?: React.ReactNode;
  onBackToList?: () => void;
  title?: string;
  className?: string;
};

const ProjectDetail = ({
  detail,
  rightSection,
  bottomSection,
  leftSection,
  topSection,
  onBackToList,
  title,
  className,
}: ProjectDetailProps): JSX.Element => {
  const { t: tCommon } = useTranslation('common');
  const { t: tForm } = useTranslation('form');
  const businessField = useMemo(() => combineBusinessFields(detail?.businessFieldCategories), [detail?.businessFieldCategories]);
  const advisoryField = useMemo(() => combineAdvisoryFields(detail?.advisoryFieldCategories), [detail?.advisoryFieldCategories]);
  const medicalField = useMemo(() => combineMedicalFields(detail?.medicalFieldCategories), [detail?.medicalFieldCategories]);

  const consultantDates = useMemo(() => {
    if (detail?.meetings?.length === 0) return '';
    return detail?.meetings
      ?.map((meeting) => {
        return `${fDate(new Date(meeting?.meeting?.startTime), 'D[th]')} (${fDate(new Date(meeting?.meeting?.startTime), 'HH:mm')} - ${fDate(new Date(meeting?.meeting.endTime), 'HH:mm')})`;
      })
      .join(' - ');
  }, [detail?.meetings]);
  if (!detail) return <></>;
  return (
    <PageContainer
      title={tCommon('project_request.project_detail')}
      isFullScreen
      hiddenScroll
      className='items-start lg:px-28 px-4 py-14'
      containerStyle={className}>
      <div className='flex flex-col md:gap-8  p-8 bg-white rounded-xl border-[1px] border-slate-200 w-full'>
        <div className='grid md:grid-cols-2 grid-cols-1 gap-10'>
          <div className='col-span-1 flex flex-col'>
            <div className='md:text-display-sm text-xl md:mb-6 mb-2 font-medium'>{title}</div>
            <div className='text-comp-text-secondary text-lg mt-6 mb-2'>{tCommon('project_management.project_status')}</div>
            <ProjectStatusBadge projectStatus={detail?.status} />
          </div>
          {rightSection ? (
            <div className='col-span-1'>{rightSection}</div>
          ) : (
            <div className='col-span-1 flex flex-col max-md:hidden' />
          )}
          {leftSection ? (
            <div className='col-span-1 gap-4 flex flex-col'>{leftSection}</div>
          ) : (
            <div className='col-span-1 flex flex-col max-md:hidden' />
          )}

          <div className='col-span-1 flex flex-col gap-4'>
            {!!topSection && topSection}
            <div className='text-slate-500 text-md md:text-lg'>{tCommon('project_request.service_request_info')}</div>
            <InfoCard title={tCommon('project_request.advisory_info')}>
              <div className='flex flex-col w-full gap-6 text-sm text-left md:col-span-2 col-span-1 mb-4'>
                {businessField?.length > 0 && (
                  <BlockContent title={tForm('label.business_area_consult_on')} content={businessField} />
                )}
                {advisoryField?.length > 0 && <BlockContent title={tForm('label.main_advisory')} content={advisoryField} />}
                {medicalField?.length > 0 && <BlockContent title={tForm('label.consultation_request')} content={medicalField} />}
              </div>
              <InfoCardItem
                className='md:col-span-2 col-span-1'
                label={tCommon('project_request.project_name')}
                content={detail?.name}
                contentStyle='font-medium text-comp-text-default'
              />
              <InfoCardItem
                className='md:col-span-2 col-span-1'
                label={tCommon('project_request.advisory_type')}
                content={tCommon(`project_request.${detail?.advisoryType}` as any)}
                contentStyle='font-medium text-comp-text-default'
              />
              <InfoCardItem
                label={tCommon('project_request.desired_consultant_date')}
                content={consultantDates}
                contentStyle='font-medium text-comp-text-default'
                contentTooltip={detail?.meetings && detail?.meetings?.length > 2 ? consultantDates : undefined}
              />
              <InfoCardItem
                label={tCommon('project_request.advisory_time')}
                content={withFallbackStr(detail?.advisoryTime?.toString()) + ' ' + tCommon('project_request.hour')}
                contentStyle='font-medium text-comp-text-default'
              />
            </InfoCard>
            <InfoCard title={tCommon('project_request.advisory_content_detail')}>
              <div className='text-sm text-slate-500 col-span-2'>{detail?.contentDetail}</div>
            </InfoCard>
            {!!detail.attachment && (
              <InfoCard title={tCommon('project_request.attach_file')} childrenCont='md:grid-cols-1 grid-cols-1'>
                <button
                  className='flex w-full'
                  form='_noexist'
                  onClick={() => {
                    window.open(detail?.attachment?.url, '_blank');
                  }}>
                  <FileCard file={detail?.attachment as any} />
                </button>
              </InfoCard>
            )}
            {bottomSection && bottomSection}
          </div>
        </div>
        <div className='flex flex-col w-full gap-4 my-4 col-span-2 items-start'>
          <Divider />
          <Button
            className='md:w-fit w-full md:px-10 px-4 flex self-center'
            leftIcon={<ArrowLeftIcon />}
            onClick={() => onBackToList?.()}>
            {tCommon('project_request.inventory')}
          </Button>
        </div>
      </div>
    </PageContainer>
  );
};

export default ProjectDetail;
