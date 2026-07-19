import { COLORS } from '@/theme';
import { DeleteModalType } from '@/utils/types/utils.type';
import { ExclamationCircleFilled } from '@ant-design/icons';
import { Modal } from 'antd';
import { useEffect, useRef } from 'react';
import { useTranslation } from 'react-i18next';

type ModalConfirmProps = {
  message?: string;
  loadingMessage?: string;
  isOpen: boolean | DeleteModalType;
  setIsOpen: (data?: any) => void;
  onConfirm: (id?: number | number[]) => void;
  className?: string;
  classNameMessage?: string;
  titleCancel?: string;
  titleYes?: string;
  onCancel?: () => void;
  isLoading?: boolean;
};

const ModalConfirm = ({
  message,
  loadingMessage,
  classNameMessage,
  isOpen,
  setIsOpen,
  onConfirm,
  className,
  titleCancel,
  titleYes,
  onCancel,
  isLoading = false,
}: ModalConfirmProps) => {
  const { t: tCommon } = useTranslation('common');
  const idRef = useRef<number | number[] | undefined>();

  useEffect(() => {
    if (typeof isOpen === 'object' && isOpen.ids !== undefined) {
      idRef.current = isOpen.ids;
    }
  }, [isOpen]);

  const clickConfirm = () => {
    onConfirm(idRef.current);
  };

  return (
    <Modal
      open={typeof isOpen === 'object' ? isOpen.isOpen : isOpen}
      centered
      onCancel={() => onCancel ?? setIsOpen(false)}
      onOk={clickConfirm}
      closable={false}
      // width={368}
      zIndex={9999}
      className={className}
      cancelText={titleCancel ?? tCommon('button.cancel')}
      okText={titleYes ?? tCommon('button.check')}
      okButtonProps={{ loading: isLoading }}
      title={
        <div className='flex items-center gap-2'>
          <ExclamationCircleFilled style={{ color: COLORS.orange[900], fontSize: 30 }} />
          <p className={`text-lg whitespace-break-spaces font-medium ${classNameMessage}`}>
            {message ?? tCommon('message.delete')}
          </p>
        </div>
      }
    />
  );
};

export default ModalConfirm;
