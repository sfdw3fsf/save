import {
  canConfirmProposalExpertAPI,
  getProjectDetailExpertAPI,
  getProposalTimeSlotsMeetingsExpertAPI,
} from '@/data/project/project-expert.api';
import {
  canConfirmProposalIndivAPI,
  getProjectDetailIndivAPI,
  getProposalTimeSlotsMeetingsIndivAPI,
} from '@/data/project/project-indiv.api';
import { QUERY_KEYS } from '@/utils/constants/data.constant';
import { USER_ROLE } from '@/utils/enums/user/user-role.enum';
import { useQuery } from '@tanstack/react-query';

type Props = {
  projectId?: number;
  //enabledFetch?: boolean;
  userRole?: USER_ROLE;
};
export const useFetchMeetings = ({ projectId, userRole }: Props) => {
  const isValidRole = userRole === USER_ROLE.MEMBER || userRole === USER_ROLE.EXPERT;
  const isValidProjectId = !!projectId && projectId > 0; // Sometimes project id is cast to 0 => so need to check projectId > 0

  const {
    data: projectDetail,
    isFetching: isFetchingDetail,
    refetch: refetchProjectDetail,
    isLoading: isLoadingDetail,
  } = useQuery({
    queryKey: [QUERY_KEYS.PROJECTS.INDIVIDUAL.LIST, projectId],
    queryFn: () =>
      userRole === USER_ROLE.MEMBER
        ? getProjectDetailIndivAPI({ projectId: +projectId! })
        : getProjectDetailExpertAPI({ projectId: +projectId! }),
    enabled: isValidProjectId && isValidRole,
    select: ({ data }) => data,
  });

  const {
    data: proposalMeetings,
    isLoading: isLoadingProposalMeetings,
    refetch: refetchProposalMeetings,
  } = useQuery({
    queryKey: [QUERY_KEYS.PROJECTS.INDIVIDUAL.PROPOSAL_MEETINGS, projectId],
    queryFn: () =>
      userRole === USER_ROLE.MEMBER
        ? getProposalTimeSlotsMeetingsIndivAPI(+projectId!)
        : getProposalTimeSlotsMeetingsExpertAPI(+projectId!),
    enabled: isValidProjectId && isValidRole,
    select: ({ data }) => data,
  });

  const { data: canConfirmProposal, refetch: refetchCanConfirmProposal } = useQuery({
    queryKey: [QUERY_KEYS.PROJECTS.INDIVIDUAL.CAN_CONFIRM_MEETING, projectId],
    queryFn: () =>
      userRole === USER_ROLE.MEMBER ? canConfirmProposalIndivAPI(+projectId!) : canConfirmProposalExpertAPI(+projectId!),
    enabled: isValidProjectId && isValidRole,
    select: ({ data }) => data?.canConfirmProposalMeeting,
  });

  return {
    projectDetail,
    isFetchingDetail,
    isLoadingDetail,
    refetchProjectDetail,
    proposalMeetings,
    isLoadingProposalMeetings,
    refetchProposalMeetings,
    canConfirmProposal,
    refetchCanConfirmProposal,
  };
};
