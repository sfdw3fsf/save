import Loading from '@/components/core/loading/Loading';
import PermissionAccept from '@/components/hocs/PermissionAccept';
import { MeetingScheduleDTO } from '@/data/project/dto/project.dto';
import { acceptInitialMeetingsExpertAPI } from '@/data/project/project-expert.api';
import InfoCard from '@/pages/project-request/project-request-indiv/components/InfoCard';
import InfoCardItem from '@/pages/project-request/project-request-indiv/components/InfoCardItem';
import { ROUTES } from '@/router/routes.constant';
import { useAuthStore } from '@/store/auth.store';
import { USER_ROLE } from '@/utils/enums/user/user-role.enum';
import { onError } from '@/utils/helpers/data.helper';
import { withFallbackStr } from '@/utils/helpers/string.helper';
import { useMutation } from '@tanstack/react-query';
import { useEffect, useMemo, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { useNavigate, useParams } from 'react-router-dom';
import ProposalCalendar from '../components/ProposalCalendar';
import { MEETING_STATUS_ENUM } from '../enums/meeting-status.enums';
import { extractMeetings } from '../helpers/meetings.helpers';
import { useFetchMeetings } from '../hooks/useFetchMeetings';
import ScheduleMeeting from '../schedule-meeting/ScheduleMeeting';
import ProjectDetail from './ProjectDetail';

const ProjectDetailExpertPage = PermissionAccept(
  (): JSX.Element => {
    const { t: tCommon } = useTranslation('common');
    const { projectId } = useParams();
    const navigate = useNavigate();
    const { userInfo } = useAuthStore();

    // STATES
    const [openScheduleCalendar, setOpenScheduleCalendar] = useState(false);
    const [selectedCard, setSelectedCard] = useState<MeetingScheduleDTO | null>(null);

    // APIS
    const {
      projectDetail,
      isLoadingDetail,
      refetchProjectDetail,
      proposalMeetings,
      isLoadingProposalMeetings,
      refetchProposalMeetings,
      canConfirmProposal,
      refetchCanConfirmProposal,
    } = useFetchMeetings({ projectId: +projectId!, userRole: userInfo?.userRole });

    const { mutate: acceptInitialMeetingsApi, isPending: isAccepingInitial } = useMutation({
      mutationFn: () => acceptInitialMeetingsExpertAPI(+projectId!),
      onSuccess: async () => {
        onRefetch().finally(() => setOpenScheduleCalendar(true));
      },
      onError,
    });

    // HELPERS
    const meetingInfo = useMemo(() => {
      return extractMeetings({
        projectDetail,
        proposalMeetings,
        userInfo,
      });
    }, [projectDetail, proposalMeetings, userInfo]);
    const { meetingStatus, meetings, backgroundMeetings, foregroundMeetings, meetingsInCard } = meetingInfo;

    // EFFECTS
    useEffect(() => {
      if (!selectedCard && meetings?.length !== 0) {
        setSelectedCard(meetings[0]);
      }
    }, [meetings, selectedCard]);

    // METHODS
    const onRefetch = async () => {
      Promise.all([refetchProposalMeetings(), refetchProjectDetail(), refetchCanConfirmProposal()]);
    };

    const onSaveCalendar = () => {
      onRefetch();
      setOpenScheduleCalendar(false);
    };

    const onViewCalendar = async () => {
      if (meetingStatus === MEETING_STATUS_ENUM.INITIAL) {
        acceptInitialMeetingsApi();
      } else {
        setOpenScheduleCalendar(true);
      }
    };

    // RENDER
    const renderTopSection = useMemo(() => {
      return (
        <>
          <div className='text-slate-500 text-md md:text-lg'>{tCommon('project_management.client_detail')}</div>
          <InfoCard title={tCommon('project_request.company_information')}>
            <InfoCardItem
              className='col-span-2'
              label={tCommon('project_management.company_name')}
              content={withFallbackStr(projectDetail?.clientDetail?.companyName)}
            />
          </InfoCard>
        </>
      );
    }, [projectDetail?.clientDetail?.companyName, tCommon]);

    const renderLeftSection = useMemo(() => {
      return (
        <ProposalCalendar
          proposalMeetings={meetingsInCard}
          userRole={userInfo?.userRole}
          onViewCalendar={onViewCalendar}
          onViewDetail={(meeting) => {
            setSelectedCard(meeting);
            onViewCalendar();
          }}
          loading={isAccepingInitial}
          //isBadge={meetingStatus !== MEETING_STATUS_ENUM.FINAL}
          //isBadge={foregroundMeetings?.length === 0}
          isBadge={false}
        />
      );
    }, [isAccepingInitial, meetingsInCard, userInfo?.userRole]);

    if (isLoadingDetail) {
      return <Loading />;
    }
    return (
      <>
        <ProjectDetail
          detail={projectDetail}
          onBackToList={() => navigate(ROUTES.PROJECT_MANAGEMENT.EXPERT)}
          topSection={renderTopSection}
          leftSection={renderLeftSection}
          title={tCommon('project_request.service_application_form_expert')}
          className={openScheduleCalendar ? 'overflow-hidden h-[100vh]' : undefined}
        />
        {!!projectId && !!meetingInfo && (
          <ScheduleMeeting
            //open={openScheduleCalendar && projectDetail?.haveProposal}
            open={openScheduleCalendar}
            onSave={onSaveCalendar}
            onClose={() => {
              setOpenScheduleCalendar(false);
            }}
            titleProps={{
              // title: tCommon('calendar.set_meeting_time'),
              // subTitle: tCommon('calendar.please_select_meeting_time'),
              title: tCommon('calendar.expert_header.title'),
              subTitle: tCommon('calendar.expert_header.sub_title'),
              info:
                foregroundMeetings?.length === 0
                  ? tCommon('calendar.please_select_from_1_to_2_green')
                  : tCommon('calendar.please_select_from_1_to_2_client'),
            }}
            defaultDate={selectedCard?.start}
            backgroundMeetings={backgroundMeetings}
            foregroundMeetings={foregroundMeetings}
            projectId={+projectId!}
            canConfirmProposal={canConfirmProposal}
            isLoading={isLoadingDetail || isLoadingProposalMeetings || isAccepingInitial}
            canCreateProposalMeetings={projectDetail?.canCreateProposalMeetings}
            //isEditable={foregroundMeetings?.length === 0}
            isEditable={projectDetail?.canCreateProposalMeetings}
          />
        )}
      </>
    );
  },
  [USER_ROLE.EXPERT],
  [],
);

export default ProjectDetailExpertPage;
