import Button from '@/components/core/button/Button';
import Drawer from '@/components/core/drawer/Drawer';
import Loading from '@/components/core/loading/Loading';
import ScheduleCalendar, { TScheduleEvent } from '@/components/core/schedule-calendar/ScheduleCalendar';
import { SuccessToast } from '@/components/core/success-notification/SuccessNotification';
import ArrowLeftIcon from '@/components/icons/ArrowLeftIcon';
import {
  confirmProposalMeetingsExpertAPI,
  proposalMeetingsExpertAPI,
  proposalsExpertAPI,
} from '@/data/project/project-expert.api';
import { confirmProposalMeetingsIndivAPI, proposalMeetingsIndivAPI, proposalsIndivAPI } from '@/data/project/project-indiv.api';
import { ProposalMeetingsREQ, ProposalsREQ } from '@/data/project/request/project.request';
import { useAuthStore } from '@/store/auth.store';
import { DateRange } from '@/types/date.type';
import { USER_ROLE } from '@/utils/enums/user/user-role.enum';
import { onError } from '@/utils/helpers/data.helper';
import { useMutation } from '@tanstack/react-query';
import { useCallback, useEffect, useMemo, useState } from 'react';
import { useTranslation } from 'react-i18next';
import CalendarHeader from '../components/CalendarHeader';
import { BUTTON_CONFIRM_ENUM } from '../enums/meeting-status.enums';

type ScheduleMeetingProps = {
  open?: boolean;
  onClose?: () => void;
  titleProps?: {
    title?: string;
    subTitle?: string;
    info?: string;
  };
  defaultDate?: Date;
  projectId: number;
  canConfirmProposal?: boolean;
  canCreateProposalMeetings?: boolean;
  backgroundMeetings?: TScheduleEvent[];
  foregroundMeetings?: TScheduleEvent[];
  isLoading?: boolean;
  onSave?: () => void;

  // NEW REQUEST FROM CLIENT
  isEditable?: boolean;
};
const ScheduleMeeting = ({
  open = false,
  onClose,
  titleProps,
  defaultDate,
  projectId,
  canConfirmProposal = false,
  canCreateProposalMeetings = false,
  backgroundMeetings = [],
  foregroundMeetings = [],
  isLoading = false,
  onSave,
  isEditable = true,
}: ScheduleMeetingProps): JSX.Element => {
  const { t: tCommon } = useTranslation('common');
  const { userInfo } = useAuthStore();
  const userRole = userInfo?.userRole;

  // STATES
  const [btnConfirm, setBtnConfirm] = useState<BUTTON_CONFIRM_ENUM>(BUTTON_CONFIRM_ENUM.PROPOSE_MEETINGS);

  // APIS
  const { mutate: proposeNewMeetingsApi, isPending: isProposingNewMeetings } = useMutation({
    mutationFn: (request: ProposalsREQ) =>
      userRole === USER_ROLE.MEMBER ? proposalsIndivAPI(+projectId!, request) : proposalsExpertAPI(+projectId!, request),
    onSuccess: () => {
      onSuccess();
      setBtnConfirm(BUTTON_CONFIRM_ENUM.PROPOSE_MEETINGS);
    },
    onError,
  });

  const { mutate: proposeMeetingsApi, isPending: isProposingMeetings } = useMutation({
    mutationFn: (request: ProposalMeetingsREQ) =>
      userRole === USER_ROLE.MEMBER
        ? proposalMeetingsIndivAPI(+projectId!, request)
        : proposalMeetingsExpertAPI(+projectId!, request),
    onSuccess: () => onSuccess(),
    onError,
  });

  const { mutate: confirmProposalMeetingsApi, isPending: isConfirming } = useMutation({
    mutationFn: () =>
      userRole === USER_ROLE.MEMBER
        ? confirmProposalMeetingsIndivAPI(+projectId!)
        : confirmProposalMeetingsExpertAPI(+projectId!),
    onSuccess: () => onSuccess(),
    onError,
  });

  // EFFECTS
  useEffect(() => {
    if (canConfirmProposal) {
      setBtnConfirm(BUTTON_CONFIRM_ENUM.ACCEPT);
    }
  }, [canConfirmProposal]);

  // METHODS
  const onSuccess = () => {
    onSave?.();
    if (foregroundMeetings?.length === 0) {
      SuccessToast({
        // label: tCommon('calendar.schedule_change_client_success.title'),
        content: tCommon('calendar.schedule_change_success'),
      });
      return;
    }
    if (userInfo?.userRole === USER_ROLE.MEMBER) {
      SuccessToast({
        // label: tCommon('calendar.schedule_change_client_success.title'),
        content: tCommon('calendar.schedule_change_client_success.sub_title'),
      });
    } else {
      SuccessToast({
        //label: tCommon('calendar.schedule_change_expert_success.title'),
        content: tCommon('calendar.schedule_change_expert_success.sub_title'),
      });
    }
  };

  const handleClose = () => {
    onClose?.();
    if (canConfirmProposal) {
      setBtnConfirm(BUTTON_CONFIRM_ENUM.ACCEPT);
    } else {
      setBtnConfirm(BUTTON_CONFIRM_ENUM.PROPOSE_MEETINGS);
    }
  };

  const onSubmitCalendar = async (events: TScheduleEvent[]) => {
    if (events?.length === 0) return;

    if (btnConfirm === BUTTON_CONFIRM_ENUM.ACCEPT) {
      confirmProposalMeetingsApi();
      return;
    }

    if (btnConfirm === BUTTON_CONFIRM_ENUM.PROPOSE_NEW) {
      const request: DateRange[] = events?.map((event) => ({
        startTime: event?.start?.getTime() as number,
        endTime: event?.end?.getTime() as number,
      }));
      proposeNewMeetingsApi({ proposalTimeSlots: request });
    }
    if (btnConfirm === BUTTON_CONFIRM_ENUM.PROPOSE_MEETINGS) {
      const request: (DateRange & { proposalTimeSlotId: number })[] = events?.map((event) => ({
        startTime: event?.start?.getTime() as number,
        endTime: event?.end?.getTime() as number,
        proposalTimeSlotId: event?.parentId as number,
      }));
      proposeMeetingsApi({ proposalMeetings: request });
      return;
    }
  };

  const genarateCalendarSubmitText = useCallback(() => {
    // if (!isEditable) {
    //   return tCommon('calendar.cancel_not_editable');
    // }
    if (foregroundMeetings?.length === 0) {
      return tCommon('calendar.selection_completion');
    }
    if (foregroundMeetings?.length !== 0) {
      if (userInfo?.userRole === USER_ROLE.MEMBER) {
        return tCommon('calendar.send_to_expert');
      }
      if (userInfo?.userRole === USER_ROLE.EXPERT) {
        return tCommon('calendar.send_to_client');
      }
    }
    return '';
  }, [foregroundMeetings?.length, tCommon, userInfo?.userRole]);

  const enableConfirmBtn = useMemo(() => {
    if (!(btnConfirm === BUTTON_CONFIRM_ENUM.PROPOSE_MEETINGS && !canCreateProposalMeetings)) {
      return true;
    }
    return false;
  }, [btnConfirm, canCreateProposalMeetings]);

  if (!userRole) return <></>;
  if (isLoading) return <Loading />;
  return (
    <Drawer
      open={open}
      onClose={handleClose}
      isDivider={false}
      childClassName='lg:w-fit md:w-full w-full'
      topSection={
        <>
          {isEditable ? (
            <Button leftIcon={<ArrowLeftIcon />} onClick={handleClose}>
              {/* {userRole === USER_ROLE.EXPERT ? tCommon('calendar.back_button_expert') : tCommon('calendar.back_button_client')} */}
              {foregroundMeetings?.length === 0 ? tCommon('calendar.drag_grey') : tCommon('calendar.drag_green')}
            </Button>
          ) : null}
        </>
      }>
      <ScheduleCalendar
        // titleSection={
        //   btnConfirm === BUTTON_CONFIRM_ENUM.PROPOSE_MEETINGS && meetingStatus !== MEETING_STATUS_ENUM.FINAL ? (
        //     <CalendarHeader {...titleProps} />
        //   ) : null
        // }
        titleSection={
          <CalendarHeader
            title={isEditable ? titleProps?.title : tCommon('calendar.view_only_header') || ''}
            subTitle={canCreateProposalMeetings ? titleProps?.subTitle : ''}
            info={isEditable ? titleProps?.info : ''}
          />
        }
        defaultDate={defaultDate}
        onSubmit={onSubmitCalendar}
        //onReset={meetingStatus !== MEETING_STATUS_ENUM.FINAL ? () => setBtnConfirm(BUTTON_CONFIRM_ENUM.PROPOSE_NEW) : undefined}
        onReset={
          isEditable && foregroundMeetings?.length !== 0 && btnConfirm !== BUTTON_CONFIRM_ENUM.PROPOSE_NEW
            ? () => setBtnConfirm(BUTTON_CONFIRM_ENUM.PROPOSE_NEW)
            : undefined
        } // Just allow reset when there is green
        submitText={genarateCalendarSubmitText()}
        bgEvents={foregroundMeetings?.length === 0 ? backgroundMeetings : []}
        events={foregroundMeetings}
        isSplitTimeSlot={btnConfirm !== BUTTON_CONFIRM_ENUM.PROPOSE_NEW}
        loading={isProposingMeetings || isProposingNewMeetings || isConfirming}
        // allowEdit={
        //   !(
        //     meetingStatus === MEETING_STATUS_ENUM.FINAL ||
        //     btnConfirm === BUTTON_CONFIRM_ENUM.ACCEPT ||
        //     (btnConfirm === BUTTON_CONFIRM_ENUM.PROPOSE_MEETINGS && !canCreateProposalMeetings)
        //   )
        // }
        // enableConfirmBtn={
        //   !(
        //     meetingStatus === MEETING_STATUS_ENUM.FINAL ||
        //     (btnConfirm === BUTTON_CONFIRM_ENUM.PROPOSE_MEETINGS && !canCreateProposalMeetings)
        //   )
        // }
        allowEdit={
          isEditable &&
          !(
            btnConfirm === BUTTON_CONFIRM_ENUM.ACCEPT ||
            (btnConfirm === BUTTON_CONFIRM_ENUM.PROPOSE_MEETINGS && !canCreateProposalMeetings)
          )
        }
        enableConfirmBtn={enableConfirmBtn}
        onCancel={foregroundMeetings?.length !== 0 && isEditable ? handleClose : undefined} // Just allow cancel when there is green part
        variant={btnConfirm === BUTTON_CONFIRM_ENUM.PROPOSE_NEW ? 'mixed' : undefined}
        allowPast={btnConfirm !== BUTTON_CONFIRM_ENUM.PROPOSE_NEW}
        havePlaceholder={false}
        isShowGreyLegend={!(btnConfirm === BUTTON_CONFIRM_ENUM.PROPOSE_MEETINGS && foregroundMeetings?.length !== 0)} // TODO: Need to improve
        onCloseViewOnly={!isEditable ? () => handleClose() : undefined}
        isDivider={!isEditable}
        isShowGreenLegend={isEditable ? btnConfirm !== BUTTON_CONFIRM_ENUM.PROPOSE_NEW : foregroundMeetings?.length !== 0}
      />
    </Drawer>
  );
};

export default ScheduleMeeting;
