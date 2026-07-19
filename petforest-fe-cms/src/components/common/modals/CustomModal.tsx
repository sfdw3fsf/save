'use client';
import { Divider, Modal } from 'antd';
import { ReactNode, useEffect, useMemo, useState } from 'react';
import { useTranslation } from 'react-i18next';
import CustomButton from '../CustomButton';

type CustomModalProps = {
  isOpen: boolean;
  onClose: () => void;
  title?: ReactNode;
  children?: ReactNode;
  isHideFooter?: boolean;
  extraFooter?: ReactNode;
  onOk?: () => void;
  onCancel?: () => void;
  isLoading?: boolean;
  className?: string;
};

const CustomModal = ({
  isOpen,
  onClose,
  title,
  children,
  isHideFooter,
  extraFooter,
  onOk,
  onCancel,
  isLoading,
  className,
}: CustomModalProps) => {
  const { t: tCommon } = useTranslation('common');
  const [isMounted, setIsMounted] = useState(false);
  const renderFooter: ReactNode = useMemo(() => {
    if (isHideFooter) return undefined;
    return (
      <div className='flex gap-3 justify-end px-5'>
        {extraFooter}
        {onCancel && (
          <CustomButton className='w-[109px]' onClick={onCancel}>
            {tCommon('modal.cancel')}
          </CustomButton>
        )}
        {onOk && (
          <CustomButton type='primary' className='w-[109px]' onClick={onOk}>
            {tCommon('modal.save')}
          </CustomButton>
        )}
      </div>
    );
  }, [isHideFooter, extraFooter, onOk, onCancel]);

  useEffect(() => {
    setIsMounted(true);
  }, []);

  if (!isMounted) {
    return null;
  }

  return (
    <Modal
      title={
        <div>
          <div style={{ fontWeight: 'bold', fontSize: '18px' }}>{title}</div>
          <Divider style={{ margin: '8px 0' }} />
        </div>
      }
      destroyOnClose={true}
      loading={isLoading}
      open={isOpen}
      footer={isHideFooter ? null : renderFooter}
      height={'100vh'}
      onCancel={onClose}
      className={` slide-in-right ${className ?? 'custom-ant-modal'}`}
      transitionName=''
      maskTransitionName=''>
      <div>{children}</div>
    </Modal>
  );
};

export default CustomModal;
