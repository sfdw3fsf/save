import { Modal, ModalBody, ModalContent, ModalOverlay } from '@chakra-ui/react';

type DonationModal = {
  isOpen: boolean;
  onClose: () => void;
};
const DonationModal = ({ isOpen, onClose }: DonationModal) => {
  return (
    <Modal isOpen={isOpen} onClose={onClose} blockScrollOnMount={false} closeOnOverlayClick={false} isCentered size='lg'>
      <ModalOverlay />
      <ModalContent bg='white' p={0} w='312px' h='570px'>
        <ModalBody position={'relative'} w={'100%'} p={0} display={'flex'}>
          <img className='w-full h-full object-cover' src={`/images/obituary/donation-info.png`} alt='' />
          <button
            onClick={onClose}
            style={{
              position: 'absolute',
              bottom: '27px',
              left: '50%',
              transform: 'translateX(-50%)',
              height: '44px',
              width: '260px',
            }}></button>
        </ModalBody>
      </ModalContent>
    </Modal>
  );
};

export default DonationModal;
