import Button from '@/components/core/button/Button';
import ArrowRightIcon from '@/components/icons/ArrowRightIcon';
import { useBreakpoint } from '@/hooks/useBreakpoint';
import { useMemo } from 'react';
import { useTranslation } from 'react-i18next';

type ControlButtonProps = {
  onPrevious?: () => void;
  form?: string;
  disablePreviousBtn?: boolean;
};
const ControlButton = ({ onPrevious, form, disablePreviousBtn }: ControlButtonProps) => {
  const { t: tCommon } = useTranslation('common');
  const { hasMinWidthMd: isWeb } = useBreakpoint('md');
  const sizing = useMemo(() => (isWeb ? 'md' : 'sm'), [isWeb]);

  return (
    <div className='flex md:w-fit w-full justify-end items-center self-end gap-3'>
      <Button sizing={sizing} className='w-full md:w-[106px]' onClick={onPrevious} disabled={disablePreviousBtn}>
        {tCommon('project_request.previous_button')}
      </Button>
      <Button sizing={sizing} form={form} className='w-full md:w-[200px]' variant='primary' rightIcon={<ArrowRightIcon />}>
        {tCommon('project_request.next_button')}
      </Button>
    </div>
  );
};

export default ControlButton;
