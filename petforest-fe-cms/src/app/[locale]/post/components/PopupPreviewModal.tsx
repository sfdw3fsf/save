import CustomButton from '@/components/common/CustomButton';
import { fileKeys, getFileApi } from '@/store/file/file.api';
import { fileRespToDto } from '@/store/file/file.service';
import { useQuery } from '@tanstack/react-query';
import { Modal } from 'antd';
import { useTranslation } from 'react-i18next';

type PopupReviewModalProps = {
  isOpen: boolean;
  onClose: () => void;
  onSubmit: () => void;
  imageId?: number;
};

const PopupPreviewModal = ({ isOpen, onClose, imageId, onSubmit }: PopupReviewModalProps) => {
  const { t: tPost } = useTranslation('post');

  const { data: popupImage, isPending } = useQuery({
    queryKey: fileKeys.detail(imageId || 0),
    queryFn: () => (imageId ? getFileApi(imageId) : null),
    select: (response) => {
      return response?.data && fileRespToDto(response.data);
    },
  });

  return (
    <Modal
      width={563}
      open={isOpen}
      styles={{ body: { padding: 0 } }}
      onCancel={onClose}
      onClose={onClose}
      transitionName=''
      maskTransitionName=''
      footer={null}
      closable={true}
      loading={isPending}>
      <div className='w-full h-[340px] bg-white '>
        <img src={popupImage?.url} className='w-full h-[302px] object-contain' />
        <div className='flex w-full justify-between mt-3'>
          <CustomButton onClick={onSubmit} type='text' className='flex-1  !h-[40px]'>
            {tPost('popup.button.stop')}
          </CustomButton>
          <CustomButton onClick={onClose} type='text' className='flex-1  !h-[40px]'>
            {tPost('popup.button.close')}
          </CustomButton>
        </div>
      </div>
    </Modal>
  );
};

export default PopupPreviewModal;
