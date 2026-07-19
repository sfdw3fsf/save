import { Divider, Modal } from 'antd';
import { ReactNode, useMemo } from 'react';
import './custom-menu-modal.scss';

type MenuModalProps = {
  isOpen: boolean;
  onClose: () => void;
  slideIn: 'left' | 'right';
  closable?: boolean;
  title?: React.ReactNode;
  children?: React.ReactNode;
  footer: ReactNode;
  isHideFooter?: boolean;
};

const MenuModal = ({ isOpen, onClose, slideIn, title, children, closable = true, isHideFooter, footer }: MenuModalProps) => {
  const renderFooter: React.ReactNode = useMemo(() => {
    if (isHideFooter) return undefined;
    return footer;
  }, [isHideFooter]);

  const isSafari = /^((?!chrome|android).)*safari/i.test(navigator.userAgent);

  return (
    <Modal
      title={
        <div className='pt-6 px-5'>
          <div style={{ fontWeight: 'bold', fontSize: '18px' }}>{title}</div>
          <Divider style={{ margin: '8px 0' }} />
        </div>
      }
      open={isOpen}
      footer={renderFooter}
      closable={closable}
      onCancel={onClose}
      className={`slide-in-${slideIn} menu-modal ${isSafari ? 'menu-modal-safari' : ''}`}
      transitionName=''
      maskTransitionName=''>
      <div>{children}</div>
    </Modal>
  );
};

export default MenuModal;
