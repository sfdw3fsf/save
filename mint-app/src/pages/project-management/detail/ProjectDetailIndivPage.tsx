import DefaultAvatar from '@/assets/images/Avatar.png';
import Button from '@/components/core/button/Button';
import Card from '@/components/core/card/Card';
import Drawer from '@/components/core/drawer/Drawer';
import Loading from '@/components/core/loading/Loading';
import PermissionAccept from '@/components/hocs/PermissionAccept';
import HeatbeatIcon from '@/components/icons/HeartbeatIcon';
import { MeetingScheduleDTO } from '@/data/project/dto/project.dto';
import { ExpertRESP } from '@/data/project/response/project.response';
import BasicInfoSection from '@/pages/project-request/project-request-indiv/components/BasicInfoSection';
import { ROUTES } from '@/router/routes.constant';
import { useAuthStore } from '@/store/auth.store';
import { PROJECT_STATUS } from '@/utils/enums/project/project-list.enum';
import { USER_ROLE } from '@/utils/enums/user/user-role.enum';
import { IMAGES } from '@/utils/theme';
import { useEffect, useMemo, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { useNavigate, useParams } from 'react-router-dom';
import HeaderCard from '../components/HeaderCard';
import ProposalCalendar from '../components/ProposalCalendar';
import { MEETING_STATUS_ENUM } from '../enums/meeting-status.enums';
import ExpertProfile from '../expert-matching-list/ExpertProfile';
import { extractMeetings } from '../helpers/meetings.helpers';
import { useFetchMeetings } from '../hooks/useFetchMeetings';
import CompletedPopUp from '../review/CompletedPopUp';
import ReviewIndiv from '../review/ReviewIndiv';
import ScheduleMeeting from '../schedule-meeting/ScheduleMeeting';
import ProjectDetail from './ProjectDetail';

const ProjectDetailIndivPage = PermissionAccept(
  (): JSX.Element => {
    const { projectId } = useParams();
    const { t: tCommon } = useTranslation('common');
    const navigate = useNavigate();
    const { userInfo } = useAuthStore();

    // STATES
    const [openScheduleCalendar, setOpenScheduleCalendar] = useState(false);
    const [selectedCard, setSelectedCard] = useState<MeetingScheduleDTO | null>(null);
    const [openExpertProfile, setOpenExpertProfile] = useState(false);

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
    const onRefetch = () => {
      refetchProposalMeetings();
      refetchProjectDetail();
      refetchCanConfirmProposal();
    };
    const onSaveCalendar = () => {
      onRefetch();
      setOpenScheduleCalendar(false);
    };

    // RENDER
    const renderRightSection = useMemo(() => {
      if (projectDetail?.expertDetail) {
        const { avatar, fullName, department } = projectDetail?.expertDetail || {};
        return (
          <HeaderCard>
            <Card className='shadow-elevation-sm w-fit h-fit flex flex-row gap-2 p-2'>
              <button className='flex flex-row gap-2 items-center' form='_noexist' onClick={() => setOpenExpertProfile(true)}>
                <div className='size-[40px] shrink-0 rounded-full border-1 border-slate-500 overflow-hidden'>
                  <img
                    src={avatar?.url || DefaultAvatar}
                    className='w-full h-full object-cover'
                    alt={DefaultAvatar}
                    onError={(e: any) => {
                      e.target.src = DefaultAvatar; // Set fallback image on error
                    }}
                  />
                </div>
                <div className='flex flex-col gap-[2px]'>
                  <div className='text-slate-800 text-sm'>{fullName}</div>
                  <div className='text-primary-600 text-xs'>{department}</div>
                </div>
              </button>
            </Card>
          </HeaderCard>
        );
      } else {
        if (projectDetail?.status === PROJECT_STATUS.EXPERT_MATCHING_IN_PROGRESS) {
          if (!projectDetail?.availableExperts) {
            return (
              <HeaderCard
                imgSrc={IMAGES.ILLUSTRATION.SEARCH_QUESTION}
                content={tCommon('project_management.matching_expert_explain')}
              />
            );
          } else {
            return (
              <HeaderCard imgSrc={IMAGES.ILLUSTRATION.LIST} content={tCommon('project_management.matching_expert_list_explain')}>
                <Button
                  className='w-full md:w-fit'
                  variant='primary'
                  labelContStyle='gap-1'
                  rightIcon={<HeatbeatIcon />}
                  onClick={() => navigate('expert-matching-list')}>
                  {tCommon('project_management.view_expert_list')}
                </Button>
              </HeaderCard>
            );
          }
        }
      }
    }, [navigate, projectDetail?.availableExperts, projectDetail?.expertDetail, projectDetail?.status, tCommon]);

    const renderLeftSection = useMemo(() => {
      return (
        <>
          <div className='text-slate-500 text-md md:text-lg'>{tCommon('project_request.basic_information')}</div>
          <BasicInfoSection />
        </>
      );
    }, [tCommon]);

    const renderBottomSection = useMemo(() => {
      return (
        <>
          {meetingStatus !== MEETING_STATUS_ENUM.INITIAL && meetingsInCard && meetingsInCard?.length !== 0 && (
            <ProposalCalendar
              proposalMeetings={meetingsInCard}
              userRole={userInfo?.userRole}
              onViewCalendar={() => setOpenScheduleCalendar(true)}
              onViewDetail={(meeting) => {
                setSelectedCard(meeting);
                setOpenScheduleCalendar(true);
              }}
              // isBadge={meetingStatus !== MEETING_STATUS_ENUM.FINAL}
              // isBadge={foregroundMeetings?.length === 0}
              isBadge={false}
            />
          )}
          {projectDetail?.status === PROJECT_STATUS.PROJECT_COMPLETED && (
            <ReviewIndiv
              projectId={projectDetail?.id as number}
              initialValues={projectDetail?.feedback}
              onReload={() => {
                refetchProjectDetail();
              }}
            />
          )}
        </>
      );
    }, [
      meetingStatus,
      meetingsInCard,
      projectDetail?.feedback,
      projectDetail?.id,
      projectDetail?.status,
      refetchProjectDetail,
      userInfo?.userRole,
    ]);

    if (isLoadingDetail) {
      return <Loading />;
    }
    return (
      <>
        <ProjectDetail
          detail={projectDetail}
          onBackToList={() => navigate(ROUTES.PROJECT_MANAGEMENT.INDIVIDUAL)}
          rightSection={renderRightSection}
          leftSection={renderLeftSection}
          bottomSection={renderBottomSection}
          title={tCommon('project_request.service_application_form')}
          className={openScheduleCalendar ? 'overflow-hidden h-[100vh]' : undefined}
        />
        {!!projectId && (
          <ScheduleMeeting
            open={openScheduleCalendar}
            onSave={onSaveCalendar}
            onClose={() => {
              setOpenScheduleCalendar(false);
            }}
            titleProps={{
              // title: tCommon('calendar.request_to_change_time'),
              // subTitle: tCommon('calendar.please_select_meeting_time_with_expert'),
              title: tCommon('calendar.client_header.title'),
              subTitle: tCommon('calendar.client_header.sub_title'),
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
            isLoading={isLoadingDetail || isLoadingProposalMeetings}
            canCreateProposalMeetings={projectDetail?.canCreateProposalMeetings}
            //isEditable={foregroundMeetings?.length === 0}
            isEditable={projectDetail?.canCreateProposalMeetings}
          />
        )}

        {!!projectDetail?.id && (
          <CompletedPopUp
            open={projectDetail?.needCheck || false}
            onClose={() => {
              refetchProjectDetail();
            }}
            projectId={projectDetail?.id as number}
          />
        )}

        <Drawer
          open={openExpertProfile}
          onClose={() => setOpenExpertProfile(false)}
          title={tCommon('expert_matching.expert_profile')}
          childClassName='md:w-1/2 w-full max-w-[480px]'>
          <ExpertProfile
            projectId={+projectId!}
            expert={projectDetail?.expertDetail as ExpertRESP}
            onCancel={() => setOpenExpertProfile(false)}
            showBottomButton={false}
          />
        </Drawer>
      </>
    );
  },
  [USER_ROLE.MEMBER],
  [],
);

export default ProjectDetailIndivPage;
