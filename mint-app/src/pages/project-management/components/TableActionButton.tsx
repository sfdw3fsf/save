import Button from '@/components/core/button/Button';
import ClockCounterClockwiseIcon from '@/components/icons/ClockCounterClockwiseIcon';
import PencilSimpleLineIcon from '@/components/icons/PencilSimpleLineIcon';
import { useBreakpoint } from '@/hooks/useBreakpoint';
import Tippy from '@tippyjs/react';
import { useTranslation } from 'react-i18next';
type TableActionButtonProps = {
  onSchedule?: () => void;
  onEdit?: () => void;
  disabledEdit?: boolean;
  disabledSchedule?: boolean;
};
const TableActionButton = ({
  onSchedule,
  onEdit,
  disabledEdit = false,
  disabledSchedule = false,
}: TableActionButtonProps): JSX.Element => {
  const { hasMinWidthMd: isWeb } = useBreakpoint('md');
  const { t: tCommon } = useTranslation('common');
  return (
    <div className='flex flex-row w-full items-center gap-2 justify-between'>
      {!!onSchedule && (
        <Tippy
          content={tCommon('tooltip.schedule_change')}
          placement='bottom'
          theme='custom'
          arrow={false}
          className='bg-white border-[1px] border-slate-200 rounded-lg text-slate-800 shadow-lg'>
          <div className='w-full'>
            <Button
              leftIcon={<ClockCounterClockwiseIcon className='text-black' />}
              className='md:border-0 p-0 justify-center w-full disabled:bg-transparent disabled:cursor-not-allowed disabled:opacity-[0.25]' // Use full width for mobile, half width for md+
              keepLeftIconStyle={isWeb}
              onClick={(event) => {
                event.stopPropagation(); // prevent the row click
                onSchedule();
              }}
              disabled={disabledSchedule}>
              {isWeb ? '' : tCommon('project_management.schedule_change')}
            </Button>
          </div>
        </Tippy>
      )}
      {!!onEdit && (
        <Tippy
          content={tCommon('tooltip.modify_request')}
          placement='bottom'
          className='bg-white border-[1px] border-slate-200 rounded-lg text-slate-800 shadow-lg'
          arrow={false}>
          <div className='w-full'>
            <Button
              leftIcon={<PencilSimpleLineIcon className='text-black' />}
              className='md:border-0 p-0 w-full disabled:bg-transparent disabled:cursor-not-allowed disabled:opacity-[0.25]' // Use full width for mobile, half width for md+
              keepLeftIconStyle={isWeb}
              onClick={(event) => {
                event.stopPropagation();
                onEdit();
              }}
              disabled={disabledEdit}>
              {isWeb ? '' : tCommon('project_management.request_modification')}
            </Button>
          </div>
        </Tippy>
      )}
    </div>
  );
};

export default TableActionButton;
