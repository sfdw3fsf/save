import { ObituaryDTO } from '@/store/obituary/dto/obituary.dto';
import { CheckCircleIcon } from '@chakra-ui/icons';
import { Divider, Modal, ModalBody, ModalContent, ModalOverlay } from '@chakra-ui/react';

type SuccessModalProps = {
  data?: ObituaryDTO;
  isOpen: boolean;
  onClose: () => void;
  id: string;
};
const SuccessModal = ({ data, isOpen, onClose, id }: SuccessModalProps) => {
  return (
    <Modal isOpen={isOpen} onClose={onClose} blockScrollOnMount={false} closeOnOverlayClick={false} isCentered size='lg'>
      <ModalOverlay />
      <ModalContent bg='white' p={0} maxW={480}>
        <ModalBody pt={'45px'} textAlign={'center'} display={'flex'} flexDirection={'column'} alignItems={'center'}>
          <CheckCircleIcon color={'#CCCCCC'} w={'40px'} h={'40px'} />
          <div className='text-[16px] font-semibold mt-[12px]'>
            <p>
              부고장이 성공적으로 <br /> 제작 되었습니다.
            </p>

            <p className='pt-[12px] pb-[24px] text-green-main'>
              부고장은 장례일로부터
              <strong>50일</strong>
              까지 유효합니다.
            </p>

            <p>{data?.guardianInformation.branch}</p>
            <p>{data?.guardianInformation.funeralDate}</p>
          </div>

          <div className='mt-[32px] flex items-center gap-[42px] w-full text-left font-[14px]'>
            <span className='flex-1 text-right text-grey-mist'>보호자</span>
            <span className='flex-1'>{data?.guardianInformation.name}</span>
          </div>

          <Divider className='my-5' />

          <div className='flex flex-col gap-3'>
            <div className='flex items-center gap-[42px] w-full text-left font-[14px]'>
              <span className='flex-1 text-right text-grey-mist'>반려동물</span>
              <span className='flex-1'>{data?.petInformation.name}</span>
            </div>

            <div className='flex items-center gap-[42px] w-full text-left font-[14px]'>
              <span className='flex-1 text-right text-grey-mist'>상세정보</span>
              <span className='flex-1'>
                {data?.petInformation.petType.name} | {data?.petInformation.petBreed.name}
              </span>
            </div>

            <div className='flex items-center gap-[42px] w-full text-left font-[14px]'>
              <span className='flex-1 text-right text-grey-mist'>나이</span>
              <span className='flex-1'>{data?.petInformation.age ? `${data.petInformation.age}살` : ''}</span>
            </div>
          </div>
          <Divider className='mt-[36px]' />

          <p className='text-[12px] text-left w-full py-2'>
            {`• 변경하실 내용이 있으시면 `}
            <a className='text-green-main underline'>부고장 수정하기</a> 를 통해 수정해주시면 됩니다.
          </p>
          <p className='text-[12px] text-left w-full pb-2'>
            {`• 부고장 내 `}
            <a className='text-green-main underline'>부고장 전송</a> 을 통해 링크 전송이 가능합니다.
          </p>

          <Divider />

          <div className='w-full h-[46px] flex justify-center items-center'>
            <a href={`/obituary/card-owner?id=${id}`} className=' text-green-main text-[16px] font-bold text-center'>
              부고장 보러가기
            </a>
          </div>
        </ModalBody>
      </ModalContent>
    </Modal>
  );
};

export default SuccessModal;
