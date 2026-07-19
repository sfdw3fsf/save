'use client';
import { Modal, ModalBody, ModalContent, ModalOverlay } from '@chakra-ui/react';
import { useState } from 'react';

type EditModalProps = {
  isOpen: boolean;
  onClose: () => void;
  onSubmit: (password: string) => void;
};
const EditModal = ({ isOpen, onClose, onSubmit }: EditModalProps) => {
  const [password, setPassword] = useState<string>('');
  return (
    <Modal isOpen={isOpen} onClose={onClose} blockScrollOnMount={false} closeOnOverlayClick={true} isCentered size='lg'>
      <ModalOverlay />
      <ModalContent bg='white' p={0} className='w-[480px] py-3 '>
        <ModalBody display={'flex'} flexDirection={'column'} alignItems={'center'} justifyContent={'center'}>
          <div className='text-[15px] font-medium'>
            방명록 작성시 입력했던
            <br />
            비밀번호를 입력 해주세요.
          </div>
          <input
            value={password}
            onChange={(e) => {
              setPassword(e.target.value);
            }}
            type='text'
            className='input font-normal text-[14px] flex-1 mt-[25px] '
            placeholder='비밀번호 입력'
          />

          <button
            onClick={() => {
              onSubmit(password);
            }}
            className='text-[16px] font-bold text-green-main mt-3'>
            확인
          </button>
        </ModalBody>
      </ModalContent>
    </Modal>
  );
};

export default EditModal;
