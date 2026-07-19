import { Modal, ModalBody, ModalContent, ModalOverlay, useOutsideClick } from '@chakra-ui/react';
import { useRef } from 'react';

type PromotionPopupProps = {
  isOpen: boolean;
  onClose: () => void;
};

const PromotionPopup = ({ isOpen, onClose }: PromotionPopupProps) => {
  const promotionPopupRef = useRef<any>();

  useOutsideClick({
    ref: promotionPopupRef,
    handler: () => onClose(),
  });

  return (
    <Modal isOpen={isOpen} onClose={onClose} blockScrollOnMount={false} closeOnOverlayClick={false} isCentered size='lg'>
      <ModalOverlay />
      <ModalContent bg='white' p={0}>
        <ModalBody display={'flex'} justifyContent={'center'} p={0}>
          <div ref={promotionPopupRef} className='w-full max-h-[700px]'>
            <img className='w-full flex-1' src='/images/branch/flowers.jpeg' alt='' />
            <div className='flex justify-between h-[40px] text-[12px] font-normal'>
              <button className='flex-1 border-r-2 border-grey-mist'>오늘하루 그만보기</button>
              <button onClick={onClose} className='flex-1'>
                닫기
              </button>
            </div>
          </div>
        </ModalBody>
      </ModalContent>
    </Modal>
  );
};

export default PromotionPopup;
