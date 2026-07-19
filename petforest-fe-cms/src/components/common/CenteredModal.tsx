import { Modal, ModalProps } from 'antd';
import { ReactNode } from 'react';
import CustomText from './Text';

type CenteredModalProps = {
  isOpen: boolean;
  title?: string;
  children: ReactNode;
  height?: number;
  isFooter?: boolean;
  className?: string;
} & ModalProps;

const CenteredModal = ({ isOpen, title, children, className, isFooter = true, ...rest }: CenteredModalProps) => {
  return (
    <Modal
      styles={{ body: { maxHeight: '80vh', overflow: 'auto' } }}
      title={<CustomText variant='headline-1'>{title}</CustomText>}
      open={isOpen}
      className={`slide-in-right center-modal ${className}`}
      transitionName=''
      maskTransitionName=''
      footer={isFooter ? undefined : null}
      {...rest}>
      {children}
    </Modal>
  );
};

export default CenteredModal;
