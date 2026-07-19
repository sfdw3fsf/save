'use client';
import { Modal, ModalBody, ModalContent, ModalOverlay } from '@chakra-ui/react';
import { useState } from 'react';

type DeleteModalProps = {
  isOpen: boolean;
  onClose: () => void;
  onSubmit: () => void;
};
const DeleteModal = ({ isOpen, onClose, onSubmit }: DeleteModalProps) => {
  const [password, setPassword] = useState<string>('');
  return (
    <Modal isOpen={isOpen} onClose={onClose} blockScrollOnMount={false} closeOnOverlayClick={false} isCentered size='lg'>
      <ModalOverlay />
      <ModalContent bg='white' p={0} className='w-[480px] py-3 '>
        <ModalBody display={'flex'} flexDirection={'column'} alignItems={'center'} justifyContent={'center'}>
          <div className='text-[15px] font-medium'>
            삭제한 방명록은 다시 복구할 수 없습니다.
            <br />
            그래도 삭제하시겠습니까?
          </div>

          <div className='flex items-center justify-between w-full border-t-[1px] mt-2'>
            <button onClick={onClose} className='flex-1 text-[16px] font-bold text-green-main mt-3'>
              예
            </button>
            <button onClick={onSubmit} className='flex-1 text-[16px] font-bold text-green-main mt-3'>
              아니요
            </button>
          </div>
        </ModalBody>
      </ModalContent>
    </Modal>
  );
};

export default DeleteModal;
