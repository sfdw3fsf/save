'use client';
import { Divider, Modal, ModalBody, ModalContent, ModalOverlay } from '@chakra-ui/react';
import { useRouter } from 'next/navigation';

type CreateSuccessModalProps = {
  id: string;
  isOpen: boolean;
  onClose: () => void;
};
const CreateSuccessModal = ({ id, isOpen, onClose }: CreateSuccessModalProps) => {
  const router = useRouter();

  const onGoBack = () => {
    router.push(`/obituary/card-owner?id=${id}`);
  };

  const onDonate = () => {
    router.push(`/obituary/card-guest/condolence?id=${id}`);
  };
  return (
    <Modal isOpen={isOpen} onClose={onClose} blockScrollOnMount={false} closeOnOverlayClick={false} isCentered size='lg'>
      <ModalOverlay />
      <ModalContent bg='white' p={0} className='w-[480px] py-3 '>
        <ModalBody display={'flex'} flexDirection={'column'} alignItems={'center'} justifyContent={'center'}>
          <div className='text-[15px] font-medium text-center'>
            방명록이 성공적으로
            <br />
            등록 되었습니다.
          </div>

          <Divider />

          <div className='flex items-center justify-between w-full border-t-[1px] mt-2'>
            <button onClick={onGoBack} className='flex-1 text-[16px] font-bold text-green-main mt-3'>
              돌아가기
            </button>
            <button onClick={onDonate} className='flex-1 text-[16px] font-bold text-green-main mt-3'>
              조의금 보내기
            </button>
          </div>
        </ModalBody>
      </ModalContent>
    </Modal>
  );
};

export default CreateSuccessModal;
