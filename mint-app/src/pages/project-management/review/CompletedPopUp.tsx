import Modal from '@/components/core/modal/Modal';
import { checkProjectCompletedAPI } from '@/data/project/project-indiv.api';
import { onError } from '@/utils/helpers/data.helper';
import { IMAGES } from '@/utils/theme';
import { useMutation } from '@tanstack/react-query';
import { useTranslation } from 'react-i18next';

type CompletedPopUpProps = {
  onClose?: () => void;
  open?: boolean;
  projectId: number;
};

const CompletedPopUp = ({ open = false, onClose, projectId }: CompletedPopUpProps): JSX.Element => {
  const { t: tCommon } = useTranslation('common');

  // APIS
  const { mutate: checkProjectCompletedAPi, isPending: isChecking } = useMutation({
    mutationFn: () => checkProjectCompletedAPI(projectId),
    onSuccess: () => {
      onClose?.();
    },
    onError,
  });

  const onCheck = async () => checkProjectCompletedAPi();
  return (
    <Modal
      open={open}
      onClose={onCheck}
      header={<div className='md:text-display-xs text-lg text-slate-800 text-center'>{tCommon('review.title')}</div>}
      isDivider={false}
      buttons={[
        {
          children: tCommon('button.check'),
          onClick: onCheck,
          className: 'w-full mt-2',
          variant: 'primary',
          isLoading: isChecking,
        },
      ]}
      body={
        <div className='flex flex-col w-full items-center justify-center'>
          <img src={IMAGES.ILLUSTRATION.CHECK} className='size-[120px] shrink-0 my-10' />
          <div className='whitespace-pre-line text-center text-slate-500 md:text-md text-sm'>{tCommon('review.description')}</div>
        </div>
      }
    />
  );
};

export default CompletedPopUp;
