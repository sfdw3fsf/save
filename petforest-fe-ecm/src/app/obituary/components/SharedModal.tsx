import { orString } from '@/utils/parser/io.parser';
import { LinkIcon } from '@chakra-ui/icons';
import { Divider, Modal, ModalBody, ModalCloseButton, ModalContent, ModalHeader, ModalOverlay } from '@chakra-ui/react';
import { useSearchParams } from 'next/navigation';
import { useMemo } from 'react';

type SharedModalProps = {
  isOpen: boolean;
  onClose: () => void;
  onKakaoShare?: () => void;
};
const SharedModal = ({ isOpen, onClose, onKakaoShare }: SharedModalProps) => {
  const searchParams = useSearchParams();

  const id: string = useMemo(() => {
    return orString(searchParams.get('id'));
  }, [searchParams]);

  return (
    <Modal isOpen={isOpen} onClose={onClose} blockScrollOnMount={false} closeOnOverlayClick={false} isCentered size='lg'>
      <ModalOverlay />
      <ModalContent bg='white' p={0}>
        <ModalHeader className='flex justify-center text-[15px]'>
          <div>부고장을 공유할 방법을 선택해주세요.</div>
          <ModalCloseButton />
        </ModalHeader>
        <Divider />
        <ModalBody py={18} gap={'20px'} display={'flex'} justifyContent={'center'}>
          <button onClick={onKakaoShare} className='shared-container'>
            <img className='shared-icon' src='/images/obituary/kakao-logo.png' alt='kakao-logo' />
            <div className='shared-text'>카카오톡</div>
          </button>
          <button
            className='shared-container'
            onClick={async () => {
              await navigator.clipboard.writeText(`https://pet-forest.wala-dev.com/obituary/card-guest?id=${id}`);
              alert('복사되었습니다.');
            }}>
            <div className='shared-icon'>
              <LinkIcon color={'white'} />
            </div>
            <div className='shared-text'>링크복사</div>
          </button>
        </ModalBody>
      </ModalContent>
    </Modal>
  );
};

export default SharedModal;
