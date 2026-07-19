'use client';
import { Divider, Modal, ModalBody, ModalContent, ModalOverlay } from '@chakra-ui/react';

type SuccessModalProps = {
  id: string;
  isOpen: boolean;
  onClose: () => void;
};
const SuccessModal = ({ id, isOpen, onClose }: SuccessModalProps) => {
  return (
    <Modal isOpen={isOpen} onClose={onClose} blockScrollOnMount={false} closeOnOverlayClick={false} isCentered size='lg'>
      <ModalOverlay />
      <ModalContent bg='white' p={0} className='w-[480px] py-3 '>
        <ModalBody display={'flex'} flexDirection={'column'} alignItems={'center'} justifyContent={'center'}>
          <div className='text-[15px] font-medium text-center'>
            방명록이 성공적으로
            <br />
            수정 되었습니다.
          </div>

          <Divider />

          <a href={`/obituary/card-owner/guest-list?id=${id}`} className='text-[16px] font-bold text-green-main mt-3'>
            방명록 보기
          </a>
        </ModalBody>
      </ModalContent>
    </Modal>
  );
};

export default SuccessModal;
