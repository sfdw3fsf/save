'use client';
import CustomButton from '@/components/common/CustomButton';
import { Divider, Modal } from 'antd';
import { ReactNode, useEffect, useMemo, useState } from 'react';
import { useTranslation } from 'react-i18next';
import './reservation-modal.scss';

type CustomModalProps = {
  isOpen: boolean;
  onClose?: () => void;
  title?: ReactNode;
  children?: ReactNode;
  isHideFooter?: boolean;
  extraFooter?: ReactNode;
  onOk?: () => void;
  onCancel?: () => void;
  isLoading?: boolean;
};

const ReservationModal = ({
  isOpen,
  onClose,
  title,
  children,
  isHideFooter,
  extraFooter,
  isLoading,
  onOk,
  onCancel,
}: CustomModalProps) => {
  const { t: tCommon } = useTranslation('common');
  const [isMounted, setIsMounted] = useState(false);
  const renderFooter: ReactNode = useMemo(() => {
    if (isHideFooter) return undefined;
    return (
      <div className='flex gap-3 justify-end px-5'>
        {extraFooter}
        <CustomButton className='w-[109px]' onClick={onCancel}>
          {tCommon('modal.close')}
        </CustomButton>
        {onOk && (
          <CustomButton type='primary' loading={isLoading} className='w-[109px]' onClick={onOk}>
            {tCommon('modal.save')}
          </CustomButton>
        )}
      </div>
    );
  }, [isHideFooter, extraFooter, onOk, isLoading, onCancel]);

  useEffect(() => {
    setIsMounted(true);
  }, []);

  if (!isMounted) {
    return null;
  }

  return (
    <Modal
      title={
        <div className='pt-6'>
          <div style={{ fontWeight: 'bold', fontSize: '18px' }}>{title}</div>
          <Divider style={{ margin: '8px 0' }} />
        </div>
      }
      open={isOpen}
      footer={renderFooter}
      height={'100vh'}
      onCancel={onClose}
      maskClosable={false}
      className='slide-in-right reservation'
      transitionName=''
      // destroyOnClose={true}
      maskTransitionName=''>
      <div>{children}</div>
    </Modal>
  );
};

export default ReservationModal;
