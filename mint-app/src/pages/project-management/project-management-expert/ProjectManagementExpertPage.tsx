import Drawer from '@/components/core/drawer/Drawer';
import { EmptyData } from '@/components/core/faq/EmptyData';
import PageContainer from '@/components/core/page-container/PageContainer';
import PermissionAccept from '@/components/hocs/PermissionAccept';
import {
  acceptInitialMeetingsExpertAPI,
  getProjectsExpertAPI,
  getProjectsExpertStaticsByStatusAPI,
} from '@/data/project/project-expert.api';
import { transformProjectStaticsResponse } from '@/data/project/project.service';
import { ProjectsExpertREQ } from '@/data/project/request/project.request';
import { ProjectsExpertRESP, ProjectStatisticsRESP } from '@/data/project/response/project.response';
import { useFilter } from '@/hooks/useFilter';
import { ROUTES } from '@/router/routes.constant';
import { useAuthStore } from '@/store/auth.store';
import { BaseResponse } from '@/types/response.type';
import { QUERY_KEYS } from '@/utils/constants/data.constant';
import { PROJECT_STATICS_ENUM, PROJECT_STATUS } from '@/utils/enums/project/project-list.enum';
import { USER_ROLE } from '@/utils/enums/user/user-role.enum';
import { USER_STATUS } from '@/utils/enums/user/user-status.enum';
import { onError } from '@/utils/helpers/data.helper';
import { toLocaleDateString } from '@/utils/helpers/datetime.helper';
import { FilterProjectInput } from '@/utils/schemas/project-request.schema';
import { useMutation, useQuery, useQueryClient } from '@tanstack/react-query';
import { useDebounce } from '@uidotdev/usehooks';
import { useEffect, useMemo, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { useLocation, useNavigate } from 'react-router-dom';
import { twMerge } from 'tailwind-merge';
import FilterSection from '../components/FilterSection';
import ProjectStatusBadge from '../components/ProjectStatusBadge';
import ProjectTable, { ProjectTableColumn } from '../components/ProjectTable';
import SearchAndFilter from '../components/SearchAndFilter';
import TabBar from '../components/TabBar';
import TableActionButton from '../components/TableActionButton';
import Title from '../components/Title';
import { extractMeetings } from '../helpers/meetings.helpers';
import { useFetchMeetings } from '../hooks/useFetchMeetings';
import ScheduleMeeting from '../schedule-meeting/ScheduleMeeting';
import AcceptMatchResultModal from './AcceptMatchResultModal';

const ProjectManagementExpertPage = PermissionAccept(
  (): JSX.Element => {
    const { t: tCommon } = useTranslation('common');
    const { userInfo } = useAuthStore();
    const navigate = useNavigate();
    const location = useLocation();
    const queryClient = useQueryClient();

    // STATES
    const [searchValue, setSearchValue] = useState('');
    const [filterValue, setFilterValue] = useState<FilterProjectInput | null>(null);
    const [selectedTab, setSelectedTab] = useState<PROJECT_STATICS_ENUM>(PROJECT_STATICS_ENUM.ENTIRE);
    const [selectedId, setSelectedId] = useState<number | null>(null);
    const [openFilter, setOpenFilter] = useState(false);
    const [openCalendar, setOpenCalendar] = useState(false);
    const debouncedSearch = useDebounce(searchValue, 300);

    // HOOKS
    const { queries, hasNone, onSearch, onReset, getPaginationConfigs } = useFilter<ProjectsExpertREQ & any>();
    const openAcceptMatchModal = useMemo(() => location?.pathname?.includes('accept-match') || false, [location?.pathname]);

    // APIS
    const { data: projects, isFetching: isFetchingProjects } = useQuery({
      queryKey: [QUERY_KEYS.PROJECTS.EXPERT.LIST, queries],
      queryFn: () => getProjectsExpertAPI(queries),
      enabled: !hasNone,
    });

    const { data: statistics } = useQuery({
      queryKey: [QUERY_KEYS.PROJECTS.EXPERT.STATISTICS],
      queryFn: getProjectsExpertStaticsByStatusAPI,
      select: ({ data }: BaseResponse<ProjectStatisticsRESP>) => transformProjectStaticsResponse(data),
    });

    const {
      projectDetail,
      isLoadingDetail,
      refetchProjectDetail,
      proposalMeetings,
      isLoadingProposalMeetings,
      refetchProposalMeetings,
      canConfirmProposal,
      refetchCanConfirmProposal,
    } = useFetchMeetings({ projectId: selectedId as number, userRole: USER_ROLE.EXPERT });

    const meetingInfo = useMemo(() => {
      if (!selectedId) return null;
      return extractMeetings({
        projectDetail,
        proposalMeetings,
        userInfo,
      });
    }, [projectDetail, proposalMeetings, selectedId, userInfo]);

    const { mutate: acceptInitialMeetingsApi, isPending: isAccepingInitial } = useMutation({
      mutationFn: () => acceptInitialMeetingsExpertAPI(selectedId as number),
      onSuccess: async () => {
        onRefetch().finally(() => {
          setOpenCalendar(true);
        });
      },
      onError,
    });

    const isValidUserStatus = useMemo(
      () => [USER_STATUS.ACTIVE].includes(userInfo?.userStatus as USER_STATUS),
      [userInfo?.userStatus],
    );

    // EFFECTS
    useEffect(() => {
      if (!location?.pathname?.includes('accept-match')) {
        if (!filterValue && !openFilter) {
          onReset();
        }
      }
      // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [filterValue, openFilter, location?.pathname]);

    useEffect(() => {
      onSearch({ name: debouncedSearch });
      // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [debouncedSearch]);

    // METHODS
    const onRefetch = async () => {
      await Promise.all([refetchProposalMeetings(), refetchProjectDetail(), refetchCanConfirmProposal()]);
    };
    const onSaveCalendar = async () => {
      await queryClient.invalidateQueries({ queryKey: [QUERY_KEYS.PROJECTS.EXPERT.LIST] });
      await onRefetch().finally(() => {
        setSelectedId(null);
        setOpenCalendar(false);
      });
    };

    // CONSTANTS
    const displayedStatus = useMemo(
      () => [
        PROJECT_STATICS_ENUM.ENTIRE,
        PROJECT_STATICS_ENUM.MATCHING_COMPLETED,
        PROJECT_STATICS_ENUM.PROJECT_IN_PROGRESS,
        PROJECT_STATICS_ENUM.PROJECT_COMPLETED,
      ],
      [],
    );
    const columns = useMemo<ProjectTableColumn<keyof ProjectsExpertRESP, ProjectsExpertRESP>[]>(
      () => [
        {
          header: tCommon('project_management.project_name'),
          value: 'name',
          render: (val) => <div className='min-w-[200px]'>{val}</div>,
        },
        {
          header: tCommon('project_management.project_status'),
          value: 'status',
          render: (val) => (
            <div className='flex md:justify-center justify-start'>
              <ProjectStatusBadge projectStatus={val as PROJECT_STATUS} />
            </div>
          ),
          renderHeader: (header) => <div className='md:text-center'>{header}</div>,
        },
        {
          header: tCommon('project_management.request_date'),
          value: 'createdAt',
          render: (val) => <div>{val ? toLocaleDateString(new Date(val as unknown as Date)) : '_'}</div>,
        },
        {
          header: tCommon('project_management.meeting_date'),
          value: 'nearestMeetingAt',
          render: (val) => <div>{val ? toLocaleDateString(new Date(val as unknown as Date)) : '_'}</div>,
        },
        {
          header: '',
          render: (_, record) => (
            <TableActionButton
              onSchedule={() => {
                if (!record?.haveProposal) {
                  acceptInitialMeetingsApi();
                } else {
                  setOpenCalendar(true);
                }
                setSelectedId(record?.id as number);
              }}
              disabledSchedule={
                !(
                  [
                    PROJECT_STATUS.MATCHING_COMPLETED,
                    PROJECT_STATUS.UNDER_CONTRACT,
                    PROJECT_STATUS.PROJECT_IN_PROGRESS,
                  ]?.includes(record?.status as PROJECT_STATUS) && record?.canCreateProposal
                )
              }
            />
          ),
        },
      ],
      [acceptInitialMeetingsApi, tCommon],
    );

    return (
      <>
        <Drawer
          open={openFilter}
          onClose={() => setOpenFilter(false)}
          title={tCommon('project_management.filter_modal')}
          childClassName='md:w-1/2 w-full max-w-[480px]'>
          <FilterSection
            initialValues={filterValue}
            onCancel={() => setOpenFilter(false)}
            onSave={(data) => {
              const filteredStatus = data?.status?.filter(Boolean);
              if (data?.status?.length !== 0) {
                setSelectedTab(PROJECT_STATICS_ENUM.ENTIRE);
              }
              setOpenFilter(false);
              setFilterValue(data);
              onSearch({
                startDate: data?.advisoryDate[0]?.getTime() || undefined,
                endDate: data?.advisoryDate[1]?.getTime() || undefined,
                projectStatuses: (filteredStatus as PROJECT_STATUS[]) || [],
              });
            }}
            onClear={() => {
              setFilterValue(null);
              setSelectedTab(PROJECT_STATICS_ENUM.ENTIRE);
            }}
          />
        </Drawer>
        {!!selectedId && !!meetingInfo && !!projectDetail?.haveProposal && openCalendar && (
          <ScheduleMeeting
            open={!!selectedId && !!meetingInfo && !!projectDetail?.haveProposal && !!openCalendar}
            onClose={() => {
              setSelectedId(null);
              setOpenCalendar(false);
            }}
            onSave={onSaveCalendar}
            titleProps={{
              title: tCommon('calendar.set_meeting_time'),
              subTitle: tCommon('calendar.please_select_meeting_time'),
              info:
                meetingInfo?.foregroundMeetings?.length === 0
                  ? tCommon('calendar.please_select_from_1_to_2_green')
                  : tCommon('calendar.please_select_from_1_to_2_client'),
            }}
            backgroundMeetings={meetingInfo?.backgroundMeetings}
            foregroundMeetings={meetingInfo?.foregroundMeetings}
            projectId={selectedId}
            canConfirmProposal={canConfirmProposal}
            defaultDate={meetingInfo?.meetings[0]?.start as Date}
            isLoading={isLoadingDetail || isLoadingProposalMeetings || isAccepingInitial}
            canCreateProposalMeetings={projectDetail?.canCreateProposalMeetings}
          />
        )}
        <PageContainer
          title={tCommon('project_management.title')}
          isFullScreen
          hiddenScroll
          className='items-start lg:px-28 px-4 py-14'
          containerStyle={twMerge(openFilter ? 'overflow-hidden h-[100vh]' : null)}>
          <div className='bg-white flex flex-col w-full p-8 rounded-xl border-[1px] border-slate-200'>
            <Title subTitle={tCommon('project_management.sub_title_expert')} />
            {isValidUserStatus ? (
              <>
                <SearchAndFilter
                  searchValue={searchValue}
                  onSearch={(value) => {
                    setSearchValue(value);
                    //onSearch({ name: value });
                  }}
                  onFilter={() => setOpenFilter(true)}
                />
                {statistics && (
                  <TabBar
                    tabs={statistics
                      ?.filter((item) => displayedStatus?.includes(item.status))
                      ?.map(({ status, value }) => ({
                        label:
                          status === PROJECT_STATICS_ENUM.PROJECT_COMPLETED
                            ? tCommon('project_management.project_completed_status_expert')
                            : tCommon(`project_management.${status}`, status),
                        value: status,
                        badge: value,
                      }))}
                    showBadge
                    selectedTab={selectedTab}
                    onChangeTab={(value: PROJECT_STATICS_ENUM) => {
                      onSearch({
                        projectStatuses:
                          value === PROJECT_STATICS_ENUM.ENTIRE ? undefined : ([value] as unknown as PROJECT_STATUS[]),
                      });
                      setSelectedTab(value);
                    }}
                  />
                )}
                <ProjectTable
                  data={projects?.data || []}
                  columns={columns}
                  isLoading={isFetchingProjects && !selectedId}
                  onRowClick={(row) => navigate(`${ROUTES.PROJECT_MANAGEMENT.EXPERT}/${row.id}`)}
                  paginationConfigs={getPaginationConfigs(projects?.pagination?.totalPages || 0)}
                />
              </>
            ) : (
              <EmptyData label={tCommon('project_management.empty')} />
            )}
          </div>
        </PageContainer>
        {openAcceptMatchModal && <AcceptMatchResultModal />}
      </>
    );
  },
  [USER_ROLE.EXPERT],
  [],
);

export default ProjectManagementExpertPage;
