import { IMAGES } from '@/utils/theme';
import { useMemo } from 'react';
import { useTranslation } from 'react-i18next';
import Modal from '../modal/Modal';

export type ContentProps = {
  title?: string;
  content?: string;
};

type SuccessFailPopUpProps = {
  open?: boolean;
  title?: string;
  content?: string;
  onCheck?: () => void;
  onClose?: () => void;
  loading?: boolean;
  children?: React.ReactNode;
  variant?: 'success' | 'fail' | null;
};
const SuccessFailPopUp = ({
  open = false,
  title,
  content,
  onCheck,
  onClose,
  loading = false,
  children,
  variant,
}: SuccessFailPopUpProps): JSX.Element => {
  const { t: tCommon } = useTranslation('common');
  const renderChildren = useMemo(() => {
    if (children) return children;
    if (variant) {
      if (variant === 'success') return <img src={IMAGES.ILLUSTRATION.CHECK} className='size-[120px] shrink-0 my-10' />;
      if (variant === 'fail') return <img src={IMAGES.ILLUSTRATION.FAIL} className='size-[120px] shrink-0 my-10' />;
    }
    return null;
  }, [children, variant]);
  return (
    <Modal
      open={open}
      onClose={() => onClose?.()}
      header={<div className='md:text-display-xs text-lg text-slate-800 text-center'>{title}</div>}
      isDivider={false}
      buttons={[
        {
          children: tCommon('button.check'),
          onClick: () => onCheck?.(),
          className: 'w-full mt-2',
          variant: 'primary',
          isLoading: loading,
        },
      ]}
      body={
        <div className='flex flex-col w-full items-center justify-center'>
          {renderChildren}
          <div className='whitespace-pre-line text-center text-slate-500 md:text-md text-sm'>{content}</div>
        </div>
      }
    />
  );
};

export default SuccessFailPopUp;
