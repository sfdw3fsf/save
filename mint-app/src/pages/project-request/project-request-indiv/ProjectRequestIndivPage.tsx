import Loading from '@/components/core/loading/Loading';
import { TScheduleEvent } from '@/components/core/schedule-calendar/ScheduleCalendar';
import PermissionAccept from '@/components/hocs/PermissionAccept';
import { getProjectDetailIndivAPI } from '@/data/project/project-indiv.api';
import { StorageContentType } from '@/types/storage-content-type.type';
import { QUERY_KEYS } from '@/utils/constants/data.constant';
import { ADVISORY_TYPE } from '@/utils/enums/project/advisory.enum';
import { USER_ROLE } from '@/utils/enums/user/user-role.enum';
import { convertTypeToMineType } from '@/utils/helpers/file.helper';
import { useQuery } from '@tanstack/react-query';
import { useEffect, useMemo, useState } from 'react';
import { useParams } from 'react-router-dom';
import { ProjectBusinessCombineInput } from './business-field-register/ProjectRequestBusinessModal';
import ProjectRequestContainer from './components/ProjectRequestContainer';
import { combineEditData } from './helpers/project-request-div.helpers';
import ProjectRequestIndivConfirm from './ProjectRequestIndivConfirm';
import ProjectRequestIndivStep1 from './ProjectRequestIndivStep1';
import ProjectRequestIndivStep2 from './ProjectRequestIndivStep2';
import ProjectRequestIndivStep3 from './ProjectRequestIndivStep3';
import ProjectRequestIndivStep4 from './ProjectRequestIndivStep4';

export type ProjectRequestIndivProps = {
  formID: string;
  onNext: (data: Partial<ProjectRequestData>) => void;
  initialValues?: Partial<ProjectRequestData> | null;
  isEdit?: boolean;
};

export type ProjectRequestData = {
  name: string;
  advisoryType?: ADVISORY_TYPE;
  advisoryTime?: number;
  projectBusinessCombineInput?: ProjectBusinessCombineInput;
  meetings?: TScheduleEvent[];
  contentDetail?: string;
  attachment?: any;
  agreedTerm?: boolean;
  agreedPolicy?: boolean;
};

const ProjectRequestIndivPage = PermissionAccept(
  (): JSX.Element => {
    const { projectId } = useParams();

    // STATES
    const [currentStep, setCurrentStep] = useState(1);
    const [projectRequestData, setProjectRequestData] = useState<ProjectRequestData | null>(null);

    // APIS
    const { data: data, isFetching: isFetchingDetail } = useQuery({
      queryKey: [QUERY_KEYS.PROJECTS.INDIVIDUAL.LIST, projectId],
      queryFn: () => getProjectDetailIndivAPI({ projectId: +projectId! }),
      enabled: !!projectId,
    });

    const projectDetail = useMemo(() => data?.data, [data?.data]);
    const editData = useMemo(() => {
      if (projectDetail) {
        return combineEditData(projectDetail);
      }
      return null;
    }, [projectDetail]);

    // EFFECTS
    useEffect(() => {
      if (projectDetail) {
        setProjectRequestData({
          ...projectDetail,
          attachment: projectDetail?.attachment
            ? {
                ...projectDetail.attachment,
                type: convertTypeToMineType(projectDetail.attachment?.type as StorageContentType),
              }
            : undefined,
          meetings: projectDetail?.meetings?.map((m) => ({
            id: m.meeting.id,
            start: new Date(m.meeting.startTime),
            end: new Date(m.meeting.endTime),
          })),
          projectBusinessCombineInput: editData as ProjectBusinessCombineInput,
          agreedTerm: true,
          agreedPolicy: true,
        });
      }
    }, [editData, projectDetail]);

    // CONST
    const formID = useMemo(() => `project-request-step-${currentStep}`, [currentStep]);

    // METHODS
    const onNext = (data: Partial<ProjectRequestData>) => {
      setProjectRequestData((prev) => ({
        ...prev,
        ...data,
      }));
      if (currentStep < 5) {
        setCurrentStep(currentStep + 1);
      }
    };

    const onPrevious = () => {
      if (currentStep > 1) {
        setCurrentStep(currentStep - 1);
      }
    };

    // RENDERS
    const renderSteps = useMemo(() => {
      switch (currentStep) {
        case 1:
          return <ProjectRequestIndivStep1 formID={formID} onNext={onNext} initialValues={projectRequestData} />;
        case 2:
          return <ProjectRequestIndivStep2 formID={formID} onNext={onNext} initialValues={projectRequestData} />;
        case 3:
          return <ProjectRequestIndivStep3 formID={formID} onNext={onNext} initialValues={projectRequestData} />;
        case 4:
          return (
            <ProjectRequestIndivStep4 formID={formID} onNext={onNext} initialValues={projectRequestData} isEdit={!!projectId} />
          );
        case 5: // Confirm Submit
          return (
            <ProjectRequestIndivConfirm
              projectRequestData={projectRequestData}
              onEdit={() => setCurrentStep(2)}
              projectId={Number(projectId)}
            />
          );
        default:
          return <></>;
      }
      // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [currentStep, formID]);

    useEffect(() => {
      window.scrollTo({ top: 0, left: 0, behavior: 'smooth' });
    }, [currentStep]);

    if (isFetchingDetail) {
      return <Loading />;
    }
    return (
      <ProjectRequestContainer form={formID} currentStep={currentStep} onPrevious={onPrevious} isShowStep={currentStep !== 5}>
        {renderSteps}
      </ProjectRequestContainer>
    );
  },
  [USER_ROLE.MEMBER],
  [],
);

export default ProjectRequestIndivPage;
