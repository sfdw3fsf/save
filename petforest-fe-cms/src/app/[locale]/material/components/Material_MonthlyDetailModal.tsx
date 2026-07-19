import CustomText from '@/components/common/Text';
import { Modal } from 'antd';
import { ReactNode } from 'react';

type MaterialMonthlyDetailModalProps = {
  children: ReactNode;
  isOpen: boolean;
  title: string;
  onClose: () => void;
};
const MaterialMonthlyDetailModal = ({ children, title, isOpen, onClose }: MaterialMonthlyDetailModalProps) => {
  return (
    <Modal
      styles={{ body: { maxHeight: '80vh', overflow: 'auto' } }}
      title={<CustomText variant='headline-1'>{title}</CustomText>}
      open={isOpen}
      footer={null}
      onCancel={onClose}
      className='slide-in-right monthly-modal'
      transitionName=''
      maskTransitionName=''>
      {children}
    </Modal>
  );
};

export default MaterialMonthlyDetailModal;
