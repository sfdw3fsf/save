import { Modal, ModalProps } from 'antd';
import CustomText from './Text';

type ViewPhotoModalProps = {
  isOpen: boolean;
  title?: string;
  isFooter?: boolean;
  photo: string | '';
} & ModalProps;

export const ViewPhotoModal = ({ isOpen, title, photo, isFooter = true, ...rest }: ViewPhotoModalProps) => {
  return (
    <Modal
      styles={{ body: { maxHeight: '80vh', overflow: 'auto' } }}
      title={<CustomText variant='headline-1'>{title}</CustomText>}
      open={isOpen}
      className='slide-in-right view-photo-modal !w-auto'
      transitionName=''
      maskTransitionName=''
      footer={isFooter ? undefined : null}
      {...rest}>
      <img src={photo} style={{ maxHeight: '80vh' }} alt='photoModal' />
    </Modal>
  );
};
