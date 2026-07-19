import Button from '@/components/core/button/Button';
import PageContainer from '@/components/core/page-container/PageContainer';
import { useEffect, useMemo, useState } from 'react';
import { useTranslation } from 'react-i18next';
import SearchAndFilter from '../components/SearchAndFilter';

import Drawer from '@/components/core/drawer/Drawer';
import { EmptyData } from '@/components/core/faq/EmptyData';
import PermissionAccept from '@/components/hocs/PermissionAccept';
import { getProjectsIndivAPI, getProjectsIndivStaticsByStatusAPI } from '@/data/project/project-indiv.api';
import { transformProjectStaticsResponse } from '@/data/project/project.service';
import { ProjectsIndivREQ } from '@/data/project/request/project.request';
import { ProjectsIndivRESP, ProjectStatisticsRESP } from '@/data/project/response/project.response';
import { useFilter } from '@/hooks/useFilter';
import { ROUTES } from '@/router/routes.constant';
import { useAuthStore } from '@/store/auth.store';
import { BaseResponse } from '@/types/response.type';
import { QUERY_KEYS } from '@/utils/constants/data.constant';
import { PROJECT_STATICS_ENUM, PROJECT_STATUS } from '@/utils/enums/project/project-list.enum';
import { USER_ROLE } from '@/utils/enums/user/user-role.enum';
import { USER_STATUS } from '@/utils/enums/user/user-status.enum';
import { toLocaleDateString } from '@/utils/helpers/datetime.helper';
import { FilterProjectInput } from '@/utils/schemas/project-request.schema';
import { useQuery, useQueryClient } from '@tanstack/react-query';
import { useDebounce } from '@uidotdev/usehooks';
import { useNavigate } from 'react-router-dom';
import { twMerge } from 'tailwind-merge';
import FilterSection from '../components/FilterSection';
import ProjectStatusBadge from '../components/ProjectStatusBadge';
import ProjectTable, { ProjectTableColumn } from '../components/ProjectTable';
import TabBar from '../components/TabBar';
import TableActionButton from '../components/TableActionButton';
import Title from '../components/Title';
import { extractMeetings } from '../helpers/meetings.helpers';
import { useFetchMeetings } from '../hooks/useFetchMeetings';
import ScheduleMeeting from '../schedule-meeting/ScheduleMeeting';

const ProjectManagementIndivPage = PermissionAccept(
  (): JSX.Element => {
    const { t: tCommon } = useTranslation('common');
    const { userInfo } = useAuthStore();
    const queryClient = useQueryClient();

    // STATES
    const [searchValue, setSearchValue] = useState('');
    const [filterValue, setFilterValue] = useState<FilterProjectInput | null>(null);
    const [selectedTab, setSelectedTab] = useState<PROJECT_STATICS_ENUM>(PROJECT_STATICS_ENUM.ENTIRE);
    const [openFilter, setOpenFilter] = useState(false);
    const [selectedId, setSelectedId] = useState<number | null>(null);
    const debouncedSearch = useDebounce(searchValue, 300);

    // HOOKS
    const navigate = useNavigate();
    const { queries, hasNone, onSearch, onReset, getPaginationConfigs } = useFilter<ProjectsIndivREQ>();

    // APIS
    const { data: projects, isFetching: isFetchingProjects } = useQuery({
      queryKey: [QUERY_KEYS.PROJECTS.INDIVIDUAL.LIST, queries],
      queryFn: () => getProjectsIndivAPI(queries),
      enabled: !hasNone,
    });

    const { data: statistics } = useQuery({
      queryKey: [QUERY_KEYS.PROJECTS.INDIVIDUAL.STATISTICS],
      queryFn: getProjectsIndivStaticsByStatusAPI,
      select: ({ data }: BaseResponse<ProjectStatisticsRESP>) => transformProjectStaticsResponse(data),
    });

    const isValidUserStatus = useMemo(
      () => [USER_STATUS.ACTIVE].includes(userInfo?.userStatus as USER_STATUS),
      [userInfo?.userStatus],
    );

    const {
      projectDetail,
      isLoadingDetail,
      refetchProjectDetail,
      proposalMeetings,
      isLoadingProposalMeetings,
      refetchProposalMeetings,
      canConfirmProposal,
      refetchCanConfirmProposal,
    } = useFetchMeetings({ projectId: selectedId as number, userRole: USER_ROLE.MEMBER });

    const meetingInfo = useMemo(() => {
      if (!selectedId) return null;
      return extractMeetings({
        projectDetail,
        proposalMeetings,
        userInfo,
      });
    }, [projectDetail, proposalMeetings, selectedId, userInfo]);

    // EFFECTS
    useEffect(() => {
      if (!filterValue && !openFilter) {
        onReset();
      }
      // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [filterValue, openFilter]);

    useEffect(() => {
      onSearch({ name: debouncedSearch });
      // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [debouncedSearch]);

    // METHODS
    const onSaveCalendar = async () => {
      await queryClient.invalidateQueries({ queryKey: [QUERY_KEYS.PROJECTS.INDIVIDUAL.LIST] });
      Promise.all([refetchProposalMeetings(), refetchProjectDetail(), refetchCanConfirmProposal()]).finally(() => {
        setSelectedId(null);
      });
    };

    const columns = useMemo<ProjectTableColumn<keyof ProjectsIndivRESP, ProjectsIndivRESP>[]>(
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
                setSelectedId(record?.id as number);
              }}
              onEdit={() => navigate(`${ROUTES.PROJECT_MANAGEMENT.INDIVIDUAL}/edit/${record?.id}`)}
              disabledEdit={
                record?.status !== PROJECT_STATUS.REQUEST_COMPLETED && record?.status !== PROJECT_STATUS.UNDER_CONTRACT
              }
              disabledSchedule={
                !record?.haveProposal ||
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
      [navigate, tCommon],
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
        {!!selectedId && !!meetingInfo && (
          <ScheduleMeeting
            open={!!selectedId && !!meetingInfo}
            onClose={() => setSelectedId(null)}
            onSave={onSaveCalendar}
            titleProps={{
              title: tCommon('calendar.request_to_change_time'),
              subTitle: tCommon('calendar.please_select_meeting_time_with_expert'),
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
            isLoading={isLoadingDetail || isLoadingProposalMeetings}
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
            <Title subTitle={tCommon('project_management.sub_title_indiv')} />
            {isValidUserStatus ? (
              <>
                <SearchAndFilter
                  searchValue={searchValue}
                  onSearch={(value) => {
                    //onSearch({ name: value });
                    setSearchValue(value);
                  }}
                  onFilter={() => {
                    setOpenFilter(true);
                  }}
                  leftButton={
                    <Button
                      className='py-[10px] px-[42.5px] md:w-fit w-full'
                      variant='primary'
                      onClick={() => {
                        navigate(ROUTES.PROJECT_REQUEST.INDIVIDUAL.REQUEST);
                      }}>
                      {tCommon('project_management.request')}
                    </Button>
                  }
                />
                {statistics && (
                  <TabBar
                    tabs={statistics?.map(({ status, value }) => ({
                      label: tCommon(`project_management.${status}`, status),
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
                  onRowClick={(row) => navigate(`${ROUTES.PROJECT_MANAGEMENT.INDIVIDUAL}/${row.id}`)}
                  paginationConfigs={getPaginationConfigs(projects?.pagination?.totalPages || 0)}
                />
              </>
            ) : (
              <EmptyData label={tCommon('project_management.empty')} />
            )}
          </div>
        </PageContainer>
      </>
    );
  },
  [USER_ROLE.MEMBER],
  [],
);

export default ProjectManagementIndivPage;
